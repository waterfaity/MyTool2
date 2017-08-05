package com.waterfairy.tool2.rocketMQ;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.client.producer.SendStatus;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.waterfairy.rocketMQ.RocketMQConsumer;
import com.waterfairy.rocketMQ.RocketMQManger;
import com.waterfairy.rocketMQ.RocketMQProducer;
import com.waterfairy.tool2.R;
import com.waterfairy.utils.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RocketMQActivity extends AppCompatActivity implements RocketMQConsumer.OnRocketMQConsumerListener, RocketMQProducer.OnRocketProducerListener {
    private EditText mETServerIp, mETTopic, mETGroup, mETSubExpression;
    private TextView mTVProducerState, mTVConsumerState;
    private TextView mTVProducerInfo, mTVConsumerInfo;

    private EditText mETInputSend, mETInputTime;

    private String mInputMessage;


    private RocketMQManger mRocketMQManger;
    private RocketMQProducer mRocketProducer;
    private RocketMQConsumer mRocketConsumer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rocket_mq);
        initView();
        initData();
    }

    private void initData() {
        mRocketMQManger = RocketMQManger.getInstance();
    }

    private void initView() {
        mETServerIp = (EditText) findViewById(R.id.serve_ip);
        mETTopic = (EditText) findViewById(R.id.topic);
        mETGroup = (EditText) findViewById(R.id.group);
        mETSubExpression = (EditText) findViewById(R.id.subExpression);

        mETInputSend = (EditText) findViewById(R.id.input);
        mETInputTime = (EditText) findViewById(R.id.input_time);

        mTVProducerState = (TextView) findViewById(R.id.producer_state);
        mTVConsumerState = (TextView) findViewById(R.id.consumer_state);

        mTVProducerInfo = (TextView) findViewById(R.id.producer_info);
        mTVConsumerInfo = (TextView) findViewById(R.id.consumer_info);
    }

    public void startProducer(View view) {
        if (mRocketProducer != null && mRocketProducer.isConnect()) {
            mRocketProducer.disConnect();
        }
        String group = mETGroup.getText().toString();
        String topic = mETTopic.getText().toString();
        String server = mETServerIp.getText().toString();
        String subExpression = mETSubExpression.getText().toString();
        if (TextUtils.isEmpty(group)) {
            ToastUtils.show("请输入 group");
        }
        if (TextUtils.isEmpty(topic)) {
            ToastUtils.show("请输入 topic");
        }
        if (TextUtils.isEmpty(server)) {
            ToastUtils.show("请输入 server");
        }
        mRocketProducer = mRocketMQManger.initProducer(server, group, topic, subExpression, this);
    }

    public void startConsumer(View view) {

        if (mRocketConsumer != null && mRocketConsumer.isConnect()) {
            mRocketConsumer.disConnect();
        }
        String group = mETGroup.getText().toString();
        String topic = mETTopic.getText().toString();
        String server = mETServerIp.getText().toString();
        String subExpression = mETSubExpression.getText().toString();
        if (TextUtils.isEmpty(group)) {
            ToastUtils.show("请输入 group");
        }
        if (TextUtils.isEmpty(topic)) {
            ToastUtils.show("请输入 topic");
        }
        if (TextUtils.isEmpty(server)) {
            ToastUtils.show("请输入 server");
        }
        mRocketConsumer = mRocketMQManger.initConsumer(server, group, topic, getFilterTime(), subExpression, this);
    }

    public void send(View view) {
        if (mRocketProducer != null) {
            mInputMessage = mETInputSend.getText().toString();
            String topic = mETTopic.getText().toString();
            String subExpression = mETSubExpression.getText().toString();
            mRocketProducer.send(topic, subExpression, mInputMessage);
        } else {
            ToastUtils.show("未启动");
        }

    }

    @Override
    public void onConsumerConnect(final boolean state, MQClientException exception) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (state) {
                    mTVConsumerState.setText("开");
                } else {
                    mTVConsumerState.setText("关");
                }
                mTVConsumerInfo.getText();
            }
        });
    }

    @Override
    public void onConsumerReceiveMsg(final String msg, final long bornTimestamp, final MessageExt message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String content = msg;
                if (TextUtils.isEmpty(content)) {
                    byte[] body = message.getBody();
                    content = new String(body);
                }
                String contentStr = mTVConsumerInfo.getText().toString();
                mTVConsumerInfo.setText(getTime(bornTimestamp) + " \n -> " + content + "--" + message.getTags() + "\n" + contentStr);
            }
        });
    }


    @Override
    public void onProducerConnect(final boolean state, MQClientException exception) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (state) {
                    mTVProducerState.setText("开");
                } else {
                    mTVProducerState.setText("关");
                }
            }
        });
    }


    @Override
    public void onProducerSend(final SendResult sendResult, Exception exception) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (sendResult != null) {
                    SendStatus sendStatus = sendResult.getSendStatus();
                    if (sendStatus == SendStatus.SEND_OK) {
                        String content = mTVProducerInfo.getText().toString();
                        mTVProducerInfo.setText(getTime(System.currentTimeMillis()) + "\n -> " + mInputMessage + "\n" + content);
                    }
                }
            }
        });
    }

    public String getTime(long bornTimestamp) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(new Date(bornTimestamp));

    }

    public void clear(View view) {
        mTVProducerInfo.setText("");
        mTVConsumerInfo.setText("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRocketProducer != null)
            mRocketProducer.disConnect();
        if (mRocketConsumer != null)
            mRocketConsumer.disConnect();
    }

    public void setTime(View view) {

        if (mRocketConsumer != null) {
            mRocketConsumer.setFilterTime(getFilterTime());
        } else {
            ToastUtils.show("未开启");
        }
    }

    private long getFilterTime() {
        String time = mETInputTime.getText().toString();
        if (TextUtils.isEmpty(time)) {
            time = "60000";
        }
        return Long.parseLong(time);
    }
}

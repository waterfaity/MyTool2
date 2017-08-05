package com.waterfairy.rocketMQ;

import android.text.TextUtils;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.client.producer.SendStatus;
import com.alibaba.rocketmq.common.message.Message;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * rockerMQ 消息生产者
 * Created by water_fairy on 2017/8/4.
 * 995637517@qq.com
 */

public class RocketMQProducer {

    private DefaultMQProducer mProducer;
    private String nameServer;//服务器名字
    private String groupName;//组名
    private String topic;//主题
    private String instanceName;//连接名称???
    private boolean isConnect;
    private OnRocketProducerListener onRocketProducerListener;
    private String subExpression;

    private RocketMQProducer() {

    }

    /**
     * 新建
     *
     * @return RocketMQProducer
     */
    public static RocketMQProducer newInstance() {
        return new RocketMQProducer();
    }

    /**
     * 初始化数据
     *
     * @param nameServer    服务器地址
     * @param groupName     组名
     * @param topic         主题
     * @param subExpression
     * @return RocketMQProducer
     */
    public RocketMQProducer initData(String nameServer, String groupName, String topic, String subExpression) {
        this.nameServer = nameServer;
        this.groupName = groupName;
        this.topic = topic;
        this.subExpression = subExpression;
        return this;
    }

    /**
     * @param onRocketProducerListener 监听
     */
    public void setOnRocketProducerListener(OnRocketProducerListener onRocketProducerListener) {
        this.onRocketProducerListener = onRocketProducerListener;
    }

    /**
     * 启动
     */
    public void start() {

        new Thread() {
            @Override
            public void run() {
                startProducer();
            }
        }.start();
    }

    private void startProducer() {
        mProducer = new DefaultMQProducer(groupName);
        mProducer.setNamesrvAddr(nameServer);
        instanceName = UUID.randomUUID().toString();
        mProducer.setInstanceName(instanceName);
        mProducer.setVipChannelEnabled(false);
        try {
            mProducer.start();
            isConnect = true;
            if (onRocketProducerListener != null)
                onRocketProducerListener.onProducerConnect(true, null);
        } catch (MQClientException e) {
            isConnect = false;
            if (onRocketProducerListener != null)
                onRocketProducerListener.onProducerConnect(false, e);
            e.printStackTrace();
        }
        System.out.println("RocketMQProducer Started! group="
                + mProducer.getProducerGroup() + " instance="
                + mProducer.getInstanceName());
    }


    /**
     * 发送消息
     *
     * @param content 消息内容 string
     */
    public void send(String content) {
        send(topic, subExpression, content);
    }

    /**
     * 发送消息
     *
     * @param topic   消息主题 string
     * @param content 消息内容 string
     */
    public void send(String topic, String subExpression, String content) {
        Message message = new Message();
        try {
            message.setBody(content.getBytes("UTF-8"));
            send(topic, subExpression, message);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            if (onRocketProducerListener != null)
                onRocketProducerListener.onProducerSend(null, e);
        }
    }

    /**
     * 发送消息
     *
     * @param message 消息内容 Message
     */
    public void send(Message message) {
        send(topic, subExpression, message);
    }

    /**
     * 发送消息
     *
     * @param topic   消息主题 topic
     * @param message 消息内容 Message
     */
    public void send(final String topic, final String subExpression, final Message message) {
        new Thread() {
            @Override
            public void run() {
                sendInThread(topic, subExpression, message);
            }
        }.start();
    }

    private void sendInThread(String topic, String subExpression, Message message) {
        message.setTopic(topic);
        if (!TextUtils.isEmpty(subExpression)) {
            message.setTags(subExpression);
        }
        try {
            SendResult result = mProducer.send(message);
            SendStatus status = result.getSendStatus();
            if (onRocketProducerListener != null)
                onRocketProducerListener.onProducerSend(result, null);
            System.out.println("messageId=" + result.getMsgId() + ", status=" + status);
        } catch (Exception e) {
            e.printStackTrace();
            if (onRocketProducerListener != null)
                onRocketProducerListener.onProducerSend(null, e);
        }
    }

    public void disConnect() {
        mProducer.shutdown();
        if (onRocketProducerListener != null)
            onRocketProducerListener.onProducerConnect(false, null);
    }

    public interface OnRocketProducerListener {
        void onProducerConnect(boolean state, MQClientException exception);

        void onProducerSend(SendResult sendResult, Exception exception);
    }

    public String getInstanceName() {
        return instanceName;
    }


    public String getNameServer() {
        return nameServer;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getTopic() {
        return topic;
    }

    public boolean isConnect() {
        return isConnect;
    }

    public DefaultMQProducer getConsumer() {
        return mProducer;
    }
}

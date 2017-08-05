package com.waterfairy.rocketMQ;

/**
 * * rockerMQ 消息消费者
 * Created by water_fairy on 2017/8/4.
 * 995637517@qq.com
 */

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

public class RocketMQConsumer implements MessageListenerConcurrently {
    private long filterTime = 60000;//过滤时间  -1  忽略时间
    private DefaultMQPushConsumer mConsumer;
    private String nameServer;//服务器名字
    private String groupName;//组名
    private String topic;//主题
    private String instanceName;//连接名称???
    private OnRocketMQConsumerListener onRocketMQConsumerListener;
    private String subExpression = "*";

    private boolean isConnect;

    private RocketMQConsumer() {

    }

    public static RocketMQConsumer newInstance() {
        return new RocketMQConsumer();
    }

    public RocketMQConsumer initData(String nameServer, String groupName, String topic, String subExpression) {
        this.nameServer = nameServer;
        this.groupName = groupName;
        this.topic = topic;
        this.subExpression = subExpression;
        return this;
    }

    public RocketMQConsumer setFilterTime(long filterTime) {
        this.filterTime = filterTime;
        return this;
    }

    public RocketMQConsumer setRocketConsumerListener(OnRocketMQConsumerListener onRocketMQConsumerListener) {
        this.onRocketMQConsumerListener = onRocketMQConsumerListener;
        return this;
    }

    public void start() {
        new Thread() {
            @Override
            public void run() {
                startConsumer();
            }
        }.start();
    }

    private void startConsumer() {
        try {
            mConsumer = new DefaultMQPushConsumer(groupName);
            mConsumer.setNamesrvAddr(nameServer);
            mConsumer.setVipChannelEnabled(false);
            mConsumer.subscribe(topic, subExpression);
            instanceName = UUID.randomUUID().toString();
            mConsumer.setInstanceName(instanceName);
            mConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
            mConsumer.registerMessageListener(this);
            mConsumer.start();
            isConnect = true;
            if (onRocketMQConsumerListener != null) {
                onRocketMQConsumerListener.onConsumerConnect(true, null);
            }
        } catch (MQClientException e) {
            isConnect = false;
            e.printStackTrace();
            if (onRocketMQConsumerListener != null) {
                onRocketMQConsumerListener.onConsumerConnect(false, e);
            }
        }

        System.out.println("RocketMQConsumer Started! group=" + mConsumer.getConsumerGroup() +
                " instance=" + mConsumer.getInstanceName());
    }


    public void disConnect() {
        if (mConsumer != null) {
            mConsumer.shutdown();
            if (onRocketMQConsumerListener != null) {
                onRocketMQConsumerListener.onConsumerConnect(false, null);
            }
        }
    }


    public interface OnRocketMQConsumerListener {

        void onConsumerConnect(boolean state, MQClientException exception);

        void onConsumerReceiveMsg(String msg, long bornTimestamp, MessageExt message);

    }

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        for (MessageExt message : list) {
            //  filterTime 之前的认为过期
            if (filterTime > 0 && System.currentTimeMillis() - message.getBornTimestamp() > filterTime) {
                continue;// 过期消息跳过
            }
            String msg = "";
            try {
                msg = new String(message.getBody(), "UTF-8");
                if (onRocketMQConsumerListener != null) {
                    message.getTopic();
                    onRocketMQConsumerListener.onConsumerReceiveMsg(msg, message.getBornTimestamp(), message);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                if (onRocketMQConsumerListener != null) {
                    onRocketMQConsumerListener.onConsumerReceiveMsg(null, message.getBornTimestamp(), message);
                }
            }

            System.out.println("msg data from rocketMQ:" + message.toString());
        }

        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
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

    public String getInstanceName() {
        return instanceName;
    }

    public boolean isConnect() {
        return isConnect;
    }

    public DefaultMQPushConsumer getConsumer() {
        return mConsumer;
    }
}
package com.waterfairy.rocketMQ;

import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;

/**
 * Created by water_fairy on 2017/8/5.
 * 995637517@qq.com
 */

public class RocketMQManger {
    private static final RocketMQManger ROCKET_MQ_MANGER = new RocketMQManger();
    private RocketMQConsumer mConsumer;
    private RocketMQProducer mProducer;

    public static RocketMQManger getInstance() {
        return ROCKET_MQ_MANGER;
    }

    /**
     * 初始化生产者  生产消息->发送消息
     */
    public RocketMQProducer initProducer(String nameServer, String groupName, String topic,String subExpression, RocketMQProducer.OnRocketProducerListener onRocketProducerListener) {
        mProducer = RocketMQProducer.newInstance();
        mProducer.initData(nameServer, groupName, topic,subExpression);
        mProducer.setOnRocketProducerListener(onRocketProducerListener);
        mProducer.start();
        return mProducer;
    }

    public RocketMQConsumer initConsumer(String nameServer, String groupName, String topic, RocketMQConsumer.OnRocketMQConsumerListener onRocketMQConsumerListener) {
        return initConsumer(nameServer, groupName, topic, 60 * 1000, "*", onRocketMQConsumerListener);
    }

    /**
     * 初始化消费者 消费消息->接收消息
     */
    public RocketMQConsumer initConsumer(String nameServer, String groupName, String topic, long filterTime, String subExpression, RocketMQConsumer.OnRocketMQConsumerListener onRocketMQConsumerListener) {
        mConsumer = RocketMQConsumer.newInstance();
        mConsumer.setFilterTime(filterTime);
        mConsumer.initData(nameServer, groupName, topic,subExpression);
        mConsumer.setRocketConsumerListener(onRocketMQConsumerListener);
        mConsumer.start();
        return mConsumer;
    }

    public RocketMQConsumer getConsumer() {
        return mConsumer;
    }

    public RocketMQProducer getProducer() {
        return mProducer;
    }
}

package com.waterfairy.tool2.mqtt.he;


/**
 * Created by water_fairy on 2017/7/31.
 * 995637517@qq.com
 */

public interface MqttProcessor {
    boolean supportTopicNameCompression();

    void process(MqttConnack var1);
}

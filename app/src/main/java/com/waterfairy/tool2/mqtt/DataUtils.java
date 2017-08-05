package com.waterfairy.tool2.mqtt;
import com.waterfairy.tool2.mqtt.he.MqttConnect;
import com.waterfairy.tool2.mqtt.he.MqttException;
import com.waterfairy.tool2.mqtt.he.MqttPacket;

/**
 * Created by water_fairy on 2017/7/31.
 * 995637517@qq.com
 */

public class DataUtils {

    public static MqttConnect getConnect(String clientId, boolean cleanStart, int keepAlive) throws MqttException {
        MqttConnect var12 = new MqttConnect();
        var12.setClientId(clientId);
        var12.CleanStart = cleanStart;
        var12.TopicNameCompression = false;
        var12.KeepAlive = (short) keepAlive;
//        if (var5 != null) {
//            var12.Will = true;
//            var12.WillTopic = var5;
//            var12.WillQoS = var6;
//            var12.WillRetain = var8;
//            var12.WillMessage = var7;
//        } else {
            var12.Will = false;
//        }

//        this.setKeepAlive(var4);
//        this.doConnect(var12, var2, var4);
        return var12;
    }

}

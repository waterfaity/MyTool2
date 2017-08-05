package com.waterfairy.tool2.mqtt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ibm.mqtt.MqttClient;
import com.waterfairy.tool2.R;
import com.waterfairy.tool2.mqtt.he.MqttConnect;
import com.waterfairy.tool2.mqtt.he.MqttException;
import com.waterfairy.utils.PingUtils;
import com.waterfairy.utils.ToastUtils;
import com.waterfairy.wifi.listener.WifiUserListener;
import com.waterfairy.wifi.manger.WifiManager;

import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mqtt);


    }

    private void initMqtt() {
        try {
            org.eclipse.paho.client.mqttv3.MqttClient client = new org.eclipse.paho.client.mqttv3.MqttClient("", "water", new MemoryPersistence());

        } catch (org.eclipse.paho.client.mqttv3.MqttException e) {
            e.printStackTrace();
        }
    }


    final WifiManager instance = WifiManager.getInstance();
    String url1;

    public void disConnectWifi(View view) {
        instance.disconnect(url1);
    }

    public void connectWifi(View view) {
        String url = ((TextView) findViewById(R.id.url)).getText().toString();
        String port = ((TextView) findViewById(R.id.port)).getText().toString();
        url1 = TextUtils.isEmpty(url) ? "192.168.3.25" : url;
        int portNum = Integer.parseInt(TextUtils.isEmpty(port) ? "61680" : port);

        instance.setPort(portNum);
        instance.connect(url1, new WifiUserListener() {

            @Override
            public void onConnecting() {
                Log.i("test", "onConnecting: " + System.currentTimeMillis());
            }

            @Override
            public void onConnectSuccess() {
                Log.i("test", "onConnectSuccess: " + System.currentTimeMillis());
//                public $operations=array(
//                        "MQTT_CONNECT"=>1,//请求连接
//                        "MQTT_CONNACK"=>2,//请求应答
//                        "MQTT_PUBLISH"=>3,//发布消息
//                        "MQTT_PUBACK"=>4,//发布应答
//                        "MQTT_PUBREC"=>5,//发布已接收，保证传递1
//                        "MQTT_PUBREL"=>6,//发布释放，保证传递2
//                        "MQTT_PUBCOMP"=>7,//发布完成，保证传递3
//                        "MQTT_SUBSCRIBE"=>8,//订阅请求
//                        "MQTT_SUBACK"=>9,//订阅应答
//                        "MQTT_UNSUBSCRIBE"=>10,//取消订阅
//                        "MQTT_UNSUBACK"=>11,//取消订阅应答
//                        "MQTT_PINGREQ"=>12,//ping请求
//                        "MQTT_PINGRESP"=>13,//ping响应
//                        "MQTT_DISCONNECT"=>14//断开连接
//        );
                try {
                    MqttConnect water = DataUtils.getConnect("water", true, 5);
//                    instance.writeMsgFromUser(url1, water.toBytes());

                } catch (MqttException e) {
                    e.printStackTrace();
                }
//                instance.writeMsgFromUser(url1, new byte[]{10, 12});

            }

            @Override
            public void onConnectError() {
                Log.i("test", "onConnectError: " + System.currentTimeMillis());
            }

            @Override
            public void onRead(byte[] readBytes) {
                String content = new String(readBytes);
                Log.i("test", "onRead: " + +System.currentTimeMillis());
                Log.i("test", "onRead: " + content);
            }

            @Override
            public void onWrite(byte[] writeBytes) {
                Log.i("test", "onWrite: " + System.currentTimeMillis());
            }

            @Override
            public void onWriteError() {
                Log.i("test", "onWriteError: ");
            }

            @Override
            public void onDisconnect() {
                Log.i("test", "onDisconnect: " + System.currentTimeMillis());
            }

            @Override
            public void isRightServer(boolean isRightServer) {
                Log.i("test", "isRightServer: " + System.currentTimeMillis());
            }
        });
    }

    public void connect(View view) {
        String url = ((TextView) findViewById(R.id.url)).getText().toString();
        String port = ((TextView) findViewById(R.id.port)).getText().toString();
        PushService.MQTT_HOST = TextUtils.isEmpty(url) ? "192.168.3.25" : url;
        PushService.MQTT_BROKER_PORT_NUM = Integer.parseInt(TextUtils.isEmpty(port) ? "61680" : port);
        PushService.actionStart(getApplicationContext());
    }

    public void disConnect(View view) {
        PushService.actionStop(getApplicationContext());
    }

    public void ping(View view) {
        String url = ((TextView) findViewById(R.id.url)).getText().toString();
        ;
        if (TextUtils.isEmpty(url)) ToastUtils.show("kong");
        else {
            int ping = PingUtils.ping(url);
            ToastUtils.show(ping + "");
        }
    }
}

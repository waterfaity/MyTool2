package com.waterfairy.tool2.mqtt.he;


/**
 * Created by water_fairy on 2017/7/31.
 * 995637517@qq.com
 */

public class MqttConnect extends  MqttPacket {
    public String ProtoName = "MQIsdp";
    public short ProtoVersion = 3;
    public boolean CleanStart;
    public boolean TopicNameCompression;
    public short KeepAlive;
    public boolean Will;
    public int WillQoS;
    public boolean WillRetain;
    public String WillTopic;
    public String WillMessage;
    protected String ClientId;

    public MqttConnect() {
        this.setMsgType((short) 1);
    }

    public MqttConnect(byte[] var1) {
        super(var1);
        this.setMsgType((short) 1);
    }

    public byte[] toBytes() {
        byte var1 = 0;
        this.message = new byte[42];
        int var8 = var1 + 1;
        this.message[var1] = super.toBytes()[0];
        byte[] var2 = MqttUtils.StringToUTF(this.ProtoName);
        System.arraycopy(var2, 0, this.message, var8, var2.length);
        var8 += var2.length;
        this.message[var8++] = (byte) this.ProtoVersion;
        int var3 = this.TopicNameCompression ? 1 : 0;
        int var4 = this.CleanStart ? 2 : 0;
        byte var5 = this.Will ? (byte) ((this.WillRetain ? 32 : 0) | (byte) ((this.WillQoS & 3) << 3) | 4) : 0;
        this.message[var8++] = (byte) (var3 | var4 | var5);
        this.message[var8++] = (byte) (this.KeepAlive / 256);
        this.message[var8++] = (byte) (this.KeepAlive % 256);
        var2 = MqttUtils.StringToUTF(this.ClientId);
        System.arraycopy(var2, 0, this.message, var8, var2.length);
        var8 += var2.length;
        if (this.Will) {
            byte[] var6 = MqttUtils.StringToUTF(this.WillTopic);
            byte[] var7 = MqttUtils.StringToUTF(this.WillMessage);
            this.message = MqttUtils.concatArray(MqttUtils.concatArray(this.message, 0, var8, var6, 0, var6.length), var7);
            var8 += var6.length + var7.length;
        }

        this.message = MqttUtils.SliceByteArray(this.message, 0, var8);
        this.createMsgLength();
        return this.message;
    }

    public void process(MqttProcessor var1) {
    }

    public String getClientId() {
        return this.ClientId;
    }

    public void setClientId(String var1) throws MqttException {
        if (var1.length() > 23) {
            throw new MqttException("MQIsdp ClientId > 23 bytes");
        } else {
            this.ClientId = var1;
        }
    }
}
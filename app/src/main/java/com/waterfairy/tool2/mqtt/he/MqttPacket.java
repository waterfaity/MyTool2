package com.waterfairy.tool2.mqtt.he;

/**
 * Created by water_fairy on 2017/7/31.
 * 995637517@qq.com
 */




public abstract class MqttPacket {
    protected byte[] message;
    private short msgType;
    private int msgLength;
    private int msgId = 0;
    private boolean retain;
    private boolean dup;
    private int qos;
    private byte[] payload = null;
    public static final int MAX_CLIENT_ID_LEN = 23;
    public static final int MAX_MSGID = 65535;

    public MqttPacket() {
    }

    public MqttPacket(byte[] var1) {
        byte var2 = var1[0];
        this.msgType = getMsgType(var2);
        this.retain = (var2 & 1) != 0;
        this.dup = (var2 >>> 3 & 1) != 0;
        this.qos = var2 >>> 1 & 3;
    }

    protected static short getMsgType(byte var0) {
        return (short) (var0 >>> 4 & 15);
    }

    protected void createMsgLength() {
        int var1 = this.message.length + -1;
        if (this.payload != null) {
            var1 += this.payload.length;
        }

        this.msgLength = var1;
        int var2 = 0;
        byte[] var3 = new byte[4];

        do {
            int var4 = var1 % 128;
            var1 /= 128;
            if (var1 > 0) {
                var4 |= 128;
            }

            var3[var2++] = (byte) var4;
        } while (var1 > 0);

        byte[] var5 = new byte[this.message.length + var2];
        var5[0] = this.message[0];
        System.arraycopy(var3, 0, var5, 1, var2);
        System.arraycopy(this.message, 1, var5, var2 + 1, this.message.length - 1);
        this.message = var5;
    }

    public abstract void process(MqttProcessor var1);

    public byte[] toBytes() {
        byte[] var1 = new byte[]{(byte) (this.msgType << 4 & 240)};
        if (this.msgType == 8 | this.msgType == 9 | this.msgType == 10 | this.msgType == 11) {
            this.qos = 1;
        }

        byte var4 = (byte) ((this.qos & 3) << 1);
        byte var2 = (byte) (this.dup ? 8 : 0);
        byte var3 = (byte) (this.retain ? 1 : 0);
        var1[0] = (byte) (var1[0] | var4 | var3 | var2);
        return var1;
    }

    public byte[] getPayload() {
        return this.payload;
    }

    public void setPayload(byte[] var1) {
        this.payload = var1;
    }

    public boolean isDup() {
        return this.dup;
    }

    public void setDup(boolean var1) {
        this.dup = var1;
    }

    public byte[] getMessage() {
        return this.message;
    }

    public void setMessage(byte[] var1) {
        this.message = var1;
    }

    public int getMsgId() {
        return this.msgId;
    }

    public void setMsgId(int var1) {
        this.msgId = var1;
    }

    public int getMsgLength() {
        return this.msgLength;
    }

    public void setMsgLength(int var1) {
        this.msgLength = var1;
    }

    public short getMsgType() {
        return this.msgType;
    }

    public void setMsgType(short var1) {
        this.msgType = var1;
    }

    public int getQos() {
        return this.qos;
    }

    public void setQos(int var1) {
        this.qos = var1;
    }

    public boolean isRetain() {
        return this.retain;
    }

    public void setRetain(boolean var1) {
        this.retain = var1;
    }
}

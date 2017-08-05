package com.waterfairy.tool2.mqtt.he;

/**
 * Created by water_fairy on 2017/7/31.
 * 995637517@qq.com
 */

public class MqttException extends Exception {
    private Throwable le = null;

    public MqttException() {
    }

    public MqttException(String var1) {
        super(var1);
    }

    public MqttException(Throwable var1) {
        super(var1 == null?null:var1.toString());
        this.le = var1;
    }

    public Throwable getCause() {
        return this.le;
    }

    public Throwable initCause(Throwable var1) {
        if(this.le != null) {
            throw new IllegalStateException();
        } else if(var1 == this) {
            throw new IllegalArgumentException();
        } else {
            this.le = var1;
            return this;
        }
    }
}
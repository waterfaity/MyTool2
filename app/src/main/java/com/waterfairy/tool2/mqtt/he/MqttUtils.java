package com.waterfairy.tool2.mqtt.he;

import java.io.UnsupportedEncodingException;
import java.util.Vector;

/**
 * Created by water_fairy on 2017/7/31.
 * 995637517@qq.com
 */

public class MqttUtils {
    public static final String STRING_ENCODING = "UTF-8";

    public MqttUtils() {
    }

    public static final byte[] concatArray(byte[] var0, byte[] var1) {
        byte[] var2 = new byte[var0.length + var1.length];
        System.arraycopy(var0, 0, var2, 0, var0.length);
        System.arraycopy(var1, 0, var2, var0.length, var1.length);
        return var2;
    }

    public static final byte[] concatArray(byte[] var0, int var1, int var2, byte[] var3, int var4, int var5) {
        byte[] var6 = new byte[var2 + var5];
        System.arraycopy(var0, var1, var6, 0, var2);
        System.arraycopy(var3, var4, var6, var2, var5);
        return var6;
    }

    public static final long getExpiry(long var0) {
        return System.currentTimeMillis() / 1000L + var0 * 3L / 2L;
    }

    public static final Vector getTopicsWithQoS(byte[] var0) {
        if (var0 == null) {
            return null;
        } else {
            Vector var1 = new Vector();
            int var3 = 0;

            while (var3 <= var0.length - 4) {
                int var4 = ((var0[var3] & 255) << 8) + ((var0[var3 + 1] & 255) << 0);
                StringBuffer var5 = new StringBuffer(var0.length);
                var3 += 2;
                var4 += var3;

                while (var3 < var4 && var4 < var0.length) {
                    int var2 = var0[var3++] & 255;
                    var5.append((char) var2);
                }

                if (var5.toString().length() > 0) {
                    var5.append(var0[var3++]);
                    var1.addElement(var5.toString());
                }
            }

            return var1;
        }
    }

    public static final byte[] SliceByteArray(byte[] var0, int var1, int var2) {
        byte[] var3 = new byte[var2];
        System.arraycopy(var0, var1, var3, 0, var2);
        return var3;
    }

    public static final byte[] StringToUTF(String var0) {
        if (var0 == null) {
            return null;
        } else {
            try {
                byte[] var1 = var0.getBytes("UTF-8");
                byte[] var2 = new byte[var1.length + 2];
                var2[0] = (new Integer(var1.length / 256)).byteValue();
                var2[1] = (new Integer(var1.length % 256)).byteValue();
                System.arraycopy(var1, 0, var2, 2, var1.length);
                return var2;
            } catch (UnsupportedEncodingException var3) {
                System.out.println("MQTT Client: Unsupported string encoding - UTF-8");
                return null;
            }
        }
    }

    public static final int toShort(byte[] var0, int var1) {
        return (((short) var0[var1 + 0] & 255) << 8) + ((short) var0[var1 + 1] & 255);
    }

    public static final String UTFToString(byte[] var0) {
        return UTFToString(var0, 0);
    }

    public static final String UTFToString(byte[] var0, int var1) {
        if (var0 == null) {
            return null;
        } else {
            int var2 = ((var0[0 + var1] & 255) << 8) + ((var0[1 + var1] & 255) << 0);
            if (var2 + 2 > var0.length) {
                return null;
            } else {
                String var3 = null;
                if (var2 > 0) {
                    try {
                        var3 = new String(var0, var1 + 2, var2, "UTF-8");
                    } catch (UnsupportedEncodingException var5) {
                        System.out.println("MQTT Client: Unsupported string encoding - UTF-8");
                    }
                } else {
                    var3 = "";
                }

                return var3;
            }
        }
    }

    public static final Vector UTFToStrings(byte[] var0, int var1) {
        if (var0 == null) {
            return null;
        } else {
            Vector var2 = new Vector();

            int var4;
            for (int var3 = var1; var3 <= var0.length - 3; var3 += var4 + 2) {
                var4 = ((var0[var3] & 255) << 8) + ((var0[var3 + 1] & 255) << 0);
                String var5 = UTFToString(var0, var3);
                if (var5 != null) {
                    var2.addElement(var5);
                }
            }

            return var2;
        }
    }

    public static final String toHexString(byte[] var0, int var1, int var2) {
        StringBuffer var3 = new StringBuffer("");
        if (var1 < 0) {
            var1 = 0;
        }

        for (int var4 = var1; var4 < var1 + var2 && var4 <= var0.length - 1; ++var4) {
            int var5 = var0[var4];
            if (var5 < 0) {
                var5 += 256;
            }

            if (var5 < 16) {
                var3.append("0" + Integer.toHexString(var5));
            } else {
                var3.append(Integer.toHexString(var5));
            }
        }

        return var3.toString();
    }
}
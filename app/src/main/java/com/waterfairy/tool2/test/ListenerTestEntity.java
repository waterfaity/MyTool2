package com.waterfairy.tool2.test;

import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by water_fairy on 2017/7/31.
 * 995637517@qq.com
 */

public class ListenerTestEntity {
    private List<OnTestListener> onTestListenerList;
    private final static ListenerTestEntity LISTENER_TEST_ENTITY = new ListenerTestEntity();

    private ListenerTestEntity() {

    }

    public static ListenerTestEntity getInstance() {
        return LISTENER_TEST_ENTITY;
    }

    public void addListener(OnTestListener onTestListener) {
        if (onTestListenerList == null) {
            onTestListenerList = new ArrayList<>();
        }
        onTestListenerList.add(onTestListener);
    }

    public void setClock() {

    }

    public void stopClock() {

    }

    public void startClock() {

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            msg.arg1++;
            sendEmptyMessageDelayed(0, 1000);
            if (onTestListenerList != null) {
                for (OnTestListener listener : onTestListenerList) {
//                    listener.onUpdate(sys);
                }
            }

        }
    };

    public interface OnTestListener {
        void onUpdate(long time);
    }
}

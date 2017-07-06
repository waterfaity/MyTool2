/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.waterfairy.tool2.audiorecord;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.IBinder;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;


import com.waterfairy.tool2.R;

import java.io.File;

public class RecorderService extends Service implements Callback {
	private NotificationManager mNM;
	private int NOTIFICATION = R.string.app_name;
	private RecMicToMp3 mRecMicToMp3;
	private TelephonyManager mTeleManager;
	private WakeLock mWakeLock;
	private static String mFilePath = "";
	private static long mStartTime = 0;
	private RemainingTimeCalculator mRemainingTimeCalculator;
	private final IBinder mBinder = new RecorderServiceBinder();
	private Handler handler = new Handler(this);
	public final static String FilePath = "filePath";
	private final Handler mHandler = new Handler();
	private onRecordMessageListener mOnRecordMessageListener;

	public class RecorderServiceBinder extends Binder {
		public RecorderService getService() {
			return RecorderService.this;
		}
	}

	@Override
	public void onCreate() {
		mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		mTeleManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		mTeleManager.listen(mPhoneStateListener,
				PhoneStateListener.LISTEN_CALL_STATE);
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
				"RecorderService");
		mRemainingTimeCalculator = new RemainingTimeCalculator();
	}

	private void checkFileExit(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			file.delete();
		}
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		// Cancel the persistent notification.
		mNM.cancel(NOTIFICATION);
		localStopRecording();
		mTeleManager.listen(null, PhoneStateListener.LISTEN_NONE);
		if (mWakeLock.isHeld()) {
			mWakeLock.release();
		}
		mTeleManager = null;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	private Runnable mUpdateRemainingTime = new Runnable() {
		public void run() {
			if (mRecMicToMp3 != null && mRecMicToMp3.isRecording()) {
				updateRemainingTime();
			}
		}
	};

	private void updateRemainingTime() {
		long t = mRemainingTimeCalculator.timeRemaining();
		if (t <= 0) {
			localStopRecording();
			return;
		} else if (t <= 1800
				&& mRemainingTimeCalculator.currentLowerLimit() != RemainingTimeCalculator.FILE_SIZE_LIMIT) {
			// less than half one hour
		}
		if (mRecMicToMp3 != null && mRecMicToMp3.isRecording()) {
			mHandler.postDelayed(mUpdateRemainingTime, 1800000);
		}
	}

	@Override
	public void onLowMemory() {
		localStopRecording();
		super.onLowMemory();
	}

	private final PhoneStateListener mPhoneStateListener = new PhoneStateListener() {
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			// 当处于待机状态中
			if (state != TelephonyManager.CALL_STATE_IDLE) {
				localStopRecording();
			}
		}
	};

	private void localStopRecording() {
		if (mRecMicToMp3 != null && mRecMicToMp3.isRecording()) {
			try {
				mRecMicToMp3.stop();
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
			mRecMicToMp3 = null;
		}
	}

	public static String getFilePath() {
		return mFilePath;
	}

	public static long getStartTime() {
		return mStartTime;
	}

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		switch (msg.what) {
		case -1:
			double decibel=(Double) msg.obj;
			if (mOnRecordMessageListener != null) {
				mOnRecordMessageListener.OnDecibel(decibel);
			}
			break;
		case 0:
			setRecordMessage(1, "录音开始");
			break;
		case 1:
			setRecordMessage(2, "录音停止");
			break;
		case 2:
			setRecordMessage(-1, "录音失败,该终端不支持设置采样率");
			break;
		case 3:
			setRecordMessage(-1, "录音失败,无法生成音频文件");
			break;
		case 6:
			setRecordMessage(-1, "录音失败,无法编码");
			break;
		case 7:
			setRecordMessage(-1, "录音失败,无法生成音频文件");
			break;
		case 8:
			setRecordMessage(-1, "录音失败,无法生成音频文件");
			break;
		default:
			setRecordMessage(-1, "录音失败");
			break;
		}
		stopSelf();
		return false;
	}

	private void setRecordMessage(int code, String message) {
		if (mOnRecordMessageListener != null) {
			mOnRecordMessageListener.Message(code, message);
		}
	}

	public void setOnProgressListener(
			onRecordMessageListener mOnRecordMessageListener, String filePath) {
		this.mOnRecordMessageListener = mOnRecordMessageListener;
		if (filePath == null || filePath.equals("")) {
			stopSelf();
		}
		mStartTime = System.currentTimeMillis();
		mRemainingTimeCalculator.reset();
		mRemainingTimeCalculator.setBitRate();
		mWakeLock.acquire();
		mFilePath = filePath;
		checkFileExit(mFilePath);
		mRecMicToMp3 = new RecMicToMp3(mFilePath, 16000);
		mRecMicToMp3.setHandle(handler);
		mRecMicToMp3.start();
		mHandler.post(mUpdateRemainingTime);
	}

	public interface onRecordMessageListener {
		public void Message(int code, String message);

		public void OnDecibel(double decibel);
	}
}

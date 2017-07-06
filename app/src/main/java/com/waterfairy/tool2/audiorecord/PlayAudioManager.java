package com.waterfairy.tool2.audiorecord;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;


import java.io.File;

/**
 * 前台音频播放
 * 
 * @author qingyun
 * 
 */
public class PlayAudioManager implements OnCompletionListener,
		OnPreparedListener, OnErrorListener, OnSeekCompleteListener {
	private MediaPlayer mediaPlayer;
	/** 当前正在播放的文件路径 */
	private String audioPath = "";
	/** 播放状态 */
	private int mState = PlayMediaRecordState.Stopped;
	private AudioPlayListener audioPlayListener;
	/** 获取流媒体的总播放时长，单位是毫秒 */
	private int duration = 0;
	/** 获取当前流媒体的播放的位置，单位是毫秒。 */
	private int currentduration = 0;
	/** 电话打来时，暂停播放 */
	private TelephonyManager mTeleManager;

	public PlayAudioManager(Context context) {
		mTeleManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		mTeleManager.listen(mPhoneStateListener,
				PhoneStateListener.LISTEN_CALL_STATE);
	}

	/** 电话状态监听 */
	private final PhoneStateListener mPhoneStateListener = new PhoneStateListener() {
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			if (state != TelephonyManager.CALL_STATE_IDLE) {
				stop();
			}
		}
	};

	/** 获取播放状态 */
	public int getState() {
		return mState;
	}

	/** 设置播放的监听接口 */
	public void setAudioPlayListener(AudioPlayListener audioPlayListener) {
		this.audioPlayListener = audioPlayListener;
	}

	/**
	 * 播放音乐，重新播放
	 */
	public void initPlay() {
//		if (audioPath.startsWith(SDFileManager.rootPath)) {
			File file = new File(audioPath);
			if (file.exists() && file.length() > 0) {
			} else {
				setOnErrorListener("音频文件不存在");
				return;
			}
//		}
		try {
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setOnPreparedListener(this);
			mediaPlayer.setOnCompletionListener(this);
			mediaPlayer.setOnSeekCompleteListener(this);
			mediaPlayer.setOnErrorListener(this);
			// 设置指定的流媒体地址
			mediaPlayer.setDataSource(audioPath);
			// 通过异步的方式装载媒体资源
			mediaPlayer.prepareAsync();
			if (audioPlayListener != null) {
				audioPlayListener.onStartPrepared(audioPath);
			}
		} catch (Exception e) {
			e.printStackTrace();
			setOnErrorListener("音频文件加载失败");
			stop();
		}
	}

	private void setOnErrorListener(String msg) {
		if (audioPlayListener != null) {
			audioPlayListener.onError(audioPath, msg);
		}
	}

	/**
	 * 暂停
	 */
	public void pause() {
		if (mediaPlayer != null && mediaPlayer.isPlaying()) {
			mediaPlayer.pause();
			mState = PlayMediaRecordState.Paused;
			if (audioPlayListener != null) {
				audioPlayListener.onMediaPause(audioPath);
			}
		}
	}

	/**
	 * 播放
	 */
	public void play() {
		if (mState == PlayMediaRecordState.Paused) {
			mediaPlayer.start();
			mState = PlayMediaRecordState.Playing;
			if (audioPlayListener != null) {
				audioPlayListener.onMediaPlay(audioPath);
			}
		}
	}

	/**
	 * 停止播放，释放播放器
	 */
	public void stop() {
		if (mediaPlayer != null) {
			try {
				mediaPlayer.stop();
				mediaPlayer.reset();
				mediaPlayer.release();
				mediaPlayer = null;
				mState = PlayMediaRecordState.Stopped;
				if (audioPlayListener != null) {
					audioPlayListener.onMediaPause(audioPath);
				}
			} catch (IllegalStateException e) {
				// this may happen sometimes
			}
		}
	}

	/***
	 * 获取当前播放进度
	 * 
	 * @return 当前播放进度
	 */
	public int getCurrentduration() {
		if (mediaPlayer != null) {
			return mediaPlayer.getCurrentPosition();
		}
		return 0;
	}

	/***
	 * 获取播放文件中时长
	 * 
	 * @return 当前播放进度
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * 当播放中发生错误的时候回调
	 */
	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		// TODO Auto-generated method stub
		switch (what) {
		case MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
			setOnErrorListener("音频文件格式错误");
			break;
		case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
			setOnErrorListener("媒体服务停止工作");
			break;
		case MediaPlayer.MEDIA_ERROR_UNKNOWN:
			setOnErrorListener("媒体播放器错误");
			break;
		case MediaPlayer.MEDIA_INFO_NOT_SEEKABLE:
			setOnErrorListener("音频文件不支持拖动播放");
			break;
		}
		stop();
		return true; // true表示我们处理了发生的错误

	}

	/**
	 * 当装载流媒体完毕的时候回调
	 */
	@Override
	public void onPrepared(MediaPlayer mp) {
		// TODO Auto-generated method stub
		// 装载完毕 开始播放流媒体
		try {
			duration = mediaPlayer.getDuration();// 获取流媒体的总播放时长，单位是毫秒
			currentduration = mediaPlayer.getCurrentPosition();// 获取当前流媒体的播放的位置，单位是毫秒。
			if (audioPlayListener != null) {
				audioPlayListener.onPrepared(audioPath, duration,
						currentduration);
			}
			if (!mediaPlayer.isPlaying()) {
				mediaPlayer.start();
				mState = PlayMediaRecordState.Playing;
			}
		} catch (Exception e) {
			e.printStackTrace();
			stop();
			if (audioPlayListener != null) {
				audioPlayListener.onError(audioPath, "加载音频文件出错失败");
			}
		}

	}

	/**
	 * 当流媒体播放完毕的时候回调
	 */
	@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub
		mState = PlayMediaRecordState.Stopped;
		if (audioPlayListener != null) {
			audioPlayListener.onCompletion(audioPath);
		}
	}

	public static class PlayMediaRecordState {
		/** MediaPlayer已经停止工作 */
		public static final int Stopped = 0;
		/** MediaPlayer正在准备中 */
		public static final int Preparing = 1;
		/** 正在播放 */
		public static final int Playing = 2;
		/** 播放暂停 */
		public static final int Paused = 3;
	}

	/**
	 * 当使用seekTo()设置播放位置的时候回调
	 */
	@Override
	public void onSeekComplete(MediaPlayer mp) {
		// TODO Auto-generated method stub
		mState = PlayMediaRecordState.Playing;
		mediaPlayer.start();
		if (audioPlayListener != null) {
			audioPlayListener.onSeekComplete(audioPath);
		}
	};

	public interface AudioPlayListener {
		public void onCompletion(String audioPath);

		public void onStartPrepared(String audioPath);

		public void onPrepared(String audioPath, int duration,
                               int currentduration);

		public void onSeekComplete(String audioPath);

		public void onError(String audioPath, String msg);

		public void onMediaPause(String audioPath);

		public void onMediaPlay(String audioPath);
	}

	/**
	 * 跳转到新的位置
	 * 
	 * @param newposition
	 */
	public void seekTo(int newposition) {
		// TODO Auto-generated method stub
		try {
			if (mediaPlayer != null && mediaPlayer.isPlaying()) {
				mediaPlayer.seekTo(newposition);
				mediaPlayer.start();
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getRecordPath() {
		return audioPath;
	}

	public void setAudioPath(String audioPath) {
		this.audioPath = audioPath;
	}

}

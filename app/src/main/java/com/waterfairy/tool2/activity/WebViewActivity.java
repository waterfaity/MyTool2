package com.waterfairy.tool2.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.ClientCertRequest;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.waterfairy.retrofit2.base.IBaseControl;
import com.waterfairy.retrofit2.base.OnProgressListener;
import com.waterfairy.retrofit2.download.DownloadControl;
import com.waterfairy.retrofit2.download.DownloadInfo;
import com.waterfairy.retrofit2.download.DownloadManager;
import com.waterfairy.tool2.MyApp;
import com.waterfairy.tool2.R;
import com.waterfairy.utils.HtmlUtils;
import com.waterfairy.utils.TxtUtils;

import java.io.File;

import im.delight.android.webview.AdvancedWebView;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        AdvancedWebView advancedWebView = (AdvancedWebView) findViewById(R.id.web_view);
//        advancedWebView.loadHtml();

//        new Thread() {
//            @Override
//            public void run() {
//                super.run();
//                try {
//                    String html = HtmlUtils.getHtml("https://m.xueduoduo.com/huiyun/home/aboutMe?systemVersion=24&appType=android&token=61a5c6f7fa3fc623&clientPackage=com.xueduoduo.wisdom.read&operatorId=246215&version=1.0&clientVersion=2.0.13");
//                    Log.i("test", "onCreate: " + html);
//                    TxtUtils.writeTxt(new File("/sdcard/jjj.html"), html, true, false);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();


//        DownloadManager downloadManager = DownloadManager.getInstance();
//        DownloadInfo downloadInfo = new DownloadInfoBean();
//        downloadInfo.setBasePath("https://m.xueduoduo.com");
//        downloadInfo.setSavePath("/sdcard/jjj.html");
//        downloadInfo.setUrl("https://m.xueduoduo.com/huiyun/home/aboutMe?systemVersion=24&appType=android&token=61a5c6f7fa3fc623&clientPackage=com.xueduoduo.wisdom.read&operatorId=246215&version=1.0&clientVersion=2.0.13");
//        DownloadControl add = (DownloadControl) downloadManager.add(downloadInfo);
//        add.setLoadListener(new OnProgressListener() {
//            @Override
//            public void onLoading(boolean done, long total, long current) {
//
//            }
//
//
//            @Override
//            public void onError(int code) {
//
//            }
//
//            @Override
//            public void onChange(int code) {
//
//            }
//        });
//        add.start();


//        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.content_view);
//        WebView webView1 = new WebView(MyApp.getApp().getApplicationContext());
//        linearLayout.addView(webView1);
//        webView1.loadUrl("/sdcard/jjj.html");
//        webView1.setWebViewClient(new MyWebViewClient());
////        webView1.setWebViewClient(new WebViewClient() {
////            @Override
////            public boolean shouldOverrideUrlLoading(WebView view, String url) {
////                // TODO Auto-generated method stub
////                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
////                view.loadUrl(url);
////                return true;
////            }
////            @Override
////            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
////                if (error.getPrimaryError() == SslError.SSL_DATE_INVALID
////                        || error.getPrimaryError() == SslError.SSL_EXPIRED
////                        || error.getPrimaryError() == SslError.SSL_INVALID
////                        || error.getPrimaryError() == SslError.SSL_UNTRUSTED) {
////                    handler.proceed();
////                } else {
////                    handler.cancel();
////                }
////
////                super.onReceivedSslError(view, handler, error);
////            }
////        });
//////        webView1.loadUrl("http://www.bilibili.com");
//
//        webView1.loadUrl("https://m.xueduoduo.com/huiyun/home/aboutMe?systemVersion=24&appType=android&token=61a5c6f7fa3fc623&clientPackage=com.xueduoduo.wisdom.read&operatorId=246215&version=1.0&clientVersion=2.0.13");

    }

    public class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
//            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }

        @Override
        public void onPageCommitVisible(WebView view, String url) {
            super.onPageCommitVisible(view, url);
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            return super.shouldInterceptRequest(view, url);
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            return super.shouldInterceptRequest(view, request);
        }

        @Override
        public void onTooManyRedirects(WebView view, Message cancelMsg, Message continueMsg) {
            super.onTooManyRedirects(view, cancelMsg, continueMsg);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
        }

        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            super.onReceivedHttpError(view, request, errorResponse);
        }

        @Override
        public void onFormResubmission(WebView view, Message dontResend, Message resend) {
            super.onFormResubmission(view, dontResend, resend);
        }

        @Override
        public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
            super.doUpdateVisitedHistory(view, url, isReload);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
        }

        @Override
        public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
            super.onReceivedClientCertRequest(view, request);
        }

        @Override
        public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
            super.onReceivedHttpAuthRequest(view, handler, host, realm);
        }

        @Override
        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
            return super.shouldOverrideKeyEvent(view, event);
        }

        @Override
        public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
            super.onUnhandledKeyEvent(view, event);
        }

        @Override
        public void onScaleChanged(WebView view, float oldScale, float newScale) {
            super.onScaleChanged(view, oldScale, newScale);
        }

        @Override
        public void onReceivedLoginRequest(WebView view, String realm, String account, String args) {
            super.onReceivedLoginRequest(view, realm, account, args);
        }
    }

}

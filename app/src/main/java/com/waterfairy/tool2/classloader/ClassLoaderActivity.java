package com.waterfairy.tool2.classloader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.waterfairy.tool2.R;

import java.lang.reflect.Method;

import dalvik.system.BaseDexClassLoader;
import dalvik.system.DexClassLoader;

public class ClassLoaderActivity extends AppCompatActivity {

    private static final String TAG = "classLoader";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_loader);
        ClassLoader classLoader = getClassLoader();
        DexClassLoader dexClassLoader = new DexClassLoader(";jfds/jar.jar", "fdsa", null, getClassLoader());


        Class libProviderClazz = null;
        try {
            libProviderClazz = dexClassLoader.loadClass("me.kaede.dexclassloader.MyLoader");
            // 遍历类里所有方法
            Method[] methods = libProviderClazz.getDeclaredMethods();
            for (int i = 0; i < methods.length; i++) {
                Log.e(TAG, methods[i].toString());
                methods[i].getName();
            }
            Method start = libProviderClazz.getDeclaredMethod("func");// 获取方法
            start.setAccessible(true);// 把方法设为public，让外部可以调用
            String string = (String) start.invoke(libProviderClazz.newInstance());// 调用方法并获取返回值
            Toast.makeText(this, string, Toast.LENGTH_LONG).show();
        } catch (Exception exception) {
            // Handle exception gracefully here.
            exception.printStackTrace();
        }
    }
}

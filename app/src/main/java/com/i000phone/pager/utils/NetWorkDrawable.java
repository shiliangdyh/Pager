package com.i000phone.pager.utils;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.os.AsyncTaskCompat;
import android.support.v4.util.LruCache;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2016/3/24.
 */
public class NetWorkDrawable extends BitmapDrawable {

    private final Bitmap loading;
    private final Bitmap failure;
    private ImageTask task;
    private ExecutorService es = Executors.newFixedThreadPool(10);
    private LruCache<String,Bitmap> cache  = new LruCache<String,Bitmap>(30<<20){
        @Override
        protected int sizeOf(String key, Bitmap value) {
            return value.getRowBytes()*value.getHeight();
        }
    };

    public NetWorkDrawable(Resources res, Bitmap loading, Bitmap failure) {
        super(res, loading);
        this.loading = loading;
        this.failure = failure;
    }
    public void cancel(){
        if (task!=null) {
            task.cancel(true);
            task = null;
        }
    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void setImageUrl(String url){
        cancel();
        Bitmap bitmap = cache.get(url);
        if (bitmap!=null) {
            setSelfBitmap(bitmap);
        }else{
            setSelfBitmap(loading);
            task = new ImageTask();
            task.executeOnExecutor(es,url);
        }
    }

    public void setSelfBitmap(Bitmap bitmap){
        Class<BitmapDrawable> aClass = BitmapDrawable.class;
        try {
            Method setBitmap = aClass.getDeclaredMethod("setBitmap", Bitmap.class);
            setBitmap.setAccessible(true);
            setBitmap.invoke(this,bitmap);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    class ImageTask extends AsyncTask<String,Void,Bitmap>{
        private String url;
        @Override
        protected Bitmap doInBackground(String... params) {
            url = params[0];
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.setConnectTimeout(3000);
                connection.connect();
                if (connection.getResponseCode()==200) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = false;
                    options.inPreferredConfig = Bitmap.Config.RGB_565;
                    return BitmapFactory.decodeStream(connection.getInputStream(),null,options);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap==null) {
                setSelfBitmap(failure);
            }else{
                setSelfBitmap(bitmap);
                cache.put(url,bitmap);
            }
        }

        @Override
        protected void onCancelled(Bitmap bitmap) {
            if (bitmap!=null) {
                cache.put(url,bitmap);
            }
        }
    }

}

package com.i000phone.pager.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.i000phone.pager.entities.AllCategory;
import com.i000phone.pager.entities.ErrorResponse;
import com.i000phone.pager.entities.Response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/3/24.
 */
public class HttpTask <T extends Response> extends AsyncTask<String,Void,Response> {
    private Class<T> aClass;
    private Callback<T> callback;

    public HttpTask(Class<T> aClass, Callback<T> callback) {
        this.aClass = aClass;
        this.callback = callback;
    }

    @Override
    protected Response doInBackground(String... params) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(params[0]).openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setConnectTimeout(3000);
            connection.connect();
            if (connection.getResponseCode()==200) {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                InputStream is = connection.getInputStream();
                byte[] b = new byte[100<<10];
                int len;
                while((len = is.read(b))!=-1){
                    bos.write(b,0,len);
                }
                Constructor<T> constructor = aClass.getConstructor(String.class);
                return constructor.newInstance(bos.toString());
            }else{
                return new ErrorResponse("ResponseCode:"+connection.getResponseCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        } catch (InstantiationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(Response response) {
        if (response instanceof ErrorResponse) {
            callback.doFailure(((ErrorResponse) response).getError());
        }else{
            callback.doResponse(((T) response));
        }
    }

    public interface Callback<T extends Response>{
        void doResponse(T t);
        void doFailure(String error);
    }
}

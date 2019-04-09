package cn.jx.jvtc.mytrafficclient.http;

import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cn.jx.jvtc.mytrafficclient.bean.UrlBean;
import cn.jx.jvtc.mytrafficclient.utils.Util;

public class VolleyUtils {
    private static final String TAG = "VolleyUtils";
    static  RequestQueue queue;
    public static  void  getVolleyData(String url,JSONObject jsonObject, Context context, final VolleyCallBack volleyCallBack){
        UrlBean urlBean= Util.loadSetting(context);
        url="http://"+urlBean.getUrl()+":"+urlBean.getPort()+url;
        Log.d(TAG, "getVolleyData: "+url);
        if(queue==null) {
             queue = Volley.newRequestQueue(context);
        }
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST,url,jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    volleyCallBack.onSuccess(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyCallBack.onError(volleyError);
            }
        });
      queue.add(jsonObjectRequest);
    }
    public interface  VolleyCallBack{
        public void onSuccess(JSONObject jsonObject) throws JSONException;
        public  void onError(VolleyError volleyError);
    }
}

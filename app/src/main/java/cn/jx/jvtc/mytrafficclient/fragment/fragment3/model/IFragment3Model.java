package cn.jx.jvtc.mytrafficclient.fragment.fragment3.model;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import cn.jx.jvtc.mytrafficclient.adapter.ListViewAccountAdapter;
import cn.jx.jvtc.mytrafficclient.fragment.fragment3.presenter.CallBack;

public interface IFragment3Model {
    public void getCars(String url, JSONObject jsonObject, Context context, CallBack callBack);
    public void getCarPeccancy(String url, JSONObject jsonObject, Context context,CallBack callBack);
    public void getPerson(String url, JSONObject jsonObject, Context context,CallBack callBack);
    public void getPeccancy(String url, JSONObject jsonObject, Context context,CallBack callBack);
}

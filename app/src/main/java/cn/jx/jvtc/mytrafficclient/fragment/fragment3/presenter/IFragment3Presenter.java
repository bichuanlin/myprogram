package cn.jx.jvtc.mytrafficclient.fragment.fragment3.presenter;

import android.content.Context;

import org.json.JSONObject;

public interface IFragment3Presenter {
    public void getCars(String url, JSONObject jsonObject, Context context);
    public void getCarPeccancy(String url, JSONObject jsonObject, Context context);
    public void getPerson(String url, JSONObject jsonObject, Context context);
    public void getPeccancy(String url, JSONObject jsonObject, Context context);
}

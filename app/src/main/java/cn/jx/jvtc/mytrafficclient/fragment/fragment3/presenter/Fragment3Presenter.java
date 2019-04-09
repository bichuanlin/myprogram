package cn.jx.jvtc.mytrafficclient.fragment.fragment3.presenter;

import android.content.Context;

import org.json.JSONObject;

import cn.jx.jvtc.mytrafficclient.fragment.fragment3.model.Fragment3Model;
import cn.jx.jvtc.mytrafficclient.fragment.fragment3.model.IFragment3Model;
import cn.jx.jvtc.mytrafficclient.fragment.fragment3.view.IFragment3View;

public class Fragment3Presenter implements  IFragment3Presenter {
    IFragment3Model mFragment3Model;
    IFragment3View mFragment3View;

    public Fragment3Presenter(IFragment3View mFragment3View) {
        this.mFragment3View = mFragment3View;
        mFragment3Model=new Fragment3Model();
    }

    @Override
    public void getCars(String url, JSONObject jsonObject, Context context) {
        mFragment3Model.getCars(url, jsonObject, context, new CallBack() {
            @Override
            public void onSuccess(Object response) {
                mFragment3View.response(response,IFragment3View.RESPONSE_CAR);

            }

            @Override
            public void onError(String t) {

            }
        });
    }

    @Override
    public void getCarPeccancy(String url, JSONObject jsonObject, Context context) {
       mFragment3Model.getCarPeccancy(url, jsonObject, context, new CallBack() {
           @Override
           public void onSuccess(Object response) {
               mFragment3View.response(response,IFragment3View.RESPONSE_CARPECCANCY);
           }

           @Override
           public void onError(String t) {

           }
       });
    }

    @Override
    public void getPerson(String url, JSONObject jsonObject, Context context) {
       mFragment3Model.getPerson(url, jsonObject, context, new CallBack() {
           @Override
           public void onSuccess(Object response) {
               mFragment3View.response(response,IFragment3View.RESPONSE_PERSON);
           }

           @Override
           public void onError(String t) {

           }
       });
    }

    @Override
    public void getPeccancy(String url, JSONObject jsonObject, Context context) {
        mFragment3Model.getPeccancy(url, jsonObject, context, new CallBack() {
                    @Override
                    public void onSuccess(Object response) {
                        mFragment3View.response(response,IFragment3View.RESPONSE_PECCANCY);
                    }

                    @Override
                    public void onError(String t) {

                    }
                }
        );
    }
}

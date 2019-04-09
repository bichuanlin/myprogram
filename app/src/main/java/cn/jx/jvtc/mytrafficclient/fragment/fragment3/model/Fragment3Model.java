package cn.jx.jvtc.mytrafficclient.fragment.fragment3.model;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.jx.jvtc.mytrafficclient.bean.UrlBean;
import cn.jx.jvtc.mytrafficclient.fragment.fragment3.presenter.CallBack;
import cn.jx.jvtc.mytrafficclient.http.VolleyUtils;
import cn.jx.jvtc.mytrafficclient.utils.Util;

public class Fragment3Model implements  IFragment3Model{
    private static final String TAG = "Fragment3Model";
    List<Car> carList;
    List<CarPeccancy> carPeccancyList;
    List<Person> personList;
    List<Peccancy> peccancyList;
    @Override
    public void getCars(String url, JSONObject jsonObject, Context context, final CallBack callBack) {
        VolleyUtils.getVolleyData(url, jsonObject, context, new VolleyUtils.VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject jsonObject) throws JSONException {
                jsonToCarList(jsonObject);
                callBack.onSuccess(carList);
            }
            @Override
            public void onError(VolleyError volleyError) {

            }
        });
    }

    @Override
    public void getCarPeccancy(String url, JSONObject jsonObject, Context context, final CallBack callBack) {
        VolleyUtils.getVolleyData(url, jsonObject, context, new VolleyUtils.VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject jsonObject) throws JSONException {
                jsonToCarPeccancy(jsonObject);
                callBack.onSuccess(carPeccancyList);
            }

            @Override
            public void onError(VolleyError volleyError) {

            }
        });
    }

    @Override
    public void getPerson(String url, JSONObject jsonObject, Context context, final CallBack callBack) {
        VolleyUtils.getVolleyData(url, jsonObject, context, new VolleyUtils.VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject jsonObject) throws JSONException {
               jsonToPersonList(jsonObject);
               callBack.onSuccess(personList);
            }

            @Override
            public void onError(VolleyError volleyError) {

            }
        });
    }

    @Override
    public void getPeccancy(String url, JSONObject jsonObject, Context context, final CallBack callBack) {
        VolleyUtils.getVolleyData(url, jsonObject, context, new VolleyUtils.VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject jsonObject) throws JSONException {
                jsonToPeccancyList(jsonObject);
                callBack.onSuccess(peccancyList);
            }

            @Override
            public void onError(VolleyError volleyError) {

            }
        });
    }
    /**
     * json转换peccancyList
     */
    public  void jsonToPeccancyList(JSONObject jsonObject) throws JSONException {
        peccancyList=new ArrayList<>();
        if (jsonObject.optString("RESULT").equals("S")) {
            JSONArray array = null;
            array = jsonObject.getJSONArray("ROWS_DETAIL");
            Peccancy peccancy;
            JSONObject object;
            for (int i = 0; i < array.length(); i++) {
                peccancy = new Peccancy();
                object = (JSONObject) array.get(i);
                peccancy.setPcode(object.getString("pcode"));
                peccancy.setPmoney(object.getInt("pmoney"));
                peccancy.setPscore(object.getInt("pscore"));
                peccancy.setPremarks(object.getString("premarks"));
                peccancyList.add(peccancy);
            }
        }
    }

    /**
     * json转换PersonList
     */
    public  void jsonToPersonList(JSONObject jsonObject) throws JSONException {
        personList=new ArrayList<>();
        if (jsonObject.optString("RESULT").equals("S")) {
            JSONArray array = null;
            array = jsonObject.getJSONArray("ROWS_DETAIL");
            Person person;
            JSONObject object;
            for (int i = 0; i < array.length(); i++) {
                person = new Person();
                object = (JSONObject) array.get(i);
                person.setPname(object.getString("pname"));
                person.setPcardid(object.getString("pcardid"));
                person.setPsex(object.getString("psex"));
                person.setUsername(object.getString("username"));
                person.setPtel(object.getString("ptel"));
                person.setPregistdate(object.getString("pregisterdate"));
                personList.add(person);
            }
        }
    }
    /**
     * json转换CarList
     */
    public  void jsonToCarList(JSONObject jsonObject) throws JSONException {
        carList=new ArrayList<>();
        if (jsonObject.optString("RESULT").equals("S")) {
            JSONArray array = null;
            try {
                array = jsonObject.getJSONArray("ROWS_DETAIL");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Car car;
            JSONObject object;
            for (int i = 0; i < array.length(); i++) {
                car = new Car();
                object = (JSONObject) array.get(i);
                car.setCarNumber(object.getString("carnumber"));
                car.setNumber(object.getString("number"));
                car.setPcardid(object.getString("pcardid"));
                car.setBuydata(object.getString("buydate"));
                car.setCarbrand(object.getString("carbrand"));
                carList.add(car);
            }
        }
    }
    /**
     * jons to CarPeccancy
     */
   public  void jsonToCarPeccancy(JSONObject jsonObject) throws JSONException {
       carPeccancyList=new ArrayList<>();
       if(jsonObject.optString("RESULT").equals("S")){
           JSONArray array=jsonObject.getJSONArray("ROWS_DETAIL");
           JSONObject object;
           CarPeccancy peccancy;
           for (int i = 0; i <array.length() ; i++) {
               peccancy=new CarPeccancy();
               object= (JSONObject) array.get(i);
               peccancy.setCarnumber(object.getString("carnumber"));
               peccancy.setPcode(object.getString("pcode"));
               peccancy.setPaddr(object.getString("paddr"));
              // Log.d(TAG, "jsonToCarPeccancy: "+object.getString("pdatetime"));
               peccancy.setDatetime(object.getString("pdatetime"));
               carPeccancyList.add(peccancy);
           }

       }
   }

}

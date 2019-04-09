package cn.jx.jvtc.mytrafficclient.fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.jx.jvtc.mytrafficclient.R;
import cn.jx.jvtc.mytrafficclient.adapter.ExpandListViewBusStationAdapter;
import cn.jx.jvtc.mytrafficclient.adapter.ListViewBusCapacityAdapter;
import cn.jx.jvtc.mytrafficclient.bean.Bus;
import cn.jx.jvtc.mytrafficclient.bean.BusStation;
import cn.jx.jvtc.mytrafficclient.http.VolleyUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment2 extends Fragment {
    private static final String TAG = "Fragment2";
    List<BusStation> busStationList;//公交站台信息
    BusStation busStation;
    String[] bus_name=new String[]{"中医院站","联想大厦站"};
    List<Bus> busList;
    Bus bus;
    ExpandListViewBusStationAdapter adapter;
    ListViewBusCapacityAdapter listViewBusCapacityAdapter;
    ExpandableListView listView;
    Button btn_fragment2_detail;
    TextView tv_fragment2_bus_capacity,tv_total;//显示公交承载能力
    ListView listView_dialog;
    int count=0,num=0;
    boolean flag=false;//3秒循环访问网络变量
    MyThread thread;
    boolean isopen=false;//判断公交车详细信息对话框是否已经打开
    Handler myHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1){//读出车站汽车信息
                getBusCapaity();//读汽车容量
            }else if(msg.what==2) {//数据读取成功
                //显示公交承载能力
                tv_fragment2_bus_capacity.setText("当前承载能力："+sumCapacity());
                adapter.notifyDataSetChanged();
                if(isopen){
                    listViewBusCapacityAdapter=new ListViewBusCapacityAdapter(getContext(),busStationList.get(0).getBusList());
                    listView_dialog.setAdapter(listViewBusCapacityAdapter);
                    tv_total.setText(sumCapacity()+"");
                }
            }
        }
    };
    public Fragment2() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment2, container, false);
        busStationList=new ArrayList<>();
        listView=v.findViewById(R.id.expListView_fragment2);
        tv_fragment2_bus_capacity=v.findViewById(R.id.tv_fragment2_bus_capacity);
        btn_fragment2_detail=v.findViewById(R.id.btn_fragment2_detail);
        adapter=new ExpandListViewBusStationAdapter(getContext(),busStationList);
        listView.setAdapter(adapter);
        flag=true;
        thread=new MyThread();
        thread.start();
        btn_fragment2_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                View v=LayoutInflater.from(getContext()).inflate(R.layout.fragment2_bus_capacity_dialog,null);
                listView_dialog=v.findViewById(R.id.listView_fragment2_dialog);
                tv_total=v.findViewById(R.id.tv_fragment2_dialog_bus_capacity);
                tv_total.setText(sumCapacity()+"");
                Button btn_cancel=v.findViewById(R.id.btn_fragment2_dialog_return);
                listViewBusCapacityAdapter=new ListViewBusCapacityAdapter(getContext(),busStationList.get(0).getBusList());
                listView_dialog.setAdapter(listViewBusCapacityAdapter);
                builder.setView(v);
                final Dialog dialog=builder.create();
                isopen=true;
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        isopen=false;
                       dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        return  v;
    }
    @Override
    public void onPause() {
        super.onPause();
        flag=false;
    }
    class MyThread extends  Thread{
        @Override
        public void run() {
            super.run();
            while (flag) {
                getBusInfor();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 网络读取公交站台各公交信息
     */
   void getBusInfor(){
       String url="/api/v2/get_bus_station_info";
       busStationList.clear();
        for (int i = 1; i <=2 ; i++) {
           count=0;
           JSONObject jsonObject = new JSONObject();
           try {
               jsonObject.put("BusStationId", i);
               jsonObject.put("UserName", "User1");
           } catch (JSONException e) {
               e.printStackTrace();
           }
           VolleyUtils.getVolleyData(url, jsonObject, getContext(), new VolleyUtils.VolleyCallBack() {
               @Override
               public void onSuccess(JSONObject jsonObject) throws JSONException {
                  if(jsonObject.optString("RESULT").equals("S")){
                      count++;
                      busStation=new BusStation();
                      busStation.setId(count);
                      busStation.setName(bus_name[count-1]);
                      JSONArray array=jsonObject.getJSONArray("ROWS_DETAIL");
                      busList=new ArrayList<>();
                      for(int j=0;j<array.length();j++){
                          bus=new Bus();
                          JSONObject  json=(JSONObject)array.get(j);
                          bus.setId(json.getInt("BusId"));
                          bus.setDistance(json.getInt("Distance"));
                          //getBusCapaity(bus);
                          busList.add(bus);
                      }
                     busStation.setBusList(busList);
                     busStationList.add(busStation);
                      if(count==2) {
                          myHandler.sendEmptyMessage(1);
                      }
                  }
               }
               @Override
               public void onError(VolleyError volleyError) {

               }
           });
       }
   }
    void  getBusCapaity(){
        String url="/api/v2/get_bus_capacity";
        num=0;
        for (int i = 0; i <busStationList.get(0).getBusList().size() ; i++) {
                bus=busStationList.get(0).getBusList().get(i);
                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("BusId",bus.getId());
                    jsonObject.put("UserName","user1");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            VolleyUtils.getVolleyData(url, jsonObject,getContext(), new VolleyUtils.VolleyCallBack() {
                @Override
                public void onSuccess(JSONObject jsonObject) throws JSONException {
                    if(jsonObject.optString("RESULT").equals("S")){
                        int capacity=jsonObject.optInt("BusCapacity");
                        busStationList.get(0).getBusList().get(num).setCapacity(capacity);
                        busStationList.get(1).getBusList().get(num).setCapacity(capacity);
                        if(num==busStationList.get(0).getBusList().size()-1) {
                            myHandler.sendEmptyMessage(2);
                        }
                        num++;
                    }
                }
                @Override
                public void onError(VolleyError volleyError) {

                }
            });
            }
        }
        //统计901路公交车的承载能力
      int sumCapacity(){
       int total=0;
          for (int i = 0; i <busStationList.get(0).getBusList().size() ; i++) {
              total=total+busStationList.get(0).getBusList().get(i).getCapacity();
          }
          return  total;
      }

}
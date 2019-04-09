package cn.jx.jvtc.mytrafficclient.fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Date;
import java.util.List;

import cn.jx.jvtc.mytrafficclient.R;
import cn.jx.jvtc.mytrafficclient.activity.MainActivity;
import cn.jx.jvtc.mytrafficclient.adapter.ListViewAccountAdapter;
import cn.jx.jvtc.mytrafficclient.bean.Account;
import cn.jx.jvtc.mytrafficclient.bean.RecordBean;
import cn.jx.jvtc.mytrafficclient.bean.UrlBean;
import cn.jx.jvtc.mytrafficclient.dao.AccountDAO;
import cn.jx.jvtc.mytrafficclient.dao.AccountOpenHelper;
import cn.jx.jvtc.mytrafficclient.http.VolleyUtils;
import cn.jx.jvtc.mytrafficclient.utils.Util;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment1 extends Fragment implements ListViewAccountAdapter.Callback,MainActivity.RechargeCallBack {
    private static final String TAG = "Fragment1";
   ListViewAccountAdapter accountAdapter;
   List<Account> accountList;
   ListView listView;
   Account account;
   Context context;
   int count=0;
   Handler myHander=new Handler(){
       @Override
       public void handleMessage(Message msg) {
           super.handleMessage(msg);
           if(msg.what==1){
              // Log.d(TAG, "handleMessage: "+"aaaaaaaaaaaaaaaaaaaaaaa");
               accountAdapter.notifyDataSetChanged();
           }
       }
   };
    public Fragment1() {
        // Required empty public constructor
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment1, container, false);
        listView=view.findViewById(R.id.listview_account);
        getAccount();
        accountAdapter=new ListViewAccountAdapter(context,accountList,Fragment1.this);
        listView.setAdapter(accountAdapter);
        return view;
    }
    /**
     * 读取小车信息
     */
    void getAccount(){
        accountList=new ArrayList<>();
        String strConn= "/api/v2/get_car_info";
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","admin");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        VolleyUtils.getVolleyData(strConn, jsonObject, context, new VolleyUtils.VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject jsonObject) throws JSONException {
                if(jsonObject.optString("RESULT").equals("S")){
                    try {
                        JSONArray accounts = jsonObject.getJSONArray("ROWS_DETAIL");
                        for (int i = 0; i <accounts.length() ; i++) {
                            String number=((JSONObject)accounts.get(i)).getString("number");
                            if(number.equals("1")||number.equals("2")||number.equals("3")||number.equals("4")) {
                                account = new Account();
                                account.setCarNumber(((JSONObject) accounts.get(i)).getString("carnumber"));
                                account.setNumber(number);
                                account.setPcardid(((JSONObject) accounts.get(i)).getString("pcardid"));
                                String carbrand = ((JSONObject) accounts.get(i)).getString("carbrand");
                                Log.d(TAG, "onSuccess: "+carbrand);
                                int icon = getResources().getIdentifier(carbrand, "drawable", context.getPackageName());
                                account.setCarIcon(icon);
                                accountList.add(account);
                            }
                        }
                        getCarName();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onError(VolleyError volleyError) {

            }
        });
    }
    /**
     * 获取车主姓名
     */
    void getCarName(){
        String url= "/api/v2/get_all_user_info";
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        VolleyUtils.getVolleyData(url, jsonObject, context, new VolleyUtils.VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject jsonObject) throws JSONException {
                if(jsonObject.optString("RESULT").equals("S")){
                    JSONArray jsonArray=jsonObject.getJSONArray("ROWS_DETAIL");
                    String username,pIdcard;
                    for (int i = 0; i <jsonArray.length() ; i++) {
                        for(int j=0;j<accountList.size();j++){
                            pIdcard=((JSONObject)jsonArray.get(i)).getString("pcardid");
                            username=((JSONObject)jsonArray.get(i)).getString("pname");
                            if(pIdcard.equals(accountList.get(j).getPcardid())){
                                accountList.get(j).setName(username);
                                Log.d(TAG, "onSuccess: "+accountList.get(j).getName());
                            }
                        }
                    }
                    //得到余额
                    getCarBalance();
                }

            }

            @Override
            public void onError(VolleyError volleyError) {

            }
        });
    }
    /**
     * 获取小车余额
     */
    void getCarBalance(){
        String url= "/api/v2/get_car_account_balance";
        JSONObject jsonObject;
        for (int i = 0; i <accountList.size() ; i++) {
            final  int index=i;
            String number=accountList.get(i).getNumber();
            jsonObject=new JSONObject();
            try {
                jsonObject.put("CarId",Integer.valueOf(number));
                jsonObject.put("UserName","user1");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            VolleyUtils.getVolleyData(url, jsonObject, context, new VolleyUtils.VolleyCallBack() {
                @Override
                public void onSuccess(JSONObject jsonObject) throws JSONException {
                    if(jsonObject.optString("RESULT").equals("S")) {
                        Log.d(TAG, "onSuccess: " + "sdddddddddddddddddddd"+jsonObject.optString("RESULT"));
                        int balance = jsonObject.optInt("Balance");
                        accountList.get(index).setBalance(balance);
                        count++;
                        if(count==4) {
                            myHander.sendEmptyMessage(1);
                        }
                    }
                }
                @Override
                public void onError(VolleyError volleyError) {

                }
            });
        }
    }
    /**
     * 充值
     */
    void  recharge(final int position, final int money){
        String url="/api/v2/set_car_account_recharge";
        int number= Integer.parseInt(accountList.get(position).getNumber());
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("CarId",number);
            jsonObject.put("Money",money);
            jsonObject.put("UserName","user1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        VolleyUtils.getVolleyData(url, jsonObject, context, new VolleyUtils.VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject jsonObject) throws JSONException {
                if(jsonObject.optString("RESULT").equals("S")){
                    accountList.get(position).setBalance(accountList.get(position).getBalance()+money);
                    myHander.sendEmptyMessage(1);
                    saveRechargeRecord(position,money);
                    Toast.makeText(context, "充值成功！", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onError(VolleyError volleyError) {

            }
        });
    }
    /**
     * 保存充值记录到本地数据库
     */
    void saveRechargeRecord(int position,int money){
        RecordBean recordBean=new RecordBean();
        Account account=accountList.get(position);
        recordBean.setCarNum(account.getCarNumber());
        recordBean.setReMoney(money+"");
        recordBean.setBalance((account.getBalance()+money)+"");
        recordBean.setPerson(account.getName());
        recordBean.setReTime(new Date().toString());
        AccountOpenHelper openHelper=new AccountOpenHelper(context);
        AccountDAO dao=new AccountDAO(openHelper);
        dao.add(recordBean);
    }
    @Override
    public void btnChargeClick(int number) {
        int[] positions=new int[]{number};//测试用
        showRechargeDialog(positions);
    }
    /**
     * 弹出充值对话框,参数positions为账号列表中的索引
     */
    public void showRechargeDialog(final int[] positions){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("车辆账户充值");
        View v=LayoutInflater.from(context).inflate(R.layout.fragment1_recharge_dialog,null);
        builder.setView(v);
        TextView tv_carNumber;
        final EditText et_money;
        Button btn_ok,btn_cancel;
        tv_carNumber=v.findViewById(R.id.tv_fragment_charge_carNumber);
        et_money=v.findViewById(R.id.et_fragment_charge_money);
        btn_ok=v.findViewById(R.id.btn_fragment_charge_ok);
        btn_cancel=v.findViewById(R.id.btn_fragment_charge_cancel);
        String carNumbers="";//车牌号
        for (int i = 0; i <positions.length ; i++) {
            carNumbers=carNumbers+","+accountList.get(positions[i]).getCarNumber();
        }
        tv_carNumber.setText(carNumbers);
        final Dialog dialog=builder.create();
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int money;
                money= Integer.parseInt(et_money.getText().toString());
                if(money<1||money>1000){
                    Toast.makeText(context, "金额只能1-999之间！", Toast.LENGTH_SHORT).show();
                    return;
                }
                for (int i = 0; i <positions.length ; i++) {
                    recharge(positions[i],money);
                }
                dialog.dismiss();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    /**
     * 批量充值
     */
    public  void rechargeAll(){
        int[] positions;
        int count=0;
        for (int i = 0; i <accountList.size() ; i++) {
            if(accountList.get(i).isChecked()){
                count++;
            }
        }
        positions=new int[count];
        count=0;
        for (int i = 0; i <accountList.size() ; i++) {
            if(accountList.get(i).isChecked()){
                positions[count]=i;
                count++;
            }
        }
        if(count==0){
            Toast.makeText(context, "请选择要充值的汽车！", Toast.LENGTH_SHORT).show();
            return;
        }
        showRechargeDialog(positions);
    }
    /**
     * 复选框选项事件
     */
    public void cbCheckedChanged(boolean b,int position){
        //Log.d(TAG, "cbCheckedChanged: "+b+position);
        accountList.get(position).setChecked(b);
    }
}

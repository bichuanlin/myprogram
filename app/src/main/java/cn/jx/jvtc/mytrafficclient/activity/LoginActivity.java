package cn.jx.jvtc.mytrafficclient.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import cn.jx.jvtc.mytrafficclient.R;
import cn.jx.jvtc.mytrafficclient.bean.UrlBean;
import cn.jx.jvtc.mytrafficclient.bean.Users;
import cn.jx.jvtc.mytrafficclient.http.VolleyUtils;
import cn.jx.jvtc.mytrafficclient.utils.LoadingDialog;
import cn.jx.jvtc.mytrafficclient.utils.Util;

import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends Activity {
    private static final String TAG = "LoginActivity";
    EditText et_username,et_password;
    CheckBox checkBox_remember,checkBox_auto_login;
    UrlBean url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        initView();
        url=new UrlBean();
        url.setPort("8080");
        url.setUrl("http://10.0.2.2");
        //判断是否要自动登录
        if(Util.getAutoLogin(this)){
            login(null);
        }
    }
    void initView(){
        et_username=findViewById(R.id.et_username);
        et_password=findViewById(R.id.et_password);
        checkBox_remember=findViewById(R.id.cb_remember_passsword);
        checkBox_auto_login=findViewById(R.id.cb_auto_login);
        //读出上次保存的用户名和密码
        Users u=Util.getUserInfo(this);
        if(u!=null){
            et_password.setText(u.getPassword());
            et_username.setText(u.getUsername());
        }
    }
    /**
     * 用户登录
     */
    public void login(View v)  {
        final String username ,password;
        String sUrl="/api/v2/user_login";
        Log.d(TAG, "login: "+sUrl);
        JSONObject json=new JSONObject();
        if(et_username.getText().toString().equals("")||et_password.getText().toString().equals("")){
            Toast.makeText(this, "用户名和密码不能为空!", Toast.LENGTH_SHORT).show();
            return;
        }
        username=et_username.getText().toString();
        password=et_password.getText().toString();
        try {
            json.put("UserName",username);
            json.put("UserPwd",password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LoadingDialog.showDialog(this,"正在登录...");
        VolleyUtils.getVolleyData(sUrl,json, this, new VolleyUtils.VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject jsonObject) throws JSONException {
                if(jsonObject.optString("RESULT").equals("S")){
                    if(checkBox_remember.isChecked()) {
                        //保存用户名和密码
                        Users users = new Users();
                        users.setUsername(username);
                        users.setPassword(password);
                        Util.saveUserInfo(LoginActivity.this, users);
                    }
                    //自动登录
                    if(checkBox_auto_login.isChecked()){
                        Util.saveAutoLogin(LoginActivity.this,true);
                    }else{
                        Util.saveAutoLogin(LoginActivity.this,false);
                    }
                    Toast.makeText(LoginActivity.this, jsonObject.optString("ERRMSG"), Toast.LENGTH_SHORT).show();
                }else if ( jsonObject.optString("RESULT").equals("F")) {
                    Toast.makeText(getApplicationContext(), jsonObject.optString("ERRMSG"), Toast.LENGTH_LONG).show();
                }
                LoadingDialog.disDialog();
            }

            @Override
            public void onError(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), volleyError.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
    /**
     * 网络设置
     */
    public  void netSetting(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("网络设置");
        View view=LayoutInflater.from(this).inflate(R.layout.url_setting_dialog,null);
        builder.setView(view);
        final Dialog dialog=builder.create();
        final EditText et_port=view.findViewById(R.id.et_server_port);
        final EditText et_url=view.findViewById(R.id.et_server_ip);
        Button btn_cancel,btn_ok;
        btn_cancel=view.findViewById(R.id.btn_urlSetting_cancel);
        btn_ok=view.findViewById(R.id.btn_urlSetting_ok);
        //读取保存的设置
        UrlBean urlBean=Util.loadSetting(LoginActivity.this);
        if(!urlBean.getUrl().equals("")){
           et_port.setText(urlBean.getPort());
           et_url.setText(urlBean.getUrl());
        }
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ip,port;
                ip=et_url.getText().toString().trim();
                port=et_port.getText().toString().trim();
                Util.saveSetting(ip,port,LoginActivity.this);
                dialog.dismiss();
            }
        });
       dialog.show();
    }

}

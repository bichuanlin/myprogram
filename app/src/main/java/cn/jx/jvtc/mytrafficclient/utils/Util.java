package cn.jx.jvtc.mytrafficclient.utils;

import android.content.Context;
import android.content.SharedPreferences;
import cn.jx.jvtc.mytrafficclient.bean.UrlBean;
import cn.jx.jvtc.mytrafficclient.bean.Users;

import static android.content.Context.MODE_PRIVATE;

public class Util {

    //保存用户自动登录信息，为true时，就跳过登录界面，直接进入主界面
    public  static  void saveAutoLogin(Context context,Boolean autoLogin){
        SharedPreferences sp=context.getSharedPreferences("setting",MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean("autoLogin", autoLogin);
        editor.commit();
    }
    //读取自动登录信息
    public  static  boolean getAutoLogin(Context context){
        SharedPreferences sp=context.getSharedPreferences("setting",MODE_PRIVATE);
        boolean autoLogin=sp.getBoolean("autoLogin",false);
        return  autoLogin;
    }
    //保存上次登录成功的用户名和密码
    public static void  saveUserInfo(Context context, Users users){
        SharedPreferences sp=context.getSharedPreferences("setting",MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("username", users.getUsername());
        editor.putString("password",users.getPassword());
        editor.commit();
    }
    //读取上次保存的用户名和密码，如果没有用户信息，返回null
    public  static  Users getUserInfo(Context context){
        SharedPreferences sp=context.getSharedPreferences("setting",MODE_PRIVATE);
        String username,password;
        username=sp.getString("username","");
        password=sp.getString("password","");
        if(username.equals("")){
            return  null;
        }else{
            Users u=new Users();
            u.setUsername(username);
            u.setPassword(password);
            return  u;
        }
    }
        //判断程序是否为第一次启动，如果是返回true，否则返回false
    public static  boolean isFirstStart(Context context){
        SharedPreferences sp=context.getSharedPreferences("setting",Context.MODE_PRIVATE);
        boolean result=false;
        result=sp.getBoolean("isStart",false);
       return  result;
    }
    public static  void saveApp_Started(Context context){
        SharedPreferences sp=context.getSharedPreferences("setting",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean("isStart",true);
        editor.commit();
    }
    public static void saveSetting(String ipUrl, String ipPort, Context context) {
        SharedPreferences spSettingSave = context.getSharedPreferences("setting", MODE_PRIVATE);// 将需要记录的数据保存在setting.xml文件中
        SharedPreferences.Editor editor = spSettingSave.edit();
        editor.putString("ipUrl", ipUrl);
        editor.putString("ipPort", ipPort);
        editor.commit();
    }
    public static UrlBean loadSetting(Context context) {
        UrlBean urlBean=new UrlBean();
        SharedPreferences loadSettingLoad = context.getSharedPreferences("setting", MODE_PRIVATE);
        //这里是将setting.xml 中的数据读出来
        urlBean.setUrl( loadSettingLoad.getString("ipUrl", "") );
        urlBean.setPort( loadSettingLoad.getString("ipPort", "") );
//        String urlSetting = "http://" + urlHttp+ ":" + urlPort + "/";
        return urlBean;
    }

}

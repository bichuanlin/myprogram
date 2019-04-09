package cn.jx.jvtc.mytrafficclient.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.jx.jvtc.mytrafficclient.R;

public class LoadingDialog {
    public static Dialog dialog;
    public static TextView textView;
    public static void showToast(Context context, String msg){
        Toast.makeText(context,msg, 1).show();
    }
    public static void showDialog(Context context,String title){
        dialog=new Dialog(context);
        //dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View view= LayoutInflater.from(context).inflate(R.layout.loading_dialog,null);
        textView=view.findViewById(R.id.tv_loading_dialog);
        textView.setText(title);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    public static void disDialog(){
        dialog.dismiss();
    }
}

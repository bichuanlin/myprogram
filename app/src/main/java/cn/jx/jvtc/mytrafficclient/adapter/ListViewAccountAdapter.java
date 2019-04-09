package cn.jx.jvtc.mytrafficclient.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.jx.jvtc.mytrafficclient.R;
import cn.jx.jvtc.mytrafficclient.bean.Account;

public class ListViewAccountAdapter extends BaseAdapter {
    Context context;
    List<Account> accountList;
    Callback callback;//接口回调
    public ListViewAccountAdapter(Context context, List<Account> accountList,Callback callback) {
        this.context = context;
        this.accountList = accountList;
        this.callback=callback;
    }
    @Override
    public int getCount() {
        return accountList.size();
    }
    @Override
    public Object getItem(int i) {
        return accountList.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.listview_account,null);
        }
        TextView tv_number,tv_carNumber,tv_person,tv_balance;
        ImageView imageView_icon;
        CheckBox checkBox;
        Button btn_charge;
        btn_charge=view.findViewById(R.id.listview_account_btn_recharge);
        tv_number=view.findViewById(R.id.listview_account_number);
        tv_carNumber=view.findViewById(R.id.listview_account_car_number);
        tv_person=view.findViewById(R.id.listview_account_car_person);
        tv_balance=view.findViewById(R.id.listview_account_balance);
        imageView_icon=view.findViewById(R.id.listview_account_car_icon);
        checkBox=view.findViewById(R.id.listview_account_chb_recharge);
        btn_charge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               callback.btnChargeClick(i);
            }
        });
        final Account account=accountList.get(i);
        tv_number.setText(account.getNumber());
        imageView_icon.setImageResource(account.getCarIcon());
        tv_carNumber.setText(account.getCarNumber());
        tv_person.setText(account.getName());
        tv_balance.setText("余额："+account.getBalance());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                callback.cbCheckedChanged(b,i);
            }
        });
        if(account.getBalance()>200) {
            view.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
        }else{
            view.setBackgroundColor(context.getResources().getColor(R.color.colorYellow));
        }
        return view;
    }
    public  interface  Callback{
        public  void btnChargeClick(int position);
        public void cbCheckedChanged(boolean b,int position);
    }
}

package cn.jx.jvtc.mytrafficclient.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import cn.jx.jvtc.mytrafficclient.R;
import cn.jx.jvtc.mytrafficclient.adapter.ViewPageAdapter;
import cn.jx.jvtc.mytrafficclient.utils.Util;

public class SplashActivity extends Activity {
    private static final String TAG = "SplashActivity";
    ConstraintLayout layout_splash;
    ViewPageAdapter adapter;
    ViewPager viewPager;
    RadioButton rb1,rb2,rb3,rb4;
    RadioGroup radioGroup;
    List<View> viewList;
       @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //判断是否为第一次启动，不是就直接打开登录界面
        if(Util.isFirstStart(this)){
            finish();
            start_loginActivty();
        }else{
            Util.saveApp_Started(this);//第一次启动，写入信息
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
//        if (getSupportActionBar() != null){
//            getSupportActionBar().hide();
//        }
        layout_splash=findViewById(R.id.layout_splash);
        layout_splash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_loginActivty();
            }
        });
        init_view();
        rb1=findViewById(R.id.rb_spash_1);
        rb2=findViewById(R.id.rb_spash_2);
        rb3=findViewById(R.id.rb_spash_3);
        rb4=findViewById(R.id.rb_spash_4);
        radioGroup=findViewById(R.id.rg_spash);
        viewPager=findViewById(R.id.viewPage_spash);
        adapter=new ViewPageAdapter(this,viewList);
        viewPager.setCurrentItem(0);
        viewPager.setAdapter(adapter);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                Log.d(TAG, "onCheckedChanged: "+i);
                switch (i){
                    case R.id.rb_spash_1:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.rb_spash_2:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.rb_spash_3:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.rb_spash_4:
                        viewPager.setCurrentItem(3);
                        break;
                }
            }
        });
        //添加页面滑动事件
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }
            @Override
            public void onPageSelected(int i) {

            }
            @Override
            public void onPageScrollStateChanged(int i) {
                //state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
                if(i==2) {
                    switch (viewPager.getCurrentItem()) {
                        case 0:
                            rb1.setChecked(true);
                            break;
                        case 1:
                            rb2.setChecked(true);
                            break;
                        case 2:
                            rb3.setChecked(true);
                            break;
                        case 3:
                            rb4.setChecked(true);
                            break;
                    }
                }
            }
        });
    }
    void start_loginActivty(){
        Intent intent=new Intent(SplashActivity.this,LoginActivity.class);
        startActivity(intent);
    }
    /**
     * 初始化引导页4个视图
     */
    void init_view(){
        viewList=new ArrayList<View>();
        View v1,v2,v3,v4;
        v1= LayoutInflater.from(this).inflate(R.layout.view_page_1,null);
        viewList.add(v1);
        v2= LayoutInflater.from(this).inflate(R.layout.view_page_2,null);
        viewList.add(v2);
        v3= LayoutInflater.from(this).inflate(R.layout.view_page_3,null);
        viewList.add(v3);
        v4= LayoutInflater.from(this).inflate(R.layout.view_page_4,null);
        viewList.add(v4);
    }

}

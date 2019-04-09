package cn.jx.jvtc.mytrafficclient.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cn.jx.jvtc.mytrafficclient.R;
import cn.jx.jvtc.mytrafficclient.adapter.ListViewLeftMenuAdapter;
import cn.jx.jvtc.mytrafficclient.fragment.Fragment1;
import cn.jx.jvtc.mytrafficclient.fragment.Fragment2;
import cn.jx.jvtc.mytrafficclient.fragment.fragment3.model.Car;
import cn.jx.jvtc.mytrafficclient.fragment.fragment3.model.CarPeccancy;
import cn.jx.jvtc.mytrafficclient.fragment.fragment3.model.Peccancy;
import cn.jx.jvtc.mytrafficclient.fragment.fragment3.model.Person;
import cn.jx.jvtc.mytrafficclient.fragment.fragment3.view.Fragment3;
import cn.jx.jvtc.mytrafficclient.fragment.fragment4.Fragment4;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    ListView listView;
    TextView tv_title;
    FragmentManager fragmentManager;
    Fragment fragment1,current_fragment,fragment2,fragment3,fragment4;
    List<Map<String,Object>> mapList;
    DrawerLayout drawlayout;
    LinearLayout layout_charge;
    int[] icons;//图标
    String[] titles;//左侧菜单名称
    List<Car> carList;
    List<CarPeccancy> carPeccancyList;
    List<Person> personList;
    List<Peccancy> peccancyList;//违章代码信息
    RechargeCallBack rechargeCallBack;
    public  void setData( List<Car> carList, List<CarPeccancy> carPeccancyList,List<Person> personList,List<Peccancy> peccancyList){
        this.carList=carList;
        this.carPeccancyList=carPeccancyList;
        this.personList=personList;
        this.peccancyList=peccancyList;
    }

    public List<Car> getCarList() {
        return carList;
    }

    public List<CarPeccancy> getCarPeccancyList() {
        return carPeccancyList;
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public List<Peccancy> getPeccancyList() {
        return peccancyList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null){
           getSupportActionBar().hide();
        }
        init_view();
        initData();
        drawlayout.closeDrawer(Gravity.LEFT);
        fragmentManager=getSupportFragmentManager();
        fragment1=new Fragment1();
        loadFragment();
    }
    /**
     * 初始化化组件
     */
    void init_view(){
      listView=findViewById(R.id.listview_left_menu);
      tv_title=findViewById(R.id.tv_main_title);
      drawlayout=findViewById(R.id.drawlayout);
      layout_charge=findViewById(R.id.layout_main_charge);
    }
    public  void initData(){
        icons=new int[]{R.drawable.icon1,R.drawable.icon2,R.drawable.icon3,R.drawable.icon4
                ,R.drawable.icon5,R.drawable.icon6,R.drawable.icon7,R.drawable.icon8};
        titles=new String[]{"账户管理","公交查询","数据分析","违章查询","路况查询","生活助手","红绿灯管理","个人中心"};
        Map<String,Object> map;
        mapList=new ArrayList<>();
        for (int i = 0; i <8 ; i++) {
            map=new HashMap<>();
            map.put("icon",icons[i]);
            map.put("title",titles[i]);
            mapList.add(map);
        }
        ListViewLeftMenuAdapter adapter=new ListViewLeftMenuAdapter(this,mapList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tv_title.setText(titles[position]);
                showDrawLayout();
                switch (position){
                    case 0://账户管理
                        if(fragment1==null){
                           fragment1=new Fragment1();
                        }
                        replaceFragment(fragment1);
                        layout_charge.setVisibility(View.VISIBLE);
                        break;
                    case 1://公交查询
                        if(fragment2==null){
                            fragment2=new Fragment2();
                        }
                        replaceFragment(fragment2);
                        layout_charge.setVisibility(View.INVISIBLE);
                        break;
                  case 2://数据分析
                        if(fragment3==null){
                            fragment3=new Fragment3();
                        }
                        replaceFragment(fragment3);
                       layout_charge.setVisibility(View.INVISIBLE);
                       break;
                    case 3://违章查询
                        if(fragment4==null){
                            fragment4=new Fragment4();
                        }
                        replaceFragment(fragment4);
                        layout_charge.setVisibility(View.INVISIBLE);
                        break;
                }
            }
        });
    }
    /**
     * 侧滑菜单打开和关闭
     */
    public  void showDrawLayout(){
        if(drawlayout.isDrawerOpen(Gravity.LEFT)){
            drawlayout.closeDrawer(Gravity.LEFT);
        }else{
            drawlayout.openDrawer(Gravity.LEFT);
        }
    }
   public void loadFragment(){
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame_content,fragment1);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        current_fragment=fragment1;
    }
    /**
     * 加载新的fragment
     */
   public   void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(fragment.isAdded()){
            fragmentTransaction.hide(current_fragment).show(fragment);
        }else{
           // Log.d(TAG, "replaceFragment: "+current_fragment);
            fragmentTransaction.hide(current_fragment).add(R.id.frame_content,fragment);
            fragmentTransaction.addToBackStack(null);
        }
        current_fragment=fragment;
        fragmentTransaction.commit();
    }
    /**
     * 批量充值
     */
    public  void tvRechargeOnClick(View v){
        rechargeCallBack= (RechargeCallBack) fragment1;
        rechargeCallBack.rechargeAll();
    }
    /**
     * 充值记录
     */
    public  void  tvRechargeRecordOnClick(View v){

    }
   public   interface  RechargeCallBack{
        public  void rechargeAll();
   }
   public int getData(){
        return  66666;
   }
}




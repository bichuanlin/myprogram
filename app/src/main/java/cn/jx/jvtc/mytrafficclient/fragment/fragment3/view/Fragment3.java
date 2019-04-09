package cn.jx.jvtc.mytrafficclient.fragment.fragment3.view;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.jx.jvtc.mytrafficclient.R;
import cn.jx.jvtc.mytrafficclient.activity.MainActivity;
import cn.jx.jvtc.mytrafficclient.adapter.ViewPageFragment3Adapter;
import cn.jx.jvtc.mytrafficclient.fragment.fragment3.model.Car;
import cn.jx.jvtc.mytrafficclient.fragment.fragment3.model.CarPeccancy;
import cn.jx.jvtc.mytrafficclient.fragment.fragment3.model.Peccancy;
import cn.jx.jvtc.mytrafficclient.fragment.fragment3.model.Person;
import cn.jx.jvtc.mytrafficclient.fragment.fragment3.presenter.Fragment3Presenter;
import cn.jx.jvtc.mytrafficclient.fragment.fragment3.presenter.IFragment3Presenter;
/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment3 extends Fragment implements IFragment3View {
    private static final String TAG = "Fragment3";
   Context context;
   Fragment[] fragments;
   RadioGroup rg;
   RadioButton[] rb;
   ViewPager viewPager;
   ViewPageFragment3Adapter adapter;
   View v;
   List<Car> carList;
   List<CarPeccancy> carPeccancyList;
   List<Person> personList;
   List<Peccancy> peccancyList;
   IFragment3Presenter mIFragment3Presenter;

    public Fragment3() {
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
        v=inflater.inflate(R.layout.fragment_fragment3, container, false);
        mIFragment3Presenter=new Fragment3Presenter(this);
        String strConn= "/api/v2/get_car_info";
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mIFragment3Presenter.getCars(strConn,jsonObject,getContext());//发送请求
        return v;
    }
    /**
     * 初始化Fragment
     */
    void initFragment(){
        fragments=new Fragment[7];
        fragments[0]=new Fragment3_1();
        fragments[1]=new Fragment3_2();
        fragments[2]=new Fragment3_3();
        fragments[3]=new Fragment3_4();
        fragments[4]=new Fragment3_5();
        fragments[5]=new Fragment3_6();
        fragments[6]=new Fragment3_7();
    }
    void initView(){
        viewPager=v.findViewById(R.id.viewPage_Fragment3);
        rb=new RadioButton[7];
        rb[0]=v.findViewById(R.id.rb_fragment_1);
        rb[1]=v.findViewById(R.id.rb_fragment_2);
        rb[2]=v.findViewById(R.id.rb_fragment_3);
        rb[3]=v.findViewById(R.id.rb_fragment_4);
        rb[4]=v.findViewById(R.id.rb_fragment_5);
        rb[5]=v.findViewById(R.id.rb_fragment_6);
        rb[6]=v.findViewById(R.id.rb_fragment_7);
        rg=v.findViewById(R.id.rg_fragment3);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rb_fragment_1:
                       viewPager.setCurrentItem(0);
                       break;
                    case R.id.rb_fragment_2:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.rb_fragment_3:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.rb_fragment_4:
                        viewPager.setCurrentItem(3);
                        break;
                    case R.id.rb_fragment_5:
                        viewPager.setCurrentItem(4);
                        break;
                    case R.id.rb_fragment_6:
                        viewPager.setCurrentItem(5);
                        break;
                    case R.id.rb_fragment_7:
                        viewPager.setCurrentItem(6);
                        break;
                }
            }
        });
    }
    void init(){
        initFragment();
        initView();
        adapter=new ViewPageFragment3Adapter(getFragmentManager(),fragments);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }
            @Override
            public void onPageSelected(int i) {

            }
            @Override
            public void onPageScrollStateChanged(int state) {
                //state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
                if (state == 2) {
                    int i=viewPager.getCurrentItem();
                    rb[i].setChecked(true);
                }
            }
        });
    }

    @Override
    public <T> void response(T response, int responseFlag) {
        if(responseFlag==IFragment3View.RESPONSE_CAR){
            carList= (List<Car>) response;
            String strConn="/api/v2/get_all_car_peccancy";
            JSONObject json=new JSONObject();
            try {
                json.put("UserName","user1");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mIFragment3Presenter.getCarPeccancy(strConn,json,getContext());//读取违章信息
        }
        if(responseFlag==IFragment3View.RESPONSE_CARPECCANCY){
            carPeccancyList= (List<CarPeccancy>) response;
            String carNumber,pcode;//车牌号
            //把违章次数和代码写入carList中
            //Log.d(TAG, "response: "+carPeccancyList.size());
            for (int i = 0; i <carPeccancyList.size() ; i++) {
                carNumber=carPeccancyList.get(i).getCarnumber();
                pcode=carPeccancyList.get(i).getPcode();
                int j=0;
                for(j=0;j<carList.size();j++){
                    if(carList.get(j).getCarNumber().equals(carNumber)){
                        //统计违章次数
                        carList.get(j).setPeccancy(carList.get(j).getPeccancy()+1);//违章次数加1
                        //保存违章代码
                        if(carList.get(j).getPcodes().equals("")){
                            carList.get(j).setPcodes(pcode);
                        }else{
                            //判断是否出现了重复代码，重复了就不连接，否则以逗号分隔连接起来
                            if(!carList.get(j).getPcodes().contains(pcode)){
                                carList.get(j).setPcodes(carList.get(j).getPcodes()+","+pcode);
                            }
                        }
                         break;
                    }
                }

            }
            //读取车主信息
            String strConn="/api/v2/get_all_user_info";
            JSONObject json=new JSONObject();
            try {
                json.put("UserName","user1");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mIFragment3Presenter.getPerson(strConn,json,getContext());
//
        }
        if(responseFlag==IFragment3View.RESPONSE_PERSON){
            personList= (List<Person>) response;//得到车主信息
            //得到车主违章信息次数
            getPeccancyToPerson();
            //读取违章代码信息
            String strConn="/api/v2/get_peccancy_type";
            JSONObject json=new JSONObject();
            try {
                json.put("UserName","user1");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mIFragment3Presenter.getPeccancy(strConn,json,getContext());
        }
        if(responseFlag==IFragment3View.RESPONSE_PECCANCY){
            peccancyList= (List<Peccancy>) response;//违章信息
            //计算各类违章次数
            sum_peccancy();
            MainActivity mainActivity= (MainActivity) context;
            mainActivity.setData(carList,carPeccancyList,personList,peccancyList);//数据传递到activity
            init();//初始化viewpage
        }
    }
    /**
     * 从carlist得到插住违章信息(违章次数，并写入到personlist
     */
    void getPeccancyToPerson(){
        String pIdcard="";
        for (int i = 0; i <carList.size() ; i++) {
            pIdcard=carList.get(i).getPcardid();//身份证信息
            for(int j=0;j<personList.size();j++){
                if(personList.get(j).getPcardid().equals(pIdcard)){
                    //违章次数+1
                    personList.get(j).setPeccancy(personList.get(j).getPeccancy()+carList.get(i).getPeccancy());
                }
            }
        }
    }
    /**
     * 统计各类违章次数，保存到PeccancyList中
     */
    void sum_peccancy(){
        String pcode;
        for (int i = 0; i <carPeccancyList.size() ; i++) {
            pcode=carPeccancyList.get(i).getPcode();//违章代码
            for(int j=0;j<peccancyList.size();j++){
                if(peccancyList.get(j).getPcode().equals(pcode)){
                    peccancyList.get(j).setPeccancyTotal( peccancyList.get(j).getPeccancyTotal()+1);
                }
            }
        }
    }
}

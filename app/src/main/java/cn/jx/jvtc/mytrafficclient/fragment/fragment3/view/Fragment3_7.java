package cn.jx.jvtc.mytrafficclient.fragment.fragment3.view;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.jx.jvtc.mytrafficclient.R;
import cn.jx.jvtc.mytrafficclient.activity.MainActivity;
import cn.jx.jvtc.mytrafficclient.fragment.fragment3.model.Peccancy;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment3_7 extends Fragment {
    private static final String TAG = "Fragment3_7";
    HorizontalBarChart mBarChart;
    List<Peccancy> peccancyList;
    float[] p;
    public Fragment3_7() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        MainActivity mainActivity= (MainActivity) context;
        this.peccancyList=mainActivity.getPeccancyList();
        //排序
        Collections.sort(peccancyList,new Peccancy.OrderPeccancyCountNoDesc());
//        for (int i = 0; i <peccancyList.size() ; i++) {
//            Log.d(TAG, "onAttach: "+peccancyList.get(i).getPcode()+":"+peccancyList.get(i).getPeccancyTotal()+":"+peccancyList.get(i).getPremarks());
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_fragment3_7, container, false);
        sum_peccancy();
        initView(v);
        return v;
    }
    private void initView(View view) {
        mBarChart=view.findViewById(R.id.barChat_fragment37);
        mBarChart.setTouchEnabled(false);
        mBarChart.setPinchZoom(false);
        mBarChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mBarChart.getXAxis().setDrawGridLines(false);
        mBarChart.getAxisLeft().setEnabled(false);
        mBarChart.setData(getBarChartData());
    }
    List<String> title;
    public BarData getBarChartData() {
        title=new ArrayList<>();
        for (int i = 9; i >=0 ; i--) {
            title.add(peccancyList.get(i).getPremarks());
            title.add("");
        }
        int colors[]=new int[]{0xff40659A,0xff320F19,0xff090091,0xff50612D,0xffB9AFE0,0xff769337
                ,0xff913535,0xffFDBE0B,0xff4D82B9,0xffff0000
        };
        List<BarEntry> data=new ArrayList<>();
        for (int i = 9; i >=0 ; i--) {
            data.add(new BarEntry(p[i],(9-i)*2));
        }
        BarDataSet dataSet=new BarDataSet(data,"");
        dataSet.setColors(colors);
        dataSet.setDrawValues(false);
        return new BarData(title,dataSet);
    }
    /**
     * 计算前10违章的百分比
     */
    void sum_peccancy(){
        int s=0;
        for(int i=0;i<peccancyList.size();i++){
            s=s+peccancyList.get(i).getPeccancyTotal();
        }
        p=new float[10];
       // Log.d(TAG, "sum_peccancy: "+s);
        for (int i = 0; i <10 ; i++) {
            p[i]=(float)( peccancyList.get(i).getPeccancyTotal()*100.0/s);
        }
    }
}

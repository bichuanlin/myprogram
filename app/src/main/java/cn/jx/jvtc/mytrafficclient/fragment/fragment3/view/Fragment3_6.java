package cn.jx.jvtc.mytrafficclient.fragment.fragment3.view;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.jx.jvtc.mytrafficclient.R;
import cn.jx.jvtc.mytrafficclient.activity.MainActivity;
import cn.jx.jvtc.mytrafficclient.fragment.fragment3.model.CarPeccancy;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment3_6 extends Fragment {
    private static final String TAG = "Fragment3_6";
    BarChart mBarChart;
    List<CarPeccancy> carPeccancyList;//车牌违章信息
    int[] period;//12个时间段违反数量
    float[] fPeriod;//12个时间段占比
    int total;//违章总数量
    public Fragment3_6() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        MainActivity mainActivity= (MainActivity) context;
        carPeccancyList=mainActivity.getCarPeccancyList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_fragment3_6, container, false);
        sum_period_peccancy();
        initView(v);
        return v;
    }
    private void initView(View view) {
        mBarChart=view.findViewById(R.id.barChat_fragment36);
        mBarChart.setTouchEnabled(false);
        mBarChart.setPinchZoom(false);
        mBarChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mBarChart.getXAxis().setDrawGridLines(false);
        mBarChart.getAxisRight().setEnabled(false);
        mBarChart.setData(getBarChartData());
    }

    List<String> title;
    public BarData getBarChartData() {
        title=new ArrayList<>();
        title.add("0:00:01-2:00:00");
        title.add("");
        title.add("2:00:01-4:00:00");
        title.add("");
        title.add("4:00:01-6:00:00");
        title.add("");
        title.add("6:00:01-8:00:00");
        title.add("");
        title.add("8:00:01-10:00:00");
        title.add("");
        title.add("10:00:01-12:00:00");
        title.add("");
        title.add("12:00:01-14:00:00");
        title.add("");
        title.add("14:00:01-16:00:00");
        title.add("");
        title.add("16:00:01-18:00:00");
        title.add("");
        title.add("18:00:01-20:00:00");
        title.add("");
        title.add("20:00:01-22:00:00");
        title.add("");
        title.add("22:00:01-24:00:00");
        int colors[]=new int[]{0xff40659A,0xff320F19,0xff090091,0xff50612D,0xffB9AFE0,0xff769337
                ,0xff913535,0xffFDBE0B,0xff4D82B9,0xffE06D12,0xff953676,0xff592223
        };
        List<BarEntry> data=new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            data.add(new BarEntry(fPeriod[i],i*2));
        }
        BarDataSet dataSet=new BarDataSet(data,"");
        dataSet.setColors(colors);
        dataSet.setDrawValues(false);
        return new BarData(title,dataSet);
    }
    /**
     * 计算一天十二个时间段占比情况
     */
    void sum_period_peccancy() {
        total = carPeccancyList.size();
        period = new int[12];
        fPeriod = new float[12];
        for (int i = 0; i < carPeccancyList.size(); i++) {
            Date peccancy_time = carPeccancyList.get(i).getDatetime();
            int hour = peccancy_time.getHours();
            // Log.d(TAG, "sum_period_peccancy: "+hour+":"+total);
            period[hour/2]++;
        }
        for (int i = 0; i <12 ; i++) {
            fPeriod[i]=(float)(period[i]*100.0/total);
        }
    }

}


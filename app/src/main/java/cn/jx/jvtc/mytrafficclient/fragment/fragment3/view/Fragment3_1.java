package cn.jx.jvtc.mytrafficclient.fragment.fragment3.view;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cn.jx.jvtc.mytrafficclient.R;
import cn.jx.jvtc.mytrafficclient.activity.MainActivity;
import cn.jx.jvtc.mytrafficclient.fragment.fragment3.model.Car;
import cn.jx.jvtc.mytrafficclient.fragment.fragment3.model.CarPeccancy;
import cn.jx.jvtc.mytrafficclient.http.VolleyUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment3_1 extends Fragment  {
    private static final String TAG = "Fragment3_1";
    PieChart mPieChart;
    int peccancyCount=0;//违章车辆数量
    List<Car> carList;//所有车辆信息
    List<CarPeccancy> carPeccancyList;//车牌违章信息
    Context context;
    public Fragment3_1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_fragment3_1, container, false);
        mPieChart=view.findViewById(R.id.pieChart_fragmentt3_1);
        MainActivity mainActivity= (MainActivity) context;
        this.carList=mainActivity.getCarList();
        this.carPeccancyList=mainActivity.getCarPeccancyList();
        sum_car_peccancy();
        initChart();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }

    void initChart(){
        // 显示百分比
        mPieChart.setUsePercentValues(true);
        // 描述信息
       mPieChart.setDescription("");
        // 设置偏移量
        mPieChart.setExtraOffsets(5, 30, 5, 5);
        // 设置滑动减速摩擦系数
        mPieChart.setDragDecelerationFrictionCoef(0.95f);
//        mPieChart.setCenterText("测试饼图，中间文字");
        /*
            设置饼图中心是否是空心的
            true 中间是空心的，环形图
            false 中间是实心的 饼图
         */
        mPieChart.setDrawHoleEnabled(false);
        /*
            设置中间空心圆孔的颜色是否透明
            true 透明的
            false 非透明的
         */
       // mPieChart.setHoleColorTransparent(true);
        // 设置环形图和中间空心圆之间的圆环的颜色
       // mPieChart.setTransparentCircleColor(Color.WHITE);
        // 设置环形图和中间空心圆之间的圆环的透明度
       // mPieChart.setTransparentCircleAlpha(110);

        // 设置圆孔半径
       // mPieChart.setHoleRadius(58f);
        // 设置空心圆的半径
       // mPieChart.setTransparentCircleRadius(61f);
        // 设置是否显示中间的文字
       // mPieChart.setDrawCenterText(true);
        // 设置旋转角度   ？？
        mPieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mPieChart.setRotationEnabled(true);
        TreeMap<String, Float> data = new TreeMap<>();
        float f=(float)(peccancyCount*1.0/carList.size());
        data.put("有违章", f);
        data.put("无违章",1-f);
        setData(data);
        // 设置动画
        mPieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // 设置显示的比例
        Legend l = mPieChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        l.setTextSize(20f);//设置图例文字大小
        l.setFormSize(20f);//设置图例大小
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
    }
    public void setData(TreeMap<String, Float> data) {
        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        int i = 0;
        Iterator it = data.entrySet().iterator();
        while (it.hasNext()) {
            // entry的输出结果如key0=value0等
            Map.Entry entry = (Map.Entry) it.next();
            String key = (String) entry.getKey();
            float value = (float) entry.getValue();
            xVals.add(key);
            yVals1.add(new Entry(value, i++));
        }
        PieDataSet dataSet = new PieDataSet(yVals1, "");
        // 设置饼图区块之间的距离
        dataSet.setSliceSpace(10f);
        dataSet.setSelectionShift(5f);
        // 添加颜色
        ArrayList<Integer> colors = new ArrayList<Integer>();//
        colors.add(Color.rgb(50,100,255));
        colors.add(Color.rgb(255,200,0));
         dataSet.setColors(colors);
        PieData data1 = new PieData(xVals, dataSet);
        data1.setValueFormatter(new PercentFormatter());
        data1.setValueTextSize(20f);
        data1.setValueTextColor(Color.BLACK);
        mPieChart.setData(data1);
        // undo all highlights
        mPieChart.highlightValues(null);
        mPieChart.invalidate();
    }

    /**
     * 计算违章车辆数量
     */
    void sum_car_peccancy(){
        peccancyCount=0;
        for (int i = 0; i <carList.size() ; i++) {
            if(carList.get(i).getPeccancy()>0){
                peccancyCount++;
            }
        }

    }

}

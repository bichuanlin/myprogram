package cn.jx.jvtc.mytrafficclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;

import cn.jx.jvtc.mytrafficclient.R;
import cn.jx.jvtc.mytrafficclient.bean.Bus;
import cn.jx.jvtc.mytrafficclient.bean.BusStation;

public class ExpandListViewBusStationAdapter extends BaseExpandableListAdapter {
    Context context;
    List<BusStation> busStationList;
    public ExpandListViewBusStationAdapter(Context context, List<BusStation> busStationList) {
        this.context = context;
        this.busStationList = busStationList;
    }
    @Override
    public int getGroupCount() {
        return busStationList.size();
    }
    @Override
    public int getChildrenCount(int i) {
        return busStationList.get(i).getBusList().size();
    }
    @Override
    public Object getGroup(int i) {
        return busStationList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return busStationList.get(i).getBusList().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }
    @Override
    public boolean hasStableIds() {
        return false;
    }
    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.fragment2_expand_group_view,null);
        }
        TextView tv;
        tv=view.findViewById(R.id.tv_fragment2_expand_group_name);
        tv.setText(busStationList.get(i).getId()+":"+busStationList.get(i).getName());
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        if(view==null){
            view=LayoutInflater.from(context).inflate(R.layout.fragment2_expand_child_view,null);
        }
        TextView tv_id,tv_capicaty,tv_distance,tv_time;
        tv_id=view.findViewById(R.id.tv_fragment2_expand_child_busid);
        tv_capicaty=view.findViewById(R.id.tv_fragment2_expand_child_capacity);
        tv_distance=view.findViewById(R.id.tv_fragment2_expand_child_bus_distince);
        tv_time=view.findViewById(R.id.tv_fragment2_expand_child_bus_time);
        Bus bus=busStationList.get(i).getBusList().get(i1);
        tv_id.setText(bus.getId()+"");
        tv_capicaty.setText("("+bus.getCapacity()+")");
        tv_time.setText(bus.getTime()+"分钟到达");
        tv_distance.setText("距离站台:"+bus.getDistance()+"米");
        return view;
    }
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}

package cn.jx.jvtc.mytrafficclient.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.jx.jvtc.mytrafficclient.R;
import cn.jx.jvtc.mytrafficclient.bean.Bus;

public class ListViewBusCapacityAdapter extends BaseAdapter {
    private static final String TAG = "ListViewBusCapacityAdap";
    Context context;
    List<Bus> busList;

    public ListViewBusCapacityAdapter(Context context, List<Bus> busList) {
        this.context = context;
        this.busList = busList;
    }

    @Override
    public int getCount() {
        return busList.size();
    }

    @Override
    public Object getItem(int i) {
        return busList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.fragment2_dialog_listview_layout,null);
        }
        TextView tv_id,tv_busId,tv_busCapacity;
        tv_id=view.findViewById(R.id.tv_fragment2_dialog_listview_id);
        tv_busId=view.findViewById(R.id.tv_fragment2_dialog_listview_busId);
        tv_busCapacity=view.findViewById(R.id.tv_fragment2_dialog_listview_bus_capacity);
        tv_id.setText((i+1)+"");
        Log.d(TAG, "getView: "+busList.get(i).getId());
        tv_busId.setText(busList.get(i).getId()+"");
        tv_busCapacity.setText(busList.get(i).getCapacity()+"");
        return view;
    }
}

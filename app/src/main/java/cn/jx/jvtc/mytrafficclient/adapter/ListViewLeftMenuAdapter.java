package cn.jx.jvtc.mytrafficclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import cn.jx.jvtc.mytrafficclient.R;

public class ListViewLeftMenuAdapter extends BaseAdapter {
    Context context;
    List<Map<String ,Object>> mapList;
    public ListViewLeftMenuAdapter(Context context, List<Map<String, Object>> mapList) {
        this.context = context;
        this.mapList = mapList;
    }
    @Override
    public int getCount() {
        return mapList.size();
    }
    @Override
    public Object getItem(int i) {
        return mapList.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.left_menu_listview,null);
        }
        ImageView imageView;
        TextView textView;
        imageView=view.findViewById(R.id.imageview_left_menu);
        textView=view.findViewById(R.id.textview_left_menu);
        imageView.setImageResource((Integer) mapList.get(i).get("icon"));
        textView.setText(mapList.get(i).get("title").toString());
        return view;
    }
}

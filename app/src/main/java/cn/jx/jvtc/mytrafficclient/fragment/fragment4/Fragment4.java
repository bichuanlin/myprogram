package cn.jx.jvtc.mytrafficclient.fragment.fragment4;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import cn.jx.jvtc.mytrafficclient.R;
import cn.jx.jvtc.mytrafficclient.activity.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment4 extends Fragment {
    Button btn_search;
    MainActivity mainActivity;
    Fragment fragment4_1;
    public Fragment4() {
        // Required empty public constructor
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity= (MainActivity) context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_fragment4, container, false);
        btn_search=view.findViewById(R.id.btn_search_fragment4);
        fragment4_1=new Fragment4_1();
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.replaceFragment(fragment4_1);
            }
        });
        return view;
    }
}

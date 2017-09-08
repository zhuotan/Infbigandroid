package rednum.com.infbigand;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/19.
 */

public class MemberInfoFragment extends Fragment {

    private ListView listView;
    private Bundle bundle;
    private ArrayList<String> memberInfoList;
    private MemeberInfoAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
        memberInfoList = bundle.getStringArrayList("member_info_key");
        adapter = new MemeberInfoAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.memeber_info_layout, null);
        listView = view.findViewById(R.id.member_info_listview);

        listView.setAdapter(adapter);

        return view;
    }

    public void updateData() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        } else {
            Log.i("company", "============> adapter is NULL");
        }

    }


    /*
     * 适配器
     */
    private class MemeberInfoAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return memberInfoList.size();
        }

        @Override
        public Object getItem(int i) {
            return memberInfoList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View itemview = inflater.inflate(R.layout.member_info_item_layout, null);
            TextView memberItem = itemview.findViewById(R.id.member_info_item);
            memberItem.setText(memberInfoList.get(i));

            return itemview;
        }
    }
}


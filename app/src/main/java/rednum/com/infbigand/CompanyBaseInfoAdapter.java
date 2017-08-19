package rednum.com.infbigand;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Administrator on 2017/8/19.
 */

public class CompanyBaseInfoAdapter extends BaseAdapter {
    private Context context;
    private LinkedList<HashMap<String, Object>> data;

    public CompanyBaseInfoAdapter(Context context, LinkedList<HashMap<String, Object>> data) {
        this.context = context;
        this.data = data;
        Log.i("yang", "data=" + data);
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = null;
        if (i == 0) {
            itemView = inflater.inflate(R.layout.company_name_item_layout, null);
        } else {
            itemView = inflater.inflate(R.layout.compayn_not_name_item_layout, null);
            ImageView icon = itemView.findViewById(R.id.item_icon);
            TextView text = itemView.findViewById(R.id.item_text);

            Log.i("yang", "i-1=" + (i - 1));

            HashMap<String, Object> map = data.get(i - 1);

            Log.i("yang", "map=" + map);

            int icon_id = (int) map.get("icon");
            icon.setImageResource(icon_id);

            String item_text = (String) map.get("content");
            text.setText(item_text);
        }

        return itemView;
    }
}

package rednum.com.infbigand;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/9/7.
 */

public class CompanyZizhiInfoAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> data;

    public CompanyZizhiInfoAdapter(Context context, ArrayList<String> data) {
        this.context = context;
        this.data = data;
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
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.company_zizhi_item_layout, null);
            holder.zizhi = convertView.findViewById(R.id.company_zizhi_item);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.zizhi.setText(data.get(i));

        return convertView;
    }

    class ViewHolder {
        public TextView zizhi;
    }
}

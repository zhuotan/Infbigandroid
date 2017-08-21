package rednum.com.infbigand;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Administrator on 2017/8/19.
 */

public class CreditLevelAdapter extends BaseAdapter {
    private Context context;
    private LinkedList<HashMap<String, String>> data;

    public CreditLevelAdapter(Context context, LinkedList<HashMap<String, String>> data) {
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
        ViewHolder holder = null;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.credit_level_item_layout, null);

            holder.yearField = convertView.findViewById(R.id.year);
            holder.provinceField = convertView.findViewById(R.id.province);
            holder.levelField = convertView.findViewById(R.id.level);
            holder.typeField = convertView.findViewById(R.id.type);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        HashMap<String, String> map = data.get(i);

        holder.yearField.setText(map.get("year"));
        holder.provinceField.setText(map.get("province"));
        holder.levelField.setText(map.get("level"));
        holder.typeField.setText(map.get("type"));


        return convertView;
    }

    private class ViewHolder {
        TextView yearField;
        TextView provinceField;
        TextView levelField;
        TextView typeField;
    }

}



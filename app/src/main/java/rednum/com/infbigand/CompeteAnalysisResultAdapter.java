package rednum.com.infbigand;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Administrator on 2017/8/28.
 */

public class CompeteAnalysisResultAdapter extends BaseAdapter {
    private Context context;
    private LinkedList<HashMap<String, String>> data;

    public CompeteAnalysisResultAdapter(Context context, LinkedList<HashMap<String, String>> data) {
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
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.compete_analysis_result_item_layout, null);
            holder = new ViewHolder();
            holder.companyName = convertView.findViewById(R.id.result_company_name);
            holder.province = convertView.findViewById(R.id.result_province);
            holder.creditLevel = convertView.findViewById(R.id.check_credit_level);
            holder.beian = convertView.findViewById(R.id.check_beian);
            holder.intell = convertView.findViewById(R.id.check_company_intell);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        HashMap<String, String> map = data.get(i);

        holder.companyName.setText(map.get("companyName"));
        holder.province.setText(map.get("province"));

        holder.creditLevel.setOnClickListener(new CompeteAnalysisOnClickListener(map));
        holder.beian.setOnClickListener(new CompeteAnalysisOnClickListener(map));
        holder.intell.setOnClickListener(new CompeteAnalysisOnClickListener(map));

        return convertView;
    }


    class ViewHolder {
        private TextView companyName;
        private TextView province;
        private TextView creditLevel;
        private TextView beian;
        private TextView intell;
    }

    class CompeteAnalysisOnClickListener implements View.OnClickListener {

        private HashMap<String, String> map;

        public CompeteAnalysisOnClickListener(HashMap<String, String> map) {
            this.map = map;
        }

        @Override
        public void onClick(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            switch (view.getId()) {
                case R.id.check_credit_level:
                    View creditLevelLayout = LayoutInflater.from(context).inflate(R.layout.null_view, null);
                    builder.setTitle("信用评级");
                    builder.setView(creditLevelLayout);
                    AlertDialog dialog_credit = builder.create();

                    dialog_credit.show();

                    break;

                case R.id.check_beian:
                    View beianLayout = LayoutInflater.from(context).inflate(R.layout.null_view, null);
                    builder.setTitle("备案");
                    builder.setView(beianLayout);
                    AlertDialog dialog_beian = builder.create();

                    dialog_beian.show();

                    break;

                case R.id.check_company_intell:
                    String intell = map.get("intell");
                    String[] intellArray = intell.split(",");

                    View intellLayout = LayoutInflater.from(context).inflate(R.layout.compete_analysis_result_intell_layout, null);
                    ListView intellListview = intellLayout.findViewById(R.id.compete_analysis_result_intell_listview);
                    ArrayAdapter<String> adapter_3 = new ArrayAdapter(context, R.layout.common_item_selected_layout, R.id.common_listview_item, intellArray);
                    intellListview.setAdapter(adapter_3);
                    builder.setTitle("企业资质");
                    builder.setView(intellLayout);
                    AlertDialog dialog = builder.create();

                    dialog.show();


                    break;
            }
        }
    }
}

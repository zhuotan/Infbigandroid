package rednum.com.infbigand;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Administrator on 2017/8/19.
 */

public class CreditLevelActivity extends Activity {
    private ListView creditLevelList;
    private CreditLevelAdapter adapter;
    private LinkedList<HashMap<String, String>> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.credit_level_layout);

        creditLevelList = findViewById(R.id.credit_level_listview);
        data = new LinkedList<HashMap<String, String>>();
        HashMap<String, String> map_1 = new HashMap<>();
        map_1.put("year", "年度：2016");
        map_1.put("province", "省份：浙江");
        map_1.put("level", "等级：AA级");
        map_1.put("type", "类型：施工");


        HashMap<String, String> map_2 = new HashMap<>();
        map_2.put("year", "年度：2015");
        map_2.put("province", "省份：四川");
        map_2.put("level", "等级：A级");
        map_2.put("type", "类型：施工");

        HashMap<String, String> map_3 = new HashMap<>();
        map_3.put("year", "年度：2015");
        map_3.put("province", "省份：四川");
        map_3.put("level", "等级：A级");
        map_3.put("type", "类型：施工");

        HashMap<String, String> map_4 = new HashMap<>();
        map_4.put("year", "年度：2015");
        map_4.put("province", "省份：四川");
        map_4.put("level", "等级：A级");
        map_4.put("type", "类型：施工");

        HashMap<String, String> map_5 = new HashMap<>();
        map_5.put("year", "年度：2015");
        map_5.put("province", "省份：四川");
        map_5.put("level", "等级：A级");
        map_5.put("type", "类型：施工");

        HashMap<String, String> map_6 = new HashMap<>();
        map_6.put("year", "年度：2015");
        map_6.put("province", "省份：四川");
        map_6.put("level", "等级：A级");
        map_6.put("type", "类型：施工");

        HashMap<String, String> map_7 = new HashMap<>();
        map_7.put("year", "年度：2015");
        map_7.put("province", "省份：四川");
        map_7.put("level", "等级：A级");
        map_7.put("type", "类型：施工");

        HashMap<String, String> map_8 = new HashMap<>();
        map_8.put("year", "年度：2015");
        map_8.put("province", "省份：四川");
        map_8.put("level", "等级：A级");
        map_8.put("type", "类型：施工");

        HashMap<String, String> map_9 = new HashMap<>();
        map_9.put("year", "年度：2015");
        map_9.put("province", "省份：四川");
        map_9.put("level", "等级：A级");
        map_9.put("type", "类型：施工");

        HashMap<String, String> map_10 = new HashMap<>();
        map_10.put("year", "年度：2015");
        map_10.put("province", "省份：四川");
        map_10.put("level", "等级：A级");
        map_10.put("type", "类型：施工");

        data.add(map_1);
        data.add(map_2);
        data.add(map_3);
        data.add(map_4);
        data.add(map_5);
        data.add(map_6);
        data.add(map_7);
        data.add(map_8);
        data.add(map_9);
        data.add(map_10);


        adapter = new CreditLevelAdapter(CreditLevelActivity.this, data);

        creditLevelList.setAdapter(adapter);
    }
}

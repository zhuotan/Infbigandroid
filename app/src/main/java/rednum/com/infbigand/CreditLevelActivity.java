package rednum.com.infbigand;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import rednum.com.infbigand.Net.NetProcess;
import rednum.com.infbigand.System.StatusBarUtil;

/**
 * Created by Administrator on 2017/8/19.
 */

public class CreditLevelActivity extends Activity {
    private ListView creditLevelList;
    private CreditLevelAdapter adapter;
    private LinkedList<HashMap<String, String>> data;
    private String companyName;
    private ArrayList<LinkedTreeMap<String, Object>> sourceData;
    private Handler mHandler;
    private ImageView backKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.credit_level_layout);
        StatusBarUtil.setColor(CreditLevelActivity.this, 0x00E8E8E8, 60);

        Intent intent = getIntent();
        companyName = intent.getStringExtra("companyName");

        data = new LinkedList<>();
        new Thread() {
            @Override
            public void run() {
                sourceData = NetProcess.getCreditLevelInfo(companyName);
                for (int i = 0; i < sourceData.size(); i++) {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("year", "年度：" + sourceData.get(i).get("YEAR"));
                    map.put("province", "省份：" + sourceData.get(i).get("AREA_ID"));
                    map.put("level", "等级：" + sourceData.get(i).get("LEVEL"));
                    map.put("type", "类型：" + sourceData.get(i).get("CATE"));

                    data.add(map);
                }

                mHandler.sendEmptyMessage(0x401);
            }
        }.start();

        creditLevelList = findViewById(R.id.credit_level_listview);
        backKey = findViewById(R.id.back_key);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x401) {
                    adapter = new CreditLevelAdapter(CreditLevelActivity.this, data);
                    creditLevelList.setAdapter(adapter);
                }
            }
        };

        backKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.new_enter_from_top, R.anim.old_exit_to_bottom);
            }
        });
    }
}

package rednum.com.infbigand;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

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
    private ProgressBar progress;

    private String[] areaArray = {"中国", "北京", "安徽", "福建", "甘肃", "广东", "广西", "贵州", "海南", "河北", "河南", "黑龙江", "湖北", "湖南", "吉林", "江苏", "江西", "辽宁", "内蒙古", "宁夏", "青海", "山东", "山西", "陕西", "上海", "四川", "天津", "西藏", "新疆", "云南", "浙江", "重庆", "香港", "澳门", "台湾"};

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
                if (NetProcess.isNetworkAvailable(CreditLevelActivity.this)) {
                    sourceData = NetProcess.getCreditLevelInfo(companyName);
                    if (sourceData != null) {
                        for (int i = 0; i < sourceData.size(); i++) {
                            HashMap<String, String> map = new HashMap<>();
                            map.put("year", "年度：" + sourceData.get(i).get("YEAR"));
                            String m = sourceData.get(i).get("AREA_ID").toString().replace(".0", "");
                            int index = Integer.parseInt(m) - 1;
                            map.put("province", "省份：" + areaArray[index]);
                            map.put("level", "等级：" + sourceData.get(i).get("LEVEL"));
                            map.put("type", "类型：" + sourceData.get(i).get("CATE"));

                            data.add(map);
                        }

                        mHandler.sendEmptyMessage(0x401);
                    } else {
                        mHandler.sendEmptyMessage(0x801);
                    }
                } else {
                    mHandler.sendEmptyMessage(0x901);
                }
            }
        }.start();

        creditLevelList = findViewById(R.id.credit_level_listview);
        backKey = findViewById(R.id.back_key);
        progress = findViewById(R.id.progress);
        progress.setVisibility(View.VISIBLE);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0x401:
                        progress.setVisibility(View.GONE);
                        adapter = new CreditLevelAdapter(CreditLevelActivity.this, data);
                        creditLevelList.setAdapter(adapter);
                        break;

                    case 0x801:
                        Toast.makeText(getApplicationContext(), "数据异常，未获取有效数据", Toast.LENGTH_SHORT).show();

                        break;

                    case 0x901:
                        Toast.makeText(getApplicationContext(), "无网络连接", Toast.LENGTH_SHORT).show();

                        break;
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
            overridePendingTransition(R.anim.new_enter_from_top, R.anim.old_exit_to_bottom);
        }
        return true;
    }
}

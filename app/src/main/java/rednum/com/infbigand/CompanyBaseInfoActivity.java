package rednum.com.infbigand;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
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

public class CompanyBaseInfoActivity extends Activity implements View.OnClickListener {
    private ListView baseInfo;
    private LinkedList<HashMap<String, Object>> data;
    private CompanyBaseInfoAdapter adapter;

    private MemberInfoFragment member_Fragment;
    private ServiceRangeFragment service_Fragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    private ArrayList<String> memberInfoList;
    private String serviceRangeContent;

    private Boolean isMemberShow = true;


    private TextView member_item;
    private TextView service_ranger_item;
    private ImageView backKey;

    private String companyName;
    private LinkedTreeMap<String, String> sourceData;
    private Handler mHandler;

    private LinearLayout mainContent;
    private ProgressBar progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.company_base_info_layout);
        StatusBarUtil.setColor(CompanyBaseInfoActivity.this, 0x00E8E8E8, 60);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0x2001:
                        progress.setVisibility(View.GONE);
                        adapter = new CompanyBaseInfoAdapter(CompanyBaseInfoActivity.this, data);
                        baseInfo.setAdapter(adapter);
                        setDefaultFragment(memberInfoList);
                        break;

                    case 0x801:
                        Toast.makeText(getApplicationContext(), "数据获取异常，未得到有效数据", Toast.LENGTH_SHORT).show();

                        break;

                    case 0x901:
                        Toast.makeText(getApplicationContext(), "无网络连接", Toast.LENGTH_SHORT).show();

                        break;
                }
            }
        };

        Intent intent = getIntent();
        companyName = intent.getStringExtra("companyName");
        baseInfo = findViewById(R.id.company_base_info_listview);
        data = new LinkedList<>();
        memberInfoList = new ArrayList<>();

        new Thread() {
            @Override
            public void run() {
                Log.i("test", "===> 公司名为:" + companyName);
                if (NetProcess.isNetworkAvailable(CompanyBaseInfoActivity.this)) {
                    sourceData = NetProcess.getCompanyBaseInfo(companyName);
                    if (sourceData != null) {
                        data.clear();

                        HashMap<String, Object> item_map_0 = new HashMap<>();
                        item_map_0.put("companyName", companyName);

                        HashMap<String, Object> item_map_1 = new HashMap<>();
                        item_map_1.put("icon", R.mipmap.recognization_code);
                        item_map_1.put("content", "机构代码：" + sourceData.get("jgdm"));

                        HashMap<String, Object> item_map_2 = new HashMap<>();
                        item_map_2.put("icon", R.mipmap.register_city);
                        item_map_2.put("content", "注册省/市：" + sourceData.get("zcarea") + sourceData.get("zccity"));

                        HashMap<String, Object> item_map_3 = new HashMap<>();
                        item_map_3.put("icon", R.mipmap.used_name);
                        item_map_3.put("content", "曾用名：" + sourceData.get("oldname"));

                        HashMap<String, Object> item_map_4 = new HashMap<>();
                        item_map_4.put("icon", R.mipmap.administer_department);
                        item_map_4.put("content", "行政主管部门：" + sourceData.get("xzzgbm"));

                        HashMap<String, Object> item_map_5 = new HashMap<>();
                        item_map_5.put("icon", R.mipmap.license_register_number);
                        item_map_5.put("content", "营业执照注册号：" + sourceData.get("yyzz"));

                        HashMap<String, Object> item_map_6 = new HashMap<>();
                        item_map_6.put("icon", R.mipmap.company_type);
                        item_map_6.put("content", "企业类型：" + sourceData.get("qylx"));

                        HashMap<String, Object> item_map_7 = new HashMap<>();
                        item_map_7.put("icon", R.mipmap.company_nature);
                        item_map_7.put("content", "企业性质：" + sourceData.get("qyxz"));

                        HashMap<String, Object> item_map_8 = new HashMap<>();
                        item_map_8.put("icon", R.mipmap.license_date);
                        item_map_8.put("content", "营业执照日期：" + sourceData.get("yyzzrq"));

                        HashMap<String, Object> item_map_9 = new HashMap<>();
                        item_map_9.put("icon", R.mipmap.credit_code);
                        item_map_9.put("content", "社会信用代码：" + sourceData.get("shxydm"));

                        memberInfoList.clear();
                        memberInfoList.add("法定代表人：" + sourceData.get("fddbr"));
                        memberInfoList.add("法定代表人职称：" + sourceData.get("fddbrzc"));
                        memberInfoList.add("企业负责人：" + sourceData.get("qyfzr"));
                        memberInfoList.add("企业负责人职称：" + sourceData.get("qyfzrzc"));
                        memberInfoList.add("技术负责人：" + sourceData.get("jsfzr"));
                        memberInfoList.add("技术负责人职称：" + sourceData.get("jsfzrzc"));

                        serviceRangeContent = sourceData.get("yyfw");

                        data.add(item_map_0);
                        data.add(item_map_1);
                        data.add(item_map_2);
                        data.add(item_map_3);
                        data.add(item_map_4);
                        data.add(item_map_5);
                        data.add(item_map_6);
                        data.add(item_map_7);
                        data.add(item_map_8);
                        data.add(item_map_9);

                        mHandler.sendEmptyMessage(0x2001);
                    } else {
                        mHandler.sendEmptyMessage(0x801);
                    }
                } else {
                    mHandler.sendEmptyMessage(0x901);
                }


            }
        }.start();


        member_Fragment = new MemberInfoFragment();
        service_Fragment = new ServiceRangeFragment();

        member_item = findViewById(R.id.member_item);
        service_ranger_item = findViewById(R.id.service_range_item);
        backKey = findViewById(R.id.back_key);
        mainContent = findViewById(R.id.maincontent);
        progress = findViewById(R.id.progress);
        progress.setVisibility(View.VISIBLE);

        member_item.setOnClickListener(this);
        service_ranger_item.setOnClickListener(this);
        backKey.setOnClickListener(this);

    }


    private void setDefaultFragment(ArrayList<String> data) {
        fragmentManager = getFragmentManager();
        transaction = fragmentManager.beginTransaction();
        MemberInfoFragment member = new MemberInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("member_info_key", data);
        member.setArguments(bundle);

        transaction.replace(R.id.content, member);
        transaction.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
            overridePendingTransition(R.anim.new_enter_from_right, R.anim.old_exit_to_left);
        }
        return true;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.member_item:
                if (!isMemberShow) {
                    fragmentManager = getFragmentManager();
                    transaction = fragmentManager.beginTransaction();

                    if (member_Fragment == null) {
                        member_Fragment = new MemberInfoFragment();
                    }

                    member_item.setBackgroundResource(R.drawable.shape_background_base_info_left);
                    service_ranger_item.setBackgroundColor(0xE8E8E8);
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("member_info_key", memberInfoList);
                    member_Fragment.setArguments(bundle);
                    transaction.replace(R.id.content, member_Fragment);

                    isMemberShow = true;
                    transaction.commit();
                }
                break;

            case R.id.service_range_item:
                if (isMemberShow) {
                    fragmentManager = getFragmentManager();
                    transaction = fragmentManager.beginTransaction();

                    if (service_Fragment == null) {
                        service_Fragment = new ServiceRangeFragment();
                    }

                    service_ranger_item.setBackgroundResource(R.drawable.shape_background_base_info_right);
                    member_item.setBackgroundColor(0xE8E8E8);
                    Bundle bundle = new Bundle();
                    bundle.putString("service_ranger_key", serviceRangeContent);
                    service_Fragment.setArguments(bundle);
                    transaction.replace(R.id.content, service_Fragment);

                    isMemberShow = false;
                    transaction.commit();
                }
                break;

            case R.id.back_key:
                finish();
                overridePendingTransition(R.anim.new_enter_from_right, R.anim.old_exit_to_left);

                break;
        }


    }
}

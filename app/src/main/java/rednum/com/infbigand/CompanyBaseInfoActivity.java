package rednum.com.infbigand;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.company_base_info_layout);

        baseInfo = findViewById(R.id.company_base_info_listview);
        data = new LinkedList<>();
        memberInfoList = new ArrayList<>();
        fillDataToMemberInfo(memberInfoList);

        setDefaultFragment(memberInfoList);
        serviceRangeContent = "高等级公路工程、桥梁工程建筑的施工，交通安全设施施工及安装（包括公路标志、标线、护栏、隔离栅、防眩板等工程施工及安装），工程机械租赁和修理（特种设备除外），沥青加温设备的研究、制造、安装，普通机械配件及所需工程材料的销售。（依法须经批准的项目，经相关部门批准后方可开展经营活动）。";


        member_Fragment = new MemberInfoFragment();
        service_Fragment = new ServiceRangeFragment();

        member_item = findViewById(R.id.member_item);
        service_ranger_item = findViewById(R.id.service_range_item);

        member_item.setOnClickListener(this);
        service_ranger_item.setOnClickListener(this);

        HashMap<String, Object> item_map_1 = new HashMap<>();
        item_map_1.put("icon", R.mipmap.recognization_code);
        item_map_1.put("content", "机构代码：15610198-3");

        HashMap<String, Object> item_map_2 = new HashMap<>();
        item_map_2.put("icon", R.mipmap.register_city);
        item_map_2.put("content", "注册省/市：福建省泉州市");

        HashMap<String, Object> item_map_3 = new HashMap<>();
        item_map_3.put("icon", R.mipmap.used_name);
        item_map_3.put("content", "曾用名");

        HashMap<String, Object> item_map_4 = new HashMap<>();
        item_map_4.put("icon", R.mipmap.administer_department);
        item_map_4.put("content", "行政主管部门：泉州市国有资产监督管理委员会");

        HashMap<String, Object> item_map_5 = new HashMap<>();
        item_map_5.put("icon", R.mipmap.license_register_number);
        item_map_5.put("content", "营业执照注册号：350500100008942");

        HashMap<String, Object> item_map_6 = new HashMap<>();
        item_map_6.put("icon", R.mipmap.company_type);
        item_map_6.put("content", "企业类型：施工单位");

        HashMap<String, Object> item_map_7 = new HashMap<>();
        item_map_7.put("icon", R.mipmap.company_nature);
        item_map_7.put("content", "企业性质：国有");

        HashMap<String, Object> item_map_8 = new HashMap<>();
        item_map_8.put("icon", R.mipmap.license_date);
        item_map_8.put("content", "营业执照日期：2016-10-24");

        HashMap<String, Object> item_map_9 = new HashMap<>();
        item_map_9.put("icon", R.mipmap.credit_code);
        item_map_9.put("content", "社会信用代码：91350500156101983");

        data.add(item_map_1);
        data.add(item_map_2);
        data.add(item_map_3);
        data.add(item_map_4);
        data.add(item_map_5);
        data.add(item_map_6);
        data.add(item_map_7);
        data.add(item_map_8);
        data.add(item_map_9);

        adapter = new CompanyBaseInfoAdapter(CompanyBaseInfoActivity.this, data);
        baseInfo.setAdapter(adapter);


    }

    private void fillDataToMemberInfo(ArrayList<String> memberInfoList) {
        memberInfoList.add("法定代表人：陈建华");
        memberInfoList.add("法定代表人职称：教授级搞基工程师");
        memberInfoList.add("企业负责人：陈建华");
        memberInfoList.add("企业负责人职称：总经理");
        memberInfoList.add("技术负责人：陈昆明");
        memberInfoList.add("技术负责人职称：总监");
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
    public void onClick(View view) {
        fragmentManager = getFragmentManager();
        transaction = fragmentManager.beginTransaction();

        switch (view.getId()) {
            case R.id.member_item:
                if (!isMemberShow) {
                    if (member_Fragment == null) {
                        member_Fragment = new MemberInfoFragment();
                    }
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("member_info_key", memberInfoList);
                    member_Fragment.setArguments(bundle);
                    transaction.replace(R.id.content, member_Fragment);

                    isMemberShow = true;
                }

                break;

            case R.id.service_range_item:
                if (isMemberShow) {
                    if (service_Fragment == null) {
                        service_Fragment = new ServiceRangeFragment();
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString("service_ranger_key", serviceRangeContent);
                    service_Fragment.setArguments(bundle);
                    transaction.replace(R.id.content, service_Fragment);

                    isMemberShow = false;
                }

                break;
        }

        transaction.commit();
    }
}

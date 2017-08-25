package rednum.com.infbigand;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import rednum.com.infbigand.Net.NetProcess;

/**
 * Created by Administrator on 2017/8/22.
 */

public class CompeteAnalysisActivity extends Activity implements View.OnClickListener {
    private LinearLayout selectProvince;
    private String[] provinceArray = {
            "全部", "北京", "安徽", "福建", "甘肃", "广东", "广西", "贵州", "海南", "河北", "河南", "黑龙江", "湖北", "湖南", "吉林", "江苏",
            "江西", "辽宁", "内蒙古", "宁夏", "青海", "山东", "山西", "陕西", "上海", "四川", "天津", "西藏", "新疆", "云南", "浙江", "重庆"
    };

    private String[] typeArray = {
            "无", "交通部", "项目属地交通厅"
    };
    private String[] creditLevelArray = {
            "AA级", "A级", "A级以上", "B级", "B级以上", "C级", "C级以上"
    };
    private String[] capitutdeType = {
            "勘察企业", "设计企业", "建筑业企业", "工程管理", "招标代理机构", "设计与施工一体化企业"
    };

    private String[] capitudeLevel = {
            "甲级", "乙级", "无"
    };
    private TextView showProvince;
    private TextView showCreditLevel;
    private LinearLayout registerCapital;
    private LinearLayout selectCreditLevel;
    private String typeAndLevel;
    private EditText capitalCash;

    private LinearLayout companyCapitude;
    private ImageView addCompanyCapitude;
    private ImageView addCompanyCapitude2;
    private ImageView addCompanyCapitude3;
    private PopupWindow popupWindow;
    private PopupWindow popupWindow2;
    private PopupWindow popupWindow3;

    private TextView capitude1;
    private TextView capitude2;
    private TextView capitude3;

    private LinearLayout companyCapitude_All_2;
    private LinearLayout companyCapitude_All_3;
    private LinearLayout companyCapitude2;
    private LinearLayout companyCapitude3;

    private LinearLayout bottomLine;

    private View line1, line2;  // 资质中的分割线
    private String selectCondition;

    private ArrayList<String> intellName;
    private ArrayList<String> intellLevel;

    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.compete_analysis_layout);

        selectProvince = findViewById(R.id.select_province);
        selectProvince.setOnClickListener(this);
        showProvince = findViewById(R.id.show_province_textview);
        showCreditLevel = findViewById(R.id.show_credit_level);
        registerCapital = findViewById(R.id.register_capital);
        registerCapital.setOnClickListener(this);
        selectCreditLevel = findViewById(R.id.select_credit_level);
        selectCreditLevel.setOnClickListener(this);
        capitalCash = findViewById(R.id.show_register_capital_textview);
        companyCapitude = findViewById(R.id.company_capitude);
        addCompanyCapitude = findViewById(R.id.add_company_capitude);
        addCompanyCapitude2 = findViewById(R.id.add_company_capitude_2);
        addCompanyCapitude3 = findViewById(R.id.add_company_capitude_3);
        addCompanyCapitude.setOnClickListener(this);
        addCompanyCapitude2.setOnClickListener(this);
        addCompanyCapitude3.setOnClickListener(this);

        capitude1 = findViewById(R.id.show_company_capitude_textview);
        capitude2 = findViewById(R.id.show_company_capitude_textview_2);
        capitude3 = findViewById(R.id.show_company_capitude_textview_3);

        line1 = findViewById(R.id.fill_view);
        line2 = findViewById(R.id.fill_view_2);

        companyCapitude_All_2 = findViewById(R.id.capitude_2);
        companyCapitude_All_3 = findViewById(R.id.capitude_3);
        companyCapitude2 = findViewById(R.id.company_capitude_2);
        companyCapitude3 = findViewById(R.id.company_capitude_3);
        bottomLine = findViewById(R.id.bottom_line);


        intellName = new ArrayList<>();
        intellLevel = new ArrayList<>();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 101:
                        line1.setVisibility(View.VISIBLE);
                        companyCapitude_All_2.setVisibility(View.VISIBLE);

                        break;

                }
            }
        };
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.select_province:
                AlertDialog.Builder builder = new AlertDialog.Builder(CompeteAnalysisActivity.this);
                View provinceSelectedView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.common_listview, null);
                ListView provnceListView = provinceSelectedView.findViewById(R.id.common_listview);

                ArrayAdapter<String> adapter = new ArrayAdapter(getApplicationContext(), R.layout.common_item_selected_layout, R.id.common_listview_item, provinceArray);
                provnceListView.setAdapter(adapter);

                builder.setView(provinceSelectedView);

                final AlertDialog dialog = builder.create();
                provnceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        showProvince.setText(provinceArray[i]);
                        dialog.cancel();
                    }
                });
                dialog.show();


                break;

            case R.id.register_capital:

                break;

            case R.id.select_credit_level:
                AlertDialog.Builder builder_2 = new AlertDialog.Builder(CompeteAnalysisActivity.this);
                View creditLevelSelectView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.common_listview, null);
                ListView creditLevelListView = creditLevelSelectView.findViewById(R.id.common_listview);

                ArrayAdapter<String> adapter_2 = new ArrayAdapter(getApplicationContext(), R.layout.common_item_selected_layout, R.id.common_listview_item, typeArray);
                creditLevelListView.setAdapter(adapter_2);

                builder_2.setTitle("请选择类别");
                builder_2.setView(creditLevelSelectView);

                final AlertDialog dialog_2 = builder_2.create();
                creditLevelListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        typeAndLevel = typeArray[i];
                        dialog_2.cancel();

                        AlertDialog.Builder builder_3 = new AlertDialog.Builder(CompeteAnalysisActivity.this);
                        View creditLevelSelectView_2 = LayoutInflater.from(getApplicationContext()).inflate(R.layout.common_listview, null);
                        ListView creditLevelListView_2 = creditLevelSelectView_2.findViewById(R.id.common_listview);

                        ArrayAdapter<String> adapter_3 = new ArrayAdapter(getApplicationContext(), R.layout.common_item_selected_layout, R.id.common_listview_item, creditLevelArray);
                        creditLevelListView_2.setAdapter(adapter_3);

                        builder_3.setTitle("请选择资质类别");
                        builder_3.setView(creditLevelSelectView_2);

                        final AlertDialog dialog_3 = builder_3.create();
                        creditLevelListView_2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                typeAndLevel += " " + creditLevelArray[i];
                                dialog_3.cancel();

                                showCreditLevel.setText(typeAndLevel);
                            }
                        });

                        dialog_3.show();


                    }
                });
                dialog_2.show();


                break;

            case R.id.add_company_capitude:
                ListView capitudeList = new ListView(getApplicationContext());
                ArrayAdapter<String> capitudeAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.popup_text_item, capitutdeType);
                capitudeList.setAdapter(capitudeAdapter);

                popupWindow = new PopupWindow(capitudeList, companyCapitude.getWidth(), ActionBar.LayoutParams.WRAP_CONTENT, true);
                popupWindow.setFocusable(true);
                popupWindow.setOutsideTouchable(true);

                Drawable drawable = getApplicationContext().getDrawable(R.drawable.popup_bg);
                popupWindow.setBackgroundDrawable(drawable);

                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
//                        popupWindow.dismiss();
                        setBackgroundAlpha(1.0f);
                    }
                });

                capitudeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                        popupWindow.dismiss();
                        capitude1.setText(capitutdeType[i]);
                        new Thread() {
                            @Override
                            public void run() {
                                intellName.clear();
                                intellName = NetProcess.getIntellNameList(capitutdeType[i]);
                                handler.sendEmptyMessage(101);
                            }
                        }.start();
                    }
                });

                popupWindow.showAsDropDown(companyCapitude);
                setBackgroundAlpha(0.5f);


                break;

            case R.id.add_company_capitude_2:
                ListView capitudeName = new ListView(getApplicationContext());
                ArrayAdapter<String> capitudeNameAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.popup_text_item, intellName);
                capitudeName.setAdapter(capitudeNameAdapter);

                int[] location = new int[2];
                addCompanyCapitude2.getLocationOnScreen(location);
                int y = location[1];

                int[] location2 = new int[2];
                bottomLine.getLocationOnScreen(location2);
                int y2 = location2[1];

                if (intellName.size() >= 6) {
                    popupWindow2 = new PopupWindow(capitudeName, companyCapitude.getWidth(), y2 - y - 70, true);
                } else {
                    popupWindow2 = new PopupWindow(capitudeName, companyCapitude.getWidth(), ActionBar.LayoutParams.WRAP_CONTENT, true);
                }


                popupWindow2.setFocusable(true);
                popupWindow2.setOutsideTouchable(true);
                Drawable drawable_2 = getApplicationContext().getDrawable(R.drawable.popup_bg);
                popupWindow2.setBackgroundDrawable(drawable_2);

                capitudeName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        popupWindow2.dismiss();
                        capitude2.setText(intellName.get(i));

                        line2.setVisibility(View.VISIBLE);
                        companyCapitude_All_3.setVisibility(View.VISIBLE);
                    }
                });

                popupWindow2.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        setBackgroundAlpha(1.0f);
                    }
                });
                popupWindow2.setClippingEnabled(false);


                popupWindow2.showAsDropDown(companyCapitude2);
                setBackgroundAlpha(0.5f);

                break;

            case R.id.add_company_capitude_3:

                ListView capitudeLevelListView = new ListView(getApplicationContext());
                ArrayAdapter<String> capitudeLevelAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.popup_text_item, capitudeLevel);
                capitudeLevelListView.setAdapter(capitudeLevelAdapter);

                popupWindow3 = new PopupWindow(capitudeLevelListView, companyCapitude.getWidth(), ActionBar.LayoutParams.WRAP_CONTENT, true);
                popupWindow3.setFocusable(true);
                popupWindow3.setOutsideTouchable(true);
                Drawable drawable_3 = getApplicationContext().getDrawable(R.drawable.popup_bg);
                popupWindow3.setBackgroundDrawable(drawable_3);

                capitudeLevelListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        intellLevel.clear();





                        popupWindow3.dismiss();
                        capitude3.setText(capitudeLevel[i]);
                    }
                });

                popupWindow3.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        setBackgroundAlpha(1.0f);
                    }
                });
                popupWindow3.setClippingEnabled(false);


                popupWindow3.showAsDropDown(companyCapitude3);
                setBackgroundAlpha(0.5f);


                break;
        }
    }

    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = CompeteAnalysisActivity.this.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        CompeteAnalysisActivity.this.getWindow().setAttributes(lp);
    }
}

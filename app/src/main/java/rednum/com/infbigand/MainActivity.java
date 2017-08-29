package rednum.com.infbigand;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;

import rednum.com.infbigand.Net.NetProcess;
import rednum.com.infbigand.UI.FlowLayout;

public class MainActivity extends Activity implements View.OnClickListener {
    private ArrayList<String> hot_search_company = new ArrayList<>();
    private ArrayList<String> hot_search_project = new ArrayList<>();

    private String[] textBackgrounds = {
            "#FF6EB4", "#EE2C2C", "#8968CD", "#EE3A8C", "#CD8500", "#66CD00", "#8B7D6B", "#7B68EE", "#00868B", "#A0522D"
    };

    private FlowLayout hotCompany;
    private FlowLayout hotProject;

    private TextView searchCompany;
    private TextView searchProject;

    private EditText editCompany;
    private EditText editProject;

    private Handler handler;
    private LinkedList<TextView> companyTextViews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Window window = getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }
        setContentView(R.layout.activity_main);

        hotCompany = findViewById(R.id.hot_company_search);
        hotProject = findViewById(R.id.hot_project_search);
        searchCompany = findViewById(R.id.search_company);
        searchProject = findViewById(R.id.search_project);
        editCompany = findViewById(R.id.company_name);
        editProject = findViewById(R.id.project_name);

        hot_search_company.add("中铁十局集团有限公司");
        hot_search_company.add("中交一公局桥隧工程有限公司");
        hot_search_company.add("道隧集团工程有限公司");

        hot_search_project.add("雅安至康定");
        hot_search_project.add("汶川至马尔康");
        hot_search_project.add("河池至百色公路");
        hot_search_project.add("阳朔至鹿寨公路");


        companyTextViews = new LinkedList<>();

        searchCompany.setOnClickListener(this);
        searchProject.setOnClickListener(this);

        setFlowLayoutProperty(hotCompany, hot_search_company);
        setFlowLayoutProperty(hotProject, hot_search_project);


        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 101:

                        removeFlowLayoutSubview(hotCompany);
                        setFlowLayoutProperty(hotCompany, hot_search_company);

                        break;

                    case 102:
                        removeFlowLayoutSubview(hotProject);
                        setFlowLayoutProperty(hotProject, hot_search_project);

                }
            }
        };
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private void setFlowLayoutProperty(final FlowLayout flowLayout, ArrayList<String> data) {
        TextView tv;
        companyTextViews.clear();
        for (int i = 0; i < data.size(); i++) {
            int ranHeight = dip2px(this, 30);
            ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ranHeight);
            lp.setMargins(dip2px(this, 3), 0, dip2px(this, 3), 0);
            tv = new TextView(this);
            tv.setOnClickListener(new TextViewOnClickListener());
            tv.setPadding(dip2px(this, 10), 0, dip2px(this, 10), 0);
            tv.setTextColor(Color.parseColor("#FFFFFF"));
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            tv.setText(data.get(i));//设置显示的数据
            tv.setGravity(Gravity.CENTER_VERTICAL);
            tv.setLines(1);
            tv.setBackgroundResource(R.drawable.bg_tag);
            String rgb = textBackgrounds[(int) (Math.random() * textBackgrounds.length)];
            tv.setBackgroundResource(R.drawable.bg_tag);
            GradientDrawable myGrad = (GradientDrawable) tv.getBackground();
            myGrad.setColor(Color.parseColor(rgb));
            tv.setBackgroundDrawable(myGrad);
            flowLayout.addView(tv, lp);
            flowLayout.relayoutToCompress();

            final String content = tv.getText().toString();
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), CompanyInfoShowActivity.class);
                    if (flowLayout == hotCompany) {
                        intent.putExtra("url", "http://221.237.189.104:8080/titanweb/SolrTitanAction?method=name&company=" + content);
                    } else if (flowLayout == hotProject) {
                        intent.putExtra("url", "http://221.237.189.104:8080/titanweb/SolrTitanAction?method=load&project=" + content);
                    }


                    Log.i("intent", "=====> 开始跳转");
                    startActivity(intent);
                    overridePendingTransition(R.anim.new_enter_from_alpha, R.anim.old_exit_to_bottom);

                }
            });


            companyTextViews.add(tv);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_company:
                new Thread() {
                    @Override
                    public void run() {
                        String companyName = editCompany.getText().toString();
                        if (companyName != null && !"".equals(companyName)) {
                            hot_search_company.clear();
                            hot_search_company = NetProcess.searchSpecifiedCompany(companyName);

                            handler.sendEmptyMessage(101);
                        }
                    }
                }.start();


                break;

            case R.id.search_project:
                new Thread() {
                    @Override
                    public void run() {
                        String projectName = editProject.getText().toString();
                        if (projectName != null && !"".equals(projectName)) {
                            hot_search_project.clear();
                            hot_search_project = NetProcess.searchSpecifiedProject(projectName);

                            handler.sendEmptyMessage(102);
                        }
                    }
                }.start();


                break;
        }
    }

    class TextViewOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

        }
    }

    private void removeFlowLayoutSubview(FlowLayout flowLayout) {
        for (int i = 0; i < companyTextViews.size(); i++) {
            flowLayout.removeView(companyTextViews.get(i));
        }
    }


}

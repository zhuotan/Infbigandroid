package rednum.com.infbigand;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import rednum.com.infbigand.UI.FlowLayout;

public class MainActivity extends Activity {
    private String[] hot_search_company = new String[]{
            "中交一公局第三工程有限公司", "陕西路桥集团有限公司", "齐齐哈尔鑫海路桥（集团）有限公司", "青建集团股份公司", "西部中大建设集团股份有限公司"
    };

    private String[] hot_search_project = {
            "俄罗沟桥维修加固工程", "国道213县郎木寺至川主寺公路养护工程（K615+500~K615+600边沟改建工程）", "乃托镇白石村通达工程", "格西桥新建工程勘察设计",
            "成乐高速公路涵洞加固工作勘察设计"
    };

//    private String[] textBackgrounds = {
//            "#FF6EB4", "#EE2C2C", "#8968CD", "#EE3A8C", "#CD8500", "#66CD00", "#8B7D6B", "#7B68EE", "#00868B", "#A0522D"
//    };

    private FlowLayout hotCompany;
    private FlowLayout hotProject;


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

        setFlowLayoutProperty(hotCompany, hot_search_company);
        setFlowLayoutProperty(hotProject, hot_search_project);
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private void setFlowLayoutProperty(FlowLayout flowLayout, String[] data) {
        TextView tv;
        for (int i = 0; i < data.length; i++) {
            int ranHeight = dip2px(this, 30);
            ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ranHeight);
            lp.setMargins(dip2px(this, 3), 0, dip2px(this, 3), 0);
            tv = new TextView(this);
            tv.setOnClickListener(new TextViewOnClickListener());
            tv.setPadding(dip2px(this, 15), 0, dip2px(this, 15), 0);
            tv.setTextColor(Color.parseColor("#FFFFFF"));
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            tv.setText(data[i]);
            tv.setGravity(Gravity.CENTER_VERTICAL);
            tv.setLines(1);
            tv.setBackgroundResource(R.drawable.bg_tag);
//            String rgb = textBackgrounds[(int) (Math.random() * textBackgrounds.length)];
            tv.setBackgroundResource(R.drawable.bg_tag);
            GradientDrawable myGrad = (GradientDrawable) tv.getBackground();
            myGrad.setColor(Color.parseColor("#B5B5B5"));
            tv.setBackgroundDrawable(myGrad);
            flowLayout.addView(tv, lp);
            flowLayout.relayoutToCompress();
        }
    }

    class TextViewOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

        }
    }


}

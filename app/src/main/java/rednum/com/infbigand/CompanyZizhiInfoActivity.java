package rednum.com.infbigand;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import rednum.com.infbigand.Net.NetProcess;
import rednum.com.infbigand.System.StatusBarUtil;

/**
 * Created by Administrator on 2017/9/7.
 */

public class CompanyZizhiInfoActivity extends Activity {
    private ListView zizhiListView;
    private CompanyZizhiInfoAdapter adapter;
    private ArrayList<String> data;
    private String companyName;
    private ImageView backKey;

    private Handler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.company_zizhi_info_activity);
        StatusBarUtil.setColor(CompanyZizhiInfoActivity.this, 0x00E8E8E8, 60);

        Intent intent = getIntent();
        companyName = intent.getStringExtra("companyName");
        zizhiListView = findViewById(R.id.company_zizhi_listview);
        backKey = findViewById(R.id.back_key);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x3001) {
                    adapter = new CompanyZizhiInfoAdapter(CompanyZizhiInfoActivity.this, data);
                    zizhiListView.setAdapter(adapter);
                }
            }
        };

        new Thread() {
            @Override
            public void run() {
                data = NetProcess.getCompanyZizhiInfo(companyName);
                mHandler.sendEmptyMessage(0x3001);
            }
        }.start();

        backKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.new_enter_from_left, R.anim.old_exit_to_right);
            }
        });
    }
}

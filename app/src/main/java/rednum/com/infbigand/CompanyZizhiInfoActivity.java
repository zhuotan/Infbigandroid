package rednum.com.infbigand;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

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
    private ProgressBar progress;

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
        progress = findViewById(R.id.progress);
        progress.setVisibility(View.VISIBLE);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0x3001:
                        progress.setVisibility(View.GONE);
                        adapter = new CompanyZizhiInfoAdapter(CompanyZizhiInfoActivity.this, data);
                        zizhiListView.setAdapter(adapter);

                        break;

                    case 0x801:
                        Toast.makeText(getApplicationContext(), "数据获取异常，未获取有效数据", Toast.LENGTH_SHORT).show();

                        break;

                    case 0x901:
                        Toast.makeText(getApplicationContext(), "无网络连接", Toast.LENGTH_SHORT).show();

                        break;
                }
            }
        };

        new Thread() {
            @Override
            public void run() {
                if (NetProcess.isNetworkAvailable(CompanyZizhiInfoActivity.this)) {
                    data = NetProcess.getCompanyZizhiInfo(companyName);
                    if (data != null) {
                        mHandler.sendEmptyMessage(0x3001);
                    } else {
                        mHandler.sendEmptyMessage(0x801);
                    }
                } else {
                    mHandler.sendEmptyMessage(0x901);
                }

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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
            overridePendingTransition(R.anim.new_enter_from_left, R.anim.old_exit_to_right);
        }
        return true;
    }
}

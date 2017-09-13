package rednum.com.infbigand;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.webkit.JavascriptInterface;

import rednum.com.infbigand.System.StatusBarUtil;

/**
 * Created by Administrator on 2017/8/29.
 */

public class CompanyInfoShowActivity extends Activity {
    private WebView webView;
    private ProgressBar progressBar;
    private ImageView back;
    private Handler mHandler;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.company_info_show_layout);
        StatusBarUtil.setColor(CompanyInfoShowActivity.this, 0x00E8E8E8, 60);

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");

        Log.i("url", "===>传递过来的地址为:" + url);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0x101:
                        String companyName = msg.getData().getString("companyName");

                        Intent intent = new Intent(getApplicationContext(), CompanyBaseInfoActivity.class);
                        intent.putExtra("companyName", companyName);
                        startActivity(intent);
                        overridePendingTransition(R.anim.new_enter_from_left, R.anim.old_exit_to_right);

                        break;

                    case 0x102:
                        String companyName2 = msg.getData().getString("companyName");

                        Intent intent2 = new Intent(getApplicationContext(), CompanyZizhiInfoActivity.class);
                        intent2.putExtra("companyName", companyName2);
                        startActivity(intent2);
                        overridePendingTransition(R.anim.new_enter_from_right, R.anim.old_exit_to_left);

                        break;

                    case 0x103:
                        String companyName3 = msg.getData().getString("companyName");

                        Intent intent3 = new Intent(getApplicationContext(), CreditLevelActivity.class);
                        intent3.putExtra("companyName", companyName3);
                        startActivity(intent3);
                        overridePendingTransition(R.anim.new_enter_from_bottom, R.anim.old_exit_to_top);

                        break;
                }
            }
        };

        webView = findViewById(R.id.webview);
        progressBar = findViewById(R.id.progress);
        back = findViewById(R.id.back_to_mainactivity);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.new_enter_from_alpha, R.anim.old_exit_to_alpha);
            }
        });

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setJavaScriptEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setDomStorageEnabled(true);
        settings.setSupportZoom(true); // 可以缩放
        settings.setBuiltInZoomControls(true); // 显示放大缩小 controler
//        settings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);// 默认缩放模式
        settings.setUseWideViewPort(true);  //为图片添加放大缩小功能

        webView.addJavascriptInterface(new JavaScriptinterface(this), "android");

        Log.i("address", "即将跳转到===>" + url);
//        webView.loadUrl("file:///android_asset/jsfile.html");  // 目录名只能是android_test，而不是是assets

        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO Auto-generated method stub
                // 当页面中出现内容时就可以取消Progressbar的显示，故设置进度值为70而非100
                if (newProgress <= 70) {
                    // 加载中
                    Log.i("web", "网页正在加载中");
                    progressBar.setVisibility(View.VISIBLE);

                } else {
                    progressBar.setVisibility(View.GONE);
                }

            }
        });


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
            overridePendingTransition(R.anim.new_enter_from_alpha, R.anim.old_exit_to_alpha);
        }
        return true;
    }

    class JavaScriptinterface {
        Context context;

        public JavaScriptinterface(Context c) {
            context = c;
        }

        /**
         * 与js交互时用到的方法，在js里直接调用的
         */
        @JavascriptInterface
        public void showCompanyInfo(String companyName) {

            Message message = Message.obtain();
            message.what = 0x101;
            Bundle bundle = new Bundle();
            bundle.putString("companyName", companyName);

            message.setData(bundle);
            mHandler.sendMessage(message);
        }

        @JavascriptInterface
        public void showZizhiInfo(String companyName) {
            Message message = Message.obtain();
            message.what = 0x102;
            Bundle bundle = new Bundle();
            bundle.putString("companyName", companyName);

            message.setData(bundle);
            mHandler.sendMessage(message);
        }

        @JavascriptInterface
        public void showCreditLevelInfo(String companyName) {
            Message message = Message.obtain();
            message.what = 0x103;
            Bundle bundle = new Bundle();
            bundle.putString("companyName", companyName);

            message.setData(bundle);
            mHandler.sendMessage(message);
        }
    }
}

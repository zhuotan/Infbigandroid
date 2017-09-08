package rednum.com.infbigand;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;

import rednum.com.infbigand.System.StatusBarUtil;

public class ProjectTopoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_project_topo);
        StatusBarUtil.setColor(ProjectTopoActivity.this, 0x00E8E8E8, 60);
        WebView webview = new WebView(this);
        //Enable JavaScript support
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl("http://221.237.189.104:8080/titanweb/SearchAction?method=load&project=%E9%98%B3%E6%9C%94%E8%87%B3%E9%B9%BF%E5%AF%A8%E5%85%AC%E8%B7%AF&index=0&link=special");
        setContentView(webview);
    }
}

package rednum.com.infbigand;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Administrator on 2017/8/28.
 */

public class CompeteAnalysisResultActivity extends Activity implements View.OnClickListener {
    private ListView resultListview;
    private CompeteAnalysisResultAdapter adapter;
    private LinkedList<HashMap<String, String>> data;
    private ImageView backToQuery;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compete_analysis_result_layout);

        ContextApplication application = (ContextApplication) getApplication();
        data = application.getData();

        backToQuery = findViewById(R.id.back_to_query);
        backToQuery.setOnClickListener(this);

        resultListview = findViewById(R.id.compete_analysis_result_listview);
        adapter = new CompeteAnalysisResultAdapter(CompeteAnalysisResultActivity.this, data);


        resultListview.setAdapter(adapter);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_to_query:
                finish();
                overridePendingTransition(R.anim.new_enter_from_alpha, R.anim.old_exit_from_top);
                break;
        }
    }
}

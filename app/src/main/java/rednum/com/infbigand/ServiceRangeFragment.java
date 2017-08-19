package rednum.com.infbigand;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/8/19.
 */

public class ServiceRangeFragment extends Fragment {
    private String data;
    private Bundle bundle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
        data = bundle.getString("service_ranger_key");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.service_range_layout, null);
        TextView serviceRangeText = view.findViewById(R.id.service_range_text);
        serviceRangeText.setText(data);

        return view;
    }
}

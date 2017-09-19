package rednum.com.infbigand;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import rednum.com.infbigand.Security.En_Dn_crypt;

/**
 * Created by Administrator on 2017/9/19.
 */

public class TestJNIActivity extends Activity {
    private EditText input, inputPri;
    private Button confirm, confirmPri;
    private TextView output, outputPri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jni_test_layout);

        input = findViewById(R.id.input);
        confirm = findViewById(R.id.confirm);
        output = findViewById(R.id.output);

        inputPri = findViewById(R.id.inputPri);
        confirmPri = findViewById(R.id.confirmPri);
        outputPri = findViewById(R.id.outputPri);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = input.getText().toString();
                String encryption = En_Dn_crypt.encode(content.toCharArray());

//                String priStr = new String(encryption);

                output.setText(encryption);
            }
        });


        confirmPri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = inputPri.getText().toString();
                String decryption = En_Dn_crypt.decode(content.toCharArray());

                outputPri.setText(decryption);
            }
        });
    }
}

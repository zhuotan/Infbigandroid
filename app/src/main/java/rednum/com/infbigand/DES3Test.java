package rednum.com.infbigand;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import rednum.com.infbigand.Security.AndroidDes3Util;

/**
 * Created by Administrator on 2017/9/21.
 */

public class DES3Test extends Activity {
    private EditText editEncrypt;
    private Button encrypt, decrypt;
    private TextView afterEncrypt, afterDecrypt;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.des_3_test);


        editEncrypt = findViewById(R.id.encrypt);
        encrypt = findViewById(R.id.encrypt_button);
        decrypt = findViewById(R.id.decrypt_button);
        afterEncrypt = findViewById(R.id.after_encrypt);
        afterDecrypt = findViewById(R.id.after_decrypt);

        encrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String before = editEncrypt.getText().toString();

                try {
                    afterEncrypt.setText(AndroidDes3Util.encode(before));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String after = afterEncrypt.getText().toString();
                try {
                    afterDecrypt.setText(AndroidDes3Util.decode("iQjrK1WBSYAk4RxBsiZTJw=="));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}

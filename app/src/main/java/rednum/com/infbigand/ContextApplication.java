package rednum.com.infbigand;

import android.app.Application;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Administrator on 2017/8/28.
 */

public class ContextApplication extends Application {
    private LinkedList<HashMap<String, String>> data;

    public void setData(LinkedList<HashMap<String, String>> data) {
        this.data = data;
    }

    public LinkedList<HashMap<String, String>> getData() {
        return this.data;
    }

}

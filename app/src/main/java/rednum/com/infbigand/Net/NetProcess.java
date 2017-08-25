package rednum.com.infbigand.Net;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.CookieStore;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Administrator on 2017/8/25.
 */

public class NetProcess {

    public static LinkedList<HashMap<String, String>> getViewPagerData() {
        LinkedList<HashMap<String, String>> viewPagerData = new LinkedList<>();
        HashMap<String, String> map;
        String url = "http://rednum.cn/ViewListAction?method=init&classfyid=1";
        Document doc = null;

        try {
            Connection conn = Jsoup.connect(url);
            conn.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");

            doc = conn.get();

            Elements els = doc.select("#carousel-example-generic > div > div");

            for (Element tr : els) {
                map = new HashMap<>();
                String itemURL = tr.select("a").attr("href");
                String picURL = "http://rednum.cn/" + tr.select("a > img").attr("src");
                map.put("pageURL", itemURL);
                map.put("pic", picURL);

                viewPagerData.add(map);
            }

            return viewPagerData;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    // 获取首页Json字符串
    public static String getHomePage() {
        //http://rednum.cn/ViewListAction?method=pagex&page=1&title=&classfyid=1
        String url = "http://rednum.cn/ViewListAction?method=pagex&page=1&title=&classfyid=1";
        Document doc = null;

        try {
            Connection conn = Jsoup.connect(url);
            conn.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");

            Log.i("list", "doc=" + doc);
            doc = conn.get();

            return doc.select("body").text();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    // 获取item上的图片数据
    public static Bitmap getPicture(String urlStr) {
        Bitmap bitmap;
        try {
//            URL url = new URL("http://rednum.cn:8080/images/20170703172630584.jpg");
            URL url = new URL(urlStr);
            InputStream is = url.openStream();
            bitmap = BitmapFactory.decodeStream(is);

            is.close();

            return bitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    // 判断网络的连接情况
    public static boolean isNetworkAvailable(Activity activity) {
        Context context = activity.getApplicationContext();
        // 获得手机所有的连接管理对象
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm == null)
            return false;
        else {   // 获取所有NetworkInfo对象
            NetworkInfo[] networkInfo = cm.getAllNetworkInfo();
            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++)
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED)
                        return true;  // 存在可用的网络连接
            }
        }
        return false;
    }

    public static boolean isUseMobile(Activity activity) {
        Context context = activity.getApplicationContext();
        // 获得手机所有的连接管理对象
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm == null)
            return false;
        else {   // 获取所有NetworkInfo对象
            NetworkInfo[] networkInfo = cm.getAllNetworkInfo();
            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++)
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED && networkInfo[i].getType() == ConnectivityManager.TYPE_MOBILE) {
                        return true;
                    }
            }
        }
        return false;
    }

    //==================================================================================================================================
    public static ArrayList<String> getIntellNameList(String type) {
        ArrayList<String> returnData = new ArrayList<>();

        String url = "http://192.168.1.84:8080/suitangmap/CptAnalysisAction?method=getIntellType&company_type=" + type;
        Document doc = null;

        try {
            Connection conn = Jsoup.connect(url);
            conn.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");

            doc = conn.get();
            String str = doc.select("body").text();
            Gson gson = new Gson();

            ArrayList list = gson.fromJson(str, ArrayList.class);

            for (int i = 0; i < list.size(); i++) {
                LinkedTreeMap<String, String> map = (LinkedTreeMap<String, String>) list.get(i);
                String content = map.get("INTELLIGENCE");
                returnData.add(content);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return returnData;

    }

    public static ArrayList<String> getIntellLevelList(String type, String name) {
        ArrayList<String> returnData = new ArrayList<>();

        String url = "http://192.168.1.84:8080/suitangmap/CptAnalysisAction?method=getIntellType&company_type=" + type + "&intelligence=" + name;
        Document doc = null;

        try {
            Connection conn = Jsoup.connect(url);
            conn.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");

            doc = conn.get();
            String str = doc.select("body").text();

            Log.i("str", "str=" + str);
//            Gson gson = new Gson();
//
//            ArrayList list = gson.fromJson(str, ArrayList.class);
//
//            for (int i = 0; i < list.size(); i++) {
//                LinkedTreeMap<String, String> map = (LinkedTreeMap<String, String>) list.get(i);
//                String content = map.get("INTELLIGENCE");
//                returnData.add(content);
//            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return returnData;

    }

}


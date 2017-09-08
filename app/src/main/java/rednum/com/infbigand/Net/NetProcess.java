package rednum.com.infbigand.Net;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

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
import java.util.List;

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

            Gson gson = new Gson();

            ArrayList list = gson.fromJson(str, ArrayList.class);

            for (int i = 0; i < list.size(); i++) {
                LinkedTreeMap<String, String> map = (LinkedTreeMap<String, String>) list.get(i);
                String content = map.get("INTELLIGENCE_GRADE");
                returnData.add(content);
            }

            Log.i("str", "data=" + returnData);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return returnData;

    }

    public static LinkedList<HashMap<String, String>> requestQeuryData(HashMap<String, String> getRequestHead) {
        LinkedList<HashMap<String, String>> returnData = new LinkedList<>();

        String url = "http://192.168.1.84:8080/suitangmap/CptAnalysisAction?method=GetInfo&area_id=" + (getRequestHead.get("area_id") == null ? "" : getRequestHead.get("area_id")) + "&beian=" + (getRequestHead.get("beian") == null ? "" : getRequestHead.get("beian")) + "&xinyong=" + (getRequestHead.get("xinyong") == null ? "" : getRequestHead.get("xinyong")) +
                "&zizhi=" + (getRequestHead.get("zizhi") == null ? "" : getRequestHead.get("zizhi")) + "&zijin=" + (getRequestHead.get("zijin") == null ? "" : getRequestHead.get("zijin")) + "&level=" + (getRequestHead.get("level") == null ? "" : getRequestHead.get("level"));
        Document doc = null;

        Log.i("url", "url=" + url);
        try {
            Log.i("content", "=====> 开始联网");
            Connection conn = Jsoup.connect(url);
            conn.timeout(60000);
            conn.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");

            doc = conn.get();
            String str = doc.select("body").text();

            Gson gson = new Gson();
            List<LinkedTreeMap<String, String>> list = gson.fromJson(str, List.class);

            for (int i = 0; i < list.size(); i++) {
                LinkedTreeMap<String, String> map = list.get(i);
                String companyName = map.get("COMPANY_NAME");
                String province = map.get("ADDRESS");
                String creditLevel = map.get("level");
                String beian = map.get("company_type");
                String intell = map.get("INTELLIGENCE");

                HashMap<String, String> data = new HashMap<>();
                data.put("companyName", companyName);
                data.put("province", province);
                data.put("creditLevel", creditLevel);
                data.put("beian", beian);
                data.put("intell", intell);

                returnData.add(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnData;
    }

    public static ArrayList<String> searchSpecifiedCompany(String company) {
        ArrayList<String> returnData;

        String url = "http://221.237.189.104:8080/titanweb/SolrTitanAction?method=nameSearch&name=" + company;
        Document doc = null;

        Log.i("company", "url=" + url);
        try {
            Log.i("content", "=====> 开始搜索公司");
            Connection conn = Jsoup.connect(url);
            conn.timeout(5000);
            conn.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");

            doc = conn.get();
            String str = doc.select("body").text();

            Gson gson = new Gson();
            returnData = gson.fromJson(str, ArrayList.class);

            return returnData;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<String> searchSpecifiedProject(String project) {
        ArrayList<String> returnData;

        String url = "http://221.237.189.104:8080/titanweb/SolrTitanAction?method=projectSearch&name=" + project;
        Document doc = null;

        Log.i("company", "url=" + url);
        try {
            Log.i("content", "=====> 开始搜索项目");
            Connection conn = Jsoup.connect(url);
            conn.timeout(5000);
            conn.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");

            doc = conn.get();
            String str = doc.select("body").text();

            Gson gson = new Gson();
            returnData = gson.fromJson(str, ArrayList.class);

            Log.i("project", "returnData=" + returnData);


            return returnData;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static LinkedTreeMap<String, String> getCompanyBaseInfo(String company) {
        LinkedTreeMap<String, String> returnData;

        String url = "http://192.168.1.84:8080/titanweb/SearchAction?method=baseInfo&company=" + company;
        Document doc = null;

        Log.i("company", "url=" + url);
        try {
            Log.i("company", "=====> 联网获取公司信息");
            Connection conn = Jsoup.connect(url);
            conn.timeout(5000);
            conn.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");

            doc = conn.get();
            String str = doc.select("body").text();

            Gson gson = new Gson();
            returnData = gson.fromJson(str, LinkedTreeMap.class);

            return returnData;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static ArrayList<String> getCompanyZizhiInfo(String company) {
        ArrayList<String> returnData;

        String url = "http://192.168.1.84:8080/titanweb/SearchAction?method=zizhiInfo&company=" + company;
        Document doc = null;

        Log.i("company", "url=" + url);
        try {
            Log.i("zizhi", "=====> 联网获取资质信息");
            Connection conn = Jsoup.connect(url);
            conn.timeout(5000);
            conn.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");

            doc = conn.get();
            String str = doc.select("body").text();

            Gson gson = new Gson();
            returnData = gson.fromJson(str, ArrayList.class);

            return returnData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<LinkedTreeMap<String, Object>> getCreditLevelInfo(String company) {
        ArrayList<LinkedTreeMap<String, Object>> returnData;

        String url = "http://192.168.1.84:8080/titanweb/SearchAction?method=evalInfo&company=" + company;
        Document doc = null;

        Log.i("company", "url=" + url);
        try {
            Log.i("level", "=====> 联网获取资质信息");
            Connection conn = Jsoup.connect(url);
            conn.timeout(5000);
            conn.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");

            doc = conn.get();
            String str = doc.select("body").text();
            Gson gson = new Gson();
            returnData = gson.fromJson(str, ArrayList.class);
            return returnData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}


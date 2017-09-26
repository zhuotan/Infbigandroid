package rednum.com.infbigand.Net;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import rednum.com.infbigand.Security.AndroidDes3Util;

/**
 * Created by Administrator on 2017/8/25.
 */

public class NetProcess {

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


    public static ArrayList<String> getIntellNameList(String type) {
        ArrayList<String> returnData = new ArrayList<>();

        String url = null;
        try {
            url = "http://221.237.189.104:8080/suitangmap/CptAnalysisAction?method=getIntellType&company_type=" + type + "&sign=" + AndroidDes3Util.encode(String.valueOf(getCurrentTimeStamp()));

            Document doc = null;

            Connection conn = Jsoup.connect(url);
            conn.timeout(60000);
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnData;
    }

    public static ArrayList<String> getIntellLevelList(String type, String name) {
        ArrayList<String> returnData = new ArrayList<>();

        String url = null;
        try {
            url = "http://221.237.189.104:8080/suitangmap/CptAnalysisAction?method=getIntellType&company_type=" + type + "&intelligence=" + name + "&sign=" + AndroidDes3Util.encode(String.valueOf(getCurrentTimeStamp()));

            Document doc = null;
            Connection conn = Jsoup.connect(url);
            conn.timeout(60000);
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
        } catch (Exception e) {
            e.printStackTrace();
        }


        return returnData;

    }

    public static LinkedList<HashMap<String, String>> requestQeuryData(HashMap<String, String> getRequestHead) {
        LinkedList<HashMap<String, String>> returnData = new LinkedList<>();

        String url = null;
        try {
            url = "http://221.237.189.104:8080/suitangmap/CptAnalysisAction?method=GetInfo&area_id=" + (getRequestHead.get("area_id") == null ? "" : getRequestHead.get("area_id")) + "&beian=" + (getRequestHead.get("beian") == null ? "" : getRequestHead.get("beian")) + "&xinyong=" + (getRequestHead.get("xinyong") == null ? "" : getRequestHead.get("xinyong")) +
                    "&zizhi=" + (getRequestHead.get("zizhi") == null ? "" : getRequestHead.get("zizhi")) + "&zijin=" + (getRequestHead.get("zijin") == null ? "" : getRequestHead.get("zijin")) + "&level=" + (getRequestHead.get("level") == null ? "" : getRequestHead.get("level")) + "&sign=" + AndroidDes3Util.encode(String.valueOf(getCurrentTimeStamp()));

            Document doc = null;

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


        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnData;
    }

    /**
     * 搜索公司的方法
     */
    public static ArrayList<String> searchSpecifiedCompany(String company, int index) {
        ArrayList<String> returnData;
        String url = null;
        try {
            //PC端访问：http://221.237.189.104:8080/titanweb/SolrTitanAction?method=nameSearch&name=%E4%B8%AD%E9%93%81&index=0
            //MO端访问：http://221.237.189.104:8080/titanweb/SolrTitanAction?method=nameSearch&name=中铁&index=0&sign=0b+BdHRAyKGdaez8g+uxCA==
            url = "http://221.237.189.104:8080/titanweb/SolrTitanAction?method=nameSearch&name=" + company + "&index=" + index + "&sign=" + AndroidDes3Util.encode(String.valueOf(getCurrentTimeStamp()));
            Document doc = null;

            try {
                Connection conn = Jsoup.connect(url);
                conn.timeout(60000);
                conn.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");

                doc = conn.get();
                String str = doc.select("body").text();

                Gson gson = new Gson();
                returnData = gson.fromJson(str, ArrayList.class);

                return returnData;

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<String> searchSpecifiedProject(String project, int index) {
        ArrayList<String> returnData;

        try {
            String url = "http://221.237.189.104:8080/titanweb/SolrTitanAction?method=projectSearch&name=" + project + "&index=" + index + "&sign=" + AndroidDes3Util.encode(String.valueOf(getCurrentTimeStamp()));

            Document doc = null;

            try {
                Connection conn = Jsoup.connect(url);
                conn.timeout(60000);
                conn.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");

                doc = conn.get();
                String str = doc.select("body").text();

                Gson gson = new Gson();
                returnData = gson.fromJson(str, ArrayList.class);

                return returnData;

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    // 获取公司相关信息的方法
    public static LinkedTreeMap<String, String> getCompanyBaseInfo(String company) {
        LinkedTreeMap<String, String> returnData;

        String url = null;
        try {
            url = "http://221.237.189.104:8080/titanweb/SearchAction?method=baseInfo&company=" + company + "&sign=" + AndroidDes3Util.encode(String.valueOf(getCurrentTimeStamp()));

            Document doc = null;

            Connection conn = Jsoup.connect(url);
            conn.timeout(60000);
            conn.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");

            doc = conn.get();
            String str = doc.select("body").text();

            Gson gson = new Gson();
            returnData = gson.fromJson(str, LinkedTreeMap.class);

            return returnData;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public static ArrayList<String> getCompanyZizhiInfo(String company) {
        ArrayList<String> returnData;

        String url = null;
        try {
            url = "http://221.237.189.104:8080/titanweb/SearchAction?method=zizhiInfo&company=" + company + "&sign=" + AndroidDes3Util.encode(String.valueOf(getCurrentTimeStamp()));

            Document doc = null;

            Connection conn = Jsoup.connect(url);
            conn.timeout(60000);
            conn.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");

            doc = conn.get();
            String str = doc.select("body").text();

            Gson gson = new Gson();
            returnData = gson.fromJson(str, ArrayList.class);

            return returnData;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ArrayList<LinkedTreeMap<String, Object>> getCreditLevelInfo(String company) {
        ArrayList<LinkedTreeMap<String, Object>> returnData;

        String url = null;
        try {
            url = "http://221.237.189.104:8080/titanweb/SearchAction?method=evalInfo&company=" + company + "&sign=" + AndroidDes3Util.encode(String.valueOf(getCurrentTimeStamp()));

            Document doc = null;

            Connection conn = Jsoup.connect(url);
            conn.timeout(60000);
            conn.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");

            doc = conn.get();
            String str = doc.select("body").text();
            Gson gson = new Gson();
            returnData = gson.fromJson(str, ArrayList.class);
            return returnData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static long getCurrentTimeStamp() {
        return System.currentTimeMillis();
    }
}


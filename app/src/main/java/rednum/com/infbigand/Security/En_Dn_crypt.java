package rednum.com.infbigand.Security;

/**
 * Created by Administrator on 2017/9/19.
 */

public class En_Dn_crypt {
    // loading so library
    static {
        System.loadLibrary("JNI_en_de_cryption");
    }

    // declare native method
    public static native String encode(char prim[]);
    // declare native method
    public static native String decode(char prim[]);
}
package hazem.nurmontage.videoquran.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.widget.Toast;

/* loaded from: classes2.dex */
public class NetworkUtils {
    public static boolean isNetworkAvailable(Context context) {
        Network activeNetwork;
        NetworkCapabilities networkCapabilities;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        return (connectivityManager == null || (activeNetwork = connectivityManager.getActiveNetwork()) == null || (networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)) == null || !networkCapabilities.hasCapability(12) || !networkCapabilities.hasCapability(16)) ? false : true;
    }

    public static void checkInternetAndShowToast(Context context) {
        if (isNetworkAvailable(context)) {
            return;
        }
        Toast.makeText(context, "No internet connection", 0).show();
    }
}

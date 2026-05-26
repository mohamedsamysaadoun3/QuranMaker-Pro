package hazem.nurmontage.videoquran.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import hazem.nurmontage.videoquran.model.Template;

/* loaded from: classes2.dex */
public class LocalPersistence {
    public static Object readObjectFromFile(Context context, String str) {
        try {
            return new GsonBuilder().create().fromJson(context.getSharedPreferences("MTemplate", 0).getString(str, ""), Template.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void writeTemplate(Context context, Object obj, String str, String str2) {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences("MTemplate", 0);
            Gson gson = new Gson();
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.remove(str);
            edit.putString(str2, gson.toJson(obj));
            edit.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void duplicateTemplate(Context context, Object obj, String str) {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences("MTemplate", 0);
            Gson gson = new Gson();
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putString(str, gson.toJson(obj));
            edit.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteTemplate(Context context, String str) {
        try {
            SharedPreferences.Editor edit = context.getSharedPreferences("MTemplate", 0).edit();
            edit.remove(str);
            edit.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

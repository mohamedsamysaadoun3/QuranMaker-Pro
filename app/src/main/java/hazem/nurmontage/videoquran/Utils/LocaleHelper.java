package hazem.nurmontage.videoquran.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;
import java.util.Locale;

/* loaded from: classes2.dex */
public class LocaleHelper {
    private static final String SELECTED_LANGUAGE = "Locale.Helper.Selected.Language";

    public static Context onAttach(Context context) {
        return setLocale(context, getPersistedData(context, getLanguage(context)));
    }

    public static String getLanguage(Context context) {
        return getPersistedData(context, "en");
    }

    public static void setLocale(String str) {
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(str));
    }

    public static Context setLocale(Context context, String str) {
        persist(context, str);
        return updateResources(context, str);
    }

    public static String getPersistedData(Context context, String str) {
        return context.getSharedPreferences("ActPreference", 0).getString(SELECTED_LANGUAGE, str);
    }

    public static void persist(Context context, String str) {
        SharedPreferences.Editor edit = context.getSharedPreferences("ActPreference", 0).edit();
        edit.putString(SELECTED_LANGUAGE, str);
        edit.apply();
    }

    public static void userIsChoice(Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences("ActPreference", 0).edit();
        edit.putBoolean("userIsChoice", true);
        edit.apply();
    }

    public static boolean getUserIsChoice(Context context) {
        return context.getSharedPreferences("ActPreference", 0).getBoolean("userIsChoice", false);
    }

    public static Context updateResources(Context context, String str) {
        Locale locale = new Locale(str);
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.create(locale));
        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);
        return context.createConfigurationContext(configuration);
    }

    public static Context updateResourcesLegacy(Context context, String str) {
        Locale locale = new Locale(str);
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.create(locale));
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        Context createConfigurationContext = context.createConfigurationContext(configuration);
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        if (Build.VERSION.SDK_INT <= 24) {
            resources.updateConfiguration(configuration, displayMetrics);
        }
        return createConfigurationContext;
    }
}

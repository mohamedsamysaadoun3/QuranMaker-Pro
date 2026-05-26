package hazem.nurmontage.videoquran.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/* loaded from: classes2.dex */
public class MyPrefereces {
    private static final String FIRST_RUN_KEY = "firstRun";
    private static final String IS_VU_ABOUT = "is_about";
    private static final String IS_VU_COPYRIGHT = "is_vu_copyright";
    private static final String PREFS_NAME = "MyPrefs";
    private static final String SCROLL_X = "scroll_view_x";

    public static boolean isCopyRight(Context context) {
        return context.getSharedPreferences(PREFS_NAME, 0).getBoolean(IS_VU_COPYRIGHT, false);
    }

    public static void putVuCopyRight(Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences(PREFS_NAME, 0).edit();
        edit.putBoolean(IS_VU_COPYRIGHT, true);
        edit.apply();
    }

    public static int getScrollX(Context context) {
        return context.getSharedPreferences(PREFS_NAME, 0).getInt(SCROLL_X, 0);
    }

    public static void putScrollX(Context context, int i) {
        SharedPreferences.Editor edit = context.getSharedPreferences(PREFS_NAME, 0).edit();
        edit.putInt(SCROLL_X, i);
        edit.apply();
    }

    public static boolean isShowHint(Context context) {
        return context.getSharedPreferences(PREFS_NAME, 0).getBoolean("hint_crop_scale", false);
    }

    public static void putShowHint(Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences(PREFS_NAME, 0).edit();
        edit.putBoolean("hint_crop_scale", true);
        edit.apply();
    }

    public static int getLastIconIndex(Context context) {
        return context.getSharedPreferences(PREFS_NAME, 0).getInt("icon_quran", 0);
    }

    public static void putIndexLastIcon(Context context, int i) {
        SharedPreferences.Editor edit = context.getSharedPreferences(PREFS_NAME, 0).edit();
        edit.putInt("icon_quran", i);
        edit.apply();
    }

    public static boolean isIncludeBismilah(Context context) {
        return context.getSharedPreferences(PREFS_NAME, 0).getBoolean("IncludeBismilah", false);
    }

    public static void putIncludeBismilah(Context context, boolean z) {
        SharedPreferences.Editor edit = context.getSharedPreferences(PREFS_NAME, 0).edit();
        edit.putBoolean("IncludeBismilah", z);
        edit.apply();
    }

    public static boolean isVueAbout(Context context) {
        return context.getSharedPreferences(PREFS_NAME, 0).getBoolean(IS_VU_ABOUT, false);
    }

    public static void putVueAbout(Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences(PREFS_NAME, 0).edit();
        edit.putBoolean(IS_VU_ABOUT, true);
        edit.apply();
    }

    public static boolean isFirstRun(Context context) {
        return context.getSharedPreferences(PREFS_NAME, 0).getBoolean(FIRST_RUN_KEY, true);
    }

    public static void putFirstRun(Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences(PREFS_NAME, 0).edit();
        edit.putBoolean(FIRST_RUN_KEY, false);
        edit.apply();
    }
}

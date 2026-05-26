package hazem.nurmontage.videoquran.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/* loaded from: classes2.dex */
public class QuranPreference {
    private static final String KEY_FROM = "from";
    private static final String KEY_NAME_READER = "name_reader_";
    private static final String KEY_SEARCH = "search";
    private static final String KEY_SURAH = "surah";
    private static final String KEY_TO = "to";
    private static final String KEY_TRANSLATION = "translation_select";
    private static final String PREF_NAME = "QuranPrefs_";
    private SharedPreferences sharedPreferences;

    public QuranPreference(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME, 0);
    }

    public static void savePreferencesSearch(Context context, int i, int i2, int i3, String str) {
        SharedPreferences.Editor edit = context.getSharedPreferences(PREF_NAME, 0).edit();
        edit.putInt("from", i2);
        edit.putInt("to", i3);
        edit.putInt("surah", i);
        edit.putString(KEY_SEARCH, str);
        edit.apply();
    }

    public static void saveLastSearch(Context context, String str) {
        SharedPreferences.Editor edit = context.getSharedPreferences(PREF_NAME, 0).edit();
        edit.putString(KEY_SEARCH, str);
        edit.apply();
    }

    public static String getLastSearch(Context context) {
        return context.getSharedPreferences(PREF_NAME, 0).getString(KEY_SEARCH, "");
    }

    public void savePreferences(int i, int i2, int i3, int i4, int i5) {
        SharedPreferences.Editor edit = this.sharedPreferences.edit();
        edit.putInt("from", i2);
        edit.putInt("to", i3);
        edit.putInt("surah", i);
        edit.putInt(KEY_NAME_READER, i4);
        edit.putInt(KEY_TRANSLATION, i5);
        edit.apply();
    }

    public static void savePreferencesSearch(Context context, int i, int i2) {
        SharedPreferences.Editor edit = context.getSharedPreferences(PREF_NAME, 0).edit();
        edit.putInt("from", i2);
        edit.putInt("to", i2);
        edit.putInt("surah", i);
        edit.apply();
    }

    public int getSurah() {
        return this.sharedPreferences.getInt("surah", 0);
    }

    public int getTranslation() {
        return this.sharedPreferences.getInt(KEY_TRANSLATION, 0);
    }

    public int getFrom() {
        return this.sharedPreferences.getInt("from", 0);
    }

    public int getTo() {
        return this.sharedPreferences.getInt("to", 0);
    }

    public int getNameReader() {
        try {
            return this.sharedPreferences.getInt(KEY_NAME_READER, 0);
        } catch (Exception unused) {
            return 0;
        }
    }
}

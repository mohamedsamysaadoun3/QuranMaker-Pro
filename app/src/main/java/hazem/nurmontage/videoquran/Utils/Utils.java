package hazem.nurmontage.videoquran.Utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.util.Pair;
import androidx.core.content.ContextCompat;
import hazem.nurmontage.videoquran.constant.ResizeType;

/* loaded from: classes2.dex */
public class Utils {
    private static final char CHARACTER_TO_COUNT = ' ';

    public static boolean isAppInstalled(Context context, String str) {
        try {
            context.getPackageManager().getPackageInfo(str, 0);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public static float f2(float f) {
        return Math.round(f * 100.0f) / 100.0f;
    }

    public static int countSpace(int i, String str) {
        if (str == null || i <= 0) {
            return 0;
        }
        int min = Math.min(i, str.length());
        int i2 = 0;
        for (int i3 = 0; i3 < min; i3++) {
            if (str.charAt(i3) == ' ') {
                i2++;
            }
        }
        return i2;
    }

    public static int countSpace(String str) {
        if (str == null) {
            return 0;
        }
        int i = 0;
        for (int i2 = 0; i2 < str.length(); i2++) {
            if (str.charAt(i2) == ' ') {
                i++;
            }
        }
        return i;
    }

    public static int countIndex(int i, int i2, String str) {
        int i3 = 0;
        if (str == null || i < 0) {
            if (str == null) {
                return 0;
            }
            return str.length();
        }
        int i4 = 0;
        for (int i5 = i; i5 < str.length() && i4 <= i2; i5++) {
            if (str.charAt(i5) == ' ') {
                i4++;
            }
            if (i4 > i2) {
                break;
            }
        }
        while (i < str.length() && i3 <= i2) {
            if (str.charAt(i) == ' ') {
                i3++;
            }
            i++;
        }
        return i;
    }

    public static int countIndex(int i, String str) {
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < str.length() && i3 < i; i4++) {
            if (str.charAt(i4) == ' ') {
                i3++;
            }
            if (i3 > i) {
                break;
            }
        }
        int i5 = 0;
        while (i2 < str.length() && i5 < i) {
            if (str.charAt(i2) == ' ') {
                i5++;
            }
            i2++;
        }
        return i2;
    }

    public static Drawable getDrawableByName(Context context, String str) {
        int identifier = context.getResources().getIdentifier(str, "drawable", context.getPackageName());
        if (identifier != 0) {
            return ContextCompat.getDrawable(context, identifier);
        }
        return null;
    }

    public static Pair<Integer, Integer> getDimension(ResizeType resizeType, int i) {
        int value;
        if (resizeType.ordinal() == ResizeType.SOCIAL_STORY.ordinal()) {
            i = (int) (i * ResizeType.VERTICAL.getValue());
            value = i;
        } else {
            value = resizeType.ordinal() == ResizeType.YOUTUBE_THUMBNAIL.ordinal() ? (int) (i * ResizeType.YOUTUBE_THUMBNAIL.getValue()) : i;
        }
        return new Pair<>(Integer.valueOf(i), Integer.valueOf(value));
    }

    public static boolean isProbablyLArabic(String str) {
        int i = 0;
        while (i < str.length()) {
            int codePointAt = str.codePointAt(i);
            if (codePointAt >= 1536 && codePointAt <= 1760) {
                return true;
            }
            i += Character.charCount(codePointAt);
        }
        return false;
    }

    public static int indexOf(int[] iArr, int i) {
        for (int i2 = 0; i2 < iArr.length; i2++) {
            if (iArr[i2] == i) {
                return i2;
            }
        }
        return -1;
    }
}

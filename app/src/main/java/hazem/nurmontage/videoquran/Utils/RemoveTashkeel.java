package hazem.nurmontage.videoquran.Utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* loaded from: classes2.dex */
public class RemoveTashkeel {
    private static final char SPECIFIC_POINT_CHAR_CODE = '.';
    private static final Set<Character> TASHKEEL_SET = new HashSet();
    public static final List<String> arabicV_original;

    static {
        char[] cArr = {1611, 1612, 1613, 1614, 1615, 1616, 1617, 1618, 1619, 1620, 1621, 1648, 1600};
        for (int i = 0; i < 13; i++) {
            TASHKEEL_SET.add(Character.valueOf(cArr[i]));
        }
        arabicV_original = Arrays.asList("ؘ", "ؙ", "ؚ", "ؐ", "ؐؑ", "ؒ", "ؓ", "ؔ", "ؕ", "ؖ", "ؗ", "ؗ", "ﹰﹰ", "ﹲ", "ﹴ", "ﹸ", "ﹼ", "ﹾ", "ٍ", "ً", "ُ", "ِ", "َ", "ّ", "ٓ", "ٔ", "ْ", "ِ", "َّ", "َ", "َْ", "َ", "ً", "ٌ", "َ", "ُ", "ٍ", "َ", "ْ", "ِ", "ُ", "ّ", "ً");
    }

    public static boolean isTashkeel(char c) {
        return TASHKEEL_SET.contains(Character.valueOf(c));
    }

    public static String removeTashkeel(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(str.length());
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (!isTashkeel(charAt)) {
                sb.append(charAt);
            }
        }
        return sb.toString();
    }

    public static int countTashkeel(String str) {
        if (str == null) {
            return 0;
        }
        int i = 0;
        for (int i2 = 0; i2 < str.length(); i2++) {
            if (isTashkeel(str.charAt(i2))) {
                i++;
            }
        }
        return i;
    }

    public static String removeTashkeelAndPoint(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(str.length());
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (!isTashkeel(charAt) && charAt != '.') {
                sb.append(charAt);
            }
        }
        return sb.toString();
    }

    public static String removeChar(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(str.length());
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (isTashkeel(charAt)) {
                sb.append(charAt);
            } else {
                sb.append(' ');
            }
        }
        return sb.toString();
    }
}

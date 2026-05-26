package hazem.nurmontage.videoquran.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class JavaBM {
    private String mPattern;
    private String mText;
    private int[] skipTable;

    public JavaBM(String str) {
        this.skipTable = new int[42];
        this.mText = str;
    }

    public JavaBM() {
        this.skipTable = new int[42];
        this.mText = "";
    }

    public void setmPattern(String str) {
        this.mPattern = str;
        setSkipTable(str, this.skipTable);
    }

    public String getmPattern() {
        return this.mPattern;
    }

    public int match(String str) {
        int i = 0;
        while (i <= str.length() - this.mPattern.length()) {
            int length = this.mPattern.length() - 1;
            char c = 1570;
            while (true) {
                if (length < 0) {
                    break;
                }
                char charAt = this.mPattern.charAt(length);
                char charAt2 = str.charAt(i + length);
                if (charAt != charAt2) {
                    c = charAt2;
                    break;
                }
                length--;
                c = charAt2;
            }
            if (c < 1570 || c > 1610) {
                c = 1611;
            }
            if (length < 0) {
                return i;
            }
            i += Math.max(length - this.skipTable[c - 1570], 1);
        }
        return -1;
    }

    private void setSkipTable(String str, int[] iArr) {
        Arrays.fill(iArr, -1);
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) < 1570 || str.charAt(i) > 1610) {
                iArr[41] = i;
            } else {
                iArr[str.charAt(i) - 1570] = i;
            }
        }
    }

    public static List<Integer> match(String str, String str2) {
        int i;
        ArrayList arrayList = new ArrayList();
        int length = str2.length();
        int length2 = str.length();
        Map<Character, Integer> preprocessForBadCharacterShift = preprocessForBadCharacterShift(str);
        int i2 = length2 - 1;
        if (i2 >= length) {
            return arrayList;
        }
        int i3 = 0;
        while (true) {
            if (i2 >= 0) {
                int i4 = i3 + i2;
                char charAt = str2.charAt(i4);
                char charAt2 = str.charAt(i2);
                if (i4 < length) {
                    if (charAt != charAt2) {
                        Integer num = preprocessForBadCharacterShift.get(Character.valueOf(charAt));
                        if (num == null) {
                            i = i4 + 1;
                        } else {
                            int intValue = i4 - (num.intValue() + i3);
                            if (intValue <= 0) {
                                intValue = 1;
                            }
                            i = intValue + i3;
                        }
                        i3 = i;
                    } else {
                        if (i2 == 0) {
                            arrayList.add(Integer.valueOf(i3));
                            i3++;
                        }
                        i2--;
                    }
                }
            }
        }
    }

    private static Map<Character, Integer> preprocessForBadCharacterShift(String str) {
        HashMap hashMap = new HashMap();
        for (int length = str.length() - 1; length >= 0; length--) {
            char charAt = str.charAt(length);
            if (!hashMap.containsKey(Character.valueOf(charAt))) {
                hashMap.put(Character.valueOf(charAt), Integer.valueOf(length));
            }
        }
        return hashMap;
    }
}

package hazem.nurmontage.videoquran.Utils;

import hazem.nurmontage.videoquran.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/* loaded from: classes2.dex */
public class DrawableHelper {
    private static final Map<String, Integer> drawableIconMap;
    private static final Map<String, Integer> drawableMap;

    public static int getIdResource(String str) {
        if (str == null || str.contains("")) {
            return R.drawable.ic_instagram;
        }
        if (str.contains("t")) {
            return R.drawable.ic_tiktok;
        }
        if (str.equals("y_16:9")) {
            return R.drawable.ic_youtube;
        }
        return R.drawable.ic_youtube_shorts_icon;
    }

    static {
        HashMap hashMap = new HashMap();
        drawableIconMap = hashMap;
        hashMap.put("hafes", Integer.valueOf(R.drawable.hafes_icon));
        hashMap.put("warach", Integer.valueOf(R.drawable.warach_icon));
        hashMap.put("shamerli", Integer.valueOf(R.drawable.shamerli_icon));
        hashMap.put("nour_hode", Integer.valueOf(R.drawable.nour_hoda_icon));
        hashMap.put("amiri", Integer.valueOf(R.drawable.amiri_icon));
        hashMap.put("taha", Integer.valueOf(R.drawable.taha_icon));
        HashMap hashMap2 = new HashMap();
        drawableMap = hashMap2;
        hashMap2.put("bg_1", Integer.valueOf(R.drawable.bg_1));
        hashMap2.put("bg_2", Integer.valueOf(R.drawable.bg_2));
        hashMap2.put("bg_3", Integer.valueOf(R.drawable.bg_3));
        hashMap2.put("bg_4", Integer.valueOf(R.drawable.bg_4));
        hashMap2.put("bg_5", Integer.valueOf(R.drawable.bg_5));
        hashMap2.put("bg_6", Integer.valueOf(R.drawable.bg_6));
        hashMap2.put("bg_7", Integer.valueOf(R.drawable.bg_7));
        hashMap2.put("bg_8", Integer.valueOf(R.drawable.bg_8));
        hashMap2.put("bg_9", Integer.valueOf(R.drawable.bg_9));
        hashMap2.put("bg_10", Integer.valueOf(R.drawable.bg_10));
        hashMap2.put("bg_11", Integer.valueOf(R.drawable.bg_11));
        hashMap2.put("bg_12", Integer.valueOf(R.drawable.bg_12));
        hashMap2.put("bg_13", Integer.valueOf(R.drawable.bg_13));
        hashMap2.put("bg_14", Integer.valueOf(R.drawable.bg_14));
        hashMap2.put("bg_15", Integer.valueOf(R.drawable.bg_15));
        hashMap2.put("bg_16", Integer.valueOf(R.drawable.bg_16));
        hashMap2.put("bg_17", Integer.valueOf(R.drawable.bg_17));
        hashMap2.put("bg_18", Integer.valueOf(R.drawable.bg_18));
        hashMap2.put("bg_19", Integer.valueOf(R.drawable.bg_19));
        hashMap2.put("bg_20", Integer.valueOf(R.drawable.bg_20));
        hashMap2.put("bg_21", Integer.valueOf(R.drawable.bg_21));
        hashMap2.put("bg_22", Integer.valueOf(R.drawable.bg_22));
        hashMap2.put("bg_23", Integer.valueOf(R.drawable.bg_23));
        hashMap2.put("bg_24", Integer.valueOf(R.drawable.bg_24));
        hashMap2.put("bg_25", Integer.valueOf(R.drawable.bg_25));
        hashMap2.put("bg_26", Integer.valueOf(R.drawable.bg_26));
        hashMap2.put("bg_27", Integer.valueOf(R.drawable.bg_27));
        hashMap2.put("bg_28", Integer.valueOf(R.drawable.bg_28));
        hashMap2.put("bg_29", Integer.valueOf(R.drawable.bg_29));
        hashMap2.put("bg_30", Integer.valueOf(R.drawable.bg_30));
        hashMap2.put("bg_31", Integer.valueOf(R.drawable.bg_31));
        hashMap2.put("bg_32", Integer.valueOf(R.drawable.bg_32));
        hashMap2.put("bg_33", Integer.valueOf(R.drawable.bg_33));
        hashMap2.put("bg_34", Integer.valueOf(R.drawable.bg_34));
        hashMap2.put("bg_35", Integer.valueOf(R.drawable.bg_35));
        hashMap2.put("bg_36", Integer.valueOf(R.drawable.bg_36));
        hashMap2.put("bg_37", Integer.valueOf(R.drawable.bg_37));
        hashMap2.put("bg_38", Integer.valueOf(R.drawable.bg_38));
    }

    public static int getIDDrawableIconByName(String str) {
        try {
            return drawableIconMap.get(str).intValue();
        } catch (Exception unused) {
            return R.drawable.hafes_icon;
        }
    }

    public static Map.Entry<String, Integer> getRandomDrawableEntry() {
        Random random = new Random();
        ArrayList arrayList = new ArrayList(drawableMap.entrySet());
        return (Map.Entry) arrayList.get(random.nextInt(arrayList.size()));
    }

    public static int getIDDrawableByName(String str) {
        try {
            return drawableMap.get(str).intValue();
        } catch (Exception unused) {
            return R.drawable.bg_24;
        }
    }
}

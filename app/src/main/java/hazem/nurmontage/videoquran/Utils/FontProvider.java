package hazem.nurmontage.videoquran.Utils;

import android.content.res.Resources;
import android.graphics.Typeface;
import android.text.TextUtils;
import hazem.nurmontage.videoquran.common.Common;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/* loaded from: classes2.dex */
public class FontProvider {
    private String DEFAULT_FONT_NAME;
    private Map<String, String> fontNameToTypefaceFileQuran;
    private List<String> fontNamesQuran;
    private Resources resources;
    private Map<String, Typeface> typefaces = new HashMap();

    public FontProvider(Resources resources) {
        this.resources = resources;
        loadQuranFont();
    }

    private void loadQuranFont() {
        HashMap hashMap = new HashMap();
        this.fontNameToTypefaceFileQuran = hashMap;
        hashMap.put("المجد", "المجد.ttf");
        this.fontNameToTypefaceFileQuran.put("جنة", "جنة.ttf");
        this.fontNameToTypefaceFileQuran.put("محمدي", "محمدي.ttf");
        this.fontNameToTypefaceFileQuran.put("خط الثلث مزخرف", "الثلث مزخرف.ttf");
        this.fontNameToTypefaceFileQuran.put("باك تايب أجراك", "باك تايب أجراك.ttf");
        this.fontNameToTypefaceFileQuran.put("باك تايب تحرير", "باك تايب تحرير.ttf");
        this.fontNameToTypefaceFileQuran.put("باك تايب نسخ", "باك تايب نسخ.ttf");
        this.fontNameToTypefaceFileQuran.put("خط نسخ عثماني", "خط نسخ عثماني.otf");
        this.fontNameToTypefaceFileQuran.put("عثماني", Common.FONT_QURAN);
        this.fontNameToTypefaceFileQuran.put("خط القيروان", "خط القيروان.ttf");
        this.fontNameToTypefaceFileQuran.put("خط حفص", "خط حفص.ttf");
        this.fontNameToTypefaceFileQuran.put("خط ورش", "خط ورش.ttf");
        this.fontNameToTypefaceFileQuran.put("قالون", "قالون.ttf");
        this.fontNameToTypefaceFileQuran.put("مريم", "مريم.ttf");
        this.fontNameToTypefaceFileQuran.put("الأقصى", "الأقصى.ttf");
        this.fontNameToTypefaceFileQuran.put("أجنادين", "أجنادين.ttf");
        this.fontNameToTypefaceFileQuran.put("بيبو", "بيبو.ttf");
        this.fontNameToTypefaceFileQuran.put("بيسان لايت", "بيسان لايت.ttf");
        this.fontNameToTypefaceFileQuran.put("تبيان", "تبيان.ttf");
        this.fontNameToTypefaceFileQuran.put("تجمع كوفي", "تجمع كوفي.ttf");
        this.fontNameToTypefaceFileQuran.put("تريكا", "تريكا.ttf");
        this.fontNameToTypefaceFileQuran.put("خط تجمع المصممين", "خط تجمع المصممين.ttf");
        this.fontNameToTypefaceFileQuran.put("شمائل", "شمائل.ttf");
        this.fontNameToTypefaceFileQuran.put("عصومي", "عصومي.ttf");
        this.fontNameToTypefaceFileQuran.put("فرشة", "فرشة.ttf");
        this.fontNameToTypefaceFileQuran.put("فسيح", "فسيح.ttf");
        this.fontNameToTypefaceFileQuran.put("كوفي", "كوفي.ttf");
        this.fontNameToTypefaceFileQuran.put("مطرية", "مطرية.ttf");
        this.fontNameToTypefaceFileQuran.put("نمر", "نمر.ttf");
        this.fontNameToTypefaceFileQuran.put("هيفن", "هيفن.ttf");
        this.fontNameToTypefaceFileQuran.put("لفتا بلاك", "لفتا بلاك.otf");
        this.fontNameToTypefaceFileQuran.put("خط الإبل", "خط الإبل.otf");
        this.fontNamesQuran = new ArrayList(new TreeSet(this.fontNameToTypefaceFileQuran.keySet()));
    }

    public String getFullName(String str) {
        return this.fontNameToTypefaceFileQuran.get(str);
    }

    public Typeface getTypeface(String str) {
        if (str == null) {
            return Typeface.DEFAULT;
        }
        try {
            if (TextUtils.isEmpty(str)) {
                return Typeface.DEFAULT;
            }
            if (this.typefaces.get(str) == null) {
                Typeface createFromAsset = Typeface.createFromAsset(this.resources.getAssets(), "fonts/arabic/" + this.fontNameToTypefaceFileQuran.get(str));
                this.typefaces.put(str, createFromAsset);
                return createFromAsset;
            }
            return this.typefaces.get(str);
        } catch (Exception unused) {
            return Typeface.DEFAULT;
        }
    }

    public void clear() {
        Map<String, String> map = this.fontNameToTypefaceFileQuran;
        if (map != null) {
            map.clear();
            this.fontNameToTypefaceFileQuran = null;
        }
        List<String> list = this.fontNamesQuran;
        if (list != null) {
            list.clear();
            this.fontNamesQuran = null;
        }
        Map<String, Typeface> map2 = this.typefaces;
        if (map2 != null) {
            map2.clear();
            this.typefaces = null;
        }
        this.resources = null;
    }

    public List<String> getFontNamesQuran() {
        return this.fontNamesQuran;
    }

    public void setDefaultFontName(String str) {
        this.DEFAULT_FONT_NAME = str;
    }

    public String getDefaultFontName() {
        return this.DEFAULT_FONT_NAME;
    }

    public Resources getResources() {
        return this.resources;
    }
}

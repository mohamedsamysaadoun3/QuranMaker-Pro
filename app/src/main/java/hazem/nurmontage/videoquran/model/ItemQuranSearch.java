package hazem.nurmontage.videoquran.model;

import java.io.Serializable;

/* loaded from: classes2.dex */
public class ItemQuranSearch implements Serializable {
    private final String aya;
    private final int endSpannble;
    private final int startSpannable;
    private final int surahIndex;
    private final String surahName;
    private final int to;

    public ItemQuranSearch(String str, String str2, int i, int i2, int i3, int i4) {
        this.aya = str;
        this.surahName = str2;
        this.to = i;
        this.surahIndex = i2;
        this.startSpannable = i3;
        this.endSpannble = i4;
    }

    public int getEndSpannble() {
        return this.endSpannble;
    }

    public int getStartSpannable() {
        return this.startSpannable;
    }

    public String getSurahName() {
        return this.surahName;
    }

    public int getSurahIndex() {
        return this.surahIndex;
    }

    public String getAya() {
        return this.aya;
    }

    public int getTo() {
        return this.to;
    }
}

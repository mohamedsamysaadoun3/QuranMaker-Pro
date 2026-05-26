package hazem.nurmontage.videoquran.model;

import java.io.Serializable;

/* loaded from: classes2.dex */
public class EntitySurahTemplate implements Serializable {
    private int clr;
    private int clrBg;
    private float factor_scale;
    private int index_surah;
    private boolean isHaveBg;
    private float left;
    private String name;
    private String name_font;
    private int preset;
    private String reader;
    private MRectF rectF;
    private int style;
    private float top;

    public void setRectF(MRectF mRectF) {
        this.rectF = mRectF;
    }

    public MRectF getRectF() {
        return this.rectF;
    }

    public void setStyle(int i) {
        this.style = i;
    }

    public void setIndex_surah(int i) {
        this.index_surah = i;
    }

    public int getIndex_surah() {
        return this.index_surah;
    }

    public int getStyle() {
        return this.style;
    }

    public void setPreset(int i) {
        this.preset = i;
    }

    public void setClr(int i) {
        this.clr = i;
    }

    public int getPreset() {
        return this.preset;
    }

    public int getClr() {
        return this.clr;
    }

    public void setName_font(String str) {
        this.name_font = str;
    }

    public String getName_font() {
        return this.name_font;
    }

    public float getFactor_scale() {
        return this.factor_scale;
    }

    public void setFactor_scale(float f) {
        this.factor_scale = f;
    }

    public int getClrBg() {
        return this.clrBg;
    }

    public void setClrBg(int i) {
        this.clrBg = i;
    }

    public boolean isHaveBg() {
        return this.isHaveBg;
    }

    public void setHaveBg(boolean z) {
        this.isHaveBg = z;
    }

    public EntitySurahTemplate(String str, String str2, float f, float f2, MRectF mRectF, float f3, String str3, int i, int i2, int i3, int i4, boolean z, int i5) {
        this.name = str;
        this.clrBg = i5;
        this.isHaveBg = z;
        this.style = i3;
        this.index_surah = i4;
        this.clr = i;
        this.preset = i2;
        this.reader = str2;
        this.left = f;
        this.top = f2;
        setRectF(mRectF);
        this.factor_scale = f3;
        this.name_font = str3;
    }

    public void setPos(float f, float f2) {
        this.left = f;
        this.top = f2;
    }

    public float getLeft() {
        return this.left;
    }

    public float getTop() {
        return this.top;
    }

    public String getName() {
        return this.name;
    }

    public String getReader() {
        return this.reader;
    }

    public void setReader(String str) {
        this.reader = str;
    }

    public void setName(String str) {
        this.name = str;
    }
}

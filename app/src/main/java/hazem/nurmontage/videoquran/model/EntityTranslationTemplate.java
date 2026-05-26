package hazem.nurmontage.videoquran.model;

import java.io.Serializable;

/* loaded from: classes2.dex */
public class EntityTranslationTemplate implements Serializable {
    private String aya;
    private float btm_x;
    private float btm_y;
    private int clr_bg;
    private int color;
    private float end;
    private String file;
    private String file_in;
    private String file_out;
    private float height;
    private boolean isHaveBg;
    private float left;
    private String name_font;
    private int number;
    private int preset;
    private MRectF rectF;
    private float right;
    private float start;
    private Transition transition;
    private float x;
    private float y;
    private float scale = 1.0f;
    private float factor_size = 1.0f;
    private float factor_sizeTrl = 1.0f;

    public void setRectF(MRectF mRectF) {
        this.rectF = mRectF;
    }

    public MRectF getRectF() {
        return this.rectF;
    }

    public float getFactor_sizeTrl() {
        return this.factor_sizeTrl;
    }

    public void setHeight(float f) {
        this.height = f;
    }

    public float getHeight() {
        return this.height;
    }

    public void setFactor_sizeTrl(float f) {
        this.factor_sizeTrl = f;
    }

    public float getFactor_size() {
        return this.factor_size;
    }

    public void setFactor_size(float f) {
        this.factor_size = f;
    }

    public void setX(float f) {
        this.x = f;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public void setY(float f) {
        this.y = f;
    }

    public float getScale() {
        return this.scale;
    }

    public void setScale(float f) {
        this.scale = f;
    }

    public int getPreset() {
        return this.preset;
    }

    public void setPreset(int i) {
        this.preset = i;
    }

    public boolean isHaveBg() {
        return this.isHaveBg;
    }

    public void setHaveBg(boolean z) {
        this.isHaveBg = z;
    }

    public void setClr_bg(int i) {
        this.clr_bg = i;
    }

    public int getClr_bg() {
        return this.clr_bg;
    }

    public void setFile(String str) {
        this.file = str;
    }

    public void setFile_in(String str) {
        this.file_in = str;
    }

    public void setFile_out(String str) {
        this.file_out = str;
    }

    public String getFile() {
        return this.file;
    }

    public String getFile_in() {
        return this.file_in;
    }

    public String getFile_out() {
        return this.file_out;
    }

    public EntityTranslationTemplate(Transition transition, float f, float f2, float f3, float f4, float f5, float f6, String str, String str2, int i, int i2, int i3) {
        this.aya = str;
        this.preset = i3;
        this.transition = transition;
        this.start = f;
        this.color = i2;
        this.end = f2;
        this.name_font = str2;
        this.number = i;
        this.right = f6;
        this.left = f5;
        this.btm_x = f3;
        this.btm_y = f4;
    }

    public Transition getTransition() {
        return this.transition;
    }

    public void setTransition(Transition transition) {
        this.transition = transition;
    }

    public String getName_font() {
        return this.name_font;
    }

    public int getColor() {
        return this.color;
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int i) {
        this.number = i;
    }

    public float getLeft() {
        return this.left;
    }

    public float getBtm_x() {
        return this.btm_x;
    }

    public float getBtm_y() {
        return this.btm_y;
    }

    public float getRight() {
        return this.right;
    }

    public void setAya(String str) {
        this.aya = str;
    }

    public void setEnd(float f) {
        this.end = f;
    }

    public void setStart(float f) {
        this.start = f;
    }

    public float getEnd() {
        return this.end;
    }

    public float getStart() {
        return this.start;
    }

    public String getAya() {
        return this.aya;
    }
}

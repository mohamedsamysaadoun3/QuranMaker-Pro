package hazem.nurmontage.videoquran.model;

import java.io.Serializable;

/* loaded from: classes2.dex */
public class EntityQuranTemplate implements Serializable {
    private String aya;
    private float btm_x;
    private float btm_y;
    private int color;
    private int colorTrsl;
    private String complete_aya;
    private float end;
    private int endWord_index;
    private String file;
    private String file_in;
    private String file_out;
    private float height;
    private int indexNumber;
    private float left;
    private String name_font;
    private int number;
    private int preset;
    private MRectF rectF;
    private float right;
    private float start;
    private int startWord_index;
    private Transition transition;
    private String translation;
    private String translation_complete;
    private float x;
    private float y;
    private String icon = "hafes";
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

    public void setStartWord_index(int i) {
        this.startWord_index = i;
    }

    public void setEndWord_index(int i) {
        this.endWord_index = i;
    }

    public int getStartWord_index() {
        return this.startWord_index;
    }

    public int getEndWord_index() {
        return this.endWord_index;
    }

    public int getPreset() {
        return this.preset;
    }

    public void setPreset(int i) {
        this.preset = i;
    }

    public int getColorTrsl() {
        return this.colorTrsl;
    }

    public void setColorTrsl(int i) {
        this.colorTrsl = i;
    }

    public void setTranslation_complete(String str) {
        this.translation_complete = str;
    }

    public void setTranslation(String str) {
        this.translation = str;
    }

    public String getTranslation() {
        return this.translation;
    }

    public String getTranslation_complete() {
        return this.translation_complete;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String str) {
        this.icon = str;
    }

    public String getComplete_aya() {
        return this.complete_aya;
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

    public EntityQuranTemplate(Transition transition, float f, float f2, float f3, float f4, float f5, float f6, String str, String str2, String str3, int i, int i2, int i3, int i4, int i5) {
        this.aya = str;
        this.colorTrsl = i4;
        this.preset = i5;
        this.complete_aya = str2;
        this.transition = transition;
        this.start = f;
        this.color = i3;
        this.end = f2;
        this.name_font = str3;
        this.number = i2;
        this.indexNumber = i;
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

    public int getIndexNumber() {
        return this.indexNumber;
    }

    public void setIndexNumber(int i) {
        this.indexNumber = i;
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

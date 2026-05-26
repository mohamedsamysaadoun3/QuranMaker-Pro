package hazem.nurmontage.videoquran.model;

import java.io.Serializable;

/* loaded from: classes2.dex */
public class EntityBismilahTemplate implements Serializable {
    private String aya;
    private float btm_x;
    private float btm_y;
    private int color;
    private float end;
    private String file;
    private String file_in;
    private String file_out;
    private float height;
    private float left;
    private int preset;
    private MRectF rectF;
    private float right;
    private float start;
    private Transition transition;
    private float x;
    private float y;
    private float scale = 1.0f;
    private float factor_size = 1.0f;

    public void setRectF(MRectF mRectF) {
        this.rectF = mRectF;
    }

    public MRectF getRectF() {
        return this.rectF;
    }

    public void setHeight(float f) {
        this.height = f;
    }

    public float getHeight() {
        return this.height;
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

    public EntityBismilahTemplate(Transition transition, float f, float f2, float f3, float f4, float f5, float f6, String str, int i, int i2) {
        this.aya = str;
        this.preset = i2;
        this.transition = transition;
        this.start = f;
        this.color = i;
        this.end = f2;
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

    public int getColor() {
        return this.color;
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

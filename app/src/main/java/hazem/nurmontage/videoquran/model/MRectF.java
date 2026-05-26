package hazem.nurmontage.videoquran.model;

import java.io.Serializable;

/* loaded from: classes2.dex */
public class MRectF implements Serializable {
    private float b;
    private float l;
    private float r;
    private float t;

    public MRectF(float f, float f2, float f3, float f4) {
        this.l = f;
        this.t = f2;
        this.r = f3;
        this.b = f4;
    }

    public float getT() {
        return this.t;
    }

    public float getL() {
        return this.l;
    }

    public float getB() {
        return this.b;
    }

    public float getR() {
        return this.r;
    }

    public void setB(float f) {
        this.b = f;
    }

    public void setL(float f) {
        this.l = f;
    }

    public void setR(float f) {
        this.r = f;
    }

    public void setT(float f) {
        this.t = f;
    }
}

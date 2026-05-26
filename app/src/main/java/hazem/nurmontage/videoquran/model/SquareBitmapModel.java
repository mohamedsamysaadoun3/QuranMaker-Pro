package hazem.nurmontage.videoquran.model;

import java.io.Serializable;

/* loaded from: classes2.dex */
public class SquareBitmapModel implements Serializable {
    private float bottom;
    private float height_square;
    private float lef_square;
    private float posX;
    private float posY;
    private float raduis;
    private float right;
    private float top_square;
    private float width_sqaure;

    public SquareBitmapModel(float f, float f2, float f3, float f4, float f5, float f6, float f7) {
        this.height_square = f6;
        this.width_sqaure = f5;
        this.top_square = f2;
        this.lef_square = f;
        this.raduis = f7;
        this.right = f3;
        this.bottom = f4;
    }

    public void set(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9) {
        this.height_square = f8;
        this.width_sqaure = f7;
        this.top_square = f4;
        this.lef_square = f3;
        this.raduis = f9;
        this.posX = f;
        this.posY = f2;
        this.right = f5;
        this.bottom = f6;
    }

    public float getRight() {
        return this.right;
    }

    public float getBottom() {
        return this.bottom;
    }

    public float getPosY() {
        return this.posY;
    }

    public float getPosX() {
        return this.posX;
    }

    public SquareBitmapModel() {
        this.height_square = 50.0f;
        this.width_sqaure = 50.0f;
    }

    public float getHeight_square() {
        return this.height_square;
    }

    public float getLef_square() {
        return this.lef_square;
    }

    public float getWidth_sqaure() {
        return this.width_sqaure;
    }

    public float getTop_square() {
        return this.top_square;
    }

    public void setHeight_square(float f) {
        this.height_square = f;
    }

    public float getRaduis() {
        return this.raduis;
    }

    public void setTop_square(float f) {
        this.top_square = f;
    }

    public void setWidth_sqaure(float f) {
        this.width_sqaure = f;
    }

    public void setLef_square(float f) {
        this.lef_square = f;
    }

    public void setRaduis(float f) {
        this.raduis = f;
    }
}

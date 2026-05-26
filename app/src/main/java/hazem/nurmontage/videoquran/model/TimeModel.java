package hazem.nurmontage.videoquran.model;

import java.io.Serializable;

/* loaded from: classes2.dex */
public class TimeModel implements Serializable {
    private String color;
    private int heightShape;
    private int height_bitmap_progress;
    private float posXRight;
    private float posY;
    private int progress_offset;
    private float size;
    private float startShape;
    private int widthShape;
    private int width_bitmap_progress;

    public TimeModel(int i, int i2, float f, String str, float f2, float f3, int i3) {
        this.size = f;
        this.color = str;
        this.posY = f2;
        this.posXRight = f3;
        this.progress_offset = i3;
        this.width_bitmap_progress = i;
        this.height_bitmap_progress = i2;
    }

    public void setHeight_bitmap_progress(int i) {
        this.height_bitmap_progress = i;
    }

    public int getHeight_bitmap_progress() {
        return this.height_bitmap_progress;
    }

    public int getHeightShape() {
        return this.heightShape;
    }

    public float getStartShape() {
        return this.startShape;
    }

    public void setHeightShape(int i) {
        this.heightShape = i;
    }

    public void setWidthShape(int i) {
        this.widthShape = i;
    }

    public int getWidthShape() {
        return this.widthShape;
    }

    public void setStartShape(float f) {
        this.startShape = f;
    }

    public int getProgress_offset() {
        return this.progress_offset;
    }

    public void setProgress_offset(int i) {
        this.progress_offset = i;
    }

    public void setWidth_bitmap_progress(int i) {
        this.width_bitmap_progress = i;
    }

    public int getWidth_bitmap_progress() {
        return this.width_bitmap_progress;
    }

    public float getPosXRight() {
        return this.posXRight;
    }

    public float getPosY() {
        return this.posY;
    }

    public float getSize() {
        return this.size;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String str) {
        this.color = str;
    }

    public void setPosXRight(float f) {
        this.posXRight = f;
    }

    public void setPosY(float f) {
        this.posY = f;
    }

    public void setSize(float f) {
        this.size = f;
    }
}

package hazem.nurmontage.videoquran.common;

import android.graphics.RectF;

/* loaded from: classes2.dex */
public class StackEntity {
    private float end;
    protected int index_end_thumbnail;
    protected int index_start_thumbnail;
    private float left;
    private float max;
    private float offset;
    private float offset_left;
    private float offset_right;
    private RectF rectF;
    private float right;
    private float start;

    public StackEntity(RectF rectF, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        this.rectF = rectF;
        this.offset = f;
        this.end = f2;
        this.start = f3;
        this.left = f4;
        this.right = f5;
        this.max = f6;
        this.offset_right = f7;
        this.offset_left = f8;
    }

    public float getOffset_left() {
        return this.offset_left;
    }

    public float getOffset_right() {
        return this.offset_right;
    }

    public int getIndex_start_thumbnail() {
        return this.index_start_thumbnail;
    }

    public int getIndex_end_thumbnail() {
        return this.index_end_thumbnail;
    }

    public float getOffset() {
        return this.offset;
    }

    public RectF getRectF() {
        return this.rectF;
    }

    public float getEnd() {
        return this.end;
    }

    public float getLeft() {
        return this.left;
    }

    public float getMax() {
        return this.max;
    }

    public float getRight() {
        return this.right;
    }

    public float getStart() {
        return this.start;
    }
}

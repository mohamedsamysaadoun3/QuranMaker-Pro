package hazem.nurmontage.videoquran.model;

import android.graphics.RectF;
import hazem.nurmontage.videoquran.entity_timeline.EntityQuranTimeline;
import hazem.nurmontage.videoquran.entity_timeline.EntityTrslTimeline;

/* loaded from: classes2.dex */
public abstract class EntityView {
    private int canvasH;
    private int canvasW;
    private RectF copyRect;
    private EntityQuranTimeline entityQuran;
    private EntityTrslTimeline entityTrslTimeline;
    private boolean isAnimTest;
    protected int max_h;
    protected int max_w;
    protected RectF rect;
    protected float x;
    protected float y;
    private float factor_scale = 1.0f;
    private float factorSize = 1.0f;
    private float factorSizeTrl = 1.0f;

    public abstract void endAnimator();

    public abstract boolean isVisible();

    public abstract void postTranslate(float f, float f2);

    public abstract void scale(float f, int i, int i2);

    public abstract void setVisible(boolean z);

    public int getMax_w() {
        return this.max_w;
    }

    public int getMax_h() {
        return this.max_h;
    }

    public void setFactorSizeTrl(float f) {
        this.factorSizeTrl = f;
    }

    public float getFactorSizeTrl() {
        return this.factorSizeTrl;
    }

    public void setFcSize(float f) {
        this.factorSize = f;
    }

    public float getFactorSize() {
        return this.factorSize;
    }

    public float getFactor_scale() {
        return this.factor_scale;
    }

    public void setFactor_scale(float f) {
        this.factor_scale = f;
    }

    public void setEntityTrslTimeline(EntityTrslTimeline entityTrslTimeline) {
        this.entityTrslTimeline = entityTrslTimeline;
    }

    public EntityTrslTimeline getEntityTrslTimeline() {
        return this.entityTrslTimeline;
    }

    public EntityQuranTimeline getEntityQuran() {
        return this.entityQuran;
    }

    public void setEntityQuran(EntityQuranTimeline entityQuranTimeline) {
        this.entityQuran = entityQuranTimeline;
    }

    public RectF getRect() {
        return this.rect;
    }

    public void setCanvasWH(int i, int i2) {
        this.canvasH = i2;
        this.canvasW = i;
    }

    public int getCanvasH() {
        return this.canvasH;
    }

    public int getCanvasW() {
        return this.canvasW;
    }

    public RectF getCopyRect() {
        return this.copyRect;
    }

    public void setCopyRect() {
        if (getRect() == null) {
            return;
        }
        this.copyRect = new RectF(getRect().left / this.canvasW, getRect().top / this.canvasH, getRect().right / this.canvasW, getRect().bottom / this.canvasH);
    }

    public boolean isAnimTest() {
        return this.isAnimTest;
    }

    public void setAnimTest(boolean z) {
        this.isAnimTest = z;
    }
}

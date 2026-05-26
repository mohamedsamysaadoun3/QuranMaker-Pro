package hazem.nurmontage.videoquran.model;

import android.graphics.Canvas;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import hazem.nurmontage.videoquran.entity_timeline.EntityQuranTimeline;

/* loaded from: classes2.dex */
public class TextEntity extends EntityView {
    private EntityQuranTimeline entityQuran;
    private boolean isVisible;
    private TextPaint paintAya = new TextPaint(1);
    private StaticLayout staticLayout;
    private String txt;
    private int viewWidth;
    private float x;
    private float y;

    @Override // hazem.nurmontage.videoquran.model.EntityView
    public void endAnimator() {
    }

    @Override // hazem.nurmontage.videoquran.model.EntityView
    public void postTranslate(float f, float f2) {
    }

    @Override // hazem.nurmontage.videoquran.model.EntityView
    public void scale(float f, int i, int i2) {
    }

    public void setTxt(String str) {
        this.txt = str;
        this.staticLayout = StaticLayout.Builder.obtain(str, 0, str.length(), this.paintAya, this.viewWidth).setAlignment(Layout.Alignment.ALIGN_CENTER).setLineSpacing(0.0f, 1.0f).setIncludePad(false).build();
    }

    private void createStaticLayout() {
        String str = this.txt;
        this.staticLayout = StaticLayout.Builder.obtain(str, 0, str.length(), this.paintAya, this.viewWidth).setAlignment(Layout.Alignment.ALIGN_CENTER).setLineSpacing(0.0f, 1.0f).setIncludePad(false).build();
    }

    public TextEntity(String str, float f, float f2, EntityQuranTimeline entityQuranTimeline) {
        this.txt = str;
        this.x = f;
        this.y = f2;
        setVisible(true);
        this.entityQuran = entityQuranTimeline;
    }

    public TextEntity(String str, float f, float f2, int i) {
        this.txt = str;
        this.x = f;
        this.y = f2;
        setVisible(true);
        this.viewWidth = i;
        this.paintAya.setColor(-1);
        this.paintAya.setTextSize(i * 0.06f);
        createStaticLayout();
    }

    public void update(int i, int i2) {
        this.y = i * 0.67f;
        this.viewWidth = i2;
        this.paintAya.setTextSize(i2 * 0.06f);
        createStaticLayout();
    }

    public StaticLayout getStaticLayout() {
        return this.staticLayout;
    }

    @Override // hazem.nurmontage.videoquran.model.EntityView
    public void setEntityQuran(EntityQuranTimeline entityQuranTimeline) {
        this.entityQuran = entityQuranTimeline;
    }

    public void draw(Canvas canvas) {
        if (this.staticLayout != null) {
            canvas.save();
            canvas.translate(this.x, this.y);
            this.staticLayout.draw(canvas);
            canvas.restore();
        }
    }

    public void singleDraw(Canvas canvas) {
        StaticLayout staticLayout = this.staticLayout;
        if (staticLayout != null) {
            staticLayout.draw(canvas);
        }
    }

    @Override // hazem.nurmontage.videoquran.model.EntityView
    public EntityQuranTimeline getEntityQuran() {
        return this.entityQuran;
    }

    @Override // hazem.nurmontage.videoquran.model.EntityView
    public boolean isVisible() {
        return this.isVisible;
    }

    @Override // hazem.nurmontage.videoquran.model.EntityView
    public void setVisible(boolean z) {
        this.isVisible = z;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public String getTxt() {
        return this.txt;
    }
}

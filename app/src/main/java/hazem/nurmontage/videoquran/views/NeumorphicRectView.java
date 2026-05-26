package hazem.nurmontage.videoquran.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/* loaded from: classes2.dex */
public class NeumorphicRectView extends View {
    private Paint backgroundPaint;
    private int baseColor;
    private float cornerRadius;
    private int darkShadowColor;
    private Paint darkShadowPaint;
    private int lightShadowColor;
    private Paint lightShadowPaint;
    private RectF rect;
    private float shadowOffset;

    public NeumorphicRectView(Context context) {
        super(context);
        this.cornerRadius = 40.0f;
        this.shadowOffset = 20.0f;
        this.baseColor = Color.parseColor("#398B89");
        this.lightShadowColor = Color.parseColor("#57A9A7");
        this.darkShadowColor = Color.parseColor("#1B6D6B");
        init();
    }

    public NeumorphicRectView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.cornerRadius = 40.0f;
        this.shadowOffset = 20.0f;
        this.baseColor = Color.parseColor("#398B89");
        this.lightShadowColor = Color.parseColor("#57A9A7");
        this.darkShadowColor = Color.parseColor("#1B6D6B");
        init();
    }

    public NeumorphicRectView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.cornerRadius = 40.0f;
        this.shadowOffset = 20.0f;
        this.baseColor = Color.parseColor("#398B89");
        this.lightShadowColor = Color.parseColor("#57A9A7");
        this.darkShadowColor = Color.parseColor("#1B6D6B");
        init();
    }

    private void init() {
        setLayerType(1, null);
        Paint paint = new Paint(1);
        this.backgroundPaint = paint;
        paint.setColor(this.baseColor);
        this.backgroundPaint.setStyle(Paint.Style.FILL);
        Paint paint2 = new Paint(1);
        this.lightShadowPaint = paint2;
        paint2.setColor(this.baseColor);
        this.lightShadowPaint.setStyle(Paint.Style.FILL);
        Paint paint3 = this.lightShadowPaint;
        float f = this.shadowOffset;
        paint3.setShadowLayer(f, -f, -f, this.lightShadowColor);
        Paint paint4 = new Paint(1);
        this.darkShadowPaint = paint4;
        paint4.setColor(this.baseColor);
        this.darkShadowPaint.setStyle(Paint.Style.FILL);
        Paint paint5 = this.darkShadowPaint;
        float f2 = this.shadowOffset;
        paint5.setShadowLayer(f2, f2, f2, this.darkShadowColor);
        this.rect = new RectF();
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        float f = this.shadowOffset;
        this.rect.set(f * 1.5f, f * 1.5f, i - (f * 1.5f), i2 - (f * 1.5f));
        this.backgroundPaint.setShader(new LinearGradient(this.rect.left, this.rect.top, this.rect.right, this.rect.bottom, Color.argb(255, (int) (Color.red(this.baseColor) * 1.1f), (int) (Color.green(this.baseColor) * 1.1f), (int) (Color.blue(this.baseColor) * 1.1f)), Color.argb(255, (int) (Color.red(this.baseColor) * 0.9f), (int) (Color.green(this.baseColor) * 0.9f), (int) (Color.blue(this.baseColor) * 0.9f)), Shader.TileMode.CLAMP));
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rectF = this.rect;
        float f = this.cornerRadius;
        canvas.drawRoundRect(rectF, f, f, this.lightShadowPaint);
        RectF rectF2 = this.rect;
        float f2 = this.cornerRadius;
        canvas.drawRoundRect(rectF2, f2, f2, this.darkShadowPaint);
        RectF rectF3 = this.rect;
        float f3 = this.cornerRadius;
        canvas.drawRoundRect(rectF3, f3, f3, this.backgroundPaint);
    }
}

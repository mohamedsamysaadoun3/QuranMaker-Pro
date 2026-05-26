package hazem.nurmontage.videoquran.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/* loaded from: classes2.dex */
public class EyeOpenView extends View {
    private Bitmap bitmap;
    private float centerX;
    private float centerY;
    private Paint clearPaint;
    private float currentRY;
    private Path eyePath;
    private RectF eyeRect;
    private Paint paint;
    private float radiusX;
    private float radiusYFull;
    private float wrapOffset;

    private float map(float f, float f2, float f3, float f4, float f5) {
        return f4 + (((f - f2) / (f3 - f2)) * (f5 - f4));
    }

    public EyeOpenView(Context context) {
        super(context);
        this.paint = new Paint(1);
        this.clearPaint = new Paint(1);
        this.eyePath = new Path();
        this.eyeRect = new RectF();
        this.currentRY = 0.0f;
        this.wrapOffset = 50.0f;
        init();
    }

    public EyeOpenView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.paint = new Paint(1);
        this.clearPaint = new Paint(1);
        this.eyePath = new Path();
        this.eyeRect = new RectF();
        this.currentRY = 0.0f;
        this.wrapOffset = 50.0f;
        init();
    }

    private void init() {
        this.clearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        invalidate();
    }

    public void startEyeOpenAnimation(long j) {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, this.radiusYFull);
        ofFloat.setDuration(j);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: hazem.nurmontage.videoquran.views.EyeOpenView$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                EyeOpenView.this.m1240x427c16e7(valueAnimator);
            }
        });
        ofFloat.setRepeatCount(5);
        ofFloat.start();
    }

    /* renamed from: lambda$startEyeOpenAnimation$0$hazem-nurmontage-videoquran-views-EyeOpenView, reason: not valid java name */
    /* synthetic */ void m1240x427c16e7(ValueAnimator valueAnimator) {
        this.currentRY = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        invalidate();
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        float f = i;
        this.centerX = f / 2.0f;
        float f2 = i2;
        this.centerY = f2 / 2.0f;
        this.radiusX = f * 0.4f;
        this.radiusYFull = f2 * 0.2f;
        super.onSizeChanged(i, i2, i3, i4);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap bitmap = this.bitmap;
        if (bitmap == null) {
            return;
        }
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, this.paint);
        this.eyePath.reset();
        RectF rectF = this.eyeRect;
        float f = this.centerX;
        float f2 = this.radiusX;
        float f3 = this.centerY;
        float f4 = this.currentRY;
        rectF.set(f - f2, f3 - f4, f + f2, f3 + f4);
        this.eyePath.addOval(this.eyeRect, Path.Direction.CW);
        canvas.saveLayer(null, null, 31);
        canvas.drawPath(this.eyePath, this.clearPaint);
        canvas.restore();
        float map = map(this.currentRY, 0.0f, this.radiusYFull, 1.0f, 0.0f);
        canvas.save();
        canvas.clipRect(0.0f, 0.0f, getWidth(), this.centerY - this.currentRY);
        canvas.scale(1.0f, map, this.centerX, this.centerY - this.currentRY);
        canvas.drawBitmap(this.bitmap, 0.0f, 0.0f, this.paint);
        canvas.restore();
        float map2 = map(this.currentRY, 0.0f, this.radiusYFull, 1.0f, 0.0f);
        canvas.save();
        canvas.clipRect(0.0f, this.centerY + this.currentRY, getWidth(), getHeight());
        canvas.scale(1.0f, map2, this.centerX, this.centerY + this.currentRY);
        canvas.drawBitmap(this.bitmap, 0.0f, 0.0f, this.paint);
        canvas.restore();
    }
}

package hazem.nurmontage.videoquran.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

/* loaded from: classes2.dex */
public class NeumorphicView extends View {
    private int accentColor;
    private int baseColor;
    private int darkShadowColor;
    private int iconColor;
    private int lightHighlightColor;
    private Paint paint;
    private RectF rectF;
    private int textColor;

    public NeumorphicView(Context context) {
        super(context);
        init();
    }

    public NeumorphicView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public NeumorphicView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        Paint paint = new Paint(1);
        this.paint = paint;
        paint.setStyle(Paint.Style.FILL);
        this.rectF = new RectF();
        setBaseThemeColor(Color.rgb(200, 200, 200));
    }

    public void setBaseThemeColor(int i) {
        this.baseColor = i;
        int red = Color.red(i);
        int green = Color.green(i);
        int blue = Color.blue(i);
        this.darkShadowColor = Color.argb(150, Math.max(0, red - 50), Math.max(0, green - 50), Math.max(0, blue - 50));
        this.lightHighlightColor = Color.argb(200, Math.min(255, red + 50), Math.min(255, green + 50), Math.min(255, blue + 50));
        this.accentColor = Color.rgb(Math.max(0, red - 30), Math.max(0, green - 30), Math.max(0, blue - 30));
        int rgb = Color.rgb(Math.max(0, red - 100), Math.max(0, green - 100), Math.max(0, blue - 100));
        this.textColor = rgb;
        this.iconColor = rgb;
        setBackgroundColor(this.baseColor);
        invalidate();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int dpToPx = (int) dpToPx(30.0f);
        float width = getWidth() - (dpToPx * 2);
        float f = dpToPx;
        drawNeumorphicRect(canvas, f, f, width, getHeight() - f, dpToPx(30.0f), this.baseColor, this.darkShadowColor, this.lightHighlightColor, dpToPx(10.0f), true);
        float dpToPx2 = dpToPx(100.0f);
        float width2 = getWidth() / 2.0f;
        float dpToPx3 = f + dpToPx(100.0f);
        drawNeumorphicCircle(canvas, width2, dpToPx3, dpToPx2, this.baseColor, this.lightHighlightColor, this.darkShadowColor, dpToPx(10.0f), false);
        this.paint.setColor(this.textColor);
        this.paint.setTextSize(dpToPx(30.0f));
        this.paint.setTypeface(Typeface.create("sans-serif-medium", 1));
        this.paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("الكَهْف", width2, dpToPx3 + (this.paint.getTextSize() / 3.0f), this.paint);
        this.paint.setTextSize(dpToPx(20.0f));
        this.paint.setTypeface(Typeface.create("sans-serif-light", 0));
        float f2 = dpToPx3 + dpToPx2;
        canvas.drawText("Al- Kahfi", getWidth() / 2.0f, f2 + dpToPx(40.0f), this.paint);
        this.paint.setTextSize(dpToPx(16.0f));
        canvas.drawText("Ustadz : Muzammil Hasballah", getWidth() / 2.0f, f2 + dpToPx(65.0f), this.paint);
        float dpToPx4 = width - dpToPx(60.0f);
        float dpToPx5 = dpToPx(20.0f);
        float dpToPx6 = f + dpToPx(30.0f);
        float dpToPx7 = f2 + dpToPx(115.0f);
        float dpToPx8 = dpToPx(10.0f);
        drawNeumorphicRect(canvas, dpToPx6, dpToPx7, dpToPx4, dpToPx5, dpToPx8, this.baseColor, this.darkShadowColor, this.lightHighlightColor, dpToPx(5.0f), true);
        this.paint.setColor(this.accentColor);
        this.rectF.set(dpToPx6, dpToPx7, dpToPx6 + ((float) (dpToPx4 * 0.6d)), dpToPx7 + dpToPx5);
        canvas.drawRoundRect(this.rectF, dpToPx8, dpToPx8, this.paint);
        float dpToPx9 = dpToPx(60.0f);
        float dpToPx10 = dpToPx(20.0f);
        float dpToPx11 = dpToPx7 + dpToPx(70.0f);
        float dpToPx12 = dpToPx(20.0f);
        float width3 = (((getWidth() / 2.0f) - dpToPx9) - dpToPx12) - dpToPx(40.0f);
        drawNeumorphicRect(canvas, width3, dpToPx11, dpToPx9, dpToPx9, dpToPx10, this.baseColor, this.darkShadowColor, this.lightHighlightColor, dpToPx(8.0f), true);
        this.paint.setColor(this.iconColor);
        canvas.drawRect(width3 + dpToPx(20.0f), dpToPx11 + dpToPx(20.0f), width3 + dpToPx(25.0f), dpToPx11 + dpToPx(40.0f), this.paint);
        canvas.drawPath(createTrianglePath(width3 + dpToPx(25.0f), dpToPx11 + dpToPx(30.0f), dpToPx(20.0f), true), this.paint);
        float width4 = (getWidth() / 2.0f) - (dpToPx9 / 2.0f);
        drawNeumorphicRect(canvas, width4, dpToPx11, dpToPx9, dpToPx9, dpToPx10, this.baseColor, this.darkShadowColor, this.lightHighlightColor, dpToPx(8.0f), true);
        this.paint.setColor(this.iconColor);
        canvas.drawRect(width4 + dpToPx(20.0f), dpToPx11 + dpToPx(20.0f), width4 + dpToPx(30.0f), dpToPx11 + dpToPx(40.0f), this.paint);
        canvas.drawRect(width4 + dpToPx(35.0f), dpToPx11 + dpToPx(20.0f), width4 + dpToPx(45.0f), dpToPx11 + dpToPx(40.0f), this.paint);
        float width5 = (getWidth() / 2.0f) + dpToPx12 + dpToPx(40.0f);
        drawNeumorphicRect(canvas, width5, dpToPx11, dpToPx9, dpToPx9, dpToPx10, this.baseColor, this.darkShadowColor, this.lightHighlightColor, dpToPx(8.0f), true);
        this.paint.setColor(this.iconColor);
        canvas.drawRect(width5 + dpToPx(40.0f), dpToPx11 + dpToPx(20.0f), width5 + dpToPx(45.0f), dpToPx11 + dpToPx(40.0f), this.paint);
        canvas.drawPath(createTrianglePath(width5 + dpToPx(35.0f), dpToPx11 + dpToPx(30.0f), dpToPx(20.0f), false), this.paint);
        this.paint.setTextSize(dpToPx(12.0f));
        this.paint.setTypeface(Typeface.create("sans-serif-light", 0));
        float measureText = this.paint.measureText("Created by : story_rilla");
        float height = (getHeight() - dpToPx) - dpToPx(20.0f);
        float dpToPx13 = measureText + dpToPx(40.0f);
        float dpToPx14 = dpToPx(40.0f);
        drawNeumorphicRect(canvas, (getWidth() / 2.0f) - (dpToPx13 / 2.0f), (getHeight() - dpToPx) - dpToPx14, dpToPx13, dpToPx14, dpToPx(15.0f), this.baseColor, this.darkShadowColor, this.lightHighlightColor, dpToPx(5.0f), true);
        this.paint.setColor(this.iconColor);
        canvas.drawText("Created by : story_rilla", getWidth() / 2.0f, height + dpToPx(25.0f), this.paint);
        float dpToPx15 = dpToPx(40.0f);
        float dpToPx16 = dpToPx(20.0f);
        drawNeumorphicRect(canvas, dpToPx16, dpToPx16, dpToPx15, dpToPx15, dpToPx(15.0f), this.baseColor, this.darkShadowColor, this.lightHighlightColor, dpToPx(5.0f), true);
        this.paint.setColor(this.iconColor);
        Path path = new Path();
        path.moveTo(dpToPx(25.0f) + dpToPx16, dpToPx(15.0f) + dpToPx16);
        path.lineTo(dpToPx(15.0f) + dpToPx16, dpToPx(25.0f) + dpToPx16);
        path.lineTo(dpToPx(25.0f) + dpToPx16, dpToPx(35.0f) + dpToPx16);
        path.close();
        canvas.drawPath(path, this.paint);
        drawNeumorphicRect(canvas, (getWidth() - dpToPx16) - dpToPx15, dpToPx16, dpToPx15, dpToPx15, dpToPx(15.0f), this.baseColor, this.darkShadowColor, this.lightHighlightColor, dpToPx(5.0f), true);
        this.paint.setColor(this.iconColor);
        canvas.drawCircle(((getWidth() - dpToPx16) - dpToPx15) + dpToPx(20.0f), dpToPx(20.0f) + dpToPx16, dpToPx(5.0f), this.paint);
        canvas.drawLine(dpToPx(20.0f) + ((getWidth() - dpToPx16) - dpToPx15), dpToPx16 + dpToPx(20.0f), dpToPx(35.0f) + ((getWidth() - dpToPx16) - dpToPx15), dpToPx16 + dpToPx(5.0f), this.paint);
        canvas.drawLine(dpToPx(20.0f) + ((getWidth() - dpToPx16) - dpToPx15), dpToPx16 + dpToPx(20.0f), dpToPx(35.0f) + ((getWidth() - dpToPx16) - dpToPx15), dpToPx16 + dpToPx(35.0f), this.paint);
        float f3 = dpToPx16 + dpToPx15;
        drawNeumorphicRect(canvas, f3 + dpToPx(10.0f), dpToPx16, dpToPx15, dpToPx15, dpToPx(15.0f), this.baseColor, this.darkShadowColor, this.lightHighlightColor, dpToPx(5.0f), true);
        this.paint.setColor(this.iconColor);
        canvas.drawCircle(dpToPx(10.0f) + f3 + dpToPx(15.0f), dpToPx(10.0f) + dpToPx16 + dpToPx(20.0f), dpToPx(5.0f), this.paint);
        canvas.drawLine(dpToPx(10.0f) + f3 + dpToPx(20.0f), dpToPx(10.0f) + dpToPx16 + dpToPx(10.0f), dpToPx(10.0f) + f3 + dpToPx(20.0f), dpToPx(10.0f) + dpToPx16 + dpToPx(20.0f), this.paint);
        canvas.drawLine(dpToPx(10.0f) + f3 + dpToPx(20.0f), dpToPx(10.0f) + dpToPx16 + dpToPx(10.0f), dpToPx(35.0f) + f3 + dpToPx(10.0f), dpToPx16 + dpToPx(10.0f) + dpToPx(10.0f), this.paint);
    }

    private void drawNeumorphicRect(Canvas canvas, float f, float f2, float f3, float f4, float f5, int i, int i2, int i3, float f6, boolean z) {
        this.paint.setColor(z ? i2 : i3);
        float f7 = f + f3;
        float f8 = f2 + f4;
        this.rectF.set(f + f6, f2 + f6, f7 + f6, f8 + f6);
        canvas.drawRoundRect(this.rectF, f5, f5, this.paint);
        this.paint.setColor(z ? i3 : i2);
        this.rectF.set(f - f6, f2 - f6, f7 - f6, f8 - f6);
        canvas.drawRoundRect(this.rectF, f5, f5, this.paint);
        this.paint.setColor(i);
        this.rectF.set(f, f2, f7, f8);
        canvas.drawRoundRect(this.rectF, f5, f5, this.paint);
    }

    private void drawNeumorphicCircle(Canvas canvas, float f, float f2, float f3, int i, int i2, int i3, float f4, boolean z) {
        this.paint.setColor(z ? i2 : i3);
        canvas.drawCircle(f + f4, f2 + f4, f3, this.paint);
        Paint paint = this.paint;
        if (z) {
            i2 = i3;
        }
        paint.setColor(i2);
        canvas.drawCircle(f - f4, f2 - f4, f3, this.paint);
        this.paint.setColor(i);
        canvas.drawCircle(f, f2, f3, this.paint);
    }

    private float dpToPx(float f) {
        return f * getResources().getDisplayMetrics().density;
    }

    private Path createTrianglePath(float f, float f2, float f3, boolean z) {
        Path path = new Path();
        if (z) {
            float f4 = f3 / 2.0f;
            float f5 = f + f4;
            path.moveTo(f5, f2 - f4);
            path.lineTo(f - f4, f2);
            path.lineTo(f5, f2 + f4);
        } else {
            float f6 = f3 / 2.0f;
            float f7 = f - f6;
            path.moveTo(f7, f2 - f6);
            path.lineTo(f + f6, f2);
            path.lineTo(f7, f2 + f6);
        }
        path.close();
        return path;
    }
}

package hazem.nurmontage.videoquran.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import androidx.core.view.ViewCompat;

/* loaded from: classes2.dex */
public class CassetteView extends View {
    private String labelText;
    private Paint paintAccent;
    private Paint paintBody;
    private Paint paintFloor;
    private Paint paintHole;
    private Paint paintLabel;
    private Paint paintReel;
    private Paint paintScrew;
    private Paint paintShadow;
    private Paint paintText;

    public CassetteView(Context context) {
        super(context);
        this.labelText = "Titanium – David Guetta Ft. Sia";
        init();
    }

    public CassetteView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.labelText = "Titanium – David Guetta Ft. Sia";
        init();
    }

    public CassetteView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.labelText = "Titanium – David Guetta Ft. Sia";
        init();
    }

    private void init() {
        Paint paint = new Paint(1);
        this.paintBody = paint;
        paint.setColor(Color.parseColor("#E74C3C"));
        Paint paint2 = new Paint(1);
        this.paintShadow = paint2;
        paint2.setColor(Color.parseColor("#A93226"));
        Paint paint3 = new Paint(1);
        this.paintLabel = paint3;
        paint3.setColor(Color.parseColor("#FAE5D3"));
        Paint paint4 = new Paint(1);
        this.paintReel = paint4;
        paint4.setColor(ViewCompat.MEASURED_STATE_MASK);
        Paint paint5 = new Paint(1);
        this.paintHole = paint5;
        paint5.setColor(Color.parseColor("#5DADE2"));
        Paint paint6 = new Paint(1);
        this.paintAccent = paint6;
        paint6.setColor(Color.parseColor("#E67E22"));
        Paint paint7 = new Paint(1);
        this.paintScrew = paint7;
        paint7.setColor(-1);
        Paint paint8 = new Paint(1);
        this.paintFloor = paint8;
        paint8.setColor(Color.parseColor("#6EC6E9"));
        Paint paint9 = new Paint(1);
        this.paintText = paint9;
        paint9.setColor(ViewCompat.MEASURED_STATE_MASK);
        this.paintText.setTextSize(36.0f);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        canvas.drawColor(Color.parseColor("#87CEEB"));
        Path path = new Path();
        float f = height;
        float f2 = 0.7f * f;
        path.moveTo(0.0f, f2);
        float f3 = width;
        path.lineTo(f3, f2);
        path.lineTo(f3, f);
        path.lineTo(0.0f, f);
        path.close();
        canvas.drawPath(path, this.paintFloor);
        RectF rectF = new RectF(0.12f * f3, 0.32f * f, 0.92f * f3, 0.72f * f);
        float f4 = f3 * 0.1f;
        float f5 = f * 0.3f;
        float f6 = 0.9f * f3;
        RectF rectF2 = new RectF(f4, f5, f6, f2);
        canvas.drawRoundRect(rectF, 20.0f, 20.0f, this.paintShadow);
        canvas.drawRoundRect(rectF2, 20.0f, 20.0f, this.paintBody);
        Path path2 = new Path();
        path2.moveTo(f4, f5);
        path2.lineTo(f6, f5);
        float f7 = f3 * 0.85f;
        float f8 = f * 0.35f;
        path2.lineTo(f7, f8);
        float f9 = 0.15f * f3;
        path2.lineTo(f9, f8);
        path2.close();
        canvas.drawPath(path2, this.paintAccent);
        Path path3 = new Path();
        path3.moveTo(f4, f2);
        path3.lineTo(f6, f2);
        float f10 = f * 0.65f;
        path3.lineTo(f7, f10);
        path3.lineTo(f9, f10);
        path3.close();
        canvas.drawPath(path3, this.paintAccent);
        float f11 = 0.2f * f3;
        float f12 = 0.8f * f3;
        canvas.drawRect(new RectF(f11, 0.36f * f, f12, 0.44f * f), this.paintLabel);
        canvas.drawText(this.labelText, (f3 - this.paintText.measureText(this.labelText)) / 2.0f, 0.415f * f, this.paintText);
        canvas.drawRect(new RectF(f11, 0.48f * f, f12, 0.62f * f), this.paintLabel);
        float f13 = f * 0.1f;
        float f14 = f13 * 0.3f;
        float f15 = f13 * 0.45f;
        float f16 = f * 0.55f;
        float f17 = 0.35f * f3;
        float f18 = f3 * 0.65f;
        canvas.drawCircle(f17, f16, f13, this.paintReel);
        drawInnerGear(canvas, f17, f16, f14, f15, 8, this.paintHole);
        canvas.drawCircle(f18, f16, f13, this.paintReel);
        drawInnerGear(canvas, f18, f16, f14, f15, 8, this.paintHole);
        float f19 = f3 * 0.015f;
        float f20 = 0.34f * f;
        canvas.drawCircle(f9, f20, f19, this.paintScrew);
        canvas.drawCircle(f7, f20, f19, this.paintScrew);
        float f21 = f * 0.66f;
        canvas.drawCircle(f9, f21, f19, this.paintScrew);
        canvas.drawCircle(f7, f21, f19, this.paintScrew);
    }

    private void drawInnerGear(Canvas canvas, float f, float f2, float f3, float f4, int i, Paint paint) {
        float f5;
        float f6;
        Path path = new Path();
        int i2 = i * 2;
        double d = 6.283185307179586d / i2;
        for (int i3 = 0; i3 < i2; i3++) {
            double d2 = i3 * d;
            if (i3 % 2 == 0) {
                f5 = f;
                f6 = f3;
            } else {
                f5 = f;
                f6 = f4;
            }
            double d3 = f6;
            float cos = (float) (f5 + (Math.cos(d2) * d3));
            float sin = (float) (f2 + (d3 * Math.sin(d2)));
            if (i3 == 0) {
                path.moveTo(cos, sin);
            } else {
                path.lineTo(cos, sin);
            }
        }
        path.close();
        canvas.drawPath(path, paint);
    }
}

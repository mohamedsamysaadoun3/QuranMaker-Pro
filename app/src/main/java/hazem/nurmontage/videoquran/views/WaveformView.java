package hazem.nurmontage.videoquran.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/* loaded from: classes2.dex */
public class WaveformView extends View {
    private int[] amplitudes;
    private OnWaveformClickListener listener;
    private Paint paint;
    private float progress;

    public interface OnWaveformClickListener {
        void onProgressChanged(float f);
    }

    public void setOnWaveformClickListener(OnWaveformClickListener onWaveformClickListener) {
        this.listener = onWaveformClickListener;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0 || motionEvent.getAction() == 2) {
            float x = motionEvent.getX() / getWidth();
            if (x < 0.0f) {
                x = 0.0f;
            }
            if (x > 1.0f) {
                x = 1.0f;
            }
            setProgress(x);
            OnWaveformClickListener onWaveformClickListener = this.listener;
            if (onWaveformClickListener == null) {
                return true;
            }
            onWaveformClickListener.onProgressChanged(x);
            return true;
        }
        return super.onTouchEvent(motionEvent);
    }

    public WaveformView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Paint paint = new Paint();
        this.paint = paint;
        this.amplitudes = new int[]{30, 40, 60, 80, 50, 90, 100, 70, 40, 60, 80, 50, 30, 50, 70, 90, 60, 40};
        this.progress = 0.0f;
        paint.setAntiAlias(true);
        this.paint.setStyle(Paint.Style.FILL);
    }

    public void setProgress(float f) {
        this.progress = f;
        invalidate();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float width = getWidth();
        float height = getHeight();
        float length = width / (this.amplitudes.length * 2);
        int i = 0;
        while (true) {
            if (i >= this.amplitudes.length) {
                return;
            }
            float f = (this.amplitudes[i] / 100.0f) * height;
            float f2 = i;
            float f3 = f2 * (length + length);
            float f4 = (height - f) / 2.0f;
            float length2 = f2 / this.amplitudes.length;
            float f5 = this.progress;
            if (f5 > 0.0f && length2 < f5) {
                this.paint.setColor(-1);
            } else {
                this.paint.setColor(-12303292);
            }
            canvas.drawRoundRect(f3, f4, f3 + length, f4 + f, 5.0f, 5.0f, this.paint);
            i++;
        }
    }
}

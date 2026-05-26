package hazem.nurmontage.videoquran.Utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;

/* loaded from: classes2.dex */
public class WaveformBitmapRenderer {
    private float[] amps;
    private int bitmapHeight;
    private int bitmapWidth;
    private Paint paint;
    private Bitmap waveformBitmap;

    public void drawOverlay(Canvas canvas, RectF rectF, float f, float f2, Paint paint) {
    }

    public WaveformBitmapRenderer(float[] fArr, int i, int i2, int i3) {
        this.amps = fArr;
        this.bitmapWidth = i;
        this.bitmapHeight = i2;
        Paint paint = new Paint(1);
        this.paint = paint;
        paint.setColor(i3);
        this.paint.setAlpha(100);
        generateBitmap();
    }

    private void generateBitmap() {
        float[] fArr = this.amps;
        if (fArr == null || fArr.length == 0) {
            return;
        }
        this.waveformBitmap = Bitmap.createBitmap(this.bitmapWidth, this.bitmapHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(this.waveformBitmap);
        float f = this.bitmapHeight * 0.85f;
        float f2 = 0.0f;
        for (float f3 : this.amps) {
            f2 = Math.max(f2, f3);
        }
        float f4 = f2 < 0.01f ? 0.01f : f2;
        int i = 0;
        while (true) {
            int i2 = this.bitmapWidth;
            if (i >= i2) {
                return;
            }
            float f5 = i;
            float[] fArr2 = this.amps;
            int length = (int) ((f5 / i2) * fArr2.length);
            if (length >= fArr2.length) {
                length = fArr2.length - 1;
            }
            float f6 = (fArr2[length] / f4) * f;
            int i3 = this.bitmapHeight;
            canvas.drawLine(f5, i3, f5, i3 - f6, this.paint);
            i++;
        }
    }

    public void draw(Canvas canvas, RectF rectF, float f, float f2) {
        if (this.waveformBitmap == null) {
            return;
        }
        float f3 = rectF.left - (f2 * f);
        Matrix matrix = new Matrix();
        matrix.postScale(f, 1.0f);
        matrix.postTranslate(f3, rectF.top);
        canvas.drawBitmap(this.waveformBitmap, matrix, null);
    }

    public void setColor(int i) {
        this.paint.setColor(i);
        generateBitmap();
    }

    public Bitmap getBitmap() {
        return this.waveformBitmap;
    }

    public void release() {
        Bitmap bitmap = this.waveformBitmap;
        if (bitmap != null) {
            if (!bitmap.isRecycled()) {
                this.waveformBitmap.recycle();
            }
            this.waveformBitmap = null;
        }
    }
}

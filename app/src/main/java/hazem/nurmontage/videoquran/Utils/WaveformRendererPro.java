package hazem.nurmontage.videoquran.Utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/* loaded from: classes2.dex */
public class WaveformRendererPro {
    public static Bitmap drawWave(int i, int i2, float[] fArr, int i3, float f, float f2, float f3) {
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint(1);
        paint.setColor(i3);
        paint.setAlpha(100);
        float f4 = i;
        float f5 = i2;
        int length = (int) (fArr.length * f3);
        if (length < 1) {
            return createBitmap;
        }
        float f6 = length;
        float f7 = f4 / f6;
        float f8 = f7 - f;
        if (f8 < 1.0f) {
            f8 = 1.0f;
        }
        float f9 = 0.0f;
        float f10 = 0.0f;
        for (float f11 : fArr) {
            f10 = Math.max(f10, f11);
        }
        if (f10 < 0.01f) {
            f10 = 0.01f;
        }
        for (int i4 = 0; i4 < length; i4++) {
            canvas.drawRoundRect(new RectF(f9, canvas.getHeight() - ((fArr[(int) ((i4 / f6) * fArr.length)] / f10) * f5), f9 + f8, canvas.getHeight()), f2, f2, paint);
            f9 += f7;
        }
        return createBitmap;
    }

    public static void drawWaveInRect(Canvas canvas, RectF rectF, float[] fArr, int i, float f, float f2, float f3) {
        Paint paint = new Paint(1);
        paint.setColor(i);
        paint.setAlpha(100);
        float width = rectF.width();
        float height = rectF.height() * 0.85f;
        int length = (int) (fArr.length * f3);
        if (length < 1) {
            return;
        }
        float f4 = length;
        float f5 = width / f4;
        float f6 = f5 - f;
        if (f6 < 1.0f) {
            f6 = 1.0f;
        }
        float f7 = 0.0f;
        for (float f8 : fArr) {
            f7 = Math.max(f7, f8);
        }
        if (f7 < 0.01f) {
            f7 = 0.01f;
        }
        float f9 = rectF.left;
        for (int i2 = 0; i2 < length; i2++) {
            canvas.drawRoundRect(new RectF(f9, rectF.bottom - ((fArr[(int) ((i2 / f4) * fArr.length)] / f7) * height), f9 + f6, rectF.bottom), f2, f2, paint);
            f9 += f5;
        }
    }

    public static void drawWaveProportional(Canvas canvas, RectF rectF, float[] fArr, int i, float f, float f2, float f3, float f4, float f5) {
        if (fArr == null || fArr.length == 0) {
            return;
        }
        Paint paint = new Paint(1);
        paint.setColor(i);
        paint.setAlpha(100);
        float width = rectF.width();
        float height = rectF.height() * 0.85f;
        float f6 = (f5 + f) * f3;
        int i2 = (int) (f4 / f6);
        if (i2 < 0) {
            i2 = 0;
        }
        if (i2 >= fArr.length) {
            i2 = fArr.length - 1;
        }
        int i3 = ((int) (width / f6)) + 2 + i2;
        if (i3 > fArr.length) {
            i3 = fArr.length;
        }
        float f7 = 0.0f;
        for (float f8 : fArr) {
            if (f8 > f7) {
                f7 = f8;
            }
        }
        if (f7 < 0.01f) {
            f7 = 0.01f;
        }
        float f9 = rectF.left - (f4 % f6);
        int i4 = i3 - i2;
        for (int i5 = 0; i5 < i4; i5++) {
            int length = ((int) ((i5 / i4) * (fArr.length - i2))) + i2;
            if (length >= fArr.length) {
                length = fArr.length - 1;
            }
            canvas.drawRoundRect(new RectF(f9, rectF.bottom - ((fArr[length] / f7) * height), (f5 * f3) + f9, rectF.bottom), f2, f2, paint);
            f9 += f6;
            if (f9 > rectF.right) {
                return;
            }
        }
    }

    public static void drawWaveInRect(Canvas canvas, RectF rectF, float[] fArr, int i, float f, float f2, float f3, float f4, float f5) {
        Paint paint = new Paint(1);
        paint.setColor(i);
        paint.setAlpha(100);
        float height = rectF.height() * 0.85f;
        float f6 = f4 * f3;
        float f7 = (f4 + f) * f3;
        int i2 = (int) (f5 / f7);
        if (i2 < 0) {
            i2 = 0;
        }
        int width = ((int) (rectF.width() / f7)) + 2 + i2;
        if (width > fArr.length) {
            width = fArr.length;
        }
        float f8 = rectF.left;
        float f9 = 0.0f;
        for (float f10 : fArr) {
            f9 = Math.max(f9, f10);
        }
        if (f9 < 0.01f) {
            f9 = 0.01f;
        }
        while (i2 < width) {
            canvas.drawRoundRect(new RectF(f8, rectF.bottom - ((fArr[i2] / f9) * height), f8 + f6, rectF.bottom), f2, f2, paint);
            f8 += f7;
            i2++;
        }
    }

    public static void drawWaveInRect(Canvas canvas, RectF rectF, float[] fArr, int i, float f, float f2, float f3, int i2) {
        if (fArr == null || fArr.length == 0) {
            return;
        }
        Paint paint = new Paint(1);
        paint.setColor(i);
        paint.setAlpha(100);
        float width = rectF.width();
        float height = rectF.height() * 0.85f;
        int length = (int) (fArr.length * f3);
        if (length < 1) {
            return;
        }
        int i3 = i2 < 0 ? 0 : i2;
        if (i3 > fArr.length - length) {
            i3 = fArr.length - length;
        }
        float f4 = width / length;
        float max = Math.max(1.0f, f4 - f);
        float f5 = rectF.left;
        float f6 = 0.0f;
        for (int i4 = i3; i4 < i3 + length; i4++) {
            f6 = Math.max(f6, fArr[i4]);
        }
        if (f6 < 0.01f) {
            f6 = 0.01f;
        }
        RectF rectF2 = new RectF();
        for (int i5 = 0; i5 < length; i5++) {
            int i6 = i3 + i5;
            if (i6 >= fArr.length) {
                return;
            }
            rectF2.set(f5, rectF.bottom - ((fArr[i6] / f6) * height), f5 + max, rectF.bottom);
            canvas.drawRoundRect(rectF2, f2, f2, paint);
            f5 += f4;
        }
    }

    public static Bitmap drawWaveformBottom(float[] fArr, int i, int i2, int i3, int i4, float f, int i5) {
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint(1);
        paint.setColor(i3);
        float f2 = 0.0f;
        for (float f3 : fArr) {
            f2 = Math.max(f2, f3);
        }
        if (f2 < 0.01f) {
            f2 = 0.01f;
        }
        int i6 = 0;
        for (float f4 : fArr) {
            canvas.drawRoundRect(new RectF(i6, i2 - ((int) ((f4 / f2) * i2)), i6 + i5, i2), f, f, paint);
            i6 += i5 + i4;
        }
        return createBitmap;
    }
}

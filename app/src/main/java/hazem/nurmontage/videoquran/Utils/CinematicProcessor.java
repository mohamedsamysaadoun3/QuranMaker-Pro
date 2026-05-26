package hazem.nurmontage.videoquran.Utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;

/* loaded from: classes2.dex */
public class CinematicProcessor {
    public static Bitmap applyCinematicEffect(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        Bitmap copy = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(copy);
        applyColorGrade(canvas, copy, new Paint(1));
        applyVignette(canvas, copy);
        return copy;
    }

    public static Bitmap createGlassRect(Bitmap bitmap, int i) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint(1);
        float f = i;
        RectF rectF = new RectF(f, f, bitmap.getWidth() - i, bitmap.getHeight() - i);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(-1);
        canvas.drawRoundRect(rectF, 40.0f, 40.0f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        paint.setXfermode(null);
        return createBitmap;
    }

    private static void applyColorGrade(Canvas canvas, Bitmap bitmap, Paint paint) {
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.set(new float[]{1.1f, 0.1f, 0.0f, 0.0f, -10.0f, 0.0f, 1.0f, 0.0f, 0.0f, -10.0f, 0.0f, 0.1f, 1.2f, 0.0f, -10.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f});
        ColorMatrix colorMatrix2 = new ColorMatrix();
        colorMatrix2.setSaturation(0.85f);
        colorMatrix.postConcat(colorMatrix2);
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
    }

    private static void applyVignette(Canvas canvas, Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float f = width;
        float f2 = height;
        RadialGradient radialGradient = new RadialGradient(f / 2.0f, f2 / 2.0f, (float) (Math.sqrt(Math.pow(width, 2.0d) + Math.pow(height, 2.0d)) * 0.7d), new int[]{0, 0, -1728053248}, new float[]{0.0f, 0.4f, 1.0f}, Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(radialGradient);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        canvas.drawRect(0.0f, 0.0f, f, f2, paint);
    }
}

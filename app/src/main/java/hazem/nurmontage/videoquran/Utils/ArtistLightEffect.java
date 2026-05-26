package hazem.nurmontage.videoquran.Utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Shader;

/* loaded from: classes2.dex */
public class ArtistLightEffect {
    public static Bitmap apply(Bitmap bitmap, float f, float f2) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
        ColorMatrix colorMatrix = new ColorMatrix(new float[]{1.05f, 0.0f, 0.0f, 0.0f, -6.0f, 0.0f, 1.02f, 0.0f, 0.0f, -4.0f, 0.0f, 0.0f, 0.95f, 0.0f, 10.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f});
        Paint paint = new Paint(1);
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(createBitmap, 0.0f, 0.0f, paint);
        float f3 = width;
        float f4 = height;
        int saveLayer = canvas.saveLayer(0.0f, 0.0f, f3, f4, null);
        RadialGradient radialGradient = new RadialGradient(f, f2, Math.max(width, height) * 0.45f, new int[]{Color.parseColor("#8844FFAA"), Color.parseColor("#33226655"), 0}, new float[]{0.0f, 0.55f, 1.0f}, Shader.TileMode.CLAMP);
        Paint paint2 = new Paint(1);
        paint2.setShader(radialGradient);
        paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.OVERLAY));
        canvas.drawRect(0.0f, 0.0f, f3, f4, paint2);
        canvas.restoreToCount(saveLayer);
        int saveLayer2 = canvas.saveLayer(0.0f, 0.0f, f3, f4, null);
        RadialGradient radialGradient2 = new RadialGradient(f, f2, Math.max(width, height) * 0.25f, new int[]{Color.parseColor("#5533FFAA"), 0}, (float[]) null, Shader.TileMode.CLAMP);
        Paint paint3 = new Paint(1);
        paint3.setShader(radialGradient2);
        paint3.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.ADD));
        canvas.drawRect(0.0f, 0.0f, f3, f4, paint3);
        canvas.restoreToCount(saveLayer2);
        int saveLayer3 = canvas.saveLayer(0.0f, 0.0f, f3, f4, null);
        RadialGradient radialGradient3 = new RadialGradient(f3 / 2.0f, f4 / 2.0f, Math.max(width, height), new int[]{0, Color.parseColor("#44000000")}, new float[]{0.6f, 1.0f}, Shader.TileMode.CLAMP);
        Paint paint4 = new Paint(1);
        paint4.setShader(radialGradient3);
        paint4.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
        canvas.drawRect(0.0f, 0.0f, f3, f4, paint4);
        canvas.restoreToCount(saveLayer3);
        return createBitmap;
    }
}

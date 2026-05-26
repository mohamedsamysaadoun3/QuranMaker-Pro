package hazem.nurmontage.videoquran.Utils;

import android.graphics.LinearGradient;
import android.graphics.RectF;
import android.graphics.Shader;

public class CreateGradient {
    public static LinearGradient createLinearGradientWithAngle(RectF rectF, float f, int[] iArr, float[] fArr) {
        if (rectF == null) {
            rectF = new RectF(0, 0, 1, 1);
        }
        if (iArr == null || iArr.length == 0) {
            iArr = new int[]{0xFF000000, 0xFFFFFFFF};
        }
        if (fArr == null || fArr.length == 0) {
            fArr = new float[]{0.0f, 1.0f};
        }
        // Ensure float arrays match int arrays length
        if (fArr.length != iArr.length) {
            fArr = new float[iArr.length];
            float step = 1.0f / (iArr.length - 1 > 0 ? iArr.length - 1 : 1);
            for (int i = 0; i < iArr.length; i++) {
                fArr[i] = i * step;
            }
        }
        double radians = Math.toRadians(f);
        float width = rectF.width() / 2.0f;
        float height = rectF.height() / 2.0f;
        float centerX = rectF.centerX();
        float centerY = rectF.centerY();
        float hypot = (float) Math.hypot(width, height);
        // Avoid degenerate gradient when hypot is 0
        if (hypot == 0f) {
            hypot = 1f;
        }
        float cos = ((float) Math.cos(radians)) * hypot;
        float sin = ((float) Math.sin(radians)) * hypot;
        return new LinearGradient(centerX - cos, centerY - sin, centerX + cos, centerY + sin, iArr, fArr, Shader.TileMode.CLAMP);
    }
}

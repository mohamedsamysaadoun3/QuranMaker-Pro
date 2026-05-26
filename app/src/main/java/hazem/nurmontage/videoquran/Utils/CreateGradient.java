package hazem.nurmontage.videoquran.Utils;

import android.graphics.LinearGradient;
import android.graphics.RectF;
import android.graphics.Shader;

/* loaded from: classes2.dex */
public class CreateGradient {
    public static LinearGradient createLinearGradientWithAngle(RectF rectF, float f, int[] iArr, float[] fArr) {
        double radians = Math.toRadians(f);
        float width = rectF.width() / 2.0f;
        float height = rectF.height() / 2.0f;
        float centerX = rectF.centerX();
        float centerY = rectF.centerY();
        float hypot = (float) Math.hypot(width, height);
        float cos = ((float) Math.cos(radians)) * hypot;
        float sin = ((float) Math.sin(radians)) * hypot;
        return new LinearGradient(centerX - cos, centerY - sin, centerX + cos, centerY + sin, iArr, fArr, Shader.TileMode.CLAMP);
    }
}

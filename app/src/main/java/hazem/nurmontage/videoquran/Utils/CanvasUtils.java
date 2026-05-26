package hazem.nurmontage.videoquran.Utils;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;

/* loaded from: classes2.dex */
public class CanvasUtils {
    public static Path drawCustomRoundedRect(Canvas canvas, float f, float f2, float f3, float f4, float f5, float f6) {
        Path path = new Path();
        float f7 = (f3 - f) / 2.0f;
        float f8 = (f4 - f2) / 2.0f;
        float min = Math.min(f5, Math.min(f7, f8));
        float min2 = Math.min(f6, Math.min(f7, f8));
        path.moveTo(f, f2);
        path.lineTo(f3 - min, f2);
        if (min > 0.0f) {
            float f9 = min * 2.0f;
            path.arcTo(new RectF(f3 - f9, f2, f3, f9 + f2), -90.0f, 90.0f, false);
        } else {
            path.lineTo(f3, f2);
        }
        path.lineTo(f3, f4 - min2);
        if (min2 > 0.0f) {
            float f10 = min2 * 2.0f;
            path.arcTo(new RectF(f3 - f10, f4 - f10, f3, f4), 0.0f, 90.0f, false);
        } else {
            path.lineTo(f3, f4);
        }
        path.lineTo(f, f4);
        path.close();
        return path;
    }
}

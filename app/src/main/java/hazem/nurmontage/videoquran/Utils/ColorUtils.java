package hazem.nurmontage.videoquran.Utils;

import android.graphics.Bitmap;
import android.graphics.Color;
import androidx.core.view.ViewCompat;

/* loaded from: classes2.dex */
public class ColorUtils {
    private static float mapHueToRange(float f, float f2, float f3, float f4, float f5) {
        if (f < f2) {
            return f4;
        }
        if (f > f3) {
            return f5;
        }
        float f6 = f3 - f2;
        return f6 == 0.0f ? (f4 + f5) / 2.0f : (((f - f2) * (f5 - f4)) / f6) + f4;
    }

    public static int convertToEnergyColor(int i) {
        float min;
        float min2;
        float[] fArr = new float[3];
        Color.colorToHSV(i, fArr);
        float f = fArr[0];
        float f2 = fArr[1];
        float f3 = fArr[2];
        if (f >= 60.0f && f <= 300.0f) {
            if (f >= 60.0f && f < 170.0f) {
                f = mapHueToRange((f / 2.0f) + 30.0f, 60.0f, 170.0f, 20.0f, 60.0f);
            } else if (f >= 170.0f && f < 260.0f) {
                f = ((f - 180.0f) + 360.0f) % 360.0f;
                if (f < 10.0f) {
                    f += 10.0f;
                }
                if (f > 60.0f) {
                    f = 60.0f;
                }
            } else if (f >= 260.0f && f <= 300.0f) {
                f = (f + 60.0f) % 360.0f;
                if (f < 300.0f && f > 270.0f) {
                    f = 300.0f;
                }
            }
            if (f2 < 0.5f) {
                min = Math.min(1.0f, f2 + 0.3f);
            } else {
                min = Math.min(1.0f, f2 + 0.15f);
            }
            if (f3 < 0.6f) {
                min2 = Math.min(1.0f, f3 + 0.25f);
            } else {
                min2 = Math.min(1.0f, f3 + 0.1f);
            }
            float f4 = fArr[0];
            if (f4 >= 60.0f && f4 <= 300.0f && f >= 70.0f && f <= 290.0f) {
                f = Math.random() < 0.5d ? 30.0f : 50.0f;
            }
        } else {
            min = Math.min(1.0f, f2 + 0.1f);
            min2 = Math.min(1.0f, f3 + 0.05f);
        }
        fArr[0] = f;
        fArr[1] = Math.max(0.4f, Math.min(1.0f, min));
        fArr[2] = Math.max(0.5f, Math.min(1.0f, min2));
        return Color.HSVToColor(Color.alpha(i), fArr);
    }

    public static int getSemiTransparentColorInt(int i, int i2) {
        return (i & ViewCompat.MEASURED_SIZE_MASK) | (Math.round((i2 * 255) / 100.0f) << 24);
    }

    public static String toHex(int i) {
        String hexString = Integer.toHexString(i);
        while (hexString.length() < 8) {
            hexString = "0" + hexString;
        }
        return "#" + hexString.toUpperCase();
    }

    public static boolean isColorDark(int i) {
        return 1.0d - ((((((double) Color.red(i)) * 0.299d) + (((double) Color.green(i)) * 0.587d)) + (((double) Color.blue(i)) * 0.114d)) / 255.0d) >= 0.3d;
    }

    public static int getAverageColor(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        for (int i5 = 0; i5 < height; i5 += 20) {
            for (int i6 = 0; i6 < width; i6 += 20) {
                int pixel = bitmap.getPixel(i6, i5);
                i2 += Color.red(pixel);
                i3 += Color.green(pixel);
                i4 += Color.blue(pixel);
                i++;
            }
        }
        if (i > 0) {
            return Color.rgb(i2 / i, i3 / i, i4 / i);
        }
        return -7829368;
    }

    public static int darkenColor(int i, float f) {
        float f2 = 1.0f - f;
        return Color.rgb((int) (Color.red(i) * f2), (int) (Color.green(i) * f2), (int) (Color.blue(i) * f2));
    }

    public static int lightenColor(int i, float f) {
        return Color.rgb((int) (Color.red(i) + ((255 - Color.red(i)) * f)), (int) (Color.green(i) + ((255 - Color.green(i)) * f)), (int) (Color.blue(i) + ((255 - Color.blue(i)) * f)));
    }
}

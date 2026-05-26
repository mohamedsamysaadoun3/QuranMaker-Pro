package hazem.nurmontage.videoquran.Utils;

import android.graphics.Point;
import hazem.nurmontage.videoquran.constant.ResizeType;
import kotlin.Pair;

/* loaded from: classes2.dex */
public class AspectRatioCalculator {
    private static final float ASPECT_RATIO_HEIGHT = 16.0f;
    private static final float ASPECT_RATIO_WIDTH = 9.0f;

    public static int calcuWattermark(int i) {
        return (int) ((i * 5.0d) / 16.0d);
    }

    public static Pair<Integer, Integer> getSize(int i, String str) {
        if (i == ResizeType.SOCIAL_STORY.ordinal()) {
            if (str.equals("480p")) {
                return new Pair<>(480, 854);
            }
            if (str.equals("720p")) {
                return new Pair<>(720, 1280);
            }
            if (str.equals("1080p")) {
                return new Pair<>(1080, 1920);
            }
        }
        if (i == ResizeType.YOUTUBE_THUMBNAIL.ordinal()) {
            if (str.equals("480p")) {
                return new Pair<>(854, 480);
            }
            if (str.equals("720p")) {
                return new Pair<>(1280, 720);
            }
            if (str.equals("1080p")) {
                return new Pair<>(1920, 1080);
            }
        }
        if (str.equals("480p")) {
            return new Pair<>(480, 480);
        }
        if (str.equals("720p")) {
            return new Pair<>(720, 720);
        }
        return new Pair<>(1080, 1080);
    }

    public static int calculateHeight(int i) {
        return Math.round((i * ASPECT_RATIO_HEIGHT) / ASPECT_RATIO_WIDTH);
    }

    public static int calculateHeight_Youtube(int i) {
        return Math.round((i * ASPECT_RATIO_WIDTH) / ASPECT_RATIO_HEIGHT);
    }

    public static int calculateWidth(int i) {
        return Math.round((i * ASPECT_RATIO_WIDTH) / ASPECT_RATIO_HEIGHT);
    }

    private static int findGCD(int i, int i2) {
        while (true) {
            int i3 = i2;
            int i4 = i;
            i = i3;
            if (i == 0) {
                return i4;
            }
            i2 = i4 % i;
        }
    }

    public static Point calculateAspectRatio(int i, int i2) {
        int findGCD = findGCD(i, i2);
        return new Point(i / findGCD, i2 / findGCD);
    }
}

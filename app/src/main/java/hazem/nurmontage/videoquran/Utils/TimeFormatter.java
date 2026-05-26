package hazem.nurmontage.videoquran.Utils;

import android.util.Pair;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public class TimeFormatter {
    private long totalDurationMs;

    public static String timeToString(long j) {
        return TimeUnit.MILLISECONDS.toHours(j) + ":" + (TimeUnit.MILLISECONDS.toMinutes(j) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(j))) + ":" + (TimeUnit.MILLISECONDS.toSeconds(j) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(j))) + "." + (TimeUnit.MILLISECONDS.toMillis(j) - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(j)));
    }

    public TimeFormatter(long j) {
        this.totalDurationMs = j;
    }

    public void setTotalDurationMs(long j) {
        this.totalDurationMs = j;
    }

    public Pair<String, String> formatTime(long j) {
        return new Pair<>(formatMsToTime(j), formatMsToTime(this.totalDurationMs - j));
    }

    private String formatMsToTime(long j) {
        if (j < 0) {
            j = 0;
        }
        long minutes = TimeUnit.MILLISECONDS.toMinutes(j);
        return String.format(Locale.ENGLISH, "%d:%02d", Long.valueOf(minutes), Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(j) - TimeUnit.MINUTES.toSeconds(minutes)));
    }
}

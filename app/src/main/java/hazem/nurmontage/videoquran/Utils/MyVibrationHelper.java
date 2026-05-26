package hazem.nurmontage.videoquran.Utils;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

/* loaded from: classes2.dex */
public class MyVibrationHelper {
    private Vibrator vibrator;

    public MyVibrationHelper(Context context) {
        this.vibrator = (Vibrator) context.getSystemService("vibrator");
    }

    public void vibrate() {
        vibrate(30L);
    }

    public void vibrate(long j) {
        if (this.vibrator != null) {
            if (Build.VERSION.SDK_INT >= 26) {
                this.vibrator.vibrate(VibrationEffect.createOneShot(j, -1));
            } else {
                this.vibrator.vibrate(j);
            }
        }
    }

    public void cancelVibration() {
        Vibrator vibrator = this.vibrator;
        if (vibrator != null) {
            vibrator.cancel();
        }
    }
}

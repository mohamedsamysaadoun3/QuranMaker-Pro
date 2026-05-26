package hazem.nurmontage.videoquran;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.core.splashscreen.SplashScreen;
import hazem.nurmontage.videoquran.databinding.ActivityFullscreenBinding;

public class SplashscreenActivity extends Base {
    private ActivityFullscreenBinding binding;

    @Override
    protected void onCreate(Bundle bundle) {
        SplashScreen.installSplashScreen(this);
        super.onCreate(bundle);
        ActivityFullscreenBinding inflate = ActivityFullscreenBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());

        // Navigate to FullscreenActivity after splash screen finishes
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isFinishing() && !isDestroyed()) {
                    startActivity(new Intent(SplashscreenActivity.this, FullscreenActivity.class));
                    finish();
                }
            }
        }, 1500);
    }
}

package hazem.nurmontage.videoquran;

import android.os.Bundle;
import androidx.core.splashscreen.SplashScreen;
import hazem.nurmontage.videoquran.databinding.ActivityFullscreenBinding;

/* loaded from: classes2.dex */
public class SplashscreenActivity extends Base {
    private ActivityFullscreenBinding binding;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        SplashScreen.installSplashScreen(this);
        super.onCreate(bundle);
        ActivityFullscreenBinding inflate = ActivityFullscreenBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());
    }
}

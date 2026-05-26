package hazem.nurmontage.videoquran;

import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;

/* loaded from: classes2.dex */
public class Base extends AppCompatActivity {
    public void hideSystemBars() {
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        if (Build.VERSION.SDK_INT >= 30) {
            hideSystemBarsApi30(-1);
        } else {
            hideSystemBarsBelowApi30(-1);
        }
    }

    private void hideSystemBarsApi30(int i) {
        Window window = getWindow();
        window.setStatusBarColor(i);
        WindowInsetsControllerCompat windowInsetsControllerCompat = new WindowInsetsControllerCompat(window, window.getDecorView());
        windowInsetsControllerCompat.hide(WindowInsets.Type.systemBars());
        windowInsetsControllerCompat.setSystemBarsBehavior(2);
    }

    private void hideSystemBarsBelowApi30(int i) {
        Window window = getWindow();
        window.addFlags(Integer.MIN_VALUE);
        window.clearFlags(67108864);
        window.setStatusBarColor(i);
        window.getDecorView().setSystemUiVisibility(5894);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        window.addFlags(1024);
    }

    public void setStatusBarColor(int i) {
        Window window = getWindow();
        window.clearFlags(67108864);
        window.addFlags(Integer.MIN_VALUE);
        window.setStatusBarColor(i);
    }

    public void setStatusBarColor() {
        Window window = getWindow();
        window.clearFlags(67108864);
        window.addFlags(Integer.MIN_VALUE);
        window.setStatusBarColor(-14540254);
    }

    public void setNavigationBarColor(int i) {
        Window window = getWindow();
        window.clearFlags(67108864);
        window.addFlags(Integer.MIN_VALUE);
        window.setNavigationBarColor(i);
    }

    public void setLightStatusBar(boolean z) {
        View decorView = getWindow().getDecorView();
        int systemUiVisibility = decorView.getSystemUiVisibility();
        decorView.setSystemUiVisibility(z ? systemUiVisibility | 8192 : systemUiVisibility & (-8193));
    }

    public void wakeLockAquire() {
        try {
            getWindow().addFlags(128);
        } catch (Exception unused) {
        }
    }
}

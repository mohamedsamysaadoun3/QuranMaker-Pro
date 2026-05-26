package hazem.nurmontage.videoquran;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import hazem.nurmontage.videoquran.Utils.LocaleHelper;
import hazem.nurmontage.videoquran.Utils.MyVibrationHelper;
import hazem.nurmontage.videoquran.views.TextCustumFont;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import nl.dionsegijn.konfetti.core.PartyFactory;
import nl.dionsegijn.konfetti.core.Position;
import nl.dionsegijn.konfetti.core.Spread;
import nl.dionsegijn.konfetti.core.emitter.Emitter;
import nl.dionsegijn.konfetti.core.models.Shape;
import nl.dionsegijn.konfetti.xml.KonfettiView;
import nl.dionsegijn.konfetti.xml.image.ImageUtil;

/* loaded from: classes2.dex */
public class ThanksYouActivity extends Base {
    private Resources mResources;
    private final OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) { // from class: hazem.nurmontage.videoquran.ThanksYouActivity.1
        @Override // androidx.activity.OnBackPressedCallback
        public void handleOnBackPressed() {
            ThanksYouActivity.this.finish();
        }
    };

    public void explode() {
        ((KonfettiView) findViewById(R.id.konfettiView)).start(new PartyFactory(new Emitter(2800L, TimeUnit.MILLISECONDS).max(100)).spread(Spread.ROUND).shapes(Arrays.asList(Shape.Square.INSTANCE, Shape.Circle.INSTANCE, ImageUtil.loadDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.favorite_24px), true, true))).colors(Arrays.asList(16572810, 16740973, 16003181, 11832815)).setSpeedBetween(0.0f, 30.0f).position(new Position.Relative(0.5d, 0.3d)).build());
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.ContextThemeWrapper, android.content.ContextWrapper
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(LocaleHelper.onAttach(context));
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thanks_you);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), new OnApplyWindowInsetsListener() { // from class: hazem.nurmontage.videoquran.ThanksYouActivity$$ExternalSyntheticLambda0
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                return ThanksYouActivity.lambda$onCreate$0(view, windowInsetsCompat);
            }
        });
        getOnBackPressedDispatcher().addCallback(this, this.onBackPressedCallback);
        setStatusBarColor(-1);
        this.mResources = getResources();
        if (getIntent() != null) {
            ((TextCustumFont) findViewById(R.id.tv_price_donate)).setText(String.format(this.mResources.getString(R.string.donate_hint), getIntent().getStringExtra("price")));
            ((TextCustumFont) findViewById(R.id.tv_thnks_donate)).setText(this.mResources.getString(R.string.thanks_hint));
        }
        explode();
        findViewById(R.id.btn_on_back).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.ThanksYouActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ThanksYouActivity.this.onBackPressedCallback.handleOnBackPressed();
            }
        });
    }

    static /* synthetic */ WindowInsetsCompat lambda$onCreate$0(View view, WindowInsetsCompat windowInsetsCompat) {
        Insets insets = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.systemBars());
        view.setPadding(insets.left, insets.top, insets.right, insets.bottom);
        return windowInsetsCompat;
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        playVibration();
    }

    private void playVibration() {
        new MyVibrationHelper(this).vibrate(250L);
    }
}

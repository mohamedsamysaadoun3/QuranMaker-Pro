package hazem.nurmontage.videoquran;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import hazem.nurmontage.videoquran.Utils.LocaleHelper;
import java.util.Map;

/* loaded from: classes2.dex */
public class ChoiceLangActivity extends Base {
    private boolean isFromSetting;
    private String lang = "en";
    private boolean isEnglishSelected = true;
    private final OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) { // from class: hazem.nurmontage.videoquran.ChoiceLangActivity.1
        @Override // androidx.activity.OnBackPressedCallback
        public void handleOnBackPressed() {
            ChoiceLangActivity.this.toStarWork();
        }
    };

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.ContextThemeWrapper, android.content.ContextWrapper
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(LocaleHelper.onAttach(context));
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_choice_lang);
        getOnBackPressedDispatcher().addCallback(this, this.onBackPressedCallback);
        setStatusBarColor(ViewCompat.MEASURED_STATE_MASK);
        setNavigationBarColor(ViewCompat.MEASURED_STATE_MASK);
        WindowInsetsControllerCompat insetsController = WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(false);
        insetsController.setAppearanceLightNavigationBars(false);
        if (getIntent() != null) {
            this.isFromSetting = getIntent().getBooleanExtra("from_setting", false);
        }
        initViews();
    }

    private void initViews() {
        final RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.layout_english);
        final RelativeLayout relativeLayout2 = (RelativeLayout) findViewById(R.id.layout_arabic);
        final ImageView imageView = (ImageView) findViewById(R.id.radio_english);
        final ImageView imageView2 = (ImageView) findViewById(R.id.radio_arabic);
        Button button = (Button) findViewById(R.id.btn_confirm);
        button.setText(getResources().getString(R.string.confirm));
        TextView textView = (TextView) findViewById(R.id.tv_cancel);
        textView.setText(getResources().getString(R.string.cancel));
        ((TextView) findViewById(R.id.tv_tittle)).setText(getResources().getString(R.string.select_language));
        ((TextView) findViewById(R.id.tv_subTittle)).setText(getResources().getString(R.string.choose_your_preferred_language));
        if ("ar".equals(LocaleHelper.getLanguage(this))) {
            relativeLayout.setBackgroundResource(R.drawable.bg_item_unselected);
            relativeLayout2.setBackgroundResource(R.drawable.bg_item_selected);
            imageView2.setBackgroundResource(R.drawable.radio_selected);
            imageView.setBackgroundResource(R.drawable.radio_unselected);
        }
        relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.ChoiceLangActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ChoiceLangActivity.this.m468x20d8e885(imageView, imageView2, relativeLayout, relativeLayout2, view);
            }
        });
        relativeLayout2.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.ChoiceLangActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ChoiceLangActivity.this.m469x5aa38a64(imageView, imageView2, relativeLayout2, relativeLayout, view);
            }
        });
        button.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.ChoiceLangActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ChoiceLangActivity.this.m470x946e2c43(view);
            }
        });
        textView.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.ChoiceLangActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ChoiceLangActivity.this.m471xce38ce22(view);
            }
        });
    }

    /* renamed from: lambda$initViews$0$hazem-nurmontage-videoquran-ChoiceLangActivity, reason: not valid java name */
    /* synthetic */ void m468x20d8e885(ImageView imageView, ImageView imageView2, RelativeLayout relativeLayout, RelativeLayout relativeLayout2, View view) {
        this.isEnglishSelected = true;
        imageView.setBackgroundResource(R.drawable.radio_selected);
        imageView2.setBackgroundResource(R.drawable.radio_unselected);
        relativeLayout.setBackgroundResource(R.drawable.bg_item_selected);
        relativeLayout2.setBackgroundResource(R.drawable.bg_item_unselected);
    }

    /* renamed from: lambda$initViews$1$hazem-nurmontage-videoquran-ChoiceLangActivity, reason: not valid java name */
    /* synthetic */ void m469x5aa38a64(ImageView imageView, ImageView imageView2, RelativeLayout relativeLayout, RelativeLayout relativeLayout2, View view) {
        this.isEnglishSelected = false;
        imageView.setBackgroundResource(R.drawable.radio_unselected);
        imageView2.setBackgroundResource(R.drawable.radio_selected);
        relativeLayout.setBackgroundResource(R.drawable.bg_item_selected);
        relativeLayout2.setBackgroundResource(R.drawable.bg_item_unselected);
    }

    /* renamed from: lambda$initViews$2$hazem-nurmontage-videoquran-ChoiceLangActivity, reason: not valid java name */
    /* synthetic */ void m470x946e2c43(View view) {
        this.lang = this.isEnglishSelected ? "en" : "ar";
        start();
    }

    /* renamed from: lambda$initViews$3$hazem-nurmontage-videoquran-ChoiceLangActivity, reason: not valid java name */
    /* synthetic */ void m471xce38ce22(View view) {
        toStarWork();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void toStarWork() {
        Intent intent;
        SharedPreferences sharedPreferences = getSharedPreferences("Template", 0);
        if (this.isFromSetting) {
            intent = new Intent(this, (Class<?>) SeettingActivity.class);
        } else {
            Map<String, ?> all = sharedPreferences.getAll();
            if (all != null && !all.isEmpty()) {
                intent = new Intent(this, (Class<?>) WorkUserActivity.class);
            } else {
                intent = new Intent(this, (Class<?>) EngineActivity.class);
            }
        }
        intent.setFlags(268468224);
        startActivity(intent);
        finish();
    }

    public void start() {
        if (LocaleHelper.getLanguage(this).equals(this.lang)) {
            startActivity(new Intent(this, (Class<?>) SeettingActivity.class));
            overridePendingTransition(0, 0);
            finish();
            return;
        }
        LocaleHelper.persist(getApplicationContext(), this.lang);
        LocaleHelper.onAttach(this);
        recreate();
        Intent intent = new Intent(this, (Class<?>) FullscreenActivity.class);
        intent.putExtra("from_setting", true);
        intent.setFlags(268468224);
        startActivity(intent);
        finish();
    }
}

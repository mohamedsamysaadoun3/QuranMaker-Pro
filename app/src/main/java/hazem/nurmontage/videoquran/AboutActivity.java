package hazem.nurmontage.videoquran;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import hazem.nurmontage.videoquran.Utils.AppUtils;
import hazem.nurmontage.videoquran.Utils.LocaleHelper;
import hazem.nurmontage.videoquran.Utils.ScreenUtils;
import hazem.nurmontage.videoquran.adabter.AboutAdabters;
import hazem.nurmontage.videoquran.views.TextCustumFont;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public class AboutActivity extends Base {
    private Resources mResources;
    private final OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) { // from class: hazem.nurmontage.videoquran.AboutActivity.1
        @Override // androidx.activity.OnBackPressedCallback
        public void handleOnBackPressed() {
            AboutActivity.this.finish();
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
        setContentView(R.layout.activity_about);
        getOnBackPressedDispatcher().addCallback(this, this.onBackPressedCallback);
        setStatusBarColor(ViewCompat.MEASURED_STATE_MASK);
        setNavigationBarColor(ViewCompat.MEASURED_STATE_MASK);
        WindowInsetsControllerCompat insetsController = WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(false);
        insetsController.setAppearanceLightNavigationBars(false);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), new OnApplyWindowInsetsListener() { // from class: hazem.nurmontage.videoquran.AboutActivity$$ExternalSyntheticLambda0
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                return AboutActivity.lambda$onCreate$0(view, windowInsetsCompat);
            }
        });
        Resources resources = getResources();
        this.mResources = resources;
        if (resources == null) {
            finish();
        }
        init();
    }

    static /* synthetic */ WindowInsetsCompat lambda$onCreate$0(View view, WindowInsetsCompat windowInsetsCompat) {
        Insets insets = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.systemBars());
        view.setPadding(insets.left, insets.top, insets.right, insets.bottom);
        return windowInsetsCompat;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void help() {
        try {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse("https://chat.whatsapp.com/DDdUegENpg83easzYDba2K?mode=wwt"));
            intent.setPackage("com.whatsapp");
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        findViewById(R.id.btn_on_back).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.AboutActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                AboutActivity.this.onBackPressedCallback.handleOnBackPressed();
            }
        });
        findViewById(R.id.btn_help).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.AboutActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                AboutActivity.this.help();
            }
        });
        ((TextCustumFont) findViewById(R.id.tv_help)).setText(this.mResources.getString(R.string.help));
        ArrayList arrayList = new ArrayList();
        int i = LocaleHelper.getLanguage(this).equals("ar") ? 5 : GravityCompat.START;
        arrayList.add(new AboutAdabters.ModelAbout(19, (Pair<String, Integer>) new Pair("<font color=#F8B195>" + this.mResources.getString(R.string.about_free_site) + "</font>", Integer.valueOf(i)), R.drawable.about_site_video));
        arrayList.add(new AboutAdabters.ModelAbout(14, (Pair<String, Integer>) new Pair(this.mResources.getString(R.string.about_free_site_desc), Integer.valueOf(i))));
        arrayList.add(new AboutAdabters.ModelAbout(14, (Pair<String, Integer>) new Pair("\n", Integer.valueOf(i))));
        arrayList.add(new AboutAdabters.ModelAbout(19, (Pair<String, Integer>) new Pair("<font color=#F8B195>" + this.mResources.getString(R.string.about_free_app) + "</font>", Integer.valueOf(i)), R.drawable.about_best_app));
        arrayList.add(new AboutAdabters.ModelAbout(14, (Pair<String, Integer>) new Pair("\n", Integer.valueOf(i))));
        arrayList.add(new AboutAdabters.ModelAbout(19, (Pair<String, Integer>) new Pair("<font color=#F8B195>" + this.mResources.getString(R.string.about_dont_subscribe) + "</font>", Integer.valueOf(i)), R.drawable.about_money));
        arrayList.add(new AboutAdabters.ModelAbout(14, (Pair<String, Integer>) new Pair("<font color='#ffffff'>" + this.mResources.getString(R.string.about_dont_subscribe_why) + "</font>", Integer.valueOf(i))));
        arrayList.add(new AboutAdabters.ModelAbout(14, (Pair<String, Integer>) new Pair("\n", Integer.valueOf(i))));
        arrayList.add(new AboutAdabters.ModelAbout(19, (Pair<String, Integer>) new Pair("<font color=#F8B195>" + this.mResources.getString(R.string.this_begeing_idea) + "</font>", Integer.valueOf(i)), R.drawable.about_hazem));
        arrayList.add(new AboutAdabters.ModelAbout(14, (Pair<String, Integer>) new Pair("<font color='#ffffff'>" + this.mResources.getString(R.string.this_begeing_idea_decp) + "</font>", Integer.valueOf(i))));
        arrayList.add(new AboutAdabters.ModelAbout(14, (Pair<String, Integer>) new Pair("\n", Integer.valueOf(i))));
        arrayList.add(new AboutAdabters.ModelAbout(19, (Pair<String, Integer>) new Pair("<font color=#F8B195>" + this.mResources.getString(R.string.about_help_tittle) + "</font>", Integer.valueOf(i)), R.drawable.about_help));
        arrayList.add(new AboutAdabters.ModelAbout(14, (Pair<String, Integer>) new Pair("<font color='#ffffff'>" + this.mResources.getString(R.string.about_help_body) + "</font>", Integer.valueOf(i))));
        arrayList.add(new AboutAdabters.ModelAbout(14, (Pair<String, Integer>) new Pair("\n", Integer.valueOf(i))));
        arrayList.add(new AboutAdabters.ModelAbout(19, (Pair<String, Integer>) new Pair("<font color=#F8B195>" + this.mResources.getString(R.string.nurmontage_means) + "</font>", Integer.valueOf(i)), R.drawable.nurmontage_means));
        arrayList.add(new AboutAdabters.ModelAbout(14, (Pair<String, Integer>) new Pair("<font color='#ffffff'>" + this.mResources.getString(R.string.nurmontage_means_descrp) + "</font>", Integer.valueOf(i))));
        arrayList.add(new AboutAdabters.ModelAbout(14, (Pair<String, Integer>) new Pair("\n", Integer.valueOf(i))));
        arrayList.add(new AboutAdabters.ModelAbout(19, (Pair<String, Integer>) new Pair("<font color=#F8B195>" + this.mResources.getString(R.string.help_me_help_you) + "</font>", Integer.valueOf(i)), R.drawable.about_help_me_help_you));
        arrayList.add(new AboutAdabters.ModelAbout(14, (Pair<String, Integer>) new Pair("<font color='#ffffff'>" + this.mResources.getString(R.string.help_me_help_you_descrp) + "</font>", Integer.valueOf(i))));
        arrayList.add(new AboutAdabters.ModelAbout(14, (Pair<String, Integer>) new Pair("\n", Integer.valueOf(i))));
        arrayList.add(new AboutAdabters.ModelAbout(0, (Pair<String, Integer>) new Pair("", Integer.valueOf(i)), R.drawable.signature_hazem));
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new AboutAdabters(this, AppUtils.getAppVersionName(this), arrayList, ScreenUtils.getScreenWidth(this), (int) (ScreenUtils.getScreenHeight(this) * 0.3f)));
    }
}

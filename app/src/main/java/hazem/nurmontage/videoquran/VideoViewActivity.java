package hazem.nurmontage.videoquran;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.media3.common.MimeTypes;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.ObjectKey;
import hazem.nurmontage.videoquran.Utils.AppUtils;
import hazem.nurmontage.videoquran.Utils.LocalPersistence;
import hazem.nurmontage.videoquran.Utils.LocaleHelper;
import hazem.nurmontage.videoquran.Utils.Utils;
import hazem.nurmontage.videoquran.common.Common;
import hazem.nurmontage.videoquran.fragment.RatingBottomSheetDialog;
import hazem.nurmontage.videoquran.views.ButtonCustumFont;
import hazem.nurmontage.videoquran.views.TextCustumFont;
import hazem.nurmontage.videoquran.views.TextCustumFontBold;
import java.io.File;

/* loaded from: classes2.dex */
public class VideoViewActivity extends Base {
    private ImageView btnPlayPause;
    private Dialog dialog;
    private String idTemplate;
    private Resources mResources;
    private String mUri;
    private final OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) { // from class: hazem.nurmontage.videoquran.VideoViewActivity.1
        @Override // androidx.activity.OnBackPressedCallback
        public void handleOnBackPressed() {
            VideoViewActivity.this.toStudio();
        }
    };
    private RelativeLayout parentLayout;
    private String reader;
    private String surah;
    private ImageView videoView;

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.ContextThemeWrapper, android.content.ContextWrapper
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(LocaleHelper.onAttach(context));
    }

    private void setSystemUiAppearance() {
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        Window window = getWindow();
        window.clearFlags(1024);
        window.clearFlags(512);
        setStatusBarColor(ViewCompat.MEASURED_STATE_MASK);
        setNavigationBarColor(ViewCompat.MEASURED_STATE_MASK);
        WindowInsetsControllerCompat insetsController = WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(false);
        insetsController.setAppearanceLightNavigationBars(false);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        setSystemUiAppearance();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_video_view);
        getOnBackPressedDispatcher().addCallback(this, this.onBackPressedCallback);
        setSystemUiAppearance();
        this.mResources = getResources();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), new OnApplyWindowInsetsListener() { // from class: hazem.nurmontage.videoquran.VideoViewActivity$$ExternalSyntheticLambda0
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                return VideoViewActivity.lambda$onCreate$0(view, windowInsetsCompat);
            }
        });
        LocalPersistence.deleteTemplate(this, Common.TEMPLATE_TMP);
        if (getIntent() != null) {
            final Uri data = getIntent().getData();
            this.idTemplate = getIntent().getStringExtra(Common.TEMPLATE);
            this.reader = getIntent().getStringExtra(Common.READER);
            this.surah = getIntent().getStringExtra(Common.SURAH);
            this.parentLayout = (RelativeLayout) findViewById(R.id.parentLayout);
            if (data != null) {
                this.mUri = data.toString();
                this.btnPlayPause = (ImageView) findViewById(R.id.btn_play_pause);
                ImageView imageView = (ImageView) findViewById(R.id.videoView);
                this.videoView = imageView;
                imageView.post(new Runnable() { // from class: hazem.nurmontage.videoquran.VideoViewActivity.2
                    @Override // java.lang.Runnable
                    public void run() {
                        Glide.with((FragmentActivity) VideoViewActivity.this).asBitmap().load(VideoViewActivity.this.mUri).frame(1000000L).centerInside().override((int) Math.max(50.0f, VideoViewActivity.this.parentLayout.getWidth() * 0.9f), (int) Math.max(50.0f, VideoViewActivity.this.parentLayout.getHeight() * 0.9f)).diskCacheStrategy(DiskCacheStrategy.NONE).signature(new ObjectKey(AppUtils.getAppVersionName(VideoViewActivity.this))).into(VideoViewActivity.this.videoView);
                    }
                });
                this.videoView.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.VideoViewActivity.3
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        Intent intent = new Intent(VideoViewActivity.this.getApplicationContext(), (Class<?>) VideoPlayerActivity.class);
                        intent.setData(data);
                        intent.addFlags(65536);
                        VideoViewActivity.this.startActivity(intent);
                        VideoViewActivity.this.overridePendingTransition(0, 0);
                    }
                });
            }
        }
        findViewById(R.id.btn_tuffah).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.VideoViewActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Utils.isAppInstalled(VideoViewActivity.this, "hazem.tuffah.quranaudio")) {
                    Intent launchIntentForPackage = VideoViewActivity.this.getPackageManager().getLaunchIntentForPackage("hazem.tuffah.quranaudio");
                    if (launchIntentForPackage != null) {
                        VideoViewActivity.this.startActivity(launchIntentForPackage);
                        return;
                    }
                    return;
                }
                VideoViewActivity.this.inslallTuffah();
            }
        });
        findViewById(R.id.btn_home).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.VideoViewActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                VideoViewActivity.this.startActivity(new Intent(VideoViewActivity.this, (Class<?>) WorkUserActivity.class));
                VideoViewActivity.this.finish();
            }
        });
        TextCustumFont textCustumFont = (TextCustumFont) findViewById(R.id.tv_share);
        Resources resources = this.mResources;
        if (resources != null) {
            textCustumFont.setText(resources.getString(R.string.just_share));
        }
        findViewById(R.id.btn_share).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.VideoViewActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                String format;
                try {
                    if (!Utils.isProbablyLArabic(VideoViewActivity.this.reader)) {
                        format = String.format("%s %s #NurMontage_app #قرآن_كريم ", VideoViewActivity.this.surah, VideoViewActivity.this.reader);
                    } else {
                        format = String.format(" %s بصوت %s #تطبيق_NurMontage #قرآن_كريم", VideoViewActivity.this.surah, VideoViewActivity.this.reader);
                    }
                    Intent intent = new Intent("android.intent.action.SEND");
                    intent.putExtra("act", "ACT_SHARE");
                    intent.putExtra("android.intent.extra.TITLE", "Send To");
                    intent.putExtra("android.intent.extra.TEXT", format);
                    intent.putExtra("android.intent.extra.SUBJECT", VideoViewActivity.this.mResources.getString(R.string.nurmontage_app));
                    VideoViewActivity videoViewActivity = VideoViewActivity.this;
                    intent.putExtra("android.intent.extra.STREAM", FileProvider.getUriForFile(videoViewActivity, videoViewActivity.getResources().getString(R.string.file_provider), new File(Uri.parse(VideoViewActivity.this.mUri).getPath())));
                    intent.setType(MimeTypes.VIDEO_MP4);
                    VideoViewActivity.this.startActivity(Intent.createChooser(intent, "Send To"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.btn_on_back).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.VideoViewActivity.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                VideoViewActivity.this.toStudio();
            }
        });
        ((TextCustumFont) findViewById(R.id.tv_help)).setText(this.mResources.getString(R.string.help));
        findViewById(R.id.btn_help).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.VideoViewActivity.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                VideoViewActivity.this.help();
            }
        });
        ratingSetup();
    }

    static /* synthetic */ WindowInsetsCompat lambda$onCreate$0(View view, WindowInsetsCompat windowInsetsCompat) {
        Insets insets = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.systemBars());
        view.setPadding(insets.left, insets.top, insets.right, insets.bottom);
        return windowInsetsCompat;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void inslallTuffah() {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=hazem.tuffah.quranaudio"));
        intent.setPackage("com.android.vending");
        intent.addFlags(1476395008);
        try {
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException unused) {
                Toast.makeText(this, "Unable to open app store or browser.", 1).show();
            }
        } catch (ActivityNotFoundException unused2) {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/details?id=hazem.tuffah.quranaudio")));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void help() {
        try {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse("https://chat.whatsapp.com/F0kqOjZS1VuBAvoiOG4XEZ"));
            intent.setPackage("com.whatsapp");
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void toAbout() {
        startActivity(new Intent(this, (Class<?>) AboutActivity.class));
        overridePendingTransition(0, 0);
    }

    private void toPro() {
        startActivity(new Intent(this, (Class<?>) ProVersionActivity.class));
        overridePendingTransition(0, 0);
    }

    private void ratingSetup() {
        try {
            if (!RatingBottomSheetDialog.shouldShowRatingDialog(this) || this.mResources == null || trackerSession() < 4) {
                return;
            }
            dialogRate();
        } catch (Exception unused) {
        }
    }

    public int trackerSession() {
        SharedPreferences sharedPreferences = getSharedPreferences("ActPreference", 0);
        int i = sharedPreferences.getInt("session_count", 0) + 1;
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt("session_count", i);
        edit.apply();
        return i;
    }

    public void resetTrackerSession() {
        SharedPreferences.Editor edit = getSharedPreferences("ActPreference", 0).edit();
        edit.putInt("session_count", 0);
        edit.apply();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void toStudio() {
        Intent intent = new Intent(this, (Class<?>) EngineActivity.class);
        intent.putExtra(Common.TEMPLATE, this.idTemplate);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        cancelDialog();
        super.onPause();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelDialog() {
        Dialog dialog = this.dialog;
        if (dialog != null && dialog.isShowing()) {
            this.dialog.dismiss();
        }
        this.dialog = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void openPlayStoreForRating() {
        String packageName = getPackageName();
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + packageName));
        intent.setPackage("com.android.vending");
        intent.addFlags(1476395008);
        try {
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException unused) {
                Toast.makeText(this, "Unable to open app store or browser.", 1).show();
            }
        } catch (ActivityNotFoundException unused2) {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/details?id=" + packageName)));
        }
    }

    public void dialogRate() {
        Dialog dialog = new Dialog(this);
        this.dialog = dialog;
        dialog.setCancelable(false);
        this.dialog.requestWindowFeature(1);
        this.dialog.getWindow().setLayout(-1, -2);
        this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        View inflate = LayoutInflater.from(this).inflate(R.layout.layout_dialog_rate, (ViewGroup) null);
        this.dialog.setContentView(inflate);
        ((TextCustumFontBold) inflate.findViewById(R.id.tv_tittle)).setText(this.mResources.getString(R.string.how_many_stars));
        ButtonCustumFont buttonCustumFont = (ButtonCustumFont) inflate.findViewById(R.id.btn_rate);
        buttonCustumFont.setText(this.mResources.getString(R.string.rate_now));
        buttonCustumFont.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.VideoViewActivity.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                try {
                    VideoViewActivity.this.openPlayStoreForRating();
                    RatingBottomSheetDialog.setNeverAskAgain(VideoViewActivity.this, true);
                    VideoViewActivity.this.cancelDialog();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        ButtonCustumFont buttonCustumFont2 = (ButtonCustumFont) inflate.findViewById(R.id.btn_rate_not_now);
        buttonCustumFont2.setText(this.mResources.getString(R.string.later));
        buttonCustumFont2.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.VideoViewActivity.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                VideoViewActivity.this.resetTrackerSession();
                VideoViewActivity.this.cancelDialog();
            }
        });
        this.dialog.show();
    }
}

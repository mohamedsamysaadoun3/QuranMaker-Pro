package hazem.nurmontage.videoquran;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import hazem.nurmontage.videoquran.Utils.LocaleHelper;
import hazem.nurmontage.videoquran.common.Common;
import hazem.nurmontage.videoquran.views.TextCustumFont;
import hazem.nurmontage.videoquran.views.VideoFrameSelectorView;

/* loaded from: classes2.dex */
public class ChoiceBgFromVideoActivity extends Base {
    private ImageView imageView;
    private Resources mResources;
    private final OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) { // from class: hazem.nurmontage.videoquran.ChoiceBgFromVideoActivity.1
        @Override // androidx.activity.OnBackPressedCallback
        public void handleOnBackPressed() {
            ChoiceBgFromVideoActivity.this.cancel();
        }
    };

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.ContextThemeWrapper, android.content.ContextWrapper
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(LocaleHelper.onAttach(context));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancel() {
        setResult(0);
        finish();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_choice_bg_from_video);
        getOnBackPressedDispatcher().addCallback(this, this.onBackPressedCallback);
        setStatusBarColor(ViewCompat.MEASURED_STATE_MASK);
        setNavigationBarColor(ViewCompat.MEASURED_STATE_MASK);
        WindowInsetsControllerCompat insetsController = WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(false);
        insetsController.setAppearanceLightNavigationBars(false);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), new OnApplyWindowInsetsListener() { // from class: hazem.nurmontage.videoquran.ChoiceBgFromVideoActivity$$ExternalSyntheticLambda0
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                return ChoiceBgFromVideoActivity.lambda$onCreate$0(view, windowInsetsCompat);
            }
        });
        Resources resources = getResources();
        this.mResources = resources;
        if (resources != null) {
            ((TextCustumFont) findViewById(R.id.tv_tittle_fragment)).setText(this.mResources.getString(R.string.choice_bg));
        }
        findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.ChoiceBgFromVideoActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ChoiceBgFromVideoActivity.this.cancel();
            }
        });
        if (getIntent() != null) {
            init(getIntent().getData());
        }
    }

    static /* synthetic */ WindowInsetsCompat lambda$onCreate$0(View view, WindowInsetsCompat windowInsetsCompat) {
        Insets insets = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.systemBars());
        view.setPadding(insets.left, insets.top, insets.right, insets.bottom);
        return windowInsetsCompat;
    }

    private void init(Uri uri) {
        if (uri == null) {
            return;
        }
        this.imageView = (ImageView) findViewById(R.id.iv_view);
        final VideoFrameSelectorView videoFrameSelectorView = (VideoFrameSelectorView) findViewById(R.id.frameSelectorView);
        videoFrameSelectorView.setVideoUri(uri);
        videoFrameSelectorView.setOnFrameSelectedListener(new VideoFrameSelectorView.OnFrameSelectedListener() { // from class: hazem.nurmontage.videoquran.ChoiceBgFromVideoActivity.3
            @Override // hazem.nurmontage.videoquran.views.VideoFrameSelectorView.OnFrameSelectedListener
            public void onFrameSelected(int i, Bitmap bitmap) {
                if (bitmap == null || ChoiceBgFromVideoActivity.this.imageView == null) {
                    return;
                }
                ChoiceBgFromVideoActivity.this.imageView.setImageBitmap(bitmap);
            }
        });
        findViewById(R.id.btn_done).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.ChoiceBgFromVideoActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Common.bitmap = videoFrameSelectorView.getFrameBitmap().getBitmap();
                ChoiceBgFromVideoActivity.this.setResult(-1, new Intent());
                ChoiceBgFromVideoActivity.this.finish();
            }
        });
    }
}

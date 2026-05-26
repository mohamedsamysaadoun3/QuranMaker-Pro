package hazem.nurmontage.videoquran;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import hazem.nurmontage.videoquran.Utils.BillingPreferences;
import hazem.nurmontage.videoquran.Utils.LocaleHelper;
import hazem.nurmontage.videoquran.Utils.MyPrefereces;
import hazem.nurmontage.videoquran.common.Common;
import hazem.nurmontage.videoquran.views.ButtonCustumFont;
import hazem.nurmontage.videoquran.views.CropView;
import hazem.nurmontage.videoquran.views.TextCustumFont;

/* loaded from: classes2.dex */
public class CropBitmapActivity extends Base {
    public static boolean isActive;
    private CropView cropView;
    private Dialog dialog;
    private Resources mResources;
    private final OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) { // from class: hazem.nurmontage.videoquran.CropBitmapActivity.1
        @Override // androidx.activity.OnBackPressedCallback
        public void handleOnBackPressed() {
            CropBitmapActivity.this.cancel();
        }
    };

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        isActive = false;
    }

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
        setContentView(R.layout.activity_crop_bitmap);
        getOnBackPressedDispatcher().addCallback(this, this.onBackPressedCallback);
        setStatusBarColor(ViewCompat.MEASURED_STATE_MASK);
        setNavigationBarColor(ViewCompat.MEASURED_STATE_MASK);
        isActive = true;
        WindowInsetsControllerCompat insetsController = WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(false);
        insetsController.setAppearanceLightNavigationBars(false);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), new OnApplyWindowInsetsListener() { // from class: hazem.nurmontage.videoquran.CropBitmapActivity$$ExternalSyntheticLambda0
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                return CropBitmapActivity.lambda$onCreate$0(view, windowInsetsCompat);
            }
        });
        Resources resources = getResources();
        this.mResources = resources;
        if (resources != null) {
            ((TextCustumFont) findViewById(R.id.tv_tittle_fragment)).setText(this.mResources.getString(R.string.choice_screen_ipod));
        }
        init();
    }

    static /* synthetic */ WindowInsetsCompat lambda$onCreate$0(View view, WindowInsetsCompat windowInsetsCompat) {
        Insets insets = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.systemBars());
        view.setPadding(insets.left, insets.top, insets.right, insets.bottom);
        return windowInsetsCompat;
    }

    public void dialogPremium() {
        try {
            if (this.dialog != null) {
                cancelDialog();
            }
            Dialog dialog = new Dialog(this);
            this.dialog = dialog;
            dialog.setCancelable(true);
            this.dialog.requestWindowFeature(1);
            this.dialog.getWindow().setLayout(-1, -2);
            this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            View inflate = LayoutInflater.from(this).inflate(R.layout.layout_dialog, (ViewGroup) null);
            this.dialog.setContentView(inflate);
            inflate.findViewById(R.id.dialog_title).setVisibility(8);
            inflate.findViewById(R.id.img_pro).setVisibility(0);
            TextCustumFont textCustumFont = (TextCustumFont) inflate.findViewById(R.id.dialog_message);
            textCustumFont.setText(this.mResources.getString(R.string.unlock_premium));
            textCustumFont.setGravity(17);
            ButtonCustumFont buttonCustumFont = (ButtonCustumFont) inflate.findViewById(R.id.dialog_no);
            buttonCustumFont.setText(this.mResources.getString(R.string.no));
            buttonCustumFont.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.CropBitmapActivity.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    CropBitmapActivity.this.cancelDialog();
                }
            });
            ButtonCustumFont buttonCustumFont2 = (ButtonCustumFont) inflate.findViewById(R.id.dialog_yes);
            buttonCustumFont2.setText(this.mResources.getString(R.string.yes));
            buttonCustumFont2.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.CropBitmapActivity.3
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    CropBitmapActivity.this.toProVersion();
                    CropBitmapActivity.this.cancelDialog();
                }
            });
            this.dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        cancelDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelDialog() {
        Dialog dialog = this.dialog;
        if (dialog != null && dialog.isShowing()) {
            this.dialog.dismiss();
        }
        this.dialog = null;
    }

    private void init() {
        findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.CropBitmapActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CropBitmapActivity.this.cancel();
            }
        });
        if (Common.bitmap == null || Common.rect == null) {
            return;
        }
        CropView cropView = (CropView) findViewById(R.id.crop_view);
        this.cropView = cropView;
        cropView.post(new Runnable() { // from class: hazem.nurmontage.videoquran.CropBitmapActivity.5
            @Override // java.lang.Runnable
            public void run() {
                if (Common.bitmap == null) {
                    return;
                }
                CropBitmapActivity.this.cropView.setBitmap(Common.bitmap, Common.rect, Common.radius, MyPrefereces.isShowHint(CropBitmapActivity.this));
            }
        });
        ButtonCustumFont buttonCustumFont = (ButtonCustumFont) findViewById(R.id.btn_done);
        buttonCustumFont.setText(this.mResources.getString(R.string.done));
        buttonCustumFont.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.CropBitmapActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (!BillingPreferences.isSubscribed(CropBitmapActivity.this)) {
                    CropBitmapActivity.this.dialogPremium();
                    return;
                }
                if (!MyPrefereces.isShowHint(CropBitmapActivity.this)) {
                    MyPrefereces.putShowHint(CropBitmapActivity.this);
                }
                Common.bitmap = CropBitmapActivity.this.cropView.getCroppedBitmap();
                Common.rect = CropBitmapActivity.this.cropView.getRectSquare();
                Intent intent = new Intent();
                intent.putExtra("x", CropBitmapActivity.this.cropView.getmX());
                intent.putExtra("y", CropBitmapActivity.this.cropView.getmY());
                intent.putExtra("w", CropBitmapActivity.this.cropView.getmW());
                intent.putExtra("h", CropBitmapActivity.this.cropView.getmH());
                CropBitmapActivity.this.setResult(-1, intent);
                CropBitmapActivity.this.finish();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void toProVersion() {
        Intent intent = new Intent(this, (Class<?>) ProVersionActivity.class);
        intent.addFlags(65536);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
}

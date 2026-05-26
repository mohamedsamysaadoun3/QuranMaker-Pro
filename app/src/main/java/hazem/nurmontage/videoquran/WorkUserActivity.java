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
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
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
import androidx.media3.common.MimeTypes;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import hazem.nurmontage.videoquran.Utils.AppUtils;
import hazem.nurmontage.videoquran.Utils.BillingPreferences;
import hazem.nurmontage.videoquran.Utils.LocalPersistence;
import hazem.nurmontage.videoquran.Utils.LocaleHelper;
import hazem.nurmontage.videoquran.Utils.MFileUtils;
import hazem.nurmontage.videoquran.Utils.ScreenUtils;
import hazem.nurmontage.videoquran.adabter.WorkUserAdabter;
import hazem.nurmontage.videoquran.common.Common;
import hazem.nurmontage.videoquran.model.Template;
import hazem.nurmontage.videoquran.views.ButtonCustumFont;
import hazem.nurmontage.videoquran.views.TextCustumFont;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.io.FileUtils;

/* loaded from: classes2.dex */
public class WorkUserActivity extends Base {
    private int countClick;
    private Dialog dialog;
    private Resources mResources;
    private Toast mToast;
    private PopupWindow popupWindow;
    private WorkUserAdabter workUserAdabter;
    private boolean backPressedOnce = false;
    private OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) { // from class: hazem.nurmontage.videoquran.WorkUserActivity.1
        @Override // androidx.activity.OnBackPressedCallback
        public void handleOnBackPressed() {
            try {
                if (WorkUserActivity.this.backPressedOnce) {
                    if (WorkUserActivity.this.mToast != null) {
                        WorkUserActivity.this.mToast.cancel();
                    }
                    WorkUserActivity.this.finish();
                } else {
                    WorkUserActivity.this.backPressedOnce = true;
                    WorkUserActivity workUserActivity = WorkUserActivity.this;
                    workUserActivity.mToast = Toast.makeText(workUserActivity, workUserActivity.mResources.getString(R.string.press_again_to_exit), 0);
                    WorkUserActivity.this.mToast.show();
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: hazem.nurmontage.videoquran.WorkUserActivity.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            WorkUserActivity.this.backPressedOnce = false;
                        }
                    }, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
                }
            } catch (Exception unused) {
                WorkUserActivity.this.finish();
            }
        }
    };
    private WorkUserAdabter.IWorkUserCallback iWorkUserCallback = new WorkUserAdabter.IWorkUserCallback() { // from class: hazem.nurmontage.videoquran.WorkUserActivity.10
        @Override // hazem.nurmontage.videoquran.adabter.WorkUserAdabter.IWorkUserCallback
        public void onClick(Template template) {
            Intent intent = new Intent(WorkUserActivity.this, (Class<?>) EngineActivity.class);
            if (template.getIdTemplate() == null) {
                template.setIdTemplate(template.getUri_video());
            }
            intent.putExtra(Common.TEMPLATE, template.getIdTemplate());
            intent.addFlags(65536);
            WorkUserActivity.this.startActivity(intent);
            WorkUserActivity.this.overridePendingTransition(0, 0);
            WorkUserActivity.this.finish();
        }

        @Override // hazem.nurmontage.videoquran.adabter.WorkUserAdabter.IWorkUserCallback
        public void toMenu(Template template, View view, int i) {
            WorkUserActivity.this.showPopup(view, template, i);
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
        setContentView(R.layout.activity_work_user);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        getOnBackPressedDispatcher().addCallback(this, this.onBackPressedCallback);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), new OnApplyWindowInsetsListener() { // from class: hazem.nurmontage.videoquran.WorkUserActivity$$ExternalSyntheticLambda0
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                return WorkUserActivity.lambda$onCreate$0(view, windowInsetsCompat);
            }
        });
        setStatusBarColor(-1);
        setNavigationBarColor(-1);
        this.mResources = getResources();
        initRv();
        findViewById(R.id.btn_menu).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.WorkUserActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                WorkUserActivity.this.startActivity(new Intent(WorkUserActivity.this, (Class<?>) SeettingActivity.class));
                WorkUserActivity.this.finish();
            }
        });
        if (BillingPreferences.isSubscribed(this)) {
            return;
        }
        findViewById(R.id.tv_secret).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.WorkUserActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (BillingPreferences.isSubscribed(WorkUserActivity.this.getApplicationContext())) {
                    return;
                }
                WorkUserActivity.this.countClick++;
                if (WorkUserActivity.this.countClick >= 5) {
                    BillingPreferences.saveSubscriptionStatus(WorkUserActivity.this.getApplicationContext(), true);
                    Toast.makeText(WorkUserActivity.this.getApplicationContext(), "Subscribed", 0).show();
                }
            }
        });
    }

    static /* synthetic */ WindowInsetsCompat lambda$onCreate$0(View view, WindowInsetsCompat windowInsetsCompat) {
        Insets insets = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.systemBars());
        view.setPadding(insets.left, insets.top, insets.right, insets.bottom);
        return windowInsetsCompat;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showPopup(View view, final Template template, final int i) {
        if (template == null) {
            return;
        }
        View inflate = ((LayoutInflater) getSystemService("layout_inflater")).inflate(R.layout.layout_work_setup, (ViewGroup) null);
        PopupWindow popupWindow = new PopupWindow(inflate, -2, -2);
        this.popupWindow = popupWindow;
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        this.popupWindow.setOutsideTouchable(true);
        this.popupWindow.setFocusable(true);
        ((RelativeLayout) inflate.findViewById(R.id.btn_share)).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.WorkUserActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                try {
                    Intent intent = new Intent("android.intent.action.SEND");
                    intent.putExtra("act", "ACT_SHARE");
                    intent.putExtra("android.intent.extra.TITLE", "Send To");
                    WorkUserActivity workUserActivity = WorkUserActivity.this;
                    intent.putExtra("android.intent.extra.STREAM", FileProvider.getUriForFile(workUserActivity, workUserActivity.getResources().getString(R.string.file_provider), new File(Uri.parse(template.getUri_video()).getPath())));
                    intent.setType(MimeTypes.VIDEO_MP4);
                    WorkUserActivity.this.startActivity(Intent.createChooser(intent, "Send To"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (WorkUserActivity.this.popupWindow != null) {
                    WorkUserActivity.this.popupWindow.dismiss();
                }
            }
        });
        ((RelativeLayout) inflate.findViewById(R.id.btn_delete)).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.WorkUserActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                try {
                    WorkUserActivity workUserActivity = WorkUserActivity.this;
                    int i2 = i;
                    Template template2 = template;
                    workUserActivity.dialog(i2, template2, Uri.parse(template2.getUri_video()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        ((RelativeLayout) inflate.findViewById(R.id.btn_duplicate)).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.WorkUserActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                try {
                    Template duplicate = template.duplicate();
                    String str = duplicate.getIdTemplate() + "_copy";
                    duplicate.setIdTemplate(str);
                    LocalPersistence.duplicateTemplate(WorkUserActivity.this, duplicate, str);
                    WorkUserActivity.this.workUserAdabter.add(i + 1, duplicate);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (WorkUserActivity.this.popupWindow != null) {
                    WorkUserActivity.this.popupWindow.dismiss();
                }
            }
        });
        ((TextCustumFont) inflate.findViewById(R.id.tv_share)).setText(this.mResources.getString(R.string.just_share));
        ((TextCustumFont) inflate.findViewById(R.id.tv_duplicate)).setText(this.mResources.getString(R.string.duplicate));
        ((TextCustumFont) inflate.findViewById(R.id.tv_delete)).setText(this.mResources.getString(R.string.delete));
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        this.popupWindow.showAtLocation(view, 0, iArr[0], iArr[1] + view.getHeight());
    }

    private void toLang() {
        Intent intent = new Intent(this, (Class<?>) ChoiceLangActivity.class);
        intent.putExtra("from_setting", true);
        startActivity(intent);
        finish();
    }

    private void initRv() {
        SharedPreferences sharedPreferences = getSharedPreferences("MTemplate", 0);
        Map<String, ?> all = sharedPreferences.getAll();
        if (all != null && !all.isEmpty()) {
            Gson create = new GsonBuilder().create();
            final ArrayList arrayList = new ArrayList();
            Iterator<? extends Map.Entry<String, ?>> it = all.entrySet().iterator();
            while (it.hasNext()) {
                try {
                    Template template = (Template) create.fromJson(sharedPreferences.getString(it.next().getKey(), ""), Template.class);
                    if (template != null) {
                        if (template.getFileInfo() == null) {
                            template.setFileInfo(MFileUtils.getFileInfo(getApplicationContext(), template.getUri_video()));
                        }
                        arrayList.add(template);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Collections.sort(arrayList, new Comparator<Template>() { // from class: hazem.nurmontage.videoquran.WorkUserActivity.7
                @Override // java.util.Comparator
                public int compare(Template template2, Template template3) {
                    if (template2.getIdTemplate() == null || template3.getIdTemplate() == null) {
                        return 0;
                    }
                    return template3.getIdTemplate().compareTo(template2.getIdTemplate());
                }
            });
            final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv);
            recyclerView.post(new Runnable() { // from class: hazem.nurmontage.videoquran.WorkUserActivity.8
                @Override // java.lang.Runnable
                public void run() {
                    int screenWidth = (int) (ScreenUtils.getScreenWidth(WorkUserActivity.this) * 0.3f);
                    WorkUserActivity.this.workUserAdabter = new WorkUserAdabter(AppUtils.getAppVersionName(WorkUserActivity.this), arrayList, WorkUserActivity.this.iWorkUserCallback, screenWidth, screenWidth);
                    recyclerView.setLayoutManager(new LinearLayoutManager(WorkUserActivity.this, 1, false));
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setItemAnimator(null);
                    recyclerView.setAdapter(WorkUserActivity.this.workUserAdabter);
                }
            });
        }
        ButtonCustumFont buttonCustumFont = (ButtonCustumFont) findViewById(R.id.btn_to_studio);
        buttonCustumFont.setText(this.mResources.getString(R.string.create_video));
        buttonCustumFont.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.WorkUserActivity.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Intent intent = new Intent(WorkUserActivity.this, (Class<?>) EngineActivity.class);
                intent.addFlags(65536);
                WorkUserActivity.this.startActivity(intent);
                WorkUserActivity.this.overridePendingTransition(0, 0);
                WorkUserActivity.this.finish();
            }
        });
    }

    private void openPlayStoreForRating() {
        try {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + getPackageName()));
            intent.addFlags(1207959552);
            startActivity(intent);
        } catch (ActivityNotFoundException unused) {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
        }
    }

    private void shareApp() {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/plain");
        intent.putExtra("android.intent.extra.SUBJECT", "Check out this app!");
        intent.putExtra("android.intent.extra.TEXT", this.mResources.getString(R.string.share_mjs));
        startActivity(Intent.createChooser(intent, "Share via"));
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        cancelDialog();
    }

    private void cancelDialog() {
        Dialog dialog = this.dialog;
        if (dialog != null && dialog.isShowing()) {
            this.dialog.dismiss();
        }
        this.dialog = null;
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        try {
            Glide.get(this).clearMemory();
        } catch (Exception unused) {
        }
        this.iWorkUserCallback = null;
        this.onBackPressedCallback = null;
        cancelDialog();
    }

    public void dialog(final int i, final Template template, final Uri uri) {
        Dialog dialog = new Dialog(this);
        this.dialog = dialog;
        dialog.setCancelable(true);
        this.dialog.requestWindowFeature(1);
        this.dialog.getWindow().setLayout(-1, -2);
        this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        View inflate = LayoutInflater.from(this).inflate(R.layout.layout_dialog, (ViewGroup) null);
        this.dialog.setContentView(inflate);
        inflate.findViewById(R.id.dialog_title).setVisibility(8);
        ((TextCustumFont) inflate.findViewById(R.id.dialog_message)).setText(this.mResources.getString(R.string.are_you_sure_to_delete_this_work));
        ButtonCustumFont buttonCustumFont = (ButtonCustumFont) inflate.findViewById(R.id.dialog_no);
        buttonCustumFont.setText(this.mResources.getString(R.string.delete));
        buttonCustumFont.setTextColor(-1499549);
        buttonCustumFont.setBackgroundResource(R.drawable.btn_dialog_delete);
        buttonCustumFont.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.WorkUserActivity.11
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                try {
                    if (uri != null) {
                        FileUtils.forceDeleteOnExit(new File(uri.getPath()));
                    }
                    if (template.getIdTemplate() != null) {
                        LocalPersistence.deleteTemplate(WorkUserActivity.this, template.getIdTemplate());
                    } else if (template.getUri_video() != null) {
                        LocalPersistence.deleteTemplate(WorkUserActivity.this, template.getUri_video());
                    }
                    WorkUserActivity.this.workUserAdabter.remove(i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (WorkUserActivity.this.popupWindow != null) {
                    WorkUserActivity.this.popupWindow.dismiss();
                }
                if (WorkUserActivity.this.dialog != null) {
                    WorkUserActivity.this.dialog.dismiss();
                }
            }
        });
        ButtonCustumFont buttonCustumFont2 = (ButtonCustumFont) inflate.findViewById(R.id.dialog_yes);
        buttonCustumFont2.setText(this.mResources.getString(R.string.no));
        buttonCustumFont2.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.WorkUserActivity.12
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                WorkUserActivity.this.dialog.dismiss();
            }
        });
        this.dialog.show();
    }
}

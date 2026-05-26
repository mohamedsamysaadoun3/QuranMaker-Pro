package hazem.nurmontage.videoquran;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.media3.extractor.metadata.icy.IcyHeaders;
import com.arthenica.ffmpegkit.FFmpegKit;
import com.arthenica.ffmpegkit.FFmpegSession;
import com.arthenica.ffmpegkit.FFmpegSessionCompleteCallback;
import com.arthenica.ffmpegkit.ReturnCode;
import com.arthenica.ffmpegkit.Statistics;
import com.arthenica.ffmpegkit.StatisticsCallback;
import hazem.nurmontage.videoquran.Utils.AudioUtils;
import hazem.nurmontage.videoquran.Utils.ColorUtils;
import hazem.nurmontage.videoquran.Utils.Feadback;
import hazem.nurmontage.videoquran.Utils.FfmpegCodecChecker;
import hazem.nurmontage.videoquran.Utils.FileMediaScanner;
import hazem.nurmontage.videoquran.Utils.LocalPersistence;
import hazem.nurmontage.videoquran.Utils.LocaleHelper;
import hazem.nurmontage.videoquran.common.Common;
import hazem.nurmontage.videoquran.model.EntityBismilahTemplate;
import hazem.nurmontage.videoquran.model.EntityMedia;
import hazem.nurmontage.videoquran.model.EntityQuranTemplate;
import hazem.nurmontage.videoquran.model.RenderManager;
import hazem.nurmontage.videoquran.model.SquareBitmapModel;
import hazem.nurmontage.videoquran.model.Template;
import hazem.nurmontage.videoquran.views.ButtonCustumFont;
import hazem.nurmontage.videoquran.views.SquareOutlineProgressBar;
import hazem.nurmontage.videoquran.views.TextCustumFont;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import org.apache.commons.io.FileUtils;

/* loaded from: classes2.dex */
public class ProgressViewActivity extends Base {
    private Dialog dialog;
    private boolean isCancel;
    private volatile boolean isDestroy;
    private Template mTemplate;
    private String mUri;
    private SquareOutlineProgressBar progressIndicator;
    private Statistics statistics;
    private Thread workerThread;
    private final StringBuilder overlay = new StringBuilder();
    private final RenderManager renderManager = new RenderManager();
    private final OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) { // from class: hazem.nurmontage.videoquran.ProgressViewActivity.1
        @Override // androidx.activity.OnBackPressedCallback
        public void handleOnBackPressed() {
            ProgressViewActivity.this.dialog();
        }
    };
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final List<Long> id_ffmpeg = new ArrayList();
    private final Handler uiHandler = new Handler(Looper.getMainLooper());
    private float displayedProgress = 0.0f;
    private float targetProgress = 0.0f;
    private final int FRAME_MS = 16;
    private boolean isAnimating = false;
    private final Runnable runnableProgress = new Runnable() { // from class: hazem.nurmontage.videoquran.ProgressViewActivity.11
        @Override // java.lang.Runnable
        public void run() {
            ProgressViewActivity progressViewActivity = ProgressViewActivity.this;
            progressViewActivity.updateProgressDialog(progressViewActivity.statistics);
        }
    };

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        getWindow().setFlags(1536, 1536);
        super.onCreate(bundle);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_progress_view);
        getOnBackPressedDispatcher().addCallback(this, this.onBackPressedCallback);
        setStatusBarColor(ViewCompat.MEASURED_STATE_MASK);
        setNavigationBarColor(ViewCompat.MEASURED_STATE_MASK);
        WindowInsetsControllerCompat insetsController = WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(false);
        insetsController.setAppearanceLightNavigationBars(false);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), new OnApplyWindowInsetsListener() { // from class: hazem.nurmontage.videoquran.ProgressViewActivity$$ExternalSyntheticLambda4
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                return ProgressViewActivity.lambda$onCreate$0(view, windowInsetsCompat);
            }
        });
        wakeLockAquire();
        this.progressIndicator = (SquareOutlineProgressBar) findViewById(R.id.progress_horizontal);
        findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.ProgressViewActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ProgressViewActivity.this.dialog();
            }
        });
        try {
            startExport();
        } catch (Exception unused) {
            toStudio();
        }
    }

    static /* synthetic */ WindowInsetsCompat lambda$onCreate$0(View view, WindowInsetsCompat windowInsetsCompat) {
        Insets insets = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.systemBars());
        view.setPadding(insets.left, insets.top, insets.right, insets.bottom);
        return windowInsetsCompat;
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        cancelDialog();
    }

    public void dialog() {
        Dialog dialog = new Dialog(this);
        this.dialog = dialog;
        dialog.setCancelable(true);
        this.dialog.requestWindowFeature(1);
        this.dialog.getWindow().setLayout(-1, -2);
        this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        View inflate = LayoutInflater.from(this).inflate(R.layout.layout_dialog, (ViewGroup) null);
        this.dialog.setContentView(inflate);
        TextCustumFont textCustumFont = (TextCustumFont) inflate.findViewById(R.id.dialog_title);
        TextCustumFont textCustumFont2 = (TextCustumFont) inflate.findViewById(R.id.dialog_message);
        ButtonCustumFont buttonCustumFont = (ButtonCustumFont) inflate.findViewById(R.id.dialog_no);
        buttonCustumFont.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.ProgressViewActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ProgressViewActivity.this.isCancel = true;
                ProgressViewActivity.this.toStudio();
            }
        });
        ButtonCustumFont buttonCustumFont2 = (ButtonCustumFont) inflate.findViewById(R.id.dialog_yes);
        buttonCustumFont2.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.ProgressViewActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (ProgressViewActivity.this.dialog != null) {
                    ProgressViewActivity.this.dialog.dismiss();
                }
            }
        });
        if (LocaleHelper.getLanguage(this).equals("ar")) {
            textCustumFont2.setText("هل أنت متأكد من مغادرة هذا العمل؟");
            textCustumFont.setText("خروج...");
            buttonCustumFont.setText("مغادرة");
            buttonCustumFont2.setText("متابعة");
        } else {
            textCustumFont2.setText("Are you sure want to leave this work ?");
            textCustumFont.setText("Exit...");
            buttonCustumFont.setText("Leave");
            buttonCustumFont2.setText("Continue");
        }
        this.dialog.show();
    }

    private void startExport() {
        String stringExtra;
        if (getIntent() == null || (stringExtra = getIntent().getStringExtra(Common.TEMPLATE)) == null) {
            return;
        }
        Template template = (Template) LocalPersistence.readObjectFromFile(this, stringExtra);
        this.mTemplate = template;
        if (template != null) {
            this.mUri = template.getUri_video();
        }
        prepareAllMedia(this.mTemplate.getEntityMediaList(), new Runnable() { // from class: hazem.nurmontage.videoquran.ProgressViewActivity$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ProgressViewActivity.this.m822x215c536f();
            }
        });
    }

    /* renamed from: lambda$startExport$1$hazem-nurmontage-videoquran-ProgressViewActivity, reason: not valid java name */
    /* synthetic */ void m822x215c536f() {
        FfmpegCodecChecker.detectCodecsAsync(new FfmpegCodecChecker.CodecCallback() { // from class: hazem.nurmontage.videoquran.ProgressViewActivity$$ExternalSyntheticLambda3
            @Override // hazem.nurmontage.videoquran.Utils.FfmpegCodecChecker.CodecCallback
            public final void onResult(FfmpegCodecChecker.CodecInfo codecInfo) {
                ProgressViewActivity.this.setupCommand(codecInfo);
            }
        });
    }

    private void clearFFmpeg() {
        Iterator<Long> it = this.id_ffmpeg.iterator();
        while (it.hasNext()) {
            try {
                FFmpegKit.cancel(it.next().longValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void checkAacEncoder(Context context) {
        try {
            File createTempFile = File.createTempFile("aac_test", ".m4a", context.getCacheDir());
            createTempFile.deleteOnExit();
            FFmpegKit.executeAsync("-y -f lavfi -i anullsrc=channel_layout=stereo:sample_rate=44100 -t 1 -c:a aac -b:a 64k " + createTempFile.getAbsolutePath(), new FFmpegSessionCompleteCallback() { // from class: hazem.nurmontage.videoquran.ProgressViewActivity$$ExternalSyntheticLambda9
                @Override // com.arthenica.ffmpegkit.FFmpegSessionCompleteCallback
                public final void apply(FFmpegSession fFmpegSession) {
                    ProgressViewActivity.lambda$checkAacEncoder$2(fFmpegSession);
                }
            });
        } catch (Exception e) {
            Log.e("AAC workd", "Error checking AAC: " + e.getMessage());
        }
    }

    static /* synthetic */ void lambda$checkAacEncoder$2(FFmpegSession fFmpegSession) {
        if (ReturnCode.isSuccess(fFmpegSession.getReturnCode())) {
            Log.e("AAC workd", "AAC encoder is available!");
        } else {
            Log.e("AAC workd", "AAC encoder NOT supported in this build!");
            Log.e("AAC workd", fFmpegSession.getAllLogsAsString());
        }
    }

    private String mFadeFilter(float f, float f2, boolean z) {
        if (f2 - 0.05f <= 0.0f) {
            f2 = 0.01f;
        }
        return "fade=t=" + (z ? "in" : "out") + ":st=" + Math.abs(f) + ":d=" + Math.abs(f2) + ":alpha=1:color=white,fps=60,format=rgba";
    }

    private String fadeInOut(float f, float f2, float f3) {
        if (f <= 0.0f) {
            f = 0.01f;
        }
        if (f2 - 0.05f <= 0.0f) {
            f2 = 0.01f;
        }
        if (f3 - 0.05f <= 0.0f) {
            f3 = 0.01f;
        }
        return "fade=t=in:st=0:d=" + Math.abs(f2) + ":alpha=1:color=white,fps=" + this.mTemplate.getFps() + ",format=rgba,fade=t=out:st=" + Math.abs(f) + ":d=" + Math.abs(f3) + ":alpha=1:color=white,fps=" + this.mTemplate.getFps() + ",format=rgba";
    }

    private String fadeFilter(String str, int i, float f, float f2, boolean z) {
        String str2 = z ? "in" : "out";
        return str + "fade=t=" + str2 + ":st=" + f + ":d=" + Math.abs(f2 - 0.05f) + ":alpha=1:color=white,fps=60,format=rgba[" + str2 + "_" + i + "];";
    }

    private String fadeFilter(String str, float f, float f2, boolean z) {
        String str2 = z ? "in" : "out";
        return "[" + str + "]fade=t=" + str2 + ":st=" + f + ":d=" + Math.abs(f2 - 0.05f) + ":alpha=1:color=white,fps=60,format=rgba[" + str2 + "_" + str + "];";
    }

    private String fadeFilter(int i, float f, float f2, boolean z) {
        String str = z ? "in" : "out";
        return "[" + i + "]fade=t=" + str + ":st=" + f + ":d=" + Math.abs(f2 - 0.05f) + ":alpha=1:color=white,fps=60,format=rgba[" + str + "_" + i + "];";
    }

    private String slideX(float f, float f2, float f3, float f4, float f5, float f6) {
        String str = "clip((t-" + f + ")/" + f2 + ",0,1)";
        return "'" + f3 + "+(" + ("(" + f5 + "+(" + (f6 - f5) + ")*" + ("(" + str + "*" + str + "*(3-2*" + str + "))") + ")") + ")*" + f4 + "'";
    }

    private String mSlideX(float f, float f2, float f3, float f4, float f5, float f6) {
        String str = "clip((t-" + f + ")/" + f2 + ",0,1)";
        return f3 + "+(" + ("(" + f5 + "+(" + (f6 - f5) + ")*" + ("(" + str + "*" + str + "*(3-2*" + str + "))") + ")") + ")*" + f4;
    }

    private File getOrCreateMask(int i, int i2, int i3) {
        File file = new File(getFilesDir(), "mask_" + i + "x" + i2 + "_r" + i3 + ".png");
        if (file.exists()) {
            return file;
        }
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        Paint paint = new Paint(1);
        paint.setColor(-1);
        RectF rectF = new RectF(0.0f, 0.0f, i, i2);
        float f = i3;
        canvas.drawRoundRect(rectF, f, f, paint);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            try {
                createBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                fileOutputStream.close();
            } finally {
            }
        } catch (Exception unused) {
        }
        return file;
    }

    private File createTransparentBg(int i, int i2) {
        File file = new File(getFilesDir(), "bg_tr_.png");
        if (file.exists()) {
            return file;
        }
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            try {
                createBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                fileOutputStream.close();
            } finally {
            }
        } catch (Exception unused) {
        }
        return file;
    }

    private String generateVideoTimer(int i, final CountDownLatch countDownLatch, final Semaphore semaphore) {
        String str = this.mTemplate.getFolder_template() + "/timer.mov";
        int max = Math.max(i / 1000, 1);
        this.renderManager.addTask("timer prerender", max);
        float posXRight = this.mTemplate.getmTimeModel().getPosXRight();
        String color = this.mTemplate.getmTimeModel().getColor();
        float size = this.mTemplate.getmTimeModel().getSize();
        String str2 = getFilesDir().getAbsolutePath() + "/NotoNaskhArabic.ttf";
        ArrayList arrayList = new ArrayList();
        arrayList.add("-y");
        arrayList.add("-f");
        arrayList.add("lavfi");
        arrayList.add("-i");
        arrayList.add("color=size=" + Math.round(this.mTemplate.getmTimeModel().getWidth_bitmap_progress() * 1.3f) + "x" + this.mTemplate.getmTimeModel().getHeight_bitmap_progress() + ":rate=10:duration=" + max + ":color=" + (ColorUtils.isColorDark(Color.parseColor(color)) ? "black@0" : "white@0") + ",format=rgba");
        int i2 = max + 1;
        arrayList.add("-vf");
        arrayList.add("drawtext=fontfile='" + str2 + "':text='%{eif\\:trunc(t/60)\\:d\\:2}\\:%{eif\\:trunc(mod(t\\,60))\\:d\\:2}':x=0.0:y=0.0:fontsize=" + size + ":fontcolor=" + color + ",drawtext=fontfile='" + str2 + "':text='-%{eif\\:trunc((" + i2 + "-t)/60)\\:d\\:2}\\:%{eif\\:trunc(mod(" + i2 + "-t\\,60))\\:d\\:2}':x=" + posXRight + ":y=0.0:fontsize=" + size + ":fontcolor=" + color);
        arrayList.add("-c:v");
        arrayList.add("qtrle");
        arrayList.add("-pix_fmt");
        arrayList.add("argb");
        arrayList.add("-preset");
        arrayList.add("veryfast");
        arrayList.add("-avoid_negative_ts");
        arrayList.add("make_zero");
        arrayList.add(str);
        try {
            semaphore.acquire();
            this.id_ffmpeg.add(Long.valueOf(FFmpegKit.executeWithArgumentsAsync((String[]) arrayList.toArray(new String[0]), new FFmpegSessionCompleteCallback() { // from class: hazem.nurmontage.videoquran.ProgressViewActivity$$ExternalSyntheticLambda12
                @Override // com.arthenica.ffmpegkit.FFmpegSessionCompleteCallback
                public final void apply(FFmpegSession fFmpegSession) {
                    ProgressViewActivity.this.m816x4bb4e94a(countDownLatch, semaphore, fFmpegSession);
                }
            }, null, new ProgressViewActivity$$ExternalSyntheticLambda6(this)).getSessionId()));
            return str;
        } catch (InterruptedException unused) {
            this.renderManager.nextTask();
            countDownLatch.countDown();
            return null;
        }
    }

    /* renamed from: lambda$generateVideoTimer$3$hazem-nurmontage-videoquran-ProgressViewActivity, reason: not valid java name */
    /* synthetic */ void m816x4bb4e94a(CountDownLatch countDownLatch, Semaphore semaphore, FFmpegSession fFmpegSession) {
        updateNext(countDownLatch, semaphore);
    }

    private String runPreRender(String str, String str2, String str3, int i, String str4, boolean z, final CountDownLatch countDownLatch, final Semaphore semaphore, String str5) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("-hide_banner");
        arrayList.add("-y");
        arrayList.add("-stream_loop");
        arrayList.add("-1");
        arrayList.add("-i");
        arrayList.add(str);
        if (str2 != null) {
            arrayList.add("-i");
            arrayList.add(str2);
        }
        arrayList.add("-filter_complex");
        arrayList.add(str3);
        if (z) {
            arrayList.add("-c:v");
            arrayList.add("qtrle");
            arrayList.add("-pix_fmt");
            arrayList.add("rgba");
        } else if (str5 != null) {
            arrayList.add("-threads");
            arrayList.add("0");
            arrayList.add("-c:v");
            arrayList.add(str5);
            arrayList.add("-preset");
            arrayList.add("fast");
            arrayList.add("-crf");
            arrayList.add("18");
        } else {
            arrayList.add("-b:v");
            arrayList.add("4M");
        }
        arrayList.add("-r");
        arrayList.add(String.valueOf(this.mTemplate.getFps()));
        arrayList.add("-t");
        arrayList.add(Math.max(i, 500) + "ms");
        if (!z) {
            arrayList.add("-movflags");
            arrayList.add("+faststart");
        }
        arrayList.add(str4);
        try {
            semaphore.acquire();
            this.id_ffmpeg.add(Long.valueOf(FFmpegKit.executeWithArgumentsAsync((String[]) arrayList.toArray(new String[0]), new FFmpegSessionCompleteCallback() { // from class: hazem.nurmontage.videoquran.ProgressViewActivity$$ExternalSyntheticLambda5
                @Override // com.arthenica.ffmpegkit.FFmpegSessionCompleteCallback
                public final void apply(FFmpegSession fFmpegSession) {
                    ProgressViewActivity.this.m820x77a4a45a(countDownLatch, semaphore, fFmpegSession);
                }
            }, null, new ProgressViewActivity$$ExternalSyntheticLambda6(this)).getSessionId()));
            return str4;
        } catch (InterruptedException unused) {
            this.renderManager.nextTask();
            countDownLatch.countDown();
            return null;
        }
    }

    /* renamed from: lambda$runPreRender$4$hazem-nurmontage-videoquran-ProgressViewActivity, reason: not valid java name */
    /* synthetic */ void m820x77a4a45a(CountDownLatch countDownLatch, Semaphore semaphore, FFmpegSession fFmpegSession) {
        updateNext(countDownLatch, semaphore);
    }

    private void updateNext(CountDownLatch countDownLatch, Semaphore semaphore) {
        RenderManager renderManager = this.renderManager;
        if (renderManager != null) {
            renderManager.nextTask();
        }
        if (semaphore != null) {
            semaphore.release();
        }
        if (countDownLatch != null) {
            countDownLatch.countDown();
        }
    }

    public String preRenderMask_Rounded(SquareBitmapModel squareBitmapModel, int i, CountDownLatch countDownLatch, Semaphore semaphore) {
        String uri_media_video = this.mTemplate.getUri_media_video();
        String str = this.mTemplate.getFolder_template() + "/rounded_" + System.currentTimeMillis() + ".mov";
        int max = Math.max(this.mTemplate.getWidth(), this.mTemplate.getHeight());
        int round = Math.round(squareBitmapModel.getRight());
        int round2 = Math.round(squareBitmapModel.getBottom());
        int round3 = Math.round(squareBitmapModel.getLef_square());
        int round4 = Math.round(squareBitmapModel.getTop_square());
        int round5 = Math.round(squareBitmapModel.getWidth_sqaure());
        int round6 = Math.round(squareBitmapModel.getHeight_square());
        if ((round5 & 1) == 1) {
            round5++;
        }
        if ((round6 & 1) == 1) {
            round6++;
        }
        return runPreRender(uri_media_video, getOrCreateMask(round5, round6, (int) squareBitmapModel.getRaduis()).getAbsolutePath(), "[0:v]scale=" + max + ":" + max + ":force_original_aspect_ratio=increase,crop=" + round + ":" + round2 + ":" + round3 + ":" + round4 + ",scale=" + round5 + ":" + round6 + ":flags=lanczos[v];[v][1:v]alphamerge,format=rgba", i, str, true, countDownLatch, semaphore, null);
    }

    public String preRenderMask_Circle(SquareBitmapModel squareBitmapModel, int i, CountDownLatch countDownLatch, Semaphore semaphore) {
        String uri_media_video = this.mTemplate.getUri_media_video();
        String str = this.mTemplate.getFolder_template() + "/circle_" + System.currentTimeMillis() + ".mov";
        int max = Math.max(this.mTemplate.getWidth(), this.mTemplate.getHeight());
        int round = Math.round(squareBitmapModel.getRight());
        int round2 = Math.round(squareBitmapModel.getBottom());
        int round3 = Math.round(squareBitmapModel.getLef_square());
        int round4 = Math.round(squareBitmapModel.getTop_square());
        int round5 = Math.round(squareBitmapModel.getWidth_sqaure());
        int round6 = Math.round(squareBitmapModel.getHeight_square());
        if ((round5 & 1) == 1) {
            round5++;
        }
        if ((round6 & 1) == 1) {
            round6++;
        }
        return runPreRender(uri_media_video, getOrCreateMaskCircle(round5, round6).getAbsolutePath(), "[0:v]scale=" + max + ":" + max + ":force_original_aspect_ratio=increase,crop=" + round + ":" + round2 + ":" + round3 + ":" + round4 + ",scale=" + round5 + ":" + round6 + ":flags=lanczos[v];[v][1:v]alphamerge,format=rgba", i, str, true, countDownLatch, semaphore, null);
    }

    public String preRender_NoMask(SquareBitmapModel squareBitmapModel, int i, CountDownLatch countDownLatch, Semaphore semaphore, String str) {
        String uri_media_video = this.mTemplate.getUri_media_video();
        String str2 = this.mTemplate.getFolder_template() + "/nomask_" + System.currentTimeMillis() + ".mp4";
        int max = Math.max(this.mTemplate.getWidth(), this.mTemplate.getHeight());
        int round = Math.round(squareBitmapModel.getRight());
        int round2 = Math.round(squareBitmapModel.getBottom());
        int round3 = Math.round(squareBitmapModel.getLef_square());
        int round4 = Math.round(squareBitmapModel.getTop_square());
        int round5 = Math.round(squareBitmapModel.getWidth_sqaure());
        int round6 = Math.round(squareBitmapModel.getHeight_square());
        if ((round5 & 1) == 1) {
            round5++;
        }
        if ((round6 & 1) == 1) {
            round6++;
        }
        return runPreRender(uri_media_video, null, "scale=" + max + ":" + max + ":force_original_aspect_ratio=increase,crop=" + round + ":" + round2 + ":" + round3 + ":" + round4 + ",scale=" + round5 + ":" + round6 + ":flags=lanczos,format=yuv420p", i, str2, false, countDownLatch, semaphore, str);
    }

    private File getOrCreateMaskCircle(int i, int i2) {
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint(1);
        paint.setColor(-1);
        canvas.drawCircle(i / 2.0f, i2 / 2.0f, Math.min(i, i2) / 2.0f, paint);
        File file = new File(this.mTemplate.getFolder_template(), "circle_" + i + "x" + i2 + ".png");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            createBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.close();
        } catch (Exception unused) {
        }
        return file;
    }

    public String preRenderVideo(int i, final CountDownLatch countDownLatch, final Semaphore semaphore, String str) {
        String uri_media_video = this.mTemplate.getUri_media_video();
        String str2 = this.mTemplate.getFolder_template() + "/layer_video_" + System.currentTimeMillis() + ".mp4";
        int max = Math.max(this.mTemplate.getWidth(), this.mTemplate.getHeight());
        String str3 = "[0:v]scale=" + max + ":" + max + ":force_original_aspect_ratio=increase:flags=lanczos,crop=" + this.mTemplate.getWidth() + ":" + this.mTemplate.getHeight() + ":" + ("(iw-" + this.mTemplate.getWidth() + ")/2") + ":" + ("(ih-" + this.mTemplate.getHeight() + ")/2") + "[v];[v][1:v]overlay,format=rgba";
        ArrayList arrayList = new ArrayList();
        arrayList.add("-hide_banner");
        arrayList.add("-y");
        arrayList.add("-stream_loop");
        arrayList.add("-1");
        arrayList.add("-i");
        arrayList.add(uri_media_video);
        File file = new File(this.mTemplate.getUri_bg_ffmpeg());
        if (file.exists() && file.isFile()) {
            arrayList.add("-i");
            arrayList.add(this.mTemplate.getUri_bg_ffmpeg());
            arrayList.add("-filter_complex");
            arrayList.add(str3);
            if (str != null) {
                arrayList.add("-threads");
                arrayList.add("0");
                arrayList.add("-c:v");
                arrayList.add(str);
                arrayList.add("-preset");
                arrayList.add("fast");
                arrayList.add("-crf");
                arrayList.add("18");
            } else {
                arrayList.add("-b:v");
                arrayList.add("4M");
            }
            arrayList.add("-r");
            arrayList.add(String.valueOf(this.mTemplate.getFps()));
            arrayList.add("-t");
            arrayList.add(Math.max(i, 500) + "ms");
            arrayList.add("-movflags");
            arrayList.add("+faststart");
            arrayList.add("-an");
            arrayList.add(str2);
            try {
                semaphore.acquire();
                this.id_ffmpeg.add(Long.valueOf(FFmpegKit.executeWithArgumentsAsync((String[]) arrayList.toArray(new String[0]), new FFmpegSessionCompleteCallback() { // from class: hazem.nurmontage.videoquran.ProgressViewActivity$$ExternalSyntheticLambda7
                    @Override // com.arthenica.ffmpegkit.FFmpegSessionCompleteCallback
                    public final void apply(FFmpegSession fFmpegSession) {
                        ProgressViewActivity.this.m817xa8871725(countDownLatch, semaphore, fFmpegSession);
                    }
                }, null, new ProgressViewActivity$$ExternalSyntheticLambda6(this)).getSessionId()));
                return str2;
            } catch (InterruptedException unused) {
                this.renderManager.nextTask();
                countDownLatch.countDown();
                return null;
            }
        }
        updateNext(countDownLatch, semaphore);
        return null;
    }

    /* renamed from: lambda$preRenderVideo$5$hazem-nurmontage-videoquran-ProgressViewActivity, reason: not valid java name */
    /* synthetic */ void m817xa8871725(CountDownLatch countDownLatch, Semaphore semaphore, FFmpegSession fFmpegSession) {
        updateNext(countDownLatch, semaphore);
    }

    public String preRenderVideoHue(int i, final CountDownLatch countDownLatch, final Semaphore semaphore, String str) {
        String uri_media_video = this.mTemplate.getUri_media_video();
        String str2 = this.mTemplate.getFolder_template() + "/layer_video_" + System.currentTimeMillis() + ".mp4";
        int max = Math.max(this.mTemplate.getWidth(), this.mTemplate.getHeight());
        int width = this.mTemplate.getWidth();
        int height = this.mTemplate.getHeight();
        String str3 = "[0:v]scale=" + max + ":" + max + ":force_original_aspect_ratio=increase:flags=lanczos,hue=s=0,crop=" + width + ":" + height + ":" + ("(iw-" + width + ")/2") + ":" + ("(ih-" + height + ")/2") + "[main];[main][1]overlay[fm];[2:v]loop=loop=-1:size=1:start=0,setpts=N/FRAME_RATE/TB[lineProg];[3:v]loop=loop=-1:size=1:start=0,setpts=N/FRAME_RATE/TB[lineBg];[lineProg][lineBg]overlay=x=" + ((-this.mTemplate.getmTimeModel().getWidth_bitmap_progress()) + " + ((cos((t / (" + (i / 1000.0d) + ") + 1) * PI) / 2 + 0.5) * " + (this.mTemplate.getmTimeModel().getWidth_bitmap_progress() - this.mTemplate.getmTimeModel().getProgress_offset()) + ")") + ":y=0[bgApplied];[fm][bgApplied]overlay=" + this.mTemplate.getEntityProgressTemplate().getLeft() + ":" + this.mTemplate.getEntityProgressTemplate().getTop();
        ArrayList arrayList = new ArrayList();
        arrayList.add("-hide_banner");
        arrayList.add("-y");
        arrayList.add("-i");
        arrayList.add(uri_media_video);
        File file = new File(this.mTemplate.getUri_bg_ffmpeg());
        if (file.exists() && file.isFile()) {
            arrayList.add("-i");
            arrayList.add(this.mTemplate.getUri_bg_ffmpeg());
            arrayList.add("-i");
            arrayList.add(this.mTemplate.getFolder_template() + "/line_progress.png");
            arrayList.add("-i");
            arrayList.add(this.mTemplate.getFolder_template() + "/line_bg.png");
            arrayList.add("-filter_complex");
            arrayList.add(str3);
            if (str != null) {
                arrayList.add("-c:v");
                arrayList.add(str);
                arrayList.add("-preset");
                arrayList.add("fast");
                arrayList.add("-crf");
                arrayList.add("18");
            } else {
                arrayList.add("-c:v");
                arrayList.add("libx264");
                arrayList.add("-preset");
                arrayList.add("veryfast");
                arrayList.add("-crf");
                arrayList.add("18");
            }
            arrayList.add("-r");
            arrayList.add(String.valueOf(this.mTemplate.getFps()));
            arrayList.add("-t");
            arrayList.add(Math.max(i, 500) + "ms");
            arrayList.add("-movflags");
            arrayList.add("+faststart");
            arrayList.add("-an");
            arrayList.add(str2);
            try {
                semaphore.acquire();
                this.id_ffmpeg.add(Long.valueOf(FFmpegKit.executeWithArgumentsAsync((String[]) arrayList.toArray(new String[0]), new FFmpegSessionCompleteCallback() { // from class: hazem.nurmontage.videoquran.ProgressViewActivity$$ExternalSyntheticLambda8
                    @Override // com.arthenica.ffmpegkit.FFmpegSessionCompleteCallback
                    public final void apply(FFmpegSession fFmpegSession) {
                        ProgressViewActivity.this.m818x87180b4a(countDownLatch, semaphore, fFmpegSession);
                    }
                }, null, new ProgressViewActivity$$ExternalSyntheticLambda6(this)).getSessionId()));
                return str2;
            } catch (InterruptedException unused) {
                this.renderManager.nextTask();
                countDownLatch.countDown();
                return null;
            }
        }
        updateNext(countDownLatch, semaphore);
        return null;
    }

    /* renamed from: lambda$preRenderVideoHue$6$hazem-nurmontage-videoquran-ProgressViewActivity, reason: not valid java name */
    /* synthetic */ void m818x87180b4a(CountDownLatch countDownLatch, Semaphore semaphore, FFmpegSession fFmpegSession) {
        updateNext(countDownLatch, semaphore);
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x0b63  */
    /* JADX WARN: Removed duplicated region for block: B:44:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x066b  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x089e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private int addBasmala(hazem.nurmontage.videoquran.model.EntityBismilahTemplate r93, int r94, java.util.concurrent.Semaphore r95, java.util.concurrent.CountDownLatch r96, java.util.List<java.lang.String> r97, float r98) {
        /*
            Method dump skipped, instructions count: 3057
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: hazem.nurmontage.videoquran.ProgressViewActivity.addBasmala(hazem.nurmontage.videoquran.model.EntityBismilahTemplate, int, java.util.concurrent.Semaphore, java.util.concurrent.CountDownLatch, java.util.List, float):int");
    }

    public void prepareAllMedia(final List<EntityMedia> list, final Runnable runnable) {
        Executors.newSingleThreadExecutor().execute(new Runnable() { // from class: hazem.nurmontage.videoquran.ProgressViewActivity$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                ProgressViewActivity.this.m819xa2a9e2f5(list, runnable);
            }
        });
    }

    /* renamed from: lambda$prepareAllMedia$7$hazem-nurmontage-videoquran-ProgressViewActivity, reason: not valid java name */
    /* synthetic */ void m819xa2a9e2f5(List list, Runnable runnable) {
        try {
            if (list != null) {
                try {
                } catch (Exception e) {
                    e.printStackTrace();
                    if (runnable == null) {
                        return;
                    }
                }
                if (!list.isEmpty()) {
                    for (int i = 0; i < list.size(); i++) {
                        EntityMedia entityMedia = (EntityMedia) list.get(i);
                        if (entityMedia != null) {
                            try {
                                if (entityMedia.getEnd() >= entityMedia.getStart() && entityMedia.getPath_ffmpeg_effect() == null && entityMedia.getUri() != null) {
                                    String downloadFile = entityMedia.getUri().startsWith("http") ? AudioUtils.downloadFile(this, entityMedia.getUri(), this.mTemplate.getFolder_template()) : AudioUtils.copyFromUri(this, Uri.parse(entityMedia.getUri()), this.mTemplate.getFolder_template());
                                    if (downloadFile != null) {
                                        entityMedia.setPath_ffmpeg(downloadFile);
                                        entityMedia.setPath_ffmpeg_effect(downloadFile);
                                    }
                                }
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }
                        }
                    }
                    if (runnable != null) {
                        runnable.run();
                        return;
                    }
                    return;
                }
            }
            Log.w("AudioUtils", "Media list is null or empty");
            if (runnable != null) {
                runnable.run();
            }
        } catch (Throwable th) {
            if (runnable != null) {
                runnable.run();
            }
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:176:0x1bb9  */
    /* JADX WARN: Removed duplicated region for block: B:196:0x1ccf A[Catch: Exception -> 0x1e6e, TryCatch #0 {Exception -> 0x1e6e, blocks: (B:194:0x1cc3, B:196:0x1ccf, B:201:0x1cf1, B:203:0x1d00, B:206:0x1d07), top: B:193:0x1cc3 }] */
    /* JADX WARN: Removed duplicated region for block: B:225:0x1e89  */
    /* JADX WARN: Removed duplicated region for block: B:230:0x1ef1  */
    /* JADX WARN: Removed duplicated region for block: B:234:0x1f1a  */
    /* JADX WARN: Removed duplicated region for block: B:235:0x1ecc  */
    /* JADX WARN: Removed duplicated region for block: B:242:0x1e6c  */
    /* JADX WARN: Removed duplicated region for block: B:243:0x1e52 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0f07  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void setupCommand(hazem.nurmontage.videoquran.Utils.FfmpegCodecChecker.CodecInfo r86) {
        /*
            Method dump skipped, instructions count: 8078
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: hazem.nurmontage.videoquran.ProgressViewActivity.setupCommand(hazem.nurmontage.videoquran.Utils.FfmpegCodecChecker$CodecInfo):void");
    }

    /* renamed from: lambda$setupCommand$8$hazem-nurmontage-videoquran-ProgressViewActivity, reason: not valid java name */
    /* synthetic */ void m821x9b61da96(CountDownLatch countDownLatch, List list) {
        try {
            // Add timeout to prevent infinite waiting
            if (!countDownLatch.await(120, java.util.concurrent.TimeUnit.SECONDS)) {
                // Timeout reached - something went wrong
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            toStudio();
                        } catch (Exception ignored) {}
                    }
                });
                return;
            }
            if (Thread.currentThread().isInterrupted() && this.isDestroy) {
                return;
            }
            export((String[]) list.toArray(new String[0]));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String concatVideoSegments(List<String> list) {
        try {
            File file = new File(this.mTemplate.getFolder_template() + "/file_list.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            Iterator<String> it = list.iterator();
            while (it.hasNext()) {
                bufferedWriter.write("file '" + it.next() + "'\n");
            }
            bufferedWriter.close();
            String str = this.mTemplate.getFolder_template() + "/final_video.mp4";
            ArrayList arrayList = new ArrayList();
            arrayList.add("-y");
            arrayList.add("-f");
            arrayList.add("concat");
            arrayList.add("-safe");
            arrayList.add("0");
            arrayList.add("-i");
            arrayList.add(file.getAbsolutePath());
            arrayList.add("-c");
            arrayList.add("copy");
            arrayList.add(str);
            FFmpegKit.executeWithArguments((String[]) arrayList.toArray(new String[0]));
            return str;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String generateVideoSegment(EntityQuranTemplate entityQuranTemplate, int i, String str, int i2, final CountDownLatch countDownLatch, final Semaphore semaphore) {
        this.renderManager.addTask("anim prerender", i2);
        String str2 = this.mTemplate.getFolder_template() + "/ayah_" + i + ".mov";
        ArrayList arrayList = new ArrayList();
        arrayList.add("-y");
        arrayList.add("-loop");
        arrayList.add(IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE);
        arrayList.add("-i");
        arrayList.add(this.mTemplate.getFolder_template() + "/" + entityQuranTemplate.getFile());
        arrayList.add("-vf");
        arrayList.add(str);
        arrayList.add("-t");
        arrayList.add(String.valueOf(Math.max(i2, 1)));
        arrayList.add("-c:v");
        arrayList.add("qtrle");
        arrayList.add("-pix_fmt");
        arrayList.add("argb");
        arrayList.add("-preset");
        arrayList.add("veryfast");
        arrayList.add("-avoid_negative_ts");
        arrayList.add("make_zero");
        arrayList.add(str2);
        try {
            semaphore.acquire();
            this.id_ffmpeg.add(Long.valueOf(FFmpegKit.executeWithArgumentsAsync((String[]) arrayList.toArray(new String[0]), new FFmpegSessionCompleteCallback() { // from class: hazem.nurmontage.videoquran.ProgressViewActivity$$ExternalSyntheticLambda2
                @Override // com.arthenica.ffmpegkit.FFmpegSessionCompleteCallback
                public final void apply(FFmpegSession fFmpegSession) {
                    ProgressViewActivity.this.m815x2d35bd16(countDownLatch, semaphore, fFmpegSession);
                }
            }, null, new ProgressViewActivity$$ExternalSyntheticLambda6(this)).getSessionId()));
            return str2;
        } catch (InterruptedException unused) {
            this.renderManager.nextTask();
            countDownLatch.countDown();
            return null;
        }
    }

    /* renamed from: lambda$generateVideoSegment$9$hazem-nurmontage-videoquran-ProgressViewActivity, reason: not valid java name */
    /* synthetic */ void m815x2d35bd16(CountDownLatch countDownLatch, Semaphore semaphore, FFmpegSession fFmpegSession) {
        updateNext(countDownLatch, semaphore);
    }

    private String generateVideoSegment(EntityBismilahTemplate entityBismilahTemplate, int i, String str, int i2, final CountDownLatch countDownLatch, final Semaphore semaphore) {
        this.renderManager.addTask("anim prerender", i2);
        String str2 = this.mTemplate.getFolder_template() + "/bismilah_" + i + ".mov";
        ArrayList arrayList = new ArrayList();
        arrayList.add("-y");
        arrayList.add("-loop");
        arrayList.add(IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE);
        arrayList.add("-i");
        arrayList.add(this.mTemplate.getFolder_template() + "/" + entityBismilahTemplate.getFile());
        arrayList.add("-vf");
        arrayList.add(str);
        arrayList.add("-t");
        arrayList.add(String.valueOf(Math.max(i2, 1)));
        arrayList.add("-c:v");
        arrayList.add("qtrle");
        arrayList.add("-pix_fmt");
        arrayList.add("argb");
        arrayList.add("-preset");
        arrayList.add("veryfast");
        arrayList.add("-avoid_negative_ts");
        arrayList.add("make_zero");
        arrayList.add(str2);
        try {
            semaphore.acquire();
            this.id_ffmpeg.add(Long.valueOf(FFmpegKit.executeWithArgumentsAsync((String[]) arrayList.toArray(new String[0]), new FFmpegSessionCompleteCallback() { // from class: hazem.nurmontage.videoquran.ProgressViewActivity$$ExternalSyntheticLambda1
                @Override // com.arthenica.ffmpegkit.FFmpegSessionCompleteCallback
                public final void apply(FFmpegSession fFmpegSession) {
                    ProgressViewActivity.this.m814x3040977c(countDownLatch, semaphore, fFmpegSession);
                }
            }, null, new ProgressViewActivity$$ExternalSyntheticLambda6(this)).getSessionId()));
            return str2;
        } catch (Exception unused) {
            this.renderManager.nextTask();
            countDownLatch.countDown();
            return null;
        }
    }

    /* renamed from: lambda$generateVideoSegment$10$hazem-nurmontage-videoquran-ProgressViewActivity, reason: not valid java name */
    /* synthetic */ void m814x3040977c(CountDownLatch countDownLatch, Semaphore semaphore, FFmpegSession fFmpegSession) {
        updateNext(countDownLatch, semaphore);
    }

    private String getBitrate(int i, int i2, int i3) {
        boolean z = i3 > 30;
        int max = Math.max(i, i2);
        if (max <= 720) {
            return z ? "2000k" : "1500k";
        }
        if (max <= 1280) {
            return z ? "4000k" : "3000k";
        }
        if (max <= 1920) {
            return z ? "6000k" : "4500k";
        }
        return "8000k";
    }

    private String getBestH264Codec() {
        String output;
        try {
            FFmpegSession execute = FFmpegKit.execute("-hide_banner -encoders");
            if (!ReturnCode.isSuccess(execute.getReturnCode()) || (output = execute.getOutput()) == null) {
                return null;
            }
            String lowerCase = output.toLowerCase();
            boolean contains = lowerCase.contains(" h264_mediacodec ");
            boolean contains2 = lowerCase.contains(" libx264 ");
            if (!contains2 && contains) {
                return "h264_mediacodec";
            }
            int i = Build.VERSION.SDK_INT;
            if (i <= 29) {
                if (contains2) {
                    return "libx264";
                }
                if (contains) {
                    return "h264_mediacodec";
                }
                return null;
            }
            if (i == 30) {
                if (contains2) {
                    return "libx264";
                }
                if (contains) {
                    return "h264_mediacodec";
                }
                return null;
            }
            if (contains) {
                return "h264_mediacodec";
            }
            if (contains2) {
                return "libx264";
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void releaseWakeLock() {
        try {
            getWindow().clearFlags(128);
        } catch (Exception unused) {
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        clearFFmpeg();
        super.onDestroy();
        try {
            this.isDestroy = true;
            releaseWakeLock();
            new Thread(new Runnable() { // from class: hazem.nurmontage.videoquran.ProgressViewActivity.5
                @Override // java.lang.Runnable
                public void run() {
                    if (ProgressViewActivity.this.mTemplate != null) {
                        ProgressViewActivity.this.deleteFolderWithCommonsIO(new File(ProgressViewActivity.this.mTemplate.getFolder_template()));
                    }
                }
            }).start();
            Thread thread = this.workerThread;
            if (thread != null) {
                thread.interrupt();
            }
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deleteFolderWithCommonsIO(File file) {
        if (Build.VERSION.SDK_INT >= 26) {
            try {
                FileUtils.deleteDirectory(file);
                return;
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        deleteDirectoryManually(file);
    }

    private void deleteDirectoryManually(File file) {
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                for (File file2 : listFiles) {
                    if (file2.isDirectory()) {
                        deleteDirectoryManually(file2);
                    } else {
                        file2.delete();
                    }
                }
            }
            file.delete();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void completeProgress() {
        this.isDestroy = true;
        this.uiHandler.post(new Runnable() { // from class: hazem.nurmontage.videoquran.ProgressViewActivity.6
            @Override // java.lang.Runnable
            public void run() {
                if (ProgressViewActivity.this.isCancel) {
                    return;
                }
                ProgressViewActivity.this.displayedProgress += (100.0f - ProgressViewActivity.this.displayedProgress) * 0.45f;
                ProgressViewActivity.this.progressIndicator.setProgress(Math.min(Math.max(Math.round(ProgressViewActivity.this.displayedProgress), 0), ProgressViewActivity.this.progressIndicator.getMax()));
                boolean z = ((float) ProgressViewActivity.this.progressIndicator.getProgress()) >= 100.0f;
                boolean z2 = Math.abs(ProgressViewActivity.this.displayedProgress - 100.0f) < 0.1f;
                if (z || z2) {
                    ProgressViewActivity.this.progressIndicator.setProgress(100);
                    ProgressViewActivity.this.displayedProgress = 100.0f;
                    ProgressViewActivity.this.targetProgress = 100.0f;
                    ProgressViewActivity progressViewActivity = ProgressViewActivity.this;
                    progressViewActivity.insertToGallery(Uri.parse(progressViewActivity.mUri));
                    ProgressViewActivity.this.toShare();
                    return;
                }
                ProgressViewActivity.this.uiHandler.postDelayed(this, 16L);
            }
        });
    }

    protected void updateProgressDialog(Statistics statistics) {
        if (statistics == null || this.isDestroy) {
            return;
        }
        try {
            int time = (int) statistics.getTime();
            if (time <= 0) {
                return;
            }
            float currentStepDuration = (time / 1000.0f) / this.renderManager.getCurrentStepDuration();
            if (currentStepDuration > 1.0f) {
                currentStepDuration = 1.0f;
            }
            this.targetProgress = this.renderManager.updateLocalProgress(currentStepDuration) * 100.0f;
            if (this.isAnimating) {
                return;
            }
            startSmoothAnimation();
        } catch (Exception unused) {
        }
    }

    private void startSmoothAnimation() {
        this.isAnimating = true;
        this.uiHandler.post(new Runnable() { // from class: hazem.nurmontage.videoquran.ProgressViewActivity.7
            @Override // java.lang.Runnable
            public void run() {
                if (ProgressViewActivity.this.isDestroy) {
                    return;
                }
                ProgressViewActivity.this.displayedProgress += (ProgressViewActivity.this.targetProgress - ProgressViewActivity.this.displayedProgress) * 0.1f;
                ProgressViewActivity.this.progressIndicator.setProgress(Math.max(0, Math.min(Math.round(ProgressViewActivity.this.displayedProgress), ProgressViewActivity.this.progressIndicator.getMax())));
                if (Math.abs(ProgressViewActivity.this.displayedProgress - ProgressViewActivity.this.targetProgress) > 0.1f) {
                    ProgressViewActivity.this.uiHandler.postDelayed(this, 16L);
                } else {
                    ProgressViewActivity.this.isAnimating = false;
                }
            }
        });
    }

    public void export(String[] strArr) {
        this.id_ffmpeg.add(Long.valueOf(FFmpegKit.executeWithArgumentsAsync(strArr, new FFmpegSessionCompleteCallback() { // from class: hazem.nurmontage.videoquran.ProgressViewActivity.8
            @Override // com.arthenica.ffmpegkit.FFmpegSessionCompleteCallback
            public void apply(final FFmpegSession fFmpegSession) {
                ProgressViewActivity.this.runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.ProgressViewActivity.8.1
                    @Override // java.lang.Runnable
                    public void run() {
                        ProgressViewActivity.this.cancelDialog();
                        if (ProgressViewActivity.this.renderManager != null) {
                            ProgressViewActivity.this.renderManager.nextTask();
                        }
                        if (ReturnCode.isSuccess(fFmpegSession.getReturnCode())) {
                            ProgressViewActivity.this.completeProgress();
                        } else {
                            ProgressViewActivity.this.showError(fFmpegSession);
                        }
                    }
                });
            }
        }, null, new StatisticsCallback() { // from class: hazem.nurmontage.videoquran.ProgressViewActivity.9
            @Override // com.arthenica.ffmpegkit.StatisticsCallback
            public void apply(Statistics statistics) {
                ProgressViewActivity.this.statistics = statistics;
                ProgressViewActivity progressViewActivity = ProgressViewActivity.this;
                progressViewActivity.runOnUiThread(progressViewActivity.runnableProgress);
            }
        }).getSessionId()));
    }

    /* renamed from: hazem.nurmontage.videoquran.ProgressViewActivity$10, reason: invalid class name */
    class AnonymousClass10 implements Runnable {
        final /* synthetic */ FFmpegSession val$fFmpegSession;

        AnonymousClass10(FFmpegSession fFmpegSession) {
            this.val$fFmpegSession = fFmpegSession;
        }

        @Override // java.lang.Runnable
        public void run() {
            final StringBuilder sb = new StringBuilder();
            if (ProgressViewActivity.this.overlay != null) {
                sb.append((CharSequence) ProgressViewActivity.this.overlay).append("\n");
            }
            String output = this.val$fFmpegSession.getOutput();
            if (output != null) {
                sb.append(output);
            }
            final LinearLayout linearLayout = (LinearLayout) ProgressViewActivity.this.findViewById(R.id.layout_error);
            linearLayout.post(new Runnable() { // from class: hazem.nurmontage.videoquran.ProgressViewActivity.10.1
                @Override // java.lang.Runnable
                public void run() {
                    linearLayout.setVisibility(0);
                    TextCustumFont textCustumFont = (TextCustumFont) ProgressViewActivity.this.findViewById(R.id.tv_error);
                    final ButtonCustumFont buttonCustumFont = (ButtonCustumFont) ProgressViewActivity.this.findViewById(R.id.btn_support_team);
                    if (LocaleHelper.getLanguage(ProgressViewActivity.this.getApplicationContext()).equals("ar")) {
                        buttonCustumFont.setText("فريق الدعم");
                        textCustumFont.setText("يوجد مشكلة في هذا التصميم ، لن يتم حفظ هذا الفيديو أخبر فريق الدعم ");
                    } else {
                        buttonCustumFont.setText("Support Team");
                        textCustumFont.setText("There is a problem with this design, this video won't be saved. Tell the support team.");
                    }
                    buttonCustumFont.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.ProgressViewActivity.10.1.1
                        @Override // android.view.View.OnClickListener
                        public void onClick(View view) {
                            Feadback.reportBug(ProgressViewActivity.this, sb.toString(), buttonCustumFont.getText().toString());
                        }
                    });
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showError(FFmpegSession fFmpegSession) {
        try {
            runOnUiThread(new AnonymousClass10(fFmpegSession));
        } catch (Exception unused) {
            toStudio();
        }
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
    public void insertToGallery(Uri uri) {
        if (uri == null) {
            return;
        }
        File file = new File(uri.getPath());
        if (file.exists()) {
            try {
                new FileMediaScanner(this, file);
                sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", uri));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void toStudio() {
        Intent intent = new Intent(this, (Class<?>) EngineActivity.class);
        Template template = this.mTemplate;
        if (template != null) {
            intent.putExtra(Common.TEMPLATE, template.getIdTemplate());
        }
        intent.addFlags(65536);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void toShare() {
        Intent intent = new Intent(this, (Class<?>) VideoViewActivity.class);
        if (this.mTemplate.getEntitySurahTemplate() != null) {
            intent.putExtra(Common.SURAH, this.mTemplate.getEntitySurahTemplate().getName());
            intent.putExtra(Common.READER, this.mTemplate.getEntitySurahTemplate().getReader());
        } else {
            intent.putExtra(Common.SURAH, "");
            intent.putExtra(Common.READER, "");
        }
        intent.putExtra(Common.TEMPLATE, this.mTemplate.getIdTemplate());
        intent.setData(Uri.parse(this.mTemplate.getUri_video()));
        intent.addFlags(65536);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }
}

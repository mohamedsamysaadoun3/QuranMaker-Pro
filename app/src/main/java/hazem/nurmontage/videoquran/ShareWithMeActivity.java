package hazem.nurmontage.videoquran;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import com.arthenica.ffmpegkit.FFmpegKit;
import com.arthenica.ffmpegkit.FFmpegSession;
import com.arthenica.ffmpegkit.FFmpegSessionCompleteCallback;
import com.arthenica.ffmpegkit.ReturnCode;
import hazem.nurmontage.videoquran.Utils.AudioUploadHelper;
import hazem.nurmontage.videoquran.Utils.LocalPersistence;
import hazem.nurmontage.videoquran.common.Common;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/* loaded from: classes2.dex */
public class ShareWithMeActivity extends Base {
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_share_with_me);
        setStatusBarColor(-1);
        setNavigationBarColor(-1);
        WindowInsetsControllerCompat insetsController = WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);
        insetsController.setAppearanceLightNavigationBars(true);
        handleIntent(getIntent());
    }

    private void handleIntent(Intent intent) {
        String type = intent.getType();
        if (type == null) {
            startActivity(new Intent(this, (Class<?>) WorkUserActivity.class));
            finish();
            return;
        }
        LocalPersistence.deleteTemplate(this, Common.TEMPLATE_TMP);
        if (type.startsWith("image/")) {
            handleImg(intent);
        } else if (type.startsWith("audio/")) {
            handleAudio(intent);
        } else if (type.startsWith("video/")) {
            handleVideo(intent);
        }
    }

    private void handleVideo(Intent intent) {
        Uri uri = (Uri) getParcelable(intent, "android.intent.extra.STREAM", Uri.class);
        if (uri != null) {
            processVideo(uri);
        }
    }

    private File copyVideoToCache(Uri uri) {
        File file = new File(getCacheDir(), "temp_video.mp4");
        try {
            InputStream openInputStream = getContentResolver().openInputStream(uri);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] bArr = new byte[4096];
            while (true) {
                int read = openInputStream.read(bArr);
                if (read == -1) {
                    break;
                }
                fileOutputStream.write(bArr, 0, read);
            }
            openInputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    private void processVideo(Uri uri) {
        final String absolutePath = copyVideoToCache(uri).getAbsolutePath();
        final String str = getExternalFilesDir(null) + "/share_with_me.m4a";
        FFmpegKit.executeAsync("-y -i \"" + absolutePath + "\" -vn -map 0:a? -c:a copy \"" + str + "\"", new FFmpegSessionCompleteCallback() { // from class: hazem.nurmontage.videoquran.ShareWithMeActivity$$ExternalSyntheticLambda0
            @Override // com.arthenica.ffmpegkit.FFmpegSessionCompleteCallback
            public final void apply(FFmpegSession fFmpegSession) {
                ShareWithMeActivity.this.m876x79670e10(str, absolutePath, fFmpegSession);
            }
        });
    }

    /* renamed from: lambda$processVideo$3$hazem-nurmontage-videoquran-ShareWithMeActivity, reason: not valid java name */
    /* synthetic */ void m876x79670e10(final String str, String str2, FFmpegSession fFmpegSession) {
        if (ReturnCode.isSuccess(fFmpegSession.getReturnCode())) {
            runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.ShareWithMeActivity$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    ShareWithMeActivity.this.m873x7aca400d(str);
                }
            });
        } else {
            FFmpegKit.executeAsync("-y -i \"" + str2 + "\" -vn -map 0:a? -c:a aac -b:a 192k \"" + str + "\"", new FFmpegSessionCompleteCallback() { // from class: hazem.nurmontage.videoquran.ShareWithMeActivity$$ExternalSyntheticLambda2
                @Override // com.arthenica.ffmpegkit.FFmpegSessionCompleteCallback
                public final void apply(FFmpegSession fFmpegSession2) {
                    ShareWithMeActivity.this.m875x79dd740f(str, fFmpegSession2);
                }
            });
        }
    }

    /* renamed from: lambda$processVideo$0$hazem-nurmontage-videoquran-ShareWithMeActivity, reason: not valid java name */
    /* synthetic */ void m873x7aca400d(String str) {
        toEngine(Uri.parse(str), str);
    }

    /* renamed from: lambda$processVideo$2$hazem-nurmontage-videoquran-ShareWithMeActivity, reason: not valid java name */
    /* synthetic */ void m875x79dd740f(final String str, FFmpegSession fFmpegSession) {
        if (ReturnCode.isSuccess(fFmpegSession.getReturnCode())) {
            runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.ShareWithMeActivity$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    ShareWithMeActivity.this.m874x7a53da0e(str);
                }
            });
        } else {
            runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.ShareWithMeActivity.1
                @Override // java.lang.Runnable
                public void run() {
                    ShareWithMeActivity.this.startActivity(new Intent(ShareWithMeActivity.this, (Class<?>) WorkUserActivity.class));
                    ShareWithMeActivity.this.finish();
                }
            });
        }
    }

    /* renamed from: lambda$processVideo$1$hazem-nurmontage-videoquran-ShareWithMeActivity, reason: not valid java name */
    /* synthetic */ void m874x7a53da0e(String str) {
        toEngine(Uri.parse(str), str);
    }

    private void toEngine(Uri uri, String str) {
        Intent intent = new Intent(this, (Class<?>) EngineActivity.class);
        intent.setData(uri);
        intent.putExtra("muri", str);
        startActivity(intent);
        finish();
    }

    private String savePermanent(Uri uri) {
        try {
            InputStream openInputStream = getContentResolver().openInputStream(uri);
            File file = new File(getFilesDir(), "img_" + System.currentTimeMillis() + ".jpg");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] bArr = new byte[1024];
            while (true) {
                int read = openInputStream.read(bArr);
                if (read > 0) {
                    fileOutputStream.write(bArr, 0, read);
                } else {
                    openInputStream.close();
                    fileOutputStream.close();
                    return file.getAbsolutePath();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void handleImg(Intent intent) {
        Uri uri = (Uri) getParcelable(intent, "android.intent.extra.STREAM", Uri.class);
        if (uri != null) {
            Intent intent2 = new Intent(this, (Class<?>) EngineActivity.class);
            intent2.putExtra("img_bg", savePermanent(uri));
            startActivity(intent2);
            finish();
        }
    }

    private void handleAudio(Intent intent) {
        Uri uri = (Uri) getParcelable(intent, "android.intent.extra.STREAM", Uri.class);
        if (uri != null) {
            Intent intent2 = new Intent(this, (Class<?>) EngineActivity.class);
            File processAudioUriForUpload = AudioUploadHelper.processAudioUriForUpload(this, uri, "share_with_me.mp3");
            intent2.setData(uri);
            intent2.putExtra("muri", processAudioUriForUpload.getAbsolutePath());
            startActivity(intent2);
            finish();
        }
    }

    private <T extends Parcelable> T getParcelable(Intent intent, String str, Class<T> cls) {
        if (Build.VERSION.SDK_INT >= 33) {
            return (T) intent.getParcelableExtra(str, cls);
        }
        return (T) intent.getParcelableExtra(str);
    }
}

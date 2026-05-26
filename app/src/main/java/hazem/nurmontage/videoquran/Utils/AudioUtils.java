package hazem.nurmontage.videoquran.Utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import hazem.nurmontage.videoquran.Utils.AudioUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes2.dex */
public class AudioUtils {
    private static final String TAG = "AudioUtils";
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();
    private static final Handler mainHandler = new Handler(Looper.getMainLooper());

    public interface Callback {
        void onError(Exception exc);

        void onSuccess(String str);
    }

    public static void copyToLocalAsync(final Context context, final String str, final String str2, final Callback callback) {
        executor.execute(new Runnable() { // from class: hazem.nurmontage.videoquran.Utils.AudioUtils$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                AudioUtils.lambda$copyToLocalAsync$2(str, context, str2, callback);
            }
        });
    }

    static /* synthetic */ void lambda$copyToLocalAsync$2(String str, Context context, String str2, final Callback callback) {
        final String copyFromUri;
        try {
            if (str.startsWith("http")) {
                copyFromUri = downloadFile(context, str, str2);
            } else {
                copyFromUri = copyFromUri(context, Uri.parse(str), str2);
            }
            mainHandler.post(new Runnable() { // from class: hazem.nurmontage.videoquran.Utils.AudioUtils$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    AudioUtils.lambda$copyToLocalAsync$0(copyFromUri, callback);
                }
            });
        } catch (Exception e) {
            mainHandler.post(new Runnable() { // from class: hazem.nurmontage.videoquran.Utils.AudioUtils$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    callback.onError(e);
                }
            });
        }
    }

    static /* synthetic */ void lambda$copyToLocalAsync$0(String str, Callback callback) {
        if (str != null) {
            callback.onSuccess(str);
        } else {
            callback.onError(new Exception("Failed to process file"));
        }
    }

    public static String downloadFile(Context context, String str, String str2) {
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setReadTimeout(15000);
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() != 200) {
                Log.e(TAG, "HTTP error: " + httpURLConnection.getResponseCode());
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                return null;
            }
            InputStream inputStream = httpURLConnection.getInputStream();
            File file = new File(str2);
            if (!file.exists()) {
                file.mkdirs();
            }
            File file2 = new File(file, "audio_" + System.currentTimeMillis() + ".mp3");
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            byte[] bArr = new byte[8192];
            while (true) {
                int read = inputStream.read(bArr);
                if (read == -1) {
                    break;
                }
                fileOutputStream.write(bArr, 0, read);
            }
            inputStream.close();
            fileOutputStream.close();
            String absolutePath = file2.getAbsolutePath();
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            return absolutePath;
        } catch (Exception e) {
            Log.e(TAG, "Download error", e);
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            return null;
        }
    }

    public static String copyFromUri(Context context, Uri uri, String str) {
        String addUniqueSuffix;
        try {
            InputStream openInputStream = context.getContentResolver().openInputStream(uri);
            if (openInputStream == null) {
                return null;
            }
            File file = new File(str);
            if (!file.exists()) {
                file.mkdirs();
            }
            String fileName = getFileName(context, uri);
            if (fileName == null) {
                addUniqueSuffix = "audio_" + System.currentTimeMillis() + ".mp3";
            } else {
                addUniqueSuffix = addUniqueSuffix(fileName);
            }
            File file2 = new File(file, addUniqueSuffix);
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            byte[] bArr = new byte[8192];
            while (true) {
                int read = openInputStream.read(bArr);
                if (read != -1) {
                    fileOutputStream.write(bArr, 0, read);
                } else {
                    openInputStream.close();
                    fileOutputStream.close();
                    return file2.getAbsolutePath();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "URI copy error", e);
            return null;
        }
    }

    private static String getFileName(Context context, Uri uri) {
        int columnIndex;
        if ("content".equals(uri.getScheme())) {
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(uri, null, null, null, null);
                if (cursor != null && cursor.moveToFirst() && (columnIndex = cursor.getColumnIndex("_display_name")) != -1) {
                    return cursor.getString(columnIndex);
                }
                if (cursor != null) {
                    cursor.close();
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        return uri.getLastPathSegment();
    }

    private static String addUniqueSuffix(String str) {
        int lastIndexOf = str.lastIndexOf(".");
        return (lastIndexOf > 0 ? str.substring(0, lastIndexOf) : str) + "_" + UUID.randomUUID() + (lastIndexOf > 0 ? str.substring(lastIndexOf) : "");
    }
}

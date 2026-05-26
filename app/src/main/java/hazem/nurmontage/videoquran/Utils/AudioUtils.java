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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00a7  */
    /* JADX WARN: Type inference failed for: r2v0 */
    /* JADX WARN: Type inference failed for: r2v1, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r2v2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.String downloadFile(android.content.Context r7, java.lang.String r8, java.lang.String r9) {
        /*
            java.lang.String r7 = "AudioUtils"
            java.lang.String r0 = "audio_"
            java.lang.String r1 = "HTTP error: "
            r2 = 0
            java.net.URL r3 = new java.net.URL     // Catch: java.lang.Throwable -> L94 java.lang.Exception -> L96
            r3.<init>(r8)     // Catch: java.lang.Throwable -> L94 java.lang.Exception -> L96
            java.net.URLConnection r8 = r3.openConnection()     // Catch: java.lang.Throwable -> L94 java.lang.Exception -> L96
            java.net.HttpURLConnection r8 = (java.net.HttpURLConnection) r8     // Catch: java.lang.Throwable -> L94 java.lang.Exception -> L96
            r3 = 15000(0x3a98, float:2.102E-41)
            r8.setConnectTimeout(r3)     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> La3
            r8.setReadTimeout(r3)     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> La3
            r8.connect()     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> La3
            int r3 = r8.getResponseCode()     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> La3
            r4 = 200(0xc8, float:2.8E-43)
            if (r3 == r4) goto L3f
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> La3
            r9.<init>(r1)     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> La3
            int r0 = r8.getResponseCode()     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> La3
            java.lang.StringBuilder r9 = r9.append(r0)     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> La3
            java.lang.String r9 = r9.toString()     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> La3
            android.util.Log.e(r7, r9)     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> La3
            if (r8 == 0) goto L3e
            r8.disconnect()
        L3e:
            return r2
        L3f:
            java.io.InputStream r1 = r8.getInputStream()     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> La3
            java.io.File r3 = new java.io.File     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> La3
            r3.<init>(r9)     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> La3
            boolean r9 = r3.exists()     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> La3
            if (r9 != 0) goto L51
            r3.mkdirs()     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> La3
        L51:
            java.io.File r9 = new java.io.File     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> La3
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> La3
            r4.<init>(r0)     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> La3
            long r5 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> La3
            java.lang.StringBuilder r0 = r4.append(r5)     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> La3
            java.lang.String r4 = ".mp3"
            java.lang.StringBuilder r0 = r0.append(r4)     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> La3
            java.lang.String r0 = r0.toString()     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> La3
            r9.<init>(r3, r0)     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> La3
            java.io.FileOutputStream r0 = new java.io.FileOutputStream     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> La3
            r0.<init>(r9)     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> La3
            r3 = 8192(0x2000, float:1.148E-41)
            byte[] r3 = new byte[r3]     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> La3
        L76:
            int r4 = r1.read(r3)     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> La3
            r5 = -1
            if (r4 == r5) goto L82
            r5 = 0
            r0.write(r3, r5, r4)     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> La3
            goto L76
        L82:
            r1.close()     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> La3
            r0.close()     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> La3
            java.lang.String r7 = r9.getAbsolutePath()     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> La3
            if (r8 == 0) goto L91
            r8.disconnect()
        L91:
            return r7
        L92:
            r9 = move-exception
            goto L98
        L94:
            r7 = move-exception
            goto La5
        L96:
            r9 = move-exception
            r8 = r2
        L98:
            java.lang.String r0 = "Download error"
            android.util.Log.e(r7, r0, r9)     // Catch: java.lang.Throwable -> La3
            if (r8 == 0) goto La2
            r8.disconnect()
        La2:
            return r2
        La3:
            r7 = move-exception
            r2 = r8
        La5:
            if (r2 == 0) goto Laa
            r2.disconnect()
        Laa:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: hazem.nurmontage.videoquran.Utils.AudioUtils.downloadFile(android.content.Context, java.lang.String, java.lang.String):java.lang.String");
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

package hazem.nurmontage.videoquran.Utils;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/* loaded from: classes2.dex */
public class UtilsFile {
    public static File getPath(Context context, Uri uri) {
        if (uri == null) {
            return null;
        }
        if ("file".equals(uri.getScheme())) {
            return new File(uri.getPath());
        }
        if ("content".equals(uri.getScheme())) {
            return copyContentUriToFile(context, uri);
        }
        return null;
    }

    private static File copyContentUriToFile(Context context, Uri uri) {
        ContentResolver contentResolver = context.getContentResolver();
        File file = new File(context.getCacheDir(), "temp_file");
        try {
            InputStream openInputStream = contentResolver.openInputStream(uri);
            if (openInputStream == null) {
                return null;
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] bArr = new byte[4096];
            while (true) {
                int read = openInputStream.read(bArr);
                if (read != -1) {
                    fileOutputStream.write(bArr, 0, read);
                } else {
                    fileOutputStream.close();
                    openInputStream.close();
                    return file;
                }
            }
        } catch (Exception e) {
            Log.e("UtilsFile", "Error copying content URI to file", e);
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x003e, code lost:
    
        if (r8 == null) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:5:0x002d, code lost:
    
        if (r8 != null) goto L14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x002f, code lost:
    
        r8.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x0041, code lost:
    
        return null;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0046  */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v3, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r0v4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.String getDataColumn(android.content.Context r8, android.net.Uri r9, java.lang.String r10, java.lang.String[] r11) {
        /*
            r0 = 1
            java.lang.String[] r3 = new java.lang.String[r0]
            r0 = 0
            java.lang.String r7 = "_data"
            r3[r0] = r7
            r0 = 0
            android.content.ContentResolver r1 = r8.getContentResolver()     // Catch: java.lang.Throwable -> L33 java.lang.Exception -> L35
            r6 = 0
            r2 = r9
            r4 = r10
            r5 = r11
            android.database.Cursor r8 = r1.query(r2, r3, r4, r5, r6)     // Catch: java.lang.Throwable -> L33 java.lang.Exception -> L35
            if (r8 == 0) goto L2d
            boolean r9 = r8.moveToFirst()     // Catch: java.lang.Exception -> L2b java.lang.Throwable -> L42
            if (r9 == 0) goto L2d
            int r9 = r8.getColumnIndexOrThrow(r7)     // Catch: java.lang.Exception -> L2b java.lang.Throwable -> L42
            java.lang.String r9 = r8.getString(r9)     // Catch: java.lang.Exception -> L2b java.lang.Throwable -> L42
            if (r8 == 0) goto L2a
            r8.close()
        L2a:
            return r9
        L2b:
            r9 = move-exception
            goto L37
        L2d:
            if (r8 == 0) goto L41
        L2f:
            r8.close()
            goto L41
        L33:
            r9 = move-exception
            goto L44
        L35:
            r9 = move-exception
            r8 = r0
        L37:
            java.lang.String r10 = "UtilsFile"
            java.lang.String r11 = "Error getting data column"
            android.util.Log.e(r10, r11, r9)     // Catch: java.lang.Throwable -> L42
            if (r8 == 0) goto L41
            goto L2f
        L41:
            return r0
        L42:
            r9 = move-exception
            r0 = r8
        L44:
            if (r0 == 0) goto L49
            r0.close()
        L49:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: hazem.nurmontage.videoquran.Utils.UtilsFile.getDataColumn(android.content.Context, android.net.Uri, java.lang.String, java.lang.String[]):java.lang.String");
    }
}

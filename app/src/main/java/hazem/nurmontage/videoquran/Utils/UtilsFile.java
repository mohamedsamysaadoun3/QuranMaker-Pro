package hazem.nurmontage.videoquran.Utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
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

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        String[] projection = {"_data"};
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                return cursor.getString(cursor.getColumnIndexOrThrow("_data"));
            }
            return null;
        } catch (Exception e) {
            Log.e("UtilsFile", "Error getting data column", e);
            return null;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}

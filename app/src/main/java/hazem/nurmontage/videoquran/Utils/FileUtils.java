package hazem.nurmontage.videoquran.Utils;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import androidx.media3.common.MimeTypes;
import hazem.nurmontage.videoquran.common.Common;
import java.io.File;

/* loaded from: classes2.dex */
public class FileUtils {
    public static boolean checkFileExists(String str) {
        return new File(str).exists();
    }

    public static File getFile(Context context) {
        File externalFilesDir = context.getExternalFilesDir(null);
        if (externalFilesDir == null) {
            return null;
        }
        if (!externalFilesDir.exists() && !externalFilesDir.mkdirs()) {
            Log.e("TAG getFileVideo", "! mkdirs.");
            return null;
        }
        File file = new File(externalFilesDir, "Work_" + System.currentTimeMillis());
        if (file.exists() || file.mkdirs()) {
            return file;
        }
        Log.e("TAG getFileVideo", "! mkdirs.");
        return null;
    }

    public static File getFileVideo(String str) {
        File file = new File(str);
        if (!file.exists() && !file.mkdirs()) {
            Log.e("TAG getFileVideo", "! mkdirs.");
            return null;
        }
        File file2 = new File(file, Common.VIDEO_FRAME_FOLDER);
        if (file2.exists() || file2.mkdirs()) {
            return file2;
        }
        Log.e("TAG getFileVideo", "! mkdirs.");
        return null;
    }

    public static File getFileFromUri(Context context, Uri uri) throws Exception {
        String str = null;
        str = null;
        Uri uri2 = null;
        str = null;
        if (DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                String[] split = DocumentsContract.getDocumentId(uri).split(":");
                if ("primary".equalsIgnoreCase(split[0])) {
                    str = Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) {
                str = getDataColumn(context, ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(DocumentsContract.getDocumentId(uri)).longValue()), null, null);
            } else if (isMediaDocument(uri)) {
                String[] split2 = DocumentsContract.getDocumentId(uri).split(":");
                String str2 = split2[0];
                if ("image".equals(str2)) {
                    uri2 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if (MimeTypes.BASE_TYPE_VIDEO.equals(str2)) {
                    uri2 = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if (MimeTypes.BASE_TYPE_AUDIO.equals(str2)) {
                    uri2 = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                str = getDataColumn(context, uri2, "_id=?", new String[]{split2[1]});
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            str = getDataColumn(context, uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            str = uri.getPath();
        }
        return new File(str);
    }

    public static String getDataColumn(Context context, Uri uri, String str, String[] strArr) {
        Cursor cursor = null;
        try {
            Cursor query = context.getContentResolver().query(uri, new String[]{"_data"}, str, strArr, null);
            if (query != null) {
                try {
                    if (query.moveToFirst()) {
                        String string = query.getString(query.getColumnIndexOrThrow("_data"));
                        if (query != null) {
                            query.close();
                        }
                        return string;
                    }
                } catch (Throwable th) {
                    if (query != null) {
                        query.close();
                    }
                    throw th;
                }
            }
            if (query != null) {
                query.close();
            }
            return null;
        } catch (Throwable th2) {
            throw th2;
        }
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
}

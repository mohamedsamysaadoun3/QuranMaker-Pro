package hazem.nurmontage.videoquran.Utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* loaded from: classes2.dex */
public class MFileUtils {

    public static class FileInfo implements Serializable {
        public String formattedDate;
        public long lastModified;
        public String name;
        public String timedDate;

        public FileInfo(String str, long j) {
            this.name = str;
            this.lastModified = j;
            this.formattedDate = MFileUtils.formatDateShort(j);
            if (j > 0) {
                this.timedDate = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date(j));
            }
        }
    }

    public static FileInfo getFileInfo(Context context, String path) {
        if (path == null) {
            return null;
        }
        Uri uri = Uri.parse(path);
        String name = null;
        long lastModified = 0;
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(uri,
                        new String[]{"_display_name", "date_modified"}, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    int nameIndex = cursor.getColumnIndex("_display_name");
                    int dateIndex = cursor.getColumnIndex("date_modified");
                    if (nameIndex != -1) {
                        name = cursor.getString(nameIndex);
                    }
                    if (dateIndex != -1) {
                        long dateValue = cursor.getLong(dateIndex);
                        if (dateValue > 0) {
                            lastModified = dateValue * 1000;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (name == null || lastModified == 0) {
            try {
                File file;
                if ("file".equalsIgnoreCase(uri.getScheme())) {
                    file = new File(uri.getPath());
                } else {
                    file = new File(path);
                }
                if (file.exists()) {
                    if (name == null) {
                        name = file.getName();
                    }
                    if (lastModified == 0) {
                        lastModified = file.lastModified();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (name == null) {
            name = uri.getLastPathSegment();
        }
        return new FileInfo(name, lastModified);
    }

    public static String formatDateShort(long j) {
        if (j <= 0) {
            return "";
        }
        return new SimpleDateFormat("MMM dd-yyyy", Locale.ENGLISH).format(new Date(j));
    }
}

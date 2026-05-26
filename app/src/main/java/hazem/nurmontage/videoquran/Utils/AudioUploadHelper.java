package hazem.nurmontage.videoquran.Utils;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes2.dex */
public class AudioUploadHelper {
    private static final String TAG = "AudioUploadHelper";

    public static File processAudioUriForUpload(Context context, Uri uri, String str) {
        InputStream inputStream = null;
        File file = null;
        try {
            inputStream = context.getContentResolver().openInputStream(uri);
            if (inputStream == null) {
                Log.e(TAG, "Failed to open InputStream for URI: " + uri);
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        Log.e(TAG, "Error closing InputStream", e);
                    }
                }
                return null;
            }
            file = new File(context.getExternalFilesDir(null), str);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            try {
                byte[] bArr = new byte[4096];
                while (true) {
                    int read = inputStream.read(bArr);
                    if (read == -1) {
                        break;
                    }
                    fileOutputStream.write(bArr, 0, read);
                }
                fileOutputStream.flush();
            } finally {
                fileOutputStream.close();
            }
            Log.d(TAG, "Audio content copied to cache file: " + file.getAbsolutePath());
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e2) {
                    Log.e(TAG, "Error closing InputStream", e2);
                }
            }
            return file;
        } catch (FileNotFoundException e3) {
            Log.e(TAG, "File not found for URI (or permission issue): " + uri, e3);
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e4) {
                    Log.e(TAG, "Error closing InputStream", e4);
                }
            }
            return null;
        } catch (IOException e5) {
            Log.e(TAG, "IOException while processing URI: " + uri, e5);
            if (file != null && file.exists()) {
                file.delete();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e6) {
                    Log.e(TAG, "Error closing InputStream", e6);
                }
            }
            return null;
        }
    }
}

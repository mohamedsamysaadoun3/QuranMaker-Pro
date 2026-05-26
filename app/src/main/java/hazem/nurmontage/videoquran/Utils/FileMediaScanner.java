package hazem.nurmontage.videoquran.Utils;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import java.io.File;

/* loaded from: classes2.dex */
public class FileMediaScanner implements MediaScannerConnection.MediaScannerConnectionClient {
    private File file;
    public MediaScannerConnection mediaScannerConnection;

    public FileMediaScanner(Context context, File file) {
        this.file = file;
        MediaScannerConnection mediaScannerConnection = new MediaScannerConnection(context, this);
        this.mediaScannerConnection = mediaScannerConnection;
        mediaScannerConnection.connect();
    }

    @Override // android.media.MediaScannerConnection.MediaScannerConnectionClient
    public void onMediaScannerConnected() {
        this.mediaScannerConnection.scanFile(this.file.getAbsolutePath(), null);
    }

    @Override // android.media.MediaScannerConnection.OnScanCompletedListener
    public void onScanCompleted(String str, Uri uri) {
        this.mediaScannerConnection.disconnect();
    }
}

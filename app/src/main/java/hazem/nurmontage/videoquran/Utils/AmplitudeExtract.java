package hazem.nurmontage.videoquran.Utils;

import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import hazem.nurmontage.videoquran.Utils.AmplitudeExtract;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes2.dex */
public class AmplitudeExtract {
    private static final String TAG = "AudioAmplitudeReader";
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    public interface AmplitudeDataCallback {
        void onComplete(List<Float> list);

        void onError(Exception exc);
    }

    public void extractAmplitudeDataAsync(final String str, final int i, final AmplitudeDataCallback amplitudeDataCallback) {
        this.executorService.execute(new Runnable() { // from class: hazem.nurmontage.videoquran.Utils.AmplitudeExtract$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                AmplitudeExtract.this.m897x997f050d(str, i, amplitudeDataCallback);
            }
        });
    }

    /* renamed from: lambda$extractAmplitudeDataAsync$2$hazem-nurmontage-videoquran-Utils-AmplitudeExtract, reason: not valid java name */
    /* synthetic */ void m897x997f050d(String str, int i, final AmplitudeDataCallback amplitudeDataCallback) {
        try {
            final List<Float> extractAmplitudeData = extractAmplitudeData(str, i);
            this.mainHandler.post(new Runnable() { // from class: hazem.nurmontage.videoquran.Utils.AmplitudeExtract$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    amplitudeDataCallback.onComplete(extractAmplitudeData);
                }
            });
        } catch (IOException | IllegalArgumentException e) {
            Log.e(TAG, "Error extracting amplitude data", e);
            this.mainHandler.post(new Runnable() { // from class: hazem.nurmontage.videoquran.Utils.AmplitudeExtract$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    amplitudeDataCallback.onError(e);
                }
            });
        }
    }

    private List<Float> extractAmplitudeData(String str, int i) throws IOException, IllegalArgumentException {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("File path cannot be null or empty.");
        }
        if (i <= 0) {
            throw new IllegalArgumentException("Target samples must be greater than zero.");
        }
        ArrayList arrayList = new ArrayList();
        MediaExtractor mediaExtractor = null;
        MediaFormat mediaFormat = null;
        try {
            MediaExtractor mediaExtractor2 = new MediaExtractor();
            try {
                mediaExtractor2.setDataSource(str);
                int i2 = 0;
                while (true) {
                    if (i2 >= mediaExtractor2.getTrackCount()) {
                        i2 = -1;
                        break;
                    }
                    mediaFormat = mediaExtractor2.getTrackFormat(i2);
                    String string = mediaFormat.getString("mime");
                    if (string != null && string.startsWith("audio/")) {
                        break;
                    }
                    i2++;
                }
                if (i2 == -1 || mediaFormat == null) {
                    throw new IOException("No audio track found in " + str);
                }
                mediaExtractor2.selectTrack(i2);
                if (mediaFormat.getLong("durationUs") <= 0) {
                    Log.w(TAG, "Duration not available or invalid, results might be inaccurate for downsampling.");
                }
                ByteBuffer allocate = ByteBuffer.allocate(16384);
                allocate.order(ByteOrder.nativeOrder());
                ArrayList arrayList2 = new ArrayList();
                while (true) {
                    int readSampleData = mediaExtractor2.readSampleData(allocate, 0);
                    if (readSampleData < 0) {
                        break;
                    }
                    if (readSampleData > 0) {
                        allocate.position(0);
                        allocate.limit(readSampleData);
                        ShortBuffer asShortBuffer = allocate.asShortBuffer();
                        while (asShortBuffer.hasRemaining()) {
                            arrayList2.add(Short.valueOf(asShortBuffer.get()));
                        }
                    }
                    allocate.clear();
                    mediaExtractor2.advance();
                }
                if (!arrayList2.isEmpty()) {
                    int size = arrayList2.size();
                    int max = Math.max(1, size / i);
                    for (int i3 = 0; i3 < i; i3++) {
                        int i4 = i3 * max;
                        int min = Math.min(i4 + max, size);
                        if (i4 >= size) {
                            arrayList.add(Float.valueOf(0.0f));
                        } else {
                            short s = 0;
                            while (i4 < min) {
                                short shortValue = ((Short) arrayList2.get(i4)).shortValue();
                                if (Math.abs((int) shortValue) > Math.abs((int) s)) {
                                    s = shortValue;
                                }
                                i4++;
                            }
                            arrayList.add(Float.valueOf(Math.abs((int) s) / 32767.0f));
                        }
                    }
                    mediaExtractor2.release();
                    return arrayList;
                }
                for (int i5 = 0; i5 < i; i5++) {
                    arrayList.add(Float.valueOf(0.0f));
                }
                mediaExtractor2.release();
                return arrayList;
            } catch (Throwable th) {
                if (mediaExtractor2 != null) {
                    mediaExtractor2.release();
                }
                throw th;
            }
        } catch (Throwable th2) {
            throw th2;
        }
    }
}

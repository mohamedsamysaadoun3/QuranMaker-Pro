package hazem.nurmontage.videoquran.Utils;

import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.view.Surface;
import java.io.IOException;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public class UltraFastWaveformOptimized {
    public static float[] extractAmplitudes(String str, int i) throws IOException {
        MediaExtractor mediaExtractor = new MediaExtractor();
        mediaExtractor.setDataSource(str);
        MediaFormat mediaFormat = null;
        int i2 = 0;
        while (true) {
            if (i2 >= mediaExtractor.getTrackCount()) {
                i2 = -1;
                break;
            }
            mediaFormat = mediaExtractor.getTrackFormat(i2);
            if (mediaFormat.getString("mime").startsWith("audio/")) {
                break;
            }
            i2++;
        }
        if (i2 == -1) {
            throw new IOException("No audio track found");
        }
        mediaExtractor.selectTrack(i2);
        MediaCodec createDecoderByType = MediaCodec.createDecoderByType(mediaFormat.getString("mime"));
        createDecoderByType.configure(mediaFormat, (Surface) null, (MediaCrypto) null, 0);
        createDecoderByType.start();
        MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
        float[] fArr = new float[i];
        mediaFormat.getLong("durationUs");
        int[] iArr = new int[i];
        boolean z = false;
        int i3 = 0;
        while (!z) {
            int dequeueInputBuffer = createDecoderByType.dequeueInputBuffer(1000L);
            if (dequeueInputBuffer >= 0) {
                int readSampleData = mediaExtractor.readSampleData(createDecoderByType.getInputBuffer(dequeueInputBuffer), 0);
                if (readSampleData < 0) {
                    createDecoderByType.queueInputBuffer(dequeueInputBuffer, 0, 0, 0L, 4);
                    z = true;
                } else {
                    createDecoderByType.queueInputBuffer(dequeueInputBuffer, 0, readSampleData, mediaExtractor.getSampleTime(), 0);
                    mediaExtractor.advance();
                }
            }
            while (true) {
                int dequeueOutputBuffer = createDecoderByType.dequeueOutputBuffer(bufferInfo, 1000L);
                if (dequeueOutputBuffer >= 0) {
                    ByteBuffer outputBuffer = createDecoderByType.getOutputBuffer(dequeueOutputBuffer);
                    outputBuffer.position(0);
                    int i4 = bufferInfo.size / 2;
                    float f = i4 / i;
                    for (int i5 = 0; i5 < i4; i5 += 2) {
                        short s = outputBuffer.getShort(i5);
                        int i6 = (int) (i3 / f);
                        if (i6 >= i) {
                            break;
                        }
                        fArr[i6] = Math.max(fArr[i6], Math.abs((int) s) / 32767.0f);
                        i3++;
                    }
                    createDecoderByType.releaseOutputBuffer(dequeueOutputBuffer, false);
                }
            }
        }
        createDecoderByType.stop();
        createDecoderByType.release();
        mediaExtractor.release();
        return fArr;
    }
}

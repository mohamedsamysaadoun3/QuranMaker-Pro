package hazem.nurmontage.videoquran.Utils;

import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.view.Surface;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public class FastWaveformExtractorPro {
    public static float[] extract(String str, int i) throws Exception {
        float[] fArr;
        long j;
        char c;
        MediaExtractor mediaExtractor = new MediaExtractor();
        mediaExtractor.setDataSource(str);
        int i2 = 0;
        while (true) {
            if (i2 >= mediaExtractor.getTrackCount()) {
                i2 = -1;
                break;
            }
            if (mediaExtractor.getTrackFormat(i2).getString("mime").startsWith("audio/")) {
                break;
            }
            i2++;
        }
        mediaExtractor.selectTrack(i2);
        MediaFormat trackFormat = mediaExtractor.getTrackFormat(i2);
        MediaCodec createDecoderByType = MediaCodec.createDecoderByType(trackFormat.getString("mime"));
        createDecoderByType.configure(trackFormat, (Surface) null, (MediaCrypto) null, 0);
        createDecoderByType.start();
        MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
        float[] fArr2 = new float[2000];
        boolean z = false;
        int i3 = 0;
        while (!z) {
            int dequeueInputBuffer = createDecoderByType.dequeueInputBuffer(0L);
            if (dequeueInputBuffer >= 0) {
                int readSampleData = mediaExtractor.readSampleData(createDecoderByType.getInputBuffer(dequeueInputBuffer), 0);
                if (readSampleData < 0) {
                    fArr = fArr2;
                    j = 0;
                    createDecoderByType.queueInputBuffer(dequeueInputBuffer, 0, 0, 0L, 4);
                    z = true;
                } else {
                    fArr = fArr2;
                    j = 0;
                    createDecoderByType.queueInputBuffer(dequeueInputBuffer, 0, readSampleData, mediaExtractor.getSampleTime(), 0);
                    mediaExtractor.advance();
                }
            } else {
                fArr = fArr2;
                j = 0;
            }
            while (true) {
                int dequeueOutputBuffer = createDecoderByType.dequeueOutputBuffer(bufferInfo, j);
                if (dequeueOutputBuffer < 0) {
                    c = 2000;
                    break;
                }
                ByteBuffer outputBuffer = createDecoderByType.getOutputBuffer(dequeueOutputBuffer);
                c = 2000;
                if (i3 < 2000) {
                    fArr[i3] = computeMaxAmp(outputBuffer, bufferInfo.size);
                    i3++;
                }
                createDecoderByType.releaseOutputBuffer(dequeueOutputBuffer, false);
                if ((bufferInfo.flags & 4) != 0) {
                    z = true;
                    break;
                }
            }
            fArr2 = fArr;
        }
        createDecoderByType.stop();
        createDecoderByType.release();
        mediaExtractor.release();
        return downsample(fArr2, i3, i);
    }

    private static float computeMaxAmp(ByteBuffer byteBuffer, int i) {
        byteBuffer.position(0);
        float f = 0.0f;
        for (int i2 = 0; i2 < i - 1; i2 += 2) {
            f = Math.max(f, Math.abs((int) byteBuffer.getShort(i2)));
        }
        return f / 32767.0f;
    }

    private static float[] downsample(float[] fArr, int i, int i2) {
        float[] fArr2 = new float[i2];
        float f = i / i2;
        int i3 = 0;
        while (i3 < i2) {
            int i4 = i3 + 1;
            int i5 = (int) (i4 * f);
            float f2 = 0.0f;
            for (int i6 = (int) (i3 * f); i6 < i5 && i6 < i; i6++) {
                f2 = Math.max(f2, fArr[i6]);
            }
            fArr2[i3] = f2;
            i3 = i4;
        }
        return fArr2;
    }
}

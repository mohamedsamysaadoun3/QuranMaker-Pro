package hazem.nurmontage.videoquran.Utils;

import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.view.Surface;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public class FastWaveformExtractorOptimized {
    public static float[] extract(String str, int i) throws Exception {
        int i2;
        long j;
        MediaExtractor mediaExtractor = new MediaExtractor();
        mediaExtractor.setDataSource(str);
        int i3 = 0;
        while (true) {
            if (i3 >= mediaExtractor.getTrackCount()) {
                i3 = -1;
                break;
            }
            if (mediaExtractor.getTrackFormat(i3).getString("mime").startsWith("audio/")) {
                break;
            }
            i3++;
        }
        mediaExtractor.selectTrack(i3);
        MediaFormat trackFormat = mediaExtractor.getTrackFormat(i3);
        MediaCodec createDecoderByType = MediaCodec.createDecoderByType(trackFormat.getString("mime"));
        createDecoderByType.configure(trackFormat, (Surface) null, (MediaCrypto) null, 0);
        createDecoderByType.start();
        float[] fArr = new float[i];
        long j2 = trackFormat.getLong("durationUs") / i;
        MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
        ByteBuffer[] inputBuffers = createDecoderByType.getInputBuffers();
        ByteBuffer[] outputBuffers = createDecoderByType.getOutputBuffers();
        long j3 = 0;
        int i4 = 0;
        long j4 = 0;
        while (i4 < i) {
            mediaExtractor.seekTo(j4, 2);
            long j5 = j4 + j2;
            int dequeueInputBuffer = createDecoderByType.dequeueInputBuffer(j3);
            if (dequeueInputBuffer >= 0) {
                int readSampleData = mediaExtractor.readSampleData(inputBuffers[dequeueInputBuffer], 0);
                if (readSampleData < 0) {
                    break;
                }
                i2 = i4;
                createDecoderByType.queueInputBuffer(dequeueInputBuffer, 0, readSampleData, mediaExtractor.getSampleTime(), 0);
                mediaExtractor.advance();
                j = 0;
            } else {
                i2 = i4;
                j = j3;
            }
            int dequeueOutputBuffer = createDecoderByType.dequeueOutputBuffer(bufferInfo, j);
            if (dequeueOutputBuffer >= 0) {
                i4 = i2 + 1;
                fArr[i2] = computeAmp(outputBuffers[dequeueOutputBuffer], bufferInfo.size);
                createDecoderByType.releaseOutputBuffer(dequeueOutputBuffer, false);
            } else {
                i4 = i2;
            }
            j3 = j;
            j4 = j5;
        }
        createDecoderByType.stop();
        createDecoderByType.release();
        mediaExtractor.release();
        return fArr;
    }

    private static float computeAmp(ByteBuffer byteBuffer, int i) {
        byteBuffer.position(0);
        float f = 0.0f;
        for (int i2 = 0; i2 < i - 1; i2 += 2) {
            f = Math.max(f, Math.abs((int) byteBuffer.getShort(i2)) / 32767.0f);
        }
        return f;
    }
}

package hazem.nurmontage.videoquran.Utils;

import android.content.Context;
import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.net.Uri;
import android.view.Surface;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Map;

/* loaded from: classes2.dex */
public class FastWaveform {
    public static float[] decodeWaveform(Context context, Uri uri, int i) {
        MediaExtractor mediaExtractor = null;
        int i2 = -1;
        MediaFormat mediaFormat = null;
        long j;
        float[] fArr = new float[i];
        try {
            mediaExtractor = new MediaExtractor();
            mediaExtractor.setDataSource(context, uri, (Map<String, String>) null);
            i2 = 0;
            while (true) {
                if (i2 >= mediaExtractor.getTrackCount()) {
                    i2 = -1;
                    mediaFormat = null;
                    break;
                }
                mediaFormat = mediaExtractor.getTrackFormat(i2);
                if (mediaFormat.getString("mime").startsWith("audio/")) {
                    break;
                }
                i2++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (i2 < 0 || mediaExtractor == null || mediaFormat == null) {
            return fArr;
        }
        try {
            mediaExtractor.selectTrack(i2);
            MediaCodec createDecoderByType = MediaCodec.createDecoderByType(mediaFormat.getString("mime"));
            createDecoderByType.configure(mediaFormat, (Surface) null, (MediaCrypto) null, 0);
        createDecoderByType.start();
        long j2 = ((mediaFormat.getLong("durationUs") / 1000000) * mediaFormat.getInteger("sample-rate")) / i;
        MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
        boolean z = false;
        int i3 = 0;
        float f = 0.0f;
        long j3 = 0;
        while (!z) {
            int dequeueInputBuffer = createDecoderByType.dequeueInputBuffer(0);
            if (dequeueInputBuffer >= 0) {
                int readSampleData = mediaExtractor.readSampleData(createDecoderByType.getInputBuffer(dequeueInputBuffer), 0);
                if (readSampleData < 0) {
                    createDecoderByType.queueInputBuffer(dequeueInputBuffer, 0, 0, 0L, 4);
                } else {
                    createDecoderByType.queueInputBuffer(dequeueInputBuffer, 0, readSampleData, mediaExtractor.getSampleTime(), 0);
                    mediaExtractor.advance();
                }
                j = 0;
            } else {
                j = 10000;
            }
            int dequeueOutputBuffer = createDecoderByType.dequeueOutputBuffer(bufferInfo, j);
            if (dequeueOutputBuffer >= 0) {
                ByteBuffer outputBuffer = createDecoderByType.getOutputBuffer(dequeueOutputBuffer);
                outputBuffer.order(ByteOrder.LITTLE_ENDIAN);
                while (true) {
                    if (outputBuffer.remaining() <= 1) {
                        break;
                    }
                    float abs = Math.abs((int) outputBuffer.getShort()) / 32768.0f;
                    if (abs > f) {
                        f = abs;
                    }
                    j3++;
                    if (j3 >= j2) {
                        fArr[i3] = f;
                        int i4 = i3 + 1;
                        if (i4 >= i) {
                            i3 = i4;
                            z = true;
                            break;
                        }
                        f = 0.0f;
                        i3 = i4;
                        j3 = 0;
                    }
                }
                createDecoderByType.releaseOutputBuffer(dequeueOutputBuffer, false);
                if ((bufferInfo.flags & 4) != 0) {
                    z = true;
                }
            }
        }
        createDecoderByType.stop();
        createDecoderByType.release();
        mediaExtractor.release();
        return fArr;
        } catch (Exception e) {
            e.printStackTrace();
            return fArr;
        }
    }
}

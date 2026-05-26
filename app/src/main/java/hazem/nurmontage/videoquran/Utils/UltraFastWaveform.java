package hazem.nurmontage.videoquran.Utils;

import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.view.Surface;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public class UltraFastWaveform {
    public static float[] extractAmplitudes(String str, int i) throws IOException {
        short[] pcmData = decodeToPCM(str);
        int length = pcmData.length;
        float[] fArr = new float[i];
        double d = length / i;
        int i2 = 0;
        while (i2 < i) {
            int i3 = i2 + 1;
            int min = Math.min((int) (i3 * d), length);
            float f = 0.0f;
            for (int i4 = (int) (i2 * d); i4 < min; i4++) {
                f = Math.max(f, Math.abs((int) pcmData[i4]) / 32767.0f);
            }
            fArr[i2] = f;
            i2 = i3;
        }
        return fArr;
    }

    private static short[] decodeToPCM(String str) throws IOException {
        MediaExtractor mediaExtractor = new MediaExtractor();
        mediaExtractor.setDataSource(str);
        MediaFormat mediaFormat = null;
        int i = 0;
        while (true) {
            if (i >= mediaExtractor.getTrackCount()) {
                i = -1;
                break;
            }
            mediaFormat = mediaExtractor.getTrackFormat(i);
            if (mediaFormat.getString("mime").startsWith("audio/")) {
                break;
            }
            i++;
        }
        if (i == -1) {
            throw new IOException("No audio track found");
        }
        mediaExtractor.selectTrack(i);
        MediaCodec createDecoderByType = MediaCodec.createDecoderByType(mediaFormat.getString("mime"));
        createDecoderByType.configure(mediaFormat, (Surface) null, (MediaCrypto) null, 0);
        createDecoderByType.start();
        ArrayList arrayList = new ArrayList();
        MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
        boolean z = false;
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
                    while (outputBuffer.remaining() > 1) {
                        arrayList.add(Short.valueOf(outputBuffer.getShort()));
                    }
                    createDecoderByType.releaseOutputBuffer(dequeueOutputBuffer, false);
                }
            }
        }
        createDecoderByType.stop();
        createDecoderByType.release();
        mediaExtractor.release();
        short[] sArr = new short[arrayList.size()];
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            sArr[i2] = ((Short) arrayList.get(i2)).shortValue();
        }
        return sArr;
    }
}

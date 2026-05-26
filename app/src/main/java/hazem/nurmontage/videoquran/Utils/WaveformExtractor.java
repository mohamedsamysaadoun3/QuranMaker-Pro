package hazem.nurmontage.videoquran.Utils;

import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.view.Surface;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;

/* loaded from: classes2.dex */
public class WaveformExtractor {
    public static float[] extractAmplitudes(String str, int i) {
        ByteBuffer[] byteBufferArr;
        float[] fArr;
        long j;
        MediaCodec.BufferInfo bufferInfo;
        int i2;
        int dequeueInputBuffer;
        MediaExtractor mediaExtractor = new MediaExtractor();
        try {
            mediaExtractor.setDataSource(str);
            int selectAudioTrack = selectAudioTrack(mediaExtractor);
            if (selectAudioTrack < 0) {
                return new float[i];
            }
            mediaExtractor.selectTrack(selectAudioTrack);
            MediaFormat trackFormat = mediaExtractor.getTrackFormat(selectAudioTrack);
            trackFormat.getInteger("sample-rate");
            MediaCodec createDecoderByType = MediaCodec.createDecoderByType(trackFormat.getString("mime"));
            int i3 = 0;
            createDecoderByType.configure(trackFormat, (Surface) null, (MediaCrypto) null, 0);
            createDecoderByType.start();
            ByteBuffer[] inputBuffers = createDecoderByType.getInputBuffers();
            ByteBuffer[] outputBuffers = createDecoderByType.getOutputBuffers();
            float[] fArr2 = new float[i];
            float[] fArr3 = new float[i];
            float f = (trackFormat.getLong("durationUs") / 1000000.0f) / i;
            MediaCodec.BufferInfo bufferInfo2 = new MediaCodec.BufferInfo();
            boolean z = false;
            while (true) {
                if (z || (dequeueInputBuffer = createDecoderByType.dequeueInputBuffer(0)) < 0) {
                    byteBufferArr = inputBuffers;
                    fArr = fArr3;
                    j = 10000;
                    bufferInfo = bufferInfo2;
                } else {
                    int readSampleData = mediaExtractor.readSampleData(inputBuffers[dequeueInputBuffer], i3);
                    if (readSampleData < 0) {
                        byteBufferArr = inputBuffers;
                        j = 10000;
                        fArr = fArr3;
                        bufferInfo = bufferInfo2;
                        createDecoderByType.queueInputBuffer(dequeueInputBuffer, 0, 0, 0L, 4);
                        z = true;
                    } else {
                        byteBufferArr = inputBuffers;
                        fArr = fArr3;
                        j = 10000;
                        bufferInfo = bufferInfo2;
                        createDecoderByType.queueInputBuffer(dequeueInputBuffer, 0, readSampleData, mediaExtractor.getSampleTime(), 0);
                        mediaExtractor.advance();
                    }
                }
                int dequeueOutputBuffer = createDecoderByType.dequeueOutputBuffer(bufferInfo, j);
                if (dequeueOutputBuffer >= 0) {
                    ByteBuffer byteBuffer = outputBuffers[dequeueOutputBuffer];
                    byteBuffer.position(bufferInfo.offset);
                    byteBuffer.limit(bufferInfo.offset + bufferInfo.size);
                    ShortBuffer shortBuffer = byteBuffer.asShortBuffer();
                    int remaining = shortBuffer.remaining();
                    for (int i4 = 0; i4 < remaining; i4++) {
                        float abs = Math.abs((int) shortBuffer.get(i4)) / 32768.0f;
                        int i5 = (int) ((bufferInfo.presentationTimeUs / 1000000.0f) / f);
                        if (i5 < i) {
                            fArr2[i5] = fArr2[i5] + abs;
                            fArr[i5] = fArr[i5] + 1.0f;
                        }
                    }
                    i2 = 0;
                    createDecoderByType.releaseOutputBuffer(dequeueOutputBuffer, false);
                    if ((bufferInfo.flags & 4) != 0) {
                        break;
                    }
                } else {
                    i2 = 0;
                }
                i3 = i2;
                bufferInfo2 = bufferInfo;
                fArr3 = fArr;
                inputBuffers = byteBufferArr;
            }
            for (int i6 = 0; i6 < i; i6++) {
                float f2 = fArr[i6];
                if (f2 > 0.0f) {
                    fArr2[i6] = fArr2[i6] / f2;
                }
            }
            createDecoderByType.stop();
            createDecoderByType.release();
            mediaExtractor.release();
            return fArr2;
        } catch (Exception e) {
            e.printStackTrace();
            return new float[i];
        }
    }

    private static int selectAudioTrack(MediaExtractor mediaExtractor) {
        for (int i = 0; i < mediaExtractor.getTrackCount(); i++) {
            if (mediaExtractor.getTrackFormat(i).getString("mime").startsWith("audio/")) {
                return i;
            }
        }
        return -1;
    }
}

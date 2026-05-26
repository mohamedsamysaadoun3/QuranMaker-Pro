package hazem.nurmontage.videoquran.Utils;

import android.util.Log;
import com.arthenica.ffmpegkit.FFmpegKit;
import com.arthenica.ffmpegkit.FFmpegSession;
import com.arthenica.ffmpegkit.FFmpegSessionCompleteCallback;
import com.arthenica.ffmpegkit.ReturnCode;
import hazem.nurmontage.videoquran.Utils.FfmpegCodecChecker;

/* loaded from: classes2.dex */
public class FfmpegCodecChecker {
    private static CodecInfo cachedCodecs;

    public interface CodecCallback {
        void onResult(CodecInfo codecInfo);
    }

    public static class CodecInfo {
        public String audioCodec;
        public boolean isVideoHwAccelerated;
        public String videoCodec;
    }

    public static void detectCodecsAsync(final CodecCallback codecCallback) {
        CodecInfo codecInfo = cachedCodecs;
        if (codecInfo != null) {
            codecCallback.onResult(codecInfo);
        } else {
            FFmpegKit.executeAsync("-hide_banner -encoders", new FFmpegSessionCompleteCallback() { // from class: hazem.nurmontage.videoquran.Utils.FfmpegCodecChecker$$ExternalSyntheticLambda0
                @Override // com.arthenica.ffmpegkit.FFmpegSessionCompleteCallback
                public final void apply(FFmpegSession fFmpegSession) {
                    FfmpegCodecChecker.lambda$detectCodecsAsync$0(codecCallback, fFmpegSession);
                }
            });
        }
    }

    static /* synthetic */ void lambda$detectCodecsAsync$0(CodecCallback codecCallback, FFmpegSession fFmpegSession) {
        CodecInfo parseEncoders = parseEncoders(fFmpegSession);
        cachedCodecs = parseEncoders;
        codecCallback.onResult(parseEncoders);
    }

    private static CodecInfo parseEncoders(FFmpegSession fFmpegSession) {
        CodecInfo codecInfo = new CodecInfo();
        if (!ReturnCode.isSuccess(fFmpegSession.getReturnCode())) {
            Log.e("CodecCheck", "Failed to query FFmpeg encoders");
            return codecInfo;
        }
        String output = fFmpegSession.getOutput();
        if (output == null) {
            return codecInfo;
        }
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        for (String str : output.split("\n")) {
            String lowerCase = str.trim().toLowerCase();
            if (!z && lowerCase.contains("libx264")) {
                z = true;
            }
            if (!z2 && lowerCase.contains("libfdk_aac")) {
                z2 = true;
            }
            if (!z3 && lowerCase.contains("aac")) {
                z3 = true;
            }
        }
        if (z) {
            codecInfo.videoCodec = "libx264";
            codecInfo.isVideoHwAccelerated = false;
        } else {
            codecInfo.videoCodec = null;
            codecInfo.isVideoHwAccelerated = false;
        }
        if (z2) {
            codecInfo.audioCodec = "libfdk_aac";
        } else if (z3) {
            codecInfo.audioCodec = "aac";
        } else {
            codecInfo.audioCodec = null;
        }
        return codecInfo;
    }
}

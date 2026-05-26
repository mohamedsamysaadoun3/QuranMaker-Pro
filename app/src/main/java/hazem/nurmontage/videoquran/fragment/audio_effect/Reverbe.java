package hazem.nurmontage.videoquran.fragment.audio_effect;

/* loaded from: classes2.dex */
public class Reverbe {
    private final String cmd_ffmpeg;
    private final String name;

    public Reverbe(String str, String str2) {
        this.cmd_ffmpeg = str2;
        this.name = str;
    }

    public String getName() {
        return this.name;
    }

    public String getCmd_ffmpeg() {
        return this.cmd_ffmpeg;
    }
}

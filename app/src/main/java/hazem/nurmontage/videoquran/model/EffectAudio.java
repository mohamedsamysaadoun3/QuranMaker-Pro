package hazem.nurmontage.videoquran.model;

import java.io.Serializable;

/* loaded from: classes2.dex */
public class EffectAudio implements Serializable {
    private int decays;
    private String decays_cmd;
    private int delays;
    private String delays_cmd;
    private int duration;
    private float end;
    private int fade_in;
    private int fade_out;
    private boolean isEnhance;
    private boolean isRemoveNoice;
    private float outGain;
    private String reverbPreset;
    private int reverbPreset_index_list;
    private float start;
    private int volume_echo;
    private float volume = 1.0f;
    private float speed = 1.0f;

    public boolean isEnhance() {
        return this.isEnhance;
    }

    public void setEnhance(boolean z) {
        this.isEnhance = z;
    }

    public boolean isRemoveNoice() {
        return this.isRemoveNoice;
    }

    public float getStart() {
        return this.start;
    }

    public float getEnd() {
        return this.end;
    }

    public void setDuration(int i) {
        this.duration = i;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setStart(float f) {
        this.start = f;
    }

    public void setEnd(float f) {
        this.end = f;
    }

    public void setReverbPreset_index_list(int i) {
        this.reverbPreset_index_list = i;
    }

    public int getReverbPreset_index_list() {
        return this.reverbPreset_index_list;
    }

    public void setRemoveNoice(boolean z) {
        this.isRemoveNoice = z;
    }

    public int getVolume_echo() {
        return this.volume_echo;
    }

    public void setVolume_echo(int i) {
        this.volume_echo = i;
    }

    public String getDecays_cmd() {
        return this.decays_cmd;
    }

    public String getDelays_cmd() {
        return this.delays_cmd;
    }

    public void setDecays(int i) {
        this.decays = i;
    }

    public void setDelays(int i) {
        this.delays = i;
    }

    public void setDecays_cmd(String str) {
        this.decays_cmd = str;
    }

    public void setDelays_cmd(String str) {
        this.delays_cmd = str;
    }

    public void setOutGain(float f) {
        this.outGain = f;
    }

    public int getDecays() {
        return this.decays;
    }

    public int getDelays() {
        return this.delays;
    }

    public float getOutGain() {
        return this.outGain;
    }

    public String getReverbPreset() {
        return this.reverbPreset;
    }

    public void setReverbPreset(String str) {
        this.reverbPreset = str;
    }

    public int getFade_in() {
        return this.fade_in;
    }

    public int getFade_out() {
        return this.fade_out;
    }

    public float getSpeed() {
        return this.speed;
    }

    public float getVolume() {
        return this.volume;
    }

    public void setFade_in(int i) {
        this.fade_in = i;
    }

    public void setSpeed(float f) {
        this.speed = f;
    }

    public void setFade_out(int i) {
        this.fade_out = i;
    }

    public void setVolume(float f) {
        this.volume = f;
    }
}

package hazem.nurmontage.videoquran.model;

import java.io.Serializable;
import java.util.List;

/* loaded from: classes2.dex */
public class EntityMedia implements Serializable {
    private float duration_fade_in;
    private float duration_fade_out;
    private EffectAudio effectAudio;
    private float end;
    private float h;
    private int id_raw;
    protected int index_end_thumbnail;
    protected int index_start_thumbnail;
    private boolean isApplyEffectInPreview;
    private boolean isSoundEnable;
    private float mScale;
    private float max;
    private String name;
    private float offset;
    private float offset_left;
    private float offset_right;
    private String path_ffmpeg;
    private String path_ffmpeg_effect;
    private List<String> paths_https;
    private float posX;
    private float posXFFmpeg;
    private float posY;
    private float start;
    private int start_original;
    private int time;
    private float topX;
    private float topY;
    private String uri;
    private String video_path;
    private float volume;
    private float w;
    private float x;
    private float y;

    public boolean isApplyEffectInPreview() {
        return false;
    }

    public void setEffectAudio(EffectAudio effectAudio) {
        this.effectAudio = effectAudio;
    }

    public EffectAudio getEffectAudio() {
        return this.effectAudio;
    }

    public List<String> getPaths_https() {
        return this.paths_https;
    }

    public void setPaths_https(List<String> list) {
        this.paths_https = list;
    }

    public String getPath_ffmpeg_effect() {
        return this.path_ffmpeg_effect;
    }

    public void setPath_ffmpeg_effect(String str) {
        this.path_ffmpeg_effect = str;
    }

    public void setVideo_path(String str) {
        this.video_path = str;
    }

    public String getVideo_path() {
        return this.video_path;
    }

    public String getPath_ffmpeg() {
        return this.path_ffmpeg;
    }

    public void setPath_ffmpeg(String str) {
        this.path_ffmpeg = str;
    }

    public void setApplyEffectInPreview(boolean z) {
        this.isApplyEffectInPreview = z;
    }

    public float getPosXFFmpeg() {
        return this.posXFFmpeg;
    }

    public float getVolume() {
        return this.volume;
    }

    public void setVolume(float f) {
        this.volume = f;
    }

    public int getIndex_end_thumbnail() {
        return this.index_end_thumbnail;
    }

    public int getIndex_start_thumbnail() {
        return this.index_start_thumbnail;
    }

    public float getDuration_fade_in() {
        return this.duration_fade_in;
    }

    public float getDuration_fade_out() {
        return this.duration_fade_out;
    }

    public void setId_raw(int i) {
        this.id_raw = i;
    }

    public int getId_raw() {
        return this.id_raw;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getName() {
        return this.name;
    }

    public float getOffset_left() {
        return this.offset_left;
    }

    public void setOffset_left(float f) {
        this.offset_left = f;
    }

    public float getMax() {
        return this.max;
    }

    public float getOffset() {
        return this.offset;
    }

    public void setOffset(float f) {
        this.offset = f;
    }

    public void setStart_original(int i) {
        this.start_original = i;
    }

    public int getStart_original() {
        return this.start_original;
    }

    public float getPosX() {
        return this.posX;
    }

    public float getPosY() {
        return this.posY;
    }

    public boolean isSoundEnable() {
        return this.isSoundEnable;
    }

    public void setSoundEnable(boolean z) {
        this.isSoundEnable = z;
    }

    public void setScale(float f) {
        this.mScale = f;
    }

    public float getScale() {
        return this.mScale;
    }

    public float getH() {
        return this.h;
    }

    public EntityMedia(String str, int i, float f, float f2, float f3, float f4, int i2, float f5, float f6, float f7, float f8, float f9, float f10, float f11) {
        this.volume = 1.0f;
        this.isSoundEnable = true;
        this.x = 0.0f;
        this.posXFFmpeg = f11;
        this.y = 0.0f;
        this.w = 1.0f;
        this.mScale = 1.0f;
        this.uri = str;
        this.offset_left = f7;
        this.offset_right = f6;
        this.max = f8;
        this.offset = f5;
        this.start_original = i;
        this.start = f;
        this.end = f2;
        this.posX = f3;
        this.posY = f4;
        this.duration_fade_in = f9;
        this.duration_fade_out = f10;
        this.time = i2;
    }

    public EntityMedia(String str) {
        this.volume = 1.0f;
        this.isSoundEnable = true;
        this.x = 0.0f;
        this.y = 0.0f;
        this.w = 1.0f;
        this.mScale = 1.0f;
        this.uri = str;
    }

    public EntityMedia(String str, float f, float f2, float f3, float f4, float f5, float f6) {
        this.volume = 1.0f;
        this.isSoundEnable = true;
        this.x = 0.0f;
        this.y = 0.0f;
        this.w = 1.0f;
        this.mScale = 1.0f;
        this.uri = str;
        this.start = f;
        this.end = f2;
        this.posX = f3;
        this.posY = f4;
        this.duration_fade_in = f5;
        this.duration_fade_out = f6;
    }

    public void setUri(String str) {
        this.uri = str;
    }

    public float getOffset_right() {
        return this.offset_right;
    }

    public EntityMedia(String str, int i, float f, float f2, int i2, float f3, float f4, float f5, float f6, float f7, boolean z, float f8, float f9, float f10, float f11) {
        this.volume = 1.0f;
        this.mScale = 1.0f;
        this.uri = str;
        this.start = f;
        this.offset = f7;
        this.duration_fade_in = f9;
        this.duration_fade_out = f10;
        this.max = f8;
        this.end = f2;
        this.posXFFmpeg = f11;
        this.time = i2;
        this.start_original = i;
        this.x = f3;
        this.h = f6;
        this.y = f4;
        this.w = f5;
        this.isSoundEnable = z;
    }

    public float getTopX() {
        return this.topX;
    }

    public float getTopY() {
        return this.topY;
    }

    public float getW() {
        return this.w;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public int getTime() {
        return this.time;
    }

    public void setTime(int i) {
        this.time = i;
    }

    public String getUri() {
        return this.uri;
    }

    public float getStart() {
        return this.start;
    }

    public void setStart(float f) {
        this.start = f;
    }

    public void setDuration_fade_in(float f) {
        this.duration_fade_in = f;
    }

    public void setDuration_fade_out(float f) {
        this.duration_fade_out = f;
    }

    public float getEnd() {
        return this.end;
    }

    public void setEnd(float f) {
        this.end = f;
    }

    public EntityMedia duplicate() {
        return new EntityMedia(this.uri, this.start_original, this.start, this.end, this.time, this.x, this.y, this.w, this.h, this.offset, this.isSoundEnable, this.max, this.duration_fade_in, this.duration_fade_out, this.posXFFmpeg);
    }
}

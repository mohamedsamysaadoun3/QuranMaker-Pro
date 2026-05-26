package hazem.nurmontage.videoquran.entity_timeline;

import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.net.Uri;
import hazem.nurmontage.videoquran.Utils.WaveformBitmapRenderer;
import hazem.nurmontage.videoquran.common.Common;
import hazem.nurmontage.videoquran.model.EffectAudio;
import hazem.nurmontage.videoquran.views.TrackEntityView;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class EntityAudio extends Entity {
    private float[] amps;
    private float downX;
    private int duration;
    private EffectAudio effectAudio;
    private float h;
    private TrackEntityView.ITrimLineCallback iTrimLineCallback;
    private boolean isApplyEffectInPreview;
    private boolean isPlay;
    private boolean isStartFadeIn;
    private boolean isStartFadeOut;
    private float lastLeft;
    private float lastRight;
    private MediaPlayer mediaPlayer;
    private int min_duration;
    private ObjectAnimator objectAnimator;
    private Paint paintLine;
    private Paint paintPath;
    private Path path;
    private String path_ffmpeg;
    private String path_ffmpeg_effect;
    private List<String> paths_http;
    WaveformBitmapRenderer renderer;
    private float scaleEffect;
    private float second_in_screen;
    private float tmpOffset;
    private Uri uri;
    private String video_path;
    public byte[] waveformValues;

    public void setScaleEffect(float f) {
        this.scaleEffect = f;
    }

    public float getScaleEffect() {
        return this.scaleEffect;
    }

    public void addPathHttp(List<String> list) {
        if (list == null) {
            return;
        }
        if (this.paths_http == null) {
            this.paths_http = new ArrayList();
        }
        this.paths_http.addAll(list);
    }

    public void setPathHttp(List<String> list) {
        this.paths_http = list;
    }

    public List<String> getPaths_http() {
        return this.paths_http;
    }

    public void setApplyEffectInPreview(boolean z) {
        this.isApplyEffectInPreview = z;
    }

    public boolean isApplyEffectInPreview() {
        return this.isApplyEffectInPreview;
    }

    public String getPath_ffmpeg_effect() {
        return this.path_ffmpeg_effect;
    }

    public void setPath_ffmpeg_effect(String str) {
        this.path_ffmpeg_effect = str;
    }

    public EffectAudio getEffectAudio() {
        return this.effectAudio;
    }

    public void updateEffect() {
        this.effectAudio.setStart(getStart());
        this.effectAudio.setEnd(getEnd());
        this.effectAudio.setDuration((int) (getEnd() - getStart()));
    }

    public void setEffectAudio(EffectAudio effectAudio) {
        if (effectAudio == null) {
            return;
        }
        this.effectAudio.setReverbPreset(effectAudio.getReverbPreset());
        this.effectAudio.setSpeed(effectAudio.getSpeed());
        this.effectAudio.setVolume(effectAudio.getVolume());
        this.effectAudio.setFade_in(effectAudio.getFade_in());
        this.effectAudio.setFade_out(effectAudio.getFade_out());
        this.effectAudio.setDecays(effectAudio.getDecays());
        this.effectAudio.setRemoveNoice(effectAudio.isRemoveNoice());
        this.effectAudio.setDelays_cmd(effectAudio.getDelays_cmd());
        this.effectAudio.setDelays(effectAudio.getDelays());
        this.effectAudio.setDecays_cmd(effectAudio.getDecays_cmd());
        this.effectAudio.setOutGain(effectAudio.getOutGain());
        this.effectAudio.setVolume_echo(effectAudio.getVolume_echo());
        this.effectAudio.setEnhance(effectAudio.isEnhance());
        this.effectAudio.setReverbPreset_index_list(effectAudio.getReverbPreset_index_list());
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public MediaPlayer getMediaPlayer() {
        return this.mediaPlayer;
    }

    public void setPath_ffmpeg(String str) {
        this.path_ffmpeg = str;
        setPath_ffmpeg_effect(str);
    }

    public String getPath_ffmpeg() {
        return this.path_ffmpeg;
    }

    @Override // hazem.nurmontage.videoquran.entity_timeline.Entity
    public void setSecond_in_screen(float f) {
        this.second_in_screen = f;
    }

    public void setVideo_path(String str) {
        this.video_path = str;
    }

    public String getVideo_path() {
        return this.video_path;
    }

    public int getMin_duration() {
        return this.min_duration;
    }

    public void setMin_duration(int i) {
        this.min_duration = i;
    }

    @Override // hazem.nurmontage.videoquran.entity_timeline.Entity
    public float getSecond_in_screen() {
        return this.second_in_screen * getmScaleFactor();
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int i) {
        this.duration = i;
    }

    public float getStart() {
        return this.start;
    }

    public void setStart(float f) {
        this.start = f;
    }

    public float getEnd() {
        return this.end;
    }

    public void setEnd(float f) {
        this.end = f;
    }

    public void setMax(float f) {
        this.max = f;
    }

    public float getMax() {
        return this.max;
    }

    @Override // hazem.nurmontage.videoquran.entity_timeline.Entity
    public void setDownX(float f) {
        this.downX = f;
    }

    @Override // hazem.nurmontage.videoquran.entity_timeline.Entity
    public float getH() {
        return this.h;
    }

    @Override // hazem.nurmontage.videoquran.entity_timeline.Entity
    public float getLeft() {
        return this.left;
    }

    @Override // hazem.nurmontage.videoquran.entity_timeline.Entity
    public void setLastLeft(float f) {
        this.lastLeft = f;
    }

    @Override // hazem.nurmontage.videoquran.entity_timeline.Entity
    public void setLastRight(float f) {
        this.lastRight = f;
    }

    @Override // hazem.nurmontage.videoquran.entity_timeline.Entity
    public void setX(float f) {
        if (f < 0.0f) {
            f = 0.0f;
        }
        this.left = f;
        this.rect.left = f;
    }

    public Uri getUri() {
        return this.uri;
    }

    @Override // hazem.nurmontage.videoquran.entity_timeline.Entity
    public float getRight() {
        return this.right;
    }

    @Override // hazem.nurmontage.videoquran.entity_timeline.Entity
    public void setRight(float f) {
        this.rect.right = f;
        this.right = f;
    }

    @Override // hazem.nurmontage.videoquran.entity_timeline.Entity
    public void onUpRight() {
        float round = (Math.round(getRect().right / getSecond_in_screen()) * 1000) - getOnTapTime();
        setOffset_right((((getRect().left / getmScaleFactor()) - getOffset_left()) + getMax()) - (getRect().right / getmScaleFactor()));
        this.end += round;
        float f = this.end;
        int i = this.duration;
        if (f > i) {
            this.end = i;
        }
        this.right = this.lastRight;
    }

    @Override // hazem.nurmontage.videoquran.entity_timeline.Entity
    public void updateStartTrim() {
        this.tmpOffset = Math.abs(getRect().left / getmScaleFactor()) - Math.abs(getOnDown() / getmScaleFactor());
    }

    @Override // hazem.nurmontage.videoquran.entity_timeline.Entity
    public void onUpLeft() {
        this.start = Math.round((Math.abs(Math.round((getRect().left / getSecond_in_screen()) * 1000.0f)) - getOnTapTime()) + getStart());
        setOffset_left(getOffset_left() + this.tmpOffset);
        this.tmpOffset = 0.0f;
        float f = this.start;
        float f2 = this.min_duration;
        if (f < f2) {
            this.start = f2;
        }
        this.left = this.lastLeft;
    }

    @Override // hazem.nurmontage.videoquran.entity_timeline.Entity
    public RectF getRect() {
        return this.rect;
    }

    public boolean isPlay() {
        return this.isPlay;
    }

    public void setPlay(boolean z) {
        this.isPlay = z;
    }

    public boolean isVisible() {
        return this.isVisible;
    }

    @Override // hazem.nurmontage.videoquran.entity_timeline.Entity
    public void setVisible(boolean z) {
        this.isVisible = z;
    }

    public EntityAudio split(float f) {
        EntityAudio entityAudio = new EntityAudio(null, this.uri, f, getRect().top, this.h, getRect().right, ((getRect().right / getmScaleFactor()) + getOffset_right()) - (f / getmScaleFactor()), getSecond_in_screen(), (int) (getDuration() / 1000.0f), 0.0f, 0.0f, 0.0f);
        entityAudio.setFade_out(getFade_out());
        entityAudio.setFade_in(getFade_in());
        entityAudio.getRect().bottom = getRect().bottom;
        return entityAudio;
    }

    public EntityAudio(Bitmap bitmap, Uri uri, float f, float f2, float f3, float f4, float f5, float f6, int i, float f7, float f8, float f9) {
        super(f3);
        this.effectAudio = new EffectAudio();
        setOffset_right(f8);
        setOffset(f7);
        setOffset_left(f9);
        this.duration = i * 1000;
        this.end = i;
        this.second_in_screen = f6;
        setVisible(true);
        this.uri = uri;
        this.max = f5;
        this.h = f3;
        this.rect = new RectF(f, f2, f4, f3);
        this.left = this.rect.left;
        this.right = this.rect.right;
        this.color = Common.COLOR_BLOCK_AUDIO;
        Paint paint = new Paint(1);
        this.paintLine = paint;
        paint.setColor(-2434342);
        this.paintLine.setStyle(Paint.Style.STROKE);
        this.paintLine.setStrokeWidth(0.01f * f3);
        Paint paint2 = new Paint(1);
        this.paintPath = paint2;
        paint2.setColor(-1236326096);
        this.paintPath.setStyle(Paint.Style.FILL);
        this.path = new Path();
        this.rectFLeft = new RectF(0.0f, 0.0f, 0.46f * f3, f3);
        this.rectFRight = new RectF(0.0f, 0.0f, this.rectFLeft.width(), f3);
        this.round = this.rectFRight.width() * 0.5f;
        this.padding = f3 * 0.07f;
    }

    public EntityAudio(Bitmap bitmap, Uri uri, float f, float f2, float f3, float f4, float f5, float f6, int i) {
        super(f3);
        this.effectAudio = new EffectAudio();
        setOffset_right(0.0f);
        setOffset(0.0f);
        this.duration = i * 1000;
        this.end = i;
        this.second_in_screen = f6;
        setVisible(true);
        this.uri = uri;
        this.max = f5;
        this.h = f3;
        this.rect = new RectF(f, f2, f4, f3);
        this.left = this.rect.left;
        this.right = this.rect.right;
        this.color = Common.COLOR_BLOCK_AUDIO;
        Paint paint = new Paint(1);
        this.paintLine = paint;
        paint.setColor(-2434342);
        this.paintLine.setStyle(Paint.Style.STROKE);
        this.paintLine.setStrokeWidth(0.01f * f3);
        Paint paint2 = new Paint(1);
        this.paintPath = paint2;
        paint2.setColor(-1236326096);
        this.paintPath.setStyle(Paint.Style.FILL);
        this.path = new Path();
        this.rectFLeft = new RectF(0.0f, 0.0f, 0.46f * f3, f3);
        this.rectFRight = new RectF(0.0f, 0.0f, this.rectFLeft.width(), f3);
        this.round = this.rectFRight.width() * 0.5f;
        this.padding = f3 * 0.07f;
    }

    @Override // hazem.nurmontage.videoquran.entity_timeline.Entity
    public void setY(float f) {
        this.rect.top = f;
        this.rect.bottom = this.h + this.rect.top;
    }

    public float[] getAmps() {
        return this.amps;
    }

    public WaveformBitmapRenderer getRenderer() {
        return this.renderer;
    }

    public void setRenderer(WaveformBitmapRenderer waveformBitmapRenderer) {
        this.renderer = waveformBitmapRenderer;
    }

    public void setAmps(float[] fArr) {
        this.amps = fArr;
    }

    public void setAmps(float[] fArr, int i, int i2) {
        this.amps = fArr;
        this.renderer = new WaveformBitmapRenderer(fArr, i, i2, Common.COLOR_WAVE_INT);
    }

    private void drawWave(Canvas canvas, RectF rectF) {
        if (this.amps == null || this.renderer == null) {
            return;
        }
        float offset = getOffset() + getOffset_left() + this.tmpOffset;
        this.renderer.draw(canvas, rectF, getmScaleFactor() + getScaleEffect(), offset);
    }

    @Override // hazem.nurmontage.videoquran.entity_timeline.Entity
    public void draw(Canvas canvas, int i, int i2) {
        try {
            drawWave(canvas, this.rect);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // hazem.nurmontage.videoquran.entity_timeline.Entity
    public void draw(Canvas canvas) {
        try {
            drawWave(canvas, this.rect);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // hazem.nurmontage.videoquran.entity_timeline.Entity
    public void setSelect(boolean z) {
        this.isSelect = z;
    }

    @Override // hazem.nurmontage.videoquran.entity_timeline.Entity
    public boolean onTouch(PointF pointF) {
        this.selectTrim = null;
        this.downX = pointF.x;
        this.trim_type = -1;
        if (this.rectFLeft.contains(pointF.x, pointF.y)) {
            this.selectTrim = this.rectFLeft;
            this.trim_type = 0;
            this.isSelect = true;
        } else if (this.rectFRight.contains(pointF.x, pointF.y)) {
            this.selectTrim = this.rectFRight;
            this.trim_type = 1;
            this.isSelect = true;
        }
        return true;
    }

    @Override // hazem.nurmontage.videoquran.entity_timeline.Entity
    public int getTrim_type() {
        return this.trim_type;
    }

    @Override // hazem.nurmontage.videoquran.entity_timeline.Entity
    public RectF getSelectTrim() {
        return this.selectTrim;
    }

    @Override // hazem.nurmontage.videoquran.entity_timeline.Entity
    public float getDownX() {
        return this.downX;
    }

    @Override // hazem.nurmontage.videoquran.entity_timeline.Entity
    public boolean contains(PointF pointF) {
        if (this.isSelect) {
            onTouch(pointF);
        }
        this.isSelect = this.rect.contains(pointF.x, pointF.y);
        return this.isSelect;
    }

    public boolean isStartFadeIn() {
        return this.isStartFadeIn;
    }

    public boolean isStartFadeOut() {
        return this.isStartFadeOut;
    }

    public void setStartFadeIn(boolean z) {
        this.isStartFadeIn = z;
    }

    public void setStartFadeOut(boolean z) {
        this.isStartFadeOut = z;
    }

    public void setiTrimLineCallback(TrackEntityView.ITrimLineCallback iTrimLineCallback) {
        this.iTrimLineCallback = iTrimLineCallback;
    }

    public void setFadeInDelta(float f) {
        TrackEntityView.ITrimLineCallback iTrimLineCallback = this.iTrimLineCallback;
        if (iTrimLineCallback != null) {
            iTrimLineCallback.fadeInAudio(f);
        }
    }

    public void startFadeIn() {
        ObjectAnimator objectAnimator = this.objectAnimator;
        if (objectAnimator != null) {
            objectAnimator.end();
        }
        float fade_in = getFade_in() * 1000.0f;
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, "FadeInDelta", 0.0f, 1.0f);
        this.objectAnimator = ofFloat;
        ofFloat.setDuration((int) fade_in);
        this.objectAnimator.start();
    }

    public void setFadeOutDelta(float f) {
        TrackEntityView.ITrimLineCallback iTrimLineCallback = this.iTrimLineCallback;
        if (iTrimLineCallback != null) {
            iTrimLineCallback.fadeOutAudio(f);
        }
    }

    public void startFadeOut() {
        ObjectAnimator objectAnimator = this.objectAnimator;
        if (objectAnimator != null) {
            objectAnimator.end();
        }
        float fade_out = getFade_out() * 1000.0f;
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, "FadeOutDelta", 1.0f, 0.0f);
        this.objectAnimator = ofFloat;
        ofFloat.setDuration((long) fade_out);
        this.objectAnimator.start();
    }

    @Override // hazem.nurmontage.videoquran.entity_timeline.Entity
    public void release() {
        super.release();
        try {
            MediaPlayer mediaPlayer = this.mediaPlayer;
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    this.mediaPlayer.pause();
                }
                this.mediaPlayer.release();
                this.mediaPlayer = null;
            }
            WaveformBitmapRenderer waveformBitmapRenderer = this.renderer;
            if (waveformBitmapRenderer != null) {
                waveformBitmapRenderer.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

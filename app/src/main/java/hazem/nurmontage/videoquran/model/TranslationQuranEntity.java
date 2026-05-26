package hazem.nurmontage.videoquran.model;

import android.animation.ObjectAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.SpannableString;
import android.text.StaticLayout;
import android.text.TextPaint;
import androidx.core.graphics.ColorUtils;
import androidx.core.view.ViewCompat;
import hazem.nurmontage.videoquran.common.Common;
import hazem.nurmontage.videoquran.constant.AyaTextPreset;
import hazem.nurmontage.videoquran.constant.IpadType;
import hazem.nurmontage.videoquran.constant.TransitionType;
import hazem.nurmontage.videoquran.views.BlurredImageView;
import hazem.nurmontage.videoquran.views.TrackEntityView;
import java.io.Serializable;
import java.lang.ref.WeakReference;

/* loaded from: classes2.dex */
public class TranslationQuranEntity extends EntityView implements Serializable {
    private static final int ALPHA_BG = 100;
    private int clrAya;
    private int index;
    private int ipad_type;
    private boolean isFadeIn;
    private boolean isFadeOut;
    private boolean isVisible;
    private int mPreset;
    private String nameFont;
    private int number;
    private ObjectAnimator objectAnimator;
    private float offsetX;
    private ObjectAnimator otherAnimation;
    private final TextPaint paintAya;
    private final TextPaint paintAyaOutline;
    private final TextPaint paintAyaTrslOutline;
    private final TextPaint paintBg;
    private SpannableString spannableString;
    private StaticLayout staticLayout;
    private StaticLayout staticLayoutOutline;
    private String txt;
    private WeakReference<TrackEntityView> viewWeakReference;
    private int viewWidth;
    private WeakReference<BlurredImageView> weakBlurredImageView;
    private int clrBg = ViewCompat.MEASURED_STATE_MASK;
    private boolean isHaveBg = true;
    private float scaleX = 1.0f;

    public int getClrBg() {
        return this.clrBg;
    }

    public void setHaveBg(boolean z) {
        this.isHaveBg = z;
    }

    public boolean isHaveBg() {
        return this.isHaveBg;
    }

    public void setClrBg(int i) {
        this.clrBg = i;
        this.paintBg.setColor(i);
        this.paintBg.setAlpha(100);
    }

    public int getmPreset() {
        return this.mPreset;
    }

    public void setmPreset(int i) {
        this.mPreset = i;
    }

    public void setIpad_type(int i) {
        this.ipad_type = i;
    }

    public String getTxt() {
        return this.txt;
    }

    public int getIpad_type() {
        return this.ipad_type;
    }

    public void applyAyaPreset(Paint paint, AyaTextPreset ayaTextPreset, int i, Typeface typeface, float f) {
        paint.reset();
        paint.setTypeface(typeface);
        paint.setTextSize(f);
        paint.setAntiAlias(true);
        paint.setSubpixelText(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(i);
        if (paint == this.paintAyaTrslOutline) {
            f *= 1.35f;
        }
        int i2 = AnonymousClass1.$SwitchMap$hazem$nurmontage$videoquran$constant$AyaTextPreset[ayaTextPreset.ordinal()];
        if (i2 != 2) {
            if (i2 != 3) {
                if (i2 != 4) {
                    return;
                }
                paint.setShadowLayer(f * 0.45f, 0.0f, 0.0f, ColorUtils.setAlphaComponent(i, 255));
                return;
            } else {
                float f2 = 0.18f * f;
                float f3 = f * 0.08f;
                paint.setShadowLayer(f2, f3, f3, ColorUtils.setAlphaComponent((this.ipad_type == IpadType.HEART.ordinal() || this.ipad_type == IpadType.BATTERY.ordinal() || this.ipad_type == IpadType.BLUE_TYPE.ordinal()) ? -1 : ViewCompat.MEASURED_STATE_MASK, 120));
                return;
            }
        }
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(f * 0.12f);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        if (this.ipad_type == IpadType.HEART.ordinal() || this.ipad_type == IpadType.BATTERY.ordinal() || this.ipad_type == IpadType.BLUE_TYPE.ordinal()) {
            paint.setColor(hazem.nurmontage.videoquran.Utils.ColorUtils.lightenColor(i, 0.85f));
        } else {
            paint.setColor(hazem.nurmontage.videoquran.Utils.ColorUtils.darkenColor(i, 0.85f));
        }
    }

    /* renamed from: hazem.nurmontage.videoquran.model.TranslationQuranEntity$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$hazem$nurmontage$videoquran$constant$AyaTextPreset;

        static {
            int[] iArr = new int[AyaTextPreset.values().length];
            $SwitchMap$hazem$nurmontage$videoquran$constant$AyaTextPreset = iArr;
            try {
                iArr[AyaTextPreset.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$hazem$nurmontage$videoquran$constant$AyaTextPreset[AyaTextPreset.OUTLINE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$hazem$nurmontage$videoquran$constant$AyaTextPreset[AyaTextPreset.SHADOW.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$hazem$nurmontage$videoquran$constant$AyaTextPreset[AyaTextPreset.GLOW.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public void setPreset(AyaTextPreset ayaTextPreset) {
        this.mPreset = ayaTextPreset.ordinal();
        if (ayaTextPreset == AyaTextPreset.OUTLINE) {
            applyAyaPreset(this.paintAyaOutline, AyaTextPreset.OUTLINE, this.clrAya, this.paintAya.getTypeface(), this.paintAya.getTextSize());
            this.staticLayoutOutline = getStaticLayoutOutline();
            applyAyaPreset(this.paintAya, AyaTextPreset.NONE, this.clrAya, this.paintAya.getTypeface(), this.paintAya.getTextSize());
        } else {
            TextPaint textPaint = this.paintAya;
            applyAyaPreset(textPaint, ayaTextPreset, this.clrAya, textPaint.getTypeface(), this.paintAya.getTextSize());
        }
    }

    public AyaTextPreset get(int i) {
        if (i == AyaTextPreset.SHADOW.ordinal()) {
            return AyaTextPreset.SHADOW;
        }
        if (i == AyaTextPreset.OUTLINE.ordinal()) {
            return AyaTextPreset.OUTLINE;
        }
        if (i == AyaTextPreset.GLOW.ordinal()) {
            return AyaTextPreset.GLOW;
        }
        return AyaTextPreset.NONE;
    }

    public void initPreset(int i) {
        this.mPreset = i;
        AyaTextPreset ayaTextPreset = get(i);
        if (ayaTextPreset == AyaTextPreset.NONE) {
            return;
        }
        if (ayaTextPreset == AyaTextPreset.OUTLINE) {
            applyAyaPreset(this.paintAyaOutline, AyaTextPreset.OUTLINE, this.clrAya, this.paintAya.getTypeface(), this.paintAya.getTextSize());
            this.staticLayoutOutline = getStaticLayoutOutline();
            applyAyaPreset(this.paintAya, AyaTextPreset.NONE, this.clrAya, this.paintAya.getTypeface(), this.paintAya.getTextSize());
        } else {
            TextPaint textPaint = this.paintAya;
            applyAyaPreset(textPaint, ayaTextPreset, this.clrAya, textPaint.getTypeface(), this.paintAya.getTextSize());
        }
    }

    public void initPresetAya(int i) {
        this.mPreset = i;
        AyaTextPreset ayaTextPreset = get(i);
        if (ayaTextPreset == AyaTextPreset.NONE) {
            return;
        }
        if (ayaTextPreset == AyaTextPreset.OUTLINE) {
            applyAyaPreset(this.paintAyaOutline, AyaTextPreset.OUTLINE, this.clrAya, this.paintAya.getTypeface(), this.paintAya.getTextSize());
            this.staticLayoutOutline = getStaticLayoutOutline();
            applyAyaPreset(this.paintAya, AyaTextPreset.NONE, this.clrAya, this.paintAya.getTypeface(), this.paintAya.getTextSize());
        } else {
            TextPaint textPaint = this.paintAya;
            applyAyaPreset(textPaint, ayaTextPreset, this.clrAya, textPaint.getTypeface(), this.paintAya.getTextSize());
        }
    }

    public void setViewWeakReference(WeakReference<TrackEntityView> weakReference, WeakReference<BlurredImageView> weakReference2) {
        this.viewWeakReference = weakReference;
        this.weakBlurredImageView = weakReference2;
    }

    public int getDuration_fade() {
        return (int) ((Math.abs(getEntityQuran().getRect().right / getEntityQuran().getSecond_in_screen()) - Math.abs(getEntityQuran().getRect().left / getEntityQuran().getSecond_in_screen())) * 0.2f * 1000.0f);
    }

    public String getNameFont() {
        return this.nameFont;
    }

    public void setIndex(int i) {
        this.index = i;
    }

    public int getIndex() {
        return this.index;
    }

    public void setClrAya(int i) {
        this.clrAya = i;
    }

    public int getClrAya() {
        return this.clrAya;
    }

    public TextPaint getPaintAya() {
        return this.paintAya;
    }

    public float calculateTextSize(String str, Paint paint, int i, int i2) {
        float f = 0.0f;
        if (str != null && !str.isEmpty() && i > 0 && i2 > 0) {
            paint.setTextSize(1.0f);
            Rect rect = new Rect();
            paint.getTextBounds(str, 0, str.length(), rect);
            rect.width();
            rect.height();
            float f2 = 1000.0f;
            for (int i3 = 0; i3 < 100; i3++) {
                float f3 = (f + f2) / 2.0f;
                paint.setTextSize(f3);
                paint.getTextBounds(str, 0, str.length(), rect);
                float width = rect.width();
                float height = rect.height();
                if (width > i || height > i2) {
                    f2 = f3;
                } else {
                    f = f3;
                }
            }
        }
        return f;
    }

    public float calculateOptimalTextSize(String str, int i, int i2, TextPaint textPaint) {
        float f = 5.0f;
        float f2 = 1000.0f;
        float f3 = 5.0f;
        while (f <= f2) {
            float f4 = (f + f2) / 2.0f;
            textPaint.setTextSize(f4);
            StaticLayout build = StaticLayout.Builder.obtain(str, 0, str.length(), textPaint, i).setAlignment(Layout.Alignment.ALIGN_CENTER).setLineSpacing(0.0f, 1.0f).setIncludePad(false).build();
            float height = build.getHeight();
            float maxLineWidth = getMaxLineWidth(build);
            if (height > i2 || maxLineWidth > i) {
                f2 = f4 - 0.03f;
            } else {
                f3 = f4;
                f = 0.03f + f4;
            }
        }
        return f3;
    }

    private float getMaxLineWidth(StaticLayout staticLayout) {
        float f = 0.0f;
        for (int i = 0; i < staticLayout.getLineCount(); i++) {
            f = Math.max(f, staticLayout.getLineWidth(i));
        }
        return f;
    }

    private float createBestSizeLayout(String str, TextPaint textPaint, int i, int i2) {
        float height = this.rect.height() * 0.08f;
        for (float height2 = this.rect.height() * 0.28f; height2 >= height; height2 -= 1.0f) {
            textPaint.setTextSize(height2);
            if (StaticLayout.Builder.obtain(str, 0, str.length(), textPaint, i).setAlignment(Layout.Alignment.ALIGN_CENTER).setLineSpacing(0.0f, 1.12f).setIncludePad(false).build().getHeight() <= i2) {
                return textPaint.getTextSize();
            }
        }
        textPaint.setTextSize(height);
        return textPaint.getTextSize();
    }

    public float calculateTextSize() {
        return createBestSizeLayout(this.txt, this.paintAya, (int) (this.rect.width() * 0.9f), (int) (this.rect.height() * 0.95f));
    }

    public void setTextSize(float f) {
        this.paintAya.setTextSize(f);
    }

    public void setTextSizeInBoucle(float f) {
        this.paintAya.setTextSize(f);
        SpannableString spannableString = new SpannableString(this.txt);
        this.staticLayout = StaticLayout.Builder.obtain(spannableString, 0, spannableString.length(), this.paintAya, this.viewWidth).setAlignment(Layout.Alignment.ALIGN_CENTER).setLineSpacing(0.0f, 1.0f).setIncludePad(false).build();
        this.y = this.rect.centerY() - (this.staticLayout.getHeight() * 0.5f);
        this.x = this.rect.centerX() - (this.staticLayout.getWidth() * 0.5f);
    }

    private StaticLayout createBalancedLayout(String str, TextPaint textPaint, int i, float f, float f2) {
        StaticLayout build;
        SpannableString spannableString = new SpannableString(str);
        do {
            textPaint.setTextSize(f);
            build = StaticLayout.Builder.obtain(spannableString, 0, spannableString.length(), textPaint, i).setAlignment(Layout.Alignment.ALIGN_CENTER).setIncludePad(false).setLineSpacing(0.0f, 1.0f).build();
            int lineCount = build.getLineCount();
            if (lineCount <= 1) {
                break;
            }
            int i2 = lineCount - 1;
            if (!(build.getLineWidth(i2) >= ((float) i) * 0.25f ? str.substring(build.getLineStart(i2), build.getLineEnd(i2)).trim().split("\\s+").length <= 1 : true)) {
                break;
            }
            f -= 1.0f;
        } while (f > f2);
        return build;
    }

    public void setupScale(float f, int i, int i2) {
        float f2 = i;
        int i3 = (int) (0.9f * f2);
        this.viewWidth = i3;
        this.staticLayout = createBalancedLayout(this.txt, this.paintAya, i3, f * f2, 2.0f);
        float width = this.staticLayout.getWidth() * 0.5f;
        float height = this.staticLayout.getHeight() * 0.5f;
        float height2 = this.rect.height() * 0.12f;
        float centerY = this.rect.centerY();
        float centerX = this.rect.centerX();
        this.rect.set(centerX - width, (centerY - height) - height2, centerX + width, centerY + height + height2);
        this.max_h = Math.round(this.rect.height() * 0.85f);
        this.max_w = Math.round(this.rect.width() * 0.85f);
        this.x = this.rect.centerX() - (this.staticLayout.getWidth() * 0.5f);
        this.y = this.rect.centerY() - (this.staticLayout.getHeight() * 0.5f);
    }

    public void setupScaleSave(float f, int i) {
        int round = Math.round(this.rect.width());
        this.viewWidth = round;
        this.staticLayout = createBalancedLayout(this.txt, this.paintAya, round, f * i, 2.0f);
        this.max_h = Math.round(this.rect.height() * 0.85f);
        this.max_w = Math.round(this.rect.width() * 0.85f);
        this.x = this.rect.centerX() - (this.staticLayout.getWidth() * 0.5f);
        this.y = this.rect.centerY() - (this.staticLayout.getHeight() * 0.5f);
    }

    public void updatePaint(float f, int i) {
        this.paintAya.setTextSize(f);
        SpannableString spannableString = new SpannableString(this.txt);
        this.viewWidth = i;
        this.staticLayout = StaticLayout.Builder.obtain(spannableString, 0, spannableString.length(), this.paintAya, this.viewWidth).setAlignment(Layout.Alignment.ALIGN_CENTER).setLineSpacing(0.0f, 1.0f).setIncludePad(false).build();
        this.max_h = Math.round(this.rect.height() * 0.85f);
        this.max_w = Math.round(this.rect.width() * 0.85f);
        this.x = this.rect.centerX() - (this.staticLayout.getWidth() * 0.5f);
        this.y = this.rect.centerY() - (this.staticLayout.getHeight() * 0.5f);
    }

    @Override // hazem.nurmontage.videoquran.model.EntityView
    public void scale(float f, int i, int i2) {
        setFactor_scale(f);
        float height = this.rect.height() * f;
        float f2 = i;
        float f3 = 0.46f * f2;
        this.rect.left = this.rect.centerX() - f3;
        this.rect.right = this.rect.centerX() + f3;
        float f4 = height * 0.5f;
        this.rect.top = this.rect.centerY() - f4;
        this.rect.bottom = this.rect.centerY() + f4;
        this.viewWidth = (int) this.rect.width();
        this.paintAya.setTextSize(calculateTextSize());
        createStaticLayout();
        setFcSize(this.paintAya.getTextSize() / f2);
        initPreset(getmPreset());
    }

    public void applyAll(int i, RectF rectF, float f, float f2, TranslationQuranEntity translationQuranEntity) {
        this.paintAya.setTextSize(f);
        SpannableString spannableString = new SpannableString(this.txt);
        this.viewWidth = (int) (((int) Math.max(rectF.width(), Math.round(this.paintAya.measureText(spannableString.toString())))) * 1.1f);
        StaticLayout build = StaticLayout.Builder.obtain(spannableString, 0, spannableString.length(), this.paintAya, this.viewWidth).setAlignment(Layout.Alignment.ALIGN_CENTER).setLineSpacing(0.0f, 1.0f).setIncludePad(false).build();
        this.staticLayout = build;
        float width = build.getWidth();
        setFcSize(f2);
        float f3 = width * 0.5f;
        float height = this.rect.height() * (width / this.rect.width()) * 0.5f;
        this.rect.set(rectF.centerX() - f3, rectF.centerY() - height, rectF.centerX() + f3, rectF.centerY() + height);
        this.x = this.rect.centerX() - (this.staticLayout.getWidth() * 0.5f);
        this.y = this.rect.centerY() - (this.staticLayout.getHeight() * 0.5f);
        this.max_h = Math.round(this.rect.height() * 0.85f);
        this.max_w = Math.round(this.rect.width() * 0.85f);
        initPreset(getmPreset());
    }

    public void applyAll(int i, RectF rectF, float f, float f2) {
        int round = Math.round(rectF.width());
        this.viewWidth = round;
        StaticLayout createBalancedLayout = createBalancedLayout(this.txt, this.paintAya, round, f, 2.0f);
        this.staticLayout = createBalancedLayout;
        float width = createBalancedLayout.getWidth();
        setFcSize(f2);
        float height = this.rect.height() * 0.12f;
        float f3 = width * 0.5f;
        float height2 = this.staticLayout.getHeight() * 0.5f;
        this.rect.set(rectF.centerX() - f3, (rectF.centerY() - height2) - height, rectF.centerX() + f3, rectF.centerY() + height2 + height);
        this.x = this.rect.centerX() - (this.staticLayout.getWidth() * 0.5f);
        this.y = this.rect.centerY() - (this.staticLayout.getHeight() * 0.5f);
        this.max_h = Math.round(this.rect.height() * 0.85f);
        this.max_w = Math.round(this.rect.width() * 0.85f);
        initPreset(getmPreset());
    }

    @Override // hazem.nurmontage.videoquran.model.EntityView
    public void postTranslate(float f, float f2) {
        this.rect.offset(f, f2);
        this.x = this.rect.centerX() - (this.staticLayout.getWidth() * 0.5f);
        this.y = this.rect.centerY() - (this.staticLayout.getHeight() * 0.5f);
    }

    public void setTranslate(float f, float f2) {
        float width = this.rect.width() * 0.5f;
        float height = this.rect.height() * 0.5f;
        this.rect.left = f - width;
        this.rect.right = f + width;
        this.rect.top = f2 - height;
        this.rect.bottom = f2 + height;
        this.x = this.rect.centerX() - (this.staticLayout.getWidth() * 0.5f);
        this.y = this.rect.centerY() - (this.staticLayout.getHeight() * 0.5f);
    }

    public float getWidth() {
        this.paintAya.setTextSize(3.0f);
        return this.paintAya.measureText(this.txt);
    }

    public void createStaticLayout() {
        String str = this.txt;
        TextPaint textPaint = this.paintAya;
        this.staticLayout = createBalancedLayout(str, textPaint, this.viewWidth, textPaint.getTextSize(), 2.0f);
        this.y = this.rect.centerY() - (this.staticLayout.getHeight() * 0.5f);
        this.x = this.rect.centerX() - (this.staticLayout.getWidth() * 0.5f);
    }

    private StaticLayout buildStaticLayout(String str, TextPaint textPaint, int i) {
        return StaticLayout.Builder.obtain(str, 0, str.length(), textPaint, i).setAlignment(Layout.Alignment.ALIGN_CENTER).setLineSpacing(0.0f, 1.0f).setIncludePad(false).build();
    }

    public void setStaticLayout() {
        SpannableString spannableString = new SpannableString(this.txt);
        this.staticLayout = StaticLayout.Builder.obtain(spannableString, 0, spannableString.length(), this.paintAya, this.viewWidth).setAlignment(Layout.Alignment.ALIGN_CENTER).setLineSpacing(0.0f, 1.0f).setIncludePad(false).build();
        this.x = this.rect.centerX() - (this.staticLayout.getWidth() * 0.5f);
        this.y = this.rect.centerY() - (this.staticLayout.getHeight() * 0.5f);
    }

    private StaticLayout getStaticLayoutOutline() {
        SpannableString spannableString = new SpannableString(this.txt);
        return StaticLayout.Builder.obtain(spannableString, 0, spannableString.length(), this.paintAyaOutline, this.viewWidth).setAlignment(Layout.Alignment.ALIGN_CENTER).setLineSpacing(0.0f, 1.0f).setIncludePad(false).build();
    }

    public void updateStaticLayout() {
        SpannableString spannableString = this.spannableString;
        this.staticLayout = StaticLayout.Builder.obtain(spannableString, 0, spannableString.length(), this.paintAya, this.viewWidth).setAlignment(Layout.Alignment.ALIGN_CENTER).setLineSpacing(0.0f, 1.0f).setIncludePad(false).build();
    }

    public void setFadeIn(boolean z) {
        this.isFadeIn = z;
    }

    public void setFadeOut(boolean z) {
        this.isFadeOut = z;
    }

    public boolean isFadeIn() {
        return this.isFadeIn;
    }

    public boolean isFadeOut() {
        return this.isFadeOut;
    }

    public boolean isAnimRun() {
        ObjectAnimator objectAnimator;
        ObjectAnimator objectAnimator2 = this.objectAnimator;
        return (objectAnimator2 != null && objectAnimator2.isRunning()) || ((objectAnimator = this.otherAnimation) != null && objectAnimator.isRunning());
    }

    public void setOpacityFade(int i) {
        this.paintAya.setAlpha(i);
        this.paintAyaTrslOutline.setAlpha(this.paintAya.getAlpha());
        this.paintAyaOutline.setAlpha(this.paintAya.getAlpha());
        if (isAnimTest()) {
            WeakReference<BlurredImageView> weakReference = this.weakBlurredImageView;
            if (weakReference != null) {
                weakReference.get().invalidate();
                return;
            }
            return;
        }
        WeakReference<TrackEntityView> weakReference2 = this.viewWeakReference;
        if (weakReference2 != null) {
            weakReference2.get().invalidate();
        }
    }

    @Override // hazem.nurmontage.videoquran.model.EntityView
    public void endAnimator() {
        try {
            ObjectAnimator objectAnimator = this.objectAnimator;
            if (objectAnimator != null && objectAnimator.isRunning()) {
                this.objectAnimator.end();
            }
            ObjectAnimator objectAnimator2 = this.otherAnimation;
            if (objectAnimator2 != null && objectAnimator2.isRunning()) {
                this.otherAnimation.end();
            }
            this.objectAnimator = null;
            this.otherAnimation = null;
        } catch (Exception unused) {
        }
        setFadeIn(false);
        setFadeOut(false);
        this.offsetX = 0.0f;
        this.paintAya.setAlpha(255);
        this.paintAyaTrslOutline.setAlpha(this.paintAya.getAlpha());
        this.paintAyaOutline.setAlpha(this.paintAya.getAlpha());
    }

    public void setSlideX(float f) {
        WeakReference<BlurredImageView> weakReference;
        this.offsetX = f;
        this.paintAya.setAlpha(Math.round((1.0f - Math.abs(f)) * 255.0f));
        this.paintAyaTrslOutline.setAlpha(this.paintAya.getAlpha());
        this.paintAyaOutline.setAlpha(this.paintAya.getAlpha());
        if (!isAnimTest() || (weakReference = this.weakBlurredImageView) == null) {
            return;
        }
        weakReference.get().invalidate();
    }

    public void setSlideXOut(float f) {
        WeakReference<BlurredImageView> weakReference;
        this.offsetX = f;
        this.paintAya.setAlpha(Math.round((1.0f - Math.abs(f)) * 255.0f));
        this.paintAyaTrslOutline.setAlpha(this.paintAya.getAlpha());
        this.paintAyaOutline.setAlpha(this.paintAya.getAlpha());
        if (!isAnimTest() || (weakReference = this.weakBlurredImageView) == null) {
            return;
        }
        weakReference.get().invalidate();
    }

    public void setFactorSize(float f) {
        WeakReference<BlurredImageView> weakReference;
        this.scaleX = f;
        if (!isAnimTest() || (weakReference = this.weakBlurredImageView) == null) {
            return;
        }
        weakReference.get().invalidate();
    }

    public void slidToLeft(int i, boolean z) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, "SlideX", 1.0f, 0.0f);
        this.otherAnimation = ofFloat;
        ofFloat.setDuration(i);
        if (z) {
            this.otherAnimation.setRepeatMode(1);
            this.otherAnimation.setRepeatCount(-1);
        }
        this.otherAnimation.start();
    }

    public void slidToRightOut(int i, boolean z) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, "SlideXOut", 0.0f, 1.0f);
        this.otherAnimation = ofFloat;
        ofFloat.setDuration(i);
        if (z) {
            this.otherAnimation.setRepeatMode(1);
            this.otherAnimation.setRepeatCount(-1);
        }
        this.otherAnimation.start();
    }

    public void slidToLeftOut(int i, boolean z) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, "SlideXOut", 0.0f, -1.0f);
        this.otherAnimation = ofFloat;
        ofFloat.setDuration(i);
        if (z) {
            this.otherAnimation.setRepeatMode(1);
            this.otherAnimation.setRepeatCount(-1);
        }
        this.otherAnimation.start();
    }

    public void slidToRight(int i, boolean z) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, "SlideX", -1.0f, 0.0f);
        this.otherAnimation = ofFloat;
        ofFloat.setDuration(i);
        if (z) {
            this.otherAnimation.setRepeatMode(1);
            this.otherAnimation.setRepeatCount(-1);
        }
        this.otherAnimation.start();
    }

    public void zoomIn_In(int i, boolean z) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, "FactorSize", 0.0f, 1.0f);
        this.otherAnimation = ofFloat;
        ofFloat.setDuration(i);
        if (z) {
            this.otherAnimation.setRepeatMode(1);
            this.otherAnimation.setRepeatCount(-1);
        }
        this.otherAnimation.start();
    }

    public void runIn(int i, boolean z, String str) {
        if (str.equals(TransitionType.SLIDE_TO_LEFT.getValue())) {
            slidToLeft(i, z);
        }
        if (str.equals(TransitionType.SLIDE_TO_RIGHT.getValue())) {
            slidToRight(i, z);
        }
        if (str.equals(TransitionType.ZOOM_IN.getValue())) {
            zoomIn_In(i, z);
        }
        if (str.equals(TransitionType.FADE_IN.getValue())) {
            fadeIn(i, z);
        }
    }

    private void fadeIn(int i, boolean z) {
        ObjectAnimator ofInt = ObjectAnimator.ofInt(this, "OpacityFade", 0, 255);
        this.objectAnimator = ofInt;
        ofInt.setDuration(i);
        if (z) {
            this.objectAnimator.setRepeatMode(1);
            this.objectAnimator.setRepeatCount(-1);
        }
        this.objectAnimator.start();
    }

    private void fadeOut(int i, boolean z) {
        ObjectAnimator ofInt = ObjectAnimator.ofInt(this, "OpacityFade", 255, 0);
        this.objectAnimator = ofInt;
        ofInt.setDuration(i);
        if (z) {
            this.objectAnimator.setRepeatMode(1);
            this.objectAnimator.setRepeatCount(-1);
        }
        this.objectAnimator.start();
    }

    public void runOut(int i, boolean z, String str) {
        if (str.equals(TransitionType.SLIDE_TO_LEFT.getValue())) {
            slidToLeftOut(i, z);
        }
        if (str.equals(TransitionType.SLIDE_TO_RIGHT.getValue())) {
            slidToRightOut(i, z);
        }
        if (str.equals(TransitionType.FADE_OUT.getValue())) {
            fadeOut(i, z);
        }
    }

    public TranslationQuranEntity(String str, RectF rectF, Typeface typeface, int i, int i2, String str2, int i3, int i4) {
        this.nameFont = Common.FONT_QURAN;
        TextPaint textPaint = new TextPaint(1);
        this.paintAya = textPaint;
        this.paintAyaOutline = new TextPaint(1);
        this.paintAyaTrslOutline = new TextPaint(1);
        TextPaint textPaint2 = new TextPaint(1);
        this.paintBg = textPaint2;
        setCanvasWH(i3, i4);
        this.txt = str;
        this.nameFont = str2;
        this.number = i;
        float f = i4;
        this.rect = new RectF(0.0f, f - rectF.height(), i3, f);
        setVisible(true);
        this.viewWidth = (int) rectF.width();
        textPaint.setTypeface(typeface);
        textPaint.setColor(i2);
        textPaint.setTextSize(calculateTextSize());
        textPaint2.setColor(ViewCompat.MEASURED_STATE_MASK);
        textPaint2.setAlpha(100);
        setClrAya(i2);
        this.max_h = (int) (this.rect.height() * 0.85f);
        this.max_w = (int) (this.rect.width() * 0.85f);
        createStaticLayout();
    }

    public TranslationQuranEntity(int i, int i2, String str, RectF rectF, Typeface typeface, int i3, int i4, String str2) {
        this.nameFont = Common.FONT_QURAN;
        TextPaint textPaint = new TextPaint(1);
        this.paintAya = textPaint;
        this.paintAyaOutline = new TextPaint(1);
        this.paintAyaTrslOutline = new TextPaint(1);
        TextPaint textPaint2 = new TextPaint(1);
        this.paintBg = textPaint2;
        setCanvasWH(i, i2);
        this.txt = str;
        this.nameFont = str2;
        this.number = i3;
        this.rect = rectF;
        setVisible(true);
        this.viewWidth = (int) rectF.width();
        textPaint.setTypeface(typeface);
        textPaint.setColor(i4);
        textPaint2.setColor(ViewCompat.MEASURED_STATE_MASK);
        textPaint2.setAlpha(100);
        setClrAya(i4);
        this.max_h = (int) (this.rect.height() * 0.85f);
        this.max_w = (int) (this.rect.width() * 0.85f);
    }

    public TranslationQuranEntity(String str, RectF rectF, Typeface typeface, int i, int i2, String str2, float f) {
        this.nameFont = Common.FONT_QURAN;
        TextPaint textPaint = new TextPaint(1);
        this.paintAya = textPaint;
        this.paintAyaOutline = new TextPaint(1);
        this.paintAyaTrslOutline = new TextPaint(1);
        TextPaint textPaint2 = new TextPaint(1);
        this.paintBg = textPaint2;
        this.txt = str;
        this.nameFont = str2;
        this.number = i;
        this.rect = new RectF(rectF.left, rectF.top, rectF.right, rectF.bottom);
        setVisible(true);
        this.viewWidth = (int) rectF.width();
        textPaint.setTypeface(typeface);
        textPaint.setColor(i2);
        textPaint.setTextSize(f);
        textPaint2.setColor(ViewCompat.MEASURED_STATE_MASK);
        textPaint2.setAlpha(100);
        setClrAya(i2);
        this.max_h = (int) (this.rect.height() * 0.85f);
        this.max_w = (int) (this.rect.width() * 0.85f);
    }

    public TranslationQuranEntity(String str, RectF rectF, Typeface typeface, int i, int i2, String str2, float f, boolean z) {
        this.nameFont = Common.FONT_QURAN;
        TextPaint textPaint = new TextPaint(1);
        this.paintAya = textPaint;
        this.paintAyaOutline = new TextPaint(1);
        this.paintAyaTrslOutline = new TextPaint(1);
        TextPaint textPaint2 = new TextPaint(1);
        this.paintBg = textPaint2;
        this.txt = str;
        this.nameFont = str2;
        this.number = i;
        this.rect = new RectF(rectF.left, rectF.top, rectF.right, rectF.bottom);
        setVisible(true);
        this.viewWidth = (int) rectF.width();
        textPaint.setTypeface(typeface);
        textPaint.setColor(i2);
        textPaint.setTextSize(f);
        this.max_h = (int) (this.rect.height() * 0.85f);
        this.max_w = (int) (this.rect.width() * 0.85f);
        textPaint2.setColor(ViewCompat.MEASURED_STATE_MASK);
        textPaint2.setAlpha(100);
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int i) {
        this.number = i;
    }

    public void update(RectF rectF, int i, int i2) {
        this.rect = rectF;
        this.max_h = i2;
        this.max_w = i;
        this.viewWidth = (int) this.rect.width();
    }

    public void onResize(RectF rectF, int i, int i2) {
        this.rect = new RectF(0.0f, getCanvasH() - rectF.height(), getCanvasW(), getCanvasH());
        this.max_h = i2;
        this.max_w = i;
        this.viewWidth = (int) this.rect.width();
    }

    @Override // hazem.nurmontage.videoquran.model.EntityView
    public int getMax_h() {
        return this.max_h;
    }

    @Override // hazem.nurmontage.videoquran.model.EntityView
    public int getMax_w() {
        return this.max_w;
    }

    public StaticLayout getStaticLayout() {
        return this.staticLayout;
    }

    public void setTypeface(Typeface typeface, String str) {
        this.paintAya.setTypeface(typeface);
        this.nameFont = str;
    }

    public void setTypefaceOneAya(Typeface typeface, String str) {
        this.paintAya.setTypeface(typeface);
        this.nameFont = str;
    }

    public void setColor(int i) {
        setClrAya(i);
        this.paintAya.setColor(i);
    }

    public void draw(Canvas canvas) {
        if (this.staticLayout != null) {
            if (this.isHaveBg) {
                canvas.drawRect(0.0f, this.rect.top, canvas.getWidth(), this.rect.bottom, this.paintBg);
            }
            canvas.save();
            canvas.translate(this.x + (this.offsetX * this.staticLayout.getWidth()), this.y);
            float f = this.scaleX;
            canvas.scale(f, f);
            if (this.mPreset == AyaTextPreset.OUTLINE.ordinal() && this.staticLayoutOutline != null) {
                this.paintAyaOutline.setTextSize(this.paintAya.getTextSize());
                this.staticLayoutOutline.draw(canvas);
            }
            this.staticLayout.draw(canvas);
            canvas.restore();
        }
    }

    public void setupCanvasDraw(Canvas canvas) {
        this.y = (canvas.getHeight() - this.staticLayout.getHeight()) * 0.5f;
        this.x = this.rect.centerX() - (this.staticLayout.getWidth() * 0.5f);
        canvas.save();
        if (this.isHaveBg) {
            canvas.drawRect(0.0f, 0.0f, canvas.getWidth(), canvas.getHeight(), this.paintBg);
        }
        canvas.translate(this.x, this.y);
    }

    public void restoreCanvas(Canvas canvas) {
        try {
            canvas.restore();
        } catch (Exception unused) {
        }
    }

    public void singleDraw(Canvas canvas, int i) {
        if (this.staticLayout != null) {
            this.paintAya.setAlpha(i);
            this.staticLayout.draw(canvas);
        }
    }

    public void singleDraw(Canvas canvas, int i, float f) {
        if (this.staticLayout != null) {
            canvas.save();
            canvas.translate(f * this.staticLayout.getWidth(), 0.0f);
            this.paintAya.setAlpha(i);
            this.staticLayout.draw(canvas);
            canvas.restore();
        }
    }

    public void singleDraw(Canvas canvas) {
        if (this.staticLayout != null) {
            if (this.mPreset == AyaTextPreset.OUTLINE.ordinal() && this.staticLayoutOutline != null) {
                this.paintAyaOutline.setTextSize(this.paintAya.getTextSize());
                this.staticLayoutOutline.draw(canvas);
            }
            this.staticLayout.draw(canvas);
        }
    }

    @Override // hazem.nurmontage.videoquran.model.EntityView
    public boolean isVisible() {
        return this.isVisible;
    }

    public void setTxt(String str) {
        this.txt = str;
        createStaticLayout();
    }

    @Override // hazem.nurmontage.videoquran.model.EntityView
    public void setVisible(boolean z) {
        this.isVisible = z;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    @Override // hazem.nurmontage.videoquran.model.EntityView
    public boolean isAnimTest() {
        return super.isAnimTest();
    }

    public void setUnderLine(boolean z) {
        this.paintAya.setUnderlineText(z);
    }

    @Override // hazem.nurmontage.videoquran.model.EntityView
    public void setAnimTest(boolean z) {
        super.setAnimTest(z);
    }
}

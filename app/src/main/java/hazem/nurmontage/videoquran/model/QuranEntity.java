package hazem.nurmontage.videoquran.model;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.VectorDrawable;
import android.text.Layout;
import android.text.SpannableString;
import android.text.StaticLayout;
import android.text.TextPaint;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import androidx.core.view.ViewCompat;
import hazem.nurmontage.videoquran.Utils.EndOfAyaSpan;
import hazem.nurmontage.videoquran.common.Common;
import hazem.nurmontage.videoquran.constant.AyaTextPreset;
import hazem.nurmontage.videoquran.constant.IpadType;
import hazem.nurmontage.videoquran.constant.TransitionType;
import hazem.nurmontage.videoquran.views.BlurredImageView;
import hazem.nurmontage.videoquran.views.TrackEntityView;
import java.io.Serializable;
import java.lang.ref.WeakReference;

/* loaded from: classes2.dex */
public class QuranEntity extends EntityView implements Serializable {
    private int clrAya;
    private int clrTrsl;
    private String complete_aya;
    private int endWord_index;
    private String icon;
    private int index;
    private int indexNumber;
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
    private final TextPaint paintTranslationAya;
    private SpannableString spannableString;
    private int startWord_index;
    private StaticLayout staticLayout;
    private StaticLayout staticLayoutOutline;
    private StaticLayout staticLayoutTranslation;
    private StaticLayout staticLayoutTranslationOutline;
    private String translation;
    private String translation_complete;
    private String txt;
    private final Typeface typefaceNumber;
    private VectorDrawable vectorDrawable;
    private WeakReference<TrackEntityView> viewWeakReference;
    private int viewWidth;
    private WeakReference<BlurredImageView> weakBlurredImageView;
    private float x_translation;
    private float padding = 1.0f;
    private float scaleX = 1.0f;

    public int getmPreset() {
        return this.mPreset;
    }

    public void setmPreset(int i) {
        this.mPreset = i;
    }

    public void setIpad_type(int i) {
        this.ipad_type = i;
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

    /* renamed from: hazem.nurmontage.videoquran.model.QuranEntity$1, reason: invalid class name */
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
            if (isTrsl()) {
                applyAyaPreset(this.paintAyaTrslOutline, AyaTextPreset.OUTLINE, this.clrTrsl, this.paintTranslationAya.getTypeface(), this.paintTranslationAya.getTextSize());
                this.staticLayoutTranslationOutline = buildStaticLayout(this.translation, this.paintAyaTrslOutline, this.staticLayoutTranslation.getWidth());
                applyAyaPreset(this.paintTranslationAya, AyaTextPreset.NONE, this.clrTrsl, this.paintTranslationAya.getTypeface(), this.paintTranslationAya.getTextSize());
                return;
            }
            return;
        }
        TextPaint textPaint = this.paintAya;
        applyAyaPreset(textPaint, ayaTextPreset, this.clrAya, textPaint.getTypeface(), this.paintAya.getTextSize());
        if (isTrsl()) {
            TextPaint textPaint2 = this.paintTranslationAya;
            applyAyaPreset(textPaint2, ayaTextPreset, this.clrTrsl, textPaint2.getTypeface(), this.paintTranslationAya.getTextSize());
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
            if (isTrsl()) {
                applyAyaPreset(this.paintAyaTrslOutline, AyaTextPreset.OUTLINE, this.clrTrsl, this.paintTranslationAya.getTypeface(), this.paintTranslationAya.getTextSize());
                this.staticLayoutTranslationOutline = buildStaticLayout(this.translation, this.paintAyaTrslOutline, this.staticLayoutTranslation.getWidth());
                applyAyaPreset(this.paintTranslationAya, AyaTextPreset.NONE, this.clrTrsl, this.paintTranslationAya.getTypeface(), this.paintTranslationAya.getTextSize());
                return;
            }
            return;
        }
        TextPaint textPaint = this.paintAya;
        applyAyaPreset(textPaint, ayaTextPreset, this.clrAya, textPaint.getTypeface(), this.paintAya.getTextSize());
        if (!isTrsl() || this.staticLayoutTranslation == null) {
            return;
        }
        TextPaint textPaint2 = this.paintTranslationAya;
        applyAyaPreset(textPaint2, ayaTextPreset, this.clrTrsl, textPaint2.getTypeface(), this.paintTranslationAya.getTextSize());
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

    public void initPresetTrsl(int i) {
        this.mPreset = i;
        AyaTextPreset ayaTextPreset = get(i);
        if (ayaTextPreset == AyaTextPreset.NONE) {
            return;
        }
        if (ayaTextPreset == AyaTextPreset.OUTLINE) {
            if (isTrsl()) {
                applyAyaPreset(this.paintAyaTrslOutline, AyaTextPreset.OUTLINE, this.clrTrsl, this.paintTranslationAya.getTypeface(), this.paintTranslationAya.getTextSize());
                this.staticLayoutTranslationOutline = buildStaticLayout(this.translation, this.paintAyaTrslOutline, this.staticLayoutTranslation.getWidth());
                applyAyaPreset(this.paintTranslationAya, AyaTextPreset.NONE, this.clrTrsl, this.paintTranslationAya.getTypeface(), this.paintTranslationAya.getTextSize());
                return;
            }
            return;
        }
        if (!isTrsl() || this.staticLayoutTranslation == null) {
            return;
        }
        TextPaint textPaint = this.paintTranslationAya;
        applyAyaPreset(textPaint, ayaTextPreset, this.clrTrsl, textPaint.getTypeface(), this.paintTranslationAya.getTextSize());
    }

    public void setVectorDrawable(VectorDrawable vectorDrawable) {
        this.vectorDrawable = vectorDrawable;
    }

    public void setTranslation(String str) {
        this.translation = str;
    }

    public String getTranslation() {
        return this.translation;
    }

    public Typeface getTypefaceNumber() {
        return this.typefaceNumber;
    }

    public void setTranslation_complete(String str) {
        this.translation_complete = str;
    }

    public String getTranslation_complete() {
        return this.translation_complete;
    }

    public void setStartWord_index(int i) {
        this.startWord_index = i;
    }

    public void setEndWord_index(int i) {
        this.endWord_index = i;
    }

    public int getStartWord_index() {
        return this.startWord_index;
    }

    public int getEndWord_index() {
        return this.endWord_index;
    }

    public void setIcon(String str) {
        this.icon = str;
    }

    public String getIcon() {
        return this.icon;
    }

    public int getViewWidth() {
        return this.viewWidth;
    }

    public void setViewWeakReference(WeakReference<TrackEntityView> weakReference, WeakReference<BlurredImageView> weakReference2) {
        this.viewWeakReference = weakReference;
        this.weakBlurredImageView = weakReference2;
    }

    public int getDuration_fade() {
        return (int) ((Math.abs(getEntityQuran().getRect().right / getEntityQuran().getSecond_in_screen()) - Math.abs(getEntityQuran().getRect().left / getEntityQuran().getSecond_in_screen())) * 0.2f * 1000.0f);
    }

    public VectorDrawable getVectorDrawable() {
        return this.vectorDrawable;
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

    public void setTxt(String str) {
        this.txt = str;
        SpannableString spannableString = new SpannableString(str);
        if (this.number != -1) {
            try {
                int length = str.length();
                if (length > this.indexNumber) {
                    spannableString.setSpan(new EndOfAyaSpan(this.vectorDrawable, this.typefaceNumber, "" + this.number), this.indexNumber, length, 0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.staticLayout = StaticLayout.Builder.obtain(spannableString, 0, spannableString.length(), this.paintAya, this.viewWidth).setAlignment(Layout.Alignment.ALIGN_CENTER).setLineSpacing(0.0f, 1.0f).setIncludePad(false).build();
        if (isTrsl()) {
            this.staticLayoutTranslation = buildStaticLayout(this.translation, this.paintTranslationAya, this.staticLayoutTranslation.getWidth());
            this.y = this.rect.centerY() - ((this.staticLayout.getHeight() + this.staticLayoutTranslation.getHeight()) * 0.5f);
            this.x_translation = this.rect.centerX() - (this.staticLayoutTranslation.getWidth() * 0.5f);
        }
    }

    public void setClrAya(int i) {
        this.clrAya = i;
    }

    public void setClrTrsl(int i) {
        this.clrTrsl = i;
    }

    public int getClrAya() {
        return this.clrAya;
    }

    public int getClrTrsl() {
        return this.clrTrsl;
    }

    public TextPaint getPaintAya() {
        return this.paintAya;
    }

    public TextPaint getPaintTranslationAya() {
        return this.paintTranslationAya;
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

    public float calculateOptimalTextSize(int i, int i2) {
        String str = this.translation;
        if (str == null) {
            return 0.0f;
        }
        return calculateOptimalTextSize(str, i, i2, this.paintTranslationAya);
    }

    private boolean isTrsl() {
        return (this.translation == null || getIpad_type() == IpadType.CASSET.ordinal() || getIpad_type() == IpadType.CASSET_IMG.ordinal() || getIpad_type() == IpadType.BOTTOM_RECT.ordinal() || getIpad_type() == IpadType.CASSET_IMG_BLUR.ordinal()) ? false : true;
    }

    public float calculateTextSize() {
        return calculateTextSize(this.txt, this.paintAya, (int) ((this.rect.width() / getFactor_scale()) * 0.85f), (int) (((isTrsl() ? this.rect.height() * 0.5f : this.rect.height()) / getFactor_scale()) * 0.85f));
    }

    public void setTextSize(float f) {
        this.paintAya.setTextSize(f);
    }

    public void setTextSizeInBoucle(float f) {
        this.paintAya.setTextSize(f);
        SpannableString spannableString = new SpannableString(this.txt);
        if (this.number != -1) {
            try {
                int length = this.txt.length();
                if (length > this.indexNumber) {
                    spannableString.setSpan(new EndOfAyaSpan(this.vectorDrawable, this.typefaceNumber, "" + this.number), this.indexNumber, length, 0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.staticLayout = StaticLayout.Builder.obtain(spannableString, 0, spannableString.length(), this.paintAya, this.viewWidth).setAlignment(Layout.Alignment.ALIGN_CENTER).setLineSpacing(0.0f, 1.0f).setIncludePad(false).build();
        this.y = this.rect.centerY() - (this.staticLayout.getHeight() * 0.5f);
        this.x = this.rect.centerX() - (this.staticLayout.getWidth() * 0.5f);
    }

    public void updateTranslation(float f) {
        if (isTrsl()) {
            this.paintTranslationAya.setTextSize(f);
            int i = (int) (this.viewWidth * 0.9f);
            this.staticLayoutTranslation = buildStaticLayout(this.translation, this.paintTranslationAya, i);
            float f2 = i * 0.17f;
            float width = this.staticLayout.getWidth() * 0.4f;
            int lineCount = this.staticLayoutTranslation.getLineCount() - 1;
            while (true) {
                if (this.translation.substring(this.staticLayoutTranslation.getLineStart(lineCount), this.staticLayoutTranslation.getLineEnd(lineCount)).trim().split("\\s+").length >= 2) {
                    break;
                }
                i = (int) (i - f2);
                if (i < width) {
                    this.paintTranslationAya.setTextSize(f);
                    this.staticLayoutTranslation = buildStaticLayout(this.translation, this.paintTranslationAya, this.viewWidth);
                    break;
                }
                this.staticLayoutTranslation = buildStaticLayout(this.translation, this.paintTranslationAya, i);
            }
            this.x_translation = this.rect.centerX() - (this.staticLayoutTranslation.getWidth() * 0.5f);
            this.y = this.rect.centerY() - ((this.staticLayout.getHeight() + this.staticLayoutTranslation.getHeight()) * 0.5f);
        }
    }

    public void updateTranslationSave(float f) {
        if (this.translation == null || getIpad_type() == IpadType.CASSET.ordinal() || getIpad_type() == IpadType.CASSET_IMG.ordinal() || getIpad_type() == IpadType.BOTTOM_RECT.ordinal() || getIpad_type() == IpadType.CASSET_IMG_BLUR.ordinal()) {
            return;
        }
        this.paintTranslationAya.setTextSize(f);
        this.staticLayoutTranslation = buildStaticLayout(this.translation, this.paintTranslationAya, (int) (this.viewWidth * 0.9f));
        if (this.staticLayout.getWidth() > this.rect.width()) {
            float width = this.staticLayout.getWidth() * 0.5f;
            this.rect.left = this.rect.centerX() - width;
            this.rect.right = this.rect.centerX() + width;
        }
        if (this.staticLayoutTranslation.getWidth() > this.rect.width()) {
            float width2 = this.staticLayoutTranslation.getWidth() * 0.5f;
            this.rect.left = this.rect.centerX() - width2;
            this.rect.right = this.rect.centerX() + width2;
        }
        if (this.staticLayout.getHeight() > this.rect.height()) {
            float height = this.staticLayout.getHeight() * 0.5f;
            this.rect.top = this.rect.centerY() - height;
            this.rect.bottom = this.rect.centerY() + height;
        }
        if (this.staticLayoutTranslation.getHeight() > this.rect.height()) {
            float height2 = this.staticLayoutTranslation.getHeight() * 0.5f;
            this.rect.top = this.rect.centerY() - height2;
            this.rect.bottom = this.rect.centerY() + height2;
        }
    }

    public void setupScale(float f, int i, int i2) {
        this.paintAya.setTextSize(f * i);
        SpannableString spannableString = new SpannableString(this.txt);
        boolean z = false;
        if (this.number != -1) {
            try {
                int length = this.txt.length();
                if (length > this.indexNumber) {
                    spannableString.setSpan(new EndOfAyaSpan(this.vectorDrawable, this.typefaceNumber, "" + this.number), this.indexNumber, length, 0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.viewWidth = (int) Math.max(this.rect.width(), Math.round(this.paintAya.measureText(spannableString.toString())));
        StaticLayout build = StaticLayout.Builder.obtain(spannableString, 0, spannableString.length(), this.paintAya, this.viewWidth).setAlignment(Layout.Alignment.ALIGN_CENTER).setLineSpacing(0.0f, 1.0f).setIncludePad(false).build();
        this.staticLayout = build;
        float width = build.getWidth();
        float f2 = width * 0.5f;
        float height = this.rect.height() * (width / this.rect.width()) * 0.5f;
        float centerY = this.rect.centerY();
        float centerX = this.rect.centerX();
        this.rect.set(centerX - f2, centerY - height, centerX + f2, centerY + height);
        if (this.translation != null && this.staticLayoutTranslation != null) {
            z = true;
        }
        if (z && this.txt.length() < 20) {
            this.max_h = Math.round(this.rect.height() * 0.55f);
            this.max_w = Math.round(this.rect.width() * 0.55f);
        } else if (z && i > i2) {
            this.max_h = Math.round(this.rect.height() * 0.64f);
            this.max_w = Math.round(this.rect.width() * 0.64f);
        } else if (z && i == i2) {
            this.max_h = Math.round(this.rect.height() * 0.8f);
            this.max_w = Math.round(this.rect.width() * 0.8f);
        } else {
            this.max_h = Math.round(this.rect.height() * 0.85f);
            this.max_w = Math.round(this.rect.width() * 0.85f);
        }
        this.x = this.rect.centerX() - (this.staticLayout.getWidth() * 0.5f);
        this.y = this.rect.centerY() - (this.staticLayout.getHeight() * 0.5f);
    }

    public void setupScaleSave(float f, int i) {
        float f2 = i;
        this.paintAya.setTextSize(f * f2);
        SpannableString spannableString = new SpannableString(this.txt);
        if (this.number != -1) {
            try {
                int length = this.txt.length();
                if (length > this.indexNumber) {
                    spannableString.setSpan(new EndOfAyaSpan(this.vectorDrawable, this.typefaceNumber, "" + this.number), this.indexNumber, length, 0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.viewWidth = (int) Math.max(this.rect.width(), Math.round(this.paintAya.measureText(spannableString.toString())));
        this.staticLayout = StaticLayout.Builder.obtain(spannableString, 0, spannableString.length(), this.paintAya, this.viewWidth).setAlignment(Layout.Alignment.ALIGN_CENTER).setLineSpacing(0.0f, 1.0f).setIncludePad(false).build();
        this.max_h = Math.round(this.rect.height() * 0.85f);
        this.max_w = Math.round(this.rect.width() * 0.85f);
        this.x = this.rect.centerX() - (this.staticLayout.getWidth() * 0.5f);
        if (this.translation != null) {
            updateTranslationSave(getFactorSizeTrl() * f2);
            if (this.staticLayoutTranslation != null) {
                this.x_translation = this.rect.centerX() - (this.staticLayoutTranslation.getWidth() * 0.5f);
                this.y = this.rect.centerY() - ((this.staticLayout.getHeight() + this.staticLayoutTranslation.getHeight()) * 0.5f);
                return;
            }
        }
        this.y = this.rect.centerY() - (this.staticLayout.getHeight() * 0.5f);
    }

    @Override // hazem.nurmontage.videoquran.model.EntityView
    public void scale(float f, int i, int i2) {
        setFactor_scale(f);
        float width = this.rect.width() * f;
        float height = this.rect.height() * f;
        float f2 = width * 0.5f;
        this.rect.left = this.rect.centerX() - f2;
        this.rect.right = this.rect.centerX() + f2;
        float f3 = height * 0.5f;
        this.rect.top = this.rect.centerY() - f3;
        this.rect.bottom = this.rect.centerY() + f3;
        boolean z = (this.translation == null || this.staticLayoutTranslation == null) ? false : true;
        this.viewWidth = (int) this.rect.width();
        this.paintAya.setTextSize(calculateTextSize());
        createStaticLayout();
        float f4 = i;
        setFcSize(this.paintAya.getTextSize() / f4);
        if (z) {
            setTls();
            setFactorSizeTrl(this.paintTranslationAya.getTextSize() / f4);
        }
        initPreset(getmPreset());
    }

    private void setTls() {
        updateTranslation(calculateOptimalTextSize((int) (getRect().width() * 0.85f), (int) (getRect().height() * 0.5f * 0.83f)));
    }

    public void applyAll(int i, RectF rectF, float f, float f2, QuranEntity quranEntity) {
        this.paintAya.setTextSize(f);
        SpannableString spannableString = new SpannableString(this.txt);
        boolean z = false;
        if (this.number != -1) {
            try {
                int length = this.txt.length();
                if (length > this.indexNumber) {
                    spannableString.setSpan(new EndOfAyaSpan(this.vectorDrawable, this.typefaceNumber, "" + this.number), this.indexNumber, length, 0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
        if (this.translation != null && this.staticLayoutTranslation != null) {
            z = true;
        }
        this.max_h = Math.round(this.rect.height() * 0.85f);
        this.max_w = Math.round(this.rect.width() * 0.85f);
        if (z) {
            this.paintTranslationAya.setTextSize(quranEntity.getPaintTranslationAya().getTextSize());
            this.staticLayoutTranslation = buildStaticLayout(this.translation, this.paintTranslationAya, (int) (this.viewWidth * 0.9f));
            setFactorSizeTrl(this.paintTranslationAya.getTextSize() / i);
        }
        initPreset(getmPreset());
    }

    public void applyAll(int i, RectF rectF, float f, float f2) {
        this.paintAya.setTextSize(f);
        SpannableString spannableString = new SpannableString(this.txt);
        boolean z = false;
        if (this.number != -1) {
            try {
                int length = this.txt.length();
                if (length > this.indexNumber) {
                    spannableString.setSpan(new EndOfAyaSpan(this.vectorDrawable, this.typefaceNumber, "" + this.number), this.indexNumber, length, 0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
        if (this.translation != null && this.staticLayoutTranslation != null) {
            z = true;
        }
        this.max_h = Math.round(this.rect.height() * 0.85f);
        this.max_w = Math.round(this.rect.width() * 0.85f);
        if (z) {
            updateTranslation(calculateOptimalTextSize((int) (getRect().width() * 0.85f), (int) (getRect().height() * 0.5f * 0.83f)));
            setFactorSizeTrl(this.paintTranslationAya.getTextSize() / i);
        }
        initPreset(getmPreset());
    }

    @Override // hazem.nurmontage.videoquran.model.EntityView
    public void postTranslate(float f, float f2) {
        this.rect.offset(f, f2);
        this.x = this.rect.centerX() - (this.staticLayout.getWidth() * 0.5f);
        if (isTrsl() && this.staticLayoutTranslation != null) {
            this.x_translation = this.rect.centerX() - (this.staticLayoutTranslation.getWidth() * 0.5f);
            this.y = this.rect.centerY() - ((this.staticLayout.getHeight() + this.staticLayoutTranslation.getHeight()) * 0.5f);
        } else {
            this.y = this.rect.centerY() - (this.staticLayout.getHeight() * 0.5f);
        }
    }

    public void setTranslate(float f, float f2) {
        float width = this.rect.width() * 0.5f;
        float height = this.rect.height() * 0.5f;
        this.rect.left = f - width;
        this.rect.right = f + width;
        this.rect.top = f2 - height;
        this.rect.bottom = f2 + height;
        this.x = this.rect.centerX() - (this.staticLayout.getWidth() * 0.5f);
        if (this.translation != null && this.staticLayoutTranslation != null) {
            this.x_translation = this.rect.centerX() - (this.staticLayoutTranslation.getWidth() * 0.5f);
            this.y = this.rect.centerY() - ((this.staticLayout.getHeight() + this.staticLayoutTranslation.getHeight()) * 0.5f);
        } else {
            this.y = this.rect.centerY() - (this.staticLayout.getHeight() * 0.5f);
        }
    }

    public float getWidth() {
        this.paintAya.setTextSize(3.0f);
        return this.paintAya.measureText(this.txt);
    }

    public float getTranslationWidth() {
        this.paintTranslationAya.setTextSize(3.0f);
        return this.paintTranslationAya.measureText(this.translation);
    }

    public void createStaticLayout() {
        float f;
        SpannableString spannableString = new SpannableString(this.txt);
        if (this.number != -1) {
            try {
                int length = this.txt.length();
                if (length > this.indexNumber) {
                    spannableString.setSpan(new EndOfAyaSpan(this.vectorDrawable, this.typefaceNumber, "" + this.number), this.indexNumber, length, 0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.staticLayout = StaticLayout.Builder.obtain(spannableString, 0, spannableString.length(), this.paintAya, this.viewWidth).setAlignment(Layout.Alignment.ALIGN_CENTER).setLineSpacing(0.0f, 1.0f).setIncludePad(false).build();
        if (this.translation != null && getIpad_type() != IpadType.BOTTOM_RECT.ordinal() && getIpad_type() != IpadType.CASSET.ordinal() && getIpad_type() != IpadType.CASSET_IMG.ordinal() && getIpad_type() != IpadType.CASSET_IMG_BLUR.ordinal()) {
            if (getIpad_type() == IpadType.IPAD_NEOMORPHIC.ordinal()) {
                f = 2.0f;
            } else {
                f = (getIpad_type() == IpadType.MASK_BRUSH.ordinal() || getIpad_type() == IpadType.BLUE_TYPE.ordinal() || getIpad_type() == IpadType.HEART.ordinal() || getIpad_type() == IpadType.BATTERY.ordinal() || getIpad_type() == IpadType.BLACK_LAYER.ordinal() || getIpad_type() == IpadType.GRADIENT.ordinal()) ? 0.45f : 1.2f;
            }
            this.x = this.rect.centerX() - (this.staticLayout.getWidth() * 0.5f);
            this.paintTranslationAya.setTextSize(calculateOptimalTextSize(this.translation, (int) (this.viewWidth * 0.85f), (int) (this.rect.height() - (this.staticLayout.getHeight() * f)), this.paintTranslationAya));
            int i = this.viewWidth;
            StaticLayout buildStaticLayout = buildStaticLayout(this.translation, this.paintTranslationAya, i);
            this.staticLayoutTranslation = buildStaticLayout;
            if (buildStaticLayout.getLineCount() == 2) {
                int lineCount = this.staticLayoutTranslation.getLineCount() - 1;
                while (this.translation.substring(this.staticLayoutTranslation.getLineStart(lineCount), this.staticLayoutTranslation.getLineEnd(lineCount)).trim().split("\\s+").length < 3 && i - 10 >= 10) {
                    this.staticLayoutTranslation = buildStaticLayout(this.translation, this.paintTranslationAya, i);
                }
                this.x_translation = this.rect.centerX() - (this.staticLayoutTranslation.getWidth() * 0.5f);
            } else {
                this.x_translation = this.x;
            }
            this.y = this.rect.centerY() - ((this.staticLayout.getHeight() + this.staticLayoutTranslation.getHeight()) * 0.5f);
        } else {
            this.y = this.rect.centerY() - (this.staticLayout.getHeight() * 0.5f);
        }
        this.x = this.rect.centerX() - (this.staticLayout.getWidth() * 0.5f);
    }

    private StaticLayout buildStaticLayout(String str, TextPaint textPaint, int i) {
        return StaticLayout.Builder.obtain(str, 0, str.length(), textPaint, i).setAlignment(Layout.Alignment.ALIGN_CENTER).setLineSpacing(0.0f, 1.0f).setIncludePad(false).build();
    }

    public void setStaticLayout() {
        SpannableString spannableString = new SpannableString(this.txt);
        if (this.number != -1) {
            try {
                int length = this.txt.length();
                if (length > this.indexNumber) {
                    spannableString.setSpan(new EndOfAyaSpan(this.vectorDrawable, this.typefaceNumber, "" + this.number), this.indexNumber, length, 0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.staticLayout = StaticLayout.Builder.obtain(spannableString, 0, spannableString.length(), this.paintAya, this.viewWidth).setAlignment(Layout.Alignment.ALIGN_CENTER).setLineSpacing(0.0f, 1.0f).setIncludePad(false).build();
        this.x = this.rect.centerX() - (this.staticLayout.getWidth() * 0.5f);
        if (this.translation != null && getIpad_type() != IpadType.BOTTOM_RECT.ordinal() && getIpad_type() != IpadType.CASSET.ordinal() && getIpad_type() != IpadType.CASSET_IMG.ordinal() && getIpad_type() != IpadType.CASSET_IMG_BLUR.ordinal()) {
            int i = this.viewWidth;
            StaticLayout buildStaticLayout = buildStaticLayout(this.translation, this.paintTranslationAya, i);
            this.staticLayoutTranslation = buildStaticLayout;
            if (buildStaticLayout.getLineCount() == 2) {
                int lineCount = this.staticLayoutTranslation.getLineCount() - 1;
                while (this.translation.substring(this.staticLayoutTranslation.getLineStart(lineCount), this.staticLayoutTranslation.getLineEnd(lineCount)).trim().split("\\s+").length < 3 && i - 10 >= 10) {
                    this.staticLayoutTranslation = buildStaticLayout(this.translation, this.paintTranslationAya, i);
                }
                this.x_translation = this.rect.centerX() - (this.staticLayoutTranslation.getWidth() * 0.5f);
            } else {
                this.x_translation = this.x;
            }
            this.y = this.rect.centerY() - ((this.staticLayout.getHeight() + this.staticLayoutTranslation.getHeight()) * 0.5f);
            return;
        }
        this.y = this.rect.centerY() - (this.staticLayout.getHeight() * 0.5f);
    }

    public void updateIconDraw() {
        SpannableString spannableString = new SpannableString(this.txt);
        if (this.number != -1) {
            try {
                int length = this.txt.length();
                if (length > this.indexNumber) {
                    spannableString.setSpan(new EndOfAyaSpan(this.vectorDrawable, this.typefaceNumber, "" + this.number), this.indexNumber, length, 0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.staticLayout = StaticLayout.Builder.obtain(spannableString, 0, spannableString.length(), this.paintAya, this.viewWidth).setAlignment(Layout.Alignment.ALIGN_CENTER).setLineSpacing(0.0f, 1.0f).setIncludePad(false).build();
    }

    private StaticLayout getStaticLayoutOutline() {
        SpannableString spannableString = new SpannableString(this.txt);
        if (this.number != -1) {
            try {
                int length = this.txt.length();
                if (length > this.indexNumber) {
                    spannableString.setSpan(new EndOfAyaSpan(this.vectorDrawable, this.typefaceNumber, "" + this.number), this.indexNumber, length, 0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return StaticLayout.Builder.obtain(spannableString, 0, spannableString.length(), this.paintAyaOutline, this.viewWidth).setAlignment(Layout.Alignment.ALIGN_CENTER).setLineSpacing(0.0f, 1.0f).setIncludePad(false).build();
    }

    public void updateStaticLayout() {
        SpannableString spannableString = this.spannableString;
        this.staticLayout = StaticLayout.Builder.obtain(spannableString, 0, spannableString.length(), this.paintAya, this.viewWidth).setAlignment(Layout.Alignment.ALIGN_CENTER).setLineSpacing(0.0f, 1.0f).setIncludePad(false).build();
        if (this.translation == null || getIpad_type() == IpadType.BOTTOM_RECT.ordinal() || getIpad_type() == IpadType.CASSET.ordinal() || getIpad_type() == IpadType.CASSET_IMG.ordinal() || getIpad_type() == IpadType.CASSET_IMG_BLUR.ordinal()) {
            return;
        }
        int i = this.viewWidth;
        StaticLayout buildStaticLayout = buildStaticLayout(this.translation, this.paintTranslationAya, i);
        this.staticLayoutTranslation = buildStaticLayout;
        if (buildStaticLayout.getLineCount() == 2) {
            int lineCount = this.staticLayoutTranslation.getLineCount() - 1;
            while (this.translation.substring(this.staticLayoutTranslation.getLineStart(lineCount), this.staticLayoutTranslation.getLineEnd(lineCount)).trim().split("\\s+").length < 3 && i - 10 >= 10) {
                this.staticLayoutTranslation = buildStaticLayout(this.translation, this.paintTranslationAya, i);
            }
            this.x_translation = this.rect.centerX() - (this.staticLayoutTranslation.getWidth() * 0.5f);
            return;
        }
        this.x_translation = this.x;
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
        this.paintTranslationAya.setAlpha(this.paintAya.getAlpha());
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
        this.paintTranslationAya.setAlpha(this.paintAya.getAlpha());
        this.paintAyaTrslOutline.setAlpha(this.paintAya.getAlpha());
        this.paintAyaOutline.setAlpha(this.paintAya.getAlpha());
    }

    public void setSlideX(float f) {
        WeakReference<BlurredImageView> weakReference;
        this.offsetX = f;
        this.paintAya.setAlpha(Math.round((1.0f - Math.abs(f)) * 255.0f));
        this.paintTranslationAya.setAlpha(this.paintAya.getAlpha());
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
        this.paintTranslationAya.setAlpha(this.paintAya.getAlpha());
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

    public QuranEntity(Context context, String str, String str2, String str3, String str4, RectF rectF, Typeface typeface, Typeface typeface2, int i, int i2, Typeface typeface3, int i3, int i4, String str5, boolean z, int i5) {
        this.nameFont = Common.FONT_QURAN;
        TextPaint textPaint = new TextPaint(1);
        this.paintAya = textPaint;
        this.paintAyaOutline = new TextPaint(1);
        this.paintAyaTrslOutline = new TextPaint(1);
        TextPaint textPaint2 = new TextPaint(1);
        this.paintTranslationAya = textPaint2;
        this.txt = str;
        this.complete_aya = str2;
        if (str2 == null || str2.isEmpty()) {
            this.complete_aya = str;
        }
        this.translation = str3;
        this.translation_complete = str4;
        this.nameFont = str5;
        this.indexNumber = i;
        this.number = i2;
        this.typefaceNumber = typeface3;
        this.rect = new RectF(rectF.left, rectF.top, rectF.right, rectF.bottom);
        setVisible(true);
        this.viewWidth = (int) rectF.width();
        textPaint.setTypeface(typeface);
        textPaint.setColor(i3);
        textPaint.setTextSize(0.05f);
        textPaint2.setTextSize(0.05f);
        textPaint2.setColor(i4);
        textPaint2.setTypeface(typeface2);
        setClrAya(i3);
        setClrTrsl(i4);
        this.vectorDrawable = (VectorDrawable) ContextCompat.getDrawable(context, i5);
        this.max_h = (int) (this.rect.height() * 0.85f);
        this.max_w = (int) (this.rect.width() * 0.85f);
    }

    public QuranEntity(Context context, int i, String str, String str2, String str3, String str4, RectF rectF, Typeface typeface, Typeface typeface2, int i2, int i3, Typeface typeface3, int i4, int i5, String str5, boolean z) {
        this.nameFont = Common.FONT_QURAN;
        TextPaint textPaint = new TextPaint(1);
        this.paintAya = textPaint;
        this.paintAyaOutline = new TextPaint(1);
        this.paintAyaTrslOutline = new TextPaint(1);
        TextPaint textPaint2 = new TextPaint(1);
        this.paintTranslationAya = textPaint2;
        this.txt = str;
        this.translation = str3;
        this.translation_complete = str4;
        this.complete_aya = str2;
        if (str2 == null || str2.isEmpty()) {
            this.complete_aya = str;
        }
        this.nameFont = str5;
        this.indexNumber = i2;
        this.number = i3;
        this.typefaceNumber = typeface3;
        this.rect = new RectF(rectF.left, rectF.top, rectF.right, rectF.bottom);
        setVisible(true);
        this.viewWidth = (int) rectF.width();
        textPaint.setTypeface(typeface);
        textPaint.setColor(i4);
        textPaint.setTextSize(0.05f);
        textPaint2.setTextSize(0.05f);
        textPaint2.setColor(i5);
        textPaint2.setTypeface(typeface2);
        setClrAya(i4);
        setClrTrsl(i5);
        this.vectorDrawable = (VectorDrawable) ContextCompat.getDrawable(context, i);
        this.max_h = (int) (this.rect.height() * 0.85f);
        this.max_w = (int) (this.rect.width() * 0.85f);
    }

    public QuranEntity(String str, String str2, String str3, String str4, RectF rectF, Typeface typeface, Typeface typeface2, int i, int i2, Typeface typeface3, int i3, int i4, String str5, float f, boolean z, VectorDrawable vectorDrawable) {
        this.nameFont = Common.FONT_QURAN;
        TextPaint textPaint = new TextPaint(1);
        this.paintAya = textPaint;
        this.paintAyaOutline = new TextPaint(1);
        this.paintAyaTrslOutline = new TextPaint(1);
        TextPaint textPaint2 = new TextPaint(1);
        this.paintTranslationAya = textPaint2;
        this.txt = str;
        this.translation = str3;
        this.translation_complete = str4;
        this.complete_aya = str2;
        this.nameFont = str5;
        this.indexNumber = i;
        this.number = i2;
        this.typefaceNumber = typeface3;
        this.rect = new RectF(rectF.left, rectF.top, rectF.right, rectF.bottom);
        setVisible(true);
        this.viewWidth = (int) rectF.width();
        textPaint.setTypeface(typeface);
        textPaint.setColor(i3);
        textPaint.setTextSize(f);
        textPaint2.setTextSize(0.05f);
        textPaint2.setTypeface(typeface2);
        textPaint2.setColor(i4);
        this.vectorDrawable = vectorDrawable;
        this.max_h = (int) (this.rect.height() * 0.85f);
        this.max_w = (int) (this.rect.width() * 0.85f);
    }

    public QuranEntity(String str, String str2, String str3, String str4, RectF rectF, Typeface typeface, Typeface typeface2, int i, int i2, Typeface typeface3, int i3, int i4, String str5, float f, float f2, boolean z, VectorDrawable vectorDrawable) {
        this.nameFont = Common.FONT_QURAN;
        TextPaint textPaint = new TextPaint(1);
        this.paintAya = textPaint;
        this.paintAyaOutline = new TextPaint(1);
        this.paintAyaTrslOutline = new TextPaint(1);
        TextPaint textPaint2 = new TextPaint(1);
        this.paintTranslationAya = textPaint2;
        this.txt = str;
        this.translation = str3;
        this.translation_complete = str4;
        this.complete_aya = str2;
        this.nameFont = str5;
        this.indexNumber = i;
        this.number = i2;
        this.typefaceNumber = typeface3;
        this.rect = new RectF(rectF.left, rectF.top, rectF.right, rectF.bottom);
        setVisible(true);
        this.viewWidth = (int) rectF.width();
        textPaint.setTypeface(typeface);
        textPaint.setColor(i3);
        textPaint.setTextSize(f);
        textPaint2.setTextSize(f2);
        textPaint2.setTypeface(typeface2);
        textPaint2.setColor(i4);
        this.vectorDrawable = vectorDrawable;
        this.max_h = (int) (this.rect.height() * 0.85f);
        this.max_w = (int) (this.rect.width() * 0.85f);
    }

    public int getIndexNumber() {
        return this.indexNumber;
    }

    public void setIndexNumber(int i) {
        this.indexNumber = i;
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int i) {
        this.number = i;
    }

    public void update(RectF rectF, int i, int i2) {
        this.rect = new RectF(rectF.left, rectF.top, rectF.right, rectF.bottom);
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

    public void setColorTranslation(int i) {
        setClrTrsl(i);
        TextPaint textPaint = this.paintTranslationAya;
        if (textPaint != null) {
            textPaint.setColor(i);
        }
    }

    public void draw(Canvas canvas) {
        if (this.staticLayout != null) {
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
            if (this.translation == null || this.staticLayoutTranslation == null || getIpad_type() == IpadType.BOTTOM_RECT.ordinal() || getIpad_type() == IpadType.CASSET.ordinal() || getIpad_type() == IpadType.CASSET_IMG.ordinal() || getIpad_type() == IpadType.CASSET_IMG_BLUR.ordinal()) {
                return;
            }
            canvas.save();
            canvas.translate(this.x_translation + (this.offsetX * this.staticLayout.getWidth()), this.y + (this.staticLayout.getHeight() * this.padding));
            float f2 = this.scaleX;
            canvas.scale(f2, f2);
            if (this.mPreset == AyaTextPreset.OUTLINE.ordinal() && this.staticLayoutTranslationOutline != null) {
                this.paintAyaTrslOutline.setTextSize(this.paintTranslationAya.getTextSize());
                this.staticLayoutTranslationOutline.draw(canvas);
            }
            this.staticLayoutTranslation.draw(canvas);
            canvas.restore();
        }
    }

    public void setupCanvasDraw(Canvas canvas) {
        if (this.translation != null && this.staticLayoutTranslation != null && getIpad_type() != IpadType.BOTTOM_RECT.ordinal() && getIpad_type() != IpadType.CASSET.ordinal() && getIpad_type() != IpadType.CASSET_IMG.ordinal() && getIpad_type() != IpadType.CASSET_IMG_BLUR.ordinal()) {
            this.y = ((canvas.getHeight() - this.staticLayout.getHeight()) - this.staticLayoutTranslation.getHeight()) * 0.5f;
            this.x_translation = (canvas.getWidth() - this.staticLayoutTranslation.getWidth()) * 0.5f;
        } else {
            this.y = (canvas.getHeight() - this.staticLayout.getHeight()) * 0.5f;
        }
        this.x = (canvas.getWidth() - this.staticLayout.getWidth()) * 0.5f;
        canvas.save();
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
            if (this.translation == null || this.staticLayoutTranslation == null || getIpad_type() == IpadType.BOTTOM_RECT.ordinal() || getIpad_type() == IpadType.CASSET.ordinal() || getIpad_type() == IpadType.CASSET_IMG.ordinal() || getIpad_type() == IpadType.CASSET_IMG_BLUR.ordinal()) {
                return;
            }
            canvas.save();
            canvas.translate((-this.x) + this.x_translation, this.staticLayout.getHeight() * this.padding);
            this.paintTranslationAya.setAlpha(i);
            this.staticLayoutTranslation.draw(canvas);
            canvas.restore();
        }
    }

    public void singleDraw(Canvas canvas, int i, float f) {
        if (this.staticLayout != null) {
            canvas.save();
            canvas.translate(this.staticLayout.getWidth() * f, 0.0f);
            this.paintAya.setAlpha(i);
            this.staticLayout.draw(canvas);
            canvas.restore();
            if (this.translation == null || this.staticLayoutTranslation == null || getIpad_type() == IpadType.BOTTOM_RECT.ordinal() || getIpad_type() == IpadType.CASSET.ordinal() || getIpad_type() == IpadType.CASSET_IMG.ordinal() || getIpad_type() == IpadType.CASSET_IMG_BLUR.ordinal()) {
                return;
            }
            canvas.save();
            canvas.translate((-this.x) + this.x_translation, this.staticLayout.getHeight() * this.padding);
            canvas.translate(f * this.staticLayout.getWidth(), 0.0f);
            this.paintTranslationAya.setAlpha(i);
            this.staticLayoutTranslation.draw(canvas);
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
            if (this.translation == null || this.staticLayoutTranslation == null || getIpad_type() == IpadType.BOTTOM_RECT.ordinal() || getIpad_type() == IpadType.CASSET.ordinal() || getIpad_type() == IpadType.CASSET_IMG.ordinal() || getIpad_type() == IpadType.CASSET_IMG_BLUR.ordinal()) {
                return;
            }
            canvas.save();
            canvas.translate((-this.x) + this.x_translation, this.staticLayout.getHeight() * this.padding);
            if (this.mPreset == AyaTextPreset.OUTLINE.ordinal() && this.staticLayoutTranslationOutline != null) {
                this.paintAyaTrslOutline.setTextSize(this.paintTranslationAya.getTextSize());
                this.staticLayoutTranslationOutline.draw(canvas);
            }
            this.staticLayoutTranslation.draw(canvas);
            canvas.restore();
        }
    }

    @Override // hazem.nurmontage.videoquran.model.EntityView
    public boolean isVisible() {
        return this.isVisible;
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

    public String getTxt() {
        return this.txt;
    }

    public String getComplete_aya() {
        return this.complete_aya;
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

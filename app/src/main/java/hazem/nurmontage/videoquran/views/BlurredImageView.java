package hazem.nurmontage.videoquran.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.text.Layout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import androidx.core.content.ContextCompat;
import androidx.core.view.GestureDetectorCompat;
import androidx.core.view.InputDeviceCompat;
import androidx.core.view.ViewCompat;
import androidx.media3.extractor.ts.PsExtractor;
import hazem.nurmontage.videoquran.R;
import hazem.nurmontage.videoquran.Utils.AspectRatioCalculator;
import hazem.nurmontage.videoquran.Utils.ColorSchemeGenerator;
import hazem.nurmontage.videoquran.Utils.ColorUtils;
import hazem.nurmontage.videoquran.Utils.CreateGradient;
import hazem.nurmontage.videoquran.Utils.FontUtils;
import hazem.nurmontage.videoquran.Utils.Utils;
import hazem.nurmontage.videoquran.Utils.UtilsFileLast;
import hazem.nurmontage.videoquran.common.Common;
import hazem.nurmontage.videoquran.constant.AyaTextPreset;
import hazem.nurmontage.videoquran.constant.IpadType;
import hazem.nurmontage.videoquran.entity_timeline.EntityQuranTimeline;
import hazem.nurmontage.videoquran.entity_timeline.EntityTrslTimeline;
import hazem.nurmontage.videoquran.constant.ResizeType;
import hazem.nurmontage.videoquran.constant.SurahNameStyle;
import hazem.nurmontage.videoquran.model.BismilahEntity;
import hazem.nurmontage.videoquran.model.EntitySelectTool;
import hazem.nurmontage.videoquran.model.EntityView;
import hazem.nurmontage.videoquran.model.Gradient;
import hazem.nurmontage.videoquran.model.QuranEntity;
import hazem.nurmontage.videoquran.model.SurahNameEntity;
import hazem.nurmontage.videoquran.model.Template;
import hazem.nurmontage.videoquran.model.TimeModel;
import hazem.nurmontage.videoquran.model.Transition;
import hazem.nurmontage.videoquran.model.TranslationQuranEntity;
import hazem.nurmontage.videoquran.multitouch.MoveGestureDetector;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import nl.dionsegijn.konfetti.core.Angle;

/* loaded from: classes2.dex */
public class BlurredImageView extends View implements View.OnTouchListener {
    private static final float SNAP_FORCE = 0.2f;
    private static final float SNAP_THRESHOLD = 30.0f;
    Paint backgroundPaint;
    private BismilahEntity bismilahEntity;
    private Bitmap bitmapBlured;
    private Bitmap bitmapNotBlur;
    private Bitmap bitmapOriginal;
    private Bitmap bitmapSquare;
    private float btmX;
    private float btmY;
    private int clr_aya;
    private int clr_trsl;
    private int color_bg_type_classic;
    private Gradient color_gradient;
    private int color_ipad;
    private int color_line_bg;
    private String currentTime;
    Paint darkShadowPaint;
    private EntityView entity_select;
    private long frameInterval;
    private GestureDetectorCompat gestureDetector;
    private final GestureDetector.SimpleOnGestureListener gestureListener;
    private Paint grayscalePaint;
    private IViewCallback iViewCallback;
    private RectF ipad_rect;
    private boolean isAnimWatermk;
    private boolean isDrawingSquareVideo;
    private boolean isGlass;
    private boolean isNotDraw;
    private boolean isOnScale;
    private boolean isPlaying;
    private boolean isPro;
    private boolean isRemoveWattermark;
    private boolean isSquare;
    private boolean isVideo;
    private boolean isWattermark;
    private float left_square;
    Paint lightShadowPaint;
    private Paint linePaint;
    private LinearGradient linearGradient_classic;
    private int mCanvas_height;
    private int mCanvas_width;
    private float mDrawingTranslationX;
    private float mDrawingTranslationY;
    private int mIpadType;
    private BismilahEntity mIsti3adhaEntity;
    private RectF mRectWattermark;
    private int mResizetype;
    private MoveGestureDetector moveGestureDetector;
    private float newLeft_txt;
    private Paint paint;
    private Paint paintClear;
    private Paint paintIpad;
    private Paint paintLecture;
    private TextPaint paintText;
    private Paint paintWattermark;
    private float prevDistance;
    private float progress;
    private final List<QuranEntity> quranEntities;
    private float radius_cursur;
    private int radius_square;
    private RectF rectFAya;
    private RectF rectFLecture;
    private RectF rectFProgress;
    private RectF rectFSurahName;
    private Rect rectSquare;
    private String remainingTime;
    private ScaleGestureDetector scaleGestureDetector;
    private ColorSchemeGenerator.Scheme scheme;
    private EntitySelectTool selectTool;
    private boolean showCenterLineX;
    private boolean showCenterLineY;
    private long startTime;
    private SurahNameEntity surahNameEntity;
    private float top_square;
    private final List<TranslationQuranEntity> translationEntities;
    private float txt_y;
    private float wmAlpha;
    private float wmScale;
    private float wmTranslateY;

    public interface IViewCallback {
        void onDrawFinish();

        void onEmtyClick();

        void onEndMove();

        void onEndScale();

        void onSelect(EntityView entityView);

        void onSquare();

        void onWattermark();
    }

    public void setEntity_select(EntityView entityView) {
        if (this.entity_select != entityView) {
            this.selectTool.reset();
        }
        this.entity_select = entityView;
    }

    public boolean isRemoveWattermark() {
        return this.isRemoveWattermark;
    }

    public void setRemoveWattermark(boolean z) {
        this.isRemoveWattermark = z;
    }

    public void setBitmapNotBlur(Bitmap bitmap) {
        this.bitmapNotBlur = bitmap;
    }

    public Bitmap getBitmapNotBlur() {
        return this.bitmapNotBlur;
    }

    public boolean isVideo() {
        return this.isVideo;
    }

    public void setVideo(boolean z) {
        this.isVideo = z;
    }

    public void setDrawingSquareVideo(boolean z) {
        this.isDrawingSquareVideo = z;
    }

    public boolean isDrawingSquareVideo() {
        return this.isDrawingSquareVideo;
    }

    public void setPlaying(boolean z) {
        this.isPlaying = z;
    }

    public boolean isPlaying() {
        return this.isPlaying;
    }

    public void setPro(boolean z) {
        this.isPro = z;
    }

    public boolean isPro() {
        return this.isPro;
    }

    public void setBitmapOriginal(Bitmap bitmap) {
        this.bitmapOriginal = bitmap;
    }

    public Bitmap getBitmapOriginal() {
        return this.bitmapOriginal;
    }

    public void setGlass(boolean z) {
        this.isGlass = z;
    }

    public boolean isGlass() {
        return this.isGlass;
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (this.selectTool == null) {
            this.selectTool = new EntitySelectTool(i, getContext());
        }
    }

    public int getColor_ipad() {
        return this.color_ipad;
    }

    public void setResizetype(int i) {
        this.mResizetype = i;
    }

    public int getmIpadType() {
        return this.mIpadType;
    }

    public void setIpad_rect(RectF rectF) {
        this.ipad_rect = rectF;
    }

    public void setRectSquare(Rect rect) {
        this.rectSquare = rect;
    }

    public float getBtmX() {
        return this.btmX;
    }

    public float getBtmY() {
        return this.btmY;
    }

    public float getmDrawingTranslationY() {
        return this.mDrawingTranslationY;
    }

    public float getmDrawingTranslationX() {
        return this.mDrawingTranslationX;
    }

    public int getmCanvas_height() {
        return this.mCanvas_height;
    }

    public int getmCanvas_width() {
        return this.mCanvas_width;
    }

    public void initCanvasDimension(int i, int i2, int i3) {
        if (i3 == ResizeType.SOCIAL_STORY.ordinal()) {
            this.mCanvas_height = i2;
            this.mCanvas_width = AspectRatioCalculator.calculateWidth(i2);
        } else if (i3 == ResizeType.SQUARE.ordinal()) {
            int min = Math.min(i, i2);
            this.mCanvas_width = min;
            this.mCanvas_height = min;
        } else {
            this.mCanvas_width = i;
            this.mCanvas_height = AspectRatioCalculator.calculateHeight_Youtube(i);
        }
    }

    public int getW() {
        return (getWidth() - getPaddingStart()) - getPaddingEnd();
    }

    public int getH() {
        return (getHeight() - getPaddingTop()) - getPaddingBottom();
    }

    public void updatePosCanvas(Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }
        float width = (getWidth() - getPaddingStart()) - getPaddingEnd();
        float height = (getHeight() - getPaddingTop()) - getPaddingBottom();
        this.mDrawingTranslationX = (width - this.mCanvas_width) / 2.0f;
        this.mDrawingTranslationY = (height - this.mCanvas_height) / 2.0f;
        this.btmX = ((width - bitmap.getWidth()) / 2.0f) - this.mDrawingTranslationX;
        this.btmY = ((height - bitmap.getHeight()) / 2.0f) - this.mDrawingTranslationY;
    }

    public void updatePosCanvas(int i, int i2, Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }
        this.mDrawingTranslationX = (i - this.mCanvas_width) / 2.0f;
        this.mDrawingTranslationY = (i2 - this.mCanvas_height) / 2.0f;
        this.btmX = ((i - bitmap.getWidth()) / 2.0f) - this.mDrawingTranslationX;
        this.btmY = ((i2 - bitmap.getHeight()) / 2.0f) - this.mDrawingTranslationY;
    }

    public float getProgress() {
        return this.progress;
    }

    public void addEntity(QuranEntity quranEntity) {
        this.quranEntities.add(quranEntity);
        quranEntity.setIndex(this.quranEntities.size() - 1);
    }

    public void addEntity(TranslationQuranEntity translationQuranEntity) {
        this.translationEntities.add(translationQuranEntity);
        translationQuranEntity.setIndex(this.translationEntities.size() - 1);
    }

    public List<QuranEntity> getQuranEntities() {
        return this.quranEntities;
    }

    public Paint getPaintLecture() {
        return this.paintLecture;
    }

    public void addEntity(QuranEntity quranEntity, int i) {
        if (i < this.quranEntities.size()) {
            this.quranEntities.add(i, quranEntity);
        } else {
            this.quranEntities.add(quranEntity);
        }
        quranEntity.setIndex(i);
    }

    public void addEntity(TranslationQuranEntity translationQuranEntity, int i) {
        if (i < this.translationEntities.size()) {
            this.translationEntities.add(i, translationQuranEntity);
        } else {
            this.translationEntities.add(translationQuranEntity);
        }
        translationQuranEntity.setIndex(i);
    }

    public Bitmap getBitmapSquare() {
        return this.bitmapSquare;
    }

    public void setClr_aya(int i) {
        this.clr_aya = i;
    }

    public void setClr_trsl(int i) {
        this.clr_trsl = i;
    }

    public int getClr_aya() {
        return this.clr_aya;
    }

    public int getClr_trsl() {
        return this.clr_trsl;
    }

    public Gradient getColor_gradient() {
        return this.color_gradient;
    }

    public void setColor_gradient(Gradient gradient) {
        this.color_gradient = gradient;
    }

    public int colorIpad() {
        return this.color_ipad;
    }

    public void changeColorIpad() {
        if (getColor_gradient() != null) {
            setColorIpad(getColor_gradient());
        } else {
            setColorIpad(colorIpad());
        }
    }

    public void setColorIpad(int i) {
        setColor_gradient(null);
        this.paintIpad.setShader(null);
        this.color_ipad = i;
        if (this.mIpadType == IpadType.IPAD_CLASSIC.ordinal()) {
            this.color_bg_type_classic = ColorUtils.lightenColor(i, 0.4f);
            this.paintIpad.setColor(ColorUtils.darkenColor(i, 0.2f));
        } else {
            this.paintIpad.setColor(i);
        }
        if (this.mIpadType == IpadType.BORDER.ordinal()) {
            this.color_line_bg = ColorUtils.darkenColor(i, 0.4f);
            this.paintLecture.setColor(i);
        } else if (this.mIpadType == IpadType.BLUE_TYPE.ordinal()) {
            this.paintLecture.setColor(ColorUtils.convertToEnergyColor(i));
            this.color_line_bg = ColorUtils.darkenColor(this.paintLecture.getColor(), 0.7f);
        } else if (this.mIpadType == IpadType.CASSET.ordinal() || this.mIpadType == IpadType.CASSET_IMG.ordinal() || this.mIpadType == IpadType.CASSET_IMG_BLUR.ordinal()) {
            ColorSchemeGenerator.Scheme generateScheme = ColorSchemeGenerator.generateScheme(i);
            this.scheme = generateScheme;
            if (ColorUtils.isColorDark(generateScheme.label)) {
                this.paintLecture.setColor(-1);
            } else {
                this.paintLecture.setColor(ViewCompat.MEASURED_STATE_MASK);
            }
            this.color_line_bg = ColorUtils.darkenColor(this.paintLecture.getColor(), 0.7f);
        } else {
            this.color_line_bg = ColorUtils.darkenColor(i, 0.4f);
            this.paintIpad.setAlpha(190);
            if (ColorUtils.isColorDark(this.paintIpad.getColor())) {
                this.paintLecture.setColor(-1);
            } else {
                this.paintLecture.setColor(ViewCompat.MEASURED_STATE_MASK);
            }
        }
        this.paintText.setColor(this.paintLecture.getColor());
    }

    public void setColorIpad(Gradient gradient) {
        setColor_gradient(gradient);
        int color = gradient.getColor();
        if (this.mIpadType == IpadType.IPAD_CLASSIC.ordinal()) {
            this.paintIpad.setShader(null);
            this.linearGradient_classic = CreateGradient.createLinearGradientWithAngle(this.ipad_rect, gradient.getAngle(), new int[]{ColorUtils.lightenColor(gradient.getColor(), 0.4f), ColorUtils.lightenColor(gradient.getSecond(), 0.4f), ColorUtils.lightenColor(gradient.getThree(), 0.4f)}, new float[]{0.0f, 0.7f, 1.0f});
            this.paintIpad.setColor(ColorUtils.darkenColor(gradient.getSecond(), 0.2f));
        } else {
            LinearGradient createLinearGradientWithAngle = CreateGradient.createLinearGradientWithAngle(this.ipad_rect, gradient.getAngle(), new int[]{gradient.getColor(), gradient.getSecond(), gradient.getThree()}, new float[]{0.0f, 0.7f, 1.0f});
            this.linearGradient_classic = createLinearGradientWithAngle;
            this.paintIpad.setShader(createLinearGradientWithAngle);
            this.paintIpad.setColor(color);
        }
        this.color_line_bg = ColorUtils.darkenColor(color, 0.4f);
        if (this.mIpadType == IpadType.BORDER.ordinal()) {
            this.paintLecture.setColor(color);
        } else if (this.mIpadType == IpadType.BLUE_TYPE.ordinal()) {
            this.paintLecture.setColor(ColorUtils.lightenColor(color, 0.7f));
        } else if (this.mIpadType == IpadType.CASSET.ordinal() || this.mIpadType == IpadType.CASSET_IMG.ordinal() || this.mIpadType == IpadType.CASSET_IMG_BLUR.ordinal()) {
            ColorSchemeGenerator.Scheme generateScheme = ColorSchemeGenerator.generateScheme(color, gradient.getAngle());
            this.scheme = generateScheme;
            if (ColorUtils.isColorDark(generateScheme.label)) {
                this.paintLecture.setColor(-1);
            } else {
                this.paintLecture.setColor(ViewCompat.MEASURED_STATE_MASK);
            }
        } else {
            this.paintIpad.setAlpha(190);
            if (ColorUtils.isColorDark(this.paintIpad.getColor())) {
                this.paintLecture.setColor(-1);
            } else {
                this.paintLecture.setColor(ViewCompat.MEASURED_STATE_MASK);
            }
        }
        this.paintText.setColor(this.paintLecture.getColor());
    }

    public void setIcon(String str, VectorDrawable vectorDrawable) {
        for (QuranEntity quranEntity : this.quranEntities) {
            if (quranEntity.getIcon() != null && !quranEntity.getIcon().equals(str) && quranEntity.getNumber() != -1) {
                quranEntity.setVectorDrawable(vectorDrawable);
                quranEntity.setIcon(str);
                quranEntity.updateIconDraw();
            }
        }
        updateSizeAya();
        invalidate();
    }

    public void setTypeface(Typeface typeface, String str) {
        EntityView entityView = this.entity_select;
        if (entityView instanceof QuranEntity) {
            for (QuranEntity quranEntity : this.quranEntities) {
                if (quranEntity.getNameFont() != null && !quranEntity.getNameFont().equals(str)) {
                    quranEntity.setTypeface(typeface, str);
                }
            }
            updateSizeAyaResize();
        } else if (entityView instanceof TranslationQuranEntity) {
            for (TranslationQuranEntity translationQuranEntity : this.translationEntities) {
                if (translationQuranEntity.getNameFont() != null && !translationQuranEntity.getNameFont().equals(str)) {
                    translationQuranEntity.setTypeface(typeface, str);
                }
            }
            updateSizeTrslAyaResize();
        }
        invalidate();
    }

    public void setPreset(AyaTextPreset ayaTextPreset) {
        Iterator<QuranEntity> it = this.quranEntities.iterator();
        while (it.hasNext()) {
            it.next().setPreset(ayaTextPreset);
        }
        BismilahEntity bismilahEntity = this.mIsti3adhaEntity;
        if (bismilahEntity != null && bismilahEntity.getBismilahTimeline().visible()) {
            this.mIsti3adhaEntity.setPreset(ayaTextPreset);
        }
        BismilahEntity bismilahEntity2 = this.bismilahEntity;
        if (bismilahEntity2 != null && bismilahEntity2.getBismilahTimeline().visible()) {
            this.bismilahEntity.setPreset(ayaTextPreset);
        }
        invalidate();
    }

    public void setTrslPreset(AyaTextPreset ayaTextPreset) {
        Iterator<TranslationQuranEntity> it = this.translationEntities.iterator();
        while (it.hasNext()) {
            it.next().setPreset(ayaTextPreset);
        }
        invalidate();
    }

    public void setColorAya(int i) {
        setClr_aya(i);
        Iterator<QuranEntity> it = this.quranEntities.iterator();
        while (it.hasNext()) {
            it.next().setColor(i);
        }
        BismilahEntity bismilahEntity = this.mIsti3adhaEntity;
        if (bismilahEntity != null && bismilahEntity.getBismilahTimeline().visible()) {
            this.mIsti3adhaEntity.setColor(i);
        }
        BismilahEntity bismilahEntity2 = this.bismilahEntity;
        if (bismilahEntity2 != null && bismilahEntity2.getBismilahTimeline().visible()) {
            this.bismilahEntity.setColor(i);
        }
        invalidate();
    }

    public void setColorTrsl(int i) {
        setClr_trsl(i);
        Iterator<TranslationQuranEntity> it = this.translationEntities.iterator();
        while (it.hasNext()) {
            it.next().setColor(i);
        }
        invalidate();
    }

    public void applyAll(float f, RectF rectF, int i, int i2) {
        EntityView entityView = this.entity_select;
        if (entityView == null) {
            return;
        }
        if (entityView instanceof QuranEntity) {
            QuranEntity quranEntity = (QuranEntity) entityView;
            for (QuranEntity quranEntity2 : this.quranEntities) {
                if (quranEntity2 != quranEntity) {
                    quranEntity2.applyAll(getmCanvas_width(), rectF, quranEntity.getPaintAya().getTextSize(), quranEntity.getFactorSize());
                }
            }
            invalidate();
            return;
        }
        if (entityView instanceof TranslationQuranEntity) {
            TranslationQuranEntity translationQuranEntity = (TranslationQuranEntity) entityView;
            for (TranslationQuranEntity translationQuranEntity2 : this.translationEntities) {
                if (translationQuranEntity2 != translationQuranEntity) {
                    translationQuranEntity2.applyAll(getmCanvas_width(), rectF, translationQuranEntity.getPaintAya().getTextSize(), translationQuranEntity.getFactorSize());
                }
            }
            invalidate();
        }
    }

    public void setCurrentTime(String str, String str2) {
        this.currentTime = str;
        this.remainingTime = "-" + str2;
    }

    public BlurredImageView(Context context) {
        super(context);
        this.mIpadType = IpadType.IPAD.ordinal();
        this.color_ipad = -1;
        this.quranEntities = new ArrayList();
        this.translationEntities = new ArrayList();
        this.paint = new Paint(1);
        this.paintClear = new Paint();
        this.currentTime = "0:00";
        this.remainingTime = "0:15";
        this.wmAlpha = 1.0f;
        this.wmScale = 1.0f;
        this.wmTranslateY = 0.0f;
        this.startTime = -1L;
        this.gestureListener = new GestureDetector.SimpleOnGestureListener() { // from class: hazem.nurmontage.videoquran.views.BlurredImageView.1
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onDown(MotionEvent motionEvent) {
                if (!BlurredImageView.this.isPro && BlurredImageView.this.mRectWattermark != null && BlurredImageView.this.mRectWattermark.contains(motionEvent.getX(), motionEvent.getY())) {
                    BlurredImageView.this.isWattermark = true;
                }
                if (BlurredImageView.this.entity_select != null && BlurredImageView.this.entity_select.isVisible() && !BlurredImageView.this.isWattermark) {
                    if (BlurredImageView.this.selectTool.isApply(BlurredImageView.this.entity_select, motionEvent.getX(), motionEvent.getY())) {
                        if (BlurredImageView.this.selectTool.isApply_Move()) {
                            BlurredImageView.this.iViewCallback.onEndMove();
                        }
                        if (BlurredImageView.this.selectTool.isApply_Scale()) {
                            BlurredImageView.this.iViewCallback.onEndScale();
                        }
                        BlurredImageView.this.selectTool.setClick_apply(true);
                        BlurredImageView.this.selectTool.reset();
                    } else {
                        BlurredImageView.this.selectTool.isScale(BlurredImageView.this.entity_select, motionEvent.getX(), motionEvent.getY());
                    }
                    if (BlurredImageView.this.selectTool.isApply_Scale()) {
                        BlurredImageView.this.selectTool.setOnProgress(true);
                        BlurredImageView blurredImageView = BlurredImageView.this;
                        blurredImageView.prevDistance = blurredImageView.distanceToCenter(motionEvent.getX(), motionEvent.getY());
                    }
                }
                return true;
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                if (BlurredImageView.this.entity_select != null && BlurredImageView.this.selectTool.isClick_apply()) {
                    BlurredImageView.this.selectTool.setClick_apply(false);
                    BlurredImageView.this.invalidate();
                    return true;
                }
                if (!BlurredImageView.this.isWattermark) {
                    BlurredImageView.this.updateSelectionOnTap(motionEvent);
                }
                BlurredImageView.this.isOnScale = false;
                if (BlurredImageView.this.iViewCallback != null) {
                    if (BlurredImageView.this.entity_select == null) {
                        if (BlurredImageView.this.isWattermark) {
                            BlurredImageView.this.iViewCallback.onWattermark();
                        } else if (BlurredImageView.this.isSquare) {
                            BlurredImageView.this.iViewCallback.onSquare();
                        } else {
                            BlurredImageView.this.iViewCallback.onEmtyClick();
                        }
                    } else if (BlurredImageView.this.selectTool != null && BlurredImageView.this.selectTool.isApply_Move() && (((BlurredImageView.this.entity_select instanceof QuranEntity) || (BlurredImageView.this.entity_select instanceof TranslationQuranEntity)) && !BlurredImageView.this.selectTool.isApply_all())) {
                        BlurredImageView.this.selectTool.setApply_all(true);
                        BlurredImageView.this.invalidate();
                    }
                    BlurredImageView blurredImageView = BlurredImageView.this;
                    blurredImageView.isWattermark = false;
                    blurredImageView.isSquare = false;
                }
                return super.onSingleTapUp(motionEvent);
            }
        };
        this.showCenterLineX = false;
        this.showCenterLineY = false;
        this.prevDistance = -1.0f;
        init();
    }

    public BlurredImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIpadType = IpadType.IPAD.ordinal();
        this.color_ipad = -1;
        this.quranEntities = new ArrayList();
        this.translationEntities = new ArrayList();
        this.paint = new Paint(1);
        this.paintClear = new Paint();
        this.currentTime = "0:00";
        this.remainingTime = "0:15";
        this.wmAlpha = 1.0f;
        this.wmScale = 1.0f;
        this.wmTranslateY = 0.0f;
        this.startTime = -1L;
        this.gestureListener = new GestureDetector.SimpleOnGestureListener() { // from class: hazem.nurmontage.videoquran.views.BlurredImageView.1
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onDown(MotionEvent motionEvent) {
                if (!BlurredImageView.this.isPro && BlurredImageView.this.mRectWattermark != null && BlurredImageView.this.mRectWattermark.contains(motionEvent.getX(), motionEvent.getY())) {
                    BlurredImageView.this.isWattermark = true;
                }
                if (BlurredImageView.this.entity_select != null && BlurredImageView.this.entity_select.isVisible() && !BlurredImageView.this.isWattermark) {
                    if (BlurredImageView.this.selectTool.isApply(BlurredImageView.this.entity_select, motionEvent.getX(), motionEvent.getY())) {
                        if (BlurredImageView.this.selectTool.isApply_Move()) {
                            BlurredImageView.this.iViewCallback.onEndMove();
                        }
                        if (BlurredImageView.this.selectTool.isApply_Scale()) {
                            BlurredImageView.this.iViewCallback.onEndScale();
                        }
                        BlurredImageView.this.selectTool.setClick_apply(true);
                        BlurredImageView.this.selectTool.reset();
                    } else {
                        BlurredImageView.this.selectTool.isScale(BlurredImageView.this.entity_select, motionEvent.getX(), motionEvent.getY());
                    }
                    if (BlurredImageView.this.selectTool.isApply_Scale()) {
                        BlurredImageView.this.selectTool.setOnProgress(true);
                        BlurredImageView blurredImageView = BlurredImageView.this;
                        blurredImageView.prevDistance = blurredImageView.distanceToCenter(motionEvent.getX(), motionEvent.getY());
                    }
                }
                return true;
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                if (BlurredImageView.this.entity_select != null && BlurredImageView.this.selectTool.isClick_apply()) {
                    BlurredImageView.this.selectTool.setClick_apply(false);
                    BlurredImageView.this.invalidate();
                    return true;
                }
                if (!BlurredImageView.this.isWattermark) {
                    BlurredImageView.this.updateSelectionOnTap(motionEvent);
                }
                BlurredImageView.this.isOnScale = false;
                if (BlurredImageView.this.iViewCallback != null) {
                    if (BlurredImageView.this.entity_select == null) {
                        if (BlurredImageView.this.isWattermark) {
                            BlurredImageView.this.iViewCallback.onWattermark();
                        } else if (BlurredImageView.this.isSquare) {
                            BlurredImageView.this.iViewCallback.onSquare();
                        } else {
                            BlurredImageView.this.iViewCallback.onEmtyClick();
                        }
                    } else if (BlurredImageView.this.selectTool != null && BlurredImageView.this.selectTool.isApply_Move() && (((BlurredImageView.this.entity_select instanceof QuranEntity) || (BlurredImageView.this.entity_select instanceof TranslationQuranEntity)) && !BlurredImageView.this.selectTool.isApply_all())) {
                        BlurredImageView.this.selectTool.setApply_all(true);
                        BlurredImageView.this.invalidate();
                    }
                    BlurredImageView blurredImageView = BlurredImageView.this;
                    blurredImageView.isWattermark = false;
                    blurredImageView.isSquare = false;
                }
                return super.onSingleTapUp(motionEvent);
            }
        };
        this.showCenterLineX = false;
        this.showCenterLineY = false;
        this.prevDistance = -1.0f;
        init();
    }

    public BlurredImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mIpadType = IpadType.IPAD.ordinal();
        this.color_ipad = -1;
        this.quranEntities = new ArrayList();
        this.translationEntities = new ArrayList();
        this.paint = new Paint(1);
        this.paintClear = new Paint();
        this.currentTime = "0:00";
        this.remainingTime = "0:15";
        this.wmAlpha = 1.0f;
        this.wmScale = 1.0f;
        this.wmTranslateY = 0.0f;
        this.startTime = -1L;
        this.gestureListener = new GestureDetector.SimpleOnGestureListener() { // from class: hazem.nurmontage.videoquran.views.BlurredImageView.1
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onDown(MotionEvent motionEvent) {
                if (!BlurredImageView.this.isPro && BlurredImageView.this.mRectWattermark != null && BlurredImageView.this.mRectWattermark.contains(motionEvent.getX(), motionEvent.getY())) {
                    BlurredImageView.this.isWattermark = true;
                }
                if (BlurredImageView.this.entity_select != null && BlurredImageView.this.entity_select.isVisible() && !BlurredImageView.this.isWattermark) {
                    if (BlurredImageView.this.selectTool.isApply(BlurredImageView.this.entity_select, motionEvent.getX(), motionEvent.getY())) {
                        if (BlurredImageView.this.selectTool.isApply_Move()) {
                            BlurredImageView.this.iViewCallback.onEndMove();
                        }
                        if (BlurredImageView.this.selectTool.isApply_Scale()) {
                            BlurredImageView.this.iViewCallback.onEndScale();
                        }
                        BlurredImageView.this.selectTool.setClick_apply(true);
                        BlurredImageView.this.selectTool.reset();
                    } else {
                        BlurredImageView.this.selectTool.isScale(BlurredImageView.this.entity_select, motionEvent.getX(), motionEvent.getY());
                    }
                    if (BlurredImageView.this.selectTool.isApply_Scale()) {
                        BlurredImageView.this.selectTool.setOnProgress(true);
                        BlurredImageView blurredImageView = BlurredImageView.this;
                        blurredImageView.prevDistance = blurredImageView.distanceToCenter(motionEvent.getX(), motionEvent.getY());
                    }
                }
                return true;
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                if (BlurredImageView.this.entity_select != null && BlurredImageView.this.selectTool.isClick_apply()) {
                    BlurredImageView.this.selectTool.setClick_apply(false);
                    BlurredImageView.this.invalidate();
                    return true;
                }
                if (!BlurredImageView.this.isWattermark) {
                    BlurredImageView.this.updateSelectionOnTap(motionEvent);
                }
                BlurredImageView.this.isOnScale = false;
                if (BlurredImageView.this.iViewCallback != null) {
                    if (BlurredImageView.this.entity_select == null) {
                        if (BlurredImageView.this.isWattermark) {
                            BlurredImageView.this.iViewCallback.onWattermark();
                        } else if (BlurredImageView.this.isSquare) {
                            BlurredImageView.this.iViewCallback.onSquare();
                        } else {
                            BlurredImageView.this.iViewCallback.onEmtyClick();
                        }
                    } else if (BlurredImageView.this.selectTool != null && BlurredImageView.this.selectTool.isApply_Move() && (((BlurredImageView.this.entity_select instanceof QuranEntity) || (BlurredImageView.this.entity_select instanceof TranslationQuranEntity)) && !BlurredImageView.this.selectTool.isApply_all())) {
                        BlurredImageView.this.selectTool.setApply_all(true);
                        BlurredImageView.this.invalidate();
                    }
                    BlurredImageView blurredImageView = BlurredImageView.this;
                    blurredImageView.isWattermark = false;
                    blurredImageView.isSquare = false;
                }
                return super.onSingleTapUp(motionEvent);
            }
        };
        this.showCenterLineX = false;
        this.showCenterLineY = false;
        this.prevDistance = -1.0f;
        init();
    }

    private void init() {
        setOnTouchListener(this);
        this.moveGestureDetector = new MoveGestureDetector(getContext(), new MoveListener());
        this.scaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
        this.gestureDetector = new GestureDetectorCompat(getContext(), this.gestureListener);
        this.grayscalePaint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0.0f);
        this.grayscalePaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        Paint paint = new Paint(1);
        this.paintWattermark = paint;
        paint.setColor(ViewCompat.MEASURED_STATE_MASK);
        this.paintWattermark.setAlpha(25);
        this.paintWattermark.setTypeface(UtilsFileLast.loadFontFromAsset(getContext(), "fonts/ReadexPro_Medium.ttf"));
        this.paintWattermark.setFakeBoldText(true);
        Paint paint2 = new Paint();
        this.linePaint = paint2;
        paint2.setAntiAlias(true);
        this.paintLecture = new Paint(1);
        this.paintIpad = new Paint(1);
        this.paintText = new TextPaint(1);
        Paint paint3 = new Paint();
        this.paintClear = paint3;
        paint3.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        this.paintText.setTypeface(UtilsFileLast.loadFontFromAsset(getContext(), "fonts/arabic/NotoNaskhArabic.ttf"));
    }

    public int getRadius_square() {
        return this.radius_square;
    }

    public void setRadius_square(int i) {
        this.radius_square = i;
    }

    public void setBitmapBlured(Bitmap bitmap) {
        this.bitmapBlured = bitmap;
    }

    public void setBitmapSquare(Bitmap bitmap) {
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        this.bitmapSquare = bitmap;
    }

    public void setBitmap(Bitmap bitmap, Bitmap bitmap2, int i, int i2, int i3, Rect rect) {
        this.bitmapBlured = bitmap;
        if (bitmap2 != null) {
            this.bitmapSquare = bitmap2;
        }
        this.rectSquare = rect;
        this.mIpadType = i2;
        if (i != -1) {
            setColorIpad(i);
        } else if (bitmap2 != null) {
            setColorIpad(ColorUtils.getAverageColor(bitmap2));
        }
        this.mResizetype = i3;
        if (this.mIpadType == IpadType.BOTTOM_RECT.ordinal()) {
            this.paintText.setTextSize(Math.min(this.ipad_rect.width(), this.ipad_rect.height()) * 0.07f);
        } else if (this.mIpadType == IpadType.BORDER.ordinal()) {
            this.paintText.setTextSize(Math.min(this.ipad_rect.width(), this.ipad_rect.height()) * 0.027f);
        } else {
            this.paintText.setTextSize(this.ipad_rect.width() * 0.0388f);
        }
        createRect();
    }

    public void updateBitmap(Bitmap bitmap, Bitmap bitmap2, int i, int i2, int i3, Rect rect) {
        this.bitmapBlured = bitmap;
        if (bitmap2 != null) {
            this.bitmapSquare = bitmap2;
        }
        this.rectSquare = rect;
        this.mIpadType = i2;
        if (i != -1) {
            setColorIpad(i);
        } else if (bitmap2 != null) {
            setColorIpad(ColorUtils.getAverageColor(bitmap2));
        }
        this.mResizetype = i3;
        if (this.mIpadType == IpadType.BOTTOM_RECT.ordinal()) {
            this.paintText.setTextSize(Math.min(this.ipad_rect.width(), this.ipad_rect.height()) * 0.07f);
        } else if (this.mIpadType == IpadType.BORDER.ordinal()) {
            this.paintText.setTextSize(Math.min(this.ipad_rect.width(), this.ipad_rect.height()) * 0.027f);
        } else {
            this.paintText.setTextSize(this.ipad_rect.width() * 0.0388f);
        }
    }

    public void setBitmap(Bitmap bitmap, Bitmap bitmap2, Gradient gradient, int i, int i2, Rect rect) {
        this.bitmapBlured = bitmap;
        if (bitmap2 != null) {
            this.bitmapSquare = bitmap2;
        }
        this.rectSquare = rect;
        this.mIpadType = i;
        setColorIpad(gradient);
        this.mResizetype = i2;
        if (this.mIpadType == IpadType.BOTTOM_RECT.ordinal()) {
            this.paintText.setTextSize(Math.min(this.ipad_rect.width(), this.ipad_rect.height()) * 0.07f);
        } else if (this.mIpadType == IpadType.BORDER.ordinal()) {
            this.paintText.setTextSize(Math.min(this.ipad_rect.width(), this.ipad_rect.height()) * 0.027f);
        } else {
            this.paintText.setTextSize(this.ipad_rect.width() * 0.0388f);
        }
        createRect();
    }

    public void updateBitmap(Bitmap bitmap, Bitmap bitmap2, Gradient gradient, int i, int i2, Rect rect) {
        this.bitmapBlured = bitmap;
        if (bitmap2 != null) {
            this.bitmapSquare = bitmap2;
        }
        this.rectSquare = rect;
        this.mIpadType = i;
        setColorIpad(gradient);
        this.mResizetype = i2;
        if (this.mIpadType == IpadType.BOTTOM_RECT.ordinal()) {
            this.paintText.setTextSize(Math.min(this.ipad_rect.width(), this.ipad_rect.height()) * 0.07f);
        } else if (this.mIpadType == IpadType.BORDER.ordinal()) {
            this.paintText.setTextSize(Math.min(this.ipad_rect.width(), this.ipad_rect.height()) * 0.027f);
        } else {
            this.paintText.setTextSize(this.ipad_rect.width() * 0.0388f);
        }
    }

    public void updateIpad(Bitmap bitmap, int i, int i2) {
        this.mResizetype = i2;
        this.bitmapNotBlur = bitmap;
        this.mIpadType = i;
        if (i == IpadType.IPAD_CLASSIC.ordinal()) {
            if (this.mResizetype == ResizeType.SOCIAL_STORY.ordinal()) {
                float height = bitmap.getHeight() * 0.7601563f;
                float height2 = this.btmY + ((bitmap.getHeight() - height) * 0.5f);
                float f = height * 0.56f;
                float width = this.btmX + ((bitmap.getWidth() - f) * 0.5f);
                this.ipad_rect = new RectF(width, height2, f + width, height + height2);
            }
            if (this.mResizetype == ResizeType.SQUARE.ordinal()) {
                float height3 = bitmap.getHeight() * 0.7601563f;
                float height4 = (this.btmY + (bitmap.getHeight() * 0.5f)) - (height3 * 0.5f);
                float f2 = height3 * 0.56f;
                float width2 = this.btmX + ((bitmap.getWidth() * 0.5f) - (f2 * 0.5f));
                this.ipad_rect = new RectF(width2, height4, f2 + width2, height3 + height4);
            }
            if (this.mResizetype == ResizeType.YOUTUBE_THUMBNAIL.ordinal()) {
                float height5 = bitmap.getHeight() * 0.7601563f;
                float height6 = (this.btmY + (bitmap.getHeight() * 0.5f)) - (height5 * 0.5f);
                float f3 = height5 * 0.56f;
                float width3 = this.btmX + ((bitmap.getWidth() * 0.5f) - (f3 * 0.5f));
                this.ipad_rect = new RectF(width3, height6, f3 + width3, height5 + height6);
            }
        }
        if (i == IpadType.IPAD_NEOMORPHIC.ordinal()) {
            if (this.mResizetype == ResizeType.SOCIAL_STORY.ordinal()) {
                float height7 = bitmap.getHeight() * 0.7601563f;
                float height8 = this.btmY + ((bitmap.getHeight() - height7) * 0.5f);
                float f4 = height7 * 0.56f;
                float width4 = this.btmX + ((bitmap.getWidth() - f4) * 0.5f);
                this.ipad_rect = new RectF(width4, height8, f4 + width4, height7 + height8);
            }
            if (this.mResizetype == ResizeType.SQUARE.ordinal()) {
                float height9 = bitmap.getHeight() * 0.7601563f;
                float height10 = (this.btmY + (bitmap.getHeight() * 0.5f)) - (height9 * 0.5f);
                float f5 = height9 * 0.56f;
                float width5 = this.btmX + ((bitmap.getWidth() * 0.5f) - (f5 * 0.5f));
                this.ipad_rect = new RectF(width5, height10, f5 + width5, height9 + height10);
            }
            if (this.mResizetype == ResizeType.YOUTUBE_THUMBNAIL.ordinal()) {
                float height11 = bitmap.getHeight() * 0.7601563f;
                float height12 = (this.btmY + (bitmap.getHeight() * 0.5f)) - (height11 * 0.5f);
                float f6 = height11 * 0.56f;
                float width6 = this.btmX + ((bitmap.getWidth() * 0.5f) - (f6 * 0.5f));
                this.ipad_rect = new RectF(width6, height12, f6 + width6, height11 + height12);
            }
        }
        if (i == IpadType.CASSET.ordinal() || i == IpadType.CASSET_IMG.ordinal() || i == IpadType.CASSET_IMG_BLUR.ordinal()) {
            float min = Math.min(bitmap.getWidth(), bitmap.getHeight());
            float f7 = min * 0.45f;
            float height13 = (this.btmY + (bitmap.getHeight() * 0.5f)) - (f7 * 0.5f);
            float f8 = min * 0.8f;
            float width7 = this.btmX + ((bitmap.getWidth() * 0.5f) - (f8 * 0.5f));
            this.ipad_rect = new RectF(width7, height13, f8 + width7, f7 + height13);
        }
        if (i == IpadType.IPAD.ordinal() || i == IpadType.IPAD_UNBLUR.ordinal()) {
            if (this.mResizetype == ResizeType.SOCIAL_STORY.ordinal()) {
                float height14 = bitmap.getHeight() * 0.7601563f;
                float height15 = this.btmY + ((bitmap.getHeight() - height14) * 0.5f);
                float f9 = height14 * 0.56f;
                float width8 = this.btmX + ((bitmap.getWidth() - f9) * 0.5f);
                this.ipad_rect = new RectF(width8, height15, f9 + width8, height14 + height15);
            }
            if (this.mResizetype == ResizeType.SQUARE.ordinal()) {
                float height16 = bitmap.getHeight() * 0.7601563f;
                float height17 = (this.btmY + (bitmap.getHeight() * 0.5f)) - (height16 * 0.5f);
                float f10 = height16 * 0.56f;
                float width9 = this.btmX + ((bitmap.getWidth() * 0.5f) - (f10 * 0.5f));
                this.ipad_rect = new RectF(width9, height17, f10 + width9, height16 + height17);
            }
            if (this.mResizetype == ResizeType.YOUTUBE_THUMBNAIL.ordinal()) {
                float height18 = bitmap.getHeight() * 0.7601563f;
                float height19 = (this.btmY + (bitmap.getHeight() * 0.5f)) - (height18 * 0.5f);
                float f11 = 0.56f * height18;
                float width10 = this.btmX + ((bitmap.getWidth() * 0.5f) - (f11 * 0.5f));
                this.ipad_rect = new RectF(width10, height19, f11 + width10, height18 + height19);
            }
        }
        if (i == IpadType.BOTTOM_RECT.ordinal()) {
            if (this.mResizetype == ResizeType.SOCIAL_STORY.ordinal()) {
                float height20 = bitmap.getHeight() * 0.2f;
                float height21 = this.btmY + ((bitmap.getHeight() * 0.88f) - height20);
                float width11 = bitmap.getWidth() * 0.75f;
                float width12 = this.btmX + ((bitmap.getWidth() - width11) * 0.5f);
                this.ipad_rect = new RectF(width12, height21, width11 + width12, height20 + height21);
            }
            if (this.mResizetype == ResizeType.SQUARE.ordinal()) {
                float height22 = bitmap.getHeight() * 0.25f;
                float height23 = this.btmY + ((bitmap.getHeight() * 0.88f) - height22);
                float width13 = bitmap.getWidth() * 0.7f;
                float width14 = this.btmX + ((bitmap.getWidth() - width13) * 0.5f);
                this.ipad_rect = new RectF(width14, height23, width13 + width14, height22 + height23);
            }
            if (this.mResizetype == ResizeType.YOUTUBE_THUMBNAIL.ordinal()) {
                float height24 = bitmap.getHeight() * 0.25f;
                float height25 = this.btmY + ((bitmap.getHeight() * 0.88f) - height24);
                float width15 = bitmap.getWidth() * 0.7f;
                float width16 = this.btmX + ((bitmap.getWidth() - width15) * 0.5f);
                this.ipad_rect = new RectF(width16, height25, width15 + width16, height24 + height25);
            }
        }
        if (i == IpadType.ROUND_RECT.ordinal()) {
            float min2 = Math.min(bitmap.getWidth(), bitmap.getHeight()) * 0.45f;
            float f12 = min2 * 0.5f;
            float height26 = (this.btmY + (bitmap.getHeight() * 0.5f)) - f12;
            float width17 = this.btmX + ((bitmap.getWidth() * 0.5f) - f12);
            this.ipad_rect = new RectF(width17, height26, width17 + min2, min2 + height26);
        }
        if (i == IpadType.RECT.ordinal()) {
            float min3 = Math.min(bitmap.getWidth(), bitmap.getHeight());
            float f13 = 0.35f * min3;
            float height27 = this.btmY + (bitmap.getHeight() * 0.3f);
            float f14 = min3 * 0.4f;
            float width18 = this.btmX + ((bitmap.getWidth() * 0.85f) - f14);
            this.ipad_rect = new RectF(width18, height27, f14 + width18, f13 + height27);
        }
        if (i == IpadType.BLACK_LAYER.ordinal() || i == IpadType.BLUE_TYPE.ordinal() || i == IpadType.HEART.ordinal() || i == IpadType.BATTERY.ordinal()) {
            float f15 = this.btmX;
            this.ipad_rect = new RectF(f15 - 2.0f, this.btmY, f15 + bitmap.getWidth() + 2.0f, this.btmY + bitmap.getHeight());
        }
        if (i == IpadType.GRADIENT.ordinal()) {
            this.ipad_rect = new RectF(this.btmX - 2.0f, this.btmY + (bitmap.getHeight() * 0.2f), this.btmX + bitmap.getWidth() + 2.0f, this.btmY + bitmap.getHeight());
        }
        if (i == IpadType.MASK_BRUSH.ordinal()) {
            float f16 = this.btmX;
            this.ipad_rect = new RectF(f16, this.btmY, bitmap.getWidth() + f16, this.btmY + bitmap.getHeight());
        }
        if (i == IpadType.BORDER.ordinal()) {
            int min4 = Math.min(bitmap.getWidth(), bitmap.getHeight());
            float height28 = bitmap.getHeight() * Common.p_h_border;
            float width19 = bitmap.getWidth() * Common.p_w_border;
            float f17 = this.btmY + height28;
            float f18 = this.btmX;
            this.ipad_rect = new RectF(f18 + width19, f17, (f18 + bitmap.getWidth()) - width19, (this.btmY + bitmap.getHeight()) - height28);
            this.paintIpad.setStyle(Paint.Style.STROKE);
            this.paintIpad.setStrokeWidth(min4 * 0.013f);
            return;
        }
        this.paintIpad.setStyle(Paint.Style.FILL);
    }

    public void updateIpad() {
        if (this.mIpadType == IpadType.IPAD_CLASSIC.ordinal()) {
            if (this.mResizetype == ResizeType.SOCIAL_STORY.ordinal()) {
                float height = this.bitmapBlured.getHeight() * 0.7601563f;
                float height2 = (this.btmY + (this.bitmapBlured.getHeight() * 0.5f)) - (height * 0.5f);
                float f = height * 0.56f;
                float width = this.btmX + ((this.bitmapBlured.getWidth() * 0.5f) - (f * 0.5f));
                this.ipad_rect = new RectF(width, height2, f + width, height + height2);
            }
            if (this.mResizetype == ResizeType.SQUARE.ordinal()) {
                float height3 = this.bitmapBlured.getHeight() * 0.7601563f;
                float height4 = (this.btmY + (this.bitmapBlured.getHeight() * 0.5f)) - (height3 * 0.5f);
                float f2 = height3 * 0.56f;
                float width2 = this.btmX + ((this.bitmapBlured.getWidth() * 0.5f) - (f2 * 0.5f));
                this.ipad_rect = new RectF(width2, height4, f2 + width2, height3 + height4);
            }
            if (this.mResizetype == ResizeType.YOUTUBE_THUMBNAIL.ordinal()) {
                float height5 = this.bitmapBlured.getHeight() * 0.7601563f;
                float height6 = (this.btmY + (this.bitmapBlured.getHeight() * 0.5f)) - (height5 * 0.5f);
                float f3 = height5 * 0.56f;
                float width3 = this.btmX + ((this.bitmapBlured.getWidth() * 0.5f) - (f3 * 0.5f));
                this.ipad_rect = new RectF(width3, height6, f3 + width3, height5 + height6);
            }
        }
        if (this.mIpadType == IpadType.IPAD_NEOMORPHIC.ordinal()) {
            if (this.mResizetype == ResizeType.SOCIAL_STORY.ordinal()) {
                float height7 = this.bitmapBlured.getHeight() * 0.7601563f;
                float height8 = (this.btmY + (this.bitmapBlured.getHeight() * 0.5f)) - (height7 * 0.5f);
                float f4 = height7 * 0.56f;
                float width4 = this.btmX + ((this.bitmapBlured.getWidth() * 0.5f) - (f4 * 0.5f));
                this.ipad_rect = new RectF(width4, height8, f4 + width4, height7 + height8);
            }
            if (this.mResizetype == ResizeType.SQUARE.ordinal()) {
                float height9 = this.bitmapBlured.getHeight() * 0.7601563f;
                float height10 = (this.btmY + (this.bitmapBlured.getHeight() * 0.5f)) - (height9 * 0.5f);
                float f5 = height9 * 0.56f;
                float width5 = this.btmX + ((this.bitmapBlured.getWidth() * 0.5f) - (f5 * 0.5f));
                this.ipad_rect = new RectF(width5, height10, f5 + width5, height9 + height10);
            }
            if (this.mResizetype == ResizeType.YOUTUBE_THUMBNAIL.ordinal()) {
                float height11 = this.bitmapBlured.getHeight() * 0.7601563f;
                float height12 = (this.btmY + (this.bitmapBlured.getHeight() * 0.5f)) - (height11 * 0.5f);
                float f6 = height11 * 0.56f;
                float width6 = this.btmX + ((this.bitmapBlured.getWidth() * 0.5f) - (f6 * 0.5f));
                this.ipad_rect = new RectF(width6, height12, f6 + width6, height11 + height12);
            }
        }
        if (this.mIpadType == IpadType.IPAD.ordinal() || this.mIpadType == IpadType.IPAD_UNBLUR.ordinal()) {
            if (this.mResizetype == ResizeType.SOCIAL_STORY.ordinal()) {
                float height13 = this.bitmapBlured.getHeight() * 0.7601563f;
                float height14 = (this.btmY + (this.bitmapBlured.getHeight() * 0.5f)) - (height13 * 0.5f);
                float f7 = height13 * 0.56f;
                float width7 = this.btmX + ((this.bitmapBlured.getWidth() * 0.5f) - (f7 * 0.5f));
                this.ipad_rect = new RectF(width7, height14, f7 + width7, height13 + height14);
            }
            if (this.mResizetype == ResizeType.SQUARE.ordinal()) {
                float height15 = this.bitmapBlured.getHeight() * 0.7601563f;
                float height16 = (this.btmY + (this.bitmapBlured.getHeight() * 0.5f)) - (height15 * 0.5f);
                float f8 = height15 * 0.56f;
                float width8 = this.btmX + ((this.bitmapBlured.getWidth() * 0.5f) - (f8 * 0.5f));
                this.ipad_rect = new RectF(width8, height16, f8 + width8, height15 + height16);
            }
            if (this.mResizetype == ResizeType.YOUTUBE_THUMBNAIL.ordinal()) {
                float height17 = this.bitmapBlured.getHeight() * 0.7601563f;
                float height18 = (this.btmY + (this.bitmapBlured.getHeight() * 0.5f)) - (height17 * 0.5f);
                float f9 = 0.56f * height17;
                float width9 = this.btmX + ((this.bitmapBlured.getWidth() * 0.5f) - (f9 * 0.5f));
                this.ipad_rect = new RectF(width9, height18, f9 + width9, height17 + height18);
            }
        }
        if (this.mIpadType == IpadType.BOTTOM_RECT.ordinal()) {
            if (this.mResizetype == ResizeType.SOCIAL_STORY.ordinal()) {
                float height19 = this.bitmapBlured.getHeight() * 0.2f;
                float height20 = this.btmY + ((this.bitmapBlured.getHeight() * 0.88f) - height19);
                float width10 = this.bitmapBlured.getWidth() * 0.75f;
                float width11 = this.btmX + ((this.bitmapBlured.getWidth() - width10) * 0.5f);
                this.ipad_rect = new RectF(width11, height20, width10 + width11, height19 + height20);
            }
            if (this.mResizetype == ResizeType.SQUARE.ordinal()) {
                float height21 = this.bitmapBlured.getHeight() * 0.25f;
                float height22 = this.btmY + ((this.bitmapBlured.getHeight() * 0.88f) - height21);
                float width12 = this.bitmapBlured.getWidth() * 0.7f;
                float width13 = this.btmX + ((this.bitmapBlured.getWidth() - width12) * 0.5f);
                this.ipad_rect = new RectF(width13, height22, width12 + width13, height21 + height22);
            }
            if (this.mResizetype == ResizeType.YOUTUBE_THUMBNAIL.ordinal()) {
                float height23 = this.bitmapBlured.getHeight() * 0.25f;
                float height24 = this.btmY + ((this.bitmapBlured.getHeight() * 0.88f) - height23);
                float width14 = this.bitmapBlured.getWidth() * 0.7f;
                float width15 = this.btmX + ((this.bitmapBlured.getWidth() - width14) * 0.5f);
                this.ipad_rect = new RectF(width15, height24, width14 + width15, height23 + height24);
            }
        }
        if (this.mIpadType == IpadType.ROUND_RECT.ordinal()) {
            float min = Math.min(this.bitmapBlured.getWidth(), this.bitmapBlured.getHeight()) * 0.45f;
            float f10 = min * 0.5f;
            float height25 = (this.btmY + (this.bitmapBlured.getHeight() * 0.5f)) - f10;
            float width16 = this.btmX + ((this.bitmapBlured.getWidth() * 0.5f) - f10);
            this.ipad_rect = new RectF(width16, height25, width16 + min, min + height25);
        }
        if (this.mIpadType == IpadType.CASSET.ordinal() || this.mIpadType == IpadType.CASSET_IMG.ordinal() || this.mIpadType == IpadType.CASSET_IMG_BLUR.ordinal()) {
            float min2 = Math.min(this.bitmapBlured.getWidth(), this.bitmapBlured.getHeight());
            float f11 = 0.45f * min2;
            float height26 = (this.btmY + (this.bitmapBlured.getHeight() * 0.5f)) - (f11 * 0.5f);
            float f12 = min2 * 0.8f;
            float width17 = this.btmX + ((this.bitmapBlured.getWidth() * 0.5f) - (0.5f * f12));
            this.ipad_rect = new RectF(width17, height26, f12 + width17, f11 + height26);
        }
        if (this.mIpadType == IpadType.RECT.ordinal()) {
            float min3 = Math.min(this.bitmapBlured.getWidth(), this.bitmapBlured.getHeight());
            float f13 = 0.35f * min3;
            float height27 = this.btmY + (this.bitmapBlured.getHeight() * 0.3f);
            float f14 = min3 * 0.4f;
            float width18 = this.btmX + ((this.bitmapBlured.getWidth() * 0.85f) - f14);
            this.ipad_rect = new RectF(width18, height27, f14 + width18, f13 + height27);
        }
        if (this.mIpadType == IpadType.BLACK_LAYER.ordinal() || this.mIpadType == IpadType.BLUE_TYPE.ordinal() || this.mIpadType == IpadType.HEART.ordinal() || this.mIpadType == IpadType.BATTERY.ordinal()) {
            float f15 = this.btmX;
            this.ipad_rect = new RectF(f15 - 2.0f, this.btmY, f15 + this.bitmapBlured.getWidth() + 2.0f, this.btmY + this.bitmapBlured.getHeight());
        }
        if (this.mIpadType == IpadType.GRADIENT.ordinal()) {
            this.ipad_rect = new RectF(this.btmX - 2.0f, this.btmY + (this.bitmapBlured.getHeight() * 0.2f), this.btmX + this.bitmapBlured.getWidth() + 2.0f, this.btmY + this.bitmapBlured.getHeight());
        }
        if (this.mIpadType == IpadType.MASK_BRUSH.ordinal()) {
            float f16 = this.btmX;
            this.ipad_rect = new RectF(f16, this.btmY, this.bitmapBlured.getWidth() + f16, this.btmY + this.bitmapBlured.getHeight());
        }
        if (this.mIpadType == IpadType.BORDER.ordinal()) {
            int min4 = Math.min(this.bitmapBlured.getWidth(), this.bitmapBlured.getHeight());
            float height28 = this.bitmapBlured.getHeight() * Common.p_h_border;
            float width19 = this.bitmapBlured.getWidth() * Common.p_w_border;
            float f17 = this.btmY + height28;
            float f18 = this.btmX;
            this.ipad_rect = new RectF(f18 + width19, f17, (f18 + this.bitmapBlured.getWidth()) - width19, (this.btmY + this.bitmapBlured.getHeight()) - height28);
            this.paintIpad.setStyle(Paint.Style.STROKE);
            this.paintIpad.setStrokeWidth(min4 * 0.013f);
            return;
        }
        this.paintIpad.setStyle(Paint.Style.FILL);
    }

    public RectF getIpad_rect() {
        return this.ipad_rect;
    }

    public void setmIpadType(int i) {
        this.mIpadType = i;
    }

    public void changeTypeIpad(int i) {
        this.mIpadType = i;
        updateIpad();
        if (this.mIpadType == IpadType.BOTTOM_RECT.ordinal()) {
            this.paintText.setTextSize(Math.min(this.ipad_rect.width(), this.ipad_rect.height()) * 0.07f);
        } else if (this.mIpadType == IpadType.BORDER.ordinal()) {
            this.paintText.setTextSize(Math.min(this.ipad_rect.width(), this.ipad_rect.height()) * 0.027f);
        } else {
            this.paintText.setTextSize(this.ipad_rect.width() * 0.0388f);
        }
    }

    public void createRect() {
        updatePosSurahName();
        if (this.mIpadType == IpadType.IPAD_CLASSIC.ordinal()) {
            float height = this.ipad_rect.height() - this.bitmapSquare.getHeight();
            float f = height * 0.03f;
            float centerX = this.ipad_rect.centerX() - (this.bitmapSquare.getWidth() * 0.5f);
            float height2 = this.ipad_rect.top + f + this.bitmapSquare.getHeight();
            this.rectFSurahName = new RectF();
            float width = this.bitmapSquare.getWidth() + centerX;
            float f2 = height2 + (f * 1.5f);
            float f3 = ((height * 0.2f) - f) + f2;
            this.rectFSurahName.set(width - (this.ipad_rect.width() * 0.4f), f2, width, f3);
            this.rectFAya = new RectF();
            float f4 = ((height * 0.35f) - f) + f3;
            this.rectFAya.set(centerX, f3, this.bitmapSquare.getWidth() + centerX, f4);
            this.rectFProgress = new RectF();
            float f5 = (height * 0.15f) + f4;
            this.rectFProgress.set(centerX, f4, this.rectFAya.right, f5);
            this.rectFAya.bottom = this.rectFProgress.centerY();
            this.rectFLecture = new RectF();
            this.rectFLecture.set(centerX, f5, this.rectFAya.right, (height * 0.25f) + f5);
        }
        if (this.mIpadType == IpadType.IPAD_NEOMORPHIC.ordinal()) {
            float height3 = this.ipad_rect.height() * 0.6f;
            float f6 = height3 * 0.03f;
            float width2 = this.ipad_rect.top + (this.ipad_rect.width() * 0.3f * 2.4f);
            this.rectFSurahName = new RectF();
            float width3 = this.ipad_rect.width() * 0.5f * 0.5f;
            float f7 = width2 + (2.8f * f6);
            this.rectFSurahName.set(this.ipad_rect.centerX() - width3, f7, this.ipad_rect.centerX() + width3, (this.ipad_rect.height() * 0.1f) + f7);
            float centerY = this.rectFSurahName.centerY() + (2.0f * f6);
            RectF rectF = new RectF();
            this.rectFAya = rectF;
            float f8 = ((height3 * 0.25f) - f6) + centerY;
            rectF.set(this.ipad_rect.left + f6, centerY, this.ipad_rect.right - f6, f8);
            float f9 = f8 + (0.055f * height3);
            this.rectFProgress = new RectF();
            float f10 = (height3 * 0.2f) + f9;
            float width4 = this.bitmapSquare.getWidth() * 0.65f;
            this.rectFProgress.set(this.ipad_rect.centerX() - width4, f9, this.ipad_rect.centerX() + width4, f10);
            this.rectFAya.bottom = this.rectFProgress.centerY();
            this.rectFLecture = new RectF();
            float width5 = this.bitmapSquare.getWidth() * 0.1f;
            this.rectFLecture.set(this.rectFProgress.left - width5, f10 + f6, this.rectFProgress.right + width5, this.ipad_rect.bottom);
        }
        if (this.mIpadType == IpadType.IPAD.ordinal() || this.mIpadType == IpadType.IPAD_UNBLUR.ordinal()) {
            float height4 = this.ipad_rect.height() - this.bitmapSquare.getHeight();
            float f11 = height4 * 0.03f;
            float centerX2 = this.ipad_rect.centerX() - (this.bitmapSquare.getWidth() * 0.5f);
            float height5 = this.ipad_rect.top + f11 + this.bitmapSquare.getHeight();
            this.rectFSurahName = new RectF();
            float width6 = this.bitmapSquare.getWidth() + centerX2;
            float f12 = height5 + (f11 * 1.5f);
            float f13 = ((height4 * 0.2f) - f11) + f12;
            this.rectFSurahName.set(width6 - (this.ipad_rect.width() * 0.4f), f12, width6, f13);
            this.rectFAya = new RectF();
            float f14 = ((height4 * 0.35f) - f11) + f13;
            this.rectFAya.set(centerX2, f13, this.bitmapSquare.getWidth() + centerX2, f14);
            this.rectFProgress = new RectF();
            float f15 = (height4 * 0.15f) + f14;
            this.rectFProgress.set(centerX2, f14, this.rectFAya.right, f15);
            this.rectFAya.bottom = this.rectFProgress.centerY();
            this.rectFLecture = new RectF();
            this.rectFLecture.set(centerX2, f15, this.rectFAya.right, (height4 * 0.25f) + f15);
        }
        if (this.mIpadType == IpadType.ROUND_RECT.ordinal()) {
            float width7 = this.ipad_rect.width() * 0.07f;
            float f16 = this.ipad_rect.left + width7;
            float f17 = this.ipad_rect.top + width7;
            this.rectFSurahName = new RectF();
            float width8 = this.ipad_rect.width() * 0.52f;
            float height6 = this.ipad_rect.height() * 0.25f;
            float f18 = this.ipad_rect.right - width7;
            float f19 = height6 + f17;
            this.rectFSurahName.set(f18 - width8, f17, f18, f19);
            this.rectFAya = new RectF();
            float width9 = this.ipad_rect.width() * 0.02f;
            float f20 = this.ipad_rect.left + width9;
            float f21 = this.ipad_rect.right - width9;
            float height7 = (this.ipad_rect.height() * 0.3f) + f19;
            this.rectFAya.set(f20, f19, f21, height7);
            this.rectFProgress = new RectF();
            float height8 = (this.ipad_rect.height() * 0.168f) + height7;
            this.rectFProgress.set(f16, height7, f18, height8);
            this.rectFAya.bottom = this.rectFProgress.centerY();
            this.rectFLecture = new RectF();
            this.rectFLecture.set(f16, height8, f18, this.ipad_rect.bottom - (width7 * 0.75f));
        }
        if (this.mIpadType == IpadType.RECT.ordinal()) {
            float width10 = this.ipad_rect.width() * 0.05f;
            float f22 = this.ipad_rect.left + width10;
            float f23 = this.ipad_rect.top + width10;
            this.rectFSurahName = new RectF();
            float width11 = this.ipad_rect.width() * 0.52f;
            float height9 = this.ipad_rect.height() * 0.25f;
            float f24 = this.ipad_rect.right - width10;
            float f25 = height9 + f23;
            this.rectFSurahName.set(f24 - width11, f23, f24, f25);
            this.rectFAya = new RectF();
            float height10 = (this.ipad_rect.height() * 0.3f) + f25;
            this.rectFAya.set(f22, f25, f24, height10);
            this.rectFProgress = new RectF();
            float height11 = (this.ipad_rect.height() * 0.18f) + height10;
            this.rectFProgress.set(f22, height10, this.rectFAya.right, height11);
            this.rectFAya.bottom = this.rectFProgress.centerY();
            this.rectFLecture = new RectF();
            this.rectFLecture.set(f22, height11, this.rectFAya.right, this.ipad_rect.bottom - width10);
        }
        if (this.mIpadType == IpadType.BOTTOM_RECT.ordinal()) {
            float width12 = this.ipad_rect.width() * 0.005f;
            float width13 = this.ipad_rect.left + (this.ipad_rect.width() * 0.025f);
            float f26 = this.ipad_rect.top + width12;
            this.rectFSurahName = new RectF();
            float width14 = this.ipad_rect.width() * 0.37f;
            float height12 = this.ipad_rect.height() * 0.2f;
            float width15 = this.ipad_rect.right - (this.ipad_rect.width() * 0.015f);
            float f27 = height12 + f26;
            this.rectFSurahName.set(width15 - width14, f26, width15, f27);
            float f28 = f27 + width12;
            float min = Math.min(this.ipad_rect.height(), this.ipad_rect.width());
            RectF rectF2 = new RectF();
            this.rectFAya = rectF2;
            float f29 = (min * 0.25f) + f28;
            rectF2.set(width13, f28, width15, f29);
            this.rectFProgress = new RectF();
            this.rectFProgress.set(width13, f29, this.rectFAya.right, (min * 0.3f) + f29);
            float f30 = this.rectFProgress.bottom - (width12 * 2.5f);
            this.rectFLecture = new RectF();
            this.rectFLecture.set(width13, f30, this.rectFAya.right, this.rectFAya.height() + f30);
            this.rectFAya.bottom = this.rectFProgress.top * 1.025f;
        }
        if (this.mIpadType == IpadType.BLACK_LAYER.ordinal()) {
            float f31 = this.mResizetype == ResizeType.SQUARE.ordinal() ? 0.34f : 0.4f;
            float width16 = this.ipad_rect.width() * 0.014f;
            float f32 = this.ipad_rect.left + width16;
            float f33 = this.ipad_rect.top + width16;
            this.rectFSurahName = new RectF();
            float min2 = Math.min(this.ipad_rect.width(), this.ipad_rect.height());
            float height13 = this.ipad_rect.height() * 0.12f;
            float f34 = this.ipad_rect.right - width16;
            this.rectFSurahName.set(f34 - (f31 * min2), f33, f34, height13 + f33);
            float centerY2 = this.ipad_rect.centerY() * 0.8f;
            this.rectFAya = new RectF();
            float f35 = min2 * 0.15f;
            this.rectFAya.set(this.ipad_rect.left + f35, centerY2, this.ipad_rect.right - f35, centerY2 + (min2 * 0.3f));
            float centerY3 = this.rectFAya.centerY();
            RectF rectF3 = new RectF();
            this.rectFProgress = rectF3;
            float f36 = (min2 * 0.168f) + centerY3;
            rectF3.set(this.rectFAya.left, centerY3, this.rectFAya.right, f36);
            this.rectFLecture = new RectF();
            this.rectFLecture.set(f32, f36, f34, this.ipad_rect.bottom - (width16 * 0.75f));
        }
        if (this.mIpadType == IpadType.HEART.ordinal() || this.mIpadType == IpadType.BATTERY.ordinal()) {
            if (this.mResizetype == ResizeType.SOCIAL_STORY.ordinal()) {
                float width17 = this.ipad_rect.width() * 0.014f;
                float f37 = this.ipad_rect.left + width17;
                float f38 = this.ipad_rect.top + width17;
                this.rectFSurahName = new RectF();
                float min3 = Math.min(this.ipad_rect.width(), this.ipad_rect.height());
                float height14 = this.ipad_rect.height() * 0.09f;
                float f39 = this.ipad_rect.right - width17;
                this.rectFSurahName.set(f39 - (min3 * 0.4f), f38, f39, height14 + f38);
                float centerY4 = this.ipad_rect.centerY() * 0.3f;
                this.rectFAya = new RectF();
                float f40 = min3 * 0.15f;
                this.rectFAya.set(this.ipad_rect.left + f40, centerY4, this.ipad_rect.right - f40, (min3 * 0.3f) + centerY4);
                this.rectFProgress = new RectF();
                float width18 = this.rectFAya.width();
                float centerY5 = this.ipad_rect.centerY() - (this.rectFAya.width() * 0.5f);
                float f41 = width18 + centerY5;
                this.rectFProgress.set(this.rectFAya.left, centerY5, this.rectFAya.right, f41);
                this.rectFLecture = new RectF();
                this.rectFLecture.set(f37, f41, f39, this.ipad_rect.bottom - (width17 * 0.75f));
            }
            if (this.mResizetype == ResizeType.YOUTUBE_THUMBNAIL.ordinal()) {
                float width19 = this.ipad_rect.width() * 0.014f;
                float f42 = this.ipad_rect.left + width19;
                float f43 = this.ipad_rect.top + width19;
                this.rectFSurahName = new RectF();
                float min4 = Math.min(this.ipad_rect.width(), this.ipad_rect.height());
                float height15 = this.ipad_rect.height() * 0.15f;
                float f44 = this.ipad_rect.right - width19;
                this.rectFSurahName.set(f44 - (min4 * 0.4f), f43, f44, height15 + f43);
                float centerY6 = this.ipad_rect.centerY() * 0.34f;
                this.rectFAya = new RectF();
                float f45 = 0.55f * min4;
                this.rectFAya.set(this.ipad_rect.left + f45, centerY6, this.ipad_rect.right - f45, (min4 * 0.3f) + centerY6);
                this.rectFProgress = new RectF();
                float centerY7 = this.rectFAya.centerY() * 1.3f;
                float f46 = this.ipad_rect.bottom * 0.9f;
                this.rectFProgress.set(this.rectFAya.left, centerY7, this.rectFAya.right, f46);
                this.rectFLecture = new RectF();
                this.rectFLecture.set(f42, f46, f44, this.ipad_rect.bottom - (width19 * 0.75f));
            }
            if (this.mResizetype == ResizeType.SQUARE.ordinal()) {
                float width20 = this.ipad_rect.width() * 0.014f;
                float f47 = this.ipad_rect.left + width20;
                float f48 = this.ipad_rect.top + width20;
                this.rectFSurahName = new RectF();
                float min5 = Math.min(this.ipad_rect.width(), this.ipad_rect.height());
                float height16 = this.ipad_rect.height() * 0.09f;
                float f49 = this.ipad_rect.right - width20;
                this.rectFSurahName.set(f49 - (min5 * 0.34f), f48, f49, height16 + f48);
                float centerY8 = this.ipad_rect.centerY() * 0.3f;
                this.rectFAya = new RectF();
                float f50 = min5 * 0.25f;
                this.rectFAya.set(this.ipad_rect.left + f50, centerY8, this.ipad_rect.right - f50, (min5 * 0.3f) + centerY8);
                this.rectFProgress = new RectF();
                float centerY9 = this.rectFAya.centerY() * 1.3f;
                float f51 = this.ipad_rect.bottom * 0.9f;
                this.rectFProgress.set(this.rectFAya.left, centerY9, this.rectFAya.right, f51);
                this.rectFLecture = new RectF();
                this.rectFLecture.set(f47, f51, f49, this.ipad_rect.bottom - (width20 * 0.75f));
            }
        }
        if (this.mIpadType == IpadType.CASSET.ordinal() || this.mIpadType == IpadType.CASSET_IMG.ordinal() || this.mIpadType == IpadType.CASSET_IMG_BLUR.ordinal()) {
            float width21 = this.ipad_rect.width() * 0.012f;
            float f52 = this.ipad_rect.top + width21;
            this.rectFSurahName = new RectF();
            float min6 = Math.min(this.ipad_rect.width(), this.ipad_rect.height()) * 0.45f;
            float height17 = this.ipad_rect.height() * 0.19f;
            float f53 = this.ipad_rect.right - width21;
            this.rectFSurahName.set(f53 - min6, f52, f53, height17 + f52);
            float width22 = this.ipad_rect.width() * 0.33f;
            float height18 = this.ipad_rect.height() * 0.2f;
            float f54 = this.ipad_rect.top + height18;
            this.rectFAya = new RectF(this.ipad_rect.centerX() - width22, f54, this.ipad_rect.centerX() + width22, height18 + f54);
            this.rectFProgress = new RectF();
            this.rectFLecture = new RectF();
        }
        if (this.mIpadType == IpadType.GRADIENT.ordinal()) {
            float width23 = this.ipad_rect.width() * 0.014f;
            float f55 = this.ipad_rect.left + width23;
            this.ipad_rect.centerY();
            this.rectFSurahName = new RectF();
            float min7 = Math.min(this.ipad_rect.width(), this.ipad_rect.height());
            float f56 = this.ipad_rect.right - width23;
            this.rectFAya = new RectF();
            float f57 = min7 * 0.15f;
            float f58 = this.ipad_rect.left + f57;
            float f59 = this.ipad_rect.right - f57;
            float f60 = min7 * 0.3f;
            float f61 = this.ipad_rect.bottom * 0.95f;
            this.rectFAya.set(f58, f61 - f60, f59, f61);
            this.rectFSurahName.set(f56 - (min7 * 0.52f), this.rectFAya.centerY() - f60, f56, this.rectFAya.centerY());
            float centerY10 = this.rectFAya.centerY();
            RectF rectF4 = new RectF();
            this.rectFProgress = rectF4;
            float f62 = (min7 * 0.168f) + centerY10;
            rectF4.set(this.rectFAya.left, centerY10, this.rectFAya.right, f62);
            this.rectFLecture = new RectF();
            this.rectFLecture.set(f55, f62, f56, this.ipad_rect.bottom - (width23 * 0.75f));
            this.rectFSurahName.bottom = this.rectFAya.top;
        }
        if (this.mIpadType == IpadType.MASK_BRUSH.ordinal()) {
            if (this.mResizetype == ResizeType.SOCIAL_STORY.ordinal()) {
                float width24 = this.ipad_rect.width() * 0.014f;
                float f63 = this.ipad_rect.left + width24;
                float f64 = this.ipad_rect.top + width24;
                this.rectFSurahName = new RectF();
                float width25 = this.ipad_rect.width();
                float height19 = this.ipad_rect.height() * 0.09f;
                float f65 = this.ipad_rect.right;
                this.rectFSurahName.set(f65 - (width25 * 0.4f), f64, f65, height19 + f64);
                float height20 = this.ipad_rect.height() * 0.19f;
                this.rectFAya = new RectF();
                float f66 = width25 * 0.15f;
                this.rectFAya.set(this.ipad_rect.left + f66, height20, this.ipad_rect.right - f66, (width25 * 0.3f) + height20);
                float centerY11 = this.rectFAya.centerY();
                RectF rectF5 = new RectF();
                this.rectFProgress = rectF5;
                float f67 = (width25 * 0.168f) + centerY11;
                rectF5.set(this.rectFAya.left, centerY11, this.rectFAya.right, f67);
                this.rectFLecture = new RectF();
                this.rectFLecture.set(f63, f67, f65, this.ipad_rect.bottom - (width24 * 0.75f));
            }
            if (this.mResizetype == ResizeType.SQUARE.ordinal()) {
                float width26 = this.ipad_rect.width() * 0.014f;
                float f68 = this.ipad_rect.left + width26;
                float f69 = this.ipad_rect.top + width26;
                this.rectFSurahName = new RectF();
                float width27 = this.ipad_rect.width();
                float height21 = this.ipad_rect.height() * 0.09f;
                float f70 = this.ipad_rect.right;
                this.rectFSurahName.set(f70 - (width27 * 0.25f), f69, f70, height21 + f69);
                float height22 = this.ipad_rect.height() * 0.11f;
                this.rectFAya = new RectF();
                float f71 = width27 * 0.15f;
                this.rectFAya.set(this.ipad_rect.left + f71, height22, this.ipad_rect.right - f71, (width27 * 0.3f) + height22);
                float centerY12 = this.rectFAya.centerY();
                RectF rectF6 = new RectF();
                this.rectFProgress = rectF6;
                float f72 = (width27 * 0.168f) + centerY12;
                rectF6.set(this.rectFAya.left, centerY12, this.rectFAya.right, f72);
                this.rectFLecture = new RectF();
                this.rectFLecture.set(f68, f72, f70, this.ipad_rect.bottom - (width26 * 0.75f));
            }
            if (this.mResizetype == ResizeType.YOUTUBE_THUMBNAIL.ordinal()) {
                float width28 = this.ipad_rect.width() * 0.014f;
                float f73 = this.ipad_rect.left + width28;
                float f74 = this.ipad_rect.top + width28;
                this.rectFSurahName = new RectF();
                float width29 = this.ipad_rect.width();
                float height23 = this.ipad_rect.height() * 0.09f;
                float f75 = this.ipad_rect.right;
                this.rectFSurahName.set(f75 - (width29 * 0.25f), f74, f75, height23 + f74);
                float f76 = (-this.rectFSurahName.height()) * 0.5f;
                this.rectFAya = new RectF();
                float f77 = width29 * 0.3f;
                this.rectFAya.set(this.ipad_rect.left + f77, f76, this.ipad_rect.right - f77, f77 + f76);
                float centerY13 = this.rectFAya.centerY();
                RectF rectF7 = new RectF();
                this.rectFProgress = rectF7;
                float f78 = (width29 * 0.168f) + centerY13;
                rectF7.set(this.rectFAya.left, centerY13, this.rectFAya.right, f78);
                this.rectFLecture = new RectF();
                this.rectFLecture.set(f73, f78, f75, this.ipad_rect.bottom - (width28 * 0.75f));
            }
        }
        if (this.mIpadType == IpadType.BLUE_TYPE.ordinal()) {
            if (this.mResizetype == ResizeType.SOCIAL_STORY.ordinal()) {
                float width30 = this.ipad_rect.width() * 0.014f;
                float f79 = this.ipad_rect.left + width30;
                float f80 = this.ipad_rect.top + width30;
                this.rectFSurahName = new RectF();
                float min8 = Math.min(this.ipad_rect.width(), this.ipad_rect.height());
                float height24 = this.ipad_rect.height() * 0.09f;
                float f81 = this.ipad_rect.right - width30;
                this.rectFSurahName.set(f81 - (min8 * 0.4f), f80, f81, height24 + f80);
                float centerY14 = this.ipad_rect.centerY() * 0.3f;
                this.rectFAya = new RectF();
                float f82 = min8 * 0.15f;
                this.rectFAya.set(this.ipad_rect.left + f82, centerY14, this.ipad_rect.right - f82, (min8 * 0.3f) + centerY14);
                float centerY15 = this.ipad_rect.centerY() * 0.2f;
                RectF rectF8 = new RectF();
                this.rectFProgress = rectF8;
                float f83 = (min8 * 0.168f) + centerY15;
                rectF8.set(this.rectFAya.left, centerY15, this.rectFAya.right, f83);
                this.rectFLecture = new RectF();
                this.rectFLecture.set(f79, f83, f81, this.ipad_rect.bottom - (width30 * 0.75f));
            }
            if (this.mResizetype == ResizeType.YOUTUBE_THUMBNAIL.ordinal()) {
                float width31 = this.ipad_rect.width() * 0.014f;
                float f84 = this.ipad_rect.left + width31;
                float f85 = this.ipad_rect.top + width31;
                this.rectFSurahName = new RectF();
                float min9 = Math.min(this.ipad_rect.width(), this.ipad_rect.height());
                float f86 = min9 * 0.4f;
                float height25 = this.ipad_rect.height() * 0.15f;
                float f87 = this.ipad_rect.right - width31;
                this.rectFSurahName.set(f87 - f86, f85, f87, height25 + f85);
                float centerY16 = this.ipad_rect.centerY() * 0.34f;
                this.rectFAya = new RectF();
                this.rectFAya.set(this.ipad_rect.left + f86, centerY16, this.ipad_rect.right - f86, (min9 * 0.3f) + centerY16);
                float centerY17 = this.ipad_rect.centerY() * 0.2f;
                RectF rectF9 = new RectF();
                this.rectFProgress = rectF9;
                float f88 = (min9 * 0.168f) + centerY17;
                rectF9.set(this.rectFAya.left, centerY17, this.rectFAya.right, f88);
                this.rectFLecture = new RectF();
                this.rectFLecture.set(f84, f88, f87, this.ipad_rect.bottom - (width31 * 0.75f));
            }
            if (this.mResizetype == ResizeType.SQUARE.ordinal()) {
                float width32 = this.ipad_rect.width() * 0.014f;
                float f89 = this.ipad_rect.left + width32;
                float f90 = this.ipad_rect.top + width32;
                this.rectFSurahName = new RectF();
                float min10 = Math.min(this.ipad_rect.width(), this.ipad_rect.height());
                float height26 = this.ipad_rect.height() * 0.09f;
                float f91 = this.ipad_rect.right - width32;
                this.rectFSurahName.set(f91 - (0.34f * min10), f90, f91, height26 + f90);
                float centerY18 = this.ipad_rect.centerY() * 0.3f;
                this.rectFAya = new RectF();
                float f92 = min10 * 0.2f;
                this.rectFAya.set(this.ipad_rect.left + f92, centerY18, this.ipad_rect.right - f92, (0.3f * min10) + centerY18);
                float centerY19 = this.ipad_rect.centerY() * 0.2f;
                RectF rectF10 = new RectF();
                this.rectFProgress = rectF10;
                float f93 = (min10 * 0.16f) + centerY19;
                rectF10.set(this.rectFAya.left, centerY19, this.rectFAya.right, f93);
                this.rectFLecture = new RectF();
                this.rectFLecture.set(f89, f93, f91, this.ipad_rect.bottom - (width32 * 0.75f));
            }
        }
        if (this.mIpadType == IpadType.BORDER.ordinal()) {
            float height27 = this.ipad_rect.height() - this.bitmapSquare.getHeight();
            float f94 = height27 * 0.03f;
            float width33 = this.ipad_rect.width() * 0.8f;
            float centerX3 = this.ipad_rect.centerX() - (width33 * 0.5f);
            float height28 = (this.ipad_rect.top + f94 + this.bitmapSquare.getHeight()) * 0.5f;
            this.rectFSurahName = new RectF();
            float f95 = centerX3 + width33;
            float f96 = height28 + (1.5f * f94);
            float f97 = ((height27 * 0.2f) - f94) + f96;
            this.rectFSurahName.set(f95 - (this.ipad_rect.width() * 0.4f), f96, f95, f97);
            this.rectFAya = new RectF();
            float f98 = width33 * 0.7f;
            float centerX4 = this.ipad_rect.centerX() - (f98 * 0.5f);
            float f99 = ((0.35f * height27) - f94) + f97;
            this.rectFAya.set(centerX4, f97, f98 + centerX4, f99);
            this.rectFProgress = new RectF();
            float f100 = (0.22f * height27) + f99;
            this.rectFProgress.set(centerX3, f99, this.rectFSurahName.right, f100);
            this.rectFAya.bottom = this.rectFProgress.centerY();
            this.rectFLecture = new RectF();
            this.rectFLecture.set(centerX3, f100, this.rectFSurahName.right, (height27 * 0.25f) + f100);
        } else {
            this.paintIpad.setStyle(Paint.Style.FILL);
        }
        if (this.mIpadType == IpadType.BOTTOM_RECT.ordinal() || this.mIpadType == IpadType.ROUND_RECT.ordinal()) {
            this.linePaint.setStrokeWidth(this.rectFProgress.height() * 0.02f);
        } else {
            this.linePaint.setStrokeWidth(this.rectFProgress.height() * 0.03f);
        }
        this.radius_cursur = this.linePaint.getStrokeWidth() * 4.2f;
    }

    public void createRectWithoutSurahName() {
        if (this.mIpadType == IpadType.IPAD_CLASSIC.ordinal()) {
            float height = this.ipad_rect.height() - this.bitmapSquare.getHeight();
            float f = height * 0.03f;
            float centerX = this.ipad_rect.centerX() - (this.bitmapSquare.getWidth() * 0.5f);
            float height2 = this.ipad_rect.top + f + this.bitmapSquare.getHeight();
            this.rectFSurahName = new RectF();
            float width = this.bitmapSquare.getWidth() + centerX;
            float f2 = height2 + (f * 1.5f);
            float f3 = ((height * 0.2f) - f) + f2;
            this.rectFSurahName.set(width - (this.ipad_rect.width() * 0.4f), f2, width, f3);
            this.rectFAya = new RectF();
            float f4 = ((height * 0.35f) - f) + f3;
            this.rectFAya.set(centerX, f3, this.bitmapSquare.getWidth() + centerX, f4);
            this.rectFProgress = new RectF();
            float f5 = (height * 0.15f) + f4;
            this.rectFProgress.set(centerX, f4, this.rectFAya.right, f5);
            this.rectFAya.bottom = this.rectFProgress.centerY();
            this.rectFLecture = new RectF();
            this.rectFLecture.set(centerX, f5, this.rectFAya.right, (height * 0.25f) + f5);
        }
        if (this.mIpadType == IpadType.IPAD_NEOMORPHIC.ordinal()) {
            float height3 = this.ipad_rect.height() * 0.6f;
            float f6 = height3 * 0.03f;
            float width2 = this.ipad_rect.top + (this.ipad_rect.width() * 0.3f * 2.4f);
            this.rectFSurahName = new RectF();
            float width3 = this.ipad_rect.width() * 0.5f * 0.5f;
            float f7 = width2 + (2.8f * f6);
            this.rectFSurahName.set(this.ipad_rect.centerX() - width3, f7, this.ipad_rect.centerX() + width3, (this.ipad_rect.height() * 0.1f) + f7);
            float centerY = this.rectFSurahName.centerY() + (2.0f * f6);
            RectF rectF = new RectF();
            this.rectFAya = rectF;
            float f8 = ((height3 * 0.25f) - f6) + centerY;
            rectF.set(this.ipad_rect.left + f6, centerY, this.ipad_rect.right - f6, f8);
            float f9 = f8 + (0.055f * height3);
            this.rectFProgress = new RectF();
            float f10 = (height3 * 0.2f) + f9;
            float width4 = this.bitmapSquare.getWidth() * 0.65f;
            this.rectFProgress.set(this.ipad_rect.centerX() - width4, f9, this.ipad_rect.centerX() + width4, f10);
            this.rectFAya.bottom = this.rectFProgress.centerY();
            this.rectFLecture = new RectF();
            float width5 = this.bitmapSquare.getWidth() * 0.1f;
            this.rectFLecture.set(this.rectFProgress.left - width5, f10 + f6, this.rectFProgress.right + width5, this.ipad_rect.bottom);
        }
        if (this.mIpadType == IpadType.IPAD.ordinal() || this.mIpadType == IpadType.IPAD_UNBLUR.ordinal()) {
            float height4 = this.ipad_rect.height() - this.bitmapSquare.getHeight();
            float f11 = height4 * 0.03f;
            float centerX2 = this.ipad_rect.centerX() - (this.bitmapSquare.getWidth() * 0.5f);
            float height5 = this.ipad_rect.top + f11 + this.bitmapSquare.getHeight();
            this.rectFSurahName = new RectF();
            float width6 = this.bitmapSquare.getWidth() + centerX2;
            float f12 = height5 + (f11 * 1.5f);
            float f13 = ((height4 * 0.2f) - f11) + f12;
            this.rectFSurahName.set(width6 - (this.ipad_rect.width() * 0.4f), f12, width6, f13);
            this.rectFAya = new RectF();
            float f14 = ((height4 * 0.35f) - f11) + f13;
            this.rectFAya.set(centerX2, f13, this.bitmapSquare.getWidth() + centerX2, f14);
            this.rectFProgress = new RectF();
            float f15 = (height4 * 0.15f) + f14;
            this.rectFProgress.set(centerX2, f14, this.rectFAya.right, f15);
            this.rectFAya.bottom = this.rectFProgress.centerY();
            this.rectFLecture = new RectF();
            this.rectFLecture.set(centerX2, f15, this.rectFAya.right, (height4 * 0.25f) + f15);
        }
        if (this.mIpadType == IpadType.ROUND_RECT.ordinal()) {
            float width7 = this.ipad_rect.width() * 0.07f;
            float f16 = this.ipad_rect.left + width7;
            float f17 = this.ipad_rect.top + width7;
            this.rectFSurahName = new RectF();
            float width8 = this.ipad_rect.width() * 0.52f;
            float height6 = this.ipad_rect.height() * 0.25f;
            float f18 = this.ipad_rect.right - width7;
            float f19 = height6 + f17;
            this.rectFSurahName.set(f18 - width8, f17, f18, f19);
            this.rectFAya = new RectF();
            float width9 = this.ipad_rect.width() * 0.02f;
            float f20 = this.ipad_rect.left + width9;
            float f21 = this.ipad_rect.right - width9;
            float height7 = (this.ipad_rect.height() * 0.3f) + f19;
            this.rectFAya.set(f20, f19, f21, height7);
            this.rectFProgress = new RectF();
            float height8 = (this.ipad_rect.height() * 0.168f) + height7;
            this.rectFProgress.set(f16, height7, f18, height8);
            this.rectFAya.bottom = this.rectFProgress.centerY();
            this.rectFLecture = new RectF();
            this.rectFLecture.set(f16, height8, f18, this.ipad_rect.bottom - (width7 * 0.75f));
        }
        if (this.mIpadType == IpadType.RECT.ordinal()) {
            float width10 = this.ipad_rect.width() * 0.05f;
            float f22 = this.ipad_rect.left + width10;
            float f23 = this.ipad_rect.top + width10;
            this.rectFSurahName = new RectF();
            float width11 = this.ipad_rect.width() * 0.52f;
            float height9 = this.ipad_rect.height() * 0.25f;
            float f24 = this.ipad_rect.right - width10;
            float f25 = height9 + f23;
            this.rectFSurahName.set(f24 - width11, f23, f24, f25);
            this.rectFAya = new RectF();
            float height10 = (this.ipad_rect.height() * 0.3f) + f25;
            this.rectFAya.set(f22, f25, f24, height10);
            this.rectFProgress = new RectF();
            float height11 = (this.ipad_rect.height() * 0.18f) + height10;
            this.rectFProgress.set(f22, height10, this.rectFAya.right, height11);
            this.rectFAya.bottom = this.rectFProgress.centerY();
            this.rectFLecture = new RectF();
            this.rectFLecture.set(f22, height11, this.rectFAya.right, this.ipad_rect.bottom - width10);
        }
        if (this.mIpadType == IpadType.BOTTOM_RECT.ordinal()) {
            float width12 = this.ipad_rect.width() * 0.005f;
            float width13 = this.ipad_rect.left + (this.ipad_rect.width() * 0.025f);
            float f26 = this.ipad_rect.top + width12;
            this.rectFSurahName = new RectF();
            float width14 = this.ipad_rect.width() * 0.37f;
            float height12 = this.ipad_rect.height() * 0.2f;
            float width15 = this.ipad_rect.right - (this.ipad_rect.width() * 0.015f);
            float f27 = height12 + f26;
            this.rectFSurahName.set(width15 - width14, f26, width15, f27);
            float f28 = f27 + width12;
            float min = Math.min(this.ipad_rect.height(), this.ipad_rect.width());
            RectF rectF2 = new RectF();
            this.rectFAya = rectF2;
            float f29 = (min * 0.25f) + f28;
            rectF2.set(width13, f28, width15, f29);
            this.rectFProgress = new RectF();
            this.rectFProgress.set(width13, f29, this.rectFAya.right, (min * 0.3f) + f29);
            float f30 = this.rectFProgress.bottom - (width12 * 2.5f);
            this.rectFLecture = new RectF();
            this.rectFLecture.set(width13, f30, this.rectFAya.right, this.rectFAya.height() + f30);
            this.rectFAya.bottom = this.rectFProgress.top * 1.025f;
        }
        if (this.mIpadType == IpadType.BLACK_LAYER.ordinal()) {
            float f31 = this.mResizetype == ResizeType.SQUARE.ordinal() ? 0.34f : 0.4f;
            float width16 = this.ipad_rect.width() * 0.014f;
            float f32 = this.ipad_rect.left + width16;
            float f33 = this.ipad_rect.top + width16;
            this.rectFSurahName = new RectF();
            float min2 = Math.min(this.ipad_rect.width(), this.ipad_rect.height());
            float height13 = this.ipad_rect.height() * 0.12f;
            float f34 = this.ipad_rect.right - width16;
            this.rectFSurahName.set(f34 - (f31 * min2), f33, f34, height13 + f33);
            float centerY2 = this.ipad_rect.centerY() * 0.8f;
            this.rectFAya = new RectF();
            float f35 = min2 * 0.15f;
            this.rectFAya.set(this.ipad_rect.left + f35, centerY2, this.ipad_rect.right - f35, centerY2 + (min2 * 0.3f));
            float centerY3 = this.rectFAya.centerY();
            RectF rectF3 = new RectF();
            this.rectFProgress = rectF3;
            float f36 = (min2 * 0.168f) + centerY3;
            rectF3.set(this.rectFAya.left, centerY3, this.rectFAya.right, f36);
            this.rectFLecture = new RectF();
            this.rectFLecture.set(f32, f36, f34, this.ipad_rect.bottom - (width16 * 0.75f));
        }
        if (this.mIpadType == IpadType.HEART.ordinal() || this.mIpadType == IpadType.BATTERY.ordinal()) {
            if (this.mResizetype == ResizeType.SOCIAL_STORY.ordinal()) {
                float width17 = this.ipad_rect.width() * 0.014f;
                float f37 = this.ipad_rect.left + width17;
                float f38 = this.ipad_rect.top + width17;
                this.rectFSurahName = new RectF();
                float min3 = Math.min(this.ipad_rect.width(), this.ipad_rect.height());
                float height14 = this.ipad_rect.height() * 0.09f;
                float f39 = this.ipad_rect.right - width17;
                this.rectFSurahName.set(f39 - (min3 * 0.4f), f38, f39, height14 + f38);
                float centerY4 = this.ipad_rect.centerY() * 0.3f;
                this.rectFAya = new RectF();
                float f40 = min3 * 0.15f;
                this.rectFAya.set(this.ipad_rect.left + f40, centerY4, this.ipad_rect.right - f40, (min3 * 0.3f) + centerY4);
                this.rectFProgress = new RectF();
                float width18 = this.rectFAya.width();
                float centerY5 = this.ipad_rect.centerY() - (this.rectFAya.width() * 0.5f);
                float f41 = width18 + centerY5;
                this.rectFProgress.set(this.rectFAya.left, centerY5, this.rectFAya.right, f41);
                this.rectFLecture = new RectF();
                this.rectFLecture.set(f37, f41, f39, this.ipad_rect.bottom - (width17 * 0.75f));
            }
            if (this.mResizetype == ResizeType.YOUTUBE_THUMBNAIL.ordinal()) {
                float width19 = this.ipad_rect.width() * 0.014f;
                float f42 = this.ipad_rect.left + width19;
                float f43 = this.ipad_rect.top + width19;
                this.rectFSurahName = new RectF();
                float min4 = Math.min(this.ipad_rect.width(), this.ipad_rect.height());
                float height15 = this.ipad_rect.height() * 0.15f;
                float f44 = this.ipad_rect.right - width19;
                this.rectFSurahName.set(f44 - (min4 * 0.4f), f43, f44, height15 + f43);
                float centerY6 = this.ipad_rect.centerY() * 0.34f;
                this.rectFAya = new RectF();
                float f45 = 0.55f * min4;
                this.rectFAya.set(this.ipad_rect.left + f45, centerY6, this.ipad_rect.right - f45, (min4 * 0.3f) + centerY6);
                this.rectFProgress = new RectF();
                float centerY7 = this.rectFAya.centerY() * 1.3f;
                float f46 = this.ipad_rect.bottom * 0.9f;
                this.rectFProgress.set(this.rectFAya.left, centerY7, this.rectFAya.right, f46);
                this.rectFLecture = new RectF();
                this.rectFLecture.set(f42, f46, f44, this.ipad_rect.bottom - (width19 * 0.75f));
            }
            if (this.mResizetype == ResizeType.SQUARE.ordinal()) {
                float width20 = this.ipad_rect.width() * 0.014f;
                float f47 = this.ipad_rect.left + width20;
                float f48 = this.ipad_rect.top + width20;
                this.rectFSurahName = new RectF();
                float min5 = Math.min(this.ipad_rect.width(), this.ipad_rect.height());
                float height16 = this.ipad_rect.height() * 0.09f;
                float f49 = this.ipad_rect.right - width20;
                this.rectFSurahName.set(f49 - (min5 * 0.34f), f48, f49, height16 + f48);
                float centerY8 = this.ipad_rect.centerY() * 0.3f;
                this.rectFAya = new RectF();
                float f50 = min5 * 0.25f;
                this.rectFAya.set(this.ipad_rect.left + f50, centerY8, this.ipad_rect.right - f50, (min5 * 0.3f) + centerY8);
                this.rectFProgress = new RectF();
                float centerY9 = this.rectFAya.centerY() * 1.3f;
                float f51 = this.ipad_rect.bottom * 0.9f;
                this.rectFProgress.set(this.rectFAya.left, centerY9, this.rectFAya.right, f51);
                this.rectFLecture = new RectF();
                this.rectFLecture.set(f47, f51, f49, this.ipad_rect.bottom - (width20 * 0.75f));
            }
        }
        if (this.mIpadType == IpadType.CASSET.ordinal() || this.mIpadType == IpadType.CASSET_IMG.ordinal() || this.mIpadType == IpadType.CASSET_IMG_BLUR.ordinal()) {
            float width21 = this.ipad_rect.width() * 0.012f;
            float f52 = this.ipad_rect.top + width21;
            this.rectFSurahName = new RectF();
            float min6 = Math.min(this.ipad_rect.width(), this.ipad_rect.height()) * 0.45f;
            float height17 = this.ipad_rect.height() * 0.19f;
            float f53 = this.ipad_rect.right - width21;
            this.rectFSurahName.set(f53 - min6, f52, f53, height17 + f52);
            float width22 = this.ipad_rect.width() * 0.33f;
            float height18 = this.ipad_rect.height() * 0.2f;
            float f54 = this.ipad_rect.top + height18;
            this.rectFAya = new RectF(this.ipad_rect.centerX() - width22, f54, this.ipad_rect.centerX() + width22, height18 + f54);
            this.rectFProgress = new RectF();
            this.rectFLecture = new RectF();
        }
        if (this.mIpadType == IpadType.GRADIENT.ordinal()) {
            float width23 = this.ipad_rect.width() * 0.014f;
            float f55 = this.ipad_rect.left + width23;
            this.ipad_rect.centerY();
            this.rectFSurahName = new RectF();
            float min7 = Math.min(this.ipad_rect.width(), this.ipad_rect.height());
            float f56 = this.ipad_rect.right - width23;
            this.rectFAya = new RectF();
            float f57 = min7 * 0.15f;
            float f58 = this.ipad_rect.left + f57;
            float f59 = this.ipad_rect.right - f57;
            float f60 = min7 * 0.3f;
            float f61 = this.ipad_rect.bottom * 0.95f;
            this.rectFAya.set(f58, f61 - f60, f59, f61);
            this.rectFSurahName.set(f56 - (min7 * 0.52f), this.rectFAya.centerY() - f60, f56, this.rectFAya.centerY());
            float centerY10 = this.rectFAya.centerY();
            RectF rectF4 = new RectF();
            this.rectFProgress = rectF4;
            float f62 = (min7 * 0.168f) + centerY10;
            rectF4.set(this.rectFAya.left, centerY10, this.rectFAya.right, f62);
            this.rectFLecture = new RectF();
            this.rectFLecture.set(f55, f62, f56, this.ipad_rect.bottom - (width23 * 0.75f));
            this.rectFSurahName.bottom = this.rectFAya.top;
        }
        if (this.mIpadType == IpadType.MASK_BRUSH.ordinal()) {
            if (this.mResizetype == ResizeType.SOCIAL_STORY.ordinal()) {
                float width24 = this.ipad_rect.width() * 0.014f;
                float f63 = this.ipad_rect.left + width24;
                float f64 = this.ipad_rect.top + width24;
                this.rectFSurahName = new RectF();
                float width25 = this.ipad_rect.width();
                float height19 = this.ipad_rect.height() * 0.09f;
                float f65 = this.ipad_rect.right;
                this.rectFSurahName.set(f65 - (width25 * 0.4f), f64, f65, height19 + f64);
                float height20 = this.ipad_rect.height() * 0.19f;
                this.rectFAya = new RectF();
                float f66 = width25 * 0.15f;
                this.rectFAya.set(this.ipad_rect.left + f66, height20, this.ipad_rect.right - f66, (width25 * 0.3f) + height20);
                float centerY11 = this.rectFAya.centerY();
                RectF rectF5 = new RectF();
                this.rectFProgress = rectF5;
                float f67 = (width25 * 0.168f) + centerY11;
                rectF5.set(this.rectFAya.left, centerY11, this.rectFAya.right, f67);
                this.rectFLecture = new RectF();
                this.rectFLecture.set(f63, f67, f65, this.ipad_rect.bottom - (width24 * 0.75f));
            }
            if (this.mResizetype == ResizeType.SQUARE.ordinal()) {
                float width26 = this.ipad_rect.width() * 0.014f;
                float f68 = this.ipad_rect.left + width26;
                float f69 = this.ipad_rect.top + width26;
                this.rectFSurahName = new RectF();
                float width27 = this.ipad_rect.width();
                float height21 = this.ipad_rect.height() * 0.09f;
                float f70 = this.ipad_rect.right;
                this.rectFSurahName.set(f70 - (width27 * 0.25f), f69, f70, height21 + f69);
                float height22 = this.ipad_rect.height() * 0.11f;
                this.rectFAya = new RectF();
                float f71 = width27 * 0.15f;
                this.rectFAya.set(this.ipad_rect.left + f71, height22, this.ipad_rect.right - f71, (width27 * 0.3f) + height22);
                float centerY12 = this.rectFAya.centerY();
                RectF rectF6 = new RectF();
                this.rectFProgress = rectF6;
                float f72 = (width27 * 0.168f) + centerY12;
                rectF6.set(this.rectFAya.left, centerY12, this.rectFAya.right, f72);
                this.rectFLecture = new RectF();
                this.rectFLecture.set(f68, f72, f70, this.ipad_rect.bottom - (width26 * 0.75f));
            }
            if (this.mResizetype == ResizeType.YOUTUBE_THUMBNAIL.ordinal()) {
                float width28 = this.ipad_rect.width() * 0.014f;
                float f73 = this.ipad_rect.left + width28;
                float f74 = this.ipad_rect.top + width28;
                this.rectFSurahName = new RectF();
                float width29 = this.ipad_rect.width();
                float height23 = this.ipad_rect.height() * 0.09f;
                float f75 = this.ipad_rect.right;
                this.rectFSurahName.set(f75 - (width29 * 0.25f), f74, f75, height23 + f74);
                float f76 = (-this.rectFSurahName.height()) * 0.5f;
                this.rectFAya = new RectF();
                float f77 = width29 * 0.3f;
                this.rectFAya.set(this.ipad_rect.left + f77, f76, this.ipad_rect.right - f77, f77 + f76);
                float centerY13 = this.rectFAya.centerY();
                RectF rectF7 = new RectF();
                this.rectFProgress = rectF7;
                float f78 = (width29 * 0.168f) + centerY13;
                rectF7.set(this.rectFAya.left, centerY13, this.rectFAya.right, f78);
                this.rectFLecture = new RectF();
                this.rectFLecture.set(f73, f78, f75, this.ipad_rect.bottom - (width28 * 0.75f));
            }
        }
        if (this.mIpadType == IpadType.BLUE_TYPE.ordinal()) {
            if (this.mResizetype == ResizeType.SOCIAL_STORY.ordinal()) {
                float width30 = this.ipad_rect.width() * 0.014f;
                float f79 = this.ipad_rect.left + width30;
                float f80 = this.ipad_rect.top + width30;
                this.rectFSurahName = new RectF();
                float min8 = Math.min(this.ipad_rect.width(), this.ipad_rect.height());
                float height24 = this.ipad_rect.height() * 0.09f;
                float f81 = this.ipad_rect.right - width30;
                this.rectFSurahName.set(f81 - (min8 * 0.4f), f80, f81, height24 + f80);
                float centerY14 = this.ipad_rect.centerY() * 0.3f;
                this.rectFAya = new RectF();
                float f82 = min8 * 0.15f;
                this.rectFAya.set(this.ipad_rect.left + f82, centerY14, this.ipad_rect.right - f82, (min8 * 0.3f) + centerY14);
                float centerY15 = this.ipad_rect.centerY() * 0.2f;
                RectF rectF8 = new RectF();
                this.rectFProgress = rectF8;
                float f83 = (min8 * 0.168f) + centerY15;
                rectF8.set(this.rectFAya.left, centerY15, this.rectFAya.right, f83);
                this.rectFLecture = new RectF();
                this.rectFLecture.set(f79, f83, f81, this.ipad_rect.bottom - (width30 * 0.75f));
            }
            if (this.mResizetype == ResizeType.YOUTUBE_THUMBNAIL.ordinal()) {
                float width31 = this.ipad_rect.width() * 0.014f;
                float f84 = this.ipad_rect.left + width31;
                float f85 = this.ipad_rect.top + width31;
                this.rectFSurahName = new RectF();
                float min9 = Math.min(this.ipad_rect.width(), this.ipad_rect.height());
                float f86 = min9 * 0.4f;
                float height25 = this.ipad_rect.height() * 0.15f;
                float f87 = this.ipad_rect.right - width31;
                this.rectFSurahName.set(f87 - f86, f85, f87, height25 + f85);
                float centerY16 = this.ipad_rect.centerY() * 0.34f;
                this.rectFAya = new RectF();
                this.rectFAya.set(this.ipad_rect.left + f86, centerY16, this.ipad_rect.right - f86, (min9 * 0.3f) + centerY16);
                float centerY17 = this.ipad_rect.centerY() * 0.2f;
                RectF rectF9 = new RectF();
                this.rectFProgress = rectF9;
                float f88 = (min9 * 0.168f) + centerY17;
                rectF9.set(this.rectFAya.left, centerY17, this.rectFAya.right, f88);
                this.rectFLecture = new RectF();
                this.rectFLecture.set(f84, f88, f87, this.ipad_rect.bottom - (width31 * 0.75f));
            }
            if (this.mResizetype == ResizeType.SQUARE.ordinal()) {
                float width32 = this.ipad_rect.width() * 0.014f;
                float f89 = this.ipad_rect.left + width32;
                float f90 = this.ipad_rect.top + width32;
                this.rectFSurahName = new RectF();
                float min10 = Math.min(this.ipad_rect.width(), this.ipad_rect.height());
                float height26 = this.ipad_rect.height() * 0.09f;
                float f91 = this.ipad_rect.right - width32;
                this.rectFSurahName.set(f91 - (0.34f * min10), f90, f91, height26 + f90);
                float centerY18 = this.ipad_rect.centerY() * 0.3f;
                this.rectFAya = new RectF();
                float f92 = min10 * 0.2f;
                this.rectFAya.set(this.ipad_rect.left + f92, centerY18, this.ipad_rect.right - f92, (0.3f * min10) + centerY18);
                float centerY19 = this.ipad_rect.centerY() * 0.2f;
                RectF rectF10 = new RectF();
                this.rectFProgress = rectF10;
                float f93 = (min10 * 0.16f) + centerY19;
                rectF10.set(this.rectFAya.left, centerY19, this.rectFAya.right, f93);
                this.rectFLecture = new RectF();
                this.rectFLecture.set(f89, f93, f91, this.ipad_rect.bottom - (width32 * 0.75f));
            }
        }
        if (this.mIpadType == IpadType.BORDER.ordinal()) {
            float height27 = this.ipad_rect.height() - this.bitmapSquare.getHeight();
            float f94 = height27 * 0.03f;
            float width33 = this.ipad_rect.width() * 0.8f;
            float centerX3 = this.ipad_rect.centerX() - (width33 * 0.5f);
            float height28 = (this.ipad_rect.top + f94 + this.bitmapSquare.getHeight()) * 0.5f;
            this.rectFSurahName = new RectF();
            float f95 = centerX3 + width33;
            float f96 = height28 + (1.5f * f94);
            float f97 = ((height27 * 0.2f) - f94) + f96;
            this.rectFSurahName.set(f95 - (this.ipad_rect.width() * 0.4f), f96, f95, f97);
            this.rectFAya = new RectF();
            float f98 = width33 * 0.7f;
            float centerX4 = this.ipad_rect.centerX() - (f98 * 0.5f);
            float f99 = ((0.35f * height27) - f94) + f97;
            this.rectFAya.set(centerX4, f97, f98 + centerX4, f99);
            this.rectFProgress = new RectF();
            float f100 = (0.22f * height27) + f99;
            this.rectFProgress.set(centerX3, f99, this.rectFSurahName.right, f100);
            this.rectFAya.bottom = this.rectFProgress.centerY();
            this.rectFLecture = new RectF();
            this.rectFLecture.set(centerX3, f100, this.rectFSurahName.right, (height27 * 0.25f) + f100);
        } else {
            this.paintIpad.setStyle(Paint.Style.FILL);
        }
        if (this.mIpadType == IpadType.BOTTOM_RECT.ordinal() || this.mIpadType == IpadType.ROUND_RECT.ordinal()) {
            this.linePaint.setStrokeWidth(this.rectFProgress.height() * 0.02f);
        } else {
            this.linePaint.setStrokeWidth(this.rectFProgress.height() * 0.03f);
        }
        this.radius_cursur = this.linePaint.getStrokeWidth() * 4.2f;
    }

    public Bitmap getBitmapBlured() {
        return this.bitmapBlured;
    }

    public void reset() {
        Bitmap bitmap = this.bitmapBlured;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.bitmapBlured.recycle();
        }
        Bitmap bitmap2 = this.bitmapSquare;
        if (bitmap2 == null || bitmap2.isRecycled()) {
            return;
        }
        this.bitmapSquare.recycle();
    }

    public void resetWatermark() {
        this.wmAlpha = 1.0f;
        this.wmScale = 1.0f;
        this.wmTranslateY = 0.0f;
        this.isAnimWatermk = false;
    }

    public void animWatermark(float f, float f2, float f3) {
        this.isAnimWatermk = true;
        this.wmAlpha = f;
        this.wmScale = f2;
        this.wmTranslateY = f3;
        invalidate();
    }

    private void drawWattermark(Canvas canvas, boolean z) {
        if (this.bitmapBlured == null || this.ipad_rect == null) {
            return;
        }
        float width = this.ipad_rect.width() * 0.057f;
        float f = 0.27f * width;
        this.paintWattermark.setTextSize(width);
        this.paintWattermark.setAntiAlias(true);
        this.paintWattermark.setColor(-1);
        this.paintWattermark.setAlpha(120);
        Rect rect = new Rect();
        this.paintWattermark.getTextBounds("NurMontage", 0, "NurMontage".length(), rect);
        float width2 = rect.width();
        float height = rect.height();
        float f2 = 3.5f * f;
        float width3 = (this.bitmapBlured.getWidth() - width2) - f2;
        float height2 = canvas.getHeight() - f2;
        canvas.drawText("NurMontage", width3, height2, this.paintWattermark);
        this.paintWattermark.clearShadowLayer();
        if (z || this.isAnimWatermk) {
            return;
        }
        this.mRectWattermark = new RectF(width3 - f, (height2 - height) - f, width3 + width2 + f, height2 + f);
        float f3 = height * 0.9f * 0.5f;
        RectF rectF = new RectF(this.mRectWattermark.right - f3, this.mRectWattermark.top - f3, this.mRectWattermark.right + f3, this.mRectWattermark.top + f3);
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_remove_wattermark);
        if (drawable != null) {
            drawable.setBounds((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom);
            drawable.setAlpha(Angle.LEFT);
            drawable.draw(canvas);
        }
        this.mRectWattermark.union(rectF);
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

    /* JADX WARN: Removed duplicated region for block: B:38:0x0130 A[Catch: all -> 0x01a6, Exception -> 0x01a8, TryCatch #1 {Exception -> 0x01a8, blocks: (B:3:0x0003, B:14:0x0015, B:16:0x0030, B:18:0x0036, B:20:0x0040, B:22:0x004a, B:24:0x0054, B:27:0x0060, B:29:0x006a, B:31:0x006e, B:33:0x0072, B:35:0x0078, B:36:0x012c, B:38:0x0130, B:39:0x0138, B:41:0x0148, B:43:0x014c, B:45:0x0152, B:47:0x0158, B:49:0x015c, B:51:0x0162, B:53:0x016e, B:55:0x0176, B:57:0x0182, B:58:0x0135, B:59:0x0085, B:61:0x008f, B:63:0x0093, B:64:0x00a0, B:66:0x00aa, B:68:0x00b0, B:69:0x00c3, B:70:0x00c9, B:72:0x00d3, B:74:0x00dd, B:76:0x00e7, B:78:0x00f1, B:80:0x00fb, B:81:0x0107, B:82:0x0113, B:84:0x0117, B:86:0x011b, B:88:0x0121, B:89:0x0189, B:91:0x018f, B:93:0x0195, B:94:0x0198), top: B:2:0x0003, outer: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0135 A[Catch: all -> 0x01a6, Exception -> 0x01a8, TryCatch #1 {Exception -> 0x01a8, blocks: (B:3:0x0003, B:14:0x0015, B:16:0x0030, B:18:0x0036, B:20:0x0040, B:22:0x004a, B:24:0x0054, B:27:0x0060, B:29:0x006a, B:31:0x006e, B:33:0x0072, B:35:0x0078, B:36:0x012c, B:38:0x0130, B:39:0x0138, B:41:0x0148, B:43:0x014c, B:45:0x0152, B:47:0x0158, B:49:0x015c, B:51:0x0162, B:53:0x016e, B:55:0x0176, B:57:0x0182, B:58:0x0135, B:59:0x0085, B:61:0x008f, B:63:0x0093, B:64:0x00a0, B:66:0x00aa, B:68:0x00b0, B:69:0x00c3, B:70:0x00c9, B:72:0x00d3, B:74:0x00dd, B:76:0x00e7, B:78:0x00f1, B:80:0x00fb, B:81:0x0107, B:82:0x0113, B:84:0x0117, B:86:0x011b, B:88:0x0121, B:89:0x0189, B:91:0x018f, B:93:0x0195, B:94:0x0198), top: B:2:0x0003, outer: #0 }] */
    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        try {
            if (this.isNotDraw) {
                if (isPlaying() && this.iViewCallback != null) {
                    this.iViewCallback.onDrawFinish();
                }
                return;
            }

            canvas.save();
            canvas.translate(this.mDrawingTranslationX, this.mDrawingTranslationY);
            canvas.clipRect(0, 0, this.mCanvas_width, this.mCanvas_height);
            canvas.drawColor(0xFF000000);

            if (this.bitmapBlured != null && !this.bitmapBlured.isRecycled()) {
                // GRADIENT, MASK_BRUSH, BLACK_LAYER, CASSET_IMG types - draw unblurred bitmap
                if (this.mIpadType == IpadType.GRADIENT.ordinal() ||
                    this.mIpadType == IpadType.MASK_BRUSH.ordinal() ||
                    this.mIpadType == IpadType.BLACK_LAYER.ordinal() ||
                    this.mIpadType == IpadType.CASSET_IMG.ordinal()) {
                    if (!this.isVideo && this.bitmapNotBlur != null && !this.bitmapNotBlur.isRecycled()) {
                        canvas.drawBitmap(this.bitmapNotBlur, this.btmX, this.btmY, this.paint);
                    }
                }
                // BLUE_TYPE - draw with grayscale paint
                else if (this.mIpadType == IpadType.BLUE_TYPE.ordinal()) {
                    if (!this.isVideo && this.bitmapNotBlur != null && !this.bitmapNotBlur.isRecycled()) {
                        canvas.drawBitmap(this.bitmapNotBlur, this.btmX, this.btmY, this.grayscalePaint);
                    }
                }
                // CASSET_IMG_BLUR
                else if (this.mIpadType == IpadType.CASSET_IMG_BLUR.ordinal()) {
                    if (!this.isVideo) {
                        canvas.drawBitmap(this.bitmapBlured, this.btmX, this.btmY, this.paint);
                    }
                }
                // IPAD_CLASSIC - draw gradient or solid color
                else if (this.mIpadType == IpadType.IPAD_CLASSIC.ordinal()) {
                    if (getColor_gradient() != null) {
                        this.paint.setShader(this.linearGradient_classic);
                        canvas.drawPaint(this.paint);
                        this.paint.setShader(null);
                    } else {
                        canvas.drawColor(this.color_bg_type_classic);
                    }
                }
                // IPAD_NEOMORPHIC, HEART, BATTERY, CASSET - no background bitmap needed
                else if (this.mIpadType != IpadType.IPAD_NEOMORPHIC.ordinal() &&
                         this.mIpadType != IpadType.HEART.ordinal() &&
                         this.mIpadType != IpadType.BATTERY.ordinal() &&
                         this.mIpadType != IpadType.CASSET.ordinal()) {
                    // IPAD_UNBLUR
                    if (this.mIpadType == IpadType.IPAD_UNBLUR.ordinal()) {
                        canvas.drawBitmap(this.bitmapNotBlur, this.btmX, this.btmY, this.paint);
                    }
                    // Default: draw blurred bitmap
                    else {
                        canvas.drawBitmap(this.bitmapBlured, this.btmX, this.btmY, this.paint);
                    }
                }
            }

            // Draw iPad frame or progress bar
            if (this.bitmapSquare != null) {
                drawIpad(canvas, true);
            } else {
                drawProgress(canvas);
            }

            // Draw overlay elements
            drawLineHelper(canvas);
            drawBismilah(canvas);
            drawEntity(canvas);
            drawNameSurah(canvas);

            // Draw selection tool
            if (this.entity_select != null && this.selectTool != null && this.entity_select.isVisible()) {
                boolean shouldDrawSelection = true;
                if (!(this.entity_select instanceof SurahNameEntity) && !(this.entity_select instanceof BismilahEntity)) {
                    EntityQuranTimeline quranTimeline = this.entity_select.getEntityQuran();
                    if (quranTimeline != null && quranTimeline.visible()) {
                        shouldDrawSelection = true;
                    } else if (quranTimeline == null) {
                        EntityTrslTimeline trslTimeline = this.entity_select.getEntityTrslTimeline();
                        if (trslTimeline != null && trslTimeline.visible()) {
                            shouldDrawSelection = true;
                        } else {
                            shouldDrawSelection = false;
                        }
                    } else {
                        shouldDrawSelection = false;
                    }
                }
                if (shouldDrawSelection) {
                    this.selectTool.draw(canvas, this.entity_select);
                }
            }

            // Draw watermark if not pro
            if (!isPro() && !isRemoveWattermark()) {
                drawWattermark(canvas, false);
            }

            canvas.restore();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (isPlaying() && this.iViewCallback != null) {
            this.iViewCallback.onDrawFinish();
        }
    }

    private void drawProgress(Canvas canvas) {
        float f = this.rectFProgress.left + ((this.rectFProgress.right - this.rectFProgress.left) * this.progress);
        this.linePaint.setColor(this.color_line_bg);
        canvas.drawLine(this.rectFProgress.left, this.rectFProgress.centerY(), this.rectFProgress.right, this.rectFProgress.centerY(), this.linePaint);
        this.linePaint.setColor(this.paintLecture.getColor());
        canvas.drawLine(this.rectFProgress.left, this.rectFProgress.centerY(), f, this.rectFProgress.centerY(), this.linePaint);
        canvas.drawCircle(f, this.rectFProgress.centerY(), this.radius_cursur, this.linePaint);
        Rect textBounds = new Rect();
        this.paintText.getTextBounds("0:60", 0, 4, textBounds);
        canvas.drawText(this.currentTime, this.rectFProgress.left, this.rectFProgress.bottom, this.paintText);
        canvas.drawText(this.remainingTime, this.rectFProgress.right - textBounds.width(), this.rectFProgress.bottom, this.paintText);
    }

    public float AccelerateDecelerateInterpolator(float f) {
        return ((float) (Math.cos((f + 1.0f) * 3.141592653589793d) / 2.0d)) + 0.5f;
    }

    private void saveProgressBitmap(File file, float f) {
        Bitmap createBitmap = Bitmap.createBitmap((int) this.rectFProgress.width(), (int) this.rectFProgress.height(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        float height = canvas.getHeight() * 0.5f;
        if (this.mIpadType == IpadType.BOTTOM_RECT.ordinal()) {
            this.paintText.setTextSize(Math.min(this.ipad_rect.width(), this.ipad_rect.height()) * 0.07f);
        } else if (this.mIpadType == IpadType.BORDER.ordinal()) {
            this.paintText.setTextSize(Math.min(this.ipad_rect.width(), this.ipad_rect.height()) * 0.027f);
        } else {
            this.paintText.setTextSize(this.ipad_rect.width() * 0.0388f);
        }
        Rect textBounds = new Rect();
        this.paintText.getTextBounds("0:60", 0, 4, textBounds);
        this.newLeft_txt = (this.rectFProgress.width() - textBounds.width()) * 0.964f;
        this.txt_y = canvas.getHeight() * 0.76f;
        this.linePaint.setAntiAlias(false);
        this.linePaint.setStrokeWidth(this.linePaint.getStrokeWidth() * 1.1f);
        this.linePaint.setColor(this.paintLecture.getColor());
        canvas.drawLine(0.0f, height, canvas.getWidth(), height, this.linePaint);
        saveBitmap(createBitmap, file, Common.LINE_BG);
        createBitmap.eraseColor(0);
        this.linePaint.setColor(this.color_line_bg);
        canvas.drawLine(0.0f, height, canvas.getWidth(), height, this.linePaint);
        this.linePaint.setColor(this.paintLecture.getColor());
        this.linePaint.setAntiAlias(true);
        canvas.drawCircle(f, height, f, this.linePaint);
        saveBitmap(createBitmap, file, Common.LINE_PROGESS);
    }

    private void saveProgressCassetBitmap(File file, int i, int i2, Drawable drawable) {
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        drawable.setBounds(0, 0, i, i2);
        drawable.draw(canvas);
        saveBitmap(createBitmap, file, Common.LINE_BG);
    }

    private void saveProgressBitmapTypeIPAD_NEOMORPHIC(File file, Bitmap bitmap) {
        Bitmap createBitmap = Bitmap.createBitmap((int) this.rectFProgress.width(), (int) this.rectFProgress.height(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        this.paintText.setTextSize(this.ipad_rect.width() * 0.0388f);
        Rect textBounds = new Rect();
        this.paintText.getTextBounds("0:60", 0, 4, textBounds);
        this.newLeft_txt = (this.rectFProgress.width() - textBounds.width()) * 0.964f;
        this.txt_y = canvas.getHeight() * 0.76f;
        float height = canvas.getHeight() * 0.5f;
        float height2 = this.rectFProgress.height() * 0.18f;
        float f = 0.5f * height2;
        this.linePaint.setStrokeWidth(f);
        this.linePaint.setColor(this.paintLecture.getColor());
        float height3 = canvas.getHeight();
        float width = canvas.getWidth() * 0.024f;
        canvas.save();
        canvas.clipRect(width, 0.0f, canvas.getWidth(), canvas.getHeight());
        float f2 = height - f;
        float f3 = height + f;
        canvas.drawRoundRect(0.0f, f2, canvas.getWidth(), f3, height3, height3, this.linePaint);
        canvas.restore();
        saveBitmap(createBitmap, file, Common.LINE_BG);
        createBitmap.eraseColor(0);
        canvas.save();
        canvas.clipRect(0.0f, f2, width, f3);
        canvas.drawBitmap(bitmap, new Rect((int) this.rectFProgress.left, (int) this.rectFProgress.top, (int) this.rectFProgress.right, (int) this.rectFProgress.bottom), new Rect(0, 0, createBitmap.getWidth(), createBitmap.getHeight()), (Paint) null);
        canvas.drawRoundRect(0.0f, f2, canvas.getWidth(), f3, height3, height3, this.linePaint);
        canvas.restore();
        saveBitmap(createBitmap, file, Common.LINE_BG_TMP);
        this.linePaint.setStrokeWidth(height2);
        this.linePaint.setColor(this.color_line_bg);
        createBitmap.eraseColor(0);
        canvas.drawRoundRect(0.0f, f2, canvas.getWidth(), f3, height3, height3, this.linePaint);
        saveBitmap(createBitmap, file, Common.LINE_PROGESS);
    }

    private void saveProgressBitmapTypeBlue(File file) {
        Bitmap createBitmap = Bitmap.createBitmap((int) this.rectFProgress.width(), (int) this.rectFProgress.height(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        float height = canvas.getHeight() * 0.5f;
        float height2 = this.rectFProgress.height() * 0.18f;
        this.linePaint.setStrokeWidth(0.5f * height2);
        this.linePaint.setColor(this.paintLecture.getColor());
        canvas.drawLine(0.0f, height, canvas.getWidth(), height, this.linePaint);
        saveBitmap(createBitmap, file, Common.LINE_BG);
        createBitmap.eraseColor(0);
        this.linePaint.setStrokeWidth(height2);
        this.linePaint.setColor(this.color_line_bg);
        canvas.drawLine(0.0f, height, canvas.getWidth(), height, this.linePaint);
        saveBitmap(createBitmap, file, Common.LINE_PROGESS);
    }

    private Pair<Float, Integer> saveProgressBitmapTypeHeart(File file, Bitmap bitmap) {
        LinearGradient linearGradient;
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), (int) this.rectFProgress.height(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Path path = new Path();
        float width = this.rectFProgress.width();
        float height = this.rectFProgress.height();
        float centerX = this.rectFProgress.centerX();
        float height2 = canvas.getHeight() * 0.5f;
        float min = Math.min(width, height) / 2.0f;
        float f = height2 + (0.6f * min);
        path.moveTo(centerX, f);
        float f2 = min * 1.2f;
        float f3 = height2 + (0.1f * min);
        float f4 = min * 0.8f;
        float f5 = height2 - (0.9f * min);
        path.cubicTo(centerX + f2, f3, centerX + f4, f5, centerX, height2 - (0.4f * min));
        path.cubicTo(centerX - f4, f5, centerX - f2, f3, centerX, f);
        path.close();
        RectF rectF = new RectF();
        path.computeBounds(rectF, true);
        float f6 = height2 - (0.536f * min);
        int round = Math.round(rectF.bottom - f6);
        int alpha = this.paintIpad.getAlpha();
        if (getColor_gradient() != null && (linearGradient = this.linearGradient_classic) != null) {
            this.paintIpad.setShader(linearGradient);
            this.paintIpad.setColor(getColor_gradient().getColor());
        } else {
            this.paintIpad.setColor(this.color_ipad);
        }
        this.paintIpad.setAlpha(alpha);
        canvas.drawColor(ViewCompat.MEASURED_STATE_MASK);
        this.paintIpad.setStyle(Paint.Style.FILL);
        this.paintIpad.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawPath(path, this.paintIpad);
        this.paintIpad.setXfermode(null);
        this.paintIpad.setStyle(Paint.Style.STROKE);
        this.paintIpad.setStrokeWidth(min * 0.02f);
        canvas.drawPath(path, this.paintIpad);
        saveBitmap(createBitmap, file, Common.LINE_PROGESS);
        this.paintIpad.setXfermode(null);
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        canvas.drawPaint(this.paintIpad);
        saveBitmap(createBitmap, file, Common.LINE_BG);
        return new Pair<>(Float.valueOf(f6), Integer.valueOf(round));
    }

    private Pair<Float, Point> saveProgressBitmapTypeBattery(File file, Bitmap bitmap) {
        LinearGradient linearGradient;
        LinearGradient linearGradient2;
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), (int) this.rectFProgress.height(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        float width = this.rectFProgress.width();
        float height = this.rectFProgress.height();
        float centerX = this.rectFProgress.centerX();
        float height2 = canvas.getHeight() * 0.5f;
        float min = Math.min(width, height);
        float f = 0.8f * min;
        float f2 = 0.45f * min;
        float f3 = f / 2.0f;
        float f4 = centerX - f3;
        float f5 = f2 / 2.0f;
        float f6 = f3 + centerX;
        float f7 = (0.07f * f) + f6;
        float f8 = 0.25f * f2;
        Path path = new Path();
        RectF rectF = new RectF(f4, height2 - f5, f6, f5 + height2);
        float f9 = 0.05f * min;
        path.addRoundRect(rectF, f9, f9, Path.Direction.CW);
        path.addRect(new RectF(f6, height2 - f8, f7, f8 + height2), Path.Direction.CW);
        Path path2 = new Path();
        float f10 = f * 0.3f;
        float f11 = 0.3f * f10;
        float f12 = f2 * 0.5f * 0.5f;
        path2.moveTo(centerX - f11, height2 - f12);
        path2.lineTo(centerX, height2);
        float f13 = f10 * 0.2f;
        path2.lineTo(centerX - f13, height2);
        path2.lineTo(f11 + centerX, f12 + height2);
        path2.lineTo(centerX, height2);
        path2.lineTo(centerX + f13, height2);
        path2.close();
        canvas.drawColor(ViewCompat.MEASURED_STATE_MASK);
        int alpha = this.paintIpad.getAlpha();
        if (getColor_gradient() != null && (linearGradient2 = this.linearGradient_classic) != null) {
            this.paintIpad.setShader(linearGradient2);
            this.paintIpad.setColor(getColor_gradient().getColor());
        } else {
            this.paintIpad.setColor(this.color_ipad);
        }
        this.paintIpad.setAlpha(alpha);
        this.paintIpad.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        this.paintIpad.setStyle(Paint.Style.FILL);
        canvas.drawPath(path, this.paintIpad);
        this.paintIpad.setXfermode(null);
        this.paintIpad.setStyle(Paint.Style.STROKE);
        this.paintIpad.setStrokeWidth(min * 0.025f);
        canvas.drawPath(path, this.paintIpad);
        this.paintIpad.setXfermode(null);
        this.paintIpad.setStyle(Paint.Style.FILL);
        Shader shader = this.paintIpad.getShader();
        this.paintIpad.setShader(null);
        this.paintIpad.setColor(ViewCompat.MEASURED_STATE_MASK);
        canvas.drawPath(path2, this.paintIpad);
        this.paintIpad.setShader(shader);
        saveBitmap(createBitmap, file, Common.LINE_PROGESS);
        Bitmap createBitmap2 = Bitmap.createBitmap(Math.round((f7 - f4) + (this.paintIpad.getStrokeWidth() * 0.5f)), createBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(createBitmap2);
        if (getColor_gradient() != null && (linearGradient = this.linearGradient_classic) != null) {
            this.paintIpad.setShader(linearGradient);
            this.paintIpad.setColor(getColor_gradient().getColor());
        } else {
            this.paintIpad.setColor(this.color_ipad);
        }
        canvas.drawPaint(this.paintIpad);
        saveBitmap(createBitmap2, file, Common.LINE_BG);
        return new Pair<>(Float.valueOf(f4), new Point(createBitmap2.getWidth(), createBitmap2.getHeight()));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1 */
    /* JADX WARN: Type inference failed for: r0v10 */
    /* JADX WARN: Type inference failed for: r0v11 */
    /* JADX WARN: Type inference failed for: r0v12 */
    /* JADX WARN: Type inference failed for: r0v2, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r0v3 */
    /* JADX WARN: Type inference failed for: r0v4, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Type inference failed for: r0v8 */
    /* JADX WARN: Type inference failed for: r0v9 */
    /* JADX WARN: Type inference failed for: r3v0, types: [android.graphics.Bitmap] */
    private void saveBitmap(Bitmap bitmap, File file, String str) {
        if (file == null) {
            file = getContext().getExternalFilesDir(null);
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(new File(file, str));
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
        }
    }

    public void setProgress(float f) {
        this.progress = f;
        postInvalidate();
    }

    private void drawEntity(Canvas canvas) {
        for (int i = 0; i < this.quranEntities.size(); i++) {
            QuranEntity quranEntity = this.quranEntities.get(i);
            if (quranEntity.isVisible() && quranEntity.getEntityQuran().visible()) {
                quranEntity.draw(canvas);
            }
        }
        for (int i2 = 0; i2 < this.translationEntities.size(); i2++) {
            TranslationQuranEntity translationQuranEntity = this.translationEntities.get(i2);
            if (translationQuranEntity.isVisible() && translationQuranEntity.getEntityTrslTimeline().visible()) {
                translationQuranEntity.draw(canvas);
            }
        }
    }

    public Rect getRectSquare() {
        return this.rectSquare;
    }

    public RectF getRectFAya() {
        return this.rectFAya;
    }

    public RectF getRectFProgress() {
        return this.rectFProgress;
    }

    private void slideInToLeft(Canvas canvas, Bitmap bitmap, QuranEntity quranEntity, File file, int i, int i2) {
        for (int i3 = 1; i3 < i2; i3++) {
            float AccelerateDecelerateInterpolator = AccelerateDecelerateInterpolator(i3 / i2);
            bitmap.eraseColor(0);
            quranEntity.singleDraw(canvas, Math.round(255.0f * AccelerateDecelerateInterpolator), 1.0f - AccelerateDecelerateInterpolator);
            saveBitmap(bitmap, file, "quran_in_" + i + "_" + i3 + ".png");
        }
    }

    private void slideInToRight(Canvas canvas, Bitmap bitmap, QuranEntity quranEntity, File file, int i, int i2) {
        for (int i3 = 1; i3 < i2; i3++) {
            float AccelerateDecelerateInterpolator = AccelerateDecelerateInterpolator(i3 / i2);
            bitmap.eraseColor(0);
            quranEntity.singleDraw(canvas, Math.round(255.0f * AccelerateDecelerateInterpolator), (-1.0f) + AccelerateDecelerateInterpolator);
            saveBitmap(bitmap, file, "quran_in_" + i + "_" + i3 + ".png");
        }
    }

    private void slideOutToRight(Canvas canvas, Bitmap bitmap, QuranEntity quranEntity, File file, int i, int i2) {
        for (int i3 = 1; i3 < i2; i3++) {
            float AccelerateDecelerateInterpolator = AccelerateDecelerateInterpolator(i3 / i2);
            int round = Math.round((1.0f - AccelerateDecelerateInterpolator) * 255.0f);
            bitmap.eraseColor(0);
            quranEntity.singleDraw(canvas, round, AccelerateDecelerateInterpolator);
            saveBitmap(bitmap, file, "quran_out_" + i + "_" + i3 + ".png");
        }
    }

    private void slideOutToLeft(Canvas canvas, Bitmap bitmap, QuranEntity quranEntity, File file, int i, int i2) {
        for (int i3 = 1; i3 < i2; i3++) {
            float AccelerateDecelerateInterpolator = AccelerateDecelerateInterpolator(i3 / i2);
            bitmap.eraseColor(0);
            quranEntity.singleDraw(canvas, Math.round((1.0f - AccelerateDecelerateInterpolator) * 255.0f), AccelerateDecelerateInterpolator * (-1.0f));
            saveBitmap(bitmap, file, "quran_out_" + i + "_" + i3 + ".png");
        }
    }

    private void fadeIn(Canvas canvas, Bitmap bitmap, QuranEntity quranEntity, File file, int i, int i2) {
        for (int i3 = 1; i3 < i2; i3++) {
            int round = Math.round(AccelerateDecelerateInterpolator(i3 / i2) * 255.0f);
            bitmap.eraseColor(0);
            quranEntity.singleDraw(canvas, round);
            saveBitmap(bitmap, file, "quran_in_" + i + "_" + i3 + ".png");
        }
    }

    private void fadeOut(Canvas canvas, Bitmap bitmap, QuranEntity quranEntity, File file, int i, int i2) {
        for (int i3 = 1; i3 < i2; i3++) {
            int round = Math.round(AccelerateDecelerateInterpolator(1.0f - (i3 / i2)) * 255.0f);
            bitmap.eraseColor(0);
            quranEntity.singleDraw(canvas, round);
            saveBitmap(bitmap, file, "quran_out_" + i + "_" + i3 + ".png");
        }
    }

    private void drawEntityBitmap(File file, int i, int i2) {
        updateSizeAyaSave(i, i2);
        updateSizeTrslSave(i, i2);
        updateBismilahEntity(i, i2);
        int i3 = 0;
        for (int i4 = 0; i4 < this.quranEntities.size(); i4++) {
            QuranEntity quranEntity = this.quranEntities.get(i4);
            if (quranEntity.getEntityQuran().visible()) {
                quranEntity.getPaintAya().setAlpha(255);
                quranEntity.getPaintTranslationAya().setAlpha(255);
                Transition transition = quranEntity.getEntityQuran().getTransition();
                float f = i2;
                float f2 = i;
                Bitmap createBitmap = Bitmap.createBitmap((int) ((quranEntity.getCopyRect().right * f2) - (quranEntity.getCopyRect().left * f2)), (int) ((quranEntity.getCopyRect().bottom * f) - (quranEntity.getCopyRect().top * f)), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(createBitmap);
                quranEntity.setupCanvasDraw(canvas);
                quranEntity.singleDraw(canvas);
                quranEntity.getEntityQuran().setFile("quran_" + i3 + ".png");
                saveBitmap(createBitmap, file, quranEntity.getEntityQuran().getFile());
                if (transition != null) {
                    transition.setFromW(createBitmap.getWidth());
                }
                i3++;
                quranEntity.restoreCanvas(canvas);
            }
        }
        int i5 = 0;
        for (int i6 = 0; i6 < this.translationEntities.size(); i6++) {
            TranslationQuranEntity translationQuranEntity = this.translationEntities.get(i6);
            if (translationQuranEntity.getEntityTrslTimeline().visible()) {
                translationQuranEntity.getPaintAya().setAlpha(255);
                Transition transition2 = translationQuranEntity.getEntityTrslTimeline().getTransition();
                float f3 = i2;
                Bitmap createBitmap2 = Bitmap.createBitmap(i, (int) ((translationQuranEntity.getCopyRect().bottom * f3) - (translationQuranEntity.getCopyRect().top * f3)), Bitmap.Config.ARGB_8888);
                Canvas canvas2 = new Canvas(createBitmap2);
                translationQuranEntity.setupCanvasDraw(canvas2);
                translationQuranEntity.singleDraw(canvas2);
                translationQuranEntity.getEntityTrslTimeline().setFile("trs_" + i5 + ".png");
                saveBitmap(createBitmap2, file, translationQuranEntity.getEntityTrslTimeline().getFile());
                if (transition2 != null) {
                    transition2.setFromW(createBitmap2.getWidth());
                }
                i5++;
                translationQuranEntity.restoreCanvas(canvas2);
            }
        }
        BismilahEntity bismilahEntity = this.bismilahEntity;
        if (bismilahEntity != null && bismilahEntity.getBismilahTimeline().visible()) {
            this.bismilahEntity.getPaintAya().setAlpha(255);
            Transition transition3 = this.bismilahEntity.getBismilahTimeline().getTransition();
            float f4 = i2;
            float f5 = i;
            Bitmap createBitmap3 = Bitmap.createBitmap((int) ((this.bismilahEntity.getCopyRect().right * f5) - (this.bismilahEntity.getCopyRect().left * f5)), (int) ((this.bismilahEntity.getCopyRect().bottom * f4) - (this.bismilahEntity.getCopyRect().top * f4)), Bitmap.Config.ARGB_8888);
            Canvas canvas3 = new Canvas(createBitmap3);
            this.bismilahEntity.setupCanvasDraw(canvas3);
            this.bismilahEntity.singleDraw(canvas3);
            this.bismilahEntity.getBismilahTimeline().setFile("bismilah.png");
            saveBitmap(createBitmap3, file, this.bismilahEntity.getBismilahTimeline().getFile());
            if (transition3 != null) {
                transition3.setFromW(createBitmap3.getWidth());
            }
        }
        BismilahEntity bismilahEntity2 = this.mIsti3adhaEntity;
        if (bismilahEntity2 == null || !bismilahEntity2.getBismilahTimeline().visible()) {
            return;
        }
        this.mIsti3adhaEntity.getPaintAya().setAlpha(255);
        Transition transition4 = this.mIsti3adhaEntity.getBismilahTimeline().getTransition();
        float f6 = i2;
        float f7 = i;
        Bitmap createBitmap4 = Bitmap.createBitmap((int) ((this.mIsti3adhaEntity.getCopyRect().right * f7) - (this.mIsti3adhaEntity.getCopyRect().left * f7)), (int) ((this.mIsti3adhaEntity.getCopyRect().bottom * f6) - (this.mIsti3adhaEntity.getCopyRect().top * f6)), Bitmap.Config.ARGB_8888);
        Canvas canvas4 = new Canvas(createBitmap4);
        this.mIsti3adhaEntity.setupCanvasDraw(canvas4);
        this.mIsti3adhaEntity.singleDraw(canvas4);
        this.mIsti3adhaEntity.getBismilahTimeline().setFile("mIstiada.png");
        saveBitmap(createBitmap4, file, this.mIsti3adhaEntity.getBismilahTimeline().getFile());
        if (transition4 != null) {
            transition4.setFromW(createBitmap4.getWidth());
        }
    }

    public RectF getRectFSurahName() {
        return this.rectFSurahName;
    }

    public void drawRectWithShadow(Canvas canvas, RectF rectF, int i, int i2, int i3, int i4, boolean z) {
        int i5;
        if (isGlass()) {
            float min = Math.min(rectF.width(), rectF.height()) * 0.14f;
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(i);
            paint.setMaskFilter(new BlurMaskFilter(i2, BlurMaskFilter.Blur.OUTER));
            paint.setAlpha(80);
            Path path = new Path();
            Path.Direction direction = Path.Direction.CW;
            if (z) {
                path.addRoundRect(rectF, min, min, direction);
            } else {
                path.addRect(rectF, direction);
            }
            path.offset(i3, i4);
            canvas.drawPath(path, paint);
            Gradient gradient = this.color_gradient;
            if (gradient != null) {
                i5 = gradient.getColor();
                int argb = Color.argb(70, Color.red(i5), Color.green(i5), Color.blue(i5));
                this.paintIpad.setShader(CreateGradient.createLinearGradientWithAngle(this.ipad_rect, this.color_gradient.getAngle(), new int[]{this.color_gradient.getColor(), this.color_gradient.getSecond(), this.color_gradient.getThree()}, new float[]{0.0f, 0.7f, 1.0f}));
                this.paintIpad.setColor(argb);
            } else {
                i5 = this.color_ipad;
                this.paintIpad.setColor(Color.argb(60, Color.red(i5), Color.green(this.color_ipad), Color.blue(this.color_ipad)));
            }
            this.paintIpad.setStyle(Paint.Style.FILL);
            Paint paint2 = this.paintIpad;
            if (z) {
                canvas.drawRoundRect(rectF, min, min, paint2);
            } else {
                canvas.drawRect(rectF, paint2);
            }
            this.paintIpad.setStyle(Paint.Style.STROKE);
            this.paintIpad.setStrokeWidth(rectF.height() * 0.003f);
            this.paintIpad.setColor(Color.argb(120, Math.min(255, Color.red(i5) + 40), Math.min(255, Color.green(i5) + 40), Math.min(255, Color.blue(i5) + 40)));
            Paint paint3 = this.paintIpad;
            if (z) {
                canvas.drawRoundRect(rectF, min, min, paint3);
            } else {
                canvas.drawRect(rectF, paint3);
            }
            this.paintIpad.setShader(new LinearGradient(rectF.left, rectF.top, rectF.right, rectF.bottom, new int[]{Color.argb(140, 255, 255, 255), Color.argb(10, 255, 255, 255)}, new float[]{0.0f, 1.0f}, Shader.TileMode.CLAMP));
            this.paintIpad.setStyle(Paint.Style.FILL);
            Paint paint4 = this.paintIpad;
            if (z) {
                canvas.drawRoundRect(rectF, min, min, paint4);
            } else {
                canvas.drawRect(rectF, paint4);
            }
            this.paintIpad.setShader(this.color_gradient != null ? this.linearGradient_classic : null);
            this.paintIpad.setColor(this.color_ipad);
            this.paintIpad.setAlpha(190);
            return;
        }
        Paint paint5 = new Paint();
        paint5.setAntiAlias(true);
        paint5.setColor(i);
        paint5.setMaskFilter(new BlurMaskFilter(i2, BlurMaskFilter.Blur.OUTER));
        paint5.setAlpha(80);
        if (z) {
            float min2 = Math.min(rectF.width(), rectF.height()) * 0.14f;
            Path path2 = new Path();
            path2.addRoundRect(rectF, min2, min2, Path.Direction.CW);
            path2.offset(i3, i4);
            canvas.drawPath(path2, paint5);
            canvas.drawRoundRect(rectF, min2, min2, this.paintIpad);
            return;
        }
        Path path3 = new Path();
        path3.addRect(rectF, Path.Direction.CW);
        path3.offset(i3, i4);
        canvas.drawPath(path3, paint5);
        canvas.drawRect(rectF, this.paintIpad);
    }

    public void drawRectBottom(Canvas canvas, RectF rectF) {
        int i;
        if (isGlass()) {
            Math.min(rectF.width(), rectF.height());
            Gradient gradient = this.color_gradient;
            if (gradient != null) {
                i = gradient.getColor();
                int argb = Color.argb(70, Color.red(i), Color.green(i), Color.blue(i));
                this.paintIpad.setShader(CreateGradient.createLinearGradientWithAngle(this.ipad_rect, this.color_gradient.getAngle(), new int[]{this.color_gradient.getColor(), this.color_gradient.getSecond(), this.color_gradient.getThree()}, new float[]{0.0f, 0.7f, 1.0f}));
                this.paintIpad.setColor(argb);
            } else {
                i = this.color_ipad;
                this.paintIpad.setColor(Color.argb(60, Color.red(i), Color.green(this.color_ipad), Color.blue(this.color_ipad)));
            }
            this.paintIpad.setStyle(Paint.Style.FILL);
            canvas.drawRect(rectF, this.paintIpad);
            this.paintIpad.setStyle(Paint.Style.STROKE);
            this.paintIpad.setStrokeWidth(rectF.height() * 0.003f);
            this.paintIpad.setColor(Color.argb(120, Math.min(255, Color.red(i) + 40), Math.min(255, Color.green(i) + 40), Math.min(255, Color.blue(i) + 40)));
            canvas.drawRect(rectF, this.paintIpad);
            this.paintIpad.setShader(new LinearGradient(rectF.left, rectF.top, rectF.right, rectF.bottom, new int[]{Color.argb(140, 255, 255, 255), Color.argb(10, 255, 255, 255)}, new float[]{0.0f, 1.0f}, Shader.TileMode.CLAMP));
            this.paintIpad.setStyle(Paint.Style.FILL);
            canvas.drawRect(rectF, this.paintIpad);
            this.paintIpad.setShader(this.color_gradient != null ? this.linearGradient_classic : null);
            this.paintIpad.setColor(this.color_ipad);
            this.paintIpad.setAlpha(190);
            return;
        }
        canvas.drawRect(rectF, this.paintIpad);
    }

    public float getLeft_square() {
        return this.left_square;
    }

    public float getTop_square() {
        return this.top_square;
    }

    public void drawBitmapWithShadow(Canvas canvas) {
        Bitmap bitmap = this.bitmapSquare;
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        try {
            BlurMaskFilter blurMaskFilter = new BlurMaskFilter(this.bitmapSquare.getWidth() * 0.03f, BlurMaskFilter.Blur.INNER);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(ViewCompat.MEASURED_STATE_MASK);
            paint.setMaskFilter(blurMaskFilter);
            this.left_square = this.ipad_rect.centerX() - (this.bitmapSquare.getWidth() * 0.5f);
            this.top_square = this.ipad_rect.top + (this.bitmapBlured.getHeight() * 0.02f);
            Bitmap bitmap2 = this.bitmapSquare;
            if (bitmap2 != null && !bitmap2.isRecycled()) {
                canvas.drawBitmap(this.bitmapSquare, this.left_square, this.top_square, paint);
            }
            Bitmap bitmap3 = this.bitmapSquare;
            if (bitmap3 == null || bitmap3.isRecycled()) {
                return;
            }
            canvas.drawBitmap(this.bitmapSquare, this.left_square, this.top_square, this.paint);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void drawBitmapWithShadowTypeBottom(Canvas canvas) {
        Bitmap bitmap = this.bitmapSquare;
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        try {
            this.left_square = this.ipad_rect.left;
            this.top_square = this.ipad_rect.top - this.bitmapSquare.getHeight();
            Bitmap bitmap2 = this.bitmapSquare;
            if (bitmap2 == null || bitmap2.isRecycled()) {
                return;
            }
            canvas.drawBitmap(this.bitmapSquare, this.left_square, this.top_square, this.paint);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void drawBitmapWithShadowTypeBottomSave(Canvas canvas) {
        Bitmap bitmap = this.bitmapSquare;
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        try {
            this.left_square = this.ipad_rect.left;
            this.top_square = this.ipad_rect.top - this.bitmapSquare.getHeight();
            Bitmap bitmap2 = this.bitmapSquare;
            if (bitmap2 == null || bitmap2.isRecycled()) {
                return;
            }
            canvas.drawBitmap(this.bitmapSquare, this.left_square, this.top_square, (Paint) null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void drawNeumorphicRect(Canvas canvas, float f, boolean z) {
        int i;
        int lightenColor;
        int darkenColor;
        if (getColor_gradient() != null) {
            this.paint.setShader(this.linearGradient_classic);
            canvas.drawPaint(this.paint);
            this.paint.setShader(null);
            float[] fArr = new float[3];
            Color.colorToHSV(getColor_gradient().getSecond(), fArr);
            fArr[0] = (fArr[0] + getColor_gradient().getAngle()) % 360.0f;
            fArr[1] = Math.min(1.0f, fArr[1] * 1.2f);
            fArr[2] = Math.min(1.0f, fArr[2] * 1.1f);
            i = Color.HSVToColor(fArr);
            lightenColor = ColorUtils.lightenColor(i, 0.4f);
            darkenColor = ColorUtils.darkenColor(i, 0.4f);
        } else {
            canvas.drawColor(this.color_ipad);
            i = this.color_ipad;
            lightenColor = ColorUtils.lightenColor(i, 0.4f);
            darkenColor = ColorUtils.darkenColor(this.color_ipad, 0.4f);
        }
        Paint paint = new Paint(1);
        this.backgroundPaint = paint;
        paint.setColor(i);
        this.backgroundPaint.setStyle(Paint.Style.FILL);
        Paint paint2 = new Paint(1);
        this.lightShadowPaint = paint2;
        paint2.setColor(i);
        this.lightShadowPaint.setStyle(Paint.Style.FILL);
        this.lightShadowPaint.setShadowLayer(5.0f, -5.0f, -5.0f, lightenColor);
        Paint paint3 = new Paint(1);
        this.darkShadowPaint = paint3;
        paint3.setColor(i);
        this.darkShadowPaint.setStyle(Paint.Style.FILL);
        this.darkShadowPaint.setShadowLayer(5.0f, 5.0f, 5.0f, darkenColor);
        this.backgroundPaint.setShader(new LinearGradient(this.ipad_rect.left, this.ipad_rect.top, this.ipad_rect.right, this.ipad_rect.bottom, Color.argb(255, (int) (Color.red(i) * 1.1f), (int) (Color.green(i) * 1.1f), (int) (Color.blue(i) * 1.1f)), Color.argb(255, (int) (Color.red(i) * 0.9f), (int) (Color.green(i) * 0.9f), (int) (Color.blue(i) * 0.9f)), Shader.TileMode.CLAMP));
        canvas.drawRoundRect(this.ipad_rect, f, f, this.lightShadowPaint);
        canvas.drawRoundRect(this.ipad_rect, f, f, this.darkShadowPaint);
        canvas.drawRoundRect(this.ipad_rect, f, f, this.backgroundPaint);
        float centerX = this.ipad_rect.centerX();
        float width = this.ipad_rect.width() * 0.32f;
        float f2 = this.ipad_rect.top + (1.35f * width);
        canvas.drawCircle(centerX, f2, width, this.lightShadowPaint);
        canvas.drawCircle(centerX, f2, width, this.darkShadowPaint);
        canvas.drawCircle(centerX, f2, width, this.backgroundPaint);
        float width2 = this.bitmapSquare.getWidth() * 0.5f;
        float f3 = centerX - width2;
        this.left_square = f3;
        float f4 = f2 - width2;
        this.top_square = f4;
        canvas.drawBitmap(this.bitmapSquare, f3, f4, (Paint) null);
    }

    private void drawCaset(Canvas canvas, boolean z, File file) {
        Shader shader;
        float f;
        float f2;
        int i = this.scheme.screen1;
        int i2 = this.scheme.screen2;
        int i3 = this.scheme.body;
        int i4 = this.scheme.shadow;
        int i5 = this.scheme.label;
        int i6 = this.scheme.accent;
        Shader shader2 = this.paintIpad.getShader();
        this.paintIpad.setShader(null);
        int width = this.bitmapBlured.getWidth();
        int height = this.bitmapBlured.getHeight();
        canvas.drawColor(i);
        Path path = new Path();
        path.moveTo(this.ipad_rect.centerX(), this.ipad_rect.bottom * 0.8f);
        float f3 = width;
        path.lineTo(f3, this.ipad_rect.bottom * 1.065f);
        float f4 = height;
        path.lineTo(f3, f4);
        path.lineTo(0.0f, f4);
        path.lineTo(0.0f, this.ipad_rect.bottom * 1.065f);
        path.close();
        this.paintIpad.setColor(i2);
        canvas.drawPath(path, this.paintIpad);
        float height2 = this.ipad_rect.height() * 0.07f;
        RectF rectF = new RectF(this.ipad_rect.left - (this.ipad_rect.height() * 0.1f), this.ipad_rect.top, this.ipad_rect.right * 1.01f, this.ipad_rect.bottom);
        this.paintIpad.setColor(i4);
        canvas.drawRoundRect(rectF, height2, height2, this.paintIpad);
        this.paintIpad.setColor(i3);
        canvas.drawRoundRect(this.ipad_rect, height2, height2, this.paintIpad);
        Path path2 = new Path();
        float width2 = this.ipad_rect.width() * 0.24f;
        float height3 = this.ipad_rect.height() * 0.14f;
        path2.moveTo(this.ipad_rect.centerX() - width2, this.ipad_rect.top);
        path2.lineTo(this.ipad_rect.centerX() + width2, this.ipad_rect.top);
        float f5 = 0.85f * width2;
        path2.lineTo(this.ipad_rect.centerX() + f5, this.ipad_rect.top + height3);
        path2.lineTo(this.ipad_rect.centerX() - f5, this.ipad_rect.top + height3);
        path2.close();
        this.paintIpad.setColor(i6);
        canvas.drawPath(path2, this.paintIpad);
        Path path3 = new Path();
        path3.moveTo(this.ipad_rect.centerX() - width2, this.ipad_rect.bottom);
        path3.lineTo(this.ipad_rect.centerX() + width2, this.ipad_rect.bottom);
        path3.lineTo(this.ipad_rect.centerX() + f5, this.ipad_rect.bottom - height3);
        path3.lineTo(this.ipad_rect.centerX() - f5, this.ipad_rect.bottom - height3);
        path3.close();
        canvas.drawPath(path3, this.paintIpad);
        float width3 = this.ipad_rect.width() * 0.4f;
        float height4 = this.ipad_rect.height() * 0.2f;
        float f6 = this.ipad_rect.top + height4;
        RectF rectF2 = new RectF(this.ipad_rect.centerX() - width3, f6, this.ipad_rect.centerX() + width3, height4 + f6);
        this.paintIpad.setColor(i5);
        canvas.drawRect(rectF2, this.paintIpad);
        RectF rectF3 = new RectF(this.ipad_rect.centerX() - width3, rectF2.bottom * 1.01f, this.ipad_rect.centerX() + width3, this.ipad_rect.bottom - (1.2f * height3));
        canvas.drawRect(rectF3, this.paintIpad);
        float height5 = rectF3.height() * 0.26f;
        float centerY = rectF3.centerY();
        float f7 = 2.0f * height5;
        float centerX = rectF3.centerX() - f7;
        float centerX2 = rectF3.centerX() + f7;
        this.paintIpad.setColor(ColorUtils.darkenColor(i3, 0.8f));
        canvas.drawCircle(centerX, centerY, height5, this.paintIpad);
        canvas.drawCircle(centerX2, centerY, height5, this.paintIpad);
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_circle_caset);
        drawable.setTint(i2);
        int round = Math.round(centerX);
        int i7 = (int) centerY;
        int i8 = (int) (height5 * 0.75f);
        int i9 = i7 - i8;
        int i10 = i7 + i8;
        Rect rect = new Rect(round - i8, i9, round + i8, i10);
        if (z) {
            if (this.startTime < 0) {
                this.startTime = System.currentTimeMillis();
            }
            shader = shader2;
            f = height3;
            float currentTimeMillis = (float) (((System.currentTimeMillis() - this.startTime) / 1000.0f) * 90.0d);
            canvas.save();
            float f8 = i7;
            canvas.rotate(currentTimeMillis, round, f8);
            drawable.setBounds(rect.left, rect.top, rect.right, rect.bottom);
            drawable.draw(canvas);
            canvas.restore();
            int i11 = (int) centerX2;
            canvas.save();
            canvas.rotate(-currentTimeMillis, i11, f8);
            drawable.setBounds(i11 - i8, i9, i11 + i8, i10);
            drawable.draw(canvas);
            canvas.restore();
            f2 = 0.5f;
        } else {
            shader = shader2;
            f = height3;
            f2 = 0.5f;
            this.rectFProgress.left = centerX - (rect.width() * 0.5f);
            this.rectFProgress.top = rect.top;
            this.rectFProgress.right = centerX2 - (rect.width() * 0.5f);
            saveProgressCassetBitmap(file, rect.width(), rect.height(), drawable);
        }
        float f9 = f * 0.25f;
        float f10 = f * f2;
        int i12 = (int) (this.ipad_rect.top + f10);
        float f11 = this.ipad_rect.bottom - f10;
        float f12 = centerX * 1.02f;
        float f13 = centerX2 * 0.95f;
        this.paintIpad.setColor(-1);
        float f14 = i12;
        canvas.drawCircle(f12, f14, f9, this.paintIpad);
        canvas.drawCircle(f13, f14, f9, this.paintIpad);
        canvas.drawCircle(f12, f11, f9, this.paintIpad);
        canvas.drawCircle(f13, f11, f9, this.paintIpad);
        this.paintIpad.setShader(shader);
    }

    private void drawCasetNoBg(Canvas canvas, boolean z, File file, boolean z2) {
        float f;
        if (z2) {
            canvas.drawBitmap(this.bitmapSquare, 0.0f, 0.0f, (Paint) null);
        }
        int i = this.scheme.screen2;
        int i2 = this.scheme.body;
        int i3 = this.scheme.shadow;
        int i4 = this.scheme.label;
        int i5 = this.scheme.accent;
        Shader shader = this.paintIpad.getShader();
        this.paintIpad.setShader(null);
        float height = this.ipad_rect.height() * 0.07f;
        RectF rectF = new RectF(this.ipad_rect.left - (this.ipad_rect.height() * 0.1f), this.ipad_rect.top, this.ipad_rect.right * 1.01f, this.ipad_rect.bottom);
        this.paintIpad.setColor(i3);
        canvas.drawRoundRect(rectF, height, height, this.paintIpad);
        this.paintIpad.setColor(i2);
        canvas.drawRoundRect(this.ipad_rect, height, height, this.paintIpad);
        Path path = new Path();
        float width = this.ipad_rect.width() * 0.24f;
        float height2 = this.ipad_rect.height() * 0.14f;
        path.moveTo(this.ipad_rect.centerX() - width, this.ipad_rect.top);
        path.lineTo(this.ipad_rect.centerX() + width, this.ipad_rect.top);
        float f2 = 0.85f * width;
        path.lineTo(this.ipad_rect.centerX() + f2, this.ipad_rect.top + height2);
        path.lineTo(this.ipad_rect.centerX() - f2, this.ipad_rect.top + height2);
        path.close();
        this.paintIpad.setColor(i5);
        canvas.drawPath(path, this.paintIpad);
        Path path2 = new Path();
        path2.moveTo(this.ipad_rect.centerX() - width, this.ipad_rect.bottom);
        path2.lineTo(this.ipad_rect.centerX() + width, this.ipad_rect.bottom);
        path2.lineTo(this.ipad_rect.centerX() + f2, this.ipad_rect.bottom - height2);
        path2.lineTo(this.ipad_rect.centerX() - f2, this.ipad_rect.bottom - height2);
        path2.close();
        canvas.drawPath(path2, this.paintIpad);
        float width2 = this.ipad_rect.width() * 0.4f;
        float height3 = this.ipad_rect.height() * 0.2f;
        float f3 = this.ipad_rect.top + height3;
        RectF rectF2 = new RectF(this.ipad_rect.centerX() - width2, f3, this.ipad_rect.centerX() + width2, height3 + f3);
        this.paintIpad.setColor(i4);
        canvas.drawRect(rectF2, this.paintIpad);
        RectF rectF3 = new RectF(this.ipad_rect.centerX() - width2, rectF2.bottom * 1.01f, this.ipad_rect.centerX() + width2, this.ipad_rect.bottom - (1.2f * height2));
        canvas.drawRect(rectF3, this.paintIpad);
        float height4 = rectF3.height() * 0.26f;
        float centerY = rectF3.centerY();
        float f4 = 2.0f * height4;
        float centerX = rectF3.centerX() - f4;
        float centerX2 = rectF3.centerX() + f4;
        this.paintIpad.setColor(ColorUtils.darkenColor(i2, 0.8f));
        canvas.drawCircle(centerX, centerY, height4, this.paintIpad);
        canvas.drawCircle(centerX2, centerY, height4, this.paintIpad);
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_circle_caset);
        drawable.setTint(i);
        int round = Math.round(centerX);
        int i6 = (int) centerY;
        int i7 = (int) (height4 * 0.75f);
        int i8 = i6 - i7;
        int i9 = i6 + i7;
        Rect rect = new Rect(round - i7, i8, round + i7, i9);
        if (z) {
            if (this.startTime < 0) {
                this.startTime = System.currentTimeMillis();
            }
            float currentTimeMillis = (float) (((System.currentTimeMillis() - this.startTime) / 1000.0f) * 90.0d);
            canvas.save();
            float f5 = i6;
            canvas.rotate(currentTimeMillis, round, f5);
            drawable.setBounds(rect.left, rect.top, rect.right, rect.bottom);
            drawable.draw(canvas);
            canvas.restore();
            int i10 = (int) centerX2;
            canvas.save();
            canvas.rotate(-currentTimeMillis, i10, f5);
            drawable.setBounds(i10 - i7, i8, i10 + i7, i9);
            drawable.draw(canvas);
            canvas.restore();
            f = 0.5f;
        } else {
            f = 0.5f;
            this.rectFProgress.left = centerX - (rect.width() * 0.5f);
            this.rectFProgress.top = rect.top;
            this.rectFProgress.right = centerX2 - (rect.width() * 0.5f);
            saveProgressCassetBitmap(file, rect.width(), rect.height(), drawable);
        }
        float f6 = 0.25f * height2;
        float f7 = height2 * f;
        int i11 = (int) (this.ipad_rect.top + f7);
        float f8 = this.ipad_rect.bottom - f7;
        float f9 = centerX * 1.02f;
        float f10 = centerX2 * 0.95f;
        this.paintIpad.setColor(-1);
        float f11 = i11;
        canvas.drawCircle(f9, f11, f6, this.paintIpad);
        canvas.drawCircle(f10, f11, f6, this.paintIpad);
        canvas.drawCircle(f9, f8, f6, this.paintIpad);
        canvas.drawCircle(f10, f8, f6, this.paintIpad);
        this.paintIpad.setShader(shader);
    }

    private void drawInnerGear(Canvas canvas, float f, float f2, float f3, float f4, int i, Paint paint) {
        float f5;
        float f6;
        Path path = new Path();
        int i2 = i * 2;
        double d = 6.283185307179586d / i2;
        for (int i3 = 0; i3 < i2; i3++) {
            double d2 = i3 * d;
            if (i3 % 2 == 0) {
                f5 = f;
                f6 = f3;
            } else {
                f5 = f;
                f6 = f4;
            }
            double d3 = f6;
            float cos = (float) (f5 + (Math.cos(d2) * d3));
            float sin = (float) (f2 + (d3 * Math.sin(d2)));
            if (i3 == 0) {
                path.moveTo(cos, sin);
            } else {
                path.lineTo(cos, sin);
            }
        }
        path.close();
        canvas.drawPath(path, paint);
    }

    private void drawProgressNeumorphic(Canvas canvas) {
        float strokeWidth = this.linePaint.getStrokeWidth();
        this.linePaint.setStrokeWidth(this.rectFProgress.height() * 0.18f);
        float strokeWidth2 = this.linePaint.getStrokeWidth() * 0.5f;
        float width = this.rectFProgress.left + (this.rectFProgress.width() * this.progress);
        this.linePaint.setColor(this.color_line_bg);
        canvas.drawRoundRect(this.rectFProgress.left, this.rectFProgress.centerY() - strokeWidth2, this.rectFProgress.right, this.rectFProgress.centerY() + strokeWidth2, this.rectFProgress.height(), this.rectFProgress.height(), this.linePaint);
        this.linePaint.setColor(this.paintLecture.getColor());
        Paint paint = this.linePaint;
        paint.setStrokeWidth(paint.getStrokeWidth() * 0.5f);
        canvas.drawRoundRect(this.rectFProgress.left, this.rectFProgress.centerY() - strokeWidth2, width, this.rectFProgress.centerY() + strokeWidth2, this.rectFProgress.height(), this.rectFProgress.height(), this.linePaint);
        this.linePaint.setStrokeWidth(strokeWidth);
        Rect rect = new Rect();
        TextPaint textPaint = this.paintText;
        String str = this.currentTime;
        textPaint.getTextBounds(str, 0, str.length(), rect);
        canvas.drawText(this.currentTime, this.rectFProgress.left, this.rectFProgress.bottom, this.paintText);
        canvas.drawText(this.remainingTime, this.rectFProgress.right - rect.width(), this.rectFProgress.bottom, this.paintText);
    }

    private void drawLectureNeumorphic(Canvas canvas) {
        float height = this.rectFLecture.height() * 0.3f;
        Rect rect = new Rect((int) (this.rectFLecture.centerX() - height), (int) (this.rectFLecture.centerY() - height), (int) (this.rectFLecture.centerX() + height), (int) (this.rectFLecture.centerY() + height));
        int width = (int) (rect.width() * 0.15f);
        canvas.drawCircle(rect.centerX(), rect.centerY(), rect.height() * 0.5f, this.lightShadowPaint);
        canvas.drawCircle(rect.centerX(), rect.centerY(), rect.height() * 0.5f, this.darkShadowPaint);
        canvas.drawCircle(rect.centerX(), rect.centerY(), rect.height() * 0.5f, this.backgroundPaint);
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.pause_24px);
        drawable.setTint(this.paintLecture.getColor());
        drawable.setBounds(rect.left + width, rect.top + width, rect.right - width, rect.bottom - width);
        drawable.draw(canvas);
        int height2 = (int) (rect.height() * 0.4f);
        float width2 = rect.width() * 0.55f;
        int i = (int) (rect.right + width2);
        Rect rect2 = new Rect(i, rect.centerY() - height2, i + height2, rect.centerY() + height2);
        int width3 = (int) (rect.width() * 0.15f);
        canvas.drawCircle(rect2.centerX(), rect2.centerY(), rect2.height() * 0.5f, this.lightShadowPaint);
        canvas.drawCircle(rect2.centerX(), rect2.centerY(), rect2.height() * 0.5f, this.darkShadowPaint);
        canvas.drawCircle(rect2.centerX(), rect2.centerY(), rect2.height() * 0.5f, this.backgroundPaint);
        Drawable drawable2 = ContextCompat.getDrawable(getContext(), R.drawable.arrow_forward_ios_24px);
        drawable2.setTint(this.paintLecture.getColor());
        drawable2.setBounds(rect2.left, rect2.top + width3, rect2.right, rect2.bottom - width3);
        drawable2.draw(canvas);
        int i2 = (int) (rect.left - width2);
        Rect rect3 = new Rect(i2 - height2, rect.centerY() - height2, i2, rect.centerY() + height2);
        canvas.drawCircle(rect3.centerX(), rect3.centerY(), rect3.height() * 0.5f, this.lightShadowPaint);
        canvas.drawCircle(rect3.centerX(), rect3.centerY(), rect3.height() * 0.5f, this.darkShadowPaint);
        canvas.drawCircle(rect3.centerX(), rect3.centerY(), rect3.height() * 0.5f, this.backgroundPaint);
        Drawable drawable3 = ContextCompat.getDrawable(getContext(), R.drawable.arrow_back_ios_24px);
        drawable3.setTint(this.paintLecture.getColor());
        drawable3.setBounds(rect3.left, rect3.top + width3, rect3.right, rect3.bottom - width3);
        drawable3.draw(canvas);
    }

    private void drawIpad(Canvas canvas, boolean z) {
        if (this.mIpadType == IpadType.IPAD_NEOMORPHIC.ordinal()) {
            drawNeumorphicRect(canvas, this.ipad_rect.width() * 0.12f, false);
            drawLectureNeumorphic(canvas);
            if (z) {
                drawProgressNeumorphic(canvas);
            }
        }
        if (this.mIpadType == IpadType.CASSET.ordinal()) {
            drawCaset(canvas, z, null);
        }
        if (this.mIpadType == IpadType.CASSET_IMG.ordinal() || this.mIpadType == IpadType.CASSET_IMG_BLUR.ordinal()) {
            drawCasetNoBg(canvas, z, null, isVideo());
        }
        if (this.mIpadType == IpadType.IPAD_CLASSIC.ordinal()) {
            canvas.drawRect(this.ipad_rect, this.paintIpad);
            drawBitmapWithShadow(canvas);
            drawLecture(canvas);
            if (z) {
                drawProgress(canvas);
            }
        }
        if (this.mIpadType == IpadType.IPAD.ordinal() || this.mIpadType == IpadType.IPAD_UNBLUR.ordinal()) {
            int min = (int) (Math.min(this.ipad_rect.width(), this.ipad_rect.height()) * 0.03f);
            drawRectWithShadow(canvas, this.ipad_rect, ViewCompat.MEASURED_STATE_MASK, min <= 0 ? 1 : min, 0, 0, true);
            drawBitmapWithShadow(canvas);
            drawLecture(canvas);
            if (z) {
                drawProgress(canvas);
            }
        }
        if (this.mIpadType == IpadType.BOTTOM_RECT.ordinal()) {
            drawRectBottom(canvas, this.ipad_rect);
            drawBitmapWithShadowTypeBottom(canvas);
            drawLecture(canvas);
            if (z) {
                drawProgress(canvas);
            }
        }
        if (this.mIpadType == IpadType.ROUND_RECT.ordinal()) {
            int width = (int) (this.ipad_rect.width() * 0.03f);
            drawRectWithShadow(canvas, this.ipad_rect, ViewCompat.MEASURED_STATE_MASK, width <= 0 ? 1 : width, 0, 0, true);
            drawLecture(canvas);
            if (z) {
                drawProgress(canvas);
            }
        }
        if (this.mIpadType == IpadType.RECT.ordinal() || this.mIpadType == IpadType.BORDER.ordinal()) {
            int width2 = (int) (this.ipad_rect.width() * 0.03f);
            drawRectWithShadow(canvas, this.ipad_rect, ViewCompat.MEASURED_STATE_MASK, width2 <= 0 ? 1 : width2, 0, 0, false);
            drawLecture(canvas);
            if (z) {
                drawProgress(canvas);
            }
        }
        if (this.mIpadType == IpadType.BLACK_LAYER.ordinal()) {
            drawBlackLayer(canvas, z, isVideo());
        }
        if (this.mIpadType == IpadType.BLUE_TYPE.ordinal()) {
            drawBlueType(canvas, z, isVideo());
        }
        if (this.mIpadType == IpadType.HEART.ordinal()) {
            drawHeartType(canvas, z, isVideo());
        }
        if (this.mIpadType == IpadType.BATTERY.ordinal()) {
            drawBatteryType(canvas, z, this.isVideo);
        }
        if (this.mIpadType == IpadType.GRADIENT.ordinal()) {
            drawGradientLayer(canvas, isVideo());
        }
        if (this.mIpadType == IpadType.MASK_BRUSH.ordinal()) {
            drawMaskedBitmap(canvas, this.isVideo);
        }
    }

    private void drawIpad(Canvas canvas, boolean z, boolean z2) {
        if (this.mIpadType == IpadType.IPAD_CLASSIC.ordinal()) {
            canvas.drawRect(this.ipad_rect, this.paintIpad);
            if (z2) {
                this.left_square = this.ipad_rect.centerX() - (this.bitmapSquare.getWidth() * 0.5f);
                this.top_square = this.ipad_rect.top + (this.bitmapBlured.getHeight() * 0.02f);
            } else {
                drawBitmapWithShadow(canvas);
            }
            drawLecture(canvas);
            if (z) {
                drawProgress(canvas);
            }
        }
        if (this.mIpadType == IpadType.IPAD_NEOMORPHIC.ordinal()) {
            drawNeumorphicRect(canvas, this.ipad_rect.width() * 0.12f, z2);
            drawLectureNeumorphic(canvas);
            if (z) {
                drawProgressNeumorphic(canvas);
            }
        }
        if (this.mIpadType == IpadType.CASSET.ordinal()) {
            drawCaset(canvas, z, null);
        }
        if (this.mIpadType == IpadType.CASSET_IMG.ordinal() || this.mIpadType == IpadType.CASSET_IMG_BLUR.ordinal()) {
            drawCasetNoBg(canvas, z, null, isVideo());
        }
        if (this.mIpadType == IpadType.IPAD.ordinal() || this.mIpadType == IpadType.IPAD_UNBLUR.ordinal()) {
            int min = (int) (Math.min(this.ipad_rect.width(), this.ipad_rect.height()) * 0.03f);
            drawRectWithShadow(canvas, this.ipad_rect, ViewCompat.MEASURED_STATE_MASK, min <= 0 ? 1 : min, 0, 0, true);
            if (z2) {
                this.left_square = this.ipad_rect.centerX() - (this.bitmapSquare.getWidth() * 0.5f);
                this.top_square = this.ipad_rect.top + (this.bitmapBlured.getHeight() * 0.02f);
            } else {
                drawBitmapWithShadow(canvas);
            }
            drawLecture(canvas);
            if (z) {
                drawProgress(canvas);
            }
        }
        if (this.mIpadType == IpadType.BOTTOM_RECT.ordinal()) {
            drawRectBottom(canvas, this.ipad_rect);
            if (z2) {
                drawBitmapWithShadowTypeBottomSave(canvas);
            } else {
                drawBitmapWithShadowTypeBottom(canvas);
            }
            drawLecture(canvas);
            if (z) {
                drawProgress(canvas);
            }
        }
        if (this.mIpadType == IpadType.ROUND_RECT.ordinal()) {
            int width = (int) (this.ipad_rect.width() * 0.03f);
            drawRectWithShadow(canvas, this.ipad_rect, ViewCompat.MEASURED_STATE_MASK, width <= 0 ? 1 : width, 0, 0, true);
            drawLecture(canvas);
            if (z) {
                drawProgress(canvas);
            }
        }
        if (this.mIpadType == IpadType.RECT.ordinal() || this.mIpadType == IpadType.BORDER.ordinal()) {
            int width2 = (int) (this.ipad_rect.width() * 0.03f);
            drawRectWithShadow(canvas, this.ipad_rect, ViewCompat.MEASURED_STATE_MASK, width2 <= 0 ? 1 : width2, 0, 0, false);
            drawLecture(canvas);
            if (z) {
                drawProgress(canvas);
            }
        }
        if (this.mIpadType == IpadType.BLACK_LAYER.ordinal()) {
            drawBlackLayer(canvas, z, z2);
        }
        if (this.mIpadType == IpadType.HEART.ordinal()) {
            drawHeartType(canvas, z, z2);
        }
        if (this.mIpadType == IpadType.BATTERY.ordinal()) {
            drawBatteryType(canvas, z, z2);
        }
        if (this.mIpadType == IpadType.BLUE_TYPE.ordinal()) {
            drawBlueType(canvas, z, isVideo());
        }
        if (this.mIpadType == IpadType.GRADIENT.ordinal()) {
            drawGradientLayer(canvas, z2);
        }
        if (this.mIpadType == IpadType.MASK_BRUSH.ordinal()) {
            drawMaskedBitmap(canvas, z2);
        }
    }

    private void drawMaskedBitmap(Canvas canvas, boolean z) {
        this.paintIpad.setAlpha(255);
        if (z) {
            canvas.drawBitmap(this.bitmapSquare, 0.0f, 0.0f, this.paint);
        }
        Bitmap createBitmap = Bitmap.createBitmap((int) (this.bitmapNotBlur.getWidth() * 1.1f), (int) (this.bitmapNotBlur.getHeight() * 1.1f), Bitmap.Config.ARGB_8888);
        Canvas canvas2 = new Canvas(createBitmap);
        if (getColor_gradient() != null) {
            this.paint.setShader(this.linearGradient_classic);
            canvas2.drawPaint(this.paint);
            this.paint.setShader(null);
        } else {
            canvas2.drawColor(this.paintIpad.getColor());
        }
        int min = (int) (Math.min(createBitmap.getWidth(), createBitmap.getHeight()) * 0.57f);
        Rect rect = new Rect(0, 0, min, min);
        Bitmap createBitmap2 = Bitmap.createBitmap(rect.width(), rect.height(), Bitmap.Config.ARGB_8888);
        canvas2.setBitmap(createBitmap2);
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.brush_mask_2);
        drawable.setBounds(0, 0, rect.width(), rect.height());
        drawable.draw(canvas2);
        Paint paint = new Paint(1);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        canvas2.setBitmap(createBitmap);
        if (this.mResizetype == ResizeType.SOCIAL_STORY.ordinal()) {
            canvas2.drawBitmap(createBitmap2, (createBitmap.getWidth() - createBitmap2.getWidth()) * 0.5f, createBitmap.getHeight() * 0.35f, paint);
        } else {
            canvas2.drawBitmap(createBitmap2, (createBitmap.getWidth() - createBitmap2.getWidth()) * 0.5f, createBitmap.getHeight() * 0.32f, paint);
        }
        canvas.drawBitmap(createBitmap, this.btmX, this.btmY, (Paint) null);
        this.paintIpad.setShader(null);
        this.paintIpad.setAlpha(190);
    }

    private void drawGradientLayer(Canvas canvas, boolean z) {
        if (z) {
            canvas.drawBitmap(this.bitmapSquare, 0.0f, 0.0f, (Paint) null);
        }
        this.paintIpad.setAlpha(255);
        if (getColor_gradient() != null) {
            this.paintIpad.setShader(new LinearGradient(0.0f, this.ipad_rect.top, 0.0f, this.ipad_rect.bottom, new int[]{0, getColor_gradient().getColor(), getColor_gradient().getSecond(), getColor_gradient().getThree()}, new float[]{0.0f, 0.87f, 0.93f, 1.0f}, Shader.TileMode.CLAMP));
        } else {
            this.paintIpad.setShader(new LinearGradient(0.0f, this.ipad_rect.top, 0.0f, this.ipad_rect.bottom, new int[]{0, this.paintIpad.getColor()}, (float[]) null, Shader.TileMode.CLAMP));
        }
        canvas.drawRect(this.ipad_rect, this.paintIpad);
        this.paintIpad.setShader(null);
    }

    private void drawHeartType(Canvas canvas, boolean z, boolean z2) {
        if (z) {
            float strokeWidth = this.linePaint.getStrokeWidth();
            Path path = new Path();
            float width = this.rectFProgress.width();
            float height = this.rectFProgress.height();
            float centerX = this.rectFProgress.centerX();
            float centerY = this.rectFProgress.centerY();
            float min = Math.min(width, height) / 2.0f;
            float f = (0.6f * min) + centerY;
            path.moveTo(centerX, f);
            float f2 = 1.2f * min;
            float f3 = (0.1f * min) + centerY;
            float f4 = min * 0.8f;
            float f5 = centerY - (0.9f * min);
            path.cubicTo(centerX + f2, f3, centerX + f4, f5, centerX, centerY - (0.4f * min));
            path.cubicTo(centerX - f4, f5, centerX - f2, f3, centerX, f);
            path.close();
            RectF rectF = new RectF();
            path.computeBounds(rectF, true);
            canvas.save();
            canvas.clipPath(path);
            float f6 = rectF.bottom;
            canvas.drawRect(rectF.left, f6 - ((rectF.height() * 0.78f) * this.progress), rectF.right, f6, this.paintIpad);
            canvas.restore();
            this.paintIpad.setStyle(Paint.Style.STROKE);
            this.paintIpad.setStrokeWidth(0.02f * min);
            canvas.drawPath(path, this.paintIpad);
            this.paintIpad.setStyle(Paint.Style.FILL);
            this.paintIpad.setStrokeWidth(strokeWidth);
        }
    }

    private void drawBatteryType(Canvas canvas, boolean z, boolean z2) {
        if (z) {
            float strokeWidth = this.linePaint.getStrokeWidth();
            float width = this.rectFProgress.width();
            float height = this.rectFProgress.height();
            float centerX = this.rectFProgress.centerX();
            float centerY = this.rectFProgress.centerY();
            float min = Math.min(width, height);
            float f = 0.8f * min;
            float f2 = 0.45f * min;
            float f3 = f / 2.0f;
            float f4 = centerX - f3;
            float f5 = f2 / 2.0f;
            float f6 = centerY - f5;
            float f7 = f3 + centerX;
            float f8 = f5 + centerY;
            float f9 = (0.07f * f) + f7;
            float f10 = 0.25f * f2;
            Path path = new Path();
            RectF rectF = new RectF(f4, f6, f7, f8);
            float f11 = min * 0.05f;
            path.addRoundRect(rectF, f11, f11, Path.Direction.CW);
            path.addRect(new RectF(f7, centerY - f10, f9, f10 + centerY), Path.Direction.CW);
            Path path2 = new Path();
            float f12 = f * 0.3f;
            float f13 = 0.3f * f12;
            float f14 = f2 * 0.5f * 0.5f;
            path2.moveTo(centerX - f13, centerY - f14);
            path2.lineTo(centerX, centerY);
            float f15 = f12 * 0.2f;
            path2.lineTo(centerX - f15, centerY);
            path2.lineTo(f13 + centerX, f14 + centerY);
            path2.lineTo(centerX, centerY);
            path2.lineTo(centerX + f15, centerY);
            path2.close();
            canvas.save();
            canvas.clipPath(path);
            if (Build.VERSION.SDK_INT >= 26) {
                Path path3 = new Path();
                path3.op(path, path2, Path.Op.DIFFERENCE);
                canvas.clipPath(path3);
            }
            canvas.drawRect(new RectF(f4, f6, ((f9 - f4) * this.progress) + f4, f8), this.paintIpad);
            canvas.restore();
            this.paintIpad.setStyle(Paint.Style.STROKE);
            this.paintIpad.setStrokeWidth(0.025f * min);
            canvas.drawPath(path, this.paintIpad);
            this.paintIpad.setStyle(Paint.Style.FILL);
            this.paintIpad.setStrokeWidth(strokeWidth);
        }
    }

    private void drawBlueType(Canvas canvas, boolean z, boolean z2) {
        this.paintIpad.setColor(ViewCompat.MEASURED_STATE_MASK);
        if (z2 && z) {
            canvas.drawBitmap(this.bitmapSquare, 0.0f, 0.0f, this.grayscalePaint);
        }
        float min = Math.min(this.ipad_rect.width(), this.ipad_rect.height()) * 1.3f;
        this.paintIpad.setAlpha(PsExtractor.VIDEO_STREAM_MASK);
        float f = (-0.15f) * min;
        this.paintIpad.setShader(new RadialGradient(this.ipad_rect.centerX(), f, min, new int[]{ViewCompat.MEASURED_STATE_MASK, -872415232, Integer.MIN_VALUE, 0}, new float[]{0.2f, 0.5f, 0.7f, 1.0f}, Shader.TileMode.CLAMP));
        if (z) {
            canvas.drawCircle(this.ipad_rect.centerX(), f, min, this.paintIpad);
            this.paintIpad.setShader(null);
            this.paintIpad.setAlpha(190);
            float strokeWidth = this.linePaint.getStrokeWidth();
            this.linePaint.setStrokeWidth(this.rectFProgress.height() * 0.18f);
            float width = this.rectFProgress.left + (this.rectFProgress.width() * this.progress);
            this.linePaint.setColor(this.color_line_bg);
            canvas.drawLine(this.rectFProgress.left, this.rectFProgress.centerY(), this.rectFProgress.right, this.rectFProgress.centerY(), this.linePaint);
            this.linePaint.setColor(this.paintLecture.getColor());
            Paint paint = this.linePaint;
            paint.setStrokeWidth(paint.getStrokeWidth() * 0.5f);
            canvas.drawLine(this.rectFProgress.left, this.rectFProgress.centerY(), width, this.rectFProgress.centerY(), this.linePaint);
            this.linePaint.setStrokeWidth(strokeWidth);
            return;
        }
        canvas.drawBitmap(this.bitmapNotBlur, 0.0f, 0.0f, this.grayscalePaint);
        canvas.drawCircle(this.ipad_rect.centerX(), f, min, this.paintIpad);
        this.paintIpad.setShader(null);
        this.paintIpad.setAlpha(190);
    }

    private void drawBlackLayer(Canvas canvas, boolean z, boolean z2) {
        this.paintIpad.setAlpha(204);
        if (z2) {
            canvas.drawBitmap(this.bitmapSquare, 0.0f, 0.0f, (Paint) null);
        }
        canvas.drawRect(this.ipad_rect, this.paintIpad);
        this.paintIpad.setAlpha(190);
    }

    public BismilahEntity getmIsti3adhaEntity() {
        return this.mIsti3adhaEntity;
    }

    public void addIsti3adhaEntity(BismilahEntity bismilahEntity) {
        this.mIsti3adhaEntity = bismilahEntity;
    }

    public BismilahEntity getBismilahEntity() {
        return this.bismilahEntity;
    }

    public void addBismilahEntity(BismilahEntity bismilahEntity) {
        this.bismilahEntity = bismilahEntity;
    }

    public SurahNameEntity getSurahNameEntity() {
        return this.surahNameEntity;
    }

    public EntityView getEntity_select() {
        return this.entity_select;
    }

    public Layout.Alignment updateAlignmentSurah(String str) {
        if (this.mIpadType == IpadType.IPAD_NEOMORPHIC.ordinal() || this.mIpadType == IpadType.CASSET.ordinal() || this.mIpadType == IpadType.CASSET_IMG.ordinal() || this.mIpadType == IpadType.CASSET_IMG_BLUR.ordinal()) {
            return Layout.Alignment.ALIGN_CENTER;
        }
        if (!Utils.isProbablyLArabic(str)) {
            return Layout.Alignment.ALIGN_NORMAL;
        }
        return Layout.Alignment.ALIGN_OPPOSITE;
    }

    public void setSurahNameEntity(String str, String str2, RectF rectF, float f, String str3, int i, int i2, int i3, int i4, boolean z, int i5) {
        Layout.Alignment alignment;
        int i6;
        float width = 0;
        float width2 = 0;
        float width3 = 0;
        float width4 = 0;
        String str4 = str2 == null ? " " : str2;
        if (this.mIpadType == IpadType.IPAD_NEOMORPHIC.ordinal() || this.mIpadType == IpadType.CASSET.ordinal() || this.mIpadType == IpadType.CASSET_IMG.ordinal() || this.mIpadType == IpadType.CASSET_IMG_BLUR.ordinal()) {
            alignment = Layout.Alignment.ALIGN_CENTER;
        } else if (!Utils.isProbablyLArabic(str4)) {
            alignment = Layout.Alignment.ALIGN_NORMAL;
            if (i3 != SurahNameStyle.ZAGHRAFAT.ordinal()) {
                if (this.mIpadType == IpadType.IPAD.ordinal() || this.mIpadType == IpadType.IPAD_UNBLUR.ordinal() || this.mIpadType == IpadType.IPAD_CLASSIC.ordinal()) {
                    float width5 = this.rectFSurahName.width();
                    this.left_square = this.ipad_rect.centerX() - (this.bitmapSquare.getWidth() * 0.5f);
                    if (this.mIpadType == IpadType.IPAD_CLASSIC.ordinal()) {
                        this.rectFSurahName.left = this.left_square;
                    } else {
                        this.rectFSurahName.left = (this.ipad_rect.width() * 0.05f) + this.left_square;
                    }
                    RectF rectF2 = this.rectFSurahName;
                    rectF2.right = rectF2.left + width5;
                } else {
                    if (this.mIpadType == IpadType.BOTTOM_RECT.ordinal()) {
                        width3 = this.ipad_rect.width();
                    } else if (this.mIpadType == IpadType.BLACK_LAYER.ordinal() || this.mIpadType == IpadType.BLUE_TYPE.ordinal() || this.mIpadType == IpadType.GRADIENT.ordinal() || this.mIpadType == IpadType.MASK_BRUSH.ordinal() || this.mIpadType == IpadType.HEART.ordinal() || this.mIpadType == IpadType.BATTERY.ordinal()) {
                        width3 = this.ipad_rect.width();
                    } else {
                        width4 = 0.07f * this.ipad_rect.width();
                        float width6 = this.rectFSurahName.width();
                        this.rectFSurahName.left = width4 + this.ipad_rect.left;
                        RectF rectF3 = this.rectFSurahName;
                        rectF3.right = rectF3.left + width6;
                    }
                    width4 = width3 * 0.015f;
                    float width62 = this.rectFSurahName.width();
                    this.rectFSurahName.left = width4 + this.ipad_rect.left;
                    RectF rectF32 = this.rectFSurahName;
                    rectF32.right = rectF32.left + width62;
                }
            }
        } else {
            alignment = Layout.Alignment.ALIGN_OPPOSITE;
            if (i3 != SurahNameStyle.ZAGHRAFAT.ordinal()) {
                if (this.mIpadType == IpadType.IPAD.ordinal() || this.mIpadType == IpadType.IPAD_UNBLUR.ordinal() || this.mIpadType == IpadType.IPAD_CLASSIC.ordinal()) {
                    float width7 = this.rectFSurahName.width();
                    this.left_square = this.ipad_rect.centerX() - (this.bitmapSquare.getWidth() * 0.5f);
                    if (this.mIpadType == IpadType.IPAD_CLASSIC.ordinal()) {
                        this.rectFSurahName.right = this.left_square + this.bitmapSquare.getWidth();
                    } else {
                        this.rectFSurahName.right = (this.left_square + this.bitmapSquare.getWidth()) - (this.ipad_rect.width() * 0.05f);
                    }
                    RectF rectF4 = this.rectFSurahName;
                    rectF4.left = rectF4.right - width7;
                } else {
                    if (this.mIpadType == IpadType.BOTTOM_RECT.ordinal()) {
                        width = this.ipad_rect.width();
                    } else if (this.mIpadType == IpadType.BLACK_LAYER.ordinal() || this.mIpadType == IpadType.BLUE_TYPE.ordinal() || this.mIpadType == IpadType.GRADIENT.ordinal() || this.mIpadType == IpadType.MASK_BRUSH.ordinal() || this.mIpadType == IpadType.HEART.ordinal() || this.mIpadType == IpadType.BATTERY.ordinal()) {
                        width = this.ipad_rect.width();
                    } else {
                        width2 = 0.07f * this.ipad_rect.width();
                        float width8 = this.rectFSurahName.width();
                        this.rectFSurahName.right = this.ipad_rect.right - width2;
                        RectF rectF5 = this.rectFSurahName;
                        rectF5.left = rectF5.right - width8;
                    }
                    width2 = width * 0.015f;
                    float width82 = this.rectFSurahName.width();
                    this.rectFSurahName.right = this.ipad_rect.right - width2;
                    RectF rectF52 = this.rectFSurahName;
                    rectF52.left = rectF52.right - width82;
                }
            }
        }
        Layout.Alignment alignment2 = alignment;
        SurahNameEntity surahNameEntity = this.surahNameEntity;
        if (surahNameEntity == null) {
            Typeface loadFontFromAsset = UtilsFileLast.loadFontFromAsset(getContext(), "fonts/arabic/" + str3);
            Typeface loadFontFromAsset2 = UtilsFileLast.loadFontFromAsset(getContext(), "fonts/surah_name.otf");
            if (i == 0) {
                int color = this.paintLecture.getColor();
                if (getmIpadType() == IpadType.CASSET.ordinal() || getmIpadType() == IpadType.CASSET_IMG.ordinal() || this.mIpadType == IpadType.CASSET_IMG_BLUR.ordinal()) {
                    color = ColorUtils.isColorDark(this.scheme.body) ? -1 : ViewCompat.MEASURED_STATE_MASK;
                }
                i6 = color;
            } else {
                i6 = i;
            }
            if (rectF != null) {
                this.rectFSurahName = rectF;
            }
            SurahNameEntity surahNameEntity2 = new SurahNameEntity(alignment2, str, str4, this.rectFSurahName, loadFontFromAsset, i6, f, str3, i2, loadFontFromAsset2, i3, i4, this.mIpadType, z, i5);
            this.surahNameEntity = surahNameEntity2;
            surahNameEntity2.setCanvasWH(getmCanvas_width(), getmCanvas_height());
            if (rectF != null) {
                this.surahNameEntity.move();
                return;
            }
            return;
        }
        surahNameEntity.setIndex_surah(i4);
        this.surahNameEntity.setRect(this.rectFSurahName);
        this.surahNameEntity.setNameAndReader(alignment2, str, str4);
    }

    private void drawBismilah(Canvas canvas) {
        BismilahEntity bismilahEntity = this.bismilahEntity;
        if (bismilahEntity != null && bismilahEntity.isVisible() && this.bismilahEntity.getBismilahTimeline().visible()) {
            this.bismilahEntity.draw(canvas);
        }
        BismilahEntity bismilahEntity2 = this.mIsti3adhaEntity;
        if (bismilahEntity2 != null && bismilahEntity2.isVisible() && this.mIsti3adhaEntity.getBismilahTimeline().visible()) {
            this.mIsti3adhaEntity.draw(canvas);
        }
    }

    private void drawNameSurah(Canvas canvas) {
        SurahNameEntity surahNameEntity = this.surahNameEntity;
        if (surahNameEntity != null) {
            surahNameEntity.draw(canvas);
        }
    }

    private void drawAya(Canvas canvas) {
        this.paintLecture.setColor(InputDeviceCompat.SOURCE_ANY);
        canvas.drawRect(this.rectFAya, this.paintLecture);
    }

    private void drawLecture(Canvas canvas) {
        float height = this.rectFLecture.height() * 0.4f;
        Rect rect = new Rect((int) (this.rectFLecture.centerX() - height), (int) (this.rectFLecture.centerY() - height), (int) (this.rectFLecture.centerX() + height), (int) (this.rectFLecture.centerY() + height));
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.pause_circle_24px);
        drawable.setTint(this.paintLecture.getColor());
        drawable.setBounds(rect.left, rect.top, rect.right, rect.bottom);
        drawable.draw(canvas);
        int height2 = (int) (rect.height() * 0.3f);
        int width = (int) (rect.width() * 0.45f);
        float width2 = rect.width() * 0.29f;
        int i = (int) (rect.right + width2);
        Rect rect2 = new Rect(i, rect.centerY() - height2, i + width, rect.centerY() + height2);
        Drawable drawable2 = ContextCompat.getDrawable(getContext(), R.drawable.skip_next_24px);
        drawable2.setTint(this.paintLecture.getColor());
        drawable2.setBounds(rect2.left, rect2.top, rect2.right, rect2.bottom);
        drawable2.draw(canvas);
        int i2 = (int) (rect.left - width2);
        Rect rect3 = new Rect(i2 - width, rect.centerY() - height2, i2, rect.centerY() + height2);
        Drawable drawable3 = ContextCompat.getDrawable(getContext(), R.drawable.skip_previous_24px);
        drawable3.setTint(this.paintLecture.getColor());
        drawable3.setBounds(rect3.left, rect3.top, rect3.right, rect3.bottom);
        drawable3.draw(canvas);
        int width3 = (int) (rect3.width() * 0.5f);
        int i3 = (int) this.rectFProgress.left;
        int width4 = rect3.width() + i3;
        Drawable drawable4 = ContextCompat.getDrawable(getContext(), R.drawable.favorite_24px);
        drawable4.setTint(this.paintLecture.getColor());
        drawable4.setBounds(i3, rect3.centerY() - width3, width4, rect3.centerY() + width3);
        drawable4.draw(canvas);
        int width5 = ((int) this.rectFProgress.right) - rect3.width();
        Drawable drawable5 = ContextCompat.getDrawable(getContext(), R.drawable.repeat_24px);
        drawable5.setTint(this.paintLecture.getColor());
        drawable5.setBounds(width5, rect3.centerY() - width3, (int) this.rectFProgress.right, rect3.centerY() + width3);
        drawable5.draw(canvas);
    }

    public String setupBitmapDraw(Bitmap bitmapBlured, Bitmap bitmapSquare, Template template) {
        this.frameInterval = (long) (1000 / template.getFps());
        this.bitmapBlured = bitmapBlured;
        this.bitmapSquare = bitmapSquare;

        if (this.surahNameEntity != null) {
            this.surahNameEntity.setCopyRect();
        }

        createRect();

        String filename = "bg_" + System.currentTimeMillis() + ".png";
        File file = new File(template.getFolder_template());
        Bitmap bitmap = getBitmapDraw(template.isVideoSquare(), file);

        FontUtils.copyFontToInternalStorage(getContext(), "NotoNaskhArabic.ttf");

        float strokeWidth = this.linePaint.getStrokeWidth() * 4.2f;
        if (template.getIpad_type() == IpadType.BLACK_LAYER.ordinal()
                || template.getIpad_type() == IpadType.BLUE_TYPE.ordinal()
                || template.getIpad_type() == IpadType.GRADIENT.ordinal()
                || template.getIpad_type() == IpadType.MASK_BRUSH.ordinal()
                || template.getIpad_type() == IpadType.HEART.ordinal()
                || this.mIpadType == IpadType.BATTERY.ordinal()) {
            strokeWidth = 0.0f;
        }

        float startShape = 0;
        int widthShape = 0;
        int heightShape = 0;

        if (template.getIpad_type() == IpadType.BLUE_TYPE.ordinal()) {
            saveProgressBitmapTypeBlue(file);
        } else if (template.getIpad_type() == IpadType.IPAD_NEOMORPHIC.ordinal()) {
            saveProgressBitmapTypeIPAD_NEOMORPHIC(file, bitmap);
        } else if (template.getIpad_type() == IpadType.HEART.ordinal()) {
            Pair<Float, Integer> pair = saveProgressBitmapTypeHeart(file, bitmap);
            startShape = pair.first;
            heightShape = pair.second;
        } else if (this.mIpadType == IpadType.BATTERY.ordinal()) {
            Pair<Float, Point> pair = saveProgressBitmapTypeBattery(file, bitmap);
            startShape = pair.first;
            widthShape = pair.second.x;
            heightShape = pair.second.y;
        } else if (this.mIpadType == IpadType.CASSET.ordinal()
                || this.mIpadType == IpadType.CASSET_IMG.ordinal()
                || this.mIpadType == IpadType.CASSET_IMG_BLUR.ordinal()) {
            startShape = this.rectFProgress.left;
            widthShape = (int) this.rectFProgress.right;
            heightShape = (int) this.rectFProgress.top;
        } else {
            saveProgressBitmap(file, strokeWidth);
        }

        drawEntityBitmap(file, bitmap.getWidth(), bitmap.getHeight());
        saveBg(filename, bitmap, file);

        TimeModel timeModel = template.getmTimeModel();
        int progressOffset = Math.round(strokeWidth * 1.98f);

        if (timeModel == null) {
            String color = this.paintText.getColor() == -1 ? "white" : "black";
            timeModel = new TimeModel(
                    (int) this.rectFProgress.width(),
                    (int) (this.rectFProgress.height() * 1.5f),
                    this.paintText.getTextSize() * 0.96f,
                    color,
                    this.txt_y,
                    this.newLeft_txt,
                    progressOffset
            );
        } else {
            String color = this.paintText.getColor() == -1 ? "white" : "black";
            timeModel.setColor(color);
            timeModel.setPosXRight(this.newLeft_txt);
            timeModel.setPosY(this.txt_y);
            timeModel.setHeight_bitmap_progress((int) (this.rectFProgress.height() * 1.5f));
            timeModel.setWidth_bitmap_progress((int) this.rectFProgress.width());
            timeModel.setSize(this.paintText.getTextSize() * 0.96f);
            timeModel.setProgress_offset(progressOffset);
        }

        timeModel.setStartShape(startShape);
        timeModel.setWidthShape(widthShape);
        timeModel.setHeightShape(heightShape);
        template.setmTimeModel(timeModel);

        return file.getAbsolutePath() + "/" + filename;
    }

    private void saveBg(String str, Bitmap bitmap, File file) {
        saveBitmap(bitmap, file, str);
    }

    public Bitmap getBitmapDraw(boolean z, File file) {
        Bitmap bitmap;
        if (this.mIpadType == IpadType.BLACK_LAYER.ordinal() || this.mIpadType == IpadType.BLUE_TYPE.ordinal() || this.mIpadType == IpadType.GRADIENT.ordinal() || this.mIpadType == IpadType.MASK_BRUSH.ordinal() || this.mIpadType == IpadType.CASSET_IMG.ordinal() || this.mIpadType == IpadType.IPAD_UNBLUR.ordinal()) {
            bitmap = this.bitmapNotBlur;
        } else {
            bitmap = this.bitmapBlured;
        }
        if (getColor_gradient() != null) {
            setColorIpad(getColor_gradient());
        }
        Canvas canvas = new Canvas(bitmap);
        if (this.mIpadType == IpadType.IPAD_CLASSIC.ordinal()) {
            if (getColor_gradient() != null) {
                this.paint.setShader(this.linearGradient_classic);
                canvas.drawPaint(this.paint);
                this.paint.setShader(null);
            } else {
                canvas.drawColor(this.color_bg_type_classic);
            }
        }
        if (this.mIpadType == IpadType.CASSET.ordinal()) {
            drawCaset(canvas, false, file);
        } else if (this.mIpadType == IpadType.CASSET_IMG.ordinal()) {
            drawCasetNoBg(canvas, false, file, z);
        } else if (this.mIpadType == IpadType.CASSET_IMG_BLUR.ordinal()) {
            this.bitmapSquare = this.bitmapBlured;
            drawCasetNoBg(canvas, false, file, z);
        } else {
            drawIpad(canvas, false, z);
        }
        if (!isPro()) {
            resetWatermark();
            drawWattermark(canvas, true);
        }
        if (this.surahNameEntity != null) {
            this.surahNameEntity.setRect(new RectF(this.surahNameEntity.getCopyRect().left * canvas.getWidth(), this.surahNameEntity.getCopyRect().top * canvas.getHeight(), this.surahNameEntity.getCopyRect().right * canvas.getWidth(), this.surahNameEntity.getCopyRect().bottom * canvas.getHeight()));
            SurahNameEntity surahNameEntity = this.surahNameEntity;
            surahNameEntity.scale(surahNameEntity.getFactor_scale(), 1, 1);
            this.surahNameEntity.draw(canvas);
        }
        return bitmap;
    }

    private QuranEntity getLastAdd() {
        for (int size = this.quranEntities.size() - 1; size >= 0; size--) {
            QuranEntity quranEntity = this.quranEntities.get(size);
            if (quranEntity.getEntityQuran().visible() && quranEntity.getFactorSize() == 1.0f) {
                return quranEntity;
            }
        }
        return this.quranEntities.get(this.quranEntities.size() - 1);
    }

    private TranslationQuranEntity getLastAddTrsl() {
        for (int size = this.translationEntities.size() - 1; size >= 0; size--) {
            TranslationQuranEntity translationQuranEntity = this.translationEntities.get(size);
            if (translationQuranEntity.getEntityTrslTimeline().visible() && translationQuranEntity.getFactorSize() == 1.0f) {
                return translationQuranEntity;
            }
        }
        return this.translationEntities.get(this.translationEntities.size() - 1);
    }

    private int countEntityQuran() {
        if (this.quranEntities.size() == 1) {
            return 1;
        }
        int i = 0;
        for (int i2 = 0; i2 < this.quranEntities.size(); i2++) {
            if (this.quranEntities.get(i2).getEntityQuran().visible()) {
                i++;
            }
            if (i > 1) {
                return 2;
            }
        }
        return i;
    }

    private int countEntityTrsl() {
        if (this.translationEntities.size() == 1) {
            return 1;
        }
        int i = 0;
        for (int i2 = 0; i2 < this.translationEntities.size(); i2++) {
            if (this.translationEntities.get(i2).getEntityTrslTimeline().visible()) {
                i++;
            }
            if (i > 1) {
                return 2;
            }
        }
        return i;
    }

    public void updateSizeAyaSave(int i, int i2) {
        List<QuranEntity> list = this.quranEntities;
        if (list == null || list.isEmpty()) {
            return;
        }
        for (int size = this.quranEntities.size() - 1; size >= 0; size--) {
            QuranEntity quranEntity = this.quranEntities.get(size);
            if (quranEntity.getEntityQuran().visible()) {
                quranEntity.setIpad_type(this.mIpadType);
                quranEntity.setCopyRect();
                float f = i;
                float f2 = i2;
                RectF rectF = new RectF(quranEntity.getCopyRect().left * f, quranEntity.getCopyRect().top * f2, quranEntity.getCopyRect().right * f, quranEntity.getCopyRect().bottom * f2);
                quranEntity.update(rectF, (int) (rectF.width() * 0.85f), (int) (rectF.height() * 0.85f));
                quranEntity.setupScaleSave(quranEntity.getFactorSize(), i);
                quranEntity.initPreset(quranEntity.getmPreset());
            }
        }
    }

    public void updateSizeTrslSave(int i, int i2) {
        List<TranslationQuranEntity> list = this.translationEntities;
        if (list == null || list.isEmpty()) {
            return;
        }
        for (int size = this.translationEntities.size() - 1; size >= 0; size--) {
            TranslationQuranEntity translationQuranEntity = this.translationEntities.get(size);
            if (translationQuranEntity.getEntityTrslTimeline().visible()) {
                translationQuranEntity.setIpad_type(this.mIpadType);
                translationQuranEntity.setCopyRect();
                float f = i;
                float f2 = i2;
                RectF rectF = new RectF(translationQuranEntity.getCopyRect().left * f, translationQuranEntity.getCopyRect().top * f2, translationQuranEntity.getCopyRect().right * f, translationQuranEntity.getCopyRect().bottom * f2);
                translationQuranEntity.update(rectF, (int) (rectF.width() * 0.85f), (int) (rectF.height() * 0.85f));
                translationQuranEntity.setupScaleSave(translationQuranEntity.getFactorSize(), i);
                translationQuranEntity.initPreset(translationQuranEntity.getmPreset());
            }
        }
    }

    public void updateSizeAya() {
        float calculateTextSize;
        float f;
        List<QuranEntity> list = this.quranEntities;
        if (list == null || list.isEmpty()) {
            return;
        }
        QuranEntity lastAdd = getLastAdd();
        QuranEntity quranEntity = lastAdd.getTranslation() != null ? lastAdd : null;
        float translationWidth = quranEntity != null ? lastAdd.getTranslationWidth() : 0.0f;
        boolean z = translationWidth != 0.0f;
        if (countEntityQuran() == 1) {
            if (lastAdd.getTxt().length() < 9) {
                calculateTextSize = lastAdd.calculateTextSize();
                f = 0.7f;
            } else {
                calculateTextSize = lastAdd.calculateTextSize();
                f = 0.95f;
            }
            float f2 = calculateTextSize * f;
            lastAdd.setTextSize(f2);
            lastAdd.setFcSize(f2 / getmCanvas_width());
            lastAdd.setupScale(lastAdd.getFactorSize(), getmCanvas_width(), getmCanvas_height());
            if (z) {
                float calculateOptimalTextSize = lastAdd.calculateOptimalTextSize((int) (lastAdd.getRect().width() * 0.85f), (int) (lastAdd.getRect().height() * 0.5f * 0.83f));
                lastAdd.updateTranslation(calculateOptimalTextSize);
                lastAdd.setFactorSizeTrl(calculateOptimalTextSize / getmCanvas_width());
            }
            lastAdd.initPreset(lastAdd.getmPreset());
            return;
        }
        float width = lastAdd.getFactorSize() == 1.0f ? lastAdd.getWidth() : -1.0f;
        for (int size = this.quranEntities.size() - 1; size >= 0; size--) {
            QuranEntity quranEntity2 = this.quranEntities.get(size);
            if (quranEntity2.getEntityQuran().visible() && quranEntity2.getRect().width() == this.rectFAya.width()) {
                float width2 = quranEntity2.getWidth();
                if (width2 > width || width == -1.0f) {
                    width = width2;
                    lastAdd = quranEntity2;
                }
                if (quranEntity2.getTranslation() != null && quranEntity2.getTranslationWidth() > translationWidth) {
                    translationWidth = quranEntity2.getTranslationWidth();
                    z = true;
                    quranEntity = quranEntity2;
                }
            }
        }
        float calculateTextSize2 = lastAdd.calculateTextSize();
        for (int size2 = this.quranEntities.size() - 1; size2 >= 0; size2--) {
            QuranEntity quranEntity3 = this.quranEntities.get(size2);
            if (quranEntity3.getEntityQuran().visible() && quranEntity3.getRect().width() == this.rectFAya.width()) {
                if (quranEntity3.getFactorSize() == 1.0f) {
                    quranEntity3.setFcSize(calculateTextSize2 / getmCanvas_width());
                }
                quranEntity3.setupScale(quranEntity3.getFactorSize(), getmCanvas_width(), getmCanvas_height());
                quranEntity3.initPresetAya(quranEntity3.getmPreset());
            }
        }
        if (z) {
            float calculateOptimalTextSize2 = quranEntity.calculateOptimalTextSize((int) (quranEntity.getRect().width() * 0.85f), (int) (quranEntity.getRect().height() * 0.5f * 0.83f));
            for (int i = 0; i < this.quranEntities.size(); i++) {
                QuranEntity quranEntity4 = this.quranEntities.get(i);
                if (quranEntity4.getEntityQuran().visible() && quranEntity4.getTranslation() != null) {
                    quranEntity4.updateTranslation(calculateOptimalTextSize2);
                    quranEntity4.setFactorSizeTrl(calculateOptimalTextSize2 / getmCanvas_width());
                    quranEntity4.initPresetTrsl(quranEntity4.getmPreset());
                }
            }
        }
    }

    public void updateSizeAyaTrsl() {
        float calculateTextSize;
        float f;
        List<TranslationQuranEntity> list = this.translationEntities;
        if (list == null || list.isEmpty()) {
            return;
        }
        TranslationQuranEntity lastAddTrsl = getLastAddTrsl();
        if (countEntityTrsl() == 1) {
            if (lastAddTrsl.getTxt().length() < 9) {
                calculateTextSize = lastAddTrsl.calculateTextSize();
                f = 0.7f;
            } else {
                calculateTextSize = lastAddTrsl.calculateTextSize();
                f = 0.95f;
            }
            float f2 = calculateTextSize * f;
            lastAddTrsl.setTextSize(f2);
            lastAddTrsl.setFcSize(f2 / getmCanvas_width());
            lastAddTrsl.setupScale(lastAddTrsl.getFactorSize(), getmCanvas_width(), getmCanvas_height());
            lastAddTrsl.initPreset(lastAddTrsl.getmPreset());
            return;
        }
        float width = lastAddTrsl.getFactorSize() == 1.0f ? lastAddTrsl.getWidth() : -1.0f;
        for (int size = this.translationEntities.size() - 1; size >= 0; size--) {
            TranslationQuranEntity translationQuranEntity = this.translationEntities.get(size);
            if (translationQuranEntity.getEntityTrslTimeline().visible()) {
                float width2 = translationQuranEntity.getWidth();
                if (width2 > width || width == -1.0f) {
                    lastAddTrsl = translationQuranEntity;
                    width = width2;
                }
            }
        }
        float calculateTextSize2 = lastAddTrsl.calculateTextSize();
        for (int size2 = this.translationEntities.size() - 1; size2 >= 0; size2--) {
            TranslationQuranEntity translationQuranEntity2 = this.translationEntities.get(size2);
            if (translationQuranEntity2.getEntityTrslTimeline().visible()) {
                if (translationQuranEntity2.getFactorSize() == 1.0f) {
                    translationQuranEntity2.setFcSize(calculateTextSize2 / getmCanvas_width());
                }
                translationQuranEntity2.setupScale(translationQuranEntity2.getFactorSize(), getmCanvas_width(), getmCanvas_height());
                translationQuranEntity2.initPresetAya(translationQuranEntity2.getmPreset());
            }
        }
    }

    public void updateSizeAyaResize() {
        float calculateTextSize;
        float f;
        List<QuranEntity> list = this.quranEntities;
        if (list == null || list.isEmpty()) {
            return;
        }
        QuranEntity lastAdd = getLastAdd();
        lastAdd.setIpad_type(this.mIpadType);
        lastAdd.setCanvasWH(getmCanvas_width(), getmCanvas_height());
        lastAdd.setFactor_scale(1.0f);
        lastAdd.setFcSize(1.0f);
        lastAdd.setFactorSizeTrl(1.0f);
        RectF rectF = this.rectFAya;
        lastAdd.update(rectF, (int) (rectF.width() * 0.85f), (int) (this.rectFAya.height() * 0.85f));
        QuranEntity quranEntity = lastAdd.getTranslation() != null ? lastAdd : null;
        float translationWidth = quranEntity != null ? lastAdd.getTranslationWidth() : 0.0f;
        boolean z = translationWidth != 0.0f;
        float width = lastAdd.getWidth();
        if (countEntityQuran() == 1) {
            if (lastAdd.getTxt().length() < 9) {
                calculateTextSize = lastAdd.calculateTextSize();
                f = 0.7f;
            } else {
                calculateTextSize = lastAdd.calculateTextSize();
                f = 0.95f;
            }
            float f2 = calculateTextSize * f;
            lastAdd.setTextSize(f2);
            lastAdd.setFcSize(f2 / getmCanvas_width());
            lastAdd.setupScale(lastAdd.getFactorSize(), getmCanvas_width(), getmCanvas_height());
            if (z) {
                float calculateOptimalTextSize = lastAdd.calculateOptimalTextSize((int) (lastAdd.getRect().width() * 0.85f), (int) (lastAdd.getRect().height() * 0.5f * 0.83f));
                lastAdd.updateTranslation(calculateOptimalTextSize);
                lastAdd.setFactorSizeTrl(calculateOptimalTextSize / getmCanvas_width());
            }
            lastAdd.initPreset(lastAdd.getmPreset());
            return;
        }
        for (int size = this.quranEntities.size() - 1; size >= 0; size--) {
            QuranEntity quranEntity2 = this.quranEntities.get(size);
            if (quranEntity2.getEntityQuran().visible()) {
                quranEntity2.setCanvasWH(getmCanvas_width(), getmCanvas_height());
                quranEntity2.setIpad_type(this.mIpadType);
                quranEntity2.update(this.rectFAya, lastAdd.getMax_w(), lastAdd.getMax_h());
                float width2 = quranEntity2.getWidth();
                if (width2 > width) {
                    width = width2;
                    lastAdd = quranEntity2;
                }
                if (quranEntity2.getTranslation() != null && quranEntity2.getTranslationWidth() > translationWidth) {
                    translationWidth = quranEntity2.getTranslationWidth();
                    z = true;
                    quranEntity = quranEntity2;
                }
            }
        }
        float calculateTextSize2 = lastAdd.calculateTextSize();
        for (int size2 = this.quranEntities.size() - 1; size2 >= 0; size2--) {
            QuranEntity quranEntity3 = this.quranEntities.get(size2);
            if (quranEntity3.getEntityQuran().visible()) {
                quranEntity3.setFactor_scale(1.0f);
                quranEntity3.setFactorSizeTrl(1.0f);
                quranEntity3.setFcSize(calculateTextSize2 / getmCanvas_width());
                quranEntity3.setupScale(quranEntity3.getFactorSize(), getmCanvas_width(), getmCanvas_height());
                quranEntity3.initPresetAya(quranEntity3.getmPreset());
            }
        }
        if (z) {
            float calculateOptimalTextSize2 = quranEntity.calculateOptimalTextSize((int) (quranEntity.getRect().width() * 0.85f), (int) (quranEntity.getRect().height() * 0.5f * 0.83f));
            for (int i = 0; i < this.quranEntities.size(); i++) {
                QuranEntity quranEntity4 = this.quranEntities.get(i);
                if (quranEntity4.getEntityQuran().visible() && quranEntity4.getTranslation() != null) {
                    quranEntity4.updateTranslation(calculateOptimalTextSize2);
                    quranEntity4.setFactorSizeTrl(calculateOptimalTextSize2 / getmCanvas_width());
                    quranEntity4.initPresetTrsl(quranEntity4.getmPreset());
                }
            }
        }
    }

    public void updateSizeTrslAyaResize() {
        float calculateTextSize;
        float f;
        List<TranslationQuranEntity> list = this.translationEntities;
        if (list == null || list.isEmpty()) {
            return;
        }
        TranslationQuranEntity lastAddTrsl = getLastAddTrsl();
        lastAddTrsl.setIpad_type(this.mIpadType);
        lastAddTrsl.setCanvasWH(getmCanvas_width(), getmCanvas_height());
        lastAddTrsl.setFactor_scale(1.0f);
        lastAddTrsl.setFcSize(1.0f);
        lastAddTrsl.setFactorSizeTrl(1.0f);
        RectF rectF = this.rectFAya;
        lastAddTrsl.onResize(rectF, (int) (rectF.width() * 0.85f), (int) (this.rectFAya.height() * 0.85f));
        float width = lastAddTrsl.getWidth();
        if (countEntityTrsl() == 1) {
            if (lastAddTrsl.getTxt().length() < 9) {
                calculateTextSize = lastAddTrsl.calculateTextSize();
                f = 0.7f;
            } else {
                calculateTextSize = lastAddTrsl.calculateTextSize();
                f = 0.95f;
            }
            float f2 = calculateTextSize * f;
            lastAddTrsl.setTextSize(f2);
            lastAddTrsl.setFcSize(f2 / getmCanvas_width());
            lastAddTrsl.setupScale(lastAddTrsl.getFactorSize(), getmCanvas_width(), getmCanvas_height());
            lastAddTrsl.initPreset(lastAddTrsl.getmPreset());
            return;
        }
        for (int size = this.translationEntities.size() - 1; size >= 0; size--) {
            TranslationQuranEntity translationQuranEntity = this.translationEntities.get(size);
            if (translationQuranEntity.getEntityTrslTimeline().visible()) {
                translationQuranEntity.setCanvasWH(getmCanvas_width(), getmCanvas_height());
                translationQuranEntity.setIpad_type(this.mIpadType);
                translationQuranEntity.onResize(this.rectFAya, lastAddTrsl.getMax_w(), lastAddTrsl.getMax_h());
                float width2 = translationQuranEntity.getWidth();
                if (width2 > width) {
                    lastAddTrsl = translationQuranEntity;
                    width = width2;
                }
            }
        }
        float calculateTextSize2 = lastAddTrsl.calculateTextSize();
        for (int size2 = this.translationEntities.size() - 1; size2 >= 0; size2--) {
            TranslationQuranEntity translationQuranEntity2 = this.translationEntities.get(size2);
            if (translationQuranEntity2.getEntityTrslTimeline().visible()) {
                translationQuranEntity2.setFactor_scale(1.0f);
                translationQuranEntity2.setFactorSizeTrl(1.0f);
                translationQuranEntity2.setFcSize(calculateTextSize2 / getmCanvas_width());
                translationQuranEntity2.setupScale(translationQuranEntity2.getFactorSize(), getmCanvas_width(), getmCanvas_height());
                translationQuranEntity2.initPresetAya(translationQuranEntity2.getmPreset());
            }
        }
    }

    public void updatePosSurahName() {
        float width = 0;
        float width2 = 0;
        float width3 = 0;
        float width4 = 0;
        if (this.surahNameEntity != null) {
            if (this.mIpadType == IpadType.IPAD_NEOMORPHIC.ordinal() || this.mIpadType == IpadType.CASSET.ordinal() || this.mIpadType == IpadType.CASSET_IMG.ordinal() || this.mIpadType == IpadType.CASSET_IMG_BLUR.ordinal()) {
                this.surahNameEntity.setAlignment(Layout.Alignment.ALIGN_CENTER);
            } else if (!Utils.isProbablyLArabic(this.surahNameEntity.getReader())) {
                if (this.mIpadType == IpadType.IPAD.ordinal() || this.mIpadType == IpadType.IPAD_UNBLUR.ordinal() || this.mIpadType == IpadType.IPAD_CLASSIC.ordinal()) {
                    float width5 = this.rectFSurahName.width();
                    this.left_square = this.ipad_rect.centerX() - (this.bitmapSquare.getWidth() * 0.5f);
                    if (this.mIpadType == IpadType.IPAD_CLASSIC.ordinal()) {
                        this.rectFSurahName.left = this.left_square;
                    } else {
                        this.rectFSurahName.left = (this.ipad_rect.width() * 0.05f) + this.left_square;
                    }
                    RectF rectF = this.rectFSurahName;
                    rectF.right = rectF.left + width5;
                } else {
                    if (this.mIpadType == IpadType.BOTTOM_RECT.ordinal()) {
                        width3 = this.ipad_rect.width();
                    } else if (this.mIpadType == IpadType.BLACK_LAYER.ordinal() || this.mIpadType == IpadType.BLUE_TYPE.ordinal() || this.mIpadType == IpadType.GRADIENT.ordinal() || this.mIpadType == IpadType.MASK_BRUSH.ordinal() || this.mIpadType == IpadType.HEART.ordinal() || this.mIpadType == IpadType.BATTERY.ordinal()) {
                        width3 = this.ipad_rect.width();
                    } else {
                        width4 = this.ipad_rect.width() * 0.07f;
                        float width6 = this.rectFSurahName.width();
                        this.rectFSurahName.left = width4 + this.ipad_rect.left;
                        RectF rectF2 = this.rectFSurahName;
                        rectF2.right = rectF2.left + width6;
                    }
                    width4 = width3 * 0.015f;
                    float width62 = this.rectFSurahName.width();
                    this.rectFSurahName.left = width4 + this.ipad_rect.left;
                    RectF rectF22 = this.rectFSurahName;
                    rectF22.right = rectF22.left + width62;
                }
                this.surahNameEntity.setAlignment(Layout.Alignment.ALIGN_NORMAL);
            } else {
                if (this.mIpadType == IpadType.IPAD.ordinal() || this.mIpadType == IpadType.IPAD_UNBLUR.ordinal() || this.mIpadType == IpadType.IPAD_CLASSIC.ordinal()) {
                    float width7 = this.rectFSurahName.width();
                    this.left_square = this.ipad_rect.centerX() - (this.bitmapSquare.getWidth() * 0.5f);
                    if (this.mIpadType == IpadType.IPAD_CLASSIC.ordinal()) {
                        this.rectFSurahName.right = this.left_square + this.bitmapSquare.getWidth();
                    } else {
                        this.rectFSurahName.right = (this.left_square + this.bitmapSquare.getWidth()) - (this.ipad_rect.width() * 0.05f);
                    }
                    RectF rectF3 = this.rectFSurahName;
                    rectF3.left = rectF3.right - width7;
                } else {
                    if (this.mIpadType == IpadType.BOTTOM_RECT.ordinal()) {
                        width = this.ipad_rect.width();
                    } else if (this.mIpadType == IpadType.BLACK_LAYER.ordinal() || this.mIpadType == IpadType.BLUE_TYPE.ordinal() || this.mIpadType == IpadType.GRADIENT.ordinal() || this.mIpadType == IpadType.MASK_BRUSH.ordinal() || this.mIpadType == IpadType.HEART.ordinal() || this.mIpadType == IpadType.BATTERY.ordinal()) {
                        width = this.ipad_rect.width();
                    } else {
                        width2 = this.ipad_rect.width() * 0.07f;
                        float width8 = this.rectFSurahName.width();
                        this.rectFSurahName.right = this.ipad_rect.right - width2;
                        RectF rectF4 = this.rectFSurahName;
                        rectF4.left = rectF4.right - width8;
                    }
                    width2 = width * 0.015f;
                    float width82 = this.rectFSurahName.width();
                    this.rectFSurahName.right = this.ipad_rect.right - width2;
                    RectF rectF42 = this.rectFSurahName;
                    rectF42.left = rectF42.right - width82;
                }
                this.surahNameEntity.setAlignment(Layout.Alignment.ALIGN_OPPOSITE);
            }
            this.surahNameEntity.setIpad_type(this.mIpadType);
            this.surahNameEntity.setFactor_scale(1.0f);
            this.surahNameEntity.update(this.rectFSurahName);
        }
    }

    private void updateBismilahEntity() {
        BismilahEntity bismilahEntity = this.bismilahEntity;
        if (bismilahEntity != null && bismilahEntity.getBismilahTimeline().visible()) {
            this.bismilahEntity.setCanvasWH(getmCanvas_width(), getmCanvas_height());
            this.bismilahEntity.setFactor_scale(1.0f);
            this.bismilahEntity.setFcSize(1.0f);
            BismilahEntity bismilahEntity2 = this.bismilahEntity;
            RectF rectF = this.rectFAya;
            bismilahEntity2.update(rectF, (int) (rectF.width() * 0.85f), (int) (this.rectFAya.height() * 0.85f));
            this.bismilahEntity.createStaticLayout();
            BismilahEntity bismilahEntity3 = this.bismilahEntity;
            bismilahEntity3.initPreset(bismilahEntity3.getmPreset());
            BismilahEntity bismilahEntity4 = this.bismilahEntity;
            bismilahEntity4.setFcSize(bismilahEntity4.getPaintAya().getTextSize() / getmCanvas_width());
        }
        BismilahEntity bismilahEntity5 = this.mIsti3adhaEntity;
        if (bismilahEntity5 == null || !bismilahEntity5.getBismilahTimeline().visible()) {
            return;
        }
        this.mIsti3adhaEntity.setCanvasWH(getmCanvas_width(), getmCanvas_height());
        this.mIsti3adhaEntity.setFactor_scale(1.0f);
        this.mIsti3adhaEntity.setFcSize(1.0f);
        BismilahEntity bismilahEntity6 = this.mIsti3adhaEntity;
        RectF rectF2 = this.rectFAya;
        bismilahEntity6.update(rectF2, (int) (rectF2.width() * 0.85f), (int) (this.rectFAya.height() * 0.85f));
        this.mIsti3adhaEntity.createStaticLayout();
        BismilahEntity bismilahEntity7 = this.mIsti3adhaEntity;
        bismilahEntity7.initPreset(bismilahEntity7.getmPreset());
        BismilahEntity bismilahEntity8 = this.mIsti3adhaEntity;
        bismilahEntity8.setFcSize(bismilahEntity8.getPaintAya().getTextSize() / getmCanvas_width());
    }

    public void updateBismilahEntity(int i, int i2) {
        BismilahEntity bismilahEntity = this.bismilahEntity;
        if (bismilahEntity != null && bismilahEntity.getBismilahTimeline().visible()) {
            this.bismilahEntity.setCopyRect();
            float f = i;
            float f2 = i2;
            RectF rectF = new RectF(this.bismilahEntity.getCopyRect().left * f, this.bismilahEntity.getCopyRect().top * f2, this.bismilahEntity.getCopyRect().right * f, this.bismilahEntity.getCopyRect().bottom * f2);
            this.bismilahEntity.update(rectF, (int) (rectF.width() * 0.85f), (int) (rectF.height() * 0.85f));
            BismilahEntity bismilahEntity2 = this.bismilahEntity;
            bismilahEntity2.setupScaleSave(bismilahEntity2.getFactorSize(), i);
            BismilahEntity bismilahEntity3 = this.bismilahEntity;
            bismilahEntity3.initPreset(bismilahEntity3.getmPreset());
        }
        BismilahEntity bismilahEntity4 = this.mIsti3adhaEntity;
        if (bismilahEntity4 == null || !bismilahEntity4.getBismilahTimeline().visible()) {
            return;
        }
        this.mIsti3adhaEntity.setCopyRect();
        float f3 = i;
        float f4 = i2;
        RectF rectF2 = new RectF(this.mIsti3adhaEntity.getCopyRect().left * f3, this.mIsti3adhaEntity.getCopyRect().top * f4, this.mIsti3adhaEntity.getCopyRect().right * f3, this.mIsti3adhaEntity.getCopyRect().bottom * f4);
        this.mIsti3adhaEntity.update(rectF2, (int) (rectF2.width() * 0.85f), (int) (rectF2.height() * 0.85f));
        BismilahEntity bismilahEntity5 = this.mIsti3adhaEntity;
        bismilahEntity5.setupScaleSave(bismilahEntity5.getFactorSize(), i);
        BismilahEntity bismilahEntity6 = this.mIsti3adhaEntity;
        bismilahEntity6.initPreset(bismilahEntity6.getmPreset());
    }

    public void resizeEntity() {
        SurahNameEntity surahNameEntity = this.surahNameEntity;
        if (surahNameEntity != null) {
            surahNameEntity.setCanvasWH(getmCanvas_width(), getmCanvas_height());
        }
        updateSizeAyaResize();
        updateSizeTrslAyaResize();
        updateBismilahEntity();
    }

    private EntityView findEntityAtPoint(float f, float f2) {
        SurahNameEntity surahNameEntity = this.surahNameEntity;
        if (surahNameEntity != null && surahNameEntity.getRect().contains(f, f2)) {
            return this.surahNameEntity;
        }
        BismilahEntity bismilahEntity = this.mIsti3adhaEntity;
        if (bismilahEntity != null && bismilahEntity.isVisible() && this.mIsti3adhaEntity.getBismilahTimeline().visible() && this.mIsti3adhaEntity.getRect().contains(f, f2)) {
            return this.mIsti3adhaEntity;
        }
        BismilahEntity bismilahEntity2 = this.bismilahEntity;
        if (bismilahEntity2 != null && bismilahEntity2.isVisible() && this.bismilahEntity.getBismilahTimeline().visible() && this.bismilahEntity.getRect().contains(f, f2)) {
            return this.bismilahEntity;
        }
        for (int size = this.quranEntities.size() - 1; size >= 0; size--) {
            QuranEntity quranEntity = this.quranEntities.get(size);
            if (quranEntity.isVisible() && quranEntity.getEntityQuran().visible() && quranEntity.getRect().contains(f, f2)) {
                return quranEntity;
            }
        }
        for (int size2 = this.translationEntities.size() - 1; size2 >= 0; size2--) {
            TranslationQuranEntity translationQuranEntity = this.translationEntities.get(size2);
            if (translationQuranEntity.isVisible() && translationQuranEntity.getEntityTrslTimeline().visible() && translationQuranEntity.getRect().contains(f, f2)) {
                return translationQuranEntity;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSelectionOnTap(MotionEvent motionEvent) {
        setEntity_select(findEntityAtPoint(motionEvent.getX(), motionEvent.getY()));
        IViewCallback iViewCallback = this.iViewCallback;
        if (iViewCallback != null) {
            EntityView entityView = this.entity_select;
            if (entityView != null) {
                iViewCallback.onSelect(entityView);
            } else {
                iViewCallback.onEmtyClick();
            }
        }
        invalidate();
    }

    public void setNotDraw(boolean z) {
        this.isNotDraw = z;
    }

    private class MoveListener extends MoveGestureDetector.SimpleOnMoveGestureListener {
        private MoveListener() {
        }

        @Override // hazem.nurmontage.videoquran.multitouch.MoveGestureDetector.SimpleOnMoveGestureListener, hazem.nurmontage.videoquran.multitouch.MoveGestureDetector.OnMoveGestureListener
        public boolean onMove(MoveGestureDetector moveGestureDetector) {
            BlurredImageView.this.handleTranslate(moveGestureDetector.getFocusDelta());
            return true;
        }

        @Override // hazem.nurmontage.videoquran.multitouch.MoveGestureDetector.SimpleOnMoveGestureListener, hazem.nurmontage.videoquran.multitouch.MoveGestureDetector.OnMoveGestureListener
        public void onMoveEnd(MoveGestureDetector moveGestureDetector) {
            super.onMoveEnd(moveGestureDetector);
            if (BlurredImageView.this.entity_select == null || BlurredImageView.this.selectTool == null) {
                return;
            }
            BlurredImageView.this.selectTool.setApply_all(true);
        }
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        private ScaleListener() {
        }

        @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
        public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
            if (BlurredImageView.this.entity_select != null) {
                BlurredImageView.this.isOnScale = true;
                BlurredImageView.this.selectTool.setApply_Scale(true);
                BlurredImageView.this.selectTool.setOnProgress(true);
            }
            return super.onScaleBegin(scaleGestureDetector);
        }

        @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            if (BlurredImageView.this.entity_select == null) {
                return true;
            }
            BlurredImageView.this.entity_select.scale(scaleGestureDetector.getScaleFactor(), BlurredImageView.this.getmCanvas_width(), BlurredImageView.this.getmCanvas_height());
            BlurredImageView.this.invalidate();
            return true;
        }

        @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
        public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
            if (BlurredImageView.this.entity_select instanceof QuranEntity) {
                BlurredImageView.this.selectTool.setApply_all(true);
                BlurredImageView.this.selectTool.setOnProgress(false);
            }
            super.onScaleEnd(scaleGestureDetector);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleTranslate(PointF pointF) {
        if (this.entity_select != null && Math.abs(pointF.x) <= 80.0f && Math.abs(pointF.y) <= 80.0f) {
            RectF rect = this.entity_select.getRect();
            float centerX = rect.centerX();
            float centerY = rect.centerY();
            float f = centerX + pointF.x;
            float f2 = centerY + pointF.y;
            float f3 = this.mCanvas_height / 2.0f;
            boolean z = false;
            this.showCenterLineX = false;
            this.showCenterLineY = false;
            float f4 = pointF.x;
            float f5 = pointF.y;
            float f6 = f - (this.mCanvas_width / 2.0f);
            boolean z2 = true;
            if (Math.abs(f6) < SNAP_THRESHOLD) {
                this.showCenterLineX = true;
                f4 -= (f6 * 0.2f) * (1.0f - (Math.abs(f6) / SNAP_THRESHOLD));
            }
            float f7 = f2 - f3;
            if (Math.abs(f7) < SNAP_THRESHOLD) {
                this.showCenterLineY = true;
                f5 -= (0.2f * f7) * (1.0f - (Math.abs(f7) / SNAP_THRESHOLD));
            }
            if (f >= 0.0f && f <= getWidth()) {
                this.entity_select.postTranslate(f4, 0.0f);
                this.selectTool.setApply_Move(true);
                z = true;
            }
            if (f2 < 0.0f || f2 > getHeight()) {
                z2 = z;
            } else {
                this.entity_select.postTranslate(0.0f, f5);
                this.selectTool.setApply_Move(true);
            }
            if (z2) {
                invalidate();
            }
        }
    }

    private void drawLineHelper(Canvas canvas) {
        if (this.showCenterLineX || this.showCenterLineY) {
            Paint paint = new Paint(1);
            paint.setColor(Color.parseColor("#80FF4081"));
            paint.setStrokeWidth(5.0f);
            float f = this.mCanvas_width / 2.0f;
            int i = this.mCanvas_height;
            float f2 = i / 2.0f;
            if (this.showCenterLineX) {
                canvas.drawLine(f, 0.0f, f, i, paint);
            }
            if (this.showCenterLineY) {
                canvas.drawLine(0.0f, f2, this.mCanvas_width, f2, paint);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float distanceToCenter(float f, float f2) {
        return (float) Math.hypot(f - this.entity_select.getRect().centerX(), f2 - this.entity_select.getRect().centerY());
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent == null) {
            return false;
        }
        motionEvent.setLocation((motionEvent.getX() + getPaddingLeft()) - this.mDrawingTranslationX, (motionEvent.getY() + getPaddingTop()) - this.mDrawingTranslationY);
        if (motionEvent.getPointerCount() > 1) {
            return this.scaleGestureDetector.onTouchEvent(motionEvent);
        }
        EntitySelectTool entitySelectTool = this.selectTool;
        if (entitySelectTool != null && entitySelectTool.isOnProgress() && this.selectTool.isApply_Scale() && this.entity_select != null) {
            if (motionEvent.getAction() == 2 && this.prevDistance > 0.0f) {
                float distanceToCenter = distanceToCenter(motionEvent.getX(), motionEvent.getY());
                if (distanceToCenter < 1.0f) {
                    distanceToCenter = 1.0f;
                }
                if (this.prevDistance < 1.0f) {
                    this.prevDistance = 1.0f;
                }
                float f = this.prevDistance;
                float f2 = (distanceToCenter - f) / f;
                if (f2 > 0.5f) {
                    f2 = 0.5f;
                }
                if (f2 < -0.5f) {
                    f2 = -0.5f;
                }
                this.entity_select.scale(f2 + 1.0f, getmCanvas_width(), getmCanvas_height());
                invalidate();
                this.prevDistance = distanceToCenter;
                return true;
            }
            if (motionEvent.getAction() == 1) {
                this.prevDistance = -1.0f;
                this.selectTool.setOnProgress(false);
                if (this.selectTool.isApply_Scale() && this.iViewCallback != null) {
                    EntityView entityView = this.entity_select;
                    if (((entityView instanceof QuranEntity) || (entityView instanceof TranslationQuranEntity)) && !this.selectTool.isApply_all()) {
                        this.selectTool.setApply_all(true);
                        invalidate();
                    }
                }
                return true;
            }
        }
        if (motionEvent.getAction() == 1 && (this.showCenterLineX || this.showCenterLineY)) {
            this.showCenterLineY = false;
            this.showCenterLineX = false;
            invalidate();
        }
        if (!this.isOnScale) {
            this.moveGestureDetector.onTouchEvent(motionEvent);
        }
        this.isOnScale = false;
        return this.gestureDetector.onTouchEvent(motionEvent);
    }

    public void setiViewCallback(IViewCallback iViewCallback) {
        this.iViewCallback = iViewCallback;
    }
}

package hazem.nurmontage.videoquran.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import hazem.nurmontage.videoquran.Utils.UtilsBitmap;
import hazem.nurmontage.videoquran.common.Common;

/* loaded from: classes2.dex */
public class CropView extends View {
    private static final int HINT_ANIMATION_DURATION = 700;
    private static final int HINT_ANIMATION_REPEATS = 2;
    private static final float HINT_SCALE_MAX_FACTOR = 1.0f;
    private static final float HINT_SCALE_MIN_FACTOR = 0.85f;
    private static final String TAG = "CropView";
    private Bitmap bitmap;
    private Paint bitmapPaint;
    private Paint cropPaint;
    private RectF cropRect;
    private boolean hintAnimationPlayed;
    private ValueAnimator hintAnimator;
    private ICropCallback iCropCallback;
    private float initialHintRectCenterX;
    private float initialHintRectCenterY;
    private float initialHintRectHeight;
    private float initialHintRectWidth;
    private boolean isDragging;
    private float lastFocusX;
    private float lastFocusY;
    private float mCanvas_height;
    private float mCanvas_width;
    private float mDrawingX;
    private float mDrawingY;
    private float mHeight;
    private float mWidth;
    private Matrix matrix;
    private float minH;
    private float minW;
    private int radius;
    private float scale;
    private float scaleFactor;
    private ScaleGestureDetector scaleGestureDetector;
    private float startX;
    private float startY;
    private int touchTolerance;

    public interface ICropCallback {
        void onSizeChange();
    }

    public void setiCropCallback(ICropCallback iCropCallback) {
        this.iCropCallback = iCropCallback;
    }

    public void setmDrawingX(float f) {
        this.mDrawingX = f;
    }

    public void setmDrawingY(float f) {
        this.mDrawingY = f;
    }

    public float getmDrawingX() {
        return this.mDrawingX;
    }

    public float getmDrawingY() {
        return this.mDrawingY;
    }

    public CropView(Context context) {
        super(context);
        this.hintAnimationPlayed = false;
        this.scaleFactor = 1.0f;
        this.isDragging = false;
        this.touchTolerance = 10;
        init();
    }

    public CropView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.hintAnimationPlayed = false;
        this.scaleFactor = 1.0f;
        this.isDragging = false;
        this.touchTolerance = 10;
        init();
    }

    public CropView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.hintAnimationPlayed = false;
        this.scaleFactor = 1.0f;
        this.isDragging = false;
        this.touchTolerance = 10;
        init();
    }

    private void init() {
        this.matrix = new Matrix();
        Paint paint = new Paint();
        this.cropPaint = paint;
        paint.setColor(-15605);
        this.cropPaint.setStyle(Paint.Style.STROKE);
        this.cropPaint.setStrokeWidth(5.0f);
        this.cropPaint.setAntiAlias(true);
        Paint paint2 = new Paint();
        this.bitmapPaint = paint2;
        paint2.setAntiAlias(true);
        this.bitmapPaint.setFilterBitmap(true);
        this.cropRect = new RectF();
        this.scaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
    }

    public Rect getRectSquare() {
        return new Rect(Math.round(this.cropRect.left / this.scale), Math.round(this.cropRect.top / this.scale), Math.round(this.cropRect.right / this.scale), Math.round(this.cropRect.bottom / this.scale));
    }

    public void setBitmapLast(Bitmap bitmap, Rect rect, int i, boolean z) {
        this.bitmap = bitmap;
        this.cropRect = new RectF(rect.left, rect.top, rect.right, rect.bottom);
        this.radius = i;
        this.mCanvas_width = (getWidth() - getPaddingStart()) - getPaddingEnd();
        float height = (getHeight() - getPaddingTop()) - getPaddingBottom();
        this.mCanvas_height = height;
        this.mDrawingY = (height - bitmap.getHeight()) * 0.5f;
        this.mWidth = this.mCanvas_width;
        this.mHeight = bitmap.getHeight();
        float f = this.mCanvas_width / this.mWidth;
        Matrix matrix = new Matrix();
        matrix.postScale(f, f);
        matrix.postTranslate(0.0f, this.mDrawingY);
        invalidate();
        if (z || getWidth() <= 0 || getHeight() <= 0) {
            return;
        }
        this.initialHintRectWidth = this.cropRect.width();
        this.initialHintRectHeight = this.cropRect.height();
        this.initialHintRectCenterX = this.cropRect.centerX();
        this.initialHintRectCenterY = this.cropRect.centerY();
        startHintAnimation();
    }

    public void setBitmap(Bitmap bitmap, Rect rect, int i, boolean z) {
        this.bitmap = bitmap;
        this.radius = i;
        this.mCanvas_width = (getWidth() - getPaddingStart()) - getPaddingEnd();
        this.mCanvas_height = (getHeight() - getPaddingTop()) - getPaddingBottom();
        float width = bitmap.getWidth();
        float height = bitmap.getHeight();
        float min = Math.min(this.mCanvas_width / width, this.mCanvas_height / height);
        this.scale = min;
        float f = width * min;
        this.mWidth = f;
        float f2 = height * min;
        this.mHeight = f2;
        this.mDrawingX = (this.mCanvas_width - f) * 0.5f;
        this.mDrawingY = (this.mCanvas_height - f2) * 0.5f;
        Matrix matrix = new Matrix();
        this.matrix = matrix;
        float f3 = this.scale;
        matrix.postScale(f3, f3);
        this.cropRect = new RectF(rect.left * this.scale, rect.top * this.scale, rect.right * this.scale, rect.bottom * this.scale);
        this.minH = Common.MIN_SQUARE_H * this.scale;
        this.minW = Common.MIN_SQUARE_W * this.scale;
        invalidate();
        if (z || getWidth() <= 0 || getHeight() <= 0) {
            return;
        }
        this.initialHintRectWidth = this.cropRect.width();
        this.initialHintRectHeight = this.cropRect.height();
        this.initialHintRectCenterX = this.cropRect.centerX();
        this.initialHintRectCenterY = this.cropRect.centerY();
        startHintAnimation();
    }

    private void startHintAnimation() {
        ValueAnimator valueAnimator = this.hintAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.hintAnimator.cancel();
        }
        this.hintAnimationPlayed = true;
        ValueAnimator ofFloat = ValueAnimator.ofFloat(1.0f, 1.8f);
        this.hintAnimator = ofFloat;
        ofFloat.setDuration(700L);
        this.hintAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        this.hintAnimator.setRepeatCount(3);
        this.hintAnimator.setRepeatMode(2);
        this.hintAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: hazem.nurmontage.videoquran.views.CropView.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator2) {
                float floatValue = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                float f = CropView.this.initialHintRectWidth * floatValue;
                float f2 = CropView.this.initialHintRectHeight * floatValue;
                float f3 = f / 2.0f;
                float f4 = f2 / 2.0f;
                CropView.this.cropRect.set(CropView.this.initialHintRectCenterX - f3, CropView.this.initialHintRectCenterY - f4, CropView.this.initialHintRectCenterX + f3, CropView.this.initialHintRectCenterY + f4);
                CropView.this.invalidate();
            }
        });
        this.hintAnimator.start();
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.bitmap != null) {
            canvas.save();
            canvas.translate(this.mDrawingX, this.mDrawingY);
            canvas.clipRect(0, 0, this.bitmap.getWidth(), this.bitmap.getHeight());
            canvas.drawBitmap(this.bitmap, this.matrix, this.bitmapPaint);
            RectF rectF = this.cropRect;
            int i = this.radius;
            canvas.drawRoundRect(rectF, i, i, this.cropPaint);
            canvas.restore();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void moveCropRect(float f, float f2) {
        float f3 = this.cropRect.left + f;
        float f4 = this.cropRect.top + f2;
        float f5 = this.cropRect.right + f;
        float f6 = this.cropRect.bottom + f2;
        if (f3 < 0.0f) {
            f5 = this.cropRect.width();
            f3 = 0.0f;
        }
        if (f4 < 0.0f) {
            f6 = this.cropRect.height();
            f4 = 0.0f;
        }
        float f7 = this.mWidth;
        if (f5 > f7) {
            f3 = f7 - this.cropRect.width();
            f5 = f7;
        }
        float f8 = this.mHeight;
        if (f6 > f8) {
            f4 = f8 - this.cropRect.height();
            f6 = f8;
        }
        float f9 = f5 - f3;
        float f10 = this.minW;
        if (f9 < f10) {
            if (f > 0.0f) {
                f5 = f3 + f10;
            } else {
                f3 = f5 - f10;
            }
        }
        float f11 = f6 - f4;
        float f12 = this.minH;
        if (f11 < f12) {
            if (f2 > 0.0f) {
                f6 = f4 + f12;
            } else {
                f4 = f6 - f12;
            }
        }
        this.cropRect.set(f3, f4, f5, f6);
    }

    public float getmY() {
        if (this.bitmap == null) {
            return 0.4f;
        }
        return Math.max(this.cropRect.top / this.mHeight, 0.0f);
    }

    public float getmX() {
        if (this.bitmap == null) {
            return 0.4f;
        }
        return Math.max(this.cropRect.left / this.mWidth, 0.0f);
    }

    public float getmW() {
        if (this.bitmap == null) {
            return 1.0f;
        }
        return this.cropRect.width() / this.mWidth;
    }

    public float getmH() {
        if (this.bitmap == null) {
            return 1.0f;
        }
        return this.cropRect.height() / this.mHeight;
    }

    public RectF getCropRect() {
        return this.cropRect;
    }

    public Bitmap getCroppedBitmap() {
        if (this.bitmap == null) {
            return null;
        }
        int round = Math.round(this.cropRect.left / this.scale);
        int round2 = Math.round(this.cropRect.top / this.scale);
        if (round < 0) {
            round = 0;
        }
        if (round2 < 0) {
            round2 = 0;
        }
        return UtilsBitmap.cropToSquareWithRoundCornersPlusScale(this.bitmap, new Rect(round, round2, Math.min(Math.round(this.cropRect.right / this.scale), this.bitmap.getWidth()), Math.min(Math.round(this.cropRect.bottom / this.scale), this.bitmap.getHeight())), this.radius, Common.MIN_SQUARE_W, Common.MIN_SQUARE_H);
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
        public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
        }

        private ScaleListener() {
        }

        @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
        public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
            CropView.this.lastFocusX = scaleGestureDetector.getFocusX();
            CropView.this.lastFocusY = scaleGestureDetector.getFocusY();
            return true;
        }

        @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            float scaleFactor = scaleGestureDetector.getScaleFactor();
            if (Float.isNaN(scaleFactor) || Float.isInfinite(scaleFactor)) {
                return false;
            }
            CropView.this.scaleFactor *= scaleFactor;
            float focusX = scaleGestureDetector.getFocusX();
            float focusY = scaleGestureDetector.getFocusY();
            float width = CropView.this.cropRect.width() * scaleFactor;
            float height = CropView.this.cropRect.height() * scaleFactor;
            if (width < CropView.this.minW) {
                width = CropView.this.minW;
            }
            if (height < CropView.this.minH) {
                height = CropView.this.minH;
            }
            if (width > CropView.this.mWidth) {
                width = CropView.this.cropRect.width();
            }
            if (height > CropView.this.mHeight) {
                height = CropView.this.cropRect.height();
            }
            float f = focusX - CropView.this.lastFocusX;
            float f2 = focusY - CropView.this.lastFocusY;
            float centerX = CropView.this.cropRect.centerX();
            float centerY = CropView.this.cropRect.centerY();
            float f3 = width / 2.0f;
            float f4 = height / 2.0f;
            CropView.this.cropRect.set(centerX - f3, centerY - f4, centerX + f3, centerY + f4);
            CropView.this.moveCropRect(f, f2);
            CropView.this.lastFocusX = focusX;
            CropView.this.lastFocusY = focusY;
            CropView.this.invalidate();
            return true;
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        if (this.hintAnimator != null && this.hintAnimator.isRunning()) {
            this.hintAnimator.cancel();
            this.hintAnimationPlayed = true;
        }
        boolean scaleResult = this.scaleGestureDetector.onTouchEvent(event);
        int action = event.getActionMasked();
        float x = event.getX();
        float y = event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (!this.scaleGestureDetector.isInProgress()) {
                    this.isDragging = true;
                    this.startX = x;
                    this.startY = y;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (!this.scaleGestureDetector.isInProgress() && this.isDragging && event.getPointerCount() == 1) {
                    float dx = x - this.startX;
                    float dy = y - this.startY;
                    moveCropRect(dx, dy);
                    this.startX = x;
                    this.startY = y;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                if (event.getActionIndex() == 0) {
                    this.isDragging = false;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                this.isDragging = false;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                this.isDragging = false;
                break;
        }
        if (scaleResult || this.isDragging || action == MotionEvent.ACTION_DOWN) {
            invalidate();
            return true;
        }
        return super.onTouchEvent(event);
    }
}

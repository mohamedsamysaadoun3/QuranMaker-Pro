package hazem.nurmontage.videoquran.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import hazem.nurmontage.videoquran.R;
import hazem.nurmontage.videoquran.Utils.LocaleHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes2.dex */
public class CustomDiscreteSeekBar extends View {
    private int mCurrentProgressIndex;
    private int[] mGradientColors;
    private boolean mIsDragging;
    private boolean mIsRTL;
    private float mLabelTextSize;
    private List<String> mLabels;
    private OnProgressChangeListener mListener;
    private int mMaxProgressIndex;
    private float mPaddingBottom;
    private Paint mProgressPaint;
    private Paint mTextPaint;
    private Paint mThumbPaint;
    private float mThumbRadius;
    private float mThumbX;
    private Paint mTickPaint;
    private float[] mTickPositionsX;
    private float mTickRadius;
    private float mTrackHeight;
    private Paint mTrackPaint;
    private RectF mTrackRect;

    public interface OnProgressChangeListener {
        void onProgressChanged(CustomDiscreteSeekBar customDiscreteSeekBar, int i, String str, boolean z);

        void onStartTrackingTouch(CustomDiscreteSeekBar customDiscreteSeekBar);

        void onStopTrackingTouch(CustomDiscreteSeekBar customDiscreteSeekBar);
    }

    public void setOnProgressChangeListener(OnProgressChangeListener onProgressChangeListener) {
        this.mListener = onProgressChangeListener;
    }

    public CustomDiscreteSeekBar(Context context) {
        super(context);
        init(null);
    }

    public CustomDiscreteSeekBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(attributeSet);
    }

    public CustomDiscreteSeekBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(attributeSet);
    }

    private void init(AttributeSet attributeSet) {
        int i;
        boolean equals = LocaleHelper.getLanguage(getContext()).equals("ar");
        this.mIsRTL = equals;
        if (equals) {
            this.mGradientColors = new int[]{Color.parseColor("#fae065"), Color.parseColor("#cbd653"), Color.parseColor("#a8ce46")};
        } else {
            this.mGradientColors = new int[]{Color.parseColor("#a8ce46"), Color.parseColor("#cbd653"), Color.parseColor("#fae065")};
        }
        this.mTrackHeight = dpToPx(1.2f);
        this.mThumbRadius = dpToPx(10.0f);
        this.mTickRadius = dpToPx(4.0f);
        this.mLabelTextSize = spToPx(10.5f);
        this.mPaddingBottom = dpToPx(8.0f);
        Paint paint = new Paint(1);
        this.mTrackPaint = paint;
        paint.setColor(-3355444);
        this.mTrackPaint.setStyle(Paint.Style.FILL);
        Paint paint2 = new Paint(1);
        this.mProgressPaint = paint2;
        paint2.setStyle(Paint.Style.FILL);
        Paint paint3 = new Paint(1);
        this.mThumbPaint = paint3;
        paint3.setColor(-3355444);
        this.mThumbPaint.setStyle(Paint.Style.FILL);
        Paint paint4 = new Paint(1);
        this.mTickPaint = paint4;
        paint4.setColor(-3355444);
        this.mTickPaint.setStyle(Paint.Style.FILL);
        Paint paint5 = new Paint(1);
        this.mTextPaint = paint5;
        paint5.setColor(-3355444);
        this.mTextPaint.setTextSize(this.mLabelTextSize);
        this.mTextPaint.setTextAlign(Paint.Align.CENTER);
        this.mTextPaint.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "fonts/ReadexPro_Medium.ttf"));
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.CustomDiscreteSeekBar);
            try {
                i = obtainStyledAttributes.getResourceId(R.styleable.CustomDiscreteSeekBar_labelsArray, 0);
            } finally {
                obtainStyledAttributes.recycle();
            }
        } else {
            i = 0;
        }
        if (i != 0) {
            this.mLabels = new ArrayList(Arrays.asList(getContext().getResources().getStringArray(i)));
        } else {
            this.mLabels = new ArrayList();
        }
        this.mMaxProgressIndex = this.mLabels.size() - 1;
        this.mCurrentProgressIndex = 0;
        this.mTrackRect = new RectF();
        this.mTickPositionsX = new float[this.mLabels.size()];
        this.mIsDragging = false;
    }

    public List<String> getmLabels() {
        return this.mLabels;
    }

    private float dpToPx(float f) {
        return TypedValue.applyDimension(1, f, getResources().getDisplayMetrics());
    }

    private float spToPx(float f) {
        return TypedValue.applyDimension(2, f, getResources().getDisplayMetrics());
    }

    public void setProgress(int i) {
        if (i < 0 || i > this.mMaxProgressIndex) {
            return;
        }
        boolean z = this.mCurrentProgressIndex != i;
        this.mCurrentProgressIndex = i;
        calculateThumbPositionForIndex();
        invalidate();
        OnProgressChangeListener onProgressChangeListener = this.mListener;
        if (onProgressChangeListener == null || !z) {
            return;
        }
        int i2 = this.mCurrentProgressIndex;
        onProgressChangeListener.onProgressChanged(this, i2, this.mLabels.get(i2), false);
    }

    public int getProgress() {
        return this.mCurrentProgressIndex;
    }

    public String getCurrentLabel() {
        int i = this.mCurrentProgressIndex;
        if (i >= 0 && i < this.mLabels.size()) {
            return this.mLabels.get(this.mCurrentProgressIndex);
        }
        return "";
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int dpToPx = (int) dpToPx(200.0f);
        int dpToPx2 = (int) ((this.mThumbRadius * 2.0f) + this.mLabelTextSize + this.mPaddingBottom + dpToPx(8.0f));
        int mode = View.MeasureSpec.getMode(i);
        int size = View.MeasureSpec.getSize(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int size2 = View.MeasureSpec.getSize(i2);
        if (mode == 1073741824) {
            dpToPx = size;
        } else if (mode == Integer.MIN_VALUE) {
            dpToPx = Math.min(dpToPx, size);
        }
        if (mode2 == 1073741824) {
            dpToPx2 = size2;
        } else if (mode2 == Integer.MIN_VALUE) {
            dpToPx2 = Math.min(dpToPx2, size2);
        }
        setMeasuredDimension(dpToPx, dpToPx2);
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        float paddingStart;
        float width;
        super.onSizeChanged(i, i2, i3, i4);
        if (this.mIsRTL) {
            paddingStart = (getWidth() - getPaddingEnd()) - this.mThumbRadius;
            width = getPaddingStart() + this.mThumbRadius;
        } else {
            paddingStart = getPaddingStart() + this.mThumbRadius;
            width = (getWidth() - getPaddingEnd()) - this.mThumbRadius;
        }
        RectF rectF = this.mTrackRect;
        float min = Math.min(paddingStart, width);
        float paddingTop = (getPaddingTop() + this.mThumbRadius) - (this.mTrackHeight / 2.0f);
        float max = Math.max(paddingStart, width);
        float paddingTop2 = getPaddingTop() + this.mThumbRadius;
        float f = this.mTrackHeight;
        rectF.set(min, paddingTop, max, (paddingTop2 - (f / 2.0f)) + f);
        this.mProgressPaint.setShader(new LinearGradient(this.mTrackRect.left, this.mTrackRect.centerY(), this.mTrackRect.right, this.mTrackRect.centerY(), this.mGradientColors, (float[]) null, Shader.TileMode.CLAMP));
        if (this.mLabels.size() > 1) {
            float abs = Math.abs(width - paddingStart) / (this.mLabels.size() - 1);
            for (int i5 = 0; i5 < this.mLabels.size(); i5++) {
                if (this.mIsRTL) {
                    this.mTickPositionsX[i5] = paddingStart - (i5 * abs);
                } else {
                    this.mTickPositionsX[i5] = (i5 * abs) + paddingStart;
                }
            }
        } else if (this.mLabels.size() == 1) {
            this.mTickPositionsX[0] = this.mTrackRect.centerX();
        }
        calculateThumbPositionForIndex();
    }

    private void calculateThumbPositionForIndex() {
        if (this.mMaxProgressIndex >= 0) {
            this.mThumbX = this.mTickPositionsX[this.mCurrentProgressIndex];
        } else {
            this.mThumbX = this.mIsRTL ? this.mTrackRect.right : this.mTrackRect.left;
        }
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRoundRect(this.mTrackRect, 100.0f, 100.0f, this.mTrackPaint);
        if (this.mIsRTL) {
            canvas.drawRoundRect(this.mThumbX, this.mTrackRect.top, this.mTrackRect.right, this.mTrackRect.bottom, 100.0f, 100.0f, this.mProgressPaint);
        } else {
            canvas.drawRoundRect(this.mTrackRect.left, this.mTrackRect.top, this.mThumbX, this.mTrackRect.bottom, 100.0f, 100.0f, this.mProgressPaint);
        }
        for (int i = 0; i < this.mLabels.size(); i++) {
            float f = this.mTickPositionsX[i];
            if (i == 0) {
                if (this.mIsRTL) {
                    f -= this.mThumbRadius * 0.7f;
                } else {
                    f += this.mThumbRadius * 0.7f;
                }
            }
            if (i == this.mLabels.size() - 1) {
                if (this.mIsRTL) {
                    f += this.mThumbRadius;
                } else {
                    f -= this.mThumbRadius;
                }
            }
            float centerY = this.mTrackRect.centerY();
            String str = this.mLabels.get(i);
            this.mTextPaint.getTextBounds(str, 0, str.length(), new Rect());
            Rect textBounds = new Rect();
            this.mTextPaint.getTextBounds(str, 0, str.length(), textBounds);
            canvas.drawText(str, f, centerY + this.mThumbRadius + this.mPaddingBottom + textBounds.height(), this.mTextPaint);
        }
        canvas.drawCircle(this.mThumbX, this.mTrackRect.centerY(), this.mThumbRadius, this.mThumbPaint);
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0037, code lost:
    
        if (r5 != 3) goto L39;
     */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean onTouchEvent(android.view.MotionEvent r8) {
        /*
            r7 = this;
            boolean r0 = r7.isEnabled()
            r1 = 0
            if (r0 == 0) goto L95
            int r0 = r7.mMaxProgressIndex
            if (r0 >= 0) goto Ld
            goto L95
        Ld:
            float r0 = r8.getX()
            float r2 = r8.getY()
            boolean r3 = r7.mIsRTL
            if (r3 == 0) goto L22
            android.graphics.RectF r3 = r7.mTrackRect
            float r3 = r3.left
            android.graphics.RectF r4 = r7.mTrackRect
            float r4 = r4.right
            goto L2a
        L22:
            android.graphics.RectF r3 = r7.mTrackRect
            float r3 = r3.left
            android.graphics.RectF r4 = r7.mTrackRect
            float r4 = r4.right
        L2a:
            int r5 = r8.getAction()
            r6 = 1
            if (r5 == 0) goto L70
            if (r5 == r6) goto L4c
            r2 = 2
            if (r5 == r2) goto L3a
            r2 = 3
            if (r5 == r2) goto L4c
            goto L90
        L3a:
            boolean r1 = r7.mIsDragging
            if (r1 == 0) goto L90
            float r8 = java.lang.Math.min(r0, r4)
            float r8 = java.lang.Math.max(r3, r8)
            r7.mThumbX = r8
            r7.invalidate()
            return r6
        L4c:
            boolean r2 = r7.mIsDragging
            if (r2 == 0) goto L90
            r7.mIsDragging = r1
            int r8 = r7.mCurrentProgressIndex
            r7.snapToNearestTickAndNotify(r0)
            hazem.nurmontage.videoquran.views.CustomDiscreteSeekBar$OnProgressChangeListener r0 = r7.mListener
            if (r0 == 0) goto L6f
            int r1 = r7.mCurrentProgressIndex
            if (r8 == r1) goto L6a
            java.util.List<java.lang.String> r8 = r7.mLabels
            java.lang.Object r8 = r8.get(r1)
            java.lang.String r8 = (java.lang.String) r8
            r0.onProgressChanged(r7, r1, r8, r6)
        L6a:
            hazem.nurmontage.videoquran.views.CustomDiscreteSeekBar$OnProgressChangeListener r8 = r7.mListener
            r8.onStopTrackingTouch(r7)
        L6f:
            return r6
        L70:
            boolean r1 = r7.isTouchNearThumbOrTrack(r0, r2)
            if (r1 == 0) goto L90
            r7.mIsDragging = r6
            hazem.nurmontage.videoquran.views.CustomDiscreteSeekBar$OnProgressChangeListener r8 = r7.mListener
            if (r8 == 0) goto L7f
            r8.onStartTrackingTouch(r7)
        L7f:
            float r8 = java.lang.Math.min(r0, r4)
            float r8 = java.lang.Math.max(r3, r8)
            r7.mThumbX = r8
            r7.invalidate()
            r7.performClick()
            return r6
        L90:
            boolean r8 = super.onTouchEvent(r8)
            return r8
        L95:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: hazem.nurmontage.videoquran.views.CustomDiscreteSeekBar.onTouchEvent(android.view.MotionEvent):boolean");
    }

    private boolean isTouchNearThumbOrTrack(float f, float f2) {
        float dpToPx = dpToPx(20.0f);
        return f2 > (this.mTrackRect.centerY() - this.mThumbRadius) - dpToPx && f2 < (((this.mTrackRect.centerY() + this.mThumbRadius) + this.mLabelTextSize) + this.mPaddingBottom) + dpToPx && f > (this.mTrackRect.left - this.mThumbRadius) - dpToPx && f < (this.mTrackRect.right + this.mThumbRadius) + dpToPx;
    }

    private void snapToNearestTickAndNotify(float f) {
        int i = 0;
        float f2 = Float.MAX_VALUE;
        int i2 = 0;
        while (true) {
            float[] fArr = this.mTickPositionsX;
            if (i < fArr.length) {
                float abs = Math.abs(f - fArr[i]);
                if (abs < f2) {
                    i2 = i;
                    f2 = abs;
                }
                i++;
            } else {
                this.mCurrentProgressIndex = i2;
                calculateThumbPositionForIndex();
                invalidate();
                return;
            }
        }
    }

    @Override // android.view.View
    public boolean performClick() {
        super.performClick();
        return true;
    }
}

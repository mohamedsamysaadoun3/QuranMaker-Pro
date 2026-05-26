package hazem.nurmontage.videoquran.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import hazem.nurmontage.videoquran.R;
import hazem.nurmontage.videoquran.common.Common;

/* loaded from: classes2.dex */
public class SquareImageView extends AppCompatImageView {
    private int anInt;
    private float cx;
    private float cy;
    private Drawable drawableDone;
    private boolean isSelect;
    private String number;
    private Paint paint;
    private Paint paintRect;
    private float r;
    private TextPaint textPaint;
    private float x;
    private float y;

    public int getAnInt() {
        return this.anInt;
    }

    public void setNumber(int i) {
        if (i == 0) {
            return;
        }
        this.anInt = i;
        this.number = "" + i;
        this.cx = (getWidth() * 0.5f) - (this.textPaint.measureText(this.number) * 0.5f);
    }

    public boolean isMSelect() {
        return this.isSelect;
    }

    public SquareImageView(Context context) {
        super(context);
        this.paint = new Paint(1);
        this.paintRect = new Paint(1);
        this.textPaint = new TextPaint(1);
        init();
    }

    public SquareImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.paint = new Paint(1);
        this.paintRect = new Paint(1);
        this.textPaint = new TextPaint(1);
        init();
    }

    public SquareImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.paint = new Paint(1);
        this.paintRect = new Paint(1);
        this.textPaint = new TextPaint(1);
        init();
    }

    private void init() {
        this.textPaint.setColor(-1);
        this.textPaint.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "fonts/" + Common.english_app_font));
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int measuredWidth = getMeasuredWidth();
        setMeasuredDimension(measuredWidth, measuredWidth);
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.paintRect.setColor(-1056964608);
        float f = i;
        this.paint.setStrokeWidth(0.02f * f);
        if (!this.isSelect) {
            this.paint.setColor(-8355712);
            this.paint.setStyle(Paint.Style.STROKE);
        } else {
            this.paint.setColor(-12190534);
            this.paint.setStyle(Paint.Style.FILL);
        }
        this.textPaint.setTextSize(0.25f * f);
        float f2 = 0.1f * f;
        this.r = f2;
        this.x = f - (1.2f * f2);
        this.y = f2 + this.paint.getStrokeWidth();
        if (this.number != null) {
            this.cx = (getWidth() * 0.5f) - (this.textPaint.measureText(this.number) * 0.5f);
        }
        this.cy = getHeight() * 0.5f;
        int i5 = (int) (f * 0.3f);
        int width = (int) (getWidth() * 0.5f);
        float f3 = this.cy;
        float f4 = i5;
        Rect rect = new Rect(width - i5, (int) (f3 - f4), width + i5, (int) (f3 + f4));
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.check_24px);
        this.drawableDone = drawable;
        drawable.setBounds(rect.left, rect.top, rect.right, rect.bottom);
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.paint == null || !this.isSelect) {
            return;
        }
        canvas.drawRect(0.0f, 0.0f, getWidth(), getHeight(), this.paintRect);
        Drawable drawable = this.drawableDone;
        if (drawable != null) {
            drawable.draw(canvas);
        }
        String str = this.number;
        if (str != null) {
            canvas.drawText(str, this.cx, this.cy, this.textPaint);
        }
    }

    public void onSelect(boolean z) {
        this.isSelect = z;
        if (!z) {
            this.paint.setColor(-8355712);
            this.paint.setStyle(Paint.Style.STROKE);
        } else {
            this.paint.setColor(-12190534);
            this.paint.setStyle(Paint.Style.FILL);
        }
        invalidate();
    }
}

package hazem.nurmontage.videoquran.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import hazem.nurmontage.videoquran.Utils.LocaleHelper;
import java.util.Locale;

/* loaded from: classes2.dex */
public class SquareOutlineProgressBar extends View {
    private float cornerRadius;
    private int[] gradientColors;
    private int maxProgress;
    private final Path partialPath;
    private final Path path;
    private final PathMeasure pathMeasure;
    private int progress;
    private final Paint progressPaint;
    private LinearGradient progressShader;
    private final RectF rect;
    private String strHint;
    private float strokeWidth;
    private final TextPaint textPaint;
    private int trackColor;
    private final Paint trackPaint;
    private float xH;
    private float xP;
    private float y_hint;
    private float y_progrees;

    public int getMax() {
        return this.maxProgress;
    }

    public SquareOutlineProgressBar(Context context) {
        super(context);
        this.trackPaint = new Paint(1);
        this.progressPaint = new Paint(1);
        this.rect = new RectF();
        this.path = new Path();
        this.partialPath = new Path();
        this.pathMeasure = new PathMeasure();
        this.progress = 0;
        this.maxProgress = 100;
        this.textPaint = new TextPaint(1);
        this.trackColor = 587202559;
        this.gradientColors = new int[]{Color.parseColor("#a8ce46"), Color.parseColor("#D2DE49"), Color.parseColor("#F4D853")};
        init();
    }

    public SquareOutlineProgressBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.trackPaint = new Paint(1);
        this.progressPaint = new Paint(1);
        this.rect = new RectF();
        this.path = new Path();
        this.partialPath = new Path();
        this.pathMeasure = new PathMeasure();
        this.progress = 0;
        this.maxProgress = 100;
        this.textPaint = new TextPaint(1);
        this.trackColor = 587202559;
        this.gradientColors = new int[]{Color.parseColor("#a8ce46"), Color.parseColor("#D2DE49"), Color.parseColor("#F4D853")};
        init();
    }

    public SquareOutlineProgressBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.trackPaint = new Paint(1);
        this.progressPaint = new Paint(1);
        this.rect = new RectF();
        this.path = new Path();
        this.partialPath = new Path();
        this.pathMeasure = new PathMeasure();
        this.progress = 0;
        this.maxProgress = 100;
        this.textPaint = new TextPaint(1);
        this.trackColor = 587202559;
        this.gradientColors = new int[]{Color.parseColor("#a8ce46"), Color.parseColor("#D2DE49"), Color.parseColor("#F4D853")};
        init();
    }

    private void init() {
        this.trackPaint.setStyle(Paint.Style.STROKE);
        this.trackPaint.setStrokeCap(Paint.Cap.ROUND);
        this.trackPaint.setStrokeJoin(Paint.Join.ROUND);
        this.trackPaint.setColor(this.trackColor);
        this.progressPaint.setStyle(Paint.Style.STROKE);
        this.progressPaint.setStrokeCap(Paint.Cap.ROUND);
        this.progressPaint.setStrokeJoin(Paint.Join.ROUND);
        this.textPaint.setColor(-1);
        Typeface createFromAsset = Typeface.createFromAsset(getResources().getAssets(), "fonts/ReadexPro_Medium.ttf");
        if (LocaleHelper.getLanguage(getContext()).equals("ar")) {
            this.strHint = "يرجى عدم قفل الشاشة أو التبديل إلى تطبيقات أخرى.";
        } else {
            this.strHint = "Please don't lock the screen or switch to other apps.";
        }
        this.textPaint.setTypeface(createFromAsset);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int size = View.MeasureSpec.getSize(i);
        setMeasuredDimension((int) (size / 1.618034f), size);
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        updateShader(i, i2);
        float f = i;
        this.textPaint.setTextSize(0.033f * f);
        Rect rect = new Rect();
        this.cornerRadius = 0.04f * f;
        float f2 = f * 0.0085f;
        this.strokeWidth = f2;
        this.trackPaint.setStrokeWidth(f2);
        this.progressPaint.setStrokeWidth(this.strokeWidth);
        TextPaint textPaint = this.textPaint;
        String str = this.strHint;
        textPaint.getTextBounds(str, 0, str.length(), rect);
        float f3 = this.strokeWidth / 2.0f;
        this.rect.set(f3, f3, getWidth() - f3, getHeight() - f3);
        this.y_progrees = this.rect.centerY() - rect.height();
        this.y_hint = this.rect.centerY() + rect.height();
        this.xH = this.rect.centerX() - (rect.width() * 0.5f);
        this.textPaint.getTextBounds("100", 0, 3, rect);
        this.xP = this.rect.centerX() - (rect.width() * 0.5f);
    }

    private void updateShader(int i, int i2) {
        if (i == 0 || i2 == 0) {
            return;
        }
        LinearGradient linearGradient = new LinearGradient(0.0f, 0.0f, i, i2, this.gradientColors, (float[]) null, Shader.TileMode.CLAMP);
        this.progressShader = linearGradient;
        this.progressPaint.setShader(linearGradient);
    }

    public void setProgress(int i) {
        int max = Math.max(0, Math.min(i, this.maxProgress));
        if (this.progress != max) {
            this.progress = max;
            invalidate();
        }
    }

    public int getProgress() {
        return this.progress;
    }

    public void setMaxProgress(int i) {
        this.maxProgress = Math.max(1, i);
        invalidate();
    }

    public void setCornerRadius(float f) {
        this.cornerRadius = f;
        invalidate();
    }

    public void setStrokeWidth(float f) {
        this.strokeWidth = f;
        this.trackPaint.setStrokeWidth(f);
        this.progressPaint.setStrokeWidth(f);
        invalidate();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float f = this.rect.left;
        float f2 = this.rect.top;
        float f3 = this.rect.right;
        float f4 = this.rect.bottom;
        float f5 = this.cornerRadius;
        canvas.drawRoundRect(this.rect, f5, f5, this.trackPaint);
        canvas.drawText(String.format(Locale.US, "%% %d", Integer.valueOf(this.progress)), this.xP, this.y_progrees, this.textPaint);
        canvas.drawText(this.strHint, this.xH, this.y_hint, this.textPaint);
        this.path.reset();
        this.partialPath.reset();
        float f6 = f + f5;
        this.path.moveTo(f6, f2);
        this.path.lineTo(f3 - f5, f2);
        float f7 = 2.0f * f5;
        float f8 = f3 - f7;
        float f9 = f2 + f7;
        this.path.arcTo(new RectF(f8, f2, f3, f9), -90.0f, 90.0f, false);
        this.path.lineTo(f3, f4 - f5);
        float f10 = f4 - f7;
        this.path.arcTo(new RectF(f8, f10, f3, f4), 0.0f, 90.0f, false);
        this.path.lineTo(f6, f4);
        float f11 = f7 + f;
        this.path.arcTo(new RectF(f, f10, f11, f4), 90.0f, 90.0f, false);
        this.path.lineTo(f, f5 + f2);
        this.path.arcTo(new RectF(f, f2, f11, f9), 180.0f, 90.0f, false);
        this.path.close();
        this.pathMeasure.setPath(this.path, false);
        this.pathMeasure.getSegment(0.0f, this.pathMeasure.getLength() * (this.progress / this.maxProgress), this.partialPath, true);
        canvas.drawPath(this.partialPath, this.progressPaint);
    }
}

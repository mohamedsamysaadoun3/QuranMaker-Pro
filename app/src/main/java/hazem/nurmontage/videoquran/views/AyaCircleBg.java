package hazem.nurmontage.videoquran.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;

/* loaded from: classes2.dex */
public class AyaCircleBg extends AppCompatTextView {
    private Paint bgPaint;
    private Path circlePath;
    private RectF circleRect;
    private Typeface typeface;

    public AyaCircleBg(Context context) {
        super(context);
        init(context);
    }

    public AyaCircleBg(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public AyaCircleBg(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    private void init(Context context) {
        if (this.typeface == null) {
            Typeface createFromAsset = Typeface.createFromAsset(getResources().getAssets(), "fonts/arabic/محمدي.ttf");
            this.typeface = createFromAsset;
            setTypeface(createFromAsset);
        }
        Paint paint = new Paint(1);
        this.bgPaint = paint;
        paint.setStyle(Paint.Style.FILL);
        this.circleRect = new RectF();
        this.circlePath = new Path();
    }

    @Override // android.widget.TextView, android.view.View
    protected void onDraw(Canvas canvas) {
        String obj = getText().toString();
        TextPaint paint = getPaint();
        float measureText = paint.measureText(obj);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float max = (Math.max(measureText, fontMetrics.descent - fontMetrics.ascent) / 2.0f) + 20.0f;
        float width = getWidth() / 2.0f;
        float height = getHeight() / 2.0f;
        float f = measureText / 2.0f;
        this.bgPaint.setShader(new LinearGradient(width - f, height, width + f, height, new int[]{Color.parseColor("#B7833AB4"), Color.parseColor("#E1306C"), Color.parseColor("#BCF58529")}, (float[]) null, Shader.TileMode.CLAMP));
        canvas.drawCircle(width, height, max, this.bgPaint);
        super.onDraw(canvas);
    }
}

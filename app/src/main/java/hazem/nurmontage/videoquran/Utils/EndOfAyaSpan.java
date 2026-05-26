package hazem.nurmontage.videoquran.Utils;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.VectorDrawable;
import android.text.style.ReplacementSpan;

/* loaded from: classes2.dex */
public class EndOfAyaSpan extends ReplacementSpan {
    private Typeface fontNumber;
    private String number;
    private VectorDrawable vectorDrawable;

    public EndOfAyaSpan(VectorDrawable vectorDrawable, Typeface typeface, String str) {
        this.vectorDrawable = vectorDrawable;
        this.fontNumber = typeface;
        this.number = str;
    }

    @Override // android.text.style.ReplacementSpan
    public void draw(Canvas canvas, CharSequence charSequence, int i, int i2, float f, int i3, int i4, int i5, Paint paint) {
        String substring = charSequence.toString().substring(i, i2);
        if (substring != null && !substring.equals(" نص")) {
            canvas.drawText(charSequence, i, i2, f, i4, paint);
            return;
        }
        float measureText = paint.measureText(substring);
        Typeface typeface = paint.getTypeface();
        float textSize = paint.getTextSize();
        paint.setTypeface(this.fontNumber);
        paint.setFakeBoldText(true);
        if (this.number.length() > 2) {
            paint.setTextSize(paint.getTextSize() * 0.8f);
        } else {
            paint.setTextSize(paint.getTextSize() * 0.7f);
        }
        Rect rect = new Rect();
        String str = this.number;
        paint.getTextBounds(str, 0, str.length(), rect);
        RectF rectF = new RectF((int) f, i3, (int) (measureText + f), i5);
        float width = rectF.width() * 0.43f;
        float height = rectF.height() * 0.42f;
        this.vectorDrawable.setBounds((int) (rectF.centerX() - width), (int) (rectF.centerY() - height), (int) (rectF.centerX() + width), (int) (rectF.centerY() + height));
        this.vectorDrawable.setColorFilter(paint.getColor(), PorterDuff.Mode.SRC_IN);
        this.vectorDrawable.draw(canvas);
        if (this.number.length() > 2) {
            paint.setTextSize(paint.getTextSize() * 0.7f);
            canvas.drawText(this.number, rectF.centerX() - (rect.width() * 0.4f), rectF.centerY() + (rect.height() * 0.35f), paint);
        } else {
            canvas.drawText(this.number, rectF.centerX() - (rect.width() * 0.54f), rectF.centerY() + (rect.height() * 0.4f), paint);
        }
        paint.setTypeface(typeface);
        paint.setTextSize(textSize);
        paint.setFakeBoldText(false);
    }

    @Override // android.text.style.ReplacementSpan
    public int getSize(Paint paint, CharSequence charSequence, int i, int i2, Paint.FontMetricsInt fontMetricsInt) {
        return Math.round(paint.measureText(charSequence, i, i2));
    }
}

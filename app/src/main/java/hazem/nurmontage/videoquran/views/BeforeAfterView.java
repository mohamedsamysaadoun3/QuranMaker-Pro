package hazem.nurmontage.videoquran.views;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.ViewCompat;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.signature.ObjectKey;
import hazem.nurmontage.videoquran.Utils.AppUtils;
import hazem.nurmontage.videoquran.Utils.LocaleHelper;
import hazem.nurmontage.videoquran.Utils.ScreenUtils;
import hazem.nurmontage.videoquran.common.Common;
import java.util.concurrent.ExecutionException;

/* loaded from: classes2.dex */
public class BeforeAfterView extends View {
    private Bitmap afterImage;
    private Bitmap beforeImage;
    private Paint circlePaint;
    private float circleRadius;
    private float dividerX;
    private ValueAnimator hintAnimator;
    private Paint imagePaint;
    private boolean isShowTxt;
    private boolean isStartAnim;
    private Paint linePaint;
    private Paint textPaint;
    private String txt;
    private float x_text;
    private float y_text;

    public void setTxt(String str) {
        this.txt = str;
    }

    public void showText(int i) {
        this.isShowTxt = true;
        Paint paint = new Paint();
        this.textPaint = paint;
        paint.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "fonts/arabic/فرشة.ttf"));
        Paint paint2 = this.textPaint;
        paint2.setTextSize(calculateTextSize(this.txt, i, paint2));
    }

    public float calculateTextSize(String str, int i, Paint paint) {
        float f = 400.0f;
        paint.setTextSize(400.0f);
        Rect rect = new Rect();
        while (true) {
            if (rect.width() > i || rect.height() > i) {
                f -= 1.0f;
                paint.setTextSize(f);
                paint.getTextBounds(str, 0, str.length(), rect);
            } else {
                float f2 = i / 2.0f;
                this.x_text = f2 - (rect.width() / 2.0f);
                this.y_text = f2 + (rect.height() / 2.0f);
                return f;
            }
        }
    }

    public boolean isShowTxt() {
        return this.isShowTxt;
    }

    public BeforeAfterView(Context context) {
        super(context);
        this.dividerX = -1.0f;
        init(context);
    }

    public BeforeAfterView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.dividerX = -1.0f;
        init(context);
    }

    private void init(final Context context) {
        Paint paint = new Paint(1);
        this.linePaint = paint;
        paint.setColor(SupportMenu.CATEGORY_MASK);
        Paint paint2 = new Paint(1);
        this.circlePaint = paint2;
        paint2.setColor(SupportMenu.CATEGORY_MASK);
        this.circlePaint.setStyle(Paint.Style.FILL);
        this.imagePaint = new Paint(1);
        ScreenUtils.getScreenWidth(getActivity(context));
        new Thread(new Runnable() { // from class: hazem.nurmontage.videoquran.views.BeforeAfterView.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    BeforeAfterView beforeAfterView = BeforeAfterView.this;
                    beforeAfterView.addTextPaint(beforeAfterView.beforeImage, BeforeAfterView.this.afterImage, context);
                    BeforeAfterView.this.invalidate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static Bitmap get(Context context, int i, int i2, int i3) throws ExecutionException, InterruptedException {
        FutureTarget submit = Glide.with(context).asBitmap().load(Integer.valueOf(i3)).diskCacheStrategy(DiskCacheStrategy.NONE).signature(new ObjectKey(AppUtils.getAppVersionName(context))).override(i, i2).centerInside().submit();
        Bitmap copy = ((Bitmap) submit.get()).copy(Bitmap.Config.ARGB_8888, true);
        Glide.with(context).clear(submit);
        return copy;
    }

    public static Activity getActivity(Context context) {
        if ((context instanceof ContextWrapper) && (context instanceof Activity)) {
            return (Activity) context;
        }
        return null;
    }

    public void addTextPaint(Bitmap bitmap, Bitmap bitmap2, Context context) {
        Paint paint = new Paint(1);
        paint.setTextSize(bitmap.getWidth() * 0.025f);
        paint.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "fonts/arabic/" + Common.english_app_font));
        Canvas canvas = new Canvas();
        float width = bitmap.getWidth() * 0.04f;
        float height = bitmap.getHeight() * 0.025f;
        if (LocaleHelper.getLanguage(context).equals("ar")) {
            paint.setColor(-7829368);
            canvas.setBitmap(bitmap);
            canvas.drawText("قبل", width, height, paint);
            canvas.setBitmap(bitmap2);
            float width2 = (bitmap.getWidth() - paint.measureText("بعد")) - width;
            paint.setColor(-15605);
            canvas.drawText("بعد", width2, height, paint);
            return;
        }
        paint.setColor(-7829368);
        canvas.setBitmap(bitmap);
        canvas.drawText("BEFORE", width, height, paint);
        canvas.setBitmap(bitmap2);
        float measureText = paint.measureText("AFTER");
        paint.setColor(-15605);
        canvas.drawText("AFTER", (bitmap.getWidth() - measureText) - width, height, paint);
    }

    private void initHintAnimation(int i) {
        ValueAnimator valueAnimator = this.hintAnimator;
        if (valueAnimator == null || !valueAnimator.isRunning()) {
            float f = this.dividerX;
            ValueAnimator ofFloat = ValueAnimator.ofFloat(f, (i * 0.065f) + f);
            this.hintAnimator = ofFloat;
            ofFloat.setDuration(700L);
            this.hintAnimator.setRepeatMode(2);
            this.hintAnimator.setRepeatCount(-1);
            this.hintAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: hazem.nurmontage.videoquran.views.BeforeAfterView.2
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    BeforeAfterView.this.dividerX = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                    BeforeAfterView.this.invalidate();
                }
            });
        }
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int size = View.MeasureSpec.getSize(i);
        setMeasuredDimension(size, size);
        float f = size;
        this.dividerX = f / 2.0f;
        float f2 = f * 0.05f;
        this.circleRadius = f2;
        this.linePaint.setStrokeWidth(f2 * 0.1f);
        initHintAnimation(size);
    }

    public void release() {
        Bitmap bitmap = this.afterImage;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.afterImage.recycle();
        }
        Bitmap bitmap2 = this.beforeImage;
        if (bitmap2 == null || bitmap2.isRecycled()) {
            return;
        }
        this.beforeImage.recycle();
    }

    public void setBeforeImage(Bitmap bitmap) {
        Bitmap bitmap2 = this.beforeImage;
        if (bitmap2 != null && !bitmap2.isRecycled()) {
            this.beforeImage.recycle();
        }
        this.beforeImage = bitmap;
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(ViewCompat.MEASURED_STATE_MASK);
        if (this.isShowTxt) {
            canvas.drawColor(-1);
            canvas.save();
            canvas.clipRect(this.dividerX, 0.0f, getWidth(), getHeight());
            canvas.drawText(this.txt, this.x_text, this.y_text, this.imagePaint);
            canvas.restore();
            float f = this.dividerX;
            canvas.drawLine(f, 0.0f, f, getHeight(), this.linePaint);
            canvas.drawCircle(this.dividerX, getHeight() / 2.0f, this.circleRadius, this.circlePaint);
            drawArrows(canvas, this.dividerX, getHeight() / 2.0f);
        } else {
            Bitmap bitmap = this.beforeImage;
            if (bitmap != null && this.afterImage != null) {
                canvas.drawBitmap(bitmap, 0.0f, 0.0f, this.imagePaint);
                canvas.save();
                canvas.clipRect(this.dividerX, 0.0f, getWidth(), getHeight());
                canvas.drawBitmap(this.afterImage, 0.0f, 0.0f, this.imagePaint);
                canvas.restore();
                float f2 = this.dividerX;
                canvas.drawLine(f2, 0.0f, f2, getHeight(), this.linePaint);
                canvas.drawCircle(this.dividerX, getHeight() / 2.0f, this.circleRadius, this.circlePaint);
                drawArrows(canvas, this.dividerX, getHeight() / 2.0f);
            }
        }
        if (this.isStartAnim) {
            return;
        }
        ValueAnimator valueAnimator = this.hintAnimator;
        if (valueAnimator != null) {
            valueAnimator.start();
        }
        this.isStartAnim = true;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        ValueAnimator valueAnimator;
        if (this.isStartAnim && motionEvent.getAction() == 0 && (valueAnimator = this.hintAnimator) != null) {
            valueAnimator.cancel();
        }
        if (motionEvent.getAction() != 2) {
            return true;
        }
        this.dividerX = motionEvent.getX();
        invalidate();
        return true;
    }

    private void drawArrows(Canvas canvas, float f, float f2) {
        float f3 = this.circleRadius / 3.0f;
        Path path = new Path();
        path.moveTo(f - (this.circleRadius / 2.0f), f2);
        float f4 = f2 - f3;
        path.lineTo((f - (this.circleRadius / 2.0f)) + f3, f4);
        float f5 = f2 + f3;
        path.lineTo((f - (this.circleRadius / 2.0f)) + f3, f5);
        path.close();
        Path path2 = new Path();
        path2.moveTo((this.circleRadius / 2.0f) + f, f2);
        path2.lineTo(((this.circleRadius / 2.0f) + f) - f3, f4);
        path2.lineTo((f + (this.circleRadius / 2.0f)) - f3, f5);
        path2.close();
        Paint paint = new Paint(1);
        paint.setColor(-1);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPath(path, paint);
        canvas.drawPath(path2, paint);
    }
}

package hazem.nurmontage.videoquran.views;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.ViewCompat;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.signature.ObjectKey;
import hazem.nurmontage.videoquran.R;
import hazem.nurmontage.videoquran.Utils.AppUtils;
import hazem.nurmontage.videoquran.Utils.LocaleHelper;
import hazem.nurmontage.videoquran.Utils.ScreenUtils;
import java.util.concurrent.ExecutionException;

/* loaded from: classes2.dex */
public class CropViewHint extends View {
    private int arrowHeadSize;
    private Paint arrowPaint;
    private Bitmap bitmap;
    private Paint cropPaint;
    private RectF cropRect;
    private float endLineX;
    private float endLineX_arrow;
    private float endLineY;
    private float endLineY_arrow;
    private float endX;
    private float endY;
    private Paint imagePaint;
    private Bitmap ipadBitmap;
    private Paint linePaint;
    private Rect mTextRect;
    private String mTittle;
    private float radius;
    private Paint textPaint;
    private float x_text;
    private float y_bitmap;
    private float y_text;

    public CropViewHint(Context context) {
        super(context);
        init(context);
    }

    public CropViewHint(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    private void init(final Context context) {
        Paint paint = new Paint();
        this.linePaint = paint;
        paint.setColor(SupportMenu.CATEGORY_MASK);
        this.linePaint.setStrokeWidth(5.0f);
        this.linePaint.setStyle(Paint.Style.STROKE);
        this.linePaint.setAntiAlias(true);
        Paint paint2 = new Paint();
        this.arrowPaint = paint2;
        paint2.setColor(SupportMenu.CATEGORY_MASK);
        this.arrowPaint.setStyle(Paint.Style.FILL);
        this.arrowPaint.setAntiAlias(true);
        Paint paint3 = new Paint();
        this.cropPaint = paint3;
        paint3.setColor(-15605);
        this.cropPaint.setStyle(Paint.Style.STROKE);
        this.cropPaint.setStrokeWidth(5.0f);
        this.cropPaint.setAntiAlias(true);
        this.mTextRect = new Rect();
        this.imagePaint = new Paint(1);
        Paint paint4 = new Paint(1);
        this.textPaint = paint4;
        paint4.setColor(-1);
        if (LocaleHelper.getLanguage(context).equals("ar")) {
            this.mTittle = "تحكم في شاشة الآيبود";
            this.textPaint.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "fonts/ReadexPro_Medium.ttf"));
        } else {
            this.mTittle = "iPod screen selection";
            this.textPaint.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "fonts/ReadexPro_Medium.ttf"));
        }
        final int screenWidth = (int) (ScreenUtils.getScreenWidth(getActivity(context)) * 0.52f);
        ScreenUtils.getScreenWidth(getActivity(context));
        new Thread(new Runnable() { // from class: hazem.nurmontage.videoquran.views.CropViewHint.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    CropViewHint cropViewHint = CropViewHint.this;
                    Context context2 = context;
                    int i = screenWidth;
                    cropViewHint.bitmap = CropViewHint.get(context2, i, i, R.drawable.bg_13);
                    CropViewHint.this.invalidate();
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

    private void drawArrowHead(Canvas canvas, float f, float f2, float f3, float f4) {
        double atan2 = Math.atan2(f2 - f4, f - f3);
        double d = f;
        double d2 = atan2 - 0.5235987755982988d;
        float cos = (float) (d - (this.arrowHeadSize * Math.cos(d2)));
        double d3 = f2;
        float sin = (float) (d3 - (this.arrowHeadSize * Math.sin(d2)));
        double d4 = atan2 + 0.5235987755982988d;
        float cos2 = (float) (d - (this.arrowHeadSize * Math.cos(d4)));
        float sin2 = (float) (d3 - (this.arrowHeadSize * Math.sin(d4)));
        Path path = new Path();
        path.moveTo(f, f2);
        path.lineTo(cos, sin);
        path.lineTo(cos2, sin2);
        path.close();
        canvas.drawPath(path, this.arrowPaint);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int size = View.MeasureSpec.getSize(i);
        setMeasuredDimension(size, size);
        float f = size;
        this.linePaint.setStrokeWidth(0.0085f * f);
        this.textPaint.setTextSize(f * 0.045f);
        Paint paint = this.textPaint;
        String str = this.mTittle;
        paint.getTextBounds(str, 0, str.length(), this.mTextRect);
        this.x_text = (size - this.mTextRect.width()) * 0.5f;
        float height = this.mTextRect.height() * 1.2f;
        this.y_text = height;
        this.y_bitmap = height + this.mTextRect.height();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(ViewCompat.MEASURED_STATE_MASK);
        Bitmap bitmap = this.bitmap;
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, 0.0f, this.y_bitmap, this.imagePaint);
            canvas.drawText(this.mTittle, this.x_text, this.y_text, this.textPaint);
            if (this.ipadBitmap != null) {
                if (this.cropRect == null) {
                    float width = this.bitmap.getWidth() * 0.25f;
                    float height = (this.bitmap.getHeight() * 0.08f) + this.y_bitmap;
                    this.cropRect = new RectF(width, height, (this.bitmap.getWidth() * 0.35f) + width, (this.bitmap.getHeight() * 0.43f) + height);
                    this.radius = (int) (Math.min(this.cropRect.width(), this.cropRect.height()) * 0.10800001f);
                    this.arrowHeadSize = (int) (this.bitmap.getWidth() * 0.1f);
                    this.endX = getWidth() - this.ipadBitmap.getWidth();
                    this.endY = this.y_bitmap + this.bitmap.getHeight();
                    float height2 = this.ipadBitmap.getHeight() * 0.28f;
                    float width2 = this.ipadBitmap.getWidth() * 0.3f;
                    float f = this.endY;
                    this.endLineY_arrow = f + height2;
                    float f2 = this.endX;
                    this.endLineX_arrow = f2 + width2;
                    this.endLineY = (f * 0.98f) + height2;
                    this.endLineX = (f2 * 0.98f) + width2;
                }
                RectF rectF = this.cropRect;
                float f3 = this.radius;
                canvas.drawRoundRect(rectF, f3, f3, this.cropPaint);
                canvas.drawBitmap(this.ipadBitmap, this.endX, this.endY, this.imagePaint);
                canvas.drawLine(this.cropRect.centerX(), this.cropRect.centerY(), this.endLineX, this.endLineY, this.linePaint);
                drawArrowHead(canvas, this.endLineX_arrow, this.endLineY_arrow, 0.0f, 0.0f);
            }
        }
    }
}

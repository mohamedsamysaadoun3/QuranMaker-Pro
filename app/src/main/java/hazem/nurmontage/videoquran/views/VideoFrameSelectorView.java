package hazem.nurmontage.videoquran.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.core.internal.view.SupportMenu;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class VideoFrameSelectorView extends View {
    private float cornerRadius;
    private Paint cursorPaint;
    private float cursorX;
    private List<BitmapFrame> frameBitmaps;
    private int frameCount;
    private float frameHeight;
    private Paint framePaint;
    private RectF frameRect;
    private float frameSpacing;
    private float frameWidth;
    private OnFrameSelectedListener onFrameSelectedListener;
    private int selectedFrameIndex;
    private Uri videoUri;

    public interface OnFrameSelectedListener {
        void onFrameSelected(int i, Bitmap bitmap);
    }

    public VideoFrameSelectorView(Context context) {
        super(context);
        this.frameCount = 7;
        this.frameBitmaps = new ArrayList();
        this.selectedFrameIndex = 0;
        this.cursorX = 0.0f;
        this.framePaint = new Paint();
        this.cursorPaint = new Paint();
        this.frameRect = new RectF();
        this.frameSpacing = 1.0f;
        this.cornerRadius = 10.0f;
        init();
    }

    public VideoFrameSelectorView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.frameCount = 7;
        this.frameBitmaps = new ArrayList();
        this.selectedFrameIndex = 0;
        this.cursorX = 0.0f;
        this.framePaint = new Paint();
        this.cursorPaint = new Paint();
        this.frameRect = new RectF();
        this.frameSpacing = 1.0f;
        this.cornerRadius = 10.0f;
        init();
    }

    private void init() {
        this.framePaint.setColor(-7829368);
        this.cursorPaint.setColor(SupportMenu.CATEGORY_MASK);
        this.cursorPaint.setStrokeWidth(5.0f);
        this.cursorPaint.setStyle(Paint.Style.STROKE);
    }

    public void setVideoUri(Uri uri) {
        this.videoUri = uri;
        loadFrames();
        invalidate();
    }

    private void loadFrames() {
        if (this.videoUri == null) {
            return;
        }
        this.frameBitmaps.clear();
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        try {
            try {
                try {
                    mediaMetadataRetriever.setDataSource(getContext(), this.videoUri);
                    long parseLong = Long.parseLong(mediaMetadataRetriever.extractMetadata(9)) / this.frameCount;
                    for (int i = 0; i < this.frameCount; i++) {
                        long j = i * parseLong * 1000;
                        Bitmap frameAtTime = mediaMetadataRetriever.getFrameAtTime(j, 2);
                        if (frameAtTime != null) {
                            this.frameBitmaps.add(new BitmapFrame(frameAtTime, j));
                        }
                    }
                    mediaMetadataRetriever.release();
                } catch (Exception e) {
                    e.printStackTrace();
                    mediaMetadataRetriever.release();
                }
            } catch (Throwable th) {
                try {
                    mediaMetadataRetriever.release();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                throw th;
            }
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        int i5 = this.frameCount;
        if (i5 > 0) {
            float f = (i * 1.0f) / i5;
            this.frameWidth = f;
            this.frameHeight = f;
            this.cursorX = f / 2.0f;
        }
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.frameBitmaps.isEmpty()) {
            float width = getWidth();
            float height = getHeight();
            float f = this.cornerRadius;
            canvas.drawRoundRect(0.0f, 0.0f, width, height, f, f, this.framePaint);
            return;
        }
        canvas.save();
        canvas.translate(0.0f, (getHeight() - this.frameHeight) * 0.5f);
        for (int i = 0; i < this.frameBitmaps.size(); i++) {
            float f2 = this.frameWidth;
            float f3 = i * (this.frameSpacing + f2);
            this.frameRect.set(f3, 0.0f, f2 + f3, this.frameHeight);
            RectF rectF = this.frameRect;
            float f4 = this.cornerRadius;
            canvas.drawRoundRect(rectF, f4, f4, this.framePaint);
            Bitmap bitmap = this.frameBitmaps.get(i).bitmap;
            if (bitmap != null) {
                canvas.drawBitmap(bitmap, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), this.frameRect, (Paint) null);
            }
        }
        canvas.restore();
        float f5 = this.cursorX;
        canvas.drawLine(f5, 0.0f, f5, getHeight(), this.cursorPaint);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0 || action == 2) {
            float max = Math.max(0.0f, Math.min(motionEvent.getX(), getWidth()));
            this.cursorX = max;
            int i = (int) (max / (this.frameWidth + this.frameSpacing));
            this.selectedFrameIndex = i;
            this.selectedFrameIndex = Math.max(0, Math.min(i, this.frameCount - 1));
            invalidate();
            return true;
        }
        return super.onTouchEvent(motionEvent);
    }

    public BitmapFrame getFrameBitmap() {
        int i = this.selectedFrameIndex;
        if (i < 0 || i >= this.frameBitmaps.size()) {
            return null;
        }
        return this.frameBitmaps.get(i);
    }

    public void setOnFrameSelectedListener(OnFrameSelectedListener onFrameSelectedListener) {
        this.onFrameSelectedListener = onFrameSelectedListener;
    }

    public class BitmapFrame {
        private Bitmap bitmap;
        private long time;

        public BitmapFrame(Bitmap bitmap, long j) {
            this.bitmap = bitmap;
            this.time = j;
        }

        public Bitmap getBitmap() {
            return this.bitmap;
        }

        public long getTime() {
            return this.time;
        }
    }
}

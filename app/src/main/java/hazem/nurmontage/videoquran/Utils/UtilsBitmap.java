package hazem.nurmontage.videoquran.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

/* loaded from: classes2.dex */
public class UtilsBitmap {
    public static Bitmap cropToSquare(Bitmap bitmap, Rect rect, int i, int i2) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap, rect.left, rect.top, i, i2);
        return (createBitmap.getWidth() == rect.width() && bitmap.getHeight() == rect.height()) ? createBitmap : Bitmap.createScaledBitmap(createBitmap, rect.width(), rect.height(), true);
    }

    public static Bitmap cropToSquare(Bitmap bitmap, Rect rect, float f, float f2) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap, rect.left, rect.top, (int) (bitmap.getWidth() * f), (int) (bitmap.getHeight() * f2));
        return (createBitmap.getWidth() == rect.width() && bitmap.getHeight() == rect.height()) ? createBitmap : Bitmap.createScaledBitmap(createBitmap, rect.width(), rect.height(), true);
    }

    public static Bitmap cropToSquare(Bitmap bitmap, Rect rect) {
        return Bitmap.createBitmap(bitmap, rect.left, rect.top, rect.width(), rect.height());
    }

    public static Bitmap cropToSquareWithRoundCorners(Bitmap bitmap, Rect rect, int i, float f, float f2) {
        int width = (int) (bitmap.getWidth() * f);
        int height = (int) (bitmap.getHeight() * f2);
        Bitmap createBitmap = Bitmap.createBitmap(bitmap, rect.left, rect.top, width, height);
        if (height != rect.height() || width != rect.width()) {
            createBitmap = Bitmap.createScaledBitmap(createBitmap, rect.width(), rect.height(), true);
        }
        RectF rectF = new RectF(0.0f, 0.0f, createBitmap.getWidth(), createBitmap.getHeight());
        Path path = new Path();
        float f3 = i;
        path.addRoundRect(rectF, f3, f3, Path.Direction.CW);
        Bitmap createBitmap2 = Bitmap.createBitmap(createBitmap.getWidth(), createBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap2);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(createBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        canvas.drawPath(path, paint);
        return createBitmap2;
    }

    public static Bitmap cropToSquareWithRoundCorners(Bitmap bitmap, Rect rect, int i, int i2, int i3) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap, rect.left, rect.top, i2, i3);
        if (i3 != rect.height() || i2 != rect.width()) {
            createBitmap = Bitmap.createScaledBitmap(createBitmap, rect.width(), rect.height(), true);
        }
        RectF rectF = new RectF(0.0f, 0.0f, createBitmap.getWidth(), createBitmap.getHeight());
        Path path = new Path();
        float f = i;
        path.addRoundRect(rectF, f, f, Path.Direction.CW);
        Bitmap createBitmap2 = Bitmap.createBitmap(createBitmap.getWidth(), createBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap2);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(createBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        canvas.drawPath(path, paint);
        return createBitmap2;
    }

    public static Bitmap cropToSquareWithRoundCorners(Bitmap bitmap, Rect rect, int i) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap, rect.left, rect.top, rect.width(), rect.height());
        RectF rectF = new RectF(0.0f, 0.0f, createBitmap.getWidth(), createBitmap.getHeight());
        Path path = new Path();
        float f = i;
        path.addRoundRect(rectF, f, f, Path.Direction.CW);
        Bitmap createBitmap2 = Bitmap.createBitmap(createBitmap.getWidth(), createBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap2);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(createBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        canvas.drawPath(path, paint);
        return createBitmap2;
    }

    public static Bitmap cropToSquareWithRoundCornersPlusScale(Bitmap bitmap, Rect rect, int i, int i2, int i3) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap, rect.left, rect.top, rect.width(), rect.height());
        if (i3 != rect.height() || i2 != rect.width()) {
            createBitmap = Bitmap.createScaledBitmap(createBitmap, i2, i3, true);
        }
        RectF rectF = new RectF(0.0f, 0.0f, createBitmap.getWidth(), createBitmap.getHeight());
        Path path = new Path();
        float f = i;
        path.addRoundRect(rectF, f, f, Path.Direction.CW);
        Bitmap createBitmap2 = Bitmap.createBitmap(createBitmap.getWidth(), createBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap2);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(createBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        canvas.drawPath(path, paint);
        return createBitmap2;
    }

    public static Bitmap cropToSquareWithRoundCorners(Bitmap bitmap, int i, int i2, int i3) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap, (bitmap.getWidth() - i) / 2, (bitmap.getHeight() - i2) / 2, i, i2);
        RectF rectF = new RectF(0.0f, 0.0f, i, i2);
        Path path = new Path();
        float f = i3;
        path.addRoundRect(rectF, f, f, Path.Direction.CW);
        Bitmap createBitmap2 = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap2);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(createBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        canvas.drawPath(path, paint);
        return createBitmap2;
    }

    public static Bitmap getResizedBitmap(Bitmap bitmap, int i, int i2) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(i / width, i2 / height);
        Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
        bitmap.recycle();
        return createBitmap;
    }

    public static Bitmap blur(Context context, Bitmap bitmap, int i, int i2) {
        if (i < 1) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bitmap, width / i2, height / i2, false);
        RenderScript create = RenderScript.create(context);
        Allocation createFromBitmap = Allocation.createFromBitmap(create, createScaledBitmap);
        Allocation createTyped = Allocation.createTyped(create, createFromBitmap.getType());
        ScriptIntrinsicBlur create2 = ScriptIntrinsicBlur.create(create, Element.U8_4(create));
        create2.setRadius(i);
        create2.setInput(createFromBitmap);
        create2.forEach(createTyped);
        createTyped.copyTo(createScaledBitmap);
        create.destroy();
        return Bitmap.createScaledBitmap(createScaledBitmap, width, height, false);
    }

    public static Bitmap blurInSave(Context context, Bitmap bitmap, int i, int i2, int i3, int i4) {
        if (i < 1) {
            return null;
        }
        Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() / i2, bitmap.getHeight() / i2, false);
        RenderScript create = RenderScript.create(context);
        Allocation createFromBitmap = Allocation.createFromBitmap(create, createScaledBitmap);
        Allocation createTyped = Allocation.createTyped(create, createFromBitmap.getType());
        ScriptIntrinsicBlur create2 = ScriptIntrinsicBlur.create(create, Element.U8_4(create));
        create2.setRadius(i);
        create2.setInput(createFromBitmap);
        create2.forEach(createTyped);
        createTyped.copyTo(createScaledBitmap);
        create.destroy();
        return Bitmap.createScaledBitmap(createScaledBitmap, i3, i4, false);
    }

    public static Bitmap cropBitmap(Bitmap bitmap, int i, int i2) {
        int width = (bitmap.getWidth() - i) / 2;
        int height = (bitmap.getHeight() - i2) / 2;
        if (width < 0) {
            width = 0;
        }
        if (height < 0) {
            height = 0;
        }
        if (width + i > bitmap.getWidth()) {
            i = bitmap.getWidth() - width;
        }
        if (height + i2 > bitmap.getHeight()) {
            i2 = bitmap.getHeight() - height;
        }
        return Bitmap.createBitmap(bitmap, width, height, i, i2);
    }
}

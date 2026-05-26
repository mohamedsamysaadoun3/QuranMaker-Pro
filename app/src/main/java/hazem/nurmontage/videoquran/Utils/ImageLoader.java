package hazem.nurmontage.videoquran.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.concurrent.ExecutionException;

/* loaded from: classes2.dex */
public class ImageLoader {
    public static Bitmap loadAndCropAndBlur(Context context, Object obj, int i, int i2) throws ExecutionException, InterruptedException {
        return Glide.with(context).asBitmap().load(obj).apply((BaseRequestOptions<?>) RequestOptions.bitmapTransform(new StoryCropTransformation(i, i2)).override(i, i2)).submit().get();
    }

    public static class StoryCropTransformation implements Transformation<Bitmap> {
        private final int targetHeight;
        private final int targetWidth;

        @Override // com.bumptech.glide.load.Transformation
        public Resource<Bitmap> transform(Context context, Resource<Bitmap> resource, int i, int i2) {
            return null;
        }

        public StoryCropTransformation(int i, int i2) {
            this.targetWidth = i;
            this.targetHeight = i2;
        }

        public Resource<Bitmap> transform(BitmapPool bitmapPool, Resource<Bitmap> resource, int i, int i2) {
            Bitmap bitmap = resource.get();
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            float f = width;
            float f2 = height;
            float f3 = f / f2;
            float f4 = this.targetWidth / this.targetHeight;
            if (f3 > f4) {
                int i3 = (int) (f2 * f4);
                bitmap = Bitmap.createBitmap(bitmap, (width - i3) / 2, 0, i3, height);
            } else if (f3 < f4) {
                int i4 = (int) (f / f4);
                bitmap = Bitmap.createBitmap(bitmap, 0, (height - i4) / 2, width, i4);
            }
            return BitmapResource.obtain(bitmap, bitmapPool);
        }

        @Override // com.bumptech.glide.load.Key
        public void updateDiskCacheKey(MessageDigest messageDigest) {
            messageDigest.update(("storyCrop(targetWidth=" + this.targetWidth + ", targetHeight=" + this.targetHeight + ")").getBytes(StandardCharsets.UTF_8));
        }
    }
}

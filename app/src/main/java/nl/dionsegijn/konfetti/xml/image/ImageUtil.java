package nl.dionsegijn.konfetti.xml.image;

import android.graphics.drawable.Drawable;
import nl.dionsegijn.konfetti.core.models.Shape;

public class ImageUtil {
    public static Shape.DrawableShape loadDrawable(Drawable drawable, boolean mutate, boolean allowRandomColor) {
        return new Shape.DrawableShape(drawable, mutate);
    }
}

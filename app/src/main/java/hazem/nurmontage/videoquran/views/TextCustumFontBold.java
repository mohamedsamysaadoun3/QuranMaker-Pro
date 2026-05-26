package hazem.nurmontage.videoquran.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;

/* loaded from: classes2.dex */
public class TextCustumFontBold extends AppCompatTextView {
    private Typeface typeface;

    public TextCustumFontBold(Context context) {
        super(context);
        init(context);
    }

    public TextCustumFontBold(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public TextCustumFontBold(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    private void init(Context context) {
        if (this.typeface == null) {
            Typeface createFromAsset = Typeface.createFromAsset(getResources().getAssets(), "fonts/ReadexPro_Bold.ttf");
            this.typeface = createFromAsset;
            setTypeface(createFromAsset);
        }
    }
}

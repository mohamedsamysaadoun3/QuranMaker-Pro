package hazem.nurmontage.videoquran.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatButton;

/* loaded from: classes2.dex */
public class ButtonCustumFontBilling extends AppCompatButton {
    private Typeface typeface;

    public ButtonCustumFontBilling(Context context) {
        super(context);
        init(context);
    }

    public ButtonCustumFontBilling(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public ButtonCustumFontBilling(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    private void init(Context context) {
        if (this.typeface == null) {
            Typeface createFromAsset = Typeface.createFromAsset(getResources().getAssets(), "fonts/ReadexPro_Medium.ttf");
            this.typeface = createFromAsset;
            setTypeface(createFromAsset);
        }
    }
}

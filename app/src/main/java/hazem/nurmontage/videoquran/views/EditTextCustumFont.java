package hazem.nurmontage.videoquran.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatEditText;

/* loaded from: classes2.dex */
public class EditTextCustumFont extends AppCompatEditText {
    private Typeface typeface;

    public EditTextCustumFont(Context context) {
        super(context);
        init(context);
    }

    public EditTextCustumFont(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public EditTextCustumFont(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    private void init(Context context) {
        if (this.typeface == null) {
            Typeface createFromAsset = Typeface.createFromAsset(getResources().getAssets(), "fonts/arabic/خط الإبل.otf");
            this.typeface = createFromAsset;
            setTypeface(createFromAsset);
        }
    }
}

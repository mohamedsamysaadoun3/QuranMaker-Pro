package hazem.nurmontage.videoquran.Utils;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;

/* loaded from: classes2.dex */
public class NonScrollableLinearLayoutManager extends LinearLayoutManager {
    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean canScrollVertically() {
        return false;
    }

    public NonScrollableLinearLayoutManager(Context context) {
        super(context);
    }
}

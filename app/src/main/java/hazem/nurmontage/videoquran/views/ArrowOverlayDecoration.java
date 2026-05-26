package hazem.nurmontage.videoquran.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes2.dex */
public class ArrowOverlayDecoration extends RecyclerView.ItemDecoration {
    private final Drawable arrowDrawable;
    private final int arrowSize;

    public ArrowOverlayDecoration(Context context, int i, int i2) {
        this.arrowDrawable = AppCompatResources.getDrawable(context, i);
        this.arrowSize = (int) TypedValue.applyDimension(1, i2, context.getResources().getDisplayMetrics());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void onDrawOver(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            int findFirstCompletelyVisibleItemPosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
            int findLastCompletelyVisibleItemPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
            int itemCount = linearLayoutManager.getItemCount();
            int height = recyclerView.getHeight() / 2;
            if (findLastCompletelyVisibleItemPosition < itemCount - 1) {
                int i = this.arrowSize;
                int i2 = height - (i / 2);
                this.arrowDrawable.setBounds(0, i2, i, i2 + i);
                this.arrowDrawable.setAutoMirrored(false);
                this.arrowDrawable.draw(canvas);
            }
            if (findFirstCompletelyVisibleItemPosition > 0) {
                int width = recyclerView.getWidth();
                int i3 = height - (this.arrowSize / 2);
                canvas.save();
                canvas.scale(-1.0f, 1.0f, width - (this.arrowSize / 2.0f), 0.0f);
                Drawable drawable = this.arrowDrawable;
                int i4 = this.arrowSize;
                drawable.setBounds(width - i4, i3, width, i4 + i3);
                this.arrowDrawable.draw(canvas);
                canvas.restore();
            }
        }
    }
}

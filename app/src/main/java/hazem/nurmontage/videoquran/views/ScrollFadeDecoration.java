package hazem.nurmontage.videoquran.views;

import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes2.dex */
public class ScrollFadeDecoration extends RecyclerView.ItemDecoration {
    private final Paint paint = new Paint();
    private final int fadeWidth = 50;
    private final int fadeColor = -2013265920;

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void onDrawOver(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            int findFirstCompletelyVisibleItemPosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
            int findLastCompletelyVisibleItemPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
            int itemCount = linearLayoutManager.getItemCount();
            int width = recyclerView.getWidth();
            int height = recyclerView.getHeight();
            if (findFirstCompletelyVisibleItemPosition > 0) {
                this.paint.setShader(new LinearGradient(0.0f, 0.0f, 50.0f, 0.0f, -2013265920, 0, Shader.TileMode.CLAMP));
                canvas.drawRect(0.0f, 0.0f, 50.0f, height, this.paint);
            }
            if (findLastCompletelyVisibleItemPosition < itemCount - 1) {
                float f = width - 50;
                float f2 = width;
                this.paint.setShader(new LinearGradient(f, 0.0f, f2, 0.0f, 0, -2013265920, Shader.TileMode.CLAMP));
                canvas.drawRect(f, 0.0f, f2, height, this.paint);
            }
            this.paint.setShader(null);
        }
    }
}

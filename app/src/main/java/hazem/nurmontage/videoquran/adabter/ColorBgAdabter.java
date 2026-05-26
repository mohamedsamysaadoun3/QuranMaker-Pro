package hazem.nurmontage.videoquran.adabter;

import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import hazem.nurmontage.videoquran.R;

/* loaded from: classes2.dex */
public class ColorBgAdabter extends RecyclerView.Adapter<ColorBgAdabter.ViewHolder> {
    private int[] colors;
    private boolean enabled = true;
    public IColor iColorCallback;
    private int pos_select;

    public interface IColor {
        void onColor(int i, int i2);
    }

    public ColorBgAdabter(IColor iColor, int[] iArr, int i) {
        this.colors = iArr;
        this.iColorCallback = iColor;
        this.pos_select = i;
    }

    public void setEnabled(boolean z) {
        this.enabled = z;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.image);
            view.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.adabter.ColorBgAdabter.ViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (ColorBgAdabter.this.iColorCallback == null || ColorBgAdabter.this.pos_select == ViewHolder.this.getAdapterPosition() || !ColorBgAdabter.this.enabled) {
                        return;
                    }
                    int i = ColorBgAdabter.this.pos_select;
                    ColorBgAdabter.this.pos_select = ViewHolder.this.getAdapterPosition();
                    ColorBgAdabter.this.notifyItemChanged(i);
                    ColorBgAdabter.this.notifyItemChanged(ColorBgAdabter.this.pos_select);
                    ColorBgAdabter.this.iColorCallback.onColor(ColorBgAdabter.this.colors[ViewHolder.this.getAdapterPosition()], ViewHolder.this.getAdapterPosition());
                }
            });
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_color, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        setGradientBackground(viewHolder.imageView, viewHolder.itemView, this.colors[i], i == this.pos_select);
    }

    public void setGradientBackground(View view, View view2, int i, boolean z) {
        if (z) {
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setShape(0);
            gradientDrawable.setCornerRadius(10.0f);
            gradientDrawable.setStroke(3, -1);
            view2.setBackground(gradientDrawable);
        } else {
            view2.setBackground(null);
        }
        GradientDrawable gradientDrawable2 = new GradientDrawable();
        gradientDrawable2.setShape(0);
        gradientDrawable2.setCornerRadius(10.0f);
        gradientDrawable2.setColor(i);
        view.setBackground(gradientDrawable2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        int[] iArr = this.colors;
        if (iArr == null) {
            return 0;
        }
        return iArr.length;
    }

    public int getPos_select() {
        return this.pos_select;
    }
}

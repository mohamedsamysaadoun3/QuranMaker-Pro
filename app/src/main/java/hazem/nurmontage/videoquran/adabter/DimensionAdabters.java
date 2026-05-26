package hazem.nurmontage.videoquran.adabter;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import hazem.nurmontage.videoquran.R;
import hazem.nurmontage.videoquran.constant.ResizeType;
import hazem.nurmontage.videoquran.model.ItemDimension;
import hazem.nurmontage.videoquran.views.TextCustumFont;
import java.util.List;

/* loaded from: classes2.dex */
public class DimensionAdabters extends RecyclerView.Adapter<DimensionAdabters.ViewHolder> {
    private List<Pair<Integer, Integer>> listDim;
    private List<ItemDimension> mDimensionList;
    private IDimensionCallback mIDimensionCallback;
    private int selected;

    public interface IDimensionCallback {
        void done();

        void isCustomSize(boolean z, ResizeType resizeType);

        void onCustumSize(int i, int i2, int i3, String str, int i4);
    }

    public DimensionAdabters(List<ItemDimension> list, IDimensionCallback iDimensionCallback, List<Pair<Integer, Integer>> list2, int i) {
        this.mDimensionList = list;
        this.mIDimensionCallback = iDimensionCallback;
        this.listDim = list2;
        this.selected = i;
    }

    public DimensionAdabters(List<ItemDimension> list, IDimensionCallback iDimensionCallback, List<Pair<Integer, Integer>> list2) {
        this.selected = 0;
        this.mDimensionList = list;
        this.mIDimensionCallback = iDimensionCallback;
        this.listDim = list2;
    }

    public void setSelected(int i) {
        this.selected = i;
    }

    public int getSelected() {
        return this.selected;
    }

    public int get() {
        return this.mDimensionList.get(getSelected()).getResizeType().ordinal();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_aspect, viewGroup, false));
    }

    public void update(List<ItemDimension> list) {
        this.mDimensionList.clear();
        this.mDimensionList = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Pair<Integer, Integer> pair = this.listDim.get(i);
        viewHolder.layout.getLayoutParams().width = ((Integer) pair.first).intValue();
        viewHolder.layout.getLayoutParams().height = ((Integer) pair.second).intValue();
        String[] split = this.mDimensionList.get(i).getName().split("\n");
        viewHolder.name.setText(split[0]);
        if (split.length > 1) {
            viewHolder.dimension.setText(split[1]);
        }
        Glide.with(viewHolder.itemView).asBitmap().centerInside().diskCacheStrategy(DiskCacheStrategy.ALL).load(Integer.valueOf(this.mDimensionList.get(i).getImage())).into(viewHolder.imageView);
        if (i == this.selected) {
            viewHolder.layout.setBackgroundResource(R.drawable.rect_btn_select);
        } else {
            viewHolder.layout.setBackgroundResource(R.drawable.rect_btn);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<ItemDimension> list = this.mDimensionList;
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public ResizeType getResizeSelected() {
        List<ItemDimension> list = this.mDimensionList;
        return list != null ? list.get(getSelected()).getResizeType() : ResizeType.SQUARE;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextCustumFont dimension;
        private final ImageView imageView;
        public FrameLayout layout;
        private final TextCustumFont name;

        public ViewHolder(View view) {
            super(view);
            this.layout = (FrameLayout) view.findViewById(R.id.layout);
            this.imageView = (ImageView) view.findViewById(R.id.icon);
            this.name = (TextCustumFont) view.findViewById(R.id.aspect_name);
            this.dimension = (TextCustumFont) view.findViewById(R.id.aspect_size);
            view.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.adabter.DimensionAdabters.ViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (DimensionAdabters.this.mIDimensionCallback != null) {
                        int i = DimensionAdabters.this.selected;
                        DimensionAdabters.this.selected = ViewHolder.this.getAdapterPosition();
                        DimensionAdabters.this.notifyItemChanged(i);
                        DimensionAdabters.this.notifyItemChanged(DimensionAdabters.this.selected);
                        ItemDimension itemDimension = (ItemDimension) DimensionAdabters.this.mDimensionList.get(ViewHolder.this.getAdapterPosition());
                        DimensionAdabters.this.mIDimensionCallback.onCustumSize(itemDimension.getW(), itemDimension.getH(), itemDimension.getResizeType().ordinal(), itemDimension.getId(), itemDimension.getImage());
                    }
                }
            });
        }
    }

    public void clear() {
        List<ItemDimension> list = this.mDimensionList;
        if (list != null) {
            list.clear();
            this.mDimensionList = null;
        }
        this.mIDimensionCallback = null;
    }
}

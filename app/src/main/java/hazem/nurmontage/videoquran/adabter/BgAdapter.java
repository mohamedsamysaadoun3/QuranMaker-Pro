package hazem.nurmontage.videoquran.adabter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.signature.ObjectKey;
import hazem.nurmontage.videoquran.R;
import hazem.nurmontage.videoquran.adabter.BgAdapter;
import hazem.nurmontage.videoquran.fragment.ChangeBgFragment;
import hazem.nurmontage.videoquran.model.BgItem;
import java.util.List;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/* loaded from: classes2.dex */
public class BgAdapter extends RecyclerView.Adapter<BgAdapter.ViewHolder> {
    private final String APP_VERSION;
    private final ChangeBgFragment.IChangeBgCallback iBgCallback;
    private final List<BgItem> images;
    private int selected;
    private final int size;

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public /* bridge */ /* synthetic */ void onBindViewHolder(ViewHolder viewHolder, int i, List list) {
        onBindViewHolder2(viewHolder, i, (List<Object>) list);
    }

    public BgAdapter(String str, ChangeBgFragment.IChangeBgCallback iChangeBgCallback, List<BgItem> list, int i, int i2) {
        this.APP_VERSION = str;
        this.iBgCallback = iChangeBgCallback;
        this.images = list;
        this.size = i;
        this.selected = i2;
        setHasStableIds(true);
    }

    public void add(BgItem bgItem) {
        int size = this.images.size();
        this.images.add(bgItem);
        notifyItemInserted(size);
    }

    public int getSelectedPosition() {
        return this.selected;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.img);
            view.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.adabter.BgAdapter$ViewHolder$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    BgAdapter.ViewHolder.this.m953x1a2a22a0(view2);
                }
            });
        }

        /* renamed from: lambda$new$0$hazem-nurmontage-videoquran-adabter-BgAdapter$ViewHolder, reason: not valid java name */
        /* synthetic */ void m953x1a2a22a0(View view) {
            int adapterPosition = getAdapterPosition();
            if (adapterPosition == -1) {
                return;
            }
            if (adapterPosition == BgAdapter.this.selected) {
                if (BgAdapter.this.iBgCallback != null) {
                    BgAdapter.this.iBgCallback.onAdd((BgItem) BgAdapter.this.images.get(adapterPosition));
                    return;
                }
                return;
            }
            int i = BgAdapter.this.selected;
            BgAdapter.this.selected = adapterPosition;
            if (i != -1) {
                BgAdapter.this.notifyItemChanged(i, "alpha");
            }
            BgAdapter bgAdapter = BgAdapter.this;
            bgAdapter.notifyItemChanged(bgAdapter.selected, "alpha");
            if (BgAdapter.this.iBgCallback != null) {
                BgAdapter.this.iBgCallback.onAdd((BgItem) BgAdapter.this.images.get(adapterPosition));
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_img_bg, viewGroup, false));
    }

    /* renamed from: onBindViewHolder, reason: avoid collision after fix types in other method */
    public void onBindViewHolder2(ViewHolder viewHolder, int i, List<Object> list) {
        if (!list.isEmpty()) {
            applyState(viewHolder, i);
            return;
        }
        RequestBuilder<Drawable> load = Glide.with(viewHolder.imageView).load(Integer.valueOf(this.images.get(i).getId()));
        int i2 = this.size;
        load.override(i2, i2).signature(new ObjectKey(this.APP_VERSION)).diskCacheStrategy(DiskCacheStrategy.NONE).transform(new MultiTransformation(new CenterCrop(), new RoundedCornersTransformation(10, 8))).into(viewHolder.imageView);
        applyState(viewHolder, i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        applyState(viewHolder, i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<BgItem> list = this.images;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int i) {
        return this.images.get(i).getId();
    }

    private void applyState(ViewHolder viewHolder, int i) {
        boolean z = i == this.selected;
        float f = z ? 1.0f : 0.65f;
        if (z) {
            viewHolder.itemView.setBackgroundResource(R.drawable.ipad_selected);
        } else {
            viewHolder.itemView.setBackgroundColor(0);
        }
        viewHolder.imageView.setAlpha(f);
    }
}

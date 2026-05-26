package hazem.nurmontage.videoquran.adabter;

import android.graphics.Bitmap;
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
import hazem.nurmontage.videoquran.fragment.ChangeBgFragment;
import hazem.nurmontage.videoquran.model.BgItem;
import java.util.List;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/* loaded from: classes2.dex */
public class BgAdabterL extends RecyclerView.Adapter<BgAdabterL.ViewHolder> {
    private String APP_VERSION;
    public ChangeBgFragment.IChangeBgCallback iBgCallback;
    private List<BgItem> images;
    private int selected = 0;
    private int size;

    public interface IBgCallback {
        void onBgClick(int i);
    }

    public void add(BgItem bgItem) {
        try {
            this.images.add(bgItem);
            notifyItemInserted(this.images.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BgAdabterL(String str, ChangeBgFragment.IChangeBgCallback iChangeBgCallback, List<BgItem> list, int i) {
        this.images = list;
        this.size = i;
        this.iBgCallback = iChangeBgCallback;
        this.APP_VERSION = str;
    }

    public int getPos_select() {
        return this.selected;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.img);
            view.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.adabter.BgAdabterL.ViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (BgAdabterL.this.iBgCallback != null) {
                        BgAdabterL.this.selected = ViewHolder.this.getAdapterPosition();
                        BgAdabterL.this.iBgCallback.onAdd((BgItem) BgAdabterL.this.images.get(ViewHolder.this.getAdapterPosition()));
                    }
                }
            });
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_img_bg, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        RequestBuilder<Bitmap> load = Glide.with(viewHolder.imageView).asBitmap().load(Integer.valueOf(this.images.get(i).getId()));
        int i2 = this.size;
        load.override(i2, i2).signature(new ObjectKey(this.APP_VERSION)).diskCacheStrategy(DiskCacheStrategy.NONE).transform(new MultiTransformation(new CenterCrop(), new RoundedCornersTransformation(8, 0, RoundedCornersTransformation.CornerType.ALL))).into(viewHolder.imageView);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<BgItem> list = this.images;
        if (list == null) {
            return 0;
        }
        return list.size();
    }
}

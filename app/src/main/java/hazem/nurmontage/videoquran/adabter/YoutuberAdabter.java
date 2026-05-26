package hazem.nurmontage.videoquran.adabter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.ObjectKey;
import hazem.nurmontage.videoquran.R;
import hazem.nurmontage.videoquran.model.YoutuberModel;
import java.util.List;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/* loaded from: classes2.dex */
public class YoutuberAdabter extends RecyclerView.Adapter<YoutuberAdabter.ViewHolder> {
    private String APP_VERSION;
    private int h;
    private IYoutuber iYoutuber;
    private List<YoutuberModel> images;
    private int w;

    public interface IYoutuber {
        void onClick(String str);
    }

    public YoutuberAdabter(IYoutuber iYoutuber, List<YoutuberModel> list, String str, int i, int i2) {
        this.images = list;
        this.APP_VERSION = str;
        this.iYoutuber = iYoutuber;
        this.w = i;
        this.h = i2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.img);
            view.findViewById(R.id.thumbnail_ytb).setVisibility(0);
            view.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.adabter.YoutuberAdabter.ViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (YoutuberAdabter.this.iYoutuber != null) {
                        YoutuberAdabter.this.iYoutuber.onClick(((YoutuberModel) YoutuberAdabter.this.images.get(ViewHolder.this.getAdapterPosition())).getLnk());
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
        Glide.with(viewHolder.imageView).asBitmap().load(Integer.valueOf(this.images.get(i).getImg())).override(this.w, this.h).signature(new ObjectKey(this.APP_VERSION)).diskCacheStrategy(DiskCacheStrategy.NONE).transform(new MultiTransformation(new RoundedCornersTransformation(8, 0, RoundedCornersTransformation.CornerType.ALL))).into(viewHolder.imageView);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<YoutuberModel> list = this.images;
        if (list == null) {
            return 0;
        }
        return list.size();
    }
}

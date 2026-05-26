package hazem.nurmontage.videoquran.adabter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import hazem.nurmontage.videoquran.R;
import hazem.nurmontage.videoquran.model.ExploreItem;
import hazem.nurmontage.videoquran.views.SquareImageViewSimple;
import hazem.nurmontage.videoquran.views.TextCustumFont;
import java.io.File;
import java.util.List;

/* loaded from: classes2.dex */
public class ExploreAdabters extends RecyclerView.Adapter<ExploreAdabters.MyViewHolder> {
    private final List<ExploreItem> exploreItems;
    private String folder_select;
    private IExplore iExplore;
    private final int size;

    public interface IExplore {
        void done();

        void folder(File file, String str, String str2);
    }

    public ExploreAdabters(List<ExploreItem> list, int i, IExplore iExplore, String str) {
        this.exploreItems = list;
        this.size = i;
        this.iExplore = iExplore;
        this.folder_select = str;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_explore, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        RequestBuilder<Drawable> load = Glide.with(myViewHolder.itemView).load(this.exploreItems.get(i).getFirstFilePath());
        int i2 = this.size;
        load.override(i2, i2).centerCrop().placeholder(R.drawable.image_24px).into(myViewHolder.imageView);
        myViewHolder.tv_name.setText(this.exploreItems.get(i).getName());
        myViewHolder.tv_size.setText(this.exploreItems.get(i).getSize());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<ExploreItem> list = this.exploreItems;
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private SquareImageViewSimple imageView;
        private TextCustumFont tv_name;
        private TextCustumFont tv_size;

        public MyViewHolder(View view) {
            super(view);
            this.imageView = (SquareImageViewSimple) view.findViewById(R.id.img);
            this.tv_name = (TextCustumFont) view.findViewById(R.id.tv_name);
            this.tv_size = (TextCustumFont) view.findViewById(R.id.tv_size);
            view.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.adabter.ExploreAdabters.MyViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (ExploreAdabters.this.iExplore != null) {
                        ExploreItem exploreItem = (ExploreItem) ExploreAdabters.this.exploreItems.get(MyViewHolder.this.getAdapterPosition());
                        ExploreAdabters.this.iExplore.folder(exploreItem.getFolder(), exploreItem.getName(), exploreItem.getPath());
                    }
                }
            });
        }
    }
}

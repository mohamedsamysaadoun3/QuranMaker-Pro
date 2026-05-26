package hazem.nurmontage.videoquran.adabter;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import hazem.nurmontage.videoquran.R;
import hazem.nurmontage.videoquran.model.GallerySelected;
import hazem.nurmontage.videoquran.views.SquareImageView;
import hazem.nurmontage.videoquran.views.TextCustumFont;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class GallerySelctedAdabters extends RecyclerView.Adapter<GallerySelctedAdabters.MyViewHolder> {
    private BitmapDrawable bitmapPlaceHolder;
    private final List<GallerySelected> gallerySelecteds = new ArrayList();
    private IGallerySelected iGallerySelected;
    private final int size;

    public interface IGallerySelected {
        void inselectPhoto(int i);

        void inselectVideo(int i);
    }

    public GallerySelctedAdabters(Resources resources, IGallerySelected iGallerySelected, int i) {
        this.size = i;
        this.iGallerySelected = iGallerySelected;
        Bitmap createBitmap = Bitmap.createBitmap(i, i, Bitmap.Config.RGB_565);
        createBitmap.eraseColor(ViewCompat.MEASURED_STATE_MASK);
        this.bitmapPlaceHolder = new BitmapDrawable(resources, createBitmap);
    }

    public List<GallerySelected> getGallerySelecteds() {
        return this.gallerySelecteds;
    }

    public int getSize() {
        return this.size;
    }

    public void addItemVideo(GallerySelected gallerySelected) {
        this.gallerySelecteds.add(gallerySelected);
        gallerySelected.getVideoItem().setGallerySelected(gallerySelected);
        notifyItemInserted(this.gallerySelecteds.size() - 1);
    }

    public void addItemPhoto(GallerySelected gallerySelected) {
        this.gallerySelecteds.add(gallerySelected);
        gallerySelected.getPhotoItem().setGallerySelected(gallerySelected);
        notifyItemInserted(this.gallerySelecteds.size() - 1);
    }

    public void deletedItem(GallerySelected gallerySelected) {
        int indexOf = this.gallerySelecteds.indexOf(gallerySelected);
        if (indexOf != -1) {
            this.gallerySelecteds.remove(indexOf);
            notifyItemRemoved(indexOf);
        }
    }

    public void deletedItem(int i) {
        this.gallerySelecteds.remove(i);
        notifyItemRemoved(i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_gallery_select, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        String path;
        if (this.gallerySelecteds.get(i).getVideoItem() != null) {
            path = this.gallerySelecteds.get(i).getVideoItem().getPath();
            myViewHolder.tv_time.setVisibility(0);
            myViewHolder.tv_time.setText(this.gallerySelecteds.get(i).getVideoItem().getTime());
        } else {
            path = this.gallerySelecteds.get(i).getPhotoItem().getPath();
            myViewHolder.tv_time.setVisibility(8);
        }
        RequestBuilder<Drawable> load = Glide.with(myViewHolder.itemView).load(path);
        int i2 = this.size;
        load.override(i2, i2).centerCrop().placeholder(this.bitmapPlaceHolder).into(myViewHolder.imageView);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<GallerySelected> list = this.gallerySelecteds;
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageButton btnDeleted;
        private SquareImageView imageView;
        private TextCustumFont tv_time;

        public MyViewHolder(View view) {
            super(view);
            this.imageView = (SquareImageView) view.findViewById(R.id.img);
            this.tv_time = (TextCustumFont) view.findViewById(R.id.tv_time);
            ImageButton imageButton = (ImageButton) view.findViewById(R.id.btn_deleted);
            this.btnDeleted = imageButton;
            imageButton.setVisibility(0);
            this.tv_time.setVisibility(0);
            this.btnDeleted.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.adabter.GallerySelctedAdabters.MyViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    GallerySelected gallerySelected = (GallerySelected) GallerySelctedAdabters.this.gallerySelecteds.get(MyViewHolder.this.getAdapterPosition());
                    GallerySelctedAdabters.this.deletedItem(MyViewHolder.this.getAdapterPosition());
                    if (gallerySelected.getVideoItem() != null) {
                        GallerySelctedAdabters.this.iGallerySelected.inselectVideo(gallerySelected.getIndex());
                    } else {
                        GallerySelctedAdabters.this.iGallerySelected.inselectPhoto(gallerySelected.getIndex());
                    }
                }
            });
        }
    }
}

package hazem.nurmontage.videoquran.adabter;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.ObjectKey;
import hazem.nurmontage.videoquran.GalleryPickerVideo;
import hazem.nurmontage.videoquran.R;
import hazem.nurmontage.videoquran.model.GallerySelected;
import hazem.nurmontage.videoquran.model.PhotoItem;
import hazem.nurmontage.videoquran.views.SquareImageView;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class GalleryPickerAdabters extends RecyclerView.Adapter<GalleryPickerAdabters.MyViewHolder> {
    private String APP_VERSION;
    private List<PhotoItem> all_paths;
    private BitmapDrawable bitmapPlaceHolder;
    private final List<GallerySelected> gallerySelectedList;
    private GalleryPickerVideo.IPicker iPicker;
    private List<PhotoItem> paths;
    private PhotoItem photoItem_selected;
    private final int size;

    public GalleryPickerAdabters(String str, Resources resources, List<GallerySelected> list, int i, GalleryPickerVideo.IPicker iPicker) {
        this.size = i;
        this.APP_VERSION = str;
        this.iPicker = iPicker;
        this.gallerySelectedList = list;
        Bitmap createBitmap = Bitmap.createBitmap(i, i, Bitmap.Config.RGB_565);
        createBitmap.eraseColor(ViewCompat.MEASURED_STATE_MASK);
        this.bitmapPlaceHolder = new BitmapDrawable(resources, createBitmap);
    }

    public void addItems(List<PhotoItem> list) {
        this.paths = list;
        if (this.iPicker != null) {
            if (list == null || list.isEmpty()) {
                this.iPicker.onEmptyList();
            }
        }
    }

    public void doneItems(List<PhotoItem> list) {
        this.paths = list;
        this.all_paths = new ArrayList(list);
    }

    public void updateAll() {
        List<PhotoItem> list;
        if (this.all_paths == null || (list = this.paths) == null) {
            return;
        }
        list.clear();
        this.paths = new ArrayList(this.all_paths);
        notifyDataSetChanged();
    }

    public void update(String str) {
        this.paths.clear();
        for (PhotoItem photoItem : this.all_paths) {
            if (photoItem.getFolder().equals(str)) {
                this.paths.add(photoItem);
            }
        }
        notifyDataSetChanged();
    }

    public void inselectItem(int i) {
        if (i >= this.paths.size()) {
            return;
        }
        PhotoItem photoItem = this.paths.get(i);
        photoItem.setSelect(false);
        notifyItemChanged(i);
        updateNumbers(photoItem.getNumber());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_gallery, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        PhotoItem photoItem = this.paths.get(i);
        myViewHolder.imageView.setNumber(photoItem.getNumber());
        myViewHolder.imageView.onSelect(photoItem.isSelect());
        RequestBuilder<Drawable> load = Glide.with(myViewHolder.itemView).load(photoItem.getPath());
        int i2 = this.size;
        load.override(i2, i2).centerCrop().signature(new ObjectKey(this.APP_VERSION)).diskCacheStrategy(DiskCacheStrategy.NONE).placeholder(this.bitmapPlaceHolder).into(myViewHolder.imageView);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<PhotoItem> list = this.paths;
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public void clear() {
        List<PhotoItem> list = this.paths;
        if (list != null) {
            list.clear();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private SquareImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            this.imageView = (SquareImageView) view.findViewById(R.id.img);
            view.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.adabter.GalleryPickerAdabters.MyViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (GalleryPickerAdabters.this.iPicker == null || MyViewHolder.this.getAdapterPosition() < 0) {
                        return;
                    }
                    if (GalleryPickerAdabters.this.gallerySelectedList == null) {
                        PhotoItem photoItem = (PhotoItem) GalleryPickerAdabters.this.paths.get(MyViewHolder.this.getAdapterPosition());
                        if (photoItem == GalleryPickerAdabters.this.photoItem_selected) {
                            return;
                        }
                        if (GalleryPickerAdabters.this.photoItem_selected != null) {
                            GalleryPickerAdabters.this.photoItem_selected.setSelect(false);
                            GalleryPickerAdabters.this.notifyItemChanged(GalleryPickerAdabters.this.photoItem_selected.getAdabter_pos());
                        }
                        photoItem.setSelect(true);
                        MyViewHolder.this.imageView.onSelect(true);
                        GalleryPickerAdabters.this.photoItem_selected = photoItem;
                        photoItem.setAdabter_pos(MyViewHolder.this.getAdapterPosition());
                        GalleryPickerAdabters.this.iPicker.onAdd(photoItem, MyViewHolder.this.getAdapterPosition());
                        return;
                    }
                    PhotoItem photoItem2 = (PhotoItem) GalleryPickerAdabters.this.paths.get(MyViewHolder.this.getAdapterPosition());
                    photoItem2.setSelect(!photoItem2.isSelect());
                    MyViewHolder.this.imageView.onSelect(photoItem2.isSelect());
                    if (photoItem2.isSelect()) {
                        MyViewHolder.this.imageView.setNumber(GalleryPickerAdabters.this.gallerySelectedList.size() + 1);
                        photoItem2.setNumber(MyViewHolder.this.imageView.getAnInt());
                        photoItem2.setAdabter_pos(MyViewHolder.this.getAdapterPosition());
                        GalleryPickerAdabters.this.iPicker.onAdd(photoItem2, MyViewHolder.this.getAdapterPosition());
                        return;
                    }
                    GalleryPickerAdabters.this.updateNumbers(MyViewHolder.this.imageView.getAnInt());
                    GalleryPickerAdabters.this.iPicker.onDelete(photoItem2.getGallerySelected());
                }
            });
        }
    }

    public void updateNumbers(int i) {
        if (this.gallerySelectedList == null) {
            return;
        }
        while (i < this.gallerySelectedList.size()) {
            PhotoItem photoItem = this.gallerySelectedList.get(i).getPhotoItem();
            if (photoItem != null) {
                photoItem.setNumber(photoItem.getNumber() - 1);
                notifyItemChanged(photoItem.getAdabter_pos());
            }
            i++;
        }
    }
}

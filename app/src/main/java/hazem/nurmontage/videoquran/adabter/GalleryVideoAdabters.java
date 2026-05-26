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
import hazem.nurmontage.videoquran.model.VideoItem;
import hazem.nurmontage.videoquran.views.SquareImageView;
import hazem.nurmontage.videoquran.views.TextCustumFont;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class GalleryVideoAdabters extends RecyclerView.Adapter<GalleryVideoAdabters.MyViewHolder> {
    private final String APP_VERSION;
    private List<VideoItem> all_videoItems;
    private BitmapDrawable bitmapPlaceHolder;
    private final List<GallerySelected> gallerySelectedList;
    private GalleryPickerVideo.IPicker iPicker;
    private final int size;
    private VideoItem videoItem_select;
    private List<VideoItem> videoItems;

    public GalleryVideoAdabters(String str, Resources resources, List<GallerySelected> list, int i, GalleryPickerVideo.IPicker iPicker) {
        this.size = i;
        this.APP_VERSION = str;
        this.iPicker = iPicker;
        this.gallerySelectedList = list;
        Bitmap createBitmap = Bitmap.createBitmap(i, i, Bitmap.Config.RGB_565);
        createBitmap.eraseColor(ViewCompat.MEASURED_STATE_MASK);
        this.bitmapPlaceHolder = new BitmapDrawable(resources, createBitmap);
    }

    public void doneItems(List<VideoItem> list) {
        this.videoItems = list;
        this.all_videoItems = new ArrayList(list);
    }

    public void updateAll() {
        List<VideoItem> list;
        if (this.all_videoItems == null || (list = this.videoItems) == null) {
            return;
        }
        list.clear();
        this.videoItems = new ArrayList(this.all_videoItems);
        notifyDataSetChanged();
    }

    public void update(String str) {
        this.videoItems.clear();
        for (VideoItem videoItem : this.all_videoItems) {
            if (videoItem.getFolderPath().equals(str)) {
                this.videoItems.add(videoItem);
            }
        }
        notifyDataSetChanged();
    }

    public void setFolder(String str) {
        notifyDataSetChanged();
    }

    public void addItems(List<VideoItem> list) {
        this.videoItems = list;
        if (this.iPicker != null) {
            if (list == null || list.isEmpty()) {
                this.iPicker.onEmptyList();
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_gallery, viewGroup, false));
    }

    public void inselectItem(int i) {
        if (i >= this.videoItems.size()) {
            return;
        }
        VideoItem videoItem = this.videoItems.get(i);
        videoItem.setSelect(false);
        notifyItemChanged(i);
        updateNumbers(videoItem.getNumber());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        VideoItem videoItem = this.videoItems.get(i);
        myViewHolder.imageView.setNumber(videoItem.getNumber());
        myViewHolder.imageView.onSelect(videoItem.isSelect());
        RequestBuilder<Drawable> load = Glide.with(myViewHolder.itemView).load(videoItem.getPath());
        int i2 = this.size;
        load.override(i2, i2).centerCrop().signature(new ObjectKey(this.APP_VERSION)).diskCacheStrategy(DiskCacheStrategy.NONE).placeholder(this.bitmapPlaceHolder).into(myViewHolder.imageView);
        myViewHolder.tv_time.setText(videoItem.getTime());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<VideoItem> list = this.videoItems;
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public void clear() {
        List<VideoItem> list = this.videoItems;
        if (list != null) {
            list.clear();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private SquareImageView imageView;
        private TextCustumFont tv_time;

        public MyViewHolder(View view) {
            super(view);
            this.imageView = (SquareImageView) view.findViewById(R.id.img);
            TextCustumFont textCustumFont = (TextCustumFont) view.findViewById(R.id.tv_time);
            this.tv_time = textCustumFont;
            textCustumFont.setVisibility(0);
            view.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.adabter.GalleryVideoAdabters.MyViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (GalleryVideoAdabters.this.iPicker == null || MyViewHolder.this.getAdapterPosition() < 0) {
                        return;
                    }
                    if (GalleryVideoAdabters.this.gallerySelectedList == null) {
                        VideoItem videoItem = (VideoItem) GalleryVideoAdabters.this.videoItems.get(MyViewHolder.this.getAdapterPosition());
                        if (videoItem == GalleryVideoAdabters.this.videoItem_select) {
                            return;
                        }
                        if (GalleryVideoAdabters.this.videoItem_select != null) {
                            GalleryVideoAdabters.this.videoItem_select.setSelect(false);
                            GalleryVideoAdabters.this.notifyItemChanged(GalleryVideoAdabters.this.videoItem_select.getAdabter_pos());
                        }
                        GalleryVideoAdabters.this.videoItem_select = videoItem;
                        videoItem.setSelect(true);
                        MyViewHolder.this.imageView.onSelect(true);
                        videoItem.setAdabter_pos(MyViewHolder.this.getAdapterPosition());
                        GalleryVideoAdabters.this.iPicker.onAdd(videoItem, MyViewHolder.this.getAdapterPosition());
                        return;
                    }
                    VideoItem videoItem2 = (VideoItem) GalleryVideoAdabters.this.videoItems.get(MyViewHolder.this.getAdapterPosition());
                    videoItem2.setSelect(!videoItem2.isSelect());
                    MyViewHolder.this.imageView.onSelect(videoItem2.isSelect());
                    if (videoItem2.isSelect()) {
                        MyViewHolder.this.imageView.setNumber(GalleryVideoAdabters.this.gallerySelectedList.size() + 1);
                        videoItem2.setNumber(MyViewHolder.this.imageView.getAnInt());
                        videoItem2.setAdabter_pos(MyViewHolder.this.getAdapterPosition());
                        GalleryVideoAdabters.this.iPicker.onAdd(videoItem2, MyViewHolder.this.getAdapterPosition());
                        return;
                    }
                    GalleryVideoAdabters.this.updateNumbers(MyViewHolder.this.imageView.getAnInt());
                    GalleryVideoAdabters.this.iPicker.onDelete(videoItem2.getGallerySelected());
                }
            });
        }
    }

    public void updateNumbers(int i) {
        while (i < this.gallerySelectedList.size()) {
            GallerySelected gallerySelected = this.gallerySelectedList.get(i);
            VideoItem videoItem = gallerySelected.getVideoItem();
            if (videoItem != null) {
                videoItem.setNumber(videoItem.getNumber() - 1);
                notifyItemChanged(videoItem.getAdabter_pos());
            }
            PhotoItem photoItem = gallerySelected.getPhotoItem();
            if (photoItem != null) {
                photoItem.setNumber(photoItem.getNumber() - 1);
            }
            i++;
        }
    }
}

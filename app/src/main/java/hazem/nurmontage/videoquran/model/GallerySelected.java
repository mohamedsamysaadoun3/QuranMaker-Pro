package hazem.nurmontage.videoquran.model;

/* loaded from: classes2.dex */
public class GallerySelected {
    private int index;
    private PhotoItem photoItem;
    private VideoItem videoItem;

    public GallerySelected(PhotoItem photoItem, int i) {
        this.photoItem = photoItem;
        this.index = i;
    }

    public GallerySelected(VideoItem videoItem, int i) {
        this.videoItem = videoItem;
        this.index = i;
    }

    public VideoItem getVideoItem() {
        return this.videoItem;
    }

    public PhotoItem getPhotoItem() {
        return this.photoItem;
    }

    public int getIndex() {
        return this.index;
    }
}

package hazem.nurmontage.videoquran.model;

/* loaded from: classes2.dex */
public class PhotoItem {
    private int adabter_pos;
    private String folder;
    private GallerySelected gallerySelected;
    private boolean isSelect;
    private int number;
    private String path;

    public void setAdabter_pos(int i) {
        this.adabter_pos = i;
    }

    public int getAdabter_pos() {
        return this.adabter_pos;
    }

    public void setNumber(int i) {
        this.number = i;
    }

    public int getNumber() {
        return this.number;
    }

    public boolean isSelect() {
        return this.isSelect;
    }

    public void setSelect(boolean z) {
        this.isSelect = z;
    }

    public void setGallerySelected(GallerySelected gallerySelected) {
        this.gallerySelected = gallerySelected;
    }

    public GallerySelected getGallerySelected() {
        return this.gallerySelected;
    }

    public PhotoItem(String str, String str2, boolean z) {
        this.path = str2;
        this.isSelect = z;
        this.folder = str;
    }

    public String getPath() {
        return this.path;
    }

    public String getFolder() {
        return this.folder;
    }

    public void setFolder(String str) {
        this.folder = str;
    }
}

package hazem.nurmontage.videoquran.model;

/* loaded from: classes2.dex */
public class VideoItem {
    private int adabter_pos;
    private String folderPath;
    private GallerySelected gallerySelected;
    private boolean isSelect;
    private int number;
    private String path;
    private String time;

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

    public VideoItem(String str, String str2, String str3, boolean z) {
        this.folderPath = str;
        this.path = str2;
        this.time = str3;
        this.isSelect = z;
    }

    public String getFolderPath() {
        return this.folderPath;
    }

    public String getPath() {
        return this.path;
    }

    public String getTime() {
        return this.time;
    }
}

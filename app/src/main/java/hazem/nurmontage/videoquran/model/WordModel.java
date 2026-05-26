package hazem.nurmontage.videoquran.model;

/* loaded from: classes2.dex */
public class WordModel {
    private boolean isSelected;
    private String w;

    public WordModel(String str, boolean z) {
        this.w = str;
        this.isSelected = z;
    }

    public WordModel(String str) {
        this.w = str;
    }

    public void setSelected(boolean z) {
        this.isSelected = z;
    }

    public void setW(String str) {
        this.w = str;
    }

    public String getW() {
        return this.w;
    }

    public boolean isSelected() {
        return this.isSelected;
    }
}

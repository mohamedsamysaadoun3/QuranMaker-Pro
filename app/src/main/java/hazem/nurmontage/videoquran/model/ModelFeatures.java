package hazem.nurmontage.videoquran.model;

/* loaded from: classes2.dex */
public class ModelFeatures {
    private boolean isForFree;
    private String name;

    public ModelFeatures(String str, boolean z) {
        this.name = str;
        this.isForFree = z;
    }

    public ModelFeatures(String str) {
        this.name = str;
    }

    public boolean isForFree() {
        return this.isForFree;
    }

    public String getName() {
        return this.name;
    }
}

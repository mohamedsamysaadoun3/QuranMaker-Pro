package hazem.nurmontage.videoquran.model;

import java.io.File;

/* loaded from: classes2.dex */
public class ExploreItem {
    private String firstFilePath;
    private File folder;
    private String name;
    private String path;
    private String size;

    public ExploreItem(File file, String str, String str2, String str3, String str4) {
        this.path = str;
        this.size = str2;
        this.name = str3;
        this.firstFilePath = str4;
        this.folder = file;
    }

    public File getFolder() {
        return this.folder;
    }

    public String getFirstFilePath() {
        return this.firstFilePath;
    }

    public String getPath() {
        return this.path;
    }

    public String getSize() {
        return this.size;
    }

    public String getName() {
        return this.name;
    }
}

package hazem.nurmontage.videoquran.model;

import hazem.nurmontage.videoquran.constant.ResizeType;

/* loaded from: classes2.dex */
public class ItemDimension {
    private final int h;
    private String id;
    private int image;
    private String name;
    private ResizeType resizeType;
    private final int w;

    public ItemDimension(String str, int i, ResizeType resizeType, int i2, int i3, String str2) {
        this.name = str;
        this.image = i;
        this.resizeType = resizeType;
        this.w = i2;
        this.h = i3;
        this.id = str2;
    }

    public String getId() {
        return this.id;
    }

    public int getW() {
        return this.w;
    }

    public int getH() {
        return this.h;
    }

    public int getImage() {
        return this.image;
    }

    public String getName() {
        return this.name;
    }

    public ResizeType getResizeType() {
        return this.resizeType;
    }
}

package hazem.nurmontage.videoquran.model;

import hazem.nurmontage.videoquran.constant.IpadType;

/* loaded from: classes2.dex */
public class IpadItem {
    private int img;
    private IpadType ipadType;

    public IpadItem(int i, IpadType ipadType) {
        this.ipadType = ipadType;
        this.img = i;
    }

    public IpadType getIpadType() {
        return this.ipadType;
    }

    public int getImg() {
        return this.img;
    }
}

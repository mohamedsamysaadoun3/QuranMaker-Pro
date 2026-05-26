package hazem.nurmontage.videoquran.model;

import java.io.Serializable;

/* loaded from: classes2.dex */
public class EntityProgressTemplate implements Serializable {
    private float left;
    private float top;

    public EntityProgressTemplate(float f, float f2) {
        this.left = f;
        this.top = f2;
    }

    public float getTop() {
        return this.top;
    }

    public float getLeft() {
        return this.left;
    }

    public void setLeft(float f) {
        this.left = f;
    }

    public void setTop(float f) {
        this.top = f;
    }
}

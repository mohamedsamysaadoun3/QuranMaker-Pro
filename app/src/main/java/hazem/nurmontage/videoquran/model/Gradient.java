package hazem.nurmontage.videoquran.model;

import java.io.Serializable;

/* loaded from: classes2.dex */
public class Gradient implements Serializable {
    private int angle = 81;
    private final int color;
    private final int second;
    private final int three;

    public Gradient(int i, int i2, int i3) {
        this.color = i;
        this.second = i2;
        this.three = i3;
    }

    public void setAngle(int i) {
        this.angle = i;
    }

    public int getAngle() {
        return this.angle;
    }

    public int getColor() {
        return this.color;
    }

    public int getSecond() {
        return this.second;
    }

    public int getThree() {
        return this.three;
    }
}

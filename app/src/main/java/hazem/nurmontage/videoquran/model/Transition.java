package hazem.nurmontage.videoquran.model;

import java.io.Serializable;

/* loaded from: classes2.dex */
public class Transition implements Serializable {
    private float duration_both;
    private float duration_in;
    private float duration_out;
    private float fromW;
    private boolean isBoth;
    private boolean isIn;
    private boolean isOut;
    private float offset_frame_in;
    private float offset_frame_out;
    private String type_both;
    private String type_in;
    private String type_out;

    public void setFromW(float f) {
        this.fromW = f;
    }

    public float getFromW() {
        return this.fromW;
    }

    public Transition() {
        this.duration_in = 1.5f;
        this.duration_out = 1.5f;
        this.duration_both = 0.1f;
        this.type_out = "none";
        this.type_both = "none";
        this.type_in = "none";
    }

    public Transition(String str, String str2, String str3, float f, float f2, float f3, boolean z, boolean z2, boolean z3) {
        this.type_in = str;
        this.type_out = str2;
        this.type_both = str3;
        this.duration_in = f;
        this.duration_out = f2;
        this.duration_both = f3;
        this.isIn = z;
        this.isOut = z2;
        this.isBoth = z3;
    }

    public void setOffset_frame_in(float f) {
        this.offset_frame_in = f;
    }

    public float getOffset_frame_out() {
        return this.offset_frame_out;
    }

    public float getOffset_frame_in() {
        return this.offset_frame_in;
    }

    public void setOffset_frame_out(float f) {
        this.offset_frame_out = f;
    }

    public boolean isIn() {
        return this.isIn;
    }

    public boolean isOut() {
        return this.isOut;
    }

    public void setIn(boolean z) {
        this.isIn = z;
    }

    public void setOut(boolean z) {
        this.isOut = z;
    }

    public void setBoth(boolean z) {
        this.isBoth = z;
    }

    public boolean isBoth() {
        return this.isBoth;
    }

    public float getDuration_both() {
        return this.duration_both;
    }

    public float getDuration_in() {
        return this.duration_in;
    }

    public float getDuration_out() {
        return this.duration_out;
    }

    public void setDuration_both(float f) {
        this.duration_both = f;
    }

    public void setDuration_in(float f) {
        this.duration_in = f;
    }

    public void setDuration_out(float f) {
        this.duration_out = f;
    }

    public void setType_in(String str) {
        this.type_in = str;
    }

    public void setType_both(String str) {
        this.type_both = str;
    }

    public void setType_out(String str) {
        this.type_out = str;
    }

    public String getType_both() {
        return this.type_both;
    }

    public String getType_in() {
        return this.type_in;
    }

    public String getType_out() {
        return this.type_out;
    }

    public Transition duplicate() {
        Transition transition = new Transition(getType_in(), getType_out(), getType_both(), getDuration_in(), getDuration_out(), getDuration_both(), isIn(), isOut(), isBoth());
        transition.setOffset_frame_in(getOffset_frame_in());
        transition.setOffset_frame_out(getOffset_frame_out());
        transition.setFromW(this.fromW);
        return transition;
    }
}

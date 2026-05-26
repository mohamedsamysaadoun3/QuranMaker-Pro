package hazem.nurmontage.videoquran.model;

/* loaded from: classes2.dex */
public class BgItem {
    private int id;
    private String name_drawable;
    private float x;
    private float y;

    public BgItem(int i, float f, float f2, String str) {
        this.id = i;
        this.x = f;
        this.y = f2;
        this.name_drawable = str;
    }

    public String getName_drawable() {
        return this.name_drawable;
    }

    public void setName_drawable(String str) {
        this.name_drawable = str;
    }

    public float getY() {
        return this.y;
    }

    public float getX() {
        return this.x;
    }

    public int getId() {
        return this.id;
    }
}

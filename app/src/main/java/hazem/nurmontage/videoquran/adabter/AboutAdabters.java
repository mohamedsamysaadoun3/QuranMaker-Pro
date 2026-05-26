package hazem.nurmontage.videoquran.adabter;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.ObjectKey;
import hazem.nurmontage.videoquran.R;
import java.util.List;

/* loaded from: classes2.dex */
public class AboutAdabters extends RecyclerView.Adapter<AboutAdabters.ViewHolder> {
    private final String APP_VERSION;
    private final Context mContext;
    private final int mDimensionH;
    private final int mDimensionW;
    private final List<ModelAbout> mModelAboutList;

    public AboutAdabters(Context context, String str, List<ModelAbout> list, int i, int i2) {
        this.mContext = context;
        this.mModelAboutList = list;
        this.mDimensionW = i;
        this.mDimensionH = i2;
        this.APP_VERSION = str;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_billing, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        ModelAbout modelAbout = this.mModelAboutList.get(i);
        viewHolder.textView.setGravity(modelAbout.geGravity());
        if (modelAbout.getSizeText() == 19) {
            viewHolder.textView.getPaint().setFakeBoldText(true);
        } else {
            viewHolder.textView.getPaint().setFakeBoldText(false);
        }
        viewHolder.textView.setTextSize(2, modelAbout.getSizeText());
        viewHolder.textView.setText(HtmlCompat.fromHtml(modelAbout.getText(), 0));
        if (modelAbout.getImage_1() != -1) {
            viewHolder.imageView_1.setVisibility(0);
            Glide.with(this.mContext).asBitmap().load(Integer.valueOf(modelAbout.getImage_1())).override(this.mDimensionW, this.mDimensionH).centerInside().signature(new ObjectKey(this.APP_VERSION)).diskCacheStrategy(DiskCacheStrategy.NONE).into(viewHolder.imageView_1);
        } else {
            viewHolder.imageView_1.setVisibility(8);
            Glide.with(this.mContext).clear(viewHolder.imageView_1);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<ModelAbout> list = this.mModelAboutList;
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView_1;
        public TextView textView;

        public ViewHolder(View view) {
            super(view);
            this.textView = (TextView) view.findViewById(R.id.tv);
            this.imageView_1 = (ImageView) view.findViewById(R.id.img);
        }
    }

    public static class ModelAbout {
        private int image_1;
        private int image_2;
        private int sizeText;
        private Pair<String, Integer> text;

        public ModelAbout(Pair<String, Integer> pair, int i) {
            this.image_2 = -1;
            this.sizeText = 16;
            this.text = pair;
            this.image_1 = i;
        }

        public ModelAbout(int i, Pair<String, Integer> pair, int i2) {
            this.image_2 = -1;
            this.text = pair;
            this.image_1 = i2;
            this.sizeText = i;
        }

        public ModelAbout(int i, int i2, Pair<String, Integer> pair) {
            this.sizeText = 16;
            this.text = pair;
            this.image_1 = i;
            this.image_2 = i2;
        }

        public ModelAbout(Pair<String, Integer> pair) {
            this.image_1 = -1;
            this.image_2 = -1;
            this.sizeText = 16;
            this.text = pair;
        }

        public ModelAbout(Pair<String, Integer> pair, int i, int i2) {
            this.image_2 = -1;
            this.text = pair;
            this.sizeText = i2;
            this.image_1 = i;
        }

        public ModelAbout(int i, Pair<String, Integer> pair) {
            this.image_1 = -1;
            this.image_2 = -1;
            this.text = pair;
            this.sizeText = i;
        }

        public int getImage_1() {
            return this.image_1;
        }

        public int getImage_2() {
            return this.image_2;
        }

        public int getSizeText() {
            return this.sizeText;
        }

        public String getText() {
            return (String) this.text.first;
        }

        public int geGravity() {
            return ((Integer) this.text.second).intValue();
        }
    }
}

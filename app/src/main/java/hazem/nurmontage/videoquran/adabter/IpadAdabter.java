package hazem.nurmontage.videoquran.adabter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import hazem.nurmontage.videoquran.R;
import hazem.nurmontage.videoquran.fragment.EditIpadFragment;
import hazem.nurmontage.videoquran.model.IpadItem;
import java.util.List;

/* loaded from: classes2.dex */
public class IpadAdabter extends RecyclerView.Adapter<IpadAdabter.ViewHolder> {
    public EditIpadFragment.IIpadEditCallback ipadEditCallback;
    private List<IpadItem> ipadItems;
    private int ipad_selected;
    private boolean isGlass;
    private boolean isSubscribe;
    private int pos_select;

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isManyOption(int i) {
        return i == 0 || i == 1 || i == 7 || i == 8 || i == 9;
    }

    public int getPos_select() {
        return this.pos_select;
    }

    public IpadAdabter(boolean z, int i, int i2, EditIpadFragment.IIpadEditCallback iIpadEditCallback, List<IpadItem> list, boolean z2) {
        this.ipadItems = list;
        this.pos_select = i;
        this.ipadEditCallback = iIpadEditCallback;
        this.ipad_selected = i2;
        this.isGlass = z2;
        this.isSubscribe = z;
    }

    private void updateDote(View view, View view2) {
        if (this.isGlass) {
            view.setAlpha(1.0f);
            view2.setAlpha(0.5f);
        } else {
            view2.setAlpha(1.0f);
            view.setAlpha(0.5f);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public ImageView iv_pro;
        public LinearLayout lyt_option;
        public View vDot1;
        public View vDot2;

        public ViewHolder(View view) {
            super(view);
            this.iv_pro = (ImageView) view.findViewById(R.id.iv_pro);
            this.lyt_option = (LinearLayout) view.findViewById(R.id.view_option);
            this.imageView = (ImageView) view.findViewById(R.id.img);
            this.vDot1 = view.findViewById(R.id.dot1);
            this.vDot2 = view.findViewById(R.id.dot2);
            view.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.adabter.IpadAdabter.ViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (IpadAdabter.this.pos_select == ViewHolder.this.getAdapterPosition()) {
                        if (!IpadAdabter.this.isManyOption(ViewHolder.this.getAdapterPosition())) {
                            return;
                        }
                        IpadAdabter.this.isGlass = !IpadAdabter.this.isGlass;
                        IpadAdabter.this.ipadEditCallback.onGlassType(IpadAdabter.this.isGlass);
                    }
                    if (!IpadAdabter.this.isSubscribe && ViewHolder.this.getAdapterPosition() > 1) {
                        IpadAdabter.this.ipadEditCallback.onDialogPremium();
                        return;
                    }
                    if (IpadAdabter.this.ipadEditCallback != null) {
                        IpadItem ipadItem = (IpadItem) IpadAdabter.this.ipadItems.get(ViewHolder.this.getAdapterPosition());
                        IpadAdabter.this.notifyItemChanged(IpadAdabter.this.pos_select);
                        IpadAdabter.this.pos_select = ViewHolder.this.getAdapterPosition();
                        IpadAdabter.this.ipad_selected = ipadItem.getIpadType().ordinal();
                        IpadAdabter.this.notifyItemChanged(IpadAdabter.this.pos_select);
                        IpadAdabter.this.ipadEditCallback.onChangeType(IpadAdabter.this.ipad_selected);
                    }
                }
            });
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_ipad, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        IpadItem ipadItem = this.ipadItems.get(i);
        Glide.with(viewHolder.imageView).asBitmap().load(Integer.valueOf(ipadItem.getImg())).diskCacheStrategy(DiskCacheStrategy.NONE).into(viewHolder.imageView);
        if (isManyOption(i)) {
            viewHolder.lyt_option.setVisibility(0);
            updateDote(viewHolder.vDot1, viewHolder.vDot2);
        } else {
            viewHolder.lyt_option.setVisibility(8);
        }
        if (ipadItem.getIpadType().ordinal() == this.ipad_selected) {
            viewHolder.itemView.setAlpha(1.0f);
            viewHolder.imageView.setBackgroundResource(R.drawable.ipad_selected);
            this.pos_select = i;
        } else {
            viewHolder.itemView.setAlpha(0.4f);
            viewHolder.imageView.setBackgroundResource(R.drawable.watch_btn_outline);
        }
        if (this.isSubscribe) {
            return;
        }
        if (i > 1) {
            viewHolder.iv_pro.setVisibility(0);
        } else {
            viewHolder.iv_pro.setVisibility(8);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<IpadItem> list = this.ipadItems;
        if (list == null) {
            return 0;
        }
        return list.size();
    }
}

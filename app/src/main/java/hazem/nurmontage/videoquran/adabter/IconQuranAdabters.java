package hazem.nurmontage.videoquran.adabter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import hazem.nurmontage.videoquran.R;
import hazem.nurmontage.videoquran.Utils.DrawableHelper;
import java.util.List;

/* loaded from: classes2.dex */
public class IconQuranAdabters extends RecyclerView.Adapter<IconQuranAdabters.ViewHolder> {
    private IIconQuranCallback iconQuranCallback;
    private List<String> list;
    private int select;

    public interface IIconQuranCallback {
        void onIcon(String str);
    }

    public IconQuranAdabters(IIconQuranCallback iIconQuranCallback, List<String> list, int i) {
        this.iconQuranCallback = iIconQuranCallback;
        this.list = list;
        this.select = i;
        if (i >= list.size()) {
            this.select = 0;
        }
    }

    public int getSelect() {
        return this.select;
    }

    public boolean isHaveSelect() {
        return this.select != -1;
    }

    public void unselect() {
        int i = this.select;
        if (i == -1) {
            return;
        }
        this.select = -1;
        notifyItemChanged(i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_anim, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.animationItem.setImageResource(DrawableHelper.getIDDrawableIconByName(this.list.get(i)));
        if (i == this.select) {
            viewHolder.animationItem.setBackgroundResource(R.drawable.circle_item_menu_select);
        } else {
            viewHolder.animationItem.setBackgroundResource(R.drawable.circle_effect);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<String> list = this.list;
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView animationItem;
        public ImageView disableView;

        public ViewHolder(View view) {
            super(view);
            this.animationItem = (ImageView) view.findViewById(R.id.anim_item);
            this.disableView = (ImageView) view.findViewById(R.id.iv_disable);
            view.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.adabter.IconQuranAdabters.ViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (IconQuranAdabters.this.iconQuranCallback == null || IconQuranAdabters.this.select == ViewHolder.this.getAdapterPosition()) {
                        return;
                    }
                    int i = IconQuranAdabters.this.select;
                    IconQuranAdabters.this.select = ViewHolder.this.getAdapterPosition();
                    IconQuranAdabters.this.notifyItemChanged(i);
                    IconQuranAdabters.this.notifyItemChanged(IconQuranAdabters.this.select);
                    IconQuranAdabters.this.iconQuranCallback.onIcon((String) IconQuranAdabters.this.list.get(ViewHolder.this.getAdapterPosition()));
                }
            });
        }
    }
}

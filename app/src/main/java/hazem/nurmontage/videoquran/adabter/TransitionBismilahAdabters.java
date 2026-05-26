package hazem.nurmontage.videoquran.adabter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import hazem.nurmontage.videoquran.R;
import hazem.nurmontage.videoquran.entity_timeline.EntityBismilahTimeline;
import hazem.nurmontage.videoquran.fragment.EffectBismilahFragment;
import java.util.List;

/* loaded from: classes2.dex */
public class TransitionBismilahAdabters extends RecyclerView.Adapter<TransitionBismilahAdabters.ViewHolder> {
    private EntityBismilahTimeline entityQuranTimeline;
    private EffectBismilahFragment.ITransition iTransition;
    private List<TransitionItem> list;
    private int max;
    private int select;
    private String type = "in";

    public TransitionBismilahAdabters(EffectBismilahFragment.ITransition iTransition, List<TransitionItem> list, int i, EntityBismilahTimeline entityBismilahTimeline) {
        this.iTransition = iTransition;
        this.select = i;
        this.list = list;
        this.max = list.size();
        this.entityQuranTimeline = entityBismilahTimeline;
    }

    public int getSelect() {
        return this.select;
    }

    public void update(List<TransitionItem> list, String str, int i) {
        this.select = i;
        this.list = list;
        this.type = str;
        this.max = list.size();
        notifyDataSetChanged();
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
        viewHolder.animationItem.setRotation(this.list.get(i).getAngle());
        viewHolder.animationItem.setImageResource(this.list.get(i).getId_ressource());
        if (i == this.select) {
            viewHolder.animationItem.setBackgroundResource(R.drawable.circle_item_menu_select);
        } else {
            viewHolder.animationItem.setBackgroundResource(R.drawable.circle_effect);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.max;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView animationItem;
        public ImageView disableView;

        public ViewHolder(View view) {
            super(view);
            this.animationItem = (ImageView) view.findViewById(R.id.anim_item);
            this.disableView = (ImageView) view.findViewById(R.id.iv_disable);
            view.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.adabter.TransitionBismilahAdabters.ViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (TransitionBismilahAdabters.this.iTransition == null || TransitionBismilahAdabters.this.select == ViewHolder.this.getAdapterPosition()) {
                        return;
                    }
                    int i = TransitionBismilahAdabters.this.select;
                    TransitionBismilahAdabters.this.select = ViewHolder.this.getAdapterPosition();
                    TransitionBismilahAdabters.this.notifyItemChanged(i);
                    TransitionBismilahAdabters.this.notifyItemChanged(TransitionBismilahAdabters.this.select);
                    if (TransitionBismilahAdabters.this.type != null) {
                        if (TransitionBismilahAdabters.this.type.equals("in")) {
                            TransitionBismilahAdabters.this.iTransition.in(((TransitionItem) TransitionBismilahAdabters.this.list.get(ViewHolder.this.getAdapterPosition())).getType(), TransitionBismilahAdabters.this.entityQuranTimeline);
                        } else if (TransitionBismilahAdabters.this.type.equals("out")) {
                            TransitionBismilahAdabters.this.iTransition.out(((TransitionItem) TransitionBismilahAdabters.this.list.get(ViewHolder.this.getAdapterPosition())).getType(), TransitionBismilahAdabters.this.entityQuranTimeline);
                        }
                    }
                }
            });
        }
    }

    public static class TransitionItem {
        private int angle;
        private int id_ressource;
        private String type;

        public TransitionItem(String str, int i, int i2) {
            this.type = str;
            this.id_ressource = i;
            this.angle = i2;
        }

        public int getAngle() {
            return this.angle;
        }

        public int getId_ressource() {
            return this.id_ressource;
        }

        public String getType() {
            return this.type;
        }
    }
}

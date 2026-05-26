package hazem.nurmontage.videoquran.adabter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import hazem.nurmontage.videoquran.R;
import hazem.nurmontage.videoquran.entity_timeline.EntityQuranTimeline;
import hazem.nurmontage.videoquran.fragment.EffectAyaFragment;
import java.util.List;

/* loaded from: classes2.dex */
public class TransitionEntityAdabters extends RecyclerView.Adapter<TransitionEntityAdabters.ViewHolder> {
    private EntityQuranTimeline entityQuranTimeline;
    private EffectAyaFragment.ITransition iTransition;
    private boolean isSubscribe;
    private List<TransitionItem> list;
    private int max;
    private int select;
    private String type = "in";

    public TransitionEntityAdabters(boolean z, EffectAyaFragment.ITransition iTransition, List<TransitionItem> list, int i, EntityQuranTimeline entityQuranTimeline) {
        this.iTransition = iTransition;
        this.select = i;
        this.list = list;
        this.max = list.size();
        this.isSubscribe = z;
        this.entityQuranTimeline = entityQuranTimeline;
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
        if (!this.isSubscribe) {
            if (i > 10) {
                viewHolder.disableView.setVisibility(0);
            } else {
                viewHolder.disableView.setVisibility(8);
            }
        }
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
            view.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.adabter.TransitionEntityAdabters.ViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (TransitionEntityAdabters.this.iTransition != null) {
                        if (!TransitionEntityAdabters.this.isSubscribe && ViewHolder.this.getAdapterPosition() > 10) {
                            TransitionEntityAdabters.this.iTransition.toSubscribe();
                            return;
                        }
                        if (TransitionEntityAdabters.this.select == ViewHolder.this.getAdapterPosition()) {
                            return;
                        }
                        int i = TransitionEntityAdabters.this.select;
                        TransitionEntityAdabters.this.select = ViewHolder.this.getAdapterPosition();
                        TransitionEntityAdabters.this.notifyItemChanged(i);
                        TransitionEntityAdabters.this.notifyItemChanged(TransitionEntityAdabters.this.select);
                        if (TransitionEntityAdabters.this.type != null) {
                            if (TransitionEntityAdabters.this.type.equals("in")) {
                                TransitionEntityAdabters.this.iTransition.in(((TransitionItem) TransitionEntityAdabters.this.list.get(ViewHolder.this.getAdapterPosition())).getType(), TransitionEntityAdabters.this.entityQuranTimeline);
                            } else if (TransitionEntityAdabters.this.type.equals("out")) {
                                TransitionEntityAdabters.this.iTransition.out(((TransitionItem) TransitionEntityAdabters.this.list.get(ViewHolder.this.getAdapterPosition())).getType(), TransitionEntityAdabters.this.entityQuranTimeline);
                            }
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

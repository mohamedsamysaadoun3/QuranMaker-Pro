package hazem.nurmontage.videoquran.adabter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import hazem.nurmontage.videoquran.R;
import hazem.nurmontage.videoquran.model.ModelFeatures;
import hazem.nurmontage.videoquran.views.TextCustumFont;
import java.util.List;

/* loaded from: classes2.dex */
public class FeaturesAdabter extends RecyclerView.Adapter<FeaturesAdabter.ViewHolder> {
    private boolean isSubscibe;
    private List<ModelFeatures> list;

    public void setSubscribe(boolean z) {
        this.isSubscibe = z;
        notifyDataSetChanged();
    }

    public FeaturesAdabter(List<ModelFeatures> list) {
        this.list = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextCustumFont text;

        public ViewHolder(View view) {
            super(view);
            this.text = (TextCustumFont) view.findViewById(R.id.tv_feature);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_feature, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.text.setText(this.list.get(i).getName());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<ModelFeatures> list = this.list;
        if (list == null) {
            return 0;
        }
        return list.size();
    }
}

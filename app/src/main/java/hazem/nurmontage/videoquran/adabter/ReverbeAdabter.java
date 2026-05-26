package hazem.nurmontage.videoquran.adabter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import hazem.nurmontage.videoquran.R;
import hazem.nurmontage.videoquran.fragment.audio_effect.Reverbe;
import hazem.nurmontage.videoquran.views.TextCustumFont;
import java.util.List;

/* loaded from: classes2.dex */
public class ReverbeAdabter extends RecyclerView.Adapter<ReverbeAdabter.ViewHolder> {
    private final IReverbPresetCallback iReverbCallback;
    private List<Reverbe> list;
    private int select;

    public interface IReverbPresetCallback {
        void cmd(String str, int i);

        void pause();
    }

    public ReverbeAdabter(List<Reverbe> list, IReverbPresetCallback iReverbPresetCallback, int i) {
        this.list = list;
        this.select = i;
        this.iReverbCallback = iReverbPresetCallback;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_btn_play;
        private TextCustumFont text;

        public ViewHolder(View view) {
            super(view);
            this.text = (TextCustumFont) view.findViewById(R.id.word_aya);
            this.iv_btn_play = (ImageView) view.findViewById(R.id.iv_btn_play);
            view.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.adabter.ReverbeAdabter.ViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (ReverbeAdabter.this.iReverbCallback != null) {
                        ReverbeAdabter.this.iReverbCallback.pause();
                        if (ReverbeAdabter.this.select == ViewHolder.this.getAdapterPosition()) {
                            int i = ReverbeAdabter.this.select;
                            ReverbeAdabter.this.select = -1;
                            ReverbeAdabter.this.notifyItemChanged(i);
                            ReverbeAdabter.this.notifyItemChanged(ViewHolder.this.getAdapterPosition());
                            return;
                        }
                        int i2 = ReverbeAdabter.this.select;
                        ReverbeAdabter.this.select = ViewHolder.this.getAdapterPosition();
                        ReverbeAdabter.this.notifyItemChanged(i2);
                        ReverbeAdabter.this.notifyItemChanged(ReverbeAdabter.this.select);
                        ReverbeAdabter.this.iReverbCallback.cmd(((Reverbe) ReverbeAdabter.this.list.get(ViewHolder.this.getAdapterPosition())).getCmd_ffmpeg(), ViewHolder.this.getAdapterPosition());
                    }
                }
            });
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_reverbe, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.text.setText(this.list.get(i).getName());
        if (this.select == i) {
            viewHolder.itemView.setBackgroundResource(R.drawable.item_reverb_select);
            viewHolder.iv_btn_play.setImageResource(R.drawable.pause_24px);
        } else {
            viewHolder.itemView.setBackgroundResource(R.drawable.round_btn_in_dark);
            viewHolder.iv_btn_play.setImageResource(R.drawable.play_arrow_24px);
        }
    }

    public List<Reverbe> getList() {
        return this.list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<Reverbe> list = this.list;
        if (list == null) {
            return 0;
        }
        return list.size();
    }
}

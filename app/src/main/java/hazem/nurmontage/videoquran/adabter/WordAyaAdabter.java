package hazem.nurmontage.videoquran.adabter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import hazem.nurmontage.videoquran.R;
import hazem.nurmontage.videoquran.model.WordModel;
import hazem.nurmontage.videoquran.views.TextCustumFont;
import java.util.List;

/* loaded from: classes2.dex */
public class WordAyaAdabter extends RecyclerView.Adapter<WordAyaAdabter.ViewHolder> {
    private IWordAya iWordAya;
    private List<WordModel> list;

    public interface IWordAya {
        void onClick();
    }

    public WordAyaAdabter(List<WordModel> list) {
        this.list = list;
    }

    public WordAyaAdabter(IWordAya iWordAya, List<WordModel> list) {
        this.list = list;
        this.iWordAya = iWordAya;
    }

    public void setList(List<WordModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextCustumFont text;

        public ViewHolder(View view) {
            super(view);
            TextCustumFont textCustumFont = (TextCustumFont) view.findViewById(R.id.word_aya);
            this.text = textCustumFont;
            textCustumFont.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.adabter.WordAyaAdabter.ViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    WordModel wordModel = WordAyaAdabter.this.list.get(ViewHolder.this.getAdapterPosition());
                    wordModel.setSelected(!wordModel.isSelected());
                    WordAyaAdabter.this.notifyItemChanged(ViewHolder.this.getAdapterPosition());
                    if (WordAyaAdabter.this.iWordAya != null) {
                        WordAyaAdabter.this.iWordAya.onClick();
                    }
                }
            });
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_word_aya, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        WordModel wordModel = this.list.get(i);
        viewHolder.text.setText(wordModel.getW());
        if (wordModel.isSelected()) {
            viewHolder.text.setBackgroundResource(R.drawable.round_btn_quran_select);
            viewHolder.text.setTextColor(-12434878);
        } else {
            viewHolder.text.setBackgroundResource(R.drawable.round_btn_in_dark);
            viewHolder.text.setTextColor(-1);
        }
    }

    public List<WordModel> getList() {
        return this.list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<WordModel> list = this.list;
        if (list == null) {
            return 0;
        }
        return list.size();
    }
}

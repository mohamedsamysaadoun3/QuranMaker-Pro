package hazem.nurmontage.videoquran.adabter;

import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import hazem.nurmontage.videoquran.R;
import hazem.nurmontage.videoquran.adabter.SearchQuranAdabters;
import hazem.nurmontage.videoquran.model.ItemQuranSearch;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class SearchQuranAdabters extends RecyclerView.Adapter<SearchQuranAdabters.ViewHolder> {
    private final ISearchQuranCallback callback;
    private final List<ItemQuranSearch> searchList = new ArrayList();
    private int minSelected = -1;
    private int maxSelected = -1;

    public interface ISearchQuranCallback {
        void onClick(int i, int i2, ItemQuranSearch itemQuranSearch);
    }

    public SearchQuranAdabters(ISearchQuranCallback iSearchQuranCallback) {
        this.callback = iSearchQuranCallback;
    }

    public int getSize() {
        return this.searchList.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_search_quran, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        ItemQuranSearch itemQuranSearch = this.searchList.get(i);
        if (itemQuranSearch.getAya() != null) {
            viewHolder.name.setText(itemQuranSearch.getSurahName() + " (" + itemQuranSearch.getTo() + ")");
            if (itemQuranSearch.getStartSpannable() != -1) {
                SpannableString spannableString = new SpannableString(itemQuranSearch.getAya());
                spannableString.setSpan(new ForegroundColorSpan(-10929), itemQuranSearch.getStartSpannable(), itemQuranSearch.getEndSpannble(), 33);
                viewHolder.aya.setText(spannableString);
            } else {
                viewHolder.aya.setText(itemQuranSearch.getAya());
            }
        } else {
            viewHolder.name.setText(String.valueOf(itemQuranSearch.getSurahIndex()));
        }
        int i2 = this.minSelected;
        viewHolder.itemView.setBackgroundColor(i2 != -1 && i >= i2 && i <= this.maxSelected ? -14540254 : 0);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.searchList.size();
    }

    public void setList(List<ItemQuranSearch> list) {
        this.searchList.clear();
        this.searchList.addAll(list);
        notifyDataSetChanged();
    }

    public void add(ItemQuranSearch itemQuranSearch) {
        this.searchList.add(itemQuranSearch);
        notifyItemInserted(this.searchList.size() - 1);
    }

    public void clear() {
        int size = this.searchList.size();
        if (size == 0) {
            return;
        }
        this.searchList.clear();
        notifyItemRangeRemoved(0, size);
    }

    public int getMinSelected() {
        return this.minSelected;
    }

    public int getMaxSelected() {
        return this.maxSelected;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView aya;
        TextView name;

        public ViewHolder(View view) {
            super(view);
            this.name = (TextView) view.findViewById(R.id.tv_surah_name_and_number);
            this.aya = (TextView) view.findViewById(R.id.tv_surah);
            view.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.adabter.SearchQuranAdabters$ViewHolder$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    SearchQuranAdabters.ViewHolder.this.m1026xcfa7fc81(view2);
                }
            });
        }

        /* renamed from: lambda$new$0$hazem-nurmontage-videoquran-adabter-SearchQuranAdabters$ViewHolder, reason: not valid java name */
        /* synthetic */ void m1026xcfa7fc81(View view) {
            int bindingAdapterPosition = getBindingAdapterPosition();
            if (bindingAdapterPosition == -1) {
                return;
            }
            if (SearchQuranAdabters.this.minSelected == -1) {
                SearchQuranAdabters.this.minSelected = bindingAdapterPosition;
                SearchQuranAdabters.this.maxSelected = bindingAdapterPosition;
            } else if (bindingAdapterPosition < SearchQuranAdabters.this.minSelected) {
                SearchQuranAdabters.this.minSelected = bindingAdapterPosition;
            } else if (bindingAdapterPosition > SearchQuranAdabters.this.maxSelected) {
                SearchQuranAdabters.this.maxSelected = bindingAdapterPosition;
            } else {
                SearchQuranAdabters.this.minSelected = bindingAdapterPosition;
                SearchQuranAdabters.this.maxSelected = bindingAdapterPosition;
            }
            SearchQuranAdabters.this.notifyDataSetChanged();
            if (SearchQuranAdabters.this.callback != null) {
                SearchQuranAdabters.this.callback.onClick(SearchQuranAdabters.this.minSelected, SearchQuranAdabters.this.maxSelected, (ItemQuranSearch) SearchQuranAdabters.this.searchList.get(bindingAdapterPosition));
            }
        }
    }
}

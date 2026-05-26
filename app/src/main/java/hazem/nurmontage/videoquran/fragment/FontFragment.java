package hazem.nurmontage.videoquran.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import hazem.nurmontage.videoquran.R;
import hazem.nurmontage.videoquran.Utils.FontProvider;
import hazem.nurmontage.videoquran.adabter.FontTextAdabters;
import hazem.nurmontage.videoquran.databinding.FragmentFontBinding;

/* loaded from: classes2.dex */
public class FontFragment extends Fragment {
    public static FontFragment instance;
    private String font_select;
    private FragmentFontBinding fragmentBinding;
    private IFontCallback iFontCallback;
    private boolean isInit = true;
    private Typeface lastTypeface;
    private String last_font;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private Typeface typeface;

    public interface IFontCallback {
        void onAdd(String str, Typeface typeface);

        void onCancel(String str, Typeface typeface);

        void onDone(String str, Typeface typeface);
    }

    public static FontFragment getInstance(IFontCallback iFontCallback, String str, Typeface typeface) {
        if (instance == null) {
            instance = new FontFragment(iFontCallback, str, typeface);
        }
        return instance;
    }

    public FontFragment() {
    }

    public FontFragment(IFontCallback iFontCallback, String str, Typeface typeface) {
        this.iFontCallback = iFontCallback;
        this.last_font = str;
        this.lastTypeface = typeface;
    }

    public void add(Typeface typeface, String str) {
        this.typeface = typeface;
        this.font_select = str;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        FragmentFontBinding inflate = FragmentFontBinding.inflate(layoutInflater, viewGroup, false);
        this.fragmentBinding = inflate;
        LinearLayout root = inflate.getRoot();
        try {
            FontProvider fontProvider = new FontProvider(getResources());
            this.recyclerView = (RecyclerView) root.findViewById(R.id.rv);
            int indexOf = fontProvider.getFontNamesQuran().indexOf(this.last_font.substring(0, this.last_font.length() - 4));
            final FontTextAdabters fontTextAdabters = new FontTextAdabters(fontProvider, this.iFontCallback, fontProvider.getFontNamesQuran(), indexOf);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            this.linearLayoutManager = linearLayoutManager;
            this.recyclerView.setLayoutManager(linearLayoutManager);
            this.recyclerView.setHasFixedSize(true);
            this.recyclerView.setAdapter(fontTextAdabters);
            final LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
            linearSnapHelper.attachToRecyclerView(this.recyclerView);
            this.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: hazem.nurmontage.videoquran.fragment.FontFragment.1
                @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
                public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                    super.onScrollStateChanged(recyclerView, i);
                }

                @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
                public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                    super.onScrolled(recyclerView, i, i2);
                    if (FontFragment.this.isInit) {
                        FontFragment.this.isInit = false;
                        return;
                    }
                    View findSnapView = linearSnapHelper.findSnapView(FontFragment.this.linearLayoutManager);
                    if (findSnapView != null) {
                        final int position = FontFragment.this.linearLayoutManager.getPosition(findSnapView);
                        FontFragment.this.recyclerView.post(new Runnable() { // from class: hazem.nurmontage.videoquran.fragment.FontFragment.1.1
                            @Override // java.lang.Runnable
                            public void run() {
                                if (fontTextAdabters != null) {
                                    fontTextAdabters.setSelected(position);
                                }
                            }
                        });
                    }
                }
            });
            if (indexOf > 1) {
                this.recyclerView.scrollToPosition(indexOf - 1);
            } else if (indexOf >= 0) {
                this.recyclerView.scrollToPosition(indexOf);
            }
            root.findViewById(R.id.btn_done).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.FontFragment.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (FontFragment.this.iFontCallback != null) {
                        FontFragment.this.iFontCallback.onDone(FontFragment.this.font_select, FontFragment.this.typeface);
                    }
                }
            });
            root.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.FontFragment.3
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (FontFragment.this.iFontCallback == null || FontFragment.this.last_font == null || FontFragment.this.lastTypeface == null) {
                        return;
                    }
                    FontFragment.this.iFontCallback.onCancel(FontFragment.this.last_font, FontFragment.this.lastTypeface);
                }
            });
        } catch (Exception unused) {
        }
        return root;
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.fragmentBinding = null;
        this.iFontCallback = null;
        instance = null;
    }
}

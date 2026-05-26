package hazem.nurmontage.videoquran.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import hazem.nurmontage.videoquran.R;
import hazem.nurmontage.videoquran.adabter.ColorAdabter;
import hazem.nurmontage.videoquran.common.Common;
import hazem.nurmontage.videoquran.databinding.FragmentColorsBinding;
import hazem.nurmontage.videoquran.fragment.EditIpadFragment;

/* loaded from: classes2.dex */
public class ColorsFragment extends Fragment {
    public static ColorsFragment instance;
    private ColorAdabter adapter;
    private FragmentColorsBinding binding;
    private ColorAdabter.IColor iColor = new ColorAdabter.IColor() { // from class: hazem.nurmontage.videoquran.fragment.ColorsFragment.1
        @Override // hazem.nurmontage.videoquran.adabter.ColorAdabter.IColor
        public void onColor(int i, int i2) {
            ColorsFragment.this.scrollToSelectedPosition();
            if (ColorsFragment.this.iIpadEditCallback != null) {
                ColorsFragment.this.iIpadEditCallback.onClick(i, i2);
            }
        }
    };
    private EditIpadFragment.IIpadEditCallback iIpadEditCallback;
    private int index;
    private RecyclerView recyclerView;

    public static ColorsFragment getInstance(EditIpadFragment.IIpadEditCallback iIpadEditCallback, int i) {
        if (instance == null) {
            instance = new ColorsFragment(iIpadEditCallback, i);
        }
        return instance;
    }

    public ColorsFragment(EditIpadFragment.IIpadEditCallback iIpadEditCallback, int i) {
        this.iIpadEditCallback = iIpadEditCallback;
        this.index = i;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        FragmentColorsBinding inflate = FragmentColorsBinding.inflate(layoutInflater, viewGroup, false);
        this.binding = inflate;
        LinearLayout root = inflate.getRoot();
        this.recyclerView = (RecyclerView) root.findViewById(R.id.rv_color);
        this.adapter = new ColorAdabter(this.iColor, Common.MUSLIM_COLORS, this.index);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), 0, false));
        this.recyclerView.setItemAnimator(null);
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setAdapter(this.adapter);
        try {
            int i = this.index;
            if (i > 3) {
                this.recyclerView.scrollToPosition(i - 3);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return root;
    }

    public void scrollToSelectedPosition() {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) this.recyclerView.getLayoutManager();
        if (linearLayoutManager != null) {
            linearLayoutManager.scrollToPositionWithOffset(this.adapter.getPos_select(), (this.recyclerView.getWidth() / 2) - 50);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        instance = null;
        this.binding = null;
        this.iColor = null;
    }
}

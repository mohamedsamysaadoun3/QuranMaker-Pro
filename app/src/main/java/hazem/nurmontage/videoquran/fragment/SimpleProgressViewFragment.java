package hazem.nurmontage.videoquran.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.fragment.app.Fragment;
import hazem.nurmontage.videoquran.R;
import hazem.nurmontage.videoquran.databinding.FragmentProgressViewBinding;

/* loaded from: classes2.dex */
public class SimpleProgressViewFragment extends Fragment {
    public static SimpleProgressViewFragment instance;
    private FragmentProgressViewBinding fragmentBinding;

    public static SimpleProgressViewFragment getInstance() {
        if (instance == null) {
            instance = new SimpleProgressViewFragment();
        }
        return instance;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        FragmentProgressViewBinding inflate = FragmentProgressViewBinding.inflate(layoutInflater, viewGroup, false);
        this.fragmentBinding = inflate;
        FrameLayout root = inflate.getRoot();
        root.setBackgroundColor(0);
        root.findViewById(R.id.view_1).setVisibility(8);
        root.findViewById(R.id.view_2).setVisibility(8);
        return root;
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.fragmentBinding = null;
        instance = null;
    }
}

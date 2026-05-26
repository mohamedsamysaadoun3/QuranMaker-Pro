package hazem.nurmontage.videoquran.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import hazem.nurmontage.videoquran.R;
import hazem.nurmontage.videoquran.Utils.BillingPreferences;
import hazem.nurmontage.videoquran.adabter.GradientAdabter;
import hazem.nurmontage.videoquran.common.Common;
import hazem.nurmontage.videoquran.databinding.FragmentColorsBinding;
import hazem.nurmontage.videoquran.fragment.EditIpadFragment;
import hazem.nurmontage.videoquran.model.Gradient;
import hazem.nurmontage.videoquran.views.TextCustumFont;

/* loaded from: classes2.dex */
public class GradientFragment extends Fragment {
    public static GradientFragment instance;
    private GradientAdabter adapter;
    private FragmentColorsBinding binding;
    private Gradient gradient;
    private GradientAdabter.IColor iColor = new GradientAdabter.IColor() { // from class: hazem.nurmontage.videoquran.fragment.GradientFragment.2
        @Override // hazem.nurmontage.videoquran.adabter.GradientAdabter.IColor
        public void onGradient(Gradient gradient, int i) {
            if (GradientFragment.this.gradient == null) {
                GradientFragment.this.binding.getRoot().findViewById(R.id.layout_edit_gradient).setVisibility(0);
            }
            GradientFragment.this.gradient = gradient;
            GradientFragment.this.gradient.setAngle(GradientFragment.this.seekBar_angle.getProgress());
            GradientFragment.this.index = i;
            GradientFragment.this.scrollToSelectedPosition();
            if (GradientFragment.this.iIpadEditCallback != null) {
                GradientFragment.this.iIpadEditCallback.onClick(gradient, i);
            }
        }
    };
    private EditIpadFragment.IIpadEditCallback iIpadEditCallback;
    private int index;
    private RecyclerView recyclerView;
    private SeekBar seekBar_angle;
    private TextCustumFont tv_angle;

    public static GradientFragment getInstance(EditIpadFragment.IIpadEditCallback iIpadEditCallback, int i) {
        if (instance == null) {
            instance = new GradientFragment(iIpadEditCallback, i);
        }
        return instance;
    }

    public GradientFragment(EditIpadFragment.IIpadEditCallback iIpadEditCallback, int i) {
        this.iIpadEditCallback = iIpadEditCallback;
        this.index = i;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        FragmentColorsBinding inflate = FragmentColorsBinding.inflate(layoutInflater, viewGroup, false);
        this.binding = inflate;
        LinearLayout root = inflate.getRoot();
        this.recyclerView = (RecyclerView) root.findViewById(R.id.rv_color);
        this.adapter = new GradientAdabter(this.iColor, Common.getListGradientColor(), BillingPreferences.isSubscribed(getContext()), this.index);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), 0, false));
        this.recyclerView.setItemAnimator(null);
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setAdapter(this.adapter);
        this.gradient = this.adapter.getSelect();
        this.tv_angle = (TextCustumFont) root.findViewById(R.id.tv_angle);
        this.seekBar_angle = (SeekBar) root.findViewById(R.id.seekbar);
        if (this.gradient != null) {
            root.findViewById(R.id.layout_edit_gradient).setVisibility(0);
            this.seekBar_angle.setProgress(this.gradient.getAngle());
        }
        this.tv_angle.setText(String.valueOf(this.seekBar_angle.getProgress()));
        this.seekBar_angle.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: hazem.nurmontage.videoquran.fragment.GradientFragment.1
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (GradientFragment.this.gradient == null || GradientFragment.this.iIpadEditCallback == null) {
                    return;
                }
                GradientFragment.this.gradient.setAngle(i);
                GradientFragment.this.tv_angle.setText(String.valueOf(i));
                GradientFragment.this.iIpadEditCallback.onClick(GradientFragment.this.gradient, GradientFragment.this.index);
            }
        });
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

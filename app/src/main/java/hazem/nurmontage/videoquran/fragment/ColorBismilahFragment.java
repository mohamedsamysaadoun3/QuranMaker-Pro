package hazem.nurmontage.videoquran.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import hazem.nurmontage.videoquran.R;
import hazem.nurmontage.videoquran.Utils.Utils;
import hazem.nurmontage.videoquran.adabter.ColorAdabter;
import hazem.nurmontage.videoquran.common.Common;
import hazem.nurmontage.videoquran.constant.AyaTextPreset;
import hazem.nurmontage.videoquran.databinding.FragmentColorAyaBinding;
import hazem.nurmontage.videoquran.fragment.EditBismilahEntityFragment;
import hazem.nurmontage.videoquran.model.BismilahEntity;
import hazem.nurmontage.videoquran.views.TextCustumFont;

/* loaded from: classes2.dex */
public class ColorBismilahFragment extends Fragment {
    public static ColorBismilahFragment instance;
    private ColorAdabter adapter;
    private FragmentColorAyaBinding binding;
    private BismilahEntity entity_select;
    private ColorAdabter.IColor iColor = new ColorAdabter.IColor() { // from class: hazem.nurmontage.videoquran.fragment.ColorBismilahFragment.2
        @Override // hazem.nurmontage.videoquran.adabter.ColorAdabter.IColor
        public void onColor(int i, int i2) {
            if (ColorBismilahFragment.this.iEditSName == null || ColorBismilahFragment.this.entity_select == null) {
                return;
            }
            ColorBismilahFragment.this.scrollToSelectedPosition();
            ColorBismilahFragment.this.iEditSName.updateAya(i);
        }
    };
    private EditBismilahEntityFragment.IBismilahEntityCallback iEditSName;
    private RecyclerView recyclerView;
    private Resources resources;

    public static ColorBismilahFragment getInstance(EditBismilahEntityFragment.IBismilahEntityCallback iBismilahEntityCallback, BismilahEntity bismilahEntity, Resources resources) {
        if (instance == null) {
            instance = new ColorBismilahFragment(iBismilahEntityCallback, bismilahEntity, resources);
        }
        return instance;
    }

    public ColorBismilahFragment(EditBismilahEntityFragment.IBismilahEntityCallback iBismilahEntityCallback, BismilahEntity bismilahEntity, Resources resources) {
        this.iEditSName = iBismilahEntityCallback;
        this.entity_select = bismilahEntity;
        this.resources = resources;
    }

    public ColorBismilahFragment() {
    }

    private void setupPresetButtons(View view) {
        TextCustumFont textCustumFont = (TextCustumFont) view.findViewById(R.id.btnNone);
        TextCustumFont textCustumFont2 = (TextCustumFont) view.findViewById(R.id.btnOutline);
        TextCustumFont textCustumFont3 = (TextCustumFont) view.findViewById(R.id.btnShadow);
        TextCustumFont textCustumFont4 = (TextCustumFont) view.findViewById(R.id.btnGlow);
        textCustumFont.setText(this.resources.getString(R.string.preset_none));
        textCustumFont2.setText(this.resources.getString(R.string.preset_outline));
        textCustumFont3.setText(this.resources.getString(R.string.preset_shadow));
        textCustumFont4.setText(this.resources.getString(R.string.preset_glow));
        int i = 0;
        final TextView[] textViewArr = {textCustumFont, textCustumFont2, textCustumFont3, textCustumFont4};
        final AyaTextPreset[] ayaTextPresetArr = {AyaTextPreset.NONE, AyaTextPreset.OUTLINE, AyaTextPreset.SHADOW, AyaTextPreset.GLOW};
        for (int i2 = 0; i2 < 4; i2++) {
            final int finalI2 = i2;
            textViewArr[i2].setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.ColorBismilahFragment$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    ColorBismilahFragment.this.m1089x2d2a96a7(textViewArr, finalI2, ayaTextPresetArr, view2);
                }
            });
        }
        BismilahEntity bismilahEntity = this.entity_select;
        AyaTextPreset ayaTextPreset = bismilahEntity.get(bismilahEntity.getmPreset());
        if (ayaTextPreset != AyaTextPreset.NONE) {
            if (ayaTextPreset == AyaTextPreset.OUTLINE) {
                i = 1;
            } else {
                i = ayaTextPreset == AyaTextPreset.SHADOW ? 2 : 3;
            }
        }
        selectPreset(textViewArr, i);
    }

    /* renamed from: lambda$setupPresetButtons$0$hazem-nurmontage-videoquran-fragment-ColorBismilahFragment, reason: not valid java name */
    /* synthetic */ void m1089x2d2a96a7(TextView[] textViewArr, int i, AyaTextPreset[] ayaTextPresetArr, View view) {
        selectPreset(textViewArr, i);
        EditBismilahEntityFragment.IBismilahEntityCallback iBismilahEntityCallback = this.iEditSName;
        if (iBismilahEntityCallback != null) {
            iBismilahEntityCallback.updatePreset(ayaTextPresetArr[i]);
        }
    }

    private void selectPreset(TextView[] textViewArr, int i) {
        int i2 = 0;
        while (i2 < textViewArr.length) {
            textViewArr[i2].setSelected(i2 == i);
            i2++;
        }
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        FragmentColorAyaBinding inflate = FragmentColorAyaBinding.inflate(layoutInflater, viewGroup, false);
        this.binding = inflate;
        LinearLayout root = inflate.getRoot();
        if (this.iEditSName != null && this.entity_select != null && this.resources != null) {
            this.recyclerView = (RecyclerView) root.findViewById(R.id.rv_color);
            this.adapter = new ColorAdabter(this.iColor, Common.MUSLIM_AYA_COLORS, Utils.indexOf(Common.MUSLIM_AYA_COLORS, this.entity_select.getClrAya()));
            this.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), 0, false));
            this.recyclerView.setItemAnimator(null);
            this.recyclerView.setHasFixedSize(true);
            this.recyclerView.setAdapter(this.adapter);
            if (this.adapter.getPos_select() > 2) {
                scrollToSelectedPosition(this.adapter.getPos_select() - 2);
            }
            initTab(root);
            setupPresetButtons(root);
            root.findViewById(R.id.btn_done).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.ColorBismilahFragment.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (ColorBismilahFragment.this.iEditSName != null) {
                        ColorBismilahFragment.this.iEditSName.onDone();
                    }
                }
            });
        }
        return root;
    }

    public void scrollToSelectedPosition() {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) this.recyclerView.getLayoutManager();
        if (linearLayoutManager != null) {
            linearLayoutManager.scrollToPositionWithOffset(this.adapter.getPos_select(), (this.recyclerView.getWidth() / 2) - 50);
        }
    }

    public void scrollToSelectedPosition(int i) {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) this.recyclerView.getLayoutManager();
        if (linearLayoutManager != null) {
            linearLayoutManager.scrollToPositionWithOffset(i, this.recyclerView.getWidth() / 2);
        }
    }

    private void initTab(View view) {
        view.findViewById(R.id.tab_layout).setVisibility(8);
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        this.binding = null;
        instance = null;
        this.iColor = null;
        super.onDestroyView();
    }
}

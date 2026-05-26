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
import com.google.android.material.tabs.TabLayout;
import hazem.nurmontage.videoquran.R;
import hazem.nurmontage.videoquran.Utils.Utils;
import hazem.nurmontage.videoquran.adabter.ColorAdabter;
import hazem.nurmontage.videoquran.common.Common;
import hazem.nurmontage.videoquran.constant.AyaTextPreset;
import hazem.nurmontage.videoquran.databinding.FragmentColorAyaBinding;
import hazem.nurmontage.videoquran.fragment.EditEntityFragment;
import hazem.nurmontage.videoquran.model.QuranEntity;
import hazem.nurmontage.videoquran.views.TextCustumFont;

/* loaded from: classes2.dex */
public class ColorAyaFragment extends Fragment {
    public static ColorAyaFragment instance;
    private ColorAdabter adapter;
    private FragmentColorAyaBinding binding;
    private QuranEntity entity_select;
    private ColorAdabter.IColor iColor = new ColorAdabter.IColor() { // from class: hazem.nurmontage.videoquran.fragment.ColorAyaFragment.2
        @Override // hazem.nurmontage.videoquran.adabter.ColorAdabter.IColor
        public void onColor(int i, int i2) {
            if (ColorAyaFragment.this.iEditEntityCallback == null) {
                return;
            }
            ColorAyaFragment.this.scrollToSelectedPosition();
            if (ColorAyaFragment.this.tabLayout == null || ColorAyaFragment.this.tabLayout.getSelectedTabPosition() == 0) {
                ColorAyaFragment.this.iEditEntityCallback.updateAya(i);
            } else {
                ColorAyaFragment.this.iEditEntityCallback.updateTrsl(i);
            }
        }
    };
    private EditEntityFragment.IEditEntityCallback iEditEntityCallback;
    private RecyclerView recyclerView;
    private Resources resources;
    private TabLayout tabLayout;

    public static ColorAyaFragment getInstance(EditEntityFragment.IEditEntityCallback iEditEntityCallback, QuranEntity quranEntity, Resources resources) {
        if (instance == null) {
            instance = new ColorAyaFragment(iEditEntityCallback, quranEntity, resources);
        }
        return instance;
    }

    public ColorAyaFragment(EditEntityFragment.IEditEntityCallback iEditEntityCallback, QuranEntity quranEntity, Resources resources) {
        this.iEditEntityCallback = iEditEntityCallback;
        this.entity_select = quranEntity;
        this.resources = resources;
    }

    public ColorAyaFragment() {
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
            textViewArr[i2].setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.ColorAyaFragment$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    ColorAyaFragment.this.m1086x321106cd(textViewArr, finalI2, ayaTextPresetArr, view2);
                }
            });
        }
        QuranEntity quranEntity = this.entity_select;
        AyaTextPreset ayaTextPreset = quranEntity.get(quranEntity.getmPreset());
        if (ayaTextPreset != AyaTextPreset.NONE) {
            if (ayaTextPreset == AyaTextPreset.OUTLINE) {
                i = 1;
            } else {
                i = ayaTextPreset == AyaTextPreset.SHADOW ? 2 : 3;
            }
        }
        selectPreset(textViewArr, i);
    }

    /* renamed from: lambda$setupPresetButtons$0$hazem-nurmontage-videoquran-fragment-ColorAyaFragment, reason: not valid java name */
    /* synthetic */ void m1086x321106cd(TextView[] textViewArr, int i, AyaTextPreset[] ayaTextPresetArr, View view) {
        selectPreset(textViewArr, i);
        EditEntityFragment.IEditEntityCallback iEditEntityCallback = this.iEditEntityCallback;
        if (iEditEntityCallback != null) {
            iEditEntityCallback.updatePreset(ayaTextPresetArr[i]);
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
        if (this.iEditEntityCallback != null && this.entity_select != null && this.resources != null) {
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
            root.findViewById(R.id.btn_done).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.ColorAyaFragment.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (ColorAyaFragment.this.iEditEntityCallback != null) {
                        ColorAyaFragment.this.iEditEntityCallback.onDone();
                    }
                }
            });
        }
        return root;
    }

    public void scrollToSelectedPosition(int i) {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) this.recyclerView.getLayoutManager();
        if (linearLayoutManager != null) {
            linearLayoutManager.scrollToPositionWithOffset(i, this.recyclerView.getWidth() / 2);
        }
    }

    public void scrollToSelectedPosition() {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) this.recyclerView.getLayoutManager();
        if (linearLayoutManager != null) {
            linearLayoutManager.scrollToPositionWithOffset(this.adapter.getPos_select(), (this.recyclerView.getWidth() / 2) - 50);
        }
    }

    private void initTab(View view) {
        if (this.entity_select.getTranslation() == null) {
            view.findViewById(R.id.tab_layout).setVisibility(8);
            return;
        }
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        this.tabLayout = tabLayout;
        TabLayout.Tab newTab = tabLayout.newTab();
        newTab.setText(this.resources.getString(R.string.tab_aya));
        addCustomViewToTab(newTab);
        TabLayout.Tab newTab2 = this.tabLayout.newTab();
        newTab2.setText(this.resources.getString(R.string.tv_translation));
        addCustomViewToTab(newTab2);
        this.tabLayout.addTab(newTab, true);
        this.tabLayout.addTab(newTab2, false);
        this.tabLayout.setTabMode(0);
    }

    private void addCustomViewToTab(TabLayout.Tab tab) {
        View inflate = getLayoutInflater().inflate(R.layout.layout_tablayout, (ViewGroup) null);
        ((TextCustumFont) inflate.findViewById(R.id.name)).setText(tab.getText().toString());
        tab.setCustomView(inflate);
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        this.binding = null;
        instance = null;
        this.iColor = null;
        super.onDestroyView();
    }
}

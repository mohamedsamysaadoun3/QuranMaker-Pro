package hazem.nurmontage.videoquran.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.tabs.TabLayout;
import hazem.nurmontage.videoquran.R;
import hazem.nurmontage.videoquran.Utils.BillingPreferences;
import hazem.nurmontage.videoquran.adabter.IpadAdabter;
import hazem.nurmontage.videoquran.constant.IpadType;
import hazem.nurmontage.videoquran.databinding.FragmentEditIpadBinding;
import hazem.nurmontage.videoquran.model.Gradient;
import hazem.nurmontage.videoquran.model.IpadItem;
import hazem.nurmontage.videoquran.views.TextCustumFont;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class EditIpadFragment extends Fragment {
    public static EditIpadFragment instance;
    private FragmentEditIpadBinding fragmentBinding;
    private IIpadEditCallback iIpadEditCallback;
    private int index_select;
    private IpadAdabter ipadAdabter;
    private int ipadType;
    private boolean isGlass;
    private boolean isGradient;
    private int mCurrentPosFragment = 0;
    private Resources resources;
    private RecyclerView rv_type;

    public interface IIpadEditCallback {
        void onCancel();

        void onChangeType(int i);

        void onClick(int i, int i2);

        void onClick(Gradient gradient, int i);

        void onDialogPremium();

        void onDone();

        void onGlassType(boolean z);
    }

    public static EditIpadFragment getInstance(Resources resources, int i, IIpadEditCallback iIpadEditCallback, int i2, boolean z, boolean z2) {
        if (instance == null) {
            instance = new EditIpadFragment(resources, i, iIpadEditCallback, i2, z, z2);
        }
        return instance;
    }

    public EditIpadFragment(Resources resources, int i, IIpadEditCallback iIpadEditCallback, int i2, boolean z, boolean z2) {
        this.iIpadEditCallback = iIpadEditCallback;
        this.ipadType = i;
        this.isGlass = z2;
        this.resources = resources;
        this.index_select = i2;
        this.isGradient = z;
    }

    public EditIpadFragment() {
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        FragmentEditIpadBinding inflate = FragmentEditIpadBinding.inflate(layoutInflater, viewGroup, false);
        this.fragmentBinding = inflate;
        RelativeLayout root = inflate.getRoot();
        this.rv_type = (RecyclerView) root.findViewById(R.id.rv_type);
        ArrayList arrayList = new ArrayList();
        arrayList.add(new IpadItem(R.drawable.ipad_t, IpadType.IPAD));
        arrayList.add(new IpadItem(R.drawable.ipad_unblur, IpadType.IPAD_UNBLUR));
        arrayList.add(new IpadItem(R.drawable.ipad_classic, IpadType.IPAD_CLASSIC));
        arrayList.add(new IpadItem(R.drawable.ipad_neomorphic, IpadType.IPAD_NEOMORPHIC));
        arrayList.add(new IpadItem(R.drawable.ipad_caset, IpadType.CASSET));
        arrayList.add(new IpadItem(R.drawable.ipad_caset_img, IpadType.CASSET_IMG));
        arrayList.add(new IpadItem(R.drawable.ipad_caset_img_blur, IpadType.CASSET_IMG_BLUR));
        arrayList.add(new IpadItem(R.drawable.ipad_rect, IpadType.RECT));
        arrayList.add(new IpadItem(R.drawable.ipad_rect_round, IpadType.ROUND_RECT));
        arrayList.add(new IpadItem(R.drawable.ipad_bottom_rect, IpadType.BOTTOM_RECT));
        arrayList.add(new IpadItem(R.drawable.ipad_layer_black, IpadType.BLACK_LAYER));
        arrayList.add(new IpadItem(R.drawable.ipad_gradient, IpadType.GRADIENT));
        arrayList.add(new IpadItem(R.drawable.ipad_mask, IpadType.MASK_BRUSH));
        arrayList.add(new IpadItem(R.drawable.ipad_blue_type, IpadType.BLUE_TYPE));
        arrayList.add(new IpadItem(R.drawable.ic_heart_ipad, IpadType.HEART));
        arrayList.add(new IpadItem(R.drawable.ic_battery, IpadType.BATTERY));
        int posSelect = getPosSelect(this.ipadType, arrayList);
        this.ipadAdabter = new IpadAdabter(BillingPreferences.isSubscribed(getContext()), posSelect, this.ipadType, this.iIpadEditCallback, arrayList, this.isGlass);
        this.rv_type.setLayoutManager(new LinearLayoutManager(getContext(), 0, false));
        this.rv_type.setHasFixedSize(true);
        this.rv_type.setItemAnimator(null);
        this.rv_type.setAdapter(this.ipadAdabter);
        if (posSelect > 3) {
            try {
                this.rv_type.scrollToPosition(posSelect - 3);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        root.findViewById(R.id.btn_done).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.EditIpadFragment.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (EditIpadFragment.this.iIpadEditCallback != null) {
                    EditIpadFragment.this.iIpadEditCallback.onDone();
                }
            }
        });
        initTab(root);
        return root;
    }

    public void scrollToSelectedPosition() {
        try {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) this.rv_type.getLayoutManager();
            if (linearLayoutManager != null) {
                linearLayoutManager.scrollToPositionWithOffset(this.ipadAdabter.getPos_select(), (this.rv_type.getWidth() / 2) - 50);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addCustomViewToTab(TabLayout.Tab tab) {
        View inflate = getLayoutInflater().inflate(R.layout.layout_tablayout, (ViewGroup) null);
        ((TextCustumFont) inflate.findViewById(R.id.name)).setText(tab.getText().toString());
        tab.setCustomView(inflate);
    }

    private void initTab(View view) {
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        TabLayout.Tab newTab = tabLayout.newTab();
        newTab.setText(this.resources.getString(R.string.color));
        addCustomViewToTab(newTab);
        TabLayout.Tab newTab2 = tabLayout.newTab();
        newTab2.setText(this.resources.getString(R.string.gradient));
        addCustomViewToTab(newTab2);
        if (this.isGradient) {
            tabLayout.addTab(newTab, false);
            tabLayout.addTab(newTab2, true);
        } else {
            tabLayout.addTab(newTab, true);
            tabLayout.addTab(newTab2, false);
        }
        tabLayout.setTabMode(0);
        this.mCurrentPosFragment = this.isGradient ? 1 : 0;
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() { // from class: hazem.nurmontage.videoquran.fragment.EditIpadFragment.2
            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabReselected(TabLayout.Tab tab) {
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabSelected(TabLayout.Tab tab) {
                if (EditIpadFragment.this.mCurrentPosFragment != tab.getPosition()) {
                    EditIpadFragment.this.mCurrentPosFragment = tab.getPosition();
                    FragmentTransaction beginTransaction = EditIpadFragment.this.getChildFragmentManager().beginTransaction();
                    int i = R.id.container;
                    EditIpadFragment editIpadFragment = EditIpadFragment.this;
                    beginTransaction.replace(i, editIpadFragment.getFragment(editIpadFragment.mCurrentPosFragment)).addToBackStack(null).commit();
                }
            }
        });
        getChildFragmentManager().beginTransaction().replace(R.id.container, getFragment(this.mCurrentPosFragment)).addToBackStack(null).commit();
        this.index_select = -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Fragment getFragment(int i) {
        if (i == 1) {
            return GradientFragment.getInstance(this.iIpadEditCallback, this.index_select);
        }
        return ColorsFragment.getInstance(this.iIpadEditCallback, this.index_select);
    }

    private int getPosSelect(int i, List<IpadItem> list) {
        for (int i2 = 0; i2 < list.size(); i2++) {
            if (list.get(i2).getIpadType().ordinal() == i) {
                return i2;
            }
        }
        return 0;
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        instance = null;
        this.fragmentBinding = null;
        this.iIpadEditCallback = null;
    }
}

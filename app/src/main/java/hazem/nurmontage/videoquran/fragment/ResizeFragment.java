package hazem.nurmontage.videoquran.fragment;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import hazem.nurmontage.videoquran.R;
import hazem.nurmontage.videoquran.Utils.ScreenUtils;
import hazem.nurmontage.videoquran.Utils.Utils;
import hazem.nurmontage.videoquran.adabter.DimensionAdabters;
import hazem.nurmontage.videoquran.common.DataDimension;
import hazem.nurmontage.videoquran.databinding.FragmentResizeBinding;
import hazem.nurmontage.videoquran.model.ItemDimension;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class ResizeFragment extends Fragment {
    public static ResizeFragment instance;
    private DimensionAdabters adabter;
    private FragmentResizeBinding binding;
    private DimensionAdabters.IDimensionCallback iDimensionCallback;
    private int posSelectResize = -1;
    private RecyclerView recyclerView;
    private Resources res;
    private String selectResize;

    public ResizeFragment() {
    }

    public static ResizeFragment getInstance(DimensionAdabters.IDimensionCallback iDimensionCallback, Resources resources, String str) {
        if (instance == null) {
            instance = new ResizeFragment(iDimensionCallback, resources, str);
        }
        return instance;
    }

    public void scrollToSelectedPosition() {
        try {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) this.recyclerView.getLayoutManager();
            if (linearLayoutManager != null) {
                linearLayoutManager.scrollToPositionWithOffset(this.adabter.getSelected(), (this.recyclerView.getWidth() / 2) - 50);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResizeFragment(DimensionAdabters.IDimensionCallback iDimensionCallback, Resources resources, String str) {
        this.iDimensionCallback = iDimensionCallback;
        this.selectResize = str;
        this.res = resources;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        FragmentResizeBinding inflate = FragmentResizeBinding.inflate(layoutInflater, viewGroup, false);
        this.binding = inflate;
        RelativeLayout root = inflate.getRoot();
        if (this.res != null && this.iDimensionCallback != null) {
            root.findViewById(R.id.btn_done).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.ResizeFragment.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (ResizeFragment.this.iDimensionCallback != null) {
                        ResizeFragment.this.iDimensionCallback.done();
                    }
                }
            });
            RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.rv);
            this.recyclerView = recyclerView;
            recyclerView.setHasFixedSize(true);
            this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), 0, false));
            this.recyclerView.setItemAnimator(null);
            List<ItemDimension> aLl = DataDimension.getALl(this.res);
            DimensionAdabters dimensionAdabters = new DimensionAdabters(aLl, this.iDimensionCallback, getListDimension(getActivity(), aLl), this.posSelectResize);
            this.adabter = dimensionAdabters;
            this.recyclerView.setAdapter(dimensionAdabters);
            int i = this.posSelectResize;
            if (i > 0) {
                this.recyclerView.scrollToPosition(i - 1);
            } else {
                this.recyclerView.scrollToPosition(i);
            }
        }
        return root;
    }

    public List<Pair<Integer, Integer>> getListDimension(Activity activity, List<ItemDimension> list) {
        int screenWidth = (int) (ScreenUtils.getScreenWidth(activity) * 0.27f);
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            ItemDimension itemDimension = list.get(i);
            if (itemDimension.getId().equals(this.selectResize)) {
                this.posSelectResize = i;
            }
            arrayList.add(Utils.getDimension(itemDimension.getResizeType(), screenWidth));
        }
        return arrayList;
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        this.iDimensionCallback = null;
        instance = null;
        FragmentResizeBinding fragmentResizeBinding = this.binding;
        if (fragmentResizeBinding != null) {
            fragmentResizeBinding.getRoot().removeAllViews();
            this.binding = null;
        }
        super.onDestroyView();
    }
}

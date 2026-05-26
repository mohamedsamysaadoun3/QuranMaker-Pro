package hazem.nurmontage.videoquran.fragment;

import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import hazem.nurmontage.videoquran.R;
import hazem.nurmontage.videoquran.Utils.AppUtils;
import hazem.nurmontage.videoquran.Utils.BillingPreferences;
import hazem.nurmontage.videoquran.Utils.ScreenUtils;
import hazem.nurmontage.videoquran.adabter.BgAdapter;
import hazem.nurmontage.videoquran.model.BgItem;
import hazem.nurmontage.videoquran.views.TextCustumFont;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class ChangeBgFragment extends Fragment {
    public static ChangeBgFragment instance;
    private BgAdapter adapter;
    private IChangeBgCallback callback;
    private boolean isSubscribed;
    private View layoutAddVideo;
    private View layoutBgRv;
    private RecyclerView recyclerView;
    private Resources res;
    private String selectedBg;

    public interface IChangeBgCallback {
        void onAdd(BgItem bgItem);

        void onCancel();

        void onCrop();

        void onDone();

        void onSubscribe();

        void onUploadImg();

        void onUploadVideo();
    }

    public static ChangeBgFragment getInstance(IChangeBgCallback iChangeBgCallback, Resources resources, String str) {
        if (instance == null) {
            instance = new ChangeBgFragment(iChangeBgCallback, resources, str);
        }
        return instance;
    }

    public ChangeBgFragment(IChangeBgCallback iChangeBgCallback, Resources resources, String str) {
        this.callback = iChangeBgCallback;
        this.res = resources;
        this.selectedBg = str;
    }

    public ChangeBgFragment() {
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getArguments() != null) {
            this.selectedBg = getArguments().getString("bg_select");
        }
        this.isSubscribed = BillingPreferences.isSubscribed(requireContext());
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_change_bg, viewGroup, false);
        bindViews(inflate);
        setupRecycler();
        setupButtons(inflate);
        return inflate;
    }

    private void bindViews(View view) {
        this.recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        this.layoutBgRv = view.findViewById(R.id.layout_bg_rv);
        this.layoutAddVideo = view.findViewById(R.id.layout_add_video_img);
        if (this.res != null) {
            ((TextCustumFont) view.findViewById(R.id.tv_img)).setText(this.res.getString(R.string.image));
            ((TextCustumFont) view.findViewById(R.id.tv_video)).setText(this.res.getString(R.string.video));
        }
    }

    private void setupRecycler() {
        List<BgItem> bgData = getBgData();
        this.adapter = new BgAdapter(AppUtils.getAppVersionName(getContext()), this.callback, bgData, (int) (ScreenUtils.getScreenWidth(requireActivity()) * 0.2f), findSelectedIndex(bgData));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), 0, false);
        linearLayoutManager.setItemPrefetchEnabled(true);
        linearLayoutManager.setInitialPrefetchItemCount(6);
        this.recyclerView.setLayoutManager(linearLayoutManager);
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setItemViewCacheSize(12);
        this.recyclerView.setItemAnimator(null);
        new LinearSnapHelper().attachToRecyclerView(this.recyclerView);
        this.recyclerView.post(new Runnable() { // from class: hazem.nurmontage.videoquran.fragment.ChangeBgFragment$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ChangeBgFragment.this.scrollToSelected();
            }
        });
    }

    private void setupButtons(View view) {
        view.findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.ChangeBgFragment$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ChangeBgFragment.this.m1077xacc46dfb(view2);
            }
        });
        view.findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.ChangeBgFragment$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ChangeBgFragment.this.m1078xd618c33c(view2);
            }
        });
        view.findViewById(R.id.btn_add_img).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.ChangeBgFragment$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ChangeBgFragment.this.m1079xff6d187d(view2);
            }
        });
        view.findViewById(R.id.btn_add_video).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.ChangeBgFragment$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ChangeBgFragment.this.m1080x28c16dbe(view2);
            }
        });
        view.findViewById(R.id.btn_done).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.ChangeBgFragment$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ChangeBgFragment.this.m1081x5215c2ff(view2);
            }
        });
        view.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.ChangeBgFragment$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ChangeBgFragment.this.m1082x7b6a1840(view2);
            }
        });
        ImageButton imageButton = (ImageButton) view.findViewById(R.id.btn_crop);
        if (!this.isSubscribed) {
            imageButton.setColorFilter(-8355712, PorterDuff.Mode.SRC_IN);
        }
        imageButton.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.ChangeBgFragment$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ChangeBgFragment.this.m1083xa4be6d81(view2);
            }
        });
        if (this.isSubscribed) {
            return;
        }
        view.findViewById(R.id.iv_data_disable).setVisibility(0);
    }

    /* renamed from: lambda$setupButtons$0$hazem-nurmontage-videoquran-fragment-ChangeBgFragment, reason: not valid java name */
    /* synthetic */ void m1077xacc46dfb(View view) {
        this.layoutAddVideo.setVisibility(0);
        this.layoutBgRv.setVisibility(4);
    }

    /* renamed from: lambda$setupButtons$1$hazem-nurmontage-videoquran-fragment-ChangeBgFragment, reason: not valid java name */
    /* synthetic */ void m1078xd618c33c(View view) {
        this.layoutAddVideo.setVisibility(8);
        this.layoutBgRv.setVisibility(0);
    }

    /* renamed from: lambda$setupButtons$2$hazem-nurmontage-videoquran-fragment-ChangeBgFragment, reason: not valid java name */
    /* synthetic */ void m1079xff6d187d(View view) {
        IChangeBgCallback iChangeBgCallback = this.callback;
        if (iChangeBgCallback != null) {
            iChangeBgCallback.onUploadImg();
        }
    }

    /* renamed from: lambda$setupButtons$3$hazem-nurmontage-videoquran-fragment-ChangeBgFragment, reason: not valid java name */
    /* synthetic */ void m1080x28c16dbe(View view) {
        IChangeBgCallback iChangeBgCallback = this.callback;
        if (iChangeBgCallback == null) {
            return;
        }
        if (!this.isSubscribed) {
            iChangeBgCallback.onSubscribe();
        } else {
            iChangeBgCallback.onUploadVideo();
        }
    }

    /* renamed from: lambda$setupButtons$4$hazem-nurmontage-videoquran-fragment-ChangeBgFragment, reason: not valid java name */
    /* synthetic */ void m1081x5215c2ff(View view) {
        IChangeBgCallback iChangeBgCallback = this.callback;
        if (iChangeBgCallback != null) {
            iChangeBgCallback.onDone();
        }
    }

    /* renamed from: lambda$setupButtons$5$hazem-nurmontage-videoquran-fragment-ChangeBgFragment, reason: not valid java name */
    /* synthetic */ void m1082x7b6a1840(View view) {
        IChangeBgCallback iChangeBgCallback = this.callback;
        if (iChangeBgCallback != null) {
            iChangeBgCallback.onCancel();
        }
    }

    /* renamed from: lambda$setupButtons$6$hazem-nurmontage-videoquran-fragment-ChangeBgFragment, reason: not valid java name */
    /* synthetic */ void m1083xa4be6d81(View view) {
        this.callback.onCrop();
    }

    private List<BgItem> getBgData() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new BgItem(R.drawable.bg_21, 0.1734694f, 0.31632653f, "bg_21"));
        arrayList.add(new BgItem(R.drawable.bg_22, 0.1734694f, 0.31632653f, "bg_22"));
        arrayList.add(new BgItem(R.drawable.bg_23, 0.1734694f, 0.31632653f, "bg_23"));
        arrayList.add(new BgItem(R.drawable.bg_24, 0.1734694f, 0.31632653f, "bg_24"));
        arrayList.add(new BgItem(R.drawable.bg_25, 0.1734694f, 0.31632653f, "bg_25"));
        arrayList.add(new BgItem(R.drawable.bg_26, 0.1734694f, 0.31632653f, "bg_26"));
        arrayList.add(new BgItem(R.drawable.bg_27, 0.1734694f, 0.31632653f, "bg_27"));
        arrayList.add(new BgItem(R.drawable.bg_32, 0.1734694f, 0.31632653f, "bg_32"));
        arrayList.add(new BgItem(R.drawable.bg_33, 0.1734694f, 0.31632653f, "bg_33"));
        arrayList.add(new BgItem(R.drawable.bg_34, 0.1734694f, 0.31632653f, "bg_34"));
        arrayList.add(new BgItem(R.drawable.bg_35, 0.1734694f, 0.31632653f, "bg_35"));
        arrayList.add(new BgItem(R.drawable.bg_36, 0.1734694f, 0.31632653f, "bg_36"));
        arrayList.add(new BgItem(R.drawable.bg_37, 0.1734694f, 0.31632653f, "bg_37"));
        arrayList.add(new BgItem(R.drawable.bg_38, 0.1734694f, 0.31632653f, "bg_38"));
        arrayList.add(new BgItem(R.drawable.bg_28, 0.1734694f, 0.31632653f, "bg_28"));
        arrayList.add(new BgItem(R.drawable.bg_29, 0.1734694f, 0.31632653f, "bg_29"));
        arrayList.add(new BgItem(R.drawable.bg_30, 0.1734694f, 0.31632653f, "bg_30"));
        arrayList.add(new BgItem(R.drawable.bg_31, 0.1734694f, 0.31632653f, "bg_31"));
        arrayList.add(new BgItem(R.drawable.bg_1, 0.1734694f, 0.51632655f, "bg_1"));
        arrayList.add(new BgItem(R.drawable.bg_2, 0.45918366f, 0.3392857f, "bg_2"));
        arrayList.add(new BgItem(R.drawable.bg_3, 0.21683674f, 0.073979594f, "bg_3"));
        arrayList.add(new BgItem(R.drawable.bg_4, 0.3469388f, 0.30612245f, "bg_4"));
        arrayList.add(new BgItem(R.drawable.bg_5, 0.19132653f, 0.26785713f, "bg_5"));
        arrayList.add(new BgItem(R.drawable.bg_6, 0.4486844f, 0.093112245f, "bg_6"));
        arrayList.add(new BgItem(R.drawable.bg_7, 0.41326532f, 0.45918366f, "bg_7"));
        arrayList.add(new BgItem(R.drawable.bg_8, 0.42091838f, 0.44005102f, "bg_8"));
        arrayList.add(new BgItem(R.drawable.bg_9, 0.3482143f, 0.2614796f, "bg_9"));
        arrayList.add(new BgItem(R.drawable.bg_10, 0.3137755f, 0.17219388f, "bg_10"));
        arrayList.add(new BgItem(R.drawable.bg_11, 0.49107143f, 0.17219388f, "bg_11"));
        arrayList.add(new BgItem(R.drawable.bg_12, 0.2755102f, 0.16709183f, "bg_12"));
        arrayList.add(new BgItem(R.drawable.bg_13, 0.35841838f, 0.1747449f, "bg_13"));
        arrayList.add(new BgItem(R.drawable.bg_14, 0.35841838f, 0.1747449f, "bg_14"));
        arrayList.add(new BgItem(R.drawable.bg_15, 0.35841838f, 0.1747449f, "bg_15"));
        arrayList.add(new BgItem(R.drawable.bg_16, 0.35841838f, 0.1747449f, "bg_16"));
        arrayList.add(new BgItem(R.drawable.bg_17, 0.35841838f, 0.1747449f, "bg_17"));
        arrayList.add(new BgItem(R.drawable.bg_18, 0.35841838f, 0.1747449f, "bg_18"));
        arrayList.add(new BgItem(R.drawable.bg_19, 0.35841838f, 0.1747449f, "bg_19"));
        arrayList.add(new BgItem(R.drawable.bg_20, 0.35841838f, 0.1747449f, "bg_20"));
        return arrayList;
    }

    private int findSelectedIndex(List<BgItem> list) {
        if (this.selectedBg == null) {
            return 0;
        }
        for (int i = 0; i < list.size(); i++) {
            if (this.selectedBg.equals(list.get(i).getName_drawable())) {
                return i;
            }
        }
        return 0;
    }

    public void scrollToSelected() {
        RecyclerView recyclerView = this.recyclerView;
        if (recyclerView == null) {
            return;
        }
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            ((LinearLayoutManager) layoutManager).scrollToPositionWithOffset(this.adapter.getSelectedPosition(), this.recyclerView.getWidth() / 2);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.callback = null;
        instance = null;
    }
}

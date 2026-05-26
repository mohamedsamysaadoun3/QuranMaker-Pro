package hazem.nurmontage.videoquran.fragment;

import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.tabs.TabLayout;
import hazem.nurmontage.videoquran.R;
import hazem.nurmontage.videoquran.Utils.BillingPreferences;
import hazem.nurmontage.videoquran.adabter.TransitionEntityAdabters;
import hazem.nurmontage.videoquran.constant.TransitionType;
import hazem.nurmontage.videoquran.databinding.FragmentEffectAyaBinding;
import hazem.nurmontage.videoquran.entity_timeline.EntityQuranTimeline;
import hazem.nurmontage.videoquran.model.Transition;
import hazem.nurmontage.videoquran.views.TextCustumFont;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import nl.dionsegijn.konfetti.core.Angle;

/* loaded from: classes2.dex */
public class EffectAyaFragment extends Fragment {
    public static EffectAyaFragment instance;
    private LinearLayout btnApplyAll;
    private ImageButton btn_unEffect;
    private EntityQuranTimeline entityQuranTimeline;
    private ITransition iTransition;
    int index;
    private ImageView iv_apply_all;
    private RecyclerView recyclerView;
    private Resources resources;
    private SeekBar seekBarDuration;
    private int tab_selected = 0;
    private float time;
    private Transition transition;
    private TransitionEntityAdabters transitionEntityAdabters;
    private FragmentEffectAyaBinding transitionEntityBinding;
    private TextCustumFont tvDuration;
    private TextCustumFont tv_apply_all;

    public interface ITransition {
        void applyAll(int i, EntityQuranTimeline entityQuranTimeline);

        void destroy(EntityQuranTimeline entityQuranTimeline);

        void in(String str, EntityQuranTimeline entityQuranTimeline);

        void onHideFragment(EntityQuranTimeline entityQuranTimeline);

        void out(String str, EntityQuranTimeline entityQuranTimeline);

        void playing(EntityQuranTimeline entityQuranTimeline);

        void remove(int i, EntityQuranTimeline entityQuranTimeline);

        void toSubscribe();

        void updateDurationIn(float f, EntityQuranTimeline entityQuranTimeline);

        void updateDurationOut(float f, EntityQuranTimeline entityQuranTimeline);
    }

    public static synchronized EffectAyaFragment get(Transition transition, Resources resources, ITransition iTransition, EntityQuranTimeline entityQuranTimeline) {
        synchronized (EffectAyaFragment.class) {
            if (instance == null) {
                instance = new EffectAyaFragment(transition, resources, iTransition, entityQuranTimeline);
            }
            return instance;
        }
    }

    public EffectAyaFragment(Transition transition, Resources resources, ITransition iTransition, EntityQuranTimeline entityQuranTimeline) {
        this.resources = resources;
        this.iTransition = iTransition;
        this.transition = transition;
        this.time = (entityQuranTimeline.getRect().width() / entityQuranTimeline.getSecond_in_screen()) * 0.5f;
        this.entityQuranTimeline = entityQuranTimeline;
    }

    public EffectAyaFragment() {
    }

    public void updateView(float f, Transition transition) {
        this.transition = transition;
        if (this.seekBarDuration.getVisibility() != 0) {
            this.seekBarDuration.setVisibility(0);
            this.tvDuration.setVisibility(0);
        }
        this.btn_unEffect.setBackgroundResource(R.drawable.circle_effect);
        updateSeek(f, true);
        visibleApplyAll();
    }

    private void addCustomViewToTab(TabLayout.Tab tab) {
        View inflate = getLayoutInflater().inflate(R.layout.layout_tablayout, (ViewGroup) null);
        TextCustumFont textCustumFont = (TextCustumFont) inflate.findViewById(R.id.name);
        inflate.findViewById(R.id.icon).setVisibility(8);
        textCustumFont.setText(tab.getText().toString());
        tab.setCustomView(inflate);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        ITransition iTransition;
        FragmentEffectAyaBinding inflate = FragmentEffectAyaBinding.inflate(layoutInflater, viewGroup, false);
        this.transitionEntityBinding = inflate;
        LinearLayout root = inflate.getRoot();
        if (this.resources != null && (iTransition = this.iTransition) != null) {
            iTransition.playing(this.entityQuranTimeline);
            TabLayout tabLayout = (TabLayout) root.findViewById(R.id.tab_layout);
            this.tvDuration = (TextCustumFont) root.findViewById(R.id.status_duration);
            TabLayout.Tab newTab = tabLayout.newTab();
            newTab.setText(this.resources.getString(R.string.in_transition));
            tabLayout.addTab(newTab);
            addCustomViewToTab(newTab);
            TabLayout.Tab newTab2 = tabLayout.newTab();
            newTab2.setText(this.resources.getString(R.string.out_transition));
            tabLayout.addTab(newTab2);
            addCustomViewToTab(newTab2);
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() { // from class: hazem.nurmontage.videoquran.fragment.EffectAyaFragment.1
                @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
                public void onTabReselected(TabLayout.Tab tab) {
                }

                @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
                public void onTabUnselected(TabLayout.Tab tab) {
                }

                @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
                public void onTabSelected(TabLayout.Tab tab) {
                    EffectAyaFragment.this.tab_selected = tab.getPosition();
                    EffectAyaFragment.this.loadTransition(tab.getPosition());
                }
            });
            ((TabLayout.Tab) Objects.requireNonNull(tabLayout.getTabAt(0))).select();
            SeekBar seekBar = (SeekBar) root.findViewById(R.id.seekbar);
            this.seekBarDuration = seekBar;
            seekBar.setMax((int) (this.time * 4.0f));
            this.seekBarDuration.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: hazem.nurmontage.videoquran.fragment.EffectAyaFragment.2
                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStartTrackingTouch(SeekBar seekBar2) {
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onProgressChanged(SeekBar seekBar2, int i, boolean z) {
                    EffectAyaFragment.this.tvDuration.setText(String.valueOf(i / 10.0f));
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStopTrackingTouch(SeekBar seekBar2) {
                    if (EffectAyaFragment.this.iTransition != null) {
                        if (EffectAyaFragment.this.tab_selected == 0) {
                            EffectAyaFragment.this.iTransition.updateDurationIn(seekBar2.getProgress() / 4.0f, EffectAyaFragment.this.entityQuranTimeline);
                        } else if (EffectAyaFragment.this.tab_selected == 1) {
                            EffectAyaFragment.this.iTransition.updateDurationOut(seekBar2.getProgress() / 4.0f, EffectAyaFragment.this.entityQuranTimeline);
                        }
                        EffectAyaFragment.this.visibleApplyAll();
                    }
                }
            });
            this.btn_unEffect = (ImageButton) root.findViewById(R.id.btn_unEffect);
            Transition transition = this.transition;
            if (transition != null && transition.isIn()) {
                float min = Math.min(this.transition.getDuration_in(), this.time);
                this.seekBarDuration.setProgress((int) (4.0f * min));
                this.tvDuration.setText(String.valueOf(this.seekBarDuration.getProgress() / 10.0f));
                this.iTransition.updateDurationIn(min, this.entityQuranTimeline);
                this.btn_unEffect.setBackgroundResource(R.drawable.circle_effect);
            } else {
                this.seekBarDuration.setVisibility(8);
                this.tvDuration.setVisibility(8);
                this.btn_unEffect.setBackgroundResource(R.drawable.circle_item_menu_select);
            }
            RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.rv);
            this.recyclerView = recyclerView;
            recyclerView.setHasFixedSize(true);
            this.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), 0, false));
            this.recyclerView.setItemViewCacheSize(20);
            this.recyclerView.setDrawingCacheEnabled(true);
            this.recyclerView.setItemAnimator(null);
            this.recyclerView.setDrawingCacheQuality(1048576);
            this.btn_unEffect.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.EffectAyaFragment.3
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (EffectAyaFragment.this.transitionEntityAdabters.isHaveSelect()) {
                        EffectAyaFragment.this.iTransition.remove(EffectAyaFragment.this.tab_selected, EffectAyaFragment.this.entityQuranTimeline);
                        EffectAyaFragment.this.transitionEntityAdabters.unselect();
                        EffectAyaFragment.this.btn_unEffect.setBackgroundResource(R.drawable.circle_item_menu_select);
                        EffectAyaFragment.this.seekBarDuration.setVisibility(8);
                        EffectAyaFragment.this.tvDuration.setVisibility(8);
                        EffectAyaFragment.this.visibleApplyAll();
                    }
                }
            });
            root.findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.EffectAyaFragment.4
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (EffectAyaFragment.this.iTransition != null) {
                        EffectAyaFragment.this.iTransition.onHideFragment(EffectAyaFragment.this.entityQuranTimeline);
                    }
                }
            });
            this.btnApplyAll = (LinearLayout) root.findViewById(R.id.btn_appl_all);
            this.tv_apply_all = (TextCustumFont) root.findViewById(R.id.tv_apply_all);
            this.iv_apply_all = (ImageView) root.findViewById(R.id.iv_apply_all);
            this.tv_apply_all.setText(this.resources.getString(R.string.applyall));
            this.btnApplyAll.setEnabled(false);
            this.btnApplyAll.setClickable(false);
            this.btnApplyAll.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.EffectAyaFragment.5
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (EffectAyaFragment.this.iTransition != null) {
                        EffectAyaFragment.this.iTransition.applyAll(EffectAyaFragment.this.tab_selected, EffectAyaFragment.this.entityQuranTimeline);
                        EffectAyaFragment.this.invisibleApplyAll();
                    }
                }
            });
            root.post(new Runnable() { // from class: hazem.nurmontage.videoquran.fragment.EffectAyaFragment.6
                @Override // java.lang.Runnable
                public void run() {
                    int i;
                    List<TransitionEntityAdabters.TransitionItem> inTransition = EffectAyaFragment.this.getInTransition();
                    if (EffectAyaFragment.this.transition == null || !EffectAyaFragment.this.transition.isIn()) {
                        i = -1;
                    } else {
                        EffectAyaFragment effectAyaFragment = EffectAyaFragment.this;
                        i = effectAyaFragment.getIndex(inTransition, effectAyaFragment.transition.getType_in());
                    }
                    int i2 = i;
                    EffectAyaFragment.this.transitionEntityAdabters = new TransitionEntityAdabters(BillingPreferences.isSubscribed(EffectAyaFragment.this.getContext()), EffectAyaFragment.this.iTransition, inTransition, i2, EffectAyaFragment.this.entityQuranTimeline);
                    EffectAyaFragment.this.recyclerView.setAdapter(EffectAyaFragment.this.transitionEntityAdabters);
                    EffectAyaFragment effectAyaFragment2 = EffectAyaFragment.this;
                    effectAyaFragment2.scroll(effectAyaFragment2.transitionEntityAdabters.getSelect());
                }
            });
        }
        return root;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void invisibleApplyAll() {
        if (this.btnApplyAll.isEnabled()) {
            this.btnApplyAll.setEnabled(false);
            this.btnApplyAll.setClickable(false);
            this.tv_apply_all.setTextColor(-8355712);
            this.iv_apply_all.setColorFilter(-8355712, PorterDuff.Mode.SRC_IN);
        }
    }

    public void visibleApplyAll() {
        if (this.btnApplyAll.isEnabled()) {
            return;
        }
        this.btnApplyAll.setEnabled(true);
        this.btnApplyAll.setClickable(true);
        this.tv_apply_all.setTextColor(-1);
        this.iv_apply_all.setColorFilter(-1, PorterDuff.Mode.SRC_IN);
    }

    public void scroll(int i) {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) this.recyclerView.getLayoutManager();
        View findViewByPosition = linearLayoutManager.findViewByPosition(i);
        linearLayoutManager.scrollToPositionWithOffset(i, (this.recyclerView.getWidth() - (findViewByPosition != null ? findViewByPosition.getWidth() : 0)) / 2);
    }

    public void updateButton(Transition transition) {
        this.transition = transition;
        this.btn_unEffect.setBackgroundResource(R.drawable.circle_effect);
        visibleSeekbar();
    }

    public int getIndex(List<TransitionEntityAdabters.TransitionItem> list, String str) {
        for (int i = 0; i < list.size(); i++) {
            if (str.equals(list.get(i).getType())) {
                return i;
            }
        }
        return -1;
    }

    private void updateSeek(float f, boolean z) {
        this.seekBarDuration.setProgress((int) (f * 4.0f));
        this.tvDuration.setText(String.valueOf(this.seekBarDuration.getProgress() / 10.0f));
    }

    private void visibleSeekbar() {
        this.seekBarDuration.setVisibility(0);
        this.tvDuration.setVisibility(0);
    }

    private void invisibleSeekbar() {
        this.seekBarDuration.setVisibility(8);
        this.tvDuration.setVisibility(8);
    }

    public void loadTransition(int i) {
        this.index = -1;
        if (i == 0) {
            List<TransitionEntityAdabters.TransitionItem> inTransition = getInTransition();
            Transition transition = this.transition;
            if (transition != null) {
                if (transition.isIn()) {
                    int index = getIndex(inTransition, this.transition.getType_in());
                    this.index = index;
                    if (index != -1) {
                        visibleSeekbar();
                        this.iTransition.updateDurationIn(this.transition.getDuration_in(), this.entityQuranTimeline);
                        this.btn_unEffect.setBackgroundResource(R.drawable.circle_effect);
                    } else {
                        this.entityQuranTimeline.getQuranEntity().endAnimator();
                        invisibleSeekbar();
                        this.btn_unEffect.setBackgroundResource(R.drawable.circle_item_menu_select);
                    }
                } else {
                    this.entityQuranTimeline.getQuranEntity().endAnimator();
                    invisibleSeekbar();
                    this.btn_unEffect.setBackgroundResource(R.drawable.circle_item_menu_select);
                }
            }
            this.transitionEntityAdabters.update(inTransition, "in", this.index);
            scroll(this.index);
            Transition transition2 = this.transition;
            if (transition2 != null) {
                updateSeek(transition2.getDuration_in(), this.transition.isIn());
                return;
            }
            return;
        }
        if (i == 1) {
            List<TransitionEntityAdabters.TransitionItem> outTransition = getOutTransition();
            Transition transition3 = this.transition;
            if (transition3 != null) {
                if (transition3.isOut()) {
                    int index2 = getIndex(outTransition, this.transition.getType_out());
                    this.index = index2;
                    if (index2 != -1) {
                        visibleSeekbar();
                        this.iTransition.updateDurationOut(this.transition.getDuration_out(), this.entityQuranTimeline);
                        this.btn_unEffect.setBackgroundResource(R.drawable.circle_effect);
                    } else {
                        this.entityQuranTimeline.getQuranEntity().endAnimator();
                        invisibleSeekbar();
                        this.btn_unEffect.setBackgroundResource(R.drawable.circle_item_menu_select);
                    }
                } else {
                    this.entityQuranTimeline.getQuranEntity().endAnimator();
                    invisibleSeekbar();
                    this.btn_unEffect.setBackgroundResource(R.drawable.circle_item_menu_select);
                }
            }
            this.transitionEntityAdabters.update(outTransition, "out", this.index);
            scroll(this.index);
            Transition transition4 = this.transition;
            if (transition4 != null) {
                updateSeek(transition4.getDuration_out(), this.transition.isOut());
            }
        }
    }

    public List<TransitionEntityAdabters.TransitionItem> getInTransition() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new TransitionEntityAdabters.TransitionItem(TransitionType.FADE_IN.getValue(), R.drawable.ic_linear_gradient, 0));
        arrayList.add(new TransitionEntityAdabters.TransitionItem(TransitionType.SLIDE_TO_RIGHT.getValue(), R.drawable.ic_btn_back, Angle.LEFT));
        arrayList.add(new TransitionEntityAdabters.TransitionItem(TransitionType.SLIDE_TO_LEFT.getValue(), R.drawable.ic_btn_back, 0));
        return arrayList;
    }

    private List<TransitionEntityAdabters.TransitionItem> getOutTransition() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new TransitionEntityAdabters.TransitionItem(TransitionType.FADE_OUT.getValue(), R.drawable.ic_linear_gradient, 0));
        arrayList.add(new TransitionEntityAdabters.TransitionItem(TransitionType.SLIDE_TO_RIGHT.getValue(), R.drawable.ic_btn_back, Angle.LEFT));
        arrayList.add(new TransitionEntityAdabters.TransitionItem(TransitionType.SLIDE_TO_LEFT.getValue(), R.drawable.ic_btn_back, 0));
        return arrayList;
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        ITransition iTransition = this.iTransition;
        if (iTransition != null) {
            iTransition.destroy(this.entityQuranTimeline);
        }
        FragmentEffectAyaBinding fragmentEffectAyaBinding = this.transitionEntityBinding;
        if (fragmentEffectAyaBinding != null) {
            fragmentEffectAyaBinding.getRoot().removeAllViews();
            this.transitionEntityBinding = null;
        }
        instance = null;
        super.onDestroyView();
    }
}

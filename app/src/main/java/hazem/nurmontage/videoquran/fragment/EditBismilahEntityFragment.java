package hazem.nurmontage.videoquran.fragment;

import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.fragment.app.Fragment;
import hazem.nurmontage.videoquran.R;
import hazem.nurmontage.videoquran.constant.AyaTextPreset;
import hazem.nurmontage.videoquran.databinding.FragmentEditEntityBinding;
import hazem.nurmontage.videoquran.entity_timeline.Entity;
import hazem.nurmontage.videoquran.views.TextCustumFont;

/* loaded from: classes2.dex */
public class EditBismilahEntityFragment extends Fragment {
    public static EditBismilahEntityFragment instance;
    private LinearLayout btnDelete;
    private LinearLayout btn_from_now;
    private LinearLayout btn_until_now;
    private Entity entity_select;
    private FragmentEditEntityBinding fragmentBinding;
    private IBismilahEntityCallback iEditEntityCallback;
    private ImageView iv_from_now;
    private ImageView iv_until_now;
    private float pos_cursur;
    private Resources resources;
    private TextCustumFont tv_from_now;
    private TextCustumFont tv_until_now;

    public interface IBismilahEntityCallback {
        void fromNow();

        void fromTheStart();

        void onAnim();

        void onColor();

        void onDelete();

        void onDone();

        void untilNow();

        void untilTheEnd();

        void update();

        void updateAya(int i);

        void updatePreset(AyaTextPreset ayaTextPreset);
    }

    public static EditBismilahEntityFragment getInstance(IBismilahEntityCallback iBismilahEntityCallback, Resources resources, Entity entity, float f) {
        if (instance == null) {
            instance = new EditBismilahEntityFragment(iBismilahEntityCallback, resources, entity, f);
        }
        return instance;
    }

    public EditBismilahEntityFragment(IBismilahEntityCallback iBismilahEntityCallback, Resources resources, Entity entity, float f) {
        this.iEditEntityCallback = iBismilahEntityCallback;
        this.resources = resources;
        this.entity_select = entity;
        this.pos_cursur = f;
    }

    public EditBismilahEntityFragment() {
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        FragmentEditEntityBinding inflate = FragmentEditEntityBinding.inflate(layoutInflater, viewGroup, false);
        this.fragmentBinding = inflate;
        RelativeLayout root = inflate.getRoot();
        if (this.iEditEntityCallback != null && this.resources != null) {
            root.findViewById(R.id.btn_duplicate).setVisibility(8);
            root.findViewById(R.id.btn_font).setVisibility(8);
            root.findViewById(R.id.btn_icon).setVisibility(8);
            root.findViewById(R.id.btn_edit).setVisibility(8);
            root.findViewById(R.id.btn_show_left).setVisibility(8);
            root.findViewById(R.id.btn_show_right).setVisibility(8);
            root.findViewById(R.id.btn_cut).setVisibility(8);
            this.iv_from_now = (ImageView) root.findViewById(R.id.iv_from_now);
            this.iv_until_now = (ImageView) root.findViewById(R.id.iv_until_now);
            ((TextCustumFont) root.findViewById(R.id.tv_delete)).setText(this.resources.getString(R.string.delete));
            ((TextCustumFont) root.findViewById(R.id.tv_color)).setText(this.resources.getString(R.string.color));
            root.findViewById(R.id.btn_color).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.EditBismilahEntityFragment.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (EditBismilahEntityFragment.this.iEditEntityCallback != null) {
                        EditBismilahEntityFragment.this.iEditEntityCallback.onColor();
                    }
                }
            });
            LinearLayout linearLayout = (LinearLayout) root.findViewById(R.id.btn_delete);
            this.btnDelete = linearLayout;
            linearLayout.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.EditBismilahEntityFragment.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (EditBismilahEntityFragment.this.iEditEntityCallback != null) {
                        EditBismilahEntityFragment.this.iEditEntityCallback.onDelete();
                    }
                }
            });
            TextCustumFont textCustumFont = (TextCustumFont) root.findViewById(R.id.tv_from_now);
            this.tv_from_now = textCustumFont;
            textCustumFont.setText(this.resources.getString(R.string.from_now));
            ((TextCustumFont) root.findViewById(R.id.tv_from_the_start)).setText(this.resources.getString(R.string.from_the_start));
            TextCustumFont textCustumFont2 = (TextCustumFont) root.findViewById(R.id.tv_until_now);
            this.tv_until_now = textCustumFont2;
            textCustumFont2.setText(this.resources.getString(R.string.until_now));
            ((TextCustumFont) root.findViewById(R.id.tv_until_the_end)).setText(this.resources.getString(R.string.until_the_end));
            ((TextCustumFont) root.findViewById(R.id.tv_anim)).setText(this.resources.getString(R.string.animtion));
            LinearLayout linearLayout2 = (LinearLayout) root.findViewById(R.id.btn_from_now);
            this.btn_from_now = linearLayout2;
            linearLayout2.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.EditBismilahEntityFragment.3
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (EditBismilahEntityFragment.this.iEditEntityCallback != null) {
                        EditBismilahEntityFragment.this.iEditEntityCallback.fromNow();
                    }
                }
            });
            root.findViewById(R.id.btn_from_the_start).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.EditBismilahEntityFragment.4
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (EditBismilahEntityFragment.this.iEditEntityCallback != null) {
                        EditBismilahEntityFragment.this.iEditEntityCallback.fromTheStart();
                    }
                }
            });
            LinearLayout linearLayout3 = (LinearLayout) root.findViewById(R.id.btn_until_now);
            this.btn_until_now = linearLayout3;
            linearLayout3.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.EditBismilahEntityFragment.5
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (EditBismilahEntityFragment.this.iEditEntityCallback != null) {
                        EditBismilahEntityFragment.this.iEditEntityCallback.untilNow();
                    }
                }
            });
            root.findViewById(R.id.btn_until_the_end).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.EditBismilahEntityFragment.6
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (EditBismilahEntityFragment.this.iEditEntityCallback != null) {
                        EditBismilahEntityFragment.this.iEditEntityCallback.untilTheEnd();
                    }
                }
            });
            root.findViewById(R.id.btn_anim).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.EditBismilahEntityFragment.7
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (EditBismilahEntityFragment.this.iEditEntityCallback != null) {
                        EditBismilahEntityFragment.this.iEditEntityCallback.onAnim();
                    }
                }
            });
            checkSplitEntity(this.entity_select, this.pos_cursur);
        }
        return root;
    }

    public void checkSplitEntity(Entity entity, float f) {
        if (entity == null) {
            return;
        }
        try {
            if (entity.getRect().right < f) {
                this.tv_from_now.setTextColor(-8355712);
                this.iv_from_now.setColorFilter(-8355712, PorterDuff.Mode.SRC_IN);
                this.btn_from_now.setClickable(false);
            } else {
                this.btn_from_now.setClickable(true);
                this.tv_from_now.setTextColor(-1);
                this.iv_from_now.setColorFilter(-1, PorterDuff.Mode.SRC_IN);
            }
            if (entity.getRect().left > f) {
                this.tv_until_now.setTextColor(-8355712);
                this.iv_until_now.setColorFilter(-8355712, PorterDuff.Mode.SRC_IN);
                this.btn_until_now.setClickable(false);
            } else {
                this.btn_until_now.setClickable(true);
                this.tv_until_now.setTextColor(-1);
                this.iv_until_now.setColorFilter(-1, PorterDuff.Mode.SRC_IN);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        this.fragmentBinding = null;
        instance = null;
        this.iEditEntityCallback = null;
        super.onDestroyView();
    }
}

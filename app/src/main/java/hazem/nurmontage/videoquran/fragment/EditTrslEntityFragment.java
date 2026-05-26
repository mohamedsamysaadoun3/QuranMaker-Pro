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
public class EditTrslEntityFragment extends Fragment {
    public static EditTrslEntityFragment instance;
    private LinearLayout btnCut;
    private LinearLayout btn_from_now;
    private LinearLayout btn_until_now;
    private Entity entity_select;
    private FragmentEditEntityBinding fragmentBinding;
    private IEditEntityCallback iEditEntityCallback;
    private ImageView iv_cut;
    private ImageView iv_from_now;
    private ImageView iv_until_now;
    private float pos_cursur;
    private Resources resources;
    private TextCustumFont tv_cut;
    private TextCustumFont tv_from_now;
    private TextCustumFont tv_until_now;

    public interface IEditEntityCallback {
        void fromNow();

        void fromTheStart();

        void onAnim();

        void onColor();

        void onCut();

        void onDelete();

        void onDone();

        void onDuplicate();

        void onEdit();

        void onFont();

        void onIcon();

        void untilNow();

        void untilTheEnd();

        void updateAya(int i);

        void updatePreset(AyaTextPreset ayaTextPreset);

        void updateTrsl(int i);
    }

    public static EditTrslEntityFragment getInstance(IEditEntityCallback iEditEntityCallback, Resources resources, Entity entity, float f) {
        if (instance == null) {
            instance = new EditTrslEntityFragment(iEditEntityCallback, resources, entity, f);
        }
        return instance;
    }

    public EditTrslEntityFragment(IEditEntityCallback iEditEntityCallback, Resources resources, Entity entity, float f) {
        this.iEditEntityCallback = iEditEntityCallback;
        this.resources = resources;
        this.entity_select = entity;
        this.pos_cursur = f;
    }

    public EditTrslEntityFragment() {
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
            this.iv_cut = (ImageView) root.findViewById(R.id.iv_cut);
            this.iv_from_now = (ImageView) root.findViewById(R.id.iv_from_now);
            this.iv_until_now = (ImageView) root.findViewById(R.id.iv_until_now);
            ((TextCustumFont) root.findViewById(R.id.tv_delete)).setText(this.resources.getString(R.string.delete));
            TextCustumFont textCustumFont = (TextCustumFont) root.findViewById(R.id.tv_cut);
            this.tv_cut = textCustumFont;
            textCustumFont.setText(this.resources.getString(R.string.cut));
            ((TextCustumFont) root.findViewById(R.id.tv_edit)).setText(this.resources.getString(R.string.edit));
            ((TextCustumFont) root.findViewById(R.id.tv_color)).setText(this.resources.getString(R.string.color));
            root.findViewById(R.id.btn_color).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.EditTrslEntityFragment.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (EditTrslEntityFragment.this.iEditEntityCallback != null) {
                        EditTrslEntityFragment.this.iEditEntityCallback.onColor();
                    }
                }
            });
            root.findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.EditTrslEntityFragment.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (EditTrslEntityFragment.this.iEditEntityCallback != null) {
                        EditTrslEntityFragment.this.iEditEntityCallback.onDelete();
                    }
                }
            });
            LinearLayout linearLayout = (LinearLayout) root.findViewById(R.id.btn_cut);
            this.btnCut = linearLayout;
            linearLayout.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.EditTrslEntityFragment.3
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (EditTrslEntityFragment.this.iEditEntityCallback != null) {
                        EditTrslEntityFragment.this.iEditEntityCallback.onCut();
                    }
                }
            });
            root.findViewById(R.id.btn_edit).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.EditTrslEntityFragment.4
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (EditTrslEntityFragment.this.iEditEntityCallback != null) {
                        EditTrslEntityFragment.this.iEditEntityCallback.onEdit();
                    }
                }
            });
            TextCustumFont textCustumFont2 = (TextCustumFont) root.findViewById(R.id.tv_from_now);
            this.tv_from_now = textCustumFont2;
            textCustumFont2.setText(this.resources.getString(R.string.from_now));
            ((TextCustumFont) root.findViewById(R.id.tv_from_the_start)).setText(this.resources.getString(R.string.from_the_start));
            TextCustumFont textCustumFont3 = (TextCustumFont) root.findViewById(R.id.tv_until_now);
            this.tv_until_now = textCustumFont3;
            textCustumFont3.setText(this.resources.getString(R.string.until_now));
            ((TextCustumFont) root.findViewById(R.id.tv_until_the_end)).setText(this.resources.getString(R.string.until_the_end));
            ((TextCustumFont) root.findViewById(R.id.tv_duplicate)).setText(this.resources.getString(R.string.duplicate));
            LinearLayout linearLayout2 = (LinearLayout) root.findViewById(R.id.btn_from_now);
            this.btn_from_now = linearLayout2;
            linearLayout2.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.EditTrslEntityFragment.5
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (EditTrslEntityFragment.this.iEditEntityCallback != null) {
                        EditTrslEntityFragment.this.iEditEntityCallback.fromNow();
                    }
                }
            });
            root.findViewById(R.id.btn_from_the_start).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.EditTrslEntityFragment.6
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (EditTrslEntityFragment.this.iEditEntityCallback != null) {
                        EditTrslEntityFragment.this.iEditEntityCallback.fromTheStart();
                    }
                }
            });
            LinearLayout linearLayout3 = (LinearLayout) root.findViewById(R.id.btn_until_now);
            this.btn_until_now = linearLayout3;
            linearLayout3.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.EditTrslEntityFragment.7
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (EditTrslEntityFragment.this.iEditEntityCallback != null) {
                        EditTrslEntityFragment.this.iEditEntityCallback.untilNow();
                    }
                }
            });
            root.findViewById(R.id.btn_until_the_end).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.EditTrslEntityFragment.8
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (EditTrslEntityFragment.this.iEditEntityCallback != null) {
                        EditTrslEntityFragment.this.iEditEntityCallback.untilTheEnd();
                    }
                }
            });
            root.findViewById(R.id.btn_duplicate).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.EditTrslEntityFragment.9
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (EditTrslEntityFragment.this.iEditEntityCallback != null) {
                        EditTrslEntityFragment.this.iEditEntityCallback.onDuplicate();
                    }
                }
            });
            root.findViewById(R.id.btn_font).setVisibility(8);
            root.findViewById(R.id.btn_icon).setVisibility(8);
            root.findViewById(R.id.btn_anim).setVisibility(8);
            ImageView imageView = (ImageView) root.findViewById(R.id.btn_show_left);
            ImageView imageView2 = (ImageView) root.findViewById(R.id.btn_show_right);
            imageView.setVisibility(8);
            imageView2.setVisibility(8);
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
            if (entity.getRect().left <= f && entity.getRect().right >= f) {
                this.btnCut.setClickable(true);
                this.tv_cut.setTextColor(-1);
                this.iv_cut.setColorFilter(-1, PorterDuff.Mode.SRC_IN);
                return;
            }
            this.tv_cut.setTextColor(-8355712);
            this.iv_cut.setColorFilter(-8355712, PorterDuff.Mode.SRC_IN);
            this.btnCut.setClickable(false);
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

package hazem.nurmontage.videoquran.fragment;

import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.fragment.app.Fragment;
import hazem.nurmontage.videoquran.R;
import hazem.nurmontage.videoquran.constant.AyaTextPreset;
import hazem.nurmontage.videoquran.databinding.FragmentEditEntityBinding;
import hazem.nurmontage.videoquran.entity_timeline.Entity;
import hazem.nurmontage.videoquran.model.QuranEntity;
import hazem.nurmontage.videoquran.views.TextCustumFont;

/* loaded from: classes2.dex */
public class EditEntityFragment extends Fragment {
    public static EditEntityFragment instance;
    private LinearLayout btnCut;
    private LinearLayout btn_from_now;
    private LinearLayout btn_icon;
    private LinearLayout btn_until_now;
    private Entity entity_select;
    private FragmentEditEntityBinding fragmentBinding;
    private IEditEntityCallback iEditEntityCallback;
    private ImageView iv_cut;
    private ImageView iv_from_now;
    private ImageView iv_icon;
    private ImageView iv_until_now;
    private float pos_cursur;
    private Resources resources;
    private TextCustumFont tv_cut;
    private TextCustumFont tv_from_now;
    private TextCustumFont tv_icon;
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

    public static EditEntityFragment getInstance(IEditEntityCallback iEditEntityCallback, Resources resources, Entity entity, float f) {
        if (instance == null) {
            instance = new EditEntityFragment(iEditEntityCallback, resources, entity, f);
        }
        return instance;
    }

    public EditEntityFragment(IEditEntityCallback iEditEntityCallback, Resources resources, Entity entity, float f) {
        this.iEditEntityCallback = iEditEntityCallback;
        this.resources = resources;
        this.entity_select = entity;
        this.pos_cursur = f;
    }

    public EditEntityFragment() {
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
            root.findViewById(R.id.btn_color).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.EditEntityFragment.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (EditEntityFragment.this.iEditEntityCallback != null) {
                        EditEntityFragment.this.iEditEntityCallback.onColor();
                    }
                }
            });
            root.findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.EditEntityFragment.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (EditEntityFragment.this.iEditEntityCallback != null) {
                        EditEntityFragment.this.iEditEntityCallback.onDelete();
                    }
                }
            });
            LinearLayout linearLayout = (LinearLayout) root.findViewById(R.id.btn_cut);
            this.btnCut = linearLayout;
            linearLayout.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.EditEntityFragment.3
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (EditEntityFragment.this.iEditEntityCallback != null) {
                        EditEntityFragment.this.iEditEntityCallback.onCut();
                    }
                }
            });
            root.findViewById(R.id.btn_edit).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.EditEntityFragment.4
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (EditEntityFragment.this.iEditEntityCallback != null) {
                        EditEntityFragment.this.iEditEntityCallback.onEdit();
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
            ((TextCustumFont) root.findViewById(R.id.tv_font)).setText(this.resources.getString(R.string.font));
            ((TextCustumFont) root.findViewById(R.id.tv_anim)).setText(this.resources.getString(R.string.animtion));
            LinearLayout linearLayout2 = (LinearLayout) root.findViewById(R.id.btn_from_now);
            this.btn_from_now = linearLayout2;
            linearLayout2.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.EditEntityFragment.5
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (EditEntityFragment.this.iEditEntityCallback != null) {
                        EditEntityFragment.this.iEditEntityCallback.fromNow();
                    }
                }
            });
            root.findViewById(R.id.btn_from_the_start).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.EditEntityFragment.6
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (EditEntityFragment.this.iEditEntityCallback != null) {
                        EditEntityFragment.this.iEditEntityCallback.fromTheStart();
                    }
                }
            });
            LinearLayout linearLayout3 = (LinearLayout) root.findViewById(R.id.btn_until_now);
            this.btn_until_now = linearLayout3;
            linearLayout3.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.EditEntityFragment.7
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (EditEntityFragment.this.iEditEntityCallback != null) {
                        EditEntityFragment.this.iEditEntityCallback.untilNow();
                    }
                }
            });
            root.findViewById(R.id.btn_until_the_end).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.EditEntityFragment.8
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (EditEntityFragment.this.iEditEntityCallback != null) {
                        EditEntityFragment.this.iEditEntityCallback.untilTheEnd();
                    }
                }
            });
            root.findViewById(R.id.btn_duplicate).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.EditEntityFragment.9
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (EditEntityFragment.this.iEditEntityCallback != null) {
                        EditEntityFragment.this.iEditEntityCallback.onDuplicate();
                    }
                }
            });
            root.findViewById(R.id.btn_font).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.EditEntityFragment.10
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (EditEntityFragment.this.iEditEntityCallback != null) {
                        EditEntityFragment.this.iEditEntityCallback.onFont();
                    }
                }
            });
            TextCustumFont textCustumFont4 = (TextCustumFont) root.findViewById(R.id.tv_icon);
            this.tv_icon = textCustumFont4;
            textCustumFont4.setText(this.resources.getString(R.string.icon));
            this.iv_icon = (ImageView) root.findViewById(R.id.iv_icon);
            LinearLayout linearLayout4 = (LinearLayout) root.findViewById(R.id.btn_icon);
            this.btn_icon = linearLayout4;
            linearLayout4.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.EditEntityFragment.11
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (EditEntityFragment.this.iEditEntityCallback != null) {
                        EditEntityFragment.this.iEditEntityCallback.onIcon();
                    }
                }
            });
            checkIcon(this.entity_select);
            root.findViewById(R.id.btn_anim).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.EditEntityFragment.12
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (EditEntityFragment.this.iEditEntityCallback != null) {
                        EditEntityFragment.this.iEditEntityCallback.onAnim();
                    }
                }
            });
            final ImageView imageView = (ImageView) root.findViewById(R.id.btn_show_left);
            final ImageView imageView2 = (ImageView) root.findViewById(R.id.btn_show_right);
            ((HorizontalScrollView) root.findViewById(R.id.scroll_menu)).setOnScrollChangeListener(new View.OnScrollChangeListener() { // from class: hazem.nurmontage.videoquran.fragment.EditEntityFragment.13
                @Override // android.view.View.OnScrollChangeListener
                public void onScrollChange(View view, int i, int i2, int i3, int i4) {
                    try {
                        if (i > EditEntityFragment.this.btnCut.getWidth() * 0.3f) {
                            imageView2.setVisibility(8);
                            imageView.setVisibility(0);
                        } else {
                            imageView.setVisibility(8);
                            imageView2.setVisibility(0);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
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

    public void checkIcon(Entity entity) {
        try {
            if (entity.getEntityView() instanceof QuranEntity) {
                if (((QuranEntity) entity.getEntityView()).getNumber() == -1) {
                    this.tv_icon.setTextColor(-8355712);
                    this.iv_icon.setColorFilter(-8355712, PorterDuff.Mode.SRC_IN);
                    this.btn_icon.setClickable(false);
                } else {
                    this.btn_icon.setClickable(true);
                    this.tv_icon.setTextColor(-1);
                    this.iv_icon.setColorFilter(-1, PorterDuff.Mode.SRC_IN);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

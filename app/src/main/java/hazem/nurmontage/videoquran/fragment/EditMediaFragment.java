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
import hazem.nurmontage.videoquran.Utils.MyPrefereces;
import hazem.nurmontage.videoquran.constant.EffectAudioType;
import hazem.nurmontage.videoquran.databinding.FragmentEditMediaBinding;
import hazem.nurmontage.videoquran.entity_timeline.EntityAudio;
import hazem.nurmontage.videoquran.model.EffectAudio;
import hazem.nurmontage.videoquran.views.TextCustumFont;

/* loaded from: classes2.dex */
public class EditMediaFragment extends Fragment {
    public static EditMediaFragment instance;
    private LinearLayout btn_cut;
    private LinearLayout btn_echo;
    private LinearLayout btn_enhance_voice;
    private LinearLayout btn_fade;
    private LinearLayout btn_remove_noice;
    private LinearLayout btn_reverb;
    private LinearLayout btn_speed;
    private LinearLayout btn_volume;
    private EntityAudio entity_select;
    private FragmentEditMediaBinding fragmentBinding;
    private IEditMediaCallback iEditMediaCallback;
    private ImageView iv_cut;
    private float pos_cursur;
    private Resources resources;
    private TextCustumFont tv_cut;

    public interface IEditMediaCallback {
        void echoEffect();

        void enhanceVoice();

        void fadeffect();

        void noice();

        void onCmd(String str);

        void onCmdAll(EffectAudio effectAudio);

        void onCmdPlay(String str);

        void onCut();

        void onDelete();

        void onDone();

        void onDuplicate();

        void onReplace();

        void pausePreview();

        void pitchffect();

        void reverbEffect();

        void speedffect();

        void startPreview();

        void updateEntity(EffectAudioType effectAudioType, EntityAudio entityAudio);

        void volumeEffect();
    }

    public static EditMediaFragment getInstance(IEditMediaCallback iEditMediaCallback, Resources resources, EntityAudio entityAudio, float f) {
        if (instance == null) {
            instance = new EditMediaFragment(iEditMediaCallback, resources, entityAudio, f);
        }
        return instance;
    }

    public EditMediaFragment(IEditMediaCallback iEditMediaCallback, Resources resources, EntityAudio entityAudio, float f) {
        this.iEditMediaCallback = iEditMediaCallback;
        this.resources = resources;
        this.entity_select = entityAudio;
        this.pos_cursur = f;
    }

    public EditMediaFragment() {
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        FragmentEditMediaBinding inflate = FragmentEditMediaBinding.inflate(layoutInflater, viewGroup, false);
        this.fragmentBinding = inflate;
        RelativeLayout root = inflate.getRoot();
        if (this.iEditMediaCallback != null && this.resources != null && this.entity_select != null) {
            this.iv_cut = (ImageView) root.findViewById(R.id.iv_cut);
            final HorizontalScrollView horizontalScrollView = (HorizontalScrollView) root.findViewById(R.id.view_scroll);
            this.btn_cut = (LinearLayout) root.findViewById(R.id.btn_cut);
            final int scrollX = MyPrefereces.getScrollX(getContext());
            if (scrollX != 0) {
                MyPrefereces.putScrollX(getContext(), 0);
                horizontalScrollView.post(new Runnable() { // from class: hazem.nurmontage.videoquran.fragment.EditMediaFragment.1
                    @Override // java.lang.Runnable
                    public void run() {
                        horizontalScrollView.scrollTo(scrollX, 0);
                    }
                });
            }
            final ImageView imageView = (ImageView) root.findViewById(R.id.btn_show_left);
            final ImageView imageView2 = (ImageView) root.findViewById(R.id.btn_show_right);
            horizontalScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() { // from class: hazem.nurmontage.videoquran.fragment.EditMediaFragment.2
                @Override // android.view.View.OnScrollChangeListener
                public void onScrollChange(View view, int i, int i2, int i3, int i4) {
                    try {
                        if (i > EditMediaFragment.this.btn_cut.getWidth() * 0.3f) {
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
            ((TextCustumFont) root.findViewById(R.id.tv_enhance)).setText(this.resources.getString(R.string.enhance));
            ((TextCustumFont) root.findViewById(R.id.tv_delete)).setText(this.resources.getString(R.string.delete));
            ((TextCustumFont) root.findViewById(R.id.tv_duplicate)).setText(this.resources.getString(R.string.duplicate));
            ((TextCustumFont) root.findViewById(R.id.tv_volume)).setText(this.resources.getString(R.string.volume));
            ((TextCustumFont) root.findViewById(R.id.tv_reverbe)).setText(this.resources.getString(R.string.reverb));
            ((TextCustumFont) root.findViewById(R.id.tv_echo)).setText(this.resources.getString(R.string.echo));
            ((TextCustumFont) root.findViewById(R.id.tv_fade)).setText(this.resources.getString(R.string.fade));
            ((TextCustumFont) root.findViewById(R.id.tv_noice)).setText(this.resources.getString(R.string.noice));
            ((TextCustumFont) root.findViewById(R.id.tv_speed)).setText(this.resources.getString(R.string.speed));
            TextCustumFont textCustumFont = (TextCustumFont) root.findViewById(R.id.tv_cut);
            this.tv_cut = textCustumFont;
            textCustumFont.setText(this.resources.getString(R.string.cut));
            root.findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.EditMediaFragment.3
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (EditMediaFragment.this.iEditMediaCallback != null) {
                        EditMediaFragment.this.iEditMediaCallback.onDelete();
                    }
                }
            });
            root.findViewById(R.id.btn_duplicate).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.EditMediaFragment.4
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (EditMediaFragment.this.iEditMediaCallback != null) {
                        EditMediaFragment.this.iEditMediaCallback.onDuplicate();
                    }
                }
            });
            LinearLayout linearLayout = (LinearLayout) root.findViewById(R.id.btn_reverb);
            this.btn_reverb = linearLayout;
            linearLayout.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.EditMediaFragment.5
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (EditMediaFragment.this.iEditMediaCallback != null) {
                        MyPrefereces.putScrollX(EditMediaFragment.this.getContext(), horizontalScrollView.getScrollX());
                        EditMediaFragment.this.iEditMediaCallback.reverbEffect();
                    }
                }
            });
            LinearLayout linearLayout2 = (LinearLayout) root.findViewById(R.id.btn_enhance_voice);
            this.btn_enhance_voice = linearLayout2;
            linearLayout2.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.EditMediaFragment.6
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (EditMediaFragment.this.iEditMediaCallback != null) {
                        MyPrefereces.putScrollX(EditMediaFragment.this.getContext(), horizontalScrollView.getScrollX());
                        EditMediaFragment.this.iEditMediaCallback.enhanceVoice();
                    }
                }
            });
            LinearLayout linearLayout3 = (LinearLayout) root.findViewById(R.id.btn_remove_noice);
            this.btn_remove_noice = linearLayout3;
            linearLayout3.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.EditMediaFragment.7
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (EditMediaFragment.this.iEditMediaCallback != null) {
                        MyPrefereces.putScrollX(EditMediaFragment.this.getContext(), horizontalScrollView.getScrollX());
                        EditMediaFragment.this.iEditMediaCallback.noice();
                    }
                }
            });
            LinearLayout linearLayout4 = (LinearLayout) root.findViewById(R.id.btn_echo);
            this.btn_echo = linearLayout4;
            linearLayout4.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.EditMediaFragment.8
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (EditMediaFragment.this.iEditMediaCallback != null) {
                        MyPrefereces.putScrollX(EditMediaFragment.this.getContext(), horizontalScrollView.getScrollX());
                        EditMediaFragment.this.iEditMediaCallback.echoEffect();
                    }
                }
            });
            LinearLayout linearLayout5 = (LinearLayout) root.findViewById(R.id.btn_volume);
            this.btn_volume = linearLayout5;
            linearLayout5.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.EditMediaFragment.9
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (EditMediaFragment.this.iEditMediaCallback != null) {
                        MyPrefereces.putScrollX(EditMediaFragment.this.getContext(), horizontalScrollView.getScrollX());
                        EditMediaFragment.this.iEditMediaCallback.volumeEffect();
                    }
                }
            });
            LinearLayout linearLayout6 = (LinearLayout) root.findViewById(R.id.btn_fade);
            this.btn_fade = linearLayout6;
            linearLayout6.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.EditMediaFragment.10
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (EditMediaFragment.this.iEditMediaCallback != null) {
                        MyPrefereces.putScrollX(EditMediaFragment.this.getContext(), horizontalScrollView.getScrollX());
                        EditMediaFragment.this.iEditMediaCallback.fadeffect();
                    }
                }
            });
            LinearLayout linearLayout7 = (LinearLayout) root.findViewById(R.id.btn_speed);
            this.btn_speed = linearLayout7;
            linearLayout7.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.EditMediaFragment.11
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (EditMediaFragment.this.iEditMediaCallback != null) {
                        MyPrefereces.putScrollX(EditMediaFragment.this.getContext(), horizontalScrollView.getScrollX());
                        EditMediaFragment.this.iEditMediaCallback.speedffect();
                    }
                }
            });
            this.btn_cut.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.EditMediaFragment.12
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (EditMediaFragment.this.iEditMediaCallback != null) {
                        EditMediaFragment.this.iEditMediaCallback.onCut();
                    }
                }
            });
            updateBtn();
            initCheckSplit(this.entity_select, this.pos_cursur);
        }
        return root;
    }

    public void updateBtn() {
        try {
            if (this.entity_select.getEffectAudio().getReverbPreset() != null) {
                this.btn_reverb.setBackgroundResource(R.drawable.bg_item_effect);
            } else {
                this.btn_reverb.setBackgroundColor(0);
            }
            if (this.entity_select.getEffectAudio().isEnhance()) {
                this.btn_enhance_voice.setBackgroundResource(R.drawable.bg_item_effect);
            } else {
                this.btn_enhance_voice.setBackgroundColor(0);
            }
            if (this.entity_select.getEffectAudio().isRemoveNoice()) {
                this.btn_remove_noice.setBackgroundResource(R.drawable.bg_item_effect);
            } else {
                this.btn_remove_noice.setBackgroundColor(0);
            }
            if (this.entity_select.getEffectAudio().getDecays() != 0 && this.entity_select.getEffectAudio().getDelays() != 0 && this.entity_select.getEffectAudio().getVolume_echo() != 0) {
                this.btn_echo.setBackgroundResource(R.drawable.bg_item_effect);
            } else {
                this.btn_echo.setBackgroundColor(0);
            }
            if (this.entity_select.getEffectAudio().getVolume() != 1.0f) {
                this.btn_volume.setBackgroundResource(R.drawable.bg_item_effect);
            } else {
                this.btn_volume.setBackgroundColor(0);
            }
            if (this.entity_select.getEffectAudio().getSpeed() != 1.0f) {
                this.btn_speed.setBackgroundResource(R.drawable.bg_item_effect);
            } else {
                this.btn_speed.setBackgroundColor(0);
            }
            if (this.entity_select.getEffectAudio().getFade_in() > 0 && this.entity_select.getEffectAudio().getFade_out() > 0) {
                this.btn_fade.setBackgroundResource(R.drawable.bg_item_effect);
            } else {
                this.btn_fade.setBackgroundColor(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initCheckSplit(EntityAudio entityAudio, float f) {
        try {
            if (entityAudio.getRect().left <= f && entityAudio.getRect().right >= f) {
                this.btn_cut.setClickable(true);
                this.tv_cut.setTextColor(-1);
                this.iv_cut.setColorFilter(-1, PorterDuff.Mode.SRC_IN);
            }
            this.tv_cut.setTextColor(-8355712);
            this.iv_cut.setColorFilter(-8355712, PorterDuff.Mode.SRC_IN);
            this.btn_cut.setClickable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkSplit(EntityAudio entityAudio, float f) {
        if (entityAudio == null) {
            return;
        }
        this.entity_select = entityAudio;
        updateBtn();
        try {
            if (entityAudio.getRect().left <= f && entityAudio.getRect().right >= f) {
                this.btn_cut.setClickable(true);
                this.tv_cut.setTextColor(-1);
                this.iv_cut.setColorFilter(-1, PorterDuff.Mode.SRC_IN);
            }
            this.tv_cut.setTextColor(-8355712);
            this.iv_cut.setColorFilter(-8355712, PorterDuff.Mode.SRC_IN);
            this.btn_cut.setClickable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        this.fragmentBinding = null;
        instance = null;
        this.iEditMediaCallback = null;
        super.onDestroyView();
    }
}

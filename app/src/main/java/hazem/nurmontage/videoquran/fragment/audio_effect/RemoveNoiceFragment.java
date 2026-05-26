package hazem.nurmontage.videoquran.fragment.audio_effect;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import hazem.nurmontage.videoquran.R;
import hazem.nurmontage.videoquran.common.Common;
import hazem.nurmontage.videoquran.constant.EffectAudioType;
import hazem.nurmontage.videoquran.databinding.FragmentRemoveNoiceBinding;
import hazem.nurmontage.videoquran.entity_timeline.EntityAudio;
import hazem.nurmontage.videoquran.fragment.EditMediaFragment;
import hazem.nurmontage.videoquran.model.EffectAudio;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* loaded from: classes2.dex */
public class RemoveNoiceFragment extends Fragment {
    public static RemoveNoiceFragment instance;
    private FragmentRemoveNoiceBinding binding;
    private ImageButton btnPreview;
    private SwitchCompat btn_remove_noice;
    private EntityAudio entityAudio;
    private EditMediaFragment.IEditMediaCallback iEditMediaCallback;
    private boolean isPlay;

    public static RemoveNoiceFragment getInstance(EditMediaFragment.IEditMediaCallback iEditMediaCallback, EntityAudio entityAudio) {
        if (instance == null) {
            instance = new RemoveNoiceFragment(iEditMediaCallback, entityAudio);
        }
        return instance;
    }

    public RemoveNoiceFragment(EditMediaFragment.IEditMediaCallback iEditMediaCallback, EntityAudio entityAudio) {
        this.iEditMediaCallback = iEditMediaCallback;
        this.entityAudio = entityAudio;
    }

    public RemoveNoiceFragment() {
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        FragmentRemoveNoiceBinding inflate = FragmentRemoveNoiceBinding.inflate(layoutInflater, viewGroup, false);
        this.binding = inflate;
        LinearLayout root = inflate.getRoot();
        if (this.iEditMediaCallback != null && this.entityAudio != null) {
            SwitchCompat switchCompat = (SwitchCompat) root.findViewById(R.id.btn_remove_noice);
            this.btn_remove_noice = switchCompat;
            switchCompat.setChecked(this.entityAudio.getEffectAudio().isRemoveNoice());
            this.btn_remove_noice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: hazem.nurmontage.videoquran.fragment.audio_effect.RemoveNoiceFragment.1
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    RemoveNoiceFragment.this.apply(false);
                }
            });
            root.findViewById(R.id.btn_appl_all).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.audio_effect.RemoveNoiceFragment$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    RemoveNoiceFragment.this.m1185xf2db8e35(view);
                }
            });
            root.findViewById(R.id.btn_done).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.audio_effect.RemoveNoiceFragment$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    RemoveNoiceFragment.this.m1186xd6074176(view);
                }
            });
            ImageButton imageButton = (ImageButton) root.findViewById(R.id.btn_play);
            this.btnPreview = imageButton;
            imageButton.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.audio_effect.RemoveNoiceFragment$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    RemoveNoiceFragment.this.m1187xb932f4b7(view);
                }
            });
        }
        return root;
    }

    /* renamed from: lambda$onCreateView$0$hazem-nurmontage-videoquran-fragment-audio_effect-RemoveNoiceFragment, reason: not valid java name */
    /* synthetic */ void m1185xf2db8e35(View view) {
        apply(true);
    }

    /* renamed from: lambda$onCreateView$1$hazem-nurmontage-videoquran-fragment-audio_effect-RemoveNoiceFragment, reason: not valid java name */
    /* synthetic */ void m1186xd6074176(View view) {
        onDone();
    }

    /* renamed from: lambda$onCreateView$2$hazem-nurmontage-videoquran-fragment-audio_effect-RemoveNoiceFragment, reason: not valid java name */
    /* synthetic */ void m1187xb932f4b7(View view) {
        preview();
    }

    public void updateButton() {
        this.btnPreview.setImageResource(R.drawable.play_arrow_24px);
        this.isPlay = false;
    }

    private void preview() {
        boolean z = this.isPlay;
        this.isPlay = !z;
        EditMediaFragment.IEditMediaCallback iEditMediaCallback = this.iEditMediaCallback;
        if (iEditMediaCallback != null) {
            if (!z) {
                iEditMediaCallback.startPreview();
                this.btnPreview.setImageResource(R.drawable.pause_24px);
            } else {
                iEditMediaCallback.pausePreview();
                this.btnPreview.setImageResource(R.drawable.play_arrow_24px);
            }
        }
    }

    private void onDone() {
        EditMediaFragment.IEditMediaCallback iEditMediaCallback = this.iEditMediaCallback;
        if (iEditMediaCallback != null) {
            iEditMediaCallback.onDone();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void apply(boolean z) {
        EffectAudio effectAudio = this.entityAudio.getEffectAudio();
        effectAudio.setRemoveNoice(this.btn_remove_noice.isChecked());
        float start = effectAudio.getStart() / 1000.0f;
        float end = effectAudio.getEnd() / 1000.0f;
        ArrayList arrayList = new ArrayList();
        arrayList.add(String.format(Locale.US, "atrim=start=%.2f:end=%.2f", Float.valueOf(start), Float.valueOf(end)));
        arrayList.add("asetpts=N/SR/TB");
        if (effectAudio.isRemoveNoice()) {
            arrayList.add("afftdn=nf=-25");
        }
        arrayList.add(String.format(Locale.US, "volume=%.2f", Float.valueOf(effectAudio.getVolume())));
        if (effectAudio.getFade_in() > 0) {
            arrayList.add("afade=t=in:st=0:d=" + effectAudio.getFade_in());
        }
        if (effectAudio.getFade_out() > 0) {
            float fade_out = effectAudio.getFade_out();
            arrayList.add("afade=t=out:st=" + ((end - start) - fade_out) + ":d=" + fade_out);
        }
        if (effectAudio.isEnhance()) {
            arrayList.add(Common.ENHANCE_CMD);
        }
        if (effectAudio.getReverbPreset() != null) {
            arrayList.add(effectAudio.getReverbPreset());
        }
        if (effectAudio.getDecays() > 0) {
            arrayList.add(String.format(Locale.US, "aecho=%.2f:%.2f:%s:%s", Float.valueOf(1.0f), Float.valueOf(effectAudio.getOutGain()), effectAudio.getDelays_cmd(), effectAudio.getDecays_cmd()));
        }
        if (effectAudio.getSpeed() != 1.0f) {
            arrayList.addAll(buildSpeedFilters(effectAudio.getSpeed()));
        }
        EditMediaFragment.IEditMediaCallback iEditMediaCallback = this.iEditMediaCallback;
        if (iEditMediaCallback != null) {
            if (z) {
                iEditMediaCallback.updateEntity(EffectAudioType.NOICE, this.entityAudio);
                this.iEditMediaCallback.onCmdAll(effectAudio);
            } else {
                this.iEditMediaCallback.onCmd(TextUtils.join(",", arrayList));
            }
        }
    }

    private List<String> buildSpeedFilters(float f) {
        ArrayList arrayList = new ArrayList();
        if (f < 0.5f) {
            while (f < 0.5f) {
                arrayList.add("atempo=0.5");
                f /= 0.5f;
            }
            arrayList.add(String.format(Locale.US, "atempo=%.2f", Float.valueOf(f)));
        } else if (f > 2.0f) {
            while (f > 2.0f) {
                arrayList.add("atempo=2.0");
                f /= 2.0f;
            }
            arrayList.add(String.format(Locale.US, "atempo=%.2f", Float.valueOf(f)));
        } else {
            arrayList.add(String.format(Locale.US, "atempo=%.2f", Float.valueOf(f)));
        }
        return arrayList;
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        instance = null;
        this.binding = null;
    }
}

package hazem.nurmontage.videoquran.fragment.audio_effect;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import androidx.fragment.app.Fragment;
import hazem.nurmontage.videoquran.R;
import hazem.nurmontage.videoquran.common.Common;
import hazem.nurmontage.videoquran.constant.EffectAudioType;
import hazem.nurmontage.videoquran.databinding.FragmentEchoEffectBinding;
import hazem.nurmontage.videoquran.entity_timeline.EntityAudio;
import hazem.nurmontage.videoquran.fragment.EditMediaFragment;
import hazem.nurmontage.videoquran.model.EffectAudio;
import hazem.nurmontage.videoquran.views.TextCustumFont;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* loaded from: classes2.dex */
public class EchoEffectFragment extends Fragment {
    public static EchoEffectFragment instance;
    private FragmentEchoEffectBinding binding;
    private ImageButton btnPreview;
    private SeekBar delaySeekBar;
    private EntityAudio entityAudio;
    private EditMediaFragment.IEditMediaCallback iEchoCallback;
    private boolean isPlay;
    private SeekBar repeatSeekBar;
    private TextCustumFont tv_hint_delay;
    private TextCustumFont tv_hint_repeat;
    private TextCustumFont tv_hint_volume;
    private SeekBar volumeSeekBar;

    public static EchoEffectFragment getInstance(EditMediaFragment.IEditMediaCallback iEditMediaCallback, EntityAudio entityAudio) {
        if (instance == null) {
            instance = new EchoEffectFragment(iEditMediaCallback, entityAudio);
        }
        return instance;
    }

    public EchoEffectFragment(EditMediaFragment.IEditMediaCallback iEditMediaCallback, EntityAudio entityAudio) {
        this.iEchoCallback = iEditMediaCallback;
        this.entityAudio = entityAudio;
    }

    public EchoEffectFragment() {
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        FragmentEchoEffectBinding inflate = FragmentEchoEffectBinding.inflate(layoutInflater, viewGroup, false);
        this.binding = inflate;
        LinearLayout root = inflate.getRoot();
        if (this.entityAudio != null && this.iEchoCallback != null) {
            this.tv_hint_delay = (TextCustumFont) root.findViewById(R.id.tv_delay_size);
            this.tv_hint_repeat = (TextCustumFont) root.findViewById(R.id.tv_repeat_size);
            this.tv_hint_volume = (TextCustumFont) root.findViewById(R.id.tv_volume_size);
            SeekBar seekBar = (SeekBar) root.findViewById(R.id.delaySeekBar);
            this.delaySeekBar = seekBar;
            seekBar.setProgress(this.entityAudio.getEffectAudio().getDelays());
            this.delaySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: hazem.nurmontage.videoquran.fragment.audio_effect.EchoEffectFragment.1
                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStopTrackingTouch(SeekBar seekBar2) {
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onProgressChanged(SeekBar seekBar2, int i, boolean z) {
                    EchoEffectFragment.this.tv_hint_delay.setText(String.valueOf(i));
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStartTrackingTouch(SeekBar seekBar2) {
                    if (EchoEffectFragment.this.isPlay) {
                        EchoEffectFragment.this.iEchoCallback.pausePreview();
                        EchoEffectFragment.this.updateButton();
                    }
                }
            });
            SeekBar seekBar2 = (SeekBar) root.findViewById(R.id.repeatSeekBar);
            this.repeatSeekBar = seekBar2;
            seekBar2.setProgress(this.entityAudio.getEffectAudio().getDecays());
            this.repeatSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: hazem.nurmontage.videoquran.fragment.audio_effect.EchoEffectFragment.2
                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStopTrackingTouch(SeekBar seekBar3) {
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onProgressChanged(SeekBar seekBar3, int i, boolean z) {
                    EchoEffectFragment.this.tv_hint_repeat.setText(String.valueOf(i + 1));
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStartTrackingTouch(SeekBar seekBar3) {
                    if (EchoEffectFragment.this.isPlay) {
                        EchoEffectFragment.this.iEchoCallback.pausePreview();
                        EchoEffectFragment.this.updateButton();
                    }
                }
            });
            SeekBar seekBar3 = (SeekBar) root.findViewById(R.id.volumeSeekBar);
            this.volumeSeekBar = seekBar3;
            seekBar3.setProgress(this.entityAudio.getEffectAudio().getVolume_echo());
            this.volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: hazem.nurmontage.videoquran.fragment.audio_effect.EchoEffectFragment.3
                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStopTrackingTouch(SeekBar seekBar4) {
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onProgressChanged(SeekBar seekBar4, int i, boolean z) {
                    EchoEffectFragment.this.tv_hint_volume.setText(String.valueOf(i));
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStartTrackingTouch(SeekBar seekBar4) {
                    if (EchoEffectFragment.this.isPlay) {
                        EchoEffectFragment.this.iEchoCallback.pausePreview();
                        EchoEffectFragment.this.updateButton();
                    }
                }
            });
            this.tv_hint_delay.setText(String.valueOf(this.delaySeekBar.getProgress()));
            this.tv_hint_repeat.setText(String.valueOf(this.repeatSeekBar.getProgress()));
            this.tv_hint_volume.setText(String.valueOf(this.volumeSeekBar.getProgress()));
            root.findViewById(R.id.btn_done).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.audio_effect.EchoEffectFragment$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    EchoEffectFragment.this.m1166xfa32dde7(view);
                }
            });
            ImageButton imageButton = (ImageButton) root.findViewById(R.id.btn_play);
            this.btnPreview = imageButton;
            imageButton.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.audio_effect.EchoEffectFragment$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    EchoEffectFragment.this.m1167x33133e86(view);
                }
            });
            root.findViewById(R.id.btn_appl_all).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.audio_effect.EchoEffectFragment$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    EchoEffectFragment.this.m1168x6bf39f25(view);
                }
            });
        }
        return root;
    }

    /* renamed from: lambda$onCreateView$0$hazem-nurmontage-videoquran-fragment-audio_effect-EchoEffectFragment, reason: not valid java name */
    /* synthetic */ void m1166xfa32dde7(View view) {
        done();
    }

    /* renamed from: lambda$onCreateView$1$hazem-nurmontage-videoquran-fragment-audio_effect-EchoEffectFragment, reason: not valid java name */
    /* synthetic */ void m1167x33133e86(View view) {
        previewAudio();
    }

    /* renamed from: lambda$onCreateView$2$hazem-nurmontage-videoquran-fragment-audio_effect-EchoEffectFragment, reason: not valid java name */
    /* synthetic */ void m1168x6bf39f25(View view) {
        applyEchoEffect(true, false);
    }

    public void updateButton() {
        this.btnPreview.setImageResource(R.drawable.play_arrow_24px);
        this.isPlay = false;
    }

    private void applyEchoEffect(boolean z, boolean z2) {
        float f;
        EchoEffectFragment echoEffectFragment;
        int progress = this.delaySeekBar.getProgress();
        int i = 1;
        int progress2 = this.repeatSeekBar.getProgress() + 1;
        int progress3 = this.volumeSeekBar.getProgress();
        EffectAudio effectAudio = this.entityAudio.getEffectAudio();
        if (!z && effectAudio.getDelays() == progress && effectAudio.getDecays() == this.repeatSeekBar.getProgress() && effectAudio.getVolume_echo() == progress3) {
            this.iEchoCallback.startPreview();
            return;
        }
        float start = effectAudio.getStart() / 1000.0f;
        float end = effectAudio.getEnd() / 1000.0f;
        effectAudio.setDecays(this.repeatSeekBar.getProgress());
        effectAudio.setDelays(progress);
        effectAudio.setVolume_echo(progress3);
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
        if (effectAudio.getDecays() <= 0 || effectAudio.getDelays() <= 0) {
            f = 1.0f;
        } else {
            float f2 = progress3 / 100.0f;
            float f3 = 0.01f;
            float max = Math.max(0.01f, 1.0f - f2);
            StringBuilder sb = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            while (i <= progress2) {
                int i2 = progress;
                float max2 = Math.max(0.01f, (float) (max * Math.pow(0.8d, i - 1)));
                sb.append(progress * i);
                sb2.append(String.format(Locale.US, "%.2f", Float.valueOf(max2)));
                if (i < progress2) {
                    sb.append("|");
                    sb2.append("|");
                }
                i++;
                progress = i2;
                f3 = 0.01f;
            }
            float max3 = Math.max(f3, f2);
            effectAudio.setOutGain(max3);
            effectAudio.setDecays_cmd(sb2.toString());
            effectAudio.setDelays_cmd(sb.toString());
            f = 1.0f;
            arrayList.add(String.format(Locale.US, "aecho=%.2f:%.2f:%s:%s", Float.valueOf(1.0f), Float.valueOf(max3), sb, sb2));
        }
        if (effectAudio.getSpeed() != f) {
            echoEffectFragment = this;
            arrayList.addAll(echoEffectFragment.buildSpeedFilters(effectAudio.getSpeed()));
        } else {
            echoEffectFragment = this;
        }
        EditMediaFragment.IEditMediaCallback iEditMediaCallback = echoEffectFragment.iEchoCallback;
        if (iEditMediaCallback != null) {
            if (z) {
                iEditMediaCallback.updateEntity(EffectAudioType.ECHO, echoEffectFragment.entityAudio);
                echoEffectFragment.iEchoCallback.onCmdAll(effectAudio);
                return;
            }
            String join = TextUtils.join(",", arrayList);
            if (z2) {
                echoEffectFragment.iEchoCallback.onCmdPlay(join);
            } else {
                echoEffectFragment.iEchoCallback.onCmd(join);
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

    private void done() {
        if (this.iEchoCallback != null) {
            if (this.entityAudio.getEffectAudio().getDelays() != this.delaySeekBar.getProgress() || this.entityAudio.getEffectAudio().getDecays() != this.repeatSeekBar.getProgress() || this.entityAudio.getEffectAudio().getVolume_echo() != this.volumeSeekBar.getProgress()) {
                applyEchoEffect(false, false);
            }
            this.iEchoCallback.pausePreview();
            this.iEchoCallback.onDone();
        }
    }

    private void previewAudio() {
        boolean z = this.isPlay;
        this.isPlay = !z;
        EditMediaFragment.IEditMediaCallback iEditMediaCallback = this.iEchoCallback;
        if (iEditMediaCallback != null) {
            if (!z) {
                applyEchoEffect(false, true);
                this.btnPreview.setImageResource(R.drawable.pause_24px);
            } else {
                iEditMediaCallback.pausePreview();
                this.btnPreview.setImageResource(R.drawable.play_arrow_24px);
            }
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        EditMediaFragment.IEditMediaCallback iEditMediaCallback = this.iEchoCallback;
        if (iEditMediaCallback != null) {
            iEditMediaCallback.pausePreview();
        }
        super.onDestroyView();
        instance = null;
        this.binding = null;
    }
}

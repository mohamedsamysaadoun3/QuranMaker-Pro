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
import hazem.nurmontage.videoquran.databinding.FragmentVolumeBinding;
import hazem.nurmontage.videoquran.entity_timeline.EntityAudio;
import hazem.nurmontage.videoquran.fragment.EditMediaFragment;
import hazem.nurmontage.videoquran.model.EffectAudio;
import hazem.nurmontage.videoquran.views.TextCustumFont;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* loaded from: classes2.dex */
public class SpeedFragment extends Fragment {
    public static SpeedFragment instance;
    private FragmentVolumeBinding binding;
    private ImageButton btnPreview;
    private EntityAudio entityAudio;
    private EditMediaFragment.IEditMediaCallback iVolumeCallback;
    private boolean isPlay;
    private TextCustumFont tvProgress;
    private SeekBar volumeSeekBar;

    public static SpeedFragment getInstance(EditMediaFragment.IEditMediaCallback iEditMediaCallback, EntityAudio entityAudio) {
        if (instance == null) {
            instance = new SpeedFragment(iEditMediaCallback, entityAudio);
        }
        return instance;
    }

    public SpeedFragment(EditMediaFragment.IEditMediaCallback iEditMediaCallback, EntityAudio entityAudio) {
        this.iVolumeCallback = iEditMediaCallback;
        this.entityAudio = entityAudio;
    }

    public SpeedFragment() {
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        FragmentVolumeBinding inflate = FragmentVolumeBinding.inflate(layoutInflater, viewGroup, false);
        this.binding = inflate;
        LinearLayout root = inflate.getRoot();
        EntityAudio entityAudio = this.entityAudio;
        if (entityAudio != null && entityAudio.getMediaPlayer() != null && this.iVolumeCallback != null) {
            this.tvProgress = (TextCustumFont) root.findViewById(R.id.tv_volume_size);
            ((TextCustumFont) root.findViewById(R.id.tv_volume)).setText(getResources().getString(R.string.speed));
            SeekBar seekBar = (SeekBar) root.findViewById(R.id.volumeSeekBar);
            this.volumeSeekBar = seekBar;
            seekBar.setMax(375);
            int speed = (int) (((this.entityAudio.getEffectAudio().getSpeed() - 0.25f) / 3.75f) * this.volumeSeekBar.getMax());
            this.volumeSeekBar.setProgress(speed);
            this.tvProgress.setText(String.format(Locale.US, "%.2fx", Float.valueOf(((speed / 375.0f) * 3.75f) + 0.25f)));
            this.volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: hazem.nurmontage.videoquran.fragment.audio_effect.SpeedFragment.1
                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onProgressChanged(SeekBar seekBar2, int i, boolean z) {
                    if (z) {
                        SpeedFragment.this.tvProgress.setText(String.format(Locale.US, "%.2fx", Float.valueOf(((i / 375.0f) * 3.75f) + 0.25f)));
                    }
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStartTrackingTouch(SeekBar seekBar2) {
                    if (SpeedFragment.this.isPlay) {
                        SpeedFragment.this.previewAudio();
                    }
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStopTrackingTouch(SeekBar seekBar2) {
                    SpeedFragment.this.applyVolume(false);
                }
            });
            root.findViewById(R.id.btn_done).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.audio_effect.SpeedFragment$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SpeedFragment.this.m1196x10fe89b6(view);
                }
            });
            ImageButton imageButton = (ImageButton) root.findViewById(R.id.btn_play);
            this.btnPreview = imageButton;
            imageButton.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.audio_effect.SpeedFragment$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SpeedFragment.this.m1197x5ebe01b7(view);
                }
            });
            root.findViewById(R.id.btn_appl_all).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.audio_effect.SpeedFragment$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SpeedFragment.this.m1198xac7d79b8(view);
                }
            });
        }
        return root;
    }

    /* renamed from: lambda$onCreateView$0$hazem-nurmontage-videoquran-fragment-audio_effect-SpeedFragment, reason: not valid java name */
    /* synthetic */ void m1196x10fe89b6(View view) {
        done();
    }

    /* renamed from: lambda$onCreateView$1$hazem-nurmontage-videoquran-fragment-audio_effect-SpeedFragment, reason: not valid java name */
    /* synthetic */ void m1197x5ebe01b7(View view) {
        previewAudio();
    }

    /* renamed from: lambda$onCreateView$2$hazem-nurmontage-videoquran-fragment-audio_effect-SpeedFragment, reason: not valid java name */
    /* synthetic */ void m1198xac7d79b8(View view) {
        applyVolume(true);
    }

    private void done() {
        EditMediaFragment.IEditMediaCallback iEditMediaCallback = this.iVolumeCallback;
        if (iEditMediaCallback != null) {
            iEditMediaCallback.pausePreview();
            this.iVolumeCallback.onDone();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void previewAudio() {
        boolean z = this.isPlay;
        this.isPlay = !z;
        EditMediaFragment.IEditMediaCallback iEditMediaCallback = this.iVolumeCallback;
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

    /* JADX INFO: Access modifiers changed from: private */
    public void applyVolume(boolean z) {
        EffectAudio effectAudio = this.entityAudio.getEffectAudio();
        effectAudio.setSpeed(((this.volumeSeekBar.getProgress() / 375.0f) * 3.75f) + 0.25f);
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
        EditMediaFragment.IEditMediaCallback iEditMediaCallback = this.iVolumeCallback;
        if (iEditMediaCallback != null) {
            if (z) {
                iEditMediaCallback.updateEntity(EffectAudioType.SPEED, this.entityAudio);
                this.iVolumeCallback.onCmdAll(effectAudio);
            } else {
                this.iVolumeCallback.onCmd(TextUtils.join(",", arrayList));
            }
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        EditMediaFragment.IEditMediaCallback iEditMediaCallback = this.iVolumeCallback;
        if (iEditMediaCallback != null) {
            iEditMediaCallback.pausePreview();
        }
        super.onDestroyView();
        instance = null;
        this.binding = null;
    }

    public void updateButton() {
        this.btnPreview.setImageResource(R.drawable.play_arrow_24px);
        this.isPlay = false;
    }
}

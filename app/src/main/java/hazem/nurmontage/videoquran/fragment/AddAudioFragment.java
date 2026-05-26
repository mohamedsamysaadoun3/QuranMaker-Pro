package hazem.nurmontage.videoquran.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.fragment.app.Fragment;
import hazem.nurmontage.videoquran.R;
import hazem.nurmontage.videoquran.databinding.FragmentAddAudioBinding;
import hazem.nurmontage.videoquran.views.TextCustumFont;

/* loaded from: classes2.dex */
public class AddAudioFragment extends Fragment {
    public static AddAudioFragment instance;
    private FragmentAddAudioBinding addAudioBinding;
    private IAudioCallback iAudioCallback;
    private Resources resources;

    public interface IAudioCallback {
        void cancel();

        void extract();

        void upload();
    }

    public static AddAudioFragment getInstance(IAudioCallback iAudioCallback, Resources resources) {
        if (instance == null) {
            instance = new AddAudioFragment(iAudioCallback, resources);
        }
        return instance;
    }

    public AddAudioFragment(IAudioCallback iAudioCallback, Resources resources) {
        this.iAudioCallback = iAudioCallback;
        this.resources = resources;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        FragmentAddAudioBinding inflate = FragmentAddAudioBinding.inflate(layoutInflater, viewGroup, false);
        this.addAudioBinding = inflate;
        LinearLayout root = inflate.getRoot();
        if (this.resources != null && this.iAudioCallback != null) {
            ((TextCustumFont) root.findViewById(R.id.tv_extract)).setText(this.resources.getString(R.string.extract_audio));
            ((TextCustumFont) root.findViewById(R.id.tv_audio)).setText(this.resources.getString(R.string.audio));
            root.findViewById(R.id.btn_upload).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.AddAudioFragment.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (AddAudioFragment.this.iAudioCallback != null) {
                        AddAudioFragment.this.iAudioCallback.upload();
                    }
                }
            });
            root.findViewById(R.id.btn_extract).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.AddAudioFragment.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (AddAudioFragment.this.iAudioCallback != null) {
                        AddAudioFragment.this.iAudioCallback.extract();
                    }
                }
            });
            root.findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.AddAudioFragment.3
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (AddAudioFragment.this.iAudioCallback != null) {
                        AddAudioFragment.this.iAudioCallback.cancel();
                    }
                }
            });
        }
        return root;
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        this.addAudioBinding = null;
        instance = null;
        this.iAudioCallback = null;
        super.onDestroyView();
    }
}

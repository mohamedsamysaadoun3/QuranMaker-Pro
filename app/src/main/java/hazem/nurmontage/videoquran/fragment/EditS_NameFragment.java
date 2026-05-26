package hazem.nurmontage.videoquran.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.fragment.app.Fragment;
import hazem.nurmontage.videoquran.R;
import hazem.nurmontage.videoquran.databinding.FragmentEditSNameBinding;
import hazem.nurmontage.videoquran.model.SurahNameEntity;
import hazem.nurmontage.videoquran.views.TextCustumFont;

/* loaded from: classes2.dex */
public class EditS_NameFragment extends Fragment {
    public static EditS_NameFragment instance;
    private SurahNameEntity entity_select;
    private FragmentEditSNameBinding fragmentBinding;
    private IEditS_Name iEditSName;
    private Resources resources;

    public interface IEditS_Name {
        void onColor(SurahNameEntity surahNameEntity);

        void onDone();

        void onEdit(SurahNameEntity surahNameEntity);

        void onFont(SurahNameEntity surahNameEntity);

        void update();
    }

    public static EditS_NameFragment getInstance(IEditS_Name iEditS_Name, Resources resources, SurahNameEntity surahNameEntity) {
        if (instance == null) {
            instance = new EditS_NameFragment(iEditS_Name, resources, surahNameEntity);
        }
        return instance;
    }

    public EditS_NameFragment(IEditS_Name iEditS_Name, Resources resources, SurahNameEntity surahNameEntity) {
        this.iEditSName = iEditS_Name;
        this.resources = resources;
        this.entity_select = surahNameEntity;
    }

    public EditS_NameFragment() {
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        FragmentEditSNameBinding inflate = FragmentEditSNameBinding.inflate(layoutInflater, viewGroup, false);
        this.fragmentBinding = inflate;
        LinearLayout root = inflate.getRoot();
        if (this.iEditSName != null && this.resources != null && this.entity_select != null) {
            ((TextCustumFont) root.findViewById(R.id.tv_color)).setText(this.resources.getString(R.string.color));
            ((TextCustumFont) root.findViewById(R.id.tv_edit)).setText(this.resources.getString(R.string.edit));
            ((TextCustumFont) root.findViewById(R.id.tv_font)).setText(this.resources.getString(R.string.font));
            root.findViewById(R.id.btn_font).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.EditS_NameFragment.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (EditS_NameFragment.this.iEditSName != null) {
                        EditS_NameFragment.this.iEditSName.onFont(EditS_NameFragment.this.entity_select);
                    }
                }
            });
            root.findViewById(R.id.btn_color).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.EditS_NameFragment.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (EditS_NameFragment.this.iEditSName != null) {
                        EditS_NameFragment.this.iEditSName.onColor(EditS_NameFragment.this.entity_select);
                    }
                }
            });
            root.findViewById(R.id.btn_edit).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.EditS_NameFragment.3
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (EditS_NameFragment.this.iEditSName != null) {
                        EditS_NameFragment.this.iEditSName.onEdit(EditS_NameFragment.this.entity_select);
                    }
                }
            });
        }
        return root;
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        instance = null;
        this.iEditSName = null;
        this.fragmentBinding = null;
        super.onDestroyView();
    }
}

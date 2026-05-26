package hazem.nurmontage.videoquran.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import hazem.nurmontage.videoquran.R;
import hazem.nurmontage.videoquran.adabter.WordAyaAdabter;
import hazem.nurmontage.videoquran.databinding.FragmentEditTextBinding;
import hazem.nurmontage.videoquran.entity_timeline.EntityQuranTimeline;
import hazem.nurmontage.videoquran.model.QuranEntity;
import hazem.nurmontage.videoquran.model.WordModel;
import hazem.nurmontage.videoquran.views.ArrowOverlayDecoration;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class EditTextFragment extends Fragment {
    public static EditTextFragment instance;
    private FragmentEditTextBinding fragmentBinding;
    private IEdiTextCallback iEditEntityCallback;
    private WordAyaAdabter.IWordAya iWordAya = new WordAyaAdabter.IWordAya() { // from class: hazem.nurmontage.videoquran.fragment.EditTextFragment.2
        @Override // hazem.nurmontage.videoquran.adabter.WordAyaAdabter.IWordAya
        public void onClick() {
            if (EditTextFragment.this.iEditEntityCallback != null) {
                String selectedAya = EditTextFragment.this.getSelectedAya();
                int findFirstDigitIndex = EditTextFragment.this.quranEntity.getNumber() != -1 ? EditTextFragment.findFirstDigitIndex(selectedAya) : -1;
                if (findFirstDigitIndex != -1) {
                    String substring = selectedAya.substring(0, findFirstDigitIndex);
                    try {
                        int parseInt = Integer.parseInt(selectedAya.substring(findFirstDigitIndex));
                        if (parseInt > 286) {
                            parseInt = 286;
                        }
                        EditTextFragment.this.quranEntity.setNumber(parseInt);
                        EditTextFragment.this.quranEntity.setIndexNumber(findFirstDigitIndex);
                        selectedAya = substring + " نص";
                    } catch (Exception unused) {
                        selectedAya = substring;
                    }
                }
                EditTextFragment.this.quranEntity.setTxt(selectedAya);
                EditTextFragment.this.quranEntity.initPreset(EditTextFragment.this.quranEntity.getmPreset());
                EditTextFragment.this.iEditEntityCallback.onUpdate(EditTextFragment.this.quranEntity);
            }
        }
    };
    private QuranEntity quranEntity;
    private RecyclerView recyclerView;
    private WordAyaAdabter wordAyaAdabter;

    public interface IEdiTextCallback {
        void onDone(EntityQuranTimeline entityQuranTimeline);

        void onUpdate(QuranEntity quranEntity);
    }

    public static EditTextFragment getInstance(IEdiTextCallback iEdiTextCallback, QuranEntity quranEntity) {
        if (instance == null) {
            instance = new EditTextFragment(iEdiTextCallback, quranEntity);
        }
        return instance;
    }

    public EditTextFragment() {
    }

    public EditTextFragment(IEdiTextCallback iEdiTextCallback, QuranEntity quranEntity) {
        this.iEditEntityCallback = iEdiTextCallback;
        this.quranEntity = quranEntity;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        FragmentEditTextBinding inflate = FragmentEditTextBinding.inflate(layoutInflater, viewGroup, false);
        this.fragmentBinding = inflate;
        LinearLayout root = inflate.getRoot();
        if (this.quranEntity != null && this.iEditEntityCallback != null) {
            init(root);
            root.findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.EditTextFragment.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (EditTextFragment.this.iEditEntityCallback != null) {
                        EditTextFragment.this.iEditEntityCallback.onDone(EditTextFragment.this.quranEntity.getEntityQuran());
                    }
                }
            });
        }
        return root;
    }

    private void init(View view) {
        String txt;
        int indexNumber = this.quranEntity.getIndexNumber();
        if (indexNumber >= 0) {
            txt = this.quranEntity.getTxt().substring(0, Math.min(indexNumber, this.quranEntity.getTxt().length())) + " " + this.quranEntity.getNumber();
        } else {
            txt = this.quranEntity.getTxt();
        }
        String complete_aya = this.quranEntity.getComplete_aya();
        int startWord_index = this.quranEntity.getStartWord_index();
        int endWord_index = this.quranEntity.getEndWord_index();
        String[] split = complete_aya.trim().replaceAll("\\s*([\\u06D6-\\u06ED])", "$1").trim().split("\\s+");
        ArrayList arrayList = new ArrayList();
        int i = 1;
        if (startWord_index == endWord_index) {
            String[] split2 = txt.trim().replaceAll("\\s*([\\u06D6-\\u06ED])", "$1").split("\\s+");
            int indexOf = complete_aya.indexOf(txt);
            int i2 = indexOf == 0 ? 1 : 0;
            int length = split.length;
            int i3 = 0;
            int i4 = 0;
            int i5 = 0;
            while (i3 < length) {
                String str = split[i3];
                if (!str.equals("-1")) {
                    if (i2 == 0) {
                        if (i4 == indexOf) {
                            i2 = i;
                        }
                        i4 += str.length() + i;
                    }
                    if (i2 != 0 && i5 < split2.length) {
                        boolean equals = str.equals(split2[i5]);
                        arrayList.add(new WordModel(str, equals));
                        if (equals) {
                            i5++;
                        }
                    } else {
                        arrayList.add(new WordModel(str, false));
                    }
                }
                i3++;
                i = 1;
            }
        } else {
            int i6 = 0;
            while (i6 < split.length) {
                if (!split[i6].equals("-1")) {
                    arrayList.add(new WordModel(split[i6], i6 >= startWord_index && i6 < endWord_index));
                }
                i6++;
            }
        }
        this.wordAyaAdabter = new WordAyaAdabter(this.iWordAya, arrayList);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        this.recyclerView = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), 0, true));
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setItemAnimator(null);
        this.recyclerView.setAdapter(this.wordAyaAdabter);
        try {
            this.recyclerView.scrollToPosition(startWord_index);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.recyclerView.addItemDecoration(new ArrowOverlayDecoration(getContext(), R.drawable.btn_on_back, 18));
    }

    public void update(QuranEntity quranEntity) {
        String txt;
        if (quranEntity == null) {
            return;
        }
        this.quranEntity = quranEntity;
        int indexNumber = quranEntity.getIndexNumber();
        if (indexNumber >= 0) {
            txt = this.quranEntity.getTxt().substring(0, Math.min(indexNumber, this.quranEntity.getTxt().length())) + " " + this.quranEntity.getNumber();
        } else {
            txt = this.quranEntity.getTxt();
        }
        String complete_aya = this.quranEntity.getComplete_aya();
        int startWord_index = this.quranEntity.getStartWord_index();
        int endWord_index = this.quranEntity.getEndWord_index();
        String[] split = complete_aya.trim().replaceAll("\\s*([\\u06D6-\\u06ED])", "$1").trim().split("\\s+");
        ArrayList arrayList = new ArrayList();
        int i = 1;
        if (startWord_index == endWord_index) {
            String[] split2 = txt.trim().replaceAll("\\s*([\\u06D6-\\u06ED])", "$1").split("\\s+");
            int indexOf = complete_aya.indexOf(txt);
            int i2 = indexOf == 0 ? 1 : 0;
            int length = split.length;
            int i3 = 0;
            int i4 = 0;
            int i5 = 0;
            while (i3 < length) {
                String str = split[i3];
                if (!str.equals("-1")) {
                    if (i2 == 0) {
                        if (i4 == indexOf) {
                            i2 = i;
                        }
                        i4 += str.length() + i;
                    }
                    if (i2 != 0 && i5 < split2.length) {
                        boolean equals = str.equals(split2[i5]);
                        arrayList.add(new WordModel(str, equals));
                        if (equals) {
                            i5++;
                        }
                    } else {
                        arrayList.add(new WordModel(str, false));
                    }
                }
                i3++;
                i = 1;
            }
        } else {
            int i6 = 0;
            while (i6 < split.length) {
                String str2 = split[i6];
                if (!str2.equals("-1")) {
                    arrayList.add(new WordModel(str2, i6 >= startWord_index && i6 < endWord_index));
                }
                i6++;
            }
        }
        this.wordAyaAdabter.setList(arrayList);
        try {
            this.recyclerView.scrollToPosition(startWord_index);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int findFirstDigitIndex(String str) {
        if (str != null && !str.isEmpty()) {
            for (int i = 0; i < str.length(); i++) {
                if (Character.isDigit(str.charAt(i))) {
                    return i;
                }
            }
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getSelectedAya() {
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        List<WordModel> list = this.wordAyaAdabter.getList();
        String[] split = this.quranEntity.getTranslation_complete() != null ? this.quranEntity.getTranslation_complete().split(",") : null;
        int i = -1;
        int i2 = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            WordModel wordModel = list.get(i3);
            if (wordModel.isSelected()) {
                if (i == -1) {
                    i = i3;
                }
                i2++;
                sb.append(wordModel.getW()).append(" ");
                if (split != null && i3 < split.length) {
                    sb2.append(split[i3]).append(" ");
                }
            }
        }
        if (sb2.length() > 0) {
            this.quranEntity.setTranslation(sb2.toString());
        } else {
            this.quranEntity.setTranslation(null);
        }
        int i4 = i2 + i;
        if (this.quranEntity.getNumber() != -1) {
            i4++;
        }
        this.quranEntity.setEndWord_index(i4);
        this.quranEntity.setStartWord_index(i);
        return sb.toString().trim();
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        FragmentEditTextBinding fragmentEditTextBinding = this.fragmentBinding;
        if (fragmentEditTextBinding != null) {
            fragmentEditTextBinding.getRoot().removeAllViews();
            this.fragmentBinding = null;
        }
        this.iWordAya = null;
        instance = null;
        super.onDestroyView();
    }
}

package hazem.nurmontage.videoquran.fragment;

import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import hazem.nurmontage.videoquran.ProVersionActivity$$ExternalSyntheticBackport0;
import hazem.nurmontage.videoquran.R;
import hazem.nurmontage.videoquran.Utils.LocaleHelper;
import hazem.nurmontage.videoquran.Utils.MyPrefereces;
import hazem.nurmontage.videoquran.Utils.NetworkUtils;
import hazem.nurmontage.videoquran.Utils.QuranPreference;
import hazem.nurmontage.videoquran.Utils.QuranReader;
import hazem.nurmontage.videoquran.adabter.IconQuranAdabters;
import hazem.nurmontage.videoquran.databinding.FragmentAddQuranBinding;
import hazem.nurmontage.videoquran.model.RecitersModel;
import hazem.nurmontage.videoquran.views.CheckboxCustumFont;
import hazem.nurmontage.videoquran.views.TextCustumFont;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes2.dex */
public class AddQuranFragment extends Fragment {
    public static AddQuranFragment instance;
    private ArrayAdapter<String> adapterFromAyah;
    private ArrayAdapter<String> adapterToAyah;
    private int[] arrayCount;
    private String[] arrayIdentifier;
    private String[] arrayReciters;
    private String[] arraySurah;
    private String[] arrayTranslation;
    private FragmentAddQuranBinding fragmentBinding;
    private IAddQuran iAddQuran;
    private IconQuranAdabters iconQuranAdabters;
    private CheckboxCustumFont includeBismilah;
    private boolean isFromSearch;
    private boolean isFromSelectReciters;
    private ImageView iv_done_upload;
    private LinearLayout layoutConnection;
    private String path_video_copy;
    private QuranPreference quranPreference;
    private QuranReader quranReader;
    private String reader_name;
    private Resources resources;
    private Spinner spinnerFrom;
    private Spinner spinnerReciters;
    private Spinner spinnerSurah;
    private Spinner spinnerTo;
    private Spinner spinnerTranslation;
    private String surah_hint;
    private TextCustumFont tv_reader_name;
    private Uri uri_recitation;
    private String icon = "hafes";
    private List<RecitersModel> recitersModels = new ArrayList();
    private int current_pos = -1;
    private final String[] translation_name = {"en.hilali.txt", "fr.hamidullah.txt", "ur.maududi.txt", "tr.ozturk.txt", "de.bubenheim.txt", "id.indonesian.txt", "fa.fooladvand.txt", "bn.bengali.txt"};
    private boolean isInit = true;
    private boolean isFromSelect = true;
    private IconQuranAdabters.IIconQuranCallback iconQuranCallback = new IconQuranAdabters.IIconQuranCallback() { // from class: hazem.nurmontage.videoquran.fragment.AddQuranFragment.8
        @Override // hazem.nurmontage.videoquran.adabter.IconQuranAdabters.IIconQuranCallback
        public void onIcon(String str) {
            AddQuranFragment.this.icon = str;
        }
    };
    private AdapterView.OnItemSelectedListener onFromAyaSelectedListener = new AdapterView.OnItemSelectedListener() { // from class: hazem.nurmontage.videoquran.fragment.AddQuranFragment.9
        @Override // android.widget.AdapterView.OnItemSelectedListener
        public void onNothingSelected(AdapterView<?> adapterView) {
        }

        @Override // android.widget.AdapterView.OnItemSelectedListener
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
            if (AddQuranFragment.this.isFromSearch) {
                AddQuranFragment.this.spinnerTo.setSelection(AddQuranFragment.this.quranPreference.getTo());
                AddQuranFragment.this.isFromSearch = false;
            } else {
                if (!AddQuranFragment.this.isFromSelect) {
                    if (AddQuranFragment.this.spinnerTo.getSelectedItemPosition() != i) {
                        AddQuranFragment.this.spinnerTo.setSelection(i);
                        return;
                    }
                    return;
                }
                AddQuranFragment.this.isFromSelect = false;
            }
        }
    };
    private AdapterView.OnItemSelectedListener onSurahSelectedListener = new AdapterView.OnItemSelectedListener() { // from class: hazem.nurmontage.videoquran.fragment.AddQuranFragment.10
        @Override // android.widget.AdapterView.OnItemSelectedListener
        public void onNothingSelected(AdapterView<?> adapterView) {
        }

        @Override // android.widget.AdapterView.OnItemSelectedListener
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
            int i2;
            if (i == AddQuranFragment.this.current_pos) {
                return;
            }
            if (AddQuranFragment.this.isInit) {
                i2 = AddQuranFragment.this.arrayCount[AddQuranFragment.this.quranPreference.getSurah()];
            } else {
                i2 = AddQuranFragment.this.arrayCount[i];
            }
            ArrayList arrayList = new ArrayList();
            for (int i3 = 1; i3 <= i2; i3++) {
                arrayList.add(String.valueOf(i3));
            }
            AddQuranFragment.this.adapterFromAyah.clear();
            AddQuranFragment.this.adapterFromAyah.addAll(arrayList);
            AddQuranFragment.this.adapterToAyah.clear();
            AddQuranFragment.this.adapterToAyah.addAll(arrayList);
            if (AddQuranFragment.this.isInit) {
                try {
                    AddQuranFragment.this.spinnerSurah.setSelection(AddQuranFragment.this.quranPreference.getSurah(), true);
                    AddQuranFragment.this.spinnerFrom.setSelection(AddQuranFragment.this.quranPreference.getFrom(), false);
                    AddQuranFragment.this.spinnerTo.setSelection(AddQuranFragment.this.quranPreference.getTo(), false);
                    AddQuranFragment.this.spinnerReciters.setSelection(AddQuranFragment.this.quranPreference.getNameReader(), false);
                    AddQuranFragment.this.spinnerTranslation.setSelection(AddQuranFragment.this.quranPreference.getTranslation(), false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                AddQuranFragment.this.isInit = false;
            } else {
                AddQuranFragment.this.spinnerTo.setSelection(0, false);
                AddQuranFragment.this.spinnerFrom.setSelection(0, false);
            }
            AddQuranFragment addQuranFragment = AddQuranFragment.this;
            addQuranFragment.current_pos = addQuranFragment.spinnerSurah.getSelectedItemPosition();
        }
    };

    public interface IAddQuran {
        void onAdd(String str, String str2, String str3, String str4, int i, int i2, String str5, int i3, int i4);

        void onAddReaderName(String str, String str2, Uri uri);

        void onAddTranslation(String str, int i, boolean z);

        void onBismilah();

        void onCancel();

        void onDone(String str, int i, String str2, Uri uri, String str3);

        void onDone(String str, int i, String str2, List<RecitersModel> list);

        void onErrorLimitation();

        void onSearch();

        void onVuCopyRight();

        void progress();

        void uploadRecitation();
    }

    private void setSystemBarsColorBlack() {
    }

    public static AddQuranFragment getInstance(IAddQuran iAddQuran, Resources resources, Uri uri, String str, String str2) {
        if (instance == null) {
            instance = new AddQuranFragment(iAddQuran, resources, uri, str, str2);
        }
        return instance;
    }

    public static AddQuranFragment getInstance(IAddQuran iAddQuran, Resources resources) {
        if (instance == null) {
            instance = new AddQuranFragment(iAddQuran, resources);
        }
        return instance;
    }

    public AddQuranFragment() {
    }

    public AddQuranFragment(IAddQuran iAddQuran, Resources resources) {
        this.iAddQuran = iAddQuran;
        this.resources = resources;
    }

    public AddQuranFragment(IAddQuran iAddQuran, Resources resources, Uri uri, String str, String str2) {
        this.iAddQuran = iAddQuran;
        this.resources = resources;
        this.uri_recitation = uri;
        this.path_video_copy = str;
        this.reader_name = str2;
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        setSystemBarsColorBlack();
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        FragmentAddQuranBinding inflate = FragmentAddQuranBinding.inflate(layoutInflater, viewGroup, false);
        this.fragmentBinding = inflate;
        RelativeLayout root = inflate.getRoot();
        if (this.resources != null && this.iAddQuran != null) {
            this.quranPreference = new QuranPreference(getContext());
            this.quranReader = new QuranReader(getContext());
            this.surah_hint = LocaleHelper.getLanguage(getContext()).equals("ar") ? "سورة " : "Surah ";
            ImageView imageView = (ImageView) root.findViewById(R.id.iv_done);
            this.iv_done_upload = imageView;
            if (this.uri_recitation != null) {
                imageView.setVisibility(0);
            }
            ((TextCustumFont) root.findViewById(R.id.tv_surah)).setText(this.resources.getString(R.string.tv_surah));
            ((TextCustumFont) root.findViewById(R.id.tv_icon)).setText(this.resources.getString(R.string.quran_icon));
            ((TextCustumFont) root.findViewById(R.id.tv_add_bismilah)).setText(this.resources.getString(R.string.add_bismilah));
            ((TextCustumFont) root.findViewById(R.id.tv_end_ayah)).setText(this.resources.getString(R.string.to));
            ((TextCustumFont) root.findViewById(R.id.tv_hint_reader)).setText(this.resources.getString(R.string.tv_hint_reader));
            ((TextCustumFont) root.findViewById(R.id.tv_translation)).setText(this.resources.getString(R.string.translation));
            this.arraySurah = getResources().getStringArray(R.array.surah_names_merged);
            this.arrayCount = getResources().getIntArray(R.array.surah_count);
            this.arrayIdentifier = getResources().getStringArray(R.array.identifier);
            this.arrayReciters = this.resources.getStringArray(R.array.reciters);
            this.arrayTranslation = this.resources.getStringArray(R.array.translation_name);
            CheckboxCustumFont checkboxCustumFont = (CheckboxCustumFont) root.findViewById(R.id.checkbox);
            this.includeBismilah = checkboxCustumFont;
            checkboxCustumFont.setChecked(MyPrefereces.isIncludeBismilah(getContext()));
            root.findViewById(R.id.add_bismilah).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.AddQuranFragment.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    AddQuranFragment.this.includeBismilah.setChecked(!AddQuranFragment.this.includeBismilah.isChecked());
                }
            });
            this.spinnerSurah = (Spinner) root.findViewById(R.id.sura_name);
            ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), R.layout.row_spinner_aya, this.arraySurah);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            this.spinnerSurah.setOnItemSelectedListener(this.onSurahSelectedListener);
            this.spinnerSurah.setAdapter((SpinnerAdapter) arrayAdapter);
            Spinner spinner = this.spinnerSurah;
            spinner.setDropDownVerticalOffset(spinner.getHeight() * (-10));
            this.spinnerFrom = (Spinner) root.findViewById(R.id.aya_from);
            ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(getContext(), R.layout.row_spinner_aya);
            this.adapterFromAyah = arrayAdapter2;
            arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            this.spinnerFrom.setOnItemSelectedListener(this.onFromAyaSelectedListener);
            this.spinnerFrom.setAdapter((SpinnerAdapter) this.adapterFromAyah);
            this.spinnerTo = (Spinner) root.findViewById(R.id.aya_to);
            ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<>(getContext(), R.layout.row_spinner_aya);
            this.adapterToAyah = arrayAdapter3;
            arrayAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            this.spinnerTo.setAdapter((SpinnerAdapter) this.adapterToAyah);
            this.spinnerReciters = (Spinner) root.findViewById(R.id.spinner_reciters);
            ArrayAdapter arrayAdapter4 = new ArrayAdapter(getContext(), R.layout.row_spinner_aya, this.arrayReciters);
            arrayAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            this.spinnerReciters.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: hazem.nurmontage.videoquran.fragment.AddQuranFragment.2
                @Override // android.widget.AdapterView.OnItemSelectedListener
                public void onNothingSelected(AdapterView<?> adapterView) {
                }

                @Override // android.widget.AdapterView.OnItemSelectedListener
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                    if (AddQuranFragment.this.isFromSelectReciters) {
                        AddQuranFragment.this.goneReaderNameUpload();
                    }
                    AddQuranFragment.this.isFromSelectReciters = true;
                }
            });
            this.spinnerReciters.setAdapter((SpinnerAdapter) arrayAdapter4);
            this.spinnerTranslation = (Spinner) root.findViewById(R.id.spinner_translation);
            ArrayAdapter arrayAdapter5 = new ArrayAdapter(getContext(), R.layout.row_spinner_aya, this.arrayTranslation);
            arrayAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            this.spinnerTranslation.setAdapter((SpinnerAdapter) arrayAdapter5);
            this.layoutConnection = (LinearLayout) root.findViewById(R.id.hint_no_internet);
            root.findViewById(R.id.btn_done).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.AddQuranFragment.3
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (AddQuranFragment.this.iAddQuran != null) {
                        final int selectedItemPosition = AddQuranFragment.this.spinnerFrom.getSelectedItemPosition() + 1;
                        final int selectedItemPosition2 = AddQuranFragment.this.spinnerTo.getSelectedItemPosition() + 1;
                        final int selectedItemPosition3 = AddQuranFragment.this.spinnerSurah.getSelectedItemPosition() + 1;
                        new Thread(new Runnable() { // from class: hazem.nurmontage.videoquran.fragment.AddQuranFragment.3.1
                            @Override // java.lang.Runnable
                            public void run() {
                                AddQuranFragment.this.iAddQuran.progress();
                                if (AddQuranFragment.this.includeBismilah != null && AddQuranFragment.this.includeBismilah.isChecked()) {
                                    AddQuranFragment.this.iAddQuran.onBismilah();
                                }
                                AddQuranFragment.this.addAyaEntityRecursive(selectedItemPosition, selectedItemPosition2, selectedItemPosition3);
                            }
                        }).start();
                    }
                }
            });
            root.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.AddQuranFragment.4
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (AddQuranFragment.this.iAddQuran != null) {
                        AddQuranFragment.this.iAddQuran.onCancel();
                    }
                }
            });
            root.findViewById(R.id.btn_search).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.AddQuranFragment.5
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    AddQuranFragment.this.savePreference();
                    if (AddQuranFragment.this.iAddQuran != null) {
                        AddQuranFragment.this.iAddQuran.onSearch();
                    }
                }
            });
            root.findViewById(R.id.btn_upload).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.AddQuranFragment.6
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (AddQuranFragment.this.iAddQuran != null) {
                        AddQuranFragment.this.iAddQuran.uploadRecitation();
                    }
                    AddQuranFragment.this.iAddQuran = null;
                }
            });
            TextCustumFont textCustumFont = (TextCustumFont) root.findViewById(R.id.tv_reader);
            this.tv_reader_name = textCustumFont;
            textCustumFont.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.fragment.AddQuranFragment.7
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (AddQuranFragment.this.iAddQuran == null || AddQuranFragment.this.uri_recitation == null) {
                        return;
                    }
                    AddQuranFragment.this.iAddQuran.onAddReaderName(AddQuranFragment.this.reader_name, AddQuranFragment.this.path_video_copy, AddQuranFragment.this.uri_recitation);
                }
            });
            String str = this.reader_name;
            if (str == null || str.isEmpty()) {
                this.reader_name = "-";
                this.tv_reader_name.setTextColor(-1);
            } else {
                this.tv_reader_name.getPaint().setUnderlineText(true);
                this.tv_reader_name.setText(this.reader_name);
            }
            initIconRv(root);
        }
        return root;
    }

    private void initIconRv(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), 0, false));
        recyclerView.setItemAnimator(null);
        recyclerView.setHasFixedSize(true);
        ArrayList arrayList = new ArrayList();
        arrayList.add("hafes");
        arrayList.add("shamerli");
        arrayList.add("nour_hode");
        arrayList.add("amiri");
        IconQuranAdabters iconQuranAdabters = new IconQuranAdabters(this.iconQuranCallback, arrayList, MyPrefereces.getLastIconIndex(getContext()));
        this.iconQuranAdabters = iconQuranAdabters;
        this.icon = (String) arrayList.get(iconQuranAdabters.getSelect());
        recyclerView.setAdapter(this.iconQuranAdabters);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void goneReaderNameUpload() {
        this.uri_recitation = null;
        this.iv_done_upload.setVisibility(8);
        this.tv_reader_name.setText("-");
        this.tv_reader_name.getPaint().setUnderlineText(false);
        this.tv_reader_name.setOnClickListener(null);
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        try {
            if (NetworkUtils.isNetworkAvailable(getContext())) {
                this.spinnerReciters.setVisibility(0);
                this.spinnerReciters.setEnabled(true);
                this.layoutConnection.setVisibility(8);
            } else {
                this.spinnerReciters.setEnabled(false);
                this.spinnerReciters.setVisibility(4);
                this.layoutConnection.setVisibility(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addAyaIndex() {
        try {
            this.isFromSearch = true;
            int surah = this.quranPreference.getSurah();
            this.current_pos = surah;
            this.spinnerSurah.setSelection(surah, false);
            int i = this.arrayCount[this.quranPreference.getSurah()];
            ArrayList arrayList = new ArrayList();
            for (int i2 = 1; i2 <= i; i2++) {
                arrayList.add(String.valueOf(i2));
            }
            this.adapterFromAyah.clear();
            this.adapterFromAyah.addAll(arrayList);
            this.adapterToAyah.clear();
            this.adapterToAyah.addAll(arrayList);
            this.spinnerFrom.setSelection(this.quranPreference.getFrom(), false);
            this.spinnerReciters.setSelection(this.quranPreference.getNameReader(), false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setNameReader(String str, Uri uri, String str2) {
        this.uri_recitation = uri;
        this.path_video_copy = str2;
        if (uri != null) {
            this.iv_done_upload.setVisibility(0);
        }
        if (str == null || str.isEmpty()) {
            this.tv_reader_name.getPaint().setUnderlineText(false);
            str = "-";
        } else {
            this.tv_reader_name.getPaint().setUnderlineText(true);
        }
        this.reader_name = str;
        this.tv_reader_name.setText(str);
    }

    public void splitAya(String str, String str2, int i) {
        int i2;
        int i3;
        int i4;
        StringBuilder sb;
        String str3;
        String str4;
        StringBuilder sb2;
        int i5;
        int i6;
        String str5;
        int i7;
        String trim = str.trim();
        String[] split = trim.replaceAll("\\s*([\\u06D6-\\u06ED])", "$1").trim().split("\\s+");
        String[] split2 = str2 != null ? str2.split(",") : null;
        String str6 = " ";
        String str7 = " نص";
        if (split.length <= 4) {
            this.iAddQuran.onAdd(str + " نص", trim, str2 != null ? str2.replace(",", " ") : null, str2, str.length(), i, this.icon, 0, split.length);
            return;
        }
        StringBuilder sb3 = new StringBuilder();
        int i8 = 1;
        int length = split.length - 1;
        int i9 = 0;
        int i10 = 0;
        int i11 = 0;
        int i12 = 0;
        while (i11 < split.length) {
            String str8 = split[i11];
            sb3.append(str8).append(str6);
            if (str8.length() > i8) {
                i9++;
            }
            int i13 = i10 + 1;
            if (i9 == 5) {
                int i14 = (i12 + i9) - (i13 - i9);
                if (i11 == length) {
                    String trim2 = sb3.toString().trim();
                    i2 = i11;
                    int i15 = i12;
                    i5 = 0;
                    i3 = length;
                    i4 = 1;
                    sb2 = sb3;
                    str3 = str7;
                    str4 = str6;
                    this.iAddQuran.onAdd(trim2 + str7, trim, split2 != null ? getWords(split2, i12, i14) : null, str2, trim2.length(), i, this.icon, i15, i14);
                    i7 = i15;
                } else {
                    i2 = i11;
                    int i16 = i12;
                    i3 = length;
                    i4 = i8;
                    sb2 = sb3;
                    str3 = str7;
                    str4 = str6;
                    i5 = 0;
                    IAddQuran iAddQuran = this.iAddQuran;
                    String trim3 = sb2.toString().trim();
                    if (split2 != null) {
                        i6 = i16;
                        str5 = getWords(split2, i6, i14);
                    } else {
                        i6 = i16;
                        str5 = null;
                    }
                    i7 = i6;
                    iAddQuran.onAdd(trim3, trim, str5, str2, -1, -1, this.icon, i6, i6 + i13);
                }
                i12 = i7 + i13;
                sb = sb2;
                sb.setLength(i5);
                i9 = i5;
                i10 = i9;
            } else {
                i2 = i11;
                i3 = length;
                i4 = i8;
                sb = sb3;
                str3 = str7;
                str4 = str6;
                i10 = i13;
            }
            sb3 = sb;
            i11 = i2 + 1;
            i8 = i4;
            str6 = str4;
            length = i3;
            str7 = str3;
        }
        int i17 = i12;
        StringBuilder sb4 = sb3;
        String str9 = str7;
        if (sb4.length() > 0) {
            String trim4 = sb4.toString().trim();
            this.iAddQuran.onAdd(trim4 + str9, trim, split2 != null ? getWords(split2, (split2.length - i9) - (i10 - i9), split2.length) : null, str2, trim4.length(), i, this.icon, i17, i17 + i10);
        }
    }

    public String getWords(String[] strArr, int i, int i2) {
        if (strArr == null || strArr.length == 0) {
            return "";
        }
        if (i < 0) {
            i = 0;
        }
        if (i2 > strArr.length) {
            i2 = strArr.length;
        }
        if (i >= i2) {
            return "";
        }
        return ProVersionActivity$$ExternalSyntheticBackport0.m(" ", (CharSequence[]) Arrays.copyOfRange(strArr, i, i2));
    }

    public void addAyaEntityRecursive(int i, int i2, int i3) {
        try {
            String ayahText = this.quranReader.getAyahText(i3, i);
            String translationAyahText = this.spinnerTranslation.getSelectedItemPosition() > 0 ? this.quranReader.getTranslationAyahText(this.translation_name[this.spinnerTranslation.getSelectedItemPosition() - 1], i3, i) : null;
            splitAya(ayahText, null, i);
            if (translationAyahText != null) {
                this.iAddQuran.onAddTranslation(translationAyahText, i, this.spinnerTranslation.getSelectedItemPosition() == 1);
            }
            if (this.iAddQuran != null) {
                if (this.spinnerReciters.isEnabled()) {
                    this.recitersModels.add(new RecitersModel(this.arrayIdentifier[this.spinnerReciters.getSelectedItemPosition()], i3, i));
                }
                if (i >= i2) {
                    if (this.uri_recitation != null) {
                        this.iAddQuran.onDone(this.surah_hint + this.arraySurah[this.spinnerSurah.getSelectedItemPosition()], this.spinnerSurah.getSelectedItemPosition() + 1, this.reader_name, this.uri_recitation, this.path_video_copy);
                        return;
                    } else {
                        this.iAddQuran.onDone(this.surah_hint + this.arraySurah[this.spinnerSurah.getSelectedItemPosition()], this.spinnerSurah.getSelectedItemPosition() + 1, this.arrayReciters[this.spinnerReciters.getSelectedItemPosition()], this.recitersModels);
                        return;
                    }
                }
            }
            addAyaEntityRecursive(i + 1, i2, i3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void savePreference() {
        QuranPreference quranPreference = this.quranPreference;
        if (quranPreference != null) {
            quranPreference.savePreferences(this.spinnerSurah.getSelectedItemPosition(), this.spinnerFrom.getSelectedItemPosition(), this.spinnerTo.getSelectedItemPosition(), this.spinnerReciters.getSelectedItemPosition(), this.spinnerTranslation.getSelectedItemPosition());
        }
        try {
            MyPrefereces.putIndexLastIcon(getContext(), this.iconQuranAdabters.getSelect());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            MyPrefereces.putIncludeBismilah(getContext(), this.includeBismilah.isChecked());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        savePreference();
        QuranPreference.saveLastSearch(requireContext(), null);
        IAddQuran iAddQuran = this.iAddQuran;
        if (iAddQuran != null) {
            iAddQuran.onCancel();
        }
        this.onFromAyaSelectedListener = null;
        this.onSurahSelectedListener = null;
        this.fragmentBinding = null;
        instance = null;
        this.iconQuranCallback = null;
    }
}

package hazem.nurmontage.videoquran;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import hazem.nurmontage.videoquran.Utils.JavaBM;
import hazem.nurmontage.videoquran.Utils.LocaleHelper;
import hazem.nurmontage.videoquran.Utils.QuranPreference;
import hazem.nurmontage.videoquran.Utils.RemoveTashkeel;
import hazem.nurmontage.videoquran.Utils.Utils;
import hazem.nurmontage.videoquran.adabter.SearchQuranAdabters;
import hazem.nurmontage.videoquran.model.ItemQuranSearch;
import hazem.nurmontage.videoquran.views.ButtonCustumFont;
import hazem.nurmontage.videoquran.views.TextCustumFont;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/* loaded from: classes2.dex */
public class QuranSearchActivity extends Base {
    private ButtonCustumFont btnDone;
    private TextView countAya;
    private EditText editText;
    private volatile int indexAya;
    private volatile int indexSurah;
    private boolean isFullSurah;
    private volatile boolean isRun;
    private JavaBM javaBM;
    private String lastKey;
    private String lastSearchKey;
    private int mTo;
    private RecyclerView recyclerView;
    private Runnable runnableByAya;
    private Runnable runnableBySurah;
    private ProgressBar searchProgressBar;
    private SearchQuranAdabters searchQuranAdapter;
    private String[] surahNames;
    private Thread thread;
    private int mFrom = -1;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private InputStream in_quran = null;
    private BufferedReader bufferedReader_quran = null;
    private final OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) { // from class: hazem.nurmontage.videoquran.QuranSearchActivity.2
        @Override // androidx.activity.OnBackPressedCallback
        public void handleOnBackPressed() {
            QuranSearchActivity.this.closeKeyboard();
            QuranSearchActivity.this.setResult(0);
            QuranSearchActivity.this.finish();
        }
    };
    private final SearchQuranAdabters.ISearchQuranCallback iSearchQuranCallback = new SearchQuranAdabters.ISearchQuranCallback() { // from class: hazem.nurmontage.videoquran.QuranSearchActivity.4
        @Override // hazem.nurmontage.videoquran.adabter.SearchQuranAdabters.ISearchQuranCallback
        public void onClick(int i, int i2, ItemQuranSearch itemQuranSearch) {
            QuranSearchActivity.this.indexSurah = itemQuranSearch.getSurahIndex();
            if (!QuranSearchActivity.this.isFullSurah) {
                QuranSearchActivity quranSearchActivity = QuranSearchActivity.this;
                int to = itemQuranSearch.getTo() - 1;
                quranSearchActivity.mTo = to;
                quranSearchActivity.mFrom = to;
                QuranSearchActivity.this.btnDone.performClick();
                return;
            }
            QuranSearchActivity.this.mFrom = i;
            QuranSearchActivity.this.mTo = i2;
            if (QuranSearchActivity.this.btnDone.getVisibility() != 0) {
                QuranSearchActivity.this.btnDone.setVisibility(0);
            }
            QuranSearchActivity.this.btnDone.setText(QuranSearchActivity.this.getResources().getString(R.string.from_to, Integer.valueOf(i + 1), Integer.valueOf(i2 + 1)));
        }
    };

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.ContextThemeWrapper, android.content.ContextWrapper
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(LocaleHelper.onAttach(context));
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        EdgeToEdge.enable(this);
        getOnBackPressedDispatcher().addCallback(this, this.onBackPressedCallback);
        setContentView(R.layout.activity_quran_search);
        setStatusBarColor(-15658732);
        setNavigationBarColor(-15658732);
        WindowInsetsControllerCompat insetsController = WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(false);
        insetsController.setAppearanceLightNavigationBars(false);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), new OnApplyWindowInsetsListener() { // from class: hazem.nurmontage.videoquran.QuranSearchActivity$$ExternalSyntheticLambda1
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                return QuranSearchActivity.lambda$onCreate$0(view, windowInsetsCompat);
            }
        });
        this.surahNames = getResources().getStringArray(R.array.sura_names);
        findViewById(R.id.btn_onBack).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.QuranSearchActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                QuranSearchActivity.this.m843x103c83(view);
            }
        });
        ButtonCustumFont buttonCustumFont = (ButtonCustumFont) findViewById(R.id.btn_done);
        this.btnDone = buttonCustumFont;
        buttonCustumFont.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.QuranSearchActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (QuranSearchActivity.this.mFrom == -1) {
                    return;
                }
                QuranSearchActivity quranSearchActivity = QuranSearchActivity.this;
                QuranPreference.savePreferencesSearch(quranSearchActivity, quranSearchActivity.indexSurah, QuranSearchActivity.this.mFrom, QuranSearchActivity.this.mTo, QuranSearchActivity.this.editText.getText().toString());
                QuranSearchActivity.this.setResult(-1);
                QuranSearchActivity.this.finish();
            }
        });
        this.searchProgressBar = (ProgressBar) findViewById(R.id.progress);
        this.countAya = (TextView) findViewById(R.id.tv_count_aya);
        ((TextCustumFont) findViewById(R.id.tv_tittle)).setText(getResources().getString(R.string.search));
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_search_quran);
        this.recyclerView = recyclerView;
        recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        this.recyclerView.setItemViewCacheSize(20);
        this.recyclerView.setDrawingCacheEnabled(true);
        this.recyclerView.setItemAnimator(null);
        this.recyclerView.setDrawingCacheQuality(1048576);
        SearchQuranAdabters searchQuranAdabters = new SearchQuranAdabters(this.iSearchQuranCallback);
        this.searchQuranAdapter = searchQuranAdabters;
        this.recyclerView.setAdapter(searchQuranAdabters);
        EditText editText = (EditText) findViewById(R.id.edt_search_quran);
        this.editText = editText;
        editText.setHint(getResources().getString(R.string.hint_search_quran));
        this.editText.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "fonts/ReadexPro_Medium.ttf"));
        this.editText.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: hazem.nurmontage.videoquran.QuranSearchActivity$$ExternalSyntheticLambda3
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                return QuranSearchActivity.this.m844xff99d684(textView, i, keyEvent);
            }
        });
        findViewById(R.id.btn_search).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.QuranSearchActivity$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                QuranSearchActivity.this.m845xff237085(view);
            }
        });
        lastSearch();
    }

    static /* synthetic */ WindowInsetsCompat lambda$onCreate$0(View view, WindowInsetsCompat windowInsetsCompat) {
        Insets insets = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.systemBars());
        view.setPadding(insets.left, insets.top, insets.right, insets.bottom);
        return windowInsetsCompat;
    }

    /* renamed from: lambda$onCreate$1$hazem-nurmontage-videoquran-QuranSearchActivity, reason: not valid java name */
    /* synthetic */ void m843x103c83(View view) {
        closeKeyboard();
        setResult(0);
        finish();
    }

    /* renamed from: lambda$onCreate$2$hazem-nurmontage-videoquran-QuranSearchActivity, reason: not valid java name */
    /* synthetic */ boolean m844xff99d684(TextView textView, int i, KeyEvent keyEvent) {
        if (i != 3) {
            return false;
        }
        closeKeyboard();
        try {
            performSearch();
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return true;
        }
    }

    /* renamed from: lambda$onCreate$3$hazem-nurmontage-videoquran-QuranSearchActivity, reason: not valid java name */
    /* synthetic */ void m845xff237085(View view) {
        closeKeyboard();
        try {
            performSearch();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    private void lastSearch() {
        String lastSearch = QuranPreference.getLastSearch(this);
        if (lastSearch == null || TextUtils.isEmpty(lastSearch)) {
            return;
        }
        try {
            this.editText.setText(lastSearch);
            performSearch();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        this.editText.requestFocus();
        showKeyboard();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateCount() {
        this.countAya.setText("الآيـــات : (" + this.searchQuranAdapter.getSize() + ")");
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        closeKeyboard();
        super.onPause();
    }

    public void showKeyboard() {
        try {
            ((InputMethodManager) getSystemService("input_method")).showSoftInput(this.editText, 1);
        } catch (Exception unused) {
        }
    }

    public void closeKeyboard() {
        try {
            ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(this.editText.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resetFromTo() {
        this.mFrom = -1;
        this.mTo = -1;
        this.btnDone.setVisibility(8);
    }

    private void performSearch() throws InterruptedException {
        int indexSurah;
        resetFromTo();
        String trim = this.editText.getText().toString().trim();
        if (trim.isEmpty() || trim.equals("--") || !Utils.isProbablyLArabic(trim)) {
            return;
        }
        String str = this.lastSearchKey;
        if (str == null || !str.equals(trim)) {
            this.lastSearchKey = trim.replace("\"", "");
            SearchQuranAdabters searchQuranAdabters = this.searchQuranAdapter;
            if (searchQuranAdabters != null) {
                searchQuranAdabters.clear();
            }
            String[] split = this.lastSearchKey.split(" ");
            if (split.length == 1) {
                indexSurah = this.lastSearchKey.contains("عمران") ? 3 : getIndexSurah(split[0]);
                if (indexSurah != -1) {
                    this.indexSurah = indexSurah;
                    this.indexAya = -1;
                    searchBySurah();
                    return;
                }
            } else if (split.length == 2) {
                indexSurah = this.lastSearchKey.contains("عمران") ? 3 : getIndexSurah(split[0]);
                try {
                    int parseInt = Integer.parseInt(split[1]);
                    if (indexSurah != -1) {
                        this.indexSurah = indexSurah;
                        this.indexAya = parseInt;
                        searchBySurah();
                        return;
                    }
                } catch (NumberFormatException unused) {
                }
            }
            this.isFullSurah = false;
            if (this.javaBM == null) {
                this.javaBM = new JavaBM();
            }
            this.javaBM.setmPattern(RemoveTashkeel.removeTashkeel(this.lastSearchKey));
            searchAllQuran();
        }
    }

    private String normalizeArabic(String str) {
        if (str == null) {
            return "";
        }
        String trim = str.trim();
        if (trim.startsWith("ال")) {
            trim = trim.substring(2);
        }
        return trim.replace("أ", "ا").replace("إ", "ا").replace("آ", "ا").replace("ى", "ي").replace("ة", "ه").replaceAll("[\\u064B-\\u065F]", "");
    }

    private int getIndexSurah(String str) {
        String normalizeArabic = normalizeArabic(str);
        int i = 0;
        while (true) {
            String[] strArr = this.surahNames;
            if (i >= strArr.length) {
                return -1;
            }
            if (normalizeArabic(strArr[i].split("-")[0].trim()).contains(normalizeArabic)) {
                return i;
            }
            i++;
        }
    }

    private void searchAllQuran() throws InterruptedException {
        stopCurrentSearchThread();
        this.isRun = true;
        Thread thread = new Thread(new Runnable() { // from class: hazem.nurmontage.videoquran.QuranSearchActivity$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                QuranSearchActivity.this.m849xd4a8c370();
            }
        });
        this.thread = thread;
        thread.start();
    }

    /* renamed from: lambda$searchAllQuran$7$hazem-nurmontage-videoquran-QuranSearchActivity, reason: not valid java name */
    /* synthetic */ void m849xd4a8c370() {
        Handler handler;
        Runnable runnable;
        String readLine;
        this.handler.post(new Runnable() { // from class: hazem.nurmontage.videoquran.QuranSearchActivity$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                QuranSearchActivity.this.m846xd60bf56d();
            }
        });
        try {
            try {
                this.in_quran = getAssets().open("quran/quran-simple.txt");
                this.bufferedReader_quran = new BufferedReader(new InputStreamReader(this.in_quran));
                while (this.isRun && (readLine = this.bufferedReader_quran.readLine()) != null && !readLine.isEmpty()) {
                    String[] split = readLine.split("\\|");
                    if (split.length >= 3) {
                        final int parseInt = Integer.parseInt(split[0]) - 1;
                        final int parseInt2 = Integer.parseInt(split[1]) - 1;
                        final String str = split[2];
                        final String removeTashkeel = RemoveTashkeel.removeTashkeel((parseInt > 0 && parseInt2 == 0 && str.contains("بِّسْمِ اللَّهِ")) ? str.substring(40) : str);
                        final int match = this.javaBM.match(removeTashkeel);
                        if (match != -1) {
                            this.handler.post(new Runnable() { // from class: hazem.nurmontage.videoquran.QuranSearchActivity$$ExternalSyntheticLambda6
                                @Override // java.lang.Runnable
                                public final void run() {
                                    QuranSearchActivity.this.m847xd5958f6e(match, removeTashkeel, str, parseInt, parseInt2);
                                }
                            });
                        }
                    }
                }
                closeQuranStreams();
                handler = this.handler;
                runnable = new Runnable() { // from class: hazem.nurmontage.videoquran.QuranSearchActivity$$ExternalSyntheticLambda7
                    @Override // java.lang.Runnable
                    public final void run() {
                        QuranSearchActivity.this.m848xd51f296f();
                    }
                };
            } catch (IOException e) {
                System.err.println("Error reading Quran file: " + e.getMessage());
                e.printStackTrace();
                closeQuranStreams();
                handler = this.handler;
                runnable = new Runnable() { // from class: hazem.nurmontage.videoquran.QuranSearchActivity$$ExternalSyntheticLambda7
                    @Override // java.lang.Runnable
                    public final void run() {
                        QuranSearchActivity.this.m848xd51f296f();
                    }
                };
            }
            handler.post(runnable);
        } catch (Throwable th) {
            closeQuranStreams();
            this.handler.post(new Runnable() { // from class: hazem.nurmontage.videoquran.QuranSearchActivity$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    QuranSearchActivity.this.m848xd51f296f();
                }
            });
            throw th;
        }
    }

    /* renamed from: lambda$searchAllQuran$4$hazem-nurmontage-videoquran-QuranSearchActivity, reason: not valid java name */
    /* synthetic */ void m846xd60bf56d() {
        ProgressBar progressBar = this.searchProgressBar;
        if (progressBar != null) {
            progressBar.setVisibility(0);
        }
    }

    /* renamed from: lambda$searchAllQuran$5$hazem-nurmontage-videoquran-QuranSearchActivity, reason: not valid java name */
    /* synthetic */ void m847xd5958f6e(int i, String str, String str2, int i2, int i3) {
        if (this.searchQuranAdapter != null) {
            int countIndex = Utils.countIndex(Utils.countSpace(i, str), str2);
            this.searchQuranAdapter.add(new ItemQuranSearch(str2, this.surahNames[i2], i3 + 1, i2, countIndex, Utils.countIndex(countIndex, Utils.countSpace(this.javaBM.getmPattern()), str2)));
            updateCount();
        }
    }

    /* renamed from: lambda$searchAllQuran$6$hazem-nurmontage-videoquran-QuranSearchActivity, reason: not valid java name */
    /* synthetic */ void m848xd51f296f() {
        SearchQuranAdabters searchQuranAdabters = this.searchQuranAdapter;
        if (searchQuranAdabters != null && searchQuranAdabters.getSize() == 0) {
            updateCount();
        }
        ProgressBar progressBar = this.searchProgressBar;
        if (progressBar != null) {
            progressBar.setVisibility(8);
        }
    }

    private void closeQuranStreams() {
        try {
            BufferedReader bufferedReader = this.bufferedReader_quran;
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            InputStream inputStream = this.in_quran;
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void searchBySurah() throws InterruptedException {
        this.isFullSurah = true;
        if (this.runnableBySurah == null) {
            this.runnableBySurah = new Runnable() { // from class: hazem.nurmontage.videoquran.QuranSearchActivity.3
                @Override // java.lang.Runnable
                public void run() {
                    String readLine;
                    QuranSearchActivity.this.handler.post(new Runnable() { // from class: hazem.nurmontage.videoquran.QuranSearchActivity.3.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (QuranSearchActivity.this.searchProgressBar != null) {
                                QuranSearchActivity.this.searchProgressBar.setVisibility(0);
                            }
                        }
                    });
                    try {
                        try {
                            try {
                                QuranSearchActivity quranSearchActivity = QuranSearchActivity.this;
                                quranSearchActivity.in_quran = quranSearchActivity.getAssets().open("quran/quran-simple.txt");
                                QuranSearchActivity.this.bufferedReader_quran = new BufferedReader(new InputStreamReader(QuranSearchActivity.this.in_quran));
                                while (QuranSearchActivity.this.isRun && (readLine = QuranSearchActivity.this.bufferedReader_quran.readLine()) != null && !readLine.isEmpty()) {
                                    final String[] split = readLine.split("\\|");
                                    if (split.length < 3) {
                                        break;
                                    }
                                    final int parseInt = Integer.parseInt(split[0]) - 1;
                                    final int parseInt2 = Integer.parseInt(split[1]);
                                    if (parseInt == QuranSearchActivity.this.indexSurah && (QuranSearchActivity.this.indexAya == -1 || QuranSearchActivity.this.indexAya == parseInt2)) {
                                        QuranSearchActivity.this.handler.post(new Runnable() { // from class: hazem.nurmontage.videoquran.QuranSearchActivity.3.2
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                if (QuranSearchActivity.this.searchQuranAdapter != null) {
                                                    QuranSearchActivity.this.searchQuranAdapter.add(new ItemQuranSearch(split[2], QuranSearchActivity.this.surahNames[QuranSearchActivity.this.indexSurah], parseInt2, parseInt, -1, -1));
                                                    QuranSearchActivity.this.updateCount();
                                                }
                                            }
                                        });
                                        if (QuranSearchActivity.this.indexAya != -1) {
                                            break;
                                        }
                                    }
                                }
                                if (QuranSearchActivity.this.bufferedReader_quran != null) {
                                    QuranSearchActivity.this.bufferedReader_quran.close();
                                }
                                if (QuranSearchActivity.this.in_quran != null) {
                                    QuranSearchActivity.this.in_quran.close();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                                return;
                            }
                        } catch (Exception e2) {
                            try {
                                System.out.println(e2);
                                if (QuranSearchActivity.this.bufferedReader_quran != null) {
                                    QuranSearchActivity.this.bufferedReader_quran.close();
                                }
                                if (QuranSearchActivity.this.in_quran != null) {
                                    QuranSearchActivity.this.in_quran.close();
                                }
                            } catch (IOException e3) {
                                e3.printStackTrace();
                            }
                        }
                        QuranSearchActivity.this.handler.post(new Runnable() { // from class: hazem.nurmontage.videoquran.QuranSearchActivity.3.3
                            @Override // java.lang.Runnable
                            public void run() {
                                if (QuranSearchActivity.this.searchQuranAdapter != null && QuranSearchActivity.this.searchQuranAdapter.getSize() == 0) {
                                    QuranSearchActivity.this.updateCount();
                                }
                                if (QuranSearchActivity.this.searchProgressBar != null) {
                                    QuranSearchActivity.this.searchProgressBar.setVisibility(8);
                                }
                            }
                        });
                    } catch (Exception unused) {
                        try {
                            if (QuranSearchActivity.this.bufferedReader_quran != null) {
                                QuranSearchActivity.this.bufferedReader_quran.close();
                            }
                            if (QuranSearchActivity.this.in_quran != null) {
                                QuranSearchActivity.this.in_quran.close();
                            }
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }
                }
            };
        }
        if (this.thread != null) {
            try {
                this.isRun = false;
                this.thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.isRun = true;
        Thread thread = new Thread(this.runnableBySurah);
        this.thread = thread;
        thread.start();
    }

    private void stopCurrentSearchThread() {
        if (this.thread != null) {
            try {
                this.isRun = false;
                this.thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
    }
}

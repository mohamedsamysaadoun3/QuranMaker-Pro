package hazem.nurmontage.videoquran;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
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
import com.arthenica.ffmpegkit.StreamInformation;
import hazem.nurmontage.videoquran.Utils.LocaleHelper;
import hazem.nurmontage.videoquran.Utils.Utils;
import hazem.nurmontage.videoquran.adabter.ColorBgAdabter;
import hazem.nurmontage.videoquran.common.Common;
import hazem.nurmontage.videoquran.constant.SurahNameStyle;
import hazem.nurmontage.videoquran.views.CheckboxCustumFont;
import hazem.nurmontage.videoquran.views.EditTextCustumFont;
import hazem.nurmontage.videoquran.views.TextCustumFont;
import hazem.nurmontage.videoquran.views.TextCustumFontBold;
import java.util.Objects;

/* loaded from: classes2.dex */
public class EditS_NameActivity extends Base {
    private ColorBgAdabter adapter;
    private CheckboxCustumFont checkBg;
    private int clrBg;
    private EditTextCustumFont editText;
    private int index_surah;
    private Resources mResources;
    private RecyclerView recyclerView;
    private int style;
    private TextCustumFont tvOption1;
    private TextCustumFont tvOption2;
    private final OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) { // from class: hazem.nurmontage.videoquran.EditS_NameActivity.1
        @Override // androidx.activity.OnBackPressedCallback
        public void handleOnBackPressed() {
            EditS_NameActivity.this.closeKeyboard();
            EditS_NameActivity.this.setResult(0, null);
            EditS_NameActivity.this.overridePendingTransition(0, 0);
            EditS_NameActivity.this.finish();
        }
    };
    final int[] BG_COLORS = {-8388608, -1, ViewCompat.MEASURED_STATE_MASK, -2838729, -16777088, -16694239, -13220529, -9404272};
    private final ColorBgAdabter.IColor iColor = new ColorBgAdabter.IColor() { // from class: hazem.nurmontage.videoquran.EditS_NameActivity.8
        @Override // hazem.nurmontage.videoquran.adabter.ColorBgAdabter.IColor
        public void onColor(int i, int i2) {
            EditS_NameActivity.this.clrBg = i;
            EditS_NameActivity.this.scrollToSelectedPosition();
        }
    };

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.ContextThemeWrapper, android.content.ContextWrapper
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(LocaleHelper.onAttach(context));
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        String str;
        super.onCreate(bundle);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_sname);
        getOnBackPressedDispatcher().addCallback(this, this.onBackPressedCallback);
        WindowInsetsControllerCompat insetsController = WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(false);
        insetsController.setAppearanceLightNavigationBars(false);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), new OnApplyWindowInsetsListener() { // from class: hazem.nurmontage.videoquran.EditS_NameActivity$$ExternalSyntheticLambda0
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                return EditS_NameActivity.lambda$onCreate$0(view, windowInsetsCompat);
            }
        });
        setStatusBarColor(-15658735);
        setNavigationBarColor(-14935010);
        Resources resources = getResources();
        this.mResources = resources;
        if (resources == null) {
            finish();
        }
        TextCustumFontBold textCustumFontBold = (TextCustumFontBold) findViewById(R.id.tv_tittle);
        TextCustumFontBold textCustumFontBold2 = (TextCustumFontBold) findViewById(R.id.tv_reader_name);
        TextCustumFontBold textCustumFontBold3 = (TextCustumFontBold) findViewById(R.id.tv_add_bg);
        textCustumFontBold3.setText(this.mResources.getString(R.string.add_bg));
        textCustumFontBold.setText(this.mResources.getString(R.string.edit_and_style));
        textCustumFontBold2.setText(this.mResources.getString(R.string.reader_name));
        this.checkBg = (CheckboxCustumFont) findViewById(R.id.checkbox_bg);
        EditTextCustumFont editTextCustumFont = (EditTextCustumFont) findViewById(R.id.edt_reader);
        this.editText = editTextCustumFont;
        editTextCustumFont.requestFocus();
        String stringExtra = getIntent().getStringExtra("reader_name");
        String stringExtra2 = getIntent().getStringExtra("surah_name");
        this.style = getIntent().getIntExtra("style", 0);
        this.clrBg = getIntent().getIntExtra("clrBg", ViewCompat.MEASURED_STATE_MASK);
        this.checkBg.setChecked(getIntent().getBooleanExtra("isBg", false));
        int intExtra = getIntent().getIntExtra(StreamInformation.KEY_INDEX, 0);
        this.index_surah = intExtra;
        if (intExtra == 0) {
            this.index_surah = findWordIndex_Loop(stringExtra2);
        }
        if (stringExtra2 != null && stringExtra2.length() > 3) {
            this.editText.setText(stringExtra);
        }
        showKeyboard();
        this.tvOption1 = (TextCustumFont) findViewById(R.id.tv_option_1);
        this.tvOption2 = (TextCustumFont) findViewById(R.id.tv_option_2);
        this.tvOption1.setText(stringExtra2);
        this.tvOption2.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "fonts/surah_name.otf"));
        int i = this.index_surah;
        if (i < 10) {
            str = "00" + this.index_surah + "sura";
        } else if (i < 100) {
            str = "0" + this.index_surah + "sura";
        } else {
            str = this.index_surah + "sura";
        }
        this.tvOption2.setText(str);
        if (this.style == 1) {
            selectOption(this.tvOption2, this.tvOption1);
        }
        this.tvOption1.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.EditS_NameActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                EditS_NameActivity.this.style = SurahNameStyle.NONE.ordinal();
                EditS_NameActivity editS_NameActivity = EditS_NameActivity.this;
                editS_NameActivity.selectOption(editS_NameActivity.tvOption1, EditS_NameActivity.this.tvOption2);
            }
        });
        this.tvOption2.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.EditS_NameActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                EditS_NameActivity.this.style = SurahNameStyle.ZAGHRAFAT.ordinal();
                EditS_NameActivity editS_NameActivity = EditS_NameActivity.this;
                editS_NameActivity.selectOption(editS_NameActivity.tvOption2, EditS_NameActivity.this.tvOption1);
            }
        });
        findViewById(R.id.btn_on_back).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.EditS_NameActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                EditS_NameActivity.this.onBackPressedCallback.handleOnBackPressed();
            }
        });
        findViewById(R.id.btn_done).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.EditS_NameActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(Common.READER, ((Editable) Objects.requireNonNull(EditS_NameActivity.this.editText.getText())).toString());
                intent.putExtra("style", EditS_NameActivity.this.style);
                intent.putExtra(StreamInformation.KEY_INDEX, EditS_NameActivity.this.index_surah);
                intent.putExtra("isBg", EditS_NameActivity.this.checkBg.isChecked());
                intent.putExtra("clrBg", EditS_NameActivity.this.clrBg);
                EditS_NameActivity.this.setResult(-1, intent);
                EditS_NameActivity.this.overridePendingTransition(0, 0);
                EditS_NameActivity.this.finish();
            }
        });
        textCustumFontBold3.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.EditS_NameActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                EditS_NameActivity.this.checkBg.setChecked(!EditS_NameActivity.this.checkBg.isChecked());
            }
        });
        initRv();
        this.checkBg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: hazem.nurmontage.videoquran.EditS_NameActivity.7
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                EditS_NameActivity.this.updateColorUI(z);
            }
        });
        updateColorUI(this.checkBg.isChecked());
    }

    static /* synthetic */ WindowInsetsCompat lambda$onCreate$0(View view, WindowInsetsCompat windowInsetsCompat) {
        Insets insets = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.systemBars());
        view.setPadding(insets.left, insets.top, insets.right, insets.bottom);
        return windowInsetsCompat;
    }

    private void initRv() {
        this.recyclerView = (RecyclerView) findViewById(R.id.rv_color);
        ColorBgAdabter.IColor iColor = this.iColor;
        int[] iArr = this.BG_COLORS;
        this.adapter = new ColorBgAdabter(iColor, iArr, Utils.indexOf(iArr, this.clrBg));
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this, 0, LocaleHelper.getLanguage(this).equals("ar")));
        this.recyclerView.setItemAnimator(null);
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setAdapter(this.adapter);
        scrollToSelectedPosition();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateColorUI(boolean z) {
        this.recyclerView.setEnabled(z);
        this.recyclerView.animate().alpha(z ? 1.0f : 0.4f).setDuration(180L).start();
        this.adapter.setEnabled(z);
    }

    public void scrollToSelectedPosition() {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) this.recyclerView.getLayoutManager();
        if (linearLayoutManager != null) {
            linearLayoutManager.scrollToPositionWithOffset(this.adapter.getPos_select(), (this.recyclerView.getWidth() / 2) - 50);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void selectOption(TextCustumFont textCustumFont, TextCustumFont textCustumFont2) {
        textCustumFont.setBackgroundResource(R.drawable.bg_option_surah_selected);
        textCustumFont.setTextColor(-1);
        textCustumFont2.setBackgroundResource(R.drawable.bg_option_surah_unselected);
        textCustumFont2.setTextColor(Color.parseColor("#888888"));
    }

    public int findWordIndex_Loop(String str) {
        String[] stringArray = getResources().getStringArray(R.array.surah_names_merged);
        if (str == null) {
            return -1;
        }
        for (int i = 0; i < stringArray.length; i++) {
            if (str.contains(stringArray[i])) {
                return i + 1;
            }
        }
        return -1;
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        closeKeyboard();
        super.onPause();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
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
}

package hazem.nurmontage.videoquran;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.media3.common.MimeTypes;

/* loaded from: classes2.dex */
public class AddReaderNameActivity extends Base {
    private EditText editText;
    private final OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) { // from class: hazem.nurmontage.videoquran.AddReaderNameActivity.1
        @Override // androidx.activity.OnBackPressedCallback
        public void handleOnBackPressed() {
            AddReaderNameActivity.this.closeKeyboard();
            Intent intent = new Intent();
            intent.putExtra("name", AddReaderNameActivity.this.getIntent().getStringExtra("name"));
            intent.putExtra(MimeTypes.BASE_TYPE_AUDIO, AddReaderNameActivity.this.getIntent().getStringExtra(MimeTypes.BASE_TYPE_AUDIO));
            intent.putExtra("path_video_copy", AddReaderNameActivity.this.getIntent().getStringExtra("path_video_copy"));
            AddReaderNameActivity.this.setResult(-1, intent);
            AddReaderNameActivity.this.finish();
        }
    };

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_reader_name);
        getOnBackPressedDispatcher().addCallback(this, this.onBackPressedCallback);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), new OnApplyWindowInsetsListener() { // from class: hazem.nurmontage.videoquran.AddReaderNameActivity$$ExternalSyntheticLambda0
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                return AddReaderNameActivity.lambda$onCreate$0(view, windowInsetsCompat);
            }
        });
        hideSystemBars();
        findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.AddReaderNameActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                AddReaderNameActivity.this.onBackPressedCallback.handleOnBackPressed();
            }
        });
        findViewById(R.id.btn_done).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.AddReaderNameActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                AddReaderNameActivity.this.closeKeyboard();
                Intent intent = new Intent();
                intent.putExtra("name", AddReaderNameActivity.this.editText.getText().toString().trim().replaceAll("\n", " "));
                intent.putExtra(MimeTypes.BASE_TYPE_AUDIO, AddReaderNameActivity.this.getIntent().getStringExtra(MimeTypes.BASE_TYPE_AUDIO));
                intent.putExtra("path_video_copy", AddReaderNameActivity.this.getIntent().getStringExtra("path_video_copy"));
                AddReaderNameActivity.this.setResult(-1, intent);
                AddReaderNameActivity.this.finish();
            }
        });
        EditText editText = (EditText) findViewById(R.id.edt_reader);
        this.editText = editText;
        editText.requestFocus();
        String stringExtra = getIntent().getStringExtra("name");
        if (stringExtra != null && stringExtra.length() > 3) {
            this.editText.setText(stringExtra);
        }
        showKeyboard();
    }

    static /* synthetic */ WindowInsetsCompat lambda$onCreate$0(View view, WindowInsetsCompat windowInsetsCompat) {
        Insets insets = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.systemBars());
        view.setPadding(insets.left, insets.top, insets.right, insets.bottom);
        return windowInsetsCompat;
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

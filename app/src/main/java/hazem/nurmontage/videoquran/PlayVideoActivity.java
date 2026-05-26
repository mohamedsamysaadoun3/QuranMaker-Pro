package hazem.nurmontage.videoquran;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;
import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

/* loaded from: classes2.dex */
public class PlayVideoActivity extends Base {
    private MediaController mediaController;
    private final OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) { // from class: hazem.nurmontage.videoquran.PlayVideoActivity.1
        @Override // androidx.activity.OnBackPressedCallback
        public void handleOnBackPressed() {
            PlayVideoActivity.this.pause();
            PlayVideoActivity.this.finish();
        }
    };
    private RelativeLayout parentLayout;
    private VideoView videoView;

    /* JADX INFO: Access modifiers changed from: private */
    public void adjustVideoViewSize(MediaPlayer mediaPlayer) {
        if (mediaPlayer == null) {
            return;
        }
        int videoWidth = mediaPlayer.getVideoWidth();
        int videoHeight = mediaPlayer.getVideoHeight();
        int width = this.parentLayout.getWidth();
        int height = this.parentLayout.getHeight();
        float f = videoWidth / videoHeight;
        float f2 = width;
        float f3 = height;
        if (f > f2 / f3) {
            height = (int) (f2 / f);
        } else {
            width = (int) (f3 * f);
        }
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, height);
        layoutParams.addRule(13);
        this.videoView.setLayoutParams(layoutParams);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pause() {
        VideoView videoView = this.videoView;
        if (videoView == null || !videoView.isPlaying()) {
            return;
        }
        this.videoView.pause();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        pause();
        super.onPause();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        VideoView videoView = this.videoView;
        if (videoView != null) {
            videoView.pause();
            this.videoView = null;
        }
        super.onDestroy();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        Uri data;
        super.onCreate(bundle);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_play_video);
        getOnBackPressedDispatcher().addCallback(this, this.onBackPressedCallback);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        setStatusBarColor(ViewCompat.MEASURED_STATE_MASK);
        setNavigationBarColor(ViewCompat.MEASURED_STATE_MASK);
        WindowInsetsControllerCompat insetsController = WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(false);
        insetsController.setAppearanceLightNavigationBars(false);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), new OnApplyWindowInsetsListener() { // from class: hazem.nurmontage.videoquran.PlayVideoActivity$$ExternalSyntheticLambda0
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                return PlayVideoActivity.lambda$onCreate$0(view, windowInsetsCompat);
            }
        });
        this.parentLayout = (RelativeLayout) findViewById(R.id.parentLayout);
        if (getIntent() != null && (data = getIntent().getData()) != null) {
            this.videoView = (VideoView) findViewById(R.id.videoView);
            MediaController mediaController = new MediaController(this);
            this.mediaController = mediaController;
            mediaController.setMediaPlayer(this.videoView);
            this.mediaController.setAnchorView(this.videoView);
            this.videoView.setMediaController(this.mediaController);
            this.videoView.setVideoURI(data);
            this.videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // from class: hazem.nurmontage.videoquran.PlayVideoActivity.2
                @Override // android.media.MediaPlayer.OnCompletionListener
                public void onCompletion(MediaPlayer mediaPlayer) {
                    if (PlayVideoActivity.this.mediaController == null || PlayVideoActivity.this.mediaController.isShowing()) {
                        return;
                    }
                    PlayVideoActivity.this.mediaController.show();
                }
            });
            this.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: hazem.nurmontage.videoquran.PlayVideoActivity.3
                @Override // android.media.MediaPlayer.OnPreparedListener
                public void onPrepared(MediaPlayer mediaPlayer) {
                    PlayVideoActivity.this.adjustVideoViewSize(mediaPlayer);
                }
            });
            this.videoView.start();
        }
        findViewById(R.id.btn_on_back).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.PlayVideoActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PlayVideoActivity.this.pause();
                PlayVideoActivity.this.finish();
            }
        });
    }

    static /* synthetic */ WindowInsetsCompat lambda$onCreate$0(View view, WindowInsetsCompat windowInsetsCompat) {
        Insets insets = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.systemBars());
        view.setPadding(insets.left, insets.top, insets.right, insets.bottom);
        return windowInsetsCompat;
    }
}

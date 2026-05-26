package hazem.nurmontage.videoquran;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.activity.OnBackPressedCallback;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.media3.common.MediaItem;
import androidx.media3.common.PlaybackException;
import androidx.media3.common.Player;
import androidx.media3.exoplayer.DefaultRenderersFactory;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

/* loaded from: classes2.dex */
public class VideoPlayerActivity extends Base {
    private ImageButton btnPlay;
    private final OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) { // from class: hazem.nurmontage.videoquran.VideoPlayerActivity.1
        @Override // androidx.activity.OnBackPressedCallback
        public void handleOnBackPressed() {
            VideoPlayerActivity.this.releasePlayer();
            VideoPlayerActivity.this.returnAct();
        }
    };
    private ExoPlayer player;
    private PlayerView playerView;
    private Uri videoUri;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        hideSystemUI();
        setContentView(R.layout.activity_video_player);
        getOnBackPressedDispatcher().addCallback(this, this.onBackPressedCallback);
        this.videoUri = getIntent().getData();
        this.playerView = (PlayerView) findViewById(R.id.playerView);
        setupButtons();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStart() {
        super.onStart();
        initializePlayer();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        ExoPlayer exoPlayer = this.player;
        if (exoPlayer != null) {
            exoPlayer.play();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        ExoPlayer exoPlayer = this.player;
        if (exoPlayer != null) {
            exoPlayer.pause();
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStop() {
        super.onStop();
        releasePlayer();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        hideSystemBars();
    }

    private void initializePlayer() {
        if (this.player != null || this.videoUri == null) {
            return;
        }
        ExoPlayer build = new ExoPlayer.Builder(this).setRenderersFactory(new DefaultRenderersFactory(this).setEnableDecoderFallback(true)).setSeekBackIncrementMs(5000L).setSeekForwardIncrementMs(5000L).build();
        this.player = build;
        this.playerView.setPlayer(build);
        this.player.setMediaItem(MediaItem.fromUri(this.videoUri));
        this.player.setRepeatMode(1);
        this.player.prepare();
        this.player.play();
        this.player.addListener(new Player.Listener() { // from class: hazem.nurmontage.videoquran.VideoPlayerActivity.2
            @Override // androidx.media3.common.Player.Listener
            public void onPlayerError(PlaybackException playbackException) {
                VideoPlayerActivity.this.retryWithFallbackDecoder();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void retryWithFallbackDecoder() {
        if (this.videoUri == null) {
            return;
        }
        ExoPlayer build = new ExoPlayer.Builder(this).setRenderersFactory(new DefaultRenderersFactory(this).setEnableDecoderFallback(true)).build();
        this.playerView.setPlayer(build);
        build.setMediaItem(MediaItem.fromUri(this.videoUri));
        build.prepare();
        build.play();
        this.player.release();
        this.player = build;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void releasePlayer() {
        if (this.player != null) {
            this.playerView.setUseController(false);
            this.playerView.setPlayer(null);
            this.player.release();
            this.player = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void returnAct() {
        finish();
    }

    private void setupButtons() {
        ImageButton imageButton = (ImageButton) this.playerView.findViewById(R.id.btnBack);
        ImageButton imageButton2 = (ImageButton) this.playerView.findViewById(R.id.btnRotate);
        this.btnPlay = (ImageButton) findViewById(R.id.btn_play_pause);
        imageButton.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.VideoPlayerActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                VideoPlayerActivity.this.m920x289c8f84(view);
            }
        });
        imageButton2.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.VideoPlayerActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                VideoPlayerActivity.this.m921x28262985(view);
            }
        });
        this.btnPlay.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.VideoPlayerActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (VideoPlayerActivity.this.player != null) {
                    if (VideoPlayerActivity.this.player.isPlaying()) {
                        VideoPlayerActivity.this.player.pause();
                        VideoPlayerActivity.this.btnPlay.setImageResource(R.drawable.play_arrow_24px);
                    } else {
                        VideoPlayerActivity.this.player.play();
                        VideoPlayerActivity.this.btnPlay.setImageResource(R.drawable.pause_24px);
                    }
                }
            }
        });
    }

    /* renamed from: lambda$setupButtons$0$hazem-nurmontage-videoquran-VideoPlayerActivity, reason: not valid java name */
    /* synthetic */ void m920x289c8f84(View view) {
        releasePlayer();
        returnAct();
    }

    /* renamed from: lambda$setupButtons$1$hazem-nurmontage-videoquran-VideoPlayerActivity, reason: not valid java name */
    /* synthetic */ void m921x28262985(View view) {
        if (getRequestedOrientation() == 0) {
            setRequestedOrientation(1);
        } else {
            setRequestedOrientation(0);
        }
    }

    private void hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        WindowInsetsControllerCompat insetsController = WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        insetsController.hide(WindowInsetsCompat.Type.systemBars());
        insetsController.setSystemBarsBehavior(2);
        setStatusBarColor(ViewCompat.MEASURED_STATE_MASK);
        setNavigationBarColor(ViewCompat.MEASURED_STATE_MASK);
    }
}

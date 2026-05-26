package hazem.nurmontage.videoquran;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.VectorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.InputDeviceCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.media3.common.MimeTypes;
import androidx.media3.exoplayer.upstream.CmcdData;
import androidx.media3.extractor.metadata.icy.IcyHeaders;
import com.arthenica.ffmpegkit.FFmpegKit;
import com.arthenica.ffmpegkit.FFmpegSession;
import com.arthenica.ffmpegkit.FFmpegSessionCompleteCallback;
import com.arthenica.ffmpegkit.ReturnCode;
import com.arthenica.ffmpegkit.StreamInformation;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import hazem.nurmontage.videoquran.Utils.AspectRatioCalculator;
import hazem.nurmontage.videoquran.Utils.AudioUtils;
import hazem.nurmontage.videoquran.Utils.BillingPreferences;
import hazem.nurmontage.videoquran.Utils.BitmapCropper;
import hazem.nurmontage.videoquran.Utils.ColorUtils;
import hazem.nurmontage.videoquran.Utils.DrawableHelper;
import hazem.nurmontage.videoquran.Utils.FileHelper;
import hazem.nurmontage.videoquran.Utils.FileUtils;
import hazem.nurmontage.videoquran.Utils.LocalPersistence;
import hazem.nurmontage.videoquran.Utils.LocaleHelper;
import hazem.nurmontage.videoquran.Utils.MyPrefereces;
import hazem.nurmontage.videoquran.Utils.MyVibrationHelper;
import hazem.nurmontage.videoquran.Utils.NetworkUtils;
import hazem.nurmontage.videoquran.Utils.PCMWaveformExtractor;
import hazem.nurmontage.videoquran.Utils.ScreenUtils;
import hazem.nurmontage.videoquran.Utils.SmoothTimelineAnimator;
import hazem.nurmontage.videoquran.Utils.SmoothVideoAnimator;
import hazem.nurmontage.videoquran.Utils.TimeFormatter;
import hazem.nurmontage.videoquran.Utils.Utils;
import hazem.nurmontage.videoquran.Utils.UtilsBitmap;
import hazem.nurmontage.videoquran.Utils.UtilsFileLast;
import hazem.nurmontage.videoquran.adabter.DimensionAdabters;
import hazem.nurmontage.videoquran.common.Common;
import hazem.nurmontage.videoquran.constant.AyaTextPreset;
import hazem.nurmontage.videoquran.constant.EffectAudioType;
import hazem.nurmontage.videoquran.constant.EntityAction;
import hazem.nurmontage.videoquran.constant.IpadType;
import hazem.nurmontage.videoquran.constant.ResizeType;
import hazem.nurmontage.videoquran.constant.SurahNameStyle;
import hazem.nurmontage.videoquran.entity_timeline.Entity;
import hazem.nurmontage.videoquran.entity_timeline.EntityAudio;
import hazem.nurmontage.videoquran.entity_timeline.EntityBismilahTimeline;
import hazem.nurmontage.videoquran.entity_timeline.EntityQuranTimeline;
import hazem.nurmontage.videoquran.entity_timeline.EntityTrslTimeline;
import hazem.nurmontage.videoquran.fragment.AddAudioFragment;
import hazem.nurmontage.videoquran.fragment.AddQuranFragment;
import hazem.nurmontage.videoquran.fragment.ChangeBgFragment;
import hazem.nurmontage.videoquran.fragment.ColorAyaFragment;
import hazem.nurmontage.videoquran.fragment.ColorBismilahFragment;
import hazem.nurmontage.videoquran.fragment.ColorS_NameFragment;
import hazem.nurmontage.videoquran.fragment.ColorTrslAyaFragment;
import hazem.nurmontage.videoquran.fragment.EditBismilahEntityFragment;
import hazem.nurmontage.videoquran.fragment.EditEntityFragment;
import hazem.nurmontage.videoquran.fragment.EditIconQuranFragment;
import hazem.nurmontage.videoquran.fragment.EditIpadFragment;
import hazem.nurmontage.videoquran.fragment.EditMediaFragment;
import hazem.nurmontage.videoquran.fragment.EditMultipleEntityFragment;
import hazem.nurmontage.videoquran.fragment.EditS_NameFragment;
import hazem.nurmontage.videoquran.fragment.EditTextFragment;
import hazem.nurmontage.videoquran.fragment.EditTrslEntityFragment;
import hazem.nurmontage.videoquran.fragment.EffectAyaFragment;
import hazem.nurmontage.videoquran.fragment.EffectBismilahFragment;
import hazem.nurmontage.videoquran.fragment.FontFragment;
import hazem.nurmontage.videoquran.fragment.ProgressViewFragment;
import hazem.nurmontage.videoquran.fragment.ResizeFragment;
import hazem.nurmontage.videoquran.fragment.SimpleProgressViewFragment;
import hazem.nurmontage.videoquran.fragment.audio_effect.EchoEffectFragment;
import hazem.nurmontage.videoquran.fragment.audio_effect.EnhanceVoiceFragment;
import hazem.nurmontage.videoquran.fragment.audio_effect.FadeInOutFragment;
import hazem.nurmontage.videoquran.fragment.audio_effect.PitchFragment;
import hazem.nurmontage.videoquran.fragment.audio_effect.RemoveNoiceFragment;
import hazem.nurmontage.videoquran.fragment.audio_effect.ReverbePresetFragment;
import hazem.nurmontage.videoquran.fragment.audio_effect.SpeedFragment;
import hazem.nurmontage.videoquran.fragment.audio_effect.VolumeFragment;
import hazem.nurmontage.videoquran.model.BgItem;
import hazem.nurmontage.videoquran.model.BismilahEntity;
import hazem.nurmontage.videoquran.model.EffectAudio;
import hazem.nurmontage.videoquran.model.EntityBismilahTemplate;
import hazem.nurmontage.videoquran.model.EntityMedia;
import hazem.nurmontage.videoquran.model.EntityProgressTemplate;
import hazem.nurmontage.videoquran.model.EntityQuranTemplate;
import hazem.nurmontage.videoquran.model.EntitySurahTemplate;
import hazem.nurmontage.videoquran.model.EntityTranslationTemplate;
import hazem.nurmontage.videoquran.model.EntityView;
import hazem.nurmontage.videoquran.model.Gradient;
import hazem.nurmontage.videoquran.model.MRectF;
import hazem.nurmontage.videoquran.model.QuranEntity;
import hazem.nurmontage.videoquran.model.RecitersModel;
import hazem.nurmontage.videoquran.model.SurahNameEntity;
import hazem.nurmontage.videoquran.model.Template;
import hazem.nurmontage.videoquran.model.Transition;
import hazem.nurmontage.videoquran.model.TranslationQuranEntity;
import hazem.nurmontage.videoquran.views.BlurredImageView;
import hazem.nurmontage.videoquran.views.ButtonCustumFont;
import hazem.nurmontage.videoquran.views.CustomDiscreteSeekBar;
import hazem.nurmontage.videoquran.views.TextCustumFont;
import hazem.nurmontage.videoquran.views.TextCustumFontBold;
import hazem.nurmontage.videoquran.views.TrackEntityView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import kotlin.Pair;

/* loaded from: classes2.dex */
public class EngineActivity extends Base {
    private static final int EXTRACT_AUDIO_VIDEO_PERMISSION_REQUEST_CODE = 12;
    private static final int FPS = 25;
    private static final int IMAGE_PERMISSION_REQUEST_CODE = 10;
    private static final int REQUEST_CODE_AUDIO = 2;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private static final int VIDEO_PERMISSION_REQUEST_CODE = 11;
    private ActivityResultLauncher<Intent> activityLauncher;
    private SmoothVideoAnimator animator_frame_video;
    private BlurredImageView blurredImageView;
    private LinearLayout btnChangeResize;
    private LinearLayout btnIpod;
    private ImageButton btnPlayPause;
    private ImageButton btnRedo;
    private ImageButton btnToEnd;
    private ImageButton btnToStart;
    private ImageButton btnUndo;
    private ImageButton btn_cancel;
    private ButtonCustumFont btn_export;
    private LinearLayout btn_setup_fps;
    private Dialog dialog;
    private Dialog dialogInternet;
    private int endFrame;
    private int endTimeAudioVisible;
    private EntityAudio entityAudio_player;
    private EntityAudio entityAudio_visible;
    private boolean isOnScroll;
    private boolean isToCrop;
    private ImageView ivIpod;
    private ImageView ivResize;
    private int lastIndexVisible;
    private LinearLayout layout_resolution;
    private Fragment mCurrentFragment;
    private boolean mIsPlaying;
    private MediaPlayer mPlayer;
    private Resources mResources;
    private Template mTemplate;
    private boolean oneExport;
    private CustomDiscreteSeekBar seekBar_fps;
    private CustomDiscreteSeekBar seekBar_res;
    private TextCustumFont textChangeResize;
    private TimeFormatter timeFormatter;
    private TrackEntityView trackViewEntity;
    private TextView tv_currentTime;
    private TextView tv_endTime;
    private TextCustumFont tv_resolution;
    private TextCustumFont tv_tittle_fragment;
    private String uri_bg;
    private SmoothTimelineAnimator valueAnimator;
    private MyVibrationHelper vibrationHelper;
    private boolean isSaveTmpTemplate = true;
    private Executor executor = Executors.newSingleThreadExecutor();
    private List<Long> id_ffmpeg = new ArrayList();
    private int current_position_time = 0;
    private int startCursur = 0;
    private OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) { // from class: hazem.nurmontage.videoquran.EngineActivity.1
        @Override // androidx.activity.OnBackPressedCallback
        public void handleOnBackPressed() {
            if (EngineActivity.this.mCurrentFragment != null) {
                EngineActivity.this.hideFragment();
            } else {
                EngineActivity.this.dialog();
            }
        }
    };
    private TrackEntityView.ITrimLineCallback iTrimLineCallback = new TrackEntityView.ITrimLineCallback() { // from class: hazem.nurmontage.videoquran.EngineActivity.60
        @Override // hazem.nurmontage.videoquran.views.TrackEntityView.ITrimLineCallback
        public void fadeInAudio(float f) {
        }

        @Override // hazem.nurmontage.videoquran.views.TrackEntityView.ITrimLineCallback
        public void fadeOutAudio(float f) {
        }

        @Override // hazem.nurmontage.videoquran.views.TrackEntityView.ITrimLineCallback
        public void onMove() {
        }

        @Override // hazem.nurmontage.videoquran.views.TrackEntityView.ITrimLineCallback
        public void onUpdatePlayerAudio(EntityAudio entityAudio) {
        }

        @Override // hazem.nurmontage.videoquran.views.TrackEntityView.ITrimLineCallback
        public void onSelectMultiple(int i) {
            EngineActivity.this.showEditMultipleEntity(i);
        }

        @Override // hazem.nurmontage.videoquran.views.TrackEntityView.ITrimLineCallback
        public void onDelete(EntityView entityView) {
            try {
                EngineActivity.this.blurredImageView.setEntity_select(null);
                EngineActivity.this.blurredImageView.postInvalidate();
                EngineActivity.this.hideFragment();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override // hazem.nurmontage.videoquran.views.TrackEntityView.ITrimLineCallback
        public void onEmptySelect() {
            EngineActivity.this.blurredImageView.setEntity_select(null);
            EngineActivity.this.blurredImageView.postInvalidate();
            EngineActivity.this.pausePlayer();
            EngineActivity.this.hideFragment();
        }

        @Override // hazem.nurmontage.videoquran.views.TrackEntityView.ITrimLineCallback
        public void onUpdate() {
            if (EngineActivity.this.blurredImageView != null) {
                EngineActivity.this.blurredImageView.postInvalidate();
            }
        }

        @Override // hazem.nurmontage.videoquran.views.TrackEntityView.ITrimLineCallback
        public void onUp() {
            EngineActivity.this.isOnScroll = false;
            EngineActivity.this.updateBtnCutState();
        }

        @Override // hazem.nurmontage.videoquran.views.TrackEntityView.ITrimLineCallback
        public void onAddStack(EntityAction entityAction) {
            EngineActivity.this.enableUndoBtn();
        }

        @Override // hazem.nurmontage.videoquran.views.TrackEntityView.ITrimLineCallback
        public void onSeekPlayer(float f) {
            try {
                EngineActivity.this.isOnScroll = true;
                for (EntityAudio entityAudio : EngineActivity.this.trackViewEntity.getEntityListAudio()) {
                    try {
                        if (entityAudio.getMediaPlayer() != null && entityAudio.getMediaPlayer().isPlaying()) {
                            entityAudio.getMediaPlayer().pause();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (EngineActivity.this.mIsPlaying) {
                    if (EngineActivity.this.btnPlayPause != null) {
                        EngineActivity.this.btnPlayPause.setImageResource(R.drawable.play_btn);
                    }
                    EngineActivity.this.mIsPlaying = false;
                    EngineActivity.this.trackViewEntity.setPlaying(false);
                    EngineActivity.this.blurredImageView.setPlaying(false);
                }
                EngineActivity.this.pauseTimelineAnimation();
                EngineActivity.this.stop();
                int round = Math.round(Math.abs((f / EngineActivity.this.trackViewEntity.getSecond_in_screen()) * (-1000.0f)));
                if (EngineActivity.this.blurredImageView != null && (round <= EngineActivity.this.trackViewEntity.getMaxTime() || EngineActivity.this.blurredImageView.getProgress() < 1.0f)) {
                    float min = Math.min(1.0f, round / EngineActivity.this.trackViewEntity.getMaxTime());
                    EngineActivity.this.updateTime(round);
                    EngineActivity.this.blurredImageView.setProgress(min);
                }
                EngineActivity.this.trackViewEntity.update_current_cursur_position(round);
                EngineActivity.this.current_position_time = (int) System.currentTimeMillis();
                EngineActivity engineActivity = EngineActivity.this;
                engineActivity.startCursur = engineActivity.trackViewEntity.getCurrent_cursur_position();
                EngineActivity engineActivity2 = EngineActivity.this;
                engineActivity2.updateViewTime(engineActivity2.trackViewEntity.getMaxTime(), EngineActivity.this.trackViewEntity.getCurrent_cursur_position());
                EngineActivity.this.updateBtnCutState();
                EngineActivity.this.updateBtnToStart();
                EngineActivity.this.updateBtnToEnd();
                EngineActivity.this.updateFrame();
            } catch (Exception unused) {
            }
        }

        @Override // hazem.nurmontage.videoquran.views.TrackEntityView.ITrimLineCallback
        public void pause() {
            EngineActivity.this.pausePlayer();
        }

        @Override // hazem.nurmontage.videoquran.views.TrackEntityView.ITrimLineCallback
        public void onPlayVibration() {
            EngineActivity.this.pausePlayer();
            EngineActivity.this.runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.60.1
                @Override // java.lang.Runnable
                public void run() {
                    if (EngineActivity.this.vibrationHelper != null) {
                        EngineActivity.this.vibrationHelper.vibrate();
                    }
                }
            });
        }

        @Override // hazem.nurmontage.videoquran.views.TrackEntityView.ITrimLineCallback
        public void onSelectEntity(Entity entity, float f) {
            EngineActivity.this.pausePlayer();
            if (entity instanceof EntityQuranTimeline) {
                EngineActivity.this.blurredImageView.setEntity_select(entity.getEntityView());
                EngineActivity.this.blurredImageView.invalidate();
                if (EditEntityFragment.instance != null) {
                    EditEntityFragment.instance.checkSplitEntity(entity, -EngineActivity.this.trackViewEntity.getCurrentPosition());
                    EditEntityFragment.instance.checkIcon(entity);
                    return;
                } else if (EditTextFragment.instance != null) {
                    EditTextFragment.instance.update(((EntityQuranTimeline) entity).getQuranEntity());
                    return;
                } else {
                    EngineActivity.this.showEditEntity(entity);
                    return;
                }
            }
            if (entity instanceof EntityTrslTimeline) {
                EngineActivity.this.blurredImageView.setEntity_select(entity.getEntityView());
                EngineActivity.this.blurredImageView.invalidate();
                if (EditTrslEntityFragment.instance != null) {
                    EditTrslEntityFragment.instance.checkSplitEntity(entity, -EngineActivity.this.trackViewEntity.getCurrentPosition());
                    return;
                } else {
                    EngineActivity.this.showEditTrslEntity(entity);
                    return;
                }
            }
            if (entity instanceof EntityBismilahTimeline) {
                EngineActivity.this.blurredImageView.setEntity_select(entity.getEntityView());
                EngineActivity.this.blurredImageView.invalidate();
                EngineActivity.this.showEditBismilahEntity(entity);
            } else if (entity instanceof EntityAudio) {
                EntityAudio entityAudio = (EntityAudio) entity;
                if (EditMediaFragment.instance != null) {
                    EditMediaFragment.instance.checkSplit(entityAudio, -EngineActivity.this.trackViewEntity.getCurrentPosition());
                } else {
                    EngineActivity.this.showEditAudioEntity(entityAudio);
                }
            }
        }

        @Override // hazem.nurmontage.videoquran.views.TrackEntityView.ITrimLineCallback
        public void enableRedo(boolean z) {
            if (z) {
                EngineActivity.this.enableRedoBtn();
            } else {
                EngineActivity.this.disableRedoBtn();
            }
        }

        @Override // hazem.nurmontage.videoquran.views.TrackEntityView.ITrimLineCallback
        public void enableUndo(boolean z) {
            if (z) {
                EngineActivity.this.enableUndoBtn();
            } else {
                EngineActivity.this.disableUndoBtn();
            }
        }

        @Override // hazem.nurmontage.videoquran.views.TrackEntityView.ITrimLineCallback
        public void progress(final boolean z) {
            EngineActivity.this.runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.60.2
                @Override // java.lang.Runnable
                public void run() {
                    if (z) {
                        EngineActivity.this.showProgress();
                    } else {
                        EngineActivity.this.hideProgressFragment();
                    }
                }
            });
        }

        @Override // hazem.nurmontage.videoquran.views.TrackEntityView.ITrimLineCallback
        public void onUpdateTime() {
            EngineActivity engineActivity = EngineActivity.this;
            engineActivity.startCursur = engineActivity.trackViewEntity.getCurrent_cursur_position();
            EngineActivity.this.updateTime();
        }
    };
    private AddQuranFragment.IAddQuran iAddQuran = new AddQuranFragment.IAddQuran() { // from class: hazem.nurmontage.videoquran.EngineActivity.65
        @Override // hazem.nurmontage.videoquran.fragment.AddQuranFragment.IAddQuran
        public void onVuCopyRight() {
            EngineActivity.this.dialogCopyRight();
        }

        @Override // hazem.nurmontage.videoquran.fragment.AddQuranFragment.IAddQuran
        public void progress() {
            EngineActivity.this.runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.65.1
                @Override // java.lang.Runnable
                public void run() {
                    EngineActivity.this.showProgress();
                }
            });
        }

        @Override // hazem.nurmontage.videoquran.fragment.AddQuranFragment.IAddQuran
        public void onSearch() {
            EngineActivity.this.isToCrop = true;
            EngineActivity.this.searchAyaResult.launch(new Intent(EngineActivity.this, (Class<?>) QuranSearchActivity.class));
        }

        @Override // hazem.nurmontage.videoquran.fragment.AddQuranFragment.IAddQuran
        public void uploadRecitation() {
            try {
                FragmentTransaction beginTransaction = EngineActivity.this.getSupportFragmentManager().beginTransaction();
                EngineActivity engineActivity = EngineActivity.this;
                engineActivity.mCurrentFragment = AddAudioFragment.getInstance(engineActivity.iAudioCallback, EngineActivity.this.mResources);
                beginTransaction.replace(R.id.m_container, EngineActivity.this.mCurrentFragment);
                beginTransaction.commit();
                EngineActivity engineActivity2 = EngineActivity.this;
                engineActivity2.setupShowFragment(engineActivity2.mResources.getString(R.string.audio));
            } catch (Exception unused) {
            }
        }

        @Override // hazem.nurmontage.videoquran.fragment.AddQuranFragment.IAddQuran
        public void onAddTranslation(String str, int i, boolean z) {
            EngineActivity.this.addTranslationEntity(str, i, z);
        }

        @Override // hazem.nurmontage.videoquran.fragment.AddQuranFragment.IAddQuran
        public void onAdd(String str, String str2, String str3, String str4, int i, int i2, String str5, int i3, int i4) {
            EngineActivity.this.addEntity(str, str2 + " " + i2, str3, str4, i, i2, str5, i3, i4);
        }

        @Override // hazem.nurmontage.videoquran.fragment.AddQuranFragment.IAddQuran
        public void onDone(String str, int i, String str2, Uri uri, String str3) {
            EngineActivity.this.runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.65.2
                @Override // java.lang.Runnable
                public void run() {
                    EngineActivity.this.hideFragment();
                }
            });
            EngineActivity.this.blurredImageView.updateSizeAya();
            EngineActivity.this.blurredImageView.updateSizeAyaTrsl();
            EngineActivity.this.blurredImageView.setSurahNameEntity(str, str2, null, 1.0f, "خط الإبل.otf", EngineActivity.this.blurredImageView.getClr_aya(), AyaTextPreset.NONE.ordinal(), EngineActivity.this.blurredImageView.getSurahNameEntity() != null ? EngineActivity.this.blurredImageView.getSurahNameEntity().getStyle() : SurahNameStyle.NONE.ordinal(), i, EngineActivity.this.blurredImageView.getSurahNameEntity() != null && EngineActivity.this.blurredImageView.getSurahNameEntity().isHaveBg(), EngineActivity.this.blurredImageView.getSurahNameEntity() != null ? EngineActivity.this.blurredImageView.getSurahNameEntity().getClrBg() : ViewCompat.MEASURED_STATE_MASK);
            if (str3 == null) {
                EngineActivity.this.addAudio(uri);
            } else {
                EngineActivity.this.addAudioFromVideo(uri, str3);
            }
        }

        @Override // hazem.nurmontage.videoquran.fragment.AddQuranFragment.IAddQuran
        public void onBismilah() {
            boolean addEntityIste3adha = EngineActivity.this.addEntityIste3adha();
            boolean addEntityBissmilah = EngineActivity.this.addEntityBissmilah();
            if (!addEntityIste3adha || !addEntityBissmilah) {
                EngineActivity.this.trackViewEntity.translateToRight(addEntityIste3adha);
            } else {
                EngineActivity.this.trackViewEntity.translateToRight();
            }
        }

        @Override // hazem.nurmontage.videoquran.fragment.AddQuranFragment.IAddQuran
        public void onDone(String str, int i, String str2, List<RecitersModel> list) {
            EngineActivity.this.runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.65.3
                @Override // java.lang.Runnable
                public void run() {
                    EngineActivity.this.hideFragment();
                }
            });
            EngineActivity.this.blurredImageView.updateSizeAya();
            EngineActivity.this.blurredImageView.updateSizeAyaTrsl();
            EngineActivity.this.blurredImageView.setSurahNameEntity(str, str2, null, 1.0f, "خط الإبل.otf", EngineActivity.this.blurredImageView.getClr_aya(), AyaTextPreset.NONE.ordinal(), EngineActivity.this.blurredImageView.getSurahNameEntity() != null ? EngineActivity.this.blurredImageView.getSurahNameEntity().getStyle() : SurahNameStyle.NONE.ordinal(), i, EngineActivity.this.blurredImageView.getSurahNameEntity() != null && EngineActivity.this.blurredImageView.getSurahNameEntity().isHaveBg(), EngineActivity.this.blurredImageView.getSurahNameEntity() != null ? EngineActivity.this.blurredImageView.getSurahNameEntity().getClrBg() : ViewCompat.MEASURED_STATE_MASK);
            if (NetworkUtils.isNetworkAvailable(EngineActivity.this) && list != null && !list.isEmpty()) {
                EngineActivity.this.addAudioReciters(list);
            } else {
                EngineActivity.this.runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.65.4
                    @Override // java.lang.Runnable
                    public void run() {
                        EngineActivity.this.updateTimeToEndAya();
                        EngineActivity.this.updateBtnToEnd();
                        EngineActivity.this.updateBtnToStart();
                        EngineActivity.this.hideProgressFragment();
                    }
                });
            }
        }

        @Override // hazem.nurmontage.videoquran.fragment.AddQuranFragment.IAddQuran
        public void onCancel() {
            EngineActivity.this.hideFragment();
        }

        @Override // hazem.nurmontage.videoquran.fragment.AddQuranFragment.IAddQuran
        public void onErrorLimitation() {
            EngineActivity.this.runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.65.5
                @Override // java.lang.Runnable
                public void run() {
                    Toast.makeText(EngineActivity.this, EngineActivity.this.mResources.getString(R.string.error_limit), 0).show();
                }
            });
        }

        @Override // hazem.nurmontage.videoquran.fragment.AddQuranFragment.IAddQuran
        public void onAddReaderName(String str, String str2, Uri uri) {
            EngineActivity.this.isToCrop = true;
            Intent intent = new Intent(EngineActivity.this, (Class<?>) AddReaderNameActivity.class);
            intent.putExtra("name", str);
            if (uri != null) {
                intent.putExtra(MimeTypes.BASE_TYPE_AUDIO, uri.toString());
            }
            intent.putExtra("path_video_copy", str2);
            EngineActivity.this.nameReaderResult.launch(intent);
        }
    };
    private ActivityResultLauncher<Intent> searchAyaResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() { // from class: hazem.nurmontage.videoquran.EngineActivity.68
        @Override // androidx.activity.result.ActivityResultCallback
        public void onActivityResult(ActivityResult activityResult) {
            EngineActivity.this.isToCrop = false;
            try {
                if (AddQuranFragment.instance != null) {
                    AddQuranFragment.instance.addAyaIndex();
                } else {
                    FragmentTransaction beginTransaction = EngineActivity.this.getSupportFragmentManager().beginTransaction();
                    EngineActivity engineActivity = EngineActivity.this;
                    engineActivity.mCurrentFragment = AddQuranFragment.getInstance(engineActivity.iAddQuran, EngineActivity.this.mResources);
                    beginTransaction.replace(R.id.m_container, EngineActivity.this.mCurrentFragment);
                    beginTransaction.commit();
                    EngineActivity.this.runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.68.1
                        @Override // java.lang.Runnable
                        public void run() {
                            EngineActivity.this.setupShowFragment(EngineActivity.this.mResources.getString(R.string.quran));
                        }
                    });
                }
            } catch (Exception unused) {
            }
        }
    });
    private ActivityResultLauncher<Intent> nameReaderResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() { // from class: hazem.nurmontage.videoquran.EngineActivity.69
        @Override // androidx.activity.result.ActivityResultCallback
        public void onActivityResult(ActivityResult activityResult) {
            EngineActivity.this.isToCrop = false;
            Intent data = activityResult.getData();
            if (data != null) {
                if (AddQuranFragment.instance != null) {
                    Uri parse = Uri.parse(data.getStringExtra(MimeTypes.BASE_TYPE_AUDIO));
                    String stringExtra = data.getStringExtra("path_video_copy");
                    AddQuranFragment.instance.setNameReader(data.getStringExtra("name"), parse, stringExtra);
                    return;
                }
                try {
                    Uri parse2 = Uri.parse(data.getStringExtra(MimeTypes.BASE_TYPE_AUDIO));
                    String stringExtra2 = data.getStringExtra("path_video_copy");
                    String stringExtra3 = data.getStringExtra("name");
                    FragmentTransaction beginTransaction = EngineActivity.this.getSupportFragmentManager().beginTransaction();
                    EngineActivity engineActivity = EngineActivity.this;
                    engineActivity.mCurrentFragment = AddQuranFragment.getInstance(engineActivity.iAddQuran, EngineActivity.this.mResources, parse2, stringExtra2, stringExtra3);
                    beginTransaction.replace(R.id.m_container, EngineActivity.this.mCurrentFragment);
                    beginTransaction.commit();
                    EngineActivity.this.runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.69.1
                        @Override // java.lang.Runnable
                        public void run() {
                            EngineActivity.this.setupShowFragment(EngineActivity.this.mResources.getString(R.string.quran));
                        }
                    });
                } catch (Exception unused) {
                }
            }
        }
    });
    private ActivityResultLauncher<Intent> editSurahNameResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() { // from class: hazem.nurmontage.videoquran.EngineActivity.70
        @Override // androidx.activity.result.ActivityResultCallback
        public void onActivityResult(ActivityResult activityResult) {
            Intent data;
            EngineActivity.this.isToCrop = false;
            if (activityResult.getResultCode() != -1 || (data = activityResult.getData()) == null) {
                return;
            }
            String stringExtra = data.getStringExtra(Common.READER);
            boolean booleanExtra = data.getBooleanExtra("isBg", false);
            int intExtra = data.getIntExtra("style", 0);
            if (EngineActivity.this.blurredImageView.getSurahNameEntity().getIndex_surah() == 0) {
                EngineActivity.this.blurredImageView.getSurahNameEntity().setIndex_surah(data.getIntExtra(StreamInformation.KEY_INDEX, 1));
            }
            EngineActivity.this.blurredImageView.getSurahNameEntity().setClrBg(data.getIntExtra("clrBg", ViewCompat.MEASURED_STATE_MASK));
            if (intExtra == SurahNameStyle.NONE.ordinal()) {
                EngineActivity.this.blurredImageView.getSurahNameEntity().setAlignment(EngineActivity.this.blurredImageView.updateAlignmentSurah(stringExtra));
            }
            EngineActivity.this.blurredImageView.getSurahNameEntity().setStyle(EngineActivity.this, intExtra, stringExtra, booleanExtra);
            EngineActivity.this.blurredImageView.invalidate();
        }
    });
    private ActivityResultLauncher<Intent> editTrslResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() { // from class: hazem.nurmontage.videoquran.EngineActivity.71
        @Override // androidx.activity.result.ActivityResultCallback
        public void onActivityResult(ActivityResult activityResult) {
            Intent data;
            EngineActivity.this.isToCrop = false;
            if (activityResult.getResultCode() != -1 || (data = activityResult.getData()) == null) {
                return;
            }
            String stringExtra = data.getStringExtra(Common.READER);
            boolean booleanExtra = data.getBooleanExtra("isBg", true);
            TranslationQuranEntity translationQuranEntity = (TranslationQuranEntity) EngineActivity.this.blurredImageView.getEntity_select();
            translationQuranEntity.setClrBg(data.getIntExtra("clrBg", ViewCompat.MEASURED_STATE_MASK));
            translationQuranEntity.setTxt(stringExtra);
            translationQuranEntity.setHaveBg(booleanExtra);
            EngineActivity.this.blurredImageView.invalidate();
        }
    });
    private ChangeBgFragment.IChangeBgCallback iChangeBgCallback = new AnonymousClass77();
    private DimensionAdabters.IDimensionCallback iDimensionCallback = new AnonymousClass78();
    private AddAudioFragment.IAudioCallback iAudioCallback = new AddAudioFragment.IAudioCallback() { // from class: hazem.nurmontage.videoquran.EngineActivity.79
        @Override // hazem.nurmontage.videoquran.fragment.AddAudioFragment.IAudioCallback
        public void upload() {
            if (EngineActivity.this.checkPermissionAudio()) {
                EngineActivity.this.pickAudio();
            }
        }

        @Override // hazem.nurmontage.videoquran.fragment.AddAudioFragment.IAudioCallback
        public void extract() {
            EngineActivity.this.pickVideoForAudio();
        }

        @Override // hazem.nurmontage.videoquran.fragment.AddAudioFragment.IAudioCallback
        public void cancel() {
            EngineActivity.this.hideFragment();
            try {
                EngineActivity engineActivity = EngineActivity.this;
                engineActivity.setupShowFragment(engineActivity.mResources.getString(R.string.quran));
                FragmentTransaction beginTransaction = EngineActivity.this.getSupportFragmentManager().beginTransaction();
                EngineActivity engineActivity2 = EngineActivity.this;
                engineActivity2.mCurrentFragment = AddQuranFragment.getInstance(engineActivity2.iAddQuran, EngineActivity.this.mResources);
                beginTransaction.replace(R.id.m_container, EngineActivity.this.mCurrentFragment);
                beginTransaction.commit();
            } catch (Exception unused) {
            }
        }
    };
    private EditIpadFragment.IIpadEditCallback iIpadEditCallback = new EditIpadFragment.IIpadEditCallback() { // from class: hazem.nurmontage.videoquran.EngineActivity.80
        @Override // hazem.nurmontage.videoquran.fragment.EditIpadFragment.IIpadEditCallback
        public void onClick(int i, int i2) {
            EngineActivity.this.mTemplate.setColor_ipad(i);
            EngineActivity.this.mTemplate.setIndex_color(i2);
            EngineActivity.this.mTemplate.setGradient(null);
            EngineActivity.this.blurredImageView.setColorIpad(i);
            EngineActivity.this.blurredImageView.invalidate();
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditIpadFragment.IIpadEditCallback
        public void onClick(Gradient gradient, int i) {
            EngineActivity.this.mTemplate.setGradient(gradient);
            EngineActivity.this.mTemplate.setIndex_color(i);
            EngineActivity.this.blurredImageView.setColorIpad(gradient);
            EngineActivity.this.blurredImageView.invalidate();
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditIpadFragment.IIpadEditCallback
        public void onDialogPremium() {
            EngineActivity.this.dialogPremium(0);
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditIpadFragment.IIpadEditCallback
        public void onGlassType(boolean z) {
            EngineActivity.this.mTemplate.setGlass(z);
            EngineActivity.this.blurredImageView.setGlass(z);
            EngineActivity.this.blurredImageView.invalidate();
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditIpadFragment.IIpadEditCallback
        public void onChangeType(int i) {
            if (EngineActivity.this.blurredImageView.getmIpadType() == i) {
                return;
            }
            if (EditIpadFragment.instance != null) {
                EditIpadFragment.instance.scrollToSelectedPosition();
            }
            try {
                EngineActivity.this.mTemplate.setIpad_type(i);
                EngineActivity.this.blurredImageView.changeTypeIpad(i);
                if (EngineActivity.this.mTemplate.isVideoSquare()) {
                    if (i != IpadType.GRADIENT.ordinal() && i != IpadType.BLACK_LAYER.ordinal() && i != IpadType.MASK_BRUSH.ordinal() && i != IpadType.BLUE_TYPE.ordinal() && i != IpadType.CASSET_IMG.ordinal()) {
                        if (EngineActivity.this.mTemplate.getIpad_type() == IpadType.CASSET_IMG_BLUR.ordinal()) {
                            EngineActivity.this.blurredImageView.setBitmapSquare(EngineActivity.this.blurredImageView.getBitmapBlured());
                            EngineActivity.this.blurredImageView.setRadius_square(0);
                        }
                    }
                    EngineActivity.this.blurredImageView.setBitmapSquare(EngineActivity.this.blurredImageView.getBitmapNotBlur());
                    EngineActivity.this.blurredImageView.setRadius_square(0);
                }
                if (i == IpadType.IPAD.ordinal() || i == IpadType.IPAD_UNBLUR.ordinal()) {
                    int width = (int) (EngineActivity.this.blurredImageView.getIpad_rect().width() * 0.87530595f);
                    int i2 = (int) (width * 1.13f);
                    int min = (int) (Math.min(width, i2) * 0.10800001f);
                    int round = Math.round(EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth() * EngineActivity.this.mTemplate.getX_square());
                    int round2 = Math.round(EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight() * EngineActivity.this.mTemplate.getY_square());
                    int i3 = width + round;
                    if (i3 > EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth()) {
                        round -= i3 - EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth();
                        i3 = EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth();
                    }
                    int i4 = i2 + round2;
                    if (i4 > EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight()) {
                        round2 -= i4 - EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight();
                        i4 = EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight();
                    }
                    if (round < 0) {
                        round = 0;
                    }
                    if (round2 < 0) {
                        round2 = 0;
                    }
                    Rect rect = new Rect(round, round2, i3, i4);
                    int width2 = (int) (EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth() * EngineActivity.this.mTemplate.getWidth_square());
                    int height = (int) (EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight() * EngineActivity.this.mTemplate.getHeight_square());
                    EngineActivity.this.blurredImageView.setBitmapSquare(UtilsBitmap.cropToSquareWithRoundCorners(EngineActivity.this.blurredImageView.getBitmapOriginal(), rect, min, width2, height));
                    EngineActivity.this.blurredImageView.setRadius_square(min);
                    rect.right = rect.left + width2;
                    rect.bottom = rect.top + height;
                    EngineActivity.this.blurredImageView.setRectSquare(rect);
                }
                if (i == IpadType.IPAD_CLASSIC.ordinal()) {
                    int width3 = (int) (EngineActivity.this.blurredImageView.getIpad_rect().width() * 0.87530595f);
                    int i5 = (int) (width3 * 1.13f);
                    int round3 = Math.round(EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth() * EngineActivity.this.mTemplate.getX_square());
                    int round4 = Math.round(EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight() * EngineActivity.this.mTemplate.getY_square());
                    int i6 = width3 + round3;
                    if (i6 > EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth()) {
                        round3 -= i6 - EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth();
                        i6 = EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth();
                    }
                    int i7 = i5 + round4;
                    if (i7 > EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight()) {
                        round4 -= i7 - EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight();
                        i7 = EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight();
                    }
                    if (round3 < 0) {
                        round3 = 0;
                    }
                    if (round4 < 0) {
                        round4 = 0;
                    }
                    Rect rect2 = new Rect(round3, round4, i6, i7);
                    int width4 = (int) (EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth() * EngineActivity.this.mTemplate.getWidth_square());
                    int height2 = (int) (EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight() * EngineActivity.this.mTemplate.getHeight_square());
                    EngineActivity.this.blurredImageView.setBitmapSquare(UtilsBitmap.cropToSquare(EngineActivity.this.blurredImageView.getBitmapOriginal(), rect2, width4, height2));
                    EngineActivity.this.blurredImageView.setRadius_square(0);
                    rect2.right = rect2.left + width4;
                    rect2.bottom = rect2.top + height2;
                    EngineActivity.this.blurredImageView.setRectSquare(rect2);
                }
                if (i == IpadType.IPAD_NEOMORPHIC.ordinal()) {
                    int width5 = (int) (EngineActivity.this.blurredImageView.getIpad_rect().width() * 0.6f);
                    int round5 = Math.round(EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth() * EngineActivity.this.mTemplate.getX_square());
                    int round6 = Math.round(EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight() * EngineActivity.this.mTemplate.getY_square());
                    int i8 = width5 + round5;
                    if (i8 > EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth()) {
                        round5 -= i8 - EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth();
                        i8 = EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth();
                    }
                    int i9 = width5 + round6;
                    if (i9 > EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight()) {
                        round6 -= i9 - EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight();
                        i9 = EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight();
                    }
                    if (round5 < 0) {
                        round5 = 0;
                    }
                    if (round6 < 0) {
                        round6 = 0;
                    }
                    Rect rect3 = new Rect(round5, round6, i8, i9);
                    int width6 = (int) (EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth() * EngineActivity.this.mTemplate.getWidth_square());
                    int height3 = (int) (EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight() * EngineActivity.this.mTemplate.getHeight_square());
                    EngineActivity.this.blurredImageView.setBitmapSquare(UtilsBitmap.cropToSquareWithRoundCorners(EngineActivity.this.blurredImageView.getBitmapOriginal(), rect3, width5, width6, height3));
                    EngineActivity.this.blurredImageView.setRadius_square(width5);
                    rect3.right = rect3.left + width6;
                    rect3.bottom = rect3.top + height3;
                    EngineActivity.this.blurredImageView.setRectSquare(rect3);
                }
                if (i == IpadType.BOTTOM_RECT.ordinal()) {
                    int width7 = (int) (EngineActivity.this.blurredImageView.getIpad_rect().width() * 1.0f);
                    int height4 = (int) (EngineActivity.this.blurredImageView.getBitmapBlured().getHeight() * 0.5355f);
                    int round7 = Math.round(EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth() * EngineActivity.this.mTemplate.getX_square());
                    int round8 = Math.round(EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight() * EngineActivity.this.mTemplate.getY_square());
                    int i10 = width7 + round7;
                    if (i10 > EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth()) {
                        round7 -= i10 - EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth();
                        i10 = EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth();
                    }
                    int i11 = height4 + round8;
                    if (i11 > EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight()) {
                        round8 -= i11 - EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight();
                        i11 = EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight();
                    }
                    if (round7 < 0) {
                        round7 = 0;
                    }
                    if (round8 < 0) {
                        round8 = 0;
                    }
                    Rect rect4 = new Rect(round7, round8, i10, i11);
                    int width8 = (int) (EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth() * EngineActivity.this.mTemplate.getWidth_square());
                    int height5 = (int) (EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight() * EngineActivity.this.mTemplate.getHeight_square());
                    EngineActivity.this.blurredImageView.setBitmapSquare(UtilsBitmap.cropToSquare(EngineActivity.this.blurredImageView.getBitmapOriginal(), rect4, width8, height5));
                    EngineActivity.this.blurredImageView.setRadius_square(0);
                    rect4.right = rect4.left + width8;
                    rect4.bottom = rect4.top + height5;
                    EngineActivity.this.blurredImageView.setRectSquare(rect4);
                }
                if (i == IpadType.BORDER.ordinal()) {
                    if (ColorUtils.isColorDark(EngineActivity.this.blurredImageView.getBitmapOriginal().getPixel((int) (EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth() * 0.5f), (int) (EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight() * 0.5f)))) {
                        EngineActivity.this.mTemplate.setColor_ipad(-1);
                    } else {
                        EngineActivity.this.mTemplate.setColor_ipad(ViewCompat.MEASURED_STATE_MASK);
                    }
                    EngineActivity.this.blurredImageView.setColorIpad(EngineActivity.this.mTemplate.getColor_ipad());
                }
                EngineActivity.this.blurredImageView.createRectWithoutSurahName();
                EngineActivity.this.blurredImageView.resizeEntity();
                if (EngineActivity.this.blurredImageView.getSurahNameEntity() != null && EngineActivity.this.blurredImageView.getSurahNameEntity().getStyle() != SurahNameStyle.ZAGHRAFAT.ordinal() && !EngineActivity.this.blurredImageView.getSurahNameEntity().isHaveBg()) {
                    EngineActivity.this.blurredImageView.updatePosSurahName();
                }
                EngineActivity.this.blurredImageView.changeColorIpad();
                EngineActivity.this.blurredImageView.invalidate();
            } catch (Exception e) {
                Log.e("execption", "onChangeType" + e.getMessage());
            }
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditIpadFragment.IIpadEditCallback
        public void onDone() {
            EngineActivity.this.hideFragment();
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditIpadFragment.IIpadEditCallback
        public void onCancel() {
            EngineActivity.this.hideFragment();
        }
    };
    ActivityResultLauncher<Intent> launchChoiceBgActivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: hazem.nurmontage.videoquran.EngineActivity$$ExternalSyntheticLambda12
        @Override // androidx.activity.result.ActivityResultCallback
        public final void onActivityResult(Object obj) {
            EngineActivity.this.m644lambda$new$8$hazemnurmontagevideoquranEngineActivity((ActivityResult) obj);
        }
    });
    ActivityResultLauncher<Intent> launchCropActivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: hazem.nurmontage.videoquran.EngineActivity$$ExternalSyntheticLambda13
        @Override // androidx.activity.result.ActivityResultCallback
        public final void onActivityResult(Object obj) {
            EngineActivity.this.m645lambda$new$9$hazemnurmontagevideoquranEngineActivity((ActivityResult) obj);
        }
    });
    private ActivityResultLauncher<Intent> launchImg = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: hazem.nurmontage.videoquran.EngineActivity$$ExternalSyntheticLambda1
        @Override // androidx.activity.result.ActivityResultCallback
        public final void onActivityResult(Object obj) {
            EngineActivity.this.m641lambda$new$10$hazemnurmontagevideoquranEngineActivity((ActivityResult) obj);
        }
    });
    private ActivityResultLauncher<Intent> launchVideo = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: hazem.nurmontage.videoquran.EngineActivity$$ExternalSyntheticLambda2
        @Override // androidx.activity.result.ActivityResultCallback
        public final void onActivityResult(Object obj) {
            EngineActivity.this.m642lambda$new$11$hazemnurmontagevideoquranEngineActivity((ActivityResult) obj);
        }
    });
    private ActivityResultLauncher<Intent> launchVideoExtract = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: hazem.nurmontage.videoquran.EngineActivity$$ExternalSyntheticLambda3
        @Override // androidx.activity.result.ActivityResultCallback
        public final void onActivityResult(Object obj) {
            EngineActivity.this.m643lambda$new$12$hazemnurmontagevideoquranEngineActivity((ActivityResult) obj);
        }
    });
    private final String[] extentions = {".mp3", ".ogg", ".acc", ".m4a", ".wav", ".mpeg"};
    private int start_extenstion = 0;
    private EditIconQuranFragment.IQuranIconCallback iQuranIconCallback = new EditIconQuranFragment.IQuranIconCallback() { // from class: hazem.nurmontage.videoquran.EngineActivity.92
        @Override // hazem.nurmontage.videoquran.fragment.EditIconQuranFragment.IQuranIconCallback
        public void add(String str) {
            try {
                QuranEntity quranEntity = (QuranEntity) EngineActivity.this.trackViewEntity.getSelectedEntity().getEntityView();
                quranEntity.setVectorDrawable((VectorDrawable) ContextCompat.getDrawable(EngineActivity.this.getApplicationContext(), DrawableHelper.getIDDrawableIconByName(str)));
                quranEntity.setIcon(str);
                quranEntity.updateIconDraw();
                quranEntity.initPreset(quranEntity.getmPreset());
                EngineActivity.this.blurredImageView.invalidate();
            } catch (Exception unused) {
                Log.e("icon  e ", "" + str);
            }
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditIconQuranFragment.IQuranIconCallback
        public void onDone(String str) {
            try {
                EngineActivity.this.blurredImageView.setIcon(str, (VectorDrawable) ContextCompat.getDrawable(EngineActivity.this.getApplicationContext(), DrawableHelper.getIDDrawableIconByName(str)));
                EngineActivity.this.hideFragment();
                EngineActivity.this.iTrimLineCallback.onSelectEntity(EngineActivity.this.trackViewEntity.getSelectedEntity(), -1.0f);
            } catch (Exception unused) {
            }
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditIconQuranFragment.IQuranIconCallback
        public void onCancel(String str) {
            try {
                QuranEntity quranEntity = (QuranEntity) EngineActivity.this.trackViewEntity.getSelectedEntity().getEntityView();
                quranEntity.setVectorDrawable((VectorDrawable) ContextCompat.getDrawable(EngineActivity.this.getApplicationContext(), DrawableHelper.getIDDrawableIconByName(str)));
                quranEntity.setIcon(str);
                quranEntity.updateIconDraw();
                quranEntity.initPreset(quranEntity.getmPreset());
                EngineActivity.this.blurredImageView.invalidate();
                EngineActivity.this.hideFragment();
                EngineActivity.this.iTrimLineCallback.onSelectEntity(EngineActivity.this.trackViewEntity.getSelectedEntity(), -1.0f);
            } catch (Exception unused) {
            }
        }
    };
    private EditS_NameFragment.IEditS_Name iEditSName = new EditS_NameFragment.IEditS_Name() { // from class: hazem.nurmontage.videoquran.EngineActivity.93
        @Override // hazem.nurmontage.videoquran.fragment.EditS_NameFragment.IEditS_Name
        public void onFont(SurahNameEntity surahNameEntity) {
            FragmentTransaction beginTransaction = EngineActivity.this.getSupportFragmentManager().beginTransaction();
            EngineActivity engineActivity = EngineActivity.this;
            engineActivity.mCurrentFragment = FontFragment.getInstance(engineActivity.iFontCallback, surahNameEntity.getNameFont(), surahNameEntity.getPaintAya().getTypeface());
            beginTransaction.replace(R.id.m_container, EngineActivity.this.mCurrentFragment);
            beginTransaction.commit();
            EngineActivity engineActivity2 = EngineActivity.this;
            engineActivity2.setupShowFragment(engineActivity2.mResources.getString(R.string.font));
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditS_NameFragment.IEditS_Name
        public void onEdit(SurahNameEntity surahNameEntity) {
            try {
                EngineActivity.this.isToCrop = true;
                Intent intent = new Intent(EngineActivity.this, (Class<?>) EditS_NameActivity.class);
                intent.putExtra("surah_name", EngineActivity.this.blurredImageView.getSurahNameEntity().getName());
                intent.putExtra("reader_name", EngineActivity.this.blurredImageView.getSurahNameEntity().getReader());
                intent.putExtra("style", EngineActivity.this.blurredImageView.getSurahNameEntity().getStyle());
                intent.putExtra(StreamInformation.KEY_INDEX, EngineActivity.this.blurredImageView.getSurahNameEntity().getIndex_surah());
                intent.putExtra("isBg", EngineActivity.this.blurredImageView.getSurahNameEntity().isHaveBg());
                intent.putExtra("clrBg", EngineActivity.this.blurredImageView.getSurahNameEntity().getClrBg());
                EngineActivity.this.editSurahNameResult.launch(intent);
                EngineActivity.this.overridePendingTransition(0, 0);
            } catch (Exception unused) {
            }
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditS_NameFragment.IEditS_Name
        public void update() {
            EngineActivity.this.blurredImageView.postInvalidate();
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditS_NameFragment.IEditS_Name
        public void onDone() {
            EngineActivity.this.selectSurahName();
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditS_NameFragment.IEditS_Name
        public void onColor(SurahNameEntity surahNameEntity) {
            try {
                EngineActivity.this.pausePlayer();
                FragmentTransaction beginTransaction = EngineActivity.this.getSupportFragmentManager().beginTransaction();
                EngineActivity engineActivity = EngineActivity.this;
                engineActivity.mCurrentFragment = ColorS_NameFragment.getInstance(engineActivity.iEditSName, surahNameEntity, EngineActivity.this.mResources);
                beginTransaction.replace(R.id.m_container, EngineActivity.this.mCurrentFragment);
                beginTransaction.commit();
                EngineActivity.this.setupShowFragment(null);
            } catch (Exception unused) {
            }
        }
    };
    private FontFragment.IFontCallback iFontCallback = new FontFragment.IFontCallback() { // from class: hazem.nurmontage.videoquran.EngineActivity.94
        @Override // hazem.nurmontage.videoquran.fragment.FontFragment.IFontCallback
        public void onAdd(String str, Typeface typeface) {
            try {
                if (EngineActivity.this.blurredImageView.getEntity_select() instanceof SurahNameEntity) {
                    EngineActivity.this.blurredImageView.getSurahNameEntity().setTypeface(typeface, str);
                    EngineActivity.this.blurredImageView.invalidate();
                } else if (str != null && typeface != null) {
                    EngineActivity.this.blurredImageView.setTypeface(typeface, str);
                }
                FontFragment.instance.add(typeface, str);
            } catch (Exception unused) {
            }
        }

        @Override // hazem.nurmontage.videoquran.fragment.FontFragment.IFontCallback
        public void onDone(String str, Typeface typeface) {
            try {
                EngineActivity.this.hideFragment();
                if (EngineActivity.this.blurredImageView.getEntity_select() instanceof SurahNameEntity) {
                    EngineActivity.this.selectSurahName();
                } else {
                    EngineActivity.this.iTrimLineCallback.onSelectEntity(EngineActivity.this.trackViewEntity.getSelectedEntity(), -1.0f);
                }
            } catch (Exception unused) {
            }
        }

        @Override // hazem.nurmontage.videoquran.fragment.FontFragment.IFontCallback
        public void onCancel(String str, Typeface typeface) {
            try {
                if (EngineActivity.this.blurredImageView.getEntity_select() instanceof SurahNameEntity) {
                    EngineActivity.this.blurredImageView.getSurahNameEntity().setTypeface(typeface, str);
                    EngineActivity.this.blurredImageView.invalidate();
                    EngineActivity.this.selectSurahName();
                } else {
                    if (str != null && typeface != null) {
                        EngineActivity.this.blurredImageView.setTypeface(typeface, str);
                    }
                    EngineActivity.this.hideFragment();
                    EngineActivity.this.iTrimLineCallback.onSelectEntity(EngineActivity.this.trackViewEntity.getSelectedEntity(), -1.0f);
                }
            } catch (Exception unused) {
            }
        }
    };
    private EditBismilahEntityFragment.IBismilahEntityCallback iBismilahEntityCallback = new EditBismilahEntityFragment.IBismilahEntityCallback() { // from class: hazem.nurmontage.videoquran.EngineActivity.95
        @Override // hazem.nurmontage.videoquran.fragment.EditBismilahEntityFragment.IBismilahEntityCallback
        public void updatePreset(AyaTextPreset ayaTextPreset) {
            EngineActivity.this.blurredImageView.setPreset(ayaTextPreset);
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditBismilahEntityFragment.IBismilahEntityCallback
        public void updateAya(int i) {
            EngineActivity.this.blurredImageView.setColorAya(i);
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditBismilahEntityFragment.IBismilahEntityCallback
        public void onAnim() {
            try {
                EngineActivity.this.pausePlayer();
                BismilahEntity bismilahEntity = (BismilahEntity) EngineActivity.this.trackViewEntity.getSelectedEntity().getEntityView();
                EngineActivity.this.trackViewEntity.updateCursurToSelectEntity();
                FragmentTransaction beginTransaction = EngineActivity.this.getSupportFragmentManager().beginTransaction();
                EngineActivity.this.mCurrentFragment = EffectBismilahFragment.get(bismilahEntity.getBismilahTimeline().getTransition(), EngineActivity.this.mResources, EngineActivity.this.iTransitionBismilahCallback, (EntityBismilahTimeline) EngineActivity.this.trackViewEntity.getSelectedEntity());
                beginTransaction.replace(R.id.m_container, EngineActivity.this.mCurrentFragment);
                beginTransaction.commit();
                EngineActivity engineActivity = EngineActivity.this;
                engineActivity.setupShowFragment(engineActivity.mResources.getString(R.string.animtion));
            } catch (Exception unused) {
            }
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditBismilahEntityFragment.IBismilahEntityCallback
        public void onDelete() {
            try {
                EngineActivity.this.pausePlayer();
                EngineActivity.this.trackViewEntity.deleteEntity(false);
                EngineActivity.this.updateTime();
                EngineActivity.this.iTrimLineCallback.onEmptySelect();
            } catch (Exception unused) {
                if (EngineActivity.this.iTrimLineCallback != null) {
                    EngineActivity.this.iTrimLineCallback.onEmptySelect();
                }
            }
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditBismilahEntityFragment.IBismilahEntityCallback
        public void update() {
            EngineActivity.this.blurredImageView.postInvalidate();
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditBismilahEntityFragment.IBismilahEntityCallback
        public void onDone() {
            EngineActivity.this.hideFragment();
            if ((EngineActivity.this.blurredImageView.getEntity_select() instanceof QuranEntity) || (EngineActivity.this.blurredImageView.getEntity_select() instanceof BismilahEntity)) {
                EngineActivity.this.iTrimLineCallback.onSelectEntity(EngineActivity.this.trackViewEntity.getSelectedEntity(), -1.0f);
            }
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditBismilahEntityFragment.IBismilahEntityCallback
        public void onColor() {
            try {
                EngineActivity.this.pausePlayer();
                BismilahEntity bismilahEntity = (BismilahEntity) EngineActivity.this.trackViewEntity.getSelectedEntity().getEntityView();
                EngineActivity.this.trackViewEntity.updateCursurToSelectEntity();
                FragmentTransaction beginTransaction = EngineActivity.this.getSupportFragmentManager().beginTransaction();
                EngineActivity engineActivity = EngineActivity.this;
                engineActivity.mCurrentFragment = ColorBismilahFragment.getInstance(engineActivity.iBismilahEntityCallback, bismilahEntity, EngineActivity.this.mResources);
                beginTransaction.replace(R.id.m_container, EngineActivity.this.mCurrentFragment);
                beginTransaction.commit();
                EngineActivity.this.setupShowFragment(null);
            } catch (Exception unused) {
            }
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditBismilahEntityFragment.IBismilahEntityCallback
        public void fromTheStart() {
            EngineActivity.this.pausePlayer();
            EngineActivity.this.trackViewEntity.translateFromStart();
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditBismilahEntityFragment.IBismilahEntityCallback
        public void fromNow() {
            EngineActivity.this.pausePlayer();
            EngineActivity.this.trackViewEntity.translateFromNow();
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditBismilahEntityFragment.IBismilahEntityCallback
        public void untilNow() {
            EngineActivity.this.pausePlayer();
            EngineActivity.this.trackViewEntity.translateUntilNow();
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditBismilahEntityFragment.IBismilahEntityCallback
        public void untilTheEnd() {
            EngineActivity.this.pausePlayer();
            EngineActivity.this.trackViewEntity.translateEndNow();
        }
    };
    private EditEntityFragment.IEditEntityCallback iEditEntityCallback = new EditEntityFragment.IEditEntityCallback() { // from class: hazem.nurmontage.videoquran.EngineActivity.96
        @Override // hazem.nurmontage.videoquran.fragment.EditEntityFragment.IEditEntityCallback
        public void updatePreset(AyaTextPreset ayaTextPreset) {
            EngineActivity.this.blurredImageView.setPreset(ayaTextPreset);
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditEntityFragment.IEditEntityCallback
        public void updateAya(int i) {
            EngineActivity.this.blurredImageView.setColorAya(i);
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditEntityFragment.IEditEntityCallback
        public void updateTrsl(int i) {
            EngineActivity.this.blurredImageView.setColorTrsl(i);
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditEntityFragment.IEditEntityCallback
        public void onFont() {
            try {
                EngineActivity.this.pausePlayer();
                QuranEntity quranEntity = (QuranEntity) EngineActivity.this.trackViewEntity.getSelectedEntity().getEntityView();
                EngineActivity.this.trackViewEntity.updateCursurToSelectEntity();
                FragmentTransaction beginTransaction = EngineActivity.this.getSupportFragmentManager().beginTransaction();
                EngineActivity engineActivity = EngineActivity.this;
                engineActivity.mCurrentFragment = FontFragment.getInstance(engineActivity.iFontCallback, quranEntity.getNameFont(), quranEntity.getPaintAya().getTypeface());
                beginTransaction.replace(R.id.m_container, EngineActivity.this.mCurrentFragment);
                beginTransaction.commit();
                EngineActivity engineActivity2 = EngineActivity.this;
                engineActivity2.setupShowFragment(engineActivity2.mResources.getString(R.string.font));
            } catch (Exception unused) {
            }
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditEntityFragment.IEditEntityCallback
        public void onIcon() {
            try {
                EngineActivity.this.pausePlayer();
                QuranEntity quranEntity = (QuranEntity) EngineActivity.this.trackViewEntity.getSelectedEntity().getEntityView();
                EngineActivity.this.trackViewEntity.updateCursurToSelectEntity();
                FragmentTransaction beginTransaction = EngineActivity.this.getSupportFragmentManager().beginTransaction();
                EngineActivity engineActivity = EngineActivity.this;
                engineActivity.mCurrentFragment = EditIconQuranFragment.getInstance(engineActivity.iQuranIconCallback, quranEntity.getIcon());
                beginTransaction.replace(R.id.m_container, EngineActivity.this.mCurrentFragment);
                beginTransaction.commit();
                EngineActivity engineActivity2 = EngineActivity.this;
                engineActivity2.setupShowFragment(engineActivity2.mResources.getString(R.string.icon));
            } catch (Exception unused) {
            }
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditEntityFragment.IEditEntityCallback
        public void onAnim() {
            try {
                EngineActivity.this.pausePlayer();
                QuranEntity quranEntity = (QuranEntity) EngineActivity.this.trackViewEntity.getSelectedEntity().getEntityView();
                EngineActivity.this.trackViewEntity.updateCursurToSelectEntity();
                FragmentTransaction beginTransaction = EngineActivity.this.getSupportFragmentManager().beginTransaction();
                EngineActivity.this.mCurrentFragment = EffectAyaFragment.get(quranEntity.getEntityQuran().getTransition(), EngineActivity.this.mResources, EngineActivity.this.iTransitionCallback, (EntityQuranTimeline) EngineActivity.this.trackViewEntity.getSelectedEntity());
                beginTransaction.replace(R.id.m_container, EngineActivity.this.mCurrentFragment);
                beginTransaction.commit();
                EngineActivity engineActivity = EngineActivity.this;
                engineActivity.setupShowFragment(engineActivity.mResources.getString(R.string.animtion));
            } catch (Exception unused) {
            }
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditEntityFragment.IEditEntityCallback
        public void onDelete() {
            try {
                EngineActivity.this.pausePlayer();
                EngineActivity.this.trackViewEntity.deleteEntity(false);
                EngineActivity.this.updateTime();
                EngineActivity.this.iTrimLineCallback.onEmptySelect();
            } catch (Exception unused) {
                if (EngineActivity.this.iTrimLineCallback != null) {
                    EngineActivity.this.iTrimLineCallback.onEmptySelect();
                }
            }
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditEntityFragment.IEditEntityCallback
        public void onDone() {
            EngineActivity.this.hideFragment();
            if (EngineActivity.this.blurredImageView.getEntity_select() instanceof QuranEntity) {
                EngineActivity.this.iTrimLineCallback.onSelectEntity(EngineActivity.this.trackViewEntity.getSelectedEntity(), -1.0f);
            }
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditEntityFragment.IEditEntityCallback
        public void onColor() {
            try {
                EngineActivity.this.pausePlayer();
                QuranEntity quranEntity = (QuranEntity) EngineActivity.this.trackViewEntity.getSelectedEntity().getEntityView();
                EngineActivity.this.trackViewEntity.updateCursurToSelectEntity();
                FragmentTransaction beginTransaction = EngineActivity.this.getSupportFragmentManager().beginTransaction();
                EngineActivity engineActivity = EngineActivity.this;
                engineActivity.mCurrentFragment = ColorAyaFragment.getInstance(engineActivity.iEditEntityCallback, quranEntity, EngineActivity.this.mResources);
                beginTransaction.replace(R.id.m_container, EngineActivity.this.mCurrentFragment);
                beginTransaction.commit();
                EngineActivity.this.setupShowFragment(null);
            } catch (Exception unused) {
            }
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditEntityFragment.IEditEntityCallback
        public void onEdit() {
            try {
                EngineActivity.this.pausePlayer();
                QuranEntity quranEntity = (QuranEntity) EngineActivity.this.trackViewEntity.getSelectedEntity().getEntityView();
                EngineActivity.this.trackViewEntity.updateCursurToSelectEntity();
                FragmentTransaction beginTransaction = EngineActivity.this.getSupportFragmentManager().beginTransaction();
                EngineActivity engineActivity = EngineActivity.this;
                engineActivity.mCurrentFragment = EditTextFragment.getInstance(engineActivity.iEdiTextCallback, quranEntity);
                beginTransaction.replace(R.id.m_container, EngineActivity.this.mCurrentFragment);
                beginTransaction.commit();
                EngineActivity.this.setupShowFragment(null);
            } catch (Exception unused) {
            }
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditEntityFragment.IEditEntityCallback
        public void onCut() {
            try {
                EngineActivity.this.pausePlayer();
                EngineActivity engineActivity = EngineActivity.this;
                engineActivity.splitEntity((QuranEntity) engineActivity.trackViewEntity.getSelectedEntity().getEntityView());
            } catch (Exception unused) {
                if (EngineActivity.this.iTrimLineCallback != null) {
                    EngineActivity.this.iTrimLineCallback.onEmptySelect();
                }
            }
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditEntityFragment.IEditEntityCallback
        public void onDuplicate() {
            try {
                EngineActivity.this.pausePlayer();
                EngineActivity engineActivity = EngineActivity.this;
                engineActivity.duplicateEntity((QuranEntity) engineActivity.trackViewEntity.getSelectedEntity().getEntityView());
                EngineActivity.this.updateTime();
            } catch (Exception unused) {
                if (EngineActivity.this.iTrimLineCallback != null) {
                    EngineActivity.this.iTrimLineCallback.onEmptySelect();
                }
            }
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditEntityFragment.IEditEntityCallback
        public void fromTheStart() {
            EngineActivity.this.pausePlayer();
            EngineActivity.this.trackViewEntity.translateFromStart();
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditEntityFragment.IEditEntityCallback
        public void fromNow() {
            EngineActivity.this.pausePlayer();
            EngineActivity.this.trackViewEntity.translateFromNow();
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditEntityFragment.IEditEntityCallback
        public void untilNow() {
            EngineActivity.this.pausePlayer();
            EngineActivity.this.trackViewEntity.translateUntilNow();
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditEntityFragment.IEditEntityCallback
        public void untilTheEnd() {
            EngineActivity.this.pausePlayer();
            EngineActivity.this.trackViewEntity.translateEndNow();
        }
    };
    private EditTrslEntityFragment.IEditEntityCallback iEditTrstEntityCallback = new EditTrslEntityFragment.IEditEntityCallback() { // from class: hazem.nurmontage.videoquran.EngineActivity.97
        @Override // hazem.nurmontage.videoquran.fragment.EditTrslEntityFragment.IEditEntityCallback
        public void updatePreset(AyaTextPreset ayaTextPreset) {
            EngineActivity.this.blurredImageView.setTrslPreset(ayaTextPreset);
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditTrslEntityFragment.IEditEntityCallback
        public void updateAya(int i) {
            EngineActivity.this.blurredImageView.setColorTrsl(i);
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditTrslEntityFragment.IEditEntityCallback
        public void updateTrsl(int i) {
            EngineActivity.this.blurredImageView.setColorTrsl(i);
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditTrslEntityFragment.IEditEntityCallback
        public void onFont() {
            try {
                EngineActivity.this.pausePlayer();
                TranslationQuranEntity translationQuranEntity = (TranslationQuranEntity) EngineActivity.this.trackViewEntity.getSelectedEntity().getEntityView();
                EngineActivity.this.trackViewEntity.updateCursurToSelectEntity();
                FragmentTransaction beginTransaction = EngineActivity.this.getSupportFragmentManager().beginTransaction();
                EngineActivity engineActivity = EngineActivity.this;
                engineActivity.mCurrentFragment = FontFragment.getInstance(engineActivity.iFontCallback, translationQuranEntity.getNameFont(), translationQuranEntity.getPaintAya().getTypeface());
                beginTransaction.replace(R.id.m_container, EngineActivity.this.mCurrentFragment);
                beginTransaction.commit();
                EngineActivity engineActivity2 = EngineActivity.this;
                engineActivity2.setupShowFragment(engineActivity2.mResources.getString(R.string.font));
            } catch (Exception unused) {
            }
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditTrslEntityFragment.IEditEntityCallback
        public void onIcon() {
            try {
                EngineActivity.this.pausePlayer();
                QuranEntity quranEntity = (QuranEntity) EngineActivity.this.trackViewEntity.getSelectedEntity().getEntityView();
                EngineActivity.this.trackViewEntity.updateCursurToSelectEntity();
                FragmentTransaction beginTransaction = EngineActivity.this.getSupportFragmentManager().beginTransaction();
                EngineActivity engineActivity = EngineActivity.this;
                engineActivity.mCurrentFragment = EditIconQuranFragment.getInstance(engineActivity.iQuranIconCallback, quranEntity.getIcon());
                beginTransaction.replace(R.id.m_container, EngineActivity.this.mCurrentFragment);
                beginTransaction.commit();
                EngineActivity engineActivity2 = EngineActivity.this;
                engineActivity2.setupShowFragment(engineActivity2.mResources.getString(R.string.icon));
            } catch (Exception unused) {
            }
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditTrslEntityFragment.IEditEntityCallback
        public void onAnim() {
            try {
                EngineActivity.this.pausePlayer();
                QuranEntity quranEntity = (QuranEntity) EngineActivity.this.trackViewEntity.getSelectedEntity().getEntityView();
                EngineActivity.this.trackViewEntity.updateCursurToSelectEntity();
                FragmentTransaction beginTransaction = EngineActivity.this.getSupportFragmentManager().beginTransaction();
                EngineActivity.this.mCurrentFragment = EffectAyaFragment.get(quranEntity.getEntityQuran().getTransition(), EngineActivity.this.mResources, EngineActivity.this.iTransitionCallback, (EntityQuranTimeline) EngineActivity.this.trackViewEntity.getSelectedEntity());
                beginTransaction.replace(R.id.m_container, EngineActivity.this.mCurrentFragment);
                beginTransaction.commit();
                EngineActivity engineActivity = EngineActivity.this;
                engineActivity.setupShowFragment(engineActivity.mResources.getString(R.string.animtion));
            } catch (Exception unused) {
            }
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditTrslEntityFragment.IEditEntityCallback
        public void onDelete() {
            try {
                EngineActivity.this.pausePlayer();
                EngineActivity.this.trackViewEntity.deleteEntity(true);
                EngineActivity.this.updateTime();
                EngineActivity.this.iTrimLineCallback.onEmptySelect();
            } catch (Exception unused) {
                if (EngineActivity.this.iTrimLineCallback != null) {
                    EngineActivity.this.iTrimLineCallback.onEmptySelect();
                }
            }
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditTrslEntityFragment.IEditEntityCallback
        public void onDone() {
            EngineActivity.this.hideFragment();
            if (EngineActivity.this.blurredImageView.getEntity_select() instanceof TranslationQuranEntity) {
                EngineActivity.this.iTrimLineCallback.onSelectEntity(EngineActivity.this.trackViewEntity.getSelectedEntity(), -1.0f);
            }
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditTrslEntityFragment.IEditEntityCallback
        public void onColor() {
            try {
                EngineActivity.this.pausePlayer();
                TranslationQuranEntity translationQuranEntity = (TranslationQuranEntity) EngineActivity.this.trackViewEntity.getSelectedEntity().getEntityView();
                EngineActivity.this.trackViewEntity.updateCursurToSelectEntity();
                FragmentTransaction beginTransaction = EngineActivity.this.getSupportFragmentManager().beginTransaction();
                EngineActivity engineActivity = EngineActivity.this;
                engineActivity.mCurrentFragment = ColorTrslAyaFragment.getInstance(engineActivity.iEditTrstEntityCallback, translationQuranEntity, EngineActivity.this.mResources);
                beginTransaction.replace(R.id.m_container, EngineActivity.this.mCurrentFragment);
                beginTransaction.commit();
                EngineActivity.this.setupShowFragment(null);
            } catch (Exception unused) {
            }
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditTrslEntityFragment.IEditEntityCallback
        public void onEdit() {
            try {
                EngineActivity.this.pausePlayer();
                EngineActivity.this.isToCrop = true;
                TranslationQuranEntity translationQuranEntity = (TranslationQuranEntity) EngineActivity.this.trackViewEntity.getSelectedEntity().getEntityView();
                Intent intent = new Intent(EngineActivity.this, (Class<?>) EditTrslTxtActivity.class);
                intent.putExtra("surah_name", "");
                intent.putExtra("reader_name", translationQuranEntity.getTxt());
                intent.putExtra("isBg", translationQuranEntity.isHaveBg());
                intent.putExtra("clrBg", translationQuranEntity.getClrBg());
                EngineActivity.this.editTrslResult.launch(intent);
                EngineActivity.this.overridePendingTransition(0, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditTrslEntityFragment.IEditEntityCallback
        public void onCut() {
            try {
                EngineActivity.this.pausePlayer();
                EngineActivity engineActivity = EngineActivity.this;
                engineActivity.splitEntity((TranslationQuranEntity) engineActivity.trackViewEntity.getSelectedEntity().getEntityView());
            } catch (Exception unused) {
                if (EngineActivity.this.iTrimLineCallback != null) {
                    EngineActivity.this.iTrimLineCallback.onEmptySelect();
                }
            }
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditTrslEntityFragment.IEditEntityCallback
        public void onDuplicate() {
            try {
                EngineActivity.this.pausePlayer();
                EngineActivity engineActivity = EngineActivity.this;
                engineActivity.duplicateEntity((TranslationQuranEntity) engineActivity.trackViewEntity.getSelectedEntity().getEntityView());
                EngineActivity.this.updateTime();
            } catch (Exception unused) {
                if (EngineActivity.this.iTrimLineCallback != null) {
                    EngineActivity.this.iTrimLineCallback.onEmptySelect();
                }
            }
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditTrslEntityFragment.IEditEntityCallback
        public void fromTheStart() {
            EngineActivity.this.pausePlayer();
            EngineActivity.this.trackViewEntity.translateFromStart();
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditTrslEntityFragment.IEditEntityCallback
        public void fromNow() {
            EngineActivity.this.pausePlayer();
            EngineActivity.this.trackViewEntity.translateFromNow();
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditTrslEntityFragment.IEditEntityCallback
        public void untilNow() {
            EngineActivity.this.pausePlayer();
            EngineActivity.this.trackViewEntity.translateUntilNow();
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditTrslEntityFragment.IEditEntityCallback
        public void untilTheEnd() {
            EngineActivity.this.pausePlayer();
            EngineActivity.this.trackViewEntity.translateEndNow();
        }
    };
    private EditMultipleEntityFragment.IEditMultipleCallback iEditMultipleCallback = new EditMultipleEntityFragment.IEditMultipleCallback() { // from class: hazem.nurmontage.videoquran.EngineActivity.100
        @Override // hazem.nurmontage.videoquran.fragment.EditMultipleEntityFragment.IEditMultipleCallback
        public void onDelete() {
            EngineActivity.this.pausePlayer();
            EngineActivity.this.dialogDeleteSelected();
        }
    };
    private EditMediaFragment.IEditMediaCallback iEditMediaCallback = new EditMediaFragment.IEditMediaCallback() { // from class: hazem.nurmontage.videoquran.EngineActivity.102
        @Override // hazem.nurmontage.videoquran.fragment.EditMediaFragment.IEditMediaCallback
        public void onReplace() {
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditMediaFragment.IEditMediaCallback
        public void updateEntity(EffectAudioType effectAudioType, EntityAudio entityAudio) {
            for (int i = 0; i < EngineActivity.this.trackViewEntity.getEntityListAudio().size(); i++) {
                EntityAudio entityAudio2 = EngineActivity.this.trackViewEntity.getEntityListAudio().get(i);
                if (entityAudio2 != entityAudio && entityAudio2.visible()) {
                    if (effectAudioType == EffectAudioType.ECHO) {
                        entityAudio2.getEffectAudio().setDecays(entityAudio.getEffectAudio().getDecays());
                        entityAudio2.getEffectAudio().setDelays(entityAudio.getEffectAudio().getDelays());
                        entityAudio2.getEffectAudio().setOutGain(entityAudio.getEffectAudio().getOutGain());
                        entityAudio2.getEffectAudio().setDecays_cmd(entityAudio.getEffectAudio().getDecays_cmd());
                        entityAudio2.getEffectAudio().setDelays_cmd(entityAudio.getEffectAudio().getDelays_cmd());
                    }
                    if (effectAudioType == EffectAudioType.NOICE) {
                        entityAudio2.getEffectAudio().setRemoveNoice(entityAudio.getEffectAudio().isRemoveNoice());
                    }
                    if (effectAudioType == EffectAudioType.ENHANCE) {
                        entityAudio2.getEffectAudio().setEnhance(entityAudio.getEffectAudio().isEnhance());
                    }
                    if (effectAudioType == EffectAudioType.SPEED) {
                        entityAudio2.getEffectAudio().setSpeed(entityAudio.getEffectAudio().getSpeed());
                    }
                    if (effectAudioType == EffectAudioType.REVERB) {
                        entityAudio2.getEffectAudio().setReverbPreset(entityAudio.getEffectAudio().getReverbPreset());
                        entityAudio2.getEffectAudio().setReverbPreset_index_list(entityAudio.getEffectAudio().getReverbPreset_index_list());
                    }
                    if (effectAudioType == EffectAudioType.VOLUME) {
                        entityAudio2.getEffectAudio().setVolume(entityAudio.getEffectAudio().getVolume());
                    }
                    if (effectAudioType == EffectAudioType.FADE) {
                        entityAudio2.getEffectAudio().setFade_in(entityAudio.getEffectAudio().getFade_in());
                        entityAudio2.getEffectAudio().setFade_out(entityAudio.getEffectAudio().getFade_out());
                    }
                }
            }
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditMediaFragment.IEditMediaCallback
        public void onDone() {
            pausePreview();
            EngineActivity.this.hideFragment();
            EngineActivity.this.iTrimLineCallback.onSelectEntity(EngineActivity.this.trackViewEntity.getSelectedEntity(), -1.0f);
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditMediaFragment.IEditMediaCallback
        public void startPreview() {
            if (EngineActivity.this.trackViewEntity.getSelectedEntity() instanceof EntityAudio) {
                EntityAudio entityAudio = (EntityAudio) EngineActivity.this.trackViewEntity.getSelectedEntity();
                if (entityAudio.getMediaPlayer().isPlaying()) {
                    return;
                }
                EngineActivity.this.trackViewEntity.previewEntity(entityAudio);
                EngineActivity.this.mIsPlaying = true;
                EngineActivity.this.trackViewEntity.translateToStart(entityAudio);
                EngineActivity engineActivity = EngineActivity.this;
                engineActivity.startCursur = engineActivity.trackViewEntity.getCurrent_cursur_position();
                EngineActivity.this.startTimelineAnimationPreview(entityAudio);
            }
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditMediaFragment.IEditMediaCallback
        public void pausePreview() {
            if (EngineActivity.this.mIsPlaying && (EngineActivity.this.trackViewEntity.getSelectedEntity() instanceof EntityAudio)) {
                EntityAudio entityAudio = (EntityAudio) EngineActivity.this.trackViewEntity.getSelectedEntity();
                EngineActivity.this.mIsPlaying = false;
                EngineActivity.this.pauseTimelineAnimation();
                EngineActivity.this.trackViewEntity.setPlaying(EngineActivity.this.mIsPlaying);
                EngineActivity.this.blurredImageView.setPlaying(EngineActivity.this.mIsPlaying);
                try {
                    if (entityAudio.getMediaPlayer() == null || !entityAudio.getMediaPlayer().isPlaying()) {
                        return;
                    }
                    entityAudio.getMediaPlayer().pause();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditMediaFragment.IEditMediaCallback
        public void onCmdPlay(String str) {
            pausePreview();
            if (EngineActivity.this.trackViewEntity.getSelectedEntity() instanceof EntityAudio) {
                EngineActivity.this.applyffectPlayAuto(str, (EntityAudio) EngineActivity.this.trackViewEntity.getSelectedEntity());
            }
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditMediaFragment.IEditMediaCallback
        public void onCmd(String str) {
            pausePreview();
            if (EngineActivity.this.trackViewEntity.getSelectedEntity() instanceof EntityAudio) {
                EngineActivity.this.applyffect(str, (EntityAudio) EngineActivity.this.trackViewEntity.getSelectedEntity());
            }
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditMediaFragment.IEditMediaCallback
        public void onCmdAll(EffectAudio effectAudio) {
            pausePreview();
            EngineActivity.this.showProgressSimple();
            EngineActivity.this.applyffectAll(effectAudio, 0);
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditMediaFragment.IEditMediaCallback
        public void onDuplicate() {
            try {
                if (EngineActivity.this.trackViewEntity.getSelectedEntity() instanceof EntityAudio) {
                    EntityAudio entityAudio = (EntityAudio) EngineActivity.this.trackViewEntity.getSelectedEntity();
                    EngineActivity.this.pausePlayer();
                    EngineActivity.this.duplicateEntityAudio(entityAudio.getMediaPlayer().getDuration(), entityAudio);
                    EngineActivity.this.updateTime();
                }
            } catch (Exception unused) {
                if (EngineActivity.this.iTrimLineCallback != null) {
                    EngineActivity.this.iTrimLineCallback.onEmptySelect();
                }
            }
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditMediaFragment.IEditMediaCallback
        public void onDelete() {
            try {
                EngineActivity.this.pausePlayer();
                EngineActivity.this.trackViewEntity.deleteMediaEntity();
                EngineActivity.this.updateTime();
                EngineActivity.this.iTrimLineCallback.onEmptySelect();
            } catch (Exception unused) {
                if (EngineActivity.this.iTrimLineCallback != null) {
                    EngineActivity.this.iTrimLineCallback.onEmptySelect();
                }
            }
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditMediaFragment.IEditMediaCallback
        public void onCut() {
            try {
                EngineActivity.this.pausePlayer();
                if (EngineActivity.this.trackViewEntity.getSelectedEntity() instanceof EntityAudio) {
                    EntityAudio entityAudio = (EntityAudio) EngineActivity.this.trackViewEntity.getSelectedEntity();
                    float abs = Math.abs(EngineActivity.this.trackViewEntity.getCurrentPosition());
                    if (abs >= entityAudio.getRect().left && abs <= entityAudio.getRect().right) {
                        float second_in_screenNoScale = EngineActivity.this.trackViewEntity.getSecond_in_screenNoScale() * 0.1f;
                        if (abs <= entityAudio.getRect().left || abs >= entityAudio.getRect().left + second_in_screenNoScale) {
                            if (abs >= entityAudio.getRect().right || abs <= entityAudio.getRect().right - second_in_screenNoScale) {
                                int round = Math.round((Math.abs(Math.round((EngineActivity.this.trackViewEntity.getCurrentPosition() / EngineActivity.this.trackViewEntity.getSecond_in_screen()) * 1000.0f)) - Math.abs(Math.round((entityAudio.getRect().left / EngineActivity.this.trackViewEntity.getSecond_in_screen()) * 1000.0f))) + entityAudio.getStart());
                                EntityAudio split = entityAudio.split(abs);
                                split.setAmps(entityAudio.getAmps());
                                split.setRenderer(entityAudio.getRenderer());
                                split.addPathHttp(entityAudio.getPaths_http());
                                split.setPath_ffmpeg_effect(entityAudio.getPath_ffmpeg_effect());
                                split.setVideo_path(entityAudio.getVideo_path());
                                split.setApplyEffectInPreview(entityAudio.isApplyEffectInPreview());
                                split.setEffectAudio(entityAudio.getEffectAudio());
                                split.setmScaleFactor(entityAudio.getmScaleFactor());
                                split.setMediaPlayer(entityAudio.getMediaPlayer());
                                split.setPath_ffmpeg(entityAudio.getPath_ffmpeg());
                                split.setIndex(entityAudio.getIndex() + 1);
                                split.setEnd(entityAudio.getEnd());
                                float f = round;
                                split.setStart(f);
                                split.setMin_duration(round);
                                EngineActivity.this.trackViewEntity.splitAudio(split, split.getIndex());
                                EngineActivity.this.trackViewEntity.stackSplit(entityAudio);
                                entityAudio.setCurrentRect();
                                entityAudio.setRight(abs);
                                entityAudio.setMax((entityAudio.getRect().right / entityAudio.getmScaleFactor()) - ((entityAudio.getRect().left / entityAudio.getmScaleFactor()) - entityAudio.getOffset_left()));
                                entityAudio.setEnd(f);
                                split.setOffset_right(entityAudio.getOffset_right());
                                entityAudio.setOffset_right(0.0f);
                                split.setOffset(entityAudio.getOffset() + entityAudio.getOffset_left() + (entityAudio.getRect().width() / entityAudio.getmScaleFactor()));
                                entityAudio.onChange();
                                split.setSecond_in_screen(EngineActivity.this.trackViewEntity.getSecond_in_screenNoScale());
                                split.updateEffect();
                                entityAudio.updateEffect();
                                EngineActivity.this.trackViewEntity.stackSplit(split);
                                EngineActivity.this.trackViewEntity.invalidate();
                            }
                        }
                    }
                }
            } catch (Exception unused) {
                if (EngineActivity.this.iTrimLineCallback != null) {
                    EngineActivity.this.iTrimLineCallback.onEmptySelect();
                }
            }
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditMediaFragment.IEditMediaCallback
        public void reverbEffect() {
            EngineActivity.this.pausePlayer();
            EngineActivity.this.findViewById(R.id.layout_menu).setVisibility(4);
            FragmentTransaction beginTransaction = EngineActivity.this.getSupportFragmentManager().beginTransaction();
            EntityAudio entityAudio = (EntityAudio) EngineActivity.this.trackViewEntity.getSelectedEntity();
            EngineActivity engineActivity = EngineActivity.this;
            engineActivity.mCurrentFragment = ReverbePresetFragment.getInstance(engineActivity.iEditMediaCallback, entityAudio);
            beginTransaction.replace(R.id.m_container, EngineActivity.this.mCurrentFragment);
            beginTransaction.commit();
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditMediaFragment.IEditMediaCallback
        public void echoEffect() {
            EngineActivity.this.pausePlayer();
            EngineActivity.this.findViewById(R.id.layout_menu).setVisibility(4);
            FragmentTransaction beginTransaction = EngineActivity.this.getSupportFragmentManager().beginTransaction();
            EntityAudio entityAudio = (EntityAudio) EngineActivity.this.trackViewEntity.getSelectedEntity();
            EngineActivity engineActivity = EngineActivity.this;
            engineActivity.mCurrentFragment = EchoEffectFragment.getInstance(engineActivity.iEditMediaCallback, entityAudio);
            beginTransaction.replace(R.id.m_container, EngineActivity.this.mCurrentFragment);
            beginTransaction.commit();
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditMediaFragment.IEditMediaCallback
        public void noice() {
            EngineActivity.this.pausePlayer();
            EngineActivity.this.findViewById(R.id.layout_menu).setVisibility(4);
            FragmentTransaction beginTransaction = EngineActivity.this.getSupportFragmentManager().beginTransaction();
            EntityAudio entityAudio = (EntityAudio) EngineActivity.this.trackViewEntity.getSelectedEntity();
            EngineActivity engineActivity = EngineActivity.this;
            engineActivity.mCurrentFragment = RemoveNoiceFragment.getInstance(engineActivity.iEditMediaCallback, entityAudio);
            beginTransaction.replace(R.id.m_container, EngineActivity.this.mCurrentFragment);
            beginTransaction.commit();
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditMediaFragment.IEditMediaCallback
        public void enhanceVoice() {
            EngineActivity.this.pausePlayer();
            EngineActivity.this.findViewById(R.id.layout_menu).setVisibility(4);
            FragmentTransaction beginTransaction = EngineActivity.this.getSupportFragmentManager().beginTransaction();
            EntityAudio entityAudio = (EntityAudio) EngineActivity.this.trackViewEntity.getSelectedEntity();
            EngineActivity engineActivity = EngineActivity.this;
            engineActivity.mCurrentFragment = EnhanceVoiceFragment.getInstance(engineActivity.iEditMediaCallback, entityAudio);
            beginTransaction.replace(R.id.m_container, EngineActivity.this.mCurrentFragment);
            beginTransaction.commit();
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditMediaFragment.IEditMediaCallback
        public void speedffect() {
            EngineActivity.this.pausePlayer();
            EngineActivity.this.findViewById(R.id.layout_menu).setVisibility(4);
            FragmentTransaction beginTransaction = EngineActivity.this.getSupportFragmentManager().beginTransaction();
            EntityAudio entityAudio = (EntityAudio) EngineActivity.this.trackViewEntity.getSelectedEntity();
            EngineActivity engineActivity = EngineActivity.this;
            engineActivity.mCurrentFragment = SpeedFragment.getInstance(engineActivity.iEditMediaCallback, entityAudio);
            beginTransaction.replace(R.id.m_container, EngineActivity.this.mCurrentFragment);
            beginTransaction.commit();
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditMediaFragment.IEditMediaCallback
        public void volumeEffect() {
            EngineActivity.this.pausePlayer();
            EngineActivity.this.findViewById(R.id.layout_menu).setVisibility(4);
            FragmentTransaction beginTransaction = EngineActivity.this.getSupportFragmentManager().beginTransaction();
            EntityAudio entityAudio = (EntityAudio) EngineActivity.this.trackViewEntity.getSelectedEntity();
            EngineActivity engineActivity = EngineActivity.this;
            engineActivity.mCurrentFragment = VolumeFragment.getInstance(engineActivity.iEditMediaCallback, entityAudio);
            beginTransaction.replace(R.id.m_container, EngineActivity.this.mCurrentFragment);
            beginTransaction.commit();
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditMediaFragment.IEditMediaCallback
        public void pitchffect() {
            EngineActivity.this.pausePlayer();
            EngineActivity.this.findViewById(R.id.layout_menu).setVisibility(4);
            FragmentTransaction beginTransaction = EngineActivity.this.getSupportFragmentManager().beginTransaction();
            EntityAudio entityAudio = (EntityAudio) EngineActivity.this.trackViewEntity.getSelectedEntity();
            EngineActivity engineActivity = EngineActivity.this;
            engineActivity.mCurrentFragment = PitchFragment.getInstance(engineActivity.iEditMediaCallback, entityAudio);
            beginTransaction.replace(R.id.m_container, EngineActivity.this.mCurrentFragment);
            beginTransaction.commit();
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditMediaFragment.IEditMediaCallback
        public void fadeffect() {
            EngineActivity.this.pausePlayer();
            EngineActivity.this.findViewById(R.id.layout_menu).setVisibility(4);
            FragmentTransaction beginTransaction = EngineActivity.this.getSupportFragmentManager().beginTransaction();
            EntityAudio entityAudio = (EntityAudio) EngineActivity.this.trackViewEntity.getSelectedEntity();
            EngineActivity engineActivity = EngineActivity.this;
            engineActivity.mCurrentFragment = FadeInOutFragment.getInstance(engineActivity.iEditMediaCallback, entityAudio);
            beginTransaction.replace(R.id.m_container, EngineActivity.this.mCurrentFragment);
            beginTransaction.commit();
        }
    };
    private EditTextFragment.IEdiTextCallback iEdiTextCallback = new EditTextFragment.IEdiTextCallback() { // from class: hazem.nurmontage.videoquran.EngineActivity.103
        @Override // hazem.nurmontage.videoquran.fragment.EditTextFragment.IEdiTextCallback
        public void onDone(EntityQuranTimeline entityQuranTimeline) {
            EngineActivity.this.setupHideFragment();
            if (entityQuranTimeline != null) {
                EngineActivity.this.showEditEntity(entityQuranTimeline);
            }
        }

        @Override // hazem.nurmontage.videoquran.fragment.EditTextFragment.IEdiTextCallback
        public void onUpdate(QuranEntity quranEntity) {
            EngineActivity.this.blurredImageView.postInvalidate();
            EngineActivity.this.trackViewEntity.postInvalidate();
        }
    };
    private EffectAyaFragment.ITransition iTransitionCallback = new EffectAyaFragment.ITransition() { // from class: hazem.nurmontage.videoquran.EngineActivity.104
        @Override // hazem.nurmontage.videoquran.fragment.EffectAyaFragment.ITransition
        public void toSubscribe() {
        }

        @Override // hazem.nurmontage.videoquran.fragment.EffectAyaFragment.ITransition
        public void destroy(EntityQuranTimeline entityQuranTimeline) {
            if (entityQuranTimeline == null) {
                return;
            }
            entityQuranTimeline.getQuranEntity().setAnimTest(false);
            entityQuranTimeline.getQuranEntity().endAnimator();
            EngineActivity.this.blurredImageView.invalidate();
        }

        @Override // hazem.nurmontage.videoquran.fragment.EffectAyaFragment.ITransition
        public void playing(EntityQuranTimeline entityQuranTimeline) {
            if (entityQuranTimeline == null) {
                return;
            }
            entityQuranTimeline.getQuranEntity().setAnimTest(true);
        }

        @Override // hazem.nurmontage.videoquran.fragment.EffectAyaFragment.ITransition
        public void onHideFragment(EntityQuranTimeline entityQuranTimeline) {
            EngineActivity.this.hideFragment();
            try {
                EngineActivity.this.iTrimLineCallback.onSelectEntity(entityQuranTimeline, -1.0f);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override // hazem.nurmontage.videoquran.fragment.EffectAyaFragment.ITransition
        public void in(String str, EntityQuranTimeline entityQuranTimeline) {
            if (entityQuranTimeline == null) {
                return;
            }
            if (entityQuranTimeline.getTransition() == null) {
                entityQuranTimeline.setTransition(new Transition());
            }
            entityQuranTimeline.getTransition().setIn(true);
            entityQuranTimeline.getTransition().setType_in(str);
            EffectAyaFragment.instance.updateView(entityQuranTimeline.getTransition().getDuration_in(), entityQuranTimeline.getTransition());
            entityQuranTimeline.getQuranEntity().endAnimator();
            entityQuranTimeline.getQuranEntity().runIn((int) (entityQuranTimeline.getTransition().getDuration_in() * 1000.0f), true, entityQuranTimeline.getTransition().getType_in());
        }

        @Override // hazem.nurmontage.videoquran.fragment.EffectAyaFragment.ITransition
        public void out(String str, EntityQuranTimeline entityQuranTimeline) {
            if (entityQuranTimeline == null) {
                return;
            }
            if (entityQuranTimeline.getTransition() == null) {
                entityQuranTimeline.setTransition(new Transition());
            }
            entityQuranTimeline.getTransition().setOut(true);
            entityQuranTimeline.getTransition().setType_out(str);
            EffectAyaFragment.instance.updateView(entityQuranTimeline.getTransition().getDuration_out(), entityQuranTimeline.getTransition());
            entityQuranTimeline.getQuranEntity().endAnimator();
            entityQuranTimeline.getQuranEntity().runOut((int) (entityQuranTimeline.getTransition().getDuration_out() * 1000.0f), true, entityQuranTimeline.getTransition().getType_out());
        }

        @Override // hazem.nurmontage.videoquran.fragment.EffectAyaFragment.ITransition
        public void remove(int i, EntityQuranTimeline entityQuranTimeline) {
            if (i == 0) {
                entityQuranTimeline.getTransition().setIn(false);
                entityQuranTimeline.getQuranEntity().endAnimator();
            }
            if (i == 1) {
                entityQuranTimeline.getTransition().setOut(false);
                entityQuranTimeline.getQuranEntity().endAnimator();
            }
        }

        @Override // hazem.nurmontage.videoquran.fragment.EffectAyaFragment.ITransition
        public void updateDurationIn(float f, EntityQuranTimeline entityQuranTimeline) {
            if (entityQuranTimeline == null) {
                return;
            }
            entityQuranTimeline.getTransition().setDuration_in(f);
            entityQuranTimeline.getQuranEntity().endAnimator();
            entityQuranTimeline.getQuranEntity().runIn((int) (entityQuranTimeline.getTransition().getDuration_in() * 1000.0f), true, entityQuranTimeline.getTransition().getType_in());
        }

        @Override // hazem.nurmontage.videoquran.fragment.EffectAyaFragment.ITransition
        public void updateDurationOut(float f, EntityQuranTimeline entityQuranTimeline) {
            if (entityQuranTimeline == null) {
                return;
            }
            entityQuranTimeline.getTransition().setDuration_out(f);
            entityQuranTimeline.getQuranEntity().endAnimator();
            entityQuranTimeline.getQuranEntity().runOut((int) (entityQuranTimeline.getTransition().getDuration_out() * 1000.0f), true, entityQuranTimeline.getTransition().getType_out());
        }

        @Override // hazem.nurmontage.videoquran.fragment.EffectAyaFragment.ITransition
        public void applyAll(int i, EntityQuranTimeline entityQuranTimeline) {
            EngineActivity.this.showProgress();
            EngineActivity engineActivity = EngineActivity.this;
            engineActivity.addUpdateAnim(engineActivity.trackViewEntity.getmIsi3adaTimeline(), entityQuranTimeline);
            EngineActivity engineActivity2 = EngineActivity.this;
            engineActivity2.addUpdateAnim(engineActivity2.trackViewEntity.getBismilahTimeline(), entityQuranTimeline);
            for (EntityQuranTimeline entityQuranTimeline2 : EngineActivity.this.trackViewEntity.getEntityListQuran()) {
                if (entityQuranTimeline2 != entityQuranTimeline) {
                    if (entityQuranTimeline.getTransition() == null) {
                        entityQuranTimeline2.setTransition(null);
                        EngineActivity.this.hideProgressFragment();
                        return;
                    }
                    if (entityQuranTimeline2.getTransition() == null) {
                        entityQuranTimeline2.setTransition(new Transition());
                    }
                    entityQuranTimeline2.getTransition().setOut(entityQuranTimeline.getTransition().isOut());
                    entityQuranTimeline2.getTransition().setType_out(entityQuranTimeline.getTransition().getType_out());
                    entityQuranTimeline2.getTransition().setDuration_out(entityQuranTimeline.getTransition().getDuration_out());
                    entityQuranTimeline2.getTransition().setIn(entityQuranTimeline.getTransition().isIn());
                    entityQuranTimeline2.getTransition().setType_in(entityQuranTimeline.getTransition().getType_in());
                    entityQuranTimeline2.getTransition().setDuration_in(entityQuranTimeline.getTransition().getDuration_in());
                }
            }
            EngineActivity.this.hideProgressFragment();
        }
    };
    private EffectBismilahFragment.ITransition iTransitionBismilahCallback = new EffectBismilahFragment.ITransition() { // from class: hazem.nurmontage.videoquran.EngineActivity.105
        @Override // hazem.nurmontage.videoquran.fragment.EffectBismilahFragment.ITransition
        public void destroy(EntityBismilahTimeline entityBismilahTimeline) {
            if (entityBismilahTimeline == null) {
                return;
            }
            entityBismilahTimeline.getQuranEntity().setAnimTest(false);
            entityBismilahTimeline.getQuranEntity().endAnimator();
            EngineActivity.this.blurredImageView.invalidate();
        }

        @Override // hazem.nurmontage.videoquran.fragment.EffectBismilahFragment.ITransition
        public void playing(EntityBismilahTimeline entityBismilahTimeline) {
            if (entityBismilahTimeline == null) {
                return;
            }
            entityBismilahTimeline.getQuranEntity().setAnimTest(true);
        }

        @Override // hazem.nurmontage.videoquran.fragment.EffectBismilahFragment.ITransition
        public void onHideFragment(EntityBismilahTimeline entityBismilahTimeline) {
            EngineActivity.this.hideFragment();
            try {
                EngineActivity.this.iTrimLineCallback.onSelectEntity(entityBismilahTimeline, -1.0f);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override // hazem.nurmontage.videoquran.fragment.EffectBismilahFragment.ITransition
        public void in(String str, EntityBismilahTimeline entityBismilahTimeline) {
            if (entityBismilahTimeline == null) {
                return;
            }
            if (entityBismilahTimeline.getTransition() == null) {
                entityBismilahTimeline.setTransition(new Transition());
            }
            entityBismilahTimeline.getTransition().setIn(true);
            entityBismilahTimeline.getTransition().setType_in(str);
            EffectBismilahFragment.instance.updateView(entityBismilahTimeline.getTransition().getDuration_in(), entityBismilahTimeline.getTransition());
            entityBismilahTimeline.getQuranEntity().endAnimator();
            entityBismilahTimeline.getQuranEntity().runIn((int) (entityBismilahTimeline.getTransition().getDuration_in() * 1000.0f), true, entityBismilahTimeline.getTransition().getType_in());
        }

        @Override // hazem.nurmontage.videoquran.fragment.EffectBismilahFragment.ITransition
        public void out(String str, EntityBismilahTimeline entityBismilahTimeline) {
            if (entityBismilahTimeline == null) {
                return;
            }
            if (entityBismilahTimeline.getTransition() == null) {
                entityBismilahTimeline.setTransition(new Transition());
            }
            entityBismilahTimeline.getTransition().setOut(true);
            entityBismilahTimeline.getTransition().setType_out(str);
            EffectBismilahFragment.instance.updateView(entityBismilahTimeline.getTransition().getDuration_out(), entityBismilahTimeline.getTransition());
            entityBismilahTimeline.getQuranEntity().endAnimator();
            entityBismilahTimeline.getQuranEntity().runOut((int) (entityBismilahTimeline.getTransition().getDuration_out() * 1000.0f), true, entityBismilahTimeline.getTransition().getType_out());
        }

        @Override // hazem.nurmontage.videoquran.fragment.EffectBismilahFragment.ITransition
        public void remove(int i, EntityBismilahTimeline entityBismilahTimeline) {
            if (i == 0) {
                entityBismilahTimeline.getTransition().setIn(false);
                entityBismilahTimeline.getQuranEntity().endAnimator();
            }
            if (i == 1) {
                entityBismilahTimeline.getTransition().setOut(false);
                entityBismilahTimeline.getQuranEntity().endAnimator();
            }
        }

        @Override // hazem.nurmontage.videoquran.fragment.EffectBismilahFragment.ITransition
        public void updateDurationIn(float f, EntityBismilahTimeline entityBismilahTimeline) {
            if (entityBismilahTimeline == null) {
                return;
            }
            entityBismilahTimeline.getTransition().setDuration_in(f);
            entityBismilahTimeline.getQuranEntity().endAnimator();
            entityBismilahTimeline.getQuranEntity().runIn((int) (entityBismilahTimeline.getTransition().getDuration_in() * 1000.0f), true, entityBismilahTimeline.getTransition().getType_in());
        }

        @Override // hazem.nurmontage.videoquran.fragment.EffectBismilahFragment.ITransition
        public void updateDurationOut(float f, EntityBismilahTimeline entityBismilahTimeline) {
            if (entityBismilahTimeline == null) {
                return;
            }
            entityBismilahTimeline.getTransition().setDuration_out(f);
            entityBismilahTimeline.getQuranEntity().endAnimator();
            entityBismilahTimeline.getQuranEntity().runOut((int) (entityBismilahTimeline.getTransition().getDuration_out() * 1000.0f), true, entityBismilahTimeline.getTransition().getType_out());
        }

        @Override // hazem.nurmontage.videoquran.fragment.EffectBismilahFragment.ITransition
        public void applyAll(EntityBismilahTimeline entityBismilahTimeline) {
            EngineActivity.this.showProgress();
            EngineActivity.this.addUpdateAnim(EngineActivity.this.trackViewEntity.getmIsi3adaTimeline() != entityBismilahTimeline ? EngineActivity.this.trackViewEntity.getmIsi3adaTimeline() : EngineActivity.this.trackViewEntity.getBismilahTimeline(), entityBismilahTimeline);
            for (EntityQuranTimeline entityQuranTimeline : EngineActivity.this.trackViewEntity.getEntityListQuran()) {
                if (entityBismilahTimeline.getTransition() == null) {
                    entityQuranTimeline.setTransition(null);
                    EngineActivity.this.hideProgressFragment();
                    return;
                }
                if (entityQuranTimeline.getTransition() == null) {
                    entityQuranTimeline.setTransition(new Transition());
                }
                entityQuranTimeline.getTransition().setOut(entityBismilahTimeline.getTransition().isOut());
                entityQuranTimeline.getTransition().setType_out(entityBismilahTimeline.getTransition().getType_out());
                entityQuranTimeline.getTransition().setDuration_out(entityBismilahTimeline.getTransition().getDuration_out());
                entityQuranTimeline.getTransition().setIn(entityBismilahTimeline.getTransition().isIn());
                entityQuranTimeline.getTransition().setType_in(entityBismilahTimeline.getTransition().getType_in());
                entityQuranTimeline.getTransition().setDuration_in(entityBismilahTimeline.getTransition().getDuration_in());
            }
            EngineActivity.this.hideProgressFragment();
        }
    };
    private final Object frameLock = new Object();
    private String pendingFramePath = null;
    private boolean isProcessingFrame = false;
    private final Runnable frameProcessorRunnable = new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.107
        @Override // java.lang.Runnable
        public void run() {
            String str;
            while (true) {
                synchronized (EngineActivity.this.frameLock) {
                    if (EngineActivity.this.pendingFramePath == null) {
                        EngineActivity.this.isProcessingFrame = false;
                        return;
                    } else {
                        str = EngineActivity.this.pendingFramePath;
                        EngineActivity.this.pendingFramePath = null;
                    }
                }
                EngineActivity.this.processFrame(str);
            }
        }
    };

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        try {
            if (this.isSaveTmpTemplate) {
                saveTemplateTmp();
            }
            if (this.isToCrop) {
                return;
            }
            TrackEntityView.ITrimLineCallback iTrimLineCallback = this.iTrimLineCallback;
            if (iTrimLineCallback != null) {
                iTrimLineCallback.onEmptySelect();
            }
            cancelDialog();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        this.isToCrop = false;
        this.isSaveTmpTemplate = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelDialog() {
        Dialog dialog = this.dialog;
        if (dialog != null && dialog.isShowing()) {
            this.dialog.dismiss();
        }
        this.dialog = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelDialogInternet() {
        Dialog dialog = this.dialogInternet;
        if (dialog != null && dialog.isShowing()) {
            this.dialogInternet.dismiss();
        }
        this.dialogInternet = null;
    }

    public void dialog() {
        try {
            this.isSaveTmpTemplate = false;
            pausePlayer();
            Dialog dialog = new Dialog(this);
            this.dialog = dialog;
            dialog.setCancelable(true);
            this.dialog.requestWindowFeature(1);
            this.dialog.getWindow().setLayout(-1, -2);
            this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            View inflate = LayoutInflater.from(this).inflate(R.layout.layout_dialog, (ViewGroup) null);
            this.dialog.setContentView(inflate);
            ((TextCustumFont) inflate.findViewById(R.id.dialog_title)).setText(this.mResources.getString(R.string.exit));
            ((TextCustumFont) inflate.findViewById(R.id.dialog_message)).setText(this.mResources.getString(R.string.are_you_sure_want_to_leave_this_work));
            ButtonCustumFont buttonCustumFont = (ButtonCustumFont) inflate.findViewById(R.id.dialog_no);
            buttonCustumFont.setText(this.mResources.getString(R.string.leave));
            buttonCustumFont.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.EngineActivity.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    LocalPersistence.deleteTemplate(EngineActivity.this, Common.TEMPLATE_TMP);
                    EngineActivity.this.cancelDialog();
                    EngineActivity.this.startActivity(new Intent(EngineActivity.this, (Class<?>) WorkUserActivity.class));
                    EngineActivity.this.finish();
                }
            });
            ButtonCustumFont buttonCustumFont2 = (ButtonCustumFont) inflate.findViewById(R.id.dialog_yes);
            buttonCustumFont2.setText(this.mResources.getString(R.string.Continue));
            buttonCustumFont2.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.EngineActivity.3
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    EngineActivity.this.cancelDialog();
                }
            });
            this.dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dialogNoInternet(final Uri uri) {
        try {
            Dialog dialog = new Dialog(this);
            this.dialogInternet = dialog;
            dialog.setCancelable(false);
            this.dialogInternet.requestWindowFeature(1);
            this.dialogInternet.getWindow().setLayout(-1, -2);
            this.dialogInternet.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            View inflate = LayoutInflater.from(this).inflate(R.layout.layout_dialog, (ViewGroup) null);
            this.dialogInternet.setContentView(inflate);
            ((TextCustumFont) inflate.findViewById(R.id.dialog_title)).setText(this.mResources.getString(R.string.no_connection));
            ((TextCustumFont) inflate.findViewById(R.id.dialog_message)).setText(this.mResources.getString(R.string.msj_connection_on));
            ButtonCustumFont buttonCustumFont = (ButtonCustumFont) inflate.findViewById(R.id.dialog_no);
            buttonCustumFont.setText(this.mResources.getString(R.string.ignore));
            buttonCustumFont.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.EngineActivity.4
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    EngineActivity.this.cancelDialogInternet();
                    EngineActivity.this.hideProgressFragment();
                }
            });
            ButtonCustumFont buttonCustumFont2 = (ButtonCustumFont) inflate.findViewById(R.id.dialog_yes);
            buttonCustumFont2.setText(this.mResources.getString(R.string.retry));
            buttonCustumFont2.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.EngineActivity.5
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (NetworkUtils.isNetworkAvailable(EngineActivity.this)) {
                        EngineActivity.this.cancelDialogInternet();
                        EngineActivity.this.addAudioTemplateHttp(uri, 0, null);
                    }
                }
            });
            this.dialogInternet.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dialogNoInternetList(final List<String> list) {
        Dialog dialog = new Dialog(this);
        this.dialogInternet = dialog;
        dialog.setCancelable(false);
        this.dialogInternet.requestWindowFeature(1);
        this.dialogInternet.getWindow().setLayout(-1, -2);
        this.dialogInternet.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        View inflate = LayoutInflater.from(this).inflate(R.layout.layout_dialog, (ViewGroup) null);
        this.dialogInternet.setContentView(inflate);
        ((TextCustumFont) inflate.findViewById(R.id.dialog_title)).setText(this.mResources.getString(R.string.no_connection));
        ((TextCustumFont) inflate.findViewById(R.id.dialog_message)).setText(this.mResources.getString(R.string.msj_connection_on));
        ButtonCustumFont buttonCustumFont = (ButtonCustumFont) inflate.findViewById(R.id.dialog_no);
        buttonCustumFont.setText(this.mResources.getString(R.string.ignore));
        buttonCustumFont.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.EngineActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                EngineActivity.this.runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.6.1
                    @Override // java.lang.Runnable
                    public void run() {
                        EngineActivity.this.trackViewEntity.invalidate();
                        EngineActivity.this.updateTime();
                        if (EngineActivity.this.mTemplate.getQuranEntityList().isEmpty()) {
                            EngineActivity.this.blurredImageView.invalidate();
                        }
                        EngineActivity.this.cancelDialogInternet();
                        EngineActivity.this.hideProgressFragment();
                    }
                });
            }
        });
        ButtonCustumFont buttonCustumFont2 = (ButtonCustumFont) inflate.findViewById(R.id.dialog_yes);
        buttonCustumFont2.setText(this.mResources.getString(R.string.retry));
        buttonCustumFont2.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.EngineActivity.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (NetworkUtils.isNetworkAvailable(EngineActivity.this)) {
                    EngineActivity.this.cancelDialogInternet();
                    EngineActivity.this.addAudioRecitersTemplate(list, 0, null);
                }
            }
        });
        this.dialogInternet.show();
    }

    private void releaseWakeLock() {
        try {
            getWindow().clearFlags(128);
        } catch (Exception unused) {
        }
    }

    private void clearFFmpeg() {
        Iterator<Long> it = this.id_ffmpeg.iterator();
        while (it.hasNext()) {
            FFmpegKit.cancel(it.next().longValue());
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        try {
            Glide.get(this).clearMemory();
        } catch (Exception unused) {
        }
        clearFFmpeg();
        releaseWakeLock();
        clearCallback();
        pausePlayer();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.ContextThemeWrapper, android.content.ContextWrapper
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(LocaleHelper.onAttach(context));
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_time_line);
        getOnBackPressedDispatcher().addCallback(this, this.onBackPressedCallback);
        WindowInsetsControllerCompat insetsController = WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(false);
        insetsController.setAppearanceLightNavigationBars(false);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), new OnApplyWindowInsetsListener() { // from class: hazem.nurmontage.videoquran.EngineActivity$$ExternalSyntheticLambda4
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                return EngineActivity.lambda$onCreate$0(view, windowInsetsCompat);
            }
        });
        this.mResources = getResources();
        setStatusBarColor(-15658735);
        setNavigationBarColor(-14935010);
        wakeLockAquire();
        showProgress();
        loadTemplate();
        initLauncher();
        this.vibrationHelper = new MyVibrationHelper(this);
        initTimeLineView();
        initViews();
        checkUriShared();
    }

    static /* synthetic */ WindowInsetsCompat lambda$onCreate$0(View view, WindowInsetsCompat windowInsetsCompat) {
        Insets insets = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.systemBars());
        view.setPadding(insets.left, insets.top, insets.right, insets.bottom);
        return windowInsetsCompat;
    }

    private void checkUriShared() {
        String stringExtra = getIntent().getStringExtra("muri");
        if (stringExtra != null) {
            addUriAudioToQuranFragment(Uri.parse(stringExtra), null);
        }
    }

    private void loadTemplate() {
        String stringExtra;
        Template template = (Template) LocalPersistence.readObjectFromFile(this, Common.TEMPLATE_TMP);
        this.mTemplate = template;
        if (template == null && getIntent() != null && (stringExtra = getIntent().getStringExtra(Common.TEMPLATE)) != null) {
            Template template2 = (Template) LocalPersistence.readObjectFromFile(this, stringExtra);
            this.mTemplate = template2;
            if (template2 != null) {
                if (template2.getName_drawable() != null) {
                    this.uri_bg = "android.resource://" + getPackageName() + "/drawable/" + DrawableHelper.getIDDrawableByName(this.mTemplate.getName_drawable());
                } else {
                    this.uri_bg = this.mTemplate.getUri_bg();
                }
                if (this.mTemplate.getWidth() < 1 || this.mTemplate.getHeight() < 1) {
                    this.mTemplate.setWidthAndHeight(720, 1280);
                }
            }
        }
        Template template3 = this.mTemplate;
        if (template3 == null) {
            this.mTemplate = new Template();
            String stringExtra2 = getIntent().getStringExtra("img_bg");
            this.uri_bg = stringExtra2;
            if (stringExtra2 != null) {
                this.mTemplate.setUri_bg(stringExtra2);
            } else {
                Map.Entry<String, Integer> randomDrawableEntry = DrawableHelper.getRandomDrawableEntry();
                String str = "android.resource://" + getPackageName() + "/drawable/" + randomDrawableEntry.getValue();
                this.uri_bg = str;
                this.mTemplate.setUri_bg(str);
                this.mTemplate.setName_drawable(randomDrawableEntry.getKey());
            }
            this.mTemplate.setWidthAndHeight(720, 1280);
        } else {
            if (template3.getName_drawable() != null) {
                this.uri_bg = "android.resource://" + getPackageName() + "/drawable/" + DrawableHelper.getIDDrawableByName(this.mTemplate.getName_drawable());
            } else {
                this.uri_bg = this.mTemplate.getUri_bg();
            }
            if (this.mTemplate.getWidth() < 1 || this.mTemplate.getHeight() < 1) {
                this.mTemplate.setWidthAndHeight(720, 1280);
            }
        }
        File file = FileUtils.getFile(getApplicationContext());
        if (file != null) {
            this.mTemplate.setFolder_template(file.getAbsolutePath());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addEntityFromTemplate() {
        RectF rectF;
        EngineActivity engineActivity = this;
        Template template = engineActivity.mTemplate;
        if (template != null) {
            boolean z = template.getIpad_type() == IpadType.GRADIENT.ordinal() || engineActivity.mTemplate.getIpad_type() == IpadType.MASK_BRUSH.ordinal() || engineActivity.mTemplate.getIpad_type() == IpadType.BLACK_LAYER.ordinal();
            Typeface loadFontFromAsset = UtilsFileLast.loadFontFromAsset(engineActivity, "fonts/arabic/خط فارس الكوفي.otf");
            Typeface createFromAsset = Typeface.createFromAsset(getResources().getAssets(), "fonts/ReadexPro_Medium.ttf");
            for (EntityQuranTemplate entityQuranTemplate : engineActivity.mTemplate.getQuranEntityList()) {
                addEntity(entityQuranTemplate.getAya(), entityQuranTemplate.getComplete_aya(), entityQuranTemplate.getTranslation(), entityQuranTemplate.getTranslation_complete(), entityQuranTemplate.getLeft(), entityQuranTemplate.getRight(), entityQuranTemplate.getIndexNumber(), entityQuranTemplate.getNumber(), entityQuranTemplate.getColor(), entityQuranTemplate.getName_font(), entityQuranTemplate.getTransition(), z, entityQuranTemplate.getIcon(), entityQuranTemplate.getStartWord_index(), entityQuranTemplate.getEndWord_index(), entityQuranTemplate.getScale(), entityQuranTemplate.getFactor_size(), entityQuranTemplate.getFactor_sizeTrl(), new RectF(entityQuranTemplate.getRectF().getL(), entityQuranTemplate.getRectF().getT(), entityQuranTemplate.getRectF().getR(), entityQuranTemplate.getRectF().getB()), loadFontFromAsset, createFromAsset, entityQuranTemplate.getColorTrsl(), entityQuranTemplate.getPreset());
                engineActivity = this;
            }
            EngineActivity engineActivity2 = engineActivity;
            for (Iterator<EntityTranslationTemplate> it = engineActivity2.mTemplate.getTranslationTemplateList().iterator(); it.hasNext(); it = it) {
                EntityTranslationTemplate next = it.next();
                addEntityTrsl(next.getAya(), next.getLeft(), next.getRight(), next.getNumber(), next.getColor(), next.getName_font(), next.getTransition(), next.getScale(), next.getFactor_size(), new RectF(next.getRectF().getL(), next.getRectF().getT(), next.getRectF().getR(), next.getRectF().getB()), next.getPreset(), next.getClr_bg(), next.isHaveBg());
            }
            if (engineActivity2.mTemplate.getEntityIsti3adaTemplate() != null) {
                addEntityIsti3ada(engineActivity2.mTemplate.getEntityIsti3adaTemplate().getAya(), engineActivity2.mTemplate.getEntityIsti3adaTemplate().getLeft(), engineActivity2.mTemplate.getEntityIsti3adaTemplate().getRight(), engineActivity2.mTemplate.getEntityIsti3adaTemplate().getColor(), engineActivity2.mTemplate.getEntityIsti3adaTemplate().getTransition(), engineActivity2.mTemplate.getEntityIsti3adaTemplate().getScale(), engineActivity2.mTemplate.getEntityIsti3adaTemplate().getFactor_size(), new RectF(engineActivity2.mTemplate.getEntityIsti3adaTemplate().getRectF().getL(), engineActivity2.mTemplate.getEntityIsti3adaTemplate().getRectF().getT(), engineActivity2.mTemplate.getEntityIsti3adaTemplate().getRectF().getR(), engineActivity2.mTemplate.getEntityIsti3adaTemplate().getRectF().getB()), engineActivity2.mTemplate.getEntityIsti3adaTemplate().getPreset());
            }
            if (engineActivity2.mTemplate.getEntityBismilahTemplate() != null) {
                addEntityBissmilah(engineActivity2.mTemplate.getEntityBismilahTemplate().getAya(), engineActivity2.mTemplate.getEntityBismilahTemplate().getLeft(), engineActivity2.mTemplate.getEntityBismilahTemplate().getRight(), engineActivity2.mTemplate.getEntityBismilahTemplate().getColor(), engineActivity2.mTemplate.getEntityBismilahTemplate().getTransition(), engineActivity2.mTemplate.getEntityBismilahTemplate().getScale(), engineActivity2.mTemplate.getEntityBismilahTemplate().getFactor_size(), new RectF(engineActivity2.mTemplate.getEntityBismilahTemplate().getRectF().getL(), engineActivity2.mTemplate.getEntityBismilahTemplate().getRectF().getT(), engineActivity2.mTemplate.getEntityBismilahTemplate().getRectF().getR(), engineActivity2.mTemplate.getEntityBismilahTemplate().getRectF().getB()), engineActivity2.mTemplate.getEntityBismilahTemplate().getPreset());
            }
            if (engineActivity2.mTemplate.getEntitySurahTemplate() != null) {
                if (engineActivity2.mTemplate.getEntitySurahTemplate().getRectF() == null) {
                    rectF = engineActivity2.blurredImageView.getRectFSurahName();
                } else {
                    rectF = new RectF(engineActivity2.mTemplate.getEntitySurahTemplate().getRectF().getL() * engineActivity2.blurredImageView.getmCanvas_width(), engineActivity2.mTemplate.getEntitySurahTemplate().getRectF().getT() * engineActivity2.blurredImageView.getmCanvas_height(), engineActivity2.mTemplate.getEntitySurahTemplate().getRectF().getR() * engineActivity2.blurredImageView.getmCanvas_width(), engineActivity2.mTemplate.getEntitySurahTemplate().getRectF().getB() * engineActivity2.blurredImageView.getmCanvas_height());
                }
                engineActivity2.blurredImageView.setSurahNameEntity(engineActivity2.mTemplate.getEntitySurahTemplate().getName(), engineActivity2.mTemplate.getEntitySurahTemplate().getReader(), rectF, engineActivity2.mTemplate.getEntitySurahTemplate().getFactor_scale(), engineActivity2.mTemplate.getEntitySurahTemplate().getName_font() == null ? "خط الإبل.otf" : engineActivity2.mTemplate.getEntitySurahTemplate().getName_font(), engineActivity2.mTemplate.getEntitySurahTemplate().getClr(), engineActivity2.mTemplate.getEntitySurahTemplate().getPreset(), engineActivity2.mTemplate.getEntitySurahTemplate().getStyle(), engineActivity2.mTemplate.getEntitySurahTemplate().getIndex_surah(), engineActivity2.mTemplate.getEntitySurahTemplate().isHaveBg(), engineActivity2.mTemplate.getEntitySurahTemplate().getClrBg() == 0 ? ViewCompat.MEASURED_STATE_MASK : engineActivity2.mTemplate.getEntitySurahTemplate().getClrBg());
            }
            if (!engineActivity2.mTemplate.getEntityMediaList().isEmpty()) {
                try {
                    final EntityMedia entityMedia = engineActivity2.mTemplate.getEntityMediaList().get(0);
                    if (entityMedia.getVideo_path() != null) {
                        if (engineActivity2.mTemplate.getUri_upload_extract_audio_video() == null) {
                            engineActivity2.runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.8
                                @Override // java.lang.Runnable
                                public void run() {
                                    EngineActivity.this.hideProgressFragment();
                                }
                            });
                        } else {
                            AudioUtils.copyToLocalAsync(engineActivity2, Uri.parse(engineActivity2.mTemplate.getUri_upload_extract_audio_video()).toString(), engineActivity2.mTemplate.getFolder_template(), new AudioUtils.Callback() { // from class: hazem.nurmontage.videoquran.EngineActivity.9
                                @Override // hazem.nurmontage.videoquran.Utils.AudioUtils.Callback
                                public void onSuccess(String str) {
                                    entityMedia.setVideo_path(str);
                                    if (EngineActivity.this.mTemplate.getExtension() != null) {
                                        EngineActivity engineActivity3 = EngineActivity.this;
                                        engineActivity3.addAudioFromVideoWithExtention(engineActivity3.mTemplate.getExtension(), entityMedia.getVideo_path(), 0);
                                    } else {
                                        EngineActivity.this.start_extenstion = 0;
                                        EngineActivity.this.extractAudioFromVideoRecursive(entityMedia.getVideo_path(), 0, true, 0);
                                    }
                                }

                                @Override // hazem.nurmontage.videoquran.Utils.AudioUtils.Callback
                                public void onError(Exception exc) {
                                    exc.printStackTrace();
                                }
                            });
                        }
                    } else if (entityMedia.getUri() != null) {
                        if (entityMedia.getPaths_https() != null) {
                            if (NetworkUtils.isNetworkAvailable(this)) {
                                engineActivity2.addAudioRecitersTemplate(entityMedia.getPaths_https(), 0, null);
                            } else {
                                engineActivity2.runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.10
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        EngineActivity.this.dialogNoInternetList(entityMedia.getPaths_https());
                                    }
                                });
                            }
                        } else if (entityMedia.getUri().contains("http")) {
                            final Uri parse = Uri.parse(entityMedia.getUri());
                            if (NetworkUtils.isNetworkAvailable(this)) {
                                engineActivity2.addAudioTemplateHttp(parse, 0, null);
                            } else {
                                engineActivity2.runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.11
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        EngineActivity.this.dialogNoInternet(parse);
                                    }
                                });
                            }
                        } else {
                            engineActivity2.addAudioTemplateHttp(Uri.parse(entityMedia.getUri()), 0, null);
                        }
                    }
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    engineActivity2.runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.12
                        @Override // java.lang.Runnable
                        public void run() {
                            EngineActivity.this.hideProgressFragment();
                        }
                    });
                    return;
                }
            }
            engineActivity2.runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.13
                @Override // java.lang.Runnable
                public void run() {
                    EngineActivity.this.trackViewEntity.invalidate();
                    EngineActivity.this.updateTime();
                    if (EngineActivity.this.mTemplate.getQuranEntityList().isEmpty()) {
                        EngineActivity.this.blurredImageView.invalidate();
                    }
                    EngineActivity.this.hideProgressFragment();
                }
            });
        }
    }

    private void initLauncher() {
        this.activityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: hazem.nurmontage.videoquran.EngineActivity$$ExternalSyntheticLambda8
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                EngineActivity.this.m640lambda$initLauncher$1$hazemnurmontagevideoquranEngineActivity((ActivityResult) obj);
            }
        });
    }

    /* renamed from: lambda$initLauncher$1$hazem-nurmontage-videoquran-EngineActivity, reason: not valid java name */
    /* synthetic */ void m640lambda$initLauncher$1$hazemnurmontagevideoquranEngineActivity(ActivityResult activityResult) {
        if (activityResult.getResultCode() == -1) {
            Intent data = activityResult.getData();
            if (data != null && data.getData() != null) {
                Uri data2 = data.getData();
                try {
                    getContentResolver().takePersistableUriPermission(data2, 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                addUriAudioToQuranFragment(data2, null);
                return;
            }
            Toast.makeText(this, this.mResources.getString(R.string.no_audio_select), 0).show();
            return;
        }
        Toast.makeText(this, this.mResources.getString(R.string.audio_cancel), 0).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addUriAudioToQuranFragment(Uri uri, String str) {
        try {
            FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
            this.mCurrentFragment = AddQuranFragment.getInstance(this.iAddQuran, this.mResources, uri, str, "-");
            beginTransaction.replace(R.id.m_container, this.mCurrentFragment);
            beginTransaction.commit();
            runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.14
                @Override // java.lang.Runnable
                public void run() {
                    EngineActivity engineActivity = EngineActivity.this;
                    engineActivity.setupShowFragment(engineActivity.mResources.getString(R.string.quran));
                }
            });
        } catch (Exception unused) {
        }
    }

    public void pauseTimelineAnimation() {
        stop();
        SmoothTimelineAnimator smoothTimelineAnimator = this.valueAnimator;
        if (smoothTimelineAnimator == null || !smoothTimelineAnimator.isRunning()) {
            return;
        }
        this.startCursur = this.valueAnimator.getCurrentTimeMs();
        this.valueAnimator.stop();
        this.valueAnimator = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pausePlayer() {
        try {
            hideLayoutResolution();
            if (this.mIsPlaying) {
                this.mIsPlaying = false;
                pauseTimelineAnimation();
                this.trackViewEntity.setPlaying(this.mIsPlaying);
                this.blurredImageView.setPlaying(this.mIsPlaying);
                this.trackViewEntity.invalidate();
                for (EntityAudio entityAudio : this.trackViewEntity.getEntityListAudio()) {
                    try {
                        if (entityAudio.getMediaPlayer() != null && entityAudio.getMediaPlayer().isPlaying()) {
                            entityAudio.getMediaPlayer().pause();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                this.btnPlayPause.setImageResource(R.drawable.play_btn);
                stop();
            }
            this.trackViewEntity.pauseScroll();
        } catch (Exception unused) {
        }
    }

    private void updateBtnToStart(int i) {
        if (i == 0) {
            this.btnToStart.setColorFilter(-8355712, PorterDuff.Mode.SRC_IN);
            this.btnToStart.setClickable(false);
        } else {
            this.btnToStart.setColorFilter(-1, PorterDuff.Mode.SRC_IN);
            this.btnToStart.setClickable(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateBtnToStart() {
        if (this.trackViewEntity.getCurrent_cursur_position() == 0) {
            this.btnToStart.setColorFilter(-8355712, PorterDuff.Mode.SRC_IN);
            this.btnToStart.setClickable(false);
        } else {
            this.btnToStart.setColorFilter(-1, PorterDuff.Mode.SRC_IN);
            this.btnToStart.setClickable(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateBtnToEnd() {
        if (this.trackViewEntity.getCurrent_cursur_position() == this.trackViewEntity.getMaxTime()) {
            this.btnToEnd.setColorFilter(-8355712, PorterDuff.Mode.SRC_IN);
            this.btnToEnd.setClickable(false);
        } else {
            this.btnToEnd.setColorFilter(-1, PorterDuff.Mode.SRC_IN);
            this.btnToEnd.setClickable(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateBtnToEndAndStart() {
        this.btnToStart.setColorFilter(-1, PorterDuff.Mode.SRC_IN);
        this.btnToStart.setClickable(true);
        this.btnToEnd.setColorFilter(-1, PorterDuff.Mode.SRC_IN);
        this.btnToEnd.setClickable(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initTypeVideo() {
        try {
            BlurredImageView blurredImageView = this.blurredImageView;
            blurredImageView.initCanvasDimension(blurredImageView.getWidth(), this.blurredImageView.getHeight(), this.mTemplate.geTypeResize());
            AudioUtils.copyToLocalAsync(this, Uri.parse(this.mTemplate.getUri_original_upload_video()).toString(), this.mTemplate.getFolder_template(), new AudioUtils.Callback() { // from class: hazem.nurmontage.videoquran.EngineActivity.15
                @Override // hazem.nurmontage.videoquran.Utils.AudioUtils.Callback
                public void onError(Exception exc) {
                }

                @Override // hazem.nurmontage.videoquran.Utils.AudioUtils.Callback
                public void onSuccess(final String str) {
                    EngineActivity.this.mTemplate.setUri_media_video(str);
                    File fileVideo = FileUtils.getFileVideo(EngineActivity.this.mTemplate.getFolder_template());
                    final File file = new File(fileVideo, "frame_%04d.jpg");
                    final File file2 = new File(fileVideo, "frame_0001.jpg");
                    final int height = EngineActivity.this.blurredImageView.getHeight();
                    EngineActivity.this.endFrame = Math.min(4, Math.round(EngineActivity.this.trackViewEntity.getMaxTime() / 1000.0f));
                    if (EngineActivity.this.endFrame == 0) {
                        EngineActivity.this.endFrame = 4;
                    }
                    EngineActivity.this.id_ffmpeg.add(Long.valueOf(FFmpegKit.executeWithArgumentsAsync(new String[]{"-i", str, "-ss", "0", "-t", "" + EngineActivity.this.endFrame, "-r", "25", "-vf", "scale=" + height + ":" + height + ":force_original_aspect_ratio=increase", "-q:v", "0", "-threads", "4", "-an", "-y", file.getAbsolutePath()}, new FFmpegSessionCompleteCallback() { // from class: hazem.nurmontage.videoquran.EngineActivity.15.1
                        /* JADX WARN: Multi-variable type inference failed */
                        /* JADX WARN: Removed duplicated region for block: B:36:0x069e A[Catch: Exception -> 0x0919, TryCatch #0 {Exception -> 0x0919, blocks: (B:3:0x0004, B:5:0x0092, B:6:0x0120, B:8:0x0169, B:10:0x017d, B:12:0x0191, B:14:0x01a5, B:16:0x01b9, B:19:0x01cf, B:21:0x01e3, B:23:0x0250, B:24:0x0272, B:26:0x0286, B:31:0x02ae, B:34:0x0690, B:36:0x069e, B:37:0x08ae, B:39:0x08c2, B:40:0x08ec, B:45:0x08d3, B:49:0x06d7, B:50:0x0326, B:52:0x033a, B:54:0x034e, B:57:0x0364, B:59:0x03d4, B:60:0x03f6, B:62:0x0409, B:67:0x0431, B:68:0x04b2, B:70:0x0528, B:71:0x054a, B:73:0x055d, B:78:0x0585, B:80:0x059e, B:81:0x061a, B:82:0x0710, B:84:0x0720, B:85:0x078f, B:87:0x07ff, B:88:0x0821, B:90:0x0834, B:95:0x085c, B:97:0x0875, B:98:0x0898, B:99:0x088d, B:100:0x0758, B:101:0x00bb, B:103:0x00cf, B:104:0x00f8), top: B:2:0x0004 }] */
                        /* JADX WARN: Removed duplicated region for block: B:39:0x08c2 A[Catch: Exception -> 0x0919, TryCatch #0 {Exception -> 0x0919, blocks: (B:3:0x0004, B:5:0x0092, B:6:0x0120, B:8:0x0169, B:10:0x017d, B:12:0x0191, B:14:0x01a5, B:16:0x01b9, B:19:0x01cf, B:21:0x01e3, B:23:0x0250, B:24:0x0272, B:26:0x0286, B:31:0x02ae, B:34:0x0690, B:36:0x069e, B:37:0x08ae, B:39:0x08c2, B:40:0x08ec, B:45:0x08d3, B:49:0x06d7, B:50:0x0326, B:52:0x033a, B:54:0x034e, B:57:0x0364, B:59:0x03d4, B:60:0x03f6, B:62:0x0409, B:67:0x0431, B:68:0x04b2, B:70:0x0528, B:71:0x054a, B:73:0x055d, B:78:0x0585, B:80:0x059e, B:81:0x061a, B:82:0x0710, B:84:0x0720, B:85:0x078f, B:87:0x07ff, B:88:0x0821, B:90:0x0834, B:95:0x085c, B:97:0x0875, B:98:0x0898, B:99:0x088d, B:100:0x0758, B:101:0x00bb, B:103:0x00cf, B:104:0x00f8), top: B:2:0x0004 }] */
                        /* JADX WARN: Removed duplicated region for block: B:45:0x08d3 A[Catch: Exception -> 0x0919, TryCatch #0 {Exception -> 0x0919, blocks: (B:3:0x0004, B:5:0x0092, B:6:0x0120, B:8:0x0169, B:10:0x017d, B:12:0x0191, B:14:0x01a5, B:16:0x01b9, B:19:0x01cf, B:21:0x01e3, B:23:0x0250, B:24:0x0272, B:26:0x0286, B:31:0x02ae, B:34:0x0690, B:36:0x069e, B:37:0x08ae, B:39:0x08c2, B:40:0x08ec, B:45:0x08d3, B:49:0x06d7, B:50:0x0326, B:52:0x033a, B:54:0x034e, B:57:0x0364, B:59:0x03d4, B:60:0x03f6, B:62:0x0409, B:67:0x0431, B:68:0x04b2, B:70:0x0528, B:71:0x054a, B:73:0x055d, B:78:0x0585, B:80:0x059e, B:81:0x061a, B:82:0x0710, B:84:0x0720, B:85:0x078f, B:87:0x07ff, B:88:0x0821, B:90:0x0834, B:95:0x085c, B:97:0x0875, B:98:0x0898, B:99:0x088d, B:100:0x0758, B:101:0x00bb, B:103:0x00cf, B:104:0x00f8), top: B:2:0x0004 }] */
                        /* JADX WARN: Removed duplicated region for block: B:49:0x06d7 A[Catch: Exception -> 0x0919, TryCatch #0 {Exception -> 0x0919, blocks: (B:3:0x0004, B:5:0x0092, B:6:0x0120, B:8:0x0169, B:10:0x017d, B:12:0x0191, B:14:0x01a5, B:16:0x01b9, B:19:0x01cf, B:21:0x01e3, B:23:0x0250, B:24:0x0272, B:26:0x0286, B:31:0x02ae, B:34:0x0690, B:36:0x069e, B:37:0x08ae, B:39:0x08c2, B:40:0x08ec, B:45:0x08d3, B:49:0x06d7, B:50:0x0326, B:52:0x033a, B:54:0x034e, B:57:0x0364, B:59:0x03d4, B:60:0x03f6, B:62:0x0409, B:67:0x0431, B:68:0x04b2, B:70:0x0528, B:71:0x054a, B:73:0x055d, B:78:0x0585, B:80:0x059e, B:81:0x061a, B:82:0x0710, B:84:0x0720, B:85:0x078f, B:87:0x07ff, B:88:0x0821, B:90:0x0834, B:95:0x085c, B:97:0x0875, B:98:0x0898, B:99:0x088d, B:100:0x0758, B:101:0x00bb, B:103:0x00cf, B:104:0x00f8), top: B:2:0x0004 }] */
                        @Override // com.arthenica.ffmpegkit.FFmpegSessionCompleteCallback
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct add '--show-bad-code' argument
                        */
                        public void apply(com.arthenica.ffmpegkit.FFmpegSession r19) {
                            /*
                                Method dump skipped, instructions count: 2533
                                To view this dump add '--comments-level debug' option
                            */
                            throw new UnsupportedOperationException("Method not decompiled: hazem.nurmontage.videoquran.EngineActivity.AnonymousClass15.AnonymousClass1.apply(com.arthenica.ffmpegkit.FFmpegSession):void");
                        }
                    }).getSessionId()));
                }
            });
        } catch (Exception e) {
            this.uri_bg = "android.resource://" + getPackageName() + "/drawable/" + R.drawable.bg_1;
            this.mTemplate.setName_drawable("bg_1");
            this.mTemplate.setColor_ipad(-1);
            this.mTemplate.setVideoSquare(false);
            iniTypeImg();
            Log.e("Tag : ", "init " + e.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void toProVersion() {
        saveTemplate();
        Intent intent = new Intent(this, (Class<?>) ProVersionActivity.class);
        intent.putExtra(Common.TEMPLATE, this.mTemplate.getIdTemplate());
        intent.addFlags(65536);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void iniTypeImg() {
        this.executor.execute(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.16
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.lang.Runnable
            public void run() {
                Bitmap bitmap = null;
                Bitmap cropTo16x9 = null;
                Bitmap cropToSquareWithRoundCorners;
                Bitmap bitmap2;
                Rect rect;
                int i;
                try {
                    EngineActivity.this.blurredImageView.initCanvasDimension(EngineActivity.this.blurredImageView.getWidth(), EngineActivity.this.blurredImageView.getHeight(), EngineActivity.this.mTemplate.geTypeResize());
                    int height = EngineActivity.this.blurredImageView.getHeight();
                    try {
                        bitmap = (Bitmap) Glide.with((FragmentActivity) EngineActivity.this).asBitmap().load(EngineActivity.this.mTemplate.getUri_bg()).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).override(height, height).submit().get();
                    } catch (Exception unused) {
                        EngineActivity.this.mTemplate.setColor_ipad(-1);
                        bitmap = (Bitmap) Glide.with((FragmentActivity) EngineActivity.this).asBitmap().load(Integer.valueOf(R.drawable.bg_19)).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).override(height, height).submit().get();
                    }
                    EngineActivity.this.blurredImageView.setBitmapOriginal(EngineActivity.this.setupOriginalBitmap(bitmap, height));
                    if (EngineActivity.this.mTemplate.geTypeResize() == ResizeType.SOCIAL_STORY.ordinal()) {
                        cropTo16x9 = BitmapCropper.cropTo9x16(EngineActivity.this.blurredImageView.getBitmapOriginal(), EngineActivity.this.blurredImageView.getW(), EngineActivity.this.blurredImageView.getH());
                    } else if (EngineActivity.this.mTemplate.geTypeResize() == ResizeType.SQUARE.ordinal()) {
                        cropTo16x9 = BitmapCropper.cropTo1x1(EngineActivity.this.blurredImageView.getBitmapOriginal(), EngineActivity.this.blurredImageView.getW(), EngineActivity.this.blurredImageView.getH());
                    } else {
                        cropTo16x9 = BitmapCropper.cropTo16x9(EngineActivity.this.blurredImageView.getBitmapOriginal(), EngineActivity.this.blurredImageView.getW(), EngineActivity.this.blurredImageView.getH());
                    }
                    EngineActivity.this.blurredImageView.setGlass(EngineActivity.this.mTemplate.isGlass());
                    int i2 = 0;
                    EngineActivity.this.blurredImageView.setVideo(false);
                    EngineActivity.this.blurredImageView.updatePosCanvas(cropTo16x9);
                    EngineActivity.this.blurredImageView.updateIpad(cropTo16x9, EngineActivity.this.mTemplate.getIpad_type(), EngineActivity.this.mTemplate.geTypeResize());
                    if (EngineActivity.this.mTemplate.getIpad_type() == IpadType.IPAD_NEOMORPHIC.ordinal()) {
                        int width = (int) (EngineActivity.this.blurredImageView.getIpad_rect().width() * 0.6f);
                        int round = Math.round(EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth() * EngineActivity.this.mTemplate.getX_square());
                        int round2 = Math.round(EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight() * EngineActivity.this.mTemplate.getY_square());
                        int i3 = width + round;
                        if (i3 > EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth()) {
                            round -= i3 - EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth();
                            i3 = EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth();
                        }
                        int i4 = width + round2;
                        if (i4 > EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight()) {
                            round2 -= i4 - EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight();
                            i4 = EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight();
                        }
                        if (round < 0) {
                            round = 0;
                        }
                        if (round2 >= 0) {
                            i2 = round2;
                        }
                        Rect rect2 = new Rect(round, i2, i3, i4);
                        EngineActivity.this.blurredImageView.setRadius_square(width);
                        int width2 = (int) (EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth() * EngineActivity.this.mTemplate.getWidth_square());
                        int height2 = (int) (EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight() * EngineActivity.this.mTemplate.getHeight_square());
                        Bitmap cropToSquareWithRoundCorners2 = UtilsBitmap.cropToSquareWithRoundCorners(EngineActivity.this.blurredImageView.getBitmapOriginal(), rect2, width, width2, height2);
                        rect2.right = rect2.left + width2;
                        rect2.bottom = rect2.top + height2;
                        EngineActivity.this.blurredImageView.setRectSquare(rect2);
                        bitmap2 = cropToSquareWithRoundCorners2;
                        rect = rect2;
                    } else {
                        if (EngineActivity.this.mTemplate.getIpad_type() != IpadType.IPAD.ordinal() && EngineActivity.this.mTemplate.getIpad_type() != IpadType.IPAD_UNBLUR.ordinal() && EngineActivity.this.mTemplate.getIpad_type() != IpadType.IPAD_CLASSIC.ordinal()) {
                            int width3 = (int) (EngineActivity.this.blurredImageView.getIpad_rect().width() * 1.0f);
                            int height3 = (int) (cropTo16x9.getHeight() * 0.5355f);
                            int round3 = Math.round(EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth() * EngineActivity.this.mTemplate.getX_square());
                            int round4 = Math.round(EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight() * EngineActivity.this.mTemplate.getY_square());
                            int i5 = width3 + round3;
                            if (i5 > EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth()) {
                                round3 -= i5 - EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth();
                                i5 = EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth();
                            }
                            int i6 = height3 + round4;
                            if (i6 > EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight()) {
                                round4 -= i6 - EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight();
                                i6 = EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight();
                            }
                            if (round3 < 0) {
                                round3 = 0;
                            }
                            if (round4 < 0) {
                                round4 = 0;
                            }
                            Rect rect3 = new Rect(round3, round4, i5, i6);
                            int width4 = (int) (EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth() * EngineActivity.this.mTemplate.getWidth_square());
                            int height4 = (int) (EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight() * EngineActivity.this.mTemplate.getHeight_square());
                            Bitmap cropToSquare = UtilsBitmap.cropToSquare(EngineActivity.this.blurredImageView.getBitmapOriginal(), rect3, width4, height4);
                            EngineActivity.this.blurredImageView.setBitmapSquare(cropToSquare);
                            EngineActivity.this.blurredImageView.setRadius_square(0);
                            rect3.right = rect3.left + width4;
                            rect3.bottom = rect3.top + height4;
                            EngineActivity.this.blurredImageView.setRectSquare(rect3);
                            bitmap2 = cropToSquare;
                            rect = rect3;
                        }
                        int width5 = (int) (EngineActivity.this.blurredImageView.getIpad_rect().width() * 0.87530595f);
                        int i7 = (int) (width5 * 1.13f);
                        int min = Math.min(width5, i7);
                        int round5 = Math.round(EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth() * EngineActivity.this.mTemplate.getX_square());
                        int round6 = Math.round(EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight() * EngineActivity.this.mTemplate.getY_square());
                        int i8 = width5 + round5;
                        if (i8 > EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth()) {
                            round5 -= i8 - EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth();
                            i8 = EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth();
                        }
                        int i9 = i7 + round6;
                        if (i9 > EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight()) {
                            round6 -= i9 - EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight();
                            i9 = EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight();
                        }
                        if (round5 < 0) {
                            round5 = 0;
                        }
                        if (round6 < 0) {
                            round6 = 0;
                        }
                        Rect rect4 = new Rect(round5, round6, i8, i9);
                        if (EngineActivity.this.mTemplate.getIpad_type() == IpadType.IPAD_CLASSIC.ordinal()) {
                            int width6 = (int) (EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth() * EngineActivity.this.mTemplate.getWidth_square());
                            int height5 = (int) (EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight() * EngineActivity.this.mTemplate.getHeight_square());
                            Bitmap cropToSquare2 = UtilsBitmap.cropToSquare(EngineActivity.this.blurredImageView.getBitmapOriginal(), rect4, width6, height5);
                            EngineActivity.this.blurredImageView.setBitmapSquare(cropToSquare2);
                            EngineActivity.this.blurredImageView.setRadius_square(0);
                            rect4.right = rect4.left + width6;
                            rect4.bottom = rect4.top + height5;
                            EngineActivity.this.blurredImageView.setRectSquare(rect4);
                            cropToSquareWithRoundCorners = cropToSquare2;
                        } else {
                            int i10 = (int) (min * 0.10800001f);
                            EngineActivity.this.blurredImageView.setRadius_square(i10);
                            int width7 = (int) (EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth() * EngineActivity.this.mTemplate.getWidth_square());
                            int height6 = (int) (EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight() * EngineActivity.this.mTemplate.getHeight_square());
                            cropToSquareWithRoundCorners = UtilsBitmap.cropToSquareWithRoundCorners(EngineActivity.this.blurredImageView.getBitmapOriginal(), rect4, i10, width7, height6);
                            rect4.right = rect4.left + width7;
                            rect4.bottom = rect4.top + height6;
                            EngineActivity.this.blurredImageView.setRectSquare(rect4);
                        }
                        bitmap2 = cropToSquareWithRoundCorners;
                        rect = rect4;
                    }
                    if (EngineActivity.this.mTemplate.getGradient() != null) {
                        EngineActivity.this.blurredImageView.setBitmap(UtilsBitmap.blur(EngineActivity.this, cropTo16x9, 20, 1), bitmap2, EngineActivity.this.mTemplate.getGradient(), EngineActivity.this.mTemplate.getIpad_type(), EngineActivity.this.mTemplate.geTypeResize(), rect);
                    } else {
                        EngineActivity.this.blurredImageView.setBitmap(UtilsBitmap.blur(EngineActivity.this, cropTo16x9, 20, 1), bitmap2, EngineActivity.this.mTemplate.getColor_ipad(), EngineActivity.this.mTemplate.getIpad_type(), EngineActivity.this.mTemplate.geTypeResize(), rect);
                    }
                    if (EngineActivity.this.mTemplate.getIpad_type() == IpadType.BLUE_TYPE.ordinal()) {
                        i = EngineActivity.this.blurredImageView.getPaintLecture().getColor();
                    } else {
                        i = EngineActivity.this.blurredImageView.getPaintLecture().getColor() == -1 ? InputDeviceCompat.SOURCE_ANY : Common.COLOR_TRANSLATION;
                    }
                    EngineActivity.this.blurredImageView.setClr_trsl(i);
                    EngineActivity.this.blurredImageView.setClr_aya(EngineActivity.this.blurredImageView.getPaintLecture().getColor());
                    EngineActivity.this.addEntityFromTemplate();
                } catch (Exception e) {
                    Log.e("Tag : ", "init " + e.getMessage());
                }
            }
        });
    }

    private void initResolution() {
        this.tv_resolution = (TextCustumFont) findViewById(R.id.tv_resolution);
        this.layout_resolution = (LinearLayout) findViewById(R.id.layout_resolution);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.btn_setup_fps);
        this.btn_setup_fps = linearLayout;
        linearLayout.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.EngineActivity.17
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (EngineActivity.this.layout_resolution == null) {
                    return;
                }
                if (EngineActivity.this.layout_resolution.getVisibility() != 0) {
                    EngineActivity.this.layout_resolution.setVisibility(0);
                } else {
                    EngineActivity.this.layout_resolution.setVisibility(8);
                }
            }
        });
        this.seekBar_fps = (CustomDiscreteSeekBar) findViewById(R.id.seekbar_fps);
        if (this.mTemplate.getFps() == 15) {
            this.seekBar_fps.setProgress(0);
        } else if (this.mTemplate.getFps() == 25) {
            this.seekBar_fps.setProgress(1);
        } else if (this.mTemplate.getFps() == 30) {
            this.seekBar_fps.setProgress(2);
        } else if (this.mTemplate.getFps() == 50) {
            this.seekBar_fps.setProgress(3);
        } else {
            this.seekBar_fps.setProgress(4);
        }
        this.seekBar_fps.setOnProgressChangeListener(new CustomDiscreteSeekBar.OnProgressChangeListener() { // from class: hazem.nurmontage.videoquran.EngineActivity.18
            @Override // hazem.nurmontage.videoquran.views.CustomDiscreteSeekBar.OnProgressChangeListener
            public void onProgressChanged(CustomDiscreteSeekBar customDiscreteSeekBar, int i, String str, boolean z) {
            }

            @Override // hazem.nurmontage.videoquran.views.CustomDiscreteSeekBar.OnProgressChangeListener
            public void onStartTrackingTouch(CustomDiscreteSeekBar customDiscreteSeekBar) {
            }

            @Override // hazem.nurmontage.videoquran.views.CustomDiscreteSeekBar.OnProgressChangeListener
            public void onStopTrackingTouch(CustomDiscreteSeekBar customDiscreteSeekBar) {
                if (EngineActivity.this.mTemplate != null) {
                    EngineActivity.this.mTemplate.setFps(Integer.parseInt(EngineActivity.this.seekBar_fps.getCurrentLabel()));
                }
            }
        });
        this.tv_resolution.setText(this.mTemplate.getResolution());
        this.seekBar_res = (CustomDiscreteSeekBar) findViewById(R.id.seekbar_resolution);
        if (this.mTemplate.getResolution().equals("480p")) {
            this.seekBar_res.setProgress(0);
        } else if (this.mTemplate.getResolution().equals("720p")) {
            this.seekBar_res.setProgress(1);
        } else if (this.mTemplate.getResolution().equals("1080p")) {
            this.seekBar_res.setProgress(2);
        } else {
            this.seekBar_res.setProgress(3);
        }
        this.seekBar_res.setOnProgressChangeListener(new CustomDiscreteSeekBar.OnProgressChangeListener() { // from class: hazem.nurmontage.videoquran.EngineActivity.19
            @Override // hazem.nurmontage.videoquran.views.CustomDiscreteSeekBar.OnProgressChangeListener
            public void onProgressChanged(CustomDiscreteSeekBar customDiscreteSeekBar, int i, String str, boolean z) {
            }

            @Override // hazem.nurmontage.videoquran.views.CustomDiscreteSeekBar.OnProgressChangeListener
            public void onStartTrackingTouch(CustomDiscreteSeekBar customDiscreteSeekBar) {
            }

            @Override // hazem.nurmontage.videoquran.views.CustomDiscreteSeekBar.OnProgressChangeListener
            public void onStopTrackingTouch(CustomDiscreteSeekBar customDiscreteSeekBar) {
                if (EngineActivity.this.mTemplate != null) {
                    EngineActivity.this.mTemplate.setResolution(EngineActivity.this.seekBar_res.getCurrentLabel());
                    Pair<Integer, Integer> size = AspectRatioCalculator.getSize(EngineActivity.this.mTemplate.geTypeResize(), EngineActivity.this.mTemplate.getResolution());
                    EngineActivity.this.tv_resolution.setText(EngineActivity.this.mTemplate.getResolution());
                    EngineActivity.this.mTemplate.setWidthAndHeight(size.getFirst().intValue(), size.getSecond().intValue());
                }
            }
        });
    }

    private void initViews() {
        initResolution();
        ImageButton imageButton = (ImageButton) findViewById(R.id.btn_play_pause);
        this.btnPlayPause = imageButton;
        imageButton.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.EngineActivity.20
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                EngineActivity.this.hideLayoutResolution();
                if (EngineActivity.this.mIsPlaying) {
                    EngineActivity.this.mIsPlaying = false;
                    EngineActivity.this.pauseTimelineAnimation();
                    EngineActivity.this.trackViewEntity.setPlaying(EngineActivity.this.mIsPlaying);
                    EngineActivity.this.blurredImageView.setPlaying(EngineActivity.this.mIsPlaying);
                    EngineActivity.this.trackViewEntity.invalidate();
                    for (EntityAudio entityAudio : EngineActivity.this.trackViewEntity.getEntityListAudio()) {
                        try {
                            if (entityAudio.visible() && entityAudio.getMediaPlayer() != null && entityAudio.getMediaPlayer().isPlaying()) {
                                entityAudio.getMediaPlayer().pause();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    EngineActivity.this.btnPlayPause.setImageResource(R.drawable.play_btn);
                    return;
                }
                if (EngineActivity.this.current_position_time == 0) {
                    EngineActivity.this.trackViewEntity.updateCursur(0);
                }
                EngineActivity.this.trackViewEntity.calculMaxTime();
                EngineActivity.this.btnPlayPause.setImageResource(R.drawable.pause_24px);
                EngineActivity.this.updateBtnToEndAndStart();
                EngineActivity.this.current_position_time = (int) System.currentTimeMillis();
                EngineActivity.this.mIsPlaying = true;
                EngineActivity.this.trackViewEntity.setPlaying(true);
                EngineActivity.this.blurredImageView.setPlaying(true);
                EngineActivity.this.startTimelineAnimation();
            }
        });
        ImageButton imageButton2 = (ImageButton) findViewById(R.id.btn_to_end);
        this.btnToEnd = imageButton2;
        imageButton2.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.EngineActivity.21
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (EngineActivity.this.trackViewEntity.getCurrent_cursur_position() == EngineActivity.this.trackViewEntity.getMaxTime()) {
                    return;
                }
                EngineActivity.this.blurredImageView.setProgress(1.0f);
                EngineActivity.this.pausePlayer();
                EngineActivity.this.startCursur = 0;
                EngineActivity.this.trackViewEntity.translateToEnd();
                EngineActivity engineActivity = EngineActivity.this;
                engineActivity.updateViewTime(engineActivity.trackViewEntity.getMaxTime(), EngineActivity.this.trackViewEntity.getCurrent_cursur_position());
                EngineActivity.this.updateBtnToEnd();
                EngineActivity.this.updateBtnToStart();
            }
        });
        ImageButton imageButton3 = (ImageButton) findViewById(R.id.btn_to_start);
        this.btnToStart = imageButton3;
        imageButton3.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.EngineActivity.22
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (EngineActivity.this.trackViewEntity.getCurrent_cursur_position() == 0) {
                    return;
                }
                EngineActivity.this.blurredImageView.setProgress(0.0f);
                EngineActivity.this.pausePlayer();
                EngineActivity.this.startCursur = 0;
                EngineActivity.this.trackViewEntity.translateToStart();
                EngineActivity engineActivity = EngineActivity.this;
                engineActivity.updateViewTime(engineActivity.trackViewEntity.getMaxTime(), EngineActivity.this.trackViewEntity.getCurrent_cursur_position());
                EngineActivity.this.updateBtnToStart();
                EngineActivity.this.updateBtnToEnd();
            }
        });
        updateBtnToStart(this.mTemplate.getCurrentCursur());
        this.btnRedo = (ImageButton) findViewById(R.id.btn_redo);
        this.btnUndo = (ImageButton) findViewById(R.id.btn_undo);
        disableUndoBtn();
        disableRedoBtn();
        this.btnRedo.setOnClickListener(new AnonymousClass23());
        this.btnUndo.setOnClickListener(new AnonymousClass24());
        this.trackViewEntity.setRedoUndo(this.btnRedo, this.btnUndo);
        BlurredImageView blurredImageView = (BlurredImageView) findViewById(R.id.view);
        this.blurredImageView = blurredImageView;
        blurredImageView.setPro(BillingPreferences.isSubscribed(this));
        this.blurredImageView.setiViewCallback(new BlurredImageView.IViewCallback() { // from class: hazem.nurmontage.videoquran.EngineActivity.25
            @Override // hazem.nurmontage.videoquran.views.BlurredImageView.IViewCallback
            public void onDrawFinish() {
            }

            @Override // hazem.nurmontage.videoquran.views.BlurredImageView.IViewCallback
            public void onSquare() {
            }

            @Override // hazem.nurmontage.videoquran.views.BlurredImageView.IViewCallback
            public void onEndMove() {
                if (EngineActivity.this.blurredImageView.getEntity_select() != null) {
                    EngineActivity.this.blurredImageView.applyAll(EngineActivity.this.blurredImageView.getEntity_select().getFactor_scale(), EngineActivity.this.blurredImageView.getEntity_select().getRect(), EngineActivity.this.blurredImageView.getEntity_select().getMax_w(), EngineActivity.this.blurredImageView.getEntity_select().getMax_h());
                }
            }

            @Override // hazem.nurmontage.videoquran.views.BlurredImageView.IViewCallback
            public void onEndScale() {
                if (EngineActivity.this.blurredImageView.getEntity_select() != null) {
                    EngineActivity.this.blurredImageView.applyAll(EngineActivity.this.blurredImageView.getEntity_select().getFactor_scale(), EngineActivity.this.blurredImageView.getEntity_select().getRect(), EngineActivity.this.blurredImageView.getEntity_select().getMax_w(), EngineActivity.this.blurredImageView.getEntity_select().getMax_h());
                }
            }

            @Override // hazem.nurmontage.videoquran.views.BlurredImageView.IViewCallback
            public void onSelect(EntityView entityView) {
                if (entityView instanceof SurahNameEntity) {
                    try {
                        if (EditS_NameFragment.instance != null) {
                            return;
                        }
                        EngineActivity.this.pausePlayer();
                        EngineActivity.this.selectSurahName();
                        return;
                    } catch (Exception unused) {
                        return;
                    }
                }
                if (entityView instanceof QuranEntity) {
                    EngineActivity.this.trackViewEntity.selectEntity(entityView.getEntityQuran(), true);
                    EngineActivity.this.iTrimLineCallback.onSelectEntity(entityView.getEntityQuran(), 0.0f);
                } else if (entityView instanceof BismilahEntity) {
                    EntityBismilahTimeline bismilahTimeline = ((BismilahEntity) entityView).getBismilahTimeline();
                    EngineActivity.this.trackViewEntity.selectEntity(bismilahTimeline, true);
                    EngineActivity.this.iTrimLineCallback.onSelectEntity(bismilahTimeline, 0.0f);
                } else if (entityView instanceof TranslationQuranEntity) {
                    EngineActivity.this.trackViewEntity.selectEntity(entityView.getEntityTrslTimeline(), true);
                    EngineActivity.this.iTrimLineCallback.onSelectEntity(entityView.getEntityTrslTimeline(), 0.0f);
                }
            }

            @Override // hazem.nurmontage.videoquran.views.BlurredImageView.IViewCallback
            public void onEmtyClick() {
                EngineActivity.this.iTrimLineCallback.onEmptySelect();
            }

            @Override // hazem.nurmontage.videoquran.views.BlurredImageView.IViewCallback
            public void onWattermark() {
                EngineActivity.this.dialogWatermark();
            }
        });
        if (this.blurredImageView.isPro()) {
            findViewById(R.id.to_pro).setVisibility(8);
        } else {
            findViewById(R.id.to_pro).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.EngineActivity.26
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    EngineActivity.this.toProVersion();
                }
            });
        }
        this.blurredImageView.post(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.27
            @Override // java.lang.Runnable
            public void run() {
                if (EngineActivity.this.mTemplate.isVideoSquare()) {
                    EngineActivity.this.initTypeVideo();
                } else {
                    EngineActivity.this.iniTypeImg();
                }
            }
        });
        ButtonCustumFont buttonCustumFont = (ButtonCustumFont) findViewById(R.id.btn_export);
        this.btn_export = buttonCustumFont;
        buttonCustumFont.setText(this.mResources.getString(R.string.export));
        this.btn_export.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.EngineActivity.28
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                EngineActivity.this.isSaveTmpTemplate = false;
                EngineActivity.this.pausePlayer();
                if (Build.VERSION.SDK_INT >= 33) {
                    EngineActivity.this.save();
                } else if (ContextCompat.checkSelfPermission(EngineActivity.this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
                    EngineActivity.this.save();
                } else {
                    ActivityCompat.requestPermissions(EngineActivity.this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 1);
                }
            }
        });
        ImageButton imageButton4 = (ImageButton) findViewById(R.id.btn_cancel);
        this.btn_cancel = imageButton4;
        imageButton4.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.EngineActivity.29
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                EngineActivity.this.dialog();
            }
        });
        this.tv_tittle_fragment = (TextCustumFont) findViewById(R.id.tv_tittle_fragment);
        ((TextCustumFont) findViewById(R.id.tv_quran)).setText(this.mResources.getString(R.string.quran));
        ((TextCustumFont) findViewById(R.id.tv_bg)).setText(this.mResources.getString(R.string.bg));
        TextCustumFont textCustumFont = (TextCustumFont) findViewById(R.id.tv_ipad);
        textCustumFont.setText(this.mResources.getString(R.string.ipad));
        findViewById(R.id.btn_add_quran).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.EngineActivity.30
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                EngineActivity.this.pausePlayer();
                try {
                    FragmentTransaction beginTransaction = EngineActivity.this.getSupportFragmentManager().beginTransaction();
                    EngineActivity engineActivity = EngineActivity.this;
                    engineActivity.mCurrentFragment = AddQuranFragment.getInstance(engineActivity.iAddQuran, EngineActivity.this.mResources);
                    beginTransaction.replace(R.id.m_container, EngineActivity.this.mCurrentFragment);
                    beginTransaction.commit();
                    EngineActivity engineActivity2 = EngineActivity.this;
                    engineActivity2.setupShowFragment(engineActivity2.mResources.getString(R.string.quran));
                } catch (Exception unused) {
                }
            }
        });
        findViewById(R.id.btn_bg).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.EngineActivity.31
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                EngineActivity.this.pausePlayer();
                try {
                    FragmentTransaction beginTransaction = EngineActivity.this.getSupportFragmentManager().beginTransaction();
                    EngineActivity engineActivity = EngineActivity.this;
                    engineActivity.mCurrentFragment = ChangeBgFragment.getInstance(engineActivity.iChangeBgCallback, EngineActivity.this.mResources, EngineActivity.this.mTemplate.getName_drawable());
                    beginTransaction.replace(R.id.m_container, EngineActivity.this.mCurrentFragment);
                    beginTransaction.commitNow();
                    EngineActivity engineActivity2 = EngineActivity.this;
                    engineActivity2.setupShowFragment(engineActivity2.mResources.getString(R.string.bg));
                } catch (Exception unused) {
                }
            }
        });
        this.btnIpod = (LinearLayout) findViewById(R.id.btn_ipad);
        this.textChangeResize = (TextCustumFont) findViewById(R.id.tv_ratio);
        this.ivResize = (ImageView) findViewById(R.id.iv_ratio);
        this.ivIpod = (ImageView) findViewById(R.id.iv_ipod);
        this.btnChangeResize = (LinearLayout) findViewById(R.id.btn_change_aspect);
        if (this.blurredImageView.isPro()) {
            this.btnChangeResize.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.EngineActivity.32
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    EngineActivity.this.pausePlayer();
                    try {
                        FragmentTransaction beginTransaction = EngineActivity.this.getSupportFragmentManager().beginTransaction();
                        EngineActivity engineActivity = EngineActivity.this;
                        engineActivity.mCurrentFragment = ResizeFragment.getInstance(engineActivity.iDimensionCallback, EngineActivity.this.mResources, "16");
                        beginTransaction.replace(R.id.m_container, EngineActivity.this.mCurrentFragment);
                        beginTransaction.commit();
                        EngineActivity.this.setupShowFragment(null);
                    } catch (Exception unused) {
                    }
                }
            });
        } else {
            this.textChangeResize.setTextColor(-8355712);
            this.ivResize.setColorFilter(-8355712, PorterDuff.Mode.SRC_IN);
            this.btnChangeResize.setBackgroundColor(0);
            this.btnChangeResize.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.EngineActivity.33
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    EngineActivity.this.pausePlayer();
                    EngineActivity.this.dialogPremium(R.drawable.iv_layout_ipod);
                }
            });
            textCustumFont.setTextColor(-8355712);
            this.ivIpod.setColorFilter(-8355712, PorterDuff.Mode.SRC_IN);
            this.btnIpod.setBackgroundColor(0);
        }
        this.btnIpod.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.EngineActivity.34
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                EngineActivity.this.pausePlayer();
                try {
                    FragmentTransaction beginTransaction = EngineActivity.this.getSupportFragmentManager().beginTransaction();
                    EngineActivity engineActivity = EngineActivity.this;
                    engineActivity.mCurrentFragment = EditIpadFragment.getInstance(engineActivity.mResources, EngineActivity.this.mTemplate.getIpad_type(), EngineActivity.this.iIpadEditCallback, EngineActivity.this.mTemplate.getIndex_color(), EngineActivity.this.mTemplate.getGradient() != null, EngineActivity.this.mTemplate.isGlass());
                    beginTransaction.replace(R.id.m_container, EngineActivity.this.mCurrentFragment);
                    beginTransaction.commit();
                    EngineActivity engineActivity2 = EngineActivity.this;
                    engineActivity2.setupShowFragment(engineActivity2.mResources.getString(R.string.ipad));
                } catch (Exception unused) {
                }
            }
        });
        updateHitRatio(this.mTemplate.geTypeResize(), this.mTemplate.getImgResize());
    }

    /* renamed from: hazem.nurmontage.videoquran.EngineActivity$23, reason: invalid class name */
    class AnonymousClass23 implements View.OnClickListener {
        AnonymousClass23() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            EngineActivity.this.pausePlayer();
            EngineActivity.this.showProgressSimple();
            new Thread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.23.1
                @Override // java.lang.Runnable
                public void run() {
                    EngineActivity.this.runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.23.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            EngineActivity.this.trackViewEntity.redo();
                            EngineActivity.this.hideProgressFragment();
                        }
                    });
                }
            }).start();
        }
    }

    /* renamed from: hazem.nurmontage.videoquran.EngineActivity$24, reason: invalid class name */
    class AnonymousClass24 implements View.OnClickListener {
        AnonymousClass24() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            EngineActivity.this.pausePlayer();
            EngineActivity.this.showProgressSimple();
            new Thread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.24.1
                @Override // java.lang.Runnable
                public void run() {
                    EngineActivity.this.runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.24.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            EngineActivity.this.trackViewEntity.undo();
                            EngineActivity.this.hideProgressFragment();
                        }
                    });
                }
            }).start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void save() {
        if (this.oneExport) {
            return;
        }
        this.oneExport = true;
        this.trackViewEntity.finishScroll();
        this.trackViewEntity.setOnProgress(true);
        this.blurredImageView.setNotDraw(true);
        if (!this.blurredImageView.isPro()) {
            this.blurredImageView.setRemoveWattermark(false);
        }
        stop();
        showProgress();
        this.executor.execute(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.35
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.lang.Runnable
            public void run() {
                Bitmap cropTo16x9 = null;
                Rect rect;
                int i;
                Bitmap cropToSquareWithRoundCorners;
                int i2;
                int i3;
                Bitmap cropTo16x92;
                try {
                    EngineActivity.this.trackViewEntity.calculMaxTime();
                    EngineActivity.this.blurredImageView.reset();
                    EngineActivity.this.blurredImageView.initCanvasDimension(EngineActivity.this.mTemplate.getWidth(), EngineActivity.this.mTemplate.getHeight(), EngineActivity.this.mTemplate.geTypeResize());
                    int max = Math.max(EngineActivity.this.mTemplate.getWidth(), EngineActivity.this.mTemplate.getHeight());
                    if (EngineActivity.this.mTemplate.getIpad_type() != IpadType.HEART.ordinal() && EngineActivity.this.mTemplate.getIpad_type() != IpadType.BATTERY.ordinal()) {
                        if (EngineActivity.this.mTemplate.isVideoSquare() && (EngineActivity.this.mTemplate.getIpad_type() == IpadType.GRADIENT.ordinal() || EngineActivity.this.mTemplate.getIpad_type() == IpadType.BLACK_LAYER.ordinal() || EngineActivity.this.mTemplate.getIpad_type() == IpadType.MASK_BRUSH.ordinal() || EngineActivity.this.mTemplate.getIpad_type() == IpadType.BLUE_TYPE.ordinal() || EngineActivity.this.mTemplate.getIpad_type() == IpadType.CASSET_IMG.ordinal())) {
                            EngineActivity.this.blurredImageView.setBitmapOriginal(Bitmap.createBitmap(max, max, Bitmap.Config.ARGB_8888));
                            if (EngineActivity.this.mTemplate.geTypeResize() == ResizeType.SOCIAL_STORY.ordinal()) {
                                cropTo16x92 = BitmapCropper.cropTo9x16(EngineActivity.this.blurredImageView.getBitmapOriginal());
                            } else if (EngineActivity.this.mTemplate.geTypeResize() == ResizeType.SQUARE.ordinal()) {
                                cropTo16x92 = BitmapCropper.cropTo1x1(EngineActivity.this.blurredImageView.getBitmapOriginal());
                            } else {
                                cropTo16x92 = BitmapCropper.cropTo16x9(EngineActivity.this.blurredImageView.getBitmapOriginal());
                            }
                            EngineActivity.this.blurredImageView.updatePosCanvas(EngineActivity.this.mTemplate.getWidth(), EngineActivity.this.mTemplate.getHeight(), cropTo16x92);
                            EngineActivity.this.blurredImageView.updateIpad(cropTo16x92, EngineActivity.this.mTemplate.getIpad_type(), EngineActivity.this.mTemplate.geTypeResize());
                            int width = (int) (EngineActivity.this.blurredImageView.getIpad_rect().width() * 1.0f);
                            int height = (int) (cropTo16x92.getHeight() * 0.5355f);
                            EngineActivity.this.mTemplate.setDrawingTranslation(EngineActivity.this.blurredImageView.getBtmX(), EngineActivity.this.blurredImageView.getBtmY());
                            int round = Math.round(EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth() * EngineActivity.this.mTemplate.getX_square());
                            int round2 = Math.round(EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight() * EngineActivity.this.mTemplate.getY_square());
                            int i4 = width + round;
                            if (i4 > EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth()) {
                                round -= i4 - EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth();
                                i4 = EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth();
                            }
                            int i5 = height + round2;
                            if (i5 > EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight()) {
                                round2 -= i5 - EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight();
                                i5 = EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight();
                            }
                            if (round < 0) {
                                round = 0;
                            }
                            if (round2 < 0) {
                                round2 = 0;
                            }
                            Rect rect2 = new Rect(round, round2, i4, i5);
                            int width2 = (int) (EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth() * EngineActivity.this.mTemplate.getWidth_square());
                            int height2 = (int) (EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight() * EngineActivity.this.mTemplate.getHeight_square());
                            Bitmap cropToSquare = UtilsBitmap.cropToSquare(EngineActivity.this.blurredImageView.getBitmapOriginal(), rect2, width2, height2);
                            EngineActivity.this.blurredImageView.setBitmapSquare(cropToSquare);
                            EngineActivity.this.blurredImageView.setRadius_square(0);
                            rect2.right = rect2.left + width2;
                            rect2.bottom = rect2.top + height2;
                            EngineActivity.this.blurredImageView.setRectSquare(rect2);
                            EngineActivity.this.mTemplate.setUri_bg_ffmpeg(EngineActivity.this.blurredImageView.setupBitmapDraw(cropTo16x92, cropToSquare, EngineActivity.this.mTemplate));
                            EngineActivity.this.mTemplate.getSquareBitmapModel().set(EngineActivity.this.blurredImageView.getLeft_square(), EngineActivity.this.blurredImageView.getTop_square(), round, round2, rect2.width(), rect2.height(), cropToSquare.getWidth(), cropToSquare.getHeight(), 0);
                        } else {
                            EngineActivity.this.blurredImageView.setBitmapOriginal(EngineActivity.this.setupOriginalBitmap((Bitmap) Glide.with((FragmentActivity) EngineActivity.this).asBitmap().load(EngineActivity.this.mTemplate.isVideoSquare() ? EngineActivity.this.mTemplate.getFrame_bg() : EngineActivity.this.mTemplate.getUri_bg()).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).override(max, max).submit().get(), max));
                            if (EngineActivity.this.mTemplate.geTypeResize() == ResizeType.SOCIAL_STORY.ordinal()) {
                                cropTo16x9 = BitmapCropper.cropTo9x16(EngineActivity.this.blurredImageView.getBitmapOriginal(), EngineActivity.this.mTemplate.getWidth(), EngineActivity.this.mTemplate.getHeight());
                            } else if (EngineActivity.this.mTemplate.geTypeResize() == ResizeType.SQUARE.ordinal()) {
                                cropTo16x9 = BitmapCropper.cropTo1x1(EngineActivity.this.blurredImageView.getBitmapOriginal(), EngineActivity.this.mTemplate.getWidth(), EngineActivity.this.mTemplate.getHeight());
                            } else {
                                cropTo16x9 = BitmapCropper.cropTo16x9(EngineActivity.this.blurredImageView.getBitmapOriginal(), EngineActivity.this.mTemplate.getWidth(), EngineActivity.this.mTemplate.getHeight());
                            }
                            Bitmap bitmap = cropTo16x9;
                            EngineActivity.this.blurredImageView.updatePosCanvas(EngineActivity.this.mTemplate.getWidth(), EngineActivity.this.mTemplate.getHeight(), bitmap);
                            EngineActivity.this.blurredImageView.updateIpad(bitmap, EngineActivity.this.mTemplate.getIpad_type(), EngineActivity.this.mTemplate.geTypeResize());
                            if (EngineActivity.this.mTemplate.getIpad_type() == IpadType.IPAD_NEOMORPHIC.ordinal()) {
                                i = (int) (EngineActivity.this.blurredImageView.getIpad_rect().width() * 0.6f);
                                EngineActivity.this.mTemplate.setDrawingTranslation(EngineActivity.this.blurredImageView.getBtmX(), EngineActivity.this.blurredImageView.getBtmY());
                                i2 = Math.round(EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth() * EngineActivity.this.mTemplate.getX_square());
                                i3 = Math.round(EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight() * EngineActivity.this.mTemplate.getY_square());
                                int i6 = i + i2;
                                if (i6 > EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth()) {
                                    i2 -= i6 - EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth();
                                    i6 = EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth();
                                }
                                int i7 = i + i3;
                                if (i7 > EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight()) {
                                    i3 -= i7 - EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight();
                                    i7 = EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight();
                                }
                                if (i2 < 0) {
                                    i2 = 0;
                                }
                                if (i3 < 0) {
                                    i3 = 0;
                                }
                                rect = new Rect(i2, i3, i6, i7);
                                int width3 = (int) (EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth() * EngineActivity.this.mTemplate.getWidth_square());
                                int height3 = (int) (EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight() * EngineActivity.this.mTemplate.getHeight_square());
                                cropToSquareWithRoundCorners = UtilsBitmap.cropToSquareWithRoundCorners(EngineActivity.this.blurredImageView.getBitmapOriginal(), rect, i, width3, height3);
                                rect.right = rect.left + width3;
                                rect.bottom = rect.top + height3;
                                EngineActivity.this.blurredImageView.setRectSquare(rect);
                            } else {
                                if (EngineActivity.this.mTemplate.getIpad_type() != IpadType.IPAD.ordinal() && EngineActivity.this.mTemplate.getIpad_type() != IpadType.IPAD_UNBLUR.ordinal() && EngineActivity.this.mTemplate.getIpad_type() != IpadType.IPAD_CLASSIC.ordinal()) {
                                    int width4 = (int) (EngineActivity.this.blurredImageView.getIpad_rect().width() * 1.0f);
                                    int height4 = (int) (bitmap.getHeight() * 0.5355f);
                                    EngineActivity.this.mTemplate.setDrawingTranslation(EngineActivity.this.blurredImageView.getBtmX(), EngineActivity.this.blurredImageView.getBtmY());
                                    int round3 = Math.round(EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth() * EngineActivity.this.mTemplate.getX_square());
                                    int round4 = Math.round(EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight() * EngineActivity.this.mTemplate.getY_square());
                                    int i8 = width4 + round3;
                                    if (i8 > EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth()) {
                                        round3 -= i8 - EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth();
                                        i8 = EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth();
                                    }
                                    int i9 = height4 + round4;
                                    if (i9 > EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight()) {
                                        round4 -= i9 - EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight();
                                        i9 = EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight();
                                    }
                                    if (round3 < 0) {
                                        round3 = 0;
                                    }
                                    if (round4 < 0) {
                                        round4 = 0;
                                    }
                                    rect = new Rect(round3, round4, i8, i9);
                                    int width5 = (int) (EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth() * EngineActivity.this.mTemplate.getWidth_square());
                                    int height5 = (int) (EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight() * EngineActivity.this.mTemplate.getHeight_square());
                                    cropToSquareWithRoundCorners = UtilsBitmap.cropToSquare(EngineActivity.this.blurredImageView.getBitmapOriginal(), rect, width5, height5);
                                    EngineActivity.this.blurredImageView.setBitmapSquare(cropToSquareWithRoundCorners);
                                    EngineActivity.this.blurredImageView.setRadius_square(0);
                                    rect.right = rect.left + width5;
                                    rect.bottom = rect.top + height5;
                                    EngineActivity.this.blurredImageView.setRectSquare(rect);
                                    i2 = round3;
                                    i = 0;
                                    i3 = round4;
                                }
                                int width6 = (int) (EngineActivity.this.blurredImageView.getIpad_rect().width() * 0.87530595f);
                                int i10 = (int) (width6 * 1.13f);
                                int min = Math.min(width6, i10);
                                EngineActivity.this.mTemplate.setDrawingTranslation(EngineActivity.this.blurredImageView.getBtmX(), EngineActivity.this.blurredImageView.getBtmY());
                                int round5 = Math.round(EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth() * EngineActivity.this.mTemplate.getX_square());
                                int round6 = Math.round(EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight() * EngineActivity.this.mTemplate.getY_square());
                                int i11 = width6 + round5;
                                if (i11 > EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth()) {
                                    round5 -= i11 - EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth();
                                    i11 = EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth();
                                }
                                int i12 = i10 + round6;
                                if (i12 > EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight()) {
                                    round6 -= i12 - EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight();
                                    i12 = EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight();
                                }
                                if (round5 < 0) {
                                    round5 = 0;
                                }
                                if (round6 < 0) {
                                    round6 = 0;
                                }
                                rect = new Rect(round5, round6, i11, i12);
                                if (EngineActivity.this.mTemplate.getIpad_type() == IpadType.IPAD_CLASSIC.ordinal()) {
                                    int width7 = (int) (EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth() * EngineActivity.this.mTemplate.getWidth_square());
                                    int height6 = (int) (EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight() * EngineActivity.this.mTemplate.getHeight_square());
                                    Bitmap cropToSquare2 = UtilsBitmap.cropToSquare(EngineActivity.this.blurredImageView.getBitmapOriginal(), rect, width7, height6);
                                    EngineActivity.this.blurredImageView.setBitmapSquare(cropToSquare2);
                                    EngineActivity.this.blurredImageView.setRadius_square(0);
                                    rect.right = rect.left + width7;
                                    rect.bottom = rect.top + height6;
                                    EngineActivity.this.blurredImageView.setRectSquare(rect);
                                    cropToSquareWithRoundCorners = cropToSquare2;
                                    i = 0;
                                } else {
                                    i = (int) (min * 0.10800001f);
                                    int width8 = (int) (EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth() * EngineActivity.this.mTemplate.getWidth_square());
                                    int height7 = (int) (EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight() * EngineActivity.this.mTemplate.getHeight_square());
                                    cropToSquareWithRoundCorners = UtilsBitmap.cropToSquareWithRoundCorners(EngineActivity.this.blurredImageView.getBitmapOriginal(), rect, i, width8, height7);
                                    rect.right = rect.left + width8;
                                    rect.bottom = rect.top + height7;
                                    EngineActivity.this.blurredImageView.setRectSquare(rect);
                                }
                                i2 = round5;
                                i3 = round6;
                            }
                            Rect rect3 = rect;
                            Bitmap bitmap2 = cropToSquareWithRoundCorners;
                            EngineActivity engineActivity = EngineActivity.this;
                            EngineActivity.this.mTemplate.setUri_bg_ffmpeg(EngineActivity.this.blurredImageView.setupBitmapDraw(UtilsBitmap.blurInSave(engineActivity, bitmap, 20, 1, engineActivity.mTemplate.getWidth(), EngineActivity.this.mTemplate.getHeight()), bitmap2, EngineActivity.this.mTemplate));
                            EngineActivity.this.mTemplate.getSquareBitmapModel().set(EngineActivity.this.blurredImageView.getLeft_square(), EngineActivity.this.blurredImageView.getTop_square(), i2, i3, rect3.width(), rect3.height(), bitmap2.getWidth(), bitmap2.getHeight(), i);
                        }
                        EngineActivity.this.saveTemplate();
                        Intent intent = new Intent(EngineActivity.this, (Class<?>) ProgressViewActivity.class);
                        intent.putExtra(Common.TEMPLATE, EngineActivity.this.mTemplate.getIdTemplate());
                        intent.addFlags(65536);
                        EngineActivity.this.startActivity(intent);
                        EngineActivity.this.overridePendingTransition(0, 0);
                        EngineActivity.this.finish();
                    }
                    Bitmap createBitmap = Bitmap.createBitmap(EngineActivity.this.mTemplate.getWidth(), EngineActivity.this.mTemplate.getHeight(), Bitmap.Config.RGB_565);
                    createBitmap.eraseColor(ViewCompat.MEASURED_STATE_MASK);
                    EngineActivity.this.blurredImageView.updatePosCanvas(EngineActivity.this.mTemplate.getWidth(), EngineActivity.this.mTemplate.getHeight(), createBitmap);
                    EngineActivity.this.blurredImageView.updateIpad(createBitmap, EngineActivity.this.mTemplate.getIpad_type(), EngineActivity.this.mTemplate.geTypeResize());
                    EngineActivity.this.mTemplate.setUri_bg_ffmpeg(EngineActivity.this.blurredImageView.setupBitmapDraw(createBitmap, null, EngineActivity.this.mTemplate));
                    EngineActivity.this.saveTemplate();
                    Intent intent2 = new Intent(EngineActivity.this, (Class<?>) ProgressViewActivity.class);
                    intent2.putExtra(Common.TEMPLATE, EngineActivity.this.mTemplate.getIdTemplate());
                    intent2.addFlags(65536);
                    EngineActivity.this.startActivity(intent2);
                    EngineActivity.this.overridePendingTransition(0, 0);
                    EngineActivity.this.finish();
                } catch (Exception e) {
                    Log.e("Tag : ", "init " + e.getMessage());
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setupShowFragment(String str) {
        findViewById(R.id.layout_time).setVisibility(4);
        findViewById(R.id.layout_menu).setVisibility(4);
        if (str != null) {
            this.tv_tittle_fragment.setText(str);
            this.tv_tittle_fragment.setVisibility(0);
            LinearLayout linearLayout = this.btnChangeResize;
            if (linearLayout != null) {
                linearLayout.setVisibility(4);
            }
        }
        this.btn_cancel.setVisibility(4);
        this.btn_export.setVisibility(4);
        this.btn_setup_fps.setVisibility(4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setupHideFragment() {
        findViewById(R.id.layout_time).setVisibility(0);
        findViewById(R.id.layout_menu).setVisibility(0);
        this.tv_tittle_fragment.setVisibility(8);
        LinearLayout linearLayout = this.btnChangeResize;
        if (linearLayout != null) {
            linearLayout.setVisibility(0);
        }
        this.btn_cancel.setVisibility(0);
        this.btn_export.setVisibility(0);
        this.btn_setup_fps.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showEditAudioEntity(EntityAudio entityAudio) {
        findViewById(R.id.layout_menu).setVisibility(4);
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        this.mCurrentFragment = EditMediaFragment.getInstance(this.iEditMediaCallback, this.mResources, entityAudio, -this.trackViewEntity.getCurrentPosition());
        beginTransaction.replace(R.id.m_container, this.mCurrentFragment);
        beginTransaction.commit();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showEditMultipleEntity(int i) {
        if (EditMultipleEntityFragment.instance != null) {
            EditMultipleEntityFragment.instance.setCount_select(i);
            return;
        }
        findViewById(R.id.layout_menu).setVisibility(4);
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        this.mCurrentFragment = EditMultipleEntityFragment.getInstance(this.iEditMultipleCallback, this.mResources, i);
        beginTransaction.replace(R.id.m_container, this.mCurrentFragment);
        beginTransaction.commit();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showEditEntity(Entity entity) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        this.mCurrentFragment = EditEntityFragment.getInstance(this.iEditEntityCallback, this.mResources, entity, -this.trackViewEntity.getCurrentPosition());
        beginTransaction.replace(R.id.m_container, this.mCurrentFragment);
        beginTransaction.commit();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showEditTrslEntity(Entity entity) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        this.mCurrentFragment = EditTrslEntityFragment.getInstance(this.iEditTrstEntityCallback, this.mResources, entity, -this.trackViewEntity.getCurrentPosition());
        beginTransaction.replace(R.id.m_container, this.mCurrentFragment);
        beginTransaction.commit();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showEditBismilahEntity(Entity entity) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        this.mCurrentFragment = EditBismilahEntityFragment.getInstance(this.iBismilahEntityCallback, this.mResources, entity, -this.trackViewEntity.getCurrentPosition());
        beginTransaction.replace(R.id.m_container, this.mCurrentFragment);
        beginTransaction.commit();
    }

    private void saveTemplateTmp() {
        String str;
        try {
            if (this.mTemplate == null) {
                this.mTemplate = new Template();
            }
            this.mTemplate.setNewCode();
            this.mTemplate.setGlass(this.blurredImageView.isGlass());
            this.mTemplate.setCurrentCursur(this.trackViewEntity.getCurrent_cursur_position());
            this.mTemplate.setScale_timeline(this.trackViewEntity.getScaleFactor());
            this.mTemplate.setGradient(this.blurredImageView.getColor_gradient());
            this.mTemplate.setDuration(this.trackViewEntity.getMaxTime());
            this.mTemplate.setColor_ipad(this.blurredImageView.colorIpad());
            this.mTemplate.getQuranEntityList().clear();
            this.mTemplate.getTranslationTemplateList().clear();
            this.mTemplate.setUri_bg(this.uri_bg);
            try {
                for (EntityQuranTimeline entityQuranTimeline : this.trackViewEntity.getEntityListQuran()) {
                    if (entityQuranTimeline.visible()) {
                        float f2 = Utils.f2(Math.abs(entityQuranTimeline.getRect().left / this.trackViewEntity.getSecond_in_screen()));
                        float f22 = Utils.f2(Math.abs(entityQuranTimeline.getRect().right / this.trackViewEntity.getSecond_in_screen()));
                        if (entityQuranTimeline.getQuranEntity().getCopyRect() == null) {
                            entityQuranTimeline.getQuranEntity().setCopyRect();
                            if (entityQuranTimeline.getQuranEntity().getCopyRect() == null) {
                            }
                        }
                        EntityQuranTemplate entityQuranTemplate = new EntityQuranTemplate(entityQuranTimeline.getTransition(), f2, f22, entityQuranTimeline.getQuranEntity().getCopyRect().left * this.mTemplate.getWidth(), this.mTemplate.getHeight() * entityQuranTimeline.getQuranEntity().getCopyRect().top, entityQuranTimeline.getRect().left / entityQuranTimeline.getmScaleFactor(), entityQuranTimeline.getRect().right / entityQuranTimeline.getmScaleFactor(), entityQuranTimeline.getQuranEntity().getTxt(), entityQuranTimeline.getQuranEntity().getComplete_aya(), entityQuranTimeline.getQuranEntity().getNameFont(), entityQuranTimeline.getQuranEntity().getIndexNumber(), entityQuranTimeline.getQuranEntity().getNumber(), entityQuranTimeline.getQuranEntity().getClrAya(), entityQuranTimeline.getQuranEntity().getPaintTranslationAya() != null ? entityQuranTimeline.getQuranEntity().getClrTrsl() : InputDeviceCompat.SOURCE_ANY, entityQuranTimeline.getQuranEntity().getmPreset());
                        entityQuranTemplate.setHeight((entityQuranTimeline.getQuranEntity().getCopyRect().bottom * this.mTemplate.getHeight()) - (entityQuranTimeline.getQuranEntity().getCopyRect().top * this.mTemplate.getHeight()));
                        entityQuranTemplate.setFactor_size(entityQuranTimeline.getQuranEntity().getFactorSize());
                        entityQuranTemplate.setFactor_sizeTrl(entityQuranTimeline.getQuranEntity().getFactorSizeTrl());
                        entityQuranTemplate.setScale(entityQuranTimeline.getQuranEntity().getFactor_scale());
                        entityQuranTemplate.setTranslation(entityQuranTimeline.getQuranEntity().getTranslation());
                        entityQuranTemplate.setTranslation_complete(entityQuranTimeline.getQuranEntity().getTranslation_complete());
                        entityQuranTemplate.setStartWord_index(entityQuranTimeline.getQuranEntity().getStartWord_index());
                        entityQuranTemplate.setEndWord_index(entityQuranTimeline.getQuranEntity().getEndWord_index());
                        entityQuranTemplate.setIcon(entityQuranTimeline.getQuranEntity().getIcon());
                        entityQuranTemplate.setFile(entityQuranTimeline.getFile());
                        entityQuranTemplate.setFile_in(entityQuranTimeline.getFile_in());
                        entityQuranTemplate.setFile_out(entityQuranTimeline.getFile_out());
                        entityQuranTemplate.setRectF(new MRectF(entityQuranTimeline.getQuranEntity().getCopyRect().left, entityQuranTimeline.getQuranEntity().getCopyRect().top, entityQuranTimeline.getQuranEntity().getCopyRect().right, entityQuranTimeline.getQuranEntity().getCopyRect().bottom));
                        this.mTemplate.addQuranEntityList(entityQuranTemplate);
                    }
                }
            } catch (Exception e) {
                Log.e("save templete quran", "" + e.getMessage());
            }
            try {
                for (EntityTrslTimeline entityTrslTimeline : this.trackViewEntity.getEntityListTrslQuran()) {
                    if (entityTrslTimeline.visible()) {
                        float f23 = Utils.f2(Math.abs(entityTrslTimeline.getRect().left / this.trackViewEntity.getSecond_in_screen()));
                        float f24 = Utils.f2(Math.abs(entityTrslTimeline.getRect().right / this.trackViewEntity.getSecond_in_screen()));
                        if (entityTrslTimeline.getQuranEntity().getCopyRect() == null) {
                            entityTrslTimeline.getQuranEntity().setCopyRect();
                            if (entityTrslTimeline.getQuranEntity().getCopyRect() == null) {
                            }
                        }
                        EntityTranslationTemplate entityTranslationTemplate = new EntityTranslationTemplate(entityTrslTimeline.getTransition(), f23, f24, entityTrslTimeline.getQuranEntity().getCopyRect().left * this.mTemplate.getWidth(), this.mTemplate.getHeight() * entityTrslTimeline.getQuranEntity().getCopyRect().top, entityTrslTimeline.getRect().left / entityTrslTimeline.getmScaleFactor(), entityTrslTimeline.getRect().right / entityTrslTimeline.getmScaleFactor(), entityTrslTimeline.getQuranEntity().getTxt(), entityTrslTimeline.getQuranEntity().getNameFont(), entityTrslTimeline.getQuranEntity().getNumber(), entityTrslTimeline.getQuranEntity().getClrAya(), entityTrslTimeline.getQuranEntity().getmPreset());
                        entityTranslationTemplate.setHeight((entityTrslTimeline.getQuranEntity().getCopyRect().bottom * this.mTemplate.getHeight()) - (entityTrslTimeline.getQuranEntity().getCopyRect().top * this.mTemplate.getHeight()));
                        entityTranslationTemplate.setFactor_size(entityTrslTimeline.getQuranEntity().getFactorSize());
                        entityTranslationTemplate.setFactor_sizeTrl(entityTrslTimeline.getQuranEntity().getFactorSizeTrl());
                        entityTranslationTemplate.setScale(entityTrslTimeline.getQuranEntity().getFactor_scale());
                        entityTranslationTemplate.setFile(entityTrslTimeline.getFile());
                        entityTranslationTemplate.setFile_in(entityTrslTimeline.getFile_in());
                        entityTranslationTemplate.setFile_out(entityTrslTimeline.getFile_out());
                        entityTranslationTemplate.setClr_bg(entityTrslTimeline.getQuranEntity().getClrBg());
                        entityTranslationTemplate.setHaveBg(entityTrslTimeline.getQuranEntity().isHaveBg());
                        entityTranslationTemplate.setRectF(new MRectF(entityTrslTimeline.getQuranEntity().getCopyRect().left, entityTrslTimeline.getQuranEntity().getCopyRect().top, entityTrslTimeline.getQuranEntity().getCopyRect().right, entityTrslTimeline.getQuranEntity().getCopyRect().bottom));
                        this.mTemplate.addTrslEntityList(entityTranslationTemplate);
                    }
                }
            } catch (Exception e2) {
                Log.e("save templete trsl quran", "" + e2.getMessage());
            }
            this.mTemplate.setEntityIsti3adaTemplate(null);
            if (this.blurredImageView.getmIsti3adhaEntity() != null && this.blurredImageView.getmIsti3adhaEntity().getBismilahTimeline().visible()) {
                EntityBismilahTimeline bismilahTimeline = this.blurredImageView.getmIsti3adhaEntity().getBismilahTimeline();
                float f25 = Utils.f2(Math.abs(bismilahTimeline.getRect().left / this.trackViewEntity.getSecond_in_screen()));
                float f26 = Utils.f2(Math.abs(bismilahTimeline.getRect().right / this.trackViewEntity.getSecond_in_screen()));
                if (bismilahTimeline.getQuranEntity().getCopyRect() == null) {
                    bismilahTimeline.getQuranEntity().setCopyRect();
                }
                EntityBismilahTemplate entityBismilahTemplate = new EntityBismilahTemplate(bismilahTimeline.getTransition(), f25, f26, bismilahTimeline.getQuranEntity().getCopyRect().left * this.mTemplate.getWidth(), this.mTemplate.getHeight() * bismilahTimeline.getQuranEntity().getCopyRect().top, bismilahTimeline.getRect().left / bismilahTimeline.getmScaleFactor(), bismilahTimeline.getRect().right / bismilahTimeline.getmScaleFactor(), bismilahTimeline.getQuranEntity().getTxt(), bismilahTimeline.getQuranEntity().getClrAya(), bismilahTimeline.getQuranEntity().getmPreset());
                entityBismilahTemplate.setHeight((bismilahTimeline.getQuranEntity().getCopyRect().bottom * this.mTemplate.getHeight()) - (bismilahTimeline.getQuranEntity().getCopyRect().top * this.mTemplate.getHeight()));
                entityBismilahTemplate.setFactor_size(bismilahTimeline.getQuranEntity().getFactorSize());
                entityBismilahTemplate.setScale(bismilahTimeline.getQuranEntity().getFactor_scale());
                entityBismilahTemplate.setFile(bismilahTimeline.getFile());
                entityBismilahTemplate.setFile_in(bismilahTimeline.getFile_in());
                entityBismilahTemplate.setFile_out(bismilahTimeline.getFile_out());
                entityBismilahTemplate.setRectF(new MRectF(bismilahTimeline.getQuranEntity().getCopyRect().left, bismilahTimeline.getQuranEntity().getCopyRect().top, bismilahTimeline.getQuranEntity().getCopyRect().right, bismilahTimeline.getQuranEntity().getCopyRect().bottom));
                this.mTemplate.setEntityIsti3adaTemplate(entityBismilahTemplate);
            }
            this.mTemplate.setEntityBismilahTemplate(null);
            if (this.blurredImageView.getBismilahEntity() != null && this.blurredImageView.getBismilahEntity().getBismilahTimeline().visible()) {
                EntityBismilahTimeline bismilahTimeline2 = this.blurredImageView.getBismilahEntity().getBismilahTimeline();
                float f27 = Utils.f2(Math.abs(bismilahTimeline2.getRect().left / this.trackViewEntity.getSecond_in_screen()));
                float f28 = Utils.f2(Math.abs(bismilahTimeline2.getRect().right / this.trackViewEntity.getSecond_in_screen()));
                if (bismilahTimeline2.getQuranEntity().getCopyRect() == null) {
                    bismilahTimeline2.getQuranEntity().setCopyRect();
                }
                EntityBismilahTemplate entityBismilahTemplate2 = new EntityBismilahTemplate(bismilahTimeline2.getTransition(), f27, f28, bismilahTimeline2.getQuranEntity().getCopyRect().left * this.mTemplate.getWidth(), this.mTemplate.getHeight() * bismilahTimeline2.getQuranEntity().getCopyRect().top, bismilahTimeline2.getRect().left / bismilahTimeline2.getmScaleFactor(), bismilahTimeline2.getRect().right / bismilahTimeline2.getmScaleFactor(), bismilahTimeline2.getQuranEntity().getTxt(), bismilahTimeline2.getQuranEntity().getClrAya(), bismilahTimeline2.getQuranEntity().getmPreset());
                entityBismilahTemplate2.setHeight((bismilahTimeline2.getQuranEntity().getCopyRect().bottom * this.mTemplate.getHeight()) - (bismilahTimeline2.getQuranEntity().getCopyRect().top * this.mTemplate.getHeight()));
                entityBismilahTemplate2.setFactor_size(bismilahTimeline2.getQuranEntity().getFactorSize());
                entityBismilahTemplate2.setScale(bismilahTimeline2.getQuranEntity().getFactor_scale());
                entityBismilahTemplate2.setFile(bismilahTimeline2.getFile());
                entityBismilahTemplate2.setFile_in(bismilahTimeline2.getFile_in());
                entityBismilahTemplate2.setFile_out(bismilahTimeline2.getFile_out());
                entityBismilahTemplate2.setRectF(new MRectF(bismilahTimeline2.getQuranEntity().getCopyRect().left, bismilahTimeline2.getQuranEntity().getCopyRect().top, bismilahTimeline2.getQuranEntity().getCopyRect().right, bismilahTimeline2.getQuranEntity().getCopyRect().bottom));
                this.mTemplate.setEntityBismilahTemplate(entityBismilahTemplate2);
            }
            if (this.blurredImageView.getSurahNameEntity() == null) {
                str = "";
            } else if (this.mTemplate.getEntitySurahTemplate() == null) {
                str = "";
                this.mTemplate.setEntitySurahTemplate(new EntitySurahTemplate(this.blurredImageView.getSurahNameEntity().getName(), this.blurredImageView.getSurahNameEntity().getReader(), this.mTemplate.getmDrawingTranslationX() + this.blurredImageView.getRectFSurahName().left, this.mTemplate.getmDrawingTranslationY() + this.blurredImageView.getRectFSurahName().top, new MRectF(this.blurredImageView.getSurahNameEntity().getCopyRect().left, this.blurredImageView.getSurahNameEntity().getCopyRect().top, this.blurredImageView.getSurahNameEntity().getCopyRect().right, this.blurredImageView.getSurahNameEntity().getCopyRect().bottom), this.blurredImageView.getSurahNameEntity().getFactor_scale(), this.blurredImageView.getSurahNameEntity().getNameFont(), this.blurredImageView.getSurahNameEntity().getClrS_name(), this.blurredImageView.getSurahNameEntity().getmPreset(), this.blurredImageView.getSurahNameEntity().getStyle(), this.blurredImageView.getSurahNameEntity().getIndex_surah(), this.blurredImageView.getSurahNameEntity().isHaveBg(), this.blurredImageView.getSurahNameEntity().getClrBg()));
            } else {
                str = "";
                this.mTemplate.getEntitySurahTemplate().setClrBg(this.blurredImageView.getSurahNameEntity().getClrBg());
                this.mTemplate.getEntitySurahTemplate().setHaveBg(this.blurredImageView.getSurahNameEntity().isHaveBg());
                this.mTemplate.getEntitySurahTemplate().setIndex_surah(this.blurredImageView.getSurahNameEntity().getIndex_surah());
                this.mTemplate.getEntitySurahTemplate().setStyle(this.blurredImageView.getSurahNameEntity().getStyle());
                this.mTemplate.getEntitySurahTemplate().setClr(this.blurredImageView.getSurahNameEntity().getClrS_name());
                this.mTemplate.getEntitySurahTemplate().setPreset(this.blurredImageView.getSurahNameEntity().getmPreset());
                this.mTemplate.getEntitySurahTemplate().setName_font(this.blurredImageView.getSurahNameEntity().getNameFont());
                this.mTemplate.getEntitySurahTemplate().setFactor_scale(this.blurredImageView.getSurahNameEntity().getFactor_scale());
                this.mTemplate.getEntitySurahTemplate().setRectF(new MRectF(this.blurredImageView.getSurahNameEntity().getCopyRect().left, this.blurredImageView.getSurahNameEntity().getCopyRect().top, this.blurredImageView.getSurahNameEntity().getCopyRect().right, this.blurredImageView.getSurahNameEntity().getCopyRect().bottom));
                this.mTemplate.getEntitySurahTemplate().setName(this.blurredImageView.getSurahNameEntity().getName());
                this.mTemplate.getEntitySurahTemplate().setReader(this.blurredImageView.getSurahNameEntity().getReader());
                this.mTemplate.getEntitySurahTemplate().setPos(this.blurredImageView.getRectFSurahName().left + this.mTemplate.getmDrawingTranslationX(), this.blurredImageView.getRectFSurahName().top + this.mTemplate.getmDrawingTranslationY());
            }
            if (this.mTemplate.getEntityProgressTemplate() == null) {
                this.mTemplate.setEntityProgressTemplate(new EntityProgressTemplate(Utils.f2(this.blurredImageView.getRectFProgress().left + this.mTemplate.getmDrawingTranslationX()), Utils.f2(this.blurredImageView.getRectFProgress().top + this.mTemplate.getmDrawingTranslationY())));
            } else {
                this.mTemplate.getEntityProgressTemplate().setLeft(Utils.f2(this.blurredImageView.getRectFProgress().left + this.mTemplate.getmDrawingTranslationX()));
                this.mTemplate.getEntityProgressTemplate().setTop(Utils.f2(this.blurredImageView.getRectFProgress().top + this.mTemplate.getmDrawingTranslationY()));
            }
            this.mTemplate.getEntityMediaList().clear();
            for (EntityAudio entityAudio : this.trackViewEntity.getEntityListAudio()) {
                if (entityAudio.visible() && entityAudio.getEnd() > entityAudio.getStart()) {
                    EntityMedia entityMedia = new EntityMedia(entityAudio.getUri().toString(), entityAudio.getMin_duration(), entityAudio.getStart(), entityAudio.getEnd(), entityAudio.getRect().left / this.trackViewEntity.getScaleFactor(), entityAudio.getRect().right / this.trackViewEntity.getScaleFactor(), Math.round(entityAudio.getEnd() - entityAudio.getStart()), entityAudio.getOffset(), entityAudio.getOffset_right(), entityAudio.getOffset_left(), entityAudio.getMax(), entityAudio.getFade_in(), entityAudio.getFade_out(), (entityAudio.getRect().left / this.trackViewEntity.getSecond_in_screen()) * 1000.0f);
                    entityMedia.setPaths_https(entityAudio.getPaths_http());
                    entityMedia.setEffectAudio(entityAudio.getEffectAudio());
                    entityMedia.setPath_ffmpeg(entityAudio.getPath_ffmpeg());
                    entityMedia.setPath_ffmpeg_effect(entityAudio.getPath_ffmpeg_effect());
                    entityMedia.setVideo_path(entityAudio.getVideo_path());
                    entityMedia.setApplyEffectInPreview(entityAudio.isApplyEffectInPreview());
                    this.mTemplate.addMedia(entityMedia);
                }
            }
            this.mTemplate.setUri_video(new FileHelper(this).createPublicVideoFolder(this.mResources.getString(R.string.app_name)).getAbsolutePath() + "/" + System.currentTimeMillis() + "_NurMontage.mp4");
            LocalPersistence.writeTemplate(this, this.mTemplate, str, Common.TEMPLATE_TMP);
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveTemplate() {
        Iterator<EntityAudio> it = null;
        EngineActivity engineActivity = this;
        try {
            if (engineActivity.mTemplate == null) {
                engineActivity.mTemplate = new Template();
            }
            engineActivity.mTemplate.setNewCode();
            engineActivity.mTemplate.setGlass(engineActivity.blurredImageView.isGlass());
            engineActivity.mTemplate.setCurrentCursur(engineActivity.trackViewEntity.getCurrent_cursur_position());
            engineActivity.mTemplate.setScale_timeline(engineActivity.trackViewEntity.getScaleFactor());
            engineActivity.mTemplate.setDuration(engineActivity.trackViewEntity.getMaxTime());
            engineActivity.mTemplate.setGradient(engineActivity.blurredImageView.getColor_gradient());
            engineActivity.mTemplate.setColor_ipad(engineActivity.blurredImageView.colorIpad());
            engineActivity.mTemplate.getQuranEntityList().clear();
            engineActivity.mTemplate.getTranslationTemplateList().clear();
            engineActivity.mTemplate.setUri_bg(engineActivity.uri_bg);
            try {
                for (EntityQuranTimeline entityQuranTimeline : engineActivity.trackViewEntity.getEntityListQuran()) {
                    if (entityQuranTimeline.visible()) {
                        float f2 = Utils.f2(Math.abs(entityQuranTimeline.getRect().left / engineActivity.trackViewEntity.getSecond_in_screen()));
                        float f22 = Utils.f2(Math.abs(entityQuranTimeline.getRect().right / engineActivity.trackViewEntity.getSecond_in_screen()));
                        if (entityQuranTimeline.getQuranEntity().getCopyRect() == null) {
                            entityQuranTimeline.getQuranEntity().setCopyRect();
                            if (entityQuranTimeline.getQuranEntity().getCopyRect() == null) {
                            }
                        }
                        EntityQuranTemplate entityQuranTemplate = new EntityQuranTemplate(entityQuranTimeline.getTransition(), f2, f22, entityQuranTimeline.getQuranEntity().getCopyRect().left * engineActivity.mTemplate.getWidth(), engineActivity.mTemplate.getHeight() * entityQuranTimeline.getQuranEntity().getCopyRect().top, entityQuranTimeline.getRect().left / entityQuranTimeline.getmScaleFactor(), entityQuranTimeline.getRect().right / entityQuranTimeline.getmScaleFactor(), entityQuranTimeline.getQuranEntity().getTxt(), entityQuranTimeline.getQuranEntity().getComplete_aya(), entityQuranTimeline.getQuranEntity().getNameFont(), entityQuranTimeline.getQuranEntity().getIndexNumber(), entityQuranTimeline.getQuranEntity().getNumber(), entityQuranTimeline.getQuranEntity().getClrAya(), entityQuranTimeline.getQuranEntity().getPaintTranslationAya() != null ? entityQuranTimeline.getQuranEntity().getClrTrsl() : InputDeviceCompat.SOURCE_ANY, entityQuranTimeline.getQuranEntity().getmPreset());
                        entityQuranTemplate.setHeight((entityQuranTimeline.getQuranEntity().getCopyRect().bottom * engineActivity.mTemplate.getHeight()) - (entityQuranTimeline.getQuranEntity().getCopyRect().top * engineActivity.mTemplate.getHeight()));
                        entityQuranTemplate.setFactor_size(entityQuranTimeline.getQuranEntity().getFactorSize());
                        entityQuranTemplate.setFactor_sizeTrl(entityQuranTimeline.getQuranEntity().getFactorSizeTrl());
                        entityQuranTemplate.setScale(entityQuranTimeline.getQuranEntity().getFactor_scale());
                        entityQuranTemplate.setTranslation(entityQuranTimeline.getQuranEntity().getTranslation());
                        entityQuranTemplate.setTranslation_complete(entityQuranTimeline.getQuranEntity().getTranslation_complete());
                        entityQuranTemplate.setStartWord_index(entityQuranTimeline.getQuranEntity().getStartWord_index());
                        entityQuranTemplate.setEndWord_index(entityQuranTimeline.getQuranEntity().getEndWord_index());
                        entityQuranTemplate.setIcon(entityQuranTimeline.getQuranEntity().getIcon());
                        entityQuranTemplate.setFile(entityQuranTimeline.getFile());
                        entityQuranTemplate.setFile_in(entityQuranTimeline.getFile_in());
                        entityQuranTemplate.setFile_out(entityQuranTimeline.getFile_out());
                        entityQuranTemplate.setRectF(new MRectF(entityQuranTimeline.getQuranEntity().getCopyRect().left, entityQuranTimeline.getQuranEntity().getCopyRect().top, entityQuranTimeline.getQuranEntity().getCopyRect().right, entityQuranTimeline.getQuranEntity().getCopyRect().bottom));
                        engineActivity.mTemplate.addQuranEntityList(entityQuranTemplate);
                    }
                }
            } catch (Exception e) {
                Log.e("save templete quran", "" + e.getMessage());
            }
            try {
                for (EntityTrslTimeline entityTrslTimeline : engineActivity.trackViewEntity.getEntityListTrslQuran()) {
                    if (entityTrslTimeline.visible()) {
                        float f23 = Utils.f2(Math.abs(entityTrslTimeline.getRect().left / engineActivity.trackViewEntity.getSecond_in_screen()));
                        float f24 = Utils.f2(Math.abs(entityTrslTimeline.getRect().right / engineActivity.trackViewEntity.getSecond_in_screen()));
                        if (entityTrslTimeline.getQuranEntity().getCopyRect() == null) {
                            entityTrslTimeline.getQuranEntity().setCopyRect();
                            if (entityTrslTimeline.getQuranEntity().getCopyRect() == null) {
                            }
                        }
                        EntityTranslationTemplate entityTranslationTemplate = new EntityTranslationTemplate(entityTrslTimeline.getTransition(), f23, f24, entityTrslTimeline.getQuranEntity().getCopyRect().left * engineActivity.mTemplate.getWidth(), engineActivity.mTemplate.getHeight() * entityTrslTimeline.getQuranEntity().getCopyRect().top, entityTrslTimeline.getRect().left / entityTrslTimeline.getmScaleFactor(), entityTrslTimeline.getRect().right / entityTrslTimeline.getmScaleFactor(), entityTrslTimeline.getQuranEntity().getTxt(), entityTrslTimeline.getQuranEntity().getNameFont(), entityTrslTimeline.getQuranEntity().getNumber(), entityTrslTimeline.getQuranEntity().getClrAya(), entityTrslTimeline.getQuranEntity().getmPreset());
                        entityTranslationTemplate.setHeight((entityTrslTimeline.getQuranEntity().getCopyRect().bottom * engineActivity.mTemplate.getHeight()) - (entityTrslTimeline.getQuranEntity().getCopyRect().top * engineActivity.mTemplate.getHeight()));
                        entityTranslationTemplate.setFactor_size(entityTrslTimeline.getQuranEntity().getFactorSize());
                        entityTranslationTemplate.setFactor_sizeTrl(entityTrslTimeline.getQuranEntity().getFactorSizeTrl());
                        entityTranslationTemplate.setScale(entityTrslTimeline.getQuranEntity().getFactor_scale());
                        entityTranslationTemplate.setFile(entityTrslTimeline.getFile());
                        entityTranslationTemplate.setFile_in(entityTrslTimeline.getFile_in());
                        entityTranslationTemplate.setFile_out(entityTrslTimeline.getFile_out());
                        entityTranslationTemplate.setClr_bg(entityTrslTimeline.getQuranEntity().getClrBg());
                        entityTranslationTemplate.setHaveBg(entityTrslTimeline.getQuranEntity().isHaveBg());
                        entityTranslationTemplate.setRectF(new MRectF(entityTrslTimeline.getQuranEntity().getCopyRect().left, entityTrslTimeline.getQuranEntity().getCopyRect().top, entityTrslTimeline.getQuranEntity().getCopyRect().right, entityTrslTimeline.getQuranEntity().getCopyRect().bottom));
                        engineActivity.mTemplate.addTrslEntityList(entityTranslationTemplate);
                    }
                }
            } catch (Exception e2) {
                Log.e("save templete trsl quran", "" + e2.getMessage());
            }
            engineActivity.mTemplate.setEntityIsti3adaTemplate(null);
            if (engineActivity.blurredImageView.getmIsti3adhaEntity() != null && engineActivity.blurredImageView.getmIsti3adhaEntity().getBismilahTimeline().visible()) {
                EntityBismilahTimeline bismilahTimeline = engineActivity.blurredImageView.getmIsti3adhaEntity().getBismilahTimeline();
                float f25 = Utils.f2(Math.abs(bismilahTimeline.getRect().left / engineActivity.trackViewEntity.getSecond_in_screen()));
                float f26 = Utils.f2(Math.abs(bismilahTimeline.getRect().right / engineActivity.trackViewEntity.getSecond_in_screen()));
                if (bismilahTimeline.getQuranEntity().getCopyRect() == null) {
                    bismilahTimeline.getQuranEntity().setCopyRect();
                }
                EntityBismilahTemplate entityBismilahTemplate = new EntityBismilahTemplate(bismilahTimeline.getTransition(), f25, f26, bismilahTimeline.getQuranEntity().getCopyRect().left * engineActivity.mTemplate.getWidth(), engineActivity.mTemplate.getHeight() * bismilahTimeline.getQuranEntity().getCopyRect().top, bismilahTimeline.getRect().left / bismilahTimeline.getmScaleFactor(), bismilahTimeline.getRect().right / bismilahTimeline.getmScaleFactor(), bismilahTimeline.getQuranEntity().getTxt(), bismilahTimeline.getQuranEntity().getClrAya(), bismilahTimeline.getQuranEntity().getmPreset());
                entityBismilahTemplate.setHeight((bismilahTimeline.getQuranEntity().getCopyRect().bottom * engineActivity.mTemplate.getHeight()) - (bismilahTimeline.getQuranEntity().getCopyRect().top * engineActivity.mTemplate.getHeight()));
                entityBismilahTemplate.setFactor_size(bismilahTimeline.getQuranEntity().getFactorSize());
                entityBismilahTemplate.setScale(bismilahTimeline.getQuranEntity().getFactor_scale());
                entityBismilahTemplate.setFile(bismilahTimeline.getFile());
                entityBismilahTemplate.setFile_in(bismilahTimeline.getFile_in());
                entityBismilahTemplate.setFile_out(bismilahTimeline.getFile_out());
                entityBismilahTemplate.setRectF(new MRectF(bismilahTimeline.getQuranEntity().getCopyRect().left, bismilahTimeline.getQuranEntity().getCopyRect().top, bismilahTimeline.getQuranEntity().getCopyRect().right, bismilahTimeline.getQuranEntity().getCopyRect().bottom));
                engineActivity.mTemplate.setEntityIsti3adaTemplate(entityBismilahTemplate);
            }
            engineActivity.mTemplate.setEntityBismilahTemplate(null);
            if (engineActivity.blurredImageView.getBismilahEntity() != null && engineActivity.blurredImageView.getBismilahEntity().getBismilahTimeline().visible()) {
                EntityBismilahTimeline bismilahTimeline2 = engineActivity.blurredImageView.getBismilahEntity().getBismilahTimeline();
                float f27 = Utils.f2(Math.abs(bismilahTimeline2.getRect().left / engineActivity.trackViewEntity.getSecond_in_screen()));
                float f28 = Utils.f2(Math.abs(bismilahTimeline2.getRect().right / engineActivity.trackViewEntity.getSecond_in_screen()));
                if (bismilahTimeline2.getQuranEntity().getCopyRect() == null) {
                    bismilahTimeline2.getQuranEntity().setCopyRect();
                }
                EntityBismilahTemplate entityBismilahTemplate2 = new EntityBismilahTemplate(bismilahTimeline2.getTransition(), f27, f28, bismilahTimeline2.getQuranEntity().getCopyRect().left * engineActivity.mTemplate.getWidth(), engineActivity.mTemplate.getHeight() * bismilahTimeline2.getQuranEntity().getCopyRect().top, bismilahTimeline2.getRect().left / bismilahTimeline2.getmScaleFactor(), bismilahTimeline2.getRect().right / bismilahTimeline2.getmScaleFactor(), bismilahTimeline2.getQuranEntity().getTxt(), bismilahTimeline2.getQuranEntity().getClrAya(), bismilahTimeline2.getQuranEntity().getmPreset());
                entityBismilahTemplate2.setHeight((bismilahTimeline2.getQuranEntity().getCopyRect().bottom * engineActivity.mTemplate.getHeight()) - (bismilahTimeline2.getQuranEntity().getCopyRect().top * engineActivity.mTemplate.getHeight()));
                entityBismilahTemplate2.setFactor_size(bismilahTimeline2.getQuranEntity().getFactorSize());
                entityBismilahTemplate2.setScale(bismilahTimeline2.getQuranEntity().getFactor_scale());
                entityBismilahTemplate2.setFile(bismilahTimeline2.getFile());
                entityBismilahTemplate2.setFile_in(bismilahTimeline2.getFile_in());
                entityBismilahTemplate2.setFile_out(bismilahTimeline2.getFile_out());
                entityBismilahTemplate2.setRectF(new MRectF(bismilahTimeline2.getQuranEntity().getCopyRect().left, bismilahTimeline2.getQuranEntity().getCopyRect().top, bismilahTimeline2.getQuranEntity().getCopyRect().right, bismilahTimeline2.getQuranEntity().getCopyRect().bottom));
                engineActivity.mTemplate.setEntityBismilahTemplate(entityBismilahTemplate2);
            }
            if (engineActivity.blurredImageView.getSurahNameEntity() != null) {
                if (engineActivity.mTemplate.getEntitySurahTemplate() == null) {
                    try {
                        if (engineActivity.blurredImageView.getSurahNameEntity().getCopyRect() == null) {
                            engineActivity.blurredImageView.getSurahNameEntity().setCopyRect();
                        }
                        engineActivity.mTemplate.setEntitySurahTemplate(new EntitySurahTemplate(engineActivity.blurredImageView.getSurahNameEntity().getName(), engineActivity.blurredImageView.getSurahNameEntity().getReader(), engineActivity.mTemplate.getmDrawingTranslationX() + engineActivity.blurredImageView.getRectFSurahName().left, engineActivity.mTemplate.getmDrawingTranslationY() + engineActivity.blurredImageView.getRectFSurahName().top, new MRectF(engineActivity.blurredImageView.getSurahNameEntity().getCopyRect().left, engineActivity.blurredImageView.getSurahNameEntity().getCopyRect().top, engineActivity.blurredImageView.getSurahNameEntity().getCopyRect().right, engineActivity.blurredImageView.getSurahNameEntity().getCopyRect().bottom), engineActivity.blurredImageView.getSurahNameEntity().getFactor_scale(), engineActivity.blurredImageView.getSurahNameEntity().getNameFont(), engineActivity.blurredImageView.getSurahNameEntity().getClrS_name(), engineActivity.blurredImageView.getSurahNameEntity().getmPreset(), engineActivity.blurredImageView.getSurahNameEntity().getStyle(), engineActivity.blurredImageView.getSurahNameEntity().getIndex_surah(), engineActivity.blurredImageView.getSurahNameEntity().isHaveBg(), engineActivity.blurredImageView.getSurahNameEntity().getClrBg()));
                        engineActivity = this;
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                } else {
                    engineActivity.mTemplate.getEntitySurahTemplate().setClrBg(engineActivity.blurredImageView.getSurahNameEntity().getClrBg());
                    engineActivity.mTemplate.getEntitySurahTemplate().setHaveBg(engineActivity.blurredImageView.getSurahNameEntity().isHaveBg());
                    engineActivity.mTemplate.getEntitySurahTemplate().setIndex_surah(engineActivity.blurredImageView.getSurahNameEntity().getIndex_surah());
                    engineActivity.mTemplate.getEntitySurahTemplate().setStyle(engineActivity.blurredImageView.getSurahNameEntity().getStyle());
                    engineActivity.mTemplate.getEntitySurahTemplate().setClr(engineActivity.blurredImageView.getSurahNameEntity().getClrS_name());
                    engineActivity.mTemplate.getEntitySurahTemplate().setPreset(engineActivity.blurredImageView.getSurahNameEntity().getmPreset());
                    engineActivity.mTemplate.getEntitySurahTemplate().setName_font(engineActivity.blurredImageView.getSurahNameEntity().getNameFont());
                    engineActivity.mTemplate.getEntitySurahTemplate().setFactor_scale(engineActivity.blurredImageView.getSurahNameEntity().getFactor_scale());
                    engineActivity.mTemplate.getEntitySurahTemplate().setRectF(new MRectF(engineActivity.blurredImageView.getSurahNameEntity().getCopyRect().left, engineActivity.blurredImageView.getSurahNameEntity().getCopyRect().top, engineActivity.blurredImageView.getSurahNameEntity().getCopyRect().right, engineActivity.blurredImageView.getSurahNameEntity().getCopyRect().bottom));
                    engineActivity.mTemplate.getEntitySurahTemplate().setName(engineActivity.blurredImageView.getSurahNameEntity().getName());
                    engineActivity.mTemplate.getEntitySurahTemplate().setReader(engineActivity.blurredImageView.getSurahNameEntity().getReader());
                    engineActivity.mTemplate.getEntitySurahTemplate().setPos(engineActivity.blurredImageView.getRectFSurahName().left + engineActivity.mTemplate.getmDrawingTranslationX(), engineActivity.blurredImageView.getRectFSurahName().top + engineActivity.mTemplate.getmDrawingTranslationY());
                }
            }
            if (engineActivity.mTemplate.getEntityProgressTemplate() == null) {
                engineActivity.mTemplate.setEntityProgressTemplate(new EntityProgressTemplate(Utils.f2(engineActivity.blurredImageView.getRectFProgress().left + engineActivity.mTemplate.getmDrawingTranslationX()), Utils.f2(engineActivity.blurredImageView.getRectFProgress().top + engineActivity.mTemplate.getmDrawingTranslationY())));
            } else {
                engineActivity.mTemplate.getEntityProgressTemplate().setLeft(Utils.f2(engineActivity.blurredImageView.getRectFProgress().left + engineActivity.mTemplate.getmDrawingTranslationX()));
                engineActivity.mTemplate.getEntityProgressTemplate().setTop(Utils.f2(engineActivity.blurredImageView.getRectFProgress().top + engineActivity.mTemplate.getmDrawingTranslationY()));
            }
            engineActivity.mTemplate.getEntityMediaList().clear();
            Iterator<EntityAudio> it2 = engineActivity.trackViewEntity.getEntityListAudio().iterator();
            while (it2.hasNext()) {
                EntityAudio next = it2.next();
                if (!next.visible()) {
                    it = it2;
                } else if (next.getEnd() > next.getStart()) {
                    it = it2;
                    EntityMedia entityMedia = new EntityMedia(next.getUri().toString(), next.getMin_duration(), next.getStart(), next.getEnd(), next.getRect().left / engineActivity.trackViewEntity.getScaleFactor(), next.getRect().right / engineActivity.trackViewEntity.getScaleFactor(), Math.round(next.getEnd() - next.getStart()), next.getOffset(), next.getOffset_right(), next.getOffset_left(), next.getMax(), next.getFade_in(), next.getFade_out(), (next.getRect().left / engineActivity.trackViewEntity.getSecond_in_screen()) * 1000.0f);
                    entityMedia.setPaths_https(next.getPaths_http());
                    entityMedia.setEffectAudio(next.getEffectAudio());
                    entityMedia.setPath_ffmpeg(next.getPath_ffmpeg());
                    entityMedia.setVideo_path(next.getVideo_path());
                    entityMedia.setPath_ffmpeg_effect(next.getPath_ffmpeg_effect());
                    entityMedia.setApplyEffectInPreview(next.isApplyEffectInPreview());
                    engineActivity.mTemplate.addMedia(entityMedia);
                    next.release();
                }
                it2 = it;
            }
            String str = "Template_" + System.currentTimeMillis();
            String idTemplate = engineActivity.mTemplate.getIdTemplate();
            engineActivity.mTemplate.setIdTemplate(str);
            engineActivity.mTemplate.setUri_video(new FileHelper(engineActivity).createPublicVideoFolder(engineActivity.mResources.getString(R.string.app_name)).getAbsolutePath() + "/" + System.currentTimeMillis() + "_NurMontage.mp4");
            Template template = engineActivity.mTemplate;
            LocalPersistence.writeTemplate(engineActivity, template, idTemplate, template.getIdTemplate());
            LocalPersistence.deleteTemplate(engineActivity, Common.TEMPLATE_TMP);
        } catch (Exception e4) {
            e4.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkPermissionAudio() {
        if (Build.VERSION.SDK_INT < 33 || ActivityCompat.checkSelfPermission(this, "android.permission.READ_MEDIA_AUDIO") == 0) {
            return true;
        }
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_MEDIA_AUDIO"}, 2);
        return false;
    }

    public void pickAudio() {
        try {
            Intent intent = new Intent("android.intent.action.OPEN_DOCUMENT");
            intent.addCategory("android.intent.category.OPENABLE");
            intent.setType("audio/*");
            this.activityLauncher.launch(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 1) {
            if (iArr.length > 0 && iArr[0] == 0) {
                save();
            } else {
                Toast.makeText(this, this.mResources.getString(R.string.permission_img), 0).show();
            }
        }
        if (i == 2) {
            if (iArr.length > 0 && iArr[0] == 0) {
                pickAudio();
            } else {
                Toast.makeText(this, this.mResources.getString(R.string.permission_audio), 0).show();
            }
        }
        if (i == 10) {
            if ((Build.VERSION.SDK_INT >= 34 && ContextCompat.checkSelfPermission(this, "android.permission.READ_MEDIA_VISUAL_USER_SELECTED") == 0) || (iArr.length > 0 && iArr[0] == 0)) {
                imageChooser();
            } else {
                Toast.makeText(this, this.mResources.getString(R.string.permission_img), 0).show();
            }
        }
        if (i == 11) {
            if ((Build.VERSION.SDK_INT >= 34 && ContextCompat.checkSelfPermission(this, "android.permission.READ_MEDIA_VISUAL_USER_SELECTED") == 0) || (iArr.length > 0 && iArr[0] == 0)) {
                videoChooser();
            } else {
                Toast.makeText(this, this.mResources.getString(R.string.permission_video), 0).show();
            }
        }
        if (i == 12) {
            if ((Build.VERSION.SDK_INT >= 34 && ContextCompat.checkSelfPermission(this, "android.permission.READ_MEDIA_VISUAL_USER_SELECTED") == 0) || (iArr.length > 0 && iArr[0] == 0)) {
                videoChooserForAudio();
            } else {
                Toast.makeText(this, this.mResources.getString(R.string.permission_video), 0).show();
            }
        }
    }

    public void startTimelineAnimation() {
        this.entityAudio_visible = null;
        this.entityAudio_player = null;
        this.lastIndexVisible = 0;
        final int maxTime = this.trackViewEntity.getMaxTime();
        final float timeLineW = this.trackViewEntity.getTimeLineW();
        this.timeFormatter = new TimeFormatter(maxTime);
        SmoothTimelineAnimator smoothTimelineAnimator = new SmoothTimelineAnimator(this.startCursur, maxTime, new SmoothTimelineAnimator.AnimatorListener() { // from class: hazem.nurmontage.videoquran.EngineActivity.36
            @Override // hazem.nurmontage.videoquran.Utils.SmoothTimelineAnimator.AnimatorListener
            public void onUpdate(int i) {
                if (!EngineActivity.this.mIsPlaying || i == 0) {
                    return;
                }
                float f = i / maxTime;
                if (EngineActivity.this.blurredImageView != null) {
                    EngineActivity.this.updateTime(i);
                    EngineActivity.this.blurredImageView.setProgress(f);
                }
                EngineActivity.this.trackViewEntity.updateCursur(f * timeLineW);
                EngineActivity.this.trackViewEntity.setCurrent_cursur_position(i);
                float abs = Math.abs(Math.round((EngineActivity.this.trackViewEntity.getCurrentPosition() / EngineActivity.this.trackViewEntity.getSecond_in_screen()) * 1000.0f));
                if (abs > EngineActivity.this.endTimeAudioVisible) {
                    EngineActivity.this.entityAudio_visible = null;
                }
                if (EngineActivity.this.entityAudio_visible == null) {
                    for (int i2 = EngineActivity.this.lastIndexVisible; i2 < EngineActivity.this.trackViewEntity.getEntityListAudio().size(); i2++) {
                        EntityAudio entityAudio = EngineActivity.this.trackViewEntity.getEntityListAudio().get(i2);
                        if (entityAudio.visible() && entityAudio.isVisible()) {
                            EngineActivity.this.entityAudio_visible = entityAudio;
                            EngineActivity engineActivity = EngineActivity.this;
                            engineActivity.endTimeAudioVisible = Math.round((engineActivity.entityAudio_visible.getRect().right / EngineActivity.this.trackViewEntity.getSecond_in_screen()) * 1000.0f);
                            EngineActivity.this.lastIndexVisible = i2;
                            break;
                        }
                    }
                }
                try {
                    if (EngineActivity.this.entityAudio_visible != null) {
                        if (EngineActivity.this.entityAudio_player != EngineActivity.this.entityAudio_visible && EngineActivity.this.mPlayer != null && EngineActivity.this.mPlayer.isPlaying()) {
                            EngineActivity.this.mPlayer.pause();
                        }
                        EngineActivity engineActivity2 = EngineActivity.this;
                        engineActivity2.mPlayer = engineActivity2.entityAudio_visible.getMediaPlayer();
                        if (EngineActivity.this.mPlayer != null && !EngineActivity.this.mPlayer.isPlaying()) {
                            EngineActivity engineActivity3 = EngineActivity.this;
                            engineActivity3.entityAudio_player = engineActivity3.entityAudio_visible;
                            int abs2 = (int) ((abs - Math.abs(Math.round((EngineActivity.this.entityAudio_visible.getRect().left / EngineActivity.this.trackViewEntity.getSecond_in_screen()) * 1000.0f))) + EngineActivity.this.entityAudio_visible.getStart());
                            if (abs2 <= EngineActivity.this.mPlayer.getDuration()) {
                                EngineActivity.this.mPlayer.seekTo(abs2);
                            }
                            Log.e("data", "" + EngineActivity.this.mPlayer.getCurrentPosition());
                            EngineActivity.this.mPlayer.start();
                            Log.e("mPlayer c ", "" + EngineActivity.this.mPlayer.isPlaying());
                        }
                    } else if (EngineActivity.this.mPlayer != null && EngineActivity.this.mPlayer.isPlaying()) {
                        EngineActivity.this.mPlayer.pause();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                EngineActivity engineActivity4 = EngineActivity.this;
                engineActivity4.updateStartViewTime(engineActivity4.trackViewEntity.getCurrent_cursur_position());
                EngineActivity.this.updateBtnCutState();
            }

            @Override // hazem.nurmontage.videoquran.Utils.SmoothTimelineAnimator.AnimatorListener
            public void onEnd() {
                if (EngineActivity.this.mIsPlaying) {
                    EngineActivity.this.mIsPlaying = false;
                    EngineActivity.this.trackViewEntity.setPlaying(EngineActivity.this.mIsPlaying);
                    EngineActivity.this.blurredImageView.setPlaying(EngineActivity.this.mIsPlaying);
                    EngineActivity.this.stop();
                    EngineActivity.this.trackViewEntity.setCurrent_cursur_position(EngineActivity.this.trackViewEntity.getMaxTime());
                    EngineActivity.this.trackViewEntity.updateCursur(EngineActivity.this.trackViewEntity.getMaxTime());
                    try {
                        if (EngineActivity.this.entityAudio_visible != null && EngineActivity.this.entityAudio_visible.getMediaPlayer() != null && EngineActivity.this.entityAudio_visible.getMediaPlayer().isPlaying()) {
                            EngineActivity.this.entityAudio_visible.getMediaPlayer().pause();
                        }
                        if (EngineActivity.this.mPlayer != null && EngineActivity.this.mPlayer.isPlaying()) {
                            EngineActivity.this.mPlayer.pause();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    EngineActivity.this.startCursur = 0;
                    EngineActivity.this.current_position_time = 0;
                    if (EngineActivity.this.btnPlayPause != null) {
                        EngineActivity.this.btnPlayPause.setImageResource(R.drawable.play_btn);
                    }
                    EngineActivity.this.updateBtnToEnd();
                    EngineActivity.this.updateBtnToStart();
                }
            }
        });
        this.valueAnimator = smoothTimelineAnimator;
        smoothTimelineAnimator.start();
        if (this.mTemplate.isVideoSquare()) {
            start();
        }
    }

    public void startTimelineAnimationPreview(final EntityAudio entityAudio) {
        final int maxTime = this.trackViewEntity.getMaxTime();
        final float timeLineW = this.trackViewEntity.getTimeLineW();
        this.timeFormatter = new TimeFormatter(maxTime);
        SmoothTimelineAnimator smoothTimelineAnimator = new SmoothTimelineAnimator(this.startCursur, maxTime, new SmoothTimelineAnimator.AnimatorListener() { // from class: hazem.nurmontage.videoquran.EngineActivity.37
            @Override // hazem.nurmontage.videoquran.Utils.SmoothTimelineAnimator.AnimatorListener
            public void onUpdate(int i) {
                if (!EngineActivity.this.mIsPlaying || i == 0) {
                    return;
                }
                float f = i / maxTime;
                if (EngineActivity.this.blurredImageView != null) {
                    EngineActivity.this.updateTime(i);
                    EngineActivity.this.blurredImageView.setProgress(f);
                }
                EngineActivity.this.trackViewEntity.updateCursur(f * timeLineW);
                EngineActivity.this.trackViewEntity.setCurrent_cursur_position(i);
                try {
                    if (entityAudio.getMediaPlayer() != null && !entityAudio.getMediaPlayer().isPlaying()) {
                        int abs = (int) ((Math.abs(Math.round((EngineActivity.this.trackViewEntity.getCurrentPosition() / EngineActivity.this.trackViewEntity.getSecond_in_screen()) * 1000.0f)) - Math.abs(Math.round((entityAudio.getRect().left / EngineActivity.this.trackViewEntity.getSecond_in_screen()) * 1000.0f))) + entityAudio.getStart());
                        if (abs <= entityAudio.getMediaPlayer().getDuration()) {
                            entityAudio.getMediaPlayer().seekTo(abs);
                        }
                        entityAudio.getMediaPlayer().start();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                EngineActivity engineActivity = EngineActivity.this;
                engineActivity.updateStartViewTime(engineActivity.trackViewEntity.getCurrent_cursur_position());
            }

            @Override // hazem.nurmontage.videoquran.Utils.SmoothTimelineAnimator.AnimatorListener
            public void onEnd() {
                if (EngineActivity.this.mIsPlaying) {
                    EngineActivity.this.mIsPlaying = false;
                    EngineActivity.this.trackViewEntity.setPlaying(EngineActivity.this.mIsPlaying);
                    EngineActivity.this.blurredImageView.setPlaying(EngineActivity.this.mIsPlaying);
                    EngineActivity.this.stop();
                    try {
                        if (entityAudio.getMediaPlayer() != null && entityAudio.getMediaPlayer().isPlaying()) {
                            entityAudio.getMediaPlayer().pause();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    EngineActivity engineActivity = EngineActivity.this;
                    engineActivity.startCursur = engineActivity.trackViewEntity.getCurrent_cursur_position();
                }
                if (VolumeFragment.instance != null) {
                    VolumeFragment.instance.updateButton();
                }
                if (SpeedFragment.instance != null) {
                    SpeedFragment.instance.updateButton();
                }
                if (FadeInOutFragment.instance != null) {
                    FadeInOutFragment.instance.updateButton();
                }
                if (EchoEffectFragment.instance != null) {
                    EchoEffectFragment.instance.updateButton();
                }
                if (EnhanceVoiceFragment.instance != null) {
                    EnhanceVoiceFragment.instance.updateButton();
                }
                if (RemoveNoiceFragment.instance != null) {
                    RemoveNoiceFragment.instance.updateButton();
                }
            }
        });
        this.valueAnimator = smoothTimelineAnimator;
        smoothTimelineAnimator.start();
        if (this.mTemplate.isVideoSquare()) {
            start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTime(long j) {
        TimeFormatter timeFormatter = this.timeFormatter;
        if (timeFormatter == null) {
            this.timeFormatter = new TimeFormatter(this.trackViewEntity.getMaxTime());
        } else {
            timeFormatter.setTotalDurationMs(this.trackViewEntity.getMaxTime());
        }
        android.util.Pair<String, String> formatTime = this.timeFormatter.formatTime(j);
        this.blurredImageView.setCurrentTime((String) formatTime.first, (String) formatTime.second);
    }

    private void initTimeLineView() {
        this.tv_currentTime = (TextView) findViewById(R.id.tv_current_time);
        this.tv_endTime = (TextView) findViewById(R.id.tv_end_time);
        TrackEntityView trackEntityView = (TrackEntityView) findViewById(R.id.time_line_view);
        this.trackViewEntity = trackEntityView;
        trackEntityView.setiTrimLineCallback(this.iTrimLineCallback);
        this.trackViewEntity.setScaleFactor(this.mTemplate.getScale_timeline());
        this.trackViewEntity.post(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.38
            @Override // java.lang.Runnable
            public void run() {
                int screenWidth = ScreenUtils.getScreenWidth(EngineActivity.this);
                float f = screenWidth * 0.12f;
                EngineActivity.this.trackViewEntity.setSecond_in_screen(f);
                EngineActivity.this.trackViewEntity.setSecond_in_screen(f, 0, screenWidth);
                EngineActivity.this.trackViewEntity.setMaxTime(0);
                EngineActivity.this.trackViewEntity.init(screenWidth, EngineActivity.this.trackViewEntity.getHeight());
                EngineActivity.this.trackViewEntity.setPosCursur(EngineActivity.this.mTemplate.getCurrentCursur());
                EngineActivity engineActivity = EngineActivity.this;
                engineActivity.startCursur = engineActivity.trackViewEntity.getCurrent_cursur_position();
                EngineActivity engineActivity2 = EngineActivity.this;
                engineActivity2.updateViewTime(engineActivity2.trackViewEntity.getMaxTime(), EngineActivity.this.trackViewEntity.getCurrent_cursur_position());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addAudioTemplateHttp(final Uri uri, final int i, final String str) {
        String uri2;
        try {
            if (isDestroyed()) {
                return;
            }
            if (uri == null) {
                hideProgressFragment();
                return;
            }
            if (this.mTemplate.getEntityMediaList() != null) {
                updateProgress(i + 1, this.mTemplate.getEntityMediaList().size());
            }
            if (str != null) {
                uri2 = uri.getPath();
            } else if (!uri.toString().contains("share_with_me")) {
                uri2 = AudioUtils.copyFromUri(this, uri, this.mTemplate.getFolder_template());
            } else {
                uri2 = uri.toString();
            }
            final String str2 = uri2;
            final EntityMedia entityMedia = this.mTemplate.getEntityMediaList().get(i);
            if (entityMedia.isApplyEffectInPreview()) {
                final File file = new File(this.mTemplate.getFolder_template(), System.currentTimeMillis() + "_audio_echo.mp3");
                final EffectAudio effectAudio = entityMedia.getEffectAudio();
                float start = effectAudio.getStart() / 1000.0f;
                float end = effectAudio.getEnd() / 1000.0f;
                ArrayList arrayList = new ArrayList();
                arrayList.add("atrim=start=" + start + ":end=" + end);
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
                this.id_ffmpeg.add(Long.valueOf(FFmpegKit.executeWithArgumentsAsync(new String[]{"-i", str2, "-af", TextUtils.join(",", arrayList), "-y", file.getAbsolutePath()}, new FFmpegSessionCompleteCallback() { // from class: hazem.nurmontage.videoquran.EngineActivity.39
                    @Override // com.arthenica.ffmpegkit.FFmpegSessionCompleteCallback
                    public void apply(FFmpegSession fFmpegSession) {
                        try {
                            EngineActivity.this.mPlayer = new MediaPlayer();
                            EngineActivity.this.mPlayer.setAudioStreamType(3);
                            Uri fromFile = Uri.fromFile(file);
                            if (fromFile.getScheme() != null && fromFile.getScheme().startsWith("http")) {
                                EngineActivity.this.mPlayer.setDataSource(fromFile.toString());
                            } else {
                                EngineActivity.this.mPlayer.setDataSource(EngineActivity.this, fromFile);
                            }
                            EngineActivity.this.mPlayer.prepareAsync();
                            EngineActivity.this.mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: hazem.nurmontage.videoquran.EngineActivity.39.1
                                @Override // android.media.MediaPlayer.OnPreparedListener
                                public void onPrepared(MediaPlayer mediaPlayer) {
                                    if (mediaPlayer == null) {
                                        return;
                                    }
                                    try {
                                        EngineActivity.this.addEntitMediaHttp(entityMedia, effectAudio.getDuration(), uri, mediaPlayer, entityMedia.getPaths_https(), i, str2, str);
                                    } catch (Exception unused) {
                                        EngineActivity.this.hideProgressFragment();
                                    }
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).getSessionId()));
                return;
            }
            MediaPlayer mediaPlayer = new MediaPlayer();
            this.mPlayer = mediaPlayer;
            mediaPlayer.setAudioStreamType(3);
            if (uri.getScheme() != null && uri.getScheme().startsWith("http")) {
                this.mPlayer.setDataSource(uri.toString());
            } else {
                this.mPlayer.setDataSource(this, uri);
            }
            this.mPlayer.prepareAsync();
            this.mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: hazem.nurmontage.videoquran.EngineActivity.40
                @Override // android.media.MediaPlayer.OnPreparedListener
                public void onPrepared(MediaPlayer mediaPlayer2) {
                    if (mediaPlayer2 == null) {
                        return;
                    }
                    try {
                        EngineActivity.this.addEntitMediaHttp(entityMedia, mediaPlayer2.getDuration(), uri, mediaPlayer2, entityMedia.getPaths_https(), i, str2, str);
                    } catch (Exception unused) {
                        EngineActivity.this.hideProgressFragment();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            hideProgressFragment();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<String> buildSpeedFilters(float f) {
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
    public void addAudioFromVideo(final Uri uri, final String str) {
        try {
            MediaPlayer mediaPlayer = new MediaPlayer();
            this.mPlayer = mediaPlayer;
            mediaPlayer.setAudioStreamType(3);
            if (uri.getScheme() != null && uri.getScheme().startsWith("http")) {
                this.mPlayer.setDataSource(uri.toString());
            } else {
                this.mPlayer.setDataSource(this, uri);
            }
            this.mPlayer.prepareAsync();
            this.mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: hazem.nurmontage.videoquran.EngineActivity.41
                @Override // android.media.MediaPlayer.OnPreparedListener
                public void onPrepared(MediaPlayer mediaPlayer2) {
                    if (mediaPlayer2 == null) {
                        return;
                    }
                    EngineActivity.this.changeEntityAudioFromVideo(mediaPlayer2.getDuration(), uri, str);
                    try {
                        EngineActivity.this.runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.41.1
                            @Override // java.lang.Runnable
                            public void run() {
                                EngineActivity.this.updateTimeToEndAya();
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        EngineActivity.this.runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.41.2
                            @Override // java.lang.Runnable
                            public void run() {
                                EngineActivity.this.hideProgressFragment();
                                EngineActivity.this.hideFragment();
                            }
                        });
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            hideFragment();
            hideProgressFragment();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateProgress(final int i, final int i2) {
        runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.42
            @Override // java.lang.Runnable
            public void run() {
                if (ProgressViewFragment.instance != null) {
                    ProgressViewFragment.instance.update(i, i2);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addAudioReciters(final List<RecitersModel> list, final int i) {
        final Uri parse;
        try {
            if (isDestroyed()) {
                return;
            }
            updateProgress(i + 1, list.size());
            if (i >= list.size()) {
                runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.43
                    @Override // java.lang.Runnable
                    public void run() {
                        EngineActivity.this.updateTime();
                        EngineActivity.this.trackViewEntity.translateToEnd();
                        EngineActivity.this.updateBtnToEnd();
                        EngineActivity.this.updateBtnToStart();
                        EngineActivity.this.hideProgressFragment();
                        EngineActivity.this.hideFragment();
                    }
                });
                return;
            }
            RecitersModel recitersModel = list.get(i);
            if (recitersModel.isTarteel()) {
                parse = Uri.parse("https://audio-cdn.tarteel.ai/quran/" + recitersModel.getIdentifer() + "/" + recitersModel.getSurah_index() + recitersModel.getNumber_aya() + ".mp3");
            } else {
                parse = Uri.parse("https://everyayah.com/data/" + recitersModel.getIdentifer() + "/" + recitersModel.getSurah_index() + recitersModel.getNumber_aya() + ".mp3");
            }
            MediaPlayer mediaPlayer = new MediaPlayer();
            this.mPlayer = mediaPlayer;
            mediaPlayer.setAudioStreamType(3);
            if (parse.getScheme() != null && parse.getScheme().startsWith("http")) {
                this.mPlayer.setDataSource(parse.toString());
            } else {
                this.mPlayer.setDataSource(this, parse);
            }
            this.mPlayer.prepareAsync();
            this.mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: hazem.nurmontage.videoquran.EngineActivity.44
                @Override // android.media.MediaPlayer.OnPreparedListener
                public void onPrepared(MediaPlayer mediaPlayer2) {
                    if (mediaPlayer2 == null) {
                        EngineActivity.this.hideProgressFragment();
                    } else {
                        EngineActivity.this.changeEntityAudioReciters(mediaPlayer2.getDuration(), parse, mediaPlayer2, list, i);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            hideProgressFragment();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addEntitMediaHttp(final EntityMedia entityMedia, int i, Uri uri, MediaPlayer mediaPlayer, List<String> list, final int i2, final String str, String str2) {
        final EntityAudio entityAudio;
        float posX;
        float posY;
        final int round = Math.round(this.trackViewEntity.getWidth() * 0.077f);
        final int round2 = Math.round(this.trackViewEntity.getSecond_in_screenNoScale() * (i / 1000.0f));
        if (entityMedia.getStart() != entityMedia.getEnd()) {
            if (this.mTemplate.isNewCode()) {
                posX = entityMedia.getPosX();
                posY = entityMedia.getPosY();
            } else {
                posX = (entityMedia.getPosX() / 1000.0f) * this.trackViewEntity.getSecond_in_screen();
                posY = (entityMedia.getPosY() / 1000.0f) * this.trackViewEntity.getSecond_in_screen();
            }
            EntityAudio entityAudio2 = new EntityAudio(null, uri, posX, 0.0f, round, posY, entityMedia.getMax(), this.trackViewEntity.getSecond_in_screenNoScale(), i, entityMedia.getOffset(), entityMedia.getOffset_right(), entityMedia.getOffset_left());
            entityAudio2.setPathHttp(list);
            entityAudio2.setMediaPlayer(mediaPlayer);
            entityAudio2.setVideo_path(str2);
            entityAudio2.setStart(entityMedia.getStart());
            entityAudio2.setMin_duration(entityMedia.getStart_original());
            if (entityMedia.getEnd() != 0.0f) {
                entityAudio2.setEnd(entityMedia.getEnd());
            }
            entityAudio2.setEffectAudio(entityMedia.getEffectAudio());
            entityAudio2.setFade_in(entityMedia.getDuration_fade_in());
            entityAudio2.setFade_out(entityMedia.getDuration_fade_out());
            this.trackViewEntity.addAudio(entityAudio2);
            entityAudio = entityAudio2;
        } else {
            entityAudio = null;
        }
        if (round2 <= 0 || round <= 0) {
            this.trackViewEntity.invalidate();
            hideProgressFragment();
            return;
        }
        try {
            final File file = new File(this.mTemplate.getFolder_template(), System.currentTimeMillis() + "_output.pcm");
            ArrayList arrayList = new ArrayList();
            arrayList.add("-i");
            arrayList.add(str);
            arrayList.add("-map");
            arrayList.add("0:a");
            arrayList.add("-ac");
            arrayList.add(IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE);
            arrayList.add("-ar");
            arrayList.add("44100");
            arrayList.add("-f");
            arrayList.add("s16le");
            arrayList.add(file.getAbsolutePath());
            arrayList.add("-y");
            this.id_ffmpeg.add(Long.valueOf(FFmpegKit.executeWithArgumentsAsync((String[]) arrayList.toArray(new String[0]), new FFmpegSessionCompleteCallback() { // from class: hazem.nurmontage.videoquran.EngineActivity.45
                @Override // com.arthenica.ffmpegkit.FFmpegSessionCompleteCallback
                public void apply(FFmpegSession fFmpegSession) {
                    if (ReturnCode.isSuccess(fFmpegSession.getReturnCode())) {
                        try {
                            int i3 = round;
                            entityAudio.setAmps(PCMWaveformExtractor.extractWaveform(file.getAbsolutePath(), round2 / (((int) (i3 * 0.1f)) + ((int) (i3 * 0.07f)))), round2, round);
                            entityAudio.setPath_ffmpeg(str);
                            int i4 = i2 + 1;
                            if (i4 >= EngineActivity.this.mTemplate.getEntityMediaList().size()) {
                                try {
                                    EngineActivity.this.runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.45.1
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            EngineActivity.this.updateTime();
                                            EngineActivity.this.trackViewEntity.invalidate();
                                            EngineActivity.this.hideProgressFragment();
                                        }
                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                EntityMedia entityMedia2 = EngineActivity.this.mTemplate.getEntityMediaList().get(i4);
                                if (entityMedia2.getVideo_path() != null) {
                                    EngineActivity engineActivity = EngineActivity.this;
                                    entityMedia.setVideo_path(AudioUtils.copyFromUri(engineActivity, Uri.parse(engineActivity.mTemplate.getUri_upload_extract_audio_video()), EngineActivity.this.mTemplate.getFolder_template()));
                                    if (EngineActivity.this.mTemplate.getExtension() != null) {
                                        EngineActivity engineActivity2 = EngineActivity.this;
                                        engineActivity2.addAudioFromVideoWithExtention(engineActivity2.mTemplate.getExtension(), entityMedia.getVideo_path(), i4);
                                    } else {
                                        EngineActivity.this.start_extenstion = 0;
                                        EngineActivity.this.extractAudioFromVideoRecursive(entityMedia.getVideo_path(), 0, true, i4);
                                    }
                                } else if (entityMedia2.getPaths_https() != null) {
                                    EngineActivity.this.addAudioRecitersTemplate(entityMedia2.getPaths_https(), i4, null);
                                } else {
                                    EngineActivity.this.addAudioTemplateHttp(Uri.parse(entityMedia2.getUri()), i4, null);
                                }
                            }
                        } catch (Exception e2) {
                            e2.printStackTrace();
                            EngineActivity.this.runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.45.2
                                @Override // java.lang.Runnable
                                public void run() {
                                    EngineActivity.this.hideProgressFragment();
                                    EngineActivity.this.hideFragment();
                                }
                            });
                        }
                    }
                }
            }).getSessionId()));
        } catch (Exception e) {
            e.printStackTrace();
            hideProgressFragment();
        }
        this.trackViewEntity.invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeEntityAudio(int i, final Uri uri, List<String> list, final int i2, final String str) {
        EntityAudio audio;
        try {
            float scaleFactor = (this.trackViewEntity.getEntityListAudio().isEmpty() || (audio = this.trackViewEntity.getAudio()) == null) ? 0.0f : audio.getRect().right / this.trackViewEntity.getScaleFactor();
            final int round = Math.round(this.trackViewEntity.getWidth() * 0.077f);
            final int round2 = Math.round(this.trackViewEntity.getSecond_in_screenNoScale() * (i / 1000.0f));
            float f = round2;
            final EntityAudio entityAudio = new EntityAudio(null, uri, scaleFactor, 0.0f, round, f + scaleFactor, f, this.trackViewEntity.getSecond_in_screenNoScale(), i);
            entityAudio.setMediaPlayer(this.mPlayer);
            entityAudio.setPathHttp(list);
            entityAudio.getEffectAudio().setEnd(entityAudio.getEnd());
            entityAudio.getEffectAudio().setStart(entityAudio.getStart());
            entityAudio.getEffectAudio().setDuration((int) (entityAudio.getEnd() - entityAudio.getStart()));
            this.trackViewEntity.addAudio(entityAudio);
            if (round2 > 0 && round > 0) {
                this.executor.execute(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        EngineActivity.this.m639xda6d3951(uri, round, round2, str, entityAudio, i2);
                    }
                });
                this.trackViewEntity.invalidate();
            }
        } catch (Exception e) {
            e.printStackTrace();
            hideProgressFragment();
            hideFragment();
        }
    }

    /* renamed from: lambda$changeEntityAudio$2$hazem-nurmontage-videoquran-EngineActivity, reason: not valid java name */
    /* synthetic */ void m639xda6d3951(Uri uri, int i, int i2, String str, EntityAudio entityAudio, int i3) {
        try {
            String copyFromUri = AudioUtils.copyFromUri(this, uri, this.mTemplate.getFolder_template());
            float f = i;
            entityAudio.setAmps(PCMWaveformExtractor.extractWaveform(str, i2 / (((int) (0.1f * f)) + ((int) (f * 0.07f)))), i2, i);
            entityAudio.setPath_ffmpeg(copyFromUri);
            if (i3 != -1) {
                int i4 = i3 + 1;
                if (i4 >= this.mTemplate.getEntityMediaList().size()) {
                    try {
                        runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.46
                            @Override // java.lang.Runnable
                            public void run() {
                                EngineActivity.this.updateTimeToEndAya();
                                EngineActivity.this.updateBtnToEnd();
                                EngineActivity.this.updateBtnToStart();
                                EngineActivity.this.hideProgressFragment();
                                EngineActivity.this.hideFragment();
                            }
                        });
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                        hideProgressFragment();
                        hideFragment();
                        return;
                    }
                }
                EntityMedia entityMedia = this.mTemplate.getEntityMediaList().get(i3);
                EntityMedia entityMedia2 = this.mTemplate.getEntityMediaList().get(i4);
                if (entityMedia2.getVideo_path() != null) {
                    entityMedia.setVideo_path(AudioUtils.copyFromUri(this, Uri.parse(this.mTemplate.getUri_upload_extract_audio_video()), this.mTemplate.getFolder_template()));
                    if (this.mTemplate.getExtension() != null) {
                        addAudioFromVideoWithExtention(this.mTemplate.getExtension(), entityMedia.getVideo_path(), i4);
                        return;
                    } else {
                        this.start_extenstion = 0;
                        extractAudioFromVideoRecursive(entityMedia.getVideo_path(), 0, true, i4);
                        return;
                    }
                }
                if (entityMedia2.getPaths_https() != null) {
                    addAudioRecitersTemplate(entityMedia2.getPaths_https(), i4, null);
                    return;
                } else {
                    addAudioTemplateHttp(Uri.parse(entityMedia2.getUri()), i4, null);
                    return;
                }
            }
            try {
                runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.47
                    @Override // java.lang.Runnable
                    public void run() {
                        EngineActivity.this.trackViewEntity.calculMaxTime();
                        EngineActivity engineActivity = EngineActivity.this;
                        engineActivity.updateViewTime(engineActivity.trackViewEntity.getMaxTime(), EngineActivity.this.trackViewEntity.getCurrent_cursur_position());
                        EngineActivity.this.trackViewEntity.translateToEnd();
                        EngineActivity.this.updateTimeToEndAya();
                        EngineActivity.this.updateBtnToEnd();
                        EngineActivity.this.updateBtnToStart();
                        EngineActivity.this.trackViewEntity.invalidate();
                        EngineActivity.this.hideProgressFragment();
                        EngineActivity.this.hideFragment();
                    }
                });
                return;
            } catch (Exception e2) {
                e2.printStackTrace();
                hideProgressFragment();
                hideFragment();
                return;
            }
        } catch (Exception e3) {
            e3.printStackTrace();
            hideProgressFragment();
            hideFragment();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addEntitMediaHttp(final EntityMedia entityMedia, int i, Uri uri, MediaPlayer mediaPlayer, List<String> list, final int i2, final String str, final String str2, String str3) {
        EntityAudio entityAudio;
        float posX;
        float posY;
        final int round = Math.round(this.trackViewEntity.getWidth() * 0.077f);
        final int round2 = Math.round(this.trackViewEntity.getSecond_in_screenNoScale() * (i / 1000.0f));
        if (entityMedia.getStart() != entityMedia.getEnd()) {
            if (this.mTemplate.isNewCode()) {
                posX = entityMedia.getPosX();
                posY = entityMedia.getPosY();
            } else {
                posX = (entityMedia.getPosX() / 1000.0f) * this.trackViewEntity.getSecond_in_screen();
                posY = (entityMedia.getPosY() / 1000.0f) * this.trackViewEntity.getSecond_in_screen();
            }
            entityAudio = new EntityAudio(null, uri, posX, 0.0f, round, posY, entityMedia.getMax(), this.trackViewEntity.getSecond_in_screenNoScale(), i, entityMedia.getOffset(), entityMedia.getOffset_right(), entityMedia.getOffset_left());
            entityAudio.setPathHttp(list);
            entityAudio.setMediaPlayer(mediaPlayer);
            entityAudio.setVideo_path(str3);
            entityAudio.setStart(entityMedia.getStart());
            entityAudio.setMin_duration(entityMedia.getStart_original());
            if (entityMedia.getEnd() != 0.0f) {
                entityAudio.setEnd(entityMedia.getEnd());
            }
            entityAudio.setEffectAudio(entityMedia.getEffectAudio());
            entityAudio.setFade_in(entityMedia.getDuration_fade_in());
            entityAudio.setFade_out(entityMedia.getDuration_fade_out());
            this.trackViewEntity.addAudio(entityAudio);
        } else {
            entityAudio = null;
        }
        final EntityAudio entityAudio2 = entityAudio;
        if (round2 <= 0 || round <= 0) {
            this.trackViewEntity.invalidate();
            hideProgressFragment();
        } else {
            this.executor.execute(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.48
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        int i3 = round;
                        entityAudio2.setAmps(PCMWaveformExtractor.extractWaveform(str2, round2 / (((int) (i3 * 0.1f)) + ((int) (i3 * 0.07f)))), round2, round);
                        entityAudio2.setPath_ffmpeg(str);
                        int i4 = i2 + 1;
                        if (i4 >= EngineActivity.this.mTemplate.getEntityMediaList().size()) {
                            try {
                                EngineActivity.this.runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.48.1
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        EngineActivity.this.updateTime();
                                        EngineActivity.this.trackViewEntity.invalidate();
                                        EngineActivity.this.hideProgressFragment();
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            EntityMedia entityMedia2 = EngineActivity.this.mTemplate.getEntityMediaList().get(i4);
                            if (entityMedia2.getVideo_path() != null) {
                                EngineActivity engineActivity = EngineActivity.this;
                                entityMedia.setVideo_path(AudioUtils.copyFromUri(engineActivity, Uri.parse(engineActivity.mTemplate.getUri_upload_extract_audio_video()), EngineActivity.this.mTemplate.getFolder_template()));
                                if (EngineActivity.this.mTemplate.getExtension() != null) {
                                    EngineActivity engineActivity2 = EngineActivity.this;
                                    engineActivity2.addAudioFromVideoWithExtention(engineActivity2.mTemplate.getExtension(), entityMedia.getVideo_path(), i4);
                                } else {
                                    EngineActivity.this.start_extenstion = 0;
                                    EngineActivity.this.extractAudioFromVideoRecursive(entityMedia.getVideo_path(), 0, true, i4);
                                }
                            } else if (entityMedia2.getPaths_https() != null) {
                                EngineActivity.this.addAudioRecitersTemplate(entityMedia2.getPaths_https(), i4, null);
                            } else {
                                EngineActivity.this.addAudioTemplateHttp(Uri.parse(entityMedia2.getUri()), i4, null);
                            }
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        EngineActivity.this.runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.48.2
                            @Override // java.lang.Runnable
                            public void run() {
                                EngineActivity.this.hideProgressFragment();
                                EngineActivity.this.hideFragment();
                            }
                        });
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addAudio(final Uri uri) {
        try {
            MediaPlayer mediaPlayer = new MediaPlayer();
            this.mPlayer = mediaPlayer;
            mediaPlayer.setAudioStreamType(3);
            if (uri.getScheme() != null && uri.getScheme().startsWith("http")) {
                this.mPlayer.setDataSource(uri.toString());
            } else {
                this.mPlayer.setDataSource(this, uri);
            }
            this.mPlayer.prepareAsync();
            this.mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: hazem.nurmontage.videoquran.EngineActivity.49
                @Override // android.media.MediaPlayer.OnPreparedListener
                public void onPrepared(MediaPlayer mediaPlayer2) {
                    if (mediaPlayer2 == null) {
                        return;
                    }
                    EngineActivity.this.changeEntityAudio(mediaPlayer2.getDuration(), uri);
                }
            });
        } catch (Exception e) {
            hideProgressFragment();
            hideFragment();
            e.printStackTrace();
        }
    }

    private void addAudio(final Uri uri, final List<String> list, final int i, final String str) {
        try {
            MediaPlayer mediaPlayer = new MediaPlayer();
            this.mPlayer = mediaPlayer;
            mediaPlayer.setAudioStreamType(3);
            if (uri.getScheme() != null && uri.getScheme().startsWith("http")) {
                this.mPlayer.setDataSource(uri.toString());
            } else {
                this.mPlayer.setDataSource(this, uri);
            }
            this.mPlayer.prepareAsync();
            this.mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: hazem.nurmontage.videoquran.EngineActivity.50
                @Override // android.media.MediaPlayer.OnPreparedListener
                public void onPrepared(MediaPlayer mediaPlayer2) {
                    if (mediaPlayer2 == null) {
                        return;
                    }
                    EngineActivity.this.changeEntityAudio(mediaPlayer2.getDuration(), uri, list, i, str);
                }
            });
        } catch (Exception e) {
            hideProgressFragment();
            hideFragment();
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addAudioTemplate(final Uri uri, final List<String> list, final int i, final String str, final String str2, final String str3) {
        try {
            MediaPlayer mediaPlayer = new MediaPlayer();
            this.mPlayer = mediaPlayer;
            mediaPlayer.setAudioStreamType(3);
            if (uri.getScheme() != null && uri.getScheme().startsWith("http")) {
                this.mPlayer.setDataSource(uri.toString());
            } else {
                this.mPlayer.setDataSource(this, uri);
            }
            this.mPlayer.prepareAsync();
            this.mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: hazem.nurmontage.videoquran.EngineActivity.51
                @Override // android.media.MediaPlayer.OnPreparedListener
                public void onPrepared(MediaPlayer mediaPlayer2) {
                    if (mediaPlayer2 != null && i < EngineActivity.this.mTemplate.getEntityMediaList().size()) {
                        EngineActivity.this.addEntitMediaHttp(EngineActivity.this.mTemplate.getEntityMediaList().get(i), mediaPlayer2.getDuration(), uri, EngineActivity.this.mPlayer, list, i, str, str2, str3);
                    }
                }
            });
        } catch (Exception e) {
            hideProgressFragment();
            hideFragment();
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeEntityAudioFromVideo(int i, final Uri uri, final String str) {
        EntityAudio audio;
        try {
            float scaleFactor = (this.trackViewEntity.getEntityListAudio().isEmpty() || (audio = this.trackViewEntity.getAudio()) == null) ? 0.0f : audio.getRect().right / this.trackViewEntity.getScaleFactor();
            final int round = Math.round(this.trackViewEntity.getWidth() * 0.077f);
            final int round2 = Math.round(this.trackViewEntity.getSecond_in_screenNoScale() * (i / 1000.0f));
            float f = round2;
            final EntityAudio entityAudio = new EntityAudio(null, uri, scaleFactor, 0.0f, round, f + scaleFactor, f, this.trackViewEntity.getSecond_in_screenNoScale(), i);
            entityAudio.setMediaPlayer(this.mPlayer);
            entityAudio.getEffectAudio().setEnd(entityAudio.getEnd());
            entityAudio.getEffectAudio().setStart(entityAudio.getStart());
            entityAudio.getEffectAudio().setDuration((int) (entityAudio.getEnd() - entityAudio.getStart()));
            this.trackViewEntity.addAudio(entityAudio);
            if (round2 > 0 && round > 0) {
                String copyFromUri = AudioUtils.copyFromUri(this, uri, this.mTemplate.getFolder_template());
                final File file = new File(this.mTemplate.getFolder_template(), System.currentTimeMillis() + "_output.pcm");
                ArrayList arrayList = new ArrayList();
                arrayList.add("-i");
                arrayList.add(copyFromUri);
                arrayList.add("-map");
                arrayList.add("0:a");
                arrayList.add("-ac");
                arrayList.add(IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE);
                arrayList.add("-ar");
                arrayList.add("44100");
                arrayList.add("-f");
                arrayList.add("s16le");
                arrayList.add(file.getAbsolutePath());
                arrayList.add("-y");
                this.id_ffmpeg.add(Long.valueOf(FFmpegKit.executeWithArgumentsAsync((String[]) arrayList.toArray(new String[0]), new FFmpegSessionCompleteCallback() { // from class: hazem.nurmontage.videoquran.EngineActivity.52
                    @Override // com.arthenica.ffmpegkit.FFmpegSessionCompleteCallback
                    public void apply(FFmpegSession fFmpegSession) {
                        if (ReturnCode.isSuccess(fFmpegSession.getReturnCode())) {
                            try {
                                int i2 = round;
                                entityAudio.setAmps(PCMWaveformExtractor.extractWaveform(file.getAbsolutePath(), round2 / (((int) (i2 * 0.1f)) + ((int) (i2 * 0.07f)))), round2, round);
                                entityAudio.setPath_ffmpeg(uri.getPath());
                                entityAudio.setVideo_path(str);
                                EngineActivity.this.runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.52.1
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        EngineActivity.this.trackViewEntity.invalidate();
                                        EngineActivity.this.hideProgressFragment();
                                        EngineActivity.this.hideFragment();
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                                EngineActivity.this.runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.52.2
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        EngineActivity.this.hideProgressFragment();
                                        EngineActivity.this.hideFragment();
                                    }
                                });
                            }
                        }
                    }
                }).getSessionId()));
                this.trackViewEntity.invalidate();
            }
        } catch (Exception e) {
            e.printStackTrace();
            runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.53
                @Override // java.lang.Runnable
                public void run() {
                    EngineActivity.this.hideProgressFragment();
                    EngineActivity.this.hideFragment();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeEntityAudioReciters(int i, Uri uri, MediaPlayer mediaPlayer, List<RecitersModel> list, int i2) {
        EntityAudio audio;
        try {
            float scaleFactor = (this.trackViewEntity.getEntityListAudio().isEmpty() || (audio = this.trackViewEntity.getAudio()) == null) ? 0.0f : audio.getRect().right / this.trackViewEntity.getScaleFactor();
            int round = Math.round(this.trackViewEntity.getWidth() * 0.077f);
            int round2 = Math.round(this.trackViewEntity.getSecond_in_screenNoScale() * (i / 1000.0f));
            float f = round2;
            EntityAudio entityAudio = new EntityAudio(null, uri, scaleFactor, 0.0f, round, f + scaleFactor, f, this.trackViewEntity.getSecond_in_screenNoScale(), i);
            entityAudio.getEffectAudio().setEnd(entityAudio.getEnd());
            entityAudio.getEffectAudio().setStart(entityAudio.getStart());
            entityAudio.getEffectAudio().setDuration((int) (entityAudio.getEnd() - entityAudio.getStart()));
            entityAudio.setMediaPlayer(mediaPlayer);
            this.trackViewEntity.addAudio(entityAudio);
            if (round2 > 0 && round > 0) {
                AudioUtils.copyToLocalAsync(this, uri.toString(), this.mTemplate.getFolder_template(), new AnonymousClass54(round2, round, entityAudio, i2, list));
            }
        } catch (Exception e) {
            e.printStackTrace();
            hideProgressFragment();
        }
    }

    /* renamed from: hazem.nurmontage.videoquran.EngineActivity$54, reason: invalid class name */
    class AnonymousClass54 implements AudioUtils.Callback {
        final /* synthetic */ EntityAudio val$entityAudio;
        final /* synthetic */ int val$h;
        final /* synthetic */ int val$index;
        final /* synthetic */ List val$recitersModels;
        final /* synthetic */ int val$w;

        AnonymousClass54(int i, int i2, EntityAudio entityAudio, int i3, List list) {
            this.val$w = i;
            this.val$h = i2;
            this.val$entityAudio = entityAudio;
            this.val$index = i3;
            this.val$recitersModels = list;
        }

        @Override // hazem.nurmontage.videoquran.Utils.AudioUtils.Callback
        public void onSuccess(final String str) {
            try {
                final File file = new File(EngineActivity.this.mTemplate.getFolder_template(), System.currentTimeMillis() + "_audio_wave.png");
                EngineActivity.this.id_ffmpeg.add(Long.valueOf(FFmpegKit.executeWithArgumentsAsync(new String[]{"-i", str, "-filter_complex", "aformat=channel_layouts=mono,showwavespic=s=" + this.val$w + "x" + this.val$h + ":colors=#522123", "-frames:v", IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE, "-y", file.getAbsolutePath()}, new FFmpegSessionCompleteCallback() { // from class: hazem.nurmontage.videoquran.EngineActivity.54.1
                    @Override // com.arthenica.ffmpegkit.FFmpegSessionCompleteCallback
                    public void apply(FFmpegSession fFmpegSession) {
                        if (ReturnCode.isSuccess(fFmpegSession.getReturnCode())) {
                            try {
                                Glide.with((FragmentActivity) EngineActivity.this).asBitmap().load(Uri.fromFile(file)).submit().get();
                                AnonymousClass54.this.val$entityAudio.setPath_ffmpeg(str);
                                EngineActivity.this.runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.54.1.1
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        EngineActivity.this.trackViewEntity.invalidate();
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                                EngineActivity.this.hideProgressFragment();
                            }
                        }
                        EngineActivity.this.addAudioReciters(AnonymousClass54.this.val$recitersModels, AnonymousClass54.this.val$index + 1);
                    }
                }).getSessionId()));
            } catch (Exception e) {
                e.printStackTrace();
                EngineActivity.this.hideProgressFragment();
            }
        }

        @Override // hazem.nurmontage.videoquran.Utils.AudioUtils.Callback
        public void onError(Exception exc) {
            exc.printStackTrace();
            EngineActivity.this.hideProgressFragment();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void duplicateEntityAudio(int i, EntityAudio entityAudio) {
        try {
            float f = entityAudio.getRect().right;
            EntityAudio entityAudio2 = new EntityAudio(null, entityAudio.getUri(), f, entityAudio.getRect().top, entityAudio.getH(), f + entityAudio.getRect().width(), entityAudio.getMax(), entityAudio.getSecond_in_screen(), (int) (i / 1000.0f), 0.0f, 0.0f, 0.0f);
            entityAudio2.setAmps(entityAudio.getAmps());
            entityAudio2.setRenderer(entityAudio.getRenderer());
            entityAudio2.addPathHttp(entityAudio.getPaths_http());
            entityAudio2.setMediaPlayer(entityAudio.getMediaPlayer());
            entityAudio2.getRect().bottom = entityAudio.getRect().bottom;
            entityAudio2.setPath_ffmpeg(entityAudio.getPath_ffmpeg());
            entityAudio2.setEffectAudio(entityAudio.getEffectAudio());
            entityAudio2.setVideo_path(entityAudio.getVideo_path());
            entityAudio2.setApplyEffectInPreview(entityAudio.isApplyEffectInPreview());
            entityAudio2.setmScaleFactor(entityAudio.getmScaleFactor());
            entityAudio2.setIndex(entityAudio.getIndex() + 1);
            entityAudio2.setOffset_right(entityAudio.getOffset_right());
            entityAudio2.setOffset_left(entityAudio.getOffset_left());
            entityAudio2.setOffset(entityAudio.getOffset());
            entityAudio2.setEnd(Math.round((Math.abs(Math.round((entityAudio.getRect().right / this.trackViewEntity.getSecond_in_screen()) * 1000.0f)) - Math.abs(Math.round((entityAudio.getRect().left / this.trackViewEntity.getSecond_in_screen()) * 1000.0f))) + entityAudio.getStart()));
            entityAudio2.setStart(entityAudio.getStart());
            entityAudio2.setMin_duration(entityAudio.getMin_duration());
            this.trackViewEntity.addAudio(entityAudio2, entityAudio.getIndex() + 1);
            this.trackViewEntity.invalidate();
        } catch (Exception e) {
            e.printStackTrace();
            hideProgressFragment();
            hideFragment();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeEntityAudio(int i, Uri uri) {
        String uri2;
        EntityAudio audio;
        try {
            float scaleFactor = (this.trackViewEntity.getEntityListAudio().isEmpty() || (audio = this.trackViewEntity.getAudio()) == null) ? 0.0f : audio.getRect().right / this.trackViewEntity.getScaleFactor();
            final int round = Math.round(this.trackViewEntity.getWidth() * 0.077f);
            final int round2 = Math.round(this.trackViewEntity.getSecond_in_screenNoScale() * (i / 1000.0f));
            float f = round2;
            final EntityAudio entityAudio = new EntityAudio(null, uri, scaleFactor, 0.0f, round, f + scaleFactor, f, this.trackViewEntity.getSecond_in_screenNoScale(), i);
            entityAudio.setMediaPlayer(this.mPlayer);
            entityAudio.getEffectAudio().setEnd(entityAudio.getEnd());
            entityAudio.getEffectAudio().setStart(entityAudio.getStart());
            entityAudio.getEffectAudio().setDuration((int) (entityAudio.getEnd() - entityAudio.getStart()));
            this.trackViewEntity.addAudio(entityAudio);
            if (round2 > 0 && round > 0) {
                if (!uri.toString().contains("share_with_me")) {
                    uri2 = AudioUtils.copyFromUri(this, uri, this.mTemplate.getFolder_template());
                } else {
                    uri2 = uri.toString();
                }
                final File file = new File(this.mTemplate.getFolder_template(), System.currentTimeMillis() + "_output.pcm");
                ArrayList arrayList = new ArrayList();
                arrayList.add("-i");
                arrayList.add(uri2);
                arrayList.add("-map");
                arrayList.add("0:a");
                arrayList.add("-ac");
                arrayList.add(IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE);
                arrayList.add("-ar");
                arrayList.add("44100");
                arrayList.add("-f");
                arrayList.add("s16le");
                arrayList.add(file.getAbsolutePath());
                arrayList.add("-y");
                final String str = uri2;
                this.id_ffmpeg.add(Long.valueOf(FFmpegKit.executeWithArgumentsAsync((String[]) arrayList.toArray(new String[0]), new FFmpegSessionCompleteCallback() { // from class: hazem.nurmontage.videoquran.EngineActivity.55
                    @Override // com.arthenica.ffmpegkit.FFmpegSessionCompleteCallback
                    public void apply(FFmpegSession fFmpegSession) {
                        if (ReturnCode.isSuccess(fFmpegSession.getReturnCode())) {
                            try {
                                int i2 = round;
                                entityAudio.setAmps(PCMWaveformExtractor.extractWaveform(file.getAbsolutePath(), round2 / (((int) (i2 * 0.1f)) + ((int) (i2 * 0.07f)))), round2, round);
                                entityAudio.setPath_ffmpeg(str);
                                EngineActivity.this.runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.55.1
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        EngineActivity.this.updateTimeToEndAya();
                                        EngineActivity.this.updateBtnToEnd();
                                        EngineActivity.this.updateBtnToStart();
                                        EngineActivity.this.hideProgressFragment();
                                        EngineActivity.this.hideFragment();
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                                EngineActivity.this.runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.55.2
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        EngineActivity.this.hideProgressFragment();
                                        EngineActivity.this.hideFragment();
                                    }
                                });
                            }
                        }
                    }
                }).getSessionId()));
                this.trackViewEntity.invalidate();
            }
        } catch (Exception e) {
            e.printStackTrace();
            hideProgressFragment();
            hideFragment();
        }
    }

    private String createCmd(EffectAudio effectAudio, float f, float f2) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(String.format(Locale.US, "atrim=start=%.2f:end=%.2f", Float.valueOf(f), Float.valueOf(f2)));
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
            arrayList.add("afade=t=out:st=" + ((f2 - f) - fade_out) + ":d=" + fade_out);
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
        return TextUtils.join(",", arrayList);
    }

    public void applyffectAll(final EffectAudio effectAudio, int i) {
        if (i >= this.trackViewEntity.getEntityListAudio().size()) {
            runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.56
                @Override // java.lang.Runnable
                public void run() {
                    EngineActivity.this.trackViewEntity.invalidate();
                    EngineActivity.this.hideProgressFragment();
                    if (EngineActivity.this.iEditMediaCallback != null) {
                        EngineActivity.this.iEditMediaCallback.onDone();
                    }
                }
            });
            return;
        }
        android.util.Pair<Integer, EntityAudio> entityAudioNotDeleted = this.trackViewEntity.getEntityAudioNotDeleted(i);
        if (entityAudioNotDeleted == null) {
            runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.57
                @Override // java.lang.Runnable
                public void run() {
                    EngineActivity.this.trackViewEntity.invalidate();
                    EngineActivity.this.hideProgressFragment();
                    if (EngineActivity.this.iEditMediaCallback != null) {
                        EngineActivity.this.iEditMediaCallback.onDone();
                    }
                }
            });
            return;
        }
        final EntityAudio entityAudio = (EntityAudio) entityAudioNotDeleted.second;
        final int intValue = ((Integer) entityAudioNotDeleted.first).intValue();
        String createCmd = createCmd(effectAudio, entityAudio.getEffectAudio().getStart() / 1000.0f, entityAudio.getEffectAudio().getEnd() / 1000.0f);
        final File file = new File(this.mTemplate.getFolder_template(), System.currentTimeMillis() + "_audio_echo.mp3");
        final Uri fromFile = Uri.fromFile(file);
        this.id_ffmpeg.add(Long.valueOf(FFmpegKit.executeWithArgumentsAsync(new String[]{"-i", entityAudio.getPath_ffmpeg(), "-af", createCmd, "-y", file.getAbsolutePath()}, new FFmpegSessionCompleteCallback() { // from class: hazem.nurmontage.videoquran.EngineActivity.58
            @Override // com.arthenica.ffmpegkit.FFmpegSessionCompleteCallback
            public void apply(FFmpegSession fFmpegSession) {
                if (ReturnCode.isSuccess(fFmpegSession.getReturnCode())) {
                    try {
                        EngineActivity.this.mPlayer = new MediaPlayer();
                        EngineActivity.this.mPlayer.setAudioStreamType(3);
                        if (fromFile.getScheme() != null && fromFile.getScheme().startsWith("http")) {
                            EngineActivity.this.mPlayer.setDataSource(fromFile.toString());
                        } else {
                            EngineActivity.this.mPlayer.setDataSource(EngineActivity.this, fromFile);
                        }
                        EngineActivity.this.mPlayer.prepareAsync();
                        EngineActivity.this.mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: hazem.nurmontage.videoquran.EngineActivity.58.1
                            @Override // android.media.MediaPlayer.OnPreparedListener
                            public void onPrepared(MediaPlayer mediaPlayer) {
                                if (entityAudio.getMediaPlayer() != null && mediaPlayer.getDuration() != entityAudio.getMediaPlayer().getDuration()) {
                                    entityAudio.setRight(entityAudio.getRect().left + Math.round(EngineActivity.this.trackViewEntity.getSecond_in_screen() * (mediaPlayer.getDuration() / 1000.0f)));
                                    entityAudio.setDuration(mediaPlayer.getDuration() * 1000);
                                    entityAudio.setEnd(mediaPlayer.getDuration());
                                    entityAudio.setStart(0.0f);
                                    entityAudio.setMax((entityAudio.getRect().right / entityAudio.getmScaleFactor()) - ((entityAudio.getRect().left / entityAudio.getmScaleFactor()) - entityAudio.getOffset_left()));
                                    EngineActivity.this.trackViewEntity.updateWhenEffect(entityAudio);
                                }
                                entityAudio.setMediaPlayer(EngineActivity.this.mPlayer);
                                EngineActivity.this.applyffectAll(effectAudio, intValue + 1);
                            }
                        });
                        entityAudio.setPath_ffmpeg_effect(file.getAbsolutePath());
                        entityAudio.setApplyEffectInPreview(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                        EngineActivity.this.runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.58.2
                            @Override // java.lang.Runnable
                            public void run() {
                                EngineActivity.this.hideProgressFragment();
                                if (EngineActivity.this.iEditMediaCallback != null) {
                                    EngineActivity.this.iEditMediaCallback.onDone();
                                }
                            }
                        });
                    }
                }
            }
        }).getSessionId()));
    }

    public void applyffect(String str, EntityAudio entityAudio) {
        showProgressSimple();
        File file = new File(this.mTemplate.getFolder_template(), System.currentTimeMillis() + "_audio_echo.mp3");
        this.id_ffmpeg.add(Long.valueOf(FFmpegKit.executeWithArgumentsAsync(new String[]{"-i", entityAudio.getPath_ffmpeg(), "-af", str, "-y", file.getAbsolutePath()}, new AnonymousClass59(Uri.fromFile(file), entityAudio, file)).getSessionId()));
    }

    /* renamed from: hazem.nurmontage.videoquran.EngineActivity$59, reason: invalid class name */
    class AnonymousClass59 implements FFmpegSessionCompleteCallback {
        final /* synthetic */ EntityAudio val$entityAudio;
        final /* synthetic */ File val$file;
        final /* synthetic */ Uri val$uri;

        AnonymousClass59(Uri uri, EntityAudio entityAudio, File file) {
            this.val$uri = uri;
            this.val$entityAudio = entityAudio;
            this.val$file = file;
        }

        @Override // com.arthenica.ffmpegkit.FFmpegSessionCompleteCallback
        public void apply(FFmpegSession fFmpegSession) {
            if (ReturnCode.isSuccess(fFmpegSession.getReturnCode())) {
                try {
                    EngineActivity.this.mPlayer = new MediaPlayer();
                    EngineActivity.this.mPlayer.setAudioStreamType(3);
                    if (this.val$uri.getScheme() != null && this.val$uri.getScheme().startsWith("http")) {
                        EngineActivity.this.mPlayer.setDataSource(this.val$uri.toString());
                    } else {
                        EngineActivity.this.mPlayer.setDataSource(EngineActivity.this, this.val$uri);
                    }
                    EngineActivity.this.mPlayer.prepareAsync();
                    EngineActivity.this.mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: hazem.nurmontage.videoquran.EngineActivity.59.1
                        @Override // android.media.MediaPlayer.OnPreparedListener
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            if (AnonymousClass59.this.val$entityAudio.getMediaPlayer() != null && mediaPlayer.getDuration() != AnonymousClass59.this.val$entityAudio.getMediaPlayer().getDuration()) {
                                AnonymousClass59.this.val$entityAudio.setRight(AnonymousClass59.this.val$entityAudio.getRect().left + Math.round(EngineActivity.this.trackViewEntity.getSecond_in_screen() * (mediaPlayer.getDuration() / 1000.0f)));
                                AnonymousClass59.this.val$entityAudio.setDuration(mediaPlayer.getDuration() * 1000);
                                AnonymousClass59.this.val$entityAudio.setMax((AnonymousClass59.this.val$entityAudio.getRect().right / AnonymousClass59.this.val$entityAudio.getmScaleFactor()) - ((AnonymousClass59.this.val$entityAudio.getRect().left / AnonymousClass59.this.val$entityAudio.getmScaleFactor()) - AnonymousClass59.this.val$entityAudio.getOffset_left()));
                                EngineActivity.this.trackViewEntity.updateWhenEffect(AnonymousClass59.this.val$entityAudio);
                                EngineActivity.this.runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.59.1.1
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        EngineActivity.this.trackViewEntity.invalidate();
                                        EngineActivity.this.hideProgressFragment();
                                    }
                                });
                            } else {
                                EngineActivity.this.runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.59.1.2
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        EngineActivity.this.hideProgressFragment();
                                    }
                                });
                            }
                            AnonymousClass59.this.val$entityAudio.setMediaPlayer(mediaPlayer);
                        }
                    });
                    this.val$entityAudio.setPath_ffmpeg_effect(this.val$file.getAbsolutePath());
                    this.val$entityAudio.setApplyEffectInPreview(true);
                } catch (Exception e) {
                    e.printStackTrace();
                    EngineActivity.this.runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.59.2
                        @Override // java.lang.Runnable
                        public void run() {
                            EngineActivity.this.hideProgressFragment();
                        }
                    });
                }
            }
        }
    }

    public EntityBismilahTimeline addTimeLineBismilah(BismilahEntity bismilahEntity, float f, float f2) {
        EntityBismilahTimeline entityBismilahTimeline = new EntityBismilahTimeline(bismilahEntity, f, 0.0f, this.trackViewEntity.getWidth() * 0.077f, f2, this.trackViewEntity.getSecond_in_screen());
        this.trackViewEntity.setBismilahTimeline(entityBismilahTimeline);
        return entityBismilahTimeline;
    }

    public EntityBismilahTimeline addTimeLineIsti3ada(BismilahEntity bismilahEntity, float f, float f2) {
        EntityBismilahTimeline entityBismilahTimeline = new EntityBismilahTimeline(bismilahEntity, f, 0.0f, this.trackViewEntity.getWidth() * 0.077f, f2, this.trackViewEntity.getSecond_in_screen());
        this.trackViewEntity.setmIsi3adaTimeline(entityBismilahTimeline);
        return entityBismilahTimeline;
    }

    public EntityQuranTimeline addTimeLineQuran(QuranEntity quranEntity, float f, float f2) {
        EntityQuranTimeline entityQuranTimeline = new EntityQuranTimeline(quranEntity, f, 0.0f, this.trackViewEntity.getWidth() * 0.077f, f2, this.trackViewEntity.getSecond_in_screen());
        this.trackViewEntity.addQuran(entityQuranTimeline);
        return entityQuranTimeline;
    }

    public EntityTrslTimeline addTimeLineQuran(TranslationQuranEntity translationQuranEntity, float f, float f2) {
        EntityTrslTimeline entityTrslTimeline = new EntityTrslTimeline(translationQuranEntity, f, 0.0f, this.trackViewEntity.getWidth() * 0.077f, f2, this.trackViewEntity.getSecond_in_screen());
        this.trackViewEntity.addTrslQuran(entityTrslTimeline);
        return entityTrslTimeline;
    }

    public EntityQuranTimeline addTimeLineQuran(int i, QuranEntity quranEntity, float f, float f2) {
        EntityQuranTimeline entityQuranTimeline = new EntityQuranTimeline(quranEntity, f, 0.0f, this.trackViewEntity.getWidth() * 0.077f, f2, this.trackViewEntity.getSecond_in_screen());
        this.trackViewEntity.addQuran(entityQuranTimeline, i);
        return entityQuranTimeline;
    }

    public EntityTrslTimeline addTimeLineQuran(int i, TranslationQuranEntity translationQuranEntity, float f, float f2) {
        EntityTrslTimeline entityTrslTimeline = new EntityTrslTimeline(translationQuranEntity, f, 0.0f, this.trackViewEntity.getWidth() * 0.077f, f2, this.trackViewEntity.getSecond_in_screen());
        this.trackViewEntity.addTrslQuran(entityTrslTimeline, i);
        return entityTrslTimeline;
    }

    public EntityQuranTimeline splitTimeLineQuran(int i, QuranEntity quranEntity, float f, float f2, float f3) {
        EntityQuranTimeline entityQuranTimeline = new EntityQuranTimeline(quranEntity, f, 0.0f, this.trackViewEntity.getWidth() * 0.077f, f2, this.trackViewEntity.getSecond_in_screen());
        entityQuranTimeline.setmScaleFactor(f3);
        this.trackViewEntity.addQuran_split(entityQuranTimeline, i);
        return entityQuranTimeline;
    }

    public EntityTrslTimeline splitTimeLineQuran(int i, TranslationQuranEntity translationQuranEntity, float f, float f2, float f3) {
        EntityTrslTimeline entityTrslTimeline = new EntityTrslTimeline(translationQuranEntity, f, 0.0f, this.trackViewEntity.getWidth() * 0.077f, f2, this.trackViewEntity.getSecond_in_screen());
        entityTrslTimeline.setmScaleFactor(f3);
        this.trackViewEntity.addQuran_split(entityTrslTimeline, i);
        return entityTrslTimeline;
    }

    public EntityQuranTimeline addTimeLineQuran(QuranEntity quranEntity) {
        float xCursur = this.trackViewEntity.getXCursur();
        EntityQuranTimeline quran = this.trackViewEntity.getQuran();
        if (quran != null) {
            xCursur = quran.getRect().right;
        }
        TrackEntityView trackEntityView = this.trackViewEntity;
        if (trackEntityView.isExist(trackEntityView.getBismilahTimeline())) {
            xCursur = Math.max(xCursur, this.trackViewEntity.getBismilahTimeline().getRect().right);
        }
        float f = xCursur;
        EntityQuranTimeline entityQuranTimeline = new EntityQuranTimeline(quranEntity, f, 0.0f, this.trackViewEntity.getWidth() * 0.077f, f + (this.trackViewEntity.getSecond_in_screen() * 4.0f), this.trackViewEntity.getSecond_in_screen());
        this.trackViewEntity.addQuran(entityQuranTimeline);
        return entityQuranTimeline;
    }

    public EntityTrslTimeline addTimeLineTrslQuran(TranslationQuranEntity translationQuranEntity) {
        float xCursur = this.trackViewEntity.getXCursur();
        EntityTrslTimeline trslQuran = this.trackViewEntity.getTrslQuran();
        if (trslQuran != null) {
            xCursur = trslQuran.getRect().right;
        }
        EntityTrslTimeline entityTrslTimeline = new EntityTrslTimeline(translationQuranEntity, xCursur, 0.0f, this.trackViewEntity.getWidth() * 0.077f, this.trackViewEntity.getQuran().getRect().right, this.trackViewEntity.getSecond_in_screen());
        this.trackViewEntity.addTrslQuran(entityTrslTimeline);
        return entityTrslTimeline;
    }

    public EntityBismilahTimeline addTimeLineBismilah(BismilahEntity bismilahEntity) {
        float f = this.trackViewEntity.getmIsi3adaTimeline() != null ? this.trackViewEntity.getmIsi3adaTimeline().getRect().right : 0.0f;
        EntityBismilahTimeline entityBismilahTimeline = new EntityBismilahTimeline(bismilahEntity, f, 0.0f, this.trackViewEntity.getWidth() * 0.077f, f + (this.trackViewEntity.getSecond_in_screen() * 4.0f), this.trackViewEntity.getSecond_in_screen());
        this.trackViewEntity.setBismilahTimeline(entityBismilahTimeline);
        return entityBismilahTimeline;
    }

    public EntityBismilahTimeline addTimeLineIsti3ada(BismilahEntity bismilahEntity) {
        EntityBismilahTimeline entityBismilahTimeline = new EntityBismilahTimeline(bismilahEntity, 0.0f, 0.0f, this.trackViewEntity.getWidth() * 0.077f, (this.trackViewEntity.getSecond_in_screen() * 4.0f) + 0.0f, this.trackViewEntity.getSecond_in_screen());
        this.trackViewEntity.setmIsi3adaTimeline(entityBismilahTimeline);
        return entityBismilahTimeline;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enableUndoBtn() {
        try {
            ImageButton imageButton = this.btnUndo;
            if (imageButton == null || imageButton.isEnabled()) {
                return;
            }
            runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.61
                @Override // java.lang.Runnable
                public void run() {
                    EngineActivity.this.btnUndo.setColorFilter(-1, PorterDuff.Mode.SRC_IN);
                    EngineActivity.this.btnUndo.setEnabled(true);
                    EngineActivity.this.btnUndo.setClickable(true);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enableRedoBtn() {
        try {
            ImageButton imageButton = this.btnRedo;
            if (imageButton == null || imageButton.isEnabled()) {
                return;
            }
            runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.62
                @Override // java.lang.Runnable
                public void run() {
                    EngineActivity.this.btnRedo.setColorFilter(-1, PorterDuff.Mode.SRC_IN);
                    EngineActivity.this.btnRedo.setEnabled(true);
                    EngineActivity.this.btnRedo.setClickable(true);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void disableRedoBtn() {
        try {
            ImageButton imageButton = this.btnRedo;
            if (imageButton == null || !imageButton.isEnabled()) {
                return;
            }
            runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.63
                @Override // java.lang.Runnable
                public void run() {
                    EngineActivity.this.btnRedo.setColorFilter(-8355712, PorterDuff.Mode.SRC_IN);
                    EngineActivity.this.btnRedo.setEnabled(false);
                    EngineActivity.this.btnRedo.setClickable(false);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void disableUndoBtn() {
        try {
            ImageButton imageButton = this.btnUndo;
            if (imageButton == null || !imageButton.isEnabled()) {
                return;
            }
            runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.64
                @Override // java.lang.Runnable
                public void run() {
                    EngineActivity.this.btnUndo.setColorFilter(-8355712, PorterDuff.Mode.SRC_IN);
                    EngineActivity.this.btnUndo.setEnabled(false);
                    EngineActivity.this.btnUndo.setClickable(false);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateBtnCutState() {
        try {
            checkSplitEntity();
            checkSplitTrslEntity();
            checkSplitAudio();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addEntity(String str, String str2, String str3, String str4, int i, int i2, String str5, int i3, int i4) {
        String nameFont;
        boolean z = this.mTemplate.getIpad_type() == IpadType.GRADIENT.ordinal() || this.mTemplate.getIpad_type() == IpadType.MASK_BRUSH.ordinal() || this.mTemplate.getIpad_type() == IpadType.BLACK_LAYER.ordinal();
        if (this.blurredImageView.getQuranEntities().isEmpty()) {
            nameFont = Common.FONT_QURAN;
        } else {
            nameFont = this.blurredImageView.getQuranEntities().get(0).getNameFont();
        }
        String str6 = nameFont;
        QuranEntity quranEntity = new QuranEntity(this, DrawableHelper.getIDDrawableIconByName(str5), str, str2, str3, str4, this.blurredImageView.getRectFAya(), UtilsFileLast.loadFontFromAsset(this, "fonts/arabic/" + str6), Typeface.createFromAsset(getResources().getAssets(), "fonts/ReadexPro_Medium.ttf"), i, i2, UtilsFileLast.loadFontFromAsset(this, "fonts/arabic/خط فارس الكوفي.otf"), this.blurredImageView.getClr_aya(), this.blurredImageView.getClr_trsl(), str6, z);
        quranEntity.setIpad_type(this.mTemplate.getIpad_type());
        quranEntity.setCanvasWH(this.blurredImageView.getmCanvas_width(), this.blurredImageView.getmCanvas_height());
        quranEntity.setStartWord_index(i3);
        quranEntity.setEndWord_index(i4);
        quranEntity.setIcon(str5);
        quranEntity.setViewWeakReference(new WeakReference<>(this.trackViewEntity), new WeakReference<>(this.blurredImageView));
        EntityQuranTimeline addTimeLineQuran = addTimeLineQuran(quranEntity);
        addTimeLineQuran.setmScaleFactor(this.trackViewEntity.getScaleFactor());
        quranEntity.setEntityQuran(addTimeLineQuran);
        addTimeLineQuran.setEntityView(quranEntity);
        this.blurredImageView.addEntity(quranEntity);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addTranslationEntity(String str, int i, boolean z) {
        TranslationQuranEntity translationQuranEntity = new TranslationQuranEntity(str, this.blurredImageView.getRectFAya(), Typeface.createFromAsset(getResources().getAssets(), "fonts/ReadexPro_Medium.ttf"), i, InputDeviceCompat.SOURCE_ANY, "ReadexPro_Medium.ttf", this.blurredImageView.getmCanvas_width(), this.blurredImageView.getmCanvas_height());
        translationQuranEntity.setIpad_type(this.mTemplate.getIpad_type());
        translationQuranEntity.setCanvasWH(this.blurredImageView.getmCanvas_width(), this.blurredImageView.getmCanvas_height());
        translationQuranEntity.setViewWeakReference(new WeakReference<>(this.trackViewEntity), new WeakReference<>(this.blurredImageView));
        EntityTrslTimeline addTimeLineTrslQuran = addTimeLineTrslQuran(translationQuranEntity);
        addTimeLineTrslQuran.setmScaleFactor(this.trackViewEntity.getScaleFactor());
        translationQuranEntity.setEntityTrslTimeline(addTimeLineTrslQuran);
        addTimeLineTrslQuran.setEntityView(translationQuranEntity);
        this.blurredImageView.addEntity(translationQuranEntity);
    }

    private void addEntityBissmilah(String str, float f, float f2, int i, Transition transition, float f3, float f4, RectF rectF, int i2) {
        RectF rectF2;
        Typeface loadFontFromAsset = UtilsFileLast.loadFontFromAsset(this, "fonts/خط البسملة.ttf");
        if (rectF == null) {
            rectF2 = this.blurredImageView.getRectFAya();
        } else {
            rectF2 = new RectF(rectF.left * this.blurredImageView.getmCanvas_width(), rectF.top * this.blurredImageView.getmCanvas_height(), rectF.right * this.blurredImageView.getmCanvas_width(), rectF.bottom * this.blurredImageView.getmCanvas_height());
        }
        BismilahEntity bismilahEntity = new BismilahEntity(str, rectF2, loadFontFromAsset, i, i2);
        bismilahEntity.setFcSize(f4);
        bismilahEntity.setFactor_scale(f3);
        bismilahEntity.setCanvasWH(this.blurredImageView.getmCanvas_width(), this.blurredImageView.getmCanvas_height());
        if (bismilahEntity.getFactorSize() == 1.0f) {
            bismilahEntity.createStaticLayout();
        } else {
            bismilahEntity.setupScaleSave(bismilahEntity.getFactorSize(), this.blurredImageView.getmCanvas_width());
        }
        bismilahEntity.initPreset(i2);
        bismilahEntity.setViewWeakReference(new WeakReference<>(this.trackViewEntity), new WeakReference<>(this.blurredImageView));
        EntityBismilahTimeline addTimeLineBismilah = addTimeLineBismilah(bismilahEntity, f, f2);
        bismilahEntity.setBismilahTimeline(addTimeLineBismilah);
        addTimeLineBismilah.setTransition(transition);
        addTimeLineBismilah.setEntityView(bismilahEntity);
        this.blurredImageView.addBismilahEntity(bismilahEntity);
    }

    private void addEntityIsti3ada(String str, float f, float f2, int i, Transition transition, float f3, float f4, RectF rectF, int i2) {
        RectF rectF2;
        Typeface loadFontFromAsset = UtilsFileLast.loadFontFromAsset(this, "fonts/خط الاستعاذه.ttf");
        if (rectF == null) {
            rectF2 = this.blurredImageView.getRectFAya();
        } else {
            rectF2 = new RectF(rectF.left * this.blurredImageView.getmCanvas_width(), rectF.top * this.blurredImageView.getmCanvas_height(), rectF.right * this.blurredImageView.getmCanvas_width(), rectF.bottom * this.blurredImageView.getmCanvas_height());
        }
        BismilahEntity bismilahEntity = new BismilahEntity(str, rectF2, loadFontFromAsset, i, i2);
        bismilahEntity.setFcSize(f4);
        bismilahEntity.setFactor_scale(f3);
        bismilahEntity.setCanvasWH(this.blurredImageView.getmCanvas_width(), this.blurredImageView.getmCanvas_height());
        if (bismilahEntity.getFactorSize() == 1.0f) {
            bismilahEntity.createStaticLayout();
        } else {
            bismilahEntity.setupScaleSave(bismilahEntity.getFactorSize(), this.blurredImageView.getmCanvas_width());
        }
        bismilahEntity.initPreset(i2);
        bismilahEntity.setViewWeakReference(new WeakReference<>(this.trackViewEntity), new WeakReference<>(this.blurredImageView));
        EntityBismilahTimeline addTimeLineIsti3ada = addTimeLineIsti3ada(bismilahEntity, f, f2);
        bismilahEntity.setBismilahTimeline(addTimeLineIsti3ada);
        addTimeLineIsti3ada.setTransition(transition);
        addTimeLineIsti3ada.setEntityView(bismilahEntity);
        this.blurredImageView.addIsti3adhaEntity(bismilahEntity);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean addEntityBissmilah() {
        if (this.blurredImageView.getBismilahEntity() != null) {
            if (this.blurredImageView.getBismilahEntity().getBismilahTimeline().visible()) {
                return false;
            }
            this.blurredImageView.getBismilahEntity().getBismilahTimeline().visible(true);
            return false;
        }
        BismilahEntity bismilahEntity = new BismilahEntity(IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE, this.blurredImageView.getRectFAya(), UtilsFileLast.loadFontFromAsset(this, "fonts/خط البسملة.ttf"), this.blurredImageView.getClr_aya());
        bismilahEntity.setCanvasWH(this.blurredImageView.getmCanvas_width(), this.blurredImageView.getmCanvas_height());
        bismilahEntity.setFcSize(bismilahEntity.getPaintAya().getTextSize() / this.blurredImageView.getmCanvas_width());
        bismilahEntity.setViewWeakReference(new WeakReference<>(this.trackViewEntity), new WeakReference<>(this.blurredImageView));
        EntityBismilahTimeline addTimeLineBismilah = addTimeLineBismilah(bismilahEntity);
        addTimeLineBismilah.setmScaleFactor(this.trackViewEntity.getScaleFactor());
        bismilahEntity.setBismilahTimeline(addTimeLineBismilah);
        addTimeLineBismilah.setEntityView(bismilahEntity);
        this.blurredImageView.addBismilahEntity(bismilahEntity);
        if (this.trackViewEntity.getQuran() != null) {
            this.trackViewEntity.translateToRightBismilah(addTimeLineBismilah);
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean addEntityIste3adha() {
        if (this.blurredImageView.getmIsti3adhaEntity() != null) {
            if (this.blurredImageView.getmIsti3adhaEntity().getBismilahTimeline().visible()) {
                return false;
            }
            this.blurredImageView.getmIsti3adhaEntity().getBismilahTimeline().visible(true);
            return false;
        }
        BismilahEntity bismilahEntity = new BismilahEntity("4", this.blurredImageView.getRectFAya(), UtilsFileLast.loadFontFromAsset(this, "fonts/خط الاستعاذه.ttf"), this.blurredImageView.getClr_aya());
        bismilahEntity.setCanvasWH(this.blurredImageView.getmCanvas_width(), this.blurredImageView.getmCanvas_height());
        bismilahEntity.setFcSize(bismilahEntity.getPaintAya().getTextSize() / this.blurredImageView.getmCanvas_width());
        bismilahEntity.setViewWeakReference(new WeakReference<>(this.trackViewEntity), new WeakReference<>(this.blurredImageView));
        EntityBismilahTimeline addTimeLineIsti3ada = addTimeLineIsti3ada(bismilahEntity);
        addTimeLineIsti3ada.setmScaleFactor(this.trackViewEntity.getScaleFactor());
        bismilahEntity.setBismilahTimeline(addTimeLineIsti3ada);
        addTimeLineIsti3ada.setEntityView(bismilahEntity);
        this.blurredImageView.addIsti3adhaEntity(bismilahEntity);
        if (this.trackViewEntity.getQuran() != null) {
            this.trackViewEntity.translateToRightBismilah(addTimeLineIsti3ada);
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void duplicateEntity(QuranEntity quranEntity) {
        Typeface typefaceNumber = quranEntity.getTypefaceNumber();
        if (typefaceNumber == null) {
            typefaceNumber = UtilsFileLast.loadFontFromAsset(this, "fonts/arabic/خط فارس الكوفي.otf");
        }
        Typeface typeface = typefaceNumber;
        Typeface typeface2 = quranEntity.getPaintAya().getTypeface();
        if (typeface2 == null) {
            typeface2 = UtilsFileLast.loadFontFromAsset(this, "fonts/arabic/" + quranEntity.getNameFont());
        }
        Typeface typeface3 = typeface2;
        Typeface typeface4 = quranEntity.getPaintTranslationAya() != null ? quranEntity.getPaintTranslationAya().getTypeface() : null;
        if (typeface4 == null) {
            typeface4 = Typeface.createFromAsset(getResources().getAssets(), "fonts/ReadexPro_Medium.ttf");
        }
        QuranEntity quranEntity2 = new QuranEntity(quranEntity.getTxt(), quranEntity.getComplete_aya(), quranEntity.getTranslation(), quranEntity.getTranslation_complete(), this.blurredImageView.getRectFAya(), typeface3, typeface4, quranEntity.getIndexNumber(), quranEntity.getNumber(), typeface, quranEntity.getClrAya(), quranEntity.getClrTrsl(), quranEntity.getNameFont(), quranEntity.getPaintAya().getTextSize(), quranEntity.getPaintTranslationAya().getTextSize(), quranEntity.getPaintAya().isUnderlineText(), quranEntity.getVectorDrawable());
        quranEntity2.setFcSize(quranEntity.getFactorSize());
        quranEntity2.setFactorSizeTrl(quranEntity.getFactorSizeTrl());
        quranEntity2.setFactor_scale(quranEntity.getFactor_scale());
        quranEntity2.setCanvasWH(this.blurredImageView.getmCanvas_width(), this.blurredImageView.getmCanvas_height());
        quranEntity2.setIpad_type(this.mTemplate.getIpad_type());
        quranEntity2.setStartWord_index(quranEntity.getStartWord_index());
        quranEntity2.setEndWord_index(quranEntity.getEndWord_index());
        quranEntity2.setIcon(quranEntity.getIcon());
        quranEntity2.setViewWeakReference(new WeakReference<>(this.trackViewEntity), new WeakReference<>(this.blurredImageView));
        quranEntity2.setVisible(false);
        quranEntity2.setupScaleSave(quranEntity2.getFactorSize(), this.blurredImageView.getmCanvas_width());
        quranEntity2.setColor(quranEntity.getClrAya());
        quranEntity2.setColorTranslation(quranEntity.getPaintTranslationAya() != null ? quranEntity.getClrTrsl() : InputDeviceCompat.SOURCE_ANY);
        quranEntity2.initPreset(quranEntity.getmPreset());
        EntityQuranTimeline addTimeLineQuran = addTimeLineQuran(quranEntity.getEntityQuran().getIndex() + 1, quranEntity2, quranEntity.getEntityQuran().getRect().right, quranEntity.getEntityQuran().getRect().right + quranEntity.getEntityQuran().getRect().width());
        addTimeLineQuran.setmScaleFactor(quranEntity.getEntityQuran().getmScaleFactor());
        quranEntity2.setEntityQuran(addTimeLineQuran);
        addTimeLineQuran.setEntityView(quranEntity2);
        if (quranEntity.getEntityQuran().getTransition() != null) {
            addTimeLineQuran.setTransition(quranEntity.getEntityQuran().getTransition().duplicate());
        }
        this.blurredImageView.addEntity(quranEntity2, quranEntity.getIndex() + 1);
        this.trackViewEntity.selectEntity(quranEntity2.getEntityQuran(), false);
        this.iTrimLineCallback.onSelectEntity(quranEntity2.getEntityQuran(), -1.0f);
        this.trackViewEntity.updateCursurToSelectEntity();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void duplicateEntity(TranslationQuranEntity translationQuranEntity) {
        Typeface typeface = translationQuranEntity.getPaintAya().getTypeface();
        if (typeface == null) {
            typeface = UtilsFileLast.loadFontFromAsset(this, "fonts/" + translationQuranEntity.getNameFont());
        }
        TranslationQuranEntity translationQuranEntity2 = new TranslationQuranEntity(translationQuranEntity.getTxt(), translationQuranEntity.getRect(), typeface, translationQuranEntity.getNumber(), translationQuranEntity.getClrAya(), translationQuranEntity.getNameFont(), translationQuranEntity.getPaintAya().getTextSize());
        translationQuranEntity2.setFcSize(translationQuranEntity.getFactorSize());
        translationQuranEntity2.setFactorSizeTrl(translationQuranEntity.getFactorSizeTrl());
        translationQuranEntity2.setFactor_scale(translationQuranEntity.getFactor_scale());
        translationQuranEntity2.setCanvasWH(this.blurredImageView.getmCanvas_width(), this.blurredImageView.getmCanvas_height());
        translationQuranEntity2.setIpad_type(this.mTemplate.getIpad_type());
        translationQuranEntity2.setVisible(false);
        translationQuranEntity2.setViewWeakReference(new WeakReference<>(this.trackViewEntity), new WeakReference<>(this.blurredImageView));
        translationQuranEntity2.updatePaint(translationQuranEntity.getPaintAya().getTextSize(), translationQuranEntity.getStaticLayout().getWidth());
        translationQuranEntity2.setColor(translationQuranEntity.getClrAya());
        translationQuranEntity2.initPreset(translationQuranEntity.getmPreset());
        EntityTrslTimeline addTimeLineQuran = addTimeLineQuran(translationQuranEntity.getEntityTrslTimeline().getIndex() + 1, translationQuranEntity2, translationQuranEntity.getEntityTrslTimeline().getRect().right, translationQuranEntity.getEntityTrslTimeline().getRect().right + translationQuranEntity.getEntityTrslTimeline().getRect().width());
        Transition transition = translationQuranEntity.getEntityTrslTimeline().getTransition();
        if (transition != null) {
            addTimeLineQuran.setTransition(transition.duplicate());
            if (transition.isIn() && transition.isOut()) {
                addTimeLineQuran.getTransition().setIn(false);
                transition.setOut(false);
            } else if (transition.isIn()) {
                addTimeLineQuran.getTransition().setIn(false);
            } else if (transition.isOut()) {
                transition.setOut(false);
            }
        }
        addTimeLineQuran.setmScaleFactor(translationQuranEntity.getEntityTrslTimeline().getmScaleFactor());
        translationQuranEntity2.setEntityTrslTimeline(addTimeLineQuran);
        addTimeLineQuran.setEntityView(translationQuranEntity2);
        if (translationQuranEntity.getEntityTrslTimeline().getTransition() != null) {
            addTimeLineQuran.setTransition(translationQuranEntity.getEntityTrslTimeline().getTransition().duplicate());
        }
        this.blurredImageView.addEntity(translationQuranEntity2, translationQuranEntity.getIndex() + 1);
        this.trackViewEntity.selectEntity(translationQuranEntity2.getEntityTrslTimeline(), false);
        this.iTrimLineCallback.onSelectEntity(translationQuranEntity2.getEntityTrslTimeline(), -1.0f);
        this.trackViewEntity.updateCursurToSelectEntity();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void splitEntity(TranslationQuranEntity translationQuranEntity) {
        float abs = Math.abs(this.trackViewEntity.getXCursur());
        if (abs <= translationQuranEntity.getEntityTrslTimeline().getRect().left || abs >= translationQuranEntity.getEntityTrslTimeline().getRect().right) {
            return;
        }
        float second_in_screen = this.trackViewEntity.getSecond_in_screen() * 0.2f;
        if (abs <= translationQuranEntity.getEntityTrslTimeline().getRect().left || abs >= translationQuranEntity.getEntityTrslTimeline().getRect().left + second_in_screen) {
            if (abs >= translationQuranEntity.getEntityTrslTimeline().getRect().right || abs <= translationQuranEntity.getEntityTrslTimeline().getRect().right - second_in_screen) {
                Typeface typeface = translationQuranEntity.getPaintAya().getTypeface();
                if (typeface == null) {
                    typeface = UtilsFileLast.loadFontFromAsset(this, "fonts/" + translationQuranEntity.getNameFont());
                }
                TranslationQuranEntity translationQuranEntity2 = new TranslationQuranEntity(translationQuranEntity.getTxt(), translationQuranEntity.getRect(), typeface, translationQuranEntity.getNumber(), translationQuranEntity.getClrAya(), translationQuranEntity.getNameFont(), translationQuranEntity.getPaintAya().getTextSize());
                translationQuranEntity2.setFcSize(translationQuranEntity.getFactorSize());
                translationQuranEntity2.setFactorSizeTrl(translationQuranEntity.getFactorSizeTrl());
                translationQuranEntity2.setFactor_scale(translationQuranEntity.getFactor_scale());
                translationQuranEntity2.setCanvasWH(this.blurredImageView.getmCanvas_width(), this.blurredImageView.getmCanvas_height());
                translationQuranEntity2.setIpad_type(this.mTemplate.getIpad_type());
                translationQuranEntity2.setViewWeakReference(new WeakReference<>(this.trackViewEntity), new WeakReference<>(this.blurredImageView));
                translationQuranEntity2.updatePaint(translationQuranEntity.getPaintAya().getTextSize(), translationQuranEntity.getStaticLayout().getWidth());
                translationQuranEntity2.setColor(translationQuranEntity.getClrAya());
                translationQuranEntity2.initPreset(translationQuranEntity.getmPreset());
                this.trackViewEntity.stackSplit(translationQuranEntity.getEntityTrslTimeline());
                EntityTrslTimeline splitTimeLineQuran = splitTimeLineQuran(translationQuranEntity.getEntityTrslTimeline().getIndex() + 1, translationQuranEntity2, Math.abs(this.trackViewEntity.getCurrentPosition()), translationQuranEntity.getEntityTrslTimeline().getRect().right, translationQuranEntity.getEntityTrslTimeline().getmScaleFactor());
                Transition transition = translationQuranEntity.getEntityTrslTimeline().getTransition();
                if (transition != null) {
                    splitTimeLineQuran.setTransition(transition.duplicate());
                    if (transition.isIn() && transition.isOut()) {
                        splitTimeLineQuran.getTransition().setIn(false);
                        transition.setOut(false);
                    } else if (transition.isIn()) {
                        splitTimeLineQuran.getTransition().setIn(false);
                    } else if (transition.isOut()) {
                        transition.setOut(false);
                    }
                }
                translationQuranEntity.getEntityTrslTimeline().setCurrentRect();
                translationQuranEntity.getEntityTrslTimeline().setRight(Math.abs(this.trackViewEntity.getCurrentPosition()));
                translationQuranEntity.getEntityTrslTimeline().onChange();
                translationQuranEntity2.setEntityTrslTimeline(splitTimeLineQuran);
                splitTimeLineQuran.setEntityView(translationQuranEntity2);
                if (translationQuranEntity.getEntityTrslTimeline().getTransition() != null) {
                    splitTimeLineQuran.setTransition(translationQuranEntity.getEntityTrslTimeline().getTransition().duplicate());
                }
                this.blurredImageView.addEntity(translationQuranEntity2, translationQuranEntity.getIndex() + 1);
                this.trackViewEntity.invalidate();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void splitEntity(QuranEntity quranEntity) {
        float abs = Math.abs(this.trackViewEntity.getXCursur());
        if (abs <= quranEntity.getEntityQuran().getRect().left || abs >= quranEntity.getEntityQuran().getRect().right) {
            return;
        }
        float second_in_screen = this.trackViewEntity.getSecond_in_screen() * 0.2f;
        if (abs <= quranEntity.getEntityQuran().getRect().left || abs >= quranEntity.getEntityQuran().getRect().left + second_in_screen) {
            if (abs >= quranEntity.getEntityQuran().getRect().right || abs <= quranEntity.getEntityQuran().getRect().right - second_in_screen) {
                Typeface typefaceNumber = quranEntity.getTypefaceNumber();
                if (typefaceNumber == null) {
                    typefaceNumber = UtilsFileLast.loadFontFromAsset(this, "fonts/arabic/خط فارس الكوفي.otf");
                }
                Typeface typeface = typefaceNumber;
                Typeface typeface2 = quranEntity.getPaintAya().getTypeface();
                if (typeface2 == null) {
                    typeface2 = UtilsFileLast.loadFontFromAsset(this, "fonts/arabic/" + quranEntity.getNameFont());
                }
                Typeface typeface3 = typeface2;
                Typeface typeface4 = quranEntity.getPaintTranslationAya() != null ? quranEntity.getPaintTranslationAya().getTypeface() : null;
                if (typeface4 == null) {
                    typeface4 = Typeface.createFromAsset(getResources().getAssets(), "fonts/ReadexPro_Medium.ttf");
                }
                QuranEntity quranEntity2 = new QuranEntity(quranEntity.getTxt(), quranEntity.getComplete_aya(), quranEntity.getTranslation(), quranEntity.getTranslation_complete(), this.blurredImageView.getRectFAya(), typeface3, typeface4, quranEntity.getIndexNumber(), quranEntity.getNumber(), typeface, quranEntity.getClrAya(), quranEntity.getClrTrsl(), quranEntity.getNameFont(), quranEntity.getPaintAya().getTextSize(), quranEntity.getPaintTranslationAya().getTextSize(), quranEntity.getPaintAya().isUnderlineText(), quranEntity.getVectorDrawable());
                quranEntity2.setFcSize(quranEntity.getFactorSize());
                quranEntity2.setFactorSizeTrl(quranEntity.getFactorSizeTrl());
                quranEntity2.setFactor_scale(quranEntity.getFactor_scale());
                quranEntity2.setCanvasWH(this.blurredImageView.getmCanvas_width(), this.blurredImageView.getmCanvas_height());
                quranEntity2.setIpad_type(this.mTemplate.getIpad_type());
                quranEntity2.setStartWord_index(quranEntity.getStartWord_index());
                quranEntity2.setEndWord_index(quranEntity.getEndWord_index());
                quranEntity2.setIcon(quranEntity.getIcon());
                quranEntity2.setViewWeakReference(new WeakReference<>(this.trackViewEntity), new WeakReference<>(this.blurredImageView));
                quranEntity2.setupScaleSave(quranEntity2.getFactorSize(), this.blurredImageView.getmCanvas_width());
                quranEntity2.setColor(quranEntity.getClrAya());
                quranEntity2.setColorTranslation(quranEntity.getPaintTranslationAya() != null ? quranEntity.getClrTrsl() : InputDeviceCompat.SOURCE_ANY);
                quranEntity2.initPreset(quranEntity.getmPreset());
                this.trackViewEntity.stackSplit(quranEntity.getEntityQuran());
                EntityQuranTimeline splitTimeLineQuran = splitTimeLineQuran(quranEntity.getEntityQuran().getIndex() + 1, quranEntity2, Math.abs(this.trackViewEntity.getCurrentPosition()), quranEntity.getEntityQuran().getRect().right, quranEntity.getEntityQuran().getmScaleFactor());
                Transition transition = quranEntity.getEntityQuran().getTransition();
                if (transition != null) {
                    splitTimeLineQuran.setTransition(transition.duplicate());
                    if (transition.isIn() && transition.isOut()) {
                        splitTimeLineQuran.getTransition().setIn(false);
                        transition.setOut(false);
                    } else if (transition.isIn()) {
                        splitTimeLineQuran.getTransition().setIn(false);
                    } else if (transition.isOut()) {
                        transition.setOut(false);
                    }
                }
                quranEntity.getEntityQuran().setCurrentRect();
                quranEntity.getEntityQuran().setRight(Math.abs(this.trackViewEntity.getCurrentPosition()));
                quranEntity.getEntityQuran().onChange();
                quranEntity2.setEntityQuran(splitTimeLineQuran);
                splitTimeLineQuran.setEntityView(quranEntity2);
                if (quranEntity.getEntityQuran().getTransition() != null) {
                    splitTimeLineQuran.setTransition(quranEntity.getEntityQuran().getTransition().duplicate());
                }
                this.blurredImageView.addEntity(quranEntity2, quranEntity.getIndex() + 1);
                this.trackViewEntity.invalidate();
            }
        }
    }

    private void addEntity(String str, String str2, String str3, String str4, float f, float f2, int i, int i2, int i3, String str5, Transition transition, boolean z, String str6, int i4, int i5, float f3, float f4, float f5, RectF rectF, Typeface typeface, Typeface typeface2, int i6, int i7) {
        RectF rectF2;
        String str7 = str6 == null ? "hafes" : str6;
        Typeface loadFontFromAsset = UtilsFileLast.loadFontFromAsset(this, "fonts/arabic/" + str5);
        if (rectF == null) {
            rectF2 = this.blurredImageView.getRectFAya();
        } else {
            rectF2 = new RectF(rectF.left * this.blurredImageView.getmCanvas_width(), rectF.top * this.blurredImageView.getmCanvas_height(), rectF.right * this.blurredImageView.getmCanvas_width(), rectF.bottom * this.blurredImageView.getmCanvas_height());
        }
        QuranEntity quranEntity = new QuranEntity(this, str, str2, str3, str4, rectF2, loadFontFromAsset, typeface2, i, i2, typeface, i3, i6, str5, z, DrawableHelper.getIDDrawableIconByName(str7));
        quranEntity.setFcSize(f4);
        quranEntity.setFactorSizeTrl(f5);
        quranEntity.setCanvasWH(this.blurredImageView.getmCanvas_width(), this.blurredImageView.getmCanvas_height());
        quranEntity.setFactor_scale(f3);
        quranEntity.setIpad_type(this.mTemplate.getIpad_type());
        quranEntity.setStartWord_index(i4);
        quranEntity.setEndWord_index(i5);
        quranEntity.setIcon(str7);
        quranEntity.setViewWeakReference(new WeakReference<>(this.trackViewEntity), new WeakReference<>(this.blurredImageView));
        if (quranEntity.getFactorSize() == 1.0f) {
            quranEntity.setTextSize(quranEntity.calculateTextSize());
        } else {
            quranEntity.setupScaleSave(quranEntity.getFactorSize(), this.blurredImageView.getmCanvas_width());
        }
        quranEntity.initPreset(i7);
        EntityQuranTimeline addTimeLineQuran = addTimeLineQuran(quranEntity, f, f2);
        quranEntity.setEntityQuran(addTimeLineQuran);
        addTimeLineQuran.setTransition(transition);
        addTimeLineQuran.setEntityView(quranEntity);
        this.blurredImageView.addEntity(quranEntity);
    }

    private void addEntityTrsl(String str, float f, float f2, int i, int i2, String str2, Transition transition, float f3, float f4, RectF rectF, int i3, int i4, boolean z) {
        RectF rectF2;
        Typeface loadFontFromAsset = UtilsFileLast.loadFontFromAsset(this, "fonts/" + str2);
        if (rectF == null) {
            rectF2 = this.blurredImageView.getRectFAya();
        } else {
            rectF2 = new RectF(rectF.left * this.blurredImageView.getmCanvas_width(), rectF.top * this.blurredImageView.getmCanvas_height(), rectF.right * this.blurredImageView.getmCanvas_width(), rectF.bottom * this.blurredImageView.getmCanvas_height());
        }
        TranslationQuranEntity translationQuranEntity = new TranslationQuranEntity(this.blurredImageView.getmCanvas_width(), this.blurredImageView.getmCanvas_height(), str, rectF2, loadFontFromAsset, i, i2, str2);
        translationQuranEntity.setHaveBg(z);
        translationQuranEntity.setClrBg(i4);
        translationQuranEntity.setFcSize(f4);
        translationQuranEntity.setCanvasWH(this.blurredImageView.getmCanvas_width(), this.blurredImageView.getmCanvas_height());
        translationQuranEntity.setFactor_scale(f3);
        translationQuranEntity.setIpad_type(this.mTemplate.getIpad_type());
        translationQuranEntity.setViewWeakReference(new WeakReference<>(this.trackViewEntity), new WeakReference<>(this.blurredImageView));
        if (translationQuranEntity.getFactorSize() == 1.0f) {
            translationQuranEntity.setTextSize(translationQuranEntity.calculateTextSize());
        } else {
            translationQuranEntity.setupScaleSave(translationQuranEntity.getFactorSize(), this.blurredImageView.getmCanvas_width());
        }
        translationQuranEntity.initPreset(i3);
        EntityTrslTimeline addTimeLineQuran = addTimeLineQuran(translationQuranEntity, f, f2);
        translationQuranEntity.setEntityTrslTimeline(addTimeLineQuran);
        addTimeLineQuran.setTransition(transition);
        addTimeLineQuran.setEntityView(translationQuranEntity);
        this.blurredImageView.addEntity(translationQuranEntity);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addAudioReciters(final List<RecitersModel> list) {
        ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
        final Handler handler = new Handler(Looper.getMainLooper());
        newSingleThreadExecutor.execute(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                EngineActivity.this.m638xadfdcdf5(list, handler);
            }
        });
    }

    /* renamed from: lambda$addAudioReciters$7$hazem-nurmontage-videoquran-EngineActivity, reason: not valid java name */
    /* synthetic */ void m638xadfdcdf5(final List list, Handler handler) {
        ArrayList arrayList = new ArrayList();
        final ArrayList arrayList2 = new ArrayList();
        StringBuilder sb = new StringBuilder();
        try {
            Iterator it = list.iterator();
            int i = 0;
            while (it.hasNext()) {
                RecitersModel recitersModel = (RecitersModel) it.next();
                try {
                    String str = recitersModel.isTarteel() ? "https://audio-cdn.tarteel.ai/quran/" + recitersModel.getIdentifer() + "/" + recitersModel.getSurah_index() + recitersModel.getNumber_aya() + ".mp3" : "https://everyayah.com/data/" + recitersModel.getIdentifer() + "/" + recitersModel.getSurah_index() + recitersModel.getNumber_aya() + ".mp3";
                    String downloadFile = AudioUtils.downloadFile(this, str, this.mTemplate.getFolder_template());
                    if (downloadFile != null) {
                        arrayList.add(downloadFile);
                        arrayList2.add(str);
                        sb.append("file '").append(downloadFile.replace("'", "\\'")).append("'\n");
                        i++;
                        try {
                            final int finalI = i;
                            handler.post(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity$$ExternalSyntheticLambda9
                                @Override // java.lang.Runnable
                                public final void run() {
                                    EngineActivity.this.m634xa24c2879(finalI, list);
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            File file = new File(this.mTemplate.getFolder_template(), "concat_" + System.currentTimeMillis() + ".txt");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(sb.toString().getBytes());
            fileOutputStream.close();
            final File file2 = new File(this.mTemplate.getFolder_template(), System.currentTimeMillis() + "_output.mp3");
            final File file3 = new File(this.mTemplate.getFolder_template(), System.currentTimeMillis() + "_output.pcm");
            ArrayList arrayList3 = new ArrayList();
            arrayList3.add("-f");
            arrayList3.add("concat");
            arrayList3.add("-safe");
            arrayList3.add("0");
            arrayList3.add("-i");
            arrayList3.add(file.getAbsolutePath());
            arrayList3.add("-map");
            arrayList3.add("0:a");
            arrayList3.add("-c");
            arrayList3.add("copy");
            arrayList3.add(file2.getAbsolutePath());
            arrayList3.add("-map");
            arrayList3.add("0:a");
            arrayList3.add("-ac");
            arrayList3.add(IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE);
            arrayList3.add("-ar");
            arrayList3.add("44100");
            arrayList3.add("-f");
            arrayList3.add("s16le");
            arrayList3.add(file3.getAbsolutePath());
            arrayList3.add("-y");
            final String[] strArr = (String[]) arrayList3.toArray(new String[0]);
            handler.post(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    EngineActivity.this.m636x2824fb37(strArr, file2, arrayList2, file3);
                }
            });
        } catch (Exception e3) {
            e3.printStackTrace();
            handler.post(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    EngineActivity.this.m637xeb116496();
                }
            });
        }
    }

    /* renamed from: lambda$addAudioReciters$3$hazem-nurmontage-videoquran-EngineActivity, reason: not valid java name */
    /* synthetic */ void m634xa24c2879(int i, List list) {
        updateProgress(i, list.size());
    }

    /* renamed from: lambda$addAudioReciters$5$hazem-nurmontage-videoquran-EngineActivity, reason: not valid java name */
    /* synthetic */ void m636x2824fb37(String[] strArr, final File file, final List list, final File file2) {
        this.id_ffmpeg.add(Long.valueOf(FFmpegKit.executeWithArgumentsAsync(strArr, new FFmpegSessionCompleteCallback() { // from class: hazem.nurmontage.videoquran.EngineActivity$$ExternalSyntheticLambda6
            @Override // com.arthenica.ffmpegkit.FFmpegSessionCompleteCallback
            public final void apply(FFmpegSession fFmpegSession) {
                EngineActivity.this.m635x653891d8(file, list, file2, fFmpegSession);
            }
        }).getSessionId()));
    }

    /* renamed from: lambda$addAudioReciters$4$hazem-nurmontage-videoquran-EngineActivity, reason: not valid java name */
    /* synthetic */ void m635x653891d8(File file, List list, File file2, FFmpegSession fFmpegSession) {
        if (ReturnCode.isSuccess(fFmpegSession.getReturnCode())) {
            addAudio(Uri.fromFile(file), list, -1, file2.getAbsolutePath());
        } else {
            Log.e("FFMPEG", "Failed: " + fFmpegSession.getFailStackTrace());
        }
    }

    /* renamed from: lambda$addAudioReciters$6$hazem-nurmontage-videoquran-EngineActivity, reason: not valid java name */
    /* synthetic */ void m637xeb116496() {
        hideProgressFragment();
        hideFragment();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addAudioRecitersTemplate(List<String> list, int i, String str) {
        Executors.newSingleThreadExecutor().execute(new AnonymousClass66(list, i, str));
    }

    /* renamed from: hazem.nurmontage.videoquran.EngineActivity$66, reason: invalid class name */
    class AnonymousClass66 implements Runnable {
        final /* synthetic */ int val$index;
        final /* synthetic */ String val$path_video;
        final /* synthetic */ List val$pathes;

        AnonymousClass66(List list, int i, String str) {
            this.val$pathes = list;
            this.val$index = i;
            this.val$path_video = str;
        }

        /* JADX WARN: Removed duplicated region for block: B:13:0x0061 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:17:0x001d A[SYNTHETIC] */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public void run() {
            /*
                Method dump skipped, instructions count: 400
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: hazem.nurmontage.videoquran.EngineActivity.AnonymousClass66.run():void");
        }
    }

    public void dialogCopyRight() {
        try {
            Dialog dialog = new Dialog(this);
            this.dialog = dialog;
            dialog.setCancelable(false);
            this.dialog.requestWindowFeature(1);
            this.dialog.getWindow().setLayout(-1, -2);
            this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            View inflate = LayoutInflater.from(this).inflate(R.layout.layout_dialog_copyright, (ViewGroup) null);
            this.dialog.setContentView(inflate);
            TextCustumFontBold textCustumFontBold = (TextCustumFontBold) inflate.findViewById(R.id.dialog_title);
            TextCustumFont textCustumFont = (TextCustumFont) inflate.findViewById(R.id.tv_msj);
            inflate.findViewById(R.id.dialog_no).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.EngineActivity.67
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    EngineActivity.this.cancelDialog();
                }
            });
            if (LocaleHelper.getLanguage(this).equals("ar")) {
                textCustumFontBold.setText("تنبيه حقوق الاستخدام ⚠️");
                textCustumFont.setText("بعض تسجيلات تلاوات القرّاء محمية بحقوق النشر، وهي مخصّصة للاستخدام الشخصي فقط.\n\nقد تسمح بعض المنصات باستخدام هذه الأصوات دون مشاكل، لكن ذلك لا يُعدّ تصريحًا بالنشر أو الاستخدام التجاري.\n\nللنشر الآمن، يُرجى اختيار قارئ مذكور على أنه مسموح بالنشر أو استخدام صوتك الخاص.\n\nالمستخدم مسؤول بالكامل عن الالتزام بسياسات حقوق النشر الخاصة بكل منصة.");
            } else {
                textCustumFontBold.setText("⚠️ Copyright Notice");
                textCustumFont.setText("Some reciters’ audio recordings are protected by copyright and are intended for personal use only.\n\nCertain platforms may allow these sounds without issues, but this does not constitute permission to publish or use them commercially.\n\nFor safe publishing, please select a reciter marked as allowed for publishing or use your own audio.\n\nThe user is solely responsible for complying with the copyright policies of each platform.");
            }
            this.dialog.show();
            MyPrefereces.putVuCopyRight(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateEndViewTime(int i) {
        String str;
        long j = i;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(j) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(j));
        if (seconds < 10) {
            str = TimeUnit.MILLISECONDS.toMinutes(j) + ":0" + seconds;
        } else {
            str = TimeUnit.MILLISECONDS.toMinutes(j) + ":" + seconds;
        }
        this.tv_endTime.setText("/" + str);
    }

    public void updateStartViewTime(int i) {
        String str;
        long j = i;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(j) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(j));
        if (seconds < 10) {
            str = TimeUnit.MILLISECONDS.toMinutes(j) + ":0" + seconds;
        } else {
            str = TimeUnit.MILLISECONDS.toMinutes(j) + ":" + seconds;
        }
        this.tv_currentTime.setText(str);
    }

    public void updateViewTime(int i, int i2) {
        String str;
        String str2;
        long j = i2;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(j) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(j));
        if (seconds < 10) {
            str = TimeUnit.MILLISECONDS.toMinutes(j) + ":0" + seconds;
        } else {
            str = TimeUnit.MILLISECONDS.toMinutes(j) + ":" + seconds;
        }
        long j2 = i;
        long seconds2 = TimeUnit.MILLISECONDS.toSeconds(j2) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(j2));
        if (seconds2 < 10) {
            str2 = TimeUnit.MILLISECONDS.toMinutes(j2) + ":0" + seconds2;
        } else {
            str2 = TimeUnit.MILLISECONDS.toMinutes(j2) + ":" + seconds2;
        }
        this.tv_currentTime.setText(str);
        this.tv_endTime.setText("/" + str2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideLayoutResolution() {
        LinearLayout linearLayout = this.layout_resolution;
        if (linearLayout == null || linearLayout.getVisibility() != 0) {
            return;
        }
        this.layout_resolution.post(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.72
            @Override // java.lang.Runnable
            public void run() {
                EngineActivity.this.layout_resolution.setVisibility(8);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideFragment() {
        try {
            if (!isFinishing() && !getSupportFragmentManager().isDestroyed()) {
                FragmentManager supportFragmentManager = getSupportFragmentManager();
                FragmentTransaction beginTransaction = supportFragmentManager.beginTransaction();
                Fragment findFragmentById = supportFragmentManager.findFragmentById(R.id.m_container);
                if (findFragmentById != null) {
                    beginTransaction.remove(findFragmentById);
                }
                beginTransaction.commit();
                setupHideFragment();
            }
        } catch (Exception unused) {
        }
        this.mCurrentFragment = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showProgress() {
        try {
            setStatusBarColor(ViewCompat.MEASURED_STATE_MASK);
            setNavigationBarColor(ViewCompat.MEASURED_STATE_MASK);
            findViewById(R.id.container_progress).setVisibility(0);
            if (isFinishing() || getSupportFragmentManager().isDestroyed()) {
                return;
            }
            FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
            beginTransaction.replace(R.id.container_progress, ProgressViewFragment.getInstance());
            beginTransaction.commit();
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showProgressSimple() {
        try {
            findViewById(R.id.container_progress).setVisibility(0);
            if (isFinishing() || getSupportFragmentManager().isDestroyed()) {
                return;
            }
            FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
            beginTransaction.replace(R.id.container_progress, SimpleProgressViewFragment.getInstance());
            beginTransaction.commit();
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideProgressFragment() {
        try {
            setStatusBarColor(-15658735);
            setNavigationBarColor(-14803426);
            if (!isFinishing() && !getSupportFragmentManager().isDestroyed()) {
                FragmentManager supportFragmentManager = getSupportFragmentManager();
                FragmentTransaction beginTransaction = supportFragmentManager.beginTransaction();
                Fragment findFragmentById = supportFragmentManager.findFragmentById(R.id.container_progress);
                if (findFragmentById != null) {
                    beginTransaction.remove(findFragmentById);
                }
                beginTransaction.commit();
            }
            findViewById(R.id.container_progress).setVisibility(8);
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void toCrop() {
        this.isSaveTmpTemplate = false;
        this.isToCrop = true;
        Common.bitmap = this.blurredImageView.getBitmapOriginal();
        Common.rect = this.blurredImageView.getRectSquare();
        if (this.blurredImageView.getBitmapSquare() != null) {
            Common.MIN_SQUARE_W = this.blurredImageView.getBitmapSquare().getWidth();
            Common.MIN_SQUARE_H = this.blurredImageView.getBitmapSquare().getHeight();
        }
        Common.radius = this.blurredImageView.getRadius_square();
        this.launchCropActivity.launch(new Intent(this, (Class<?>) CropBitmapActivity.class));
    }

    public void dialogWatermark() {
        try {
            if (this.dialog != null) {
                cancelDialog();
            }
            this.isSaveTmpTemplate = false;
            this.isToCrop = true;
            Dialog dialog = new Dialog(this);
            this.dialog = dialog;
            dialog.setCancelable(true);
            this.dialog.requestWindowFeature(1);
            this.dialog.getWindow().setLayout(-1, -2);
            this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            View inflate = LayoutInflater.from(this).inflate(R.layout.layout_dialog, (ViewGroup) null);
            this.dialog.setContentView(inflate);
            inflate.findViewById(R.id.dialog_title).setVisibility(8);
            inflate.findViewById(R.id.img_pro).setVisibility(0);
            TextCustumFont textCustumFont = (TextCustumFont) inflate.findViewById(R.id.dialog_message);
            textCustumFont.setText(this.mResources.getString(R.string.do_want_delete_watermark));
            textCustumFont.setGravity(17);
            ButtonCustumFont buttonCustumFont = (ButtonCustumFont) inflate.findViewById(R.id.dialog_no);
            buttonCustumFont.setText(this.mResources.getString(R.string.no));
            buttonCustumFont.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.EngineActivity.73
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    EngineActivity.this.cancelDialog();
                }
            });
            ButtonCustumFont buttonCustumFont2 = (ButtonCustumFont) inflate.findViewById(R.id.dialog_yes);
            buttonCustumFont2.setText(this.mResources.getString(R.string.yes));
            buttonCustumFont2.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.EngineActivity.74
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    EngineActivity.this.toProVersion();
                    EngineActivity.this.cancelDialog();
                }
            });
            this.dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dialogPremium(int i) {
        try {
            if (this.dialog != null) {
                cancelDialog();
            }
            this.isSaveTmpTemplate = false;
            Dialog dialog = new Dialog(this);
            this.dialog = dialog;
            dialog.setCancelable(true);
            this.dialog.requestWindowFeature(1);
            this.dialog.getWindow().setLayout(-1, -2);
            this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            View inflate = LayoutInflater.from(this).inflate(R.layout.layout_dialog, (ViewGroup) null);
            this.dialog.setContentView(inflate);
            inflate.findViewById(R.id.dialog_title).setVisibility(8);
            inflate.findViewById(R.id.img_pro).setVisibility(0);
            TextCustumFont textCustumFont = (TextCustumFont) inflate.findViewById(R.id.dialog_message);
            textCustumFont.setText(this.mResources.getString(R.string.unlock_premium));
            textCustumFont.setGravity(17);
            ButtonCustumFont buttonCustumFont = (ButtonCustumFont) inflate.findViewById(R.id.dialog_no);
            buttonCustumFont.setText(this.mResources.getString(R.string.no));
            buttonCustumFont.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.EngineActivity.75
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    EngineActivity.this.cancelDialog();
                }
            });
            ButtonCustumFont buttonCustumFont2 = (ButtonCustumFont) inflate.findViewById(R.id.dialog_yes);
            buttonCustumFont2.setText(this.mResources.getString(R.string.yes));
            buttonCustumFont2.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.EngineActivity.76
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    EngineActivity.this.toProVersion();
                    EngineActivity.this.cancelDialog();
                }
            });
            this.dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: hazem.nurmontage.videoquran.EngineActivity$77, reason: invalid class name */
    class AnonymousClass77 implements ChangeBgFragment.IChangeBgCallback {
        AnonymousClass77() {
        }

        @Override // hazem.nurmontage.videoquran.fragment.ChangeBgFragment.IChangeBgCallback
        public void onSubscribe() {
            EngineActivity.this.dialogPremium(0);
        }

        @Override // hazem.nurmontage.videoquran.fragment.ChangeBgFragment.IChangeBgCallback
        public void onCrop() {
            EngineActivity.this.toCrop();
        }

        @Override // hazem.nurmontage.videoquran.fragment.ChangeBgFragment.IChangeBgCallback
        public void onAdd(BgItem bgItem) {
            if (bgItem.getName_drawable().equals(EngineActivity.this.mTemplate.getName_drawable())) {
                return;
            }
            if (ChangeBgFragment.instance != null) {
                ChangeBgFragment.instance.scrollToSelected();
            }
            EngineActivity.this.mTemplate.setName_drawable(bgItem.getName_drawable());
            EngineActivity.this.uri_bg = "android.resource://" + EngineActivity.this.getPackageName() + "/drawable/" + bgItem.getId();
            EngineActivity.this.showProgressSimple();
            EngineActivity.this.executor.execute(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.77.1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.lang.Runnable
                public void run() {
                    EngineActivity engineActivity;
                    Runnable runnable;
                    Bitmap cropTo16x9 = null;
                    Bitmap bitmap = null;
                    Bitmap bitmap2;
                    Rect rect;
                    try {
                        try {
                            try {
                                EngineActivity.this.mTemplate.setUri_bg(EngineActivity.this.uri_bg);
                                int i = 0;
                                EngineActivity.this.mTemplate.setVideoSquare(false);
                                EngineActivity.this.blurredImageView.setVideo(false);
                                int height = EngineActivity.this.blurredImageView.getHeight();
                                EngineActivity.this.blurredImageView.setBitmapOriginal((Bitmap) Glide.with((FragmentActivity) EngineActivity.this).asBitmap().load(EngineActivity.this.uri_bg).override(height, height).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).submit().get());
                                if (EngineActivity.this.mTemplate.geTypeResize() == ResizeType.SOCIAL_STORY.ordinal()) {
                                    cropTo16x9 = BitmapCropper.cropTo9x16(EngineActivity.this.blurredImageView.getBitmapOriginal(), EngineActivity.this.blurredImageView.getW(), EngineActivity.this.blurredImageView.getH());
                                } else if (EngineActivity.this.mTemplate.geTypeResize() == ResizeType.SQUARE.ordinal()) {
                                    cropTo16x9 = BitmapCropper.cropTo1x1(EngineActivity.this.blurredImageView.getBitmapOriginal(), EngineActivity.this.blurredImageView.getW(), EngineActivity.this.blurredImageView.getH());
                                } else {
                                    cropTo16x9 = BitmapCropper.cropTo16x9(EngineActivity.this.blurredImageView.getBitmapOriginal(), EngineActivity.this.blurredImageView.getW(), EngineActivity.this.blurredImageView.getH());
                                }
                                EngineActivity.this.blurredImageView.updatePosCanvas(cropTo16x9);
                                EngineActivity.this.blurredImageView.updateIpad(cropTo16x9, EngineActivity.this.mTemplate.getIpad_type(), EngineActivity.this.mTemplate.geTypeResize());
                                if (EngineActivity.this.mTemplate.getIpad_type() == IpadType.IPAD_NEOMORPHIC.ordinal()) {
                                    int width = (int) (EngineActivity.this.blurredImageView.getIpad_rect().width() * 0.6f);
                                    int round = Math.round(EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth() * EngineActivity.this.mTemplate.getX_square());
                                    int round2 = Math.round(EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight() * EngineActivity.this.mTemplate.getY_square());
                                    int i2 = width + round;
                                    if (i2 > EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth()) {
                                        round -= i2 - EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth();
                                        i2 = EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth();
                                    }
                                    int i3 = width + round2;
                                    if (i3 > EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight()) {
                                        round2 -= i3 - EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight();
                                        i3 = EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight();
                                    }
                                    if (round < 0) {
                                        round = 0;
                                    }
                                    if (round2 >= 0) {
                                        i = round2;
                                    }
                                    Rect rect2 = new Rect(round, i, i2, i3);
                                    EngineActivity.this.blurredImageView.setRadius_square(width);
                                    int width2 = (int) (EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth() * EngineActivity.this.mTemplate.getWidth_square());
                                    int height2 = (int) (EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight() * EngineActivity.this.mTemplate.getHeight_square());
                                    Bitmap cropToSquareWithRoundCorners = UtilsBitmap.cropToSquareWithRoundCorners(EngineActivity.this.blurredImageView.getBitmapOriginal(), rect2, width, width2, height2);
                                    rect2.right = rect2.left + width2;
                                    rect2.bottom = rect2.top + height2;
                                    EngineActivity.this.blurredImageView.setRectSquare(rect2);
                                    bitmap2 = cropToSquareWithRoundCorners;
                                    rect = rect2;
                                } else {
                                    if (EngineActivity.this.mTemplate.getIpad_type() != IpadType.IPAD.ordinal() && EngineActivity.this.mTemplate.getIpad_type() != IpadType.IPAD_UNBLUR.ordinal() && EngineActivity.this.mTemplate.getIpad_type() != IpadType.IPAD_CLASSIC.ordinal()) {
                                        int width3 = (int) (EngineActivity.this.blurredImageView.getIpad_rect().width() * 1.0f);
                                        int height3 = (int) (cropTo16x9.getHeight() * 0.5355f);
                                        int round3 = Math.round(EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth() * EngineActivity.this.mTemplate.getX_square());
                                        int round4 = Math.round(EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight() * EngineActivity.this.mTemplate.getY_square());
                                        int i4 = width3 + round3;
                                        if (i4 > EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth()) {
                                            round3 -= i4 - EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth();
                                            i4 = EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth();
                                        }
                                        int i5 = height3 + round4;
                                        if (i5 > EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight()) {
                                            round4 -= i5 - EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight();
                                            i5 = EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight();
                                        }
                                        if (round3 < 0) {
                                            round3 = 0;
                                        }
                                        if (round4 < 0) {
                                            round4 = 0;
                                        }
                                        Rect rect3 = new Rect(round3, round4, i4, i5);
                                        int width4 = (int) (EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth() * EngineActivity.this.mTemplate.getWidth_square());
                                        int height4 = (int) (EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight() * EngineActivity.this.mTemplate.getHeight_square());
                                        Bitmap cropToSquare = UtilsBitmap.cropToSquare(EngineActivity.this.blurredImageView.getBitmapOriginal(), rect3, width4, height4);
                                        EngineActivity.this.blurredImageView.setBitmapSquare(cropToSquare);
                                        EngineActivity.this.blurredImageView.setRadius_square(0);
                                        rect3.right = rect3.left + width4;
                                        rect3.bottom = rect3.top + height4;
                                        EngineActivity.this.blurredImageView.setRectSquare(rect3);
                                        bitmap2 = cropToSquare;
                                        rect = rect3;
                                    }
                                    int width5 = (int) (EngineActivity.this.blurredImageView.getIpad_rect().width() * 0.87530595f);
                                    int i6 = (int) (width5 * 1.13f);
                                    int min = Math.min(width5, i6);
                                    int round5 = Math.round(EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth() * EngineActivity.this.mTemplate.getX_square());
                                    int round6 = Math.round(EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight() * EngineActivity.this.mTemplate.getY_square());
                                    int i7 = width5 + round5;
                                    if (i7 > EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth()) {
                                        round5 -= i7 - EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth();
                                        i7 = EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth();
                                    }
                                    int i8 = i6 + round6;
                                    if (i8 > EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight()) {
                                        round6 -= i8 - EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight();
                                        i8 = EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight();
                                    }
                                    if (round5 < 0) {
                                        round5 = 0;
                                    }
                                    if (round6 < 0) {
                                        round6 = 0;
                                    }
                                    Rect rect4 = new Rect(round5, round6, i7, i8);
                                    if (EngineActivity.this.mTemplate.getIpad_type() == IpadType.IPAD_CLASSIC.ordinal()) {
                                        int width6 = (int) (EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth() * EngineActivity.this.mTemplate.getWidth_square());
                                        int height5 = (int) (EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight() * EngineActivity.this.mTemplate.getHeight_square());
                                        Bitmap cropToSquare2 = UtilsBitmap.cropToSquare(EngineActivity.this.blurredImageView.getBitmapOriginal(), rect4, width6, height5);
                                        EngineActivity.this.blurredImageView.setBitmapSquare(cropToSquare2);
                                        EngineActivity.this.blurredImageView.setRadius_square(0);
                                        rect4.right = rect4.left + width6;
                                        rect4.bottom = rect4.top + height5;
                                        EngineActivity.this.blurredImageView.setRectSquare(rect4);
                                        bitmap = cropToSquare2;
                                    } else {
                                        int i9 = (int) (min * 0.10800001f);
                                        EngineActivity.this.blurredImageView.setRadius_square(i9);
                                        int width7 = (int) (EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth() * EngineActivity.this.mTemplate.getWidth_square());
                                        int height6 = (int) (EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight() * EngineActivity.this.mTemplate.getHeight_square());
                                        Bitmap cropToSquareWithRoundCorners2 = UtilsBitmap.cropToSquareWithRoundCorners(EngineActivity.this.blurredImageView.getBitmapOriginal(), rect4, i9, width7, height6);
                                        rect4.right = rect4.left + width7;
                                        rect4.bottom = rect4.top + height6;
                                        EngineActivity.this.blurredImageView.setRectSquare(rect4);
                                        bitmap = cropToSquareWithRoundCorners2;
                                    }
                                    bitmap2 = bitmap;
                                    rect = rect4;
                                }
                                if (EngineActivity.this.mTemplate.getIpad_type() == IpadType.GRADIENT.ordinal()) {
                                    EngineActivity.this.blurredImageView.updateBitmap(UtilsBitmap.blur(EngineActivity.this, cropTo16x9, 20, 1), bitmap2, ViewCompat.MEASURED_STATE_MASK, EngineActivity.this.mTemplate.getIpad_type(), EngineActivity.this.mTemplate.geTypeResize(), rect);
                                } else if (EngineActivity.this.mTemplate.getIpad_type() == IpadType.BLUE_TYPE.ordinal()) {
                                    if (EngineActivity.this.blurredImageView.getColor_gradient() != null) {
                                        EngineActivity.this.blurredImageView.updateBitmap(UtilsBitmap.blur(EngineActivity.this, cropTo16x9, 20, 1), bitmap2, EngineActivity.this.blurredImageView.getColor_gradient(), EngineActivity.this.mTemplate.getIpad_type(), EngineActivity.this.mTemplate.geTypeResize(), rect);
                                    } else {
                                        EngineActivity.this.blurredImageView.updateBitmap(UtilsBitmap.blur(EngineActivity.this, cropTo16x9, 20, 1), bitmap2, EngineActivity.this.blurredImageView.getColor_ipad(), EngineActivity.this.mTemplate.getIpad_type(), EngineActivity.this.mTemplate.geTypeResize(), rect);
                                    }
                                } else {
                                    EngineActivity.this.blurredImageView.updateBitmap(UtilsBitmap.blur(EngineActivity.this, cropTo16x9, 20, 1), bitmap2, -1, EngineActivity.this.mTemplate.getIpad_type(), EngineActivity.this.mTemplate.geTypeResize(), rect);
                                }
                                EngineActivity.this.mTemplate.setColor_ipad(EngineActivity.this.blurredImageView.colorIpad());
                                EngineActivity.this.runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.77.1.1
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        EngineActivity.this.blurredImageView.invalidate();
                                    }
                                });
                                engineActivity = EngineActivity.this;
                                runnable = new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.77.1.2
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        EngineActivity.this.hideProgressFragment();
                                    }
                                };
                            } catch (Exception e) {
                                e.printStackTrace();
                                engineActivity = EngineActivity.this;
                                runnable = new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.77.1.2
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        EngineActivity.this.hideProgressFragment();
                                    }
                                };
                            }
                            engineActivity.runOnUiThread(runnable);
                        } catch (Exception unused) {
                        }
                    } finally {
                    }
                }
            });
        }

        @Override // hazem.nurmontage.videoquran.fragment.ChangeBgFragment.IChangeBgCallback
        public void onUploadVideo() {
            EngineActivity.this.pickVideoFromGallery();
        }

        @Override // hazem.nurmontage.videoquran.fragment.ChangeBgFragment.IChangeBgCallback
        public void onUploadImg() {
            EngineActivity.this.pickImageFromGallery();
        }

        @Override // hazem.nurmontage.videoquran.fragment.ChangeBgFragment.IChangeBgCallback
        public void onDone() {
            EngineActivity.this.hideFragment();
        }

        @Override // hazem.nurmontage.videoquran.fragment.ChangeBgFragment.IChangeBgCallback
        public void onCancel() {
            EngineActivity.this.hideFragment();
        }
    }

    public void updateHitRatio(int i, String str) {
        if (i == ResizeType.SOCIAL_STORY.ordinal()) {
            this.textChangeResize.setText("9:16");
        } else if (i == ResizeType.SQUARE.ordinal()) {
            this.textChangeResize.setText("1:1");
        } else {
            this.textChangeResize.setText("16:9");
        }
        this.ivResize.setImageResource(DrawableHelper.getIdResource(str));
    }

    /* renamed from: hazem.nurmontage.videoquran.EngineActivity$78, reason: invalid class name */
    class AnonymousClass78 implements DimensionAdabters.IDimensionCallback {
        @Override // hazem.nurmontage.videoquran.adabter.DimensionAdabters.IDimensionCallback
        public void isCustomSize(boolean z, ResizeType resizeType) {
        }

        AnonymousClass78() {
        }

        @Override // hazem.nurmontage.videoquran.adabter.DimensionAdabters.IDimensionCallback
        public void done() {
            EngineActivity.this.hideFragment();
        }

        @Override // hazem.nurmontage.videoquran.adabter.DimensionAdabters.IDimensionCallback
        public void onCustumSize(int i, int i2, final int i3, final String str, int i4) {
            EngineActivity.this.updateHitRatio(i3, str);
            if (i3 == EngineActivity.this.mTemplate.geTypeResize()) {
                return;
            }
            if (ResizeFragment.instance != null) {
                ResizeFragment.instance.scrollToSelectedPosition();
            }
            EngineActivity.this.showProgressSimple();
            EngineActivity.this.executor.execute(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.78.1
                @Override // java.lang.Runnable
                public void run() {
                    EngineActivity engineActivity;
                    Runnable runnable;
                    Bitmap cropTo16x9 = null;
                    int i5 = 0;
                    Bitmap cropToSquareWithRoundCorners;
                    Bitmap bitmap = null;
                    Rect rect;
                    try {
                        try {
                            try {
                                EngineActivity.this.blurredImageView.reset();
                                EngineActivity.this.mTemplate.setResizeType(i3);
                                EngineActivity.this.mTemplate.setImgResize(str);
                                Pair<Integer, Integer> size = AspectRatioCalculator.getSize(i3, EngineActivity.this.mTemplate.getResolution());
                                EngineActivity.this.mTemplate.setWidthAndHeight(size.getFirst().intValue(), size.getSecond().intValue());
                                EngineActivity.this.blurredImageView.initCanvasDimension(EngineActivity.this.blurredImageView.getWidth(), EngineActivity.this.blurredImageView.getHeight(), i3);
                                if (EngineActivity.this.mTemplate.geTypeResize() == ResizeType.SOCIAL_STORY.ordinal()) {
                                    cropTo16x9 = BitmapCropper.cropTo9x16(EngineActivity.this.blurredImageView.getBitmapOriginal(), EngineActivity.this.blurredImageView.getW(), EngineActivity.this.blurredImageView.getH());
                                } else if (EngineActivity.this.mTemplate.geTypeResize() == ResizeType.SQUARE.ordinal()) {
                                    cropTo16x9 = BitmapCropper.cropTo1x1(EngineActivity.this.blurredImageView.getBitmapOriginal(), EngineActivity.this.blurredImageView.getW(), EngineActivity.this.blurredImageView.getH());
                                } else {
                                    cropTo16x9 = BitmapCropper.cropTo16x9(EngineActivity.this.blurredImageView.getBitmapOriginal(), EngineActivity.this.blurredImageView.getW(), EngineActivity.this.blurredImageView.getH());
                                }
                                EngineActivity.this.blurredImageView.updatePosCanvas(cropTo16x9);
                                EngineActivity.this.blurredImageView.setBitmapBlured(cropTo16x9);
                                EngineActivity.this.blurredImageView.updateIpad(cropTo16x9, EngineActivity.this.mTemplate.getIpad_type(), EngineActivity.this.mTemplate.geTypeResize());
                                i5 = 0;
                            } finally {
                            }
                        } catch (Exception e) {
                            Log.e("Tag resize : ", "init " + e.getMessage());
                            engineActivity = EngineActivity.this;
                            runnable = new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.78.1.2
                                @Override // java.lang.Runnable
                                public void run() {
                                    EngineActivity.this.hideProgressFragment();
                                }
                            };
                        }
                        if (EngineActivity.this.mTemplate.getIpad_type() != IpadType.GRADIENT.ordinal() && EngineActivity.this.mTemplate.getIpad_type() != IpadType.BLACK_LAYER.ordinal() && EngineActivity.this.mTemplate.getIpad_type() != IpadType.MASK_BRUSH.ordinal() && EngineActivity.this.mTemplate.getIpad_type() != IpadType.BLUE_TYPE.ordinal() && EngineActivity.this.mTemplate.getIpad_type() != IpadType.CASSET_IMG.ordinal() && EngineActivity.this.mTemplate.getIpad_type() != IpadType.CASSET_IMG_BLUR.ordinal()) {
                            if (EngineActivity.this.mTemplate.getIpad_type() == IpadType.IPAD_NEOMORPHIC.ordinal()) {
                                int width = (int) (EngineActivity.this.blurredImageView.getIpad_rect().width() * 0.6f);
                                int round = Math.round(EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth() * EngineActivity.this.mTemplate.getX_square());
                                int round2 = Math.round(EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight() * EngineActivity.this.mTemplate.getY_square());
                                int i6 = width + round;
                                if (i6 > EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth()) {
                                    round -= i6 - EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth();
                                    i6 = EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth();
                                }
                                int i7 = width + round2;
                                if (i7 > EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight()) {
                                    round2 -= i7 - EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight();
                                    i7 = EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight();
                                }
                                if (round < 0) {
                                    round = 0;
                                }
                                if (round2 >= 0) {
                                    i5 = round2;
                                }
                                Rect rect2 = new Rect(round, i5, i6, i7);
                                EngineActivity.this.blurredImageView.setRadius_square(width);
                                int width2 = (int) (EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth() * EngineActivity.this.mTemplate.getWidth_square());
                                int height = (int) (EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight() * EngineActivity.this.mTemplate.getHeight_square());
                                Bitmap cropToSquareWithRoundCorners2 = UtilsBitmap.cropToSquareWithRoundCorners(EngineActivity.this.blurredImageView.getBitmapOriginal(), rect2, width, width2, height);
                                rect2.right = rect2.left + width2;
                                rect2.bottom = rect2.top + height;
                                EngineActivity.this.blurredImageView.setRectSquare(rect2);
                                bitmap = cropToSquareWithRoundCorners2;
                                rect = rect2;
                            } else {
                                if (EngineActivity.this.mTemplate.getIpad_type() != IpadType.IPAD.ordinal() && EngineActivity.this.mTemplate.getIpad_type() != IpadType.IPAD_UNBLUR.ordinal() && EngineActivity.this.mTemplate.getIpad_type() != IpadType.IPAD_CLASSIC.ordinal()) {
                                    int width3 = (int) (EngineActivity.this.blurredImageView.getIpad_rect().width() * 1.0f);
                                    int height2 = (int) (cropTo16x9.getHeight() * 0.5355f);
                                    int round3 = Math.round(EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth() * EngineActivity.this.mTemplate.getX_square());
                                    int round4 = Math.round(EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight() * EngineActivity.this.mTemplate.getY_square());
                                    int i8 = width3 + round3;
                                    if (i8 > EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth()) {
                                        round3 -= i8 - EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth();
                                        i8 = EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth();
                                    }
                                    int i9 = height2 + round4;
                                    if (i9 > EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight()) {
                                        round4 -= i9 - EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight();
                                        i9 = EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight();
                                    }
                                    if (round3 < 0) {
                                        round3 = 0;
                                    }
                                    if (round4 < 0) {
                                        round4 = 0;
                                    }
                                    Rect rect3 = new Rect(round3, round4, i8, i9);
                                    int width4 = (int) (EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth() * EngineActivity.this.mTemplate.getWidth_square());
                                    int height3 = (int) (EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight() * EngineActivity.this.mTemplate.getHeight_square());
                                    Bitmap cropToSquare = UtilsBitmap.cropToSquare(EngineActivity.this.blurredImageView.getBitmapOriginal(), rect3, width4, height3);
                                    EngineActivity.this.blurredImageView.setBitmapSquare(cropToSquare);
                                    EngineActivity.this.blurredImageView.setRadius_square(0);
                                    rect3.right = rect3.left + width4;
                                    rect3.bottom = rect3.top + height3;
                                    EngineActivity.this.blurredImageView.setRectSquare(rect3);
                                    bitmap = cropToSquare;
                                    rect = rect3;
                                }
                                int width5 = (int) (EngineActivity.this.blurredImageView.getIpad_rect().width() * 0.87530595f);
                                int i10 = (int) (width5 * 1.13f);
                                int min = Math.min(width5, i10);
                                int round5 = Math.round(EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth() * EngineActivity.this.mTemplate.getX_square());
                                int round6 = Math.round(EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight() * EngineActivity.this.mTemplate.getY_square());
                                int i11 = width5 + round5;
                                if (i11 > EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth()) {
                                    round5 -= i11 - EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth();
                                    i11 = EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth();
                                }
                                int i12 = i10 + round6;
                                if (i12 > EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight()) {
                                    round6 -= i12 - EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight();
                                    i12 = EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight();
                                }
                                if (round5 < 0) {
                                    round5 = 0;
                                }
                                if (round6 < 0) {
                                    round6 = 0;
                                }
                                Rect rect4 = new Rect(round5, round6, i11, i12);
                                if (EngineActivity.this.mTemplate.getIpad_type() == IpadType.IPAD_CLASSIC.ordinal()) {
                                    int width6 = (int) (EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth() * EngineActivity.this.mTemplate.getWidth_square());
                                    int height4 = (int) (EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight() * EngineActivity.this.mTemplate.getHeight_square());
                                    Bitmap cropToSquare2 = UtilsBitmap.cropToSquare(EngineActivity.this.blurredImageView.getBitmapOriginal(), rect4, width6, height4);
                                    EngineActivity.this.blurredImageView.setBitmapSquare(cropToSquare2);
                                    EngineActivity.this.blurredImageView.setRadius_square(0);
                                    rect4.right = rect4.left + width6;
                                    rect4.bottom = rect4.top + height4;
                                    EngineActivity.this.blurredImageView.setRectSquare(rect4);
                                    cropToSquareWithRoundCorners = cropToSquare2;
                                } else {
                                    int i13 = (int) (min * 0.10800001f);
                                    EngineActivity.this.blurredImageView.setRadius_square(i13);
                                    int width7 = (int) (EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth() * EngineActivity.this.mTemplate.getWidth_square());
                                    int height5 = (int) (EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight() * EngineActivity.this.mTemplate.getHeight_square());
                                    cropToSquareWithRoundCorners = UtilsBitmap.cropToSquareWithRoundCorners(EngineActivity.this.blurredImageView.getBitmapOriginal(), rect4, i13, width7, height5);
                                    rect4.right = rect4.left + width7;
                                    rect4.bottom = rect4.top + height5;
                                    EngineActivity.this.blurredImageView.setRectSquare(rect4);
                                }
                                bitmap = cropToSquareWithRoundCorners;
                                rect = rect4;
                            }
                            if (EngineActivity.this.blurredImageView.getColor_gradient() != null) {
                                EngineActivity.this.blurredImageView.setBitmap(UtilsBitmap.blur(EngineActivity.this, cropTo16x9, 20, 1), bitmap, EngineActivity.this.blurredImageView.getColor_gradient(), EngineActivity.this.mTemplate.getIpad_type(), i3, rect);
                            } else {
                                EngineActivity.this.blurredImageView.setBitmap(UtilsBitmap.blur(EngineActivity.this, cropTo16x9, 20, 1), bitmap, EngineActivity.this.blurredImageView.getColor_ipad(), EngineActivity.this.mTemplate.getIpad_type(), i3, rect);
                            }
                            EngineActivity.this.blurredImageView.resizeEntity();
                            EngineActivity.this.blurredImageView.updatePosSurahName();
                            EngineActivity.this.runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.78.1.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    if (EngineActivity.this.trackViewEntity.getCurrent_cursur_position() > EngineActivity.this.trackViewEntity.getMaxTime()) {
                                        EngineActivity.this.blurredImageView.invalidate();
                                    }
                                    EngineActivity.this.trackViewEntity.invalidate();
                                    EngineActivity.this.updateTime();
                                }
                            });
                            engineActivity = EngineActivity.this;
                            runnable = new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.78.1.2
                                @Override // java.lang.Runnable
                                public void run() {
                                    EngineActivity.this.hideProgressFragment();
                                }
                            };
                            engineActivity.runOnUiThread(runnable);
                        }
                        EngineActivity.this.blurredImageView.setBitmapNotBlur(cropTo16x9);
                        Bitmap copy = cropTo16x9.copy(cropTo16x9.getConfig() != null ? cropTo16x9.getConfig() : Bitmap.Config.ARGB_8888, true);
                        if (EngineActivity.this.blurredImageView.getColor_gradient() != null) {
                            EngineActivity.this.blurredImageView.setBitmap(UtilsBitmap.blur(EngineActivity.this, cropTo16x9, 20, 1), copy, EngineActivity.this.blurredImageView.getColor_gradient(), EngineActivity.this.mTemplate.getIpad_type(), i3, EngineActivity.this.blurredImageView.getRectSquare());
                        } else {
                            EngineActivity.this.blurredImageView.setBitmap(UtilsBitmap.blur(EngineActivity.this, cropTo16x9, 20, 1), copy, EngineActivity.this.blurredImageView.getColor_ipad(), EngineActivity.this.mTemplate.getIpad_type(), i3, EngineActivity.this.blurredImageView.getRectSquare());
                        }
                        if (EngineActivity.this.mTemplate.getIpad_type() == IpadType.CASSET_IMG_BLUR.ordinal()) {
                            EngineActivity.this.blurredImageView.setBitmapSquare(EngineActivity.this.blurredImageView.getBitmapBlured());
                            EngineActivity.this.blurredImageView.setRadius_square(0);
                        }
                        EngineActivity.this.blurredImageView.resizeEntity();
                        EngineActivity.this.blurredImageView.updatePosSurahName();
                        EngineActivity.this.runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.78.1.1
                            @Override // java.lang.Runnable
                            public void run() {
                                if (EngineActivity.this.trackViewEntity.getCurrent_cursur_position() > EngineActivity.this.trackViewEntity.getMaxTime()) {
                                    EngineActivity.this.blurredImageView.invalidate();
                                }
                                EngineActivity.this.trackViewEntity.invalidate();
                                EngineActivity.this.updateTime();
                            }
                        });
                        engineActivity = EngineActivity.this;
                        runnable = new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.78.1.2
                            @Override // java.lang.Runnable
                            public void run() {
                                EngineActivity.this.hideProgressFragment();
                            }
                        };
                        engineActivity.runOnUiThread(runnable);
                    } catch (Exception unused) {
                    }
                }
            });
        }
    }

    public void dialogPremiumIpad() {
        this.isSaveTmpTemplate = false;
        try {
            Dialog dialog = new Dialog(this);
            this.dialog = dialog;
            dialog.setCancelable(true);
            this.dialog.requestWindowFeature(1);
            this.dialog.getWindow().setLayout(-1, -2);
            this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            View inflate = LayoutInflater.from(this).inflate(R.layout.layout_dialog_premuim, (ViewGroup) null);
            this.dialog.setContentView(inflate);
            inflate.findViewById(R.id.dialog_title).setVisibility(8);
            TextCustumFont textCustumFont = (TextCustumFont) inflate.findViewById(R.id.dialog_message);
            TextCustumFont textCustumFont2 = (TextCustumFont) inflate.findViewById(R.id.tv_subscribe);
            inflate.findViewById(R.id.dialog_no).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.EngineActivity.81
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    EngineActivity.this.cancelDialog();
                }
            });
            RelativeLayout relativeLayout = (RelativeLayout) inflate.findViewById(R.id.dialog_yes);
            relativeLayout.setBackgroundResource(R.drawable.btn_dialog_premium_state);
            relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.EngineActivity.82
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    EngineActivity.this.toProVersion();
                    EngineActivity.this.cancelDialog();
                }
            });
            if (LocaleHelper.getLanguage(this).equals("ar")) {
                textCustumFont.setText("🎁 هذه الميزة فقط للمشتركين في التطبيق.");
                textCustumFont2.setText("النسخة المدفوعة");
            } else {
                textCustumFont.setText("🎁 This feature is only for app subscribers.");
                textCustumFont2.setText("Upgrade premium");
            }
            this.dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pickVideoForAudio() {
        if (Build.VERSION.SDK_INT >= 34) {
            if (ContextCompat.checkSelfPermission(this, "android.permission.READ_MEDIA_VISUAL_USER_SELECTED") != 0 && ContextCompat.checkSelfPermission(this, "android.permission.READ_MEDIA_VIDEO") != 0) {
                ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_MEDIA_VIDEO", "android.permission.READ_MEDIA_VISUAL_USER_SELECTED"}, 12);
                return;
            }
        } else if (Build.VERSION.SDK_INT >= 33) {
            if (ContextCompat.checkSelfPermission(this, "android.permission.READ_MEDIA_VIDEO") != 0) {
                ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_MEDIA_VIDEO"}, 12);
                return;
            }
        } else if (ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") != 0) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 12);
            return;
        }
        videoChooserForAudio();
    }

    public void pickVideoFromGallery() {
        if (Build.VERSION.SDK_INT >= 34) {
            if (ContextCompat.checkSelfPermission(this, "android.permission.READ_MEDIA_VISUAL_USER_SELECTED") != 0 && ContextCompat.checkSelfPermission(this, "android.permission.READ_MEDIA_VIDEO") != 0) {
                ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_VIDEO", "android.permission.READ_MEDIA_VISUAL_USER_SELECTED"}, 11);
                return;
            }
        } else if (Build.VERSION.SDK_INT == 33) {
            if (ContextCompat.checkSelfPermission(this, "android.permission.READ_MEDIA_IMAGES") != 0 || ContextCompat.checkSelfPermission(this, "android.permission.READ_MEDIA_VIDEO") != 0) {
                ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_VIDEO"}, 11);
                return;
            }
        } else if (ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") != 0) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 11);
            return;
        }
        videoChooser();
    }

    public void pickImageFromGallery() {
        if (Build.VERSION.SDK_INT >= 34) {
            if (ContextCompat.checkSelfPermission(this, "android.permission.READ_MEDIA_VISUAL_USER_SELECTED") != 0 || ContextCompat.checkSelfPermission(this, "android.permission.READ_MEDIA_IMAGES") != 0) {
                ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_VIDEO", "android.permission.READ_MEDIA_VISUAL_USER_SELECTED"}, 10);
                return;
            }
        } else if (Build.VERSION.SDK_INT >= 33) {
            if (ContextCompat.checkSelfPermission(this, "android.permission.READ_MEDIA_IMAGES") != 0 || ContextCompat.checkSelfPermission(this, "android.permission.READ_MEDIA_VIDEO") != 0) {
                ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_VIDEO"}, 10);
                return;
            }
        } else if (ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") != 0) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 10);
            return;
        }
        imageChooser();
    }

    private void videoChooserForAudio() {
        this.isToCrop = true;
        this.launchVideoExtract.launch(new Intent(this, (Class<?>) GalleryPickerVideo.class));
    }

    private void videoChooser() {
        this.launchVideo.launch(new Intent(this, (Class<?>) GalleryPickerVideo.class));
    }

    private void imageChooser() {
        this.launchImg.launch(new Intent(this, (Class<?>) GalleryPickerOneImage.class));
    }

    /* renamed from: lambda$new$8$hazem-nurmontage-videoquran-EngineActivity, reason: not valid java name */
    /* synthetic */ void m644lambda$new$8$hazemnurmontagevideoquranEngineActivity(ActivityResult activityResult) {
        Bitmap cropTo16x9 = null;
        if (activityResult.getResultCode() != -1 || activityResult.getData() == null || Common.bitmap == null || Common.bitmap.isRecycled()) {
            return;
        }
        Common.bitmap = Bitmap.createScaledBitmap(Common.bitmap, this.blurredImageView.getHeight(), this.blurredImageView.getHeight(), false);
        this.blurredImageView.setBitmapOriginal(Common.bitmap);
        if (this.mTemplate.geTypeResize() == ResizeType.SOCIAL_STORY.ordinal()) {
            cropTo16x9 = BitmapCropper.cropTo9x16(this.blurredImageView.getBitmapOriginal(), this.blurredImageView.getW(), this.blurredImageView.getH());
        } else if (this.mTemplate.geTypeResize() == ResizeType.SQUARE.ordinal()) {
            cropTo16x9 = BitmapCropper.cropTo1x1(this.blurredImageView.getBitmapOriginal(), this.blurredImageView.getW(), this.blurredImageView.getH());
        } else {
            cropTo16x9 = BitmapCropper.cropTo16x9(this.blurredImageView.getBitmapOriginal(), this.blurredImageView.getW(), this.blurredImageView.getH());
        }
        this.blurredImageView.setBitmapBlured(UtilsBitmap.blur(this, cropTo16x9, 20, 1));
        this.blurredImageView.invalidate();
    }

    /* renamed from: lambda$new$9$hazem-nurmontage-videoquran-EngineActivity, reason: not valid java name */
    /* synthetic */ void m645lambda$new$9$hazemnurmontagevideoquranEngineActivity(ActivityResult activityResult) {
        if (activityResult.getResultCode() == -1) {
            Intent data = activityResult.getData();
            if (data == null) {
                return;
            }
            this.mTemplate.setX_square(data.getFloatExtra("x", 0.3f));
            this.mTemplate.setY_square(data.getFloatExtra("y", 0.4f));
            this.mTemplate.setWidth_square(data.getFloatExtra("w", 1.0f));
            this.mTemplate.setHeight_square(data.getFloatExtra("h", 0.5f));
            this.blurredImageView.setBitmapSquare(Common.bitmap);
            this.blurredImageView.setRectSquare(Common.rect);
            this.blurredImageView.invalidate();
        }
        this.isToCrop = false;
    }

    /* renamed from: lambda$new$10$hazem-nurmontage-videoquran-EngineActivity, reason: not valid java name */
    /* synthetic */ void m641lambda$new$10$hazemnurmontagevideoquranEngineActivity(ActivityResult activityResult) {
        Intent data;
        if (activityResult.getResultCode() != -1 || (data = activityResult.getData()) == null || data.getData() == null) {
            return;
        }
        handleImg(data.getData());
    }

    /* renamed from: lambda$new$11$hazem-nurmontage-videoquran-EngineActivity, reason: not valid java name */
    /* synthetic */ void m642lambda$new$11$hazemnurmontagevideoquranEngineActivity(ActivityResult activityResult) {
        Intent data;
        if (activityResult.getResultCode() != -1 || (data = activityResult.getData()) == null || data.getData() == null) {
            return;
        }
        Uri data2 = data.getData();
        try {
            getContentResolver().takePersistableUriPermission(data2, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        handleVideo(data2);
    }

    /* renamed from: lambda$new$12$hazem-nurmontage-videoquran-EngineActivity, reason: not valid java name */
    /* synthetic */ void m643lambda$new$12$hazemnurmontagevideoquranEngineActivity(ActivityResult activityResult) {
        Intent data;
        this.isToCrop = false;
        if (activityResult.getResultCode() != -1 || (data = activityResult.getData()) == null || data.getData() == null) {
            return;
        }
        try {
            Uri data2 = data.getData();
            try {
                getContentResolver().takePersistableUriPermission(data2, 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.83
                @Override // java.lang.Runnable
                public void run() {
                    EngineActivity.this.showProgress();
                }
            });
            this.mTemplate.setUri_upload_extract_audio_video(data2.toString());
            String copyFromUri = AudioUtils.copyFromUri(this, data2, this.mTemplate.getFolder_template());
            this.start_extenstion = 0;
            extractAudioFromVideoRecursive(copyFromUri, 0, false, 0);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addAudioFromVideoWithExtention(String str, final String str2, final int i) {
        try {
            final File file = new File(new File(this.mTemplate.getFolder_template()), System.currentTimeMillis() + "_audio" + str);
            FFmpegKit.executeWithArgumentsAsync(new String[]{"-i", str2, "-vn", "-acodec", "copy", "-y", file.getAbsolutePath()}, new FFmpegSessionCompleteCallback() { // from class: hazem.nurmontage.videoquran.EngineActivity.84
                @Override // com.arthenica.ffmpegkit.FFmpegSessionCompleteCallback
                public void apply(FFmpegSession fFmpegSession) {
                    if (ReturnCode.isSuccess(fFmpegSession.getReturnCode())) {
                        EngineActivity.this.addAudioTemplateHttp(Uri.fromFile(file), i, str2);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void extractAudioFromVideoRecursive(final String str, final int i, final boolean z, final int i2) {
        if (isDestroyed()) {
            return;
        }
        if (i < this.extentions.length) {
            try {
                final File file = new File(new File(this.mTemplate.getFolder_template()), System.currentTimeMillis() + "_audio" + this.extentions[i]);
                FFmpegKit.executeWithArgumentsAsync(new String[]{"-i", str, "-vn", "-acodec", "copy", "-y", file.getAbsolutePath()}, new FFmpegSessionCompleteCallback() { // from class: hazem.nurmontage.videoquran.EngineActivity.85
                    @Override // com.arthenica.ffmpegkit.FFmpegSessionCompleteCallback
                    public void apply(FFmpegSession fFmpegSession) {
                        if (ReturnCode.isSuccess(fFmpegSession.getReturnCode())) {
                            EngineActivity.this.mTemplate.setExtension(EngineActivity.this.extentions[i]);
                            Uri fromFile = Uri.fromFile(file);
                            if (!z) {
                                EngineActivity.this.runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.85.1
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        EngineActivity.this.hideFragment();
                                        EngineActivity.this.hideProgressFragment();
                                    }
                                });
                                EngineActivity.this.addUriAudioToQuranFragment(fromFile, str);
                                return;
                            } else {
                                EngineActivity.this.addAudioTemplateHttp(fromFile, i2, str);
                                return;
                            }
                        }
                        EngineActivity.this.start_extenstion++;
                        EngineActivity engineActivity = EngineActivity.this;
                        engineActivity.extractAudioFromVideoRecursive(str, engineActivity.start_extenstion, z, i);
                    }
                });
                return;
            } catch (Exception e) {
                e.printStackTrace();
                extractAudioFromVideo(str, z);
                return;
            }
        }
        extractAudioFromVideo(str, z);
    }

    private void extractAudioFromVideo(final String str, final boolean z) {
        try {
            final File file = new File(new File(this.mTemplate.getFolder_template()), System.currentTimeMillis() + "_audio.mp3");
            FFmpegKit.executeWithArgumentsAsync(new String[]{"-i", str, "-vn", "-acodec", "copy", "-y", file.getAbsolutePath()}, new FFmpegSessionCompleteCallback() { // from class: hazem.nurmontage.videoquran.EngineActivity.86
                @Override // com.arthenica.ffmpegkit.FFmpegSessionCompleteCallback
                public void apply(FFmpegSession fFmpegSession) {
                    if (fFmpegSession == null) {
                        EngineActivity.this.runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.86.1
                            @Override // java.lang.Runnable
                            public void run() {
                                EngineActivity.this.hideFragment();
                                EngineActivity.this.hideProgressFragment();
                            }
                        });
                        return;
                    }
                    if (fFmpegSession.getReturnCode().isValueSuccess()) {
                        Uri fromFile = Uri.fromFile(file);
                        EngineActivity.this.mTemplate.setExtension(".mp3");
                        if (!z) {
                            EngineActivity.this.addUriAudioToQuranFragment(fromFile, str);
                            return;
                        } else {
                            EngineActivity.this.addAudioTemplateHttp(fromFile, 0, str);
                            return;
                        }
                    }
                    EngineActivity.this.runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.86.2
                        @Override // java.lang.Runnable
                        public void run() {
                            EngineActivity.this.hideProgressFragment();
                            EngineActivity.this.hideFragment();
                            Toast.makeText(EngineActivity.this, EngineActivity.this.mResources.getString(R.string.video_not_have_sound), 0).show();
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.87
                @Override // java.lang.Runnable
                public void run() {
                    EngineActivity.this.hideFragment();
                    EngineActivity.this.hideProgressFragment();
                }
            });
        }
    }

    private void toChoiceBgFromVideo(Uri uri) {
        Intent intent = new Intent(this, (Class<?>) ChoiceBgFromVideoActivity.class);
        intent.setData(uri);
        this.launchChoiceBgActivity.launch(intent);
    }

    private void handleVideo(Uri uri) {
        showProgress();
        clearFFmpeg();
        this.executor.execute(new AnonymousClass88(uri));
    }

    /* renamed from: hazem.nurmontage.videoquran.EngineActivity$88, reason: invalid class name */
    class AnonymousClass88 implements Runnable {
        final /* synthetic */ Uri val$uri;

        AnonymousClass88(Uri uri) {
            this.val$uri = uri;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                EngineActivity engineActivity = EngineActivity.this;
                String copyFromUri = AudioUtils.copyFromUri(engineActivity, this.val$uri, engineActivity.mTemplate.getFolder_template());
                MediaPlayer mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(EngineActivity.this, this.val$uri);
                mediaPlayer.setOnPreparedListener(new AnonymousClass1(copyFromUri));
                mediaPlayer.prepare();
            } catch (Exception e) {
                e.printStackTrace();
                EngineActivity.this.runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.88.2
                    @Override // java.lang.Runnable
                    public void run() {
                        EngineActivity.this.hideProgressFragment();
                    }
                });
            }
        }

        /* renamed from: hazem.nurmontage.videoquran.EngineActivity$88$1, reason: invalid class name */
        class AnonymousClass1 implements MediaPlayer.OnPreparedListener {
            final /* synthetic */ String val$path;

            AnonymousClass1(String str) {
                this.val$path = str;
            }

            @Override // android.media.MediaPlayer.OnPreparedListener
            public void onPrepared(MediaPlayer mediaPlayer) {
                if (mediaPlayer == null) {
                    return;
                }
                final int height = EngineActivity.this.blurredImageView.getHeight();
                EngineActivity.this.mTemplate.setVideoSquare(true);
                EngineActivity.this.blurredImageView.setVideo(true);
                EngineActivity.this.mTemplate.setName_drawable(null);
                EngineActivity.this.mTemplate.setUri_original_upload_video(AnonymousClass88.this.val$uri.toString());
                EngineActivity.this.mTemplate.setUri_media_video(this.val$path);
                EngineActivity.this.mTemplate.setDuration_video_media(mediaPlayer.getDuration() / 1000);
                File fileVideo = FileUtils.getFileVideo(EngineActivity.this.mTemplate.getFolder_template());
                final File file = new File(fileVideo, "frame_%04d.jpg");
                final File file2 = new File(fileVideo, "frame_0001.jpg");
                EngineActivity.this.mTemplate.setFrame_bg(file2.getAbsolutePath());
                EngineActivity.this.endFrame = Math.min(Math.round(EngineActivity.this.trackViewEntity.getMaxTime() / 1000.0f), 3);
                if (EngineActivity.this.endFrame == 0) {
                    EngineActivity.this.endFrame = 3;
                }
                EngineActivity.this.id_ffmpeg.add(Long.valueOf(FFmpegKit.executeWithArgumentsAsync(new String[]{"-i", this.val$path, "-ss", "0", "-t", "" + EngineActivity.this.endFrame, "-r", "25", "-vf", "scale=" + height + ":" + height + ":force_original_aspect_ratio=increase", "-q:v", "0", "-threads", "4", "-an", "-y", file.getAbsolutePath()}, new FFmpegSessionCompleteCallback() { // from class: hazem.nurmontage.videoquran.EngineActivity.88.1.1
                    @Override // com.arthenica.ffmpegkit.FFmpegSessionCompleteCallback
                    public void apply(FFmpegSession fFmpegSession) {
                        EngineActivity.this.changeBitmap(file2.getAbsolutePath());
                        EngineActivity.this.runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.88.1.1.1
                            @Override // java.lang.Runnable
                            public void run() {
                                EngineActivity.this.hideProgressFragment();
                            }
                        });
                        EngineActivity.this.id_ffmpeg.add(Long.valueOf(FFmpegKit.executeWithArgumentsAsync(new String[]{"-i", AnonymousClass1.this.val$path, "-ss", "" + EngineActivity.this.endFrame, "-r", "25", "-vf", "scale=" + height + ":" + height + ":force_original_aspect_ratio=increase", "-start_number", "" + (EngineActivity.this.endFrame * 25), "-q:v", "0", "-threads", "4", "-an", "-y", file.getAbsolutePath()}, new FFmpegSessionCompleteCallback() { // from class: hazem.nurmontage.videoquran.EngineActivity.88.1.1.2
                            @Override // com.arthenica.ffmpegkit.FFmpegSessionCompleteCallback
                            public void apply(FFmpegSession fFmpegSession2) {
                            }
                        }).getSessionId()));
                    }
                }).getSessionId()));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeBitmap(final String str) {
        this.executor.execute(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.89
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.lang.Runnable
            public void run() {
                Bitmap cropTo16x9 = null;
                Bitmap cropToSquareWithRoundCorners;
                Bitmap bitmap = null;
                Rect rect;
                try {
                    int height = EngineActivity.this.blurredImageView.getHeight();
                    Bitmap bitmap2 = (Bitmap) Glide.with((FragmentActivity) EngineActivity.this).asBitmap().load(str).override(height, height).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).submit().get();
                    if (bitmap2 == null) {
                        return;
                    }
                    EngineActivity.this.blurredImageView.setBitmapOriginal(bitmap2);
                    if (EngineActivity.this.mTemplate.getIpad_type() == IpadType.RECT.ordinal() || EngineActivity.this.mTemplate.getIpad_type() == IpadType.ROUND_RECT.ordinal()) {
                        EngineActivity.this.mTemplate.setIpad_type(IpadType.IPAD.ordinal());
                    }
                    if (EngineActivity.this.mTemplate.geTypeResize() == ResizeType.SOCIAL_STORY.ordinal()) {
                        cropTo16x9 = BitmapCropper.cropTo9x16(EngineActivity.this.blurredImageView.getBitmapOriginal(), EngineActivity.this.blurredImageView.getW(), EngineActivity.this.blurredImageView.getH());
                    } else if (EngineActivity.this.mTemplate.geTypeResize() == ResizeType.SQUARE.ordinal()) {
                        cropTo16x9 = BitmapCropper.cropTo1x1(EngineActivity.this.blurredImageView.getBitmapOriginal(), EngineActivity.this.blurredImageView.getW(), EngineActivity.this.blurredImageView.getH());
                    } else {
                        cropTo16x9 = BitmapCropper.cropTo16x9(EngineActivity.this.blurredImageView.getBitmapOriginal(), EngineActivity.this.blurredImageView.getW(), EngineActivity.this.blurredImageView.getH());
                    }
                    EngineActivity.this.blurredImageView.updatePosCanvas(cropTo16x9);
                    EngineActivity.this.blurredImageView.updateIpad(cropTo16x9, EngineActivity.this.mTemplate.getIpad_type(), EngineActivity.this.mTemplate.geTypeResize());
                    if (EngineActivity.this.mTemplate.getIpad_type() != IpadType.BLACK_LAYER.ordinal() && EngineActivity.this.mTemplate.getIpad_type() != IpadType.GRADIENT.ordinal() && EngineActivity.this.mTemplate.getIpad_type() != IpadType.MASK_BRUSH.ordinal() && EngineActivity.this.mTemplate.getIpad_type() != IpadType.BLUE_TYPE.ordinal() && EngineActivity.this.mTemplate.getIpad_type() != IpadType.CASSET_IMG.ordinal()) {
                        if (EngineActivity.this.mTemplate.getIpad_type() == IpadType.CASSET_IMG_BLUR.ordinal()) {
                            EngineActivity.this.blurredImageView.setBitmapBlured(UtilsBitmap.blur(EngineActivity.this, cropTo16x9, 20, 1));
                            EngineActivity.this.blurredImageView.setBitmapSquare(EngineActivity.this.blurredImageView.getBitmapBlured());
                        } else {
                            int min = Math.min(EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth(), EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight());
                            int i = 0;
                            if (EngineActivity.this.mTemplate.getIpad_type() == IpadType.IPAD_NEOMORPHIC.ordinal()) {
                                int width = (int) (EngineActivity.this.blurredImageView.getIpad_rect().width() * 0.6f);
                                float f = min;
                                int round = Math.round(EngineActivity.this.mTemplate.getX_square() * f);
                                int round2 = Math.round(EngineActivity.this.mTemplate.getY_square() * f);
                                int i2 = width + round;
                                if (i2 > EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth()) {
                                    round -= i2 - EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth();
                                    i2 = EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth();
                                }
                                int i3 = width + round2;
                                if (i3 > EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight()) {
                                    round2 -= i3 - EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight();
                                    i3 = EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight();
                                }
                                if (round < 0) {
                                    round = 0;
                                }
                                if (round2 >= 0) {
                                    i = round2;
                                }
                                Rect rect2 = new Rect(round, i, i2, i3);
                                EngineActivity.this.blurredImageView.setRadius_square(width);
                                int width_square = (int) (EngineActivity.this.mTemplate.getWidth_square() * f);
                                int height_square = (int) (f * EngineActivity.this.mTemplate.getHeight_square());
                                Bitmap cropToSquareWithRoundCorners2 = UtilsBitmap.cropToSquareWithRoundCorners(EngineActivity.this.blurredImageView.getBitmapOriginal(), rect2, width, width_square, height_square);
                                rect2.right = rect2.left + width_square;
                                rect2.bottom = rect2.top + height_square;
                                EngineActivity.this.blurredImageView.setRectSquare(rect2);
                                bitmap = cropToSquareWithRoundCorners2;
                                rect = rect2;
                            } else {
                                if (EngineActivity.this.mTemplate.getIpad_type() != IpadType.IPAD.ordinal() && EngineActivity.this.mTemplate.getIpad_type() != IpadType.IPAD_UNBLUR.ordinal() && EngineActivity.this.mTemplate.getIpad_type() != IpadType.IPAD_CLASSIC.ordinal()) {
                                    int width2 = (int) (EngineActivity.this.blurredImageView.getIpad_rect().width() * 1.0f);
                                    int height2 = (int) (cropTo16x9.getHeight() * 0.5355f);
                                    float f2 = min;
                                    int round3 = Math.round(EngineActivity.this.mTemplate.getX_square() * f2);
                                    int round4 = Math.round(EngineActivity.this.mTemplate.getY_square() * f2);
                                    int i4 = width2 + round3;
                                    if (i4 > EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth()) {
                                        round3 -= i4 - EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth();
                                        i4 = EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth();
                                    }
                                    int i5 = height2 + round4;
                                    if (i5 > EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight()) {
                                        round4 -= i5 - EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight();
                                        i5 = EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight();
                                    }
                                    if (round3 < 0) {
                                        round3 = 0;
                                    }
                                    if (round4 < 0) {
                                        round4 = 0;
                                    }
                                    Rect rect3 = new Rect(round3, round4, i4, i5);
                                    int width_square2 = (int) (EngineActivity.this.mTemplate.getWidth_square() * f2);
                                    int height_square2 = (int) (f2 * EngineActivity.this.mTemplate.getHeight_square());
                                    Bitmap cropToSquare = UtilsBitmap.cropToSquare(EngineActivity.this.blurredImageView.getBitmapOriginal(), rect3, width_square2, height_square2);
                                    EngineActivity.this.blurredImageView.setBitmapSquare(cropToSquare);
                                    EngineActivity.this.blurredImageView.setRadius_square(0);
                                    rect3.right = rect3.left + width_square2;
                                    rect3.bottom = rect3.top + height_square2;
                                    EngineActivity.this.blurredImageView.setRectSquare(rect3);
                                    bitmap = cropToSquare;
                                    rect = rect3;
                                }
                                int width3 = (int) (EngineActivity.this.blurredImageView.getIpad_rect().width() * 0.87530595f);
                                int i6 = (int) (width3 * 1.13f);
                                int min2 = Math.min(width3, i6);
                                float f3 = min;
                                int round5 = Math.round(EngineActivity.this.mTemplate.getX_square() * f3);
                                int round6 = Math.round(EngineActivity.this.mTemplate.getY_square() * f3);
                                int i7 = width3 + round5;
                                if (i7 > EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth()) {
                                    round5 -= i7 - EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth();
                                    i7 = EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth();
                                }
                                int i8 = i6 + round6;
                                if (i8 > EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight()) {
                                    round6 -= i8 - EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight();
                                    i8 = EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight();
                                }
                                if (round5 < 0) {
                                    round5 = 0;
                                }
                                if (round6 < 0) {
                                    round6 = 0;
                                }
                                Rect rect4 = new Rect(round5, round6, i7, i8);
                                if (EngineActivity.this.mTemplate.getIpad_type() == IpadType.IPAD_CLASSIC.ordinal()) {
                                    int width_square3 = (int) (EngineActivity.this.mTemplate.getWidth_square() * f3);
                                    int height_square3 = (int) (f3 * EngineActivity.this.mTemplate.getHeight_square());
                                    Bitmap cropToSquare2 = UtilsBitmap.cropToSquare(EngineActivity.this.blurredImageView.getBitmapOriginal(), rect4, width_square3, height_square3);
                                    EngineActivity.this.blurredImageView.setBitmapSquare(cropToSquare2);
                                    EngineActivity.this.blurredImageView.setRadius_square(0);
                                    rect4.right = rect4.left + width_square3;
                                    rect4.bottom = rect4.top + height_square3;
                                    EngineActivity.this.blurredImageView.setRectSquare(rect4);
                                    cropToSquareWithRoundCorners = cropToSquare2;
                                } else {
                                    int i9 = (int) (min2 * 0.10800001f);
                                    EngineActivity.this.blurredImageView.setRadius_square(i9);
                                    int width_square4 = (int) (EngineActivity.this.mTemplate.getWidth_square() * f3);
                                    int height_square4 = (int) (f3 * EngineActivity.this.mTemplate.getHeight_square());
                                    cropToSquareWithRoundCorners = UtilsBitmap.cropToSquareWithRoundCorners(EngineActivity.this.blurredImageView.getBitmapOriginal(), rect4, i9, width_square4, height_square4);
                                    rect4.right = rect4.left + width_square4;
                                    rect4.bottom = rect4.top + height_square4;
                                    EngineActivity.this.blurredImageView.setRectSquare(rect4);
                                }
                                bitmap = cropToSquareWithRoundCorners;
                                rect = rect4;
                            }
                            EngineActivity.this.blurredImageView.setBitmap(UtilsBitmap.blur(EngineActivity.this, cropTo16x9, 20, 1), bitmap, -1, EngineActivity.this.mTemplate.getIpad_type(), EngineActivity.this.mTemplate.geTypeResize(), rect);
                        }
                        EngineActivity.this.mTemplate.setColor_ipad(EngineActivity.this.blurredImageView.colorIpad());
                        EngineActivity.this.runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.89.1
                            @Override // java.lang.Runnable
                            public void run() {
                                EngineActivity.this.blurredImageView.invalidate();
                            }
                        });
                    }
                    if (EngineActivity.this.mTemplate.getIpad_type() == IpadType.GRADIENT.ordinal()) {
                        EngineActivity.this.blurredImageView.setColorIpad(ViewCompat.MEASURED_STATE_MASK);
                    }
                    EngineActivity.this.blurredImageView.setBitmapSquare(cropTo16x9);
                    EngineActivity.this.blurredImageView.setBitmapBlured(UtilsBitmap.blur(EngineActivity.this, cropTo16x9, 20, 1));
                    EngineActivity.this.mTemplate.setColor_ipad(EngineActivity.this.blurredImageView.colorIpad());
                    EngineActivity.this.runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.89.1
                        @Override // java.lang.Runnable
                        public void run() {
                            EngineActivity.this.blurredImageView.invalidate();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void updateSquareBitmap(final String str) {
        if (this.isOnScroll) {
            if (this.mIsPlaying) {
                return;
            }
        } else if (!this.mIsPlaying) {
            return;
        }
        this.executor.execute(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.90
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.lang.Runnable
            public void run() {
                EngineActivity engineActivity;
                Runnable runnable;
                Bitmap bitmap = null;
                Bitmap cropTo16x9 = null;
                try {
                    try {
                        int height = EngineActivity.this.blurredImageView.getHeight();
                        bitmap = (Bitmap) Glide.with((FragmentActivity) EngineActivity.this).asBitmap().load(str).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).override(height, height).submit().get();
                    } catch (Exception e) {
                        e.printStackTrace();
                        engineActivity = EngineActivity.this;
                        runnable = new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.90.1
                            @Override // java.lang.Runnable
                            public void run() {
                                if (!EngineActivity.this.isOnScroll) {
                                    EngineActivity.this.blurredImageView.setDrawingSquareVideo(true);
                                }
                                EngineActivity.this.blurredImageView.invalidate();
                            }
                        };
                    }
                    if (bitmap == null) {
                        return;
                    }
                    if (EngineActivity.this.mTemplate.getIpad_type() != IpadType.BLACK_LAYER.ordinal() && EngineActivity.this.mTemplate.getIpad_type() != IpadType.GRADIENT.ordinal() && EngineActivity.this.mTemplate.getIpad_type() != IpadType.MASK_BRUSH.ordinal() && EngineActivity.this.mTemplate.getIpad_type() != IpadType.BLUE_TYPE.ordinal() && EngineActivity.this.mTemplate.getIpad_type() != IpadType.CASSET_IMG.ordinal() && EngineActivity.this.mTemplate.getIpad_type() != IpadType.CASSET_IMG_BLUR.ordinal()) {
                        if (EngineActivity.this.mTemplate.getIpad_type() != IpadType.IPAD.ordinal() && EngineActivity.this.mTemplate.getIpad_type() != IpadType.IPAD_UNBLUR.ordinal() && EngineActivity.this.mTemplate.getIpad_type() != IpadType.BOTTOM_RECT.ordinal() && EngineActivity.this.mTemplate.getIpad_type() != IpadType.IPAD_CLASSIC.ordinal() && EngineActivity.this.mTemplate.getIpad_type() != IpadType.IPAD_NEOMORPHIC.ordinal()) {
                            int width = (int) (EngineActivity.this.blurredImageView.getIpad_rect().width() * 0.87530595f);
                            int i = (int) (width * 1.13f);
                            int min = (int) (Math.min(width, i) * 0.10800001f);
                            int round = Math.round(EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth() * EngineActivity.this.mTemplate.getX_square());
                            int round2 = Math.round(EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight() * EngineActivity.this.mTemplate.getY_square());
                            int i2 = width + round;
                            if (i2 > EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth()) {
                                round -= i2 - EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth();
                                i2 = EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth();
                            }
                            int i3 = i + round2;
                            if (i3 > EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight()) {
                                round2 -= i3 - EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight();
                                i3 = EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight();
                            }
                            if (round < 0) {
                                round = 0;
                            }
                            if (round2 < 0) {
                                round2 = 0;
                            }
                            Rect rect = new Rect(round, round2, i2, i3);
                            int width2 = (int) (EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth() * EngineActivity.this.mTemplate.getWidth_square());
                            int height2 = (int) (EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight() * EngineActivity.this.mTemplate.getHeight_square());
                            EngineActivity.this.blurredImageView.setBitmapSquare(UtilsBitmap.cropToSquareWithRoundCorners(bitmap, rect, min, width2, height2));
                            rect.right = rect.left + width2;
                            rect.bottom = rect.top + height2;
                            EngineActivity.this.blurredImageView.setRectSquare(rect);
                        } else {
                            EngineActivity.this.blurredImageView.setBitmapSquare(UtilsBitmap.cropToSquareWithRoundCornersPlusScale(bitmap, EngineActivity.this.blurredImageView.getRectSquare(), EngineActivity.this.blurredImageView.getRadius_square(), EngineActivity.this.blurredImageView.getBitmapSquare().getWidth(), EngineActivity.this.blurredImageView.getBitmapSquare().getHeight()));
                        }
                        engineActivity = EngineActivity.this;
                        runnable = new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.90.1
                            @Override // java.lang.Runnable
                            public void run() {
                                if (!EngineActivity.this.isOnScroll) {
                                    EngineActivity.this.blurredImageView.setDrawingSquareVideo(true);
                                }
                                EngineActivity.this.blurredImageView.invalidate();
                            }
                        };
                        engineActivity.runOnUiThread(runnable);
                    }
                    if (EngineActivity.this.mTemplate.geTypeResize() == ResizeType.SOCIAL_STORY.ordinal()) {
                        cropTo16x9 = BitmapCropper.cropTo9x16(bitmap, EngineActivity.this.blurredImageView.getW(), EngineActivity.this.blurredImageView.getH());
                    } else if (EngineActivity.this.mTemplate.geTypeResize() == ResizeType.SQUARE.ordinal()) {
                        cropTo16x9 = BitmapCropper.cropTo1x1(bitmap, EngineActivity.this.blurredImageView.getW(), EngineActivity.this.blurredImageView.getH());
                    } else {
                        cropTo16x9 = BitmapCropper.cropTo16x9(bitmap, EngineActivity.this.blurredImageView.getW(), EngineActivity.this.blurredImageView.getH());
                    }
                    EngineActivity.this.blurredImageView.setBitmapSquare(cropTo16x9);
                    engineActivity = EngineActivity.this;
                    runnable = new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.90.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (!EngineActivity.this.isOnScroll) {
                                EngineActivity.this.blurredImageView.setDrawingSquareVideo(true);
                            }
                            EngineActivity.this.blurredImageView.invalidate();
                        }
                    };
                    engineActivity.runOnUiThread(runnable);
                } finally {
                    EngineActivity.this.runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.90.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (!EngineActivity.this.isOnScroll) {
                                EngineActivity.this.blurredImageView.setDrawingSquareVideo(true);
                            }
                            EngineActivity.this.blurredImageView.invalidate();
                        }
                    });
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Bitmap setupOriginalBitmap(Uri uri) throws IOException {
        int height = this.blurredImageView.getHeight();
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        float min = (float) height / Math.min(bitmapWidth, bitmapHeight);
        return Bitmap.createScaledBitmap(bitmap, Math.round(bitmapWidth * min), Math.round(bitmapHeight * min), true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Bitmap setupOriginalBitmap(Bitmap bitmap, int i) {
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        float min = (float) i / Math.min(bitmapWidth, bitmapHeight);
        return Bitmap.createScaledBitmap(bitmap, Math.round(bitmapWidth * min), Math.round(bitmapHeight * min), true);
    }

    private void handleImg(final Uri uri) {
        showProgress();
        this.executor.execute(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.91
            @Override // java.lang.Runnable
            public void run() {
                EngineActivity engineActivity;
                Runnable runnable;
                Bitmap cropTo16x9 = null;
                Bitmap cropToSquareWithRoundCorners;
                Bitmap bitmap = null;
                Rect rect;
                try {
                    try {
                        EngineActivity.this.getContentResolver().takePersistableUriPermission(uri, 1);
                    } catch (Exception unused) {
                    }
                    try {
                        try {
                            EngineActivity.this.uri_bg = uri.toString();
                            EngineActivity.this.mTemplate.setName_drawable(null);
                            EngineActivity.this.mTemplate.setUri_bg(EngineActivity.this.uri_bg);
                            int i = 0;
                            EngineActivity.this.mTemplate.setVideoSquare(false);
                            EngineActivity.this.blurredImageView.setVideo(false);
                            EngineActivity.this.blurredImageView.setBitmapOriginal(EngineActivity.this.setupOriginalBitmap(uri));
                            if (EngineActivity.this.mTemplate.geTypeResize() == ResizeType.SOCIAL_STORY.ordinal()) {
                                cropTo16x9 = BitmapCropper.cropTo9x16(EngineActivity.this.blurredImageView.getBitmapOriginal(), EngineActivity.this.blurredImageView.getW(), EngineActivity.this.blurredImageView.getH());
                            } else if (EngineActivity.this.mTemplate.geTypeResize() == ResizeType.SQUARE.ordinal()) {
                                cropTo16x9 = BitmapCropper.cropTo1x1(EngineActivity.this.blurredImageView.getBitmapOriginal(), EngineActivity.this.blurredImageView.getW(), EngineActivity.this.blurredImageView.getH());
                            } else {
                                cropTo16x9 = BitmapCropper.cropTo16x9(EngineActivity.this.blurredImageView.getBitmapOriginal(), EngineActivity.this.blurredImageView.getW(), EngineActivity.this.blurredImageView.getH());
                            }
                            EngineActivity.this.blurredImageView.updatePosCanvas(cropTo16x9);
                            EngineActivity.this.blurredImageView.updateIpad(cropTo16x9, EngineActivity.this.mTemplate.getIpad_type(), EngineActivity.this.mTemplate.geTypeResize());
                            int min = Math.min(EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth(), EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight());
                            if (EngineActivity.this.mTemplate.getIpad_type() == IpadType.IPAD_NEOMORPHIC.ordinal()) {
                                int width = (int) (EngineActivity.this.blurredImageView.getIpad_rect().width() * 0.6f);
                                float f = min;
                                int round = Math.round(EngineActivity.this.mTemplate.getX_square() * f);
                                int round2 = Math.round(EngineActivity.this.mTemplate.getY_square() * f);
                                int i2 = width + round;
                                if (i2 > EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth()) {
                                    round -= i2 - EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth();
                                    i2 = EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth();
                                }
                                int i3 = width + round2;
                                if (i3 > EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight()) {
                                    round2 -= i3 - EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight();
                                    i3 = EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight();
                                }
                                if (round < 0) {
                                    round = 0;
                                }
                                if (round2 >= 0) {
                                    i = round2;
                                }
                                Rect rect2 = new Rect(round, i, i2, i3);
                                EngineActivity.this.blurredImageView.setRadius_square(width);
                                int width_square = (int) (EngineActivity.this.mTemplate.getWidth_square() * f);
                                int height_square = (int) (f * EngineActivity.this.mTemplate.getHeight_square());
                                Bitmap cropToSquareWithRoundCorners2 = UtilsBitmap.cropToSquareWithRoundCorners(EngineActivity.this.blurredImageView.getBitmapOriginal(), rect2, width, width_square, height_square);
                                EngineActivity.this.blurredImageView.setBitmapSquare(cropToSquareWithRoundCorners2);
                                rect2.right = rect2.left + width_square;
                                rect2.bottom = rect2.top + height_square;
                                EngineActivity.this.blurredImageView.setRectSquare(rect2);
                                bitmap = cropToSquareWithRoundCorners2;
                                rect = rect2;
                            } else {
                                if (EngineActivity.this.mTemplate.getIpad_type() != IpadType.IPAD.ordinal() && EngineActivity.this.mTemplate.getIpad_type() != IpadType.IPAD_UNBLUR.ordinal() && EngineActivity.this.mTemplate.getIpad_type() != IpadType.IPAD_CLASSIC.ordinal()) {
                                    if (EngineActivity.this.mTemplate.getIpad_type() == IpadType.BOTTOM_RECT.ordinal()) {
                                        int width2 = (int) (EngineActivity.this.blurredImageView.getIpad_rect().width() * 1.0f);
                                        int height = (int) (cropTo16x9.getHeight() * 0.5355f);
                                        float f2 = min;
                                        int round3 = Math.round(EngineActivity.this.mTemplate.getX_square() * f2);
                                        int round4 = Math.round(EngineActivity.this.mTemplate.getY_square() * f2);
                                        int i4 = width2 + round3;
                                        if (i4 > EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth()) {
                                            round3 -= i4 - EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth();
                                            i4 = EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth();
                                        }
                                        int i5 = height + round4;
                                        if (i5 > EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight()) {
                                            round4 -= i5 - EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight();
                                            i5 = EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight();
                                        }
                                        if (round3 < 0) {
                                            round3 = 0;
                                        }
                                        if (round4 < 0) {
                                            round4 = 0;
                                        }
                                        Rect rect3 = new Rect(round3, round4, i4, i5);
                                        int width_square2 = (int) (EngineActivity.this.mTemplate.getWidth_square() * f2);
                                        int height_square2 = (int) (f2 * EngineActivity.this.mTemplate.getHeight_square());
                                        Bitmap cropToSquare = UtilsBitmap.cropToSquare(EngineActivity.this.blurredImageView.getBitmapOriginal(), rect3, width_square2, height_square2);
                                        EngineActivity.this.blurredImageView.setBitmapSquare(cropToSquare);
                                        EngineActivity.this.blurredImageView.setRadius_square(0);
                                        rect3.right = rect3.left + width_square2;
                                        rect3.bottom = rect3.top + height_square2;
                                        EngineActivity.this.blurredImageView.setRectSquare(rect3);
                                        bitmap = cropToSquare;
                                        rect = rect3;
                                    } else {
                                        bitmap = null;
                                        rect = null;
                                    }
                                }
                                int width3 = (int) (EngineActivity.this.blurredImageView.getIpad_rect().width() * 0.87530595f);
                                int i6 = (int) (width3 * 1.13f);
                                int min2 = Math.min(width3, i6);
                                float f3 = min;
                                int round5 = Math.round(EngineActivity.this.mTemplate.getX_square() * f3);
                                int round6 = Math.round(EngineActivity.this.mTemplate.getY_square() * f3);
                                int i7 = width3 + round5;
                                if (i7 > EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth()) {
                                    round5 -= i7 - EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth();
                                    i7 = EngineActivity.this.blurredImageView.getBitmapOriginal().getWidth();
                                }
                                int i8 = i6 + round6;
                                if (i8 > EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight()) {
                                    round6 -= i8 - EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight();
                                    i8 = EngineActivity.this.blurredImageView.getBitmapOriginal().getHeight();
                                }
                                if (round5 < 0) {
                                    round5 = 0;
                                }
                                if (round6 < 0) {
                                    round6 = 0;
                                }
                                Rect rect4 = new Rect(round5, round6, i7, i8);
                                if (EngineActivity.this.mTemplate.getIpad_type() == IpadType.IPAD_CLASSIC.ordinal()) {
                                    int width_square3 = (int) (EngineActivity.this.mTemplate.getWidth_square() * f3);
                                    int height_square3 = (int) (f3 * EngineActivity.this.mTemplate.getHeight_square());
                                    Bitmap cropToSquare2 = UtilsBitmap.cropToSquare(EngineActivity.this.blurredImageView.getBitmapOriginal(), rect4, width_square3, height_square3);
                                    EngineActivity.this.blurredImageView.setBitmapSquare(cropToSquare2);
                                    EngineActivity.this.blurredImageView.setRadius_square(0);
                                    rect4.right = rect4.left + width_square3;
                                    rect4.bottom = rect4.top + height_square3;
                                    EngineActivity.this.blurredImageView.setRectSquare(rect4);
                                    cropToSquareWithRoundCorners = cropToSquare2;
                                } else {
                                    int i9 = (int) (min2 * 0.10800001f);
                                    EngineActivity.this.blurredImageView.setRadius_square(i9);
                                    int width_square4 = (int) (EngineActivity.this.mTemplate.getWidth_square() * f3);
                                    int height_square4 = (int) (f3 * EngineActivity.this.mTemplate.getHeight_square());
                                    cropToSquareWithRoundCorners = UtilsBitmap.cropToSquareWithRoundCorners(EngineActivity.this.blurredImageView.getBitmapOriginal(), rect4, i9, width_square4, height_square4);
                                    EngineActivity.this.blurredImageView.setBitmapSquare(cropToSquareWithRoundCorners);
                                    rect4.right = rect4.left + width_square4;
                                    rect4.bottom = rect4.top + height_square4;
                                    EngineActivity.this.blurredImageView.setRectSquare(rect4);
                                }
                                bitmap = cropToSquareWithRoundCorners;
                                rect = rect4;
                            }
                            if (EngineActivity.this.mTemplate.getIpad_type() == IpadType.GRADIENT.ordinal()) {
                                EngineActivity.this.blurredImageView.setBitmap(UtilsBitmap.blur(EngineActivity.this, cropTo16x9, 20, 1), bitmap, ViewCompat.MEASURED_STATE_MASK, EngineActivity.this.mTemplate.getIpad_type(), EngineActivity.this.mTemplate.geTypeResize(), rect);
                            } else if (EngineActivity.this.mTemplate.getIpad_type() == IpadType.BLUE_TYPE.ordinal()) {
                                if (EngineActivity.this.blurredImageView.getColor_gradient() != null) {
                                    EngineActivity.this.blurredImageView.setBitmap(UtilsBitmap.blur(EngineActivity.this, cropTo16x9, 20, 1), bitmap, EngineActivity.this.blurredImageView.getColor_gradient(), EngineActivity.this.mTemplate.getIpad_type(), EngineActivity.this.mTemplate.geTypeResize(), rect);
                                } else {
                                    EngineActivity.this.blurredImageView.setBitmap(UtilsBitmap.blur(EngineActivity.this, cropTo16x9, 20, 1), bitmap, EngineActivity.this.blurredImageView.getColor_ipad(), EngineActivity.this.mTemplate.getIpad_type(), EngineActivity.this.mTemplate.geTypeResize(), rect);
                                }
                            } else {
                                EngineActivity.this.blurredImageView.setBitmap(UtilsBitmap.blur(EngineActivity.this, cropTo16x9, 20, 1), bitmap, -1, EngineActivity.this.mTemplate.getIpad_type(), EngineActivity.this.mTemplate.geTypeResize(), rect);
                            }
                            EngineActivity.this.blurredImageView.invalidate();
                            engineActivity = EngineActivity.this;
                            runnable = new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.91.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    EngineActivity.this.hideProgressFragment();
                                }
                            };
                        } catch (Exception e) {
                            e.printStackTrace();
                            engineActivity = EngineActivity.this;
                            runnable = new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.91.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    EngineActivity.this.hideProgressFragment();
                                }
                            };
                        }
                        engineActivity.runOnUiThread(runnable);
                    } catch (Exception unused2) {
                    }
                } finally {
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTime() {
        this.trackViewEntity.calculMaxTime();
        long maxTime = this.trackViewEntity.getMaxTime();
        updateViewTime((int) maxTime, (int) this.trackViewEntity.getCurrent_cursur_position());
        if (maxTime > 0 && this.trackViewEntity.getCurrent_cursur_position() <= maxTime) {
            updateTime(this.trackViewEntity.getCurrent_cursur_position());
            TrackEntityView trackEntityView = this.trackViewEntity;
            trackEntityView.setCurrent_cursur_position(trackEntityView.getCurrent_cursur_position());
            this.blurredImageView.setProgress((float) this.trackViewEntity.getCurrent_cursur_position() / maxTime);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTimeToEndAya() {
        this.trackViewEntity.calculMaxTime();
        this.trackViewEntity.translateToEnd();
        long maxTime = this.trackViewEntity.getMaxTime();
        updateViewTime((int) maxTime, (int) this.trackViewEntity.getCurrent_cursur_position());
        if (maxTime > 0 && this.trackViewEntity.getCurrent_cursur_position() <= maxTime) {
            updateTime(this.trackViewEntity.getCurrent_cursur_position());
            TrackEntityView trackEntityView = this.trackViewEntity;
            trackEntityView.setCurrent_cursur_position(trackEntityView.getCurrent_cursur_position());
            this.blurredImageView.setProgress((float) this.trackViewEntity.getCurrent_cursur_position() / maxTime);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void selectSurahName() {
        findViewById(R.id.layout_menu).setVisibility(4);
        SurahNameEntity surahNameEntity = this.blurredImageView.getSurahNameEntity();
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        this.mCurrentFragment = EditS_NameFragment.getInstance(this.iEditSName, this.mResources, surahNameEntity);
        beginTransaction.replace(R.id.m_container, this.mCurrentFragment);
        beginTransaction.commit();
    }

    public void dialogDeleteSelected() {
        try {
            Dialog dialog = new Dialog(this);
            this.dialog = dialog;
            dialog.setCancelable(true);
            this.dialog.requestWindowFeature(1);
            this.dialog.getWindow().setLayout(-1, -2);
            this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            View inflate = LayoutInflater.from(this).inflate(R.layout.layout_dialog, (ViewGroup) null);
            this.dialog.setContentView(inflate);
            inflate.findViewById(R.id.dialog_title).setVisibility(8);
            ((TextCustumFont) inflate.findViewById(R.id.dialog_message)).setText(this.mResources.getString(R.string.are_you_sure_to_delete_this_work));
            ButtonCustumFont buttonCustumFont = (ButtonCustumFont) inflate.findViewById(R.id.dialog_no);
            buttonCustumFont.setText(this.mResources.getString(R.string.delete));
            buttonCustumFont.setTextColor(-1499549);
            buttonCustumFont.setBackgroundResource(R.drawable.btn_dialog_delete);
            buttonCustumFont.setOnClickListener(new AnonymousClass98(buttonCustumFont));
            ButtonCustumFont buttonCustumFont2 = (ButtonCustumFont) inflate.findViewById(R.id.dialog_yes);
            buttonCustumFont2.setText(this.mResources.getString(R.string.no));
            buttonCustumFont2.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.EngineActivity.99
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    EngineActivity.this.dialog.dismiss();
                }
            });
            this.dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: hazem.nurmontage.videoquran.EngineActivity$98, reason: invalid class name */
    class AnonymousClass98 implements View.OnClickListener {
        final /* synthetic */ ButtonCustumFont val$dialog_no;

        AnonymousClass98(ButtonCustumFont buttonCustumFont) {
            this.val$dialog_no = buttonCustumFont;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            this.val$dialog_no.setClickable(false);
            EngineActivity.this.showProgress();
            new Thread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.98.1
                @Override // java.lang.Runnable
                public void run() {
                    EngineActivity.this.trackViewEntity.deleteEntityAllSelect();
                    EngineActivity.this.runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.98.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            EngineActivity.this.trackViewEntity.invalidate();
                            EngineActivity.this.updateTime();
                            EngineActivity.this.hideProgressFragment();
                            EngineActivity.this.iTrimLineCallback.onEmptySelect();
                        }
                    });
                }
            }).start();
            if (EngineActivity.this.dialog != null) {
                EngineActivity.this.dialog.dismiss();
            }
        }
    }

    public void applyffectPlayAuto(String str, EntityAudio entityAudio) {
        showProgressSimple();
        File file = new File(this.mTemplate.getFolder_template(), System.currentTimeMillis() + "_audio_echo.mp3");
        this.id_ffmpeg.add(Long.valueOf(FFmpegKit.executeWithArgumentsAsync(new String[]{"-i", entityAudio.getPath_ffmpeg(), "-af", str, "-y", file.getAbsolutePath()}, new AnonymousClass101(Uri.fromFile(file), entityAudio, file)).getSessionId()));
    }

    /* renamed from: hazem.nurmontage.videoquran.EngineActivity$101, reason: invalid class name */
    class AnonymousClass101 implements FFmpegSessionCompleteCallback {
        final /* synthetic */ EntityAudio val$entityAudio;
        final /* synthetic */ File val$file;
        final /* synthetic */ Uri val$uri;

        AnonymousClass101(Uri uri, EntityAudio entityAudio, File file) {
            this.val$uri = uri;
            this.val$entityAudio = entityAudio;
            this.val$file = file;
        }

        @Override // com.arthenica.ffmpegkit.FFmpegSessionCompleteCallback
        public void apply(FFmpegSession fFmpegSession) {
            if (ReturnCode.isSuccess(fFmpegSession.getReturnCode())) {
                try {
                    EngineActivity.this.mPlayer = new MediaPlayer();
                    EngineActivity.this.mPlayer.setAudioStreamType(3);
                    if (this.val$uri.getScheme() != null && this.val$uri.getScheme().startsWith("http")) {
                        EngineActivity.this.mPlayer.setDataSource(this.val$uri.toString());
                    } else {
                        EngineActivity.this.mPlayer.setDataSource(EngineActivity.this, this.val$uri);
                    }
                    EngineActivity.this.mPlayer.prepareAsync();
                    EngineActivity.this.mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: hazem.nurmontage.videoquran.EngineActivity.101.1
                        @Override // android.media.MediaPlayer.OnPreparedListener
                        public void onPrepared(final MediaPlayer mediaPlayer) {
                            if (AnonymousClass101.this.val$entityAudio.getMediaPlayer() != null && mediaPlayer.getDuration() != AnonymousClass101.this.val$entityAudio.getMediaPlayer().getDuration()) {
                                AnonymousClass101.this.val$entityAudio.setRight(AnonymousClass101.this.val$entityAudio.getRect().left + Math.round(EngineActivity.this.trackViewEntity.getSecond_in_screen() * (mediaPlayer.getDuration() / 1000.0f)));
                                AnonymousClass101.this.val$entityAudio.setEnd(mediaPlayer.getDuration());
                                AnonymousClass101.this.val$entityAudio.setStart(0.0f);
                                AnonymousClass101.this.val$entityAudio.setMax((AnonymousClass101.this.val$entityAudio.getRect().right / AnonymousClass101.this.val$entityAudio.getmScaleFactor()) - ((AnonymousClass101.this.val$entityAudio.getRect().left / AnonymousClass101.this.val$entityAudio.getmScaleFactor()) - AnonymousClass101.this.val$entityAudio.getOffset_left()));
                                EngineActivity.this.trackViewEntity.updateWhenEffect(AnonymousClass101.this.val$entityAudio);
                                EngineActivity.this.runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.101.1.1
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        EngineActivity.this.trackViewEntity.invalidate();
                                        AnonymousClass101.this.val$entityAudio.setMediaPlayer(mediaPlayer);
                                        EngineActivity.this.iEditMediaCallback.startPreview();
                                        EngineActivity.this.hideProgressFragment();
                                    }
                                });
                                return;
                            }
                            EngineActivity.this.runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.101.1.2
                                @Override // java.lang.Runnable
                                public void run() {
                                    AnonymousClass101.this.val$entityAudio.setMediaPlayer(mediaPlayer);
                                    EngineActivity.this.iEditMediaCallback.startPreview();
                                    EngineActivity.this.hideProgressFragment();
                                }
                            });
                        }
                    });
                    this.val$entityAudio.setApplyEffectInPreview(true);
                    this.val$entityAudio.setPath_ffmpeg_effect(this.val$file.getAbsolutePath());
                } catch (Exception e) {
                    e.printStackTrace();
                    EngineActivity.this.runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity.101.2
                        @Override // java.lang.Runnable
                        public void run() {
                            EngineActivity.this.hideProgressFragment();
                        }
                    });
                }
            }
        }
    }

    public void checkSplitEntity() {
        if (EditEntityFragment.instance == null || this.trackViewEntity.getSelectedEntity() == null) {
            return;
        }
        EditEntityFragment.instance.checkSplitEntity(this.trackViewEntity.getSelectedEntity(), -this.trackViewEntity.getCurrentPosition());
    }

    public void checkSplitTrslEntity() {
        if (EditTrslEntityFragment.instance == null || this.trackViewEntity.getSelectedEntity() == null) {
            return;
        }
        EditTrslEntityFragment.instance.checkSplitEntity(this.trackViewEntity.getSelectedEntity(), -this.trackViewEntity.getCurrentPosition());
    }

    public void checkSplitAudio() {
        if (EditMediaFragment.instance == null || !(this.trackViewEntity.getSelectedEntity() instanceof EntityAudio)) {
            return;
        }
        float f = -this.trackViewEntity.getCurrentPosition();
        EditMediaFragment.instance.checkSplit((EntityAudio) this.trackViewEntity.getSelectedEntity(), f);
    }

    private void clearCallback() {
        this.iBismilahEntityCallback = null;
        this.iEditSName = null;
        this.iEditMultipleCallback = null;
        this.iEditMediaCallback = null;
        this.iEditTrstEntityCallback = null;
        this.iEditEntityCallback = null;
        this.iChangeBgCallback = null;
        this.iTrimLineCallback = null;
        this.iIpadEditCallback = null;
        this.iDimensionCallback = null;
        this.searchAyaResult = null;
        this.iFontCallback = null;
        this.launchVideoExtract = null;
        this.launchChoiceBgActivity = null;
        this.launchVideo = null;
        this.launchImg = null;
        this.activityLauncher = null;
        this.onBackPressedCallback = null;
        this.iAddQuran = null;
        this.iAudioCallback = null;
        this.iTransitionCallback = null;
        this.iTransitionBismilahCallback = null;
        this.nameReaderResult = null;
        this.iQuranIconCallback = null;
        this.launchCropActivity = null;
        this.editSurahNameResult = null;
        this.iEdiTextCallback = null;
        this.editTrslResult = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addUpdateAnim(EntityBismilahTimeline entityBismilahTimeline, EntityBismilahTimeline entityBismilahTimeline2) {
        if (entityBismilahTimeline == null) {
            return;
        }
        if (entityBismilahTimeline.getTransition() == null) {
            entityBismilahTimeline.setTransition(new Transition());
        }
        entityBismilahTimeline.getTransition().setOut(entityBismilahTimeline2.getTransition().isOut());
        entityBismilahTimeline.getTransition().setType_out(entityBismilahTimeline2.getTransition().getType_out());
        entityBismilahTimeline.getTransition().setDuration_out(entityBismilahTimeline2.getTransition().getDuration_out());
        entityBismilahTimeline.getTransition().setIn(entityBismilahTimeline2.getTransition().isIn());
        entityBismilahTimeline.getTransition().setType_in(entityBismilahTimeline2.getTransition().getType_in());
        entityBismilahTimeline.getTransition().setDuration_in(entityBismilahTimeline2.getTransition().getDuration_in());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addUpdateAnim(EntityBismilahTimeline entityBismilahTimeline, EntityQuranTimeline entityQuranTimeline) {
        if (entityBismilahTimeline == null) {
            return;
        }
        if (entityBismilahTimeline.getTransition() == null) {
            entityBismilahTimeline.setTransition(new Transition());
        }
        entityBismilahTimeline.getTransition().setOut(entityQuranTimeline.getTransition().isOut());
        entityBismilahTimeline.getTransition().setType_out(entityQuranTimeline.getTransition().getType_out());
        entityBismilahTimeline.getTransition().setDuration_out(entityQuranTimeline.getTransition().getDuration_out());
        entityBismilahTimeline.getTransition().setIn(entityQuranTimeline.getTransition().isIn());
        entityBismilahTimeline.getTransition().setType_in(entityQuranTimeline.getTransition().getType_in());
        entityBismilahTimeline.getTransition().setDuration_in(entityQuranTimeline.getTransition().getDuration_in());
    }

    public void start() {
        if (this.mTemplate.getIpad_type() == IpadType.RECT.ordinal() || this.mTemplate.getIpad_type() == IpadType.ROUND_RECT.ordinal() || this.mTemplate.getIpad_type() == IpadType.CASSET_IMG_BLUR.ordinal() || this.mTemplate.getIpad_type() == IpadType.CASSET.ordinal()) {
            return;
        }
        this.isOnScroll = false;
        SmoothVideoAnimator smoothVideoAnimator = new SmoothVideoAnimator(this.trackViewEntity, this.mTemplate, 25, new SmoothVideoAnimator.FrameUpdateListener() { // from class: hazem.nurmontage.videoquran.EngineActivity.106
            @Override // hazem.nurmontage.videoquran.Utils.SmoothVideoAnimator.FrameUpdateListener
            public void onAnimationEnd() {
            }

            @Override // hazem.nurmontage.videoquran.Utils.SmoothVideoAnimator.FrameUpdateListener
            public void onFrameUpdate(String str) {
                synchronized (EngineActivity.this.frameLock) {
                    EngineActivity.this.pendingFramePath = str;
                    if (!EngineActivity.this.isProcessingFrame) {
                        EngineActivity.this.isProcessingFrame = true;
                        EngineActivity.this.executor.execute(EngineActivity.this.frameProcessorRunnable);
                    }
                }
            }
        });
        this.animator_frame_video = smoothVideoAnimator;
        smoothVideoAnimator.start();
    }

    public void stop() {
        this.blurredImageView.setDrawingSquareVideo(false);
        SmoothVideoAnimator smoothVideoAnimator = this.animator_frame_video;
        if (smoothVideoAnimator != null) {
            smoothVideoAnimator.stop();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateFrame() {
        Template template = this.mTemplate;
        if (template == null || !template.isVideoSquare() || this.mTemplate.getIpad_type() == IpadType.RECT.ordinal() || this.mTemplate.getIpad_type() == IpadType.ROUND_RECT.ordinal() || this.mTemplate.getIpad_type() == IpadType.CASSET_IMG_BLUR.ordinal() || this.mTemplate.getIpad_type() == IpadType.CASSET.ordinal() || this.mIsPlaying) {
            return;
        }
        int max = Math.max(1, Math.round((this.trackViewEntity.getCurrent_cursur_position() / 1000.0f) * 25.0f));
        int min = Math.min(this.mTemplate.getDuration_video_media() * 25, this.trackViewEntity.getDuration() * 25);
        if (max > min) {
            max = ((max - 1) % min) + 1;
        }
        String str = max < 10 ? "frame_000" + max + ".jpg" : max < 100 ? "frame_00" + max + ".jpg" : max < 1000 ? "frame_0" + max + ".jpg" : "frame_" + max + ".jpg";
        this.isOnScroll = true;
        updateSquareBitmap(new File(this.mTemplate.getFolder_template() + "/VideoFrame", str).getAbsolutePath());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public void processFrame(String str) {
        Bitmap cropTo16x9 = null;
        try {
            if (!(this.isOnScroll && this.mIsPlaying) && this.mIsPlaying) {
                int height = this.blurredImageView.getHeight();
                Bitmap bitmap = (Bitmap) Glide.with((FragmentActivity) this).asBitmap().load(str).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).override(height, height).submit().get();
                if (bitmap == null) {
                    return;
                }
                if (this.mTemplate.getIpad_type() != IpadType.BLACK_LAYER.ordinal() && this.mTemplate.getIpad_type() != IpadType.GRADIENT.ordinal() && this.mTemplate.getIpad_type() != IpadType.MASK_BRUSH.ordinal() && this.mTemplate.getIpad_type() != IpadType.BLUE_TYPE.ordinal() && this.mTemplate.getIpad_type() != IpadType.CASSET_IMG.ordinal()) {
                    if (this.mTemplate.getIpad_type() != IpadType.IPAD.ordinal() && this.mTemplate.getIpad_type() != IpadType.IPAD_UNBLUR.ordinal() && this.mTemplate.getIpad_type() != IpadType.BOTTOM_RECT.ordinal() && this.mTemplate.getIpad_type() != IpadType.IPAD_CLASSIC.ordinal() && this.mTemplate.getIpad_type() != IpadType.IPAD_NEOMORPHIC.ordinal()) {
                        int width = (int) (this.blurredImageView.getIpad_rect().width() * 0.87530595f);
                        int i = (int) (width * 1.13f);
                        int min = (int) (Math.min(width, i) * 0.10800001f);
                        int round = Math.round(this.blurredImageView.getBitmapOriginal().getWidth() * this.mTemplate.getX_square());
                        int round2 = Math.round(this.blurredImageView.getBitmapOriginal().getHeight() * this.mTemplate.getY_square());
                        int i2 = width + round;
                        if (i2 > this.blurredImageView.getBitmapOriginal().getWidth()) {
                            round -= i2 - this.blurredImageView.getBitmapOriginal().getWidth();
                            i2 = this.blurredImageView.getBitmapOriginal().getWidth();
                        }
                        int i3 = i + round2;
                        if (i3 > this.blurredImageView.getBitmapOriginal().getHeight()) {
                            round2 -= i3 - this.blurredImageView.getBitmapOriginal().getHeight();
                            i3 = this.blurredImageView.getBitmapOriginal().getHeight();
                        }
                        if (round < 0) {
                            round = 0;
                        }
                        if (round2 < 0) {
                            round2 = 0;
                        }
                        Rect rect = new Rect(round, round2, i2, i3);
                        int width2 = (int) (this.blurredImageView.getBitmapOriginal().getWidth() * this.mTemplate.getWidth_square());
                        int height2 = (int) (this.blurredImageView.getBitmapOriginal().getHeight() * this.mTemplate.getHeight_square());
                        cropTo16x9 = UtilsBitmap.cropToSquareWithRoundCorners(bitmap, rect, min, width2, height2);
                        rect.right = rect.left + width2;
                        rect.bottom = rect.top + height2;
                        this.blurredImageView.setRectSquare(rect);
                    } else {
                        cropTo16x9 = UtilsBitmap.cropToSquareWithRoundCornersPlusScale(bitmap, this.blurredImageView.getRectSquare(), this.blurredImageView.getRadius_square(), this.blurredImageView.getBitmapSquare().getWidth(), this.blurredImageView.getBitmapSquare().getHeight());
                    }
                    final Bitmap finalCrop1 = cropTo16x9;
                    runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity$$ExternalSyntheticLambda7
                        @Override // java.lang.Runnable
                        public final void run() {
                            EngineActivity.this.m646x854d6eda(finalCrop1);
                        }
                    });
                }
                Bitmap cropTo16x9_2 = null;
                if (this.mTemplate.geTypeResize() == ResizeType.SOCIAL_STORY.ordinal()) {
                    cropTo16x9_2 = BitmapCropper.cropTo9x16(bitmap, this.blurredImageView.getW(), this.blurredImageView.getH());
                } else if (this.mTemplate.geTypeResize() == ResizeType.SQUARE.ordinal()) {
                    cropTo16x9_2 = BitmapCropper.cropTo1x1(bitmap, this.blurredImageView.getW(), this.blurredImageView.getH());
                } else {
                    cropTo16x9_2 = BitmapCropper.cropTo16x9(bitmap, this.blurredImageView.getW(), this.blurredImageView.getH());
                }
                final Bitmap finalCrop2 = cropTo16x9_2;
                runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.EngineActivity$$ExternalSyntheticLambda7
                    @Override // java.lang.Runnable
                    public final void run() {
                        EngineActivity.this.m646x854d6eda(finalCrop2);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: lambda$processFrame$13$hazem-nurmontage-videoquran-EngineActivity, reason: not valid java name */
    /* synthetic */ void m646x854d6eda(Bitmap bitmap) {
        this.blurredImageView.setBitmapSquare(bitmap);
        if (!this.isOnScroll) {
            this.blurredImageView.setDrawingSquareVideo(true);
        }
        this.blurredImageView.invalidate();
    }
}

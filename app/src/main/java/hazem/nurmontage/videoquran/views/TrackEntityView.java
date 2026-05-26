package hazem.nurmontage.videoquran.views;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Insets;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Scroller;
import androidx.core.content.ContextCompat;
import androidx.core.view.GestureDetectorCompat;
import hazem.nurmontage.videoquran.R;
import hazem.nurmontage.videoquran.constant.EntityAction;
import hazem.nurmontage.videoquran.entity_timeline.Entity;
import hazem.nurmontage.videoquran.entity_timeline.EntityAudio;
import hazem.nurmontage.videoquran.entity_timeline.EntityBismilahTimeline;
import hazem.nurmontage.videoquran.entity_timeline.EntityQuranTimeline;
import hazem.nurmontage.videoquran.entity_timeline.EntityTrslTimeline;
import hazem.nurmontage.videoquran.model.BismilahEntity;
import hazem.nurmontage.videoquran.model.EntityView;
import hazem.nurmontage.videoquran.model.QuranEntity;
import hazem.nurmontage.videoquran.model.Transition;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Stack;

/* loaded from: classes2.dex */
public class TrackEntityView extends FrameLayout implements View.OnTouchListener {
    private static final float DEFAULT_SCALE = 0.5f;
    private final int CLR_DEFAULT_BG;
    private float DETECT_LEFT_MOVE;
    private float DETECT_RIGHT_MOVE;
    private final float FACTOR_VITESSE;
    private final float MAX_SCALE;
    private final float MIN_SCALE;
    private float SPEED;
    private float TOLERANCE_X;
    private Runnable autoMoveRunnable;
    private Handler autoScrollHandler;
    private Runnable autoScrollRunnable;
    private EntityBismilahTimeline bismilahTimeline;
    private ImageButton btn_redo;
    private ImageButton btn_undo;
    private float canvas_top_Y;
    private float centerX;
    private int clr_btn_audio;
    private int clr_btn_quran;
    private int clr_btn_trsl;
    private final int clr_select;
    private int countMove;
    private float currentEventX;
    private float currentPosition;
    private int current_cursur_position;
    private int duration;
    private float dx;
    private Stack<Pair<Entity, EntityAction>> entityList;
    private List<EntityAudio> entityListAudio;
    private final List<EntityQuranTimeline> entityListQuran;
    private final List<EntityTrslTimeline> entityListTrslQuran;
    private float eventX;
    private float eventY;
    List<Rect> exclusionRects;
    private GestureDetectorCompat gestureDetector;
    private final GestureDetector.SimpleOnGestureListener gestureListener;
    private ITrimLineCallback iTrimLineCallback;
    private boolean isArabic_lang;
    private boolean isAutoMove;
    private boolean isAutoScroll;
    private boolean isCheckLine;
    private boolean isCheckLineCursur;
    private boolean isDetectChange;
    private boolean isFling;
    private boolean isMove;
    private boolean isOnUp;
    private boolean isPassScroll;
    private boolean isPlaying;
    private boolean isProgress;
    private boolean isScaleListener;
    private float lasX;
    private long lastDifference;
    private long lastTime;
    private EntityBismilahTimeline mIsi3adaTimeline;
    private float mScrollY;
    private float m_pos_y_marker;
    private float markerHeight;
    private float maxBottom;
    private int maxTime;
    private float max_trim;
    private ObjectAnimator objectAnimator;
    private boolean onThink;
    private float p;
    private float paddingCursur;
    private Paint paintCursur;
    private final Paint paintItem;
    private Paint paintLineCheck;
    private Paint paintMaker;
    private Paint paint_time;
    private boolean pass;
    private Path pathItemAudio;
    private Path pathItemQuran;
    private Path pathItemTrslQuran;
    private float posY;
    private float radius;
    private RectF rectFItemQuran;
    private RectF rectFItemTrslQuran;
    private RectF rectItemAudio;
    private RectF rectSquareAudio;
    private RectF rectSquareQuran;
    private RectF rectSquareTrslQuran;
    private float scaleFactor;
    private ScaleGestureDetector scaleGestureDetector;
    private float scrolled_with_zoom;
    private Scroller scroller;
    private float second_in_screen;
    private Entity selectedEntity;
    private float signeX;
    private float signeY;
    private float startXLine;
    private float start_y_draw;
    private float target;
    private float timeLineW;
    private long time_start;
    private Stack<Pair<Entity, EntityAction>> undoEntityList;
    private float w_time_item;
    private int width_screen;
    float y;

    public interface ITrimLineCallback {
        void enableRedo(boolean z);

        void enableUndo(boolean z);

        void fadeInAudio(float f);

        void fadeOutAudio(float f);

        void onAddStack(EntityAction entityAction);

        void onDelete(EntityView entityView);

        void onEmptySelect();

        void onMove();

        void onPlayVibration();

        void onSeekPlayer(float f);

        void onSelectEntity(Entity entity, float f);

        void onSelectMultiple(int i);

        void onUp();

        void onUpdate();

        void onUpdatePlayerAudio(EntityAudio entityAudio);

        void onUpdateTime();

        void pause();

        void progress(boolean z);
    }

    private void setupFade(EntityAudio entityAudio) {
    }

    public float getDefaultScale() {
        return 0.5f;
    }

    public void setmIsi3adaTimeline(EntityBismilahTimeline entityBismilahTimeline) {
        this.mIsi3adaTimeline = entityBismilahTimeline;
    }

    public EntityBismilahTimeline getmIsi3adaTimeline() {
        return this.mIsi3adaTimeline;
    }

    public void setBismilahTimeline(EntityBismilahTimeline entityBismilahTimeline) {
        this.bismilahTimeline = entityBismilahTimeline;
    }

    public EntityBismilahTimeline getBismilahTimeline() {
        return this.bismilahTimeline;
    }

    public List<EntityTrslTimeline> getEntityListTrslQuran() {
        return this.entityListTrslQuran;
    }

    public List<EntityQuranTimeline> getEntityListQuran() {
        return this.entityListQuran;
    }

    public List<EntityAudio> getEntityListAudio() {
        return this.entityListAudio;
    }

    public Pair<Integer, EntityAudio> getEntityAudioNotDeleted(int i) {
        while (i < this.entityListAudio.size()) {
            EntityAudio entityAudio = this.entityListAudio.get(i);
            if (entityAudio.visible()) {
                return new Pair<>(Integer.valueOf(i), entityAudio);
            }
            i++;
        }
        return null;
    }

    public void clearAudio() {
        if (this.entityListAudio.isEmpty()) {
            return;
        }
        this.entityListAudio.clear();
        Stack<Pair<Entity, EntityAction>> stack = new Stack<>();
        Iterator<Pair<Entity, EntityAction>> it = this.entityList.iterator();
        while (it.hasNext()) {
            Pair<Entity, EntityAction> next = it.next();
            if (!(next.first instanceof EntityAudio)) {
                stack.push(next);
            }
        }
        this.entityList.clear();
        this.entityList = stack;
    }

    public float getScaleFactor() {
        return this.scaleFactor;
    }

    public void setScaleFactor(float f) {
        this.scaleFactor = f;
        this.scrolled_with_zoom = f * this.currentPosition;
    }

    public TrackEntityView(Context context) {
        super(context);
        this.entityListAudio = new ArrayList();
        this.entityListQuran = new ArrayList();
        this.entityListTrslQuran = new ArrayList();
        this.entityList = new Stack<>();
        this.undoEntityList = new Stack<>();
        this.MAX_SCALE = 8.0f;
        this.MIN_SCALE = 0.09f;
        this.FACTOR_VITESSE = 180.0f;
        this.exclusionRects = new ArrayList();
        this.isPassScroll = true;
        this.onThink = true;
        this.scaleFactor = 0.5f;
        this.paintItem = new Paint(1);
        this.clr_select = -794718;
        this.CLR_DEFAULT_BG = -13421771;
        this.clr_btn_audio = -13421771;
        this.clr_btn_quran = -13421771;
        this.clr_btn_trsl = -13421771;
        this.gestureListener = new GestureDetector.SimpleOnGestureListener() { // from class: hazem.nurmontage.videoquran.views.TrackEntityView.3
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onDown(MotionEvent motionEvent) {
                TrackEntityView.this.pauseScroll();
                PointF pointF = new PointF(motionEvent.getX(), motionEvent.getY());
                TrackEntityView.this.isPassScroll = true;
                if (TrackEntityView.this.selectedEntity != null) {
                    boolean contains = TrackEntityView.this.selectedEntity.contains(pointF);
                    TrackEntityView trackEntityView = TrackEntityView.this;
                    trackEntityView.isPassScroll = !contains && trackEntityView.selectedEntity.getTrim_type() == -1;
                    TrackEntityView.this.selectedEntity.setSelect(true);
                    if (!TrackEntityView.this.isPassScroll && TrackEntityView.this.iTrimLineCallback != null) {
                        if (TrackEntityView.this.selectedEntity.getTrim_type() == 0) {
                            TrackEntityView.this.selectedEntity.setCurrentRect();
                            TrackEntityView.this.selectedEntity.setOnTapTime(Math.round(TrackEntityView.this.selectedEntity.getRect().left / TrackEntityView.this.getSecond_in_screen()) * 1000, TrackEntityView.this.selectedEntity.getRect().left);
                            TrackEntityView.this.iTrimLineCallback.onPlayVibration();
                        } else if (TrackEntityView.this.selectedEntity.getTrim_type() == 1) {
                            TrackEntityView.this.selectedEntity.setCurrentRect();
                            TrackEntityView.this.selectedEntity.setOnTapTime(Math.round(TrackEntityView.this.selectedEntity.getRect().right / TrackEntityView.this.getSecond_in_screen()) * 1000, TrackEntityView.this.selectedEntity.getRect().right);
                            TrackEntityView.this.iTrimLineCallback.onPlayVibration();
                        } else if (contains) {
                            TrackEntityView.this.selectedEntity.setCurrentRect();
                            TrackEntityView.this.iTrimLineCallback.onSelectEntity(TrackEntityView.this.selectedEntity, 0.0f);
                        }
                    }
                }
                return true;
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                if (!TrackEntityView.this.isPlaying()) {
                    if (TrackEntityView.this.handleItemInteraction(motionEvent.getX() + TrackEntityView.this.getPaddingLeft() + (TrackEntityView.this.centerX - (TrackEntityView.this.radius * 0.5f)) + TrackEntityView.this.scrolled_with_zoom, motionEvent.getY())) {
                        return true;
                    }
                } else if (TrackEntityView.this.clr_btn_quran != -13421771 || TrackEntityView.this.clr_btn_audio != -13421771 || TrackEntityView.this.clr_btn_trsl != -13421771) {
                    TrackEntityView trackEntityView = TrackEntityView.this;
                    trackEntityView.clr_btn_trsl = -13421771;
                    trackEntityView.clr_btn_quran = -13421771;
                    trackEntityView.clr_btn_audio = -13421771;
                }
                if (TrackEntityView.this.isPassScroll) {
                    TrackEntityView.this.updateSelectionOnTap(motionEvent);
                }
                return true;
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                if (TrackEntityView.this.isProgress || !TrackEntityView.this.isPassScroll || (TrackEntityView.this.selectedEntity != null && TrackEntityView.this.selectedEntity.getTrim_type() != -1)) {
                    return super.onScroll(motionEvent, motionEvent2, f, f2);
                }
                if (!TrackEntityView.this.isScaleListener && motionEvent2.getEventTime() - motionEvent.getEventTime() >= 107 && TrackEntityView.this.isPass(motionEvent2)) {
                    if (TrackEntityView.this.isPlaying()) {
                        TrackEntityView.this.setPlaying(false);
                    }
                    if (TrackEntityView.this.eventX == 0.0f) {
                        TrackEntityView.this.eventX = motionEvent2.getRawX();
                        TrackEntityView.this.eventY = motionEvent2.getRawY();
                        return true;
                    }
                    float rawX = motionEvent2.getRawX() - TrackEntityView.this.eventX;
                    TrackEntityView.this.currentPosition += rawX / TrackEntityView.this.scaleFactor;
                    if (TrackEntityView.this.currentPosition > 0.0f) {
                        TrackEntityView.this.currentPosition = 0.0f;
                    }
                    TrackEntityView trackEntityView = TrackEntityView.this;
                    trackEntityView.scrolled_with_zoom = trackEntityView.currentPosition * TrackEntityView.this.scaleFactor;
                    if (TrackEntityView.this.iTrimLineCallback != null) {
                        TrackEntityView.this.iTrimLineCallback.onSeekPlayer(TrackEntityView.this.scrolled_with_zoom);
                    }
                    TrackEntityView.this.eventX = motionEvent2.getRawX();
                    TrackEntityView.this.eventY = motionEvent2.getRawY();
                    TrackEntityView.this.invalidate();
                }
                return true;
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                if (TrackEntityView.this.isProgress) {
                    return true;
                }
                if (TrackEntityView.this.isPlaying()) {
                    TrackEntityView.this.setPlaying(false);
                }
                if (motionEvent2.getEventTime() - motionEvent.getEventTime() > 107) {
                    return true;
                }
                if (TrackEntityView.this.eventX == 0.0f) {
                    TrackEntityView.this.eventX = motionEvent.getRawX();
                    TrackEntityView.this.eventY = motionEvent.getRawY();
                }
                float abs = Math.abs(motionEvent2.getRawX() - TrackEntityView.this.eventX);
                float abs2 = Math.abs(motionEvent2.getRawY() - TrackEntityView.this.eventY);
                TrackEntityView.this.eventX = motionEvent2.getRawX();
                TrackEntityView.this.eventY = motionEvent2.getRawY();
                if (motionEvent2.getRawX() > motionEvent.getRawX() ? f < 0.0f : f > 0.0f) {
                    f *= -1.0f;
                }
                if (abs2 > abs * 1.2f) {
                    TrackEntityView.this.target = f2;
                    TrackEntityView.this.flingY();
                } else {
                    TrackEntityView.this.scroller.fling((int) TrackEntityView.this.currentPosition, 0, (int) f, 0, (int) (-TrackEntityView.this.timeLineW), 0, 0, 0);
                    TrackEntityView.this.invalidate();
                }
                return true;
            }
        };
        init();
    }

    public TrackEntityView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.entityListAudio = new ArrayList();
        this.entityListQuran = new ArrayList();
        this.entityListTrslQuran = new ArrayList();
        this.entityList = new Stack<>();
        this.undoEntityList = new Stack<>();
        this.MAX_SCALE = 8.0f;
        this.MIN_SCALE = 0.09f;
        this.FACTOR_VITESSE = 180.0f;
        this.exclusionRects = new ArrayList();
        this.isPassScroll = true;
        this.onThink = true;
        this.scaleFactor = 0.5f;
        this.paintItem = new Paint(1);
        this.clr_select = -794718;
        this.CLR_DEFAULT_BG = -13421771;
        this.clr_btn_audio = -13421771;
        this.clr_btn_quran = -13421771;
        this.clr_btn_trsl = -13421771;
        this.gestureListener = new GestureDetector.SimpleOnGestureListener() { // from class: hazem.nurmontage.videoquran.views.TrackEntityView.3
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onDown(MotionEvent motionEvent) {
                TrackEntityView.this.pauseScroll();
                PointF pointF = new PointF(motionEvent.getX(), motionEvent.getY());
                TrackEntityView.this.isPassScroll = true;
                if (TrackEntityView.this.selectedEntity != null) {
                    boolean contains = TrackEntityView.this.selectedEntity.contains(pointF);
                    TrackEntityView trackEntityView = TrackEntityView.this;
                    trackEntityView.isPassScroll = !contains && trackEntityView.selectedEntity.getTrim_type() == -1;
                    TrackEntityView.this.selectedEntity.setSelect(true);
                    if (!TrackEntityView.this.isPassScroll && TrackEntityView.this.iTrimLineCallback != null) {
                        if (TrackEntityView.this.selectedEntity.getTrim_type() == 0) {
                            TrackEntityView.this.selectedEntity.setCurrentRect();
                            TrackEntityView.this.selectedEntity.setOnTapTime(Math.round(TrackEntityView.this.selectedEntity.getRect().left / TrackEntityView.this.getSecond_in_screen()) * 1000, TrackEntityView.this.selectedEntity.getRect().left);
                            TrackEntityView.this.iTrimLineCallback.onPlayVibration();
                        } else if (TrackEntityView.this.selectedEntity.getTrim_type() == 1) {
                            TrackEntityView.this.selectedEntity.setCurrentRect();
                            TrackEntityView.this.selectedEntity.setOnTapTime(Math.round(TrackEntityView.this.selectedEntity.getRect().right / TrackEntityView.this.getSecond_in_screen()) * 1000, TrackEntityView.this.selectedEntity.getRect().right);
                            TrackEntityView.this.iTrimLineCallback.onPlayVibration();
                        } else if (contains) {
                            TrackEntityView.this.selectedEntity.setCurrentRect();
                            TrackEntityView.this.iTrimLineCallback.onSelectEntity(TrackEntityView.this.selectedEntity, 0.0f);
                        }
                    }
                }
                return true;
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                if (!TrackEntityView.this.isPlaying()) {
                    if (TrackEntityView.this.handleItemInteraction(motionEvent.getX() + TrackEntityView.this.getPaddingLeft() + (TrackEntityView.this.centerX - (TrackEntityView.this.radius * 0.5f)) + TrackEntityView.this.scrolled_with_zoom, motionEvent.getY())) {
                        return true;
                    }
                } else if (TrackEntityView.this.clr_btn_quran != -13421771 || TrackEntityView.this.clr_btn_audio != -13421771 || TrackEntityView.this.clr_btn_trsl != -13421771) {
                    TrackEntityView trackEntityView = TrackEntityView.this;
                    trackEntityView.clr_btn_trsl = -13421771;
                    trackEntityView.clr_btn_quran = -13421771;
                    trackEntityView.clr_btn_audio = -13421771;
                }
                if (TrackEntityView.this.isPassScroll) {
                    TrackEntityView.this.updateSelectionOnTap(motionEvent);
                }
                return true;
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                if (TrackEntityView.this.isProgress || !TrackEntityView.this.isPassScroll || (TrackEntityView.this.selectedEntity != null && TrackEntityView.this.selectedEntity.getTrim_type() != -1)) {
                    return super.onScroll(motionEvent, motionEvent2, f, f2);
                }
                if (!TrackEntityView.this.isScaleListener && motionEvent2.getEventTime() - motionEvent.getEventTime() >= 107 && TrackEntityView.this.isPass(motionEvent2)) {
                    if (TrackEntityView.this.isPlaying()) {
                        TrackEntityView.this.setPlaying(false);
                    }
                    if (TrackEntityView.this.eventX == 0.0f) {
                        TrackEntityView.this.eventX = motionEvent2.getRawX();
                        TrackEntityView.this.eventY = motionEvent2.getRawY();
                        return true;
                    }
                    float rawX = motionEvent2.getRawX() - TrackEntityView.this.eventX;
                    TrackEntityView.this.currentPosition += rawX / TrackEntityView.this.scaleFactor;
                    if (TrackEntityView.this.currentPosition > 0.0f) {
                        TrackEntityView.this.currentPosition = 0.0f;
                    }
                    TrackEntityView trackEntityView = TrackEntityView.this;
                    trackEntityView.scrolled_with_zoom = trackEntityView.currentPosition * TrackEntityView.this.scaleFactor;
                    if (TrackEntityView.this.iTrimLineCallback != null) {
                        TrackEntityView.this.iTrimLineCallback.onSeekPlayer(TrackEntityView.this.scrolled_with_zoom);
                    }
                    TrackEntityView.this.eventX = motionEvent2.getRawX();
                    TrackEntityView.this.eventY = motionEvent2.getRawY();
                    TrackEntityView.this.invalidate();
                }
                return true;
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                if (TrackEntityView.this.isProgress) {
                    return true;
                }
                if (TrackEntityView.this.isPlaying()) {
                    TrackEntityView.this.setPlaying(false);
                }
                if (motionEvent2.getEventTime() - motionEvent.getEventTime() > 107) {
                    return true;
                }
                if (TrackEntityView.this.eventX == 0.0f) {
                    TrackEntityView.this.eventX = motionEvent.getRawX();
                    TrackEntityView.this.eventY = motionEvent.getRawY();
                }
                float abs = Math.abs(motionEvent2.getRawX() - TrackEntityView.this.eventX);
                float abs2 = Math.abs(motionEvent2.getRawY() - TrackEntityView.this.eventY);
                TrackEntityView.this.eventX = motionEvent2.getRawX();
                TrackEntityView.this.eventY = motionEvent2.getRawY();
                if (motionEvent2.getRawX() > motionEvent.getRawX() ? f < 0.0f : f > 0.0f) {
                    f *= -1.0f;
                }
                if (abs2 > abs * 1.2f) {
                    TrackEntityView.this.target = f2;
                    TrackEntityView.this.flingY();
                } else {
                    TrackEntityView.this.scroller.fling((int) TrackEntityView.this.currentPosition, 0, (int) f, 0, (int) (-TrackEntityView.this.timeLineW), 0, 0, 0);
                    TrackEntityView.this.invalidate();
                }
                return true;
            }
        };
        init();
    }

    private void init() {
        this.maxTime = -1;
        this.TOLERANCE_X = 0.95f;
        this.entityListAudio = new ArrayList();
        this.lastTime = 0L;
        this.lastDifference = 0L;
        setWillNotDraw(false);
        initAutoScroll();
        setOnTouchListener(this);
        this.scaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
        this.gestureDetector = new GestureDetectorCompat(getContext(), this.gestureListener);
        this.scroller = new Scroller(getContext());
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            TrackEntityView.this.scaleFactor = Math.max(0.09f, Math.min(TrackEntityView.this.scaleFactor * scaleGestureDetector.getScaleFactor(), 8.0f));
            TrackEntityView trackEntityView = TrackEntityView.this;
            trackEntityView.scrolled_with_zoom = trackEntityView.scaleFactor * TrackEntityView.this.currentPosition;
            TrackEntityView.this.invalidate();
            return true;
        }

        private ScaleListener() {
        }

        @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
        public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
            TrackEntityView.this.isScaleListener = true;
            if (TrackEntityView.this.iTrimLineCallback != null) {
                TrackEntityView.this.iTrimLineCallback.pause();
            }
            return super.onScaleBegin(scaleGestureDetector);
        }

        @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
        public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
            super.onScaleEnd(scaleGestureDetector);
        }
    }

    private void initAutoScroll() {
        this.autoScrollHandler = new Handler();
        this.autoScrollRunnable = new Runnable() { // from class: hazem.nurmontage.videoquran.views.TrackEntityView.1
            @Override // java.lang.Runnable
            public void run() {
                TrackEntityView trackEntityView;
                EntityTrslTimeline previewOrNextEntityTrslQuran;
                TrackEntityView trackEntityView2;
                EntityQuranTimeline previewOrNextEntityQuran;
                TrackEntityView trackEntityView3;
                EntityTrslTimeline previewOrNextEntityTrslQuran2;
                TrackEntityView trackEntityView4;
                EntityQuranTimeline previewOrNextEntityQuran2;
                if (TrackEntityView.this.isAutoScroll) {
                    float currentTimeMillis = (System.currentTimeMillis() - TrackEntityView.this.time_start) / 180.0f;
                    if (TrackEntityView.this.SPEED < 0.0f) {
                        currentTimeMillis *= -1.0f;
                    }
                    float f = currentTimeMillis + TrackEntityView.this.SPEED;
                    if (TrackEntityView.this.selectedEntity == null) {
                        return;
                    }
                    if (TrackEntityView.this.selectedEntity.getTrim_type() == 1) {
                        RectF rect = TrackEntityView.this.selectedEntity.getRect();
                        float f2 = rect.right + f;
                        rect.right = f2;
                        if (rect.right - TrackEntityView.this.selectedEntity.getRect().left <= TrackEntityView.this.max_trim) {
                            TrackEntityView.this.selectedEntity.getRect().right = TrackEntityView.this.selectedEntity.getRect().left + TrackEntityView.this.max_trim;
                            TrackEntityView.this.selectedEntity.setLastRight(TrackEntityView.this.selectedEntity.getRect().right);
                            TrackEntityView.this.invalidate();
                            TrackEntityView.this.autoScrollHandler.removeCallbacks(this);
                            return;
                        }
                        if (TrackEntityView.this.selectedEntity instanceof EntityQuranTimeline) {
                            EntityQuranTimeline entityQuranTimeline = (EntityQuranTimeline) TrackEntityView.this.selectedEntity;
                            if (entityQuranTimeline.getIndex() + 1 < TrackEntityView.this.entityListQuran.size() && (previewOrNextEntityQuran2 = (trackEntityView4 = TrackEntityView.this).getPreviewOrNextEntityQuran(trackEntityView4.entityListQuran, entityQuranTimeline.getIndex() + 1, true)) != null && f2 > previewOrNextEntityQuran2.getRect().left) {
                                TrackEntityView.this.selectedEntity.getRect().right = previewOrNextEntityQuran2.getRect().left;
                                TrackEntityView.this.selectedEntity.setLastRight(TrackEntityView.this.selectedEntity.getRect().right);
                                TrackEntityView.this.autoScrollHandler.removeCallbacks(this);
                                TrackEntityView.this.isAutoScroll = false;
                                TrackEntityView.this.invalidate();
                                return;
                            }
                        }
                        if (TrackEntityView.this.selectedEntity instanceof EntityTrslTimeline) {
                            EntityTrslTimeline entityTrslTimeline = (EntityTrslTimeline) TrackEntityView.this.selectedEntity;
                            if (entityTrslTimeline.getIndex() + 1 < TrackEntityView.this.entityListTrslQuran.size() && (previewOrNextEntityTrslQuran2 = (trackEntityView3 = TrackEntityView.this).getPreviewOrNextEntityTrslQuran(trackEntityView3.entityListTrslQuran, entityTrslTimeline.getIndex() + 1, true)) != null && f2 > previewOrNextEntityTrslQuran2.getRect().left) {
                                TrackEntityView.this.selectedEntity.getRect().right = previewOrNextEntityTrslQuran2.getRect().left;
                                TrackEntityView.this.selectedEntity.setLastRight(TrackEntityView.this.selectedEntity.getRect().right);
                                TrackEntityView.this.autoScrollHandler.removeCallbacks(this);
                                TrackEntityView.this.isAutoScroll = false;
                                TrackEntityView.this.invalidate();
                                return;
                            }
                        }
                        TrackEntityView.this.selectedEntity.getRect().right = f2;
                        TrackEntityView.this.selectedEntity.setLastRight(TrackEntityView.this.selectedEntity.getRect().right);
                    } else if (TrackEntityView.this.selectedEntity.getTrim_type() == 0) {
                        RectF rect2 = TrackEntityView.this.selectedEntity.getRect();
                        float f3 = rect2.left + f;
                        rect2.left = f3;
                        if (f3 < 0.0f) {
                            TrackEntityView.this.selectedEntity.getRect().left = 0.0f;
                            TrackEntityView.this.selectedEntity.setLastLeft(TrackEntityView.this.selectedEntity.getRect().left);
                            TrackEntityView.this.selectedEntity.updateStartTrim();
                            TrackEntityView.this.isAutoScroll = false;
                            TrackEntityView.this.autoScrollHandler.removeCallbacks(this);
                            TrackEntityView.this.invalidate();
                            return;
                        }
                        if (TrackEntityView.this.selectedEntity.getRect().right - f3 <= TrackEntityView.this.max_trim) {
                            float f4 = TrackEntityView.this.selectedEntity.getRect().right - TrackEntityView.this.max_trim;
                            TrackEntityView.this.isAutoScroll = false;
                            TrackEntityView.this.selectedEntity.getRect().left = f4;
                            TrackEntityView.this.selectedEntity.setLastLeft(TrackEntityView.this.selectedEntity.getRect().left);
                            TrackEntityView.this.selectedEntity.updateStartTrim();
                            TrackEntityView.this.autoScrollHandler.removeCallbacks(this);
                            TrackEntityView.this.invalidate();
                            return;
                        }
                        if (TrackEntityView.this.selectedEntity instanceof EntityQuranTimeline) {
                            EntityQuranTimeline entityQuranTimeline2 = (EntityQuranTimeline) TrackEntityView.this.selectedEntity;
                            if (entityQuranTimeline2.getIndex() > 0 && (previewOrNextEntityQuran = (trackEntityView2 = TrackEntityView.this).getPreviewOrNextEntityQuran(trackEntityView2.entityListQuran, entityQuranTimeline2.getIndex() - 1, false)) != null && f3 <= previewOrNextEntityQuran.getRect().right) {
                                TrackEntityView.this.selectedEntity.getRect().left = previewOrNextEntityQuran.getRect().right;
                                TrackEntityView.this.selectedEntity.setLastLeft(TrackEntityView.this.selectedEntity.getRect().left);
                                TrackEntityView.this.selectedEntity.updateStartTrim();
                                TrackEntityView.this.autoScrollHandler.removeCallbacks(this);
                                TrackEntityView.this.isAutoScroll = false;
                                TrackEntityView.this.invalidate();
                                return;
                            }
                        }
                        if (TrackEntityView.this.selectedEntity instanceof EntityTrslTimeline) {
                            EntityTrslTimeline entityTrslTimeline2 = (EntityTrslTimeline) TrackEntityView.this.selectedEntity;
                            if (entityTrslTimeline2.getIndex() > 0 && (previewOrNextEntityTrslQuran = (trackEntityView = TrackEntityView.this).getPreviewOrNextEntityTrslQuran(trackEntityView.entityListTrslQuran, entityTrslTimeline2.getIndex() - 1, false)) != null && f3 <= previewOrNextEntityTrslQuran.getRect().right) {
                                TrackEntityView.this.selectedEntity.getRect().left = previewOrNextEntityTrslQuran.getRect().right;
                                TrackEntityView.this.selectedEntity.setLastLeft(TrackEntityView.this.selectedEntity.getRect().left);
                                TrackEntityView.this.selectedEntity.updateStartTrim();
                                TrackEntityView.this.autoScrollHandler.removeCallbacks(this);
                                TrackEntityView.this.isAutoScroll = false;
                                TrackEntityView.this.invalidate();
                                return;
                            }
                        }
                        TrackEntityView.this.selectedEntity.getRect().left = f3;
                        TrackEntityView.this.selectedEntity.setLastLeft(TrackEntityView.this.selectedEntity.getRect().left);
                        TrackEntityView.this.selectedEntity.updateStartTrim();
                    }
                    TrackEntityView.this.currentPosition -= f / TrackEntityView.this.scaleFactor;
                    if (TrackEntityView.this.currentPosition > 0.0f) {
                        TrackEntityView.this.currentPosition = 0.0f;
                        TrackEntityView trackEntityView5 = TrackEntityView.this;
                        trackEntityView5.scrolled_with_zoom = trackEntityView5.currentPosition * TrackEntityView.this.scaleFactor;
                        TrackEntityView.this.isAutoScroll = false;
                        TrackEntityView.this.autoScrollHandler.removeCallbacks(this);
                        TrackEntityView.this.invalidate();
                        return;
                    }
                    TrackEntityView trackEntityView6 = TrackEntityView.this;
                    trackEntityView6.scrolled_with_zoom = trackEntityView6.currentPosition * TrackEntityView.this.scaleFactor;
                    TrackEntityView.this.invalidate();
                    TrackEntityView.this.autoScrollHandler.postDelayed(this, 100L);
                }
            }
        };
        this.autoMoveRunnable = new Runnable() { // from class: hazem.nurmontage.videoquran.views.TrackEntityView.2
            @Override // java.lang.Runnable
            public void run() {
                TrackEntityView trackEntityView;
                EntityAudio previewOrNextEntityAudio;
                TrackEntityView trackEntityView2;
                EntityAudio previewOrNextEntityAudio2;
                TrackEntityView trackEntityView3;
                EntityQuranTimeline previewOrNextEntityQuran;
                TrackEntityView trackEntityView4;
                EntityTrslTimeline previewOrNextEntityTrslQuran;
                TrackEntityView trackEntityView5;
                EntityQuranTimeline previewOrNextEntityQuran2;
                TrackEntityView trackEntityView6;
                EntityQuranTimeline previewOrNextEntityQuran3;
                if (TrackEntityView.this.isAutoMove) {
                    float currentTimeMillis = (System.currentTimeMillis() - TrackEntityView.this.time_start) / 180.0f;
                    if (TrackEntityView.this.SPEED < 0.0f) {
                        currentTimeMillis *= -1.0f;
                    }
                    float f = currentTimeMillis + TrackEntityView.this.SPEED;
                    float width = TrackEntityView.this.selectedEntity.getRect().width();
                    float f2 = TrackEntityView.this.selectedEntity.getRect().left + f;
                    if (f2 < 0.0f) {
                        f2 = 0.0f;
                    }
                    float f3 = f2 + width;
                    if (TrackEntityView.this.selectedEntity instanceof EntityQuranTimeline) {
                        EntityQuranTimeline entityQuranTimeline = (EntityQuranTimeline) TrackEntityView.this.selectedEntity;
                        if (entityQuranTimeline.getIndex() > 0 && (previewOrNextEntityQuran3 = (trackEntityView6 = TrackEntityView.this).getPreviewOrNextEntityQuran(trackEntityView6.entityListQuran, entityQuranTimeline.getIndex() - 1, false)) != null && f2 <= previewOrNextEntityQuran3.getRect().right) {
                            TrackEntityView.this.selectedEntity.setX(previewOrNextEntityQuran3.getRect().right);
                            TrackEntityView.this.selectedEntity.setRight(previewOrNextEntityQuran3.getRect().right + width);
                            TrackEntityView.this.pass = false;
                            TrackEntityView.this.invalidate();
                            TrackEntityView.this.isAutoMove = false;
                            TrackEntityView.this.autoScrollHandler.removeCallbacks(this);
                            return;
                        }
                        if (entityQuranTimeline.getIndex() + 1 < TrackEntityView.this.getEntityListQuran().size() && (previewOrNextEntityQuran2 = (trackEntityView5 = TrackEntityView.this).getPreviewOrNextEntityQuran(trackEntityView5.entityListQuran, entityQuranTimeline.getIndex() + 1, true)) != null && f3 >= previewOrNextEntityQuran2.getRect().left) {
                            TrackEntityView.this.selectedEntity.setX(previewOrNextEntityQuran2.getRect().left - width);
                            TrackEntityView.this.selectedEntity.setRight(previewOrNextEntityQuran2.getRect().left);
                            TrackEntityView.this.pass = false;
                            TrackEntityView.this.invalidate();
                            TrackEntityView.this.isAutoMove = false;
                            TrackEntityView.this.autoScrollHandler.removeCallbacks(this);
                            return;
                        }
                    }
                    if (TrackEntityView.this.selectedEntity instanceof EntityTrslTimeline) {
                        EntityTrslTimeline entityTrslTimeline = (EntityTrslTimeline) TrackEntityView.this.selectedEntity;
                        if (entityTrslTimeline.getIndex() > 0 && (previewOrNextEntityTrslQuran = (trackEntityView4 = TrackEntityView.this).getPreviewOrNextEntityTrslQuran(trackEntityView4.entityListTrslQuran, entityTrslTimeline.getIndex() - 1, false)) != null && f2 <= previewOrNextEntityTrslQuran.getRect().right) {
                            TrackEntityView.this.selectedEntity.setX(previewOrNextEntityTrslQuran.getRect().right);
                            TrackEntityView.this.selectedEntity.setRight(previewOrNextEntityTrslQuran.getRect().right + width);
                            TrackEntityView.this.pass = false;
                            TrackEntityView.this.invalidate();
                            TrackEntityView.this.isAutoMove = false;
                            TrackEntityView.this.autoScrollHandler.removeCallbacks(this);
                            return;
                        }
                        if (entityTrslTimeline.getIndex() + 1 < TrackEntityView.this.getEntityListQuran().size() && (previewOrNextEntityQuran = (trackEntityView3 = TrackEntityView.this).getPreviewOrNextEntityQuran(trackEntityView3.entityListQuran, entityTrslTimeline.getIndex() + 1, true)) != null && f3 >= previewOrNextEntityQuran.getRect().left) {
                            TrackEntityView.this.selectedEntity.setX(previewOrNextEntityQuran.getRect().left - width);
                            TrackEntityView.this.selectedEntity.setRight(previewOrNextEntityQuran.getRect().left);
                            TrackEntityView.this.pass = false;
                            TrackEntityView.this.invalidate();
                            TrackEntityView.this.isAutoMove = false;
                            TrackEntityView.this.autoScrollHandler.removeCallbacks(this);
                            return;
                        }
                    }
                    if (TrackEntityView.this.selectedEntity instanceof EntityAudio) {
                        EntityAudio entityAudio = (EntityAudio) TrackEntityView.this.selectedEntity;
                        if (entityAudio.getIndex() > 0 && (previewOrNextEntityAudio2 = (trackEntityView2 = TrackEntityView.this).getPreviewOrNextEntityAudio(trackEntityView2.entityListAudio, entityAudio.getIndex() - 1, false)) != null && f2 <= previewOrNextEntityAudio2.getRect().right) {
                            TrackEntityView.this.selectedEntity.setX(previewOrNextEntityAudio2.getRect().right);
                            TrackEntityView.this.selectedEntity.setRight(previewOrNextEntityAudio2.getRect().right + width);
                            TrackEntityView.this.pass = false;
                            TrackEntityView.this.invalidate();
                            TrackEntityView.this.isAutoMove = false;
                            TrackEntityView.this.autoScrollHandler.removeCallbacks(this);
                            return;
                        }
                        if (entityAudio.getIndex() + 1 < TrackEntityView.this.getEntityListAudio().size() && (previewOrNextEntityAudio = (trackEntityView = TrackEntityView.this).getPreviewOrNextEntityAudio(trackEntityView.entityListAudio, entityAudio.getIndex() + 1, true)) != null && f3 >= previewOrNextEntityAudio.getRect().left) {
                            TrackEntityView.this.selectedEntity.setX(previewOrNextEntityAudio.getRect().left - width);
                            TrackEntityView.this.selectedEntity.setRight(previewOrNextEntityAudio.getRect().left);
                            TrackEntityView.this.pass = false;
                            TrackEntityView.this.invalidate();
                            TrackEntityView.this.isAutoMove = false;
                            TrackEntityView.this.autoScrollHandler.removeCallbacks(this);
                            return;
                        }
                    }
                    TrackEntityView.this.currentPosition -= f / TrackEntityView.this.scaleFactor;
                    if (TrackEntityView.this.currentPosition > 0.0f) {
                        TrackEntityView.this.currentPosition = 0.0f;
                        TrackEntityView trackEntityView7 = TrackEntityView.this;
                        trackEntityView7.scrolled_with_zoom = trackEntityView7.currentPosition * TrackEntityView.this.scaleFactor;
                        TrackEntityView.this.isAutoMove = false;
                        TrackEntityView.this.autoScrollHandler.removeCallbacks(this);
                        TrackEntityView.this.invalidate();
                        return;
                    }
                    TrackEntityView trackEntityView8 = TrackEntityView.this;
                    trackEntityView8.scrolled_with_zoom = trackEntityView8.currentPosition * TrackEntityView.this.scaleFactor;
                    TrackEntityView.this.selectedEntity.getRect().left = f2;
                    TrackEntityView.this.selectedEntity.getRect().right = f3;
                    TrackEntityView.this.isMove = true;
                    TrackEntityView.this.invalidate();
                    TrackEntityView.this.autoScrollHandler.postDelayed(this, 100L);
                }
            }
        };
    }

    public TrackEntityView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.entityListAudio = new ArrayList();
        this.entityListQuran = new ArrayList();
        this.entityListTrslQuran = new ArrayList();
        this.entityList = new Stack<>();
        this.undoEntityList = new Stack<>();
        this.MAX_SCALE = 8.0f;
        this.MIN_SCALE = 0.09f;
        this.FACTOR_VITESSE = 180.0f;
        this.exclusionRects = new ArrayList();
        this.isPassScroll = true;
        this.onThink = true;
        this.scaleFactor = 0.5f;
        this.paintItem = new Paint(1);
        this.clr_select = -794718;
        this.CLR_DEFAULT_BG = -13421771;
        this.clr_btn_audio = -13421771;
        this.clr_btn_quran = -13421771;
        this.clr_btn_trsl = -13421771;
        this.gestureListener = new GestureDetector.SimpleOnGestureListener() { // from class: hazem.nurmontage.videoquran.views.TrackEntityView.3
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onDown(MotionEvent motionEvent) {
                TrackEntityView.this.pauseScroll();
                PointF pointF = new PointF(motionEvent.getX(), motionEvent.getY());
                TrackEntityView.this.isPassScroll = true;
                if (TrackEntityView.this.selectedEntity != null) {
                    boolean contains = TrackEntityView.this.selectedEntity.contains(pointF);
                    TrackEntityView trackEntityView = TrackEntityView.this;
                    trackEntityView.isPassScroll = !contains && trackEntityView.selectedEntity.getTrim_type() == -1;
                    TrackEntityView.this.selectedEntity.setSelect(true);
                    if (!TrackEntityView.this.isPassScroll && TrackEntityView.this.iTrimLineCallback != null) {
                        if (TrackEntityView.this.selectedEntity.getTrim_type() == 0) {
                            TrackEntityView.this.selectedEntity.setCurrentRect();
                            TrackEntityView.this.selectedEntity.setOnTapTime(Math.round(TrackEntityView.this.selectedEntity.getRect().left / TrackEntityView.this.getSecond_in_screen()) * 1000, TrackEntityView.this.selectedEntity.getRect().left);
                            TrackEntityView.this.iTrimLineCallback.onPlayVibration();
                        } else if (TrackEntityView.this.selectedEntity.getTrim_type() == 1) {
                            TrackEntityView.this.selectedEntity.setCurrentRect();
                            TrackEntityView.this.selectedEntity.setOnTapTime(Math.round(TrackEntityView.this.selectedEntity.getRect().right / TrackEntityView.this.getSecond_in_screen()) * 1000, TrackEntityView.this.selectedEntity.getRect().right);
                            TrackEntityView.this.iTrimLineCallback.onPlayVibration();
                        } else if (contains) {
                            TrackEntityView.this.selectedEntity.setCurrentRect();
                            TrackEntityView.this.iTrimLineCallback.onSelectEntity(TrackEntityView.this.selectedEntity, 0.0f);
                        }
                    }
                }
                return true;
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                if (!TrackEntityView.this.isPlaying()) {
                    if (TrackEntityView.this.handleItemInteraction(motionEvent.getX() + TrackEntityView.this.getPaddingLeft() + (TrackEntityView.this.centerX - (TrackEntityView.this.radius * 0.5f)) + TrackEntityView.this.scrolled_with_zoom, motionEvent.getY())) {
                        return true;
                    }
                } else if (TrackEntityView.this.clr_btn_quran != -13421771 || TrackEntityView.this.clr_btn_audio != -13421771 || TrackEntityView.this.clr_btn_trsl != -13421771) {
                    TrackEntityView trackEntityView = TrackEntityView.this;
                    trackEntityView.clr_btn_trsl = -13421771;
                    trackEntityView.clr_btn_quran = -13421771;
                    trackEntityView.clr_btn_audio = -13421771;
                }
                if (TrackEntityView.this.isPassScroll) {
                    TrackEntityView.this.updateSelectionOnTap(motionEvent);
                }
                return true;
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                if (TrackEntityView.this.isProgress || !TrackEntityView.this.isPassScroll || (TrackEntityView.this.selectedEntity != null && TrackEntityView.this.selectedEntity.getTrim_type() != -1)) {
                    return super.onScroll(motionEvent, motionEvent2, f, f2);
                }
                if (!TrackEntityView.this.isScaleListener && motionEvent2.getEventTime() - motionEvent.getEventTime() >= 107 && TrackEntityView.this.isPass(motionEvent2)) {
                    if (TrackEntityView.this.isPlaying()) {
                        TrackEntityView.this.setPlaying(false);
                    }
                    if (TrackEntityView.this.eventX == 0.0f) {
                        TrackEntityView.this.eventX = motionEvent2.getRawX();
                        TrackEntityView.this.eventY = motionEvent2.getRawY();
                        return true;
                    }
                    float rawX = motionEvent2.getRawX() - TrackEntityView.this.eventX;
                    TrackEntityView.this.currentPosition += rawX / TrackEntityView.this.scaleFactor;
                    if (TrackEntityView.this.currentPosition > 0.0f) {
                        TrackEntityView.this.currentPosition = 0.0f;
                    }
                    TrackEntityView trackEntityView = TrackEntityView.this;
                    trackEntityView.scrolled_with_zoom = trackEntityView.currentPosition * TrackEntityView.this.scaleFactor;
                    if (TrackEntityView.this.iTrimLineCallback != null) {
                        TrackEntityView.this.iTrimLineCallback.onSeekPlayer(TrackEntityView.this.scrolled_with_zoom);
                    }
                    TrackEntityView.this.eventX = motionEvent2.getRawX();
                    TrackEntityView.this.eventY = motionEvent2.getRawY();
                    TrackEntityView.this.invalidate();
                }
                return true;
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                if (TrackEntityView.this.isProgress) {
                    return true;
                }
                if (TrackEntityView.this.isPlaying()) {
                    TrackEntityView.this.setPlaying(false);
                }
                if (motionEvent2.getEventTime() - motionEvent.getEventTime() > 107) {
                    return true;
                }
                if (TrackEntityView.this.eventX == 0.0f) {
                    TrackEntityView.this.eventX = motionEvent.getRawX();
                    TrackEntityView.this.eventY = motionEvent.getRawY();
                }
                float abs = Math.abs(motionEvent2.getRawX() - TrackEntityView.this.eventX);
                float abs2 = Math.abs(motionEvent2.getRawY() - TrackEntityView.this.eventY);
                TrackEntityView.this.eventX = motionEvent2.getRawX();
                TrackEntityView.this.eventY = motionEvent2.getRawY();
                if (motionEvent2.getRawX() > motionEvent.getRawX() ? f < 0.0f : f > 0.0f) {
                    f *= -1.0f;
                }
                if (abs2 > abs * 1.2f) {
                    TrackEntityView.this.target = f2;
                    TrackEntityView.this.flingY();
                } else {
                    TrackEntityView.this.scroller.fling((int) TrackEntityView.this.currentPosition, 0, (int) f, 0, (int) (-TrackEntityView.this.timeLineW), 0, 0, 0);
                    TrackEntityView.this.invalidate();
                }
                return true;
            }
        };
        init();
    }

    public void init(int i, int i2) {
        if (i <= 0 || i2 <= 0) {
            return;
        }
        float f = i;
        this.SPEED = 0.04f * f;
        Paint paint = new Paint(1);
        this.paint_time = paint;
        paint.setColor(-8355712);
        this.paint_time.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "fonts/ReadexPro_Medium.ttf"));
        this.radius = 0.006f * f;
        this.paint_time.setTextSize(f * 0.023f);
        Paint paint2 = new Paint(1);
        this.paintMaker = paint2;
        paint2.setColor(-1);
        this.paintMaker.setStrokeWidth(this.radius * 0.5f);
        this.markerHeight = this.radius * 3.0f;
        this.m_pos_y_marker = this.paintMaker.getStrokeWidth() * 4.0f;
        float f2 = this.radius;
        this.paddingCursur = 4.0f * f2;
        float f3 = (this.width_screen * 0.5f) - (f2 * 0.5f);
        this.centerX = f3;
        this.DETECT_RIGHT_MOVE = 0.4f * f3;
        this.DETECT_LEFT_MOVE = f3 * 0.45f;
        Paint paint3 = new Paint(1);
        this.paintCursur = paint3;
        paint3.setStrokeWidth(this.radius);
        float strokeWidth = this.paintCursur.getStrokeWidth() * 2.8f;
        Paint paint4 = new Paint(1);
        this.paintLineCheck = paint4;
        paint4.setColor(-16121);
        this.paintLineCheck.setStrokeWidth(this.paintCursur.getStrokeWidth());
        this.paintLineCheck.setPathEffect(new DashPathEffect(new float[]{strokeWidth, strokeWidth}, 0.0f));
        this.w_time_item = this.paint_time.measureText("999") * 0.5f;
    }

    private void drawIconDrawable(Canvas canvas) {
        Paint paint = new Paint(1);
        paint.setColor(-14540254);
        float width = (int) (getWidth() * 0.015f);
        int width2 = (int) (getWidth() * 0.104f);
        float width3 = (int) (getWidth() * 0.03f);
        int start_y_int = (int) this.start_y_draw;
        RectF rectF = new RectF(width3, start_y_int, width3 + width2, start_y_int + width2);
        canvas.drawRoundRect(rectF, width, width, paint);
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.add_audio);
        drawable.setTint(-1052689);
        drawable.setBounds((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom);
        drawable.draw(canvas);
        int bottomY = (int) (rectF.bottom + width3);
        RectF rectF2 = new RectF(rectF.left, bottomY, rectF.right, bottomY + width2);
        canvas.drawRoundRect(rectF2, width, width, paint);
        Drawable drawable2 = ContextCompat.getDrawable(getContext(), R.drawable.add_quran);
        drawable2.setTint(-1052689);
        drawable2.setBounds((int) rectF2.left, (int) rectF2.top, (int) rectF2.right, (int) rectF2.bottom);
        drawable2.draw(canvas);
    }

    public void setMaxTime(int i) {
        this.maxTime = i;
        this.timeLineW = (i * getSecond_in_screen()) / 1000.0f;
    }

    public void setSecond_in_screen(float f, int i, int i2) {
        this.second_in_screen = f;
        this.duration = i;
        this.width_screen = i2;
        float f2 = 0.03f * f;
        this.dx = f2;
        this.TOLERANCE_X = f2;
        this.max_trim = f * 0.2f;
    }

    public float getTextSize() {
        Paint paint = this.paint_time;
        if (paint == null) {
            return 1.0f;
        }
        return paint.getTextSize() * 1.42f;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (z) {
            updateGestureExclusion();
        }
    }

    private void updateGestureExclusion() {
        try {
            if (Build.VERSION.SDK_INT >= 29) {
                this.exclusionRects.clear();
                Insets systemGestureInsets = getRootWindowInsets().getSystemGestureInsets();
                Rect rect = new Rect(0, 0, systemGestureInsets.left, getHeight());
                Rect rect2 = new Rect(getRight() - systemGestureInsets.right, 0, getRight(), getHeight());
                this.exclusionRects.add(rect);
                this.exclusionRects.add(rect2);
                setSystemGestureExclusionRects(this.exclusionRects);
            }
        } catch (Exception unused) {
        }
    }

    @Override // android.view.View
    public void onWindowSystemUiVisibilityChanged(int i) {
        super.onWindowSystemUiVisibilityChanged(i);
        updateGestureExclusion();
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (i2 < 1 || i < 1) {
            return;
        }
        float f = i2;
        this.maxBottom = 0.78f * f;
        this.start_y_draw = 0.18f * f;
        this.canvas_top_Y = 0.1f * f;
        this.posY = 0.05f * f;
        this.p = f * 0.026f;
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x016d, code lost:
    
        r0 = r14.bismilahTimeline;
     */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0218 A[Catch: Exception -> 0x02bc, TryCatch #0 {Exception -> 0x02bc, blocks: (B:2:0x0000, B:4:0x0011, B:6:0x0028, B:7:0x0060, B:9:0x008b, B:10:0x00ae, B:14:0x00c3, B:16:0x00c9, B:18:0x00e0, B:20:0x011e, B:22:0x013c, B:23:0x00e6, B:24:0x0212, B:26:0x0218, B:28:0x022f, B:30:0x026d, B:32:0x028e, B:37:0x0235, B:40:0x016d, B:41:0x0172, B:43:0x0189, B:45:0x01c7, B:47:0x01e5, B:48:0x018f, B:49:0x0170), top: B:1:0x0000 }] */
    /* JADX WARN: Removed duplicated region for block: B:38:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void drawItemBtn(android.graphics.Canvas r15) {
        /*
            Method dump skipped, instructions count: 708
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: hazem.nurmontage.videoquran.views.TrackEntityView.drawItemBtn(android.graphics.Canvas):void");
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        if (this.paint_time == null || this.isProgress) {
            return;
        }
        try {
            mDraw(canvas);
            if (!isPlaying()) {
                drawItemBtn(canvas);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDraw(canvas);
    }

    public void setSecond_in_screen(float f) {
        this.second_in_screen = f;
        this.dx = 0.03f * f;
        this.max_trim = f * 0.2f;
    }

    public float getSecond_in_screenNoScale() {
        return this.second_in_screen;
    }

    public float getSecond_in_screen() {
        return this.second_in_screen * this.scaleFactor;
    }

    public Entity getSelectedEntity() {
        return this.selectedEntity;
    }

    private void mDraw(Canvas canvas) {
        canvas.drawColor(-15658735);
        canvas.save();
        float second_in_screen = getSecond_in_screen();
        canvas.translate(this.centerX + this.scrolled_with_zoom, getPaddingTop());
        int abs = (int) ((Math.abs(this.scrolled_with_zoom) - this.centerX) / second_in_screen);
        int abs2 = ((int) ((Math.abs(this.scrolled_with_zoom) + this.centerX) / second_in_screen)) + 1;
        if (abs < 0) {
            abs = 0;
        }
        drawTimeBar(canvas, abs, abs2, second_in_screen);
        canvas.clipRect(-this.second_in_screen, this.canvas_top_Y, getWidth() - this.scrolled_with_zoom, getHeight() - this.mScrollY);
        canvas.translate(0.0f, this.mScrollY);
        drawAllEntities(canvas, abs, abs2);
        if (this.isCheckLine) {
            float f = this.startXLine;
            canvas.drawLine(f, 0.0f, f, getHeight() - this.mScrollY, this.paintLineCheck);
        }
        canvas.restore();
        if (this.isCheckLineCursur) {
            this.paintCursur.setColor(this.paintLineCheck.getColor());
            canvas.drawLine(this.centerX + this.paintMaker.getStrokeWidth(), this.posY + this.m_pos_y_marker + this.paintMaker.getStrokeWidth(), this.centerX, getHeight(), this.paintCursur);
        } else {
            this.paintCursur.setColor(-1);
            canvas.drawLine(this.centerX + this.paintMaker.getStrokeWidth(), this.posY + this.m_pos_y_marker + this.paintMaker.getStrokeWidth(), this.centerX, getHeight(), this.paintCursur);
        }
    }

    private void drawMarker(Canvas canvas, float f, float f2) {
        float strokeWidth = f + this.paintMaker.getStrokeWidth();
        float f3 = this.posY + this.m_pos_y_marker;
        canvas.drawLine(strokeWidth, f3, strokeWidth, f3 + f2, this.paintMaker);
    }

    public void setiTrimLineCallback(ITrimLineCallback iTrimLineCallback) {
        this.iTrimLineCallback = iTrimLineCallback;
    }

    private void drawTimeBar(Canvas canvas, int i, int i2, float f) {
        String formatTimeLabel;
        float f2 = this.scaleFactor;
        float f3 = 4.0f;
        if (f2 >= 4.0f) {
            f3 = 0.25f;
        } else if (f2 >= 2.0f) {
            f3 = 0.5f;
        } else if (f2 >= 0.8f) {
            f3 = 2.0f;
        } else if (f2 < 0.4f) {
            f3 = f2 > 0.25f ? 6.0f : 8.0f;
        }
        float f4 = i;
        float f5 = f * f3 * 0.2f;
        for (float f6 = f4 - (f4 % f3); f6 <= i2; f6 += f3) {
            float f7 = f6 * f;
            float f8 = f7 / f;
            drawMarker(canvas, f7, this.markerHeight);
            if (this.isArabic_lang) {
                formatTimeLabel = formatTimeLabelArabic(f8);
            } else {
                formatTimeLabel = formatTimeLabel(f8);
            }
            canvas.drawText(formatTimeLabel, f7 - this.w_time_item, this.posY, this.paint_time);
            for (int i3 = 1; i3 <= 4; i3++) {
                drawMarker(canvas, (i3 * f5) + f7, this.markerHeight / 2.0f);
            }
        }
    }

    private String formatTimeLabel(float f) {
        if (f < 60.0f) {
            if (Math.abs(f - 14.0f) < 0.01d) {
                return String.format(Locale.ENGLISH, "14s", new Object[0]);
            }
            if (Math.abs(f - Math.round(f)) < 0.01d) {
                return String.format(Locale.ENGLISH, "%ds", Integer.valueOf((int) f));
            }
            return String.format(Locale.ENGLISH, "%.2fs", Float.valueOf(f));
        }
        int i = (int) (f / 60.0f);
        int round = Math.round(f % 60.0f);
        if (round == 0) {
            return String.format(Locale.ENGLISH, "%dm", Integer.valueOf(i));
        }
        return String.format(Locale.ENGLISH, "%dm %ds", Integer.valueOf(i), Integer.valueOf(round));
    }

    private String formatTimeLabelArabic(float f) {
        if (f < 60.0f) {
            if (Math.abs(f - 14.0f) < 0.01d) {
                return String.format(Locale.ENGLISH, "14ث", new Object[0]);
            }
            if (Math.abs(f - Math.round(f)) < 0.01d) {
                return String.format(Locale.ENGLISH, "%dث", Integer.valueOf((int) f));
            }
            return String.format(Locale.ENGLISH, "%.2fث", Float.valueOf(f));
        }
        int i = (int) (f / 60.0f);
        int round = Math.round(f % 60.0f);
        if (round == 0) {
            return String.format(Locale.ENGLISH, "%dد", Integer.valueOf(i));
        }
        return String.format(Locale.ENGLISH, "%dد %dث", Integer.valueOf(i), Integer.valueOf(round));
    }

    public float getCurrentPosition() {
        return this.scrolled_with_zoom;
    }

    public boolean isPlaying() {
        return this.isPlaying;
    }

    public void setPlaying(boolean z) {
        this.isPlaying = z;
    }

    public boolean isExist(EntityBismilahTimeline entityBismilahTimeline) {
        return entityBismilahTimeline != null && entityBismilahTimeline.visible();
    }

    private float drawBasmala(Canvas canvas, RectF rectF) {
        float f;
        if (isExist(this.bismilahTimeline)) {
            this.bismilahTimeline.updateRect(this.scaleFactor);
            if (this.bismilahTimeline.getEntityView() != null) {
                if (this.bismilahTimeline.getEntityView().isVisible()) {
                    if (Math.round(getCurrentPosition() + this.bismilahTimeline.getRect().left) > 0.0f || Math.round(getCurrentPosition() + this.bismilahTimeline.getRect().right) <= 0.0f) {
                        this.bismilahTimeline.getEntityView().setVisible(false);
                        this.bismilahTimeline.getQuranEntity().endAnimator();
                        this.iTrimLineCallback.onUpdate();
                    } else {
                        setupAnimation(this.bismilahTimeline.getQuranEntity());
                    }
                } else if (Math.round(this.bismilahTimeline.getRect().left + getCurrentPosition()) <= 0.0f && Math.round(this.bismilahTimeline.getRect().right + getCurrentPosition()) > 0.0f) {
                    setupAnimation(this.bismilahTimeline.getQuranEntity());
                    this.bismilahTimeline.getEntityView().setVisible(true);
                    this.iTrimLineCallback.onUpdate();
                }
            }
            this.bismilahTimeline.setY(this.y);
            if (RectF.intersects(rectF, this.bismilahTimeline.getRect())) {
                this.bismilahTimeline.update(canvas);
            }
            f = this.bismilahTimeline.getRect().bottom;
        } else {
            f = 0.0f;
        }
        if (!isExist(this.mIsi3adaTimeline)) {
            return f;
        }
        this.mIsi3adaTimeline.updateRect(this.scaleFactor);
        if (this.mIsi3adaTimeline.getEntityView() != null) {
            if (this.mIsi3adaTimeline.getEntityView().isVisible()) {
                if (Math.round(getCurrentPosition() + this.mIsi3adaTimeline.getRect().left) > 0.0f || Math.round(getCurrentPosition() + this.mIsi3adaTimeline.getRect().right) <= 0.0f) {
                    this.mIsi3adaTimeline.getEntityView().setVisible(false);
                    this.mIsi3adaTimeline.getQuranEntity().endAnimator();
                    this.iTrimLineCallback.onUpdate();
                } else {
                    setupAnimation(this.mIsi3adaTimeline.getQuranEntity());
                }
            } else if (Math.round(this.mIsi3adaTimeline.getRect().left + getCurrentPosition()) <= 0.0f && Math.round(this.mIsi3adaTimeline.getRect().right + getCurrentPosition()) > 0.0f) {
                setupAnimation(this.mIsi3adaTimeline.getQuranEntity());
                this.mIsi3adaTimeline.getEntityView().setVisible(true);
                this.iTrimLineCallback.onUpdate();
            }
        }
        this.mIsi3adaTimeline.setY(this.y);
        if (RectF.intersects(rectF, this.mIsi3adaTimeline.getRect())) {
            this.mIsi3adaTimeline.update(canvas);
        }
        return this.mIsi3adaTimeline.getRect().bottom;
    }

    private void drawAllEntities(Canvas canvas, int i, int i2) {
        float f;
        float f2;
        float f3;
        float f4;
        float f5;
        float f6;
        float f7 = this.start_y_draw;
        this.y = f7;
        float f8 = this.scrolled_with_zoom;
        float f9 = this.centerX;
        float f10 = -f8;
        RectF rectF = new RectF(f10 - f9, (-this.mScrollY) + this.y, f10 + f9, canvas.getHeight() - this.mScrollY);
        for (int i3 = 0; i3 < this.entityListAudio.size(); i3++) {
            EntityAudio entityAudio = this.entityListAudio.get(i3);
            if (entityAudio.visible()) {
                if (this.selectedEntity == entityAudio && !isPlaying()) {
                    this.selectedEntity.setY(this.y);
                    this.selectedEntity.updateRect(this.scaleFactor);
                    f5 = entityAudio.getRect().bottom;
                    f6 = this.p;
                } else {
                    entityAudio.updateRect(this.scaleFactor);
                    if (entityAudio.isVisible()) {
                        if (Math.round(getCurrentPosition() + entityAudio.getRect().left) > 0.0f || Math.round(getCurrentPosition() + entityAudio.getRect().right) <= 0.0f) {
                            entityAudio.setVisible(false);
                        } else {
                            setupFade(entityAudio);
                        }
                    } else if (Math.round(entityAudio.getRect().left + getCurrentPosition()) <= 0.0f && Math.round(entityAudio.getRect().right + getCurrentPosition()) > 0.0f) {
                        setupFade(entityAudio);
                        entityAudio.setVisible(true);
                        this.iTrimLineCallback.onUpdatePlayerAudio(entityAudio);
                    }
                    entityAudio.setY(this.y);
                    if (RectF.intersects(rectF, entityAudio.getRect())) {
                        entityAudio.update(canvas);
                    }
                    f5 = entityAudio.getRect().bottom;
                    f6 = this.p;
                }
                f7 = f5 + f6;
            }
        }
        this.y = f7;
        float max = Math.max(this.start_y_draw, drawBasmala(canvas, rectF) + this.p);
        for (int i4 = 0; i4 < this.entityListQuran.size(); i4++) {
            EntityQuranTimeline entityQuranTimeline = this.entityListQuran.get(i4);
            if (entityQuranTimeline.visible()) {
                if (this.selectedEntity == entityQuranTimeline && !isPlaying()) {
                    entityQuranTimeline.updateRect(this.scaleFactor);
                    this.selectedEntity.setY(this.y);
                    f3 = entityQuranTimeline.getRect().bottom;
                    f4 = this.p;
                } else {
                    entityQuranTimeline.updateRect(this.scaleFactor);
                    if (entityQuranTimeline.getEntityView() != null) {
                        if (entityQuranTimeline.getEntityView().isVisible()) {
                            if (Math.round(getCurrentPosition() + entityQuranTimeline.getRect().left) > 0.0f || Math.round(getCurrentPosition() + entityQuranTimeline.getRect().right) <= 0.0f) {
                                entityQuranTimeline.getEntityView().setVisible(false);
                                entityQuranTimeline.getQuranEntity().endAnimator();
                                this.iTrimLineCallback.onUpdate();
                            } else {
                                setupAnimation(entityQuranTimeline.getQuranEntity());
                            }
                        } else if (Math.round(entityQuranTimeline.getRect().left + getCurrentPosition()) <= 0.0f && Math.round(entityQuranTimeline.getRect().right + getCurrentPosition()) > 0.0f) {
                            setupAnimation(entityQuranTimeline.getQuranEntity());
                            entityQuranTimeline.getEntityView().setVisible(true);
                            this.iTrimLineCallback.onUpdate();
                        }
                    }
                    entityQuranTimeline.setY(this.y);
                    if (RectF.intersects(rectF, entityQuranTimeline.getRect())) {
                        entityQuranTimeline.update(canvas);
                    }
                    f3 = entityQuranTimeline.getRect().bottom;
                    f4 = this.p;
                }
                max = f3 + f4;
            }
        }
        this.y = max;
        for (int i5 = 0; i5 < this.entityListTrslQuran.size(); i5++) {
            EntityTrslTimeline entityTrslTimeline = this.entityListTrslQuran.get(i5);
            if (entityTrslTimeline.visible()) {
                if (this.selectedEntity == entityTrslTimeline && !isPlaying()) {
                    entityTrslTimeline.updateRect(this.scaleFactor);
                    this.selectedEntity.setY(this.y);
                    f = entityTrslTimeline.getRect().bottom;
                    f2 = this.p;
                } else {
                    entityTrslTimeline.updateRect(this.scaleFactor);
                    if (entityTrslTimeline.getEntityView() != null) {
                        if (entityTrslTimeline.getEntityView().isVisible()) {
                            if (Math.round(getCurrentPosition() + entityTrslTimeline.getRect().left) > 0.0f || Math.round(getCurrentPosition() + entityTrslTimeline.getRect().right) <= 0.0f) {
                                entityTrslTimeline.getEntityView().setVisible(false);
                                entityTrslTimeline.getQuranEntity().endAnimator();
                                this.iTrimLineCallback.onUpdate();
                            }
                        } else if (Math.round(entityTrslTimeline.getRect().left + getCurrentPosition()) <= 0.0f && Math.round(entityTrslTimeline.getRect().right + getCurrentPosition()) > 0.0f) {
                            entityTrslTimeline.getEntityView().setVisible(true);
                            this.iTrimLineCallback.onUpdate();
                        }
                    }
                    entityTrslTimeline.setY(this.y);
                    if (RectF.intersects(rectF, entityTrslTimeline.getRect())) {
                        entityTrslTimeline.update(canvas);
                    }
                    f = entityTrslTimeline.getRect().bottom;
                    f2 = this.p;
                }
                max = f + f2;
            }
        }
        this.y = max;
        if (this.selectedEntity == null || isPlaying() || !this.selectedEntity.visible()) {
            return;
        }
        if (RectF.intersects(rectF, this.selectedEntity.getRect())) {
            Entity entity = this.selectedEntity;
            if (entity instanceof EntityAudio) {
                if (Math.round(entity.getRect().left + getCurrentPosition()) <= 0.0f && Math.round(this.selectedEntity.getRect().right + getCurrentPosition()) > 0.0f) {
                    this.selectedEntity.setVisible(true);
                } else {
                    this.selectedEntity.setVisible(false);
                }
            } else if (entity.getEntityView() != null) {
                if (Math.round(this.selectedEntity.getRect().left + getCurrentPosition()) <= 0.0f && Math.round(this.selectedEntity.getRect().right + getCurrentPosition()) > 0.0f) {
                    this.selectedEntity.getEntityView().endAnimator();
                    if (!this.selectedEntity.getEntityView().isVisible()) {
                        this.selectedEntity.getEntityView().setVisible(true);
                        this.iTrimLineCallback.onUpdate();
                    }
                } else if (this.selectedEntity.getEntityView().isVisible()) {
                    this.selectedEntity.getEntityView().endAnimator();
                    this.selectedEntity.getEntityView().setVisible(false);
                    this.iTrimLineCallback.onUpdate();
                }
            }
            this.selectedEntity.update(canvas, i, i2);
            return;
        }
        if (this.selectedEntity.getEntityView() == null || !this.selectedEntity.getEntityView().isVisible()) {
            return;
        }
        this.selectedEntity.getEntityView().endAnimator();
        this.selectedEntity.getEntityView().setVisible(false);
        this.iTrimLineCallback.onUpdate();
    }

    private void setupAnimation(QuranEntity quranEntity) {
        if (!isPlaying() || quranEntity.getEntityQuran().getTransition() == null || quranEntity.isAnimRun()) {
            return;
        }
        Transition transition = quranEntity.getEntityQuran().getTransition();
        float abs = Math.abs(Math.round((getCurrentPosition() / getSecond_in_screen()) * 1000.0f));
        if (transition.isIn()) {
            int round = Math.round((quranEntity.getEntityQuran().getRect().left / getSecond_in_screen()) * 1000.0f);
            int duration_in = (int) (transition.getDuration_in() * 1000.0f);
            float f = round;
            if (abs < (duration_in * 0.5f) + f) {
                quranEntity.runIn(duration_in, false, transition.getType_in());
            } else if (!transition.isOut() && (abs < f || abs >= round + duration_in)) {
                quranEntity.endAnimator();
            }
        }
        if (!quranEntity.isAnimRun() && transition.isOut()) {
            int second_in_screen = (int) ((quranEntity.getEntityQuran().getRect().right / getSecond_in_screen()) * 1000.0f);
            int duration_out = (int) (transition.getDuration_out() * 1000.0f);
            float f2 = second_in_screen - duration_out;
            float f3 = (duration_out * 0.5f) + f2;
            if (abs >= f2 && abs < f3) {
                quranEntity.runOut(duration_out, false, transition.getType_out());
            } else if (abs >= second_in_screen) {
                quranEntity.endAnimator();
            }
        }
    }

    private void setupAnimation(BismilahEntity bismilahEntity) {
        if (!isPlaying() || bismilahEntity.getBismilahTimeline().getTransition() == null || bismilahEntity.isAnimRun()) {
            return;
        }
        Transition transition = bismilahEntity.getBismilahTimeline().getTransition();
        float abs = Math.abs(Math.round((getCurrentPosition() / getSecond_in_screen()) * 1000.0f));
        if (transition.isIn()) {
            int round = Math.round((bismilahEntity.getBismilahTimeline().getRect().left / getSecond_in_screen()) * 1000.0f);
            int duration_in = (int) (transition.getDuration_in() * 1000.0f);
            float f = round;
            if (abs < (duration_in * 0.5f) + f) {
                bismilahEntity.runIn(duration_in, false, transition.getType_in());
            } else if (!transition.isOut() && (abs < f || abs >= round + duration_in)) {
                bismilahEntity.endAnimator();
            }
        }
        if (!bismilahEntity.isAnimRun() && transition.isOut()) {
            int second_in_screen = (int) ((bismilahEntity.getBismilahTimeline().getRect().right / getSecond_in_screen()) * 1000.0f);
            int duration_out = (int) (transition.getDuration_out() * 1000.0f);
            float f2 = second_in_screen - duration_out;
            float f3 = (duration_out * 0.5f) + f2;
            if (abs >= f2 && abs < f3) {
                bismilahEntity.runOut(duration_out, false, transition.getType_out());
            } else if (abs >= second_in_screen) {
                bismilahEntity.endAnimator();
            }
        }
    }

    public void translateFromNow() {
        EntityTrslTimeline previewOrNextEntityTrslQuran;
        EntityQuranTimeline previewOrNextEntityQuran;
        float second_in_screen = getSecond_in_screen() * 0.5f;
        Entity entity = this.selectedEntity;
        if (entity instanceof EntityQuranTimeline) {
            EntityQuranTimeline entityQuranTimeline = (EntityQuranTimeline) entity;
            float abs = Math.abs(getCurrentPosition());
            if (entityQuranTimeline.getRect().right - abs < second_in_screen) {
                return;
            }
            if (entityQuranTimeline.getIndex() - 1 >= 0 && (previewOrNextEntityQuran = getPreviewOrNextEntityQuran(getEntityListQuran(), entityQuranTimeline.getIndex() - 1, false)) != null) {
                if (abs < previewOrNextEntityQuran.getRect().left + getSecond_in_screen()) {
                    abs = getSecond_in_screen() + previewOrNextEntityQuran.getRect().left;
                }
                entityQuranTimeline.setCurrentRect();
                entityQuranTimeline.setX(abs);
                if (entityQuranTimeline.getRect().left < previewOrNextEntityQuran.getRect().right) {
                    previewOrNextEntityQuran.setCurrentRect();
                    previewOrNextEntityQuran.setRight(entityQuranTimeline.getRect().left);
                    previewOrNextEntityQuran.onChange();
                    this.entityList.push(new Pair<>(previewOrNextEntityQuran, EntityAction.MOVE));
                    ITrimLineCallback iTrimLineCallback = this.iTrimLineCallback;
                    if (iTrimLineCallback != null) {
                        iTrimLineCallback.onAddStack(EntityAction.MOVE);
                    }
                }
                invalidate();
                this.selectedEntity.onChange();
                this.entityList.push(new Pair<>(this.selectedEntity, EntityAction.TRIM));
                ITrimLineCallback iTrimLineCallback2 = this.iTrimLineCallback;
                if (iTrimLineCallback2 != null) {
                    iTrimLineCallback2.onAddStack(EntityAction.TRIM);
                    return;
                }
                return;
            }
            if (isExist(this.bismilahTimeline)) {
                if (abs < this.bismilahTimeline.getRect().left + getSecond_in_screen()) {
                    abs = this.bismilahTimeline.getRect().left + getSecond_in_screen();
                }
                entityQuranTimeline.setCurrentRect();
                entityQuranTimeline.setX(abs);
                if (entityQuranTimeline.getRect().left < this.bismilahTimeline.getRect().right) {
                    this.bismilahTimeline.setCurrentRect();
                    this.bismilahTimeline.setRight(entityQuranTimeline.getRect().left);
                    this.bismilahTimeline.onChange();
                    this.entityList.push(new Pair<>(this.bismilahTimeline, EntityAction.MOVE));
                    ITrimLineCallback iTrimLineCallback3 = this.iTrimLineCallback;
                    if (iTrimLineCallback3 != null) {
                        iTrimLineCallback3.onAddStack(EntityAction.MOVE);
                    }
                }
                invalidate();
                this.selectedEntity.onChange();
                this.entityList.push(new Pair<>(this.selectedEntity, EntityAction.TRIM));
                ITrimLineCallback iTrimLineCallback4 = this.iTrimLineCallback;
                if (iTrimLineCallback4 != null) {
                    iTrimLineCallback4.onAddStack(EntityAction.TRIM);
                    return;
                }
                return;
            }
            if (isExist(this.mIsi3adaTimeline)) {
                if (abs < this.mIsi3adaTimeline.getRect().left + getSecond_in_screen()) {
                    abs = this.mIsi3adaTimeline.getRect().left + getSecond_in_screen();
                }
                entityQuranTimeline.setCurrentRect();
                entityQuranTimeline.setX(abs);
                if (entityQuranTimeline.getRect().left < this.mIsi3adaTimeline.getRect().right) {
                    this.mIsi3adaTimeline.setCurrentRect();
                    this.mIsi3adaTimeline.setRight(entityQuranTimeline.getRect().left);
                    this.mIsi3adaTimeline.onChange();
                    this.entityList.push(new Pair<>(this.mIsi3adaTimeline, EntityAction.MOVE));
                    ITrimLineCallback iTrimLineCallback5 = this.iTrimLineCallback;
                    if (iTrimLineCallback5 != null) {
                        iTrimLineCallback5.onAddStack(EntityAction.MOVE);
                    }
                }
                invalidate();
                this.selectedEntity.onChange();
                this.entityList.push(new Pair<>(this.selectedEntity, EntityAction.TRIM));
                ITrimLineCallback iTrimLineCallback6 = this.iTrimLineCallback;
                if (iTrimLineCallback6 != null) {
                    iTrimLineCallback6.onAddStack(EntityAction.TRIM);
                    return;
                }
                return;
            }
            entityQuranTimeline.setCurrentRect();
            this.selectedEntity.setX(abs);
            invalidate();
            this.selectedEntity.onChange();
            this.entityList.push(new Pair<>(this.selectedEntity, EntityAction.TRIM));
            ITrimLineCallback iTrimLineCallback7 = this.iTrimLineCallback;
            if (iTrimLineCallback7 != null) {
                iTrimLineCallback7.onAddStack(EntityAction.TRIM);
                return;
            }
            return;
        }
        if (entity instanceof EntityBismilahTimeline) {
            EntityBismilahTimeline entityBismilahTimeline = (EntityBismilahTimeline) entity;
            float abs2 = Math.abs(getCurrentPosition());
            if (entityBismilahTimeline.getRect().right - abs2 < second_in_screen) {
                return;
            }
            entityBismilahTimeline.setCurrentRect();
            this.selectedEntity.setX(abs2);
            EntityBismilahTimeline entityBismilahTimeline2 = this.bismilahTimeline;
            if (entity == entityBismilahTimeline2 && this.mIsi3adaTimeline != null && entityBismilahTimeline2.getRect().left < this.mIsi3adaTimeline.getRect().right) {
                this.mIsi3adaTimeline.setCurrentRect();
                this.mIsi3adaTimeline.setRight(entityBismilahTimeline.getRect().left);
                this.mIsi3adaTimeline.onChange();
                this.entityList.push(new Pair<>(this.mIsi3adaTimeline, EntityAction.MOVE));
                ITrimLineCallback iTrimLineCallback8 = this.iTrimLineCallback;
                if (iTrimLineCallback8 != null) {
                    iTrimLineCallback8.onAddStack(EntityAction.MOVE);
                }
            }
            invalidate();
            this.selectedEntity.onChange();
            this.entityList.push(new Pair<>(this.selectedEntity, EntityAction.TRIM));
            ITrimLineCallback iTrimLineCallback9 = this.iTrimLineCallback;
            if (iTrimLineCallback9 != null) {
                iTrimLineCallback9.onAddStack(EntityAction.TRIM);
                return;
            }
            return;
        }
        if (entity instanceof EntityTrslTimeline) {
            EntityTrslTimeline entityTrslTimeline = (EntityTrslTimeline) entity;
            float abs3 = Math.abs(getCurrentPosition());
            if (entityTrslTimeline.getRect().right - abs3 < second_in_screen) {
                return;
            }
            if (entityTrslTimeline.getIndex() - 1 >= 0 && (previewOrNextEntityTrslQuran = getPreviewOrNextEntityTrslQuran(getEntityListTrslQuran(), entityTrslTimeline.getIndex() - 1, false)) != null) {
                if (abs3 < previewOrNextEntityTrslQuran.getRect().left + getSecond_in_screen()) {
                    abs3 = getSecond_in_screen() + previewOrNextEntityTrslQuran.getRect().left;
                }
                entityTrslTimeline.setCurrentRect();
                entityTrslTimeline.setX(abs3);
                if (entityTrslTimeline.getRect().left < previewOrNextEntityTrslQuran.getRect().right) {
                    previewOrNextEntityTrslQuran.setCurrentRect();
                    previewOrNextEntityTrslQuran.setRight(entityTrslTimeline.getRect().left);
                    previewOrNextEntityTrslQuran.onChange();
                    this.entityList.push(new Pair<>(previewOrNextEntityTrslQuran, EntityAction.MOVE));
                    ITrimLineCallback iTrimLineCallback10 = this.iTrimLineCallback;
                    if (iTrimLineCallback10 != null) {
                        iTrimLineCallback10.onAddStack(EntityAction.MOVE);
                    }
                }
                invalidate();
                this.selectedEntity.onChange();
                this.entityList.push(new Pair<>(this.selectedEntity, EntityAction.TRIM));
                ITrimLineCallback iTrimLineCallback11 = this.iTrimLineCallback;
                if (iTrimLineCallback11 != null) {
                    iTrimLineCallback11.onAddStack(EntityAction.TRIM);
                    return;
                }
                return;
            }
            entityTrslTimeline.setCurrentRect();
            this.selectedEntity.setX(abs3);
            invalidate();
            this.selectedEntity.onChange();
            this.entityList.push(new Pair<>(this.selectedEntity, EntityAction.TRIM));
            ITrimLineCallback iTrimLineCallback12 = this.iTrimLineCallback;
            if (iTrimLineCallback12 != null) {
                iTrimLineCallback12.onAddStack(EntityAction.TRIM);
            }
        }
    }

    public void translateToRight(boolean z) {
        EntityBismilahTimeline entityBismilahTimeline = z ? this.mIsi3adaTimeline : this.bismilahTimeline;
        float f = entityBismilahTimeline.getRect().right;
        if (z && isExist(this.bismilahTimeline) && f >= this.bismilahTimeline.getRect().left) {
            float width = this.bismilahTimeline.getRect().width() + f;
            float f2 = f - this.bismilahTimeline.getRect().left;
            this.bismilahTimeline.setCurrentRect();
            this.bismilahTimeline.setX(f);
            this.bismilahTimeline.setRight(width);
            for (int index = this.bismilahTimeline.getIndex(); index < this.entityListQuran.size(); index++) {
                EntityQuranTimeline entityQuranTimeline = this.entityListQuran.get(index);
                if (entityQuranTimeline.visible()) {
                    float f3 = entityQuranTimeline.getRect().left + f2;
                    float width2 = entityQuranTimeline.getRect().width() + f3;
                    entityQuranTimeline.setCurrentRect();
                    entityQuranTimeline.setX(f3);
                    entityQuranTimeline.setRight(width2);
                }
            }
            return;
        }
        EntityQuranTimeline previewOrNextEntityQuran = getPreviewOrNextEntityQuran(this.entityListQuran, entityBismilahTimeline.getIndex(), true);
        if (previewOrNextEntityQuran == null || f < previewOrNextEntityQuran.getRect().left) {
            return;
        }
        float f4 = f - previewOrNextEntityQuran.getRect().left;
        float width3 = previewOrNextEntityQuran.getRect().width() + f;
        previewOrNextEntityQuran.setCurrentRect();
        previewOrNextEntityQuran.setX(f);
        previewOrNextEntityQuran.setRight(width3);
        for (int index2 = entityBismilahTimeline.getIndex() + 1; index2 < this.entityListQuran.size(); index2++) {
            EntityQuranTimeline entityQuranTimeline2 = this.entityListQuran.get(index2);
            if (entityQuranTimeline2.visible()) {
                float f5 = entityQuranTimeline2.getRect().left + f4;
                float width4 = entityQuranTimeline2.getRect().width() + f5;
                entityQuranTimeline2.setCurrentRect();
                entityQuranTimeline2.setX(f5);
                entityQuranTimeline2.setRight(width4);
            }
        }
    }

    public void translateToRight() {
        EntityBismilahTimeline entityBismilahTimeline = this.bismilahTimeline;
        float f = entityBismilahTimeline.getRect().right;
        EntityQuranTimeline previewOrNextEntityQuran = getPreviewOrNextEntityQuran(this.entityListQuran, entityBismilahTimeline.getIndex(), true);
        if (previewOrNextEntityQuran == null || f < previewOrNextEntityQuran.getRect().left) {
            return;
        }
        float f2 = f - previewOrNextEntityQuran.getRect().left;
        float width = previewOrNextEntityQuran.getRect().width() + f;
        previewOrNextEntityQuran.setCurrentRect();
        previewOrNextEntityQuran.setX(f);
        previewOrNextEntityQuran.setRight(width);
        for (int index = entityBismilahTimeline.getIndex() + 1; index < this.entityListQuran.size(); index++) {
            EntityQuranTimeline entityQuranTimeline = this.entityListQuran.get(index);
            if (entityQuranTimeline.visible()) {
                float f3 = entityQuranTimeline.getRect().left + f2;
                float width2 = entityQuranTimeline.getRect().width() + f3;
                entityQuranTimeline.setCurrentRect();
                entityQuranTimeline.setX(f3);
                entityQuranTimeline.setRight(width2);
            }
        }
    }

    public void translateFromStart() {
        EntityTrslTimeline previewOrNextEntityTrslQuran;
        EntityQuranTimeline previewOrNextEntityQuran;
        Entity entity = this.selectedEntity;
        if (entity instanceof EntityQuranTimeline) {
            EntityQuranTimeline entityQuranTimeline = (EntityQuranTimeline) entity;
            if (entityQuranTimeline.getIndex() - 1 >= 0 && (previewOrNextEntityQuran = getPreviewOrNextEntityQuran(this.entityListQuran, entityQuranTimeline.getIndex() - 1, false)) != null) {
                entityQuranTimeline.setCurrentRect();
                entityQuranTimeline.setX(previewOrNextEntityQuran.getRect().right);
                invalidate();
                this.selectedEntity.onChange();
                this.entityList.push(new Pair<>(this.selectedEntity, EntityAction.TRIM));
                ITrimLineCallback iTrimLineCallback = this.iTrimLineCallback;
                if (iTrimLineCallback != null) {
                    iTrimLineCallback.onAddStack(EntityAction.TRIM);
                    return;
                }
                return;
            }
            if (isExist(this.bismilahTimeline)) {
                entityQuranTimeline.setCurrentRect();
                entityQuranTimeline.setX(this.bismilahTimeline.getRect().right);
                invalidate();
                this.selectedEntity.onChange();
                this.entityList.push(new Pair<>(this.selectedEntity, EntityAction.TRIM));
                ITrimLineCallback iTrimLineCallback2 = this.iTrimLineCallback;
                if (iTrimLineCallback2 != null) {
                    iTrimLineCallback2.onAddStack(EntityAction.TRIM);
                    return;
                }
                return;
            }
            if (isExist(this.mIsi3adaTimeline)) {
                entityQuranTimeline.setCurrentRect();
                entityQuranTimeline.setX(this.mIsi3adaTimeline.getRect().right);
                invalidate();
                this.selectedEntity.onChange();
                this.entityList.push(new Pair<>(this.selectedEntity, EntityAction.TRIM));
                ITrimLineCallback iTrimLineCallback3 = this.iTrimLineCallback;
                if (iTrimLineCallback3 != null) {
                    iTrimLineCallback3.onAddStack(EntityAction.TRIM);
                    return;
                }
                return;
            }
            entityQuranTimeline.setCurrentRect();
            this.selectedEntity.setX(0.0f);
            invalidate();
            this.selectedEntity.onChange();
            this.entityList.push(new Pair<>(this.selectedEntity, EntityAction.TRIM));
            ITrimLineCallback iTrimLineCallback4 = this.iTrimLineCallback;
            if (iTrimLineCallback4 != null) {
                iTrimLineCallback4.onAddStack(EntityAction.TRIM);
                return;
            }
            return;
        }
        if (entity instanceof EntityBismilahTimeline) {
            EntityBismilahTimeline entityBismilahTimeline = (EntityBismilahTimeline) entity;
            if (entity == this.bismilahTimeline && isExist(this.mIsi3adaTimeline)) {
                entityBismilahTimeline.setCurrentRect();
                entityBismilahTimeline.setX(this.mIsi3adaTimeline.getRect().right);
                invalidate();
                this.selectedEntity.onChange();
                this.entityList.push(new Pair<>(this.selectedEntity, EntityAction.TRIM));
                ITrimLineCallback iTrimLineCallback5 = this.iTrimLineCallback;
                if (iTrimLineCallback5 != null) {
                    iTrimLineCallback5.onAddStack(EntityAction.TRIM);
                    return;
                }
                return;
            }
            entityBismilahTimeline.setCurrentRect();
            this.selectedEntity.setX(0.0f);
            invalidate();
            this.selectedEntity.onChange();
            this.entityList.push(new Pair<>(this.selectedEntity, EntityAction.TRIM));
            ITrimLineCallback iTrimLineCallback6 = this.iTrimLineCallback;
            if (iTrimLineCallback6 != null) {
                iTrimLineCallback6.onAddStack(EntityAction.TRIM);
                return;
            }
            return;
        }
        if (entity instanceof EntityTrslTimeline) {
            EntityTrslTimeline entityTrslTimeline = (EntityTrslTimeline) entity;
            if (entityTrslTimeline.getIndex() - 1 >= 0 && (previewOrNextEntityTrslQuran = getPreviewOrNextEntityTrslQuran(this.entityListTrslQuran, entityTrslTimeline.getIndex() - 1, false)) != null) {
                entityTrslTimeline.setCurrentRect();
                entityTrslTimeline.setX(previewOrNextEntityTrslQuran.getRect().right);
                invalidate();
                this.selectedEntity.onChange();
                this.entityList.push(new Pair<>(this.selectedEntity, EntityAction.TRIM));
                ITrimLineCallback iTrimLineCallback7 = this.iTrimLineCallback;
                if (iTrimLineCallback7 != null) {
                    iTrimLineCallback7.onAddStack(EntityAction.TRIM);
                    return;
                }
                return;
            }
            entityTrslTimeline.setCurrentRect();
            this.selectedEntity.setX(0.0f);
            invalidate();
            this.selectedEntity.onChange();
            this.entityList.push(new Pair<>(this.selectedEntity, EntityAction.TRIM));
            ITrimLineCallback iTrimLineCallback8 = this.iTrimLineCallback;
            if (iTrimLineCallback8 != null) {
                iTrimLineCallback8.onAddStack(EntityAction.TRIM);
            }
        }
    }

    public void translateUntilNow() {
        EntityTrslTimeline previewOrNextEntityTrslQuran;
        EntityQuranTimeline previewOrNextEntityQuran;
        EntityQuranTimeline previewOrNextEntityQuran2;
        float second_in_screen = getSecond_in_screen() * 0.5f;
        Entity entity = this.selectedEntity;
        if (entity instanceof EntityQuranTimeline) {
            EntityQuranTimeline entityQuranTimeline = (EntityQuranTimeline) entity;
            float abs = Math.abs(getCurrentPosition());
            if (abs - entityQuranTimeline.getRect().left < second_in_screen) {
                return;
            }
            if (entityQuranTimeline.getIndex() + 1 < getEntityListQuran().size() && (previewOrNextEntityQuran2 = getPreviewOrNextEntityQuran(getEntityListQuran(), entityQuranTimeline.getIndex() + 1, true)) != null) {
                entityQuranTimeline.setCurrentRect();
                entityQuranTimeline.setRight(abs);
                if (entityQuranTimeline.getRect().right > previewOrNextEntityQuran2.getRect().left) {
                    float width = entityQuranTimeline.getRect().right + previewOrNextEntityQuran2.getRect().width();
                    float f = entityQuranTimeline.getRect().right - previewOrNextEntityQuran2.getRect().left;
                    previewOrNextEntityQuran2.setCurrentRect();
                    previewOrNextEntityQuran2.setX(entityQuranTimeline.getRect().right);
                    previewOrNextEntityQuran2.setRight(width);
                    previewOrNextEntityQuran2.onChange();
                    this.entityList.push(new Pair<>(previewOrNextEntityQuran2, EntityAction.MOVE));
                    ITrimLineCallback iTrimLineCallback = this.iTrimLineCallback;
                    if (iTrimLineCallback != null) {
                        iTrimLineCallback.onAddStack(EntityAction.MOVE);
                    }
                    for (int index = entityQuranTimeline.getIndex() + 2; index < getEntityListQuran().size(); index++) {
                        EntityQuranTimeline entityQuranTimeline2 = getEntityListQuran().get(index);
                        entityQuranTimeline2.setCurrentRect();
                        float f2 = entityQuranTimeline2.getRect().left + f;
                        float width2 = entityQuranTimeline2.getRect().width() + f2;
                        entityQuranTimeline2.setX(f2);
                        entityQuranTimeline2.setRight(width2);
                        invalidate();
                        entityQuranTimeline2.onChange();
                        this.entityList.push(new Pair<>(entityQuranTimeline2, EntityAction.MOVE));
                        ITrimLineCallback iTrimLineCallback2 = this.iTrimLineCallback;
                        if (iTrimLineCallback2 != null) {
                            iTrimLineCallback2.onAddStack(EntityAction.MOVE);
                        }
                    }
                }
                invalidate();
                this.selectedEntity.onChange();
                this.entityList.push(new Pair<>(this.selectedEntity, EntityAction.TRIM));
                ITrimLineCallback iTrimLineCallback3 = this.iTrimLineCallback;
                if (iTrimLineCallback3 != null) {
                    iTrimLineCallback3.onAddStack(EntityAction.TRIM);
                    return;
                }
                return;
            }
            entityQuranTimeline.setCurrentRect();
            this.selectedEntity.setRight(abs);
            invalidate();
            this.selectedEntity.onChange();
            this.entityList.push(new Pair<>(this.selectedEntity, EntityAction.TRIM));
            ITrimLineCallback iTrimLineCallback4 = this.iTrimLineCallback;
            if (iTrimLineCallback4 != null) {
                iTrimLineCallback4.onAddStack(EntityAction.TRIM);
                return;
            }
            return;
        }
        if (entity instanceof EntityBismilahTimeline) {
            EntityBismilahTimeline entityBismilahTimeline = (EntityBismilahTimeline) entity;
            float abs2 = Math.abs(getCurrentPosition());
            if (abs2 - entityBismilahTimeline.getRect().left < second_in_screen) {
                return;
            }
            if (entityBismilahTimeline == this.mIsi3adaTimeline && isExist(this.bismilahTimeline)) {
                entityBismilahTimeline.setCurrentRect();
                entityBismilahTimeline.setRight(abs2);
                if (entityBismilahTimeline.getRect().right > this.bismilahTimeline.getRect().left) {
                    float width3 = entityBismilahTimeline.getRect().right + this.bismilahTimeline.getRect().width();
                    float f3 = entityBismilahTimeline.getRect().right - this.bismilahTimeline.getRect().left;
                    this.bismilahTimeline.setCurrentRect();
                    this.bismilahTimeline.setX(entityBismilahTimeline.getRect().right);
                    this.bismilahTimeline.setRight(width3);
                    this.bismilahTimeline.onChange();
                    this.entityList.push(new Pair<>(this.bismilahTimeline, EntityAction.MOVE));
                    ITrimLineCallback iTrimLineCallback5 = this.iTrimLineCallback;
                    if (iTrimLineCallback5 != null) {
                        iTrimLineCallback5.onAddStack(EntityAction.MOVE);
                    }
                    for (int index2 = this.bismilahTimeline.getIndex(); index2 < getEntityListQuran().size(); index2++) {
                        EntityQuranTimeline entityQuranTimeline3 = getEntityListQuran().get(index2);
                        entityQuranTimeline3.setCurrentRect();
                        float f4 = entityQuranTimeline3.getRect().left + f3;
                        float width4 = entityQuranTimeline3.getRect().width() + f4;
                        entityQuranTimeline3.setX(f4);
                        entityQuranTimeline3.setRight(width4);
                        invalidate();
                        entityQuranTimeline3.onChange();
                        this.entityList.push(new Pair<>(entityQuranTimeline3, EntityAction.MOVE));
                        ITrimLineCallback iTrimLineCallback6 = this.iTrimLineCallback;
                        if (iTrimLineCallback6 != null) {
                            iTrimLineCallback6.onAddStack(EntityAction.MOVE);
                        }
                    }
                }
                invalidate();
                this.selectedEntity.onChange();
                this.entityList.push(new Pair<>(this.selectedEntity, EntityAction.TRIM));
                ITrimLineCallback iTrimLineCallback7 = this.iTrimLineCallback;
                if (iTrimLineCallback7 != null) {
                    iTrimLineCallback7.onAddStack(EntityAction.TRIM);
                    return;
                }
                return;
            }
            if (entityBismilahTimeline.getIndex() < getEntityListQuran().size() && (previewOrNextEntityQuran = getPreviewOrNextEntityQuran(getEntityListQuran(), entityBismilahTimeline.getIndex(), true)) != null) {
                entityBismilahTimeline.setCurrentRect();
                entityBismilahTimeline.setRight(abs2);
                if (entityBismilahTimeline.getRect().right > previewOrNextEntityQuran.getRect().left) {
                    float width5 = entityBismilahTimeline.getRect().right + previewOrNextEntityQuran.getRect().width();
                    float f5 = entityBismilahTimeline.getRect().right - previewOrNextEntityQuran.getRect().left;
                    previewOrNextEntityQuran.setCurrentRect();
                    previewOrNextEntityQuran.setX(entityBismilahTimeline.getRect().right);
                    previewOrNextEntityQuran.setRight(width5);
                    previewOrNextEntityQuran.onChange();
                    this.entityList.push(new Pair<>(previewOrNextEntityQuran, EntityAction.MOVE));
                    ITrimLineCallback iTrimLineCallback8 = this.iTrimLineCallback;
                    if (iTrimLineCallback8 != null) {
                        iTrimLineCallback8.onAddStack(EntityAction.MOVE);
                    }
                    for (int index3 = entityBismilahTimeline.getIndex() + 1; index3 < getEntityListQuran().size(); index3++) {
                        EntityQuranTimeline entityQuranTimeline4 = getEntityListQuran().get(index3);
                        entityQuranTimeline4.setCurrentRect();
                        float f6 = entityQuranTimeline4.getRect().left + f5;
                        float width6 = entityQuranTimeline4.getRect().width() + f6;
                        entityQuranTimeline4.setX(f6);
                        entityQuranTimeline4.setRight(width6);
                        invalidate();
                        entityQuranTimeline4.onChange();
                        this.entityList.push(new Pair<>(entityQuranTimeline4, EntityAction.MOVE));
                        ITrimLineCallback iTrimLineCallback9 = this.iTrimLineCallback;
                        if (iTrimLineCallback9 != null) {
                            iTrimLineCallback9.onAddStack(EntityAction.MOVE);
                        }
                    }
                }
                invalidate();
                this.selectedEntity.onChange();
                this.entityList.push(new Pair<>(this.selectedEntity, EntityAction.TRIM));
                ITrimLineCallback iTrimLineCallback10 = this.iTrimLineCallback;
                if (iTrimLineCallback10 != null) {
                    iTrimLineCallback10.onAddStack(EntityAction.TRIM);
                    return;
                }
                return;
            }
            entityBismilahTimeline.setCurrentRect();
            this.selectedEntity.setRight(abs2);
            invalidate();
            this.selectedEntity.onChange();
            this.entityList.push(new Pair<>(this.selectedEntity, EntityAction.TRIM));
            ITrimLineCallback iTrimLineCallback11 = this.iTrimLineCallback;
            if (iTrimLineCallback11 != null) {
                iTrimLineCallback11.onAddStack(EntityAction.TRIM);
                return;
            }
            return;
        }
        if (entity instanceof EntityTrslTimeline) {
            EntityTrslTimeline entityTrslTimeline = (EntityTrslTimeline) entity;
            float abs3 = Math.abs(getCurrentPosition());
            if (abs3 - entityTrslTimeline.getRect().left < second_in_screen) {
                return;
            }
            if (entityTrslTimeline.getIndex() + 1 < getEntityListTrslQuran().size() && (previewOrNextEntityTrslQuran = getPreviewOrNextEntityTrslQuran(getEntityListTrslQuran(), entityTrslTimeline.getIndex() + 1, true)) != null) {
                entityTrslTimeline.setCurrentRect();
                entityTrslTimeline.setRight(abs3);
                if (entityTrslTimeline.getRect().right > previewOrNextEntityTrslQuran.getRect().left) {
                    float width7 = entityTrslTimeline.getRect().right + previewOrNextEntityTrslQuran.getRect().width();
                    float f7 = entityTrslTimeline.getRect().right - previewOrNextEntityTrslQuran.getRect().left;
                    previewOrNextEntityTrslQuran.setCurrentRect();
                    previewOrNextEntityTrslQuran.setX(entityTrslTimeline.getRect().right);
                    previewOrNextEntityTrslQuran.setRight(width7);
                    previewOrNextEntityTrslQuran.onChange();
                    this.entityList.push(new Pair<>(previewOrNextEntityTrslQuran, EntityAction.MOVE));
                    ITrimLineCallback iTrimLineCallback12 = this.iTrimLineCallback;
                    if (iTrimLineCallback12 != null) {
                        iTrimLineCallback12.onAddStack(EntityAction.MOVE);
                    }
                    for (int index4 = entityTrslTimeline.getIndex() + 2; index4 < getEntityListTrslQuran().size(); index4++) {
                        EntityTrslTimeline entityTrslTimeline2 = getEntityListTrslQuran().get(index4);
                        entityTrslTimeline2.setCurrentRect();
                        float f8 = entityTrslTimeline2.getRect().left + f7;
                        float width8 = entityTrslTimeline2.getRect().width() + f8;
                        entityTrslTimeline2.setX(f8);
                        entityTrslTimeline2.setRight(width8);
                        invalidate();
                        entityTrslTimeline2.onChange();
                        this.entityList.push(new Pair<>(entityTrslTimeline2, EntityAction.MOVE));
                        ITrimLineCallback iTrimLineCallback13 = this.iTrimLineCallback;
                        if (iTrimLineCallback13 != null) {
                            iTrimLineCallback13.onAddStack(EntityAction.MOVE);
                        }
                    }
                }
                invalidate();
                this.selectedEntity.onChange();
                this.entityList.push(new Pair<>(this.selectedEntity, EntityAction.TRIM));
                ITrimLineCallback iTrimLineCallback14 = this.iTrimLineCallback;
                if (iTrimLineCallback14 != null) {
                    iTrimLineCallback14.onAddStack(EntityAction.TRIM);
                    return;
                }
                return;
            }
            entityTrslTimeline.setCurrentRect();
            this.selectedEntity.setRight(abs3);
            invalidate();
            this.selectedEntity.onChange();
            this.entityList.push(new Pair<>(this.selectedEntity, EntityAction.TRIM));
            ITrimLineCallback iTrimLineCallback15 = this.iTrimLineCallback;
            if (iTrimLineCallback15 != null) {
                iTrimLineCallback15.onAddStack(EntityAction.TRIM);
            }
        }
    }

    public void translateToRightBismilah(EntityBismilahTimeline entityBismilahTimeline) {
        EntityQuranTimeline previewOrNextEntityQuran;
        if (Math.abs(getCurrentPosition()) - entityBismilahTimeline.getRect().left >= this.second_in_screen && entityBismilahTimeline.getIndex() < getEntityListQuran().size() && (previewOrNextEntityQuran = getPreviewOrNextEntityQuran(getEntityListQuran(), entityBismilahTimeline.getIndex(), true)) != null) {
            if (entityBismilahTimeline.getRect().right > previewOrNextEntityQuran.getRect().left) {
                float width = entityBismilahTimeline.getRect().right + previewOrNextEntityQuran.getRect().width();
                float f = entityBismilahTimeline.getRect().right - previewOrNextEntityQuran.getRect().left;
                previewOrNextEntityQuran.setCurrentRect();
                previewOrNextEntityQuran.setX(entityBismilahTimeline.getRect().right);
                previewOrNextEntityQuran.setRight(width);
                previewOrNextEntityQuran.onChange();
                this.entityList.push(new Pair<>(previewOrNextEntityQuran, EntityAction.MOVE));
                ITrimLineCallback iTrimLineCallback = this.iTrimLineCallback;
                if (iTrimLineCallback != null) {
                    iTrimLineCallback.onAddStack(EntityAction.MOVE);
                }
                for (int index = entityBismilahTimeline.getIndex() + 1; index < getEntityListQuran().size(); index++) {
                    EntityQuranTimeline entityQuranTimeline = getEntityListQuran().get(index);
                    entityQuranTimeline.setCurrentRect();
                    float f2 = entityQuranTimeline.getRect().left + f;
                    float width2 = entityQuranTimeline.getRect().width() + f2;
                    entityQuranTimeline.setX(f2);
                    entityQuranTimeline.setRight(width2);
                    invalidate();
                    entityQuranTimeline.onChange();
                    this.entityList.push(new Pair<>(entityQuranTimeline, EntityAction.MOVE));
                    ITrimLineCallback iTrimLineCallback2 = this.iTrimLineCallback;
                    if (iTrimLineCallback2 != null) {
                        iTrimLineCallback2.onAddStack(EntityAction.MOVE);
                    }
                }
            }
            invalidate();
        }
    }

    public void translateEndNow() {
        EntityTrslTimeline previewOrNextEntityTrslQuran;
        EntityQuranTimeline previewOrNextEntityQuran;
        EntityQuranTimeline previewOrNextEntityQuran2;
        Entity entity = this.selectedEntity;
        if (entity instanceof EntityQuranTimeline) {
            EntityQuranTimeline entityQuranTimeline = (EntityQuranTimeline) entity;
            if (entityQuranTimeline.getIndex() + 1 < this.entityListQuran.size() && (previewOrNextEntityQuran2 = getPreviewOrNextEntityQuran(this.entityListQuran, entityQuranTimeline.getIndex() + 1, true)) != null) {
                entityQuranTimeline.setCurrentRect();
                entityQuranTimeline.setRight(previewOrNextEntityQuran2.getRect().left);
                invalidate();
                this.selectedEntity.onChange();
                this.entityList.push(new Pair<>(this.selectedEntity, EntityAction.TRIM));
                ITrimLineCallback iTrimLineCallback = this.iTrimLineCallback;
                if (iTrimLineCallback != null) {
                    iTrimLineCallback.onAddStack(EntityAction.TRIM);
                    return;
                }
                return;
            }
            entityQuranTimeline.setCurrentRect();
            getSelectedEntity().setRight(getTimeLineW() * getScaleFactor());
            invalidate();
            this.selectedEntity.onChange();
            this.entityList.push(new Pair<>(this.selectedEntity, EntityAction.TRIM));
            ITrimLineCallback iTrimLineCallback2 = this.iTrimLineCallback;
            if (iTrimLineCallback2 != null) {
                iTrimLineCallback2.onAddStack(EntityAction.TRIM);
                return;
            }
            return;
        }
        if (entity instanceof EntityBismilahTimeline) {
            EntityBismilahTimeline entityBismilahTimeline = (EntityBismilahTimeline) entity;
            if (entityBismilahTimeline == this.mIsi3adaTimeline && isExist(this.bismilahTimeline)) {
                entityBismilahTimeline.setCurrentRect();
                entityBismilahTimeline.setRight(this.bismilahTimeline.getRect().left);
                invalidate();
                this.selectedEntity.onChange();
                this.entityList.push(new Pair<>(this.selectedEntity, EntityAction.TRIM));
                ITrimLineCallback iTrimLineCallback3 = this.iTrimLineCallback;
                if (iTrimLineCallback3 != null) {
                    iTrimLineCallback3.onAddStack(EntityAction.TRIM);
                    return;
                }
                return;
            }
            if (entityBismilahTimeline.getIndex() < this.entityListQuran.size() && (previewOrNextEntityQuran = getPreviewOrNextEntityQuran(this.entityListQuran, entityBismilahTimeline.getIndex(), true)) != null) {
                entityBismilahTimeline.setCurrentRect();
                entityBismilahTimeline.setRight(previewOrNextEntityQuran.getRect().left);
                invalidate();
                this.selectedEntity.onChange();
                this.entityList.push(new Pair<>(this.selectedEntity, EntityAction.TRIM));
                ITrimLineCallback iTrimLineCallback4 = this.iTrimLineCallback;
                if (iTrimLineCallback4 != null) {
                    iTrimLineCallback4.onAddStack(EntityAction.TRIM);
                    return;
                }
                return;
            }
            entityBismilahTimeline.setCurrentRect();
            getSelectedEntity().setRight(getTimeLineW() * getScaleFactor());
            invalidate();
            this.selectedEntity.onChange();
            this.entityList.push(new Pair<>(this.selectedEntity, EntityAction.TRIM));
            ITrimLineCallback iTrimLineCallback5 = this.iTrimLineCallback;
            if (iTrimLineCallback5 != null) {
                iTrimLineCallback5.onAddStack(EntityAction.TRIM);
                return;
            }
            return;
        }
        if (entity instanceof EntityTrslTimeline) {
            EntityTrslTimeline entityTrslTimeline = (EntityTrslTimeline) entity;
            if (entityTrslTimeline.getIndex() + 1 < this.entityListTrslQuran.size() && (previewOrNextEntityTrslQuran = getPreviewOrNextEntityTrslQuran(this.entityListTrslQuran, entityTrslTimeline.getIndex() + 1, true)) != null) {
                entityTrslTimeline.setCurrentRect();
                entityTrslTimeline.setRight(previewOrNextEntityTrslQuran.getRect().left);
                invalidate();
                this.selectedEntity.onChange();
                this.entityList.push(new Pair<>(this.selectedEntity, EntityAction.TRIM));
                ITrimLineCallback iTrimLineCallback6 = this.iTrimLineCallback;
                if (iTrimLineCallback6 != null) {
                    iTrimLineCallback6.onAddStack(EntityAction.TRIM);
                    return;
                }
                return;
            }
            entityTrslTimeline.setCurrentRect();
            getSelectedEntity().setRight(getTimeLineW() * getScaleFactor());
            invalidate();
            this.selectedEntity.onChange();
            this.entityList.push(new Pair<>(this.selectedEntity, EntityAction.TRIM));
            ITrimLineCallback iTrimLineCallback7 = this.iTrimLineCallback;
            if (iTrimLineCallback7 != null) {
                iTrimLineCallback7.onAddStack(EntityAction.TRIM);
            }
        }
    }

    public void translateToStart() {
        this.current_cursur_position = 0;
        this.currentPosition = 0.0f;
        this.scrolled_with_zoom = 0.0f;
        invalidate();
    }

    public void translateToEnd() {
        this.current_cursur_position = this.maxTime;
        float f = ((-this.maxTime) * this.second_in_screen) / 1000.0f;
        this.currentPosition = f;
        this.scrolled_with_zoom = f * this.scaleFactor;
        invalidate();
    }

    public void translateToStart(Entity entity) {
        if (entity == null) {
            return;
        }
        this.current_cursur_position = Math.round(entity.getRect().left / getSecond_in_screen()) * 1000;
        float second_in_screen = ((-this.current_cursur_position) * getSecond_in_screen()) / 1000.0f;
        this.currentPosition = second_in_screen;
        this.scrolled_with_zoom = second_in_screen * this.scaleFactor;
        invalidate();
    }

    public void translateToEnd(Entity entity) {
        if (entity == null) {
            return;
        }
        this.current_cursur_position = Math.round(entity.getRect().right / getSecond_in_screen()) * 1000;
        float second_in_screen = ((-this.current_cursur_position) * getSecond_in_screen()) / 1000.0f;
        this.currentPosition = second_in_screen;
        this.scrolled_with_zoom = second_in_screen * this.scaleFactor;
        invalidate();
    }

    public void previewEntity(Entity entity) {
        if (entity == null) {
            return;
        }
        this.current_cursur_position = Math.round(entity.getRect().left / getSecond_in_screen()) * 1000;
        float second_in_screen = ((-this.current_cursur_position) * getSecond_in_screen()) / 1000.0f;
        this.currentPosition = second_in_screen;
        this.scrolled_with_zoom = second_in_screen * this.scaleFactor;
        this.maxTime = (int) ((entity.getRect().right / getSecond_in_screen()) * 1000.0f);
        this.timeLineW = entity.getRect().right / this.scaleFactor;
    }

    public void updateCursurToSelectEntity() {
        Entity entity = this.selectedEntity;
        if (entity == null || entity.getEntityView().isVisible()) {
            return;
        }
        this.current_cursur_position = Math.round((entity.getRect().left + (this.selectedEntity.getRect().width() * 0.5f)) / getSecond_in_screen()) * 1000;
        float f = ((-this.current_cursur_position) * this.second_in_screen) / 1000.0f;
        this.currentPosition = f;
        this.scrolled_with_zoom = f * this.scaleFactor;
        invalidate();
    }

    public void addStack(Entity entity, EntityAction entityAction) {
        this.entityList.push(new Pair<>(entity, entityAction));
    }

    public void selectEntity(Entity entity, boolean z) {
        Entity entity2 = this.selectedEntity;
        if (entity2 != null) {
            entity2.setSelect(false);
        }
        if (entity != null) {
            entity.setSelect(true);
        }
        this.selectedEntity = entity;
        if (z) {
            invalidate();
        }
    }

    public void stackSplit(Entity entity) {
        this.entityList.push(new Pair<>(entity, EntityAction.SPLIT));
        ITrimLineCallback iTrimLineCallback = this.iTrimLineCallback;
        if (iTrimLineCallback != null) {
            iTrimLineCallback.onAddStack(EntityAction.SPLIT);
        }
    }

    public void splitAudio(EntityAudio entityAudio, int i) {
        if (i < this.entityListAudio.size()) {
            this.entityListAudio.add(i, entityAudio);
        } else {
            this.entityListAudio.add(entityAudio);
        }
        selectEntity(entityAudio, false);
    }

    public void deleteEntity(boolean z) {
        try {
            Entity entity = this.selectedEntity;
            if (entity != null) {
                entity.visible(false);
                ITrimLineCallback iTrimLineCallback = this.iTrimLineCallback;
                if (iTrimLineCallback != null) {
                    iTrimLineCallback.onDelete(this.selectedEntity.getEntityView());
                }
                this.entityList.push(new Pair<>(this.selectedEntity, EntityAction.DELETE));
                ITrimLineCallback iTrimLineCallback2 = this.iTrimLineCallback;
                if (iTrimLineCallback2 != null) {
                    iTrimLineCallback2.onAddStack(EntityAction.DELETE);
                }
                this.selectedEntity = null;
                if (z) {
                    updateTrslIndex();
                } else {
                    updateIndex();
                }
            }
        } catch (Exception unused) {
        }
        invalidate();
    }

    public void deleteEntityAllSelect() {
        try {
            List<EntityQuranTimeline> list = this.entityListQuran;
            EntityAudio entityAudio = null;
            if (list != null && !list.isEmpty()) {
                ArrayList arrayList = new ArrayList();
                EntityQuranTimeline entityQuranTimeline = null;
                for (int i = 0; i < this.entityListQuran.size(); i++) {
                    EntityQuranTimeline entityQuranTimeline2 = this.entityListQuran.get(i);
                    if (entityQuranTimeline2.visible() && entityQuranTimeline2.isSelect()) {
                        if (entityQuranTimeline == null) {
                            entityQuranTimeline = entityQuranTimeline2;
                        } else {
                            arrayList.add(entityQuranTimeline2);
                        }
                        entityQuranTimeline2.visible(false);
                        entityQuranTimeline2.setSelect(false);
                        ITrimLineCallback iTrimLineCallback = this.iTrimLineCallback;
                        if (iTrimLineCallback != null) {
                            iTrimLineCallback.onDelete(entityQuranTimeline2.getEntityView());
                        }
                        ITrimLineCallback iTrimLineCallback2 = this.iTrimLineCallback;
                        if (iTrimLineCallback2 != null) {
                            iTrimLineCallback2.onAddStack(EntityAction.DELETE);
                        }
                    }
                }
                if (entityQuranTimeline != null) {
                    this.entityList.push(new Pair<>(entityQuranTimeline, EntityAction.DELETE_MULTIPLE));
                    if (!arrayList.isEmpty()) {
                        entityQuranTimeline.setEntitiesGroup(arrayList);
                    }
                }
                updateIndex();
            }
            List<EntityTrslTimeline> list2 = this.entityListTrslQuran;
            if (list2 != null && !list2.isEmpty()) {
                ArrayList arrayList2 = new ArrayList();
                EntityTrslTimeline entityTrslTimeline = null;
                for (int i2 = 0; i2 < this.entityListTrslQuran.size(); i2++) {
                    EntityTrslTimeline entityTrslTimeline2 = this.entityListTrslQuran.get(i2);
                    if (entityTrslTimeline2.visible() && entityTrslTimeline2.isSelect()) {
                        if (entityTrslTimeline == null) {
                            entityTrslTimeline = entityTrslTimeline2;
                        } else {
                            arrayList2.add(entityTrslTimeline2);
                        }
                        entityTrslTimeline2.visible(false);
                        entityTrslTimeline2.setSelect(false);
                        ITrimLineCallback iTrimLineCallback3 = this.iTrimLineCallback;
                        if (iTrimLineCallback3 != null) {
                            iTrimLineCallback3.onDelete(entityTrslTimeline2.getEntityView());
                        }
                        ITrimLineCallback iTrimLineCallback4 = this.iTrimLineCallback;
                        if (iTrimLineCallback4 != null) {
                            iTrimLineCallback4.onAddStack(EntityAction.DELETE);
                        }
                    }
                }
                if (entityTrslTimeline != null) {
                    this.entityList.push(new Pair<>(entityTrslTimeline, EntityAction.DELETE_MULTIPLE));
                    if (!arrayList2.isEmpty()) {
                        entityTrslTimeline.setEntitiesGroup(arrayList2);
                    }
                }
                updateTrslIndex();
            }
            if (isExist(this.bismilahTimeline) && this.bismilahTimeline.isSelect()) {
                this.bismilahTimeline.visible(false);
                this.bismilahTimeline.setSelect(false);
                this.bismilahTimeline.setSelectMultiple(false);
                ITrimLineCallback iTrimLineCallback5 = this.iTrimLineCallback;
                if (iTrimLineCallback5 != null) {
                    iTrimLineCallback5.onDelete(this.bismilahTimeline.getEntityView());
                }
                this.entityList.push(new Pair<>(this.bismilahTimeline, EntityAction.DELETE_MULTIPLE));
            }
            if (isExist(this.mIsi3adaTimeline) && this.mIsi3adaTimeline.isSelect()) {
                this.mIsi3adaTimeline.visible(false);
                this.mIsi3adaTimeline.setSelect(false);
                this.mIsi3adaTimeline.setSelectMultiple(false);
                ITrimLineCallback iTrimLineCallback6 = this.iTrimLineCallback;
                if (iTrimLineCallback6 != null) {
                    iTrimLineCallback6.onDelete(this.mIsi3adaTimeline.getEntityView());
                }
                this.entityList.push(new Pair<>(this.mIsi3adaTimeline, EntityAction.DELETE_MULTIPLE));
            }
            List<EntityAudio> list3 = this.entityListAudio;
            if (list3 != null && !list3.isEmpty()) {
                ArrayList arrayList3 = new ArrayList();
                for (int i3 = 0; i3 < this.entityListAudio.size(); i3++) {
                    EntityAudio entityAudio2 = this.entityListAudio.get(i3);
                    if (entityAudio2.visible() && entityAudio2.isSelect()) {
                        entityAudio2.visible(false);
                        entityAudio2.setSelect(false);
                        if (entityAudio == null) {
                            entityAudio = entityAudio2;
                        } else {
                            arrayList3.add(entityAudio2);
                        }
                        ITrimLineCallback iTrimLineCallback7 = this.iTrimLineCallback;
                        if (iTrimLineCallback7 != null) {
                            iTrimLineCallback7.onAddStack(EntityAction.DELETE);
                        }
                    }
                }
                if (entityAudio != null) {
                    this.entityList.push(new Pair<>(entityAudio, EntityAction.DELETE_MULTIPLE));
                    if (!arrayList3.isEmpty()) {
                        entityAudio.setEntitiesGroup(arrayList3);
                    }
                }
                updateMediaIndex();
            }
        } catch (Exception unused) {
        }
        this.clr_btn_audio = -13421771;
        this.clr_btn_quran = -13421771;
        this.clr_btn_trsl = -13421771;
    }

    public void deleteMediaEntity() {
        try {
            Entity entity = this.selectedEntity;
            if (entity != null) {
                entity.visible(false);
                this.entityList.push(new Pair<>(this.selectedEntity, EntityAction.DELETE));
                ITrimLineCallback iTrimLineCallback = this.iTrimLineCallback;
                if (iTrimLineCallback != null) {
                    iTrimLineCallback.onAddStack(EntityAction.DELETE);
                }
                this.selectedEntity = null;
                updateMediaIndex();
            }
        } catch (Exception unused) {
        }
        invalidate();
    }

    public void addAudio(EntityAudio entityAudio, int i) {
        if (i < this.entityListAudio.size()) {
            entityAudio.setIndex(i);
            this.entityListAudio.add(i, entityAudio);
            float f = entityAudio.getRect().right;
            while (true) {
                i++;
                if (i >= this.entityListAudio.size()) {
                    break;
                }
                EntityAudio entityAudio2 = this.entityListAudio.get(i);
                if (entityAudio2.visible()) {
                    float width = entityAudio2.getRect().width();
                    entityAudio2.setCurrentRect();
                    entityAudio2.setX(f);
                    entityAudio2.setRight(f + width);
                    entityAudio2.setIndex(i);
                    f = entityAudio2.getRect().right;
                }
            }
        } else {
            entityAudio.setIndex(i);
            this.entityListAudio.add(entityAudio);
        }
        this.entityList.push(new Pair<>(entityAudio, EntityAction.ADD));
        ITrimLineCallback iTrimLineCallback = this.iTrimLineCallback;
        if (iTrimLineCallback != null) {
            iTrimLineCallback.onAddStack(EntityAction.ADD);
        }
    }

    public void addAudio(EntityAudio entityAudio) {
        this.entityListAudio.add(entityAudio);
        entityAudio.setIndex(this.entityListAudio.size() - 1);
        this.entityList.push(new Pair<>(entityAudio, EntityAction.ADD));
        ITrimLineCallback iTrimLineCallback = this.iTrimLineCallback;
        if (iTrimLineCallback != null) {
            iTrimLineCallback.onAddStack(EntityAction.ADD);
        }
    }

    public void addQuran(EntityQuranTimeline entityQuranTimeline) {
        this.entityListQuran.add(entityQuranTimeline);
        entityQuranTimeline.setIndex(this.entityListQuran.size() - 1);
        this.entityList.push(new Pair<>(entityQuranTimeline, EntityAction.ADD));
        ITrimLineCallback iTrimLineCallback = this.iTrimLineCallback;
        if (iTrimLineCallback != null) {
            iTrimLineCallback.onAddStack(EntityAction.ADD);
        }
    }

    public void addTrslQuran(EntityTrslTimeline entityTrslTimeline) {
        this.entityListTrslQuran.add(entityTrslTimeline);
        entityTrslTimeline.setIndex(this.entityListTrslQuran.size() - 1);
        this.entityList.push(new Pair<>(entityTrslTimeline, EntityAction.ADD));
        ITrimLineCallback iTrimLineCallback = this.iTrimLineCallback;
        if (iTrimLineCallback != null) {
            iTrimLineCallback.onAddStack(EntityAction.ADD);
        }
    }

    public void addTrslQuran(EntityTrslTimeline entityTrslTimeline, int i) {
        if (i < this.entityListTrslQuran.size()) {
            entityTrslTimeline.setIndex(i);
            this.entityListTrslQuran.add(i, entityTrslTimeline);
            float f = entityTrslTimeline.getRect().right;
            while (true) {
                i++;
                if (i >= this.entityListTrslQuran.size()) {
                    break;
                }
                EntityTrslTimeline entityTrslTimeline2 = this.entityListTrslQuran.get(i);
                if (entityTrslTimeline2.visible()) {
                    float width = entityTrslTimeline2.getRect().width();
                    entityTrslTimeline2.setCurrentRect();
                    entityTrslTimeline2.setX(f);
                    entityTrslTimeline2.setRight(f + width);
                    entityTrslTimeline2.setIndex(i);
                    f = entityTrslTimeline2.getRect().right;
                }
            }
        } else {
            entityTrslTimeline.setIndex(i);
            this.entityListTrslQuran.add(entityTrslTimeline);
        }
        this.entityList.push(new Pair<>(entityTrslTimeline, EntityAction.ADD));
        ITrimLineCallback iTrimLineCallback = this.iTrimLineCallback;
        if (iTrimLineCallback != null) {
            iTrimLineCallback.onAddStack(EntityAction.ADD);
        }
    }

    public void addQuran(EntityQuranTimeline entityQuranTimeline, int i) {
        if (i < this.entityListQuran.size()) {
            entityQuranTimeline.setIndex(i);
            this.entityListQuran.add(i, entityQuranTimeline);
            float f = entityQuranTimeline.getRect().right;
            while (true) {
                i++;
                if (i >= this.entityListQuran.size()) {
                    break;
                }
                EntityQuranTimeline entityQuranTimeline2 = this.entityListQuran.get(i);
                if (entityQuranTimeline2.visible()) {
                    float width = entityQuranTimeline2.getRect().width();
                    entityQuranTimeline2.setCurrentRect();
                    entityQuranTimeline2.setX(f);
                    entityQuranTimeline2.setRight(f + width);
                    entityQuranTimeline2.setIndex(i);
                    f = entityQuranTimeline2.getRect().right;
                }
            }
        } else {
            entityQuranTimeline.setIndex(i);
            this.entityListQuran.add(entityQuranTimeline);
        }
        this.entityList.push(new Pair<>(entityQuranTimeline, EntityAction.ADD));
        ITrimLineCallback iTrimLineCallback = this.iTrimLineCallback;
        if (iTrimLineCallback != null) {
            iTrimLineCallback.onAddStack(EntityAction.ADD);
        }
    }

    public void addQuran_split(EntityQuranTimeline entityQuranTimeline, int i) {
        if (i < this.entityListQuran.size()) {
            entityQuranTimeline.setIndex(i);
            this.entityListQuran.add(i, entityQuranTimeline);
            while (true) {
                i++;
                if (i >= this.entityListQuran.size()) {
                    break;
                }
                EntityQuranTimeline entityQuranTimeline2 = this.entityListQuran.get(i);
                if (entityQuranTimeline2.visible()) {
                    entityQuranTimeline2.setIndex(i);
                }
            }
        } else {
            entityQuranTimeline.setIndex(i);
            this.entityListQuran.add(entityQuranTimeline);
        }
        this.entityList.push(new Pair<>(entityQuranTimeline, EntityAction.SPLIT));
        ITrimLineCallback iTrimLineCallback = this.iTrimLineCallback;
        if (iTrimLineCallback != null) {
            iTrimLineCallback.onAddStack(EntityAction.SPLIT);
        }
    }

    public void addQuran_split(EntityTrslTimeline entityTrslTimeline, int i) {
        if (i < this.entityListTrslQuran.size()) {
            entityTrslTimeline.setIndex(i);
            this.entityListTrslQuran.add(i, entityTrslTimeline);
            while (true) {
                i++;
                if (i >= this.entityListTrslQuran.size()) {
                    break;
                }
                EntityTrslTimeline entityTrslTimeline2 = this.entityListTrslQuran.get(i);
                if (entityTrslTimeline2.visible()) {
                    entityTrslTimeline2.setIndex(i);
                }
            }
        } else {
            entityTrslTimeline.setIndex(i);
            this.entityListTrslQuran.add(entityTrslTimeline);
        }
        this.entityList.push(new Pair<>(entityTrslTimeline, EntityAction.SPLIT));
        ITrimLineCallback iTrimLineCallback = this.iTrimLineCallback;
        if (iTrimLineCallback != null) {
            iTrimLineCallback.onAddStack(EntityAction.SPLIT);
        }
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int i) {
        this.duration = i;
    }

    public float getXCursur() {
        return (-this.currentPosition) * this.scaleFactor;
    }

    public int getCurrent_cursur_position() {
        return this.current_cursur_position;
    }

    public void setOnProgress(boolean z) {
        this.isProgress = z;
    }

    public boolean isPass(MotionEvent motionEvent) {
        long eventTime = motionEvent.getEventTime();
        long j = this.lastTime;
        long j2 = eventTime - j;
        if (this.isDetectChange || j == 0) {
            int i = this.countMove + 1;
            this.countMove = i;
            if (i > 3) {
                this.isDetectChange = false;
                this.countMove = 0;
            }
        } else if (j2 > this.lastDifference * 2.88d) {
            this.isDetectChange = true;
        }
        if (this.isDetectChange) {
            return false;
        }
        this.lastTime = motionEvent.getEventTime();
        this.lastDifference = j2;
        return true;
    }

    private void updateMediaIndex() {
        for (int i = 0; i < this.entityListAudio.size(); i++) {
            this.entityListAudio.get(i).setIndex(i);
        }
    }

    private void updateIndex() {
        for (int i = 0; i < this.entityListQuran.size(); i++) {
            EntityQuranTimeline entityQuranTimeline = this.entityListQuran.get(i);
            entityQuranTimeline.setIndex(i);
            entityQuranTimeline.getQuranEntity().setIndex(i);
        }
    }

    private void updateTrslIndex() {
        for (int i = 0; i < this.entityListTrslQuran.size(); i++) {
            EntityTrslTimeline entityTrslTimeline = this.entityListTrslQuran.get(i);
            entityTrslTimeline.setIndex(i);
            entityTrslTimeline.getQuranEntity().setIndex(i);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:138:0x02c7  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x0352  */
    /* JADX WARN: Removed duplicated region for block: B:168:0x03a7  */
    /* JADX WARN: Removed duplicated region for block: B:179:0x03b2  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x014f  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x01ef  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void updateSelectionOnTap(android.view.MotionEvent r10) {
        /*
            Method dump skipped, instructions count: 970
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: hazem.nurmontage.videoquran.views.TrackEntityView.updateSelectionOnTap(android.view.MotionEvent):void");
    }

    public void finishScroll() {
        try {
            Scroller scroller = this.scroller;
            if (scroller != null && !scroller.isFinished()) {
                this.scroller.abortAnimation();
            }
            this.scroller = null;
        } catch (Exception unused) {
        }
    }

    @Override // android.view.View
    public void computeScroll() {
        ITrimLineCallback iTrimLineCallback;
        Scroller scroller = this.scroller;
        if (scroller == null || this.isProgress || !scroller.computeScrollOffset()) {
            return;
        }
        if (this.currentPosition != 0.0f || this.scroller.getCurrX() <= 0) {
            float currX = this.scroller.getCurrX();
            this.currentPosition = currX;
            if (currX > 0.0f) {
                this.currentPosition = 0.0f;
            }
            float f = this.currentPosition * this.scaleFactor;
            this.scrolled_with_zoom = f;
            if (!this.isPlaying && (iTrimLineCallback = this.iTrimLineCallback) != null) {
                iTrimLineCallback.onSeekPlayer(f);
            }
            invalidate();
        }
    }

    public void pauseScroll() {
        Scroller scroller = this.scroller;
        if (scroller == null || scroller.isFinished()) {
            return;
        }
        this.scroller.abortAnimation();
    }

    public void updateWhenEffect(EntityAudio entityAudio) {
        EntityAudio previewOrNextEntityAudio;
        if (entityAudio.getIndex() + 1 >= this.entityListAudio.size() || (previewOrNextEntityAudio = getPreviewOrNextEntityAudio(this.entityListAudio, entityAudio.getIndex() + 1, true)) == null || entityAudio.getRect().right <= previewOrNextEntityAudio.getRect().left) {
            return;
        }
        float width = previewOrNextEntityAudio.getRect().width() + entityAudio.getRect().right;
        float f = entityAudio.getRect().right - previewOrNextEntityAudio.getRect().left;
        previewOrNextEntityAudio.setCurrentRect();
        previewOrNextEntityAudio.setX(entityAudio.getRect().right);
        previewOrNextEntityAudio.setRight(width);
        for (int index = entityAudio.getIndex() + 2; index < this.entityListAudio.size(); index++) {
            EntityAudio entityAudio2 = this.entityListAudio.get(index);
            if (entityAudio2.visible()) {
                float f2 = entityAudio2.getRect().left + f;
                float width2 = entityAudio2.getRect().width() + f2;
                entityAudio2.setCurrentRect();
                entityAudio2.setX(f2);
                entityAudio2.setRight(width2);
            }
        }
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, final MotionEvent motionEvent) {
        Entity entity;
        EntityAudio previewOrNextEntityAudio;
        EntityAudio previewOrNextEntityAudio2;
        EntityTrslTimeline previewOrNextEntityTrslQuran;
        EntityTrslTimeline previewOrNextEntityTrslQuran2;
        EntityQuranTimeline previewOrNextEntityQuran;
        EntityQuranTimeline previewOrNextEntityQuran2;
        float f;
        EntityTrslTimeline previewOrNextEntityTrslQuran3;
        EntityQuranTimeline previewOrNextEntityQuran3;
        EntityAudio previewOrNextEntityAudio3;
        EntityTrslTimeline previewOrNextEntityTrslQuran4;
        EntityQuranTimeline previewOrNextEntityQuran4;
        EntityAudio previewOrNextEntityAudio4;
        if (motionEvent == null || this.isProgress) {
            return false;
        }
        motionEvent.setLocation((motionEvent.getX() + getPaddingLeft()) - ((this.centerX - (this.radius * 0.5f)) + this.scrolled_with_zoom), (motionEvent.getY() + getPaddingTop()) - this.mScrollY);
        if (motionEvent.getPointerCount() > 1) {
            return this.scaleGestureDetector.onTouchEvent(motionEvent);
        }
        if (this.isScaleListener) {
            if (motionEvent.getAction() == 1) {
                this.isScaleListener = false;
            }
            return true;
        }
        int action = motionEvent.getAction();
        if (action == 1) {
            this.eventY = 0.0f;
            this.eventX = 0.0f;
            this.signeY = -1.0f;
            this.signeX = -1.0f;
            this.lastTime = 0L;
            this.lastDifference = 0L;
            this.countMove = 0;
            this.isDetectChange = false;
            this.isPassScroll = true;
            this.isAutoMove = false;
            if (this.selectedEntity != null) {
                if (this.isMove) {
                    this.current_cursur_position = Math.round(((this.currentPosition * 1000.0f) / this.second_in_screen) * (-1.0f));
                    this.isAutoScroll = false;
                    this.isOnUp = true;
                    this.isCheckLineCursur = false;
                    this.isCheckLine = false;
                    invalidate();
                    this.selectedEntity.onChange();
                    this.entityList.push(new Pair<>(this.selectedEntity, EntityAction.MOVE));
                    ITrimLineCallback iTrimLineCallback = this.iTrimLineCallback;
                    if (iTrimLineCallback != null) {
                        iTrimLineCallback.onUpdateTime();
                        this.iTrimLineCallback.onAddStack(EntityAction.MOVE);
                    }
                }
                if (this.selectedEntity.getSelectTrim() != null) {
                    this.isAutoScroll = false;
                    ITrimLineCallback iTrimLineCallback2 = this.iTrimLineCallback;
                    if (iTrimLineCallback2 != null) {
                        iTrimLineCallback2.onUp();
                    }
                    this.pass = true;
                    this.onThink = true;
                    this.lasX = 0.0f;
                    this.isOnUp = true;
                    this.isCheckLineCursur = false;
                    this.isCheckLine = false;
                    if (this.selectedEntity.getTrim_type() == 0) {
                        this.selectedEntity.onChange();
                        this.entityList.push(new Pair<>(this.selectedEntity, EntityAction.TRIM));
                        ITrimLineCallback iTrimLineCallback3 = this.iTrimLineCallback;
                        if (iTrimLineCallback3 != null) {
                            iTrimLineCallback3.onAddStack(EntityAction.TRIM);
                        }
                        this.selectedEntity.onUpLeft();
                    }
                    if (this.selectedEntity.getTrim_type() == 1) {
                        Entity entity2 = this.selectedEntity;
                        if (entity2 instanceof EntityQuranTimeline) {
                            for (EntityQuranTimeline entityQuranTimeline : getEntityListQuran()) {
                                if (entityQuranTimeline.visible() && entityQuranTimeline.getCurrentStackEntity() != null && entityQuranTimeline != this.selectedEntity) {
                                    entityQuranTimeline.onChange();
                                    this.entityList.push(new Pair<>(entityQuranTimeline, EntityAction.MOVE));
                                }
                            }
                        } else if (entity2 instanceof EntityAudio) {
                            for (EntityAudio entityAudio : getEntityListAudio()) {
                                if (entityAudio.visible() && entityAudio.getCurrentStackEntity() != null && entityAudio != this.selectedEntity) {
                                    entityAudio.onChange();
                                    this.entityList.push(new Pair<>(entityAudio, EntityAction.MOVE));
                                }
                            }
                        } else if ((entity2 instanceof EntityBismilahTimeline) && entity2.getCurrentStackEntity() != null) {
                            this.selectedEntity.onChange();
                            this.entityList.push(new Pair<>(this.selectedEntity, EntityAction.MOVE));
                        }
                        this.selectedEntity.onChange();
                        this.entityList.push(new Pair<>(this.selectedEntity, EntityAction.TRIM));
                        ITrimLineCallback iTrimLineCallback4 = this.iTrimLineCallback;
                        if (iTrimLineCallback4 != null) {
                            iTrimLineCallback4.onAddStack(EntityAction.TRIM);
                        }
                        this.selectedEntity.onUpRight();
                    }
                    this.selectedEntity.resetTrim_type();
                    invalidate();
                }
                Entity entity3 = this.selectedEntity;
                entity3.setX(entity3.getRect().left);
                Entity entity4 = this.selectedEntity;
                entity4.setRight(entity4.getRect().right);
                ITrimLineCallback iTrimLineCallback5 = this.iTrimLineCallback;
                if (iTrimLineCallback5 != null && !this.isMove) {
                    iTrimLineCallback5.onUpdateTime();
                }
                this.isMove = false;
                this.autoScrollHandler.removeCallbacks(this.autoMoveRunnable);
                this.autoScrollHandler.removeCallbacks(this.autoScrollRunnable);
            }
            ITrimLineCallback iTrimLineCallback6 = this.iTrimLineCallback;
            if (iTrimLineCallback6 != null) {
                iTrimLineCallback6.onUp();
            }
        } else if (action == 2 && (entity = this.selectedEntity) != null && !this.isPassScroll) {
            if (entity.getSelectTrim() != null) {
                if (!isPass(motionEvent)) {
                    return true;
                }
                ITrimLineCallback iTrimLineCallback7 = this.iTrimLineCallback;
                if (iTrimLineCallback7 != null) {
                    iTrimLineCallback7.onMove();
                }
                if (this.selectedEntity.getTrim_type() == 0 && this.onThink) {
                    if (Math.abs(motionEvent.getX() - this.lasX) <= this.TOLERANCE_X) {
                        return false;
                    }
                    this.lasX = motionEvent.getX();
                    float x = motionEvent.getX() - this.selectedEntity.getDownX();
                    if (x == 0.0f) {
                        return false;
                    }
                    this.selectedEntity.setTrimLeft(true);
                    float left = this.selectedEntity.getLeft() + x;
                    boolean z = this.selectedEntity.getRect().right - left > this.max_trim;
                    if (left < 0.0f) {
                        left = 0.0f;
                    } else if (!z) {
                        left = this.selectedEntity.getRect().right - this.max_trim;
                    }
                    Entity entity5 = this.selectedEntity;
                    if (entity5 instanceof EntityAudio) {
                        EntityAudio entityAudio2 = (EntityAudio) entity5;
                        float offset_right = entityAudio2.getOffset_right() * entityAudio2.getmScaleFactor();
                        float f2 = (this.selectedEntity.getRect().right + offset_right) - left;
                        float max = entityAudio2.getMax() * entityAudio2.getmScaleFactor();
                        if (f2 > max) {
                            Entity entity6 = this.selectedEntity;
                            entity6.setX((entity6.getRect().right + offset_right) - max);
                            entityAudio2.updateStartTrim();
                            invalidate();
                            return true;
                        }
                        if (entityAudio2.getIndex() > 0 && (previewOrNextEntityAudio4 = getPreviewOrNextEntityAudio(this.entityListAudio, entityAudio2.getIndex() - 1, false)) != null && left <= previewOrNextEntityAudio4.getRect().right) {
                            float width = previewOrNextEntityAudio4.getRect().right + this.selectedEntity.getRect().width();
                            this.selectedEntity.setX(previewOrNextEntityAudio4.getRect().right);
                            entityAudio2.updateStartTrim();
                            this.selectedEntity.setRight(width);
                            this.pass = false;
                            invalidate();
                            return true;
                        }
                    }
                    Entity entity7 = this.selectedEntity;
                    if (entity7 instanceof EntityQuranTimeline) {
                        EntityQuranTimeline entityQuranTimeline2 = (EntityQuranTimeline) entity7;
                        if (entityQuranTimeline2.getIndex() > 0 && (previewOrNextEntityQuran4 = getPreviewOrNextEntityQuran(this.entityListQuran, entityQuranTimeline2.getIndex() - 1, false)) != null && left <= previewOrNextEntityQuran4.getRect().right) {
                            this.selectedEntity.setX(previewOrNextEntityQuran4.getRect().right);
                            this.pass = false;
                            invalidate();
                            return true;
                        }
                        if (isExist(this.bismilahTimeline) && left <= this.bismilahTimeline.getRect().right) {
                            this.selectedEntity.setX(this.bismilahTimeline.getRect().right);
                            this.pass = false;
                            invalidate();
                            return true;
                        }
                        if (isExist(this.mIsi3adaTimeline) && left <= this.mIsi3adaTimeline.getRect().right) {
                            this.selectedEntity.setX(this.mIsi3adaTimeline.getRect().right);
                            this.pass = false;
                            invalidate();
                            return true;
                        }
                    }
                    Entity entity8 = this.selectedEntity;
                    if (entity8 instanceof EntityTrslTimeline) {
                        EntityTrslTimeline entityTrslTimeline = (EntityTrslTimeline) entity8;
                        if (entityTrslTimeline.getIndex() > 0 && (previewOrNextEntityTrslQuran4 = getPreviewOrNextEntityTrslQuran(this.entityListTrslQuran, entityTrslTimeline.getIndex() - 1, false)) != null && left <= previewOrNextEntityTrslQuran4.getRect().right) {
                            this.selectedEntity.setX(previewOrNextEntityTrslQuran4.getRect().right);
                            this.pass = false;
                            invalidate();
                            return true;
                        }
                    }
                    Entity entity9 = this.selectedEntity;
                    if ((entity9 instanceof EntityBismilahTimeline) && ((EntityBismilahTimeline) entity9) == this.bismilahTimeline && isExist(this.mIsi3adaTimeline) && left <= this.mIsi3adaTimeline.getRect().right) {
                        this.selectedEntity.setX(this.mIsi3adaTimeline.getRect().right);
                        this.pass = false;
                        invalidate();
                        return true;
                    }
                    if (this.onThink && this.pass) {
                        float f3 = this.selectedEntity.getRect().left;
                        float f4 = this.scrolled_with_zoom;
                        float f5 = f3 + f4;
                        float f6 = this.TOLERANCE_X;
                        if (f5 >= (-f6) && f5 < f6) {
                            this.onThink = false;
                            float f7 = -f4;
                            this.selectedEntity.setX(f7);
                            this.selectedEntity.updateStartTrim();
                            Entity entity10 = this.selectedEntity;
                            if (entity10 instanceof EntityAudio) {
                                entity10.setRight(f7 + entity10.getRect().width());
                            }
                            this.isCheckLineCursur = true;
                            this.startXLine = this.selectedEntity.getRect().left;
                            invalidate();
                            ITrimLineCallback iTrimLineCallback8 = this.iTrimLineCallback;
                            if (iTrimLineCallback8 != null) {
                                iTrimLineCallback8.onPlayVibration();
                            }
                            new Handler().postDelayed(new Runnable() { // from class: hazem.nurmontage.videoquran.views.TrackEntityView.4
                                @Override // java.lang.Runnable
                                public void run() {
                                    if (TrackEntityView.this.selectedEntity != null) {
                                        TrackEntityView.this.selectedEntity.setDownX(motionEvent.getX());
                                    }
                                    TrackEntityView.this.onThink = true;
                                    TrackEntityView.this.pass = false;
                                    TrackEntityView.this.isCheckLineCursur = false;
                                }
                            }, 500L);
                            return false;
                        }
                        Iterator<Pair<Entity, EntityAction>> it = this.entityList.iterator();
                        while (it.hasNext()) {
                            Pair<Entity, EntityAction> next = it.next();
                            Entity entity11 = (Entity) next.first;
                            if (entity11.getRect().top != this.selectedEntity.getRect().top && entity11 != this.selectedEntity && (next.second == EntityAction.ADD || next.second == EntityAction.SPLIT)) {
                                if (!((Entity) next.first).visible()) {
                                    continue;
                                } else {
                                    if (this.selectedEntity.getRect().left >= entity11.getRect().left - this.TOLERANCE_X && this.selectedEntity.getRect().left <= entity11.getRect().left + this.TOLERANCE_X) {
                                        this.onThink = false;
                                        this.selectedEntity.setX(entity11.getRect().left);
                                        this.selectedEntity.updateStartTrim();
                                        Entity entity12 = this.selectedEntity;
                                        if (entity12 instanceof EntityAudio) {
                                            entity12.setRight(entity11.getRect().left + this.selectedEntity.getRect().width());
                                        }
                                        this.isCheckLine = true;
                                        this.startXLine = this.selectedEntity.getRect().left;
                                        invalidate();
                                        ITrimLineCallback iTrimLineCallback9 = this.iTrimLineCallback;
                                        if (iTrimLineCallback9 != null) {
                                            iTrimLineCallback9.onPlayVibration();
                                        }
                                        new Handler().postDelayed(new Runnable() { // from class: hazem.nurmontage.videoquran.views.TrackEntityView.5
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                if (TrackEntityView.this.selectedEntity != null) {
                                                    TrackEntityView.this.selectedEntity.setDownX(motionEvent.getX());
                                                }
                                                TrackEntityView.this.onThink = true;
                                                TrackEntityView.this.pass = false;
                                                TrackEntityView.this.isCheckLine = false;
                                            }
                                        }, 500L);
                                        return false;
                                    }
                                    if (this.selectedEntity.getRect().left >= entity11.getRect().right - this.TOLERANCE_X && this.selectedEntity.getRect().left <= entity11.getRect().right + this.TOLERANCE_X) {
                                        this.onThink = false;
                                        this.selectedEntity.setX(entity11.getRect().right);
                                        Entity entity13 = this.selectedEntity;
                                        if (entity13 instanceof EntityAudio) {
                                            entity13.setRight(entity11.getRect().right + this.selectedEntity.getRect().width());
                                            this.selectedEntity.updateStartTrim();
                                        }
                                        this.isCheckLine = true;
                                        this.startXLine = this.selectedEntity.getRect().left;
                                        invalidate();
                                        ITrimLineCallback iTrimLineCallback10 = this.iTrimLineCallback;
                                        if (iTrimLineCallback10 != null) {
                                            iTrimLineCallback10.onPlayVibration();
                                        }
                                        new Handler().postDelayed(new Runnable() { // from class: hazem.nurmontage.videoquran.views.TrackEntityView.6
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                if (TrackEntityView.this.selectedEntity != null) {
                                                    TrackEntityView.this.selectedEntity.setDownX(motionEvent.getX());
                                                }
                                                TrackEntityView.this.onThink = true;
                                                TrackEntityView.this.pass = false;
                                                TrackEntityView.this.isCheckLine = false;
                                            }
                                        }, 500L);
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                    Entity entity14 = this.selectedEntity;
                    if (entity14 instanceof EntityAudio) {
                        entity14.getRect().left = left;
                        Entity entity15 = this.selectedEntity;
                        entity15.setLastLeft(entity15.getLeft() + x);
                        this.selectedEntity.updateStartTrim();
                        this.autoScrollHandler.removeCallbacks(this.autoScrollRunnable);
                        this.isAutoScroll = false;
                    } else if (z) {
                        if (entity14.getRect().left < left) {
                            if (this.selectedEntity.getRect().left + getCurrentPosition() > this.DETECT_RIGHT_MOVE) {
                                if (!this.isAutoScroll) {
                                    if (left > this.selectedEntity.getRect().left) {
                                        float f8 = this.SPEED;
                                        if (f8 < 0.0f) {
                                            this.SPEED = f8 * (-1.0f);
                                        }
                                    } else {
                                        float f9 = this.SPEED;
                                        if (f9 > 0.0f) {
                                            this.SPEED = f9 * (-1.0f);
                                        }
                                    }
                                    this.isAutoScroll = true;
                                    this.time_start = System.currentTimeMillis();
                                    this.autoScrollHandler.postDelayed(this.autoScrollRunnable, 100L);
                                } else if (left < this.selectedEntity.getRect().left && this.isAutoScroll) {
                                    this.isAutoScroll = false;
                                    this.autoScrollHandler.removeCallbacks(this.autoScrollRunnable);
                                }
                            } else if (this.isAutoScroll) {
                                this.isAutoScroll = false;
                                this.autoScrollHandler.removeCallbacks(this.autoScrollRunnable);
                            }
                        } else if (this.selectedEntity.getRect().left > 0.0f && this.selectedEntity.getRect().left + getCurrentPosition() < (-this.DETECT_LEFT_MOVE)) {
                            if (!this.isAutoScroll) {
                                float f10 = this.SPEED;
                                if (f10 < 0.0f) {
                                    this.SPEED = f10 * (-1.0f);
                                }
                                this.isAutoScroll = true;
                                this.time_start = System.currentTimeMillis();
                                this.autoScrollHandler.postDelayed(this.autoScrollRunnable, 100L);
                            } else {
                                float f11 = this.SPEED;
                                if (f11 > 0.0f) {
                                    this.SPEED = f11 * (-1.0f);
                                }
                            }
                        } else if (this.isAutoScroll) {
                            this.isAutoScroll = false;
                            this.autoScrollHandler.removeCallbacks(this.autoScrollRunnable);
                        }
                    }
                    if (!this.isAutoScroll) {
                        if (left > this.selectedEntity.getRect().left) {
                            this.selectedEntity.getRect().left = left + this.TOLERANCE_X;
                        } else {
                            this.selectedEntity.getRect().left = left - this.TOLERANCE_X;
                        }
                    }
                    float strokeWidth = this.paintCursur.getStrokeWidth() * 0.3f;
                    this.pass = this.selectedEntity.getRect().left < this.startXLine - strokeWidth || this.selectedEntity.getRect().left > this.startXLine + strokeWidth;
                    invalidate();
                } else if (this.selectedEntity.getTrim_type() == 1 && this.onThink) {
                    if (Math.abs(motionEvent.getX() - this.lasX) <= this.TOLERANCE_X) {
                        return false;
                    }
                    this.lasX = motionEvent.getX();
                    float x2 = motionEvent.getX() - this.selectedEntity.getDownX();
                    if (x2 == 0.0f) {
                        return false;
                    }
                    float right = this.selectedEntity.getRight() + x2;
                    boolean z2 = right - this.selectedEntity.getRect().left > this.max_trim;
                    if (!z2) {
                        right = this.selectedEntity.getRect().left + this.max_trim;
                    }
                    Entity entity16 = this.selectedEntity;
                    if (entity16 instanceof EntityAudio) {
                        EntityAudio entityAudio3 = (EntityAudio) entity16;
                        f = right - entity16.getRect().left;
                        float max2 = (entityAudio3.getMax() * entityAudio3.getmScaleFactor()) - (entityAudio3.getOffset_left() * entityAudio3.getmScaleFactor());
                        if (f > max2) {
                            right = this.selectedEntity.getRect().left + max2;
                        } else if (entityAudio3.getIndex() + 1 < this.entityListAudio.size() && (previewOrNextEntityAudio3 = getPreviewOrNextEntityAudio(this.entityListAudio, entityAudio3.getIndex() + 1, true)) != null && right > previewOrNextEntityAudio3.getRect().left) {
                            this.selectedEntity.getRect().right = right;
                            if (f == -1.0f) {
                                Entity entity17 = this.selectedEntity;
                                entity17.setLastRight(entity17.getRight() + x2);
                            } else {
                                Entity entity18 = this.selectedEntity;
                                entity18.setLastRight(entity18.getRect().right);
                            }
                            float width2 = previewOrNextEntityAudio3.getRect().width() + right;
                            float f12 = right - previewOrNextEntityAudio3.getRect().left;
                            previewOrNextEntityAudio3.setCurrentRect();
                            previewOrNextEntityAudio3.setX(right);
                            previewOrNextEntityAudio3.setRight(width2);
                            for (int index = entityAudio3.getIndex() + 2; index < this.entityListAudio.size(); index++) {
                                EntityAudio entityAudio4 = this.entityListAudio.get(index);
                                if (entityAudio4.visible()) {
                                    float f13 = entityAudio4.getRect().left + f12;
                                    float width3 = entityAudio4.getRect().width() + f13;
                                    entityAudio4.setCurrentRect();
                                    entityAudio4.setX(f13);
                                    entityAudio4.setRight(width3);
                                }
                            }
                            this.pass = false;
                            invalidate();
                            return true;
                        }
                    } else {
                        f = -1.0f;
                    }
                    if (this.onThink && this.pass) {
                        float f14 = this.selectedEntity.getRect().right;
                        float f15 = this.scrolled_with_zoom;
                        float f16 = f14 + f15;
                        float f17 = this.TOLERANCE_X;
                        if (f16 >= (-f17) && f16 < f17) {
                            this.onThink = false;
                            float f18 = (-f15) + f17;
                            Entity entity19 = this.selectedEntity;
                            if (entity19 instanceof EntityAudio) {
                                entity19.setX(entity19.getRect().right - this.selectedEntity.getRect().width());
                            }
                            this.selectedEntity.setRight(f18);
                            this.isCheckLineCursur = true;
                            this.startXLine = this.selectedEntity.getRect().right;
                            invalidate();
                            ITrimLineCallback iTrimLineCallback11 = this.iTrimLineCallback;
                            if (iTrimLineCallback11 != null) {
                                iTrimLineCallback11.onPlayVibration();
                            }
                            new Handler().postDelayed(new Runnable() { // from class: hazem.nurmontage.videoquran.views.TrackEntityView.7
                                @Override // java.lang.Runnable
                                public void run() {
                                    if (TrackEntityView.this.selectedEntity != null) {
                                        TrackEntityView.this.selectedEntity.setDownX(motionEvent.getX());
                                    }
                                    TrackEntityView.this.onThink = true;
                                    TrackEntityView.this.pass = false;
                                    TrackEntityView.this.isCheckLineCursur = false;
                                }
                            }, 500L);
                            return false;
                        }
                        Iterator<Pair<Entity, EntityAction>> it2 = this.entityList.iterator();
                        while (it2.hasNext()) {
                            Pair<Entity, EntityAction> next2 = it2.next();
                            Entity entity20 = (Entity) next2.first;
                            if (entity20.getRect().top != this.selectedEntity.getRect().top && entity20 != this.selectedEntity && ((next2.second == EntityAction.ADD || next2.second == EntityAction.SPLIT) && entity20.visible())) {
                                if (this.selectedEntity.getRect().right >= entity20.getRect().left - this.TOLERANCE_X && this.selectedEntity.getRect().right <= entity20.getRect().left + this.TOLERANCE_X) {
                                    this.onThink = false;
                                    this.selectedEntity.setRight(entity20.getRect().left);
                                    Entity entity21 = this.selectedEntity;
                                    if (entity21 instanceof EntityAudio) {
                                        entity21.setX(entity20.getRect().left - this.selectedEntity.getRect().width());
                                    }
                                    this.isCheckLine = true;
                                    this.startXLine = this.selectedEntity.getRect().right;
                                    invalidate();
                                    ITrimLineCallback iTrimLineCallback12 = this.iTrimLineCallback;
                                    if (iTrimLineCallback12 != null) {
                                        iTrimLineCallback12.onPlayVibration();
                                    }
                                    new Handler().postDelayed(new Runnable() { // from class: hazem.nurmontage.videoquran.views.TrackEntityView.8
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            if (TrackEntityView.this.selectedEntity != null) {
                                                TrackEntityView.this.selectedEntity.setDownX(motionEvent.getX());
                                            }
                                            TrackEntityView.this.onThink = true;
                                            TrackEntityView.this.pass = false;
                                            TrackEntityView.this.isCheckLine = false;
                                        }
                                    }, 500L);
                                    return false;
                                }
                                if (this.selectedEntity.getRect().right >= entity20.getRect().right - this.TOLERANCE_X && this.selectedEntity.getRect().right <= entity20.getRect().right + this.TOLERANCE_X) {
                                    this.onThink = false;
                                    this.selectedEntity.setRight(entity20.getRect().right);
                                    Entity entity22 = this.selectedEntity;
                                    if (entity22 instanceof EntityAudio) {
                                        entity22.setX(entity20.getRect().right - this.selectedEntity.getRect().width());
                                    }
                                    this.isCheckLine = true;
                                    this.startXLine = this.selectedEntity.getRect().right;
                                    invalidate();
                                    ITrimLineCallback iTrimLineCallback13 = this.iTrimLineCallback;
                                    if (iTrimLineCallback13 != null) {
                                        iTrimLineCallback13.onPlayVibration();
                                    }
                                    new Handler().postDelayed(new Runnable() { // from class: hazem.nurmontage.videoquran.views.TrackEntityView.9
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            if (TrackEntityView.this.selectedEntity != null) {
                                                TrackEntityView.this.selectedEntity.setDownX(motionEvent.getX());
                                            }
                                            TrackEntityView.this.onThink = true;
                                            TrackEntityView.this.pass = false;
                                            TrackEntityView.this.isCheckLine = false;
                                        }
                                    }, 500L);
                                    return false;
                                }
                            }
                        }
                    }
                    Entity entity23 = this.selectedEntity;
                    if (entity23 instanceof EntityAudio) {
                        entity23.getRect().right = right;
                        if (f == -1.0f) {
                            Entity entity24 = this.selectedEntity;
                            entity24.setLastRight(entity24.getRight() + x2);
                        } else {
                            Entity entity25 = this.selectedEntity;
                            entity25.setLastRight(entity25.getRect().right);
                        }
                        this.autoScrollHandler.removeCallbacks(this.autoScrollRunnable);
                        this.isAutoScroll = false;
                    }
                    Entity entity26 = this.selectedEntity;
                    if (entity26 instanceof EntityQuranTimeline) {
                        EntityQuranTimeline entityQuranTimeline3 = (EntityQuranTimeline) entity26;
                        if (entityQuranTimeline3.getIndex() < this.entityListQuran.size() && (previewOrNextEntityQuran3 = getPreviewOrNextEntityQuran(this.entityListQuran, entityQuranTimeline3.getIndex() + 1, true)) != null && right > previewOrNextEntityQuran3.getRect().left) {
                            float width4 = previewOrNextEntityQuran3.getRect().width() + right;
                            float f19 = right - previewOrNextEntityQuran3.getRect().left;
                            previewOrNextEntityQuran3.setCurrentRect();
                            previewOrNextEntityQuran3.setX(right);
                            previewOrNextEntityQuran3.setRight(width4);
                            for (int index2 = entityQuranTimeline3.getIndex() + 2; index2 < this.entityListQuran.size(); index2++) {
                                EntityQuranTimeline entityQuranTimeline4 = this.entityListQuran.get(index2);
                                if (entityQuranTimeline4.visible()) {
                                    float f20 = entityQuranTimeline4.getRect().left + f19;
                                    float width5 = entityQuranTimeline4.getRect().width() + f20;
                                    entityQuranTimeline4.setCurrentRect();
                                    entityQuranTimeline4.setX(f20);
                                    entityQuranTimeline4.setRight(width5);
                                }
                            }
                            this.pass = false;
                            this.selectedEntity.getRect().right = right;
                            invalidate();
                            return true;
                        }
                        if (z2) {
                            if (this.selectedEntity.getRect().right < right) {
                                if (this.selectedEntity.getRect().right + getCurrentPosition() > this.DETECT_RIGHT_MOVE) {
                                    if (!this.isAutoScroll) {
                                        if (right > this.selectedEntity.getRect().right) {
                                            float f21 = this.SPEED;
                                            if (f21 < 0.0f) {
                                                this.SPEED = f21 * (-1.0f);
                                            }
                                        } else {
                                            float f22 = this.SPEED;
                                            if (f22 > 0.0f) {
                                                this.SPEED = f22 * (-1.0f);
                                            }
                                        }
                                        this.isAutoScroll = true;
                                        this.time_start = System.currentTimeMillis();
                                        this.autoScrollHandler.postDelayed(this.autoScrollRunnable, 100L);
                                    } else if (right < this.selectedEntity.getRect().right && this.isAutoScroll) {
                                        this.isAutoScroll = false;
                                        this.autoScrollHandler.removeCallbacks(this.autoScrollRunnable);
                                    }
                                } else if (this.isAutoScroll) {
                                    this.isAutoScroll = false;
                                    this.autoScrollHandler.removeCallbacks(this.autoScrollRunnable);
                                }
                            } else if (this.selectedEntity.getRect().right > 0.0f && this.selectedEntity.getRect().right + getCurrentPosition() < (-this.DETECT_LEFT_MOVE)) {
                                if (!this.isAutoScroll) {
                                    float f23 = this.SPEED;
                                    if (f23 < 0.0f) {
                                        this.SPEED = f23 * (-1.0f);
                                    }
                                    this.isAutoScroll = true;
                                    this.time_start = System.currentTimeMillis();
                                    this.autoScrollHandler.postDelayed(this.autoScrollRunnable, 100L);
                                } else {
                                    float f24 = this.SPEED;
                                    if (f24 > 0.0f) {
                                        this.SPEED = f24 * (-1.0f);
                                    }
                                }
                            } else if (this.isAutoScroll) {
                                this.isAutoScroll = false;
                                this.autoScrollHandler.removeCallbacks(this.autoScrollRunnable);
                            }
                        }
                    }
                    Entity entity27 = this.selectedEntity;
                    if (entity27 instanceof EntityTrslTimeline) {
                        EntityTrslTimeline entityTrslTimeline2 = (EntityTrslTimeline) entity27;
                        if (entityTrslTimeline2.getIndex() < this.entityListTrslQuran.size() && (previewOrNextEntityTrslQuran3 = getPreviewOrNextEntityTrslQuran(this.entityListTrslQuran, entityTrslTimeline2.getIndex() + 1, true)) != null && right > previewOrNextEntityTrslQuran3.getRect().left) {
                            float width6 = previewOrNextEntityTrslQuran3.getRect().width() + right;
                            float f25 = right - previewOrNextEntityTrslQuran3.getRect().left;
                            previewOrNextEntityTrslQuran3.setCurrentRect();
                            previewOrNextEntityTrslQuran3.setX(right);
                            previewOrNextEntityTrslQuran3.setRight(width6);
                            for (int index3 = entityTrslTimeline2.getIndex() + 2; index3 < this.entityListTrslQuran.size(); index3++) {
                                EntityTrslTimeline entityTrslTimeline3 = this.entityListTrslQuran.get(index3);
                                if (entityTrslTimeline3.visible()) {
                                    float f26 = entityTrslTimeline3.getRect().left + f25;
                                    float width7 = entityTrslTimeline3.getRect().width() + f26;
                                    entityTrslTimeline3.setCurrentRect();
                                    entityTrslTimeline3.setX(f26);
                                    entityTrslTimeline3.setRight(width7);
                                }
                            }
                            this.pass = false;
                            this.selectedEntity.getRect().right = right;
                            invalidate();
                            return true;
                        }
                        if (z2) {
                            if (this.selectedEntity.getRect().right < right) {
                                if (this.selectedEntity.getRect().right + getCurrentPosition() > this.DETECT_RIGHT_MOVE) {
                                    if (!this.isAutoScroll) {
                                        if (right > this.selectedEntity.getRect().right) {
                                            float f27 = this.SPEED;
                                            if (f27 < 0.0f) {
                                                this.SPEED = f27 * (-1.0f);
                                            }
                                        } else {
                                            float f28 = this.SPEED;
                                            if (f28 > 0.0f) {
                                                this.SPEED = f28 * (-1.0f);
                                            }
                                        }
                                        this.isAutoScroll = true;
                                        this.time_start = System.currentTimeMillis();
                                        this.autoScrollHandler.postDelayed(this.autoScrollRunnable, 100L);
                                    } else if (right < this.selectedEntity.getRect().right && this.isAutoScroll) {
                                        this.isAutoScroll = false;
                                        this.autoScrollHandler.removeCallbacks(this.autoScrollRunnable);
                                    }
                                } else if (this.isAutoScroll) {
                                    this.isAutoScroll = false;
                                    this.autoScrollHandler.removeCallbacks(this.autoScrollRunnable);
                                }
                            } else if (this.selectedEntity.getRect().right > 0.0f && this.selectedEntity.getRect().right + getCurrentPosition() < (-this.DETECT_LEFT_MOVE)) {
                                if (!this.isAutoScroll) {
                                    float f29 = this.SPEED;
                                    if (f29 < 0.0f) {
                                        this.SPEED = f29 * (-1.0f);
                                    }
                                    this.isAutoScroll = true;
                                    this.time_start = System.currentTimeMillis();
                                    this.autoScrollHandler.postDelayed(this.autoScrollRunnable, 100L);
                                } else {
                                    float f30 = this.SPEED;
                                    if (f30 > 0.0f) {
                                        this.SPEED = f30 * (-1.0f);
                                    }
                                }
                            } else if (this.isAutoScroll) {
                                this.isAutoScroll = false;
                                this.autoScrollHandler.removeCallbacks(this.autoScrollRunnable);
                            }
                        }
                    }
                    Entity entity28 = this.selectedEntity;
                    if (entity28 instanceof EntityBismilahTimeline) {
                        EntityBismilahTimeline entityBismilahTimeline = (EntityBismilahTimeline) entity28;
                        if (entityBismilahTimeline == this.mIsi3adaTimeline && isExist(this.bismilahTimeline) && right >= this.bismilahTimeline.getRect().left) {
                            float f31 = right - this.bismilahTimeline.getRect().left;
                            float width8 = this.bismilahTimeline.getRect().width() + right;
                            this.bismilahTimeline.setCurrentRect();
                            this.bismilahTimeline.setX(right);
                            this.bismilahTimeline.setRight(width8);
                            for (int index4 = this.bismilahTimeline.getIndex(); index4 < this.entityListQuran.size(); index4++) {
                                EntityQuranTimeline entityQuranTimeline5 = this.entityListQuran.get(index4);
                                if (entityQuranTimeline5.visible()) {
                                    float f32 = entityQuranTimeline5.getRect().left + f31;
                                    float width9 = entityQuranTimeline5.getRect().width() + f32;
                                    entityQuranTimeline5.setCurrentRect();
                                    entityQuranTimeline5.setX(f32);
                                    entityQuranTimeline5.setRight(width9);
                                }
                            }
                            this.pass = false;
                            this.selectedEntity.getRect().right = right;
                            invalidate();
                            return true;
                        }
                        EntityQuranTimeline previewOrNextEntityQuran5 = getPreviewOrNextEntityQuran(this.entityListQuran, entityBismilahTimeline.getIndex(), true);
                        if (previewOrNextEntityQuran5 != null && right >= previewOrNextEntityQuran5.getRect().left) {
                            float f33 = right - previewOrNextEntityQuran5.getRect().left;
                            float width10 = previewOrNextEntityQuran5.getRect().width() + right;
                            previewOrNextEntityQuran5.setCurrentRect();
                            previewOrNextEntityQuran5.setX(right);
                            previewOrNextEntityQuran5.setRight(width10);
                            for (int index5 = entityBismilahTimeline.getIndex() + 1; index5 < this.entityListQuran.size(); index5++) {
                                EntityQuranTimeline entityQuranTimeline6 = this.entityListQuran.get(index5);
                                if (entityQuranTimeline6.visible()) {
                                    float f34 = entityQuranTimeline6.getRect().left + f33;
                                    float width11 = entityQuranTimeline6.getRect().width() + f34;
                                    entityQuranTimeline6.setCurrentRect();
                                    entityQuranTimeline6.setX(f34);
                                    entityQuranTimeline6.setRight(width11);
                                }
                            }
                            this.pass = false;
                            this.selectedEntity.getRect().right = right;
                            invalidate();
                            return true;
                        }
                        if (z2) {
                            if (this.selectedEntity.getRect().right < right) {
                                if (this.selectedEntity.getRect().right + getCurrentPosition() > this.DETECT_RIGHT_MOVE) {
                                    if (!this.isAutoScroll) {
                                        if (right > this.selectedEntity.getRect().right) {
                                            float f35 = this.SPEED;
                                            if (f35 < 0.0f) {
                                                this.SPEED = f35 * (-1.0f);
                                            }
                                        } else {
                                            float f36 = this.SPEED;
                                            if (f36 > 0.0f) {
                                                this.SPEED = f36 * (-1.0f);
                                            }
                                        }
                                        this.isAutoScroll = true;
                                        this.time_start = System.currentTimeMillis();
                                        this.autoScrollHandler.postDelayed(this.autoScrollRunnable, 100L);
                                    } else if (right < this.selectedEntity.getRect().right && this.isAutoScroll) {
                                        this.isAutoScroll = false;
                                        this.autoScrollHandler.removeCallbacks(this.autoScrollRunnable);
                                    }
                                } else if (this.isAutoScroll) {
                                    this.isAutoScroll = false;
                                    this.autoScrollHandler.removeCallbacks(this.autoScrollRunnable);
                                }
                            } else if (this.selectedEntity.getRect().right > 0.0f && this.selectedEntity.getRect().right + getCurrentPosition() < (-this.DETECT_LEFT_MOVE)) {
                                if (!this.isAutoScroll) {
                                    float f37 = this.SPEED;
                                    if (f37 < 0.0f) {
                                        this.SPEED = f37 * (-1.0f);
                                    }
                                    this.isAutoScroll = true;
                                    this.time_start = System.currentTimeMillis();
                                    this.autoScrollHandler.postDelayed(this.autoScrollRunnable, 100L);
                                } else {
                                    float f38 = this.SPEED;
                                    if (f38 > 0.0f) {
                                        this.SPEED = f38 * (-1.0f);
                                    }
                                }
                            } else if (this.isAutoScroll) {
                                this.isAutoScroll = false;
                                this.autoScrollHandler.removeCallbacks(this.autoScrollRunnable);
                            }
                        }
                    }
                    if (!this.isAutoScroll) {
                        if (right > this.selectedEntity.getRect().right) {
                            this.selectedEntity.getRect().right = right + this.TOLERANCE_X;
                        } else {
                            this.selectedEntity.getRect().right = right - this.TOLERANCE_X;
                        }
                    }
                    float strokeWidth2 = this.paintCursur.getStrokeWidth() * 0.3f;
                    this.pass = this.selectedEntity.getRect().right < this.startXLine - strokeWidth2 || this.selectedEntity.getRect().right > this.startXLine + strokeWidth2;
                    invalidate();
                }
            } else {
                if (Math.abs(motionEvent.getX() - this.lasX) <= this.TOLERANCE_X) {
                    return false;
                }
                this.lasX = motionEvent.getX();
                float x3 = motionEvent.getX() - this.selectedEntity.getDownX();
                if (x3 == 0.0f) {
                    return false;
                }
                float width12 = this.selectedEntity.getRect().width();
                float left2 = x3 + this.selectedEntity.getLeft();
                if (left2 < 0.0f) {
                    left2 = 0.0f;
                }
                float f39 = left2 + width12;
                Entity entity29 = this.selectedEntity;
                if (entity29 instanceof EntityQuranTimeline) {
                    EntityQuranTimeline entityQuranTimeline7 = (EntityQuranTimeline) entity29;
                    if (entityQuranTimeline7.getIndex() > 0 && (previewOrNextEntityQuran2 = getPreviewOrNextEntityQuran(this.entityListQuran, entityQuranTimeline7.getIndex() - 1, false)) != null && left2 <= previewOrNextEntityQuran2.getRect().right) {
                        this.selectedEntity.setX(previewOrNextEntityQuran2.getRect().right);
                        this.selectedEntity.setRight(previewOrNextEntityQuran2.getRect().right + width12);
                        this.pass = false;
                        invalidate();
                        return true;
                    }
                    if (entityQuranTimeline7.getIndex() + 1 < getEntityListQuran().size() && (previewOrNextEntityQuran = getPreviewOrNextEntityQuran(this.entityListQuran, entityQuranTimeline7.getIndex() + 1, true)) != null && f39 >= previewOrNextEntityQuran.getRect().left) {
                        this.selectedEntity.setX(previewOrNextEntityQuran.getRect().left - width12);
                        this.selectedEntity.setRight(previewOrNextEntityQuran.getRect().left);
                        this.pass = false;
                        invalidate();
                        return true;
                    }
                    if (isExist(this.bismilahTimeline) && left2 <= this.bismilahTimeline.getRect().right) {
                        this.selectedEntity.setX(this.bismilahTimeline.getRect().right);
                        this.selectedEntity.setRight(this.bismilahTimeline.getRect().right + width12);
                        this.pass = false;
                        invalidate();
                        return true;
                    }
                    if (isExist(this.mIsi3adaTimeline) && left2 <= this.mIsi3adaTimeline.getRect().right) {
                        this.selectedEntity.setX(this.mIsi3adaTimeline.getRect().right);
                        this.selectedEntity.setRight(this.mIsi3adaTimeline.getRect().right + width12);
                        this.pass = false;
                        invalidate();
                        return true;
                    }
                }
                Entity entity30 = this.selectedEntity;
                if (entity30 instanceof EntityTrslTimeline) {
                    EntityTrslTimeline entityTrslTimeline4 = (EntityTrslTimeline) entity30;
                    if (entityTrslTimeline4.getIndex() > 0 && (previewOrNextEntityTrslQuran2 = getPreviewOrNextEntityTrslQuran(this.entityListTrslQuran, entityTrslTimeline4.getIndex() - 1, false)) != null && left2 <= previewOrNextEntityTrslQuran2.getRect().right) {
                        this.selectedEntity.setX(previewOrNextEntityTrslQuran2.getRect().right);
                        this.selectedEntity.setRight(previewOrNextEntityTrslQuran2.getRect().right + width12);
                        this.pass = false;
                        invalidate();
                        return true;
                    }
                    if (entityTrslTimeline4.getIndex() + 1 < getEntityListTrslQuran().size() && (previewOrNextEntityTrslQuran = getPreviewOrNextEntityTrslQuran(this.entityListTrslQuran, entityTrslTimeline4.getIndex() + 1, true)) != null && f39 >= previewOrNextEntityTrslQuran.getRect().left) {
                        this.selectedEntity.setX(previewOrNextEntityTrslQuran.getRect().left - width12);
                        this.selectedEntity.setRight(previewOrNextEntityTrslQuran.getRect().left);
                        this.pass = false;
                        invalidate();
                        return true;
                    }
                }
                Entity entity31 = this.selectedEntity;
                if (entity31 instanceof EntityBismilahTimeline) {
                    EntityBismilahTimeline entityBismilahTimeline2 = (EntityBismilahTimeline) entity31;
                    if (entityBismilahTimeline2 == this.mIsi3adaTimeline && isExist(this.bismilahTimeline) && f39 >= this.bismilahTimeline.getRect().left) {
                        this.selectedEntity.setX(this.bismilahTimeline.getRect().left - width12);
                        this.selectedEntity.setRight(this.bismilahTimeline.getRect().left);
                        this.pass = false;
                        invalidate();
                        return true;
                    }
                    if (entityBismilahTimeline2 == this.bismilahTimeline && isExist(this.mIsi3adaTimeline) && left2 <= this.mIsi3adaTimeline.getRect().right) {
                        this.selectedEntity.setX(this.mIsi3adaTimeline.getRect().right);
                        this.selectedEntity.setRight(this.mIsi3adaTimeline.getRect().right + width12);
                        this.pass = false;
                        invalidate();
                        return true;
                    }
                    EntityQuranTimeline previewOrNextEntityQuran6 = getPreviewOrNextEntityQuran(this.entityListQuran, entityBismilahTimeline2.getIndex(), true);
                    if (previewOrNextEntityQuran6 != null && f39 >= previewOrNextEntityQuran6.getRect().left) {
                        this.selectedEntity.setX(previewOrNextEntityQuran6.getRect().left - width12);
                        this.selectedEntity.setRight(previewOrNextEntityQuran6.getRect().left);
                        this.pass = false;
                        invalidate();
                        return true;
                    }
                }
                Entity entity32 = this.selectedEntity;
                if (entity32 instanceof EntityAudio) {
                    EntityAudio entityAudio5 = (EntityAudio) entity32;
                    if (entityAudio5.getIndex() > 0 && (previewOrNextEntityAudio2 = getPreviewOrNextEntityAudio(this.entityListAudio, entityAudio5.getIndex() - 1, false)) != null && left2 <= previewOrNextEntityAudio2.getRect().right) {
                        this.selectedEntity.setX(previewOrNextEntityAudio2.getRect().right);
                        this.selectedEntity.setRight(previewOrNextEntityAudio2.getRect().right + width12);
                        this.pass = false;
                        invalidate();
                        return true;
                    }
                    if (entityAudio5.getIndex() + 1 < getEntityListAudio().size() && (previewOrNextEntityAudio = getPreviewOrNextEntityAudio(this.entityListAudio, entityAudio5.getIndex() + 1, true)) != null && f39 >= previewOrNextEntityAudio.getRect().left) {
                        this.selectedEntity.setX(previewOrNextEntityAudio.getRect().left - width12);
                        this.selectedEntity.setRight(previewOrNextEntityAudio.getRect().left);
                        this.pass = false;
                        invalidate();
                        return true;
                    }
                }
                if (this.selectedEntity.getRect().right < f39) {
                    if (this.selectedEntity.getRect().left + getCurrentPosition() > this.DETECT_RIGHT_MOVE) {
                        if (!this.isAutoMove) {
                            float f40 = this.SPEED;
                            if (f40 > 0.0f) {
                                this.SPEED = f40 * (-1.0f);
                            }
                            this.isAutoMove = true;
                            this.time_start = System.currentTimeMillis();
                            this.autoScrollHandler.postDelayed(this.autoMoveRunnable, 100L);
                        } else {
                            float f41 = this.SPEED;
                            if (f41 < 0.0f) {
                                this.SPEED = f41 * (-1.0f);
                            }
                        }
                    } else if (this.isAutoMove) {
                        this.isAutoMove = false;
                        this.autoScrollHandler.removeCallbacks(this.autoMoveRunnable);
                    }
                } else if (this.selectedEntity.getRect().left > 0.0f && this.selectedEntity.getRect().left + getCurrentPosition() < (-this.DETECT_LEFT_MOVE)) {
                    if (!this.isAutoMove) {
                        float f42 = this.SPEED;
                        if (f42 < 0.0f) {
                            this.SPEED = f42 * (-1.0f);
                        }
                        this.isAutoMove = true;
                        this.time_start = System.currentTimeMillis();
                        this.autoScrollHandler.postDelayed(this.autoMoveRunnable, 100L);
                    } else {
                        float f43 = this.SPEED;
                        if (f43 > 0.0f) {
                            this.SPEED = f43 * (-1.0f);
                        }
                    }
                } else if (this.isAutoMove) {
                    this.isAutoMove = false;
                    this.autoScrollHandler.removeCallbacks(this.autoMoveRunnable);
                }
                if (!this.isAutoMove) {
                    this.selectedEntity.getRect().left = left2;
                    this.selectedEntity.getRect().right = f39;
                    this.isMove = true;
                }
                this.pass = this.selectedEntity.getRect().left < (-this.TOLERANCE_X) || this.selectedEntity.getRect().left >= this.TOLERANCE_X;
                invalidate();
            }
        }
        return this.gestureDetector.onTouchEvent(motionEvent);
    }

    public void flingY() {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, "FlingY", this.target, 0.0f);
        this.objectAnimator = ofFloat;
        ofFloat.setDuration(1000L);
        this.objectAnimator.start();
    }

    public void setFlingY(float f) {
        this.target = f;
        if (f <= 0.0f) {
            if (this.y + this.mScrollY >= getHeight()) {
                float f2 = this.mScrollY + (this.target / 100.0f);
                this.mScrollY = f2;
                if (this.y + f2 < getHeight()) {
                    this.mScrollY = getHeight() - this.y;
                }
                invalidate();
                return;
            }
            return;
        }
        float f3 = this.mScrollY;
        if (f3 < 0.0f) {
            float f4 = f3 + (f / 100.0f);
            this.mScrollY = f4;
            if (f4 > 0.0f) {
                this.mScrollY = 0.0f;
            }
            invalidate();
        }
    }

    public EntityAudio getPreviewOrNextEntityAudio(List<EntityAudio> list, int i, boolean z) {
        if (z) {
            while (i < list.size()) {
                if (list.get(i).visible()) {
                    return list.get(i);
                }
                i++;
            }
            return null;
        }
        while (i >= 0 && i < list.size()) {
            if (list.get(i).visible()) {
                return list.get(i);
            }
            i--;
        }
        return null;
    }

    public EntityQuranTimeline getPreviewOrNextEntityQuran(List<EntityQuranTimeline> list, int i, boolean z) {
        if (z) {
            while (i < list.size()) {
                if (list.get(i).visible()) {
                    return list.get(i);
                }
                i++;
            }
            return null;
        }
        while (i >= 0 && i < list.size()) {
            if (list.get(i).visible()) {
                return list.get(i);
            }
            i--;
        }
        return null;
    }

    public EntityTrslTimeline getPreviewOrNextEntityTrslQuran(List<EntityTrslTimeline> list, int i, boolean z) {
        if (z) {
            while (i < list.size()) {
                if (list.get(i).visible()) {
                    return list.get(i);
                }
                i++;
            }
            return null;
        }
        while (i >= 0 && i < list.size()) {
            if (list.get(i).visible()) {
                return list.get(i);
            }
            i--;
        }
        return null;
    }

    public EntityAudio getAudio() {
        for (int size = this.entityListAudio.size() - 1; size >= 0; size--) {
            EntityAudio entityAudio = this.entityListAudio.get(size);
            if (entityAudio.visible()) {
                return entityAudio;
            }
        }
        return null;
    }

    public EntityQuranTimeline getLastAyaQuran() {
        if (this.entityListQuran.isEmpty()) {
            return null;
        }
        return this.entityListQuran.get(this.entityListQuran.size() - 1);
    }

    public EntityQuranTimeline getQuran() {
        for (int size = this.entityListQuran.size() - 1; size >= 0; size--) {
            EntityQuranTimeline entityQuranTimeline = this.entityListQuran.get(size);
            if (entityQuranTimeline.visible()) {
                return entityQuranTimeline;
            }
        }
        return null;
    }

    public EntityTrslTimeline getTrslQuran() {
        for (int size = this.entityListTrslQuran.size() - 1; size >= 0; size--) {
            EntityTrslTimeline entityTrslTimeline = this.entityListTrslQuran.get(size);
            if (entityTrslTimeline.visible()) {
                return entityTrslTimeline;
            }
        }
        return null;
    }

    public void calculMaxTime() {
        float f;
        float f2;
        float scaleFactor;
        EntityAudio audio = getAudio();
        float f3 = 0.0f;
        if (audio == null || audio.getRect() == null) {
            f = 0.0f;
        } else if (audio.getmScaleFactor() != getScaleFactor()) {
            f = (audio.getRect().right / audio.getmScaleFactor()) * getScaleFactor();
        } else {
            f = audio.getRect().right;
        }
        EntityQuranTimeline quran = getQuran();
        if (quran == null || quran.getRect() == null) {
            if (isExist(this.bismilahTimeline)) {
                if (this.bismilahTimeline.getmScaleFactor() != getScaleFactor()) {
                    f2 = this.bismilahTimeline.getRect().right / this.bismilahTimeline.getmScaleFactor();
                    scaleFactor = getScaleFactor();
                    f3 = scaleFactor * f2;
                } else {
                    f3 = this.bismilahTimeline.getRect().right;
                }
            } else if (isExist(this.mIsi3adaTimeline)) {
                if (this.mIsi3adaTimeline.getmScaleFactor() != getScaleFactor()) {
                    f2 = this.mIsi3adaTimeline.getRect().right / this.mIsi3adaTimeline.getmScaleFactor();
                    scaleFactor = getScaleFactor();
                    f3 = scaleFactor * f2;
                } else {
                    f3 = this.mIsi3adaTimeline.getRect().right;
                }
            }
        } else if (quran.getmScaleFactor() != getScaleFactor()) {
            f3 = (quran.getRect().right / quran.getmScaleFactor()) * getScaleFactor();
        } else {
            f3 = quran.getRect().right;
        }
        EntityTrslTimeline trslQuran = getTrslQuran();
        if (trslQuran != null && trslQuran.getRect() != null) {
            if (trslQuran.getmScaleFactor() != getScaleFactor()) {
                f3 = Math.max((trslQuran.getRect().right / trslQuran.getmScaleFactor()) * getScaleFactor(), f3);
            } else {
                f3 = Math.max(trslQuran.getRect().right, f3);
            }
        }
        float max = Math.max(f3, f);
        int second_in_screen = (int) ((max / getSecond_in_screen()) * 1000.0f);
        this.maxTime = second_in_screen;
        this.duration = (int) (second_in_screen / 1000.0f);
        this.timeLineW = max / this.scaleFactor;
    }

    public void update_current_cursur_position(int i) {
        this.current_cursur_position = i;
    }

    public void setCurrent_cursur_position(int i) {
        this.current_cursur_position = i;
    }

    public float getTimeLineW() {
        return this.timeLineW;
    }

    public void unselectEntity() {
        Entity entity = this.selectedEntity;
        if (entity != null) {
            entity.setSelect(false);
            this.selectedEntity = null;
        }
    }

    public int getMaxTime() {
        return this.maxTime;
    }

    public void updateCursur(int i) {
        this.current_cursur_position = i;
        float f = ((-i) * this.second_in_screen) / 1000.0f;
        this.currentPosition = f;
        this.scrolled_with_zoom = f * this.scaleFactor;
        invalidate();
    }

    public void setPosCursur(int i) {
        this.current_cursur_position = i;
        float f = ((-i) * this.second_in_screen) / 1000.0f;
        this.currentPosition = f;
        this.scrolled_with_zoom = f * this.scaleFactor;
        invalidate();
    }

    public void updateCursur(float f) {
        float f2 = -f;
        this.currentPosition = f2;
        this.scrolled_with_zoom = f2 * this.scaleFactor;
        invalidate();
    }

    public void setRedoUndo(ImageButton imageButton, ImageButton imageButton2) {
        this.btn_redo = imageButton;
        this.btn_undo = imageButton2;
    }

    public void undo() {
        try {
            if (this.entityList.isEmpty()) {
                return;
            }
            Pair<Entity, EntityAction> pop = this.entityList.pop();
            if (pop.second == EntityAction.DELETE) {
                ((Entity) pop.first).visible(true);
                if (this.iTrimLineCallback != null && ((Entity) pop.first).getEntityView() != null) {
                    this.iTrimLineCallback.onDelete(((Entity) pop.first).getEntityView());
                }
            } else if (pop.second == EntityAction.DELETE_MULTIPLE) {
                if (this.iTrimLineCallback != null) {
                    ((Entity) pop.first).visible(true);
                    if (((Entity) pop.first).getEntityView() != null) {
                        this.iTrimLineCallback.onDelete(((Entity) pop.first).getEntityView());
                    }
                    if (((Entity) pop.first).getEntitiesGroup() != null) {
                        for (Entity entity : ((Entity) pop.first).getEntitiesGroup()) {
                            entity.visible(true);
                            if (entity.getEntityView() != null) {
                                this.iTrimLineCallback.onDelete(entity.getEntityView());
                            }
                        }
                    }
                }
            } else if (pop.second == EntityAction.SPLIT) {
                ((Entity) pop.first).visible(false);
                this.undoEntityList.push(pop);
                pop = this.entityList.pop();
                ((Entity) pop.first).undo();
            } else if (pop.second != EntityAction.ADD) {
                ((Entity) pop.first).undo();
            } else {
                ((Entity) pop.first).visible(false);
                if (((Entity) pop.first).getEntityView() != null) {
                    ((Entity) pop.first).getEntityView().setVisible(false);
                    ITrimLineCallback iTrimLineCallback = this.iTrimLineCallback;
                    if (iTrimLineCallback != null) {
                        iTrimLineCallback.onUpdate();
                    }
                }
            }
            this.undoEntityList.push(pop);
            if (this.iTrimLineCallback != null) {
                if (this.entityList.isEmpty()) {
                    this.iTrimLineCallback.enableUndo(false);
                }
                this.iTrimLineCallback.enableRedo(true);
                this.iTrimLineCallback.onUpdateTime();
                Entity entity2 = this.selectedEntity;
                if (entity2 != null && !entity2.visible()) {
                    unselectEntity();
                    this.iTrimLineCallback.onEmptySelect();
                }
            }
            invalidate();
        } catch (Exception e) {
            Log.e("m_undo_expection", "" + e.getMessage());
        }
    }

    public void redo() {
        try {
            if (this.undoEntityList.isEmpty()) {
                return;
            }
            Pair<Entity, EntityAction> pop = this.undoEntityList.pop();
            if (pop.second == EntityAction.DELETE) {
                ((Entity) pop.first).visible(false);
                if (this.iTrimLineCallback != null && ((Entity) pop.first).getEntityView() != null) {
                    this.iTrimLineCallback.onDelete(((Entity) pop.first).getEntityView());
                }
            } else if (pop.second == EntityAction.DELETE_MULTIPLE) {
                if (this.iTrimLineCallback != null) {
                    ((Entity) pop.first).visible(false);
                    if (((Entity) pop.first).getEntityView() != null) {
                        this.iTrimLineCallback.onDelete(((Entity) pop.first).getEntityView());
                    }
                    if (((Entity) pop.first).getEntitiesGroup() != null) {
                        for (Entity entity : ((Entity) pop.first).getEntitiesGroup()) {
                            entity.visible(false);
                            if (entity.getEntityView() != null) {
                                this.iTrimLineCallback.onDelete(entity.getEntityView());
                            }
                        }
                    }
                }
            } else if (pop.second == EntityAction.SPLIT) {
                ((Entity) pop.first).redo();
                this.entityList.push(pop);
                pop = this.undoEntityList.pop();
                ((Entity) pop.first).visible(true);
            } else if (pop.second != EntityAction.ADD) {
                ((Entity) pop.first).redo();
                ((Entity) pop.first).visible(true);
            } else {
                ((Entity) pop.first).visible(true);
            }
            this.entityList.push(pop);
            if (this.iTrimLineCallback != null) {
                if (this.undoEntityList.isEmpty()) {
                    this.iTrimLineCallback.enableRedo(false);
                }
                this.iTrimLineCallback.enableUndo(true);
                this.iTrimLineCallback.onUpdateTime();
                Entity entity2 = this.selectedEntity;
                if (entity2 != null && !entity2.visible()) {
                    unselectEntity();
                    this.iTrimLineCallback.onEmptySelect();
                }
            }
            invalidate();
        } catch (Exception e) {
            Log.e("m_redo_expection", "" + e.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean handleItemInteraction(float f, float f2) {
        int i;
        boolean z;
        RectF rectF = this.rectFItemQuran;
        boolean z2 = rectF != null && rectF.contains(f, f2);
        RectF rectF2 = this.rectItemAudio;
        boolean z3 = rectF2 != null && rectF2.contains(f, f2);
        RectF rectF3 = this.rectFItemTrslQuran;
        boolean z4 = rectF3 != null && rectF3.contains(f, f2);
        if (z2 || z3 || z4) {
            Entity entity = this.selectedEntity;
            if (entity != null) {
                entity.setSelect(false);
            }
            int processQuranItemsSelection = z2 ? processQuranItemsSelection() : 0;
            if (z3) {
                processQuranItemsSelection += processAudioItemsSelection();
            }
            if (z4) {
                processQuranItemsSelection += processTrslQuranItemsSelection();
            }
            i = processQuranItemsSelection;
            z = true;
        } else {
            z = deselectAllQuranItems() || deselectAllAudioItems() || deselectAllTrslQuranItems();
            i = 0;
        }
        if (z) {
            ITrimLineCallback iTrimLineCallback = this.iTrimLineCallback;
            if (iTrimLineCallback != null && (z2 || z3 || z4)) {
                this.selectedEntity = null;
                iTrimLineCallback.onSelectMultiple(i);
            }
            invalidate();
        }
        return z2 || z3 || z4;
    }

    private int processQuranItemsSelection() {
        List<EntityQuranTimeline> list = this.entityListQuran;
        int i = 0;
        if (list == null) {
            return 0;
        }
        for (EntityQuranTimeline entityQuranTimeline : list) {
            if (entityQuranTimeline.visible()) {
                boolean isSelect = entityQuranTimeline.isSelect();
                entityQuranTimeline.setSelect(!isSelect);
                entityQuranTimeline.setSelectMultiple(!isSelect);
                if (entityQuranTimeline.isSelect()) {
                    i++;
                }
            }
        }
        if (isExist(this.bismilahTimeline)) {
            boolean isSelect2 = this.bismilahTimeline.isSelect();
            this.bismilahTimeline.setSelect(!isSelect2);
            this.bismilahTimeline.setSelectMultiple(!isSelect2);
            if (this.bismilahTimeline.isSelect()) {
                i++;
            }
        }
        if (isExist(this.mIsi3adaTimeline)) {
            boolean isSelect3 = this.mIsi3adaTimeline.isSelect();
            this.mIsi3adaTimeline.setSelect(!isSelect3);
            this.mIsi3adaTimeline.setSelectMultiple(!isSelect3);
            if (this.mIsi3adaTimeline.isSelect()) {
                i++;
            }
        }
        if (i > 0) {
            this.clr_btn_quran = -794718;
        } else {
            this.clr_btn_quran = -13421771;
        }
        return i;
    }

    private int processTrslQuranItemsSelection() {
        List<EntityTrslTimeline> list = this.entityListTrslQuran;
        int i = 0;
        if (list == null) {
            return 0;
        }
        for (EntityTrslTimeline entityTrslTimeline : list) {
            if (entityTrslTimeline.visible()) {
                boolean isSelect = entityTrslTimeline.isSelect();
                entityTrslTimeline.setSelect(!isSelect);
                entityTrslTimeline.setSelectMultiple(!isSelect);
                if (entityTrslTimeline.isSelect()) {
                    i++;
                }
            }
        }
        if (i > 0) {
            this.clr_btn_trsl = -794718;
        } else {
            this.clr_btn_trsl = -13421771;
        }
        return i;
    }

    private int processAudioItemsSelection() {
        List<EntityAudio> list = this.entityListAudio;
        int i = 0;
        if (list == null) {
            return 0;
        }
        for (EntityAudio entityAudio : list) {
            if (entityAudio.visible()) {
                boolean isSelect = entityAudio.isSelect();
                entityAudio.setSelect(!isSelect);
                entityAudio.setSelectMultiple(!isSelect);
                if (entityAudio.isSelect()) {
                    i++;
                }
            }
        }
        if (i > 0) {
            this.clr_btn_audio = -794718;
        } else {
            this.clr_btn_audio = -13421771;
        }
        return i;
    }

    private boolean deselectAllQuranItems() {
        boolean z;
        if (isExist(this.bismilahTimeline) && this.bismilahTimeline.isSelect()) {
            this.bismilahTimeline.setSelect(false);
            this.bismilahTimeline.setSelectMultiple(false);
            z = true;
        } else {
            z = false;
        }
        if (isExist(this.mIsi3adaTimeline) && this.mIsi3adaTimeline.isSelect()) {
            this.mIsi3adaTimeline.setSelect(false);
            this.mIsi3adaTimeline.setSelectMultiple(false);
            z = true;
        }
        List<EntityQuranTimeline> list = this.entityListQuran;
        if (list == null) {
            return z;
        }
        for (EntityQuranTimeline entityQuranTimeline : list) {
            if (entityQuranTimeline.visible() && entityQuranTimeline.isSelect()) {
                entityQuranTimeline.setSelect(false);
                entityQuranTimeline.setSelectMultiple(false);
                z = true;
            }
        }
        if (z) {
            this.clr_btn_quran = -13421771;
        }
        return z;
    }

    private boolean deselectAllTrslQuranItems() {
        List<EntityTrslTimeline> list = this.entityListTrslQuran;
        if (list == null) {
            return false;
        }
        boolean z = false;
        for (EntityTrslTimeline entityTrslTimeline : list) {
            if (entityTrslTimeline.visible() && entityTrslTimeline.isSelect()) {
                entityTrslTimeline.setSelect(false);
                entityTrslTimeline.setSelectMultiple(false);
                z = true;
            }
        }
        if (z) {
            this.clr_btn_trsl = -13421771;
        }
        return z;
    }

    private boolean deselectAllAudioItems() {
        List<EntityAudio> list = this.entityListAudio;
        if (list == null) {
            return false;
        }
        boolean z = false;
        for (EntityAudio entityAudio : list) {
            if (entityAudio.visible() && entityAudio.isSelect()) {
                entityAudio.setSelect(false);
                entityAudio.setSelectMultiple(false);
                z = true;
            }
        }
        if (z) {
            this.clr_btn_audio = -13421771;
        }
        return z;
    }
}

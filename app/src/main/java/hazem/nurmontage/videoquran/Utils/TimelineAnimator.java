package hazem.nurmontage.videoquran.Utils;

import android.os.Handler;

/* loaded from: classes2.dex */
public class TimelineAnimator {
    private int currentTimeMs;
    private boolean isRunning;
    private long lastFrameTime;
    private final AnimatorListener listener;
    private int maxTimeMs;
    private int startTimeMs;
    private final Runnable updateRunnable = new Runnable() { // from class: hazem.nurmontage.videoquran.Utils.TimelineAnimator.1
        @Override // java.lang.Runnable
        public void run() {
            if (TimelineAnimator.this.isRunning) {
                long currentTimeMillis = System.currentTimeMillis();
                int i = (int) (currentTimeMillis - TimelineAnimator.this.lastFrameTime);
                TimelineAnimator.this.lastFrameTime = currentTimeMillis;
                TimelineAnimator.this.currentTimeMs += i;
                if (TimelineAnimator.this.currentTimeMs >= TimelineAnimator.this.maxTimeMs) {
                    TimelineAnimator timelineAnimator = TimelineAnimator.this;
                    timelineAnimator.currentTimeMs = timelineAnimator.maxTimeMs;
                    TimelineAnimator.this.listener.onUpdate(TimelineAnimator.this.currentTimeMs);
                    TimelineAnimator.this.listener.onEnd();
                    TimelineAnimator.this.isRunning = false;
                    return;
                }
                TimelineAnimator.this.listener.onUpdate(TimelineAnimator.this.currentTimeMs);
                TimelineAnimator.this.postFrame();
            }
        }
    };
    private final Handler handler = new Handler();

    public interface AnimatorListener {
        void onEnd();

        void onUpdate(int i);
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    public TimelineAnimator(int i, int i2, AnimatorListener animatorListener) {
        this.startTimeMs = i;
        this.currentTimeMs = i;
        this.maxTimeMs = i2;
        this.listener = animatorListener;
    }

    public int getCurrentTimeMs() {
        return this.currentTimeMs;
    }

    public void start() {
        this.isRunning = true;
        this.lastFrameTime = System.currentTimeMillis();
        postFrame();
    }

    public void stop() {
        this.isRunning = false;
        this.handler.removeCallbacks(this.updateRunnable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postFrame() {
        this.handler.postDelayed(this.updateRunnable, 16L);
    }
}

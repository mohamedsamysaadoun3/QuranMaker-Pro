package hazem.nurmontage.videoquran.Utils;

import android.os.SystemClock;
import android.view.Choreographer;

/* loaded from: classes2.dex */
public class SmoothTimelineAnimator {
    private int currentTimeMs;
    private final Choreographer.FrameCallback frameCallback = new Choreographer.FrameCallback() { // from class: hazem.nurmontage.videoquran.Utils.SmoothTimelineAnimator.1
        @Override // android.view.Choreographer.FrameCallback
        public void doFrame(long j) {
            if (SmoothTimelineAnimator.this.isRunning) {
                int uptimeMillis = (int) (SystemClock.uptimeMillis() - SmoothTimelineAnimator.this.startTimeMs);
                SmoothTimelineAnimator smoothTimelineAnimator = SmoothTimelineAnimator.this;
                smoothTimelineAnimator.currentTimeMs = smoothTimelineAnimator.startCursorMs + uptimeMillis;
                if (SmoothTimelineAnimator.this.currentTimeMs >= SmoothTimelineAnimator.this.maxTimeMs) {
                    SmoothTimelineAnimator.this.listener.onUpdate(SmoothTimelineAnimator.this.maxTimeMs);
                    SmoothTimelineAnimator.this.listener.onEnd();
                    SmoothTimelineAnimator.this.isRunning = false;
                } else {
                    SmoothTimelineAnimator.this.listener.onUpdate(SmoothTimelineAnimator.this.currentTimeMs);
                    Choreographer.getInstance().postFrameCallback(this);
                }
            }
        }
    };
    private boolean isRunning;
    private final AnimatorListener listener;
    private final int maxTimeMs;
    private int startCursorMs;
    private long startTimeMs;

    public interface AnimatorListener {
        void onEnd();

        void onUpdate(int i);
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    public int getCurrentTimeMs() {
        return this.currentTimeMs;
    }

    public SmoothTimelineAnimator(int i, int i2, AnimatorListener animatorListener) {
        this.startCursorMs = i;
        this.maxTimeMs = i2;
        this.listener = animatorListener;
    }

    public void start() {
        this.isRunning = true;
        this.startTimeMs = SystemClock.uptimeMillis();
        Choreographer.getInstance().postFrameCallback(this.frameCallback);
    }

    public void stop() {
        this.isRunning = false;
        Choreographer.getInstance().removeFrameCallback(this.frameCallback);
    }
}

package hazem.nurmontage.videoquran.multitouch;

import android.content.Context;
import android.view.MotionEvent;

/* loaded from: classes2.dex */
public class ShoveGestureDetector extends TwoFingerGestureDetector {
    private float mCurrAverageY;
    private final OnShoveGestureListener mListener;
    private float mPrevAverageY;
    private boolean mSloppyGesture;

    public interface OnShoveGestureListener {
        boolean onShove(ShoveGestureDetector shoveGestureDetector);

        boolean onShoveBegin(ShoveGestureDetector shoveGestureDetector);

        void onShoveEnd(ShoveGestureDetector shoveGestureDetector);
    }

    public static class SimpleOnShoveGestureListener implements OnShoveGestureListener {
        @Override // hazem.nurmontage.videoquran.multitouch.ShoveGestureDetector.OnShoveGestureListener
        public boolean onShove(ShoveGestureDetector shoveGestureDetector) {
            return false;
        }

        @Override // hazem.nurmontage.videoquran.multitouch.ShoveGestureDetector.OnShoveGestureListener
        public boolean onShoveBegin(ShoveGestureDetector shoveGestureDetector) {
            return true;
        }

        @Override // hazem.nurmontage.videoquran.multitouch.ShoveGestureDetector.OnShoveGestureListener
        public void onShoveEnd(ShoveGestureDetector shoveGestureDetector) {
        }
    }

    public ShoveGestureDetector(Context context, OnShoveGestureListener onShoveGestureListener) {
        super(context);
        this.mListener = onShoveGestureListener;
    }

    @Override // hazem.nurmontage.videoquran.multitouch.TwoFingerGestureDetector, hazem.nurmontage.videoquran.multitouch.BaseGestureDetector
    protected void handleStartProgressEvent(int i, MotionEvent motionEvent) {
        if (i == 2) {
            if (this.mSloppyGesture) {
                boolean isSloppyGesture = isSloppyGesture(motionEvent);
                this.mSloppyGesture = isSloppyGesture;
                if (isSloppyGesture) {
                    return;
                }
                this.mGestureInProgress = this.mListener.onShoveBegin(this);
                return;
            }
            return;
        }
        if (i != 5) {
            return;
        }
        resetState();
        this.mPrevEvent = MotionEvent.obtain(motionEvent);
        this.mTimeDelta = 0L;
        updateStateByEvent(motionEvent);
        boolean isSloppyGesture2 = isSloppyGesture(motionEvent);
        this.mSloppyGesture = isSloppyGesture2;
        if (isSloppyGesture2) {
            return;
        }
        this.mGestureInProgress = this.mListener.onShoveBegin(this);
    }

    @Override // hazem.nurmontage.videoquran.multitouch.TwoFingerGestureDetector, hazem.nurmontage.videoquran.multitouch.BaseGestureDetector
    protected void handleInProgressEvent(int i, MotionEvent motionEvent) {
        if (i == 2) {
            updateStateByEvent(motionEvent);
            if (this.mCurrPressure / this.mPrevPressure <= 0.67f || Math.abs(getShovePixelsDelta()) <= 0.5f || !this.mListener.onShove(this)) {
                return;
            }
            this.mPrevEvent.recycle();
            this.mPrevEvent = MotionEvent.obtain(motionEvent);
            return;
        }
        if (i == 3) {
            if (!this.mSloppyGesture) {
                this.mListener.onShoveEnd(this);
            }
            resetState();
        } else {
            if (i != 6) {
                return;
            }
            updateStateByEvent(motionEvent);
            if (!this.mSloppyGesture) {
                this.mListener.onShoveEnd(this);
            }
            resetState();
        }
    }

    @Override // hazem.nurmontage.videoquran.multitouch.TwoFingerGestureDetector, hazem.nurmontage.videoquran.multitouch.BaseGestureDetector
    protected void updateStateByEvent(MotionEvent motionEvent) {
        super.updateStateByEvent(motionEvent);
        MotionEvent motionEvent2 = this.mPrevEvent;
        this.mPrevAverageY = (motionEvent2.getY(0) + motionEvent2.getY(1)) / 2.0f;
        this.mCurrAverageY = (motionEvent.getY(0) + motionEvent.getY(1)) / 2.0f;
    }

    @Override // hazem.nurmontage.videoquran.multitouch.TwoFingerGestureDetector
    protected boolean isSloppyGesture(MotionEvent motionEvent) {
        if (super.isSloppyGesture(motionEvent)) {
            return true;
        }
        double abs = Math.abs(Math.atan2(this.mCurrFingerDiffY, this.mCurrFingerDiffX));
        return (0.0d >= abs || abs >= 0.3499999940395355d) && (2.7899999618530273d >= abs || abs >= 3.141592653589793d);
    }

    public float getShovePixelsDelta() {
        return this.mCurrAverageY - this.mPrevAverageY;
    }

    @Override // hazem.nurmontage.videoquran.multitouch.BaseGestureDetector
    protected void resetState() {
        super.resetState();
        this.mSloppyGesture = false;
        this.mPrevAverageY = 0.0f;
        this.mCurrAverageY = 0.0f;
    }
}

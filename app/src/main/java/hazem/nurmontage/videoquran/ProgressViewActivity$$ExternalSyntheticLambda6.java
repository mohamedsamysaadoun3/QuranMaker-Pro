package hazem.nurmontage.videoquran;

import com.arthenica.ffmpegkit.Statistics;
import com.arthenica.ffmpegkit.StatisticsCallback;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes2.dex */
public final /* synthetic */ class ProgressViewActivity$$ExternalSyntheticLambda6 implements StatisticsCallback {
    public final /* synthetic */ ProgressViewActivity f$0;

    public ProgressViewActivity$$ExternalSyntheticLambda6(ProgressViewActivity progressViewActivity) {
        this.f$0 = progressViewActivity;
    }

    @Override // com.arthenica.ffmpegkit.StatisticsCallback
    public final void apply(Statistics statistics) {
        this.f$0.updateProgressDialog(statistics);
    }
}

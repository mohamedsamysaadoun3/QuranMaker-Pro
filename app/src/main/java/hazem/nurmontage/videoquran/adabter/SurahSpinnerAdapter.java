package hazem.nurmontage.videoquran.adabter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import hazem.nurmontage.videoquran.R;

/* loaded from: classes2.dex */
public class SurahSpinnerAdapter extends ArrayAdapter<String> {
    private final Context context;
    private boolean isArabic;
    private final String[] surahNames;

    public SurahSpinnerAdapter(Context context, String[] strArr, boolean z) {
        super(context, R.layout.row_spinner_aya, strArr);
        this.context = context;
        this.surahNames = strArr;
        this.isArabic = z;
    }

    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        return getCustomView(i, view, viewGroup, R.layout.row_spinner_aya);
    }

    @Override // android.widget.ArrayAdapter, android.widget.BaseAdapter, android.widget.SpinnerAdapter
    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        return getCustomView(i, view, viewGroup, R.layout.row_spinner_aya);
    }

    private View getCustomView(int i, View view, ViewGroup viewGroup, int i2) {
        String str;
        if (view == null) {
            view = LayoutInflater.from(this.context).inflate(i2, viewGroup, false);
        }
        Log.e("getCustomView", "" + i);
        TextView textView = (TextView) view.findViewById(R.id.spinner_text);
        if (this.isArabic) {
            str = this.surahNames[i].split(" - ")[0];
        } else {
            str = this.surahNames[i].split(" - ")[1];
        }
        textView.setText(str);
        return view;
    }
}

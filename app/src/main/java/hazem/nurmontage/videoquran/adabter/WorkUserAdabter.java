package hazem.nurmontage.videoquran.adabter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.ObjectKey;
import hazem.nurmontage.videoquran.R;
import hazem.nurmontage.videoquran.model.Template;
import java.util.List;

/* loaded from: classes2.dex */
public class WorkUserAdabter extends RecyclerView.Adapter<WorkUserAdabter.ViewHolder> {
    private final String APP_VERSION;
    private int h;
    public IWorkUserCallback iWorkUserCallback;
    private List<Template> images;
    private int w;

    public interface IWorkUserCallback {
        void onClick(Template template);

        void toMenu(Template template, View view, int i);
    }

    public WorkUserAdabter(String str, List<Template> list, IWorkUserCallback iWorkUserCallback, int i, int i2) {
        this.images = list;
        this.iWorkUserCallback = iWorkUserCallback;
        this.w = i;
        this.h = i2;
        this.APP_VERSION = str;
    }

    public void remove(int i) {
        try {
            if (i < this.images.size()) {
                this.images.remove(i);
            }
            notifyItemRemoved(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void add(int i, Template template) {
        try {
            if (i < this.images.size()) {
                this.images.add(i, template);
            } else {
                this.images.add(template);
            }
            notifyItemInserted(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageButton btn_menu;
        public ImageView imageView;
        public ImageView iv_ratio;
        private TextView tvDate;
        private TextView tvName;

        public ViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.imageView);
            this.btn_menu = (ImageButton) view.findViewById(R.id.btn_menu);
            this.iv_ratio = (ImageView) view.findViewById(R.id.iv_ratio);
            this.tvName = (TextView) view.findViewById(R.id.tv_name);
            this.tvDate = (TextView) view.findViewById(R.id.tv_date);
            this.btn_menu.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.adabter.WorkUserAdabter.ViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (WorkUserAdabter.this.iWorkUserCallback != null) {
                        WorkUserAdabter.this.iWorkUserCallback.toMenu((Template) WorkUserAdabter.this.images.get(ViewHolder.this.getAdapterPosition()), view2, ViewHolder.this.getAdapterPosition());
                    }
                }
            });
            view.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.adabter.WorkUserAdabter.ViewHolder.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (WorkUserAdabter.this.iWorkUserCallback != null) {
                        WorkUserAdabter.this.iWorkUserCallback.onClick((Template) WorkUserAdabter.this.images.get(ViewHolder.this.getAdapterPosition()));
                    }
                }
            });
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_work_user, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Template template = this.images.get(i);
        if (template.getFileInfo() != null) {
            viewHolder.tvName.setText(template.getFileInfo().formattedDate);
            viewHolder.tvDate.setText(template.getFileInfo().timedDate);
        }
        Glide.with(viewHolder.imageView).asBitmap().load(template.getUri_video()).frame(1000000L).centerInside().override(this.w, this.h).diskCacheStrategy(DiskCacheStrategy.NONE).signature(new ObjectKey(this.APP_VERSION)).placeholder(R.drawable.broken_image_24px).into(viewHolder.imageView);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.images.size();
    }
}

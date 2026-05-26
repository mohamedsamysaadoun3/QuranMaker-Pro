package hazem.nurmontage.videoquran.fragment;

import android.content.ContentUris;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import hazem.nurmontage.videoquran.GalleryPickerVideo;
import hazem.nurmontage.videoquran.R;
import hazem.nurmontage.videoquran.Utils.AppUtils;
import hazem.nurmontage.videoquran.Utils.ScreenUtils;
import hazem.nurmontage.videoquran.adabter.GalleryVideoAdabters;
import hazem.nurmontage.videoquran.databinding.FragmentGalleryVideoBinding;
import hazem.nurmontage.videoquran.model.GallerySelected;
import hazem.nurmontage.videoquran.model.VideoItem;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class GalleryPhotosFragment extends Fragment {
    public static GalleryPhotosFragment instance;
    private GalleryVideoAdabters adabters;
    private File folder;
    private List<GallerySelected> gallerySelecteds;
    private FragmentGalleryVideoBinding galleryVideoBinding;
    private GalleryPickerVideo.IPicker iPicker;

    public static synchronized GalleryPhotosFragment get(List<GallerySelected> list, File file, GalleryPickerVideo.IPicker iPicker) {
        synchronized (GalleryPhotosFragment.class) {
            if (instance == null) {
                instance = new GalleryPhotosFragment(list, file, iPicker);
            }
            return instance;
        }
    }

    public GalleryPhotosFragment(List<GallerySelected> list, File file, GalleryPickerVideo.IPicker iPicker) {
        this.iPicker = iPicker;
        this.folder = file;
        this.gallerySelecteds = list;
    }

    public GalleryPhotosFragment() {
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        FragmentGalleryVideoBinding inflate = FragmentGalleryVideoBinding.inflate(layoutInflater, viewGroup, false);
        this.galleryVideoBinding = inflate;
        FrameLayout root = inflate.getRoot();
        loadVideos(root);
        return root;
    }

    public VideoItem isContains(String str) {
        List<GallerySelected> list = this.gallerySelecteds;
        if (list == null) {
            return null;
        }
        for (GallerySelected gallerySelected : list) {
            if (gallerySelected.getVideoItem() != null && gallerySelected.getVideoItem().getPath().equals(str)) {
                return gallerySelected.getVideoItem();
            }
        }
        return null;
    }

    public void inselect(int i) {
        this.adabters.inselectItem(i);
    }

    private void loadVideos(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_gallery);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setItemAnimator(null);
        GalleryVideoAdabters galleryVideoAdabters = new GalleryVideoAdabters(AppUtils.getAppVersionName(getContext()), getResources(), this.gallerySelecteds, (int) (ScreenUtils.getScreenWidth(getActivity()) * 0.24f), this.iPicker);
        this.adabters = galleryVideoAdabters;
        recyclerView.setAdapter(galleryVideoAdabters);
        File file = this.folder;
        if (file != null) {
            changeFolder(file);
        } else {
            new Thread(new Runnable() { // from class: hazem.nurmontage.videoquran.fragment.GalleryPhotosFragment.1
                @Override // java.lang.Runnable
                public void run() {
                    Cursor query = GalleryPhotosFragment.this.requireActivity().getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, new String[]{"_id", "duration"}, null, null, null);
                    final ArrayList arrayList = new ArrayList();
                    while (query.moveToNext()) {
                        query.getInt(query.getColumnIndexOrThrow("duration"));
                        VideoItem isContains = GalleryPhotosFragment.this.isContains(ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, query.getLong(query.getColumnIndexOrThrow("_id"))).toString());
                        if (isContains != null) {
                            isContains.setSelect(true);
                            arrayList.add(isContains);
                        }
                    }
                    query.close();
                    GalleryPhotosFragment.this.requireActivity().runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.fragment.GalleryPhotosFragment.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            GalleryPhotosFragment.this.adabters.addItems(arrayList);
                            GalleryPhotosFragment.this.adabters.notifyDataSetChanged();
                        }
                    });
                }
            }).start();
        }
    }

    private boolean isVideoFile(File file) {
        String lowerCase = file.getName().toLowerCase();
        return lowerCase.endsWith(".mp4") || lowerCase.endsWith(".avi") || lowerCase.endsWith(".mov") || lowerCase.endsWith(".mkv") || lowerCase.endsWith(".wmv") || lowerCase.endsWith(".flv") || lowerCase.endsWith(".webm") || lowerCase.endsWith(".3gp") || lowerCase.endsWith(".m4v") || lowerCase.endsWith(".mpg") || lowerCase.endsWith(".mpeg");
    }

    public void changeFolder(File file) {
        File[] listFiles;
        this.adabters.clear();
        if (file != null && file.exists() && file.isDirectory() && (listFiles = file.listFiles()) != null) {
            ArrayList arrayList = new ArrayList();
            for (File file2 : listFiles) {
                if (file2.isFile() && isVideoFile(file2)) {
                    String absolutePath = file2.getAbsolutePath();
                    getVideoDuration(absolutePath);
                    VideoItem isContains = isContains(absolutePath);
                    if (isContains != null) {
                        isContains.setSelect(true);
                        arrayList.add(isContains);
                    }
                }
            }
            if (!arrayList.isEmpty()) {
                this.adabters.addItems(arrayList);
                GalleryVideoAdabters galleryVideoAdabters = this.adabters;
                galleryVideoAdabters.notifyItemInserted(galleryVideoAdabters.getItemCount() - 1);
                return;
            }
        }
        if (this.adabters.getItemCount() == 0) {
            this.adabters.notifyDataSetChanged();
        }
    }

    private int getVideoDuration(String str) {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(str);
        int parseInt = Integer.parseInt(mediaMetadataRetriever.extractMetadata(9));
        try {
            mediaMetadataRetriever.release();
        } catch (IOException unused) {
        }
        return parseInt;
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        this.iPicker = null;
        FragmentGalleryVideoBinding fragmentGalleryVideoBinding = this.galleryVideoBinding;
        if (fragmentGalleryVideoBinding != null) {
            fragmentGalleryVideoBinding.getRoot().removeAllViews();
            this.galleryVideoBinding = null;
        }
        instance = null;
        super.onDestroyView();
    }
}

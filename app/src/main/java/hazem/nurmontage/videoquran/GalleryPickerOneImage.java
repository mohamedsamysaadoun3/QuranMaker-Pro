package hazem.nurmontage.videoquran;

import android.content.ContentUris;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import hazem.nurmontage.videoquran.GalleryPickerVideo;
import hazem.nurmontage.videoquran.Utils.AppSettingsHelper;
import hazem.nurmontage.videoquran.Utils.AppUtils;
import hazem.nurmontage.videoquran.Utils.ScreenUtils;
import hazem.nurmontage.videoquran.adabter.ExploreAdabters;
import hazem.nurmontage.videoquran.adabter.GalleryPickerAdabters;
import hazem.nurmontage.videoquran.common.Common;
import hazem.nurmontage.videoquran.model.ExploreItem;
import hazem.nurmontage.videoquran.model.GallerySelected;
import hazem.nurmontage.videoquran.model.PhotoItem;
import hazem.nurmontage.videoquran.model.VideoItem;
import hazem.nurmontage.videoquran.views.TextCustumFont;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

/* loaded from: classes2.dex */
public class GalleryPickerOneImage extends Base {
    private ImageButton btnDone;
    private TextCustumFont btnExplore;
    private GalleryPickerAdabters galleryPickerAdabters;
    private boolean isUpdate;
    private LinearLayout layoutSetting;
    private PhotoItem mPhotoItem;
    private Resources mResources;
    private RecyclerView rv_explore;
    private OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) { // from class: hazem.nurmontage.videoquran.GalleryPickerOneImage.1
        @Override // androidx.activity.OnBackPressedCallback
        public void handleOnBackPressed() {
            if (GalleryPickerOneImage.this.rv_explore != null && GalleryPickerOneImage.this.rv_explore.getVisibility() == 0) {
                GalleryPickerOneImage.this.btnExplore.performClick();
                return;
            }
            Common.LIST_SELECT = null;
            Common.INDEX_LIST_SELECT = 1;
            GalleryPickerOneImage.this.finish();
        }
    };
    private GalleryPickerVideo.IPicker iPicker = new GalleryPickerVideo.IPicker() { // from class: hazem.nurmontage.videoquran.GalleryPickerOneImage.2
        @Override // hazem.nurmontage.videoquran.GalleryPickerVideo.IPicker
        public void onAdd(VideoItem videoItem, int i) {
        }

        @Override // hazem.nurmontage.videoquran.GalleryPickerVideo.IPicker
        public void onDelete(GallerySelected gallerySelected) {
        }

        @Override // hazem.nurmontage.videoquran.GalleryPickerVideo.IPicker
        public void onEmptyList() {
            GalleryPickerOneImage.this.setSetting(false);
        }

        @Override // hazem.nurmontage.videoquran.GalleryPickerVideo.IPicker
        public void onAdd(PhotoItem photoItem, int i) {
            GalleryPickerOneImage.this.mPhotoItem = photoItem;
        }
    };
    private ExploreAdabters.IExplore iExplore = new ExploreAdabters.IExplore() { // from class: hazem.nurmontage.videoquran.GalleryPickerOneImage.3
        @Override // hazem.nurmontage.videoquran.adabter.ExploreAdabters.IExplore
        public void folder(File file, String str, String str2) {
            if (GalleryPickerOneImage.this.rv_explore.getVisibility() != 4) {
                GalleryPickerOneImage.this.rv_explore.setVisibility(4);
            }
            GalleryPickerOneImage.this.changeFolder(str2);
            GalleryPickerOneImage.this.btnExplore.setText(str);
        }

        @Override // hazem.nurmontage.videoquran.adabter.ExploreAdabters.IExplore
        public void done() {
            if (GalleryPickerOneImage.this.rv_explore.getVisibility() != 4) {
                GalleryPickerOneImage.this.rv_explore.setVisibility(4);
            }
        }
    };

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        this.iExplore = null;
        this.iPicker = null;
        this.onBackPressedCallback = null;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gallery_picker_video);
        setStatusBarColor(ViewCompat.MEASURED_STATE_MASK);
        setNavigationBarColor(ViewCompat.MEASURED_STATE_MASK);
        WindowInsetsControllerCompat insetsController = WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(false);
        insetsController.setAppearanceLightNavigationBars(false);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), new OnApplyWindowInsetsListener() { // from class: hazem.nurmontage.videoquran.GalleryPickerOneImage$$ExternalSyntheticLambda0
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                return GalleryPickerOneImage.lambda$onCreate$0(view, windowInsetsCompat);
            }
        });
        Common.LIST_SELECT = null;
        Common.INDEX_LIST_SELECT = 1;
        getOnBackPressedDispatcher().addCallback(this, this.onBackPressedCallback);
        this.mResources = getResources();
        this.btnDone = (ImageButton) findViewById(R.id.tv_done);
        this.rv_explore = (RecyclerView) findViewById(R.id.rv_explore);
        this.btnDone.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.GalleryPickerOneImage.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (GalleryPickerOneImage.this.mPhotoItem != null) {
                    Intent intent = new Intent();
                    intent.setData(Uri.parse(GalleryPickerOneImage.this.mPhotoItem.getPath()));
                    GalleryPickerOneImage.this.setResult(-1, intent);
                }
                GalleryPickerOneImage.this.finish();
            }
        });
        initViews();
        initFolder();
        if (Build.VERSION.SDK_INT >= 33 && (ContextCompat.checkSelfPermission(this, "android.permission.READ_MEDIA_IMAGES") == 0 || ContextCompat.checkSelfPermission(this, "android.permission.READ_MEDIA_VIDEO") == 0)) {
            setSetting(true);
            return;
        }
        if (Build.VERSION.SDK_INT >= 34 && ContextCompat.checkSelfPermission(this, "android.permission.READ_MEDIA_VISUAL_USER_SELECTED") == 0) {
            setSetting(false);
        } else if (ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") == 0) {
            setSetting(true);
        } else {
            setSetting(false);
        }
    }

    static /* synthetic */ WindowInsetsCompat lambda$onCreate$0(View view, WindowInsetsCompat windowInsetsCompat) {
        Insets insets = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.systemBars());
        view.setPadding(insets.left, insets.top, insets.right, insets.bottom);
        return windowInsetsCompat;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSetting(boolean z) {
        if (z) {
            return;
        }
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.to_setting);
        this.layoutSetting = linearLayout;
        linearLayout.setVisibility(0);
        this.layoutSetting.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.GalleryPickerOneImage.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                GalleryPickerOneImage.this.isUpdate = true;
                AppSettingsHelper.openAppSettings(GalleryPickerOneImage.this);
            }
        });
    }

    private void updateSetting() {
        if (Build.VERSION.SDK_INT >= 33 && (ContextCompat.checkSelfPermission(this, "android.permission.READ_MEDIA_IMAGES") == 0 || ContextCompat.checkSelfPermission(this, "android.permission.READ_MEDIA_VIDEO") == 0)) {
            recreate();
        } else if ((Build.VERSION.SDK_INT < 34 || ContextCompat.checkSelfPermission(this, "android.permission.READ_MEDIA_VISUAL_USER_SELECTED") != 0) && ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") == 0) {
            recreate();
        }
        this.isUpdate = false;
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        if (this.isUpdate) {
            updateSetting();
        }
    }

    private void initFolder() {
        TextCustumFont textCustumFont = (TextCustumFont) findViewById(R.id.tv_folders);
        this.btnExplore = textCustumFont;
        textCustumFont.setText(this.mResources.getString(R.string.all));
        this.btnExplore.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.GalleryPickerOneImage.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (GalleryPickerOneImage.this.rv_explore == null || GalleryPickerOneImage.this.btnExplore == null) {
                    return;
                }
                if (GalleryPickerOneImage.this.rv_explore.getVisibility() != 0) {
                    GalleryPickerOneImage.this.rv_explore.setVisibility(0);
                    GalleryPickerOneImage.this.btnExplore.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.arrow_up_float, 0);
                } else {
                    GalleryPickerOneImage.this.rv_explore.setVisibility(4);
                    GalleryPickerOneImage.this.btnExplore.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.arrow_down_float, 0);
                }
            }
        });
        new Thread(new Runnable() { // from class: hazem.nurmontage.videoquran.GalleryPickerOneImage.7
            @Override // java.lang.Runnable
            public void run() {
                HashSet hashSet;
                int i;
                String str;
                int i2;
                String str2;
                int i3 = 0;
                Cursor query = GalleryPickerOneImage.this.getContentResolver().query(MediaStore.Files.getContentUri("external"), new String[]{"_id", "_data", "parent"}, "media_type=1", null, "date_added DESC");
                final ArrayList arrayList = new ArrayList();
                final ArrayList arrayList2 = new ArrayList();
                HashSet hashSet2 = new HashSet();
                Log.e("query start", "" + query);
                int i4 = 0;
                int i5 = 0;
                String str3 = null;
                while (true) {
                    if (!query.moveToNext()) {
                        break;
                    }
                    String parent = new File(query.getString(query.getColumnIndexOrThrow("_data"))).getParent();
                    if (!hashSet2.contains(parent)) {
                        hashSet2.add(parent);
                        File file = new File(parent);
                        File[] listFiles = file.listFiles();
                        if (listFiles != null) {
                            int length = listFiles.length;
                            int i6 = i3;
                            String str4 = null;
                            while (i3 < length) {
                                int i7 = length;
                                File file2 = listFiles[i3];
                                File[] fileArr = listFiles;
                                if (GalleryPickerOneImage.this.isImageFile(file2)) {
                                    i6++;
                                    if (str4 == null) {
                                        str4 = file2.getAbsolutePath();
                                        if (str3 == null) {
                                            str3 = str4;
                                        }
                                    }
                                }
                                i3++;
                                length = i7;
                                listFiles = fileArr;
                            }
                            i2 = i6;
                            str2 = str4;
                        } else {
                            i2 = 0;
                            str2 = null;
                        }
                        if (i2 > 0) {
                            i4 += i2;
                            arrayList.add(new ExploreItem(file, parent, "" + i2, file.getName(), str2));
                        }
                    }
                    String str5 = str3;
                    int i8 = i4;
                    arrayList2.add(new PhotoItem(parent, ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, query.getLong(query.getColumnIndexOrThrow("_id"))).toString(), false));
                    i5++;
                    if (i5 > 50) {
                        str3 = str5;
                        i4 = i8;
                        break;
                    } else {
                        str3 = str5;
                        i4 = i8;
                        i3 = 0;
                    }
                }
                GalleryPickerOneImage.this.runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.GalleryPickerOneImage.7.1
                    @Override // java.lang.Runnable
                    public void run() {
                        RecyclerView recyclerView = (RecyclerView) GalleryPickerOneImage.this.findViewById(R.id.rv);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new GridLayoutManager(GalleryPickerOneImage.this, 3));
                        recyclerView.setItemViewCacheSize(20);
                        recyclerView.setDrawingCacheEnabled(true);
                        recyclerView.setItemAnimator(null);
                        GalleryPickerOneImage.this.galleryPickerAdabters = new GalleryPickerAdabters(AppUtils.getAppVersionName(GalleryPickerOneImage.this), GalleryPickerOneImage.this.mResources, null, (int) (ScreenUtils.getScreenWidth(GalleryPickerOneImage.this) * 0.3f), GalleryPickerOneImage.this.iPicker);
                        GalleryPickerOneImage.this.galleryPickerAdabters.addItems(arrayList2);
                        recyclerView.setAdapter(GalleryPickerOneImage.this.galleryPickerAdabters);
                    }
                });
                String str6 = str3;
                while (query.moveToNext()) {
                    String parent2 = new File(query.getString(query.getColumnIndexOrThrow("_data"))).getParent();
                    if (hashSet2.contains(parent2)) {
                        hashSet = hashSet2;
                    } else {
                        hashSet2.add(parent2);
                        File file3 = new File(parent2);
                        File[] listFiles2 = file3.listFiles();
                        if (listFiles2 != null) {
                            int length2 = listFiles2.length;
                            int i9 = 0;
                            i = 0;
                            String str7 = null;
                            while (i9 < length2) {
                                HashSet hashSet3 = hashSet2;
                                File file4 = listFiles2[i9];
                                File[] fileArr2 = listFiles2;
                                if (GalleryPickerOneImage.this.isImageFile(file4)) {
                                    i++;
                                    if (str7 == null) {
                                        str7 = file4.getAbsolutePath();
                                        if (str6 == null) {
                                            str6 = str7;
                                        }
                                    }
                                }
                                i9++;
                                hashSet2 = hashSet3;
                                listFiles2 = fileArr2;
                            }
                            hashSet = hashSet2;
                            str = str7;
                        } else {
                            hashSet = hashSet2;
                            i = 0;
                            str = null;
                        }
                        if (i > 0) {
                            i4 += i;
                            arrayList.add(new ExploreItem(file3, parent2, "" + i, file3.getName(), str));
                        }
                    }
                    arrayList2.add(new PhotoItem(parent2, ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, query.getLong(query.getColumnIndexOrThrow("_id"))).toString(), false));
                    hashSet2 = hashSet;
                }
                query.close();
                arrayList.add(0, new ExploreItem(null, GalleryPickerOneImage.this.mResources.getString(R.string.all), "" + i4, GalleryPickerOneImage.this.mResources.getString(R.string.all), str6));
                GalleryPickerOneImage.this.runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.GalleryPickerOneImage.7.2
                    @Override // java.lang.Runnable
                    public void run() {
                        GalleryPickerOneImage.this.galleryPickerAdabters.doneItems(arrayList2);
                        GalleryPickerOneImage.this.galleryPickerAdabters.notifyDataSetChanged();
                        GalleryPickerOneImage.this.rv_explore.setHasFixedSize(true);
                        GalleryPickerOneImage.this.rv_explore.setLayoutManager(new LinearLayoutManager(GalleryPickerOneImage.this));
                        GalleryPickerOneImage.this.rv_explore.setItemViewCacheSize(20);
                        GalleryPickerOneImage.this.rv_explore.setDrawingCacheEnabled(true);
                        GalleryPickerOneImage.this.rv_explore.setItemAnimator(null);
                        GalleryPickerOneImage.this.rv_explore.setAdapter(new ExploreAdabters(arrayList, (int) (ScreenUtils.getScreenWidth(GalleryPickerOneImage.this) * 0.2f), GalleryPickerOneImage.this.iExplore, GalleryPickerOneImage.this.btnExplore.getText().toString()));
                        GalleryPickerOneImage.this.findViewById(R.id.view_progress).setVisibility(8);
                        GalleryPickerOneImage.this.btnExplore.setVisibility(0);
                    }
                });
            }
        }).start();
    }

    public boolean isImageFile(File file) {
        String lowerCase = file.getName().toLowerCase();
        return lowerCase.endsWith(".jpg") || lowerCase.endsWith(".jpeg") || lowerCase.endsWith(".png");
    }

    private void initViews() {
        findViewById(R.id.btn_onBack).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.GalleryPickerOneImage.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                GalleryPickerOneImage.this.finish();
            }
        });
    }

    public void changeFolder(String str) {
        if (str.equals(this.mResources.getString(R.string.all))) {
            this.galleryPickerAdabters.updateAll();
        } else {
            this.galleryPickerAdabters.update(str);
        }
    }
}

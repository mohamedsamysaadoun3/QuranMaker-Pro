package hazem.nurmontage.videoquran.model;

import hazem.nurmontage.videoquran.Utils.MFileUtils;
import hazem.nurmontage.videoquran.constant.IpadType;
import hazem.nurmontage.videoquran.constant.ResizeType;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class Template implements Serializable {
    private int currentCursur;
    private int duration;
    private int duration_video_media;
    private EntityBismilahTemplate entityBismilahTemplate;
    private EntityBismilahTemplate entityIsti3adaTemplate;
    private EntityProgressTemplate entityProgressTemplate;
    private EntitySurahTemplate entitySurahTemplate;
    private String extension;
    private MFileUtils.FileInfo fileInfo;
    private String folder_template;
    private String frame_bg;
    private Gradient gradient;
    private int height;
    private String idTemplate;
    private boolean isGlass;
    private boolean isNewCode;
    private boolean isVideoSquare;
    private float mDrawingTranslationX;
    private float mDrawingTranslationY;
    private TimeModel mTimeModel;
    private String name_drawable;
    private SquareBitmapModel squareBitmapModel;
    private String uri_bg;
    private String uri_bg_ffmpeg;
    private String uri_media_video;
    private String uri_original_upload_video;
    private String uri_upload_extract_audio_video;
    private String uri_video;
    private int width;
    private String resolution = "720p";
    private float scale_timeline = 0.5f;
    private int fps = 30;
    private int resizeType = ResizeType.SOCIAL_STORY.ordinal();
    private String imgResize = "i_9:16";
    private float x_square = 0.3f;
    private float y_square = 0.2f;
    private float width_square = 0.37218544f;
    private float height_square = 0.41986755f;
    private int color_ipad = -1;
    private int ipad_type = IpadType.IPAD.ordinal();
    private final List<EntityMedia> entityMediaList = new ArrayList();
    private int index_color = -1;
    private final List<EntityQuranTemplate> quranEntityList = new ArrayList();
    private final List<EntityTranslationTemplate> translationTemplateList = new ArrayList();

    public void setFileInfo(MFileUtils.FileInfo fileInfo) {
        this.fileInfo = fileInfo;
    }

    public MFileUtils.FileInfo getFileInfo() {
        return this.fileInfo;
    }

    public int getCurrentCursur() {
        return this.currentCursur;
    }

    public void setCurrentCursur(int i) {
        this.currentCursur = i;
    }

    public float getScale_timeline() {
        return this.scale_timeline;
    }

    public boolean isNewCode() {
        return this.isNewCode;
    }

    public void setNewCode() {
        this.isNewCode = true;
    }

    public void setScale_timeline(float f) {
        this.scale_timeline = f;
    }

    public String getResolution() {
        return this.resolution;
    }

    public void setResolution(String str) {
        this.resolution = str;
    }

    public int getFps() {
        return this.fps;
    }

    public void setFps(int i) {
        this.fps = i;
    }

    public void setFolder_template(String str) {
        this.folder_template = str;
    }

    public String getFolder_template() {
        return this.folder_template;
    }

    public String getUri_original_upload_video() {
        return this.uri_original_upload_video;
    }

    public String getExtension() {
        return this.extension;
    }

    public void setExtension(String str) {
        this.extension = str;
    }

    public String getUri_upload_extract_audio_video() {
        return this.uri_upload_extract_audio_video;
    }

    public void setUri_upload_extract_audio_video(String str) {
        this.uri_upload_extract_audio_video = str;
    }

    public void setUri_original_upload_video(String str) {
        this.uri_original_upload_video = str;
    }

    public void setName_drawable(String str) {
        this.name_drawable = str;
    }

    public String getName_drawable() {
        return this.name_drawable;
    }

    public boolean isVideoSquare() {
        return this.isVideoSquare;
    }

    public int getDuration_video_media() {
        return this.duration_video_media;
    }

    public void setDuration_video_media(int i) {
        this.duration_video_media = i;
    }

    public String getFrame_bg() {
        return this.frame_bg;
    }

    public void setFrame_bg(String str) {
        this.frame_bg = str;
    }

    public void setVideoSquare(boolean z) {
        this.isVideoSquare = z;
    }

    public float getWidth_square() {
        return this.width_square;
    }

    public float getHeight_square() {
        return this.height_square;
    }

    public void setWidth_square(float f) {
        this.width_square = f;
    }

    public void setHeight_square(float f) {
        this.height_square = f;
    }

    public SquareBitmapModel getSquareBitmapModel() {
        if (this.squareBitmapModel == null) {
            this.squareBitmapModel = new SquareBitmapModel();
        }
        return this.squareBitmapModel;
    }

    public String getUri_media_video() {
        return this.uri_media_video;
    }

    public void setUri_media_video(String str) {
        this.uri_media_video = str;
    }

    public void setmTimeModel(TimeModel timeModel) {
        this.mTimeModel = timeModel;
    }

    public TimeModel getmTimeModel() {
        return this.mTimeModel;
    }

    public void setX_square(float f) {
        this.x_square = f;
    }

    public void setY_square(float f) {
        this.y_square = f;
    }

    public float getX_square() {
        return this.x_square;
    }

    public float getY_square() {
        return this.y_square;
    }

    public void setImgResize(String str) {
        this.imgResize = str;
    }

    public String getImgResize() {
        return this.imgResize;
    }

    public Gradient getGradient() {
        return this.gradient;
    }

    public void setGradient(Gradient gradient) {
        this.gradient = gradient;
    }

    public int getIpad_type() {
        return this.ipad_type;
    }

    public void setIpad_type(int i) {
        this.ipad_type = i;
    }

    public void setGlass(boolean z) {
        this.isGlass = z;
    }

    public boolean isGlass() {
        return this.isGlass;
    }

    public float getmDrawingTranslationX() {
        return this.mDrawingTranslationX;
    }

    public float getmDrawingTranslationY() {
        return this.mDrawingTranslationY;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setDrawingTranslation(float f, float f2) {
        this.mDrawingTranslationX = f;
        this.mDrawingTranslationY = f2;
    }

    public void setWidthAndHeight(int i, int i2) {
        this.width = i;
        this.height = i2;
    }

    public void addMedia(EntityMedia entityMedia) {
        this.entityMediaList.add(entityMedia);
    }

    public List<EntityMedia> getEntityMediaList() {
        return this.entityMediaList;
    }

    public void setColor_ipad(int i) {
        this.color_ipad = i;
    }

    public int getColor_ipad() {
        return this.color_ipad;
    }

    public void setIndex_color(int i) {
        this.index_color = i;
    }

    public int getIndex_color() {
        return this.index_color;
    }

    public String getUri_bg_ffmpeg() {
        return this.uri_bg_ffmpeg;
    }

    public void setUri_bg_ffmpeg(String str) {
        this.uri_bg_ffmpeg = str;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int i) {
        this.duration = i;
    }

    public void setEntityProgressTemplate(EntityProgressTemplate entityProgressTemplate) {
        this.entityProgressTemplate = entityProgressTemplate;
    }

    public EntityProgressTemplate getEntityProgressTemplate() {
        return this.entityProgressTemplate;
    }

    public void setEntityIsti3adaTemplate(EntityBismilahTemplate entityBismilahTemplate) {
        this.entityIsti3adaTemplate = entityBismilahTemplate;
    }

    public EntityBismilahTemplate getEntityIsti3adaTemplate() {
        return this.entityIsti3adaTemplate;
    }

    public void setEntityBismilahTemplate(EntityBismilahTemplate entityBismilahTemplate) {
        this.entityBismilahTemplate = entityBismilahTemplate;
    }

    public EntityBismilahTemplate getEntityBismilahTemplate() {
        return this.entityBismilahTemplate;
    }

    public List<EntityTranslationTemplate> getTranslationTemplateList() {
        return this.translationTemplateList;
    }

    public void setEntitySurahTemplate(EntitySurahTemplate entitySurahTemplate) {
        this.entitySurahTemplate = entitySurahTemplate;
    }

    public EntitySurahTemplate getEntitySurahTemplate() {
        return this.entitySurahTemplate;
    }

    public void setIdTemplate(String str) {
        this.idTemplate = str;
    }

    public String getIdTemplate() {
        return this.idTemplate;
    }

    public void addQuranEntityList(EntityQuranTemplate entityQuranTemplate) {
        this.quranEntityList.add(entityQuranTemplate);
    }

    public void addTrslEntityList(EntityTranslationTemplate entityTranslationTemplate) {
        this.translationTemplateList.add(entityTranslationTemplate);
    }

    public void setUri_video(String str) {
        this.uri_video = str;
    }

    public String getUri_video() {
        return this.uri_video;
    }

    public void setUri_bg(String str) {
        this.uri_bg = str;
    }

    public List<EntityQuranTemplate> getQuranEntityList() {
        return this.quranEntityList;
    }

    public String getUri_bg() {
        return this.uri_bg;
    }

    public Template duplicate() {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.flush();
            return (Template) new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray())).readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int geTypeResize() {
        return this.resizeType;
    }

    public void setResizeType(int i) {
        this.resizeType = i;
    }
}

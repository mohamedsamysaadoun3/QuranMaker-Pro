package com.arthenica.ffmpegkit;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.SparseArray;
import com.arthenica.smartexception.java.Exceptions;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public class FFmpegKitConfig {
    static final String FFMPEG_KIT_NAMED_PIPE_PREFIX = "fk_pipe_";
    static final String TAG = "ffmpeg-kit";
    private static Level activeLogLevel;
    private static int asyncConcurrencyLimit;
    private static ExecutorService asyncExecutorService;
    private static FFmpegSessionCompleteCallback globalFFmpegSessionCompleteCallback;
    private static FFprobeSessionCompleteCallback globalFFprobeSessionCompleteCallback;
    private static LogCallback globalLogCallback;
    private static LogRedirectionStrategy globalLogRedirectionStrategy;
    private static MediaInformationSessionCompleteCallback globalMediaInformationSessionCompleteCallback;
    private static StatisticsCallback globalStatisticsCallback;
    private static final SparseArray<SAFProtocolUrl> safFileDescriptorMap;
    private static final SparseArray<SAFProtocolUrl> safIdMap;
    private static final List<Session> sessionHistoryList;
    private static final Object sessionHistoryLock;
    private static final Map<Long, Session> sessionHistoryMap;
    private static int sessionHistorySize;
    private static final AtomicInteger uniqueIdGenerator;

    private static native void disableNativeRedirection();

    private static native void enableNativeRedirection();

    private static native String getNativeBuildDate();

    private static native String getNativeFFmpegVersion();

    static native int getNativeLogLevel();

    private static native String getNativeVersion();

    private static native void ignoreNativeSignal(int i);

    public static native int messagesInTransmit(long j);

    static native void nativeFFmpegCancel(long j);

    private static native int nativeFFmpegExecute(long j, String[] strArr);

    static native int nativeFFprobeExecute(long j, String[] strArr);

    private static native int registerNewNativeFFmpegPipe(String str);

    private static native int setNativeEnvironmentVariable(String str, String str2);

    private static native void setNativeLogLevel(int i);

    static class SAFProtocolUrl {
        private final ContentResolver contentResolver;
        private final String openMode;
        private ParcelFileDescriptor parcelFileDescriptor;
        private final Integer safId;
        private final Uri uri;

        public SAFProtocolUrl(Integer num, Uri uri, String str, ContentResolver contentResolver) {
            this.safId = num;
            this.uri = uri;
            this.openMode = str;
            this.contentResolver = contentResolver;
        }

        public Integer getSafId() {
            return this.safId;
        }

        public Uri getUri() {
            return this.uri;
        }

        public String getOpenMode() {
            return this.openMode;
        }

        public ContentResolver getContentResolver() {
            return this.contentResolver;
        }

        public void setParcelFileDescriptor(ParcelFileDescriptor parcelFileDescriptor) {
            this.parcelFileDescriptor = parcelFileDescriptor;
        }

        public ParcelFileDescriptor getParcelFileDescriptor() {
            return this.parcelFileDescriptor;
        }
    }

    static {
        android.util.Log.i(TAG, "Loading ffmpeg-kit.");
        NativeLoader.loadFFmpegKit(NativeLoader.loadFFmpeg());
        uniqueIdGenerator = new AtomicInteger(1);
        activeLogLevel = Level.from(NativeLoader.loadLogLevel());
        asyncConcurrencyLimit = 10;
        asyncExecutorService = Executors.newFixedThreadPool(10);
        sessionHistorySize = 10;
        sessionHistoryMap = new LinkedHashMap<Long, Session>() { // from class: com.arthenica.ffmpegkit.FFmpegKitConfig.1
            @Override // java.util.LinkedHashMap
            protected boolean removeEldestEntry(Map.Entry<Long, Session> entry) {
                return size() > FFmpegKitConfig.sessionHistorySize;
            }
        };
        sessionHistoryList = new LinkedList();
        sessionHistoryLock = new Object();
        globalLogCallback = null;
        globalStatisticsCallback = null;
        globalFFmpegSessionCompleteCallback = null;
        globalFFprobeSessionCompleteCallback = null;
        globalMediaInformationSessionCompleteCallback = null;
        safIdMap = new SparseArray<>();
        safFileDescriptorMap = new SparseArray<>();
        globalLogRedirectionStrategy = LogRedirectionStrategy.PRINT_LOGS_WHEN_NO_CALLBACKS_DEFINED;
        android.util.Log.i(TAG, String.format("Loaded ffmpeg-kit-%s-%s-%s-%s.", NativeLoader.loadPackageName(), NativeLoader.loadAbi(), NativeLoader.loadVersion(), NativeLoader.loadBuildDate()));
    }

    private FFmpegKitConfig() {
    }

    public static void enableRedirection() {
        enableNativeRedirection();
    }

    public static void disableRedirection() {
        disableNativeRedirection();
    }

    private static void log(long sessionId, int levelValue, byte[] data) {
        Level level = Level.from(levelValue);
        String message = new String(data);
        Log log = new Log(sessionId, level, message);

        LogRedirectionStrategy logRedirectionStrategy = globalLogRedirectionStrategy;

        // Check if this log level should be processed
        if (activeLogLevel == Level.AV_LOG_QUIET) {
            if (levelValue != Level.AV_LOG_STDERR.getValue()) {
                return;
            }
        } else if (levelValue > activeLogLevel.getValue()) {
            return;
        }

        Session session = getSession(sessionId);
        boolean sessionCallbackDefined = false;
        boolean globalCallbackDefined = false;

        if (session != null) {
            logRedirectionStrategy = session.getLogRedirectionStrategy();
            session.addLog(log);
            LogCallback sessionLogCallback = session.getLogCallback();
            if (sessionLogCallback != null) {
                try {
                    sessionLogCallback.apply(log);
                } catch (Exception e) {
                    android.util.Log.e(TAG, String.format("Exception thrown inside session log callback.%s",
                            Exceptions.getStackTraceString(e)));
                }
                sessionCallbackDefined = true;
            }
        }

        if (globalLogCallback != null) {
            try {
                globalLogCallback.apply(log);
            } catch (Exception e) {
                android.util.Log.e(TAG, String.format("Exception thrown inside global log callback.%s",
                        Exceptions.getStackTraceString(e)));
            }
            globalCallbackDefined = true;
        }

        // Apply log redirection strategy
        switch (logRedirectionStrategy) {
            case NEVER_PRINT_LOGS:
                return;
            case PRINT_LOGS_WHEN_GLOBAL_CALLBACK_NOT_DEFINED:
                if (globalCallbackDefined) return;
                break;
            case PRINT_LOGS_WHEN_SESSION_CALLBACK_NOT_DEFINED:
                if (sessionCallbackDefined) return;
                break;
            case PRINT_LOGS_WHEN_NO_CALLBACKS_DEFINED:
                if (globalCallbackDefined || sessionCallbackDefined) return;
                break;
            case ALWAYS_PRINT_LOGS:
            default:
                break;
        }

        // Print to Android log based on level
        switch (level) {
            case AV_LOG_QUIET:
                break;
            case AV_LOG_TRACE:
            case AV_LOG_DEBUG:
                android.util.Log.d(TAG, message);
                break;
            case AV_LOG_INFO:
                android.util.Log.i(TAG, message);
                break;
            case AV_LOG_WARNING:
                android.util.Log.w(TAG, message);
                break;
            case AV_LOG_ERROR:
            case AV_LOG_FATAL:
            case AV_LOG_PANIC:
                android.util.Log.e(TAG, message);
                break;
            default:
                android.util.Log.v(TAG, message);
                break;
        }
    }

    /* renamed from: com.arthenica.ffmpegkit.FFmpegKitConfig$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$arthenica$ffmpegkit$Level;
        static final /* synthetic */ int[] $SwitchMap$com$arthenica$ffmpegkit$LogRedirectionStrategy;

        static {
            int[] iArr = new int[Level.values().length];
            $SwitchMap$com$arthenica$ffmpegkit$Level = iArr;
            try {
                iArr[Level.AV_LOG_QUIET.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$arthenica$ffmpegkit$Level[Level.AV_LOG_TRACE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$arthenica$ffmpegkit$Level[Level.AV_LOG_DEBUG.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$arthenica$ffmpegkit$Level[Level.AV_LOG_INFO.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$arthenica$ffmpegkit$Level[Level.AV_LOG_WARNING.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$arthenica$ffmpegkit$Level[Level.AV_LOG_ERROR.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$arthenica$ffmpegkit$Level[Level.AV_LOG_FATAL.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$arthenica$ffmpegkit$Level[Level.AV_LOG_PANIC.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$arthenica$ffmpegkit$Level[Level.AV_LOG_STDERR.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$arthenica$ffmpegkit$Level[Level.AV_LOG_VERBOSE.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            int[] iArr2 = new int[LogRedirectionStrategy.values().length];
            $SwitchMap$com$arthenica$ffmpegkit$LogRedirectionStrategy = iArr2;
            try {
                iArr2[LogRedirectionStrategy.NEVER_PRINT_LOGS.ordinal()] = 1;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$arthenica$ffmpegkit$LogRedirectionStrategy[LogRedirectionStrategy.PRINT_LOGS_WHEN_GLOBAL_CALLBACK_NOT_DEFINED.ordinal()] = 2;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$arthenica$ffmpegkit$LogRedirectionStrategy[LogRedirectionStrategy.PRINT_LOGS_WHEN_SESSION_CALLBACK_NOT_DEFINED.ordinal()] = 3;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$arthenica$ffmpegkit$LogRedirectionStrategy[LogRedirectionStrategy.PRINT_LOGS_WHEN_NO_CALLBACKS_DEFINED.ordinal()] = 4;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$arthenica$ffmpegkit$LogRedirectionStrategy[LogRedirectionStrategy.ALWAYS_PRINT_LOGS.ordinal()] = 5;
            } catch (NoSuchFieldError unused15) {
            }
        }
    }

    private static void statistics(long j, int i, float f, float f2, long j2, double d, double d2, double d3) {
        Statistics statistics = new Statistics(j, i, f, f2, j2, d, d2, d3);
        Session session = getSession(j);
        if (session != null && session.isFFmpeg()) {
            FFmpegSession fFmpegSession = (FFmpegSession) session;
            fFmpegSession.addStatistics(statistics);
            if (fFmpegSession.getStatisticsCallback() != null) {
                try {
                    fFmpegSession.getStatisticsCallback().apply(statistics);
                } catch (Exception e) {
                    android.util.Log.e(TAG, String.format("Exception thrown inside session statistics callback.%s", Exceptions.getStackTraceString(e)));
                }
            }
        }
        StatisticsCallback statisticsCallback = globalStatisticsCallback;
        if (statisticsCallback != null) {
            try {
                statisticsCallback.apply(statistics);
            } catch (Exception e2) {
                android.util.Log.e(TAG, String.format("Exception thrown inside global statistics callback.%s", Exceptions.getStackTraceString(e2)));
            }
        }
    }

    public static int setFontconfigConfigurationPath(String str) {
        return setNativeEnvironmentVariable("FONTCONFIG_PATH", str);
    }

    public static void setFontDirectory(Context context, String str, Map<String, String> map) {
        setFontDirectoryList(context, Collections.singletonList(str), map);
    }

    public static void setFontDirectoryList(Context context, List<String> list, Map<String, String> map) {
        Object obj;
        Object obj2 = null;
        File file = new File(context.getCacheDir(), "fontconfig");
        if (!file.exists()) {
            android.util.Log.d(TAG, String.format("Created temporary font conf directory: %s.", Boolean.valueOf(file.mkdirs())));
        }
        File file2 = new File(file, "fonts.conf");
        if (file2.exists()) {
            android.util.Log.d(TAG, String.format("Deleted old temporary font configuration: %s.", Boolean.valueOf(file2.delete())));
        }
        StringBuilder sb = new StringBuilder("");
        int i = 0;
        if (map != null && map.size() > 0) {
            map.entrySet();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (key != null && value != null && key.trim().length() > 0 && value.trim().length() > 0) {
                    sb.append("    <match target=\"pattern\">\n        <test qual=\"any\" name=\"family\">\n");
                    sb.append(String.format("            <string>%s</string>\n", key));
                    sb.append("        </test>\n        <edit name=\"family\" mode=\"assign\" binding=\"same\">\n");
                    sb.append(String.format("            <string>%s</string>\n", value));
                    sb.append("        </edit>\n    </match>\n");
                    i++;
                }
            }
        }
        StringBuilder sb2 = new StringBuilder("<?xml version=\"1.0\"?>\n<!DOCTYPE fontconfig SYSTEM \"fonts.dtd\">\n<fontconfig>\n    <dir prefix=\"cwd\">.</dir>\n");
        for (String str : list) {
            sb2.append("    <dir>");
            sb2.append(str);
            sb2.append("</dir>\n");
        }
        sb2.append((CharSequence) sb);
        sb2.append("</fontconfig>\n");
        AtomicReference atomicReference = new AtomicReference();
        try {
            try {
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file2);
                    atomicReference.set(fileOutputStream);
                    fileOutputStream.write(sb2.toString().getBytes());
                    fileOutputStream.flush();
                    android.util.Log.d(TAG, String.format("Saved new temporary font configuration with %d font name mappings.", Integer.valueOf(i)));
                    setFontconfigConfigurationPath(file.getAbsolutePath());
                    Iterator<String> it = list.iterator();
                    while (it.hasNext()) {
                        android.util.Log.d(TAG, String.format("Font directory %s registered successfully.", it.next()));
                    }
                } catch (IOException e) {
                    android.util.Log.e(TAG, String.format("Failed to set font directory: %s.%s", Arrays.toString(list.toArray()), Exceptions.getStackTraceString(e)));
                    if (atomicReference.get() == null) {
                        return;
                    } else {
                        obj = atomicReference.get();
                    }
                }
                if (obj2 != null) {
                    obj = atomicReference.get();
                    ((FileOutputStream) obj).close();
                }
            } catch (IOException unused) {
            }
        } finally {
            if (atomicReference.get() != null) {
                try {
                    ((FileOutputStream) atomicReference.get()).close();
                } catch (IOException unused2) {
                }
            }
        }
    }

    public static String registerNewFFmpegPipe(Context context) {
        File file = new File(context.getCacheDir(), "pipes");
        if (!file.exists() && !file.mkdirs()) {
            android.util.Log.e(TAG, String.format("Failed to create pipes directory: %s.", file.getAbsolutePath()));
            return null;
        }
        String format = MessageFormat.format("{0}{1}{2}{3}", file, File.separator, FFMPEG_KIT_NAMED_PIPE_PREFIX, Integer.valueOf(uniqueIdGenerator.getAndIncrement()));
        closeFFmpegPipe(format);
        int registerNewNativeFFmpegPipe = registerNewNativeFFmpegPipe(format);
        if (registerNewNativeFFmpegPipe == 0) {
            return format;
        }
        android.util.Log.e(TAG, String.format("Failed to register new FFmpeg pipe %s. Operation failed with rc=%d.", format, Integer.valueOf(registerNewNativeFFmpegPipe)));
        return null;
    }

    public static void closeFFmpegPipe(String str) {
        File file = new File(str);
        if (file.exists()) {
            file.delete();
        }
    }

    public static List<String> getSupportedCameraIds(Context context) {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(CameraSupport.extractSupportedCameraIds(context));
        return arrayList;
    }

    public static String getFFmpegVersion() {
        return getNativeFFmpegVersion();
    }

    public static String getVersion() {
        if (isLTSBuild()) {
            return String.format("%s-lts", getNativeVersion());
        }
        return getNativeVersion();
    }

    public static boolean isLTSBuild() {
        return AbiDetect.isNativeLTSBuild();
    }

    public static String getBuildDate() {
        return getNativeBuildDate();
    }

    public static void printToLogcat(int i, String str) {
        do {
            if (str.length() <= 4000) {
                android.util.Log.println(i, TAG, str);
                str = "";
            } else {
                int lastIndexOf = str.substring(0, 4000).lastIndexOf(10);
                if (lastIndexOf < 0) {
                    android.util.Log.println(i, TAG, str.substring(0, 4000));
                    str = str.substring(4000);
                } else {
                    android.util.Log.println(i, TAG, str.substring(0, lastIndexOf));
                    str = str.substring(lastIndexOf);
                }
            }
        } while (str.length() > 0);
    }

    public static int setEnvironmentVariable(String str, String str2) {
        return setNativeEnvironmentVariable(str, str2);
    }

    public static void ignoreSignal(Signal signal) {
        ignoreNativeSignal(signal.getValue());
    }

    public static void ffmpegExecute(FFmpegSession fFmpegSession) {
        fFmpegSession.startRunning();
        try {
            fFmpegSession.complete(new ReturnCode(nativeFFmpegExecute(fFmpegSession.getSessionId(), fFmpegSession.getArguments())));
        } catch (Exception e) {
            fFmpegSession.fail(e);
            android.util.Log.w(TAG, String.format("FFmpeg execute failed: %s.%s", argumentsToString(fFmpegSession.getArguments()), Exceptions.getStackTraceString(e)));
        }
    }

    public static void ffprobeExecute(FFprobeSession fFprobeSession) {
        fFprobeSession.startRunning();
        try {
            fFprobeSession.complete(new ReturnCode(nativeFFprobeExecute(fFprobeSession.getSessionId(), fFprobeSession.getArguments())));
        } catch (Exception e) {
            fFprobeSession.fail(e);
            android.util.Log.w(TAG, String.format("FFprobe execute failed: %s.%s", argumentsToString(fFprobeSession.getArguments()), Exceptions.getStackTraceString(e)));
        }
    }

    public static void getMediaInformationExecute(MediaInformationSession mediaInformationSession, int i) {
        mediaInformationSession.startRunning();
        try {
            ReturnCode returnCode = new ReturnCode(nativeFFprobeExecute(mediaInformationSession.getSessionId(), mediaInformationSession.getArguments()));
            mediaInformationSession.complete(returnCode);
            if (returnCode.isValueSuccess()) {
                List<Log> allLogs = mediaInformationSession.getAllLogs(i);
                StringBuilder sb = new StringBuilder();
                int size = allLogs.size();
                for (int i2 = 0; i2 < size; i2++) {
                    Log log = allLogs.get(i2);
                    if (log.getLevel() == Level.AV_LOG_STDERR) {
                        sb.append(log.getMessage());
                    }
                }
                mediaInformationSession.setMediaInformation(MediaInformationJsonParser.fromWithError(sb.toString()));
            }
        } catch (Exception e) {
            mediaInformationSession.fail(e);
            android.util.Log.w(TAG, String.format("Get media information execute failed: %s.%s", argumentsToString(mediaInformationSession.getArguments()), Exceptions.getStackTraceString(e)));
        }
    }

    public static void asyncFFmpegExecute(FFmpegSession fFmpegSession) {
        fFmpegSession.setFuture(asyncExecutorService.submit(new AsyncFFmpegExecuteTask(fFmpegSession)));
    }

    public static void asyncFFmpegExecute(FFmpegSession fFmpegSession, ExecutorService executorService) {
        fFmpegSession.setFuture(executorService.submit(new AsyncFFmpegExecuteTask(fFmpegSession)));
    }

    public static void asyncFFprobeExecute(FFprobeSession fFprobeSession) {
        fFprobeSession.setFuture(asyncExecutorService.submit(new AsyncFFprobeExecuteTask(fFprobeSession)));
    }

    public static void asyncFFprobeExecute(FFprobeSession fFprobeSession, ExecutorService executorService) {
        fFprobeSession.setFuture(executorService.submit(new AsyncFFprobeExecuteTask(fFprobeSession)));
    }

    public static void asyncGetMediaInformationExecute(MediaInformationSession mediaInformationSession, int i) {
        mediaInformationSession.setFuture(asyncExecutorService.submit(new AsyncGetMediaInformationTask(mediaInformationSession, Integer.valueOf(i))));
    }

    public static void asyncGetMediaInformationExecute(MediaInformationSession mediaInformationSession, ExecutorService executorService, int i) {
        mediaInformationSession.setFuture(executorService.submit(new AsyncGetMediaInformationTask(mediaInformationSession, Integer.valueOf(i))));
    }

    public static int getAsyncConcurrencyLimit() {
        return asyncConcurrencyLimit;
    }

    public static void setAsyncConcurrencyLimit(int i) {
        if (i > 0) {
            asyncConcurrencyLimit = i;
            ExecutorService executorService = asyncExecutorService;
            asyncExecutorService = Executors.newFixedThreadPool(i);
            executorService.shutdown();
        }
    }

    public static void enableLogCallback(LogCallback logCallback) {
        globalLogCallback = logCallback;
    }

    public static void enableStatisticsCallback(StatisticsCallback statisticsCallback) {
        globalStatisticsCallback = statisticsCallback;
    }

    public static void enableFFmpegSessionCompleteCallback(FFmpegSessionCompleteCallback fFmpegSessionCompleteCallback) {
        globalFFmpegSessionCompleteCallback = fFmpegSessionCompleteCallback;
    }

    public static FFmpegSessionCompleteCallback getFFmpegSessionCompleteCallback() {
        return globalFFmpegSessionCompleteCallback;
    }

    public static void enableFFprobeSessionCompleteCallback(FFprobeSessionCompleteCallback fFprobeSessionCompleteCallback) {
        globalFFprobeSessionCompleteCallback = fFprobeSessionCompleteCallback;
    }

    public static FFprobeSessionCompleteCallback getFFprobeSessionCompleteCallback() {
        return globalFFprobeSessionCompleteCallback;
    }

    public static void enableMediaInformationSessionCompleteCallback(MediaInformationSessionCompleteCallback mediaInformationSessionCompleteCallback) {
        globalMediaInformationSessionCompleteCallback = mediaInformationSessionCompleteCallback;
    }

    public static MediaInformationSessionCompleteCallback getMediaInformationSessionCompleteCallback() {
        return globalMediaInformationSessionCompleteCallback;
    }

    public static Level getLogLevel() {
        return activeLogLevel;
    }

    public static void setLogLevel(Level level) {
        if (level != null) {
            activeLogLevel = level;
            setNativeLogLevel(level.getValue());
        }
    }

    static String extractExtensionFromSafDisplayName(String str) {
        try {
            return new StringTokenizer(str.lastIndexOf(".") >= 0 ? str.substring(str.lastIndexOf(".")) : str, " .").nextToken();
        } catch (Exception e) {
            android.util.Log.w(TAG, String.format("Failed to extract extension from saf display name: %s.%s", str, Exceptions.getStackTraceString(e)));
            return "raw";
        }
    }

    public static String getSafParameter(Context context, Uri uri, String openMode) {
        String displayNameColumn = "_display_name";
        String displayName = "unknown";
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex(displayNameColumn);
                displayName = cursor.getString(columnIndex);
            }
        } catch (Throwable throwable) {
            if (cursor != null) {
                try {
                    cursor.close();
                } catch (Throwable suppressed) {
                    throwable.addSuppressed(suppressed);
                }
            }
            android.util.Log.e(TAG, String.format("Failed to get %s column for %s.%s",
                    displayNameColumn, uri.toString(), Exceptions.getStackTraceString(throwable)));
            throw throwable;
        }
        if (cursor != null) {
            cursor.close();
        }

        int safId = uniqueIdGenerator.getAndIncrement();
        safIdMap.put(safId, new SAFProtocolUrl(Integer.valueOf(safId), uri, openMode, context.getContentResolver()));

        return new StringBuilder("saf:").append(safId).append(".").append(extractExtensionFromSafDisplayName(displayName)).toString();
    }

    public static String getSafParameterForRead(Context context, Uri uri) {
        return getSafParameter(context, uri, "r");
    }

    public static String getSafParameterForWrite(Context context, Uri uri) {
        return getSafParameter(context, uri, "w");
    }

    private static int safOpen(int i) {
        try {
            SAFProtocolUrl sAFProtocolUrl = safIdMap.get(i);
            if (sAFProtocolUrl == null) {
                android.util.Log.e(TAG, String.format("SAF id %d not found.", Integer.valueOf(i)));
                return 0;
            }
            ParcelFileDescriptor openFileDescriptor = sAFProtocolUrl.getContentResolver().openFileDescriptor(sAFProtocolUrl.getUri(), sAFProtocolUrl.getOpenMode());
            sAFProtocolUrl.setParcelFileDescriptor(openFileDescriptor);
            int fd = openFileDescriptor.getFd();
            safFileDescriptorMap.put(fd, sAFProtocolUrl);
            return fd;
        } catch (Throwable th) {
            android.util.Log.e(TAG, String.format("Failed to open SAF id: %d.%s", Integer.valueOf(i), Exceptions.getStackTraceString(th)));
            return 0;
        }
    }

    private static int safClose(int i) {
        try {
            SparseArray<SAFProtocolUrl> sparseArray = safFileDescriptorMap;
            SAFProtocolUrl sAFProtocolUrl = sparseArray.get(i);
            if (sAFProtocolUrl == null) {
                android.util.Log.e(TAG, String.format("SAF fd %d not found.", Integer.valueOf(i)));
                return 0;
            }
            ParcelFileDescriptor parcelFileDescriptor = sAFProtocolUrl.getParcelFileDescriptor();
            if (parcelFileDescriptor == null) {
                android.util.Log.e(TAG, String.format("ParcelFileDescriptor for SAF fd %d not found.", Integer.valueOf(i)));
                return 0;
            }
            sparseArray.delete(i);
            safIdMap.delete(sAFProtocolUrl.getSafId().intValue());
            parcelFileDescriptor.close();
            return 1;
        } catch (Throwable th) {
            android.util.Log.e(TAG, String.format("Failed to close SAF fd: %d.%s", Integer.valueOf(i), Exceptions.getStackTraceString(th)));
            return 0;
        }
    }

    public static int getSessionHistorySize() {
        return sessionHistorySize;
    }

    public static void setSessionHistorySize(int i) {
        if (i >= 1000) {
            throw new IllegalArgumentException("Session history size must not exceed the hard limit!");
        }
        if (i > 0) {
            sessionHistorySize = i;
            deleteExpiredSessions();
        }
    }

    private static void deleteExpiredSessions() {
        while (true) {
            List<Session> list = sessionHistoryList;
            if (list.size() <= sessionHistorySize) {
                return;
            }
            try {
                Session remove = list.remove(0);
                if (remove != null) {
                    sessionHistoryMap.remove(Long.valueOf(remove.getSessionId()));
                }
            } catch (IndexOutOfBoundsException unused) {
            }
        }
    }

    static void addSession(Session session) {
        synchronized (sessionHistoryLock) {
            Map<Long, Session> map = sessionHistoryMap;
            if (!map.containsKey(Long.valueOf(session.getSessionId()))) {
                map.put(Long.valueOf(session.getSessionId()), session);
                sessionHistoryList.add(session);
                deleteExpiredSessions();
            }
        }
    }

    public static Session getSession(long j) {
        Session session;
        synchronized (sessionHistoryLock) {
            session = sessionHistoryMap.get(Long.valueOf(j));
        }
        return session;
    }

    public static Session getLastSession() {
        synchronized (sessionHistoryLock) {
            List<Session> list = sessionHistoryList;
            if (list.size() <= 0) {
                return null;
            }
            return list.get(list.size() - 1);
        }
    }

    public static Session getLastCompletedSession() {
        synchronized (sessionHistoryLock) {
            for (int size = sessionHistoryList.size() - 1; size >= 0; size--) {
                Session session = sessionHistoryList.get(size);
                if (session.getState() == SessionState.COMPLETED) {
                    return session;
                }
            }
            return null;
        }
    }

    public static List<Session> getSessions() {
        LinkedList linkedList;
        synchronized (sessionHistoryLock) {
            linkedList = new LinkedList(sessionHistoryList);
        }
        return linkedList;
    }

    public static void clearSessions() {
        synchronized (sessionHistoryLock) {
            sessionHistoryList.clear();
            sessionHistoryMap.clear();
        }
    }

    public static List<FFmpegSession> getFFmpegSessions() {
        LinkedList linkedList = new LinkedList();
        synchronized (sessionHistoryLock) {
            for (Session session : sessionHistoryList) {
                if (session.isFFmpeg()) {
                    linkedList.add((FFmpegSession) session);
                }
            }
        }
        return linkedList;
    }

    public static List<FFprobeSession> getFFprobeSessions() {
        LinkedList linkedList = new LinkedList();
        synchronized (sessionHistoryLock) {
            for (Session session : sessionHistoryList) {
                if (session.isFFprobe()) {
                    linkedList.add((FFprobeSession) session);
                }
            }
        }
        return linkedList;
    }

    public static List<MediaInformationSession> getMediaInformationSessions() {
        LinkedList linkedList = new LinkedList();
        synchronized (sessionHistoryLock) {
            for (Session session : sessionHistoryList) {
                if (session.isMediaInformation()) {
                    linkedList.add((MediaInformationSession) session);
                }
            }
        }
        return linkedList;
    }

    public static List<Session> getSessionsByState(SessionState sessionState) {
        LinkedList linkedList = new LinkedList();
        synchronized (sessionHistoryLock) {
            for (Session session : sessionHistoryList) {
                if (session.getState() == sessionState) {
                    linkedList.add(session);
                }
            }
        }
        return linkedList;
    }

    public static LogRedirectionStrategy getLogRedirectionStrategy() {
        return globalLogRedirectionStrategy;
    }

    public static void setLogRedirectionStrategy(LogRedirectionStrategy logRedirectionStrategy) {
        globalLogRedirectionStrategy = logRedirectionStrategy;
    }

    public static String sessionStateToString(SessionState sessionState) {
        return sessionState.toString();
    }

    public static String[] parseArguments(String str) {
        ArrayList arrayList = new ArrayList();
        StringBuilder sb = new StringBuilder();
        int i = 0;
        boolean z = false;
        boolean z2 = false;
        while (i < str.length()) {
            Character valueOf = i > 0 ? Character.valueOf(str.charAt(i - 1)) : null;
            char charAt = str.charAt(i);
            if (charAt == ' ') {
                if (z || z2) {
                    sb.append(charAt);
                } else if (sb.length() > 0) {
                    arrayList.add(sb.toString());
                    sb = new StringBuilder();
                }
            } else if (charAt != '\'' || (valueOf != null && valueOf.charValue() == '\\')) {
                if (charAt != '\"' || (valueOf != null && valueOf.charValue() == '\\')) {
                    sb.append(charAt);
                } else if (z2) {
                    z2 = false;
                } else if (z) {
                    sb.append(charAt);
                } else {
                    z2 = true;
                }
            } else if (z) {
                z = false;
            } else if (z2) {
                sb.append(charAt);
            } else {
                z = true;
            }
            i++;
        }
        if (sb.length() > 0) {
            arrayList.add(sb.toString());
        }
        return (String[]) arrayList.toArray(new String[0]);
    }

    public static String argumentsToString(String[] strArr) {
        if (strArr == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strArr.length; i++) {
            if (i > 0) {
                sb.append(" ");
            }
            sb.append(strArr[i]);
        }
        return sb.toString();
    }
}

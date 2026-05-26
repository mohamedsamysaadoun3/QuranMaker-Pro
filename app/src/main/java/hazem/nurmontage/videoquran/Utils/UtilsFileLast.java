package hazem.nurmontage.videoquran.Utils;

import android.content.ContentUris;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import androidx.media3.common.MimeTypes;

/* loaded from: classes2.dex */
public class UtilsFileLast {
    private static final String TAG = "UtilsFileLast";

    private static String extractNumericId(String str) {
        return str;
    }

    public static Typeface loadFontFromAsset(Context context, String str) {
        try {
            return Typeface.createFromAsset(context.getAssets(), str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getPath(Context context, Uri uri) {
        Uri uri2;
        Log.d(TAG, "getPath called with URI: " + uri);
        if (context == null || uri == null) {
            Log.e(TAG, "Context or URI is null");
            return null;
        }
        if (DocumentsContract.isDocumentUri(context, uri)) {
            Log.d(TAG, "URI is a document URI");
            if (isExternalStorageDocument(uri)) {
                Log.d(TAG, "URI is an external storage document");
                String documentId = DocumentsContract.getDocumentId(uri);
                String[] split = documentId.split(":");
                if ("primary".equalsIgnoreCase(split[0])) {
                    String str = Environment.getExternalStorageDirectory() + "/" + split[1];
                    Log.d(TAG, "External storage path (primary): " + str);
                    return str;
                }
                Log.d(TAG, "External storage path (non-primary): " + documentId);
                String pathFromTreeUri = getPathFromTreeUri(context, DocumentsContract.buildTreeDocumentUri("com.android.externalstorage.documents", documentId), split[1]);
                if (pathFromTreeUri != null) {
                    return pathFromTreeUri;
                }
                return null;
            }
            if (isDownloadsDocument(uri)) {
                Log.d(TAG, "URI is a downloads document");
                String documentId2 = DocumentsContract.getDocumentId(uri);
                String extractNumericId = extractNumericId(documentId2);
                if (extractNumericId == null) {
                    Log.e(TAG, "Could not extract numeric ID from downloads document ID: " + documentId2);
                    return null;
                }
                try {
                    String dataColumn = getDataColumn(context, ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.parseLong(extractNumericId)), null, null);
                    Log.d(TAG, "Downloads document path: " + dataColumn);
                    return dataColumn;
                } catch (NumberFormatException e) {
                    Log.e(TAG, "Error parsing numeric ID from downloads document ID: " + extractNumericId, e);
                    return null;
                }
            }
            if (isMediaDocument(uri)) {
                Log.d(TAG, "URI is a media document");
                String[] split2 = DocumentsContract.getDocumentId(uri).split(":");
                String str2 = split2[0];
                if ("image".equals(str2)) {
                    uri2 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if (MimeTypes.BASE_TYPE_VIDEO.equals(str2)) {
                    uri2 = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if (MimeTypes.BASE_TYPE_AUDIO.equals(str2)) {
                    uri2 = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                } else {
                    Log.w(TAG, "Unsupported media document type: " + str2);
                    return null;
                }
                String dataColumn2 = getDataColumn(context, uri2, "_id=?", new String[]{split2[1]});
                Log.d(TAG, "Media document path: " + dataColumn2);
                return dataColumn2;
            }
            Log.w(TAG, "Unsupported document URI: " + uri);
            return null;
        }
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            Log.d(TAG, "URI is a content URI");
            String dataColumn3 = getDataColumn(context, uri, null, null);
            Log.d(TAG, "Content URI path: " + dataColumn3);
            return dataColumn3;
        }
        if ("file".equalsIgnoreCase(uri.getScheme())) {
            Log.d(TAG, "URI is a file URI");
            String path = uri.getPath();
            Log.d(TAG, "File URI path: " + path);
            return path;
        }
        Log.w(TAG, "Unsupported URI scheme: " + uri.getScheme());
        return null;
    }

    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x003e, code lost:
    
        if (r8 == null) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:5:0x002d, code lost:
    
        if (r8 != null) goto L14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x002f, code lost:
    
        r8.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x0041, code lost:
    
        return null;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0046  */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v3, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r0v4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static java.lang.String getDataColumn(android.content.Context r8, android.net.Uri r9, java.lang.String r10, java.lang.String[] r11) {
        /*
            r0 = 1
            java.lang.String[] r3 = new java.lang.String[r0]
            r0 = 0
            java.lang.String r7 = "_data"
            r3[r0] = r7
            r0 = 0
            android.content.ContentResolver r1 = r8.getContentResolver()     // Catch: java.lang.Throwable -> L33 java.lang.Exception -> L35
            r6 = 0
            r2 = r9
            r4 = r10
            r5 = r11
            android.database.Cursor r8 = r1.query(r2, r3, r4, r5, r6)     // Catch: java.lang.Throwable -> L33 java.lang.Exception -> L35
            if (r8 == 0) goto L2d
            boolean r9 = r8.moveToFirst()     // Catch: java.lang.Exception -> L2b java.lang.Throwable -> L42
            if (r9 == 0) goto L2d
            int r9 = r8.getColumnIndexOrThrow(r7)     // Catch: java.lang.Exception -> L2b java.lang.Throwable -> L42
            java.lang.String r9 = r8.getString(r9)     // Catch: java.lang.Exception -> L2b java.lang.Throwable -> L42
            if (r8 == 0) goto L2a
            r8.close()
        L2a:
            return r9
        L2b:
            r9 = move-exception
            goto L37
        L2d:
            if (r8 == 0) goto L41
        L2f:
            r8.close()
            goto L41
        L33:
            r9 = move-exception
            goto L44
        L35:
            r9 = move-exception
            r8 = r0
        L37:
            java.lang.String r10 = "UtilsFileLast"
            java.lang.String r11 = "Error getting data column"
            android.util.Log.e(r10, r11, r9)     // Catch: java.lang.Throwable -> L42
            if (r8 == 0) goto L41
            goto L2f
        L41:
            return r0
        L42:
            r9 = move-exception
            r0 = r8
        L44:
            if (r0 == 0) goto L49
            r0.close()
        L49:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: hazem.nurmontage.videoquran.Utils.UtilsFileLast.getDataColumn(android.content.Context, android.net.Uri, java.lang.String, java.lang.String[]):java.lang.String");
    }

    /* JADX WARN: Code restructure failed: missing block: B:44:0x00a5, code lost:
    
        if (r4 == null) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:5:0x0097, code lost:
    
        if (r4 != null) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x00aa, code lost:
    
        return null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x00a7, code lost:
    
        r4.close();
     */
    /* JADX WARN: Not initialized variable reg: 4, insn: 0x00ac: MOVE (r3 I:??[OBJECT, ARRAY]) = (r4 I:??[OBJECT, ARRAY]), block:B:47:0x00ac */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00af  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static java.lang.String getPathFromTreeUri(android.content.Context r12, android.net.Uri r13, java.lang.String r14) {
        /*
            java.lang.String r0 = "mime_type"
            java.lang.String r1 = "_display_name"
            java.lang.String r2 = "document_id"
            r3 = 0
            java.lang.String r4 = r13.toString()     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9c
            java.lang.String r5 = android.provider.DocumentsContract.getTreeDocumentId(r13)     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9c
            android.net.Uri r7 = android.provider.DocumentsContract.buildChildDocumentsUri(r4, r5)     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9c
            android.content.ContentResolver r6 = r12.getContentResolver()     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9c
            r4 = 3
            java.lang.String[] r8 = new java.lang.String[r4]     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9c
            r4 = 0
            r8[r4] = r2     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9c
            r4 = 1
            r8[r4] = r1     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9c
            r4 = 2
            r8[r4] = r0     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9c
            r10 = 0
            r11 = 0
            r9 = 0
            android.database.Cursor r4 = r6.query(r7, r8, r9, r10, r11)     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9c
            if (r4 == 0) goto L97
            boolean r5 = r4.moveToFirst()     // Catch: java.lang.Exception -> L95 java.lang.Throwable -> Lab
            if (r5 == 0) goto L97
        L32:
            int r5 = r4.getColumnIndexOrThrow(r2)     // Catch: java.lang.Exception -> L95 java.lang.Throwable -> Lab
            java.lang.String r5 = r4.getString(r5)     // Catch: java.lang.Exception -> L95 java.lang.Throwable -> Lab
            int r6 = r4.getColumnIndexOrThrow(r1)     // Catch: java.lang.Exception -> L95 java.lang.Throwable -> Lab
            java.lang.String r6 = r4.getString(r6)     // Catch: java.lang.Exception -> L95 java.lang.Throwable -> Lab
            int r7 = r4.getColumnIndexOrThrow(r0)     // Catch: java.lang.Exception -> L95 java.lang.Throwable -> Lab
            java.lang.String r7 = r4.getString(r7)     // Catch: java.lang.Exception -> L95 java.lang.Throwable -> Lab
            boolean r6 = r6.equals(r14)     // Catch: java.lang.Exception -> L95 java.lang.Throwable -> Lab
            java.lang.String r8 = "vnd.android.document/directory"
            if (r6 == 0) goto L78
            boolean r6 = r8.equals(r7)     // Catch: java.lang.Exception -> L95 java.lang.Throwable -> Lab
            if (r6 == 0) goto L68
            android.net.Uri r5 = android.provider.DocumentsContract.buildDocumentUriUsingTree(r13, r5)     // Catch: java.lang.Exception -> L95 java.lang.Throwable -> Lab
            java.lang.String r5 = getPathFromTreeUri(r12, r5, r14)     // Catch: java.lang.Exception -> L95 java.lang.Throwable -> Lab
            if (r5 == 0) goto L8e
            if (r4 == 0) goto L67
            r4.close()
        L67:
            return r5
        L68:
            android.net.Uri r5 = android.provider.DocumentsContract.buildDocumentUriUsingTree(r13, r5)     // Catch: java.lang.Exception -> L95 java.lang.Throwable -> Lab
            java.lang.String r5 = getDataColumn(r12, r5, r3, r3)     // Catch: java.lang.Exception -> L95 java.lang.Throwable -> Lab
            if (r5 == 0) goto L8e
            if (r4 == 0) goto L77
            r4.close()
        L77:
            return r5
        L78:
            boolean r6 = r7.equals(r8)     // Catch: java.lang.Exception -> L95 java.lang.Throwable -> Lab
            if (r6 == 0) goto L8e
            android.net.Uri r5 = android.provider.DocumentsContract.buildDocumentUriUsingTree(r13, r5)     // Catch: java.lang.Exception -> L95 java.lang.Throwable -> Lab
            java.lang.String r5 = getPathFromTreeUri(r12, r5, r14)     // Catch: java.lang.Exception -> L95 java.lang.Throwable -> Lab
            if (r5 == 0) goto L8e
            if (r4 == 0) goto L8d
            r4.close()
        L8d:
            return r5
        L8e:
            boolean r5 = r4.moveToNext()     // Catch: java.lang.Exception -> L95 java.lang.Throwable -> Lab
            if (r5 != 0) goto L32
            goto L97
        L95:
            r12 = move-exception
            goto L9e
        L97:
            if (r4 == 0) goto Laa
            goto La7
        L9a:
            r12 = move-exception
            goto Lad
        L9c:
            r12 = move-exception
            r4 = r3
        L9e:
            java.lang.String r13 = "UtilsFileLast"
            java.lang.String r14 = "Error in getPathFromTreeUri"
            android.util.Log.e(r13, r14, r12)     // Catch: java.lang.Throwable -> Lab
            if (r4 == 0) goto Laa
        La7:
            r4.close()
        Laa:
            return r3
        Lab:
            r12 = move-exception
            r3 = r4
        Lad:
            if (r3 == 0) goto Lb2
            r3.close()
        Lb2:
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: hazem.nurmontage.videoquran.Utils.UtilsFileLast.getPathFromTreeUri(android.content.Context, android.net.Uri, java.lang.String):java.lang.String");
    }
}

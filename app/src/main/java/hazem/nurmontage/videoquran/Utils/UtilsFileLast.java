package hazem.nurmontage.videoquran.Utils;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
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

    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        String[] projection = {"_data"};
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                return cursor.getString(cursor.getColumnIndexOrThrow("_data"));
            }
            return null;
        } catch (Exception e) {
            Log.e(TAG, "Error getting data column", e);
            return null;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private static String getPathFromTreeUri(Context context, Uri treeUri, String targetName) {
        Cursor cursor = null;
        try {
            String authority = treeUri.toString();
            String treeDocId = DocumentsContract.getTreeDocumentId(treeUri);
            Uri childrenUri = DocumentsContract.buildChildDocumentsUri(authority, treeDocId);
            cursor = context.getContentResolver().query(childrenUri,
                    new String[]{"document_id", "_display_name", "mime_type"}, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String documentId = cursor.getString(cursor.getColumnIndexOrThrow("document_id"));
                    String displayName = cursor.getString(cursor.getColumnIndexOrThrow("_display_name"));
                    String mimeType = cursor.getString(cursor.getColumnIndexOrThrow("mime_type"));
                    boolean isDirectory = "vnd.android.document/directory".equals(mimeType);
                    if (displayName.equals(targetName)) {
                        if (isDirectory) {
                            Uri childUri = DocumentsContract.buildDocumentUriUsingTree(treeUri, documentId);
                            String result = getPathFromTreeUri(context, childUri, targetName);
                            if (result != null) return result;
                        } else {
                            Uri childUri = DocumentsContract.buildDocumentUriUsingTree(treeUri, documentId);
                            String result = getDataColumn(context, childUri, null, null);
                            if (result != null) return result;
                        }
                    } else if (isDirectory) {
                        Uri childUri = DocumentsContract.buildDocumentUriUsingTree(treeUri, documentId);
                        String result = getPathFromTreeUri(context, childUri, targetName);
                        if (result != null) return result;
                    }
                } while (cursor.moveToNext());
            }
            return null;
        } catch (Exception e) {
            Log.e(TAG, "Error in getPathFromTreeUri", e);
            return null;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}

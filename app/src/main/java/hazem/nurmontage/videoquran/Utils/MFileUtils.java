package hazem.nurmontage.videoquran.Utils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* loaded from: classes2.dex */
public class MFileUtils {

    public static class FileInfo implements Serializable {
        public String formattedDate;
        public long lastModified;
        public String name;
        public String timedDate;

        public FileInfo(String str, long j) {
            this.name = str;
            this.lastModified = j;
            this.formattedDate = MFileUtils.formatDateShort(j);
            if (j > 0) {
                this.timedDate = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date(j));
            }
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(12:6|(14:32|33|(3:38|39|(3:41|(1:43)|(3:45|46|(6:48|(1:37)|(5:16|17|(1:19)(1:29)|20|(3:(1:23)|24|(2:26|27)))|(1:13)|14|15))))|35|(0)|(1:10)|16|17|(0)(0)|20|(0)|(0)|14|15)|8|(0)|16|17|(0)(0)|20|(0)|(0)|14|15) */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x00af, code lost:
    
        r13 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x00b0, code lost:
    
        r13.printStackTrace();
     */
    /* JADX WARN: Removed duplicated region for block: B:10:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x00b5  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x008a A[Catch: Exception -> 0x00af, TryCatch #0 {Exception -> 0x00af, blocks: (B:17:0x007e, B:19:0x008a, B:20:0x0099, B:23:0x00a1, B:26:0x00a9, B:29:0x0094), top: B:16:0x007e }] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x009f  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0094 A[Catch: Exception -> 0x00af, TryCatch #0 {Exception -> 0x00af, blocks: (B:17:0x007e, B:19:0x008a, B:20:0x0099, B:23:0x00a1, B:26:0x00a9, B:29:0x0094), top: B:16:0x007e }] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x005f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static hazem.nurmontage.videoquran.Utils.MFileUtils.FileInfo getFileInfo(android.content.Context r13, java.lang.String r14) {
        /*
            java.lang.String r0 = "date_modified"
            java.lang.String r1 = "_display_name"
            r2 = 0
            if (r14 != 0) goto L8
            return r2
        L8:
            android.net.Uri r9 = android.net.Uri.parse(r14)
            java.lang.String r3 = "content"
            java.lang.String r4 = r9.getScheme()
            boolean r3 = r3.equalsIgnoreCase(r4)
            r10 = 0
            if (r3 == 0) goto L77
            android.content.ContentResolver r3 = r13.getContentResolver()     // Catch: java.lang.Throwable -> L63 java.lang.Exception -> L65
            r13 = 2
            java.lang.String[] r5 = new java.lang.String[r13]     // Catch: java.lang.Throwable -> L63 java.lang.Exception -> L65
            r13 = 0
            r5[r13] = r1     // Catch: java.lang.Throwable -> L63 java.lang.Exception -> L65
            r13 = 1
            r5[r13] = r0     // Catch: java.lang.Throwable -> L63 java.lang.Exception -> L65
            r7 = 0
            r8 = 0
            r6 = 0
            r4 = r9
            android.database.Cursor r13 = r3.query(r4, r5, r6, r7, r8)     // Catch: java.lang.Throwable -> L63 java.lang.Exception -> L65
            if (r13 == 0) goto L5c
            boolean r3 = r13.moveToFirst()     // Catch: java.lang.Throwable -> L54 java.lang.Exception -> L57
            if (r3 == 0) goto L5c
            int r1 = r13.getColumnIndex(r1)     // Catch: java.lang.Throwable -> L54 java.lang.Exception -> L57
            int r0 = r13.getColumnIndex(r0)     // Catch: java.lang.Throwable -> L54 java.lang.Exception -> L57
            r3 = -1
            if (r1 == r3) goto L46
            java.lang.String r2 = r13.getString(r1)     // Catch: java.lang.Throwable -> L54 java.lang.Exception -> L57
        L46:
            if (r0 == r3) goto L5c
            long r0 = r13.getLong(r0)     // Catch: java.lang.Throwable -> L54 java.lang.Exception -> L57
            int r3 = (r0 > r10 ? 1 : (r0 == r10 ? 0 : -1))
            if (r3 <= 0) goto L5c
            r3 = 1000(0x3e8, double:4.94E-321)
            long r0 = r0 * r3
            goto L5d
        L54:
            r14 = move-exception
            r2 = r13
            goto L71
        L57:
            r0 = move-exception
            r12 = r2
            r2 = r13
            r13 = r12
            goto L67
        L5c:
            r0 = r10
        L5d:
            if (r13 == 0) goto L78
            r13.close()
            goto L78
        L63:
            r14 = move-exception
            goto L71
        L65:
            r0 = move-exception
            r13 = r2
        L67:
            r0.printStackTrace()     // Catch: java.lang.Throwable -> L63
            if (r2 == 0) goto L6f
            r2.close()
        L6f:
            r2 = r13
            goto L77
        L71:
            if (r2 == 0) goto L76
            r2.close()
        L76:
            throw r14
        L77:
            r0 = r10
        L78:
            if (r2 == 0) goto L7e
            int r13 = (r0 > r10 ? 1 : (r0 == r10 ? 0 : -1))
            if (r13 != 0) goto Lb3
        L7e:
            java.lang.String r13 = "file"
            java.lang.String r3 = r9.getScheme()     // Catch: java.lang.Exception -> Laf
            boolean r13 = r13.equalsIgnoreCase(r3)     // Catch: java.lang.Exception -> Laf
            if (r13 == 0) goto L94
            java.io.File r13 = new java.io.File     // Catch: java.lang.Exception -> Laf
            java.lang.String r14 = r9.getPath()     // Catch: java.lang.Exception -> Laf
            r13.<init>(r14)     // Catch: java.lang.Exception -> Laf
            goto L99
        L94:
            java.io.File r13 = new java.io.File     // Catch: java.lang.Exception -> Laf
            r13.<init>(r14)     // Catch: java.lang.Exception -> Laf
        L99:
            boolean r14 = r13.exists()     // Catch: java.lang.Exception -> Laf
            if (r14 == 0) goto Lb3
            if (r2 != 0) goto La5
            java.lang.String r2 = r13.getName()     // Catch: java.lang.Exception -> Laf
        La5:
            int r14 = (r0 > r10 ? 1 : (r0 == r10 ? 0 : -1))
            if (r14 != 0) goto Lb3
            long r13 = r13.lastModified()     // Catch: java.lang.Exception -> Laf
            r0 = r13
            goto Lb3
        Laf:
            r13 = move-exception
            r13.printStackTrace()
        Lb3:
            if (r2 != 0) goto Lb9
            java.lang.String r2 = r9.getLastPathSegment()
        Lb9:
            hazem.nurmontage.videoquran.Utils.MFileUtils$FileInfo r13 = new hazem.nurmontage.videoquran.Utils.MFileUtils$FileInfo
            r13.<init>(r2, r0)
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: hazem.nurmontage.videoquran.Utils.MFileUtils.getFileInfo(android.content.Context, java.lang.String):hazem.nurmontage.videoquran.Utils.MFileUtils$FileInfo");
    }

    public static String formatDateShort(long j) {
        if (j <= 0) {
            return "";
        }
        return new SimpleDateFormat("MMM dd-yyyy", Locale.ENGLISH).format(new Date(j));
    }
}

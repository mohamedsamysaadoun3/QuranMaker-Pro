package hazem.nurmontage.videoquran.Utils;

import android.content.Context;
import android.util.Log;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

/* loaded from: classes2.dex */
public class QuranFileUtils {
    private static final String REPLACEMENT = "*";
    private static final String TARGET = "بِّسْمِ اللَّهِ الرَّحْمَـٰنِ الرَّحِيمِ";

    public static void replacePhraseInFile(File file, File file2) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
        while (true) {
            try {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    sb.append(readLine).append('\n');
                } else {
                    bufferedReader.close();
                    String replace = sb.toString().replace(TARGET, REPLACEMENT);
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file2), StandardCharsets.UTF_8));
                    try {
                        bufferedWriter.write(replace);
                        bufferedWriter.close();
                        return;
                    } catch (Throwable th) {
                        try {
                            bufferedWriter.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th3) {
                try {
                    bufferedReader.close();
                } catch (Throwable th4) {
                    th3.addSuppressed(th4);
                }
                throw th3;
            }
        }
    }

    public static void replacePhraseFromAssetsToFilesDir(Context context, String str, String str2) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStream open = context.getAssets().open(str);
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(open, StandardCharsets.UTF_8));
            while (true) {
                try {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    } else {
                        sb.append(readLine).append('\n');
                    }
                } finally {
                }
            }
            bufferedReader.close();
            if (open != null) {
                open.close();
            }
            String replace = sb.toString().replace(TARGET, REPLACEMENT);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(context.getFilesDir(), str2)), StandardCharsets.UTF_8));
            try {
                bufferedWriter.write(replace);
                bufferedWriter.close();
            } catch (Throwable th) {
                try {
                    bufferedWriter.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (Throwable th3) {
            if (open != null) {
                try {
                    open.close();
                } catch (Throwable th4) {
                    th3.addSuppressed(th4);
                }
            }
            throw th3;
        }
    }

    public static void counTPhraseFromAssetsToFilesDir(Context context, String str) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStream open = context.getAssets().open(str);
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(open, StandardCharsets.UTF_8));
            while (true) {
                try {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    if (readLine.contains("بِّسْمِ اللَّهِ الرَّحْمَـٰنِ الرَّحِيم")) {
                        Log.e("mLine", "" + readLine);
                    }
                    sb.append(readLine).append('\n');
                } finally {
                }
            }
            bufferedReader.close();
            if (open != null) {
                open.close();
            }
        } catch (Throwable th) {
            if (open != null) {
                try {
                    open.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }
}

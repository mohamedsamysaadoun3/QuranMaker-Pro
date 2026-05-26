package hazem.nurmontage.videoquran.Utils;

import android.content.Context;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/* loaded from: classes2.dex */
public class QuranReader {
    private final Context context;

    public QuranReader(Context context) {
        this.context = context;
    }

    public String getAyahText(int i, int i2) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.context.getAssets().open("quran/quran-simple.txt"), StandardCharsets.UTF_8));
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    String[] split = readLine.split("\\|");
                    if (split.length == 3) {
                        try {
                            int parseInt = Integer.parseInt(split[0]);
                            int parseInt2 = Integer.parseInt(split[1]);
                            String str = split[2];
                            if (parseInt == i && parseInt2 == i2) {
                                bufferedReader.close();
                                return str;
                            }
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    bufferedReader.close();
                    return "Ayah not found";
                }
            }
        } catch (IOException e2) {
            e2.printStackTrace();
            return "Error reading file: " + e2.getMessage();
        }
    }

    public String getTranslationAyahText(String str, int i, int i2) {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(this.context.getAssets().open("quran/" + str), StandardCharsets.UTF_8));
            String prefix = i + "|" + i2;
            String readLine;
            do {
                readLine = bufferedReader.readLine();
                if (readLine == null) {
                    bufferedReader.close();
                    return "Aya Not Found !";
                }
            } while (!readLine.startsWith(prefix));
            String substring = readLine.substring(prefix.length());
            bufferedReader.close();
            return substring;
        } catch (IOException e) {
            e.printStackTrace();
            return "Aya Not Found !";
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }
}

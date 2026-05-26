package hazem.nurmontage.videoquran.Utils;

import android.content.Context;
import android.util.Log;
import com.bumptech.glide.load.Key;
import hazem.nurmontage.videoquran.ProVersionActivity$$ExternalSyntheticBackport0;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class TranslationExtractor {
    public static void convertJsonToTxt(Context context, String str, String str2) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(context.getAssets().open(str), StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                } else {
                    sb.append(readLine);
                }
            }
            bufferedReader.close();
            JSONObject jSONObject = new JSONObject(sb.toString());
            ArrayList<String> arrayList = new ArrayList();
            Iterator<String> keys = jSONObject.keys();
            while (keys.hasNext()) {
                arrayList.add(keys.next());
            }
            Collections.sort(arrayList, new Comparator() { // from class: hazem.nurmontage.videoquran.Utils.TranslationExtractor$$ExternalSyntheticLambda1
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return TranslationExtractor.lambda$convertJsonToTxt$0((String) obj, (String) obj2);
                }
            });
            LinkedHashMap<String, Map> linkedHashMap = new LinkedHashMap<>();
            HashMap hashMap = new HashMap();
            for (String str3 : arrayList) {
                String[] split = str3.split(":");
                String str4 = split[0];
                String str5 = split[1];
                int parseInt = Integer.parseInt(split[2]);
                String str6 = str4 + "|" + str5;
                String string = jSONObject.getString(str3);
                if (!string.matches("\\(\\d+\\)") && !string.matches("\\d+")) {
                    ((Map) linkedHashMap.computeIfAbsent(str6, new Function() { // from class: hazem.nurmontage.videoquran.Utils.TranslationExtractor$$ExternalSyntheticLambda2
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return TranslationExtractor.lambda$convertJsonToTxt$1((String) obj);
                        }
                    })).put(Integer.valueOf(parseInt), string);
                    hashMap.put(str6, Integer.valueOf(Math.max(((Integer) hashMap.getOrDefault(str6, 0)).intValue(), parseInt)));
                }
            }
            File file = new File(context.getExternalFilesDir(null), "QuranTranslations");
            if (!file.exists()) {
                file.mkdirs();
            }
            File file2 = new File(file, str2);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file2), Key.STRING_CHARSET_NAME));
            for (Map.Entry<String, Map> entry : linkedHashMap.entrySet()) {
                String str7 = (String) entry.getKey();
                Map map = (Map) entry.getValue();
                int intValue = ((Integer) hashMap.get(str7)).intValue();
                ArrayList arrayList2 = new ArrayList();
                for (int i = 1; i <= intValue; i++) {
                    arrayList2.add((String) map.getOrDefault(Integer.valueOf(i), "*"));
                }
                bufferedWriter.write(str7 + ProVersionActivity$$ExternalSyntheticBackport0.m(",", arrayList2));
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
            Log.d("JSON_TO_TXT", "Conversion completed. File saved: " + file2.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("JSON_TO_TXT", "Error: " + e.getMessage());
        }
    }

    static /* synthetic */ int lambda$convertJsonToTxt$0(String str, String str2) {
        String[] split = str.split(":");
        String[] split2 = str2.split(":");
        int parseInt = Integer.parseInt(split[0]);
        int parseInt2 = Integer.parseInt(split2[0]);
        int parseInt3 = Integer.parseInt(split[1]);
        int parseInt4 = Integer.parseInt(split2[1]);
        return parseInt != parseInt2 ? parseInt - parseInt2 : parseInt3 != parseInt4 ? parseInt3 - parseInt4 : Integer.parseInt(split[2]) - Integer.parseInt(split2[2]);
    }

    static /* synthetic */ Map lambda$convertJsonToTxt$1(String str) {
        return new LinkedHashMap();
    }

    /* JADX WARN: Removed duplicated region for block: B:57:0x0167 A[Catch: IOException -> 0x0163, TRY_LEAVE, TryCatch #3 {IOException -> 0x0163, blocks: (B:66:0x015f, B:57:0x0167), top: B:65:0x015f }] */
    /* JADX WARN: Removed duplicated region for block: B:64:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x015f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static void extractTranslationsBySurahAndAyah(android.content.Context r16) {
        /*
            Method dump skipped, instructions count: 367
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: hazem.nurmontage.videoquran.Utils.TranslationExtractor.extractTranslationsBySurahAndAyah(android.content.Context):void");
    }
}

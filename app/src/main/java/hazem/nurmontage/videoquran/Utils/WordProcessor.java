package hazem.nurmontage.videoquran.Utils;

import hazem.nurmontage.videoquran.model.WordModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes2.dex */
public class WordProcessor {
    public static int mapIndexAfterGroupReverse(int i, int i2, int i3) {
        return ((i / i2) * i2) + ((Math.min(i2, i3 - (i / i2)) - 1) - (i % i2));
    }

    public List<WordModel> reverseInGroupsOfFour(List<WordModel> list) {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (i < list.size()) {
            int i2 = i + 4;
            ArrayList arrayList2 = new ArrayList(list.subList(i, Math.min(i2, list.size())));
            Collections.reverse(arrayList2);
            arrayList.addAll(arrayList2);
            i = i2;
        }
        return arrayList;
    }

    public List<WordModel> findAndSelectPhrase(String str, String str2) {
        String[] split = str.trim().split("\\s+");
        String[] split2 = str2.trim().split("\\s+");
        ArrayList arrayList = new ArrayList();
        int i = 0;
        loop0: while (true) {
            if (i > split.length - split2.length) {
                i = -1;
                break;
            }
            for (int i2 = 0; i2 < split2.length; i2++) {
                if (!split[i + i2].equals(split2[i2])) {
                    break;
                }
            }
            break loop0;
        }
        int i3 = 0;
        while (i3 < split.length) {
            arrayList.add(new WordModel(split[i3], i != -1 && i3 >= i && i3 < split2.length + i));
            i3++;
        }
        return arrayList;
    }
}

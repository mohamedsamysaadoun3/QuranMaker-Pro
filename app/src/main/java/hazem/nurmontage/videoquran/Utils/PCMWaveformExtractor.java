package hazem.nurmontage.videoquran.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/* loaded from: classes2.dex */
public class PCMWaveformExtractor {
    public static float[] extractWaveform(String str, int i) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(str);
        float[] fArr = new float[i];
        byte[] bArr = new byte[8192];
        float f = i;
        float length = ((int) (new File(str).length() / 2)) / f;
        int i2 = 0;
        while (true) {
            int read = fileInputStream.read(bArr);
            if (read <= 0) {
                fileInputStream.close();
                return fArr;
            }
            for (int i3 = 0; i3 < read; i3 += 2) {
                float f2 = i2 / length;
                if (f2 >= f) {
                    break;
                }
                int i4 = (int) f2;
                fArr[i4] = Math.max(fArr[i4], Math.abs((int) ((short) ((bArr[i3 + 1] << 8) | (bArr[i3] & 255)))) / 32767.0f);
                i2++;
            }
        }
    }
}

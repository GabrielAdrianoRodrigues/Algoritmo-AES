import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

public abstract class ChiperUtils {
    public static Path chiper(Path toChiper, Byte[] key) {
        try (Scanner sc = new Scanner(toChiper);){
            
            while (sc.hasNext()) {

            }

        } catch (IOException ex) {

        }
        return null;
    }

    public static byte[][] createStateMatrix(Byte[] key) {
        byte[][] stateMatrix = new byte[4][4];
        int count = 0;
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                stateMatrix[i][j] = key[count++];
            }
        }
        return stateMatrix;
    }
}

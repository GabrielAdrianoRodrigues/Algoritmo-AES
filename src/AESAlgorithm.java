import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class AESAlgorithm {
    private Queue<int[][]> roundsKeys = new LinkedList<>();

    public Path encryptFile(Path toChiper, String key) {
        return null;
    }

    //Metodo de teste
    public void lastRoundKey(String[] key) {
        keyExpansion(generateKey(key));

        int[] x = rotWord();
        System.out.println();
        IntStream.range(0, x.length).forEach(y -> System.out.println(x[y]));  
    }

    private int[] generateKey(String[] keyToConvert) {
        return Stream.of(keyToConvert).mapToInt(Integer::parseInt).toArray();
    }

    private void keyExpansion(int[] key) {
        int matrixSize = (int) Math.sqrt(key.length);
        int[][] stateMatrix = new int[matrixSize][matrixSize];
        for (int i = 0, idx = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                stateMatrix[j][i] = key[idx++];
            }
        }
        //Teste apenas
        // for (int i = 0; i < stateMatrix.length; i++) {
        //     for (int j = 0; j < stateMatrix[i].length; j++) {
        //         System.out.print(stateMatrix[i][j] + " ");
        //     }
        //     System.out.println();
        // }
        this.roundsKeys.add(stateMatrix);
    }

    private int[] getLastW() {
        int[][] roundKey = roundsKeys.peek();
        int[] w = new int[roundKey.length]; 
        for (int i = 0; i < roundKey.length; i++) {
            w[i] = roundKey[i][roundKey.length-1];
        }
        return w;
    }

    private int[] rotWord() {
        int[] w = getLastW();
        int[] rotWord = Arrays.copyOfRange(w, 1, w.length+1);
        rotWord[rotWord.length-1] = w[0];
        return rotWord;
    }

    @Deprecated
    private byte[] generateByteKey(String[] keyToConvert) {
        byte[] key = new byte[keyToConvert.length];
        int[] temp = Stream.of(keyToConvert).mapToInt(Integer::parseInt).toArray();
        IntStream.range(0, keyToConvert.length).forEach(x -> key[x] = (byte)temp[x]);
        return key;
    }

    @Deprecated
    private byte[][] createByteStateMatrix(byte[] key) {
        byte[][] matrix = new byte[4][];
        for (int i = 0, start = 0; i < 4; i++, start += 4) {
            matrix[i] = Arrays.copyOfRange(key, start, start + 3);
        }
        return matrix;
    }
}
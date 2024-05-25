import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public abstract class AESCipher {
    private static List<int[][]> roundsKeys     = new ArrayList<>();
    private static List<int[][]> encryptBlocks = new ArrayList<>();
                                                                
    public static void encrypt(byte[] toEncrypt, String key) {
        generateRoundsKeys(key); 
        buildEncryptBlocks(toEncrypt);
        int[][] a, b, c;
        for(int i = 0; i < encryptBlocks.size(); i++) {
            a = addRoundKey(encryptBlocks.get(i), roundsKeys.get(0));
            for (int j = 1; j < 10; j++) {
                //IntStream.range(0, a.length).forEach(x -> a[x] = S_BOX[a[x] >> 4][a[x] & ((1 << 4) - 1)]);
            }
        }
    }

    private static void buildEncryptBlocks(byte[] toBuild) {
        if (toBuild.length < 16) {
            encryptBlocks.add(createBlocks(toBuild));
            return;
        } 
        IntStream.range(0, (toBuild.length / 16)).forEach(x -> encryptBlocks.add(createBlocks(Arrays.copyOfRange(toBuild, x*16, 16*(x+1)))));
        if(toBuild.length % 16 == 0) {  
            encryptBlocks.add(new int[][]{{16, 16, 16, 16},{16, 16, 16, 16},{16, 16, 16, 16},{16, 16, 16, 16}});
            return;
        } 
        encryptBlocks.add(createBlocks(Arrays.copyOfRange(toBuild, toBuild.length / 16 * 16, toBuild.length)));
    }

    private static int[][] createBlocks(byte[] bytes) {
        int[][] stateMatrix = new int[4][4];
        for (int i = 0, idx = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                stateMatrix[j][i] = idx < bytes.length ? bytes[idx++] : 16 - idx;
            }
        }
        return stateMatrix;
    }

    private static int[][] addRoundKey(int[][] block, int[][] roundKey) {
        int[][] result = new int[block.length][block.length];
        for (int i = 0; i < block.length; i++) {
            for (int j = 0; j < block.length; j++) {
                result[j][i] = block[j][i] ^ roundKey[j][i];
            }
        }
        return result;
    }

    private static void generateRoundsKeys(String rawKey) {
        roundsKeys.add(keyExpansion(generateKey(rawKey.split(","))));
        IntStream.range(0, 10).forEach(x -> roundsKeys.add(generateRoundKey(x)));
    }

    private static int[] generateKey(String[] keyToConvert) {
        return Stream.of(keyToConvert).mapToInt(Integer::parseInt).toArray();
    }

    private static int[][] keyExpansion(int[] key) {
        int matrixSize = (int) Math.sqrt(key.length);
        int[][] stateMatrix = new int[matrixSize][matrixSize];
        for (int i = 0, idx = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                stateMatrix[j][i] = key[idx++];
            }
        }
        return stateMatrix;
    }
    
    private static int[][] generateRoundKey(int roundKeyIdx) {
        int[][] lastRoundKey = roundsKeys.get(roundKeyIdx);
        int[][] newRoundKey = new int[lastRoundKey.length][lastRoundKey.length];
        int[] rotWord = generateRotWord(getW(lastRoundKey, lastRoundKey.length - 1));
        int[] subWord = new int[rotWord.length];
        IntStream.range(0, rotWord.length).forEach(x -> subWord[x] = S_BOX[rotWord[x] >> 4][rotWord[x] & ((1 << 4) - 1)]);
        int idx = 0;
        for(int x : xorWords(getW(lastRoundKey, 0), xorWords(subWord, roundConstant(roundKeyIdx)))) {
            newRoundKey[idx++][0] = x;
        }
        for (int i = 1; i < lastRoundKey.length; i++) {
            idx = 0;
            for(int x : xorWords(getW(lastRoundKey, i), getW(newRoundKey, i-1))) {
                newRoundKey[idx++][i] = x;
            }
        }
        return newRoundKey;
    }

    private static int[] getW(int[][] roundKey, int idx) {
        int[] w = new int[roundKey.length]; 
        for (int i = 0; i < roundKey.length; i++) {
            w[i] = roundKey[i][idx];
        }
        return w;
    }

    private static int[] generateRotWord(int[] lastW) {
        int[] rotWord = Arrays.copyOfRange(lastW, 1, lastW.length+1);
        rotWord[rotWord.length-1] = lastW[0];
        return rotWord;
    }

    private static int[] roundConstant(int idx) { 
        return new int[]{new int[]{1, 2, 4, 8, 16, 32, 64, 128, 27, 54}[idx], 0, 0, 0};
    }

    private static int[] xorWords(int[] w1, int[] w2) {
        int[] result = new int[w1.length];
        IntStream.range(0, w1.length).forEach(x -> result[x] = w1[x] ^ w2[x]);
        return result;
    }

    private static final int[][] S_BOX = {
        {0x63, 0x7C, 0x77, 0x7B, 0xF2, 0x6B, 0x6F, 0xC5, 0x30, 0x01, 0x67, 0x2B, 0xFE, 0xD7, 0xAB, 0x76},
        {0xCA, 0x82, 0xC9, 0x7D, 0xFA, 0x59, 0x47, 0xF0, 0xAD, 0xD4, 0xA2, 0xAF, 0x9C, 0xA4, 0x72, 0xC0},
        {0xB7, 0xFD, 0x93, 0x26, 0x36, 0x3F, 0xF7, 0xCC, 0x34, 0xA5, 0xE5, 0xF1, 0x71, 0xD8, 0x31, 0x15},
        {0x04, 0xC7, 0x23, 0xC3, 0x18, 0x96, 0x05, 0x9A, 0x07, 0x12, 0x80, 0xE2, 0xEB, 0x27, 0xB2, 0x75},
        {0x09, 0x83, 0x2C, 0x1A, 0x1B, 0x6E, 0x5A, 0xA0, 0x52, 0x3B, 0xD6, 0xB3, 0x29, 0xE3, 0x2F, 0x84},
        {0x53, 0xD1, 0x00, 0xED, 0x20, 0xFC, 0xB1, 0x5B, 0x6A, 0xCB, 0xBE, 0x39, 0x4A, 0x4C, 0x58, 0xCF},
        {0xD0, 0xEF, 0xAA, 0xFB, 0x43, 0x4D, 0x33, 0x85, 0x45, 0xF9, 0x02, 0x7F, 0x50, 0x3C, 0x9F, 0xA8},
        {0x51, 0xA3, 0x40, 0x8F, 0x92, 0x9D, 0x38, 0xF5, 0xBC, 0xB6, 0xDA, 0x21, 0x10, 0xFF, 0xF3, 0xD2},
        {0xCD, 0x0C, 0x13, 0xEC, 0x5F, 0x97, 0x44, 0x17, 0xC4, 0xA7, 0x7E, 0x3D, 0x64, 0x5D, 0x19, 0x73},
        {0x60, 0x81, 0x4F, 0xDC, 0x22, 0x2A, 0x90, 0x88, 0x46, 0xEE, 0xB8, 0x14, 0xDE, 0x5E, 0x0B, 0xDB},
        {0xE0, 0x32, 0x3A, 0x0A, 0x49, 0x06, 0x24, 0x5C, 0xC2, 0xD3, 0xAC, 0x62, 0x91, 0x95, 0xE4, 0x79},
        {0xE7, 0xC8, 0x37, 0x6D, 0x8D, 0xD5, 0x4E, 0xA9, 0x6C, 0x56, 0xF4, 0xEA, 0x65, 0x7A, 0xAE, 0x08},
        {0xBA, 0x78, 0x25, 0x2E, 0x1C, 0xA6, 0xB4, 0xC6, 0xE8, 0xDD, 0x74, 0x1F, 0x4B, 0xBD, 0x8B, 0x8A},
        {0x70, 0x3E, 0xB5, 0x66, 0x48, 0x03, 0xF6, 0x0E, 0x61, 0x35, 0x57, 0xB9, 0x86, 0xC1, 0x1D, 0x9E},
        {0xE1, 0xF8, 0x98, 0x11, 0x69, 0xD9, 0x8E, 0x94, 0x9B, 0x1E, 0x87, 0xE9, 0xCE, 0x55, 0x28, 0xDF},
        {0x8C, 0xA1, 0x89, 0x0D, 0xBF, 0xE6, 0x42, 0x68, 0x41, 0x99, 0x2D, 0x0F, 0xB0, 0x54, 0xBB, 0x16}
    };
}
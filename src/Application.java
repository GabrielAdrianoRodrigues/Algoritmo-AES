import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

public class Application {
    public static final JFileChooser c = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

    public static void main(String[] args) throws Exception {
        //Scanner sc = new Scanner(System.in);
        // c.showOpenDialog(null);
        // System.out.println(Digite o nome do arquivo destino:);
        // Paths.get(sc.next());   
        // System.out.println(Digite a chave de criptografia);
        // c.getSelectedFile().toPath()    
        // ## Teste para criar os blocos do texto simples.
        // byte[] textoSimples = new byte[]{0x44, 0x4e, 0x56, 0x4e, 
        //     0x45, 0x56, 0x49, 0x54, 
        //     0x53, 0x4f, 0x4d, 0x4f, 
        //     0x45, 0x4c, 0x45, 0x21};
        // List<int[][]> temp = AESCipher.buildEncryptBlocks(textoSimples);
        
        // for(int[][] result : temp) {
        //     System.out.println();
        //     for (int i = 0; i < result.length; i++) {
        //         for (int j = 0; j < result[i].length; j++) {
        //             System.out.print("0x"+Integer.toHexString(result[i][j])+" ");
        //         }
        //         System.out.println();
        //     }
        //     System.out.println();
        // }

        // ## Teste encrypt
        // byte[] textoSimples = new byte[]{0x44, 0x4e, 0x56, 0x4e, 
        //                                 0x45, 0x56, 0x49, 0x54, 
        //                                 0x53, 0x4f, 0x4d, 0x4f, 
        //                                 0x45, 0x4c, 0x45, 0x21};
        String textoSimples = "DESENVOLVIMENTO!";
        List<int[][]> encriptado = AESCipher.encrypt(textoSimples.getBytes(), "65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80");

        String algorithm = "AES/CBC/PKCS5Padding";
        Cipher cipher = Cipher.getInstance(algorithm);
        
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128);

        SecretKey secKey = keyGen.generateKey();

        cipher.init(Cipher.DECRYPT_MODE, secKey);

        byte[] teste = new byte[32];
        int idx = 0;
        for (int[][] result : encriptado) {
            for (int i = 0; i < result.length; i++) {
                for (int j = 0; j < result.length; j++) {
                    teste[idx++] = (byte)result[i][j]; 
                }
            }
        }
        String result = new String(cipher.doFinal(teste));
        System.out.println(result);

        // for (int[][] result : encriptado) {
        //     System.out.println();
        //         for (int i = 0; i < result.length; i++) {
        //             for (int j = 0; j < result[i].length; j++) {
        //                 System.out.print("0x"+Integer.toHexString(result[i][j])+" ");
        //             }
        //             System.out.println();
        //         }
        //      System.out.println();
        // }

        // ## Teste para passo a passo até mixColumns
        //  int[][] teste2 = new int[][]{{0x44, 0x4e, 0x56, 0x4e}, 
        //              {0x45, 0x56, 0x49, 0x54}, 
        //              {0x53, 0x4f, 0x4d, 0x4f}, 
        //              {0x45, 0x4c, 0x45, 0x21}};
        //  int[][] roundteste = new int[][]{{0x41, 0x45, 0x49, 0x4d},
        //                               {0x42, 0x46, 0x4a, 0x4e},
        //                               {0x43, 0x47, 0x4b, 0x4f},
        //                               {0x44, 0x48, 0x4c, 0x50}};
        // int[][] result = AESCipher.addRoundKey(teste2, roundteste);
        // // int[][] b = AESCipher.subBytes(a);
        // // int[][] c = AESCipher.shiftRows(b);
        // // int[][] result = AESCipher.mixColumns(c);

        //  System.out.println();
        //      for (int i = 0; i < result.length; i++) {
        //          for (int j = 0; j < result[i].length; j++) {
        //              System.out.print("0x"+Integer.toHexString(result[i][j])+" ");
        //          }
        //          System.out.println();
        //      }
        //  System.out.println();

        // ## Teste para gerar roundKeys
        // AESCipher.encrypt(teste, "65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80");
        // AESAlgorithm.generateRoundsKeys("65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80").forEach(x -> {
        //     System.out.println();
        //     for (int i = 0; i < x.length; i++) {
        //         for (int j = 0; j < x[i].length; j++) {
        //             System.out.print("0x"+Integer.toHexString(x[i][j])+" ");
        //         }
        //         System.out.println();
        //     }
        //     System.out.println();
        // });
        //sc.close();
    }


}

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

//Bernardo e Nicole
public class JavaCipher {

    public static final String ALGORITMO = "AES";
    public static final JFileChooser c = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException,
            IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IOException {
        Scanner sc = new Scanner(System.in);
        c.showOpenDialog(null);
        File selectFile = c.getSelectedFile();
        // System.out.println("Digite a chave:");
        // String key = sc.next();   

        System.out.println("Deseja cifrar(1) ou decifrar(2)?");
        int option = sc.nextInt();

        byte[] chave = new byte[]{65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80}; // chave para teste;
        SecretKeySpec ks = new SecretKeySpec(chave, ALGORITMO);
        switch (option) {
            case 1:
                cifrar(ks, Files.readAllBytes(selectFile.toPath()));
                break;
            case 2:
                decifrar(chave, Files.readAllBytes(selectFile.toPath()));
                break;
            default:
                cifrar(ks, Files.readAllBytes(selectFile.toPath()));
                break;
        }       
        sc.close();
    }

    private static void cifrar(SecretKeySpec ks, byte[] file) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        Cipher cifraECB = Cipher.getInstance(ALGORITMO + "/ECB/PKCS5Padding");
        cifraECB.init(Cipher.ENCRYPT_MODE, ks);
        criptografa(cifraECB, file);
    }

    private static void decifrar(byte[] chave, byte[] file) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher decifrarECB = Cipher.getInstance(ALGORITMO + "/ECB/PKCS5Padding");
        decifrarECB.init(Cipher.DECRYPT_MODE, new SecretKeySpec(chave, ALGORITMO));
        descriptografa(decifrarECB, file);
    }

    private static void descriptografa(Cipher c, byte[] textoCifrado) throws IllegalBlockSizeException, BadPaddingException {
        byte[] textoDecifrado = c.doFinal(textoCifrado);
        System.out.println("Texto decifrado: " + new String(textoDecifrado));
    }

    public static  byte[] criptografa(Cipher c, byte[] textoSimples) throws IllegalBlockSizeException, BadPaddingException {
        byte[] textoCifrado = c.doFinal(textoSimples);
        System.out.println("Texto: " + textoSimples);

        convertByteToHexadecimal(textoCifrado);
        System.out.println("Tamanho do texto cifrado: " + textoCifrado.length + "\n");
        return textoCifrado;
    }

    public static void convertByteToHexadecimal(byte[] byteArray) {
        String hex = "";
        for (byte i : byteArray) {
            hex += String.format("%02X ", i);
        }

        System.out.println("Texto cifrado em hexadecimal: " + hex);
    }

}

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

public class Application {
    public static final JFileChooser c = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        c.showOpenDialog(null);
        File selectFile = c.getSelectedFile();
        
        System.out.println("Informe o nome do arquivo a ser gerado:");
        String fileName = sc.next();
        
        c.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        c.showSaveDialog(null);

        try (FileOutputStream fos = new FileOutputStream(new File(c.getSelectedFile().getPath()+"/"+fileName+selectFile.getName().substring(selectFile.getName().lastIndexOf("."))))) {
            System.out.println("Digite a chave:");
            String key = sc.next();
            fos.write(AESCipher.encrypt(Files.readAllBytes(selectFile.toPath()), key));
        } catch (IOException e) {}

        sc.close();
    }


}

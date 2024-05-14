import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;



public class Application {
    public static final JFileChooser c = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        // c.showOpenDialog(null);
        // System.out.println("Digite o nome do arquivo destino:");
        // Paths.get(sc.next());   
        // System.out.println("Digite a chave de criptografia");
        // c.getSelectedFile().toPath()

        new AESAlgorithm().lastRoundKey("20,1,94,33,199,0,48,9,31,94,112,40,59,30,100,248".split(","));
        sc.close();
    }
}

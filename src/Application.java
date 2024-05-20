import java.util.Scanner;

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

        AESAlgorithm.generateRoundsKeys("65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80").forEach(x -> {
            for (int i = 0; i < x.length; i++) {
                for (int j = 0; j < x[i].length; j++) {
                    System.out.print(Integer.toHexString(x[i][j])+" ");
                }
                System.out.println();
            }
            System.out.println();
        });
        //sc.close();
    }


}

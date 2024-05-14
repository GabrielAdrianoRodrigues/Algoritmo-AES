import java.util.Scanner;
import java.util.stream.Stream;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

public class Application {
    public static final JFileChooser c = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        // c.showOpenDialog(null);

        // System.out.println("Digite o nome do arquivo destino:");
        // String nomeArquivo = sc.next();   
        System.out.println("Digite a chave de criptografia");

        // ChiperUtils.chiper(
        //     c.getSelectedFile().toPath(),
        //     Stream.of(sc.next().split(",")).map(Byte::parseByte).toArray(Byte[]::new)
        // );
        byte[][] teste = ChiperUtils.createStateMatrix(Stream.of(sc.next().split(",")).map(Byte::parseByte).toArray(Byte[]::new));
        
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(teste[i][j]+" ");
            }
            System.out.println();
        }
        sc.close();
    }
}

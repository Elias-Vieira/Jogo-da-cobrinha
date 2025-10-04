import javax.swing.*;
import java.awt.*;

public class Tela extends JFrame {

    public Tela(){

        setTitle("Jogo da cobrinha");

        ImageIcon icone = new ImageIcon("imagens/icone.png");
        setIconImage(icone.getImage());
        //setSize(900, 700);

        PainelDoJogo painel = new PainelDoJogo();
        add(painel);
        pack();



        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);


        painel.requestFocusInWindow();
    }
}

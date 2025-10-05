import javax.swing.*;
import java.awt.*;

public class Tela extends JFrame{

    private CardLayout layout;
    private JPanel container;
    private PainelDoJogo painelDoJogo;

    public Tela() {

        setTitle("Jogo da cobrinha");
        ImageIcon icone = new ImageIcon("imagens/icone.png");
        setIconImage(icone.getImage());

        layout = new CardLayout();
        container = new JPanel(layout);

        painelDoJogo = new PainelDoJogo();
        TelaInicial telaInicial = new TelaInicial(this);

        container.add(telaInicial, "INICIO");
        container.add(painelDoJogo, "JOGO");

        add(container);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void checarIaOuNao(String retorno){

        if (retorno.equals("JOGAR")){

            painelDoJogo.modificarModoIA(false);
            painelDoJogo.iniciarJogo();
            layout.show(container, "JOGO");
            painelDoJogo.requestFocusInWindow();

        } else {

            painelDoJogo.modificarModoIA(true);
            painelDoJogo.iniciarJogo();
            layout.show(container, "JOGO");
            painelDoJogo.requestFocusInWindow();

        }

    }
}


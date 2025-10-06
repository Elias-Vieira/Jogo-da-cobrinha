import javax.swing.*;
import java.awt.*;

public class TelaInicial extends JPanel {

    public Tela tela;
    public PainelDoJogo painelDoJogo;

    public TelaInicial(Tela tela) {

        this.tela = tela;
        this.painelDoJogo = new PainelDoJogo();
        int tamanho = painelDoJogo.obterNumLinhas() * painelDoJogo.obterTamanhoDaCelula();

        setPreferredSize(new Dimension(tamanho, tamanho));
        setBackground(Color.BLACK);

        setLayout(new GridBagLayout());
        GridBagConstraints configuracaoLayout = new GridBagConstraints();
        configuracaoLayout.gridx = 0;
        configuracaoLayout.gridy = GridBagConstraints.RELATIVE;
        configuracaoLayout.insets = new Insets(1, 0, 10, 0);
        configuracaoLayout.anchor = GridBagConstraints.CENTER;

        JLabel titulo = new JLabel("COMO DESEJA BRINCAR");
        titulo.setFont(new Font("Comic Sans MS", Font.BOLD, 40));
        titulo.setForeground(Color.RED);

        JLabel subtitulo = new JLabel("COM A MINHA COBRA?");
        subtitulo.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        subtitulo.setForeground(Color.WHITE);

        Color cinzaEscuro = new Color(50, 50, 50);
        Dimension tamanhoBotao = new Dimension(250, 50);

        JButton botaoJogar = new JButton("Jogar Sozinho");
        JButton botaoJogarComIa = new JButton("Jogar Com IA");

        configurarBotao(botaoJogar, tamanhoBotao, cinzaEscuro);
        configurarBotao(botaoJogarComIa, tamanhoBotao, cinzaEscuro);

        botaoJogar.addActionListener(e -> tela.checarIaOuNao("JOGAR"));
        botaoJogarComIa.addActionListener(e -> tela.checarIaOuNao("IA"));

        add(titulo, configuracaoLayout);
        add(subtitulo, configuracaoLayout);
        add(Box.createRigidArea(new Dimension(0, 20)), configuracaoLayout);
        add(botaoJogar, configuracaoLayout);
        add(botaoJogarComIa, configuracaoLayout);
    }

    private void configurarBotao(JButton botao, Dimension tamanho, Color corFundo) {
        botao.setPreferredSize(tamanho);
        botao.setMaximumSize(tamanho);
        botao.setBackground(corFundo);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setFont(new Font("Comic Sans MS", Font.BOLD, 16));

    }

}



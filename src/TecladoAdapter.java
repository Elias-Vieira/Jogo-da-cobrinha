import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TecladoAdapter extends KeyAdapter {

    private PainelDoJogo painelDoJogo;

    public TecladoAdapter(PainelDoJogo painelDoJogo){
        this.painelDoJogo = painelDoJogo;

    }

    @Override
    public void keyPressed(KeyEvent teclaApertada){

        int tecla = teclaApertada.getKeyCode();

        switch (tecla){
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                if (painelDoJogo.obterDirecao() != 'D') painelDoJogo.ajustarDirecao('E');
                painelDoJogo.desativarIA();
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                if (painelDoJogo.obterDirecao() != 'E') painelDoJogo.ajustarDirecao('D');
                painelDoJogo.desativarIA();
                break;
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                if (painelDoJogo.obterDirecao() != 'B') painelDoJogo.ajustarDirecao('C');
                painelDoJogo.desativarIA();
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                if (painelDoJogo.obterDirecao() != 'C') painelDoJogo.ajustarDirecao('B');
                painelDoJogo.desativarIA();
                break;
            case KeyEvent.VK_ENTER:
                if (!painelDoJogo.obterJogoAtivo()) painelDoJogo.iniciarJogo();
                break;
            case KeyEvent.VK_SPACE:
                    painelDoJogo.ativarIA();

                break;

        }

    }
}

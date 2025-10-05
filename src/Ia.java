import java.awt.*;
import java.util.LinkedList;

public class Ia {

    private Point posicaoDaCobra;
    private Point posicaoDaComida;
    private char direcao;
    private final char[] possiveisDirecoes = {'C', 'B', 'E', 'D'};
    private LinkedList<Point> cobra;
    private PainelDoJogo painelDoJogo;

    public Ia(Point posicaoDaCobra, Point posicaoDaComida, PainelDoJogo painelDoJogo, LinkedList<Point> cobra) {

        this.painelDoJogo = new PainelDoJogo();
        this.posicaoDaCobra = posicaoDaCobra;
        this.posicaoDaComida = posicaoDaComida;
        this.painelDoJogo = painelDoJogo;
        this.cobra = cobra;
    }

    public void atualizarEstado(Point novaCobra, Point novaComida, LinkedList<Point> partesDaCobra) {
        this.posicaoDaCobra = novaCobra;
        this.posicaoDaComida = novaComida;
        this.cobra = partesDaCobra;
    }

    public void moverIa() {
        Point cabeca = new Point(posicaoDaCobra);
        Point comida = new Point(posicaoDaComida);

        if (cabeca.x > comida.x) {
            direcao = 'E';
        } else if (cabeca.x < comida.x) {
            direcao = 'D';
        } else if (cabeca.y > comida.y) {
            direcao = 'C';
        } else {
            direcao = 'B';
        }

        // verifica se vai colidir
        Point posicaoPrevista = preverMovimento(cabeca, direcao);
        if (checarColisao(posicaoPrevista)) {
            direcao = evitarColisao();
        }

        painelDoJogo.ajustarDirecao(direcao);
    }

    private char evitarColisao() {
        for (int i = 0; i < possiveisDirecoes.length; i++) {
            char ondeIr = possiveisDirecoes[i];
            Point cabecaPrevista = preverMovimento(posicaoDaCobra, ondeIr);
            if (!checarColisao(cabecaPrevista)) {
                return ondeIr;
            }
        }
        return direcao;
    }

    private Point preverMovimento(Point cabecaAtual, char direcao) {
        Point cabecaCopia = new Point(cabecaAtual);
        switch (direcao) {
            case 'C': cabecaCopia.y -= 1; break;
            case 'B': cabecaCopia.y += 1; break;
            case 'E': cabecaCopia.x -= 1; break;
            case 'D': cabecaCopia.x += 1; break;
        }
        return cabecaCopia;
    }

    private boolean checarColisao(Point cabecaPrevista) {
        // Colizao parede
        if (cabecaPrevista.x < 0 || cabecaPrevista.x >= painelDoJogo.obterNumLinhas() || cabecaPrevista.y < 0 || cabecaPrevista.y >= painelDoJogo.obterNumColunas()) {
            return true;
        }

        // Colizao cobra
        for (Point parte : cobra) {
            if (cabecaPrevista.equals(parte)) {
                return true;
            }
        }

        return false;
    }
}


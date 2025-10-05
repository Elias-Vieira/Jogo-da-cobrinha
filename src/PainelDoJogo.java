import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class PainelDoJogo extends JPanel implements ActionListener {

    private static final int tamanhoCelula = 20;
    private static final int numColunas = 30;
    private static final int numLinhas = 30;

    private static int intervalo = 100;
    private int contadorCobraComeu = 1;
    private int nivel = 1;

    private LinkedList<Point> cobra;
    private Point comida;

    private char direcaoDaCobra = 'D';
    private boolean jogoAtivo = false;
    private Timer temporizadr;

    private Random aleatorio;

    private boolean cobraComeu = false;

    private int pontuacao = 0;

    private ArrayList<Color> corDaCobraComida = new ArrayList<>();
    private Color corAleatoria;

    private Ia ia;
    private boolean modoIA = false;

    public PainelDoJogo(){

        setPreferredSize(new Dimension(numLinhas * tamanhoCelula, numColunas * tamanhoCelula));
        setBackground(Color.BLACK);

        setFocusable(true);
        addKeyListener(new TecladoAdapter(this));

       corAleatoria = gerarCorAleatoria();
       corDaCobraComida.add(corAleatoria);


    }

    public void iniciarJogo(){
        cobra = new LinkedList<>();
        cobra.add(new Point(numLinhas / 2, numColunas / 2));

        aleatorio = new Random();
        criarComida();

        direcaoDaCobra = 'D';
        jogoAtivo = true;
        pontuacao = 0;
        cobraComeu = false;

        if (temporizadr != null) temporizadr.stop();
        temporizadr = new Timer(intervalo, this);
        temporizadr.start();

        if (modoIA) {
            ia = new Ia(cobra.getFirst(), new Point(comida.x, comida.y), this, cobra);
        } else {
            ia = null;
        }

    }


    public void criarComida(){
        Point posicao;

        do {
            int x = aleatorio.nextInt(numLinhas);
            int y = aleatorio.nextInt(numColunas);

            posicao = new Point(x, y);
        } while (cobra.contains(posicao));

        comida = posicao;
        corAleatoria = gerarCorAleatoria();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //IA
        if (ia != null) {
            ia.atualizarEstado(cobra.getFirst(), new Point(comida.x, comida.y), cobra);
            ia.moverIa();
        }


        if (jogoAtivo){
            mover();
            checarColisao();
            if (cobraComeu){
                pontuacao++;
                corDaCobraComida.add(corAleatoria);
                criarComida();
            }
        }

        repaint();

    }

    private void mover(){
        Point cabeca = new Point(cobra.getFirst());

        switch (direcaoDaCobra){
            case 'C': cabeca.y -= 1; break;
            case 'B': cabeca.y += 1; break;
            case 'E': cabeca.x -= 1; break;
            case 'D': cabeca.x += 1; break;
        }

        cobra.addFirst(cabeca);

        if (cabeca.equals(comida)){
            cobraComeu = true;
            corDaCobraComida.add(1, corAleatoria);

            alterarDificuldade();

        } else {
            cobraComeu = false;
            cobra.removeLast();
        }
    }

    public void checarColisao(){
        Point cabeca = cobra.getFirst();

        //checar colizao com a parede
        if (cabeca.x < 0 || cabeca.x >= numLinhas || cabeca.y < 0 || cabeca.y >= numColunas){
            jogoAtivo = false;
            temporizadr.stop();
            return;
        }

        //checar colizao com a cobra
        for (int i = 1; i < cobra.size(); i++){
            if (cabeca.equals(cobra.get(i))){
                jogoAtivo = false;
                temporizadr.stop();
                return;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics grafico){
        super.paintComponent(grafico);

        //grafico da comida
        if (jogoAtivo){
            grafico.setColor(corAleatoria);
            grafico.fillRect(comida.x * tamanhoCelula, comida.y * tamanhoCelula, tamanhoCelula, tamanhoCelula);


            //grafico cobra
            for (int i = 0; i < cobra.size(); i++){
                Point posicao = cobra.get(i);
                Color cor = corDaCobraComida.get(i);

                grafico.setColor(i == 0? Color.GREEN : cor);
                grafico.fillRect(posicao.x * tamanhoCelula, posicao.y * tamanhoCelula, tamanhoCelula, tamanhoCelula);
            }


            //exibir pontos
            grafico.setColor(Color.WHITE);
            grafico.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
            grafico.drawString("Nível: " + nivel + " / " + " Pontuação: " + pontuacao, 5, 18);

        } else {
            gameOver(grafico);
        }


    }

    public void gameOver(Graphics grafico){

        intervalo = 100;
        nivel = 1;

        grafico.setFont(new Font("Comic Sans MS", Font.BOLD, 40));

        FontMetrics medidas = grafico.getFontMetrics();
        int x = (getWidth() - medidas.stringWidth("SE FODEU!")) / 2;
        int y = getHeight() / 2;

        grafico.setColor(Color.WHITE);
        grafico.drawString("SE FODEU!", x + 2, y + 2);


        grafico.setColor(Color.RED);
        grafico.drawString("SE FODEU!", x, y);

        grafico.setColor(Color.WHITE);

        grafico.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        int posicao = (getWidth() - grafico.getFontMetrics().stringWidth("Pontuação: " + pontuacao)) / 2;
        grafico.drawString("Pontuação: " + pontuacao, posicao, y + 30);

    }

    public static Color gerarCorAleatoria(){
        Random aleatorio = new Random();
        int R = aleatorio.nextInt(256);
        int G = aleatorio.nextInt(256);
        int B = aleatorio.nextInt(256);


        return new Color(G, R, B);
    }

    public void alterarDificuldade(){

        //a cada 5 comida a variavel zera
        if (contadorCobraComeu == 5){

            contadorCobraComeu = 0;
            intervalo = intervalo - 10;
            if (temporizadr != null) temporizadr.stop();
            temporizadr = new Timer(intervalo, this);
            temporizadr.start();
            nivel++;

        } else {
            contadorCobraComeu++;
        }

    }

    public char obterDirecao(){
        return direcaoDaCobra;
    }

    public boolean obterJogoAtivo(){
        return jogoAtivo;
    }

    public int obterNumLinhas(){
        return numLinhas;
    }

    public int obterNumColunas(){
        return numColunas;
    }

    public int obterTamanhoDaCelula(){
        return tamanhoCelula;
    }

    public void ajustarDirecao(char direcaoDaCobra){
        this.direcaoDaCobra = direcaoDaCobra;
    }

    public void modificarModoIA(boolean modoIA) {
        this.modoIA = modoIA;
    }

    public void ativarIA() {
        if (cobra != null && comida != null) {
            ia = new Ia(cobra.getFirst(), new Point(comida.x, comida.y), this, cobra);
            modoIA = true;
        }
    }

}

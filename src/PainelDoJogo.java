import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Random;

public class PainelDoJogo extends JPanel implements ActionListener {

    private static final int tamanhoCelula = 20;
    private static final int numColunas = 30;
    private static final int numLinhas = 30;

    private static final int intervalo = 100;

    private LinkedList<Point> cobra;
    private Point comida;

    private char direcaoDaCobra = 'D';
    private boolean jogoAtivo = false;
    private Timer temporizadr;

    private Random aleatorio;

    private boolean cobraComeu = false;

    private int pontuacao = 0;

    public PainelDoJogo(){

        setPreferredSize(new Dimension(numLinhas * tamanhoCelula, numColunas * tamanhoCelula));
        setBackground(Color.BLACK);

        setFocusable(true);
        addKeyListener(new TecladoAdapter(this));

       // iniciarJogo();

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
    }


    public void criarComida(){
        Point posicao;

        do {
            int x = aleatorio.nextInt(numLinhas);
            int y = aleatorio.nextInt(numColunas);

            posicao = new Point(x, y);
        } while (cobra.contains(posicao));

        comida = posicao;
    }

    @Override
    public void actionPerformed(ActionEvent e) {


        if (jogoAtivo){
            mover();
            checarColisao();
            if (cobraComeu){
                pontuacao++;
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
            grafico.setColor(Color.RED);
            grafico.fillRect(comida.x * tamanhoCelula, comida.y * tamanhoCelula, tamanhoCelula, tamanhoCelula);


            //grafico cobra
            for (int i = 0; i < cobra.size(); i++){
                Point posicao = cobra.get(i);

                grafico.setColor(i == 0? Color.GREEN : Color.WHITE);
                grafico.fillRect(posicao.x * tamanhoCelula, posicao.y * tamanhoCelula, tamanhoCelula, tamanhoCelula);
            }


            //exibir pontos
            grafico.setColor(Color.WHITE);
            grafico.setFont(new Font("Arial", Font.BOLD, 15));
            grafico.drawString("Pontuação: " + pontuacao, 5, 18);

        } else if (cobra == null || cobra.isEmpty()){
            telaInicial(grafico);
        } else {
            gameOver(grafico);
        }


    }

    public void gameOver(Graphics grafico){

        grafico.setFont(new Font("Arial", Font.BOLD, 40));

        FontMetrics medidas = grafico.getFontMetrics();
        int x = (getWidth() - medidas.stringWidth("SE FODEU!")) / 2;
        int y = getHeight() / 2;

        grafico.setColor(Color.WHITE);
        grafico.drawString("SE FODEU!", x + 2, y + 2);


        grafico.setColor(Color.RED);
        grafico.drawString("SE FODEU!", x, y);

        grafico.setColor(Color.WHITE);

        grafico.setFont(new Font("Arial", Font.PLAIN, 20));
        int scoreX = (getWidth() - grafico.getFontMetrics().stringWidth("Pontuação: " + pontuacao)) / 2;
        grafico.drawString("Pontuação: " + pontuacao, scoreX, y + 30);

    }

    public void telaInicial(Graphics grafico){

        grafico.setFont(new Font("Arial", Font.BOLD, 40));

        FontMetrics medidas = grafico.getFontMetrics();
        int x = (getWidth() - medidas.stringWidth("APERTE ENTER!")) / 2;
        int y = getHeight() / 2;

        grafico.setColor(Color.WHITE);
        grafico.drawString("APERTE ENTER", x + 2, y + 2);

        grafico.setColor(Color.RED);
        grafico.drawString("APERTE ENTER", x, y);

        grafico.setColor(Color.WHITE);
        grafico.setFont(new Font("Arial", Font.PLAIN, 20));

        int scoreX = (getWidth() - grafico.getFontMetrics().stringWidth("PARA INICIAR O JOGO")) / 2;
        grafico.drawString("PARA INICIAR O JOGO", scoreX, y + 30);

    }


    public char obterDirecao(){
        return direcaoDaCobra;
    }

    public boolean obterJogoAtivo(){
        return jogoAtivo;
    }

    public void ajustarDirecao(char direcaoDaCobra){
        this.direcaoDaCobra = direcaoDaCobra;
    }

}

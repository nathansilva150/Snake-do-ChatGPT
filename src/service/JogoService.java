package service;

import model.Cobra;
import model.Fruta;

import java.util.Random;

public class JogoService {

    public final int ALTURA = 600;
    public final int LARGURA = 600;
    public final int TAMANHO = 25;

    private final int TOTAL = (ALTURA * LARGURA) / (TAMANHO * TAMANHO);

    private Cobra cobra;
    private Fruta fruta;

    private char direcao = 'R';
    private boolean rodando = false;
    private int pontos = 0;

    private Random random;

    public JogoService() {
        random = new Random();
        cobra = new Cobra(TOTAL);
        gerarFruta();
        rodando = true;
    }

    public void gerarFruta() {
        fruta = new Fruta(
            random.nextInt(LARGURA / TAMANHO) * TAMANHO,
            random.nextInt(ALTURA / TAMANHO) * TAMANHO
        );
    }

    public void mover() {
        for (int i = cobra.corpo; i > 0; i--) {
            cobra.x[i] = cobra.x[i - 1];
            cobra.y[i] = cobra.y[i - 1];
        }

        switch (direcao) {
            case 'U' -> cobra.y[0] -= TAMANHO;
            case 'D' -> cobra.y[0] += TAMANHO;
            case 'L' -> cobra.x[0] -= TAMANHO;
            case 'R' -> cobra.x[0] += TAMANHO;
        }
    }

    public void pegarFruta() {
        if (cobra.x[0] == fruta.x && cobra.y[0] == fruta.y) {
            cobra.corpo++;
            pontos++;
            gerarFruta();
        }
    }

    public void checarColisoes() {
        // corpo
        for (int i = cobra.corpo; i > 0; i--) {
            if (cobra.x[0] == cobra.x[i] && cobra.y[0] == cobra.y[i]) {
                rodando = false;
                return;
            }
        }

        // paredes
        if (cobra.x[0] < 0 || cobra.x[0] >= LARGURA || cobra.y[0] < 0 || cobra.y[0] >= ALTURA) {
            rodando = false;
        }
    }

    public void reiniciar() {
        cobra.resetar();
        direcao = 'R';
        rodando = true;
        pontos = 0;
        gerarFruta();
    }

    // GETTERS
    public Cobra getCobra() { return cobra; }
    public Fruta getFruta() { return fruta; }
    public int getPontos() { return pontos; }
    public boolean isRodando() { return rodando; }
    public int getTamanho() { return TAMANHO; }
    public int getAltura() { return ALTURA; }
    public int getLargura() { return LARGURA; }

    // direção
    public void setDirecao(char direcao) {
        if (this.direcao == 'U' && direcao == 'D') return;
        if (this.direcao == 'D' && direcao == 'U') return;
        if (this.direcao == 'L' && direcao == 'R') return;
        if (this.direcao == 'R' && direcao == 'L') return;
        this.direcao = direcao;
    }
}

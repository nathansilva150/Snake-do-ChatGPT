package model;

public class Cobra {

    public int[] x;
    public int[] y;
    public int tamanhoInicial = 3;
    public int corpo = tamanhoInicial;

    public Cobra(int maxPartes) {
        x = new int[maxPartes];
        y = new int[maxPartes];
    }

    public void resetar() {
        corpo = tamanhoInicial;
        for (int i = 0; i < x.length; i++) {
            x[i] = 0;
            y[i] = 0;
        }
    }
}
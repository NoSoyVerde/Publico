package paint;

import java.awt.Color;
import java.awt.Graphics;

abstract class Forma {
    protected int x, y;
    protected Color color;

    public Forma(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public abstract void dibujar(Graphics g);
}






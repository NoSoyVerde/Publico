package paint;

import java.awt.*;

class Linea extends Forma {
    private int endX, endY;

    public Linea(int x, int y, int endX, int endY, Color color) {
        super(x, y, color);
        this.endX = endX;
        this.endY = endY;
    }

    @Override
    public void dibujar(Graphics g) {
        g.setColor(color);
        g.drawLine(x, y, endX, endY);
    }
}





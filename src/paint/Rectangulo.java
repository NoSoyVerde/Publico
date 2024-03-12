package paint;

import java.awt.*;

class Rectangulo extends FormaRellenada {
    public Rectangulo(int x, int y, int width, int height, Color color, boolean rellenado) {
        super(x, y, width, height, color, rellenado);
    }

    @Override
    public void dibujar(Graphics g) {
        g.setColor(color);
        if (rellenado)
            g.fillRect(x, y, width, height);
        else
            g.drawRect(x, y, width, height);
    }
}

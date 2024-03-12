package paint;
// Hola :D
import java.awt.Color;
import java.awt.Graphics;

class Ovalo extends FormaRellenada {
    public Ovalo(int x, int y, int width, int height, Color color, boolean rellenado) {
        super(x, y, width, height, color, rellenado);
    }

    @Override
    public void dibujar(Graphics g) {
        g.setColor(color);
        if (rellenado)
            g.fillOval(x, y, width, height);
        else
            g.drawOval(x, y, width, height);
    }
}



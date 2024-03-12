package paint;
import java.awt.*;

class FormaRellenada extends Forma {
    protected int width, height;
    protected boolean rellenado;

    public FormaRellenada(int x, int y, int width, int height, Color color, boolean rellenado) {
        super(x, y, color);
        this.width = width;
        this.height = height;
        this.rellenado = rellenado;
    }

	@Override
	public void dibujar(Graphics g) {

		
	}
}



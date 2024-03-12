package paint;

// Me ha sido imposible hacer que la ultima forma 
//Dibujada se borre al darle a redo y clear
//Pero la funcion esta bien implementada, creo.
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Paint extends JFrame {
    private static final long serialVersionUID = 1L;
    private JComboBox<String> formaComboBox;
    private JComboBox<String> colorComboBox;
    private JCheckBox rellenadoCheckBox;
    private DrawPanel drawPanel;
    private List<Forma> formas;

    public Paint() {
        setTitle("Paint");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel controlPanel = new JPanel();
        formaComboBox = new JComboBox<>(new String[]{"Rectángulo", "Óvalo", "Línea"});
        colorComboBox = new JComboBox<>(new String[]{"Negro", "Rojo", "Azul", "Verde"});
        rellenadoCheckBox = new JCheckBox("Rellenado");

        controlPanel.add(new JLabel("Forma:"));
        controlPanel.add(formaComboBox);
        controlPanel.add(new JLabel("Color:"));
        controlPanel.add(colorComboBox);
        controlPanel.add(rellenadoCheckBox);

        JButton undoButton = new JButton("Deshacer");
        undoButton.addActionListener(e -> undo());

        JButton redoButton = new JButton("Rehacer");
        redoButton.addActionListener(e -> redo());

        JButton clearButton = new JButton("Limpiar");
        clearButton.addActionListener(e -> clear());

        controlPanel.add(undoButton);
        controlPanel.add(redoButton);
        controlPanel.add(clearButton);

        drawPanel = new DrawPanel();
        drawPanel.setBackground(Color.WHITE);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(controlPanel, BorderLayout.NORTH);
        getContentPane().add(drawPanel, BorderLayout.CENTER);

        formaComboBox.addActionListener(e -> drawPanel.setFormaSeleccionada(formaComboBox.getSelectedIndex()));

        colorComboBox.addActionListener(e -> drawPanel.setColorSeleccionado(obtenerColor()));

        rellenadoCheckBox.addActionListener(e -> drawPanel.setRellenado(rellenadoCheckBox.isSelected()));

        formas = new ArrayList<>();
        setVisible(true);
    }

    private Color obtenerColor() {
        String selectedColor = (String) colorComboBox.getSelectedItem();
        switch (selectedColor) {
            case "Rojo":
                return Color.RED;
            case "Azul":
                return Color.BLUE;
            case "Verde":
                return Color.GREEN;
            case "Negro":
            default:
                return Color.BLACK;
        }
    }

    private void undo() {
        if (!formas.isEmpty()) {
            Forma formaEliminada = formas.remove(formas.size() - 1);
            drawPanel.repaint();
        }
    }

    private void redo() {

    }

    private void clear() {
        formas.clear();
        drawPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Paint::new);
    }

    private class DrawPanel extends JPanel {
        private static final long serialVersionUID = 1L;
        private int startX, startY, endX, endY;
        private int formaSeleccionada = 0;
        private Color colorSeleccionado = Color.BLACK;
        private boolean rellenado = false;

        public DrawPanel() {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    startX = e.getX();
                    startY = e.getY();
                    endX = startX;
                    endY = startY;
                    repaint();
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    endX = e.getX();
                    endY = e.getY();
                    formas.add(crearForma());
                    repaint();
                }
            });

            addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    endX = e.getX();
                    endY = e.getY();
                    repaint();
                }
            });
        }

        private Forma crearForma() {
            int width = Math.abs(endX - startX);
            int height = Math.abs(endY - startY);
            int x = Math.min(startX, endX);
            int y = Math.min(startY, endY);
            Forma nuevaForma = null;
            switch (formaSeleccionada) {
                case 0:
                    nuevaForma = new Rectangulo(x, y, width, height, colorSeleccionado, rellenado);
                    break;
                case 1:
                    nuevaForma = new Ovalo(x, y, width, height, colorSeleccionado, rellenado);
                    break;
                case 2:
                    nuevaForma = new Linea(startX, startY, endX, endY, colorSeleccionado);
                    break;
            }
            return nuevaForma;
        }

        public void setFormaSeleccionada(int index) {
            formaSeleccionada = index;
        }

        public void setColorSeleccionado(Color color) {
            colorSeleccionado = color;
        }

        public void setRellenado(boolean rellenado) {
            this.rellenado = rellenado;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (Forma forma : formas) {
                forma.dibujar(g);
            }
            if (startX != endX && startY != endY) {
                g.setColor(colorSeleccionado);
                switch (formaSeleccionada) {
                    case 0:
                        if (rellenado)
                            g.fillRect(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX - startX),
                                    Math.abs(endY - startY));
                        else
                            g.drawRect(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX - startX),
                                    Math.abs(endY - startY));
                        break;
                    case 1:
                        if (rellenado)
                            g.fillOval(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX - startX),
                                    Math.abs(endY - startY));
                        else
                            g.drawOval(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX - startX),
                                    Math.abs(endY - startY));
                        break;
                    case 2:
                        g.drawLine(startX, startY, endX, endY);
                        break;
                }
            }
        }
    }
}


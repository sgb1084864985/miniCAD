package draw_elements;
import java.awt.Graphics;

// ElementCircle
import java.awt.*;

public class ElementCircle extends DrawElement{
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D gg = (Graphics2D)g;
        gg.setColor(c);
        gg.fillOval(0,0,w,h);
    }

    @Override
    public DrawElement propertyClone() {
        return new ElementCircle();
    }
}
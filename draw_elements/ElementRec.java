package draw_elements;

import java.awt.*;

public class ElementRec extends DrawElement{
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D gg = (Graphics2D)g;
        gg.setColor(c);
        gg.fillRect(0,0,w,h);
    }

    @Override
    public DrawElement propertyClone() {
        return new ElementRec();
    }
}

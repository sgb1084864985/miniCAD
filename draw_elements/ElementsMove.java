package draw_elements;
import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

// ElementsMove.java

public class ElementsMove extends MouseInputAdapter{
    int oriX;
    int oriY;

    private static DrawElement getSource(MouseEvent e){
        return (DrawElement)e.getSource();
    }
    @Override
    public void mousePressed(MouseEvent e) {
        oriX=e.getX();
        oriY=e.getY();
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        DrawElement src = getSource(e);
        src.x+=e.getX()-oriX;
        src.y+=e.getY()-oriY;
        src.reBound();
    }
}
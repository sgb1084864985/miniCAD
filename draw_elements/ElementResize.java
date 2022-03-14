package draw_elements;
import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

// ElementResize.java

public class ElementResize extends MouseInputAdapter{
    int oriX,oriY;
    @Override
    public void mousePressed(MouseEvent e) {
        oriX=e.getX();
        oriY=e.getY();
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        DrawElement.Corner corner = (DrawElement.Corner)e.getSource();
        int x=e.getX();
        int y=e.getY();
        // int oriOwnerX=corner.getOwnerW()-corner.width;
        // int oriOwnerY=corner.getOwnerY()-corner.height;
        // if(oriOwnerX+oriX-x>0 && oriOwnerY+oriY-y>0){
            corner.OwnerResize(x-oriX, y-oriY);
            corner.requestRebound();
        // }

    }
}
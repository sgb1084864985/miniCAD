// ElementsOperations.java
package draw_elements;
import java.awt.event.*;
import java.awt.Point;

import javax.swing.JPanel;

public class ElementsCreate extends MouseAdapter{
    int oriX,oriY;
    boolean clicked=false;
    String type;
    DrawElement tmpElement;
    ActionListener ElementCreateAction;
    public synchronized void setElementCreateAction(ActionListener elementCreateAction) {
        ElementCreateAction = elementCreateAction;
    }
    public synchronized void clearElementCreateAction(){
        ElementCreateAction=null;
    }

    public void processCreateEvent(){
        ActionListener actionCopy;
        synchronized(this){
            if(ElementCreateAction==null) return;
            actionCopy = ElementCreateAction;
        }
        actionCopy.actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,null));
    }
    public DrawElement getTmpElement() {
        return tmpElement;
    }

    public ElementsCreate(String type){
        this.type=type;
    }
    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton()!=1) return;
        clicked=true;
        oriX=e.getX();
        oriY=e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(clicked){
            int x=e.getX();
            int y=e.getY();

            if(tmpElement==null){
                tmpElement = DrawElement.createElement(type,new Point(oriX,oriY));
                processCreateEvent();
            }

            tmpElement.setDimension(Math.min(x,oriX), Math.min(y,oriY), Math.abs(oriX-x),Math.abs(oriY-y));
            // tmpElement.reBound();
            tmpElement.setPos();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(clicked){
            clicked=false;
            JPanel src = (JPanel)e.getSource();
            src.removeMouseListener(this);
            src.removeMouseMotionListener(this);
            clearElementCreateAction();
        }
    }
}
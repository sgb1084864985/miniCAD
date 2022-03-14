package draw_elements;

import java.awt.*;
// import javax.swing.*;

public class ElementLine extends DrawElement{
    Point OriP;
    private int orient=-1;
    private boolean orientMutable=true;
    static final int kLD=0,kRD=2,kXX=-1;

    @Override
    public DrawElement propertyClone() {
        return new ElementLine();
    }

    @Override
    void infoCopy(DrawElement other) {
        ElementLine otherL =(ElementLine)other;
        otherL.orientMutable=orientMutable;
        otherL.OriP=OriP;
        super.infoCopy(other);
        otherL.orient=orient;
    }

    boolean isOrientValid(){
        return orient!=kXX;
    }

    public boolean isOrientMutable() {
        return orientMutable;
    }

    public void setOrientMutable(boolean orientMutable) {
        this.orientMutable = orientMutable;
    }
    ElementLine(){
        super();
    }
    ElementLine(Point p){
        this();
        OriP=p;
    }
    public void setOriP(Point oriP) {
        OriP = oriP;
    }

    @Override
    public void setDimension(int x, int y, int w, int h) {
        super.setDimension(x, y, w, h);
        if(isOrientMutable()){
            if(OriP.x==x&&OriP.y==y || OriP.x==x+w && OriP.y==y+h){
                orient=kLD;
            }
            else if(OriP.x==x&&OriP.y==y+h || OriP.x==x+w && OriP.y==y){
                orient=kRD;
            }
            else{
                setOrientMutable(false);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        if(!isOrientValid())return;
        Graphics2D gg = (Graphics2D)g;
        gg.setColor(c);
        if(orient==kLD){
            gg.drawLine(0, 0, w, h);
        }
        else gg.drawLine(0, h, w, 0);
    }
}

package draw_elements;
import javax.swing.event.MouseInputAdapter;
import javax.swing.border.*;
import javax.swing.*;


import java.awt.*;
import java.awt.event.*;
// DrawObject.java

abstract public class DrawElement extends JPanel{
    int x,y,w,h;
    Color c=Color.black;
    Stroke stroke;
    Corner[] corners=new Corner[4];
    static Border eleBorder=BorderFactory.createDashedBorder(null);

    public DrawElement(){
        setLayout(null);

        // add mouse listener
        setMouse();

        // add corner
        corners[0]=new Corner(Corner.RD);
        corners[1]=new Corner(Corner.RU);
        corners[2]=new Corner(Corner.LD);
        corners[3]=new Corner(Corner.LU);

        for(Corner corner:corners){
            add(corner);
        }
        setCornersVisible(false);
        // focus
        setFocus();
    }

    public void biInit(){
        setMouse();
        setFocus();
        for(Corner corner:corners){
            corner.addResizer();
        }
    }
    @Override
    public DrawElement clone(){
        DrawElement copyElement=propertyClone();
        infoCopy(copyElement);
        return copyElement;
    }

    abstract public DrawElement propertyClone();

    void infoCopy(DrawElement other){
        other.setDimension(x, y, w, h);
        other.c=c;
        other.stroke=stroke;
    }

    public void slightMove(){
        setDimension(x+x/5+3, y+y/5+3, w, h);
        setPos();
    }

    void setFocus(){
        setFocusable(true);
        addFocusListener(new FocusAdapter(){
            @Override
            public void focusGained(FocusEvent e) {
                setBorder(eleBorder);
                setCornersVisible(true);
                getParent().repaint();
            }
            @Override
            public void focusLost(FocusEvent e) {
                setBorder(null);
                setCornersVisible(false);
                if(getParent()!=null) getParent().repaint();
            }
        });
    }

    void setMouse(){
        MouseInputAdapter mia=new ElementsMove();
        addMouseListener(mia);
        addMouseMotionListener(mia);
    }

    public void setAllCorners(){
        for(Corner corner:corners){
            corner.setCorner();
        }
    }

    void reBound(){
        setPos();
        setAllCorners();
    }
    public void setColor(Color c) {
        this.c = c;
    }
    public Color getColor(){
        return c;
    }
    public void setDimension(int x,int y,int w,int h){
        this.x=x;
        this.y=y;
        this.w=w;
        this.h=h;
    }
    public void setPos(){
        setBounds(x,y, w, h);
    }

    public void setCornersVisible(boolean visible){
        for(Corner corner:corners){
            corner.setVisible(visible);
        }
    }

    public static DrawElement createElement(String name,Point oriP){
        if(name.equals("rect")){
            return new ElementRec();
        }
        else if(name.equals("circle")){
            return new ElementCircle();
        }
        else if(name.equals("text")){
            return new ElementText();
        }
        else if(name.equals("line")){
            return new ElementLine(oriP);
        }
        return null;
    }

    class Corner extends JPanel{
        final int width=4,height=4;
        static final int RD=0,RU=1,LD=2,LU=3;
        int Pos;
        Corner(){
            addResizer();
        }

        void addResizer(){
            ElementResize resizer = new ElementResize();
            addMouseListener(resizer);
            addMouseMotionListener(resizer);
        }
        Corner(int pos){
            this();
            setPosition(pos);
        }
        public int getOwnerX(){
            return x;
        }
        public int getOwnerY(){
            return y;
        }
        public int getOwnerW(){
            return w;
        }
        public int getOwnerH(){
            return h;
        }
        public void setOwnerSize(int w_,int h_){
            setDimension(x, y, w_, h_);
        }
        public void setPosition(int pos) {
            Pos = pos;
        }

        void setCorner(){
            setCorner(w, h, Pos);
        }
        void setCorner(int w,int h){
            setCorner(w, h, Pos);
        }
        void setCorner(int w,int h,int Pos){
            this.Pos=Pos;
            switch(Pos){
                case RD:
                    setBounds(w-width,h-height,width,height);
                    break;
                case RU:
                    setBounds(w-width,0,width,height);
                    break;
                case LD:
                    setBounds(0,h-height,width,height);
                    break;
                case LU:
                    setBounds(0,0,width,height);
                    break;
                default:
            }
        }
        void OwnerResize(int dx,int dy){
            switch(Pos){
                case RD:
                    setDimension(x, y, w+dx, h+dy);
                    break;
                case RU:
                    setDimension(x, y+dy, w+dx, h-dy);
                    break;
                case LD:
                    setDimension(x+dx, y, w-dx, h+dy);
                    break;
                case LU:
                    setDimension(x+dx, y+dy, w-dx, h-dy);
                    break;
                default:
            }
        }
        void requestRebound(){
            reBound();
        }
    }
}

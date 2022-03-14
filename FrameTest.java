// FrameTest.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FrameTest extends JFrame{
    FrameTest(){
        DrawPanel dp=null;
        JMenuBar jmb=new JMenuBar();
        setJMenuBar(jmb);
        JMenu jm=new JMenu("hello");
        jmb.add(jm);

        JMenuItem jmi1=new JMenuItem("world");
        jm.add(jmi1);
        add(new MyToolBar(),BorderLayout.EAST);
        add(dp=new DrawPanel(),BorderLayout.CENTER);

        
        jmi1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                TextFrame.main(null);
                System.out.println("Hello,World!");
            }
        });

    }



    public void bi_show(){
        setTitle("The Front View of a Microwave Oven");
        setSize(1000,750);
        setLocationRelativeTo(null); // Center the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    public static void main(String[] args) {
        FrameTest frame = new FrameTest();
        frame.bi_show();        
    }
}

class MyToolBar extends JToolBar{
    MyToolBar(){
        setFloatable(false);
        setOrientation(VERTICAL);
        JButton jb1,jb2,jb3;
        add(jb1=new JButton("1"));
        add(jb2=new JButton("2"));
        add(jb3=new JButton("3"));
        jb1.setBorderPainted(false);
        jb2.setBorderPainted(false);
        jb3.setBorderPainted(false);
    }
}

class DrawPanel extends JPanel{
    MyDrawing j1,j2;
    DrawPanel(){
        setLayout(null);
        j1 = new MyLine(10,10,100,30);
        j2 = new MyLine(100,100,30,30);

        add(j1);
        add(j2);
        addMouseMotionListener(new MouseAdapter(){
            @Override
            public void mouseDragged(MouseEvent e) {
                j1.r.width-=4;
                j1.r.height+=4;
                j1.setBounds(j1.r.x,j1.r.y,j1.r.width,j1.r.height);
            }
        });
    }
}

class MyDrawing extends JPanel{
    
    Rectangle r = new Rectangle();
    Point clickP = new Point();
    Color c=Color.BLACK;

    MyDrawing(){
        this(0,0,10,10);
    }
    MyDrawing(int x,int y,int w,int h){
        r.x=x;
        r.y=y;
        r.width=w;
        r.height=h;
        // setBackground(c);
        setBounds(r);
        addMouseMotionListener(new MouseMotionAdapter(){
            @Override
            public void mouseDragged(MouseEvent e) {
                r.setLocation(r.x+e.getX()-clickP.x,r.y+e.getY()-clickP.y);
                setBounds(r);
            }
        });
        addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e) {
                // System.out.printf("%d,%d\n",e.getX(),e.getY());
                clickP.x=e.getX();
                clickP.y=e.getY();
            }
        });
    }
}

class MyLine extends MyDrawing{
    MyLine(int x,int y,int w,int h){
        super(x, y, w, h);
    }
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D gg= (Graphics2D) g;
        // super.paintComponent(g);
        gg.setBackground(Color.red);
        gg.setColor(c);
        gg.fillRect(0, 0, r.width, r.height);
        // gg.drawLine(0,0,r.width,r.height);
    }
}

class TextFrame extends JFrame{
    
    TextFrame(){
        setLayout(new BorderLayout(30,15));
        add(new JPanel(),BorderLayout.EAST);
        add(new JPanel(),BorderLayout.WEST);
        add(new JPanel(),BorderLayout.NORTH);
        add(new JPanel(),BorderLayout.SOUTH);

        add(new CenterPanel(),BorderLayout.CENTER);
    }

    void biInit(){
        
        setTitle("input");
        setSize(400,300);
        setResizable(false);
        setLocationByPlatform(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        setVisible(true);
    }
    public static void main(String[] args) {
        TextFrame tf=new TextFrame();
        tf.biInit();    
    }

    class CenterPanel extends JPanel{
        JLabel lText,lSize;
        JTextField tText,tSize;
        JButton bOk,bCancel;
        CenterPanel(){
            setLayout(new GridLayout(0,2,40,20));
            lText=new JLabel("text",JLabel.CENTER);
            lSize=new JLabel("size",JLabel.CENTER);
            tText=new JTextField();
            tSize=new JTextField();
            bOk=new JButton("OK");
            bCancel=new JButton("CANCEL");
            add(lText);
            add(tText);
            add(lSize);
            add(tSize);
            add(bOk);
            add(bCancel);

            bOk.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(tText.getText());
                    dispose();
                }
            });
        }
    }
}
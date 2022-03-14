package draw_elements;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


// ElementText.java

public class ElementText extends DrawElement{
    String content="Write Something...";
    Font contentFont=new Font("Courier", Font.BOLD, 20);
    ElementText(){
        addKeyBoard();
    }

    void addKeyBoard(){
        addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ENTER){
                    TextFrame tf = new TextFrame();
                    tf.biInit();
                    getParent().repaint();
                }
            }
        });
    }

    @Override
    public void biInit() {
        super.biInit();
        addKeyBoard();
    }

    @Override
    public DrawElement propertyClone() {
        return new ElementText();
    }

    @Override
    void infoCopy(DrawElement other) {
        super.infoCopy(other);
        ElementText tmp = (ElementText) other;
        tmp.content=content;
        tmp.contentFont=contentFont;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D gg = (Graphics2D)g;
        
        gg.setColor(c);
        gg.setFont(contentFont);
        gg.drawString(content,w/2-gg.getFontMetrics().stringWidth(content)/2,h/2+gg.getFontMetrics().getAscent()/2);
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
            
            setTitle("text settings");
            setSize(400,300);
            setResizable(false);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            
            setVisible(true);
        }
        class CenterPanel extends JPanel{
            JLabel lText,lSize;
            JTextField tText,tSize;
            JButton bOk,bCancel;
            CenterPanel(){
                setLayout(new GridLayout(0,2,40,20));
                lText=new JLabel("text",JLabel.CENTER);
                lSize=new JLabel("size",JLabel.CENTER);
                tText=new JTextField(content);
                tSize=new JTextField(Integer.toString(contentFont.getSize()));
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
                        content=tText.getText();
                        contentFont=contentFont.deriveFont(contentFont.getStyle(),Integer.valueOf(tSize.getText()));
                        dispose();
                        ElementText.this.getParent().repaint();
                        
                    }
                });

                bCancel.addActionListener(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                        ElementText.this.getParent().repaint();

                    }
                });
            }
        }
    }
}


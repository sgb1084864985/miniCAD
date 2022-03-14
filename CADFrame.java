import java.awt.*;
import javax.swing.*;

// CADFrame.java

class CADFrame extends JFrame{
    subMenuBar sMenuBar;
    subToolBar sToolBar;
    subDrawPanel sDrawPanel;
    public static void main(String[] args) {
        CADFrame frame = new CADFrame();

        // binding
        ImageIcon iRect = new ImageIcon(frame.getClass().getResource("icons/rect.png"));
        ImageIcon iCircle = new ImageIcon(frame.getClass().getResource("icons/circle.png"));
        ImageIcon iText = new ImageIcon(frame.getClass().getResource("icons/text.png"));
        ImageIcon iLine = new ImageIcon(frame.getClass().getResource("icons/line.png"));

        frame.getsToolBar().add(frame.getsDrawPanel().getActionCreateElement("rect",iRect));
        frame.getsToolBar().add(frame.getsDrawPanel().getActionCreateElement("circle",iCircle));
        frame.getsToolBar().add(frame.getsDrawPanel().getActionCreateElement("text",iText));
        frame.getsToolBar().add(frame.getsDrawPanel().getActionCreateElement("line",iLine));
        frame.getsToolBar().add(frame.getsDrawPanel().getActionColorChooser());

        frame.getsMenuBar().getJmFile().add(frame.getsDrawPanel().getActionOpen());
        frame.getsMenuBar().getJmFile().add(frame.getsDrawPanel().getActionSave());
        frame.getsMenuBar().getJmFile().add(frame.getsDrawPanel().getActionClear());

        frame.getsMenuBar().getJmEdit().add(frame.getsDrawPanel().getActionPaste());
        frame.getsMenuBar().getJmEdit().add(frame.getsDrawPanel().getActionCopy());
        frame.getsMenuBar().getJmEdit().add(frame.getsDrawPanel().getActionCut());
        frame.getsMenuBar().getJmEdit().add(frame.getsDrawPanel().getActionRemove());

        //show
        frame.setTitle("miniCAD");
        frame.setSize(1000,750);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public subDrawPanel getsDrawPanel() {
        return sDrawPanel;
    }

    public subToolBar getsToolBar() {
        return sToolBar;
    }

    public subMenuBar getsMenuBar() {
        return sMenuBar;
    }
    CADFrame(){
        // add components
        setJMenuBar(sMenuBar=new subMenuBar());
        add(sToolBar=new subToolBar(),BorderLayout.EAST);
        add(sDrawPanel=new subDrawPanel(),BorderLayout.CENTER);
    }

    static class subMenuBar extends JMenuBar{
        JMenu jmFile,jmEdit;
        subMenuBar(){
            jmFile = new JMenu("File");
            add(jmFile);

            jmEdit = new JMenu("Edit");
            add(jmEdit);
        }
        public JMenu getJmFile() {
            return jmFile;
        }
        public JMenu getJmEdit() {
            return jmEdit;
        }
    }
    static class subToolBar extends JToolBar{
        subToolBar(){
            setFloatable(false);
            setOrientation(VERTICAL);
            setBorder(BorderFactory.createEtchedBorder());
        }
    }
}
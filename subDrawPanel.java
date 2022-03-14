import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.filechooser.FileNameExtensionFilter;

import draw_elements.DrawElement;
import draw_elements.ElementsCreate;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

// DrawPanel.java

public class subDrawPanel extends JPanel{
    Color bColor=Color.lightGray;
    DrawElement MouseFocusedElement;
    DrawElement copyElement;
    Action saveAction,createAction,openAction,clearAction,colorAction;
    Action removeAction,copyAction,cutAction,pasteAction;

    subDrawPanel(){
        setLayout(null);
        setBackground(bColor);

        addMouseListener(new MouseInputAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                reclaimFocus();
                // repaint();
            }
        });
    }

    void addDrawElement(DrawElement ele){
        add(ele);
        ele.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                grantFocus((DrawElement)e.getSource());
                // repaint();
            }
        });
    }

    void grantFocus(DrawElement ele){
        MouseFocusedElement=ele;
        ele.requestFocusInWindow();
    }

    void reclaimFocus(){
        MouseFocusedElement=null;
        requestFocusInWindow();
    }

    public Action getActionCreateElement(String name,Icon icon){
        return new ElementCreateAction(name,icon);
    }

    void saveDrawElements(File file){
        synchronized(getTreeLock()){
            try{
                Component[] components = getComponents();
                ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
                output.writeInt(components.length);
                for(Component obj:components){
                    output.writeObject(obj);
                }
                output.close();
            }
            catch(IOException ex){
                System.out.printf("can not open file %s\n",file.getAbsoluteFile());
            }
        }
    }

    void removeDrawElements(DrawElement ele){
        remove(ele);
        repaint();
    }

    void clearDrawElements(){
        removeAll();
        subDrawPanel.this.repaint();
    }

    public Action getActionRemove(){
        if(removeAction==null){
            removeAction=
            new AbstractAction() {
                {
                    putValue(Action.NAME, "delete");
                    putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
                    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
                }
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(MouseFocusedElement!=null){
                        removeDrawElements(MouseFocusedElement);
                        reclaimFocus();
                    }
                }
            };
        }
        return removeAction;
    }

    public Action getActionClear(){
        if(clearAction==null)
            clearAction=
            new AbstractAction() {
                {
                    putValue(Action.NAME, "new file");
                    putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
                    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
                }
                @Override
                public void actionPerformed(ActionEvent e) {
                    clearDrawElements();
                }
            };
        return clearAction;
    }

    public Action getActionSave(){
        if(saveAction==null){
            saveAction=
            new AbstractAction() {
                {
                    putValue(Action.NAME, "save");
                    putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
                    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
                }
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFileChooser jf = new JFileChooser();
                    FileNameExtensionFilter filter = new FileNameExtensionFilter(
                             "CAD文件(*cad)", "cad");
                    jf.setFileFilter(filter);
                    jf.setSelectedFile(new File("new1.cad"));
                    int option = jf.showSaveDialog(null);
                    if(option==JFileChooser.APPROVE_OPTION){
                        File file = jf.getSelectedFile();
                        System.out.println(file.getAbsolutePath());
                        saveDrawElements(file);
                    }
                }
            };
        }
        return saveAction;
    }

    void loadDrawElements(File file){
        clearDrawElements();
        try{
            int n;
            DrawElement ele;
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(file));
            n=input.readInt();
            for(int i=0;i<n;i++){
                ele=(DrawElement)input.readObject();
                if(ele==null)continue;
                ele.biInit();
                addDrawElement(ele);
            }
            input.close();
            subDrawPanel.this.repaint();
        }
        catch(IOException ex){
            System.out.printf("can not open file %s\n",file.getAbsoluteFile());
        }
        catch(ClassNotFoundException ex){
            System.out.println("class not found");
        }
    }

    public Action getActionOpen(){
        if(openAction==null){
            
            openAction=new AbstractAction() {
                {
                    putValue(Action.NAME, "open");
                    putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
                    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
                }
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFileChooser jf = new JFileChooser();
                    FileNameExtensionFilter filter = new FileNameExtensionFilter(
                             "CAD文件(*cad)", "cad");
                    jf.setFileFilter(filter);
                    // jf.setSelectedFile(new File("new1.cad"));
                    int option = jf.showOpenDialog(null);
                    if(option==JFileChooser.APPROVE_OPTION){
                        File file = jf.getSelectedFile();
                        System.out.println(file.getAbsolutePath());
    
                        if(file.exists()){
                            loadDrawElements(file);
                        }
                    }
                }
            };
        }
        return openAction;
    }

    public Action getActionCopy(){
        if(copyAction==null){
            copyAction = new AbstractAction() {
                {
                    putValue(Action.NAME, "copy");
                    putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
                    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
                }
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(MouseFocusedElement!=null){
                        copyElement = MouseFocusedElement.clone();
                    }
                }
            };
        }
        return copyAction;
    }

    public Action getActionPaste(){
        if(pasteAction==null){
            pasteAction = new AbstractAction() {
                {
                    putValue(Action.NAME, "paste");
                    putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);
                    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
                }
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(copyElement!=null){
                        DrawElement newCopy=copyElement.clone();
                        addDrawElement(newCopy);
                        newCopy.slightMove();
                        grantFocus(newCopy);
                    }
                }
            };
        }
        return pasteAction;
    }

    public Action getActionCut(){
        if(cutAction==null){
            cutAction = new AbstractAction() {
                {
                    putValue(Action.NAME, "cut");
                    putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
                    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
                }
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(MouseFocusedElement!=null){
                        copyElement = MouseFocusedElement.clone();
                        removeDrawElements(MouseFocusedElement);
                        reclaimFocus();
                    }
                }
            };
        }
        return cutAction;
    }

    public Action getActionColorChooser(){
        if(colorAction==null){
            colorAction=
                new AbstractAction() {
                    {
                        putValue(Action.NAME,"color");
                        ImageIcon iColor = new ImageIcon(getClass().getResource("icons/color.png"));
                        putValue(Action.SMALL_ICON, iColor);
                    }
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(MouseFocusedElement==null) return;
                        Color tmpColor = JColorChooser.showDialog(subDrawPanel.this,"choose color",MouseFocusedElement.getColor());
                        if(tmpColor!=null){
                            MouseFocusedElement.setColor(tmpColor);
                        }
                        subDrawPanel.this.repaint();
                }
            };
        }
        return colorAction;
    }

    class ElementCreateAction extends AbstractAction{
        ElementCreateAction(String name,Icon icon){
            super(name,icon);
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            ElementsCreate eleCreate= new ElementsCreate((String)getValue(Action.NAME));
            addMouseListener(eleCreate);
            addMouseMotionListener(eleCreate);

            eleCreate.setElementCreateAction(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    ElementsCreate src = (ElementsCreate)e.getSource();
                    addDrawElement(src.getTmpElement());
                }
            });
        }
    }

}


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scr;

/**
 *
 * @author keliyi
 */
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.TimeUnit;
import javax.swing.text.BadLocationException;

import com.sun.awt.AWTUtilities;

public class JavaScrFrm {

    static Font f = new Font("微软雅黑", Font.BOLD, 20);
    static int screenwid=(int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(),screenhei=(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();

    private static class MainFrm extends JFrame implements Runnable {

        StringsList sl0 = new StringsList(MainFrm.class.getClassLoader().getResource("wnwb2_gb.txt").getPath());
        StringPanel sp = new StringPanel(20);
        JPanel ResultStringJPanel = new JPanel();
        JLabel ResultStringJLabel = new JLabel();
        int pagenum = 1, x = 0, y = 0;
        
        private boolean typing=false;

        public MainFrm(String title) {

            //set title
            setTitle(title);
            setFocusable(false);
            add(ResultStringJLabel);
            ResultStringJLabel.setFont(f);
            ResultStringJLabel.setBounds(0, 0, 0, 0);
            add(sp);
            setLayout(null);
            setUndecorated(true);
            AWTUtilities.setWindowOpaque(this, false);
            LocationSuit(x, y);
            //set visible
            setVisible(true);

            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    System.out.println(".windowClosing()");
                }
            });

           

        }

        public void LocationSuit(int x,int y) {
            int finx, finy;
//            this.x = (int) MouseInfo.getPointerInfo().getLocation().getX();
//            this.y = (int) MouseInfo.getPointerInfo().getLocation().getY();
            this.x=x;
            this.y=y;
            if (x>(screenwid-getWidth())) {
                finx= (screenwid-getWidth());
            } else {
                finx = x;
            }
            if (y > (screenhei-getHeight())) {
                finy = (screenhei-getHeight());
            } else {
                finy = y;
            }
            setBounds(finx, finy, getWidth(), getHeight());
        }

        

        @Override
        public void run() {
            while (true) {
                LocationSuit(x, y);
                try {
                    
                    TimeUnit.MILLISECONDS.sleep(100);
                    
                } catch (InterruptedException e) {
                }
            }
        }
        
       

       
        public void suitText(int num, String str) {
            sp.setString(num, (num + 1) + "." + str);
        }

       

    }

//    public JavaScrFrm() {
//        MainFrm mf = new MainFrm("");
//        new Thread(mf).start();
//
//    }
    
    private MainFrm mf = new MainFrm("");
    
    public JavaScrFrm() {
    	
    	new Thread(mf).start();
    	
    }
    
    public void setFrmLoc(int x,int y ){
    	mf.LocationSuit(x, y);
    }

    public void getFocus(){
    	mf.requestFocus();
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scr;



import java.awt.*;

import javax.swing.*;

/**
 *
 * @author keliyi
 */
public class StringPanel extends JPanel {

    JLabel StringButton[] = new JLabel[4];
    static Font f = new Font("微软雅黑", Font.BOLD, 20);
    protected FontMetrics fm = this.getFontMetrics(f);
    protected int width = fm.charWidth('A')*2;

    public StringPanel( int btnhei) {
        //setOpaque(false);
        setLayout(new GridLayout(1, 4));
        for (int i = 0; i < 4; i++) {
            StringButton[i] = new JLabel();
            StringButton[i].setSize(width, btnhei);
            StringButton[i].setFont(f);
            add(StringButton[i]);
        }
    }
    
    public void setString(int num,String str){
    StringButton[num].setText(str);
        seekWid(fm.charsWidth(str.toCharArray(), 0, str.length()));
    }
    
    public String getString(int num){
        return StringButton[num-1].getText();
    }
    
    
    public void seekWid(int width){
        this.width=width;
    }
    
    public int reWid(){
        return width;
    }
}

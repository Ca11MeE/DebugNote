package menu.textarea;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;

import org.junit.Test;

import main.TextArea;

/**
 * 实现行号条
 * 1.载入文件初始化行号，行号严格跟随文档
 * 2.实时监测文本行数变化
 * 3.跟随文本浏览滚动行号
 * @author keliyi
 * 
 *
 */
public class LineBar extends JPopupMenu {
	public LineBar(int lines,int fontSize) {
		for (int i = 0; i < lines; i++) {
			JMenuItem j = new JMenuItem();
			JLabel jLabel = new JLabel(i+"");
			jLabel.setPreferredSize(new Dimension(fontSize,fontSize));
			j.add(jLabel,BorderLayout.EAST);
			
			//j.setPreferredSize(new Dimension((int)(fontSize*1.5),fontSize));
			this.add(j);
		}
		this.setLocation(0, 0);
		this.setVisible(true);
	}
	
	public void updateMenu() {
		//this.setPreferredSize(new Dimension((int)(TextArea.getJTP().getFont().getSize()*1.5*this.getComponentCount()),TextArea.getJTP().getFont().getSize()));
		super.updateUI();
	}
	
	public void addLine() {
		JMenuItem j = new JMenuItem();
		JLabel jLabel = new JLabel(this.getComponentCount()+"");
		jLabel.setPreferredSize(new Dimension(TextArea.getJTP().getFont().getSize(),TextArea.getJTP().getFont().getSize()));
		j.add(jLabel,BorderLayout.EAST);
		//j.setPreferredSize(new Dimension((int)(TextArea.getJTP().getFont().getSize()*1.5),TextArea.getJTP().getFont().getSize()));
		this.add(j,this.getComponentCount());
		this.setVisible(false);
		this.setVisible(true);
	}
	
	public void subLine() {
		this.remove(this.getComponentCount()-1);
	}
	
	public static void main(String[] args) {
		LineBar lineBar = new LineBar(10, 12);
		lineBar.addLine();
		lineBar.subLine();
	}
}

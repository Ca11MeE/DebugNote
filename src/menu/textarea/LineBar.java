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

/**
 * 实现行号条
 * 1.载入文件初始化行号，行号严格跟随文档
 * 2.实时监测文本行数变化
 * 3.跟随文本浏览滚动行号
 * @author keliyi
 *
 */
public class LineBar extends JPopupMenu {
	public LineBar() {
		for (int i = 0; i < 10; i++) {
			JMenuItem j = new JMenuItem();
			JLabel jLabel = new JLabel(i+"");
			jLabel.setPreferredSize(new Dimension(jLabel.getFont().getSize(),jLabel.getFont().getSize()));
			j.add(jLabel,BorderLayout.EAST);
			
			j.setPreferredSize(new Dimension((int)(jLabel.getFont().getSize()*1.5),jLabel.getFont().getSize()));
			this.add(j);
		}
		this.setOpaque(true);
		this.setLocation(0, 0);
		this.setVisible(true);
		while (true) {
			
		}
	}
	
	@Test
	public void test() {
		new LineBar();
	}
}

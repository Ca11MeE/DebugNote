package menu.textarea;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import app.DebugNote;
import main.StyleForm;
import main.TextArea;
import utils.StyleInitor;

public class TextAreaMenu extends JPopupMenu{
	private List<JMenuItem> styles=new LinkedList<JMenuItem>();
	private List<JLabel> lblTemp=new LinkedList<JLabel>();
	private static TextAreaMenu menu=new TextAreaMenu();
	
	private TextAreaMenu(){
		this.setLabel("文件操作");
	}
	
	public static TextAreaMenu getMenu(){
		return menu;
	}
	
	/*
	 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 * 
	 * 
	 */
	public void addStyles(){
		this.removeAll();
		styles.clear();
		List<JLabel> list = StyleForm.getStyles();
		for (JLabel jLabel : list) {
			lblTemp.add(jLabel);
			JMenuItem addStyle = new JMenuItem();
			addStyle.addMouseListener(new MouseAdapter() {
				
				@Override
				public void mouseClicked(MouseEvent e) {
					
					menu.setVisible(false);
				}
				
			});
			addStyle.setText(jLabel.getText());
			styles.add(addStyle);
			
		}
		
		for (JMenuItem jMenuItem : styles) {
			this.add(jMenuItem);
		}
	}

}

package menu.textarea;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import main.StyleForm;

public class TextAreaMenu extends JPopupMenu{
	private List<JMenuItem> styles=new LinkedList<JMenuItem>();
	private static TextAreaMenu menu=new TextAreaMenu();
	
	private TextAreaMenu(){
		this.setLabel("文件操作");
	}
	
	public static TextAreaMenu getMenu(){
		return menu;
	}
	
	public void addStyles(){
		this.removeAll();
		styles.clear();
		List<JLabel> list = StyleForm.getStyles();
		for (JLabel jLabel : list) {
			JMenuItem addStyle = new JMenuItem();
			jLabel.addMouseListener(new MouseAdapter() {
				
				@Override
				public void mouseClicked(MouseEvent e) {
					menu.setVisible(false);
				}
				
			});
			addStyle.add(jLabel);
			styles.add(addStyle);
			
		}
		
		for (JMenuItem jMenuItem : styles) {
			this.add(jMenuItem);
		}
	}

}

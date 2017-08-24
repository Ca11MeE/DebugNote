package menu.textarea;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class TextAreaMenu extends JPopupMenu{
	private List<JMenuItem> styles=new LinkedList<JMenuItem>();
	
	private TextAreaMenu(){
		this.setLabel("文件操作");
	}
	
	public static TextAreaMenu getMenu(){
		return new TextAreaMenu();
	}
	
	public void addStyles(JLabel style){
		this.removeAll();
		JMenuItem addStyle = new JMenuItem();
		addStyle.add(style);
		styles.add(addStyle);
		for (JMenuItem jMenuItem : styles) {
			this.add(jMenuItem);
		}
	}

}

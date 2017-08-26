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

import com.sun.xml.internal.ws.org.objectweb.asm.Label;

import app.DebugNote;
import main.StyleForm;
import main.TextArea;
import utils.StyleInitor;

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
			addStyle.setText(jLabel.getText());
			addStyle.setForeground(jLabel.getForeground());
			addStyle.setBackground(jLabel.getBackground());
			addStyle.addMouseListener(new MouseAdapter() {
				
				@Override
				public void mouseClicked(MouseEvent e) {
					TextArea.changeSelectedStyle(StyleInitor.getInitStyleList().get(((JMenuItem)e.getComponent()).getText()));
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

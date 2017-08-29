package menu.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import main.FilePane;
import main.TextArea;

public class MainMenu extends JPopupMenu {

	private static JPopupMenu mainFrmMenu = new JPopupMenu("文件操作");
	private static JMenuItem createItem = new JMenuItem("新建");
	
	static{
		mainFrmMenu.add(createItem);
		createItem.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				/*
				 * 此处写入弹出新建文件窗口
				 */
				TextArea.createNewFile();
				mainFrmMenu.setVisible(false);
				FilePane.getLeftPane().updateUI();
			}

		});
		
 
	}

	public static JPopupMenu getMainFrmMenu() {
		return mainFrmMenu;
	}
	
	
}

package menu.file;

import javax.swing.JPopupMenu;

public class LeftPaneMenu extends JPopupMenu{
	
	private LeftPaneMenu(){
		this.setLabel("文件操作");
	}
	
	public static LeftPaneMenu getMenu(){
		return new LeftPaneMenu();
	}

}

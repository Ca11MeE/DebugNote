package menu.head;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenu;
import javax.swing.JPopupMenu;

public class MainMenu extends JMenu{
	
	private MainMenu() {
		this.setText("菜单");
		/*
		 * 菜单栏可以实现标题栏功能
		 */
		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("clicked");
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("released");
			}

			
		});
	}
	
	public static MainMenu getMainMenu(){
		
			return new MainMenu();
	}
	
}

package menu.style;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

import app.DebugNote;
import utils.StyleInitor;

public class Opt4OpenStyXML extends JFrame{
	
	private static Opt4OpenStyXML optFrm=new Opt4OpenStyXML();
	private static JButton openStyXML=new JButton("打开样式文档");
	
	static{
		optFrm.setUndecorated(true);
		openStyXML.setBorderPainted(false);
		openStyXML.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				hideFrm();
			}
		});
		openStyXML.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				DebugNote.getText().read(StyleInitor.getStyconfFile());
				DebugNote.getHeadPane().setLblText(StyleInitor.getStyconfFile().getPath());
				hideFrm();
			}
			
		});
		optFrm.add(openStyXML);
	}
	
	public void setLoc(int x,int y){
		optFrm.setBounds(x, y, 150,30);
		showFrm();
	}
	
	public static void showFrm(){optFrm.setVisible(true);}
	
	public static void hideFrm(){optFrm.setVisible(false);}
	
	public static Opt4OpenStyXML getOptFrm(){return optFrm;}
}

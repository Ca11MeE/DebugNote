package menu.style;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.text.BadLocationException;

import app.DebugNote;
import main.left.FilePane;
import utils.ListSort;
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
				File styconfFile = StyleInitor.getStyconfFile();
				try {
					DebugNote.getText().read(styconfFile);
				} catch (ClassNotFoundException e1) {
				} catch (IOException e1) {
				} catch (BadLocationException e1) {
				}
				if (ListSort.findPathInPathList(styconfFile.getName())==null) {
					ListSort.allPathAdd(styconfFile.getPath());
					FilePane.updateFullPathList();
					FilePane.updateHeadList();
					
				}
				DebugNote.getHeadPane().setLblText(styconfFile.getPath());
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

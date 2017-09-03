package frms;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import main.head.FrmHead;

public class FontFrm extends JFrame{
	
//	完成字体样式窗口
	
	private static FontFrm mFrm=new FontFrm(); 
	private static FrmHead frmHead=new FrmHead(mFrm,"双击选择字体，单击查看效果");
	private static JTextField search=new JTextField();
	private static JList<String> fontsInSys=new JList<String>();
	private static JScrollPane fontsPane=new JScrollPane(fontsInSys);
	private static DefaultListModel<String> fontModel=new DefaultListModel();
	private static String[] fontFamilys = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
	private static JButton apply=new JButton("应用");
	
	static {
		mFrm.setUndecorated(true);
		mFrm.setBounds(CreateFrm.getFrmBound());
		mFrm.setLayout(null);
		
		for (String str : fontFamilys) {
			fontModel.addElement(str);
		}
		fontsInSys.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				String selectedValue = ((JList<String>)e.getSource()).getSelectedValue();
				search.setFont(new Font(selectedValue, search.getFont().getStyle(), search.getFont().getSize()));
				search.setText(selectedValue);
				
			}
		});
		fontsInSys.setModel(fontModel);
		frmHead.setBounds(0, 0, mFrm.getWidth(), 30);
		
		apply.setOpaque(true);
		apply.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
			}
			
		});
		search.setBounds(0,frmHead.getHeight(),mFrm.getWidth(),mFrm.getHeight()/10);
		fontsPane.setBounds(0,frmHead.getHeight()+search.getHeight(),mFrm.getWidth(),mFrm.getHeight()-frmHead.getHeight()-search.getHeight()-search.getHeight());
		apply.setBounds(0, frmHead.getHeight()+search.getHeight()+fontsPane.getHeight(), mFrm.getWidth(), search.getHeight());
		mFrm.add(frmHead);
		mFrm.add(search);
		mFrm.add(fontsPane);
		mFrm.add(apply);
	}
	
	private FontFrm() {
		
	}

	public static FontFrm getmFrm() {
		mFrm.setBounds(CreateFrm.getFrmBound());
		return mFrm;
	}
	
	
}

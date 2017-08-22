package frms;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import main.FrmHead;
import main.MainFrm;

public class OpenFrm extends JFrame{
	private static OpenFrm mFrm=new OpenFrm();
	private static JFileChooser openDialog;
	private static FrmHead frmHead = new FrmHead(mFrm);
	private static FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("TXT File", "txt");
	private static FileNameExtensionFilter dbnFilter = new FileNameExtensionFilter("DBN File", "dbn");
	
	static{
		mFrm.setUndecorated(true);
		mFrm.setBounds(MainFrm.getmFrm().getX()+MainFrm.WIDTH / 3, MainFrm.getmFrm().getY()+MainFrm.HEIGHT / 3, MainFrm.WIDTH / 3, MainFrm.HEIGHT / 3);
		mFrm.setLayout(null);
		
		openDialog=new JFileChooser(){

			@Override
			public void cancelSelection() {
				mFrm.setVisible(false);
			}

			@Override
			public void approveSelection() {
				//打开文件
				System.out.println(openDialog.getSelectedFile());
				mFrm.setVisible(false);
			}
			
			
			
		};
		openDialog.setDialogType(JFileChooser.OPEN_DIALOG);
		//添加文件过滤器
		openDialog.setAcceptAllFileFilterUsed(true);
		openDialog.setFileFilter(txtFilter);
		openDialog.setFileFilter(dbnFilter);
		openDialog.setVisible(true);
		openDialog.setBounds(0,30,mFrm.getWidth(),mFrm.getHeight()-30);
		
		frmHead.setBounds(0, 0, mFrm.getWidth(), 30);
		mFrm.add(openDialog);
		mFrm.add(frmHead);
		
	}
	private OpenFrm(){
	}
	
	public static OpenFrm getFrm() {
		mFrm.setBounds(MainFrm.getmFrm().getX()+MainFrm.WIDTH / 3, MainFrm.getmFrm().getY()+MainFrm.HEIGHT / 3, MainFrm.WIDTH / 3, MainFrm.HEIGHT / 3);
		return mFrm;
	}
}

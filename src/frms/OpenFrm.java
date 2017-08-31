package frms;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import main.head.FrmHead;
import main.left.FilePane;
import utils.ListSort;

public class OpenFrm extends JFrame{
	private static OpenFrm mFrm=new OpenFrm();
	private static JFileChooser openDialog;
	private static FrmHead frmHead = new FrmHead(mFrm);
	private static FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("TXT File", "txt");
	private static FileNameExtensionFilter dbnFilter = new FileNameExtensionFilter("DBN File", "dbn");
	
	static{
		mFrm.setUndecorated(true);
		mFrm.setBounds(CreateFrm.getFrmBound());
		mFrm.setLayout(null);
		
		openDialog=new JFileChooser(){

			@Override
			public void cancelSelection() {
				mFrm.setVisible(false);
			}

			@Override
			public void approveSelection() {
				//打开文件
				File file = openDialog.getSelectedFile();
				String path = file.getPath();
				if (ListSort.findPathInPathList(file.getName())==null) {
					ListSort.allPathAdd(path);
					FilePane.updateFullPathList();
					FilePane.updateHeadList();
					
				}
				mFrm.setVisible(false);
			}
			
			
			
		};
		openDialog.setDialogType(JFileChooser.OPEN_DIALOG);
		//添加文件过滤器
		openDialog.setAcceptAllFileFilterUsed(false);
		openDialog.addChoosableFileFilter(dbnFilter);
		openDialog.addChoosableFileFilter(txtFilter);
//		openDialog.setFileFilter(txtFilter);
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
		mFrm.setBounds(CreateFrm.getFrmBound());
		return mFrm;
	}
}

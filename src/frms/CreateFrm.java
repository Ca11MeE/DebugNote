package frms;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import main.FrmHead;
import main.MainFrm;

public class CreateFrm extends JFrame{
	
	private static JFileChooser createDialog;
	private static CreateFrm mFrm=new CreateFrm();
	private static FrmHead frmHead = new FrmHead(mFrm);
	private static FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("TXT File", "txt");
	private static FileNameExtensionFilter dbnFilter = new FileNameExtensionFilter("DBN File", "dbn");

	
	static{
		mFrm.setUndecorated(true);
		mFrm.setLayout(null);
		mFrm.setBounds(MainFrm.getmFrm().getX()+MainFrm.WIDTH / 3, MainFrm.getmFrm().getY()+MainFrm.HEIGHT / 3, MainFrm.WIDTH / 3, MainFrm.HEIGHT / 3);
		createDialog=new JFileChooser(){

			@Override
			public void cancelSelection() {
				mFrm.setVisible(false);
			}

			@Override
			public void approveSelection() {
				//新建文件并保存
				String path = createDialog.getSelectedFile().getPath();
				File createFile =new File(path);
				//文件存在
				if (createFile.exists()) {
					int result = JOptionPane.showConfirmDialog(mFrm, "文件已存在,是否覆盖?");
					switch (result) {
					//点击确认
					case JOptionPane.OK_OPTION:
						createFile.delete();
						try {
							createFile.createNewFile();
						} catch (IOException e) {
							JOptionPane.showMessageDialog(mFrm, "文件创建失败");
						}
						break;

					case JOptionPane.NO_OPTION:
						break;
					}
				}else {
					//文件不存在
					try {
						createFile.createNewFile();
					} catch (IOException e) {
						
					}
					
				}
				
				mFrm.setVisible(false);
			}
			
			
			
		};
		createDialog.setDialogType(JFileChooser.SAVE_DIALOG);
		//添加文件过滤器
		createDialog.setAcceptAllFileFilterUsed(true);
		createDialog.setFileFilter(txtFilter);
		createDialog.setFileFilter(dbnFilter);
		createDialog.setVisible(true);
		createDialog.setBounds(0,30,mFrm.getWidth(),mFrm.getHeight()-30);
		
		frmHead.setBounds(0, 0, mFrm.getWidth(), 30);
		mFrm.add(createDialog);
		mFrm.add(frmHead);
		
		
	}
	
	private CreateFrm(){
		
	}
	
	public static CreateFrm getFrm() {
		mFrm.setBounds(MainFrm.getmFrm().getX()+MainFrm.WIDTH / 3, MainFrm.getmFrm().getY()+MainFrm.HEIGHT / 3, MainFrm.WIDTH / 3, MainFrm.HEIGHT / 3);
		return mFrm;
	}
}

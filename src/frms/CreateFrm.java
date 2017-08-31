package frms;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import app.DebugNote;
import main.head.FrmHead;
import main.left.FilePane;
import utils.FileReader;
import utils.ListSort;

public class CreateFrm extends JFrame{
	
	private static JFileChooser createDialog;
	private static CreateFrm mFrm=new CreateFrm();
	private static FrmHead frmHead = new FrmHead(mFrm);
	private static FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("TXT File", "txt");
	private static FileNameExtensionFilter dbnFilter = new FileNameExtensionFilter("DBN File", "dbn");
	private static Rectangle frmBound=new Rectangle(DebugNote.getmFrm().getX()+DebugNote.getmFrm().getWidth() / 4,
			DebugNote.getmFrm().getY()+DebugNote.getmFrm().getHeight()  / 4,
			DebugNote.getmFrm().getWidth()  / 2, 
			DebugNote.getmFrm().getHeight()  / 2);
	
	
	static{
		mFrm.setUndecorated(true);
		mFrm.setLayout(null);
		mFrm.setBounds(getFrmBound());
		createDialog=new JFileChooser(){

			@Override
			public void cancelSelection() {
				mFrm.setVisible(false);
			}

			@Override
			public void approveSelection() {
				//新建文件并保存
				String path = createDialog.getSelectedFile().getPath();
				File createFile =new File(path+"."+((FileNameExtensionFilter)createDialog.getFileFilter()).getExtensions()[0]);
				//默认以dbn形式写出文件
				int fileType=0;
				switch (((FileNameExtensionFilter)createDialog.getFileFilter()).getExtensions()[0]) {
				case "txt":
					fileType=FileReader.TXT_TYPE;
					break;
				case "dbn":
					fileType=FileReader.DBN_TYPE;
					break;
				}
				//文件存在
				if (createFile.exists()) {
					int result = JOptionPane.showConfirmDialog(mFrm, "文件已存在,是否覆盖?");
					switch (result) {
					//点击确认
					case JOptionPane.OK_OPTION:
						createFile.delete();
						try {
							createFile.createNewFile();
							//文件列表框添加新建文件路径
							ListSort.allPathAdd(createFile.getPath());
							FilePane.updateFullPathList();
							FilePane.updateHeadList();
							try {
								FileReader.writeWithExample(fileType, createFile);
								//文本编辑框打开该文件
								DebugNote.getText().read(createFile);
							} catch (Exception e) {
								JOptionPane.showMessageDialog(mFrm, e.getMessage());
							}
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
						//文件列表框添加新建文件路径
						ListSort.allPathAdd(createFile.getPath());
						FilePane.updateFullPathList();
						FilePane.updateHeadList();
						try {
							FileReader.writeWithExample(fileType, createFile);
							//文本编辑框打开该文件
							DebugNote.getText().read(createFile);
						} catch (Exception e) {
							JOptionPane.showMessageDialog(mFrm, e.getMessage());
						}
					} catch (IOException e) {
						
					}
					
				}
				
				mFrm.setVisible(false);
			}
			
			
			
		};
		createDialog.setDialogType(JFileChooser.SAVE_DIALOG);
		//添加文件过滤器
		createDialog.setAcceptAllFileFilterUsed(false);
		createDialog.addChoosableFileFilter(dbnFilter);
		createDialog.addChoosableFileFilter(txtFilter);
//		createDialog.setFileFilter(txtFilter);
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
		mFrm.setBounds(getFrmBound());
		return mFrm;
	}

	public static Rectangle getFrmBound() {
		return new Rectangle(DebugNote.getmFrm().getX()+DebugNote.getmFrm().getWidth() / 4,
				DebugNote.getmFrm().getY()+DebugNote.getmFrm().getHeight()  / 4,
				DebugNote.getmFrm().getWidth()  / 2, 
				DebugNote.getmFrm().getHeight()  / 2);
	}
	
	
}

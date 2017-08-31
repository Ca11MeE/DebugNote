package frms;

import java.awt.AWTEventMulticaster;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.InvalidPathException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import main.head.FrmHead;
import main.TextArea;
import utils.FileReader;

public class SaveFrm extends JFrame {

	private static JFileChooser saveDialog;
	private static SaveFrm mFrm = new SaveFrm();
	private static FrmHead frmHead = new FrmHead(mFrm);
	private static FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("TXT File", "txt");
	private static FileNameExtensionFilter dbnFilter = new FileNameExtensionFilter("DBN File", "dbn");

	static {
		mFrm.setUndecorated(true);
		mFrm.setLayout(null);
		mFrm.setBounds(CreateFrm.getFrmBound());
		saveDialog = new JFileChooser() {

			@Override
			public void cancelSelection() {
				mFrm.setVisible(false);
			}

			@Override
			public void approveSelection() {
				try {

					File saveFile = new File(saveDialog.getSelectedFile().toPath() + "."
							+ ((FileNameExtensionFilter) saveDialog.getFileFilter()).getExtensions()[0]);
					if (saveFile.exists()) {
						int option = JOptionPane.showConfirmDialog(mFrm, "文件已存在,是否覆盖?");
						if (option != JOptionPane.OK_OPTION) {
							mFrm.setVisible(false);
							return;
						}
					}else {
						saveFile.createNewFile();
					}
					mFrm.saveBtnClick(saveFile);
					mFrm.setVisible(false);
				} catch (InvalidPathException e) {
					JOptionPane.showMessageDialog(mFrm, "文件格式不正确!!!");
					return;
				} catch (IOException e) {
					JOptionPane.showMessageDialog(mFrm, "文件创建失败!!!");
				}
			}

		};
		saveDialog.setDialogType(JFileChooser.SAVE_DIALOG);
		// 添加文件过滤器
		saveDialog.setAcceptAllFileFilterUsed(false);
		saveDialog.addChoosableFileFilter(dbnFilter);
		saveDialog.addChoosableFileFilter(txtFilter);
		// createDialog.setFileFilter(txtFilter);
		saveDialog.setFileFilter(dbnFilter);
		saveDialog.setVisible(true);
		saveDialog.setBounds(0, 30, mFrm.getWidth(), mFrm.getHeight() - 30);

		frmHead.setBounds(0, 0, mFrm.getWidth(), 30);
		mFrm.add(saveDialog);
		mFrm.add(frmHead);

	}

	private SaveFrm() {
	};

	public static void showFrm() {
		mFrm.setBounds(CreateFrm.getFrmBound());
		mFrm.setVisible(true);
		frmHead.setBounds(0, 0, mFrm.getWidth(), 30);
	}

	/**
	 * 无参txt格式保存 默认项目目录同级路径
	 * 
	 * public void saveAsTXT() { try { File fileData = new
	 * File(MainFrm.uriString + fileName.getText() +
	 * saveType.getSelectedItem().toString().substring(1)); if
	 * (!fileData.exists()) { fileData.createNewFile(); } FileOutputStream file
	 * = new FileOutputStream(fileData);
	 * 
	 * // 用字节流写出,暂时不能保存样式 OutputStreamWriter save = new
	 * OutputStreamWriter(file); save.write(TextArea.getJTP().getText());
	 * save.flush(); save.close(); } catch (FileNotFoundException e) { return; }
	 * catch (IOException e) { JOptionPane.showMessageDialog(mFrm,
	 * "文件保存出错!!!!!"); } }
	 * 
	 */

	/**
	 * 有参txt格式保存
	 * 
	 * @param filePath
	 *            保存路径
	 */
	public void saveAsTXT(String filePath) {
		try {
			File fileData = new File(filePath);
			if (!fileData.exists()) {
				fileData.createNewFile();
			}
			FileOutputStream file = new FileOutputStream(fileData);

			// 用字节流写出,暂时不能保存样式
			OutputStreamWriter save = new OutputStreamWriter(file);
			save.write(TextArea.getJTP().getText());
			save.flush();
			save.close();
		} catch (FileNotFoundException e) {
			return;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(mFrm, "文件保存出错!!!!!");
		}
	}

	/**
	 * 无参dbn格式保存 默认项目目录同级路径
	 * 
	 * public void saveAsDBN() { try { File fileData = new
	 * File(MainFrm.uriString + fileName.getText() +
	 * saveType.getSelectedItem().toString().substring(1)); if
	 * (!fileData.exists()) { fileData.createNewFile(); } FileOutputStream file
	 * = new FileOutputStream(fileData);
	 * 
	 * ObjectOutputStream objOut = new ObjectOutputStream(file);
	 * objOut.writeObject(TextArea.getJTP().getStyledDocument());
	 * objOut.flush(); objOut.close(); } catch (FileNotFoundException e) {
	 * return; } catch (IOException e) { JOptionPane.showMessageDialog(mFrm,
	 * "文件保存出错!!!!!"); } }
	 * 
	 */

	/**
	 * 有参dbn格式保存
	 * 
	 * @param filePath
	 *            保存路径
	 */
	public void saveAsDBN(String filePath) {
		try {
			File fileData = new File(filePath);
			if (!fileData.exists()) {
				fileData.createNewFile();
			}
			FileOutputStream file = new FileOutputStream(fileData);

			ObjectOutputStream objOut = new ObjectOutputStream(file);
			objOut.writeObject(TextArea.getJTP().getStyledDocument());
			objOut.flush();
			objOut.close();
		} catch (FileNotFoundException e) {
			return;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(mFrm, "文件保存出错!!!!!");
		}
	}

	public static SaveFrm getmFrm() {
		return mFrm;
	}

	public void saveBtnClick(File file) {
		String fileType = ((FileNameExtensionFilter) saveDialog.getFileFilter()).getExtensions()[0];
		if (fileType != null && !"".equals(fileType)) {
			switch (fileType) {
			case "txt":
				saveAsTXT(file.getPath());
				break;
			case "dbn":
				saveAsDBN(file.getPath());
				break;
			}
		} else {
			JOptionPane.showMessageDialog(mFrm, "不支持的文件保存格式");
		}
		
	}

}

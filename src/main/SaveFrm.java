package main;

import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import utils.FileReader;

public class SaveFrm extends JFrame {

	private static SaveFrm mFrm = new SaveFrm();
	private JButton save = new JButton("保存");
	private JButton cancel = new JButton("取消");
	private JPanel btnPanel = new JPanel();
	private JPanel saveInfoJPanel = new JPanel();
	private JComboBox<String> saveType = new JComboBox<String>();
	private JTextArea fileName = new JTextArea();

	private SaveFrm() {
		this.setUndecorated(true);
		this.setBounds(MainFrm.WIDTH / 3, MainFrm.HEIGHT / 3, MainFrm.WIDTH / 3, MainFrm.HEIGHT / 3);
		this.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				if (mFrm.isVisible()) {
					mFrm.requestFocus();
				}

			}

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub

			}
		});

		// 初始化各种控件样式
		save.setOpaque(true);
		cancel.setOpaque(true);
		fileName.setRows(1);
		fileName.setBounds(this.getWidth() / 20 * 3, this.getHeight() / 6 * 2, this.getWidth() / 20 * 14,
				MainFrm.getjComboBox().getHeight());
		saveType.setBounds(this.getWidth() / 20 * 6, this.getHeight() / 2, this.getWidth() / 20 * 8,
				MainFrm.getjComboBox().getHeight());
		saveType.addItem("*.txt");
		saveType.addItem("*.dbn");
		// 添加监听器
		save.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String selectedItem = (String) saveType.getSelectedItem();
				String text = fileName.getText();
				if (text != ""  && text.matches("\\w*")) {

					switch (selectedItem) {
					case "*.txt":
						saveAsTXT();
						break;
					case "*.dbn":
						saveAsDBN();
						break;
					}
					try {
						MainFrm.getHead().clear();
						Vector<File> vFiles = FileReader.getFiles(MainFrm.getjComboBox().getSelectedItem().toString().substring(1));
						for (File exist : vFiles) {
							MainFrm.getHead().add(MainFrm.getHead().getSize(), exist.getName());
						}
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
					closeFrm();
				}else {
					JOptionPane.showMessageDialog(mFrm, "文件名格式不正确的!!!!!");
				}
				
			}
		});
		cancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mFrm.closeFrm();
			}
		});
		// 初始化布局
		this.setLayout(null);
		btnPanel.setLayout(new GridLayout(1, 2));
		saveInfoJPanel.setLayout(null);
		saveInfoJPanel.setBounds(0, 0, this.getWidth(), this.getHeight() / 10 * 9);
		saveInfoJPanel.add(fileName);
		saveInfoJPanel.add(saveType);
		btnPanel.setBounds(0, saveInfoJPanel.getHeight(), this.getWidth(), this.getHeight() / 10);
		btnPanel.add(save);
		btnPanel.add(cancel);
		this.add(saveInfoJPanel);
		this.add(btnPanel);
	};

	public static void showFrm() {

		mFrm.setVisible(true);
	}

	public static void closeFrm() {
		mFrm.setVisible(false);
	}

	public void saveAsTXT() {
		try {
			File fileData = new File(MainFrm.uriString + fileName.getText() + saveType.getSelectedItem().toString().substring(1));
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

	public void saveAsDBN() {
		try {
			File fileData = new File(MainFrm.uriString + fileName.getText() + saveType.getSelectedItem().toString().substring(1));
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
}

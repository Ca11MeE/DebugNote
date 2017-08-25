package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import app.DebugNote;
import utils.FileReader;
import utils.ListSort;

public class MainFrm extends JPanel {

	

	// 静态私有变量
	// private static DefaultListModel<String> head = new
	// DefaultListModel<String>();
	private static JList<String> headList = null;
	private static JList<String> fullPathList = null;
	private static JScrollPane leftPane = null;
	 
	private static JComboBox<String> jComboBox = new JComboBox<String>();
	
	private static JLabel hideJLabel = new JLabel("<");
	private static JLabel showJLabel = new JLabel(">");
	
	

	

	// 静态公开变量
	public static String uriString = System.getProperty("user.dir").substring(0,
			System.getProperty("user.dir").lastIndexOf("\\") + 1);
	public static boolean fullScrean = false;

	

	/*
	 * 初始化文件列表和读取对应后缀文件并列出来
	 */
	static {
		// headList = new JList<String>(head);
		// 下一行代码留待以后读取上次保存的已打开文件列表
		fullPathList = new JList<String>();

		headList = new JList<String>(ListSort.fileTypeFilter(fullPathList));
		leftPane = new JScrollPane(headList, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		// headList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// 批量读取文件夹中文件并放入
		// try {
		// Vector<File> vFiles = FileReader.getFiles();
		// for (File file : vFiles) {
		// head.add(head.getSize(), file.getName());
		// }
		// } catch (FileNotFoundException e) {
		// e.printStackTrace();
		// }

	}

	/**
	 * 获取文件后缀名下拉框对象
	 * 
	 * @return 下拉框对象
	 */
	public static JComboBox<String> getjComboBox() {
		return jComboBox;
	}

	

	// 添加窗体移动方法
	public static void frmMove(Point point) {
		DebugNote.getmFrm().setLocation(point);
	}

	/**
	 * 构造函数
	 */
	public MainFrm() {
		// 初始化隐藏文件栏标签标签
		hideJLabel.setBackground(Color.red);
		hideJLabel.setOpaque(true);
		// 初始化显示文件栏标签标签
		showJLabel.setBackground(Color.red);
		showJLabel.setOpaque(true);
		// 初始化下拉框元素
		jComboBox.addItem("*.*");
		jComboBox.addItem("*.txt");
		// jComboBox.addItem("*.exe");
		jComboBox.addItem("*.html");
		jComboBox.addItem("*.xml");
		// jComboBox.addItem("*.class");
		// jComboBox.addItem("*.css");
		// jComboBox.addItem("*.java");
		jComboBox.addItem("*.dbn");

		// 添加下拉框选择监听器
		jComboBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// String endName = e.getItem().toString().substring(1);
				// fileTypeString = endName;
					updateHeadList();
				// 批量读取文件夹中文件
				// try {
				// head.clear();
				// Vector<File> vFiles = FileReader.getFiles(endName);
				// for (File file : vFiles) {
				// head.add(head.getSize(), file.getName());
				// }
				// } catch (FileNotFoundException ex) {
				// ex.printStackTrace();
				// }

			}
		});
		// 添加文件列表选择监听器
		headList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				String uri = ListSort.findPathInPathList(headList.getSelectedValue());
				if (uri == null) {
					return;
				}
				if (headList.getSelectedValue() == null) {
					return;
				}
				FileReader.getData(new File(uri), DebugNote.getText());
				DebugNote.getHeadPane().setLblText(uri);
			}
		});
		// 添加面板隐藏监听器
		hideJLabel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				Component[] components = e.getComponent().getParent().getComponents();
				for (Component component : components) {

					component.setVisible(false);
				}
				showJLabel.setVisible(true);
				e.getComponent().getParent().add(showJLabel, BorderLayout.EAST);
				e.getComponent().getParent().remove(hideJLabel);
			}

		});
		// 添加面板显示监听器
		showJLabel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				Component[] components = e.getComponent().getParent().getComponents();
				for (Component component : components) {
					component.setVisible(true);
				}
				hideJLabel.setVisible(true);
				e.getComponent().getParent().add(hideJLabel, BorderLayout.EAST);
				e.getComponent().getParent().remove(showJLabel);
			}

		});

		this.setLayout(new BorderLayout());
		this.add(jComboBox, BorderLayout.NORTH);
		this.add(leftPane, BorderLayout.CENTER);
		this.add(showJLabel, BorderLayout.EAST);
		this.add(hideJLabel, BorderLayout.EAST);

		/**
		 * 2017-08-17 17:31 遗留任务 实现添加文件夹选择
		 */
		// JFileChooser jFC = new JFileChooser(){
		//
		// @Override
		// public void approveSelection() {
		// System.out.println(this.getSelectedFile());
		// }
		//
		// };
		// jFC.setControlButtonsAreShown(false);
		// jFC.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		// jFC.setFileSystemView(new FileSystemView() {
		//
		// @Override
		// public File createNewFolder(File containingDir) throws IOException {
		// // TODO Auto-generated method stub
		// return null;
		// }
		// });
		// this.add(jFC,BorderLayout.SOUTH);

		// 添加菜单栏
		headList.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					menu.view.MainMenu.getMainFrmMenu().setLocation((int) e.getLocationOnScreen().getX(),
							(int) e.getLocationOnScreen().getY());

					menu.view.MainMenu.getMainFrmMenu().setVisible(true);
				} else {
					menu.view.MainMenu.getMainFrmMenu().setVisible(false);
				}
			}

		});
		
	}

	

	public static JScrollPane getLeftPane() {
		return leftPane;
	}

	public static void updateFullPathList() {
		fullPathList.setModel(ListSort.getHead());
	}

	public static void updateHeadList() {
		headList.setModel(ListSort.fileTypeFilter(fullPathList));
		headList.updateUI();
	}

	
}

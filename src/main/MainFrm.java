package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.MenuBar;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileSystemView;

import org.junit.Test;

import com.sun.java.swing.plaf.windows.DesktopProperty;
import com.sun.media.sound.SF2GlobalRegion;

import javafx.scene.input.MouseButton;
import menu.file.MainMenu;
import scr.JavaScr;
import utils.FileReader;
import utils.ListSort;

public class MainFrm extends JPanel {

	// 常量
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 800;

	// 静态私有变量
	// private static DefaultListModel<String> head = new
	// DefaultListModel<String>();
	private static JList<String> headList = null;
	private static JList<String> fullPathList = null;
	private static JScrollPane leftPane = null;
	private static TextArea text = new TextArea();
	private static JComboBox<String> jComboBox = new JComboBox<String>();
	private static Head headPane = new Head();
	private static JLabel hideJLabel = new JLabel("<");
	private static JLabel showJLabel = new JLabel(">");

	// 样式窗口
	private static StyleForm sf;

	// 静态公开变量
	public static String uriString = System.getProperty("user.dir").substring(0,
			System.getProperty("user.dir").lastIndexOf("\\") + 1);
	public static String fileTypeString;
	public static boolean fullScrean = false;

	// 程序主界面
	private static JFrame mFrm = new JFrame("DebugNote");

	/*
	 * 初始化文件列表和读取对应后缀文件并列出来
	 */
	static {
		// headList = new JList<String>(head);
		//下一行代码留待以后读取上次保存的已打开文件列表
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

	/**
	 * 获取程序主窗口
	 * 
	 * @return 程序主窗口
	 */
	public static JFrame getmFrm() {
		return mFrm;
	}

	// 添加窗体移动方法
	public static void frmMove(Point point) {
		mFrm.setLocation(point);
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
		jComboBox.addItem("*.txt");
		// jComboBox.addItem("*.exe");
		jComboBox.addItem("*.html");
		jComboBox.addItem("*.xml");
		// jComboBox.addItem("*.class");
		// jComboBox.addItem("*.css");
		// jComboBox.addItem("*.java");
		jComboBox.addItem("*.dbn");

		fileTypeString = (String) jComboBox.getSelectedItem();
		// 添加下拉框选择监听器
		jComboBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
//				String endName = e.getItem().toString().substring(1);
//				fileTypeString = endName;
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
				String uri = "";

				if (headList.getSelectedValue() == null) {
					return;
				}

				if (headList.getSelectedValue().endsWith(".txt")) {

					uri = uriString + headList.getSelectedValue();
					FileReader.getData(new File(uri), text);

				}

				if (headList.getSelectedValue().endsWith(fileTypeString)) {

					uri = uriString + headList.getSelectedValue();
					FileReader.getData(new File(uri), text, fileTypeString);

				}

				MainFrm.getHeadPane().setLblText(uri);
			}
		});

		/**
		 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		 */
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

	public static void main(String[] args) throws Exception {

		MainFrm mf = new MainFrm();
		mFrm.setLayout(new BorderLayout());
		mFrm.setSize(WIDTH, HEIGHT);
		mFrm.setUndecorated(true);

		sf = new StyleForm();
		sf.setLocation(mFrm.getX(), mFrm.getY() + mFrm.getHeight());
		sf.setPreferredSize(new Dimension(WIDTH, HEIGHT / 20));
		sf.setVisible(true);

		MainFrm.getLeftPane().setPreferredSize(new Dimension(WIDTH / 6, HEIGHT / 20 * 18));

		mFrm.add(headPane, BorderLayout.NORTH);
		mFrm.add(sf, BorderLayout.SOUTH);
		mFrm.add(mf, BorderLayout.WEST);
		mFrm.add(text.getTextlist(), BorderLayout.CENTER);

		// 初始化输入法

		mFrm.addComponentListener(new ComponentListener() {

			@Override
			public void componentShown(ComponentEvent e) {
				MainFrm.getLeftPane().setPreferredSize(new Dimension(mFrm.getWidth() / 6, mFrm.getHeight() / 20 * 18));
			}

			@Override
			public void componentResized(ComponentEvent e) {
				MainFrm.getLeftPane().setPreferredSize(new Dimension(mFrm.getWidth() / 6, mFrm.getHeight() / 20 * 18));
			}

			@Override
			public void componentMoved(ComponentEvent e) {
				MainFrm.getLeftPane().setPreferredSize(new Dimension(mFrm.getWidth() / 6, mFrm.getHeight() / 20 * 18));
			}

			@Override
			public void componentHidden(ComponentEvent e) {
				MainFrm.getLeftPane().setPreferredSize(new Dimension(mFrm.getWidth() / 6, mFrm.getHeight() / 20 * 18));
			}
		});

		mFrm.setVisible(true);

	}

	public static JScrollPane getLeftPane() {
		return leftPane;
	}

	public static StyleForm getSf() {
		return sf;
	}

	public static TextArea getText() {
		return text;
	}

	public static Head getHeadPane() {
		return headPane;
	}

	public static void updateFullPathList() {
		fullPathList.setModel(ListSort.getHead());
	}
	
	public static void updateHeadList() {
		headList.setModel(ListSort.fileTypeFilter(fullPathList));
		headList.updateUI();
	}
}

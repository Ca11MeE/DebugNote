package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.annotation.Target;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.junit.Test;

import com.sun.java.swing.plaf.windows.DesktopProperty;
import com.sun.media.sound.SF2GlobalRegion;

import scr.JavaScr;
import utils.FileReader;

public class MainFrm extends JPanel {
	
	//常量
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 800;
	
	//静态私有变量
	private static DefaultListModel<String> head = new DefaultListModel<String>();
	private static JList<String> headList = null;
	private static JScrollPane headPane = null;
	private static TextArea text = new TextArea();
	private static JComboBox<String> jComboBox=new JComboBox<String>();
	
	//静态公开变量
	public static String uriString=System.getProperty("user.dir").substring(0,System.getProperty("user.dir").indexOf("DebugNote"));
	public static String fileTypeString;
	public static boolean fullScrean=false;

	//程序主界面
	private static JFrame mFrm = new JFrame("DebugNote");
	
	/*
	 * 初始化文件列表和读取对应后缀文件并列出来
	 */
	static {
		headList = new JList<String>(head);
		headPane = new JScrollPane(headList, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		//headList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		try {
			Vector<File> vFiles=FileReader.getFiles();
			for (File file : vFiles) {
				head.add(head.getSize(), file.getName());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}

	
	public static DefaultListModel<String> getHead() {
		return head;
	}

	public static void setHead(DefaultListModel<String> head) {
		MainFrm.head = head;
	}

	/**
	 * 获取文件后缀名下拉框对象
	 * @return 下拉框对象
	 */
	public static JComboBox<String> getjComboBox() {
		return jComboBox;
	}

	/**
	 * 获取程序主窗口
	 * @return 程序主窗口
	 */
	public static JFrame getmFrm() {
		return mFrm;
	}

	//添加窗体移动方法
	public static void frmMove(Point point){
		mFrm.setLocation(point);
	}

	/**
	 * 构造函数
	 */
	public MainFrm() {
		//初始化下拉框元素
		jComboBox.addItem("*.txt");
		jComboBox.addItem("*.exe");
		jComboBox.addItem("*.html");
		jComboBox.addItem("*.class");
		jComboBox.addItem("*.css");
		jComboBox.addItem("*.java");
		jComboBox.addItem("*.dbn");
		
		fileTypeString=(String) jComboBox.getSelectedItem();
		//添加下拉框选择监听器
		jComboBox.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				String endName = e.getItem().toString().substring(1);
				fileTypeString=endName;
				try {
					head.clear();
					Vector<File> vFiles=FileReader.getFiles(endName);
					for (File file : vFiles) {
						head.add(head.getSize(), file.getName());
					}
				} catch (FileNotFoundException ex) {
					ex.printStackTrace();
				}
					
			}
		});
		//添加文件列表选择监听器
		headList.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				String uri;
				
				if (headList.getSelectedValue()==null) {
					return;
				}
				
				if (headList.getSelectedValue().endsWith(".txt")) {
					
					uri=uriString+headList.getSelectedValue();
					FileReader.getData(new File(uri), text);
					
				}
				
				if (headList.getSelectedValue().endsWith(fileTypeString)) {
					
					uri=uriString+headList.getSelectedValue();
					FileReader.getData(new File(uri), text,fileTypeString);
					
				}
			}
		});
		this.setLayout(new BorderLayout());
		this.add(jComboBox,BorderLayout.NORTH);
		this.add(headPane,BorderLayout.CENTER);
		
		
		
	}

	public static void main(String[] args) throws Exception {
		
		MainFrm mf = new MainFrm();
		mFrm.setLayout(new BorderLayout());
		mFrm.setSize(WIDTH, HEIGHT);
		mFrm.setUndecorated(true);
		
		
		StyleForm sf=new StyleForm();
		sf.setLocation(mFrm.getX(), mFrm.getY()+mFrm.getHeight());
		sf.setPreferredSize(new Dimension(WIDTH,HEIGHT/20));
		sf.setVisible(true);
		
		Head headPane=new Head();
		
		MainFrm.getHeadPane().setPreferredSize(new Dimension(WIDTH/6,HEIGHT/20*18));
		
		mFrm.add(headPane,BorderLayout.NORTH);
		mFrm.add(sf,BorderLayout.SOUTH);
		mFrm.add(mf,BorderLayout.WEST);
		mFrm.add(text.getTextlist(),BorderLayout.CENTER);
		
		
		//初始化输入法
		
		
		mFrm.addComponentListener(new ComponentListener() {
			
			@Override
			public void componentShown(ComponentEvent e) {
				MainFrm.getHeadPane().setPreferredSize(new Dimension(mFrm.getWidth()/6,mFrm.getHeight()/20*18));
			}
			
			@Override
			public void componentResized(ComponentEvent e) {
				MainFrm.getHeadPane().setPreferredSize(new Dimension(mFrm.getWidth()/6,mFrm.getHeight()/20*18));
			}
			
			@Override
			public void componentMoved(ComponentEvent e) {
				MainFrm.getHeadPane().setPreferredSize(new Dimension(mFrm.getWidth()/6,mFrm.getHeight()/20*18));
			}
			
			@Override
			public void componentHidden(ComponentEvent e) {
				MainFrm.getHeadPane().setPreferredSize(new Dimension(mFrm.getWidth()/6,mFrm.getHeight()/20*18));
			}
		});
		
		mFrm.setVisible(true);
		
		
	}
	
	
	
	public static JScrollPane getHeadPane() {
		return headPane;
	}

	

	@Test
	public void test(){
		System.out.println();
	}
}

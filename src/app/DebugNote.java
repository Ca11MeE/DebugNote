package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import main.Head;
import main.MainFrm;
import main.StyleForm;
import main.TextArea;
import main.TopPanel;

public class DebugNote {

	// 常量
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 800;
	// 窗口边框判断常量
	public static final int FRM_RECT_INNER = 0;
	public static final int FRM_RECT_LEFT = 1;
	public static final int FRM_RECT_BOTTOM = 2;
	public static final int FRM_RECT_RIGHT = 3;
	public static final int FRM_RECT_TOP = 4;

	// 程序窗口
	private static JFrame app = new JFrame("DebugNote");
	// 程序主界面
	private static JPanel mFrm = new JPanel();
	/*
	 * 0 -> 不改变(不在判断区域) 1 -> 左端改变 2 -> 下端改变 3 -> 右端改变 4 -> 上端改变
	 */
	private static int frmSizeChangeType = FRM_RECT_INNER;
	// 样式窗口
	private static StyleForm sf;
	private static Head headPane = new Head();
	private static TextArea text = new TextArea();
	private static MainFrm mf = new MainFrm();

	// 监听器
	private static MouseAdapter frmSizeAdapter = new MouseAdapter() {
		@Override
		public void mouseMoved(MouseEvent e) {
			
			int chkResult = chkRectType(e.getLocationOnScreen());
			switch (chkResult) {
			case FRM_RECT_LEFT:
				app.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
				break;
			case FRM_RECT_BOTTOM:
				app.setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
				break;
			case FRM_RECT_RIGHT:
				app.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
				break;
			case FRM_RECT_TOP:
				app.setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
				break;

			default:
				app.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				break;
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			app.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		
		
	};
	

	public static void main(String[] args) {

		mFrm.setLayout(new BorderLayout());
		app.setSize(WIDTH, HEIGHT);
		app.setUndecorated(true);

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

		// 添加窗口监听器
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

		// 添加窗口调整大小触发器

		mFrm.addMouseMotionListener(DebugNote.getFrmSizeAdapter());
		headPane.getTitle().addMouseMotionListener(DebugNote.getFrmSizeAdapter());
		mf.getLeftPane().getViewport().getView().addMouseMotionListener(DebugNote.getFrmSizeAdapter());
		mf.getjComboBox().addMouseMotionListener(DebugNote.getFrmSizeAdapter());
		text.getTextlist().addMouseMotionListener(DebugNote.getFrmSizeAdapter());
		app.add(mFrm);
		app.setVisible(true);

	}

	private static int chkRectType(Point mouseLoc) {
		Point frmPoint = mFrm.getLocationOnScreen();
		Dimension size = mFrm.getSize();
		if (mouseLoc.x >= frmPoint.x && mouseLoc.x <= frmPoint.x + 5) {
			return FRM_RECT_LEFT;
		}
		if (mouseLoc.x >= frmPoint.x + size.width - 5 && mouseLoc.x <= frmPoint.x + size.width) {
			return FRM_RECT_RIGHT;
		}
		if (mouseLoc.y >= frmPoint.y && mouseLoc.y <= frmPoint.y + 5) {
			return FRM_RECT_TOP;
		}
		if (mouseLoc.y >= frmPoint.y + size.height - 5 && mouseLoc.y <= frmPoint.y + size.height) {
			return FRM_RECT_BOTTOM;
		}
		return FRM_RECT_INNER;
	}

	/**
	 * 获取程序主窗口
	 * 
	 * @return 程序主窗口
	 */
	public static JFrame getmFrm() {
		return app;
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

	public static MouseAdapter getFrmSizeAdapter() {
		return frmSizeAdapter;
	}

	
}

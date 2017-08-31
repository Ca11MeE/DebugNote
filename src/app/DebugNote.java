package app;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import main.left.FilePane;
import main.head.Head;
import main.left.LeftPane;
import main.StyleForm;
import main.TextArea;

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
	// 鼠标按下时的位置记录
	private static Point mouseLocBefore;
	private static int frmSizeWidthBefore;
	private static int frmSizeHeightBefore;
	private static boolean reSizeOn = false;
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
	private static LeftPane lf = new LeftPane(JTabbedPane.TOP);

	// 监听器
	private static MouseAdapter frmSizeAdapter4Mouse = new MouseAdapter() {
		@Override
		public void mousePressed(MouseEvent e) {
			mouseLocBefore = e.getLocationOnScreen();
			frmSizeWidthBefore = DebugNote.getmFrm().getWidth();
			frmSizeHeightBefore = DebugNote.getmFrm().getHeight();
			if (frmSizeChangeType != DebugNote.FRM_RECT_INNER) {
				reSizeOn = true;
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			reSizeOn = false;
		}

	};
	private static MouseAdapter frmSizeAdapter = new MouseAdapter() {
		@Override
		public void mouseMoved(MouseEvent e) {
			if (!reSizeOn) {
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
				frmSizeChangeType = chkResult;
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			app.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (reSizeOn) {

				Point mouseLoc = e.getLocationOnScreen();
				Point frmLoc = DebugNote.getmFrm().getLocationOnScreen();
				switch (frmSizeChangeType) {
				case FRM_RECT_LEFT:
					// 左
					DebugNote.getmFrm().setBounds((int) mouseLoc.getX(), (int) frmLoc.getY(),
							DebugNote.getFrmSizeWidthBefore() - (int) (mouseLoc.getX() - mouseLocBefore.getX()),
							DebugNote.getFrmSizeHeightBefore());
					break;
				case FRM_RECT_BOTTOM:
					// 下
					DebugNote.getmFrm().setLocation(frmLoc);
					DebugNote.getmFrm().setSize(
							DebugNote.getFrmSizeWidthBefore(),
							DebugNote.getFrmSizeHeightBefore()+(int)(mouseLoc.getY()-mouseLocBefore.getY()));
					break;
				case FRM_RECT_RIGHT:
					//右
					DebugNote.getmFrm().setLocation(frmLoc);
					DebugNote.getmFrm().setSize(
							DebugNote.getFrmSizeWidthBefore() + (int) (mouseLoc.getX() - mouseLocBefore.getX()),
							DebugNote.getFrmSizeHeightBefore());
					break;
				case FRM_RECT_TOP:
					// 上
					DebugNote.getmFrm().setBounds((int) frmLoc.getX(), (int) mouseLoc.getY(),
							DebugNote.getFrmSizeWidthBefore(),
							DebugNote.getFrmSizeHeightBefore()-(int)(mouseLoc.getY()-mouseLocBefore.getY()));
					break;

				default:
					app.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					break;
				}
			}
		}

	};

	public static void main(String[] args) {

		mFrm.setLayout(new BorderLayout());
		app.setSize(WIDTH, HEIGHT);
		app.setUndecorated(true);

		sf = new StyleForm();
		sf.setLocation(mFrm.getX(), mFrm.getY() + mFrm.getHeight());
		sf.setPreferredSize(new Dimension(WIDTH, HEIGHT / 20));
		sf.setVisible(false);

		lf.getFileList().setPreferredSize(new Dimension(WIDTH / 6, HEIGHT / 20 * 18));

		mFrm.add(headPane, BorderLayout.NORTH);
		mFrm.add(sf, BorderLayout.SOUTH);
		mFrm.add(lf, BorderLayout.WEST);
		mFrm.add(text.getTextlist(), BorderLayout.CENTER);

		// 初始化输入法

		// 添加窗口监听器
		mFrm.addComponentListener(new ComponentListener() {

			@Override
			public void componentShown(ComponentEvent e) {
				lf.getFileList().setPreferredSize(new Dimension(mFrm.getWidth() / 6, mFrm.getHeight() / 20 * 18));
				
				DebugNote.getSf().reSize();
			}

			@Override
			public void componentResized(ComponentEvent e) {
				lf.getFileList().setPreferredSize(new Dimension(mFrm.getWidth() / 6, mFrm.getHeight() / 20 * 18));
				sf.setPreferredSize(new Dimension(mFrm.getWidth(), mFrm.getHeight() / 20 ));
				
				DebugNote.getSf().reSize();
			}

			@Override
			public void componentMoved(ComponentEvent e) {
				lf.getFileList().setPreferredSize(new Dimension(mFrm.getWidth() / 6, mFrm.getHeight() / 20 * 18));
				
				DebugNote.getSf().reSize();
			}

			@Override
			public void componentHidden(ComponentEvent e) {
				lf.getFileList().setPreferredSize(new Dimension(mFrm.getWidth() / 6, mFrm.getHeight() / 20 * 18));
				
				DebugNote.getSf().reSize();
			}
		});

		// 添加窗口调整大小触发器
		mFrm.addMouseMotionListener(DebugNote.getFrmSizeAdapter());
		headPane.getTitle().addMouseMotionListener(DebugNote.getFrmSizeAdapter());
		lf.getFileList().getLeftPane().getViewport().getView().addMouseMotionListener(DebugNote.getFrmSizeAdapter());
		lf.getFileList().getjComboBox().addMouseMotionListener(DebugNote.getFrmSizeAdapter());
		lf.getShowJLabel().addMouseMotionListener(DebugNote.getFrmSizeAdapter());
		text.getTextlist().addMouseMotionListener(DebugNote.getFrmSizeAdapter());
		//--------------------------------------------------------------------------------------------------
		mFrm.addMouseListener(DebugNote.getFrmSizeAdapter4Mouse());
		headPane.getTitle().addMouseListener(DebugNote.getFrmSizeAdapter4Mouse());
		lf.getFileList().getLeftPane().getViewport().getView().addMouseListener(DebugNote.getFrmSizeAdapter4Mouse());
		lf.getFileList().getjComboBox().addMouseListener(DebugNote.getFrmSizeAdapter4Mouse());
		lf.getShowJLabel().addMouseListener(DebugNote.getFrmSizeAdapter4Mouse());
		text.getTextlist().addMouseListener(DebugNote.getFrmSizeAdapter4Mouse());
		//--------------------------------------------------------------------------------------------------
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

	public static MouseAdapter getFrmSizeAdapter4Mouse() {
		return frmSizeAdapter4Mouse;
	}

	public static int getFrmSizeWidthBefore() {
		return frmSizeWidthBefore;
	}

	public static int getFrmSizeHeightBefore() {
		return frmSizeHeightBefore;
	}

}

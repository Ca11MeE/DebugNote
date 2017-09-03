package main.head;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SpringLayout.Constraints;

import main.TextArea;
import sun.net.www.content.text.plain;

public class FrmHead extends JPanel {
	private JFrame call;
	private JButton closeButton = new JButton("X");
	private JButton saveButton = new JButton("保存");
	private JLabel title = new JLabel();
	private Point point = new Point();
	private Point mFrmLoc;
	private Point pOnS;
	// 鼠标和窗体原点横向距离
	private double wid;
	// 鼠标和窗体原点竖向距离
	private double hei;

	public FrmHead(final JFrame call) {
		init(call);
	}

	public FrmHead(final JFrame call,String title) {
		this.title=new JLabel(title);
		init(call);
	}
	
	public void init(final JFrame call) {
		this.call = call;
		GridBagLayout gridBagLayout = new GridBagLayout();
		// 从右往左添加控件
		// this.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		this.setLayout(gridBagLayout);
//		this.setLayout(null);
		GridBagConstraints c = new GridBagConstraints();

		// 设置按钮样式
		closeButton.setBackground(Color.GRAY);
		closeButton.setPreferredSize(new Dimension(call.getWidth() / 8, 30));
		saveButton.setBackground(Color.GRAY);
		//saveButton.setPreferredSize(new Dimension(call.getWidth() / 8, 30));
		// 设置容器样式
		this.setBackground(Color.red);

		title.setOpaque(true);
		title.setBackground(Color.RED);
		title.setBounds(0,0,call.getWidth(),30);
		title.setHorizontalAlignment(SwingConstants.CENTER);

		c.weightx = 20;
		c.fill = GridBagConstraints.BOTH;
		gridBagLayout.setConstraints(title, c);
		this.add(title);
		//c.weightx = 1;
		//gridBagLayout.setConstraints(saveButton, c);
		// this.add(saveButton);
		c.weightx = 1;
		gridBagLayout.setConstraints(closeButton, c);
		this.add(closeButton);

		// 添加鼠标拖动事件
		title.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				mFrmLoc = call.getLocation();

				pOnS = e.getLocationOnScreen();

				wid = pOnS.getX() - mFrmLoc.getX();
				hei = pOnS.getY() - mFrmLoc.getY();
			}

		});
		title.addMouseMotionListener(new MouseMotionAdapter() {

			public void mouseDragged(MouseEvent e) {
				pOnS = e.getLocationOnScreen();
				point.setLocation((pOnS.getX() - wid), (pOnS.getY() - hei));
				call.setLocation(point);
			}

		});

		saveButton.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				//
				TextArea.saveText();
				// System.out.println("保存!!!!!");
				super.mouseClicked(e);
			}

		});

		closeButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				call.setVisible(false);
			}
		});

		call.add(this,BorderLayout.NORTH);
	}
	
}

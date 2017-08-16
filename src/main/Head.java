package main;

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
import javax.swing.SpringLayout.Constraints;

import sun.net.www.content.text.plain;

public class Head extends JPanel {
	private static JButton closeButton = new JButton("关闭");
	private static JButton saveButton = new JButton("保存");
	private static JLabel title = new JLabel();
	private Point point = new Point();
	private Point mFrmLoc;
	private Point pOnS;
	//鼠标和窗体原点横向距离
	private double wid;
	//鼠标和窗体原点竖向距离
	private double hei;


	public Head() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		//从右往左添加控件
		// this.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		this.setLayout(gridBagLayout);

		GridBagConstraints c = new GridBagConstraints();

		// 设置按钮样式
		closeButton.setBackground(Color.GRAY);
		saveButton.setBackground(Color.GRAY);
		// 设置容器样式
		// this.setBackground(Color.red);

		title.setOpaque(true);
		title.setBackground(Color.RED);

		c.weightx = 20;
		c.fill = GridBagConstraints.BOTH;
		gridBagLayout.setConstraints(title, c);
		this.add(title);
		c.weightx = 1;
		gridBagLayout.setConstraints(saveButton, c);
		this.add(saveButton);
		c.weightx = 1;
		gridBagLayout.setConstraints(closeButton, c);
		this.add(closeButton);


		// 添加鼠标拖动事件
		title.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				mFrmLoc = MainFrm.getmFrm().getLocation();
				pOnS = e.getLocationOnScreen();

				wid = pOnS.getX() - mFrmLoc.getX();
				hei = pOnS.getY() - mFrmLoc.getY();
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				GraphicsConfiguration[] configurations = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0]
						.getConfigurations();
				Rectangle bounds = configurations[0].getBounds();
				if (e.getClickCount() == 2) {
					if (MainFrm.fullScrean) {
						MainFrm.getmFrm().setSize(MainFrm.WIDTH, MainFrm.HEIGHT);
						MainFrm.fullScrean = false;
					} else {
						MainFrm.getmFrm().setBounds(bounds);
						MainFrm.fullScrean =true;

					}
				}

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				GraphicsConfiguration[] configurations = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0]
						.getConfigurations();
				Rectangle bounds = configurations[0].getBounds();
				JFrame getmFrm = MainFrm.getmFrm();
				if (getmFrm.getLocation().getX()<0 && e.getLocationOnScreen().getX()<1) {
					getmFrm.setBounds(0, 0, (int)bounds.getWidth()/2, (int)bounds.getHeight());
				}
				if (getmFrm.getLocation().getX()>(bounds.getWidth()-getmFrm.getWidth()) && e.getLocationOnScreen().getX()>(bounds.getWidth()-5)) {
					getmFrm.setBounds((int)bounds.getWidth()/2, 0, (int)bounds.getWidth()/2, (int)bounds.getHeight());
				}
				if (getmFrm.getLocation().getY()<0 && e.getLocationOnScreen().getY()<2) {
					MainFrm.getmFrm().setBounds(bounds);
					MainFrm.fullScrean =true;
				}
			}
			
			

		});
		title.addMouseMotionListener(new MouseMotionAdapter() {

			public void mouseDragged(MouseEvent e) {
				pOnS = e.getLocationOnScreen();
				point.setLocation((pOnS.getX() - wid), (pOnS.getY() - hei));
				MainFrm.getmFrm().setLocation(point);
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
				System.exit(0);
			}
		});
	}

}

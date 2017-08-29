package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class LeftPane extends JPanel {

	private static FilePane fileList = new FilePane();
	private static JTabbedPane tabs;
	private static JLabel hideJLabel = new JLabel("<");
	private static JLabel showJLabel = new JLabel(">");
	private static JPanel

	public LeftPane(int tabPosition) {
		tabs = new JTabbedPane(tabPosition);
		tabs.addTab("文件", fileList);
		
		// 初始化隐藏文件栏标签标签
		hideJLabel.setBackground(Color.red);
		hideJLabel.setOpaque(true);
		// 初始化显示文件栏标签标签
		showJLabel.setBackground(Color.red);
		showJLabel.setOpaque(true);

		// 添加面板隐藏监听器
		hideJLabel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				Component[] components = e.getComponent().getParent().getComponents();
				for (Component component : components) {

					component.setVisible(false);
				}
				tabs.setVisible(false);
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
				tabs.setVisible(true);
				hideJLabel.setVisible(true);
				e.getComponent().getParent().add(hideJLabel, BorderLayout.EAST);
				e.getComponent().getParent().remove(showJLabel);
			}

		});
		this.setLayout(new BorderLayout());
		this.add(tabs, BorderLayout.CENTER);
		this.add(showJLabel, BorderLayout.EAST);
		this.add(hideJLabel, BorderLayout.EAST);
	}

	public static FilePane getFileList() {
		return fileList;
	}

}

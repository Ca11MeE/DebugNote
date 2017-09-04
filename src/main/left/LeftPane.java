package main.left;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import main.TextArea;
import utils.StyleInitor;

public class LeftPane extends JPanel {

	// 文件栏
	private static FilePane fileList = new FilePane();
	private static FontPane fontSet = new FontPane();
	// 分栏窗口
	private static JTabbedPane tabs;
	// 面板隐藏按钮
	private static JLabel hideJLabel = new JLabel("<");
	// 面板显示按钮
	private static JLabel showJLabel = new JLabel(">");
	// 分栏面板
	private static JPanel filePanel = new JPanel();
	// 准备字体设置不可用界面
	private static JLabel unable = new JLabel("<html>带样式文档下字体设置不可用<br/>修改样式文档的样式请到<br/>"
			+ StyleInitor.getStyconfFile().getPath() + "下修改<br/>或右键输入样式打开文件</html>");

	public LeftPane(int tabPosition) {

		tabs = new JTabbedPane(tabPosition);
		tabs.addTab("文件", fileList);
		tabs.addTab("字体", fontSet);
		tabs.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (1 == ((JTabbedPane) e.getSource()).getSelectedIndex()) {
					if (0 == TextArea.getEditState()) {
						((JTabbedPane) e.getSource()).setComponentAt(1, fontSet);
					} else {
						((JTabbedPane) e.getSource()).setComponentAt(1, unable);
					}
				}
			}

		});

		filePanel.setLayout(new BorderLayout());
		filePanel.add(tabs, BorderLayout.CENTER);

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
				LeftPane parent = (LeftPane) e.getComponent().getParent();
				filePanel.setVisible(false);
				showJLabel.setVisible(true);
				parent.add(showJLabel, BorderLayout.EAST);
				parent.remove(hideJLabel);
				parent.updateUI();
			}

		});
		// 添加面板显示监听器
		showJLabel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				LeftPane parent = (LeftPane) e.getComponent().getParent();
				filePanel.setVisible(true);
				hideJLabel.setVisible(true);
				parent.add(hideJLabel, BorderLayout.EAST);
				parent.remove(showJLabel);
				parent.updateUI();
			}

		});
		this.setOpaque(true);
		this.setBackground(Color.black);
		this.setLayout(new BorderLayout());
		this.add(filePanel, BorderLayout.CENTER);
		this.add(showJLabel, BorderLayout.EAST);
		this.add(hideJLabel, BorderLayout.EAST);
	}

	public static FilePane getFileList() {
		return fileList;
	}

	public static JLabel getShowJLabel() {
		return showJLabel;
	}

	public static FontPane getFontSet() {
		return fontSet;
	}

}

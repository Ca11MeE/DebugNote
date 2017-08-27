package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import org.junit.Test;

import app.DebugNote;
import menu.style.Opt4OpenStyXML;
import utils.StyleInitor;

public class StyleForm extends JPanel {

	private static JLabel inputStyle = new JLabel("输入样式", JLabel.CENTER);
	private static JLabel inputBuffer = new JLabel("", JLabel.CENTER);
	private static List<JLabel> styles;
	private static JPanel stylePanel = new JPanel();
	private static boolean styleListShow = false;
	private static SimpleAttributeSet defaultStyle = new SimpleAttributeSet();

	public StyleForm() {
		this.setPreferredSize(new Dimension(MainFrm.WIDTH, MainFrm.HEIGHT / 20*3));
		this.setLayout(new GridLayout(1, 5));
		init();
	}

	public void init() {
		this.setLayout(null);
		inputStyle.setOpaque(true);
		inputStyle.setBackground(Color.white);
		inputStyle.setForeground(Color.black);
		inputStyle.setBounds(DebugNote.getmFrm().getWidth() / 20 * 17, 0, DebugNote.getmFrm().getWidth() / 20 * 3,
				MainFrm.HEIGHT / 20);
		this.add(inputStyle);
		initStyle();
		stylePanel.setLayout(new GridLayout(5, 5));
		stylePanel.setBounds(0, 0, DebugNote.getmFrm().getWidth() / 20 * 17, DebugNote.getmFrm().getHeight() / 20);
		this.add(stylePanel);
		this.updateUI();
		inputStyle.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				switch (e.getButton()) {
				case MouseEvent.BUTTON1:
					if (!styleListShow) {
						stylePanel.setLayout(new GridLayout(1, styles.size() + 1));
						for (JLabel jLabel : styles) {
							stylePanel.add(jLabel);
							stylePanel.updateUI();
						}
						styleListShow = true;
					} else {
						stylePanel.removeAll();
						stylePanel.updateUI();
						styleListShow = false;
					}
					break;
				case MouseEvent.BUTTON3:
					Opt4OpenStyXML.getOptFrm().setLoc((int)e.getLocationOnScreen().getX(),(int)e.getLocationOnScreen().getY());
					break;

				}
			}
		});
		this.addComponentListener(new ComponentListener() {

			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentResized(ComponentEvent e) {
				stylePanel.setBounds(0, 0, DebugNote.getmFrm().getWidth() / 20 * 17, DebugNote.getSf().getHeight() );
				inputBuffer.setBounds(DebugNote.getmFrm().getWidth() / 20 * 17, 0, DebugNote.getmFrm().getWidth() / 20 * 3,
						DebugNote.getSf().getHeight());
			}

			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub

			}
		});
	}

	public static void showBuffer(char[] cs, SimpleAttributeSet style) {

		int size = StyleConstants.getFontSize(style);
		Color foreground = StyleConstants.getForeground(style);
		inputStyle.setForeground(foreground);
		inputStyle.setFont(new Font("微软雅黑", Font.BOLD, size));
		inputStyle.setText("字体大小:" + size);
		inputBuffer.setText(new String(cs));

	}

	public void initStyle() {
		styles = new LinkedList<JLabel>();
		Map<String, SimpleAttributeSet> initStyleList = StyleInitor.getInitStyleList();
		Set<Entry<String, SimpleAttributeSet>> entrySet = initStyleList.entrySet();
		for (Entry<String, SimpleAttributeSet> entry : entrySet) {
			String name = entry.getKey();
			SimpleAttributeSet styattr = entry.getValue();
			if ("defattr".equals(name.toLowerCase())) {
				defaultStyle=styattr;
			}
			JLabel attr = new JLabel(name);
			attr.setOpaque(true);
			/*
			 * 字体 字体大小 前景色 背景色
			 */
			attr.setFont(new Font(StyleConstants.getFontFamily(styattr),
					styattr.getAttribute("bold") == null ? Font.PLAIN : Font.BOLD,
					StyleConstants.getFontSize(styattr)));
			attr.setBackground(StyleConstants.getBackground(styattr));
			attr.setForeground(StyleConstants.getForeground(styattr));
			attr.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					SimpleAttributeSet selectAttr = StyleInitor.getInitStyleList().get(((JLabel) e.getSource()).getText());
					defaultStyle = selectAttr;
					stylePanel.removeAll();
					setInputStyle(defaultStyle);
					stylePanel.updateUI();
					styleListShow = false;
					showBuffer(("字体大小:"+selectAttr.getAttribute("FontSize")).toCharArray(), selectAttr);
					TextArea.changeSelectedStyle();
				}
			});
			styles.add(attr);
		}
	}

	public void setInputStyle(SimpleAttributeSet attributeSet) {
		Color background = StyleConstants.getBackground(attributeSet);
		Color foreground = StyleConstants.getForeground(attributeSet);
		inputStyle.setBackground(background);
		inputStyle.setForeground(foreground);
	}

	public static SimpleAttributeSet getDefaultStyle() {
		return defaultStyle;
	}

	public void reSize() {
		stylePanel.setBounds(0, 0, DebugNote.getmFrm().getWidth() / 20 * 17, DebugNote.getmFrm().getHeight() / 20);
		inputStyle.setBounds(DebugNote.getmFrm().getWidth() / 20 * 17, 0, DebugNote.getmFrm().getWidth() / 20 * 3,
				DebugNote.getmFrm().getHeight() / 20);
	}

	public static List<JLabel> getStyles() {
		return styles;
	}

	public static JLabel getInputBuffer() {
		return inputBuffer;
	}

	public static JLabel getInputStyle() {
		return inputStyle;
	}
	
	
	
	
}

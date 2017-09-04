package main.left;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DebugGraphics;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import app.DebugNote;
import frms.FontFrm;
import main.TextArea;
import menu.textarea.LineNumberBorder;
import utils.StyleInitor;

public class FontPane extends JPanel {
	private JPanel fontInfo = new JPanel();
	private Border fontInfoBorder = BorderFactory.createTitledBorder("字体信息");
	private JPanel fontView = new JPanel();
	private Border fontViewBorder = BorderFactory.createLoweredBevelBorder();
	private JPanel btns = new JPanel();
	private static SimpleAttributeSet txtFont = StyleInitor.getDefaultAttr();

	// 字体预览标签
	private static JLabel fontViewLbl = new JLabel("DebugNote");

	// 字体加载初始属性记录
	private static int fontSizeInit = StyleConstants.getFontSize(txtFont);
	private static String fontFamilyInit = StyleConstants.getFontFamily(txtFont);
	private static Color foregroundInit = StyleConstants.getForeground(txtFont);
	private static Color backgroundInit = StyleConstants.getBackground(txtFont);
	private static boolean boldInit = StyleConstants.isBold(txtFont);
	private static boolean italicInit = StyleConstants.isItalic(txtFont);

	public FontPane() {

		// 设置字体信息面板
		fontInfo.setBorder(fontInfoBorder);
		fontInfo.setLayout(new GridLayout(10, 1));
		// 设定字体
		// 0
		JButton fontType = new JButton("当前字体:" + fontFamilyInit);
		fontType.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				FontFrm.getmFrm().setVisible(true);
			}

		});
		// 00
		fontInfo.add(fontType);
		// 设定字体大小
		JLabel fontSize = new JLabel("字体大小:" + fontSizeInit);
		// 01
		fontInfo.add(fontSize);
		// 字体大小滑动条
		JSlider fontSizeSlider = new JSlider(JSlider.HORIZONTAL, 8, 40, fontSizeInit);
		// fontSizeSlider.setMinorTickSpacing(1);
		// fontSizeSlider.setMajorTickSpacing(5);
		// fontSizeSlider.setPaintTicks(true);
		// fontSizeSlider.setPaintLabels(true);
		fontSizeSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider target = (JSlider) (e.getSource());
				int value = target.getValue();
				JLabel fontSize = (JLabel) target.getParent().getComponent(1);
				fontViewLbl.setFont(fontViewLbl.getFont().deriveFont((float) value));
				fontSize.setText("字体大小:" + value);
			}
		});
		// 02
		fontInfo.add(fontSizeSlider);
		// 设置字体颜色
		JPanel fontColorPanel = new JPanel(new GridLayout(1, 2));
		JLabel fontColor = new JLabel("字体颜色");
		JLabel fontForeColor = new JLabel();
		fontForeColor.setBackground(foregroundInit);
		fontForeColor.setOpaque(true);
		fontForeColor.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// 判断是否选择颜色(用户是否取消选择)
				Color chooseColor = JColorChooser.showDialog(e.getComponent().getParent().getParent(), "请选择颜色",
						StyleConstants.getForeground(txtFont));
				if (chooseColor == null) {
					chooseColor = e.getComponent().getParent().getComponent(1).getBackground();
				}
				e.getComponent().getParent().getComponent(1).setBackground(chooseColor);
				fontViewLbl.setForeground(chooseColor);
			}

		});
		fontColorPanel.add(fontColor);
		fontColorPanel.add(fontForeColor);
		// 03
		fontInfo.add(fontColorPanel);

		// 设置背景颜色
		JPanel backColorPanel = new JPanel(new GridLayout(1, 2));
		JLabel backColor = new JLabel("背景颜色");
		JLabel fontBackColor = new JLabel();
		fontBackColor.setBackground(backgroundInit);
		fontBackColor.setOpaque(true);
		fontBackColor.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// 判断是否选择颜色(用户是否取消选择)
				Color chooseColor = JColorChooser.showDialog(e.getComponent().getParent().getParent(), "请选择颜色",
						StyleConstants.getBackground(txtFont));
				if (chooseColor == null) {
					chooseColor = e.getComponent().getParent().getComponent(1).getBackground();
				}
				e.getComponent().getParent().getComponent(1).setBackground(chooseColor);
				fontViewLbl.setBackground(chooseColor);
			}

		});
		backColorPanel.add(backColor);
		backColorPanel.add(fontBackColor);
		// 04
		fontInfo.add(backColorPanel);

		// 字体加粗
		// 字体加斜
		JPanel fontStylePanel = new JPanel(new GridLayout(1, 4));
		JCheckBox fontBold = new JCheckBox("加粗", boldInit);
		fontBold.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int style = fontViewLbl.getFont().getStyle();
				if (((JCheckBox) e.getSource()).isSelected()) {
					fontViewLbl.setFont(new Font(fontViewLbl.getFont().getFamily(), Font.BOLD + style,
							fontViewLbl.getFont().getSize()));
				} else {
					fontViewLbl.setFont(new Font(fontViewLbl.getFont().getFamily(),
							style == Font.ITALIC + Font.BOLD ? Font.ITALIC : Font.PLAIN,
							fontViewLbl.getFont().getSize()));
				}
			}

		});
		JCheckBox fontItalic = new JCheckBox("倾斜", StyleConstants.isItalic(txtFont));
		fontItalic.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int style = fontViewLbl.getFont().getStyle();
				if (((JCheckBox) e.getSource()).isSelected()) {
					fontViewLbl.setFont(new Font(fontViewLbl.getFont().getFamily(), Font.ITALIC + style,
							fontViewLbl.getFont().getSize()));
				} else {
					fontViewLbl.setFont(new Font(fontViewLbl.getFont().getFamily(),
							style == Font.BOLD + Font.ITALIC ? Font.BOLD : Font.PLAIN,
							fontViewLbl.getFont().getSize()));
				}
			}

		});
		fontStylePanel.add(fontBold);
		fontStylePanel.add(fontItalic);
		// 05
		fontInfo.add(fontStylePanel);

		// 设置字体预览面板
		fontViewLbl.setOpaque(true);
		fontViewLbl.setBorder(fontViewBorder);
		fontViewLbl.setFont(DebugNote.getText().getJTP().getFont());
		fontViewLbl.setHorizontalAlignment(SwingConstants.CENTER);
		// 060
		fontView.add(fontViewLbl);

		// 添加面板
		this.setLayout(new BorderLayout());
		fontInfo.setPreferredSize(new Dimension(this.getWidth(), this.getHeight() / 5 * 3));
		fontInfo.setVisible(true);
		fontView.setPreferredSize(new Dimension(this.getWidth(), this.getHeight() / 5));
		fontView.setVisible(true);
		// 06
		fontInfo.add(fontView);

		// 应用和重置按钮域
		btns.setPreferredSize(new Dimension(this.getWidth(), this.getHeight() / 5));
		JButton apply = new JButton("应用");
		apply.addMouseListener(new MouseAdapter() {

			/*
			 * 字体样式 f 字体大小 f 字体颜色 f 字体背景色 f 字体加粗 字体加斜
			 */

			@Override
			public void mouseClicked(MouseEvent e) {
				JPanel fontInfoPanel = (JPanel) ((JPanel) ((JButton) e.getSource()).getParent()).getParent();
				// 获取属性调整框的属性
				String family = ((JButton) fontInfoPanel.getComponent(0)).getText().substring(5);
				int size = ((JSlider) fontInfoPanel.getComponent(2)).getValue();
				Color foreColor = ((JLabel) ((JPanel) fontInfoPanel.getComponent(3)).getComponent(1)).getBackground(),
						backColor = ((JLabel) ((JPanel) fontInfoPanel.getComponent(4)).getComponent(1)).getBackground();
				int bold = ((JCheckBox) ((JPanel) fontInfoPanel.getComponent(5)).getComponent(0)).isSelected()
						? Font.BOLD
						: Font.PLAIN,
						italic = ((JCheckBox) ((JPanel) fontInfoPanel.getComponent(5)).getComponent(1)).isSelected()
								? Font.ITALIC
								: Font.PLAIN;

				// 根据属性设定字体

				StyleConstants.setFontSize(txtFont, size);
				StyleConstants.setFontFamily(txtFont, family);
				StyleConstants.setForeground(txtFont, foreColor);
				StyleConstants.setBackground(txtFont, backColor);
				StyleConstants.setBold(txtFont, bold == Font.BOLD);
				StyleConstants.setItalic(txtFont, italic == Font.ITALIC);

				fontSizeInit = StyleConstants.getFontSize(txtFont);
				fontFamilyInit = StyleConstants.getFontFamily(txtFont);
				foregroundInit = StyleConstants.getForeground(txtFont);
				backgroundInit = StyleConstants.getBackground(txtFont);
				boldInit = StyleConstants.isBold(txtFont);
				italicInit = StyleConstants.isItalic(txtFont);

				// 判断是否是txt编辑状态
				if (0 == TextArea.getEditState()) {
					TextArea.getJTP().setParagraphAttributes(txtFont, true);
					TextArea.getJTP().setCharacterAttributes(txtFont, true);
					TextArea.getJTP().getStyledDocument().setCharacterAttributes(0, TextArea.getJTP().getStyledDocument().getLength(), txtFont, true);
					TextArea.getJTP().setBorder(LineNumberBorder.getNewMainBar());
				} else {
					JOptionPane.showMessageDialog(DebugNote.getmFrm(), "<html>带样式文档下字体设置不可用<br/>修改样式文档的样式请到<br/>"
							+ StyleInitor.getStyconfFile().getPath() + "下修改<br/>或右键输入样式打开文件</html>");
				}

			}

		});
		JButton reset = new JButton("重置");
		reset.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				JPanel fontInfoPanel = (JPanel) ((JPanel) ((JButton) e.getSource()).getParent()).getParent();
				// 重置属性调整框的属性
				((JButton) fontInfoPanel.getComponent(0)).setText("当前字体:" + fontFamilyInit);
				((JSlider) fontInfoPanel.getComponent(2)).setValue(fontSizeInit);
				;
				((JLabel) ((JPanel) fontInfoPanel.getComponent(3)).getComponent(1)).setBackground(foregroundInit);
				((JLabel) ((JPanel) fontInfoPanel.getComponent(4)).getComponent(1)).setBackground(backgroundInit);
				((JCheckBox) ((JPanel) fontInfoPanel.getComponent(5)).getComponent(0)).setSelected(boldInit);
				((JCheckBox) ((JPanel) fontInfoPanel.getComponent(5)).getComponent(1)).setSelected(italicInit);

				// 重置预览域字体属性
				fontViewLbl.setFont(new Font(fontFamilyInit,
						(boldInit == true ? Font.BOLD : Font.PLAIN) + (italicInit == true ? Font.ITALIC : Font.PLAIN),
						fontSizeInit));
				fontViewLbl.setBackground(backgroundInit);
				fontViewLbl.setForeground(foregroundInit);
			}

		});
		btns.setLayout(new GridLayout(1, 2));
		btns.add(apply);
		btns.add(reset);
		btns.setVisible(true);
		// 07
		fontInfo.add(btns);
		this.add(fontInfo);

		this.setVisible(true);
	}

	public void updateFamily(String family) {
		((JButton) ((JPanel) this.getComponent(0)).getComponent(0)).setText("当前字体:" + family);
		JLabel fVL = (JLabel) ((JPanel) this.fontInfo.getComponent(6)).getComponent(0);
		fVL.setFont(new Font(family, fVL.getFont().getStyle(), fVL.getFont().getSize()));
	}
	
	public static Font getSetedFont() {
		return fontViewLbl.getFont();
	}

}

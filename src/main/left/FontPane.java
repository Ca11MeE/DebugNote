package main.left;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import frms.FontFrm;
import utils.StyleInitor;

public class FontPane extends JPanel {
	private JPanel fontInfo=new JPanel();
	private Border fontInfoBorder=BorderFactory.createTitledBorder("字体信息");
	private JPanel fontView=new JPanel();
	private Border fontViewBorder=BorderFactory.createLoweredBevelBorder();
	private JPanel btns=new JPanel();
	private SimpleAttributeSet txtFont=StyleInitor.getDefaultAttr();
	
	//字体预览标签
	private static JLabel fontViewLbl=new JLabel("aA1一+-");
	
	public FontPane() {
		//设置字体信息面板
		fontInfo.setBorder(fontInfoBorder);
		fontInfo.setLayout(new GridLayout(10,1));
		//设定字体
		JButton fontType=new JButton("当前字体:"+StyleConstants.getFontFamily(txtFont));
		fontType.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				FontFrm.getmFrm().setVisible(true);
			}
			
		});
		fontInfo.add(fontType);
		//设定字体大小
		JLabel fontSize =new JLabel("字体大小:"+StyleConstants.getFontSize(txtFont));
		fontInfo.add(fontSize);
		//字体大小滑动条
		JSlider fontSizeSlider=new JSlider(JSlider.HORIZONTAL, 8, 40, StyleConstants.getFontSize(txtFont));
//		fontSizeSlider.setMinorTickSpacing(1);
//		fontSizeSlider.setMajorTickSpacing(5);
//		fontSizeSlider.setPaintTicks(true);
//		fontSizeSlider.setPaintLabels(true);
		fontSizeSlider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider target=(JSlider)(e.getSource());
				int value = target.getValue();
				JLabel fontSize = (JLabel) target.getParent().getComponent(0);
				fontViewLbl.setFont(fontViewLbl.getFont().deriveFont((float)value));
				fontSize.setText("字体大小:"+value);
			}
		});
		fontInfo.add(fontSizeSlider);
		//设置字体颜色
		JPanel fontColorPanel=new JPanel(new GridLayout(1, 2));
		JLabel fontColor=new JLabel("字体颜色");
		JLabel fontForeColor =new JLabel();
		fontForeColor.setBackground(StyleConstants.getForeground(txtFont));
		fontForeColor.setOpaque(true);
		fontForeColor.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				Color chooseColor = JColorChooser.showDialog(e.getComponent().getParent().getParent(), "请选择颜色", StyleConstants.getForeground(txtFont));
				e.getComponent().getParent().getComponent(1).setBackground(chooseColor);
				fontViewLbl.setForeground(chooseColor);
			}
			
		});
		fontColorPanel.add(fontColor);
		fontColorPanel.add(fontForeColor);
		fontInfo.add(fontColorPanel);
		
		//设置字体预览面板
//		fontView.setBorder(fontViewBorder);
		fontViewLbl.setOpaque(false);
		fontViewLbl.setBorder(fontViewBorder);
		fontViewLbl.setHorizontalAlignment(SwingConstants.CENTER);
		fontView.add(fontViewLbl);
		
		
		//添加面板
		this.setLayout(new BorderLayout());
		fontInfo.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()/5*3));
		fontInfo.setVisible(true);
		fontView.setPreferredSize(new Dimension(this.getWidth(),this.getHeight()/5));
		fontView.setVisible(true);
		fontInfo.add(fontView);
		btns.setPreferredSize(new Dimension(this.getWidth(),this.getHeight()/5));
		btns.setVisible(true);
		fontInfo.add(btns);
		this.add(fontInfo);
		
		this.setVisible(true);
	}
	
	
	
}

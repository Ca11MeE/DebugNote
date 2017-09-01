package menu.textarea;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.Arrays;

import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.border.AbstractBorder;
import javax.swing.border.LineBorder;

import app.DebugNote;
import main.StyleForm;
import main.TextArea;

public class LineNumberBorder extends LineBorder {

	private static final Component JTextPane = TextArea.getJTP();
	private static LineNumberBorder mainBar = new LineNumberBorder(Color.red);
	private int borderWidth;

	private LineNumberBorder(Color color) {
		super(color);
	}

	public static LineNumberBorder getMainBar() {

		return mainBar;
	}

	/*
	 * Insets 对象是容器边界的表示形式。 它指定容器必须在其各个边缘留出的空间。
	 */
	// 此方法在实例化时自己主动调用
	// 此方法关系到边框是否占用组件的空间
	public Insets getBorderInsets(Component c) {
		return getBorderInsets(c, new Insets(0, 0, 0, 0));
	}

	public Insets getBorderInsets(Component c, Insets insets) {
		if (c instanceof JTextPane) {
			// 这里设置行号左边边距
			borderWidth=TextArea.getJTP().getFontMetrics(StyleForm.getStyles().get(0).getFont()).stringWidth(StyleForm.getStyles().get(0).getText().substring(0,1)) * 4+3;
					//TextArea.getJTP().getFontMetrics(StyleForm.getStyles().get(0).getFont()).stringWidth(StyleForm.getStyles().get(0).getText().substring(0,1)) * 4+3;
			insets.left = borderWidth;
		}

		return insets;

	}

	public boolean isBorderOpaque() {
		return false;
	}

	// 边框的绘制方法
	// 此方法必须实现
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		// 获得当前剪贴区域的边界矩形。
		java.awt.Rectangle clip = g.getClipBounds();
		FontMetrics fm = TextArea.getJTP().getFontMetrics(StyleForm.getInputStyle().getFont());
		g.setFont(StyleForm.getStyles().get(0).getFont());
		/**
		 * 
		 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		 */
		int fontHeight = fm.getHeight()+fm.getLeading();
		// (int)fm.getLineMetrics(TextArea.getJTP().getText().split("\\n")[0],
		// g).getHeight();
		// starting location at the "top" of the page...
		// y is the starting baseline for the font...
		int ybaseline = y + fm.getAscent();

		// now determine if it is the "top" of the page...or somewhere
		// else
		int startingLineNumber = (clip.y / fontHeight) + 1;

		if (startingLineNumber != 1) {
			ybaseline = y + startingLineNumber * fontHeight - (fontHeight);
		}

		int yend = ybaseline + height;
		if (yend > (y + height)) {
			yend = y + height;
		}
		int lineWidth = fm.stringWidth(startingLineNumber+"");
		int index = 0;
		String[] splits=TextArea.getJTP().getText().split("\\n");
		g.setColor(Color.red);
		g.fill3DRect(0, ybaseline- fontHeight+fm.getDescent(), borderWidth, fontHeight+fm.getDescent(), true);
		// 绘制行号
		while (ybaseline < yend) {
			String label = padLabel(startingLineNumber, 0, true);
			int lineHeight = index < splits.length
					? (int) fm.getLineMetrics(splits[index], g).getHeight()
					: fm.getAscent();
			// 设置行号背景色
			g.setColor(JTextPane.getBackground().darker());
			g.fill3DRect(0, ybaseline+fm.getDescent(), borderWidth, fontHeight+fm.getDescent(), true);
			g.setColor(TextArea.getJTP().getForeground());
			g.setFont(StyleForm.getStyles().get(0).getFont());
			g.drawString(label, 0, ybaseline);
			ybaseline += fontHeight;
			startingLineNumber++;
			index++;
		}
	}

	// 寻找适合的数字宽度
	private int lineNumberWidth(JTextArea jta) {
		int lineCount = Math.max(jta.getRows(), jta.getLineCount());
		return jta.getFontMetrics(jta.getFont()).stringWidth(lineCount + " ");
	}

	private String padLabel(int lineNumber, int length, boolean addSpace) {
		StringBuffer buffer = new StringBuffer();
		for (int count = (length - buffer.length()); count > 0; count--) {
			if (buffer.length() < 4) {
				buffer.insert(0, ' ');
			}
		}
		if (addSpace) {
			for (int i = buffer.length(); i < 4; i++) {
				buffer.append("  ");

			}
		}
		buffer.insert(1, lineNumber);
		return buffer.toString();
	}

}
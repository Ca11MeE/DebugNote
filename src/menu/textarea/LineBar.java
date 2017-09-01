package menu.textarea;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.TextListener;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.text.StyleConstants;

import org.junit.Test;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Text;

import app.DebugNote;
import main.StyleForm;
import main.TextArea;

/**
 * 实现行号条
 * 1.载入文件初始化行号，行号严格跟随文档
 * 2.实时监测文本行数变化
 * 3.跟随文本浏览滚动行号
 * 
 * 
 * 未使用,存在缺陷
 * 1.行号不会跟随文本滚动
 * 2.换行存在间隔差异,与文本框不同步
 * @author keliyi
 * 
 *
 */
@Deprecated
public class LineBar extends JComponent {  
	  
    private final  Font DEFAULT_FONT = new Font(Font.MONOSPACED, Font.PLAIN, 13);  
    public final Color DEFAULT_BACKGROUD = new Color(228, 228, 228);  
    public final Color DEFAULT_FOREGROUD = Color.red;  
    public final int nHEIGHT = Integer.MAX_VALUE - 1000000;  
    public final int MARGIN = 5;  
    private int lineHeight;  
    private int fontLineHeight;  
    private int currentRowWidth;  
    private FontMetrics fontMetrics;  
  
    public LineBar() {  
        setFont(TextArea.getJTP().getFont());  
        setForeground(DEFAULT_FOREGROUD);  
        setBackground(DEFAULT_BACKGROUD);  
        setPreferredSize(9999);  
        //this.setComponentPopupMenu(popup);
    }  
  
    public void setPreferredSize(int row) {  
        int width = (int)fontMetrics.stringWidth(String.valueOf(row));  
        int height=fontMetrics.getHeight();
        if (currentRowWidth < width) {  
            currentRowWidth = width;  
            setPreferredSize(new Dimension(2 * MARGIN + width + 1,height));  
        }  
    }  
  
    public void setFont(Font font) {  
        super.setFont(font);  
        fontMetrics = getFontMetrics(getFont());  
        fontLineHeight = fontMetrics.getHeight();  
    }  
  
    public int getLineHeight() {  
        if (lineHeight == 0) {  
            return fontLineHeight;  
        }  
        return lineHeight;  
    }  
  
    public void setLineHeight(int lineHeight) {  
        if (lineHeight > 0) {  
            this.lineHeight = lineHeight;  
        }  
    }  
  
    public int getStartOffset() {  
        return 4;  
    }  
  
    protected void paintComponent(Graphics g) {  
        int nlineHeight = getLineHeight();  
        int startOffset = getStartOffset();  
        Rectangle drawHere = g.getClipBounds();  
        g.setColor(getBackground());  
        g.fillRect(drawHere.x, drawHere.y, drawHere.width, drawHere.height);  
        g.setColor(getForeground());  
        int startLineNum = (drawHere.y / nlineHeight) + 1;  
        int endLineNum =TextArea.getJTP().getText().split("\\n").length+1;
        		
        		
        		//startLineNum + (drawHere.height / nlineHeight);  
        int start = (drawHere.y / nlineHeight) * nlineHeight + nlineHeight - startOffset;  
        for (int i = startLineNum; i <= endLineNum; ++i) {  
            String lineNum = String.valueOf(i);  
            int width = fontMetrics.stringWidth(lineNum);  
            g.drawString(lineNum + " ", MARGIN + currentRowWidth - width - 1, start);  
            start += nlineHeight;  
        }  
        setPreferredSize(endLineNum);  
    }  
    
}
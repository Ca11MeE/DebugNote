package utils;

import java.awt.Color;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class TextWorker {

	public static SimpleAttributeSet attr = new SimpleAttributeSet();
	public static SimpleAttributeSet attr1 = new SimpleAttributeSet();
	public static SimpleAttributeSet attr2 = new SimpleAttributeSet();

	static {
		StyleConstants.setFontSize(attr, 12);
		StyleConstants.setForeground(attr, Color.black);

		StyleConstants.setFontSize(attr1, 20);
		StyleConstants.setForeground(attr1, Color.RED);

		StyleConstants.setFontSize(attr2, 10);
		StyleConstants.setForeground(attr2, Color.blue);

	}

	public static SimpleAttributeSet chkStartMark(String readString,SimpleAttributeSet defAttr,Document dtext) throws BadLocationException {
		if (readString.contains("<!+>")) {
			int start = readString.indexOf("<!+>");
			String forwardString = readString.substring(0, start);
			String behindString = readString.substring(start + 4);

			dtext.insertString(dtext.getLength(), forwardString, defAttr);
			dtext.insertString(dtext.getLength(), behindString, attr1);
			return attr1;
		}
		return null;
	}
	
	public static SimpleAttributeSet chkEndMark(String readString,SimpleAttributeSet defAttr,Document dtext) throws BadLocationException {
		if (readString.contains("<!->")) {
			int start = readString.indexOf("<!->");
			String forwardString = readString.substring(0, start);
			String behindString = readString.substring(start + 4);
			
			dtext.insertString(dtext.getLength(), forwardString, defAttr);
			dtext.insertString(dtext.getLength(), behindString, attr);
			return attr;
		}
		return null;
	}
}

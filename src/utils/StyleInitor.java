package utils;

import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.TabSet;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import app.DebugNote;
import main.MainFrm;
import main.StyleForm;

/**
 * 样式初始者,负责初始文本样式并返回
 * 
 * @author 37247
 *
 */
public class StyleInitor {
	private static SAXReader reader = new SAXReader();
	private static Integer align;
	private static Color background;
	private static Integer bidiLevel;
	private static Boolean bold;
	private static Float firstLineIndent;
	private static String fontFamily;
	private static Integer fontSize;
	private static Color foreground;
	private static Boolean italic;
	private static Float leftIndent;
	private static Float lineSpacing;
	private static Float rightIndent;
	private static Float spaceAbove;
	private static Float spaceBelow;
	private static Boolean strikeThrough;
	private static Boolean subscript;
	private static Boolean superscript;
	private static Boolean underline;
	
	private static File styconfFile=new File(MainFrm.uriString+"styconf.xml");

	public static Map<String,SimpleAttributeSet> getInitStyleList() {
		// 返回的样式列表
		Map<String,SimpleAttributeSet> result = new HashMap<String,SimpleAttributeSet>();
		File file=styconfFile;
		if (!file.exists()) {
			try {
				int o = JOptionPane.showConfirmDialog(DebugNote.getmFrm(), "配置文件不存在,是否创建?\n是:创建自带样式配置文件\n否:创建默认样式配置文件\n取消:退出程序");
				FileOutputStream fOutputStream=null;
				String exam="";
				switch (o) {
				case 0:
					file.createNewFile();
					fOutputStream=new FileOutputStream(file);
					exam="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
							+ "<!--StyleConstants样式属性集对象设置方法\n"
							+ "\t标签中对应属性名:\n"
							+ "\t\tAlignment=(int align) \n"
							+ "\t\t\tSets alignment. \n"
							+ "\t\tBackground=(Color fg) \n"
							+ "\t\t\tSets the background color. \n"
							+ "\t\tBidiLevel=(int o) \n"
							+ "\t\t\tSets the BidiLevel. \n"
							+ "\t\tBold=(boolean b) \n"
							+ "\t\t\tSets the bold attribute. \n"
							+ "\t\tComponent=(Component c) \n"
							+ "\t\t\tSets the component attribute. \n"
							+ "\t\tFirstLineIndent=(float i) \n"
							+ "\t\t\tSets the first line indent. \n"
							+ "\t\tFontFamily=(String fam) \n"
							+ "\t\t\tSets the font attribute. \n"
							+ "\t\tFontSize=(int s) \n"
							+ "\t\t\tSets the font size attribute. \n"
							+ "\t\tForeground=(Color fg) \n"
							+ "\t\t\tSets the foreground color. \n"
							+ "\t\tIcon=(Icon c) \n"
							+ "\t\t\tSets the icon attribute. \n"
							+ "\t\tItalic=(boolean b) \n"
							+ "\t\t\tSets the italic attribute. \n"
							+ "\t\tLeftIndent=(float i) \n"
							+ "\t\t\tSets left indent. \n"
							+ "\t\tLineSpacing=(float i) \n"
							+ "\t\t\tSets line spacing. \n"
							+ "\t\tRightIndent=(float i) \n"
							+ "\t\t\tSets right indent. \n"
							+ "\t\tSpaceAbove=(float i) \n"
							+ "\t\t\tSets space above. \n"
							+ "\t\tSpaceBelow=(float i) \n"
							+ "\t\t\tSets space below. \n"
							+ "\t\tStrikeThrough=(boolean b) \n"
							+ "\t\t\tSets the strikethrough attribute. \n"
							+ "\t\tSubscript=(boolean b) \n"
							+ "\t\t\tSets the subscript attribute. \n"
							+ "\t\tSuperscript=(boolean b) \n"
							+ "\t\t\tSets the superscript attribute. \n"
							+ "\t\tTabSet=(TabSet tabs) \n"
							+ "\t\t\tSets the TabSet. \n"
							+ "\t\tUnderline=(boolean b) \n"
							+ "\t\t\tSets the underline attribute. \n"
							+ "-->\n"
					+ "<!--样式范例-->\n"
					+ "<Style>\n"
					+ "\t<TextStyle name=\"defattr\" FontSize=\"12\" Foreground=\"black\" FontFamily=\"微软雅黑\"/>\n"
					+ "\t<TextStyle name=\"attr1\" FontSize=\"20\" Foreground=\"red\" FontFamily=\"微软雅黑\"/>\n"
					+ "\t<TextStyle name=\"attr2\" FontSize=\"10\" Foreground=\"blue\" FontFamily=\"微软雅黑\" Background=\"green\"/>\n"
					+ "</Style>";
					fOutputStream.write(exam.getBytes("utf-8"));
					fOutputStream.close();
					break;
				case 1:
					file.createNewFile();
					 fOutputStream=new FileOutputStream(file);
					exam="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
							+ "<!--StyleConstants样式属性集对象设置方法\n"
							+ "\t标签中对应属性名\n"
							+ "\t\tAlignment=(int align) \n"
							+ "\t\t\tSets alignment. \n"
							+ "\t\tBackground=(Color fg) \n"
							+ "\t\t\tSets the background color. \n"
							+ "\t\tBidiLevel=(int o) \n"
							+ "\t\t\tSets the BidiLevel. \n"
							+ "\t\tBold=(boolean b) \n"
							+ "\t\t\tSets the bold attribute. \n"
							+ "\t\tComponent=(Component c) \n"
							+ "\t\t\tSets the component attribute. \n"
							+ "\t\tFirstLineIndent=(float i) \n"
							+ "\t\t\tSets the first line indent. \n"
							+ "\t\tFontFamily=(String fam) \n"
							+ "\t\t\tSets the font attribute. \n"
							+ "\t\tFontSize=(int s) \n"
							+ "\t\t\tSets the font size attribute. \n"
							+ "\t\tForeground=(Color fg) \n"
							+ "\t\t\tSets the foreground color. \n"
							+ "\t\tIcon=(Icon c) \n"
							+ "\t\t\tSets the icon attribute. \n"
							+ "\t\tItalic=(boolean b) \n"
							+ "\t\t\tSets the italic attribute. \n"
							+ "\t\tLeftIndent=(float i) \n"
							+ "\t\t\tSets left indent. \n"
							+ "\t\tLineSpacing=(float i) \n"
							+ "\t\t\tSets line spacing. \n"
							+ "\t\tRightIndent=(float i) \n"
							+ "\t\t\tSets right indent. \n"
							+ "\t\tSpaceAbove=(float i) \n"
							+ "\t\t\tSets space above. \n"
							+ "\t\tSpaceBelow=(float i) \n"
							+ "\t\t\tSets space below. \n"
							+ "\t\tStrikeThrough=(boolean b) \n"
							+ "\t\t\tSets the strikethrough attribute. \n"
							+ "\t\tSubscript=(boolean b) \n"
							+ "\t\t\tSets the subscript attribute. \n"
							+ "\t\tSuperscript=(boolean b) \n"
							+ "\t\t\tSets the superscript attribute. \n"
							+ "\t\tTabSet=(TabSet tabs) \n"
							+ "\t\t\tSets the TabSet. \n"
							+ "\t\tUnderline=(boolean b) \n"
							+ "\t\t\tSets the underline attribute. \n"
							+ "-->\n"
					+ "<!--样式范例-->\n"
					+ "<Style>\n"
					+ "\t<TextStyle name=\"defattr\" FontSize=\"12\" Foreground=\"black\" FontFamily=\"微软雅黑\"/>\n"
					+ "</Style>";
					fOutputStream.write(exam.getBytes("utf-8"));
					fOutputStream.close();
					break;
				case 2:
					System.exit(0);
					break;
				}
			} catch (IOException e) {
				JOptionPane.showMessageDialog(DebugNote.getmFrm(), "样式配置文件创建失败");
			}
		}
		styconfFile=file;
		try {
			// 获取本地配置文件路径
			//String path = reader.getClass().getClassLoader().getResource("styconf.xml").getFile();
			//System.out.println(new File(path));
			Document doc = reader.read(file);
			// 获取配置文件根元素
			Element root = doc.getRootElement();
			List<Element> elements = root.elements();
			// 获取所有配置文件元素
			for (Element obj : elements) {
				// 获取标签名
				String objname = obj.getName();
				if (objname.equals("TextStyle")) {
					result.put(obj.attributeValue("name"), initStyle(obj));
					// 没有该属性会返回null
//					String name = obj.attribute("name").getValue();
//					System.out.println(name);
				}
			}

		} catch (DocumentException e) {
			JOptionPane.showMessageDialog(DebugNote.getmFrm(), "配置文件解析失败");
			return new HashMap<String,SimpleAttributeSet>();
		}catch (NullPointerException e) {
			JOptionPane.showMessageDialog(DebugNote.getmFrm(), "配置文件找不到");
			return new HashMap<String,SimpleAttributeSet>();
		}
		return result;

	}

	/**
	 * 解析样式配置文件单个元素
	 * 
	 * @param styleEle
	 *            单个样式元素
	 * @return 样式结果集
	 */
	public static SimpleAttributeSet initStyle(Element styleEle) {
		// Sets alignment.
		try {
			align = Integer.parseInt(styleEle.attribute("Alignment").getValue());
		} catch (Exception e) {
			align = null;
		}
		// Sets the background color.
		try {
			background = str2color(styleEle.attribute("Background").getValue());
		} catch (Exception e) {
			background = Color.white;
		}
		// Sets the BidiLevel.
		try {
			bidiLevel = Integer.parseInt(styleEle.attribute("BidiLevel").getValue());
		} catch (Exception e) {
			bidiLevel = null;
		}
		// Sets the bold attribute.
		try {
			bold = Boolean.parseBoolean(styleEle.attribute("Bold").getValue());
		} catch (Exception e) {
			bold = null;
		}
		// Sets the first line indent.
		try {
			firstLineIndent = Float.parseFloat(styleEle.attribute("FirstLineIndent").getValue());
		} catch (Exception e) {
			firstLineIndent = null;
		}
		// Sets the font attribute.
		try {
			fontFamily = styleEle.attribute("FontFamily").getValue();
		} catch (Exception e) {
			fontFamily = null;
		}
		// Sets the font size attribute.
		try {
			fontSize = Integer.parseInt(styleEle.attribute("FontSize").getValue());
		} catch (Exception e) {
			fontSize = null;
		}
		// Sets the foreground color.
		try {
			foreground = str2color(styleEle.attribute("Foreground").getValue());
		} catch (Exception e) {
			foreground = null;
		}
		// Sets the italic attribute.
		try {
			italic = Boolean.parseBoolean(styleEle.attribute("Italic").getValue());
		} catch (Exception e) {
			italic = null;
		}
		// Sets left indent.
		try {
			leftIndent = Float.parseFloat(styleEle.attribute("LeftIndent").getValue());
		} catch (Exception e) {
			leftIndent = null;
		}
		// Sets line spacing.
		try {
			lineSpacing = Float.parseFloat(styleEle.attribute("LineSpacing").getValue());
		} catch (Exception e) {
			lineSpacing = null;
		}
		// Sets right indent.
		try {
			rightIndent = Float.parseFloat(styleEle.attribute("RightIndent").getValue());
		} catch (Exception e) {
			rightIndent = null;
		}
		// Sets space above.
		try {
			spaceAbove = Float.parseFloat(styleEle.attribute("SpaceAbove").getValue());
		} catch (Exception e) {
			spaceAbove = null;
		}
		// Sets space below.
		try {
			spaceBelow = Float.parseFloat(styleEle.attribute("SpaceBelow").getValue());
		} catch (Exception e) {
			spaceBelow = null;
		}
		// Sets the strikethrough attribute.
		try {
			strikeThrough = Boolean.parseBoolean(styleEle.attribute("StrikeThrough").getValue());
		} catch (Exception e) {
			strikeThrough = null;
		}
		// Sets the subscript attribute.
		try {
			subscript = Boolean.parseBoolean(styleEle.attribute("Subscript").getValue());
		} catch (Exception e) {
			subscript = null;
		}
		// Sets the superscript attribute.
		try {
			superscript = Boolean.parseBoolean(styleEle.attribute("Superscript").getValue());
		} catch (Exception e) {
			superscript = null;
		}
		// Sets the underline attribute.
		try {
			underline = Boolean.parseBoolean(styleEle.attribute("Underline").getValue());
		} catch (Exception e) {
			underline = null;
		}

		// Sets the TabSet.
		// TabSet tabSet = styleEle.attribute("TabSet").getValue();
		// Sets the component attribute.
		// Component component = styleEle.attribute("Component").getValue();
		// Sets the icon attribute.
		// Icon icon = styleEle.attribute("Icon").getValue();

		// 将样式应用到属性集中
		SimpleAttributeSet result = new SimpleAttributeSet();

		if (align != null) {
			StyleConstants.setAlignment(result, align);
		}
		if (background != null) {
			StyleConstants.setBackground(result, background);
		}
		if (bidiLevel != null) {
			StyleConstants.setBidiLevel(result, bidiLevel);
		}
		if (bold != null) {
			StyleConstants.setBold(result, bold);
		}
		// StyleConstants.setComponent(result, c);
		if (firstLineIndent != null) {
			StyleConstants.setFirstLineIndent(result, firstLineIndent);
		}
		if (fontFamily != null) {
			StyleConstants.setFontFamily(result, fontFamily);
		}
		if (fontSize != null) {
			StyleConstants.setFontSize(result, fontSize);
		}
		if (foreground != null) {
			StyleConstants.setForeground(result, foreground);
		}
		// StyleConstants.setIcon(result, c);
		if (italic != null) {
			StyleConstants.setItalic(result, italic);
		}
		if (leftIndent != null) {
			StyleConstants.setLeftIndent(result, leftIndent);
		}
		if (lineSpacing != null) {
			StyleConstants.setLineSpacing(result, lineSpacing);
		}
		if (rightIndent != null) {
			StyleConstants.setRightIndent(result, rightIndent);
		}
		if (spaceAbove != null) {
			StyleConstants.setSpaceAbove(result, spaceAbove);
		}
		if (spaceBelow != null) {
			StyleConstants.setSpaceBelow(result, spaceBelow);
		}
		if (strikeThrough != null) {
			StyleConstants.setStrikeThrough(result, strikeThrough);
		}
		if (subscript != null) {
			StyleConstants.setSubscript(result, subscript);
		}
		if (superscript != null) {
			StyleConstants.setSuperscript(result, superscript);
		}
		// StyleConstants.setTabSet(result, tabs);
		if (underline != null) {
			StyleConstants.setUnderline(result, underline);
		}

		return result;

	}

	/**
	 * 把颜色文字转换成对应的颜色对象
	 * 
	 * @param str
	 *            表示颜色的文字
	 * @return 对应的colo对象
	 */
	public static Color str2color(String str) {
		switch (str.toLowerCase()) {
		case "white":
			return new Color(255, 255, 255);
		case "lightGray":
			return new Color(192, 192, 192);
		case "gray":
			return new Color(128, 128, 128);
		case "darkGray":
			return new Color(64, 64, 64);
		case "black":
			return new Color(0, 0, 0);
		case "red":
			return new Color(255, 0, 0);
		case "pink":
			return new Color(255, 175, 175);
		case "orange":
			return new Color(255, 200, 0);
		case "yellow":
			return new Color(255, 255, 0);
		case "green":
			return new Color(0, 255, 0);
		case "magenta":
			return new Color(255, 0, 255);
		case "cyan":
			return new Color(0, 255, 255);
		case "blue":
			return new Color(0, 0, 255);
		default:
			return new Color(0, 0, 0);
		}
	}

	/**
	 * 根据rgb值返回color对象
	 * 
	 * @param r
	 *            红
	 * @param g
	 *            绿
	 * @param b
	 *            蓝
	 * @return color对象
	 */
	public static Color rgb2color(int r, int g, int b) {
		return new Color(r, g, b);
	}

	/**
	 * 根据rgb值返回color对象
	 * 
	 * @param r
	 *            红
	 * @param g
	 *            绿
	 * @param b
	 *            蓝
	 * @return color对象
	 */
	public static Color rgb2color(float r, float g, float b) {
		return new Color(r, g, b);
	}

	public static File getStyconfFile() {
		return styconfFile;
	}

	
	
}

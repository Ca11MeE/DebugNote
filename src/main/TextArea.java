package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.TextListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;

import org.junit.Test;

import com.sun.org.apache.bcel.internal.classfile.LineNumber;

import app.DebugNote;
import frms.CreateFrm;
import frms.SaveFrm;
import main.head.Head;
import menu.head.MainMenu;
import menu.style.Opt4OpenStyXML;
import menu.textarea.LineBar;
import menu.textarea.LineBarMenu;
import menu.textarea.LineNumberBorder;
import menu.textarea.TextAreaMenu;
import utils.StyleInitor;
import utils.poi.DocReader;

public class TextArea {

	private static JTextPane jtp = new JTextPane();
	private JScrollPane textlist;
	private DefaultStyledDocument dtext;
	// 该文本默认样式
	private SimpleAttributeSet selectAttr = StyleForm.getDefaultStyle();
	private SimpleAttributeSet defAttr = StyleInitor.getDefaultAttr();
	
	// 定义xml标签内文字样式
	// 开始标签样式
	private SimpleAttributeSet xmlLabelAttr = new SimpleAttributeSet();
	// 结束标签样式
	private SimpleAttributeSet xmlLabelEndAttr = new SimpleAttributeSet();
	//注释标签样式
	private SimpleAttributeSet xmlDiscriptionAttr = new SimpleAttributeSet();

	// 输入字符缓冲区
	private char[] inCBuffer = new char[4];
	// 样式标签开关
	private boolean inMarkStrat = false;
	private boolean inMarkEnd = false;
	private int inStartMarkCount = 0;
	// 保存位置变量
	private static int x = 0;
	private static int y = 0;
	// 保存垂直滚动条对象
	private static JScrollBar verticalScrollBar;

	public JScrollPane getTextlist() {
		return textlist;
	}

	/**
	 * 
	 */
	public TextArea() {
		defAttr = StyleForm.getDefaultStyle();
		// 设置自动换行
		textlist = new JScrollPane(jtp, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		// textlist.setRowHeaderView(lineBar);

		verticalScrollBar = textlist.getVerticalScrollBar();

		verticalScrollBar.addMouseListener(new MouseAdapter() {

			/**
			 * 鼠标进入显示垂直滚动条 ,并设置鼠标图标
			 */
			public void mouseEntered(MouseEvent e) {
				textlist.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
				DebugNote.getmFrm().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			/**
			 * 鼠标离开隐藏垂直滚动条
			 */
			public void mouseExited(MouseEvent e) {
				textlist.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
			}

		});

		jtp.addKeyListener(new KeyAdapter() {

			public void keyTyped(KeyEvent e) {
				// jtp.setBorder(LineNumberBorder.getMainBar());
				// String[] splits=TextArea.getJTP().getText().split("\\n");
				// for (String string : splits) {
				// float height =
				// jtp.getFontMetrics(jtp.getFont()).getLineMetrics(string,jtp.getGraphics()).getHeight();
				// System.out.println(height);
				// }
				//
				if (selectAttr != StyleForm.getDefaultStyle()) {
					selectAttr = StyleForm.getDefaultStyle();
				}
				/*
				 * 先取消标签换样式功能
				 * 
				 * 
				 * // 获取字符 char inC = e.getKeyChar();
				 * 
				 * // 显示输入法
				 * 
				 * 
				 * // 判断是否为样式标签开头 try { switch (inStartMarkCount) { case 0: if (!inMarkStrat &&
				 * inC == '<') { inMarkStrat = true; inCBuffer[0] = inC; inStartMarkCount++;
				 * e.setKeyChar('\b'); }
				 * 
				 * break; case 1: if (inMarkStrat && inC == '!') { inCBuffer[1] = inC;
				 * inStartMarkCount++; e.setKeyChar('\b'); } else {
				 * 
				 * dtext.insertString(dtext.getLength(), new String(inCBuffer), defAttr);
				 * inCBuffer = new char[4]; inMarkStrat = false; inStartMarkCount = 0; }
				 * 
				 * break; case 2: if (inMarkStrat && inC == '+') { inCBuffer[2] = inC;
				 * inStartMarkCount++; e.setKeyChar('\b'); } else { if (inMarkStrat && inC ==
				 * '-') { inCBuffer[2] = inC; inStartMarkCount++; inMarkEnd = true;
				 * e.setKeyChar('\b'); } else { dtext.insertString(dtext.getLength(), new
				 * String(inCBuffer), defAttr); inCBuffer = new char[4]; inMarkStrat = false;
				 * inStartMarkCount = 0; } }
				 * 
				 * break; case 3:
				 * 
				 * if (inMarkStrat && inC == '>') { inCBuffer[3] = inC; // 改变样式 if (inMarkEnd) {
				 * defAttr = attr1; } else { defAttr = attr2; } inCBuffer = new char[4];
				 * inMarkStrat = false; inMarkEnd = false; inStartMarkCount = 0;
				 * e.setKeyChar('\b'); System.out.println(jtp.getCaretPosition()); } break; }
				 * 
				 * 
				 * // Enumeration<?> attributeNames = //
				 * jtp.getCharacterAttributes().getAttributeNames(); // while
				 * (attributeNames.hasMoreElements()) { // Object object = (Object)
				 * attributeNames.nextElement(); // System.out.println(object); // }
				 * 
				 * 
				 * // 获取光标位置 // System.out.println(jtp.getCaretPosition()); //
				 * System.out.println(dtext.getLogicalStyle(jtp.getCaretPosition ()));
				 * 
				 * } catch (Exception ex) {
				 * 
				 * }
				 * 
				 * }
				 */

				StyleForm.showBuffer(inCBuffer, null);
				// 设定文本样式
				if (inMarkEnd) {
					
				}
				
				jtp.setCharacterAttributes(selectAttr, true);
			}

		});

		jtp.addMouseListener(new MouseAdapter() {

			/**
			 * 鼠标进入显示垂直滚动条
			 */
			public void mouseEntered(MouseEvent e) {
				textlist.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			}

			/**
			 * 鼠标离开隐藏垂直滚动条
			 */
			public void mouseExited(MouseEvent e) {
				textlist.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
			}

			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3 && DebugNote.getSf().isVisible()) {
					TextAreaMenu.getMenu().setLocation(e.getLocationOnScreen());

					TextAreaMenu.getMenu().addStyles();
					TextAreaMenu.getMenu().setVisible(true);

				} else {
					TextAreaMenu.getMenu().setVisible(false);
				}
			}

		});

	}

	public void read(File file) throws IOException, BadLocationException, ClassNotFoundException {

		/*
		 * 
		 * SimpleAttributeSet attr=new SimpleAttributeSet(); //实例化一个simpleAttributeSet类。
		 * 
		 * //StyleConstants.setAlignment(attr, StyleConstants.ALIGN_CENTER);
		 * //使用StyleConstants工具类来设置attr属性，这里设置居中属性。
		 * 
		 * 
		 * //设置字体是否加粗 StyleConstants.setBold(attr, true);
		 * 
		 * //设置字体大小 //StyleConstants.setFontSize(attr, 20);
		 * 
		 * //设置字体颜色 StyleConstants.setForeground(attr, Color.red);
		 * 
		 * jtp.setParagraphAttributes(attr,false);
		 * //设置段落属性，第二个参数为false表示不覆盖以前的属性，如果选择true，会覆盖以前的属性。
		 * 
		 */

		// text.setText("");
		dtext = new DefaultStyledDocument();

		// 利用缓冲池检测标签

		int index = 0;
		int markCount = 0;
		char[] bufferC = new char[1024];
		boolean endMark = false;
		boolean mark = false;
		/*
		 * 20170811待解决 .dbn文件读取 已解决
		 */
		// 根据下拉框选择的文件后缀读取文件
		switch (file.getPath().substring(file.getPath().lastIndexOf('.'))) {

		// txt后缀
		case ".txt":
			
			InputStreamReader input = new InputStreamReader(new FileInputStream(file), "utf-8");
			char[] readC = new char[1];
			while (input.read(readC) != -1) {
				char c = readC[0];

				/*
				 * 
				 * if (file.getAbsolutePath().endsWith(".txt")) {
				 * 
				 * 先取消标签换样式功能
				 * 
				 * switch (markCount) { case 0: if (c == '<') { bufferC[index] = c; index++;
				 * mark = true; markCount++; continue; } else { mark = false; markCount = 0; }
				 * break; case 1: if (mark && c == '!') { bufferC[index] = c; index++;
				 * markCount++; continue; } else { mark = false; markCount = 0; } break; case 2:
				 * if (mark && c == '+') { bufferC[index] = c; index++; endMark = false;
				 * markCount++; continue; } else if (mark && c == '-') { bufferC[index] = c;
				 * index++; endMark = true; markCount++; continue; } else { mark = false;
				 * markCount = 0; } break; case 3: if (mark && c == '>') { bufferC[index] = c;
				 * if (endMark) {
				 * 
				 * defAttr = attr1; } else { defAttr = attr2; } mark = false; markCount = 0; //
				 * 清空缓冲区 bufferC = new char[1024]; index = 0; continue; } else { mark = false;
				 * markCount = 0; // 打印缓冲区字符 String buffStr = new String(bufferC);
				 * dtext.insertString(dtext.getLength(), buffStr, defAttr); // 重置指针 index = 0;
				 * continue; } }
				 * 
				 * 
				 * 
				 * 
				 * }
				 */
				String readString = new String(readC);

				// 将流读入数据写入文档对象中
				dtext.insertString(dtext.getLength(), readString, defAttr);
			}

			// 添加行号条
			jtp.setBorder(LineNumberBorder.getMainBar());

			// 不显示样式面板
			DebugNote.getSf().setVisible(false);

			break;
		case ".dbn":
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream objIn = new ObjectInputStream(fis);
			dtext = (DefaultStyledDocument) objIn.readObject();

			// 取消显示行号条
			jtp.setBorder(null);
			// 显示样式窗口
			DebugNote.getSf().setVisible(true);
			break;

		case ".xml":
			// xml文件读取
			StyleConstants.setForeground(xmlLabelAttr, Color.BLUE);
			StyleConstants.setForeground(xmlLabelEndAttr, Color.RED);
			StyleConstants.setForeground(xmlDiscriptionAttr, Color.GRAY);
			InputStreamReader xmlInput = new InputStreamReader(new FileInputStream(file), "utf-8");
			char[] xmlReadC = new char[1];
			//注释计数器
			int disCount=0,disEndCount=0;
			//注释开关
			boolean disFlag=false;
			SimpleAttributeSet insertStyle = defAttr;
			while (xmlInput.read(xmlReadC) != -1) {
				String readString = new String(xmlReadC);
				// 判断标签开始或结束
				switch (readString) {
				case "<":
					dtext.insertString(dtext.getLength(), readString, insertStyle);
					insertStyle = xmlLabelAttr;
					break;
				case ">":
					insertStyle = defAttr;
					dtext.insertString(dtext.getLength(), readString, insertStyle);
					disEndCount++;
					break;
				case "/":
					insertStyle = xmlLabelEndAttr;
					dtext.insertString(dtext.getLength(), readString, insertStyle);
					insertStyle = xmlLabelAttr;
					break;
				case "!":
					disCount++;
					dtext.insertString(dtext.getLength(), readString, insertStyle);
					break;
				case "-":
					if (disCount>0) {
						disCount++;
					}
					disEndCount++;
					dtext.insertString(dtext.getLength(), readString, insertStyle);
					break;
				default:
					if (3==disCount) {
						insertStyle=xmlDiscriptionAttr;
						disCount=0;
						disFlag=true;
					}else {
						if (3==disEndCount) {
							insertStyle=defAttr;
							disEndCount=0;
							disFlag=false;
						}
						if (disFlag) {
							insertStyle=xmlDiscriptionAttr;
						}
						disCount=0;
						disEndCount=0;
					}
					dtext.insertString(dtext.getLength(), readString, insertStyle);
					break;
				}
			}

			// 添加行行号条
			jtp.setBorder(LineNumberBorder.getMainBar());
			// 不显示样式窗口
			DebugNote.getSf().setVisible(false);
			break;
		case ".doc":
			// doc文件读取
			String[] readDoc = DocReader.readDoc(file);
			for (String string : readDoc) {
				dtext.insertString(dtext.getLength(), string, null);
			}
			break;
		default:
			InputStreamReader defInput = new InputStreamReader(new FileInputStream(file), "utf-8");
			char[] defReadC = new char[1];
			while (defInput.read(defReadC) != -1) {
				char c = defReadC[0];
				String readString = new String(defReadC);
				// 将流读入数据写入文档对象中
				dtext.insertString(dtext.getLength(), readString, null);
			}

			// 添加行行号条
			jtp.setBorder(LineNumberBorder.getMainBar());
			// 不显示样式窗口
			DebugNote.getSf().setVisible(false);
			break;
		}

		/*
		 * 已过期代码,存在缺陷
		 * 
		 * 
		 *
		 * //检测开始标识符 if (readString.contains("<!+>")) { int start =
		 * readString.indexOf("<!+>"); String forwardString = readString.substring(0,
		 * start); String behindString = readString.substring(start + 4);
		 * 
		 * // 将标识符前的字符串写入文档 dtext.insertString(dtext.getLength(), forwardString,
		 * defAttr); // 将标识符后的字符写入文档 dtext.insertString(dtext.getLength(), behindString,
		 * attr1); defAttr=attr1; continue; }
		 * 
		 * //检测结束标识符 if (readString.contains("<!->")) { int start =
		 * readString.indexOf("<!->"); String forwardString = readString.substring(0,
		 * start); String behindString = readString.substring(start + 4);
		 * 
		 * // 将标识符前的字符串写入文档 dtext.insertString(dtext.getLength(), forwardString,
		 * defAttr); // 将标识符后的字符写入文档 dtext.insertString(dtext.getLength(), behindString,
		 * attr); defAttr=attr; continue; }
		 * 
		 */

		// text.append(new String(bs,"utf-8"));

		// jtp.setContentType("text/html;charset=utf-8");
		// jtp.setPage(new File("c:/1.html").toURL());\

		// dtext=(DefaultStyledDocument)text.getDocument();

		jtp.setDocument(dtext);

	}

	public static JTextPane getJTP() {
		jtp.setBorder(LineNumberBorder.getMainBar());
		return jtp;
	}

	public static void saveText() {

		/*
		 * 
		 * 旧式保存
		 */
		// try {
		//
		// /*
		// * 弹出保存界面 未实现
		// */
		// // 根据界面返回信息创建保存流对象
		// FileOutputStream file = new FileOutputStream(new File("c:/任务" +
		// MainFrm.fileTypeString));
		// /*
		// * 用字节流写出,暂时不能保存样式 OutputStreamWriter save=new
		// * OutputStreamWriter(new FileOutputStream(file));
		// * save.write(jtp.getText()); save.flush(); save.close();
		// */
		//
		// // 用对象流写出
		// ObjectOutputStream objOut = new ObjectOutputStream(file);
		// objOut.writeObject(jtp.getStyledDocument());
		// objOut.flush();
		// objOut.close();
		//
		//
		//
		// } catch (FileNotFoundException e) {
		// System.out.println("文件找不到");
		// } catch (IOException e) {
		// System.out.println("保存失败");
		// }

		String filePath = "";
		/*
		 * 判断是否打开已有文件 是:保存时以原有文件名直接保存在文件路径 否:打开保存窗口
		 */
		if (Head.getTitle().getText() != "" && Head.getTitle().getText() != null) {
			filePath = Head.getTitle().getText();
			File file = new File(filePath);
			SaveFrm.getmFrm().saveBtnClick(file);

		} else {
			SaveFrm.showFrm();
		}
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public static void createNewFile() {

		CreateFrm.getFrm().setVisible(true);
	}

	public static void changeSelectedStyle() {
		// String selectedText = jtp.getSelectedText();
		jtp.setCharacterAttributes(StyleForm.getDefaultStyle(), true);
	}

	public static void changeSelectedStyle(SimpleAttributeSet attr) {
		// String selectedText = jtp.getSelectedText();
		jtp.setCharacterAttributes(attr, true);
	}

}

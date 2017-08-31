package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.Vector;

import javax.swing.text.DefaultStyledDocument;

import com.sun.org.apache.bcel.internal.generic.NEW;

import main.left.FilePane;
import main.TextArea;


public class FileReader {
	//文件类型常量
	public static final int DBN_TYPE=0;
	public static final int TXT_TYPE=1;
	private static String path=(FilePane.uriString==null || "".equals(FilePane.uriString))?"~/":FilePane.uriString;
	
	/**
	 * 根据文件后缀名获取文件列表
	 * @param endName 文件后缀名
	 * @return 符合后缀名的文件列表
	 * @throws FileNotFoundException 找不到文件
	 */
	@Deprecated
	public static Vector<File> getFiles(String endName) throws FileNotFoundException{
		File f=new File(path);
		if (!f.isDirectory()) {
			return null;
		}
		File[] fs=f.listFiles();
		Vector<File> vFiles=new Vector<File>();
		for (int i = 0; i < fs.length; i++) {
			if (fs[i].isFile()&&fs[i].getName().endsWith(endName)) {
				vFiles.addElement(fs[i]);
				
			}
		}
		return vFiles;
	}
	
	/**
	 * 根据文件后缀名和目录路径获取文件列表
	 * @param loc
	 * @param endName
	 * @return
	 * @throws FileNotFoundException
	 */
	@Deprecated
	public static Vector<File> getFiles(File loc,String endName) throws FileNotFoundException{
		File f=loc;
		if (!f.isDirectory()) {
			return null;
		}
		File[] fs=f.listFiles();
		Vector<File> vFiles=new Vector<File>();
		for (int i = 0; i < fs.length; i++) {
			if (fs[i].isFile()&&fs[i].getName().endsWith(endName)) {
				vFiles.addElement(fs[i]);
				
			}
		}
		return vFiles;
	}
	
	/**
	 * 获取默认目录中的文件列表
	 * @return	.txt格式的文件列表
	 * @throws FileNotFoundException 找不到文件
	 */
	@Deprecated
	public static Vector<File> getFiles() throws FileNotFoundException{
		File f=new File(path);
		File[] fs=f.listFiles();
		Vector<File> vFiles=new Vector<File>();
		if (fs!=null) {
			
		for (int i = 0; i < fs.length; i++) {
			if (fs[i].isFile()&&fs[i].getName().endsWith(".txt")) {
				vFiles.addElement(fs[i]);
				
			}
		}
		}
		return vFiles;
	}

	
	/**
	 * 用文本编辑器根据文件路径读取文件
	 * @param file	文件路径	
	 * @param textArea	文本编辑器
	 */
	public static void getData(File file,TextArea textArea){
			textArea.read(file);
	}
	
	/**
	 * 根据文件类型向指定文件写出对应模板
	 * @param fileType	文件类型
	 * @param target	目标文件
	 * @throws Exception	找不到文件模板
	 */
	public static void writeWithExample(int fileType,File target) throws Exception{
		switch (fileType) {
		case DBN_TYPE:
			//直接在程序中写出模板
			/*
			 *后续版本会根据xml中的信息建造写出模板 
			 */
			FileOutputStream fos = new FileOutputStream(target);
			ObjectOutputStream objOut = new ObjectOutputStream(fos);
			objOut.writeObject(new DefaultStyledDocument());
			objOut.close();
			fos.close();
			break;
		case TXT_TYPE:
			//写出最终文件流
			OutputStreamWriter output = new OutputStreamWriter(new FileOutputStream(target), "utf-8");
			//获取模板文件
			File example = new File(FilePane.class.getClassLoader().getResource("txt.xml").getPath());
			//写入模板流
			InputStreamReader input = new InputStreamReader(new FileInputStream(example), "utf-8");
			//将模板写出到被创建的文件中
			char[] c=new char[1];
			int len=-1;
			while ((len=input.read(c))!=-1) {
				output.write(c);
			}
			output.close();
			input.close();
			break;

		default:
			throw new Exception("不能创建的文件类型");
		}
	}
}

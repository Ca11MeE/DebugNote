package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Vector;

import main.MainFrm;
import main.TextArea;


public class FileReader {
	private static String path=(MainFrm.uriString==null || "".equals(MainFrm.uriString))?"~/":MainFrm.uriString;
	
	public static Vector<File> getFiles(String endName) throws FileNotFoundException{
		File f=new File(path);
		File[] fs=f.listFiles();
		Vector<File> vFiles=new Vector<File>();
		for (int i = 0; i < fs.length; i++) {
			if (fs[i].isFile()&&fs[i].getName().endsWith(endName)) {
				vFiles.addElement(fs[i]);
				
			}
		}
		return vFiles;
	}
	public static Vector<File> getFiles(File loc,String endName) throws FileNotFoundException{
		File f=loc;
		File[] fs=f.listFiles();
		Vector<File> vFiles=new Vector<File>();
		for (int i = 0; i < fs.length; i++) {
			if (fs[i].isFile()&&fs[i].getName().endsWith(endName)) {
				vFiles.addElement(fs[i]);
				
			}
		}
		return vFiles;
	}
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

	public static void getData(File file,TextArea textArea){
		if (file.getPath().endsWith(".txt")) {
			//��ȡ�ı��ļ�
			textArea.read(file);
		}
	}
	
	
	public static void getData(File file,TextArea textArea,String endName){
		if (file.getPath().endsWith(endName)) {
			//��ȡ�ı��ļ�
			textArea.read(file);
		}
	}
}

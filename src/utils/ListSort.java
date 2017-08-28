package utils;

import java.io.File;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.ListModel;

import main.LeftPane;

public class ListSort {
	private static DefaultListModel<String> allPath= new DefaultListModel<String>();
	private static DefaultListModel<String> pathResult = new DefaultListModel<String>();
	private static DefaultListModel<String> result = new DefaultListModel<String>();
	private static JComboBox<String> filter=LeftPane.getjComboBox();
	/**
	 * 将目标列表中的所有条目根据下拉框的选择过滤出最终结果
	 * @param target	目标列表
	 * @param filter	用作过滤条件的下拉框
	 * @return 带有过滤后的条目的文件路径列表
	 */
	public static DefaultListModel<String> fileTypeFilter(JList<String> target) {
		result.clear();
		pathResult.clear();
		ListModel<String> model = target.getModel();
		for (int i = 0; i < model.getSize(); i++) {
			if (filtType(model.getElementAt(i))) {
				
				pathResult.add(pathResult.getSize(), model.getElementAt(i));
				result.add(result.getSize(), model.getElementAt(i).substring(model.getElementAt(i).lastIndexOf(File.separatorChar)+1));
			}
		}
		return result;
	}

	/**
	 * 向记录全部打开的文件路径列表添加路径
	 * @param obj	所添加的文件路径
	 */
	public static void allPathAdd(String obj) {
		if (collectType(obj)) {
			
			allPath.add(allPath.getSize(), obj);
		}
	}

	/**
	 * 获取记录所有文件路径的列表模型
	 * @return	记录所有文件路径的列表模型
	 */
	public static DefaultListModel<String> getHead() {
		return allPath;
	}
	
	/**
	 * 判断文件路径是否符合过滤器拥有的过滤条件
	 * @param path	需要判断的文件路径
	 * @return	是否符合
	 */
	public static boolean collectType(String path) {
		boolean result=false;
		for (int i = 0; i < filter.getItemCount(); i++) {
			String item = filter.getItemAt(i).toString().substring(1);
			if (path.lastIndexOf('.')!=-1 &&path.substring(path.lastIndexOf('.')).equals(item)) {
				result=true;
				break;
			}
		}
		return result;
	}
	
	/**
	 * 判断文件路径是否符合过滤器提供的过滤条件
	 * @param path	需要判断的文件路径
	 * @return	是否符合
	 */
	public static boolean filtType(String path) {
		if (filter.getSelectedItem().toString().equals("*.*")) {
			return true;
		}
		return path.substring(path.lastIndexOf('.')).equals(filter.getSelectedItem().toString().substring(1));
	}

	public static DefaultListModel<String> getAllPath() {
		return allPath;
	}

	public static DefaultListModel<String> getPathResult() {
		return pathResult;
	}
	
	public static String findPathInPathList(String name){
		for (int i = 0; i < pathResult.getSize(); i++) {
			String ele = pathResult.getElementAt(i);
			if (ele.matches(".*"+name)) {
				return ele;
			}
		}
		return null;
	}
	
}

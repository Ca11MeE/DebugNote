package utils;

import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.ListModel;

import main.MainFrm;

public class ListSort {
	private static DefaultListModel<String> allPath= new DefaultListModel<String>();
	private static DefaultListModel<String> pathResult = new DefaultListModel<String>();
	private static DefaultListModel<String> result = new DefaultListModel<String>();
	private static JComboBox<String> filter=MainFrm.getjComboBox();
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
			if (collectType(model.getElementAt(i))) {
				
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
	 * 判断文件路径是否符合过滤器提供的过滤条件
	 * @param path	需要判断的文件路径
	 * @return	是否符合
	 */
	public static boolean collectType(String path) {
		return path.substring(path.lastIndexOf('.')).equals(filter.getSelectedItem().toString().substring(1));
	}
	
}

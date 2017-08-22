package utils;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.ListModel;

public class ListSort {
	private static DefaultListModel<String> head;
	public static DefaultListModel<String> fileTypeFilter(JList<String> target,JComboBox<String> filter){
		head = new DefaultListModel<String>();
		ListModel<String> model = target.getModel();
		String selectedItem =filter.getSelectedItem().toString().substring(1);
		for (int i = 0; i < model.getSize(); i++) {
			head.add(head.getSize(), model.getElementAt(i));
		}
		return head;
	}
}

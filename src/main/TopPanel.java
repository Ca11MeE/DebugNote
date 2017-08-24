package main;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import app.DebugNote;

public class TopPanel extends JPanel {
	public TopPanel(){
		this.setSize(DebugNote.WIDTH, DebugNote.HEIGHT);
		JLabel top = new JLabel();
		top.setBackground(Color.black);
		this.setVisible(true);
	}
}

package test;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.border.Border;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import frms.FontFrm;
import main.left.FontPane;

public class test {
private JFrame frame;
	@After
	public void after() {
		frame.setSize(400, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setVisible(true);
		//保持运行
		while (true) {

		}
	}
	@Before
	public void before() {
		frame = new JFrame();
	}

	@Test
	public void box() {
		frame.add(new Box(3));
	}
	
	@Test
	public void splitPane() {
		frame.add(new JSplitPane());
	}
	
	@Test
	public void borderTest() {
		Border b=BorderFactory.createTitledBorder("按钮");
		JButton jButton = new JButton("111");
		jButton.setBorder(b);
		frame.add(jButton);
	}
	
	@Test
	public void testFontPane() {
		frame.setContentPane(new FontPane());
	}
	
	@Test
	public void testFontFrm() {
		FontFrm.getmFrm().setVisible(true);
	}
	
}



























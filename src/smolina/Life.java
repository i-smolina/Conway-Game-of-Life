package smolina;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedList;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import smolina.controller.GamePanel;
import smolina.controller.TemplatePanel;
import smolina.model.Board;
import smolina.model.Game;

public class Life extends JFrame{
	
	private Game game;
	private GamePanel gamePanel;
	private TemplatePanel templatePanel;

	public static void main(String[] args) {
		try{
			Life frame = new Life();
			frame.setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
			frame.setVisible(true);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Life() {
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		setSize((int)(screenSize.width/1.5f) , (int)(screenSize.height/1.5f ));
		setLocation(1, 1);
		setTitle("Life");
		
		JPanel configuration = new JPanel();		
		Box vbox = Box.createVerticalBox();
		configuration.add(vbox);
				
		getContentPane().add(BorderLayout.WEST, configuration);
		getContentPane().add(BorderLayout.NORTH, new JPanel());
		getContentPane().add(BorderLayout.SOUTH, new JPanel());				
		
		templatePanel = new TemplatePanel();
		templatePanel.setPreferredSize(new Dimension(230, screenSize.height));
		
		vbox.add(templatePanel);		
			
		gamePanel = new GamePanel();
		getContentPane().add(BorderLayout.CENTER, gamePanel);		
		
		JMenuBar menubar = new JMenuBar();
		menubar.add(createGameMenu());
		setJMenuBar(menubar);
	}
	
	private JMenu createGameMenu() {
		JMenu menu = new JMenu("Game");
		menu.add(new JMenuItem(new NewGameAction()));
		
		menu.addSeparator();
		menu.add(new JMenuItem(new ExitAction()));
		return menu;
	}
	
	class ExitAction extends AbstractAction {
		
		public ExitAction() {
			putValue(NAME,"Exit");;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);			
		}		
	}
	
	class NewGameAction extends AbstractAction {
		
		public NewGameAction() {
			putValue(NAME, "New Game");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			game = new Game(gamePanel);
			game.play();
			
		}		
	}
	
}

package package1;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**********************************************************************
Creates the GUI for the TicTacToe board

@author Jarod Collier and Ben Burger
@version 5/31/2018
**********************************************************************/

public class SuperTicTacToe {
	public static void main (String[] args) {
		JMenuBar menus;
		JMenu fileMenu;
		JMenuItem quitItem;
		JMenuItem gameItem;

		JFrame frame = new JFrame ("Jarod and Ben's Super TicTacToe!");
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);

		fileMenu = new JMenu("File");
		quitItem = new JMenuItem("quit");
		gameItem = new JMenuItem("new game");

		fileMenu.add(gameItem);
		fileMenu.add(quitItem);
		menus = new JMenuBar();
		frame.setJMenuBar(menus);
		menus.add(fileMenu);

		SuperTicTacToePanel panel = new SuperTicTacToePanel();
		frame.getContentPane().add(panel);

		frame.pack();
//		frame.setSize(1200, 700);
		frame.setVisible(true);
    }
}

package org.Project_MP_2014;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MenuPanel extends JPanel {
	ChessGame chessGameReference;
	JButton undo=new JButton(new ImageIcon("undoImage.png"));
	JButton redo=new JButton(new ImageIcon("redoImage.png"));
	//JButton newGame=new JButton(new ImageIcon("newGame.png"));
	
	public MenuPanel(ChessGame game) {
		this.add(undo);
		this.add(redo);
		//this.add(newGame);
		chessGameReference=game;
		undo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				chessGameReference.TemporaryUndo();
			}
		});
	}
}
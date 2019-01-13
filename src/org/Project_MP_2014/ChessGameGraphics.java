package org.Project_MP_2014;

import java.awt.BorderLayout;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class ChessGameGraphics extends JFrame{
	BoardGraphicPanel boardGraphic;
	MoveHistoryGraphicPanel moveHistoryGraphicPanel;
	private String[] position={
		".k......",
		"........",
		"........",
		"........",
		"........",
		"....B...",
		"..B.....",
		".K......"
	};
	ChessGame game=new ChessGame(position);
	
	public ChessGameGraphics() {
		super("Chess Game");
		this.setLayout(new BorderLayout(10,10));
		boardGraphic=new BoardGraphicPanel(game);
		moveHistoryGraphicPanel=new MoveHistoryGraphicPanel(game.GetMovesList());
		getContentPane().add(moveHistoryGraphicPanel,BorderLayout.LINE_START);
		getContentPane().add(boardGraphic,BorderLayout.CENTER);
		getContentPane().add(new MenuPanel(game), BorderLayout.PAGE_START);
		pack();
		setResizable( false );
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GameLoop();
	}
	
	public void GameLoop() {
		while(true) {
			this.repaint();
			try {
				Thread.sleep(100);
			}
			catch(InterruptedException e) { }
		}
	}
}

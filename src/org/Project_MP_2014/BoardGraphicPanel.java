package org.Project_MP_2014;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


@SuppressWarnings("serial")
public class BoardGraphicPanel extends JPanel{
	private int _squareSize=75;
	private int _boardHeight;
	private int _boardWidth;
	private Square _squareFrom=null;
	private Square _squareTo=null;
	//Dichiarata final per essere accessibile anche alla classe
	//anonima per la gestione dei popupmenu
	final private ChessGame _game;
	private boolean _destinationSquareSelected=false;
	public JPopupMenu _pawnPromotionMenu;
	public JPopupMenu _gameOverMenu;
	
	private class MouseHandler implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			int x=e.getX()/_squareSize;
			int y=Math.abs(e.getY()/_squareSize-(_boardHeight-1));
			
			if(_squareFrom==null) {
				_squareFrom=new Square(x,y);
			} else {
				_squareTo=new Square(x,y);
				try {
					_game.Move(_squareFrom, _squareTo);
					_destinationSquareSelected=true;
				}
				catch(EmptySquareException chessException) { _squareFrom=_squareTo; }
				catch(WrongTurnException chessException) { _squareFrom=_squareTo; }
				catch(OccupiedSquareException chessException) { _squareFrom=_squareTo; }
				catch(PromotionException chessException) {
					_pawnPromotionMenu.show(BoardGraphicPanel.this,200,200);
				}
				catch(CheckMateException chessException) {
					_gameOverMenu.removeAll();
					JLabel gameOverMessage=new JLabel();
					gameOverMessage.setFont(gameOverMessage.getFont().deriveFont(24.0f));
					gameOverMessage.setText("<html>Check Mate!"+"<br>The "+chessException._gameWinner+" won the game!</html>");
					_gameOverMenu.add(gameOverMessage);
					int gameOverMenuX=(int)((_boardWidth*_squareSize)/2-gameOverMessage.getPreferredSize().getWidth()/2);
					int gameOverMenuY=(int)((_boardHeight*_squareSize)/2-gameOverMessage.getPreferredSize().getHeight()/2);
					_gameOverMenu.show(BoardGraphicPanel.this,gameOverMenuX, gameOverMenuY);
				}
				catch(DrawException chessException) {
					_gameOverMenu.removeAll();
					JLabel gameOverMessage=new JLabel();
					gameOverMessage.setFont(gameOverMessage.getFont().deriveFont(24.0f));
					gameOverMessage.setText("<html>Draw!</html>");
					_gameOverMenu.add(gameOverMessage);
					int gameOverMenuX=(int)((_boardWidth*_squareSize)/2-gameOverMessage.getPreferredSize().getWidth()/2);
					int gameOverMenuY=(int)((_boardHeight*_squareSize)/2-gameOverMessage.getPreferredSize().getHeight()/2);
					_gameOverMenu.show(BoardGraphicPanel.this,gameOverMenuX, gameOverMenuY);
				}
				catch(CheckException chessException) { }
				catch(ChessRuleException chessException) { }
				finally { }
				if(_destinationSquareSelected==true) {
					_squareFrom=null;
					_squareTo=null;
					_destinationSquareSelected=false;
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

	}
	
	BufferedImage boardImg=null;
	BufferedImage selectionImg=null;
	BufferedImage whitePawn=null;
	BufferedImage blackPawn=null;
	BufferedImage whiteKnight=null;
	BufferedImage blackKnight=null;
	BufferedImage whiteBishop=null;
	BufferedImage blackBishop=null;
	BufferedImage whiteRock=null;
	BufferedImage blackRock=null;
	BufferedImage whiteQueen=null;
	BufferedImage blackQueen=null;
	BufferedImage whiteKing=null;
	BufferedImage blackKing=null;
	
	public BoardGraphicPanel(final ChessGame game) {
		super();
		this._game=game;
		_boardHeight=_game.GetChessboard().GetHeight();
		_boardWidth=_game.GetChessboard().GetWidth();
		setLayout(new FlowLayout());
		Dimension preferredSize=new Dimension(_squareSize*_boardWidth+10,_squareSize*_boardHeight+10);
		setPreferredSize(preferredSize);
		try {
			boardImg=ImageIO.read(new File("Resources/Images/Chessboard.png"));
			selectionImg=ImageIO.read(new File("Resources/Images/Selection.png"));
			whitePawn=ImageIO.read(new File("Resources/Images/Pawn_white.png"));
			blackPawn=ImageIO.read(new File("Resources/Images/Pawn_black.png"));
			whiteKnight=ImageIO.read(new File("Resources/Images/Knight_white.png"));
			blackKnight=ImageIO.read(new File("Resources/Images/Knight_black.png"));
			whiteBishop=ImageIO.read(new File("Resources/Images/Bishop_white.png"));
			blackBishop=ImageIO.read(new File("Resources/Images/Bishop_black.png"));
			whiteRock=ImageIO.read(new File("Resources/Images/Rock_white.png"));
			blackRock=ImageIO.read(new File("Resources/Images/Rock_black.png"));
			whiteQueen=ImageIO.read(new File("Resources/Images/Queen_white.png"));
			blackQueen=ImageIO.read(new File("Resources/Images/Queen_black.png"));
			whiteKing=ImageIO.read(new File("Resources/Images/King_white.png"));
			blackKing=ImageIO.read(new File("Resources/Images/King_black.png"));
			
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		this.addMouseListener(new MouseHandler());
		
		ActionListener popupMenuListener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				PieceColor color=_game.GetChessboard().GetPiece(_squareFrom).GetColor();
				Piece p=SimplePieceFactory.GetInstance().MakeNewPiece(PieceType.valueOf(event.getActionCommand()), color);
				game.StoreAndMove(_squareFrom, _squareTo, p, SpecialMove.PROMOTION);
			}
		};
		
		_pawnPromotionMenu=new JPopupMenu();
		JMenuItem popupMenuItem;  
		for(int i=1;i<PieceType.values().length-1;i++) {
			popupMenuItem=new JMenuItem(PieceType.values()[i].toString());
			popupMenuItem.addActionListener(popupMenuListener);
			_pawnPromotionMenu.add(popupMenuItem);
		}
		
		_gameOverMenu=new JPopupMenu();
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d=(Graphics2D) g;
		g2d.drawImage(boardImg, 0, 0, null);
		if(_squareFrom!=null) {
			int x=_squareFrom.GetColumn()*_squareSize;
			int y=Math.abs(_squareFrom.GetRow()*_squareSize-(_boardHeight-1)*_squareSize);
			g2d.drawImage(selectionImg,x,y,null);
		}
		String boardRepresentation=_game.GetChessboard().toString();
		for(int i=0;i<boardRepresentation.length();i++) {
			int x=((i%_boardWidth)*_squareSize);
			int y=Math.abs(((i/_boardHeight)*_squareSize)-(_boardHeight-1)*_squareSize);
			switch(boardRepresentation.charAt(i)) {
			case 'P':
				g2d.drawImage(whitePawn, x+(_squareSize/2)-whitePawn.getWidth()/2, y+(_squareSize/2)-whitePawn.getHeight()/2, null);
				break;
			case 'p':
				g2d.drawImage(blackPawn, x+(_squareSize/2)-blackPawn.getWidth()/2, y+(_squareSize/2)-blackPawn.getHeight()/2, null);
				break;
			case 'N':
				g2d.drawImage(whiteKnight, x+(_squareSize/2)-whiteKnight.getWidth()/2, y+(_squareSize/2)-whiteKnight.getHeight()/2, null);
				break;
			case 'n':
				g2d.drawImage(blackKnight, x+(_squareSize/2)-blackKnight.getWidth()/2, y+(_squareSize/2)-blackKnight.getHeight()/2, null);
				break;
			case 'B':
				g2d.drawImage(whiteBishop, x+(_squareSize/2)-whiteBishop.getWidth()/2, y+(_squareSize/2)-whiteBishop.getHeight()/2, null);
				break;
			case 'b':
				g2d.drawImage(blackBishop, x+(_squareSize/2)-blackBishop.getWidth()/2, y+(_squareSize/2)-blackBishop.getHeight()/2, null);
				break;
			case 'R':
				g2d.drawImage(whiteRock, x+(_squareSize/2)-whiteRock.getWidth()/2, y+(_squareSize/2)-whiteRock.getHeight()/2, null);
				break;
			case 'r':
				g2d.drawImage(blackRock, x+(_squareSize/2)-blackRock.getWidth()/2, y+(_squareSize/2)-blackRock.getHeight()/2, null);
				break;
			case 'Q':
				g2d.drawImage(whiteQueen, x+(_squareSize/2)-whiteQueen.getWidth()/2, y+(_squareSize/2)-whiteQueen.getHeight()/2, null);
				break;
			case 'q':
				g2d.drawImage(blackQueen, x+(_squareSize/2)-blackQueen.getWidth()/2, y+(_squareSize/2)-blackQueen.getHeight()/2, null);
				break;
			case 'K':
				g2d.drawImage(whiteKing, x+(_squareSize/2)-whiteKing.getWidth()/2, y+(_squareSize/2)-whiteKing.getHeight()/2, null);
				break;
			case 'k':
				g2d.drawImage(blackKing, x+(_squareSize/2)-blackKing.getWidth()/2, y+(_squareSize/2)-blackKing.getHeight()/2, null);
				break;
			}
		}
	}
	
	public JPopupMenu GetGameOverMenu() { return _gameOverMenu; }
	public JPopupMenu GetPawnPromotionMenu() { return _pawnPromotionMenu; }
	public int GetSquareSize() { return _squareSize; }
	public int GetBoardWidth() { return _boardWidth; }
	public int GetBoardHeight() { return _boardHeight; }
	
	public void GameLoop() {
		while(true) {
			this.repaint();
			try {
				Thread.sleep(33);
			}
			catch(InterruptedException e) { }
		}
	}
}
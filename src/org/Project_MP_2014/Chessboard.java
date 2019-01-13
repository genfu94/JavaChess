package org.Project_MP_2014;

import java.util.Vector;

/**
 * Rappresenta una scacchiera di dimensione width*height tramite una
 * matrice di oggetti di tipo "Piece" e fornisce metodi utili per
 * interfacciarsi ad essa.
 * @author giuseppe
 *
 */
public class Chessboard {
	private int _width;
	private int _height;
	private Piece[][] _board;
	private ChessMoveNotation _lastMove=null;
	
	public Chessboard(int width, int height) {
		_width=width;
		_height=height;
		
		_board=new Piece[_height][_width];
		for(int r=0;r<_height;r++) {
			for(int c=0;c<_width;c++) {
				_board[r][c]=null;
			}
		}
	}
	
	public boolean IsSquareOccupiedByEnemy(Square s, PieceColor playerColor) {
		if(GetPiece(s)==null) { return false; }
		else { if(GetPiece(s).GetColor()==playerColor) { return false; } }
		return true;
	}
	
	public boolean IsSquareOccupiedByPlayerPiece(Square s, PieceColor playerColor) {
		if(GetPiece(s)==null) { return false; }
		else { if(GetPiece(s).GetColor()!=playerColor) { return false; } }
		return true;
	}
	
	/**
	 * Verifica se le caselle che vanno da src a dst rappresentati la linea specificata
	 * come parametro sono vuote.
	 * @param src	La casella sorgente.
	 * @param dst	La casella di destinazione.
	 * @param line	Specifica se le caselle che vanno da src a dst rappresentano una colonna, riga o diagonale.
	 * @return	Ritorna null se src-dst non rappresentano la linea specificata oppure se src=dst.
	 * 			Ritorna un vettore contenente le caselle [src, dst] se è possibile muovere un pezzo in (src, dst].
	 * 			Nel caso in cui non è possibile muovere il pezzo lungo la linea specificata, ritorna un vettore
	 * 			contenente tutte le caselle che si potrebbero percorrere per arrivare alla casella di destinazione
	 * 			esclusa la casella sorgente, con in testa una casella di colonna e riga = -1.
	 */
	public Vector<Square> IsLineEmpty(Square src, Square dst, LineType line) {
		PieceColor pieceColor=GetPiece(src).GetColor();
		Vector<Square> squares=new Vector<Square>();
		if(!line.RepresentALine(src, dst)) { return null; }
		
		Square squareSrc=src.clone();
		Square squareDst=dst.clone();
		int hOffset=((src.GetColumn()==dst.GetColumn())?0:((dst.GetColumn()-src.GetColumn()>0)?1:-1));
		int vOffset=((src.GetRow()==dst.GetRow())?0:(src.GetRow()-dst.GetRow()<0?1:-1));
		squareDst.SetColumn(squareDst.GetColumn());
		squareDst.SetRow(squareDst.GetRow());
		squareSrc.AddToColumn(hOffset);
		squareSrc.AddToRow(vOffset);
		for(;!squareSrc.equals(squareDst);squareSrc.AddToRow(vOffset),squareSrc.AddToColumn(hOffset)) {
			if(GetPiece(squareSrc)==null) {
				squares.add(squareSrc.clone());
			} else { break; }			
		}
		if(squareSrc.equals(squareDst)) {
			if(this.GetPiece(squareDst)==null || IsSquareOccupiedByEnemy(squareSrc, pieceColor)) {
				squares.add(0, src.clone());
				squares.add(squareSrc.clone());
			}
			else { squares.add(0, new Square(-1, -1)); }
		}
		else {
			if(GetPiece(squareSrc).GetColor()!=pieceColor) squares.add(squareSrc.clone());
			squares.add(0, new Square(-1, -1));
		}
		
		return squares;
	}
	
	/**
	 * Ritorna un vettore contenente tutte le caselle occupate da pezzi del tipo e del
	 * colore specificato. 
	 * @param pieceType		Il tipo di pezzo su cui effettuare la ricerca
	 * @param playerColor	Il colore del pezzo su cui effettuare la ricerca
	 * @return
	 */
	public Vector<Square> GetPiecesSquare(PieceType pieceType, PieceColor playerColor) {
		Square square=new Square();
		Vector<Square> piecesSquare=new Vector<Square>();
		
		//Fa una scansione di tutta la scacchiera e se trova un pezzo che soddisfa tutte
		//le richieste lo aggiunge al vettore
		for(square.SetRow(0);square.GetRow()<_height;square.SetRow(square.GetRow()+1)) {
			for(square.SetColumn(0);square.GetColumn()<_width;square.SetColumn(square.GetColumn()+1)) {
				if(GetPiece(square)!=null) {
					if(GetPiece(square).GetType()==pieceType && GetPiece(square).GetColor()==playerColor) {
						piecesSquare.add(square.clone());
					}
				}
			}
		}
		
		return piecesSquare;
	}
	
	/**
	 * Ritorna true se la mossa src-dst è legale, false altrimenti.
	 * N.B Non tiene conto di mosse speciali come l'arrocco o l'en-passant
	 * @param src	La casella di partenza
	 * @param dst	La casella di destinazione
	 * @param playerColor	Il colore del giocatore che deve muovere
	 * @return
	 */
	public boolean IsALegalMove(Square src, Square dst, PieceColor playerColor) {
		if(GetPiece(src)!=null) {
			if(GetPiece(dst)!=null) {
				if(GetPiece(dst).GetColor()==playerColor) { return false; }
			}
			if(GetPiece(src).GetColor()==playerColor) {
				if(GetPiece(src).GetMovePath(src, dst, this)!=null) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Verifica se la casella src occupata è minacciata da un pezzo di colore diverso.
	 * @param src	La casella su cui bisogna effettuare la verifica
	 * @param playerColor	Il colore del giocatore che vuole effettuare la verifica
	 * @return	true se la casella è minacciato, false altrimenti.
	 */
	public Vector<Vector<Square>> IsSquareSafe(Square src, PieceColor playerColor) {
		Vector<Vector<Square>> threats=new Vector<Vector<Square>>();
		Vector<Square> threatenedSquares;
		Square app=new Square();
		Piece p=GetPiece(src);
		for(app.SetRow(0); app.GetRow()<_height; app.AddToRow(1)) {
			for(app.SetColumn(0); app.GetColumn()<_width; app.AddToColumn(1)) {
				if(!src.equals(app) && IsSquareOccupiedByEnemy(app, playerColor)) {
					if(GetPiece(src)==null || IsSquareOccupiedByEnemy(src, playerColor))
						SetPiece(src, new Pawn(playerColor));
					threatenedSquares=(Vector<Square>) GetPiece(app).GetMovePath(app, src, this);
					SetPiece(src,p);
					if(threatenedSquares!=null) {
						threatenedSquares.remove(threatenedSquares.size()-1);
						threats.add(Utils.CloneSquareVector(threatenedSquares));
					}
				}
			}
		}
		
		return threats;
	}
	
	/**
	 * Ritorna true se la linea di estremi src,dst è minacciata da un pezzo avversario.
	 * Ritorna false altrimenti.
	 * @param src
	 * @param dst
	 * @param playerColor
	 * @return
	 */
	public boolean IsLineSafe(Square src, Square dst, PieceColor playerColor) {
		Square squareSrc=src.clone();
		Square squareDst=dst.clone();
		int hOffset=((src.GetColumn()==dst.GetColumn())?0:((dst.GetColumn()-src.GetColumn()>0)?1:-1));
		int vOffset=((src.GetRow()==dst.GetRow())?0:(src.GetRow()-dst.GetRow()<0?1:-1));
		squareDst.SetColumn(squareDst.GetColumn()+hOffset);
		squareDst.SetRow(squareDst.GetRow()+vOffset);
		for(;!squareSrc.equals(squareDst);squareSrc.SetRow(squareSrc.GetRow()+vOffset),squareSrc.SetColumn(squareSrc.GetColumn()+hOffset)) {
			if(IsSquareSafe(squareSrc,playerColor).size()!=0) { return false; }
		}
		return true;
	}
	
	/**
	 * Esegue la mossa src-dst.
	 * @param src
	 * @param dst
	 */
	public void MovePiece(Square src, Square dst) {
		GetPiece(src).SetMoved();
		SetPiece(dst, GetPiece(src));
		SetPiece(src, null);
	}
	
	public String toString() {
		String out=new String();
		for(int r=0;r<_height;r++) {
			for(int c=0;c<_width;c++) {
				if(_board[r][c]==null) { out+="."; }
				else { out+=_board[r][c]; }
			}
		}
		return out;
	}
	
	/********METODI GETTER E SETTER*******/
	public Piece GetPiece(int row, int column) { return _board[row][column]; }
	public void SetPiece(int row, int column, Piece p) { _board[row][column]=p; }
	public Piece GetPiece(Square s) { return _board[s.GetRow()][s.GetColumn()]; }
	public void SetPiece(Square s,Piece p) { _board[s.GetRow()][s.GetColumn()]=p; }
	public Piece[][] GetBoardRepresentation() { return _board; }
	public int GetWidth() { return _width; }
	public int GetHeight() { return _height; }
	public void SetLastMove(ChessMoveNotation lastMove) { _lastMove=lastMove; }
	public ChessMoveNotation GetLastMove() { return _lastMove; }
}

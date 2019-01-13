package org.Project_MP_2014;

import java.util.Vector;

/**
 * Rappresenta un pezzo, definendone il colore e fornendo interfacce per implementarne
 * il movimento.
 * @author giuseppe
 *
 */
public abstract class Piece {
	protected PieceColor _color;
	protected boolean _hasMoved;
	protected PieceType _type;
	
	/**
	 * Crea un nuovo pezzo definendone il colore.
	 * @param color
	 */
	public Piece(PieceType type, PieceColor color) {
		_type=type;
		_color=color;
		_hasMoved=false;
	}
	
	protected Vector<Square> GetPossibleMovesOnDiagonals(Square src, Chessboard board) {
		Vector<Square> possibleMovesOnDiagonal=new Vector<Square>();
		Square start=src.GetUpperLeftDiagonalSquareProjection();
		Square stop=src.GetLowerLeftDiagonalSquareProjection();
		Utils.<Square>MergeVectors(possibleMovesOnDiagonal, board.IsLineEmpty(src,start,LineType.DIAGONAL), 1);
		Utils.<Square>MergeVectors(possibleMovesOnDiagonal, board.IsLineEmpty(src,stop,LineType.DIAGONAL), 1);
		start=src.GetUpperRightDiagonalSquareProjection();
		stop=src.GetLowerRightDiagonalSquareProjection();
		Utils.<Square>MergeVectors(possibleMovesOnDiagonal, board.IsLineEmpty(src,start,LineType.DIAGONAL), 1);
		Utils.<Square>MergeVectors(possibleMovesOnDiagonal, board.IsLineEmpty(src,stop,LineType.DIAGONAL), 1);
		if(possibleMovesOnDiagonal.size()==0) { return null; }
		return possibleMovesOnDiagonal;
	}
	
	protected Vector<Square> GetPossibleMovesOnColumn(Square src, Chessboard board) {
		Vector<Square> possibleMovesOnColumn=new Vector<Square>();
		Utils.<Square>MergeVectors(possibleMovesOnColumn, board.IsLineEmpty(src, new Square(src.GetColumn(),0), LineType.COLUMN), 1);
		Utils.<Square>MergeVectors(possibleMovesOnColumn, board.IsLineEmpty(src, new Square(src.GetColumn(),board.GetHeight()-1), LineType.COLUMN),1);
		if(possibleMovesOnColumn.size()==0) { return null; }
		return possibleMovesOnColumn;
	}
	
	protected Vector<Square> GetPossibleMovesOnRow(Square src, Chessboard board) {
		Vector<Square> possibleMovesOnRow=new Vector<Square>();
		Utils.<Square>MergeVectors(possibleMovesOnRow, board.IsLineEmpty(src, new Square(0,src.GetRow()), LineType.ROW), 1);
		Utils.<Square>MergeVectors(possibleMovesOnRow, board.IsLineEmpty(src, new Square(board.GetWidth()-1,src.GetRow()), LineType.ROW), 1);
		if(possibleMovesOnRow.size()==0) { return null; }
		return possibleMovesOnRow;
	}
	
	protected Vector<Square> GetLineMovePath(Square src, Square dst, Chessboard board, LineType lineType) {
		Vector<Square> path;
		path=board.IsLineEmpty(src, dst, lineType);
		if(path!=null) {
			if(path.get(0).GetColumn()!=-1) return path;
		}
		return null;
	}
	
	public Vector<Square> GetPossibleMoves(Square src, Chessboard board) {
		Vector<Square> possibleMoves=new Vector<Square>();
		if(!src.IsAValidSquare()) { return null; }
		possibleMoves=GetPossibleMovesAux(src, board);
		if(possibleMoves==null) { return null; }
		if(possibleMoves.size()==0) { return null; }
		return possibleMoves;
	}
	
	public Vector<Square> GetMovePath(Square src, Square dst, Chessboard board) {
		Vector<Square> movePath=new Vector<Square>();
		if(!dst.IsAValidSquare()) { return null; }
		if(board.GetPiece(src)!=null && board.IsSquareOccupiedByPlayerPiece(dst, _color)) return null;
		movePath=GetMovePathAux(src, dst, board);
		if(movePath==null) { return null; }
		if(movePath.size()==0) return null;
		return movePath;
	}
	
	/************************METODI GETTER E SETTER******************************/
	public void SetMoved() { _hasMoved=true; }
	public boolean HasMoved() { return _hasMoved; }
	public PieceColor GetColor() { return _color; }
	public PieceType GetType() { return _type; }
	
	/*************************INTERFACCE DA IMPLEMENTARE*********************/
	public abstract Piece clone();
	
	/**
	 * Ritorna il percorso fatto dal pezzo nel caso in cui possa eseguire la mossa, altrimenti
	 * ritorna null.
	 * @param src	La casella di partenza del pezzo
	 * @param dst	La casella da raggiungere
	 * @param board
	 * @return
	 */
	abstract protected Vector<Square> GetMovePathAux(Square src, Square dst, Chessboard board);
	
	/**
	 * Ritorna true se il pezzo può effettuare la mossa, altrimenti lancia un eccezione 
	 * del tipo IllegalPieceMoveException.
	 * Il pezzo potrebbe anche lanciare un eccezione del tipo SpecialMoveExcpetion per gestire
	 * mosse eccezionali come l'en-passant o l'arrocco.
	 * @param from
	 * @param to
	 * @param board
	 * @return
	 * @throws IllegalPieceMoveException
	 * @throws SpecialMoveException
	 */
	abstract boolean CanMove(Square from,Square to,Chessboard board) throws IllegalPieceMoveException,SpecialMoveException;
	
	/**
	 * Ritorna tutte le caselle in cui il pezzo nella casella src può muovere.
	 * @param src	La casella su cui si trova il pezzo
	 * @param board	La scacchiera
	 * @return
	 */
	abstract protected Vector<Square> GetPossibleMovesAux(Square src, Chessboard board);
}
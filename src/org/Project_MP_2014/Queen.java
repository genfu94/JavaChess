package org.Project_MP_2014;

import java.util.Vector;

public class Queen extends Piece {
	public Queen(PieceColor color) {
		super(PieceType.QUEEN,color);
	}

	@Override
	protected Vector<Square> GetMovePathAux(Square src, Square dst, Chessboard board) {
		Vector<Square> path=new Vector<Square>();
		Utils.<Square>MergeVectors(path, GetLineMovePath(src, dst, board, LineType.COLUMN), 0);
		Utils.<Square>MergeVectors(path, GetLineMovePath(src, dst, board, LineType.ROW),0);
		Utils.<Square>MergeVectors(path, GetLineMovePath(src, dst, board, LineType.DIAGONAL), 0);
		return path;
	}
	
	protected Vector<Square> GetPossibleMovesAux(Square src, Chessboard board) {
		Vector<Square> possibleMoves=new Vector<Square>();
		Utils.<Square>MergeVectors(possibleMoves, GetPossibleMovesOnDiagonals(src,board), 0);
		Utils.<Square>MergeVectors(possibleMoves, GetPossibleMovesOnColumn(src,board), 0);
		Utils.<Square>MergeVectors(possibleMoves, GetPossibleMovesOnRow(src,board), 0);
		return possibleMoves;
	}
	
	public boolean CanMove(Square src, Square dst, Chessboard board) throws IllegalPieceMoveException {
		if(GetMovePath(src,dst,board)!=null) {
			return true;
		}
		throw new IllegalPieceMoveException(PieceType.QUEEN);
	}
	
	public String toString() {
		if(_color==PieceColor.WHITE) { return "Q"; }
		else { return "q"; }
	}
	
	public Piece clone() {
		Piece app=new Queen(this.GetColor());
		if(this.HasMoved()) { app.SetMoved(); }
		return app;
	}
}

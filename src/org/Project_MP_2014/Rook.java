package org.Project_MP_2014;

import java.util.Vector;

public class Rook extends Piece {
	public Rook(PieceColor color) {
		super(PieceType.ROOK,color);
	}
	
	protected Vector<Square> GetMovePathAux(Square src, Square dst, Chessboard board) {
		Vector<Square> path=new Vector<Square>();
		Utils.<Square>MergeVectors(path, GetLineMovePath(src, dst, board, LineType.COLUMN), 0);
		Utils.<Square>MergeVectors(path, GetLineMovePath(src, dst, board, LineType.ROW),0);
		return path;
	}
	
	protected Vector<Square> GetPossibleMovesAux(Square src, Chessboard board) {
		Vector<Square> possibleMoves=new Vector<Square>();
		Utils.<Square>MergeVectors(possibleMoves, GetPossibleMovesOnColumn(src,board), 0);
		Utils.<Square>MergeVectors(possibleMoves, GetPossibleMovesOnRow(src,board), 0);
		return possibleMoves;
	}
	
	public boolean CanMove(Square src,Square dst,Chessboard board) throws IllegalPieceMoveException{
		if(GetMovePath(src,dst,board)!=null) { return true; }
		throw new IllegalPieceMoveException(PieceType.ROOK);
	}
	
	public String toString() {
		if(_color==PieceColor.WHITE) { return "R"; }
		else { return "r"; }
	}
	
	public Piece clone() {
		Piece app=new Rook(this.GetColor());
		if(this.HasMoved()) { app.SetMoved(); }
		return app;
	}
}

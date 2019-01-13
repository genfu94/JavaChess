package org.Project_MP_2014;

import java.util.Vector;

public class Bishop extends Piece {
	public Bishop(PieceColor color) {
		super(PieceType.BISHOP,color);
	}
	
	@Override
	protected Vector<Square> GetMovePathAux(Square src, Square dst, Chessboard board) {
		return GetLineMovePath(src, dst, board, LineType.DIAGONAL);
	}
	
	protected Vector<Square> GetPossibleMovesAux(Square src, Chessboard board) {
		return GetPossibleMovesOnDiagonals(src, board);
	}
	
	public boolean CanMove(Square src, Square dst, Chessboard board) throws IllegalPieceMoveException {
		if(GetMovePath(src,dst,board)!=null) { return true; }
		throw new IllegalPieceMoveException(PieceType.BISHOP);
	}
	
	public String toString() {
		if(_color==PieceColor.WHITE) { return "B"; }
		else { return "b"; }
	}
	
	public Piece clone() {
		Piece app=new Bishop(this.GetColor());
		if(this.HasMoved()) { app.SetMoved(); }
		return app;
	}
}

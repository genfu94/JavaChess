package org.Project_MP_2014;

import java.util.Vector;

public class Pawn extends Piece {
	private int _direction;
	
	/**
	 * Crea un nuovo pedone del colore specificato
	 * @param color
	 */
	public Pawn(PieceColor color) {
		super(PieceType.PAWN,color);
		if(_color==PieceColor.WHITE) { _direction=1; }
		else { _direction=-1; }
	}
	
	private Vector<Square> GetNormalPawnMovePath(Square src, Square dst, Chessboard board) {
		Vector<Square> path=board.IsLineEmpty(src, dst, LineType.COLUMN);
		if(path!=null) { if(path.get(0).GetColumn()==-1) path=null; }
		if(path!=null && board.GetPiece(dst)==null &&
		  (dst.GetRow()-src.GetRow()==_direction ||
		  (!_hasMoved && dst.GetRow()-src.GetRow()==2*_direction))) {
			return path;
		}
		return null;
	}
	
	private Vector<Square> GetPawnEatingPath(Square src, Square dst, Chessboard board) {
		Vector<Square> path=board.IsLineEmpty(src, dst, LineType.DIAGONAL);
		if(path!=null) { if(path.get(0).GetColumn()==-1) path=null; }
		if(path!=null && board.GetPiece(dst)!=null &&
		   Math.abs(dst.GetColumn()-src.GetColumn())==1 &&
		   dst.GetRow()-src.GetRow()==_direction) {
			return path;
		}
		return null;
	}
	
	boolean IsEnPassantPossible(Square src, Square dst, Chessboard board) {
		ChessMoveNotation lastMove=board.GetLastMove();
		if(lastMove!=null) {
			if(GetLineMovePath(src, dst, board, LineType.DIAGONAL)!=null &&
			   lastMove.GetMovingPiece().GetType()==PieceType.PAWN &&
			   lastMove.GetMovingPiece().GetColor()!=_color &&
			   Math.abs(lastMove.GetSourceSquare().GetRow()-lastMove.GetDestinationSquare().GetRow())==2 &&
			   lastMove.GetDestinationSquare().equals(new Square(dst.GetColumn(), dst.GetRow()-_direction))) {
				return true;
			}
		}
		return false;
	}
	
	private Vector<Square> GetEnPassantPath(Square src, Square dst, Chessboard board) {
		Vector<Square> path=null;
		if(IsEnPassantPossible(src, dst, board)) { path=board.IsLineEmpty(src, dst, LineType.DIAGONAL); }
		return path;
	}
	
	protected Vector<Square> GetMovePathAux(Square src, Square dst, Chessboard board) {
		Vector<Square> path=new Vector<Square>();
		Utils.<Square>MergeVectors(path, GetNormalPawnMovePath(src, dst, board), 0);
		Utils.<Square>MergeVectors(path, GetPawnEatingPath(src,dst, board), 0);
		Utils.<Square>MergeVectors(path, GetEnPassantPath(src, dst, board), 0);
		return path;
	}
	
	protected Vector<Square> GetPossibleMovesAux(Square src, Chessboard board) {
		Vector<Square> possibleMoves=new Vector<Square>();
		Square app;
		app=new Square(src.GetColumn(),src.GetRow()+_direction);
		if(GetMovePath(src, app, board)!=null) { possibleMoves.add(app); }
		app=new Square(src.GetColumn(), src.GetRow()+_direction*2);
		if(GetMovePath(src, app, board)!=null) { possibleMoves.add(app); }
		app=new Square(src.GetColumn()-1, src.GetRow()+_direction);
		if(app.IsAValidSquare())  if(GetMovePath(src, app, board)!=null) { possibleMoves.add(app); }
		app=new Square(src.GetColumn()+1, src.GetRow()+_direction);
		if(app.IsAValidSquare())  if(GetMovePath(src, app, board)!=null) { possibleMoves.add(app); }
		return possibleMoves;
	}
	
	public boolean CanMove(Square src, Square dst, Chessboard board) throws IllegalPieceMoveException, SpecialMoveException {
		if(GetMovePath(src,dst,board)!=null) {
			if(IsEnPassantPossible(src, dst, board)) {
				throw new EnPassantException(board.GetPiece(new Square(dst.GetColumn(), dst.GetRow()-_direction))); }
			if(dst.GetRow()==board.GetHeight()-1 || dst.GetRow()==0) { throw new PromotionException(src, dst); }
			return true;
		}
		throw new IllegalPieceMoveException(PieceType.PAWN);
	}
	
	public String toString() {
		if(_color==PieceColor.WHITE) { return "P"; }
		else { return "p"; }
	}
	
	public Piece clone() {
		Piece app=new Pawn(this.GetColor());
		if(this.HasMoved()) { app.SetMoved(); }
		return app;
	}
}

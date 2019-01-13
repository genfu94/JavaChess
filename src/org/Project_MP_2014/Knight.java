package org.Project_MP_2014;

import java.util.Vector;

public class Knight extends Piece {
	public Knight(PieceColor color) {
		super(PieceType.KNIGHT,color);
	}
	
	public boolean CanMove(Square src,Square dst,Chessboard board) throws IllegalPieceMoveException{
		if(GetMovePath(src,dst,board)!=null) { return true; }
		throw new IllegalPieceMoveException(PieceType.KNIGHT);
	}

	@Override
	protected Vector<Square> GetMovePathAux(Square src, Square dst, Chessboard board) {
		Vector<Square> path=new Vector<Square>();;
		if(((Math.abs(src.GetColumn()-dst.GetColumn())==2 && Math.abs(src.GetRow()-dst.GetRow())==1) ||
		   (Math.abs(src.GetRow()-dst.GetRow())==2 && Math.abs(src.GetColumn()-dst.GetColumn())==1))) {
			path.add(src);
			path.add(dst);
		}
		return path;
	}
	
	protected Vector<Square> GetPossibleMovesAux(Square src, Chessboard board) {
		Vector<Square> possibleMoves=new Vector<Square>();
		Square s=new Square();
		for(s.SetRow(src.GetRow()-2);s.GetRow()<=src.GetRow()+2;s.SetRow(s.GetRow()+1)) {
			for(s.SetColumn(src.GetColumn()-2);s.GetColumn()<=src.GetColumn()+2;s.SetColumn(s.GetColumn()+1)) {
				if((s.GetRow()>=0 && s.GetRow()<=board.GetHeight()-1) 	  &&
				   (s.GetColumn()>=0 && s.GetColumn()<=board.GetWidth()-1)) {
					try {
						if(CanMove(src,s,board)) {
							possibleMoves.add(s.clone());
						}
					} catch (IllegalPieceMoveException e) {
					}
				}
			}
		}
		return possibleMoves;
	}
	
	public String toString() {
		if(_color==PieceColor.WHITE) { return "N"; }
		else { return "n"; }
	}
	
	public Piece clone() {
		Piece app=new Knight(this.GetColor());
		if(this.HasMoved()) { app.SetMoved(); }
		return app;
	}
}

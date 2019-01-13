package org.Project_MP_2014;

import java.util.Vector;

public class King extends Piece {
	public King(PieceColor color) {
		super(PieceType.KING,color);
	}
	
	private boolean IsCastlingPossible(Square src, Square dst, Chessboard board) {
		Square rockFrom=src.clone();
		Square rockTo=src.clone();
		if(Math.abs(src.GetColumn()-dst.GetColumn())==2 && Math.abs(src.GetRow()-dst.GetRow())==0) {
			rockFrom.SetColumn((src.GetColumn()-dst.GetColumn()>0)?0:board.GetWidth()-1);
			rockTo.SetColumn((src.GetColumn()-dst.GetColumn()>0)?dst.GetColumn()+1:dst.GetColumn()-1);
			if(board.GetPiece(rockFrom)!=null) {
				if(!board.GetPiece(rockFrom).HasMoved() && !board.GetPiece(src).HasMoved()) {
					if(board.IsLineSafe(src, rockFrom, board.GetPiece(src).GetColor())) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	protected Vector<Square> GetMovePathAux(Square src, Square dst, Chessboard board) {
		Vector<Square> path=null;
		if(board.IsSquareOccupiedByPlayerPiece(dst, _color)) return null;
		if(IsCastlingPossible(src, dst, board)) {
			path=new Vector<Square>();
			path.add(dst);
		}
		if(Math.abs(src.GetColumn()-dst.GetColumn())<=1 &&
		   Math.abs(src.GetRow()-dst.GetRow())<=1) {
			path=new Vector<Square>();
			path.add(src);
			path.add(dst);
		}
		return path;
	}
	
	public boolean CanMove(Square src, Square dst, Chessboard board) throws IllegalPieceMoveException,SpecialMoveException {
		if(IsCastlingPossible(src, dst, board)) {
			Square rockFrom=src.clone();
			Square rockTo=src.clone();
			rockFrom.SetColumn((src.GetColumn()-dst.GetColumn()>0)?0:board.GetWidth()-1);
			rockTo.SetColumn((src.GetColumn()-dst.GetColumn()>0)?dst.GetColumn()+1:dst.GetColumn()-1);
			SpecialMove castlingType=((dst.GetColumn()-src.GetColumn()>0)?SpecialMove.SHORT_CASTLING:SpecialMove.LONG_CASTLING);
			throw new CastlingException(castlingType,rockFrom, rockTo);
		}
		
		//Gestisci il normale movimento di re
		if(GetMovePath(src,dst,board)!=null) {
			return true;
		}
		
		throw new IllegalPieceMoveException(PieceType.KING);
	}
	
	protected Vector<Square> GetPossibleMovesAux(Square src, Chessboard board) {
		Vector<Square> possibleMoves=new Vector<Square>();
		Square s=new Square();
		for(s.SetRow(src.GetRow()-1);s.GetRow()<=src.GetRow()+1;s.SetRow(s.GetRow()+1)) {
			for(s.SetColumn(src.GetColumn()-1);s.GetColumn()<=src.GetColumn()+1;s.SetColumn(s.GetColumn()+1)) {
				if((s.GetRow()>=0 && s.GetRow()<=board.GetHeight()-1) 	  &&
				   (s.GetColumn()>=0 && s.GetColumn()<=board.GetWidth()-1)  &&
				   !s.equals(src)) {
					if(board.IsALegalMove(src, s, _color)) {
						possibleMoves.add(s.clone());
					}
				}
			}
		}
		s=src.clone();
		s.AddToColumn(2);
		if(s.IsAValidSquare()) {
		if(board.IsALegalMove(src, s, _color)) {
			possibleMoves.add(s.clone());
		}}
		s.AddToColumn(-4);
		if(s.IsAValidSquare()) {
		if(board.IsALegalMove(src, s, _color)) {
			possibleMoves.add(s.clone());
		} }
		return possibleMoves;
	}
	
	public String toString() {
		if(_color==PieceColor.WHITE) { return "K"; }
		else { return "k"; }
	}
	
	public Piece clone() {
		Piece app=new King(this.GetColor());
		if(this.HasMoved()) { app.SetMoved(); }
		return app;
	}
}

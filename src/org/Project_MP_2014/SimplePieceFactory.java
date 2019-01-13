package org.Project_MP_2014;

public class SimplePieceFactory {
	private static SimplePieceFactory _instance=null;
	
	private SimplePieceFactory() {}
	public static SimplePieceFactory GetInstance() {
		if(_instance==null) {
			_instance=new SimplePieceFactory();
		}
		return _instance;
	}
	public Piece MakeNewPiece(PieceType type, PieceColor color) {
		Piece piece=null;
		switch(type) {
		case KING:
			piece=new King(color);
			break;
		case QUEEN:
			piece=new Queen(color);
			break;
		case ROOK:
			piece=new Rook(color);
			break;
		case BISHOP:
			piece=new Bishop(color);
			break;
		case KNIGHT:
			piece=new Knight(color);
			break;
		case PAWN:
			piece=new Pawn(color);
			break;
		}
		return piece;
	}
	
	public Piece MakeNewPiece(char pieceCode) {
		if(pieceCode == 'P') { return MakeNewPiece(PieceType.PAWN, PieceColor.WHITE); }
		if(pieceCode == 'p') { return MakeNewPiece(PieceType.PAWN, PieceColor.BLACK); }
		if(pieceCode == 'N') { return MakeNewPiece(PieceType.KNIGHT, PieceColor.WHITE); }
		if(pieceCode == 'n') { return MakeNewPiece(PieceType.KNIGHT, PieceColor.BLACK); }
		if(pieceCode == 'R') { return MakeNewPiece(PieceType.ROOK, PieceColor.WHITE); }
		if(pieceCode == 'r') { return MakeNewPiece(PieceType.ROOK, PieceColor.BLACK); }
		if(pieceCode == 'B') { return MakeNewPiece(PieceType.BISHOP, PieceColor.WHITE); }
		if(pieceCode == 'b') { return MakeNewPiece(PieceType.BISHOP, PieceColor.BLACK); }
		if(pieceCode == 'K') { return MakeNewPiece(PieceType.KING, PieceColor.WHITE); }
		if(pieceCode == 'k') { return MakeNewPiece(PieceType.KING, PieceColor.BLACK); }
		if(pieceCode == 'Q') { return MakeNewPiece(PieceType.QUEEN, PieceColor.WHITE); }
		if(pieceCode == 'q') { return MakeNewPiece(PieceType.QUEEN, PieceColor.BLACK); }
		return null;
	}
}

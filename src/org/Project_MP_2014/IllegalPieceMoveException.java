package org.Project_MP_2014;

/**
 * Eccezione lanciata nel caso in cui si vuole muovere un pezzo
 * violando le sue regole di movimento.
 * @author giuseppe
 *
 */
@SuppressWarnings("serial")
public class IllegalPieceMoveException extends IllegalMoveException {
	PieceType _pieceType;
	
	public IllegalPieceMoveException(PieceType pieceType) {
		super(IllegalMove.ILLEGAL_PIECE_MOVE);
		_pieceType=pieceType;
	}
	
	public void PrintErrorMessage() {
		System.out.print("A ");
		System.out.print(_pieceType);
		System.out.print(" can't move that way!\n");
	}
}

package org.Project_MP_2014;

@SuppressWarnings("serial")
public class EnPassantException extends SpecialMoveException {
	Piece _piece;
	public EnPassantException(Piece p) {
		super(SpecialMove.ENPASSANT);
		_piece=p;
	}
}

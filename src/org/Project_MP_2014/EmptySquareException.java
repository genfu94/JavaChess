package org.Project_MP_2014;

/**
 * Eccezione lanciata nel caso in cui una mossa effettuata
 * ha una casella sorgente vuota.
 * @author giuseppe
 *
 */
@SuppressWarnings("serial")
public class EmptySquareException extends IllegalMoveException {
	private Square _square;
	
	public EmptySquareException(Square s) {
		super(IllegalMove.EMPTY_SOURCE_SQUARE);
		_square=s;
	}
	
	public void PrintErrorMessage() {
		System.out.print(_square);
		System.out.print(" is empty!\n");
	}
}

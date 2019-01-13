package org.Project_MP_2014;

/**
 * Eccezione lanciata nel caso in cui si vuole muovere
 * un pezzo in una casella già occupata da un altro
 * dello stesso colore.
 * @author giuseppe
 *
 */
@SuppressWarnings("serial")
public class OccupiedSquareException extends IllegalMoveException {
	private Square _square;
	
	public OccupiedSquareException(Square s) {
		super(IllegalMove.OCCUPIED_DEST_SQUARE);
		_square=s;
	}
	
	public void PrintErrorMessage() {
		System.out.print("There's already a piece on ");
		System.out.print(_square);
		System.out.print("\n");
	}
}

package org.Project_MP_2014;

/**
 * Eccezione lanciata nel caso in cui si vuole muovere un pezzo
 * del colore sbagliato.
 * @author giuseppe
 *
 */
@SuppressWarnings("serial")
public class WrongTurnException extends IllegalMoveException {
	private PieceColor _actualTurn;
	
	public WrongTurnException(PieceColor actualTurn) {
		super(IllegalMove.WRONG_TURN);
		_actualTurn=actualTurn;
	}
	
	public void PrintErrorMessage() {
		System.out.print("It's up to the ");
		System.out.print(_actualTurn);
		System.out.print(" to make a move!");
	}
}
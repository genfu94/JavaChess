package org.Project_MP_2014;

/**
 * Lanciata nel caso in cui il re del giocatore che ha mosso si trova sotto scacco.
 */
@SuppressWarnings("serial")
public class CheckException extends IllegalMoveException {
	public CheckException() {
		super(IllegalMove.CHECK_EXCEPTION);
	}

}

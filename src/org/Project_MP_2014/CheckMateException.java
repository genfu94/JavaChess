package org.Project_MP_2014;

@SuppressWarnings("serial")
public class CheckMateException extends ChessGameOverException {
	PieceColor _gameWinner;
	
	public CheckMateException(PieceColor turn) {
		super(GameOverType.CHECK_MATE);
		_gameWinner=turn;
	}
}

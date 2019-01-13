package org.Project_MP_2014;

@SuppressWarnings("serial")
public class DrawException extends ChessGameOverException{
	public enum DrawType { STALEMATE, IMPOSSIBILITY_OF_CHECKMATE, FIFTY_MOVES, THREEFOLD_REPETITION };
	PieceColor _player;
	DrawType _type;
	public DrawException(DrawType drawType, PieceColor player) {
		super(GameOverType.DRAW);
		_type=drawType;
		_player=player;
	}
}

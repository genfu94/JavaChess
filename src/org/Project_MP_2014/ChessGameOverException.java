package org.Project_MP_2014;

@SuppressWarnings("serial")
public class ChessGameOverException extends ChessRuleException {
	public enum GameOverType {DRAW,CHECK_MATE};
	
	GameOverType _type;
	public ChessGameOverException(GameOverType type) {
		super("GameOver");
		_type=type;
	}
}

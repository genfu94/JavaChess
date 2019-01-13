package org.Project_MP_2014;

/**
 * Eccezione che identifica qualsiasi mossa illegale.
 * @author giuseppe
 *
 */
@SuppressWarnings("serial")
public class IllegalMoveException extends ChessRuleException {
	public enum IllegalMove { WRONG_TURN, EMPTY_SOURCE_SQUARE, OCCUPIED_DEST_SQUARE, ILLEGAL_PIECE_MOVE, CHECK_EXCEPTION} ;
	public IllegalMove _type;
	
	public IllegalMoveException(IllegalMove type) {
		super("IllegalMove");
		_type=type;
	}
}
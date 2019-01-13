package org.Project_MP_2014;

/**
 * Eccezione che identifica qualsiasi mossa che non è contemplata
 * nel normale comportamento dei pezzi.
 * @author giuseppe
 *
 */
@SuppressWarnings("serial")
public class SpecialMoveException extends ChessRuleException {
	SpecialMove _type;
	
	public SpecialMoveException(SpecialMove type) {
		super("SpecialMove");
		_type=type;
	}
}

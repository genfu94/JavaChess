package org.Project_MP_2014;

/**
 * Eccezione lanciata in caso di arrocco.
 * @author giuseppe
 *
 */
@SuppressWarnings("serial")
public class CastlingException extends SpecialMoveException {
	Square _rockFrom;
	Square _rockTo;
	SpecialMove _type;
	
	public CastlingException(SpecialMove type, Square rockFrom, Square rockTo) {
		super(type);
		_rockFrom=rockFrom;
		_rockTo=rockTo;
		_type=type;
	}
}

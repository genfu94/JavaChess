package org.Project_MP_2014;

/**
 * Eccezione base che la classe ChessGame può lanciare.
 * @author giuseppe
 *
 */
@SuppressWarnings("serial")
public class ChessRuleException extends Exception {
	public ChessRuleException(String exceptionType) {
		super(exceptionType);
	}
}

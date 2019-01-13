package org.Project_MP_2014;

/**
 * Eccezione lanciata nel caso di promozione di un pedone.
 * @author giuseppe
 *
 */
@SuppressWarnings("serial")
public class PromotionException extends SpecialMoveException {
	Square dst;
	Square src;
	
	public PromotionException(Square src, Square dst) {
		super(SpecialMove.PROMOTION);
		this.dst=dst;
		this.src=src;
	}
}

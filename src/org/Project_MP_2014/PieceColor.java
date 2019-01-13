package org.Project_MP_2014;

/**
 * Enumerazione che elenca i colori dei pezzi sulla scacchiera.
 * @author giuseppe
 *
 */
public enum PieceColor {
	WHITE,
	BLACK;
	
	public PieceColor GetNextVal() {
		return this.ordinal() < PieceColor.values().length - 1
		         ? PieceColor.values()[this.ordinal() + 1]
		         : PieceColor.values()[0];
	}
	
	public PieceColor GetPrevVal() {
		return this.ordinal()>0 &&  this.ordinal()<=PieceColor.values().length-1
		         ? PieceColor.values()[this.ordinal()-1]
		         : PieceColor.values()[PieceColor.values().length - 1];
	}
}

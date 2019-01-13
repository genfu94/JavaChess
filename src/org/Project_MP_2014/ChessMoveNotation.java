package org.Project_MP_2014;

/**
 * Salva le informazioni per la rappresentazione di una mossa.
 * @author giuseppe
 *
 */
public class ChessMoveNotation {
	PieceColor _player;
	Square _src;
	Square _dst;
	Piece _moving;
	Piece _eaten;
	SpecialMove _specialMove;
	boolean _toBeRemoved=false;
	
	public ChessMoveNotation(Square Source, Square Destination, Piece moving,Piece eaten) {
		_src=Source.clone();
		_dst=Destination.clone();
		_eaten=((eaten!=null)?eaten.clone():null);
		_moving=moving.clone();
		_specialMove=SpecialMove.NONE;
	}
	
	public ChessMoveNotation(Square Source, Square Destination, Piece moving,Piece eaten,SpecialMove specialMove) {
		_src=Source.clone();
		_dst=Destination.clone();
		_eaten=((eaten!=null)?eaten.clone():null);
		_moving=moving.clone();
		_specialMove=specialMove;
	}
	
	public Square GetSourceSquare() { return _src; }
	public Square GetDestinationSquare() { return _dst; }
	public Piece GetEatenPiece() { return _eaten; }
	public Piece GetMovingPiece() { return _moving; }
	public SpecialMove GetSpecialMove() { return _specialMove; }
	public void SetToBeRemoved(boolean flag) { _toBeRemoved=flag; }
	public boolean GetToBeRemoved() { return _toBeRemoved; }
	
	public String toString() {
		String out="";
		switch(_specialMove) {
			case NONE:
				String separator=((_eaten!=null)?"x":"-");
				out=_src.toString()+separator+_dst.toString();
				break;
			case SHORT_CASTLING:
				out="0-0";
				break;
			case LONG_CASTLING:
				out="0-0-0";
				break;
			case PROMOTION:
				out=_src.toString()+"-"+_dst.toString()+"="+_eaten.toString().toUpperCase();
				break;
			case ENPASSANT:
				out=_src.toString()+"-"+_dst.toString()+"e.p";
				break;
		}
		return out;
	}
}

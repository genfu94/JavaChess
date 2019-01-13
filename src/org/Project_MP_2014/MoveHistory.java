package org.Project_MP_2014;

import java.util.Vector;

/**
 * Classe che permette il salvataggio delle mosse effettuate durante una partita.
 * @author giuseppe
 *
 */
public class MoveHistory {
	private Vector<ChessMoveNotation> _history=new Vector<ChessMoveNotation>();
	private int _currentMove=0;
	
	/**
	 * Salva una mossa del tipo src-dst.
	 * @param from
	 * @param to
	 * @param moving
	 * @param eaten
	 */
	public void AddMove(Square src,Square dst,Piece moving,Piece eaten) {
		RemoveUndoedMoves();
		_history.add(new ChessMoveNotation(src,dst,moving,eaten));
		_currentMove+=1;
	}
	
	/**
	 * Salva una mossa speciale indicandone il tipo.
	 * NB:
	 * Per l'arrocco specificare in "eaten" la torre da muovere assieme al re.
	 * Per la promozione specificare in "eaten" il pezzo scelto.
	 * @param src
	 * @param dst
	 * @param moving
	 * @param eaten
	 * @param specialMove
	 */
	public void AddMove(Square src,Square dst,Piece moving,Piece eaten, SpecialMove specialMove) {
		RemoveUndoedMoves();
		_history.add(new ChessMoveNotation(src,dst,moving,eaten,specialMove));
		_currentMove+=1;
	}
	
	public String toString() {
		String out="";
		for(int i=0,j=1; i<_history.size();i++) {
			if(i%2==0) { out+=String.valueOf(j++)+"."; }
			out+=_history.get(i).toString()+" ";
		}
		return out;
	}
	
	/**
	 * Cancella l'ultima mossa effettuata dalla lista.
	 * @param board
	 */
	public void TemporaryUndo(Chessboard board) {
		Square rock;
		if(_currentMove-1>=0) {
			ChessMoveNotation move=_history.get(_currentMove-1);
			_history.get(_currentMove-1).SetToBeRemoved(true);
			_currentMove-=1;
			switch(move.GetSpecialMove()) {
			case PROMOTION:
			case NONE:
				board.SetPiece(move.GetSourceSquare(), move.GetMovingPiece());
				board.SetPiece(move.GetDestinationSquare(), move.GetEatenPiece());
				break;
			case SHORT_CASTLING:
				board.SetPiece(move.GetSourceSquare(), move.GetMovingPiece());
				board.SetPiece(move.GetDestinationSquare(), null);
				rock=move.GetDestinationSquare().clone();
				rock.SetColumn(move.GetDestinationSquare().GetColumn()-1);
				board.SetPiece(rock,null);
				rock.SetColumn(move.GetDestinationSquare().GetColumn()+1);
				board.SetPiece(rock,move.GetEatenPiece());
				break;
			case LONG_CASTLING:
				board.SetPiece(move.GetSourceSquare(), move.GetMovingPiece());
				board.SetPiece(move.GetDestinationSquare(), null);
				rock=move.GetDestinationSquare().clone();
				rock.SetColumn(move.GetDestinationSquare().GetColumn()+1);
				board.SetPiece(rock,null);
				rock.SetColumn(move.GetDestinationSquare().GetColumn()-2);
				board.SetPiece(rock,move.GetEatenPiece());
				break;
			case ENPASSANT:
				int pawnDirection=move.GetDestinationSquare().GetRow()-move.GetSourceSquare().GetRow();
				Square app=new Square(move.GetDestinationSquare().GetColumn(), move.GetDestinationSquare().GetRow()-pawnDirection);
				board.SetPiece(app, move.GetEatenPiece());
				board.SetPiece(move.GetDestinationSquare(), null);
				board.SetPiece(move.GetSourceSquare(), move.GetMovingPiece());
				break;
			default: break;
			}
		}
	}
	
	public void Undo(Chessboard board) {
		TemporaryUndo(board);
		_history.removeElementAt(_currentMove);
	}
	
	private void RemoveUndoedMoves() {
		int j=_history.size()-1;
		while(j>0) {
			if(_history.get(j).GetToBeRemoved()) { _history.removeElementAt(j); }
			else { break; }
			j=_history.size()-1;
		}
		if(_history.size()==1) { if(_history.get(0).GetToBeRemoved()) { _history.removeElementAt(0); } }
	}
	
	public ChessMoveNotation[] toArray() {
		ChessMoveNotation[] historyArray=new ChessMoveNotation[_history.size()];
		return _history.toArray(historyArray);
	}
	
	public ChessMoveNotation GetCurrentMove() { return toArray()[_currentMove-1]; }
	public int GetCurrentMoveIndex() { return _currentMove; }
}

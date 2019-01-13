package org.Project_MP_2014;

import java.util.Vector;

import org.Project_MP_2014.DrawException.DrawType;

/**
 * Classe che gestisce una partita di scacchi
 * @author giuseppe
 *
 */
public class ChessGame {
	private PieceColor _currentTurn;
	//La classe che gestisce la scacchiera
	private Chessboard _chessboard;
	//Lo storico delle mosse
	private MoveHistory _history = new MoveHistory();
	private boolean _gameOver=false;
	//La posizione iniziale della scacchiera
	//Il primo pezzo è quello in posizione a1
	private static final String[] StandardInitialPosition = { 
		"rnbqkbnr",
		"pppppppp",
		"........",
		"........",
		"........",
		"........",
		"PPPPPPPP",
		"RNBQKBNR" };
	private static String[] _unusualInitialPosition=null;
	private boolean _standardInitialPosition=true;
	/**
	 * Crea una nuova scacchiera di dimensione 8x8 e setta la usuale posizione
	 * dei pezzi. Da la mossa al bianco.
	 */
	public ChessGame() {
		SetPositionOnBoard(StandardInitialPosition);
		_currentTurn = PieceColor.WHITE;
	}
	
	public ChessGame(String[] p) {
		SetPositionOnBoard(p);
		_standardInitialPosition=false;
		_unusualInitialPosition=p;
		_currentTurn = PieceColor.WHITE;
	}
	
	private void SetPositionOnBoard(String[] p) {
		Piece piece;
		_chessboard = new Chessboard(8,8);
		for(int r=0; r<_chessboard.GetHeight(); r++) {
			for(int c = 0; c<_chessboard.GetWidth(); c++) {
				piece=SimplePieceFactory.GetInstance().MakeNewPiece(p[r].charAt(c));
				_chessboard.SetPiece(_chessboard.GetHeight()-1-r,c,piece);
			}
		}
	}
	
	private void SetLastMove() {
		if(_history.GetCurrentMoveIndex()-1<0) { _chessboard.SetLastMove(null); }
		else {
			if(_history.GetCurrentMoveIndex()-1>=_history.toArray().length) {
				_chessboard.SetLastMove(_history.toArray()[_history.toArray().length-1]);
			}
			else { _chessboard.SetLastMove(_history.toArray()[_history.GetCurrentMoveIndex()-1]); }
		}
	}
	
	/**
	 * Esegue la mossa specificata e setta il turno al prossimo giocatore.
	 * @param src La casella sorgente
	 * @param dst La casella di destinazione
	 * @param p   Riferimento ad un pezzo "extra", da utilizzare ad esempio per la promozione
	 * 			  del pedone.
	 * @param specialMove Settare a NONE se è una mossa normale, specificare invece
	 * 					  il tipo di mossa speciale se presente.
	 */
	public void StoreAndMove(Square src, Square dst, Piece p, SpecialMove specialMove) {
		switch(specialMove) {
			case NONE:
				_history.AddMove(src, dst, _chessboard.GetPiece(src), _chessboard.GetPiece(dst));
				_chessboard.MovePiece(src,dst);
				break;
			case LONG_CASTLING:
			case SHORT_CASTLING:
				Square rockFrom=src.clone();	//La casella della torre che occorre muovere
				Square rockTo=src.clone();		//La casella in cui bisogna spostare la torre
				rockFrom.SetColumn((src.GetColumn()-dst.GetColumn()>0)?0:7);
				rockTo.SetColumn((src.GetColumn()-dst.GetColumn()>0)?dst.GetColumn()+1:dst.GetColumn()-1);
				_history.AddMove(src, dst, _chessboard.GetPiece(src), _chessboard.GetPiece(rockFrom), specialMove);
				_chessboard.MovePiece(src,dst);
				_chessboard.MovePiece(rockFrom,rockTo);
				break;
			case PROMOTION:
				_history.AddMove(src, dst, _chessboard.GetPiece(src), p, specialMove);
				_chessboard.SetPiece(src, null);
				_chessboard.SetPiece(dst,p);
				break;
			case ENPASSANT:
				_history.AddMove(src, dst, _chessboard.GetPiece(src), p, specialMove);
				int pawnDirection=dst.GetRow()-src.GetRow();
				Square app=new Square(dst.GetColumn(), dst.GetRow()-pawnDirection);
				_chessboard.MovePiece(src, dst);
				_chessboard.SetPiece(app, null);
		}
		SetLastMove();
		_currentTurn=_currentTurn.GetNextVal();
	}
	
	/**
	 * Funzione di verifica dello scacco matto.
	 * @param playerColor	Il colore del giocatore su cui verificare lo scacco matto
	 * @return	true se il giocatore playerColor è in scacco matto, false altrimenti
	 */
	private boolean CheckMate(PieceColor playerColor) {
		//La casella su cui si trova attualmente il re del colore specificato
		Square kingSquare=_chessboard.GetPiecesSquare(PieceType.KING, playerColor).get(0);
		//Le caselle che minacciano il re
		Vector<Vector<Square>> kingThreats=_chessboard.IsSquareSafe(kingSquare, playerColor);
		
		//Se nessuna casella minaccia il re allora non c'è scacco
		//di conseguenza non ci può essere lo scacco matto
		if(kingThreats.size()==0) { return false; }
		
		//Se invece c'è scacco, controlla se un pezzo, che non sia il re stesso, può
		//eliminare la minaccia, inserendosi tra il pezzo attaccante ed il re oppure 
		//mangiando la pedina interessata
		Square s=new Square();
		for(s.SetRow(0);s.GetRow()<_chessboard.GetHeight();s.AddToRow(1)) {
			for(s.SetColumn(0);s.GetColumn()<_chessboard.GetWidth();s.AddToColumn(1)) {
				for(Vector<Square> v : kingThreats) {
					for(Square threat : v) {
						if(!s.equals(kingSquare) && 
							_chessboard.IsALegalMove(s,threat,playerColor)) {
							StoreAndMove(s.clone(), threat, null, SpecialMove.NONE);
							if(_chessboard.IsSquareSafe(kingSquare, playerColor).size()==0) { Undo(); return false; }
							Undo();
						}
					}
				}
			}
		}
		
		//Se nessun pezzo alleato ha potuto difendere il re, cerca la fuga di quest'ultimo
		for(Square square:_chessboard.GetPiece(kingSquare).GetPossibleMoves(kingSquare, _chessboard)) {
			if(_chessboard.IsSquareSafe(square, playerColor).size()==0) { return false; }
		}
		
		return true;
	}
	
	/**
	 * Verifica se la partita è in stallo.
	 * @param playerColor	Il colore del giocatore su cui verificare lo stallo
	 * @return	true se il giocatore non ha nessuna mossa a disposizione, false altrimenti.		
	 */
	public boolean StaleMate(PieceColor playerColor) {
		Square s=new Square();
		Vector<Square> currentPieceMoves;
		Square kingSquare=_chessboard.GetPiecesSquare(PieceType.KING, playerColor).get(0);
		
		//Per ogni pezzo del colore scelto, eccetto il re, controlla se uno di questi può effettuare
		//una mossa qualsiasi ed in tal caso ritorna false
		for(s.SetRow(0);s.GetRow()<_chessboard.GetHeight();s.AddToRow(1)) {
			for(s.SetColumn(0);s.GetColumn()<_chessboard.GetWidth();s.AddToColumn(1)) {
				if(!s.equals(kingSquare) && _chessboard.IsSquareOccupiedByPlayerPiece(s, playerColor)) {
					currentPieceMoves=_chessboard.GetPiece(s).GetPossibleMoves(s, _chessboard);
					if(currentPieceMoves!=null)
						for(Square move : currentPieceMoves) {
							if(_chessboard.IsALegalMove(s, move, playerColor)) {
								StoreAndMove(s.clone(), move, null, SpecialMove.NONE);
								if(_chessboard.IsSquareSafe(kingSquare, playerColor).size()==0) { Undo(); return false; }
								Undo();
							}
						}
				}
			}
		}
		
		//Se nessun altro pezzo ad eccezione del Re può fare una mossa, controlla se quest'ultimo è in grado
		//di muoversi, in tal caso ritorna false.
		for(Square square:_chessboard.GetPiece(kingSquare).GetPossibleMoves(kingSquare, _chessboard)) {
			if(_chessboard.IsSquareSafe(square, playerColor).size()==0) { return false; }
		}
		
		//Se si arriva a questo punto significa che non è disponibile nessuna mossa quindi ritorna true
		return true;
	}
	
	/**
	 * Verifica se sulla scacchiera ci sono i pezzi necessari per cui uno dei due giocatori possa
	 * dare scacco matto.
	 * @return true se ci sono i pezzi necessari per dare scacco matto, false altrimenti.
	 */
	private boolean ImpossibilityOfCheckMate() {
		Square s=new Square();
		int i=0, j=0;
		for(s.SetRow(0);s.GetRow()<_chessboard.GetHeight() && i<2 && j<2;s.AddToRow(1)) {
			for(s.SetColumn(0);s.GetColumn()<_chessboard.GetWidth();s.AddToColumn(1)) {
				if(_chessboard.GetPiece(s)!=null) {
					if(_chessboard.GetPiece(s).GetType()!=PieceType.KING) {
						if((_chessboard.GetPiece(s).GetType()==PieceType.KNIGHT ||
							_chessboard.GetPiece(s).GetType()==PieceType.BISHOP)){
							if(_chessboard.GetPiece(s).GetColor()==PieceColor.WHITE) i++;
							else j++;
						} else { return false; }
					}
				}
			}
		}
		
		if(i>=2 || j>=2) return false;
		return true;
	}
	
	/**
	 * Ritorna true se sono state eseguite 50 mosse senza cattura e senza mosse di pedone.
	 * Ritorna false altrimenti.
	 * @return
	 */
	private boolean FiftyMovesDraw() {
		int j=0;
		ChessMoveNotation[] history=_history.toArray();
		for(int i=_history.GetCurrentMoveIndex()-1;i>=0;i--) {
			if(history[i].GetMovingPiece().GetType()!=PieceType.PAWN && history[i].GetEatenPiece()==null) {
				j++;
			}
		}
		if(j>=100) return true;
		return false;
	}
	
	public void Resign() {
		_gameOver=true;
		System.out.print("The "+_currentTurn.GetNextVal()+" won the game!");
	}
	
	/**
	 * Esegue la mossa specificata lanciando le eventuali eccezioni
	 * @param src	La casella sorgente
	 * @param dst	La casella di destinazione
	 * @throws ChessRuleException
	 */
	public void Move(Square src, Square dst) throws ChessRuleException {
		if(_gameOver) { return; }
		if(_chessboard.GetPiece(src)==null) {
			throw new EmptySquareException(src);
		}
		if(_chessboard.GetPiece(src).GetColor()!=_currentTurn) {
			throw new WrongTurnException(_currentTurn);
		}
		if(_chessboard.GetPiece(dst)!=null) {
			if(_chessboard.GetPiece(dst).GetColor()==_currentTurn) {
				throw new OccupiedSquareException(dst);
			}
		}
		try {
			_chessboard.GetPiece(src).CanMove(src, dst, _chessboard);
			StoreAndMove(src,dst, null, SpecialMove.NONE);
			PieceColor kingColor=_currentTurn.GetPrevVal();
			Square kingSquare=_chessboard.GetPiecesSquare(PieceType.KING, kingColor).get(0);
			if(_chessboard.IsSquareSafe(kingSquare, kingColor).size()>0) { Undo(); throw new CheckException(); }
		}
		catch(IllegalPieceMoveException e) { throw e; }
		catch(CastlingException e) { StoreAndMove(src, dst, null, e._type); }
		catch(PromotionException e) { throw e; }
		catch(EnPassantException e) { StoreAndMove(src, dst, e._piece, e._type); }
		catch(SpecialMoveException e) { }
		
		if(CheckMate(_currentTurn)) { _gameOver=true; throw new CheckMateException(_currentTurn.GetPrevVal()); }
		if(StaleMate(_currentTurn)) { _gameOver=true; throw new DrawException(DrawType.STALEMATE, _currentTurn.GetPrevVal()); }
		if(ImpossibilityOfCheckMate()) { _gameOver=true; throw new DrawException(DrawType.IMPOSSIBILITY_OF_CHECKMATE, _currentTurn.GetPrevVal()); }
		if(FiftyMovesDraw()) { _gameOver=true; throw new DrawException(DrawType.FIFTY_MOVES, _currentTurn.GetPrevVal()); }
		System.out.print(toStringGame());
	}
	
	/**
	 * Torna indietro di una mossa senza cancellarla dallo storico.
	 */
	public void TemporaryUndo() {
		_gameOver=false;
		_history.TemporaryUndo(_chessboard);
		SetLastMove();
		_currentTurn=_currentTurn.GetPrevVal();
	}
	
	/**
	 * Torna indietro di una mossa cancellandola dallo storico.
	 */
	public void Undo() {
		_gameOver=false;
		_history.Undo(_chessboard);
		SetLastMove();
		_currentTurn=_currentTurn.GetPrevVal();
	}
	
	/************************GETTER E SETTERS********************************/
	public String toString() { return _chessboard.toString()+"\n"+_currentTurn.toString(); }
	public String toStringGame() {
		String out="";
		if(_standardInitialPosition==false) {
			for(int i=0;i<_unusualInitialPosition.length;i++) {
				System.out.print(_unusualInitialPosition[i]+"\n");
			}
		}
		out+=_history.toString();
		return out;
	}
	public MoveHistory GetMovesList() { return _history; }
	public Chessboard GetChessboard() { return _chessboard; }
}

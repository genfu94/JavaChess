package org.Project_MP_2014;

/**
 * Definisce la casella di una scacchiera tramite 2 interi o una stringa
 * contenente caratteri alfabetici e numerici come nella consueta rappresentazione
 * scacchistica.
 * 
 * @author Giuseppe Gelfusa
 *
 */
public class Square {
	private static int _boardHeight=8;
	private static int _boardWidth=8;
	private int _row;
	private int _column;
	
	/**
	 * Inizializza l'istanza alla casella a1
	 */
	public Square() {
		_column=0;
		_row=0;
	}
	
	/**
	 * Crea una nuova casella.
	 * @param column	La colonna della casella
	 * @param row		La riga della casella
	 */
	public Square(int column,int row) {
		_column=column;
		_row=row;
	}
	
	/**
	 * Inizializza la casella ai valori di colonna e riga specificati
	 * nella stringa passata come parametro.
	 * @param square	Stringa rappresentante una casella
	 */
	public Square(String square) {
		_column=square.charAt(0)-'a';
		if (square.length()==2) {
			_row=square.charAt(1)-'1';
		} else {
			_row=0;
			_row+=(square.charAt(1)-'1')*10;
			_row+=(square.charAt(2)-'1');
		}
	}
	
	/**
	 * Verifica se la colonna e la riga sono validi.
	 * @return true se l'oggetto ricevente rappresenta una casella valida, false altrimenti
	 */
	public boolean IsAValidSquare() {
		return (_column>=0 && _column<_boardWidth) && (_row>=0 && _row<_boardHeight);
	}
	
	/**
	 * Ritorna la rappresentazione testuale della casella.
	 */
	@Override
	public String toString() {
		//return String.valueOf((char)(_column+'a'))+String.valueOf((char)(_row+'1'));
		if(_row>=10) {
			return String.valueOf((char)(_column+'a'))+String.valueOf((char)((_row/10-1)+'1'))+String.valueOf((char)(_row%10+'0'));
		} else {
			return String.valueOf((char)(_column+'a'))+String.valueOf((char)(_row+'1'));
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj==this) { return true; }
		if(obj==null) { return false; }
		if(getClass()!=obj.getClass()) { return false; }
		Square s=(Square) obj;
		if(s._row==this._row && s._column==this._column) { return true; }
		return false;
	}
	
	@Override
	public Square clone() {
		Square app=new Square();
		app.SetRow(this.GetRow());
		app.SetColumn(this.GetColumn());
		return app;
	}
	
	/********METODI GETTER E SETTER*******/
	public void AddToColumn(int n) { _column+=n; }
	public void AddToRow(int n) { _row+=n; }
	public int GetColumn() { return _column; }
	public int GetRow() { return _row; }
	public void SetColumn(int column) { _column=column; }
	public void SetRow(int row) { _row=row; }
	
	public Square GetUpperLeftDiagonalSquareProjection() {
		int offset=((_boardHeight-1-this.GetRow()<this.GetColumn())?_boardHeight-1-this.GetRow():this.GetColumn());
		return new Square(this.GetColumn()-offset, this.GetRow()+offset);
	}
	public Square GetLowerLeftDiagonalSquareProjection() {
		int offset=((this.GetRow()<_boardWidth-1-this.GetColumn())?this.GetRow():_boardWidth-1-this.GetColumn());
		return new Square(this.GetColumn()+offset, this.GetRow()-offset);
	}
	public Square GetUpperRightDiagonalSquareProjection() {
		int offset=((_boardHeight-1-this.GetRow()<_boardWidth-1-this.GetColumn())?_boardHeight-1-this.GetRow():_boardWidth-1-this.GetColumn());
		return new Square(this.GetColumn()+offset, this.GetRow()+offset);
	}
	public Square GetLowerRightDiagonalSquareProjection() {
		int offset=((this.GetRow()<this.GetColumn())?this.GetRow():this.GetColumn());
		return new Square(this.GetColumn()-offset, this.GetRow()-offset);
	}
}

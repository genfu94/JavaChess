package org.Project_MP_2014;

public enum LineType {
	COLUMN, ROW, DIAGONAL;
	
	/**
	 * Verifica che le caselle src e dst rappresentano gli estremi della linea
	 * rappresentata dall'oggetto ricevente.
	 * @param src	Il primo possibile estremo della linea
	 * @param dst	Il secondo possibile estremo della linea
	 * @return		false se src=dst oppure se src-dst non rappresentano gli estremi
	 * 				dell'oggetto ricevente.
	 */
	public boolean RepresentALine(Square src, Square dst) {
		if(src.equals(dst)) { return false; }
		switch(this) {
			case COLUMN:
				if(src.GetColumn()==dst.GetColumn()) { return true; }
				break;
			case ROW:
				if(src.GetRow()==dst.GetRow()) { return true; }
				break;
			case DIAGONAL:
				if(Math.abs(src.GetRow()-dst.GetRow())==Math.abs(src.GetColumn()-dst.GetColumn())) { return true; }
				break;
		}
		return false;
	}
};

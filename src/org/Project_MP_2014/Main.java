package org.Project_MP_2014;

public class Main {
	
	public static boolean Move(Square src, Square dst, ChessGame game) {
		try {
			game.Move(src, dst);
			return true;
		} catch (ChessRuleException e) {
			return false;
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ChessGameGraphics chessGame=new ChessGameGraphics();
	}
}
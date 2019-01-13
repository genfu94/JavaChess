package org.Project_MP_2014;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;

@SuppressWarnings("serial")
public class MoveHistoryGraphicPanel extends JPanel {
	MoveHistory _moveHistoryReference;
	JLabel label1;
	JTextArea moveHistoryTextArea;
	int _lastMove=0;
	
	public MoveHistoryGraphicPanel(MoveHistory historyReference) {
		this.setLayout(new FlowLayout());
		this.setPreferredSize(new Dimension(250,250));
		label1=new JLabel("Notation");
		moveHistoryTextArea=new JTextArea();
		moveHistoryTextArea.setPreferredSize(new Dimension(240,400));
		moveHistoryTextArea.setLineWrap(true);
		moveHistoryTextArea.setEditable(false);
		this.add(label1);
		this.add(moveHistoryTextArea);
		_moveHistoryReference=historyReference;
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d=(Graphics2D) g;
		moveHistoryTextArea.setText(_moveHistoryReference.toString());
		if(_moveHistoryReference.GetCurrentMoveIndex()!=0) {
			int charSum=0;
			String[] movesArray=_moveHistoryReference.toString().split("\\s+");
			String currentMove=movesArray[_moveHistoryReference.GetCurrentMoveIndex()-1];
			for(int i=0; i<_moveHistoryReference.GetCurrentMoveIndex()-1; i++) {
				charSum+=movesArray[i].length()+1;
			}
			charSum+=currentMove.length()-currentMove.substring(currentMove.indexOf(".")+1).length();
			currentMove=currentMove.substring(currentMove.indexOf(".")+1);
			int p0=charSum;
			try {
				moveHistoryTextArea.getHighlighter().addHighlight(p0, p0+currentMove.length(), new DefaultHighlighter.DefaultHighlightPainter(Color.lightGray));
			} catch (BadLocationException e) {
			}
			_lastMove=_moveHistoryReference.GetCurrentMoveIndex();
		}
	}
}

package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

	public Pawn(Board board, Color color) {
		super(board, color);
	}

	
	
	
	
	
	@Override
	public boolean[][] possibleMove() {
		
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		Position p = new Position(0,0);
		
		
		if (getColor() == Color.WHITE ) 
		{
			p.setValues(position.getRow()-1, position.getColumn());
			 if (getBoard().positionExists(p) && !getBoard().thereISAPiece(p)) {
				 mat[p.getRow()][p.getColumn()] = true;
			 }
			 
				p.setValues(position.getRow()-1, position.getColumn());
				 if (getBoard().positionExists(p) && !getBoard().thereISAPiece(p)) {
					 mat[p.getRow()][p.getColumn()] = true;
				 }
			 
			 
		}
		
		
		
		
		
		return null;
	}

}

package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece{

	public King(Board board, chess.Color color) {
		super(board, color);
	}

	@Override
	public String toString()
	{
		return "K";
	}
	
	
	
}

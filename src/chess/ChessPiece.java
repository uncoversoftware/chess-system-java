package chess;

import boardgame.Board;
import boardgame.Piece;

public class ChessPiece extends Piece {
   
	private Color Color;

	public ChessPiece(Board board, chess.Color color) {
		super(board);
		Color = color;
	}

	public Color getColor() {
		return Color;
	}
	
}

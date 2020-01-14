package chess.pieces;
import boardgame.Board;
import chess.ChessPiece;

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

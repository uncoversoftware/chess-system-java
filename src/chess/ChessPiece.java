package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece {
   
	private Color color;
	private int moveCount;
	
	
	public ChessPiece(Board board, chess.Color color) {
		super(board);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}
	
	public void increaseMoveCount() {
		this.moveCount++;
	}
	
	public void decreaseMoveCount() {
		this.moveCount--;
	}
	
	public int getMoveCount() {
		return this.moveCount;
	}


	
	protected boolean isThereOpponentPiece(Position position) {
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		return p != null && p.getColor() != color;
	}
	
	
	public ChessPosition getChessPosition() 
	{
		return ChessPosition.fromPosition(position);
	}

	
	
	
	
}

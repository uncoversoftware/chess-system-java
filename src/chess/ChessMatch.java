package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;


public class ChessMatch {

	private Board board;
	private int turn;
	private Color currentPlayer;
	private boolean check;
	
	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();
	private boolean checkMate;
	private ChessPiece enPassandVulnerable;
	
	public ChessMatch()
	{
		board = new Board(8,8);
		turn = 1;
		currentPlayer = Color.WHITE;
		initialSetup();
	}
	
	public boolean getcheckMate() {
		return checkMate;
	}
	
	public int getTurn()
	{
		return turn;
	}
	
	public boolean getCheck() {
		return check; 
	}
	
	public Color getCurrentPlayer()
	{
		return currentPlayer;
	}
	
	public ChessPiece getEnPassandVulnerable() {
		return enPassandVulnerable;
	}
	
	public ChessPiece[][] getPieces()
	{
	   	ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
	   	for (int i=0;i <board.getRows();i++)
	   	{
	   		for (int j=0;j<board.getColumns();j++)
	   		{
	   			mat[i][j] = (ChessPiece)board.piece(i, j);
	   		}
	   	}
	   	
	   	return mat;
	}
	
	private void validateSourcePosition(Position position)
 {
	 if (!board.thereISAPiece(position))
	 {
		 throw new ChessException("There is not piece on source position");
	 }
	 
	 if (currentPlayer != ((ChessPiece)board.piece(position)).getColor()) {
		 throw new ChessException("There chosen piece not yours");
	 }
	 
	 
	 if (!board.piece(position).isThereAnyPossibleMove()) {
		 throw new ChessException("There is no possible for the chosen position");
	 }
 }
 
 	private void validadetargetPosition(Position source, Position target) {
	 
	 if (!board.piece(source).possibleMove(target)) {
		 throw new ChessException("The chosen pleace can't move target position");
	 }
 }

 	private Piece makeMove(Position source,Position target)
 {
	 ChessPiece p = (ChessPiece)board.removePiece(source);
	 p.increaseMoveCount();
	 Piece capturedPiece = board.removePiece(target);
	 board.placePiece(p, target);
	
	 if (capturedPiece != null) {
		 piecesOnTheBoard.remove(capturedPiece);
		 capturedPieces.add(capturedPiece);
	 }
	 
	// #specialmove castling kingside rook
	if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
		Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
		Position targetT = new Position(source.getRow(), source.getColumn() + 1);
		ChessPiece rook = (ChessPiece)board.removePiece(sourceT);
		board.placePiece(rook, targetT);
		rook.increaseMoveCount();
	}
	 
	 
	// #specialmove castling queenside rook
	if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
		Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
		Position targetT = new Position(source.getRow(), source.getColumn() - 1);
		ChessPiece rook = (ChessPiece)board.removePiece(sourceT);
		board.placePiece(rook, targetT);
		rook.increaseMoveCount();
	}	
	
	// #specialmove en passant.
	if (p instanceof Pawn) {
		if (source.getColumn() != target.getColumn() && capturedPiece == null) {
			Position pawnPosition;
			if (p.getColor() == Color.WHITE) {
				pawnPosition = new Position(target.getRow()+1, target.getColumn());
			}
			else
			{
				pawnPosition = new Position(target.getRow()-1, target.getColumn());
			}
		  capturedPiece = board.removePiece(pawnPosition);
		  capturedPieces.add(capturedPiece);
		  piecesOnTheBoard.remove(capturedPiece);
		}
	}
	  
	
	
	
	 
	 
	 return capturedPiece;
 }
 
 	private void undoMoke(Position source,Position target,Piece capturedPiece)
 {
	 ChessPiece p = (ChessPiece)board.removePiece(target);
	 p.decreaseMoveCount();
	 board.placePiece(p, source);
	 
	 if (capturedPiece != null)
	 {
		 board.placePiece(capturedPiece, target);
		 capturedPieces.remove(capturedPiece);
		 piecesOnTheBoard.add(capturedPiece);
	 }
	 
	// #specialmove castling kingside rook
	if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
		Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
		Position targetT = new Position(source.getRow(), source.getColumn() + 1);
		ChessPiece rook = (ChessPiece)board.removePiece(targetT);
		board.placePiece(rook, sourceT);
		rook.decreaseMoveCount();
	}

	// #specialmove castling queenside rook
	if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
		Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
		Position targetT = new Position(source.getRow(), source.getColumn() - 1);
		ChessPiece rook = (ChessPiece)board.removePiece(targetT);
		board.placePiece(rook, sourceT);
		rook.decreaseMoveCount();
	}
 
	// #specialmove enpasam.
	if (p instanceof Pawn) {
	if (source.getColumn() != target.getColumn() && capturedPiece == enPassandVulnerable) {
		ChessPiece pawn = (ChessPiece)board.removePiece(target);
		Position pawnPosition;
		
		if (p.getColor() == Color.WHITE) {
			pawnPosition = new Position(target.getRow()+1, target.getColumn());
		}
		else
		{
			pawnPosition = new Position(target.getRow()-1, target.getColumn());
		}
		  board.placePiece(pawn, pawnPosition);
		  piecesOnTheBoard.remove(capturedPiece);
	}
	}
			  
 
	 
	 
	
	 
	 
 }
 
 	private Color opponent(Color color) {
	return (color == Color.WHITE) ? Color.BLACK : Color.WHITE; 
    }
 
 	private ChessPiece king(Color color) {
	 List<Piece> list = piecesOnTheBoard.stream().filter(x ->((ChessPiece)x).getColor() == color).collect(Collectors.toList());
	 for (Piece p : list)
	 {
		 if (p  instanceof King )
		 {
			 return (ChessPiece)p;
		 }	
		}
	   throw new IllegalStateException("There is no " + color + " King on the board");
	 }
	 
 	private boolean testCheck(Color color) {
 		Position kingPosition = king(color).getChessPosition().toPosition();
 		List<Piece> opponetPieces = piecesOnTheBoard.stream().filter(x ->((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
 		
 		for (Piece p : opponetPieces ) {
 			boolean[][] mat = p.possibleMove();
 			if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
 				return true;
 			}
 		}
 		return false;
 	}
 	
 	
 	private boolean testCheckMate(Color color) {
 		if (!testCheck(color)) {
			return false;
		}
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for (Piece p : list) {
			boolean[][] mat = p.possibleMove();
			for (int i=0; i<board.getRows(); i++) {
				for (int j=0; j<board.getColumns(); j++) {
					if (mat[i][j]) {
						Position source = ((ChessPiece)p).getChessPosition().toPosition();
						Position target = new Position(i, j);
						Piece capturedPiece = makeMove(source, target);
						boolean testCheck = testCheck(color);
						undoMoke(source, target, capturedPiece);
						if (!testCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
 	}
 	
 	
 	
 	
 	public boolean[][] possibleMoves(ChessPosition sourcePosition){
    Position position = sourcePosition.toPosition();
    validateSourcePosition(position);
    
    return board.piece(position).possibleMove();
    
    
 }
 
 	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition)
 {
	 Position source = sourcePosition.toPosition();
	 Position target = targetPosition.toPosition();
	 validateSourcePosition(source);
	 validadetargetPosition(source,target);
	 Piece capturedPiece = makeMove(source,target);
	 
	 ChessPiece movedPiece = (ChessPiece)board.piece(target);
 	 
	 
	 
	 
	 if (testCheck(currentPlayer))
	 {
		 undoMoke(source, target, capturedPiece);
		 throw new ChessException("yout can't put yourself check!");
	 }
	 
	 check = (testCheck(opponent(currentPlayer))) ? true : false;
	 
	 if (testCheckMate(opponent(currentPlayer))) {
		 checkMate = true;
	 }
	 else {
		 nextTurn();	 
	 }
	 
	 //
	 if (movedPiece instanceof Pawn && (target.getRow() == source.getRow()-2 || target.getRow() == source.getRow()+2)) {
		enPassandVulnerable = movedPiece; 
	 }
	 else {
		 enPassandVulnerable = null;
	 }
	 
	 
	 
	 
	 
	 return (ChessPiece)capturedPiece;
 }
 
 	private void placeNewPiece(char column,int row,ChessPiece piece)
	  {
		  board.placePiece(piece, new ChessPosition(column, row).toPosition());
		  piecesOnTheBoard.add(piece);
	  }
	
	private void nextTurn()
	  {
		  turn ++;
		  currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK :  Color.WHITE;
	  }
  
	private void initialSetup()
	   {
		   //King.
		   placeNewPiece('e', 1, new King(board, Color.WHITE,this));
		   
		   // Queen
		   placeNewPiece('d', 1, new Queen(board, Color.WHITE));
		   
		  //Rook
		   placeNewPiece('b', 1, new Knight(board, Color.WHITE));
		   placeNewPiece('g', 1, new Knight(board, Color.WHITE));
		   
		   
		   //Rook
		   placeNewPiece('a', 1, new Rook(board, Color.WHITE));
		   placeNewPiece('h', 1, new Rook(board, Color.WHITE));
		   
		   //Bishop.
		   placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
		   placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
		   
		   //Pawn.
		   placeNewPiece('a', 2, new Pawn(board, Color.WHITE,this));
		   placeNewPiece('b', 2, new Pawn(board, Color.WHITE,this));
		   placeNewPiece('c', 2, new Pawn(board, Color.WHITE,this));
		   placeNewPiece('d', 2, new Pawn(board, Color.WHITE,this));
		   placeNewPiece('e', 2, new Pawn(board, Color.WHITE,this));
		   placeNewPiece('f', 2, new Pawn(board, Color.WHITE,this));
		   placeNewPiece('g', 2, new Pawn(board, Color.WHITE,this));
		   placeNewPiece('h', 2, new Pawn(board, Color.WHITE,this));
		  
		   //----PIECES BLACK
		   
		   
		   //King.
		   placeNewPiece('e', 8, new King(board, Color.BLACK,this));
		   
		// Queen
		   placeNewPiece('d', 8, new Queen(board, Color.BLACK));
		   
		   //Rook.
		   placeNewPiece('a', 8, new Rook(board, Color.BLACK));
		   placeNewPiece('h', 8, new Rook(board, Color.BLACK));
		   
		   //Bishop.
		   placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
		   placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
		   
		   
		   //Rook
		   placeNewPiece('b', 8, new Knight(board, Color.BLACK));
		   placeNewPiece('g', 8, new Knight(board, Color.BLACK));
		   
		   //Pawn.
		   placeNewPiece('a', 7, new Pawn(board, Color.BLACK,this));
		   placeNewPiece('b', 7, new Pawn(board, Color.BLACK,this));
		   placeNewPiece('c', 7, new Pawn(board, Color.BLACK,this));
		   placeNewPiece('d', 7, new Pawn(board, Color.BLACK,this));
		   placeNewPiece('e', 7, new Pawn(board, Color.BLACK,this));
		   placeNewPiece('f', 7, new Pawn(board, Color.BLACK,this));
		   placeNewPiece('g', 7, new Pawn(board, Color.BLACK,this));
		   placeNewPiece('h', 7, new Pawn(board, Color.BLACK,this));
		   
	   }

	
	
	
	
	
}

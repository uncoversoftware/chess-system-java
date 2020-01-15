package application;
import java.util.InputMismatchException;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) 
	{
		
		Scanner sc = new Scanner(System.in);
		ChessMatch  chessMatch = new ChessMatch();
		
		while (true)
		{
		try {
				UI.clearScreen();
				UI.printMacth(chessMatch);
				
				// origem.
				System.out.println();
				System.out.print("Source: ");
				ChessPosition source = UI.readChessPossition(sc);
				
				boolean[][] possibleMoves = chessMatch.possibleMoves(source);
				UI.clearScreen();
				UI.printBoard(chessMatch.getPieces(),possibleMoves);
			
				
				// destino.
				System.out.println();
				System.out.print("Target : ");
				ChessPosition target = UI.readChessPossition(sc);
				
				
				
				ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
		}
		catch(ChessException e)
		{
			System.out.print(e.getMessage());
			sc.nextLine();
		}
		catch(InputMismatchException e)
		{
			System.out.print(e.getMessage());
			sc.nextLine();
		}
		
		
		}
	}

}

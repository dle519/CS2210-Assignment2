/**
 * Creates a boardgame to play against a computer
 * 
 * @author David Le
 *
 */
public class BoardGame {
	public char[][] gameBoard; // Cell layout for the game board
	private int board_size; // board size
	private int empty_positions; // empty positions to be allowed on the board
	private int max_levels; // quality of the program
	private char EMPTY_TILE = 'g';
	private char PLAYER_TILE = 'b';
	private char COMP_TILE = 'o';

	/**
	 * Creates a board game with the given parameters
	 * 
	 * @param board_size      - size of the board
	 * @param empty_positions - empty positions allowed on the game board
	 * @param max_levels      - quality of program
	 */
	public BoardGame(int board_size, int empty_positions, int max_levels) {
		this.board_size = board_size;
		this.empty_positions = empty_positions;
		this.max_levels = max_levels;
		gameBoard = new char[board_size][board_size];
		for (int i = 0; i < board_size; i++) {
			for (int j = 0; j < board_size; j++) {
				gameBoard[i][j] = EMPTY_TILE;
			}
		}
	}

	/**
	 * Creates a hash dictionary of size 6899
	 * 
	 * @return A hash dictionary
	 */
	public HashDictionary makeDictionary() {
		HashDictionary x = new HashDictionary(6899);
		return x;
	}

	/**
	 * Checks if the configuration of the board is in the hash dictionary
	 * 
	 * @param dict
	 * @return
	 */
	public int isRepeatedConfig(HashDictionary dict) {
		String board_game_layout = "";
		int result;
		for (int i = 0; i < board_size; i++) {
			for (int j = 0; j < board_size; j++) {
				board_game_layout += gameBoard[i][j];
			}
		}
		result = dict.getScore(board_game_layout);
		return result;
	}

	/**
	 * Inserts the string and score of the board into the dictionary
	 * 
	 * @param dict  - Hash Dictionary
	 * @param score - Score of the configuration
	 */
	public void putConfig(HashDictionary dict, int score) {
		String board_game_layout = "";
		Configuration config;
		for (int i = 0; i < board_size; i++) {
			for (int j = 0; j < board_size; j++) {
				board_game_layout += gameBoard[i][j];
			}
		}
		config = new Configuration(board_game_layout, score);
	}

	/**
	 * Saves the play made
	 * 
	 * @param row    - row on board
	 * @param col    - column on board
	 * @param symbol - Symbol used to play (player or computer)
	 */
	public void savePlay(int row, int col, char symbol) {
		gameBoard[row][col] = symbol;
	}

	/**
	 * True if position is empty
	 */
	public boolean positionIsEmpty(int row, int col) {
		return gameBoard[row][col] == 'g';
	}

	/**
	 * Checks if the current tile is a computer
	 */
	public boolean tileOfComputer(int row, int col) {
		return gameBoard[row][col] == 'o';
	}

	/**
	 * Checks if the current tile is a player
	 */
	public boolean tileOfHuman(int row, int col) {
		return gameBoard[row][col] == 'v';
	}

	/**
	 * Checks if player or computer won the game
	 * 
	 * @param symbol - player or computer tile
	 * @return true if symbol given has won
	 */
	public boolean wins(char symbol) {
		String column[] = new String[board_size]; // Array containing all the column tiles
		String win_line = ""; // Winning line of symbol to be checked
		String diagonal_LR = ""; // Diagonal tiles from Left -> Right
		String diagonal_RL = ""; // Diagonal tiles from Right -> Left
		// Creating the winning condition string and filling the column array with empty
		// strings
		for (int fill = 0; fill < board_size; fill++) {
			win_line += symbol;
			column[fill] = "";
		}

		// Traverse gameBoard
		for (int x = 0; x < board_size; x++) {
			String row = "";
			for (int y = 0, z = board_size - 1; y < board_size; y++, z--) {
				row = row + gameBoard[x][y]; // Fill row string with tiles
				column[y] += gameBoard[x][y]; // Adding to the string for each column in the array
				// When the tile is on the diagonal
				if (x == y) {
					diagonal_LR += gameBoard[x][y]; // Fill with diagonal tiles left -> right
					diagonal_RL += gameBoard[x][z]; // Fill with diagonal tiles right -> left
				}
			}
			// If the row is filled with desired symbo;
			if (row.length() == board_size && row.equals(win_line)) {
				return true;
			}
		}
		// If any diagonal has won
		if (diagonal_LR.equals(win_line) || diagonal_RL.equals(win_line)) {
			return true;
		}
		// Check if any of the columns has won
		for (int i = 0; i < board_size; i++) {
			if (column[i].equals(win_line)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if there is a draw
	 * 
	 * @param symbol - Tile of the player or computer to be checked for draw
	 * @return True if there is none of the adjacent tiles to the empty is the
	 *         symbol tile
	 */
	public boolean isDraw(char symbol, int empty_positions) {
		int count = 0; // Used to count the empty tiles checked
		int empty_tiles_left = 0; // Amount of empty tiles left
		boolean draw = false;
		for (int i = 0; i < board_size; i++) {
			for (int j = 0; j < board_size; j++) {
				if (gameBoard[i][j] == EMPTY_TILE) {
					empty_tiles_left++;
					System.out.println(empty_tiles_left);
				}
			}
		}
		if (empty_positions == 0 && empty_tiles_left == 0 && !wins(symbol)) {
			return true;
		} 
		
		else if (empty_tiles_left == empty_positions) {
			while (!draw) {
				for (int i = 0; i < board_size; i++) {
					for (int j = 0; j < board_size; j++) {
						if (gameBoard[i][j] == EMPTY_TILE) {
							// Checking the surrounding of the empty tile is not symbol
							// If empty is top left corner tile
							if (i == 0 && j == 0) {
								if (gameBoard[i][j + 1] != symbol && gameBoard[i + 1][j + 1] != symbol
										&& gameBoard[i + 1][j] != symbol) {
									count++;
								}
							}
							// If empty is top right corner
							else if (i == 0 && j == board_size - 1) {
								if (gameBoard[i][j - 1] != symbol && gameBoard[i + 1][j - 1] != symbol
										&& gameBoard[i + 1][j] != symbol) {
									count++;
								}
							}
							// If empty is bottom left corner
							else if (i == board_size - 1 && j == 0) {
								if (gameBoard[i - 1][j] != symbol && gameBoard[i - 1][j + 1] != symbol
										&& gameBoard[i][j + 1] != symbol) {
									count++;
								}
							}
							// If empty is bottom right corner
							else if (i == board_size - 1 && j == board_size - 1) {
								if (gameBoard[i][j - 1] != symbol && gameBoard[i - 1][j - 1] != symbol
										&& gameBoard[i - 1][j] != symbol) {
									count++;
								}
							}
							// If empty is the top edge of the board (not the corners)
							else if (i == 0) {
								if (gameBoard[i][j - 1] != symbol && gameBoard[i + 1][j - 1] != symbol
										&& gameBoard[i + 1][j] != symbol && gameBoard[i + 1][j + 1] != symbol
										&& gameBoard[i][j + 1] != symbol) {
									count++;
								}
							}
							// If empty is the left edge of the board (not the corners)
							else if (j == 0) {
								if (gameBoard[i - 1][j] != symbol && gameBoard[i - 1][j + 1] != symbol
										&& gameBoard[i][j + 1] != symbol && gameBoard[i + 1][j + 1] != symbol
										&& gameBoard[i + 1][j] != symbol) {
									count++;
								}
							}
							// If empty is the right edge of the board (not the corners)
							else if (j == board_size - 1) {
								if (gameBoard[i - 1][j] != symbol && gameBoard[i][j - 1] != symbol
										&& gameBoard[i + 1][j - 1] != symbol && gameBoard[i + 1][j] != symbol) {
									count++;
								}
							}
							// If empty is the bottom edge of the board (not the corners)
							else if (i == board_size - 1) {
								if (gameBoard[i][j - 1] != symbol && gameBoard[i - 1][j - 1] != symbol
										&& gameBoard[i - 1][j] != symbol && gameBoard[i - 1][j + 1] != symbol
										&& gameBoard[i][j + 1] != symbol) {
									count++;
								}
							}
							// Checks the rest of the board thats not an edge or corner
							else {
								if (gameBoard[i][j - 1] != symbol && gameBoard[i - 1][j - 1] != symbol
										&& gameBoard[i - 1][j] != symbol && gameBoard[i - 1][j + 1] != symbol
										&& gameBoard[i][j + 1] != symbol && gameBoard[i + 1][j + 1] != symbol
										&& gameBoard[i + 1][j] != symbol && gameBoard[i + 1][j - 1] != symbol) {
									count++;
								}
							}

						}
					}
					// If all of the empty tiles does not have the desired symbol adjacent to it.
					System.out.println(count + ", " + empty_tiles_left + ", " + empty_positions);
					if (count == empty_positions) {
						draw = true;
					}
				}
			}
		}
		return draw;
	}

	/**
	 * Evaluate the board to a win or loss or draw or undecided
	 * 
	 * @param symbol - player or computer tile desired
	 * @return 3 if computer wins, 0 if player wins, 2 if tie, and 1 if undecided
	 */
	public int evalBoard(char symbol, int empty_positions) {
		int COMP_WIN = 3;
		int PLAYER_WIN = 0;
		int TIE = 2;
		// If computer wins
		if (wins(COMP_TILE)) {
			return COMP_WIN;
		}
		// If player wins
		else if (wins(PLAYER_TILE)) {
			return PLAYER_WIN;
		}
		// If there is a draw
		else if (isDraw(COMP_TILE, empty_positions) && isDraw(PLAYER_TILE, empty_positions)) {
			return TIE;
		}
		// Undecided
		return 1;
	}

}

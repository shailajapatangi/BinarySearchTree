package AlgoProject.Project3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class wordPuzzle {
	static char[][] alphabets;
	Set<String> words1 = new HashSet<>();
	Set<String> words2 = new HashSet<>();

	public static void main(String[] args) {
		int num_rows = 0;
		int num_columns = 0;
		wordPuzzle puzzle = new wordPuzzle();
		Scanner scan = new Scanner(System.in);
		try {
			System.out.println("Enter number of rows");
			num_rows = scan.nextInt();
			System.out.println("Enter number of columns");
			num_columns = scan.nextInt();
			puzzle.createGrid(num_rows, num_columns);
		} catch (InputMismatchException e) {
			System.out.println("Please provide valid integers");
		}

		MyHashTable<String> hashTable = new MyHashTable<>();
		puzzle.populateDictionaryWords(hashTable);
		long startTime = System.currentTimeMillis();
		puzzle.searchWordsInGrid(hashTable, num_rows, num_columns);
		long endTime = System.currentTimeMillis();
		System.out.println("Time taken in first method: " + (endTime - startTime) + " milliseconds");

		puzzle.printWords1();

		MyHashTable<String> hashTable1 = new MyHashTable<>();
		puzzle.populateDictionaryWordsWithPrefix(hashTable1);
		startTime = System.currentTimeMillis();
		puzzle.enhancedSearchWordsInGrid(hashTable1, num_rows, num_columns);
		endTime = System.currentTimeMillis();
		System.out.println("Time taken in second method: " + (endTime - startTime) + " milliseconds");

		puzzle.printWords2();
	}

	private void enhancedSearchWordsInGrid(MyHashTable<String> hashTable, int rows, int columns) {
		enhancedSearchRowColumn(rows, columns, hashTable);
		enhancedSearchDiagnonalNorthEast(rows - 1, columns - 1, hashTable);
		enhancedSearchDiagnonalSouthWest(rows - 1, columns - 1, hashTable);

	}

	private void enhancedSearchDiagnonalSouthWest(int rows, int columns, MyHashTable<String> hashTable) {
		StringBuilder builder = new StringBuilder();
		char letter;
		for (int x = rows; x >= 0; x--) {
			int row = x;
			builder = new StringBuilder();
			for (int y = 0, z = row; y <= columns && z >= 0; y++, z--) {
				int column = y;
				row = z;
				builder = new StringBuilder();
				for (int i = row, j = column; i >= 0 && j <= columns; j++, i--) {
					letter = alphabets[i][j];
					builder = builder.append(letter);
					String word1 = builder.toString();

					if (hashTable.contains(word1, "word")) {
						words2.add(word1);
					}
					builder.reverse();
					String word2 = builder.toString();
					if (hashTable.contains(word2, "word")) {
						words2.add(word2);
					}
					builder.reverse();
//					if (!hashTable.contains(word1, "prefix") && !hashTable.contains(word2, "prefix")) {
//						break;
//					}

				}
			}
		}

		for (int x = 0; x <= columns; x++) {
			int column = x;
			builder = new StringBuilder();
			for (int y = rows, z = column; y >= 0 && z <= columns; y--, z++) {
				int row = y;
				column = z;
				builder = new StringBuilder();
				for (int i = row, j = column; i >= 0 && j <= column; j++, i--) {
					letter = alphabets[i][j];
					builder = builder.append(letter);
					String word1 = builder.toString();

					if (hashTable.contains(word1, "word")) {
						words2.add(word1);
					}
					builder.reverse();
					String word2 = builder.toString();
					if (hashTable.contains(word2, "word")) {
						words2.add(word2);
					}
					builder.reverse();
//					if (!hashTable.contains(word1, "prefix") && !hashTable.contains(word2, "prefix")) {
//						break;
//					}

				}
			}
		}
	}

	private void enhancedSearchDiagnonalNorthEast(int rows, int columns, MyHashTable<String> hashTable) {
		StringBuilder builder = new StringBuilder();
		char letter;

		for (int x = rows; x >= 0; x--) {
			int row = x;
			builder = new StringBuilder();
			for (int y = columns, z = row; y >= 0 && z >= 0; y--, z--) {
				builder = new StringBuilder();
				int column = y;
				row = z;
				for (int i = row, j = column; i >= 0 && j >= 0; j--, i--) {
					letter = alphabets[i][j];
					builder = builder.append(letter);
					String word1 = builder.toString();

					if (hashTable.contains(word1, "word")) {
						words2.add(word1);
					}
					builder.reverse();
					String word2 = builder.toString();
					if (hashTable.contains(word2, "word")) {
						words2.add(word2);
					}
					builder.reverse();
//					if (!hashTable.contains(word1, "prefix") && !hashTable.contains(word2, "prefix")) {
//						break;
//					}

				}
			}
		}

		for (int x = columns; x >= 0; x--) {
			int column = x;
			builder = new StringBuilder();
			for (int y = rows, z = column; y >= 0 && z >= 0; y--, z--) {
				builder = new StringBuilder();
				int row = y;
				column = z;
				for (int i = row, j = column; i >= 0 && j >= 0; j--, i--) {
					letter = alphabets[i][j];
					builder = builder.append(letter);
					String word1 = builder.toString();

					if (hashTable.contains(word1, "word")) {
						words2.add(word1);
					}
					builder.reverse();
					String word2 = builder.toString();
					if (hashTable.contains(word2, "word")) {
						words2.add(word2);
					}
					builder.reverse();
//					if (!hashTable.contains(word1, "prefix") && !hashTable.contains(word2, "prefix")) {
//						break;
//					}

				}
			}
		}
	}

	private void enhancedSearchRowColumn(int rows, int columns, MyHashTable<String> hashTable) {
		StringBuilder builder = new StringBuilder();
		char letter;
		for (int i = 0; i < rows; i++) {
			builder = new StringBuilder();
			for (int x = 0; x < columns; x++) {
				int column = x;
				builder = new StringBuilder();
				for (int j = column; j < columns; j++) {
					letter = alphabets[i][j];
					builder = builder.append(letter);
					String word1 = builder.toString();

					if (hashTable.contains(word1, "word")) {
						words2.add(word1);
					}
					builder.reverse();
					String word2 = builder.toString();
					if (hashTable.contains(word2, "word")) {
						words2.add(word2);
					}
					builder.reverse();
//					if (!hashTable.contains(word1, "prefix") && !hashTable.contains(word2, "prefix")) {
//						break;
//					}

				}
			}
		}

		for (int i = 0; i < columns; i++) {
			builder = new StringBuilder();
			for (int x = 0; x < rows; x++) {
				int row = x;
				builder = new StringBuilder();
				for (int j = row; j < rows; j++) {
					letter = alphabets[j][i];
					builder = builder.append(letter);
					String word1 = builder.toString();

					if (hashTable.contains(word1, "word")) {
						words2.add(word1);
					}
					builder.reverse();
					String word2 = builder.toString();
					if (hashTable.contains(word2, "word")) {
						words2.add(word2);
					}
					builder.reverse();
//					if (!hashTable.contains(word1, "prefix") && !hashTable.contains(word2, "prefix")) {
//						break;
//					}

				}
			}
		}
	}

	private void printWords1() {
		Iterator iter = words1.iterator();
		while (iter.hasNext()) {
			System.out.println(iter.next());
		}
	}

	private void printWords2() {
		Iterator iter = words2.iterator();
		while (iter.hasNext()) {
			System.out.println(iter.next());
		}
	}

	/**
	 * Search words in crossword
	 * 
	 * @param hashTable
	 * @param rows
	 * @param columns
	 */
	public void searchWordsInGrid(MyHashTable<String> hashTable, int rows, int columns) {
		searchRowColumn(rows, columns, hashTable);
		searchDiagnonalNorthEast(rows - 1, columns - 1, hashTable);
		searchDiagnonalSouthWest(rows - 1, columns - 1, hashTable);
	}

	private void searchDiagnonalSouthWest(int rows, int columns, MyHashTable<String> hashTable) {
		StringBuilder builder = new StringBuilder();
		char letter;
		for (int x = rows; x >= 0; x--) {
			int row = x;
			builder = new StringBuilder();
			for (int y = 0, z = row; y <= columns && z >= 0; y++, z--) {
				int column = y;
				row = z;
				builder = new StringBuilder();
				for (int i = row, j = column; i >= 0 && j <= columns; j++, i--) {
					letter = alphabets[i][j];
					searchWordInHashTable(hashTable, builder, letter);
				}
			}
		}

		for (int x = 0; x <= columns; x++) {
			int column = x;
			builder = new StringBuilder();
			for (int y = rows, z = column; y >= 0 && z <= columns; y--, z++) {
				int row = y;
				column = z;
				builder = new StringBuilder();
				for (int i = row, j = column; i >= 0 && j <= column; j++, i--) {
					letter = alphabets[i][j];
					searchWordInHashTable(hashTable, builder, letter);
				}
			}
		}
	}

	private void searchDiagnonalNorthEast(int rows, int columns, MyHashTable<String> hashTable) {
		StringBuilder builder = new StringBuilder();
		char letter;

		for (int x = rows; x >= 0; x--) {
			int row = x;
			builder = new StringBuilder();
			for (int y = columns, z = row; y >= 0 && z >= 0; y--, z--) {
				builder = new StringBuilder();
				int column = y;
				row = z;
				for (int i = row, j = column; i >= 0 && j >= 0; j--, i--) {
					letter = alphabets[i][j];
					searchWordInHashTable(hashTable, builder, letter);
				}
			}
		}

		for (int x = columns; x >= 0; x--) {
			int column = x;
			builder = new StringBuilder();
			for (int y = rows, z = column; y >= 0 && z >= 0; y--, z--) {
				builder = new StringBuilder();
				int row = y;
				column = z;
				for (int i = row, j = column; i >= 0 && j >= 0; j--, i--) {
					letter = alphabets[i][j];
					searchWordInHashTable(hashTable, builder, letter);
				}
			}
		}
	}

	private void searchRowColumn(int rows, int columns, MyHashTable<String> hashTable) {
		StringBuilder builder = new StringBuilder();
		char letter;
		for (int i = 0; i < rows; i++) {
			builder = new StringBuilder();
			for (int x = 0; x < columns; x++) {
				int column = x;
				builder = new StringBuilder();
				for (int j = column; j < columns; j++) {
					letter = alphabets[i][j];
					searchWordInHashTable(hashTable, builder, letter);
				}
			}
		}

		for (int i = 0; i < columns; i++) {
			builder = new StringBuilder();
			for (int x = 0; x < rows; x++) {
				int row = x;
				builder = new StringBuilder();
				for (int j = row; j < rows; j++) {
					letter = alphabets[j][i];
					searchWordInHashTable(hashTable, builder, letter);
				}
			}
		}
	}

	private void searchWordInHashTable(MyHashTable<String> hashTable, StringBuilder builder, char letter) {
		builder = builder.append(letter);
		String word = builder.toString();

		if (hashTable.contains(word, "word")) {
			words1.add(word);
		}
		builder.reverse();
		word = builder.toString();
		if (hashTable.contains(word, "word")) {
			words1.add(word);
		}
		builder.reverse();
	}

	/**
	 * Populate words with prefix in Dictionary
	 * 
	 * @param hashTable1
	 */
	public void populateDictionaryWordsWithPrefix(MyHashTable<String> hashTable1) {
		Scanner sc2 = null;
		try {
			sc2 = new Scanner(new File("Dictionary.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (sc2.hasNextLine()) {
			Scanner s2 = new Scanner(sc2.nextLine());
			while (s2.hasNext()) {
				String s = s2.next();
				StringBuilder word = new StringBuilder();

				hashTable1.insert(s, "word");
				for (int i = 0; i < s.length() - 2; i++) {
					word.append(s.charAt(i));
					String pre = word.toString();
					if (pre.equals("i")) {
						System.out.println(i);
					}
					if (!hashTable1.contains(pre, "word")) {
						hashTable1.insert(pre, "prefix");
					}
				}
			}
		}

	}

	/**
	 * Populate words in Dictionary
	 * 
	 * @param hashTable
	 */
	private void populateDictionaryWords(MyHashTable<String> hashTable) {
		Scanner sc2 = null;
		try {
			sc2 = new Scanner(new File("Dictionary.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (sc2.hasNextLine()) {
			Scanner s2 = new Scanner(sc2.nextLine());
			while (s2.hasNext()) {
				String s = s2.next();
				hashTable.insert(s, "word");
			}
		}
	}

	private void createGrid(int rows, int columns) {
		Random random = new Random();
		String alphabet = "abcdefghijklmnopqrstuwxyz";

		alphabets = new char[rows][columns];
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				alphabets[r][c] = alphabet.charAt(random.nextInt(alphabet.length()));
			}
		}

		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				System.out.print(alphabets[r][c] + " ");
			}
			System.out.println();
		}

	}
}

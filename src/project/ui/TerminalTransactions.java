package project.ui;

import project.delegates.TerminalTransactionsDelegate;
import project.model.Funds;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

/**
 * The class is only responsible for handling terminal text inputs.
 *
 * Directly from Java Demo project
 */
public class TerminalTransactions {
	private static final String EXCEPTION_TAG = "[EXCEPTION]";
	private static final String WARNING_TAG = "[WARNING]";
	private static final int INVALID_INPUT = Integer.MIN_VALUE;
	private static final int EMPTY_INPUT = 0;
	
	private BufferedReader bufferedReader = null;
	private TerminalTransactionsDelegate delegate = null;

	public TerminalTransactions() {
	}
	
	/**
	 * Sets up the database to have a branch table with two tuples so we can insert/update/delete from it.
	 * Refer to the databaseSetupDemo.sql file to determine what tuples are going to be in the table.
	 */
	public void setupDatabase(TerminalTransactionsDelegate delegate) {
		this.delegate = delegate;
		
		bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		int choice = INVALID_INPUT;
		
		while(choice != 1 && choice != 2) {
			System.out.println("If you have a table called Branch in your database (capitialization of the name does not matter), it will be dropped and a new Branch table will be created.\nIf you want to proceed, enter 1; if you want to quit, enter 2.");
			
			choice = readInteger(false);
			
			if (choice != INVALID_INPUT) {
				switch (choice) {
				case 1:  
					delegate.databaseSetup(); 
					break;
				case 2:  
					handleQuitOption();
					break;
				default:
					System.out.println(WARNING_TAG + " The number that you entered was not a valid option.\n");
					break;
				}
			}
		}
	}

	/**
	 * Displays simple text interface
	 */ 
	public void showMainMenu(TerminalTransactionsDelegate delegate) {
		this.delegate = delegate;
		
	    bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		int choice = INVALID_INPUT;
		
		while (choice != 5) {
			System.out.println();
			System.out.println("1. Insert fund");
			System.out.println("2. Delete fund");
			System.out.println("3. Update fund contribution");
			System.out.println("4. Show Sponsors");
			System.out.println("5. Quit");
			System.out.println("6. Show Funds");
			System.out.println("7. Find Leagues");
			System.out.println("8. Get Games");
			System.out.println("9. Players who have played all sports in the database.");
			System.out.println("10. Show Networks.");
			System.out.println("11. Show Number of teams per city.");
			System.out.println("12. Show Broadcasts with >1 broadcast?");
			System.out.println("13. Show tier 1 league with highest revenue.");
			System.out.print("Please choose one of the above 9 options: ");

			choice = readInteger(false);

			System.out.println(" ");

			if (choice != INVALID_INPUT) {
				switch (choice) {
				case 1:  
					handleInsertOption(); 
					break;
				case 2:  
					handleDeleteOption(); 
					break;
				case 3: 
					handleUpdateOption();
					break;
				case 4:  
//					delegate.showSponsors();
					break;
				case 5:
					handleQuitOption();
					break;
				case 6:
					delegate.showFunds();
					break;
				case 7:
					handleFindLeagueOption();
					break;
				case 8:
					delegate.getAllGames();
					break;
				case 9:
					delegate.getPlayersPlayedAllSports();
					break;
				case 10:
					delegate.getNetworkNames();
					break;
				case 11:
					delegate.getNumTeamsInCity();
					break;
				case 12:
					delegate.getMultipleBroadcasts();
					break;
				case 13:
					delegate.getHighestTierRevenue();
					break;
				default:
					System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
					break;
				}
			}
		}		
	}

	private void handleFindLeagueOption() {
		String sport = null;
		while (sport == null) {
			System.out.print("Please enter the sport the league you want to find organises: ");
			sport = readLine().trim();
		}

		delegate.selectLeaguesPlayingSport(sport);
	}

	private void handleDeleteOption() {
//		int branchId = INVALID_INPUT;
//		while (branchId == INVALID_INPUT) {
//			System.out.print("Please enter the branch ID you wish to delete: ");
//			branchId = readInteger(false);
//			if (branchId != INVALID_INPUT) {
//				delegate.deleteBranch(branchId);
//			}
//		}


		String sponName = "";
		String lName = "";
		while(sponName == "") {
			System.out.print("Please enter the sponsor name you wish to delete: ");
			sponName = readLine().trim();
		}
		while(lName == "") {
			System.out.print("Please enter the league name you wish to delete: ");
			lName = readLine().trim();
		}
		delegate.deleteFund(sponName, lName);
	}
	
	private void handleInsertOption() {
		String sponName = null;
		while (sponName == null || sponName.length() <= 0 ) {
			System.out.print("Please enter the sponsor name  you wish to insert: ");
			sponName = readLine().trim();
		}

		String lName = null;
		while (lName == null || lName.length() <= 0 ) {
			System.out.print("Please enter the league name  you wish to insert: ");
			lName = readLine().trim();
		}

//		System.out.print("Please enter the contribution value:  ");
//		float monetaryContributions = (float) Double.parseDouble(readLine().trim());
//		if(monetaryContributions == INVALID_INPUT) {
//			monetaryContributions = 0;
//		}

		System.out.print("Please enter the Amount you would like to sponsor: ");
		String monetaryInput = readLine().trim();

		float monetaryContributions = 0; // or any other default value that makes sense in your context

		if (!monetaryInput.isEmpty()) {
			try {
				monetaryContributions = Float.parseFloat(monetaryInput);
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please enter a valid floating-point number for the address.");
			}
		}

//		int id = INVALID_INPUT;
//		while (id == INVALID_INPUT) {
//			System.out.print("Please enter the branch ID you wish to insert: ");
//			id = readInteger(false);
//		}
//
//		String name = null;
//		while (name == null || name.length() <= 0) {
//			System.out.print("Please enter the branch name you wish to insert: ");
//			name = readLine().trim();
//		}
//
//		// branch address is allowed to be null so we don't need to repeatedly ask for the address
//		System.out.print("Please enter the branch address you wish to insert: ");
//		String address = readLine().trim();
//		if (address.length() == 0) {
//			address = null;
//		}

//		String city = null;
//		while (city == null || city.length() <= 0) {
//			System.out.print("Please enter the branch city you wish to insert: ");
//			city = readLine().trim();
//		}
//
//		int phoneNumber = INVALID_INPUT;
//		while (phoneNumber == INVALID_INPUT) {
//			System.out.print("Please enter the branch phone number you wish to insert: ");
//			phoneNumber = readInteger(true);
//		}

//		BranchModel model = new BranchModel(address,
//											city,
//											id,
//											name,
//											phoneNumber);
//		delegate.insertBranch(model);


		Funds model = new Funds(sponName,lName,monetaryContributions);
		try {
			delegate.insertFund(model);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void handleQuitOption() {
		System.out.println("Good Bye!");

		if (bufferedReader != null) {
			try {
				bufferedReader.close();
			} catch (IOException e) {
				System.out.println("IOException!");
			}
		}

		delegate.terminalTransactionsFinished();
	}
	
	private void handleUpdateOption() {
		String sponName = null;
		while (sponName == null) {
			System.out.print("Please enter the Sponsor name you wish to update: ");
			sponName = readLine().trim();
		}

		String lName = null;
		while (lName == null || lName.length() <= 0) {
			System.out.print("Please enter the league name the sponsor contributed to: ");
			lName = readLine().trim();
		}

		System.out.print("Please enter the contribution value:  ");
		String monetaryInput = readLine().trim();

		float monetaryContributions = INVALID_INPUT; // or any other default value that makes sense in your context

		if (!monetaryInput.isEmpty()) {
			try {
				monetaryContributions = Float.parseFloat(monetaryInput);
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please enter a valid floating-point number for the address.");
			}
		}
		Funds f = new Funds(sponName, lName, monetaryContributions);
		delegate.updateFund(f);
	}
	
	private int readInteger(boolean allowEmpty) {
		String line = null;
		int input = INVALID_INPUT;
		try {
			line = bufferedReader.readLine();
			input = Integer.parseInt(line);
		} catch (IOException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		} catch (NumberFormatException e) {
			if (allowEmpty && line.length() == 0) {
				input = EMPTY_INPUT;
			} else {
				System.out.println(WARNING_TAG + " Your input was not an integer");
			}
		}
		return input;
	}
	
	private String readLine() {
		String result = null;
		try {
			result = bufferedReader.readLine();
		} catch (IOException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
		return result;
	}
}

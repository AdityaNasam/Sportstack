package project.delegates;

//import ca.ubc.cs304.model.BranchModel;

import project.model.Funds;

import java.sql.SQLException;

/**
 * This interface uses the delegation design pattern where instead of having
 * the TerminalTransactions class try to do everything, it will only
 * focus on handling the UI. The actual logic/operation will be delegated to the 
 * controller class (in this case Bank).
 * 
 * TerminalTransactions calls the methods that we have listed below but 
 * Bank is the actual class that will implement the methods.
 *
 * DIRECTLY FROM JAVA DEMO PROJECT
 */
public interface TerminalTransactionsDelegate {
	public void databaseSetup();
	
	public void deleteFund(String sponName, String lName);
	public void insertFund(Funds f) throws SQLException;
//	public void showSponsors();
	public void showFunds();
	public void updateFund(Funds f);
	public void selectLeaguesPlayingSport(String sport);
	public void getPlayersPlayedAllSports();
	public void getAllGames();
	public void getNetworkNames();
	public void getNumTeamsInCity();
	public void getMultipleBroadcasts();
	public void getHighestTierRevenue();
	public void terminalTransactionsFinished();
}

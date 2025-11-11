package project.controller;

import project.database.DatabaseConnectionHandler;
import project.delegates.LoginWindowDelegate;
import project.delegates.TerminalTransactionsDelegate;
import project.model.Funds;
import project.ui.LoginWindow;
import project.ui.TerminalTransactions;
import project.ui.UITransactions;

import java.sql.SQLException;

// Heavily inspired by the course Java Demo project
public class SportsTerminalController implements LoginWindowDelegate, TerminalTransactionsDelegate {
    private DatabaseConnectionHandler dbHandler = null;
    private LoginWindow loginWindow = null;

    private UITransactions uiTransactions = null;

    public SportsTerminalController() {
        dbHandler = new DatabaseConnectionHandler();
    }

    private void start() {
        loginWindow = new LoginWindow();
        loginWindow.showFrame(this);
    }

    public void login(String username, String password) {
        boolean didConnect = dbHandler.login(username, password);

        if (didConnect) {
            // Once connected, remove login window and start text transaction flow
            loginWindow.dispose();

            TerminalTransactions transaction = new TerminalTransactions();
//            transaction.setupDatabase(this);

            transaction.showMainMenu(this);
        } else {
            loginWindow.handleLoginFailed();

            if (loginWindow.hasReachedMaxLoginAttempts()) {
                loginWindow.dispose();
                System.out.println("You have exceeded your number of allowed attempts");
                System.exit(-1);
            }
        }
    }

    public void databaseSetup() {

    }

    public void deleteFund(String sponName, String lName) {
        dbHandler.deleteFund(sponName, lName);
    }

    public void insertFund(Funds f) throws SQLException {
        dbHandler.insertFund(f);
    }

//    public void showSponsors() {
//        Sponsor[] sponsors = dbHandler.getSponsorInfo();
//
//        for (int i = 0; i < sponsors.length; i++) {
//            Sponsor sponsor = sponsors[i];
//
//            // simplified output formatting; truncation may occur
//            System.out.printf("%-10.10s", sponsor.getSponName());
//            System.out.printf("%-20.20s", sponsor.getNetworth());
//
//            System.out.println();
//        }
//    }

    public void showFunds() {
        Funds[] funds = dbHandler.getFundInfo();

        for (int i = 0; i < funds.length; i++) {
            Funds fund = funds[i];

            // simplified output formatting; truncation may occur
            System.out.printf("%-10.10s", fund.getSponName());
            System.out.printf("%-20.20s", fund.getLName());
            System.out.printf("%-20.20s", fund.getMonetaryContribution());

            System.out.println();
        }
    }

    public void updateFund(Funds f) {
        dbHandler.updateFund(f);
    }

    public void updateBranch(int branchId, String name) {

    }

    public void terminalTransactionsFinished() {
        dbHandler.close();
        dbHandler = null;

        System.exit(0);
    }

    public void selectLeaguesPlayingSport(String sport) {
//        String[] data = dbHandler.getLeaguesFilter(sport);
//
//        for (int i = 0; i < data.length; i++) {
//            // simplified output formatting; truncation may occur
//            System.out.printf(data[i]);
//            System.out.println();
//        }
    }

    public void getAllGames() {
        String[] data = dbHandler.getAllGamesInfo();

        for (int i = 0; i < data.length; i++) {
            // simplified output formatting; truncation may occur
            System.out.printf(data[i]);
            System.out.println();
        }
    }

    // DIVISION: find names of sportPersons who have played all sports in database

    public void getPlayersPlayedAllSports() {
        String[] data = dbHandler.getPlayersPlayingAllSportsInfo();

        for (int i = 0; i < data.length; i++) {
            // simplified output formatting; truncation may occur
            System.out.printf(data[i]);
            System.out.println();
        }
    }

    public void getNetworkNames() {
        String[] data = dbHandler.getNetworkNamesInfo();

        for (int i = 0; i < data.length; i++) {
            // simplified output formatting; truncation may occur
            System.out.printf(data[i]);
            System.out.println();
        }
    }

    public void getNumTeamsInCity() {
        String[] data = dbHandler.getNumTeamsInCityInfo();

        for (int i = 0; i < data.length; i++) {
            // simplified output formatting; truncation may occur
            System.out.printf(data[i]);
            System.out.println();
        }
    }

    public void getMultipleBroadcasts() {
        String[] data = dbHandler.getMultipleBroadCastsInfo();

        for (int i = 0; i < data.length; i++) {
            // simplified output formatting; truncation may occur
            System.out.printf(data[i]);
            System.out.println();
        }
    }

    public void getHighestTierRevenue() {
        String data = dbHandler.getHighestTierRevenueInfo();

        System.out.printf(data);
        System.out.println();
    }

    public static void main(String args[]) {
        SportsTerminalController sCon = new SportsTerminalController();
        sCon.start();
    }
//    Natural join for game:
//    select * from GameHostsInSeason natural join GameDateYear natural join GameTeamAOpponent natural join GameAddressDate natural join GameTeamAScore natural join GameTeamBScore natural join GameAddress natural join LeagueTeamA;
//    Natural join for sportsPerson:
//    select * from SPORTSPERSON natural join SPORTSPERSONAGE natural join SPORTSPERSONDEATHDATE natural join SPORTSPERSONDOB natural join SPORTSPERSONNATIONALITY natural join SPORTSPERSONNAME natural join SPORTSPERSONSALARY;

}
package project.controller;

import project.database.DatabaseConnectionHandler;
import project.delegates.LoginWindowDelegate;
import project.delegates.UITransactionsDelegate;
import project.model.Funds;
import project.model.LeagueOrganises;
import project.ui.LoginWindow;
import project.ui.TerminalTransactions;
import project.ui.UITransactions;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

// Heavily inspired by the course Java Demo project
public class SportsUIController implements LoginWindowDelegate, UITransactionsDelegate {
    private DatabaseConnectionHandler dbHandler = null;
    private LoginWindow loginWindow = null;

    private UITransactions uiTransactions = null;

    public SportsUIController() {
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

            uiTransactions = new UITransactions();
            uiTransactions.showFrame(this);
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

    public void deleteFundUI() {
        this.showFundsUI();
        String[] sponsors = dbHandler.getAllSponsorNames();
        String selectedSponsor = (String) JOptionPane.showInputDialog(null, "Select Sponsor",
                "Select Sponsor", JOptionPane.QUESTION_MESSAGE, null, sponsors, sponsors[0]);
        String[] leagues = dbHandler.getLeagueNamesBySponsor(selectedSponsor);
        String selectedLeague = (String) JOptionPane.showInputDialog(null, "Select League",
                "Select League", JOptionPane.QUESTION_MESSAGE, null, leagues, leagues[0]);

        dbHandler.deleteFund(selectedSponsor, selectedLeague);
        uiTransactions.removeTable();
        this.showFundsUI();
    }

    public void insertFundUI() {
        this.showFundsUI();
        try{
            String enteredSponsor = (String) JOptionPane.showInputDialog(null, "Enter Sponsor",
                    "Enter Sponsor", JOptionPane.QUESTION_MESSAGE);
            String[] leagues = dbHandler.getAllLeagueNames();
            String selectedLeague = (String) JOptionPane.showInputDialog(null, "Select League",
                    "Select League", JOptionPane.QUESTION_MESSAGE, null, leagues, leagues[0]);
            float amount = Float.parseFloat(JOptionPane.showInputDialog(null, "Enter Amount to add to current funding",
                    "Enter Amount", JOptionPane.QUESTION_MESSAGE));
            Funds f = new Funds(enteredSponsor, selectedLeague, amount);
            try {
                dbHandler.insertFund(f);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage() + ". Please enter a Number.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        uiTransactions.removeTable();
        this.showFundsUI();
        JOptionPane.showMessageDialog(null, "Successfully Added the funds",
                "Success", JOptionPane.INFORMATION_MESSAGE);

    }

    public void updateFundUI() {
        String[] sponsors = dbHandler.getAllSponsorNames();
        String selectedSponsor = (String) JOptionPane.showInputDialog(null, "Select Sponsor",
                "Select Sponsor", JOptionPane.QUESTION_MESSAGE, null, sponsors, sponsors[0]);
        String[] leagues = dbHandler.getLeagueNamesBySponsor(selectedSponsor);
        String selectedLeague = (String) JOptionPane.showInputDialog(null, "Select League",
                "Select League", JOptionPane.QUESTION_MESSAGE, null, leagues, leagues[0]);
        float amount = Float.parseFloat(JOptionPane.showInputDialog(null, "Enter Amount to add to current funding",
                "Enter Amount", JOptionPane.QUESTION_MESSAGE));
        float currentFunding = dbHandler.getContribution(selectedSponsor, selectedLeague);
        Funds f = new Funds(selectedSponsor, selectedLeague, currentFunding + amount);
        dbHandler.updateFund(f);
        this.showFundsUI();
    }


    public void updateLeagueUI() {
        this.showLeaguesUI();
        try {
            String[] leagues = dbHandler.getAllLeagueNames();
            String selectedLeague = (String) JOptionPane.showInputDialog(null, "Select League",
                    "Select League", JOptionPane.QUESTION_MESSAGE, null, leagues, leagues[0]);
            String[] sports = dbHandler.getAllSportNames();
            String selectedSport = (String) JOptionPane.showInputDialog(null, "Select Sport",
                    "Select Sport", JOptionPane.QUESTION_MESSAGE, null, sports, sports[0]);
            int tier = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter tier you want to update to",
                    "Enter Tier", JOptionPane.QUESTION_MESSAGE));
            float rev = Float.parseFloat(JOptionPane.showInputDialog(null, "Enter revenue for the league",
                    "Enter Amount", JOptionPane.QUESTION_MESSAGE));
            LeagueOrganises league = new LeagueOrganises(selectedLeague, tier, rev, selectedSport);
            try {
                dbHandler.updateLeague(league);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage() + ". Please enter a Number.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        uiTransactions.removeTable();
        this.showLeaguesUI();
        JOptionPane.showMessageDialog(null, "Successfully updated the league info",
                "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showLeaguesUI() {
        String[] data = dbHandler.getAllLeaguesInfo();

        String[] columnNames = {"League Name", "Sport", "Tier", "Revenue (million $)"};

        String[][] data2D = new String[data.length][4];
        for (int i = 0; i < data.length; i++) {
            String[] tuple = data[i].split("~");
            data2D[i][0] = tuple[0];
            data2D[i][1] = tuple[1];
            data2D[i][2] = tuple[2];
            data2D[i][3] = tuple[3];
        }
        displayDataTable(data2D, columnNames, "Leagues");
    }

//    public void showSponsorsUI() {
//
//        String[] columnNames = {"Name", "Networth"};
//
//        String selectedColumn = (String) JOptionPane.showInputDialog(null, "Select column of Sponsor",
//                "Select Sport", JOptionPane.QUESTION_MESSAGE, null, columnNames, columnNames[0]);
//
//        Sponsor[] sponsors = dbHandler.getSponsorInfo(selectedColumn);
//
//        String[][] data = new String[sponsors.length][2];
//
//        for (int i = 0; i < sponsors.length; i++) {
//            data[i][0] = sponsors[i].getSponName();
//            data[i][1] = String.valueOf(sponsors[i].getNetworth());
//        }
//
//        displayDataTable(data, columnNames, "Sponsors");
//    }

    public void projectionUI() {
        String[] tableNames = dbHandler.getTableNames();

        String selectedTable = (String) JOptionPane.showInputDialog(null, "Select table",
                "Select Table", JOptionPane.QUESTION_MESSAGE, null, tableNames, tableNames[0]);

        String[] columnNames = dbHandler.getColumnNames(selectedTable);

        ArrayList<String> selectedColumns = new ArrayList<String>();

        do {
            selectedColumns.add((String) JOptionPane.showInputDialog(null, "Select column",
                    "Select Column", JOptionPane.QUESTION_MESSAGE, null, columnNames, columnNames[0]));
        }
        while(JOptionPane.showConfirmDialog(null, "Would you like add more attributes?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_NO_OPTION);

        String[] selectedColumnsArray = selectedColumns.toArray(new String[0]);

        displayDataTable(dbHandler.makeProjectionQuery(selectedTable, selectedColumnsArray), selectedColumnsArray, "Projection");
    }

    public void filterLeaguesUI() {
        this.showLeaguesUI();
        String[] sports = dbHandler.getAllSportsNames();
        String[] leagueNames = dbHandler.getAllLeagueNames();

        String[] availableColumns = {"League Name", "Sport", "Tier", "Revenue"};

        ArrayList<String> selectedInfo = new ArrayList<String>();

        do {
            String selectedAttribute = (String) JOptionPane.showInputDialog(null, "Select Attribute to filter",
                    "Select Attribute", JOptionPane.QUESTION_MESSAGE, null, availableColumns, availableColumns[0]);

            switch (selectedAttribute) {
                case "League Name":
                    selectedInfo.add("lName = " + "'" + (String) JOptionPane.showInputDialog(null, "Select League Name",
                            "Select Name", JOptionPane.QUESTION_MESSAGE, null, leagueNames, leagueNames[0]) + "'");
                    break;
                case "Sport":
                    selectedInfo.add("sportName = " + "'" + (String) JOptionPane.showInputDialog(null, "Select Sport",
                            "Select Sport", JOptionPane.QUESTION_MESSAGE, null, sports, sports[0]) + "'");
                    break;
                case "Tier":
                    int tier = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter league tier",
                            "Enter Tier", JOptionPane.QUESTION_MESSAGE));
                    selectedInfo.add("tier = " + String.valueOf(tier));
                    break;
                case "Revenue":
                    float rev = Float.parseFloat(JOptionPane.showInputDialog(null, "Enter minimum revenue in millions of USD",
                            "Enter Revenue", JOptionPane.QUESTION_MESSAGE));
                    selectedInfo.add("revenue >= " + String.valueOf(rev));
                    break;
                default:
                    // shouldn't occur
                    break;
            }
        }
        while(JOptionPane.showConfirmDialog(null, "Would you like add more attributes to the filter?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_NO_OPTION);

        String[] selectedInfoArray = selectedInfo.toArray(new String[0]);

        String[] data = dbHandler.getLeaguesFilter(selectedInfoArray);

        String[] columnNames = {"League Name", "Sport", "Tier", "Revenue (million $)"};

        String[][] data2D = new String[data.length][4];
        for (int i = 0; i < data.length; i++) {
            String[] tuple = data[i].split("~");
            data2D[i][0] = tuple[0];
            data2D[i][1] = tuple[1];
            data2D[i][2] = tuple[2];
            data2D[i][3] = tuple[3];
        }
        uiTransactions.removeTable();
        displayDataTable(data2D, columnNames, "LeaguesFiltered");
    }

    public void showFundsUI() {
        Funds[] funds = dbHandler.getFundInfo();

        String[][] data = new String[funds.length][3];
        for (int i = 0; i < funds.length; i++) {
            data[i][0] = funds[i].getSponName();
            data[i][1] = funds[i].getLName();
            data[i][2] = String.valueOf(funds[i].getMonetaryContribution());
        }

        String[] columnNames = {"Sponsor", "League", "Funds"};
        displayDataTable(data, columnNames, "Funds");
    }

    public void terminalTransactionsFinishedUI() {
        dbHandler.close();
        dbHandler = null;
        System.exit(0);
    }

    public void getAllGamesMinScoreUI() {
        this.getAllGamesUI();

        int minScore = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter minimum total score",
                "Enter Min Score", JOptionPane.QUESTION_MESSAGE));

        String[] data = dbHandler.getAllGamesInfoMinScore(minScore);

        String[] columnNames = {"League Name", "Date", "Home Team", "Away Team", "Score"};

        String[][] data2D = new String[data.length][5];
        for (int i = 0; i < data.length; i++) {
            String[] tuple = data[i].split("~");
            data2D[i][0] = tuple[0];
            data2D[i][1] = tuple[1];
            data2D[i][2] = tuple[2] + " " + tuple[3];
            data2D[i][3] = tuple[4] + " " + tuple[5];
            data2D[i][4] = tuple[6] + " - " + tuple[7];
        }
        uiTransactions.removeTable();
        displayDataTable(data2D, columnNames, "All Games Min Score");
    }

    public void getAllGamesUI() {
        String[] data = dbHandler.getAllGamesInfo();

        String[] columnNames = {"League Name", "Date", "Home Team", "Away Team", "Score"};

        String[][] data2D = new String[data.length][5];
        for (int i = 0; i < data.length; i++) {
            String[] tuple = data[i].split("~");
            data2D[i][0] = tuple[0];
            data2D[i][1] = tuple[1];
            data2D[i][2] = tuple[2] + " " + tuple[3];
            data2D[i][3] = tuple[4] + " " + tuple[5];
            data2D[i][4] = tuple[6] + " - " + tuple[7];
        }
        displayDataTable(data2D, columnNames, "All Games");
    }

    // DIVISION: find names of sportPersons who have played all sports in database

    public void getPlayersPlayedAllSportsUI() {
        String[] data = dbHandler.getPlayersPlayingAllSportsInfo();

        String[] columnNames = {"Player Name"};

        String[][] data2D = new String[data.length][1];
        for (int i = 0; i < data.length; i++) {
            data2D[i][0] = data[i];
        }

        displayDataTable(data2D, columnNames, "PlayersAllSports");
    }

    private void displayDataTable(String[][] data2D, String[] columnNames, String title) {
        DefaultTableModel tableModel = new DefaultTableModel(data2D, columnNames);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setName(UITransactions.tableName);
        uiTransactions.setTitle(title);
        uiTransactions.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        scrollPane.setPreferredSize(new Dimension(1000, 200));
        uiTransactions.add(scrollPane);
        uiTransactions.setLocationRelativeTo(null);
        uiTransactions.setVisible(true);
        uiTransactions.pack();
    }

    public void getNetworkNamesUI() {
        String[] data = dbHandler.getNetworkNamesInfo();

        String[][] data2D = new String[data.length][1];
        for (int i = 0; i < data.length; i++) {
            data2D[i][0] = data[i];
        }

        String[] columnNames = {"NetWork"};
        displayDataTable(data2D, columnNames, "Networks");
    }

    public void getNumTeamsInCityUI() {
        String[] data = dbHandler.getNumTeamsInCityInfo();

        String[] columnNames = {"City", "Number of Teams"};

        String[][] data2D = new String[data.length][2];
        for (int i = 0; i < data.length; i++) {
            String[] tuple = data[i].split("~");
            data2D[i][0] = tuple[0];
            data2D[i][1] = tuple[1];
        }

        displayDataTable(data2D, columnNames, "NumTeamsInCity");
    }

    public void getMultipleBroadcastsUI() {
        String[] data = dbHandler.getMultipleBroadCastsInfo();

        for (int i = 0; i < data.length; i++) {
            // simplified output formatting; truncation may occur
            System.out.printf(data[i]);
            System.out.println();
        }

        String[] columnNames = {"Network", "Number of Broadcasts"};

        String[][] data2D = new String[data.length][2];
        for (int i = 0; i < data.length; i++) {
            String[] tuple = data[i].split("~");
            data2D[i][0] = tuple[0];
            data2D[i][1] = tuple[1];
        }
        displayDataTable(data2D, columnNames, "MultipleBroadcasts");
    }

    public void getHighestTierRevenueUI() {
        String highestTierRevenue = dbHandler.getHighestTierRevenueInfo();

        System.out.printf(highestTierRevenue);
        System.out.println();

        String[] columnNames = {"Tier", "Revenue ($ million)"};

        String[][] data2D = new String[1][2];
        String[] tuple = highestTierRevenue.split("~");
        data2D[0][0] = tuple[0];
        data2D[0][1] = tuple[1];

        displayDataTable(data2D, columnNames, "HighestTierRevenue");
    }

    public void deleteNetworkUI() {
        String[] networks = dbHandler.getNetworkNamesInfo();
        String selectedNetwork = (String) JOptionPane.showInputDialog(null, "Select Network you want to Delete",
                "Select Network", JOptionPane.QUESTION_MESSAGE, null, networks, networks[0]);
        if (dbHandler.deleteNetwork(selectedNetwork)) {
            JOptionPane.showMessageDialog(null, selectedNetwork + " successfully removed!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "An error occurred removing network " + selectedNetwork,
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String args[]) {
        SportsUIController sCon = new SportsUIController();
        sCon.start();
    }
//    Natural join for game:
//    select * from GameHostsInSeason natural join GameDateYear natural join GameTeamAOpponent natural join GameAddressDate natural join GameTeamAScore natural join GameTeamBScore natural join GameAddress natural join LeagueTeamA;
//    Natural join for sportsPerson:
//    select * from SPORTSPERSON natural join SPORTSPERSONAGE natural join SPORTSPERSONDEATHDATE natural join SPORTSPERSONDOB natural join SPORTSPERSONNATIONALITY natural join SPORTSPERSONNAME natural join SPORTSPERSONSALARY;
}
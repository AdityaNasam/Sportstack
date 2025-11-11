package project.database;

import java.sql.*;
import java.util.ArrayList;

import project.model.Funds;
import project.model.LeagueOrganises;
import project.util.PrintablePreparedStatement;

/**
 * Structured from cs304 Java demo project.
 * This class handles all database related transactions
 */
public class DatabaseConnectionHandler {
    // Use this version of the ORACLE_URL if you are running the code off of the server
//	private static final String ORACLE_URL = "jdbc:oracle:thin:@dbhost.students.cs.ubc.ca:1522:stu";
    // Use this version of the ORACLE_URL if you are tunneling into the undergrad servers
    private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";

    private Connection connection = null;

    public DatabaseConnectionHandler() {
        try {
            // Load the Oracle JDBC driver
            // Note that the path could change for new drivers
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    private void rollbackConnection() {
        try  {
            connection.rollback();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public boolean login(String username, String password) {
        try {
            if (connection != null) {
                connection.close();
            }

            connection = DriverManager.getConnection(ORACLE_URL, username, password);
            connection.setAutoCommit(false);

            System.out.println("\nConnected to Oracle!");
            return true;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return false;
        }
    }

//    public Sponsor[] getSponsorInfo(String column) {
//        ArrayList<Sponsor> result = new ArrayList<Sponsor>();
//
//        try {
//            String query = "SELECT ? FROM sponsor";
//            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
//            ps.setString(1, column);
//            ResultSet rs = ps.executeQuery();
//
//            while(rs.next()) {
//                Sponsor model = new Sponsor(rs.getString("sponName"),
//                        rs.getFloat("networth"));
//                result.add(model);
//            }
//
//            rs.close();
//            ps.close();
//        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//        }
//
//        return result.toArray(new Sponsor[result.size()]);
//    }

    public Funds[] getFundInfo() {
        ArrayList<Funds> result = new ArrayList<Funds>();

        try {
            String query = "SELECT * FROM funds";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Funds model = new Funds(rs.getString("sponName"),
                        rs.getString("lName"),
                        rs.getFloat("monetaryContribution"));
                result.add(model);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new Funds[result.size()]);
    }

    public void databaseSetup() {}

    public void updateFund(Funds f) {
        try {
            String query = "UPDATE Funds SET monetaryContribution = ? WHERE sponName = ? AND lName = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setFloat(1, f.getMonetaryContribution());
            ps.setString(2, f.getSponName());
            ps.setString(3, f.getLName());

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " Funds " + f.getSponName() + ", " + f.getLName() + " does not exist!");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void updateLeague(LeagueOrganises l) throws SQLException {
        try {
            String query = "UPDATE LeagueOrganises SET tier = ?, revenue = ?, sportName = ? WHERE lName = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, l.getTier());
            ps.setFloat(2, l.getRevenue());
            ps.setString(3, l.getSportName());
            ps.setString(4, l.getlName());

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " League " + l.getlName() + " does not exist!");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            rollbackConnection();
            throw e;
        }
    }

    public void insertFund(Funds f) throws SQLException{
        try {
            String query = "INSERT INTO Sponsor VALUES (?,?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, f.getSponName());
            ps.setNull(2, java.sql.Types.FLOAT);

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        try {
            String query = "INSERT INTO Funds VALUES (?,?,?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, f.getSponName());
            ps.setString(2, f.getLName());
            ps.setFloat(3, f.getMonetaryContribution());

            ps.executeUpdate();
            // hanging here for some reason...
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
            throw e;
        }
    }

    public void deleteFund(String sponName, String lName) {
        try {
            String query = "DELETE FROM Funds WHERE sponName = ? AND lName = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
//            ps.setInt(1, branchId);
            ps.setString(1,sponName);
            ps.setString(2,lName);
//            ps.setFloat(3,0);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " sponsor and league combination" + sponName +" "+lName + " does not exist!");
            }

            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    // SELECTION
    public String[] getLeaguesFilter(String[] filters) {
        String concatFilters = String.join(" OR ", filters);
        ArrayList<String> result = new ArrayList<String>();

        try {
            String query = "SELECT * FROM LeagueOrganises WHERE " + concatFilters;
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                String data = String.join("~",
                        rs.getString("lName"),
                        rs.getString("sportName"),
                        String.valueOf(rs.getInt("tier")),
                        String.valueOf(rs.getFloat("revenue")));
                result.add(data);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new String[result.size()]);
    }

    public String[] getAllGamesInfo() {
        ArrayList<String> result = new ArrayList<String>();

        try {
            String query = "SELECT lName, gameDate, cityA, tNameA, cityB, tNameB, scoreA, scoreB FROM GameHostsInSeason natural join GameDateYear natural join GameTeamAOpponent natural join GameAddressDate natural join GameTeamAScore natural join GameTeamBScore natural join GameAddress natural join LeagueTeamA ORDER BY lName, gameDate desc";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                String data = String.join("~",
                        rs.getString("lName"),
                        String.valueOf(rs.getDate("gameDate")),
                        rs.getString("cityA"),
                        rs.getString("tNameA"),
                        rs.getString("cityB"),
                        rs.getString("tNameB"),
                        String.valueOf(rs.getInt("scoreA")),
                        String.valueOf(rs.getInt("scoreB")));
                result.add(data);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new String[result.size()]);
    }

    public String[] getAllGamesInfoMinScore(int minScore) {
        ArrayList<String> result = new ArrayList<String>();

        try {
            String query = "SELECT lName, gameDate, cityA, tNameA, cityB, tNameB, scoreA, scoreB FROM GameHostsInSeason natural join GameDateYear natural join GameTeamAOpponent natural join GameAddressDate natural join GameTeamAScore natural join GameTeamBScore natural join GameAddress natural join LeagueTeamA WHERE scoreA + ScoreB >= ? ORDER BY lName, gameDate desc";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, minScore);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                String data = String.join("~",
                        rs.getString("lName"),
                        String.valueOf(rs.getDate("gameDate")),
                        rs.getString("cityA"),
                        rs.getString("tNameA"),
                        rs.getString("cityB"),
                        rs.getString("tNameB"),
                        String.valueOf(rs.getInt("scoreA")),
                        String.valueOf(rs.getInt("scoreB")));
                result.add(data);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new String[result.size()]);
    }

    public String[] getAllLeaguesInfo() {
        ArrayList<String> result = new ArrayList<String>();

        try {
            String query = "SELECT * FROM LeagueOrganises";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                String data = String.join("~",
                        rs.getString("lName"),
                        rs.getString("sportName"),
                        String.valueOf(rs.getInt("tier")),
                        String.valueOf(rs.getFloat("revenue")));
                result.add(data);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new String[result.size()]);
    }

    // DIVISION
    public String[] getPlayersPlayingAllSportsInfo() {
        ArrayList<String> result = new ArrayList<String>();

        try {
            String query = "SELECT p.spName From sportsPersonName p Where NOT EXISTS ((SELECT s.sportName FROM Sport s) MINUS (SELECT t.sportName FROM InR i, TeamHomeStadium t WHERE i.tName = t.tName AND i.city = t.city AND p.spID = i.spID))";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                String data = rs.getString("spName");
                result.add(data);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new String[result.size()]);
    }

    public String[] getNetworkNamesInfo() {

        ArrayList<String> result = new ArrayList<String>();

        try {

            String query = "SELECT nName FROM Network";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String nName = rs.getString("nName");
                result.add(nName);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new String[result.size()]);
    }

    public String[] getNumTeamsInCityInfo() {

        ArrayList<String> result = new ArrayList<>();

        try {

            String query = "SELECT city, COUNT(*) AS count FROM TeamHomeStadium GROUP BY city";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String row = String.join("~",
                        rs.getString("city"),
                        rs.getString("count")
                );

                result.add(row);
            }

            rs.close();
            ps.close();


        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new String[result.size()]);
    }

    public String[] getMultipleBroadCastsInfo() {

        ArrayList<String> result = new ArrayList<>();

        try {

            String query = "SELECT nName, COUNT(*) AS count FROM Broadcasts GROUP BY nName HAVING COUNT(*) > 1";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String row = String.join("~",
                        rs.getString("nName"),
                        rs.getString("count")
                );

                result.add(row);
            }

            rs.close();
            ps.close();


        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new String[result.size()]);
    }

    public String getHighestTierRevenueInfo() {
        String result = null;

        try {
            String query = "SELECT * FROM (SELECT tier, AVG(revenue) AS avg_revenue FROM LeagueOrganises GROUP BY tier) WHERE avg_revenue = (SELECT MAX(avg_revenue) FROM (SELECT tier, AVG(revenue) AS avg_revenue FROM LeagueOrganises GROUP BY tier))";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                result = String.join("~",
                        String.valueOf(rs.getInt("tier")), //maybe add tier
                        String.valueOf(rs.getFloat("avg_revenue"))
                );
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result;
    }

    public String[] getAllSportsNames() {
        ArrayList<String> result = new ArrayList<String>();

        try {
            String query = "SELECT sportName FROM Sport";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                String data = rs.getString("sportName");
                result.add(data);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new String[result.size()]);
    }

    public String[] getAllLeagueNames() {
        ArrayList<String> result = new ArrayList<String>();

        try {
            String query = "SELECT lName FROM LeagueOrganises";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                String data = rs.getString("lName");
                result.add(data);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new String[result.size()]);
    }

    public String[] getAllSportNames() {
        ArrayList<String> result = new ArrayList<String>();

        try {
            String query = "SELECT sportName FROM Sport";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                String data = rs.getString("sportName");
                result.add(data);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new String[result.size()]);
    }

    public String[] getAllSponsorNames() {
        ArrayList<String> result = new ArrayList<String>();

        try {
            String query = "SELECT sponName FROM Sponsor";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                String data = rs.getString("sponName");
                result.add(data);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new String[result.size()]);
    }

    public String[] getLeagueNamesBySponsor(String selectedSponsor) {
        ArrayList<String> result = new ArrayList<String>();

        try {
            String query = "SELECT lName FROM Funds WHERE sponName = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, selectedSponsor);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                String data = rs.getString("lName");
                result.add(data);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new String[result.size()]);
    }

    public float getContribution(String selectedSponsor, String selectedLeague) {
        float contribution = 0;
        try {
            String query = "SELECT monetaryContribution FROM Sponsor WHERE sponName = ? AND lName = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, selectedSponsor);
            ps.setString(2, selectedLeague);

            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                contribution = rs.getFloat("monetaryContribution");
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return contribution;
    }


    public String[][] makeProjectionQuery(String tableName, String[] columnNames) {

        String formattedColumnNames = String.join(", ", columnNames);

        ArrayList<String[]> results = new ArrayList<String[]>();
        try {

//            String query = buildDynamicProjectionQuery(tableName, columnNames);
            String query =  "SELECT " + formattedColumnNames + " FROM " + tableName;
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
//            for (int i = 1; i <= columnNames.length; i++) {
//                ps.setString(1, columnNames[i]);
//            }
//            ps.setString(2, tableName);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                String[] row = new String[columnNames.length];

                for (int i = 0; i < columnNames.length; i++) {
                    row[i] = rs.getString(columnNames[i]);
                }

                results.add(row);
            }
            rs.close();
            ps.close();

        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return results.toArray(new String[0][]);
    }

    private String buildDynamicProjectionQuery(String tableName, String[] columns) {
        StringBuilder query = new StringBuilder("SELECT");

        for(int i = 0; i < columns.length; i++) {
            if (i == 0) {
                query.append(" ?");
            } else {
                query.append(",?");
            }
        }

        query.append(" FROM ?");
        return query.toString();
    }

    public String[] getTableNames() {
        ArrayList<String> results = new ArrayList<String>();
        try {
            String query = "SELECT table_name FROM user_tables";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                results.add(rs.getString("table_name"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return results.toArray(new String[0]);
    }

    public String[] getColumnNames(String tableName) {
        ArrayList<String> results = new ArrayList<String>();
        try {
            String query = "SELECT column_name FROM user_tab_columns WHERE table_name = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, tableName);

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                results.add(rs.getString("column_name"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return results.toArray(new String[0]);
    }

    public boolean deleteNetwork(String name) {
        boolean r = true;
        try {
            String query = "DELETE FROM Network WHERE nName = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, name);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " Network " + name + " does not exist!");
            }

            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
            r = false;
        }
        return r;
    }
}
package project.ui;

import project.delegates.UITransactionsDelegate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UITransactions extends JFrame implements ActionListener {
    private static final int TEXT_FIELD_WIDTH = 10;
    private static final int MAX_LOGIN_ATTEMPTS = 3;

    // use 500 for skinny window
    public static final int WIN_WIDTH = 1000;

    public static final int WIN_HEIGHT = 1250;
    public static final String tableName = "theTable";

    // running accumulator for login attempts
    private int loginAttempts;

    // components of the login window
    private JTextField usernameField;
    private JPasswordField passwordField;

    // delegate
    private UITransactionsDelegate delegate;

    public UITransactions() {
        super("User Login");
    }

    public void showFrame(UITransactionsDelegate delegate) {
        this.delegate = delegate;

        JButton showFundsButton = new JButton("Show Funds");
        JButton filterLeaguesButton = new JButton("Filter Leagues");
        JButton showAllGamesButtonMinScore = new JButton("Show All Games With Minimum Total Score");
        JButton updateFundButton = new JButton("Update Existing Funds");
        JButton playersPlayedAllSportsButton = new JButton("Players that Play All Sports in the Database");
        JButton showNumTeamsInCityButton = new JButton("Show Number of Teams In Each City");
        JButton showMultipleBroadcastsButton = new JButton("Show Networks Broadcasting Multiple Games");
        JButton showHighestTierRevenueButton = new JButton("Show League Tier with Greatest Average Revenue");
        JButton showNetworkNamesButton = new JButton("Show Network Names");
        JButton insertFundButton = new JButton("Want to fund your favourite league? Click here today!");
        JButton deleteFundButton = new JButton("Delete Fund");
        JButton projectionButton = new JButton("View Data");
        JButton updateLeagueButton = new JButton("Update League");
        JButton showLeaguesButton = new JButton("Show Leagues");
        JButton deleteNetworkButton = new JButton("Delete Network");

        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);

        // layout components using the GridBag layout manager
        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        contentPane.setLayout(gb);
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(5, 10, 10, 10);
        c.anchor = GridBagConstraints.CENTER;

        gb.setConstraints(filterLeaguesButton, c);
        gb.setConstraints(showFundsButton, c);
        gb.setConstraints(projectionButton, c);
        gb.setConstraints(showAllGamesButtonMinScore, c);
        gb.setConstraints(updateFundButton, c);
        gb.setConstraints(playersPlayedAllSportsButton, c);
        gb.setConstraints(showNumTeamsInCityButton, c);
        gb.setConstraints(showMultipleBroadcastsButton, c);
        gb.setConstraints(showHighestTierRevenueButton, c);
        gb.setConstraints(showNetworkNamesButton, c);
        gb.setConstraints(insertFundButton, c);
        gb.setConstraints(deleteFundButton, c);
        gb.setConstraints(updateLeagueButton, c);
        gb.setConstraints(showLeaguesButton, c);
        gb.setConstraints(deleteNetworkButton, c);

        contentPane.add(insertFundButton);
        contentPane.add(updateFundButton);
        contentPane.add(deleteFundButton);
        contentPane.add(updateLeagueButton);
        contentPane.add(showLeaguesButton);
        contentPane.add(showFundsButton);
        contentPane.add(showAllGamesButtonMinScore);
        contentPane.add(projectionButton);
        contentPane.add(showNetworkNamesButton);
        contentPane.add(filterLeaguesButton);
        contentPane.add(playersPlayedAllSportsButton);
        contentPane.add(showNumTeamsInCityButton);
        contentPane.add(showMultipleBroadcastsButton);
        contentPane.add(showHighestTierRevenueButton);
        contentPane.add(deleteNetworkButton);

        // register login button with action event handler
        playersPlayedAllSportsButton.addActionListener(new PlayersPlayedAllSportsButtonListener());
        updateFundButton.addActionListener(new UpdateFundButtonListener());
        showAllGamesButtonMinScore.addActionListener(new ShowAllGamesButtonListener());
        filterLeaguesButton.addActionListener(new FilterLeaguesButtonListener());
        projectionButton.addActionListener(new MakeProjectionListener());
        showFundsButton.addActionListener(new ShowFundsListener());
        showNetworkNamesButton.addActionListener(new GetNetworksListener());
        showNumTeamsInCityButton.addActionListener(new GetNumTeamsInCityListener());
        showMultipleBroadcastsButton.addActionListener(new GetMultipleBroadcastsListener());
        showHighestTierRevenueButton.addActionListener(new GetHighestTierRevenueListener());
        insertFundButton.addActionListener(new InsertFundButtonListener());
        deleteFundButton.addActionListener(new DeleteFundButtonListener());
        updateLeagueButton.addActionListener(new UpdateLeagueButtonListener());
        showLeaguesButton.addActionListener(new ShowLeaguesButtonListener());
        deleteNetworkButton.addActionListener(new DeleteNetworkButtonListener());

        // anonymous inner class for closing the window
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                delegate.terminalTransactionsFinishedUI();
//                System.exit(0);
            }
        });

        // size the window to obtain a best fit for the components
        this.pack();

        // center the frame
        Dimension d = this.getToolkit().getScreenSize();
        Rectangle r = this.getBounds();
        this.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );

        // make the window visible
        this.setVisible(true);
    }

    /**
     * ActionListener Methods
     */
    @Override
    public void actionPerformed(ActionEvent e) {
//        delegate.login(usernameField.getText(), String.valueOf(passwordField.getPassword()));
        delegate.showFundsUI();


    }

    public void removeTable() {
        Component[] comps = this.getContentPane().getComponents();
        for (Component c : comps) {
            if (c.getName() != null && c.getName().equals(UITransactions.tableName)) {
                this.remove(c);
            }
        }
    }


    private class MakeProjectionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            UITransactions.this.removeTable();
            delegate.projectionUI();
        }
    }


    private class ShowFundsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            UITransactions.this.removeTable();
            delegate.showFundsUI();
        }
    }

    private class FilterLeaguesButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            UITransactions.this.removeTable();
            delegate.filterLeaguesUI();
        }
    }

    private class GetNetworksListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            UITransactions.this.removeTable();
            delegate.getNetworkNamesUI();
        }
    }
            
    private class ShowAllGamesButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            UITransactions.this.removeTable();
            delegate.getAllGamesMinScoreUI();
        }
    }

    private class GetNumTeamsInCityListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            UITransactions.this.removeTable();
            delegate.getNumTeamsInCityUI();
        }
    }


    private class GetMultipleBroadcastsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            UITransactions.this.removeTable();
            delegate.getMultipleBroadcastsUI();
        }
    }

    private class GetHighestTierRevenueListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            UITransactions.this.removeTable();
            delegate.getHighestTierRevenueUI();
        }
    }

    private class UpdateFundButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            UITransactions.this.removeTable();
            delegate.updateFundUI();
        }
    }

    private class PlayersPlayedAllSportsButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            UITransactions.this.removeTable();
            delegate.getPlayersPlayedAllSportsUI();
        }
    }

    private class InsertFundButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            UITransactions.this.removeTable();
            delegate.insertFundUI();
        }
    }

    private class DeleteFundButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            UITransactions.this.removeTable();
            delegate.deleteFundUI();
        }
    }

    private class UpdateLeagueButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            UITransactions.this.removeTable();
            delegate.updateLeagueUI();
        }
    }

    private class ShowLeaguesButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            UITransactions.this.removeTable();
            delegate.showLeaguesUI();
        }
    }

    private class DeleteNetworkButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            UITransactions.this.removeTable();
            delegate.deleteNetworkUI();
        }
    }
}

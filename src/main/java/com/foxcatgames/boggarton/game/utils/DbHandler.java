package com.foxcatgames.boggarton.game.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.sqlite.JDBC;

import com.foxcatgames.boggarton.GameParams;
import com.foxcatgames.boggarton.players.IPlayer;

public class DbHandler {

    private static final String GAMES = "CREATE TABLE IF NOT EXISTS Games (ga_id INTEGER PRIMARY KEY AUTOINCREMENT, ga_time DATETIME)";
    private static final String LOSERS = "CREATE TABLE IF NOT EXISTS Losers (lo_game INTEGER, lo_debth INTEGER, lo_set INTEGER, lo_size INTEGER, lo_score INTEGER, lo_count INTEGER, lo_player TEXT, lo_yuck TEXT, lo_random TEXT, lo_price TEXT, lo_virtual INTEGER, FOREIGN KEY(lo_game) REFERENCES artist(ga_id))";
    private static final String WINNERS = "CREATE TABLE IF NOT EXISTS Winners (wi_game INTEGER, wi_debth INTEGER, wi_set INTEGER, wi_size INTEGER, wi_score INTEGER, wi_count INTEGER, wi_player TEXT, wi_yuck TEXT, wi_random TEXT, wi_price TEXT, wi_virtual INTEGER, FOREIGN KEY(wi_game) REFERENCES artist(ga_id))";

    private static final String GAME = "INSERT INTO Games (ga_time) values(?)";
    private static final String LOSER = "INSERT INTO Losers  (lo_game, lo_debth, lo_set, lo_size, lo_score, lo_count, lo_player, lo_yuck, lo_random, lo_price, lo_virtual) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String WINNER = "INSERT INTO Winners (wi_game, wi_debth, wi_set, wi_size, wi_score, wi_count, wi_player, wi_yuck, wi_random, wi_price, wi_virtual) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String CONN_STR = "jdbc:sqlite:boggarton.db";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static DbHandler instance = null;
    private final Connection conn;

    private static PreparedStatement loserStmt;
    private static PreparedStatement winnerStmt;
    private static PreparedStatement gameStmt;

    public static synchronized DbHandler getInstance() {
        try {
            if (instance == null)
                instance = new DbHandler();

            initDb(instance);

            gameStmt = instance.conn.prepareStatement(GAME);
            loserStmt = instance.conn.prepareStatement(LOSER);
            winnerStmt = instance.conn.prepareStatement(WINNER);

            return instance;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private DbHandler() throws SQLException {
        DriverManager.registerDriver(new JDBC());
        conn = DriverManager.getConnection(CONN_STR);
        conn.setAutoCommit(false);
    }

    private void executePreraredStatemnt(final PreparedStatement st, final Integer id, final GameParams params) throws SQLException {
        st.setInt(1, id);

        st.setInt(2, params.getPrognosisDebth());
        st.setInt(3, params.getSetSize());
        st.setInt(4, params.getFigureSize());
        st.setInt(5, params.getScore());
        st.setInt(6, params.getCount());

        st.setString(7, params.getPlayerName());
        st.setString(8, params.getYuckName());
        st.setString(9, params.getRandomName());
        st.setString(10, params.getPriceName());

        st.setInt(11, params.isVirtual() ? 1 : 0);

        st.executeUpdate();
    }

    public void saveGameOutcome(final IPlayer winner, final IPlayer loser) {
        try {
            final Statement stmt = conn.createStatement();
            final String date = DATE_FORMAT.format(new Date());

            gameStmt.setString(1, date);
            gameStmt.executeUpdate();

            final ResultSet rs = stmt.executeQuery("select max(ga_id) as id from games");
            final Integer gameId = rs.getInt("id");

            executePreraredStatemnt(loserStmt, gameId, loser.getGamesParams());
            if (winner != null)
                executePreraredStatemnt(winnerStmt, gameId, winner.getGamesParams());

            rs.close();
            stmt.close();
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
        }
    }

    public void saveGameOutcome(final IPlayer loser) throws SQLException {
        saveGameOutcome(null, loser);
    }

    private static void initDb(final DbHandler dbHandler) throws SQLException {
        final Statement st = dbHandler.conn.createStatement();
        st.executeUpdate(GAMES);
        st.executeUpdate(LOSERS);
        st.executeUpdate(WINNERS);
        st.close();
    }

    public static void saveOutcome(IPlayer player) {
        try {
            DbHandler.getInstance().saveGameOutcome(player);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeHandler() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close() {
        DbHandler.getInstance().closeHandler();
    }
}

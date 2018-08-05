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

import com.foxcatgames.boggarton.players.SurrogatePlayerParams;

public class DbHandler {

    private static final String GAMES = "CREATE TABLE IF NOT EXISTS Games (ga_id INTEGER PRIMARY KEY AUTOINCREMENT, ga_time DATETIME)";
    private static final String LOSERS = "CREATE TABLE IF NOT EXISTS Losers (lo_game INTEGER, lo_debth INTEGER, lo_set INTEGER, lo_size INTEGER, lo_score INTEGER, lo_count INTEGER, lo_player TEXT, lo_yuck TEXT, lo_random TEXT, lo_price TEXT, FOREIGN KEY(lo_game) REFERENCES artist(ga_id))";
    private static final String WINNERS = "CREATE TABLE IF NOT EXISTS Winners (wi_game INTEGER, wi_debth INTEGER, wi_set INTEGER, wi_size INTEGER, wi_score INTEGER, wi_count INTEGER, wi_player TEXT, wi_yuck TEXT, wi_random TEXT, wi_price TEXT, FOREIGN KEY(wi_game) REFERENCES artist(ga_id))";

    private static final String GAME = "INSERT INTO Games (ga_time) values(?)";
    private static final String LOSER = "INSERT INTO Losers  (lo_game, lo_debth, lo_set, lo_size, lo_score, lo_count, lo_player, lo_yuck, lo_random, lo_price) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String WINNER = "INSERT INTO Winners (wi_game, wi_debth, wi_set, wi_size, wi_score, wi_count, wi_player, wi_yuck, wi_random, wi_price) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String CONN_STR = "jdbc:sqlite:boggarton.db";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static DbHandler instance = null;
    private final Connection conn;

    private static PreparedStatement loserStmt;
    private static PreparedStatement winnerStmt;
    private static PreparedStatement gameStmt;

    public static synchronized DbHandler getInstance() throws SQLException {
        if (instance == null)
            instance = new DbHandler();

        initDb(instance);

        gameStmt = instance.conn.prepareStatement(GAME);
        loserStmt = instance.conn.prepareStatement(LOSER);
        winnerStmt = instance.conn.prepareStatement(WINNER);

        return instance;
    }

    private DbHandler() throws SQLException {
        DriverManager.registerDriver(new JDBC());
        conn = DriverManager.getConnection(CONN_STR);
    }

    private void executePreraredStatemnt(final PreparedStatement st, final Integer id, final SurrogatePlayerParams params) throws SQLException {
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

        st.executeUpdate();
    }

    public void saveGameOutcome(final SurrogatePlayerParams winner, final SurrogatePlayerParams loser) throws SQLException {
        final Statement stmt = conn.createStatement();
        try {
            stmt.executeUpdate("BEGIN");

            final String date = DATE_FORMAT.format(new Date());
            gameStmt.setString(1, date);
            gameStmt.executeUpdate();

            final ResultSet rs = stmt.executeQuery("select max(ga_id) as id from games");
            final Integer gameId = rs.getInt("id");

            executePreraredStatemnt(loserStmt, gameId, loser);
            if (winner != null)
                executePreraredStatemnt(winnerStmt, gameId, winner);

            rs.close();
            stmt.close();
            conn.createStatement().executeUpdate("COMMIT");
        } catch (SQLException e) {
            conn.createStatement().executeUpdate("ROLLBACK");
        }
    }

    private static void initDb(final DbHandler dbHandler) throws SQLException {
        final Statement st = dbHandler.conn.createStatement();
        st.executeUpdate(GAMES);
        st.executeUpdate(LOSERS);
        st.executeUpdate(WINNERS);
        st.close();
    }

    public void closeHandler() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

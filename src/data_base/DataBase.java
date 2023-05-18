package data_base;

import java.sql.*;

public class DataBase {
    private final String gameName;
    private final String playerName;
    private final int level;
    private final int strength;
    private final int dexterity;
    private final int attackPlayer;
    private final int defence;
    private final int exp;
    private final int coin;
    private final int life;

    public DataBase(String gameName, String playerName, int life, int level, int strength, int dexterity, int attackPlayer, int defence, int exp, int coin) {
        this.gameName = gameName;
        this.playerName = playerName;
        this.level = level;
        this.strength = strength;
        this.dexterity = dexterity;
        this.attackPlayer = attackPlayer;
        this.defence = defence;
        this.exp = exp;
        this.coin = coin;
        this.life = life;
    }

    public void loadAndStoreData() {
        Connection connection = null;
        try {
            // Load the JDBC driver for SQLite
            Class.forName("org.sqlite.JDBC");

            // Connect to the SQLite database
            String url = "jdbc:sqlite:/Game/game.db";
            connection = DriverManager.getConnection(url);

            connection.setAutoCommit(false);

            // Prepare the SQL query to insert the game data
            String sql = "INSERT INTO game_data (game_name, player_name, life, level, strength, dexterity, 'attack', defence, exp, coin) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, gameName);
            statement.setString(2, playerName);
            statement.setInt(3, life);
            statement.setInt(4, level);
            statement.setInt(5, strength);
            statement.setInt(6, dexterity);
            statement.setInt(7, attackPlayer);
            statement.setInt(8, defence);
            statement.setInt(9, exp);
            statement.setInt(10, coin);

            statement.executeUpdate();

            connection.commit();

            statement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            try{
                if(connection != null){
                    connection.rollback();
                }
            } catch (SQLException ex){
                System.out.println("Error rolling back changes: " + ex.getMessage());
            }
        }
    }
}

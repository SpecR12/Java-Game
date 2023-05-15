package data_base;

import java.sql.*;

public class DataBase {
    private Connection conn;

    public DataBase(){
        try{
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:game.db");
            Statement state = conn.createStatement();
            state.execute("CREATE TABLE IF NOT EXISTS game_data ("
                    + "player_hp INTEGER, player_attack INTEGER,"
                    + "monster_hp INTEGER, monster_attack INTEGER"
                    + ")");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public int getPlayerHP(){
        int hp = 0;
        try{
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery("SELECT player_hp FROM game_data");
            if(rs.next()) {
                hp = rs.getInt("player_hp");
            }
            rs.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return hp;
    }
    public void setPlayerHP(int hp){
        try {
            PreparedStatement pstmt = conn.prepareStatement("UPDATE game_data SET player_hp = ?");
            pstmt.setInt(1, hp);
            pstmt.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public int getPlayerAttack(){
        int attack = 0;
        try {
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery("SELECT player_attack FROM game_data");
            if(rs.next()) {
                attack = rs.getInt("player_attack");
            }
            rs.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return attack;
    }
    public void setPlayerAttack(int attack) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("UPDATE game_data SET player_attack = ?");
            pstmt.setInt(1, attack);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public int getMonsterHP() {
        int hp = 0;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT monster_hp FROM game_data");
            hp = rs.getInt("monster_hp");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hp;
    }
    public void setMonsterHP(int hp) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("UPDATE game_data SET monster_hp = ?");
            pstmt.setInt(1, hp);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public int getMonsterAttack() {
        int attack = 0;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT monster_attack FROM game_data");
            attack = rs.getInt("monster_attack");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return attack;
    }
    public void setMonsterAttack(int attack) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("UPDATE game_data SET monster_attack = ?");
            pstmt.setInt(1, attack);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void closeConnection() {
        try {
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
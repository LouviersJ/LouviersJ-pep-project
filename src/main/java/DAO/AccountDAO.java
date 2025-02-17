package DAO;
import Model.Account;
import Model.Message;
import Util.ConnectionUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    public Account addAccount(Account account){
        if(account.getPassword().length() < 4)
            return null;

        try(Connection connection = ConnectionUtil.getConnection()) {
            String sql = "INSERT INTO account(username, password) VALUES(?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            
            preparedStatement.executeUpdate();

            return account;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account checkAccount(Account account){
        try(Connection connection = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            
            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next())
                return account;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> getAccountMessages(int account_id){
        List<Message> messages = new ArrayList<>();

        try(Connection connection = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM message WHERE posted_by = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, account_id);

            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                                            rs.getInt("posted_by"), 
                                            rs.getString("message_text"), 
                                            rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

}

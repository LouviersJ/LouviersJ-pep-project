package DAO;
import Model.Message;
import Util.ConnectionUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    public Message addMessage(Message message){
        if(message.getMessage_text().strip()=="" | message.getMessage_text().length()> 255)
            return null;

        try(Connection connection = ConnectionUtil.getConnection()) {
            String sql = "INSERT INTO message(posted_by, message_text, time_posted_epoch) VALUES(?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());
            
            preparedStatement.executeUpdate();

            return message;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> getAllMessages(){
        List<Message> messages = new ArrayList<>();

        try(Connection connection = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM message;";
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()){
                Message message = new Message(
                                        rs.getInt("message_id"),
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

    public Message getMessage(int message_id){
        try(Connection connection = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM message WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, message_id);
            
            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next())
                return new Message(
                            rs.getInt("message_id"),
                            rs.getInt("posted_by"), 
                            rs.getString("message_text"), 
                            rs.getLong("time_posted_epoch"));
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message deleteMessage(int message_id){
        try(Connection connection = ConnectionUtil.getConnection()) {
            String sql_ = "SELECT * FROM message WHERE message_id = ?";
            String sql = "DELETE FROM message WHERE message_id = ?;";

            PreparedStatement deletedMessage = connection.prepareStatement(sql_);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            
            deletedMessage.setInt(1, message_id);
            preparedStatement.setInt(1, message_id);
            
            ResultSet rs = deletedMessage.executeQuery();
            preparedStatement.executeUpdate();

            if(rs.next())
                return new Message(
                            rs.getInt("message_id"),
                            rs.getInt("posted_by"), 
                            rs.getString("message_text"), 
                            rs.getLong("time_posted_epoch"));
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message updateMessage(String message_text, int message_id){
        if(message_text.strip()=="" | message_text.length()> 255)
            return null;

        try(Connection connection = ConnectionUtil.getConnection()) {
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?;";
            String sql_ = "SELECT * FROM message WHERE message_id = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            PreparedStatement updatedMessage = connection.prepareStatement(sql_);

            preparedStatement.setString(1, message_text);
            preparedStatement.setInt(2, message_id);
            updatedMessage.setInt(1, message_id);
            
            preparedStatement.executeUpdate();
            ResultSet rs = updatedMessage.executeQuery();

            if(rs.next())
                return new Message(
                            rs.getInt("message_id"),
                            rs.getInt("posted_by"), 
                            rs.getString("message_text"), 
                            rs.getLong("time_posted_epoch"));
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}

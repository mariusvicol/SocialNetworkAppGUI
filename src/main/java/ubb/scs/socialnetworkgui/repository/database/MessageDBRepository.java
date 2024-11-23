package ubb.scs.socialnetworkgui.repository.database;

import ubb.scs.socialnetworkgui.domain.Message;
import ubb.scs.socialnetworkgui.repository.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MessageDBRepository implements Repository<Integer, Message> {
    private final String url;
    private final String usernameDB;
    private final String password;
    private Connection connection;

    public MessageDBRepository(String url, String usernameDB, String password){
        this.url = url;
        this.usernameDB = usernameDB;
        this.password = password;
        try{
            connection = DriverManager.getConnection(this.url, this.usernameDB, this.password);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Message> findOne(Integer integer) {
        try(PreparedStatement statement = connection.prepareStatement("SELECT * from messages WHERE id = ?")) {
            statement.setInt(1, integer);
            try(var resultSet = statement.executeQuery()) {
                if(resultSet.next()) {
                    String sender = resultSet.getString("sender");
                    String receiver = resultSet.getString("receiver");
                    String content = resultSet.getString("content");
                    java.sql.Timestamp timestamp = resultSet.getTimestamp("data");
                    Message message = new Message(sender, receiver, content, timestamp.toLocalDateTime());
                    message.setId(resultSet.getInt("id"));
                    return Optional.of(message);
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Message> findAll() {
        List<Message> messages = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement("SELECT * from messages");
            var resultSet = statement.executeQuery()) {
            while(resultSet.next()) {
                String sender = resultSet.getString("sender");
                String receiver = resultSet.getString("receiver");
                String content = resultSet.getString("message");
                java.sql.Timestamp timestamp = resultSet.getTimestamp("data");
                Message message = new Message(sender, receiver, content, timestamp.toLocalDateTime());
                message.setId(resultSet.getInt("id"));
                messages.add(message);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    @Override
    public Optional<Message> save(Message entity) {
        int rez = 0;
        try(PreparedStatement statement = connection.prepareStatement("INSERT INTO messages (sender, receiver, message, data) VALUES (?, ?, ?, ?)")) {
            statement.setString(1, entity.getSender());
            statement.setString(2, entity.getReceiver());
            statement.setString(3, entity.getContent());
            statement.setTimestamp(4, java.sql.Timestamp.valueOf(entity.getData()));
            rez = statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return rez == 1 ? Optional.empty() : Optional.of(entity);
    }

    @Override
    public Optional<Message> delete(Integer integer) {
        int rez = 0;
        Message message;
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM messages WHERE id = ?")) {
            statement.setInt(1, integer);
            rez = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rez == 1 ? Optional.of(new Message("","", "", LocalDateTime.now())) : Optional.empty();
    }

    @Override
    public Optional<Message> update(Message entity) {
        /// TODO;
        return Optional.empty();
    }
}

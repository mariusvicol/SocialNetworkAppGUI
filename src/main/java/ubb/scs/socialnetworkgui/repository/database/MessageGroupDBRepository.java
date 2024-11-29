package ubb.scs.socialnetworkgui.repository.database;

import ubb.scs.socialnetworkgui.domain.MessageGroup;
import ubb.scs.socialnetworkgui.repository.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MessageGroupDBRepository implements Repository<Integer, MessageGroup> {
    private final String url;
    private final String username;
    private final String password;

    public MessageGroupDBRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Optional<MessageGroup> findOne(Integer id) {
        String query = "SELECT * FROM message_group WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(mapMessageGroup(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Iterable<MessageGroup> findAll() {
        List<MessageGroup> messages = new ArrayList<>();
        String query = "SELECT * FROM message_group";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                messages.add(mapMessageGroup(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    @Override
    public Optional<MessageGroup> save(MessageGroup entity) {
        String queryMessage = "INSERT INTO message_group (sender, message, reply_id, date) VALUES (?, ?, ?, ?) RETURNING id";
        String queryReceiver = "INSERT INTO message_group_receivers (message_id, receiver) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement messageStatement = connection.prepareStatement(queryMessage);
             PreparedStatement receiverStatement = connection.prepareStatement(queryReceiver)) {

            messageStatement.setString(1, entity.getSender());
            messageStatement.setString(2, entity.getMessage());
            messageStatement.setObject(3, entity.getReply() == null ? null : entity.getReply().getId());
            messageStatement.setTimestamp(4, Timestamp.valueOf(entity.getDate()));

            ResultSet resultSet = messageStatement.executeQuery();
            if (resultSet.next()) {
                int messageId = resultSet.getInt(1);
                for (String receiver : entity.getReceivers()) {
                    receiverStatement.setInt(1, messageId);
                    receiverStatement.setString(2, receiver);
                    receiverStatement.addBatch();
                }
                receiverStatement.executeBatch();
                return Optional.empty();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.of(entity);
    }

    @Override
    public Optional<MessageGroup> delete(Integer id) {
        Optional<MessageGroup> messageGroup = findOne(id);
        if (messageGroup.isEmpty()) {
            return Optional.empty();
        }

        String query = "DELETE FROM message_group WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messageGroup;
    }

    @Override
    public Optional<MessageGroup> update(MessageGroup entity) {
        String queryMessage = "UPDATE message_group SET sender = ?, message = ?, reply_id = ?, date = ? WHERE id = ?";
        String deleteReceivers = "DELETE FROM message_group_receivers WHERE message_id = ?";
        String queryReceiver = "INSERT INTO message_group_receivers (message_id, receiver) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement updateStatement = connection.prepareStatement(queryMessage);
             PreparedStatement deleteReceiverStatement = connection.prepareStatement(deleteReceivers);
             PreparedStatement receiverStatement = connection.prepareStatement(queryReceiver)) {

            updateStatement.setString(1, entity.getSender());
            updateStatement.setString(2, entity.getMessage());
            updateStatement.setObject(3, entity.getReply() == null ? null : entity.getReply().getId());
            updateStatement.setTimestamp(4, Timestamp.valueOf(entity.getDate()));
            updateStatement.setInt(5, entity.getId());
            updateStatement.executeUpdate();

            deleteReceiverStatement.setInt(1, entity.getId());
            deleteReceiverStatement.executeUpdate();

            for (String receiver : entity.getReceivers()) {
                receiverStatement.setInt(1, entity.getId());
                receiverStatement.setString(2, receiver);
                receiverStatement.addBatch();
            }
            receiverStatement.executeBatch();
            return Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.of(entity);
    }

    private MessageGroup mapMessageGroup(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String sender = resultSet.getString("sender");
        String message = resultSet.getString("message");
        int replyId = resultSet.getInt("reply_id");
        LocalDateTime date = resultSet.getTimestamp("date").toLocalDateTime();

        List<String> receivers = new ArrayList<>();
        String receiverQuery = "SELECT receiver FROM message_group_receivers WHERE message_id = ?";
        try (PreparedStatement statement = resultSet.getStatement().getConnection().prepareStatement(receiverQuery)) {
            statement.setInt(1, id);
            ResultSet receiverResultSet = statement.executeQuery();
            while (receiverResultSet.next()) {
                receivers.add(receiverResultSet.getString("receiver"));
            }
        }

        MessageGroup messageGroup = new MessageGroup(sender, receivers, message, date);
        messageGroup.setId(id);
        return messageGroup;
    }

}


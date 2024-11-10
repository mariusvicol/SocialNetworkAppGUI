package ubb.scs.socialnetworkgui.repository.database;

import ubb.scs.socialnetworkgui.domain.FriendRequest;
import ubb.scs.socialnetworkgui.domain.Tuple;
import ubb.scs.socialnetworkgui.repository.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class FriendRequestsDBRepository implements Repository<Tuple<String,String>, FriendRequest> {
    private final String url;
    private final String username;
    private final String password;
    private Connection connection;

    public FriendRequestsDBRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
        try{
            this.connection = DriverManager.getConnection(this.url, this.username, this.password);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public Optional<FriendRequest> findOne(Tuple<String, String> stringStringTuple) {
        FriendRequest friendRequest = null;
        try(PreparedStatement statement = connection.prepareStatement("SELECT * from friendrequests WHERE sender = ? AND receiver = ?")) {
            statement.setString(1, stringStringTuple.getFirst());
            statement.setString(2, stringStringTuple.getSecond());
            try(var resultSet = statement.executeQuery()) {
                if(resultSet.next()) {
                    String from = resultSet.getString("sender");
                    String to = resultSet.getString("receiver");
                    LocalDateTime dateTime = resultSet.getTimestamp("date").toLocalDateTime();
                    friendRequest = new FriendRequest(from, to, dateTime);
                    friendRequest.setId(new Tuple<>(from, to));
                    return Optional.of(friendRequest);
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(friendRequest);
    }

    @Override
    public Iterable<FriendRequest> findAll() {
        Set<FriendRequest> friendrequests = new HashSet<>();
        try(PreparedStatement statement = connection.prepareStatement("SELECT * from friendrequests");
            var resultSet = statement.executeQuery()) {
            while(resultSet.next()) {
                String from = resultSet.getString("sender");
                String to = resultSet.getString("receiver");
                LocalDateTime dateTime = resultSet.getTimestamp("date").toLocalDateTime();
                FriendRequest friendRequest = new FriendRequest(from, to, dateTime);
                friendRequest.setId(new Tuple<>(from, to));
                friendrequests.add(friendRequest);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return friendrequests;
    }

    @Override
    public Optional<FriendRequest> save(FriendRequest entity) {
        int rez = -1;
        try(PreparedStatement statement = connection.prepareStatement("INSERT INTO friendrequests (sender, receiver, date) VALUES (?, ?, ?)")) {
            statement.setString(1, entity.getFrom());
            statement.setString(2, entity.getTo());
            statement.setTimestamp(3, java.sql.Timestamp.valueOf(entity.getDateTime()));
            rez = statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return rez == 1 ? Optional.empty() : Optional.of(entity);
    }

    @Override
    public Optional<FriendRequest> delete(Tuple<String, String> stringStringTuple) {
        int rez = -1;
        try(PreparedStatement statement = connection.prepareStatement("DELETE FROM friendrequests WHERE sender = ? AND receiver = ?")) {
            statement.setString(1, stringStringTuple.getFirst());
            statement.setString(2, stringStringTuple.getSecond());
            rez = statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return rez == 1 ? Optional.of(new FriendRequest(stringStringTuple.getFirst(), stringStringTuple.getSecond(), LocalDateTime.now())) : Optional.empty();
    }

    @Override
    public Optional<FriendRequest> update(FriendRequest entity) {
        int rez = -1;
        try(PreparedStatement statement = connection.prepareStatement("UPDATE friendrequests SET sender = ?, receiver = ?, date = ? WHERE sender = ? AND receiver = ? AND date = ?")) {
            statement.setString(1, entity.getFrom());
            statement.setString(2, entity.getTo());
            statement.setTimestamp(3, java.sql.Timestamp.valueOf(entity.getDateTime()));
            statement.setString(4, entity.getFrom());
            statement.setString(5, entity.getTo());
            statement.setTimestamp(6, java.sql.Timestamp.valueOf(entity.getDateTime()));
            rez = statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return rez == 1 ? Optional.empty() : Optional.of(entity);
    }
}

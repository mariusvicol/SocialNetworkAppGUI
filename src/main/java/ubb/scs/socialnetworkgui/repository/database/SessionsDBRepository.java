package ubb.scs.socialnetworkgui.repository.database;

import ubb.scs.socialnetworkgui.domain.Sessions;
import ubb.scs.socialnetworkgui.repository.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class SessionsDBRepository implements Repository<String, Sessions> {
    private final String url;
    private final String username;
    private final String password;
    private Connection connection;

    public SessionsDBRepository(String url, String username, String password) {
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
    public Optional<Sessions> findOne(String s) {
        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM sessions WHERE username = ?")){
            statement.setString(1, s);
            var result = statement.executeQuery();
            if(result.next()){
                return Optional.of(new Sessions(result.getString("username"), result.getTimestamp("data").toLocalDateTime()));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Sessions> findAll() {
        List<Sessions> sessions = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM sessions");
            var resultSet = statement.executeQuery()) {
            while(resultSet.next()) {
                String username = resultSet.getString("username");
                sessions.add(new Sessions(username, resultSet.getTimestamp("data").toLocalDateTime()));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return sessions;
    }

    @Override
    public Optional<Sessions> save(Sessions entity) {
        int rez = 0;
        try(PreparedStatement statement = connection.prepareStatement("INSERT INTO sessions(username, data) VALUES (?, ?)")){
            statement.setString(1, entity.getUsername());
            statement.setTimestamp(2, java.sql.Timestamp.valueOf(entity.getDataConnection()));
            rez = statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return rez == 1 ? Optional.empty() : Optional.of(entity);
    }

    @Override
    public Optional<Sessions> delete(String s) {
        int rez = 0;
        try(PreparedStatement statement = connection.prepareStatement("DELETE FROM sessions WHERE username = ?")){
            statement.setString(1, s);
            rez = statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return rez == 1 ? Optional.of(new Sessions(s, LocalDateTime.now())): Optional.empty();
    }

    @Override
    public Optional<Sessions> update(Sessions entity) {
        int rez = 0;
        try(PreparedStatement statement = connection.prepareStatement("UPDATE sessions SET data = ? WHERE username = ?")){
            statement.setTimestamp(1, java.sql.Timestamp.valueOf(entity.getDataConnection()));
            statement.setString(2, entity.getUsername());
            rez = statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return rez == 1 ? Optional.empty() : Optional.of(entity);
    }
}

package ubb.scs.socialnetworkgui.repository.database;

import ubb.scs.socialnetworkgui.domain.UserInfo;
import ubb.scs.socialnetworkgui.repository.Repository;

import java.sql.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class UsersDBInfo implements Repository<String, UserInfo> {
    private final String url;
    private final String username;
    private final String password;

    public UsersDBInfo(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }
    @Override
    public Optional<UserInfo> findOne(String usernameSearch) {
        UserInfo userInfo = null;
        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("SELECT * from infos WHERE username = ?")) {
            statement.setString(1, usernameSearch);
            try(ResultSet resultSet = statement.executeQuery()) {
                if(resultSet.next()) {
                    String nume = resultSet.getString("last_name");
                    String prenume = resultSet.getString("first_name");
                    String password = resultSet.getString("password");
                    userInfo = new UserInfo(nume, prenume, password);
                    userInfo.setId(usernameSearch);
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(userInfo);
    }

    @Override
    public Iterable<UserInfo> findAll() {
        Set<UserInfo> userInfos = new HashSet<>();
        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("SELECT * from infos");
            ResultSet resultSet = statement.executeQuery()) {
            while(resultSet.next()) {
                String username = resultSet.getString("username");
                String nume = resultSet.getString("last_name");
                String prenume = resultSet.getString("first_name");

                UserInfo userInfo = new UserInfo(nume, prenume, "");
                userInfo.setId(username);
                userInfos.add(userInfo);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return userInfos;
    }

    @Override
    public Optional<UserInfo> save(UserInfo entity) {
        int rez = 0;
        Optional<UserInfo> userInfo = findOne(entity.getId());
        if (userInfo.isPresent()) {
            return Optional.of(entity);
        }
        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("INSERT INTO infos (username, first_name, last_name, password) VALUES (?, ?, ?, ?)")) {
            statement.setString(1, entity.getId());
            statement.setString(2, entity.getFirstName());
            statement.setString(3, entity.getLastName());
            statement.setString(4, entity.getPassword());
            rez = statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return rez == 1 ? Optional.empty() : Optional.of(entity);
    }

    @Override
    public Optional<UserInfo> delete(String username) {
        int rez = 0;
        Optional<UserInfo> userInfo = findOne(username);
        if (userInfo.isEmpty()) {
            return Optional.empty();
        }
        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("DELETE FROM infos WHERE username = ?")) {
            statement.setString(1, username);
            rez = statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return rez == 1 ? userInfo : Optional.empty();
    }

    @Override
    public Optional<UserInfo> update(UserInfo entity) {
        int rez = 0;
        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("UPDATE infos SET first_name = ?, last_name = ?, password = ? WHERE username = ?")) {
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setString(3, entity.getPassword());
            statement.setString(4, entity.getId());
            rez = statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return rez == 1 ? Optional.empty() : Optional.of(entity);
    }
}

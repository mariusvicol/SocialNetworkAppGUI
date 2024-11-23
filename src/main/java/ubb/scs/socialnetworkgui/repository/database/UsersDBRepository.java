package ubb.scs.socialnetworkgui.repository.database;

import ubb.scs.socialnetworkgui.domain.User;
import ubb.scs.socialnetworkgui.domain.validators.Validator;
import ubb.scs.socialnetworkgui.repository.Repository;

import java.sql.*;
import java.util.*;

public class UsersDBRepository implements Repository<Long, User> {
    private final String url;
    private final String usernameTabel;
    private final String password;
    private final Validator<User> validator;
    private Connection connection;


    public UsersDBRepository(String url, String username, String password, Validator<User> validator) {
        this.url = url;
        this.usernameTabel = username;
        this.password = password;
        this.validator = validator;
        try{
            this.connection = DriverManager.getConnection(this.url, this.usernameTabel, this.password);
        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public Optional<User> findOne(Long id) {
        User user = null;
        try(PreparedStatement statement = connection.prepareStatement("SELECT * from users WHERE id = ?")) {
            statement.setLong(1, id);
            try(ResultSet resultSet = statement.executeQuery()) {
                if(resultSet.next()) {
                    Long idUser = resultSet.getLong("id");
                    String nume = resultSet.getString("nume");
                    String prenume = resultSet.getString("prenume");
                    String usernameUser = resultSet.getString("username");
                    user = new User(nume, prenume);
                    user.setUsername(usernameUser);
                    user.setId(idUser);
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return Optional.ofNullable(user);
    }

    @Override
    public Iterable<User> findAll() {
        List<User> users = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * from users");
             ResultSet resultSet = statement.executeQuery()) {
            while(resultSet.next()) {
                Long id = resultSet.getLong("id");
                String nume = resultSet.getString("nume");
                String prenume = resultSet.getString("prenume");
                String usernameUser = resultSet.getString("username");

                User user = new User(nume, prenume);
                user.setId(id);
                user.setUsername(usernameUser);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }


    @Override
    public Optional<User> save(User entity) {
        int rez = -1;
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO users(nume, prenume,username) VALUES (?, ?, ?)")) {
            statement.setString(1, entity.getLastName());
            statement.setString(2, entity.getFirstName());
            statement.setString(3, entity.getUsername());
            rez = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rez == 1 ? Optional.empty() : Optional.of(entity);
    }

    @Override
    public Optional<User> delete(Long id) {
        int rez = -1;
        Optional<User> user = findOne(id);
        if (user.isEmpty()) {
            return Optional.empty();
        }
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id = ?")) {
            statement.setLong(1, id);
            rez = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rez == 1 ? user : Optional.empty();
    }

    @Override
    public Optional<User> update(User entity) {
        int rez = -1;
        validator.validate(entity);
        try (PreparedStatement statement = connection.prepareStatement("UPDATE users SET nume = ?, prenume = ?, username = ? WHERE id = ?")) {
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setString(3, entity.getUsername());
            statement.setLong(4, entity.getId());
            rez = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rez == 1 ? Optional.empty() : Optional.of(entity);
    }
}

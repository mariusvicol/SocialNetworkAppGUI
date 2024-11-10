package ubb.scs.socialnetworkgui.repository.database;

import ubb.scs.socialnetworkgui.domain.Friendship;
import ubb.scs.socialnetworkgui.domain.Tuple;
import ubb.scs.socialnetworkgui.domain.validators.Validator;
import ubb.scs.socialnetworkgui.repository.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class FriendshipsDBRepository implements Repository<Tuple<Long, Long>, Friendship> {
    private final String url;
    private final String username;
    private final String password;
    private final Validator<Friendship> validator;
    private Connection connection;

    public FriendshipsDBRepository(String url, String username, String password, Validator<Friendship> validator){
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
        try{
            connection = DriverManager.getConnection(this.url, this.username, this.password);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    @Override
    public Optional<Friendship> findOne(Tuple<Long, Long> id) {
        Friendship friendship = null;
        try(PreparedStatement statement = connection.prepareStatement("SELECT * from friendships WHERE id_user1 = ? AND id_user2 = ?")) {
            statement.setLong(1, id.getFirst());
            statement.setLong(2, id.getSecond());
            try(ResultSet resultSet = statement.executeQuery()) {
                if(resultSet.next()) {
                    Long idUser1 = resultSet.getLong("id_user1");
                    Long idUser2 = resultSet.getLong("id_user2");
                    LocalDateTime date = resultSet.getTimestamp("date").toLocalDateTime();
                    friendship = new Friendship(idUser1, idUser2, date);
                    Long idMin = Math.min(idUser1, idUser2);
                    Long idMax = Math.max(idUser1, idUser2);
                    friendship.setId(new Tuple<>(idMin, idMax));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(friendship);
    }

    @Override
    public Iterable<Friendship> findAll() {
        Set<Friendship> friendships = new HashSet<>();
        try(PreparedStatement statement = connection.prepareStatement("SELECT * from friendships");
        ResultSet resultSet = statement.executeQuery()) {
            while(resultSet.next()) {
                Long idUser1 = resultSet.getLong("id_user1");
                Long idUser2 = resultSet.getLong("id_user2");
                LocalDateTime date = resultSet.getTimestamp("date").toLocalDateTime();
                Friendship friendship = new Friendship(idUser1, idUser2, date);
                Long idMin = Math.min(idUser1, idUser2);
                Long idMax = Math.max(idUser1, idUser2);

                friendship.setId(new Tuple<>(idMin, idMax));
                friendships.add(friendship);
            }
            return friendships;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return friendships;
    }

    @Override
    public Optional<Friendship> save(Friendship entity) {
        int rez;
        validator.validate(entity);
        try(PreparedStatement statement = connection.prepareStatement("INSERT INTO friendships (id_user1, id_user2, date) VALUES (?, ?, ?)")) {
            statement.setLong(1, entity.getIdUser1());
            statement.setLong(2, entity.getIdUser2());
            statement.setTimestamp(3, java.sql.Timestamp.valueOf(entity.getDate()));
            rez = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rez == 1 ? Optional.empty() : Optional.of(entity);
    }

    @Override
    public Optional<Friendship> delete(Tuple<Long, Long> id) {
        int rez;
        try(PreparedStatement statement = connection.prepareStatement("DELETE FROM friendships WHERE id_user1 = ? AND id_user2 = ?")) {
            statement.setLong(1, id.getFirst());
            statement.setLong(2, id.getSecond());
            rez = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rez == 1 ? Optional.of(new Friendship(id.getFirst(), id.getSecond(),LocalDateTime.now())) : Optional.empty();
    }

    @Override
    public Optional<Friendship> update(Friendship entity) {
        validator.validate(entity);
        int rez;
        try(PreparedStatement statement = connection.prepareStatement("UPDATE friendships SET date = ? WHERE id_user1 = ? AND id_user2 = ?")) {
            statement.setTimestamp(1, java.sql.Timestamp.valueOf(entity.getDate()));
            statement.setLong(2, entity.getIdUser1());
            statement.setLong(3, entity.getIdUser2());
            rez = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rez == 1 ? Optional.empty() : Optional.of(entity);
    }
}

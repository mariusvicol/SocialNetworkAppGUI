package ubb.scs.socialnetworkgui.service;

import ubb.scs.socialnetworkgui.domain.Friendship;
import ubb.scs.socialnetworkgui.domain.Tuple;
import ubb.scs.socialnetworkgui.domain.User;
import ubb.scs.socialnetworkgui.domain.UserInfo;
import ubb.scs.socialnetworkgui.repository.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class UserInfoServiceGUI extends UsersAndFriendshipsService {

    private final Repository<String, UserInfo> userInfoRepository;

    public UserInfoServiceGUI(Repository<Long, User> userRepository, Repository<Tuple<Long, Long>, Friendship> friendshipRepository, Repository<String, UserInfo> userInfoRepository) {
        super(userRepository, friendshipRepository);
        this.userInfoRepository = userInfoRepository;
    }

    public void addUserInfo(String username, String firstName, String lastName, String password){
        User user = new User(firstName, lastName);
        user.setUsername(username);
        super.addUser(user);
        UserInfo userInfo = new UserInfo(firstName, lastName, password);
        userInfo.setId(username);
        userInfoRepository.save(userInfo);
    }

    @Override
    public void addFriendsForUser(User user){
        super.addFriendsForUser(user);
    }

    @Override
    public User getUser(Long userId) {
        return super.getUser(userId);
    }

    public UserInfo getUserInfo(String username){
        return userInfoRepository.findOne(username).orElse(null);
    }

    @Override
    public Iterable<User> getUsers() {
        return super.getUsers();
    }

    public Iterable<UserInfo> getUsersInfo(){
        return userInfoRepository.findAll();
    }

    public void removeUserInfo(String username){
        Long id = findIdUserByUsername(username);
        super.removeUser(id);
        userInfoRepository.delete(username);
    }

    public List<User> getFriends(String username){
        Long id = findIdUserByUsername(username);
        return super.getFriends(id);
    }

    @Override
    public Iterable<Friendship> getAllFriendships(){
        return super.getAllFriendships();
    }

    public void addFriendshipUsername(String username1, String username2){
        Long idUser1 = findIdUserByUsername(username1);
        Long idUser2 = findIdUserByUsername(username2);
        super.addFriendship(idUser1, idUser2);
    }

    public void removeFriendship(String username1, String username2){
        Long idUser1 = findIdUserByUsername(username1);
        Long idUser2 = findIdUserByUsername(username2);
        super.removeFriendship(idUser1, idUser2);
    }

    public UserInfo searchUsersInfo(String username){
        return userInfoRepository.findOne(username).orElse(null);
    }

    @Override
    public void addFriendship(Long idUser1, Long idUser2){
        super.addFriendship(idUser1, idUser2);
    }

    public Optional<UserInfo> findOneService(String username){
        return userInfoRepository.findOne(username);
    }

    public LocalDateTime getFriendshipDate(String username1, String username2){
        Long idUser1 = findIdUserByUsername(username1);
        Long idUser2 = findIdUserByUsername(username2);
        Friendship friendship = super.getFriendship(idUser1, idUser2);
        return friendship.getDate();
    }
}
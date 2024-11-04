package ubb.scs.socialnetworkgui.service;

import ubb.scs.socialnetworkgui.domain.Friendship;
import ubb.scs.socialnetworkgui.domain.Tuple;
import ubb.scs.socialnetworkgui.domain.User;
import ubb.scs.socialnetworkgui.domain.validators.ValidationException;
import ubb.scs.socialnetworkgui.repository.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;


public class NetworkService {
    private final Repository<Long, User> userRepository;
    private final Repository<Tuple<Long, Long>, Friendship> friendshipRepository;

    /**
     * @param userRepository User repository
     * @param friendshipRepository Friendship repository
     */
    public NetworkService(Repository<Long, User> userRepository, Repository<Tuple<Long, Long>, Friendship> friendshipRepository) {
        this.userRepository = userRepository;
        this.friendshipRepository = friendshipRepository;
        addAllFriendsLoad();
    }

    public void addFriendsForUser(User user){
        List<User> friends = new ArrayList<>();
        friendshipRepository.findAll().forEach(friendship -> {
            if(friendship.getIdUser1().equals(user.getId())){
                User friend = userRepository.findOne(friendship.getIdUser2()).orElseThrow();
                friends.add(friend);
            }
            if(friendship.getIdUser2().equals(user.getId())){
                User friend = userRepository.findOne(friendship.getIdUser1()).orElseThrow();
                friends.add(friend);
            }
        });
        user.setFriends(friends);
    }

    /// TODO: Find a better solution for adding all friends from file that contains friendships..
    public void addAllFriendsLoad(){
        friendshipRepository.findAll().forEach(friendship -> {
            User user1 = userRepository.findOne(friendship.getIdUser1()).orElseThrow();
            User user2 = userRepository.findOne(friendship.getIdUser2()).orElseThrow();
            user1.addFriend(user2);
            user2.addFriend(user1);
        });
    }

    /**
     * @param userId User's id
     * @return User object for user with userId id.
     */
    public User getUser(Long userId) {
        User user = userRepository.findOne(userId).orElseThrow(ValidationException::new);
        addFriendsForUser(user);
        return user;
    }

    /**
     * @return all users from userRepository
     */
    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    private Long getNewUserId() {
        final Long[] id = {1L};
        userRepository.findAll().forEach(user -> {
            if (user.getId() >= id[0]) {
                id[0] = user.getId() + 1;
            }
        });
        return id[0];
    }

    /**
     * @param firstName User's first name
     * @param lastName User's last name
     * adds a new user with first name firstName and last name lastName
     */
    public void addUser(String firstName, String lastName){
        User user = new User(firstName, lastName);
        user.setId(getNewUserId());
        userRepository.save(user);
    }

    /**
     * @param id User's id
     * revokes all friendships of user with id id
     */
    private void deleteAllFriends(Long id){
        List<Friendship> friendships = new ArrayList<>();
        friendshipRepository.findAll().forEach(friendship1 -> {
            if(friendship1.getIdUser1().equals(id) || friendship1.getIdUser2().equals(id)){
                friendships.add(new Friendship(friendship1.getIdUser1(), friendship1.getIdUser2()));
            }
        });
        for(Friendship friendship : friendships) {
            if (friendship.getIdUser1().equals(id) || friendship.getIdUser2().equals(id)) {
                Long idMin = min(friendship.getIdUser1(), friendship.getIdUser2());
                Long idMax = max(friendship.getIdUser1(), friendship.getIdUser2());
                removeFriendship(idMin, idMax);
            }
        }
    }

    /**
     * @param id User's id
     * removes user with id id
     */
    public void removeUser(Long id){
        try {
            getUser(id);
            deleteAllFriends(id);
            userRepository.delete(id);
        }
        catch (ValidationException e){
            throw new ValidationException("User not found.");
        }
    }

    /**
     * @param id User's id
     * @return list of friends of user with id id
     */
    public List<User> getFriends(Long id){
        try {
            User user = getUser(id);
            return user.getFriends();
        }
        catch (ValidationException e){
            throw new ValidationException("User not found.");
        }
    }

    /**
     * @return all friendships from friendshipRepository
     */
    public Iterable<Friendship> getAllFriendships(){
        return friendshipRepository.findAll();
    }

    /**
     * @param idUser1 User1's id
     * @param idUser2 User2's id
     * adds a new friendship between user1 with id idUser1 and user2 with id idUser2
     */
    public void addFriendship(Long idUser1, Long idUser2){
        Friendship friendship = new Friendship(idUser1, idUser2);
        Long idMin = min(idUser1, idUser2);
        Long idMax = max(idUser1, idUser2);
        friendship.setId(new Tuple<>(idMin, idMax));
        friendship.setDate(LocalDate.now());
        User user1;
        User user2;
        try {
            user1 = getUser(idUser1);
            user2 = getUser(idUser2);
        }
        catch (ValidationException e){
            throw new ValidationException("Users not found.");
        }
        friendshipRepository.findAll().forEach(friendship1 -> {
            if(friendshipRepository.findOne(friendship.getId()).isPresent()){
                throw new ValidationException("Friendship already exists.");
            }
        });
        if (friendshipRepository.findOne(friendship.getId()).isEmpty()) {
            user1.addFriend(user2);
            user2.addFriend(user1);
            friendshipRepository.save(friendship);
        }
    }

    /**
     * @param idUser1 User1's id
     * @param idUser2 User2's id
     * removes friendship between user1 with id idUser1 and user2 with id idUser2
     */
    public void removeFriendship(Long idUser1, Long idUser2){
        Long idMin = min(idUser1, idUser2);
        Long idMax = max(idUser1, idUser2);
        Tuple<Long, Long> idTuple = new Tuple<>(idMin, idMax);
        try {
            User user1 = userRepository.findOne(idUser1).orElseThrow(ValidationException::new);
            User user2 = userRepository.findOne(idUser2).orElseThrow(ValidationException::new);
            user1.removeFriend(user2);
            user2.removeFriend(user1);
        }
        catch (ValidationException e){
            throw new ValidationException("Users not found.");
        }
        friendshipRepository.delete(idTuple);
    }
}

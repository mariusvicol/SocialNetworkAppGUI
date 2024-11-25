package ubb.scs.socialnetworkgui.service;

import ubb.scs.socialnetworkgui.domain.*;
import ubb.scs.socialnetworkgui.repository.Repository;
import ubb.scs.socialnetworkgui.utils.observer.Observable;
import ubb.scs.socialnetworkgui.utils.observer.Observer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public class ApplicationService extends UserInfoServiceGUI implements Observable {
    private final Repository<Tuple<String,String>, FriendRequest> friendRequestRepository;
    private final Repository<Integer, Message> messageRepository;
    private final Repository<String, Sessions> sessionsRepository;

    public ApplicationService(Repository<Long, User> userRepository, Repository<Tuple<Long, Long>, Friendship> friendshipRepository,
                              Repository<String, UserInfo> userInfoRepository, Repository<Tuple<String,String>, FriendRequest> friendRequestRepository,
                              Repository<Integer, Message> messageRepository,
                              Repository<String, Sessions> sessionsRepository) {
        super(userRepository, friendshipRepository, userInfoRepository);
        this.friendRequestRepository = friendRequestRepository;
        this.messageRepository = messageRepository;
        this.sessionsRepository = sessionsRepository;
    }

    public List<FriendRequest> showAllFriendRequestsUser(String username){
        Iterable<FriendRequest> friendRequests =  friendRequestRepository.findAll();
        List<FriendRequest> friendRequestsUser = new ArrayList<>();
        for (FriendRequest friendRequest : friendRequests) {
            if(friendRequest.getTo().equals(username)){
                friendRequest.setId(new Tuple<>(friendRequest.getTo(), friendRequest.getFrom()));
                friendRequestsUser.add(friendRequest);
            }
        }
        return friendRequestsUser;
    }

    public void sendFriendRequest(String from, String to){
        FriendRequest friendRequest = new FriendRequest(from, to, LocalDateTime.now());
        friendRequestRepository.save(friendRequest);
        notifyObservers();
    }

    public void rejectFriendRequest(String from, String to){
        FriendRequest friendRequest = new FriendRequest(from, to, LocalDateTime.now());
        friendRequest.setId(new Tuple<>(from, to));
        friendRequestRepository.delete(friendRequest.getId());
        notifyObservers();
    }

    public void acceptFriendRequest(String from, String to){
        Long idUser1 = findIdUserByUsername(from);
        Long idUser2 = findIdUserByUsername(to);
        super.addFriendship(idUser1, idUser2);
        rejectFriendRequest(from, to);
        notifyObservers();
    }

    @Override
    public User getUser(Long userId) {
        return super.getUser(userId);
    }

    @Override
    public Iterable<User> getUsers() {
        return super.getUsers();
    }

    @Override
    public Iterable<Friendship> getAllFriendships(){
        return super.getAllFriendships();
    }

    @Override
    public List<User> getFriends(Long id){
        return super.getFriends(id);
    }

    @Override
    public void addFriendship(Long idUser1, Long idUser2){
        super.addFriendship(idUser1, idUser2);
        notifyObservers();
    }

    @Override
    public void removeFriendship(Long idUser1, Long idUser2){
        super.removeFriendship(idUser1, idUser2);
        notifyObservers();
    }

    @Override
    public UserInfo getUserInfo(String username){
        return super.getUserInfo(username);
    }

    @Override
    public Iterable<UserInfo> getUsersInfo(){
        return super.getUsersInfo();
    }

    @Override
    public void removeUserInfo(String username){
        super.removeUserInfo(username);
        notifyObservers();
    }

    @Override
    public List<User> getFriends(String username){
        return super.getFriends(username);
    }

    @Override
    public UserInfo searchUsersInfo(String username){
        return super.searchUsersInfo(username);
    }

    @Override
    public void addFriendshipUsername(String username1, String username2){
        super.addFriendshipUsername(username1, username2);
        notifyObservers();
    }

    @Override
    public void removeFriendship(String username1, String username2){
        super.removeFriendship(username1, username2);
        notifyObservers();
    }

    @Override
    public void addUserInfo(String username, String first_name, String last_name, String password){
        super.addUserInfo(username, first_name, last_name, password);
        notifyObservers();
    }

    @Override
    public void removeUser(Long id){
        super.removeUser(id);
        notifyObservers();
    }

    @Override
    public Optional<UserInfo> findOneService(String username){
        return super.findOneService(username);
    }

    public FriendRequest getFriendRequest(String from, String to){
        return friendRequestRepository.findOne(new Tuple<>(from, to)).orElse(null);
    }

    private void removeAllFriendRequestsForUser(String username){
        Iterable<FriendRequest> friendRequests = friendRequestRepository.findAll();
        for (FriendRequest friendRequest : friendRequests) {
            if(friendRequest.getFrom().equals(username) || friendRequest.getTo().equals(username)){
                friendRequestRepository.delete(new Tuple<>(friendRequest.getFrom(), friendRequest.getTo()));
            }
        }
    }

    public void deleteUser(String username){
        deleteAllMessageForUser(username);
        removeAllFriendRequestsForUser(username);
        super.removeUserInfo(username);
        notifyObservers();
    }

    public LocalDateTime getFriendshipDate(String username1, String username2){
        return super.getFriendshipDate(username1, username2);
    }

    /// ----------------------- OBSERVER -----------------------

    @Override
    public void addObserver(Observer e) {
        observers.add(e);
        System.out.println("Observer added");
    }

    @Override
    public void removeObserver(Observer e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers() {
        observers.forEach(Observer::update);
    }

    public void addObserverFriendRequest(Observer e){
        observerFriendRequest.add(e);
        System.out.println("ObserverFriendRequest add");
    }

    /// ----------------------- CHATS -----------------------
    public HashSet<Tuple<UserInfo, UserInfo>> getChats(String username){
        HashSet<Tuple<UserInfo, UserInfo>> chats = new HashSet<>();
        Iterable<Message> messages = messageRepository.findAll();
        for (Message message : messages) {
            if(message.getSender().equals(username)){
                UserInfo to = searchUsersInfo(message.getReceiver());
                if(to != null){
                    chats.add(new Tuple<>(getUserInfo(username), to));
                }
            }
            if(message.getReceiver().equals(username)){
                UserInfo from = searchUsersInfo(message.getSender());
                if(from != null){
                    chats.add(new Tuple<>(from, getUserInfo(username)));
                }
            }
        }
        return chats;
    }

    /// ----------------------- MESSAGES -----------------------

    public List<Message> getMessage(String username1, String username2){
        List<Message> messages = new ArrayList<>();
        Iterable<Message> allMessages = messageRepository.findAll();
        for (Message message : allMessages) {
            if((message.getSender().equals(username1) && message.getReceiver().equals(username2)) ||
                    (message.getSender().equals(username2) && message.getReceiver().equals(username1))){
                messages.add(message);
            }
        }
        return messages;
    }

    public void sendMessage(Message message){
        messageRepository.save(message);
        notifyObservers();
    }

    public void deleteAllMessageForUser(String username){
        Iterable<Message> messages = messageRepository.findAll();
        for (Message message : messages) {
            if(message.getSender().equals(username) || message.getReceiver().equals(username)){
                messageRepository.delete(message.getId());
            }
        }
    }

    /// ----------------------- SESSIONS -----------------------
    public void addSession(String username){
        Sessions session = new Sessions(username, LocalDateTime.now());
        sessionsRepository.save(session);
    }

    public void removeSession(String username){
        sessionsRepository.delete(username);
    }

    public boolean isOnline(String username){
        return sessionsRepository.findOne(username).isPresent();
    }
}


package ubb.scs.socialnetworkgui;

import ubb.scs.socialnetworkgui.domain.Friendship;
import ubb.scs.socialnetworkgui.domain.Tuple;
import ubb.scs.socialnetworkgui.domain.User;
import ubb.scs.socialnetworkgui.domain.validators.FriendshipValidator;
import ubb.scs.socialnetworkgui.domain.validators.UserValidator;
import ubb.scs.socialnetworkgui.repository.Repository;
import ubb.scs.socialnetworkgui.repository.database.FriendshipDBRepository;
import ubb.scs.socialnetworkgui.repository.database.UserDBRepository;
import ubb.scs.socialnetworkgui.repository.file.FriendshipRepository;
import ubb.scs.socialnetworkgui.repository.file.UtilizatorRepository;
import ubb.scs.socialnetworkgui.repository.memory.InMemoryRepository;
import ubb.scs.socialnetworkgui.service.CommunitiesService;
import ubb.scs.socialnetworkgui.service.NetworkService;
import ubb.scs.socialnetworkgui.ui.Console;

import java.util.Scanner;

public class StartApp {
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static void startApp(){
        String startOption;
        System.out.println(ANSI_GREEN + "-------| Welcome to SOCIAL NETWORK app | -------" + ANSI_RESET);
        System.out.println("Choose what type of data you want to use: file / memory / database");
        System.out.print("    >");
        Scanner scanner = new Scanner(System.in);
        startOption = scanner.next();
        startOption = startOption.toLowerCase();
        switch (startOption){
            case "file" -> startFile();
            case "memory" -> startMemory();
            case "database" -> startDataBase();
            default -> System.out.println("Invalid option");
        }
    }

    private static void startMemory(){
        Repository<Long, User> userRepository = new InMemoryRepository<>(new UserValidator());
        Repository<Tuple<Long,Long>, Friendship> friendshipRepository = new InMemoryRepository<>(new FriendshipValidator());
        NetworkService networkService = new NetworkService(userRepository, friendshipRepository);
        CommunitiesService communitiesService = new CommunitiesService(networkService);

        Console console = new Console(networkService, communitiesService, false);
        console.menu();
    }

    private static void startFile(){
        Repository<Long,User> userFileRepository = new UtilizatorRepository(new UserValidator(), "data/users.txt");
        Repository<Tuple<Long,Long>, Friendship> friendshipFileRepository = new FriendshipRepository(new FriendshipValidator(), "data/friendships.txt");
        NetworkService networkFileService = new NetworkService(userFileRepository, friendshipFileRepository);

        CommunitiesService communitiesFileSevice = new CommunitiesService(networkFileService);

        Console consoleFile = new Console(networkFileService, communitiesFileSevice, true);
        consoleFile.menu();
    }

    private static void startDataBase(){
        Repository<Long, User> userRepository = new UserDBRepository("jdbc:postgresql://localhost:5432/socialnetwork", "postgres", "0806", new UserValidator());
        Repository<Tuple<Long,Long>, Friendship> friendshipRepository = new FriendshipDBRepository("jdbc:postgresql://localhost:5432/socialnetwork", "postgres", "0806", new FriendshipValidator());
        NetworkService networkService = new NetworkService(userRepository, friendshipRepository);

        CommunitiesService communitiesService = new CommunitiesService(networkService);

        Console consoleDB = new Console(networkService, communitiesService, true);
        consoleDB.menu();
    }
}

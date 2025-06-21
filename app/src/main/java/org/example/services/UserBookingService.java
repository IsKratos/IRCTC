package org.example.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entities.Ticket;
import org.example.entities.User;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class UserBookingService {

    private User user;

    private List<User> users;

    private ObjectMapper objectMapper = new ObjectMapper();

    private static final String USER_PATH = "app/src/main/java/org/example/localDb/users.json";

    public UserBookingService(User user) throws IOException {
        this.user = user;
        File userS = new File(USER_PATH);
        users = objectMapper.readValue(userS, new TypeReference<List<User>>() {});

    }

    public UserBookingService() throws IOException {
       fetchUser();
    }

    public void fetchUser() throws IOException {
        File userS = new File(USER_PATH);
        users = objectMapper.readValue(userS, new TypeReference<List<User>>() {});
    }

    public Boolean login(){
        Optional<User> foundUser = users.stream().filter(user1 -> { return user1.getName().equalsIgnoreCase(user.getName()) &&
                UserServiceUtil.checkPassword(user.getPassword(),user1.getHashPassword());}).findFirst();
        return foundUser.isPresent();
    }

    public Boolean signUp(User user1){
        try{
            users.add(user1);
            saveUserListToFile();
            return Boolean.TRUE;
        }catch (IOException ioException){
            return Boolean.FALSE;
        }
    }

    public void saveUserListToFile() throws IOException {
        File file = new File(USER_PATH);
        objectMapper.writeValue(file,users);
    }

    public void fetchBooking(){
        user.printTickets();
    }

    public Boolean cancelBooking(String ticketId) throws IOException {
        try {
            List<Ticket> ticBooked = user.getTicketsBooked();
            ticBooked.removeIf(tic->(tic.getTicketId().equals(ticketId)));
            saveUserListToFile();
            return Boolean.TRUE;
        } catch (IOException e) {
            return Boolean.FALSE;
        }
    }
}

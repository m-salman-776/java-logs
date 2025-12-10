package Project101.BookingSystem.MoviesBooking.Services;

import Project101.BookingSystem.MoviesBooking.Model.User;
import Project101.BookingSystem.MoviesBooking.Repository.UserRepository;

public class UserService {
    private UserRepository userRepository;
    public UserService(UserRepository repository){
        this.userRepository = repository;
    }

    public User getUserById(Long userId){
        // todo validation;
        return userRepository.getUserById(userId);
    }

    public boolean addUser(String userName ,String email){
        User user = new User(userName,email);
        this.userRepository.addUser(user);
        return true;
    }

}

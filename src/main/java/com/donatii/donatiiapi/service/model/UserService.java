package com.donatii.donatiiapi.service.model;

import com.donatii.donatiiapi.model.*;
import com.donatii.donatiiapi.repository.IUserRepository;
import com.donatii.donatiiapi.service.exceptions.EmptyObjectException;
import com.donatii.donatiiapi.service.exceptions.MyException;
import com.donatii.donatiiapi.service.exceptions.NotFoundException;
import com.donatii.donatiiapi.service.interfaces.IAchievementService;
import com.donatii.donatiiapi.service.interfaces.IUserService;
import com.donatii.donatiiapi.utils.Ensure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final IAchievementService achievementService;

    @Autowired
    public UserService(IUserRepository userRepository, PasswordEncoder passwordEncoder, IAchievementService achievementService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.achievementService = achievementService;
    }

    public User login(String email, String parola) throws NotFoundException, EmptyObjectException {
        Ensure.NotNullOrEmpty(email);
        Ensure.NotNullOrEmpty(parola);

        Optional<User> user = userRepository.findUserByEmail(email);
        if(user.isEmpty() || !passwordEncoder.matches(parola, user.get().getParola())) {
            throw new NotFoundException("User not found");
        }
        return user.get();
    }

    public User register(User user) throws MyException {
        Ensure.NotNull(user);

        if(userRepository.findUserByEmail(user.getEmail()).isPresent())
            throw new MyException("Email already exists");
        return save(user);
    }

    public void update(Long id, User user) throws NotFoundException {
        Ensure.NotNull(id);
        Ensure.NotNull(user);

        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty())
            throw new NotFoundException("User not found");
        User userToUpdate = userOptional.get();
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setParola(user.getParola());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setFullName(user.getFullName());
        userToUpdate.setGender(user.getGender());
        save(userToUpdate);
    }

    public void delete(Long id) throws NotFoundException {
        Ensure.NotNull(id);

        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty())
            throw new NotFoundException("User not found");
        userRepository.deleteById(id);
    }

    public User findById(Long id) throws NotFoundException {
        Ensure.NotNull(id);

        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty())
            throw new NotFoundException("User not found");
        return userOptional.get();
    }

    public User save(User user) {
        Ensure.NotNull(user);

        String encodedPassword = passwordEncoder.encode(user.getParola());
        user.setParola(encodedPassword);
        return userRepository.save(user);
    }

    public void deleteCauzaFromUser(Cauza cauza) throws NotFoundException {
        Ensure.NotNull(cauza);

        Optional<User> user = userRepository.findUserByCauzaId(cauza.getId());
        if(user.isEmpty())
        {
            throw new NotFoundException("User inexistent!");
        }
        user.get().getCauze().remove(cauza);
        save(user.get());
    }

    @Override
    public List<Achievement> donate(Long userId, Integer sum, String currency, Cauza cauza) throws NotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty())
            throw new NotFoundException("User not found");
        User user = userOptional.get();
        Donatie donatie = new Donatie(0L, sum, LocalDateTime.now(), currency, cauza.getId(), cauza.getTitlu());
        user.getDonatii().add(donatie);
        Long points = 3L* sum ;
        if(user.getNrDonations() == null)
            user.setNrDonations(0);
        if(user.getPoints() == null)
            user.setPoints(0L);
        user.setNrDonations(user.getNrDonations()+1);
        user.setPoints(user.getPoints() + points);
        return unlockAchievements(user);
    }

    public List<Achievement> unlockAchievements(User user){
        List<Achievement> achievementList = new ArrayList<Achievement>(){};
        for (Achievement achievement: achievementService.getAchievements()) {
            if(!user.getAchievements().contains(achievement)) {
                if (achievement.getPoints_required() != -1 && achievement.getPoints_required() <= user.getPoints()) {
                    achievementList.add(achievement);
                    user.getAchievements().add(achievement);
                } else if (achievement.getNr_donations_required() != -1 && achievement.getNr_donations_required() <= user.getNrDonations()) {
                    achievementList.add(achievement);
                    user.getAchievements().add(achievement);
                }
            }
        }
        save(user);
        return achievementList;
    }

    public List<User> getTopUsersByPoints(int numberOfUsers) {
        return userRepository.findTopNUsersByOrderByPointsDesc(numberOfUsers);
    }

    @Override
    public void updateResources(Long userId, Long points) throws NotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty())
            throw new NotFoundException("User not found");
        User user = userOptional.get();
        user.setPoints(user.getPoints() + points);
        save(user);
    }
}

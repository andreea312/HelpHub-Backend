package com.donatii.donatiiapi.service.model;

import com.donatii.donatiiapi.model.*;
import com.donatii.donatiiapi.repository.IUserRepository;
import com.donatii.donatiiapi.service.exceptions.EmptyObjectException;
import com.donatii.donatiiapi.service.exceptions.MyException;
import com.donatii.donatiiapi.service.exceptions.NotFoundException;
import com.donatii.donatiiapi.service.interfaces.IUserService;
import com.donatii.donatiiapi.utils.Ensure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(IUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
    public Pair<Long, Integer> donate(Long userId, Integer sum, String currency, Cauza cauza) throws NotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty())
            throw new NotFoundException("User not found");
        User user = userOptional.get();
        Donatie donatie = new Donatie(0L, sum, LocalDateTime.now(), currency, cauza.getId(), cauza.getTitlu());
        user.getDonatii().add(donatie);
        Long coins = 3L * sum;
        Integer level = sum / 5;
        user.setCoins(user.getCoins() + coins);
        user.setLevel(user.getLevel() + level);
        save(user);
        return Pair.of(coins, level);
    }

    @Override
    public void updateResources(Long userId, Long coins, Integer level) throws NotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty())
            throw new NotFoundException("User not found");
        User user = userOptional.get();
        user.setCoins(user.getCoins() + coins);
        if(level != null)
            user.setLevel(user.getLevel() + level);
        save(user);
    }
}

package com.donatii.donatiiapi.service.interfaces;

import com.donatii.donatiiapi.model.Achievement;
import com.donatii.donatiiapi.model.Cauza;
import com.donatii.donatiiapi.model.User;
import com.donatii.donatiiapi.service.exceptions.EmptyObjectException;
import com.donatii.donatiiapi.service.exceptions.MyException;
import com.donatii.donatiiapi.service.exceptions.NotFoundException;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.Set;

public interface IUserService {
    /**
     * Logheaza un user
     * @param email Email User
     * @param parola Parola User
     * @return User
     */
    User login(String email, String parola) throws NotFoundException, EmptyObjectException;

    /**
     * Inregistreaza un user
     *
     * @param user User
     * @return User
     */
    User register(User user) throws MyException;

    /**
     * Modifica un user
     * @param id Identificator User
     * @param user User
     */
    void update(Long id, User user) throws NotFoundException;

    User findUserOfCauza(Cauza cauza) throws NotFoundException;

    /**
     * Sterge un user
     * @param id Identificator User
     */
    void delete(Long id) throws NotFoundException;

    /**
     * Gaseste un user dupa id
     * @param id Identificator User
     * @return User
     */
    User findById(Long id) throws NotFoundException;

    /**
     * Salveaza un user
     *
     * @param user User
     * @return User
     */
    User save(User user);

    /**
     * Sterge o cauza a unui user
     * @param cauza Cauza
     */
    void deleteCauzaFromUser(Cauza cauza) throws NotFoundException;

    /**
     * Doneaza
     *
     * @param userId   Identificator User
     * @param sum      Suma
     * @param currency Moneda
     * @param cauza    Cauza
     * @return
     */
    List<Achievement> donate(Long userId, Integer sum, String currency, Cauza cauza) throws NotFoundException;

    /**
     * Updateaza resursele unui user
     * @param userId Identificator User
     * @param coins Monede
     */
    void updateResources(Long userId, Long coins) throws NotFoundException;

    /**
     * Seteaza achievementurile unui user
     * @param user
     * @return lista de achievementuri deblocate de user dupa o donatie
     */
    public List<Achievement> unlockAchievements(User user);

    /**
     * Top-ul userilor dupa puncte
     * @param numberOfUsers cati useri se vor in top
     * @return top ul userilor
     */
    public List<User> getTopUsersByPoints(int numberOfUsers) ;

    /**
     * Gaseste un user dupa email-ul lui
     * @param email
     * @return userul dupa email
     * @throws NotFoundException
     * @throws EmptyObjectException
     */
    public User GetUserByEmail(String email) throws NotFoundException, EmptyObjectException;
}

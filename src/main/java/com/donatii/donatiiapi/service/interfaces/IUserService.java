package com.donatii.donatiiapi.service.interfaces;

import com.donatii.donatiiapi.model.Cauza;
import com.donatii.donatiiapi.model.User;
import com.donatii.donatiiapi.service.exceptions.EmptyObjectException;
import com.donatii.donatiiapi.service.exceptions.MyException;
import com.donatii.donatiiapi.service.exceptions.NotFoundException;
import org.springframework.data.util.Pair;

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
    Pair<Long, Integer> donate(Long userId, Integer sum, String currency, Cauza cauza) throws NotFoundException;

    /**
     * Updateaza resursele unui user
     * @param userId Identificator User
     * @param coins Monede
     * @param level Nivel
     */
    void updateResources(Long userId, Long coins, Integer level) throws NotFoundException;
}

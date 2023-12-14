package com.donatii.donatiiapi.repository;

import com.donatii.donatiiapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmailAndParola(@Param("email")String email, @Param("parola")String parola);
    Optional<User> findUserByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u JOIN u.cauze c WHERE c.id = :cauzaId")
    Optional<User> findUserByCauzaId(@Param("cauzaId") Long cauzaId);


    @Query( "SELECT u FROM User u ORDER BY u.points DESC")
    List<User> findTopNUsersByOrderByPointsDesc(int numberOfUsers);
}


package com.donatii.donatiiapi.repository;

import com.donatii.donatiiapi.model.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAchievementRepository extends JpaRepository<Achievement, Long> {
}

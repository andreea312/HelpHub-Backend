package com.donatii.donatiiapi.service.interfaces;

import com.donatii.donatiiapi.model.Achievement;
import com.donatii.donatiiapi.service.exceptions.NotFoundException;

public interface IAchievementService {
    /**
     * Get achievement by id
     * @param achievement_id
     * @return Achievement
     * @throws NotFoundException
     */
    Achievement getAchievement(Long achievement_id) throws NotFoundException;

    /**
     * Get all achievements
     * @return Achievement list
     */
    Iterable<Achievement> getAchievements();

}

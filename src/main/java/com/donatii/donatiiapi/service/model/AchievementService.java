package com.donatii.donatiiapi.service.model;

import com.donatii.donatiiapi.model.Achievement;
import com.donatii.donatiiapi.repository.IAchievementRepository;
import com.donatii.donatiiapi.service.exceptions.NotFoundException;
import com.donatii.donatiiapi.service.interfaces.IAchievementService;
import com.donatii.donatiiapi.utils.Ensure;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AchievementService implements IAchievementService {
    private final IAchievementRepository achievementRepository;

    public AchievementService(IAchievementRepository achievementRepository) {
        this.achievementRepository = achievementRepository;
    }

    @Override
    public Achievement getAchievement(Long achievement_id) throws NotFoundException {
        Ensure.NotNull(achievement_id);

        Optional<Achievement> achievementOptional = achievementRepository.findById(achievement_id);
        if(achievementOptional.isEmpty())
            throw new NotFoundException("Achievement inexistent!");
        return achievementOptional.get();
    }

    @Override
    public Iterable<Achievement> getAchievements() {
        return achievementRepository.findAll();
    }
}

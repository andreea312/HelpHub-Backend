package com.donatii.donatiiapi.controller;

import com.donatii.donatiiapi.model.Achievement;
import com.donatii.donatiiapi.model.User;
import com.donatii.donatiiapi.service.interfaces.IAchievementService;
import com.donatii.donatiiapi.service.interfaces.IUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/achievement")
@Tag(name = "Achievement")
public class AchievementController {
    private final IAchievementService achievementService;
    private final IUserService userService;

    @Autowired
    public AchievementController(IAchievementService achievementService, IUserService userService) {
        this.achievementService = achievementService;
        this.userService = userService;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findAll() {
        try {
            List<Achievement> achs = (List<Achievement>) achievementService.getAchievements();
            return ResponseEntity.ok().body(achs);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/{idUser}/{idAchievement}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> userGotAchievement(@PathVariable("idUser") Long idUser,
                                          @PathVariable("idAchievement") Long idAchievement) {
        try {
            User user = userService.findById(idUser);
            for (Achievement a: user.getAchievements()){
                if (a.getId() == idAchievement){
                    return ResponseEntity.ok().body("DA");
                }
            }
            return ResponseEntity.ok().body("NU");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}


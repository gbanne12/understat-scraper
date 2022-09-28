package com.bannerga.app.controller;

import com.bannerga.app.entity.Footballer;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.bannerga.app.stats.DataOrganiser;
import com.bannerga.app.stats.Understat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {

    @GetMapping(path="/")
    public String index(Model model) {
        JSONArray playerData = new JSONArray();
        try {
            Understat understat = new Understat();
            playerData = understat.getPlayerData();
        } catch (IOException e) {
            // do something
        }
        DataOrganiser dataOrganiser = new DataOrganiser();
        List<JSONObject> sortedByGoals = dataOrganiser.sortByKey(playerData, "xG");
        sortedByGoals = dataOrganiser.limit(sortedByGoals, 30);

        List<Footballer> goalScorers = new ArrayList<>();
        for (int i = 0; i < sortedByGoals.size(); i++) {
            JSONObject current = sortedByGoals.get(i);
            Footballer footballer = new Footballer();
            footballer.setName(current.getString("player_name"));
            footballer.setTeam(current.getString("team_title"));
            footballer.setxG(current.getString("xG"));
            footballer.setNpxG(current.getString("npxG"));
            footballer.setGames(current.getString("games"));
            footballer.setShots(current.getString("shots"));
            footballer.setGoals(current.getString("goals"));
            footballer.setNpg(current.getString("npg"));
            goalScorers.add(footballer);
        }

        List<JSONObject> sortedByAssists = dataOrganiser.sortByKey(playerData, "xA");
        sortedByAssists = dataOrganiser.limit(sortedByAssists, 30);

        List<Footballer> assistProviders = new ArrayList<>();
        for (int i = 0; i < sortedByAssists.size(); i++) {
            JSONObject current = sortedByAssists.get(i);
            Footballer footballer = new Footballer();
            footballer.setName(current.getString("player_name"));
            footballer.setTeam(current.getString("team_title"));
            footballer.setKey_passes(current.getString("key_passes"));
            footballer.setAssists(current.getString("assists"));
            footballer.setxA(current.getString("xA"));
            assistProviders.add(footballer);
        }
        model.addAttribute("goalScorers", goalScorers);
        model.addAttribute("assistProviders", assistProviders);
        return "index";
    }

    @GetMapping(path="/help")
    public String displayHelp(Model model) {
        return "forward:helpme.html";
    }
}

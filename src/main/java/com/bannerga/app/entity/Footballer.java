package com.bannerga.app.entity;

public class Footballer {

    private String name;
    private String team;

    private String npxG;
    private String xG;
    private String npg;
    private String goals;
    private String shots;

    private String xA;
    private String assists;
    private String key_passes;

    private String games;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNpxG() {
        return npxG.substring(0,4);
    }

    public void setNpxG(String npxG) {
        this.npxG = npxG;
    }

    public String getNpg() {
        return npg;
    }

    public void setNpg(String npg) {
        this.npg = npg;
    }

    public String getxA() {
        return xA.substring(0,4);
    }

    public void setxA(String xA) {
        this.xA = xA;
    }

    public String getAssists() {
        return assists;
    }

    public void setAssists(String assists) {
        this.assists = assists;
    }

    public String getGames() {
        return games;
    }

    public void setGames(String games) {
        this.games = games;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getxG() {
        return xG.substring(0,4);
    }

    public void setxG(String xG) {
        this.xG = xG;
    }

    public String getGoals() {
        return goals;
    }

    public void setGoals(String goals) {
        this.goals = goals;
    }

    public String getShots() {
        return shots;
    }

    public void setShots(String shots) {
        this.shots = shots;
    }

    public String getKey_passes() {
        return key_passes;
    }

    public void setKey_passes(String key_passes) {
        this.key_passes = key_passes;
    }
}

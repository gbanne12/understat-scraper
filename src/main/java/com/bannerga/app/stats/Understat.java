package com.bannerga.app.stats;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Scrapes player data from http://understat.com
 * The data is contained within a script tag on the page.
 */
public class Understat {

    private Document document;

    public Understat() throws IOException {
        final String EPL_PLAYER_DATA = "https://understat.com/league/EPL";
        document = Jsoup.connect(EPL_PLAYER_DATA).get();
    }

    /**
     * Obtains the player data from http://understat.com/League/EPL
     *
     * @return a JSONArray containing the data for each player
     */
    public JSONArray getPlayerData() {
        String playersData = getData("playersData");
        /* The response string is now as below:
         *  \n\tvar playersData\t= JSON.parse('[{"id":"5555","player_name":"Dominic Calvert-Lewin","games":"10"...
         *   ..."npxG":"0","xGChain":"0.21879525482654572","xGBuildup":"0.21879525482654572"}]');
         *
         * To build the JSONArray we need to discard the first 32 chars and the final 3 chars.
         */
        int initialCharsToTrim = 32;
        int finalCharactersToTrim = 3;
        String json = playersData.substring(initialCharsToTrim, playersData.length() - finalCharactersToTrim + 1);
        return new JSONArray(json);
    }

    /**
     * Obtains the fixture data from http://understat.com/League/EPL
     *
     * @return a JSONArray containing the data for each of the 380 fixtures played in a season
     */
    public JSONArray getFixtureData() {
        String fixtureData = getData("datesData");
        /* The response string is now as below:
         * \n\tvar datesData\t= JSON.parse('[{"id":"14086","isResult":true,"h":{"id":"228","title":"Fulham", ...
         *  ... "xG":{"h":null,"a":null},"datetime":"2021-05-23 07:00:00"}]'),
         *   _week		= 49,
         *	_year		= 2020;
         *
         * To build the JSONArray we need to discard the first 30 chars and the final 37 chars.
         */
        int initialCharsToTrim = 31;
        int finalCharactersToTrim = 37;
        String json = fixtureData.substring(initialCharsToTrim, fixtureData.length() - finalCharactersToTrim + 1);
        return new JSONArray(json);
    }

    /**
     * Obtains the teams data from http://understat.com/League/EPL
     *
     * @return a JSONArray containing the data for each of the 20 teams
     */
    public JSONObject getTeamsData() {
        String teamsData = getData("teamsData");
        int initialCharsToTrim = 30;
        int finalCharactersToTrim = 5;
        String json = teamsData.substring(initialCharsToTrim, teamsData.length() - finalCharactersToTrim + 1);
        return new JSONObject(json);
    }

    private String getData(String name) {
        // The data is contained within script tags on the page
        Elements scriptElements = document.getElementsByTag("script");
        String response = "";
        for (int i = 0; i < scriptElements.size(); i++) {
            DataNode node = scriptElements.dataNodes().get(i);
            if (node.getWholeData().contains(name)) {
                response = node.getWholeData();
                // replace obfuscated characters based on hex codes
                response = response.replace("\\x5B", "[");
                response = response.replace("\\x5D", "]");
                response = response.replace("\\x7B", "{");
                response = response.replace("\\x7D", "}");
                response = response.replace("\\x22", "\"");
                response = response.replace("\\x3A", ":");
                response = response.replace("\\x26\\x23039\\x3B", "'");
                response = response.replace("\\x20", " ");
                response = response.replace("\\x2D", "-");
                response = response.replace("\\x5Cu00c1", "a");
                response = response.replace("\\x5Cu00e3", "a");
                response = response.replace("\\x5Cu00e1n", "a");
                response = response.replace("\\x5Cu00e1", "a");
                response = response.replace("\\x5Cu00e4", "a");
                response = response.replace("\\x5Cu00e7", "c");
                response = response.replace("\\x5Cu00e9", "e");
                response = response.replace("\\x5Cu00eb", "e");
                response = response.replace("\\x5Cu00ed", "i");
                response = response.replace("\\x5Cu00f1", "n");
                response = response.replace("\\x5Cu00f8", "o");
                response = response.replace("\\x5Cu00f6", "o");
                response = response.replace("\\x5Cu00f3", "o");
                response = response.replace("\\x5Cu00fa", "u");
                response = response.replace("\\x5Cu00fc", "u");
                response = response.replace("\\x5Cu00dc", "u");
                response = response.replace("\\x5Cu00df", "ss");
                break;
            }

        }
        return response;
    }
}


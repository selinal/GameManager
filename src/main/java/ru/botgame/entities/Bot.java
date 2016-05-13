package ru.botgame.entities;

public class Bot {

    private String Name;
    private String Location;

    private int victoryCount;//количество побед
    private int defeatCount;//количество поражений
    private int drawCount;//Количество ничьей

    public Bot() {
    }

    public Bot(String name, String location) {
        Name = name;
        Location = location;

        victoryCount = 0;
        defeatCount = 0;
        drawCount = 0;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public void addOneVictory(){victoryCount++;}

    public void addOneDefeat(){defeatCount++;}

    public void addOneDraw(){drawCount++;}

    public int getVictoryCount() {
        return victoryCount;
    }

    public int getDefeatCount() {
        return defeatCount;
    }

    public int getDrawCount() {
        return drawCount;
    }
}

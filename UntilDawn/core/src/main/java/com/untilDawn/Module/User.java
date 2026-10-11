package com.untilDawn.Module;

public class User {
    private String username;
    private String password;
    private boolean isGuest ;
    private String question;
    private String answer;
    private String avatarPath;
    private transient Player player;
    private int score;
    private int kills;
    private int survivalTime;
    private boolean hasWonLastGame;

    public User(String username, String password, String avatarPath) {
        this.username = username;
        this.password = password;
        this.avatarPath = avatarPath;
        this.isGuest = false;
        player = new Player();
    }

    public User(String avatarPath){
        this.avatarPath = avatarPath;
        this.isGuest = true;
        player = new Player();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isGuest() {
        return isGuest;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void setSurvivalTime(int survivalTime) {
        this.survivalTime = survivalTime;
    }

    public void setHasWonLastGame(boolean hasWonLastGame) {
        this.hasWonLastGame = hasWonLastGame;
    }

    public int getKills() {
        return kills;
    }

    public int getSurvivalTime() {
        return survivalTime;
    }

    public boolean isHasWonLastGame() {
        return hasWonLastGame;
    }

    public int getScore() {
        return score;
    }

    public Player getPlayer() {
        return player;
    }

    public void resetPlayer() {
        player.setWin(false);
        player.setHealth(player.getInitialHealth());
        player.setLevel(1);
        player.setXp(0);
        player.setScore(0);
        player.setKill(0);
        player.setSurvivalTime(0);
        player.getWeapon().resetWeapon();
        player.setPlayerX(0);
        player.setPlayerY(0);
    }
}

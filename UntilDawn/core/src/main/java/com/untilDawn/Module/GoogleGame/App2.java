package com.untilDawn.Module.GoogleGame;


public class App2 {
    private Game currentGame;


    private static App2 app;
    private App2(){
        this.currentGame = new Game();

    }
    public static App2 getInstance(){
        if(app == null){
            app = new App2();
        }
        return app;
    }

    public Game getCurrentGame() {
        return currentGame;
    }

}

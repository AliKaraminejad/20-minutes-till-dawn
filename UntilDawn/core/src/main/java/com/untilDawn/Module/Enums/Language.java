package com.untilDawn.Module.Enums;

public enum Language {
    SIGN_UP("Sign Up", "S'inscrire"),
    GUEST("Guest", "Invité"),
    USERNAME("Username", "Nom d'utilisateur"),
    PASSWORD("Password", "Mot de passe"),
    EXIT("Exit", "Quitter"),
    LOGIN("Login", "Se connecter"),
    LOGIN_MENU("Login Menu", "Menu de connexion"),
    GO_TO_SIGN_UP("Go to Sign Up", "Aller à l'inscription"),
    FORGOT_PASSWORD("Forgot Password", "Mot de passe oublié"),
    SUBMIT("Submit", "Soumettre"),
    CANCEL("Cancel", "Annuler"),
    MAIN_MENU("Main Menu", "Menu Principal"),
    SETTINGS("Settings", "Paramètres"),
    PROFILE("Profile", "Profil"),
    PREGAME("Pregame", "Pré-jeu"),
    SCOREBOARD("ScoreBoard", "Tableau des scores"),
    HINT("Hint", "Astuce"),
    LOGOUT("Logout", "Déconnexion"),
    SAVED_GAME("Saved Game", "Partie sauvegardée"),
    SCORE("Score", "Score"),
    MUSIC_VOLUME("Music Volume", "Volume de la musique"),
    MUSIC1("Music1", "Musique 1"),
    MUSIC2("Music2", "Musique 2"),
    MUSIC3("Music3", "Musique 3"),
    BACK("Back", "Retour"),
    MUTE_SFX("Mute SFX", "Couper les effets sonores"),
    AUTO_RELOAD("Auto Reload", "Rechargement automatique"),
    BLACK_AND_WHITE("Black and White", "Noir et blanc"),
    PROFILE_MENU("Profile Menu", "Menu du profil"),
    CHANGE_USERNAME("Change Username", "Changer le nom d'utilisateur"),
    CHANGE_PASSWORD("Change Password", "Changer le mot de passe"),
    DELETE_ACCOUNT("Delete Account", "Supprimer le compte"),
    CHANGE_AVATAR("Change Avatar", "Changer l'avatar"),
    CONFIRM_DELETE("Are you sure you want to delete your account?", "Êtes-vous sûr de vouloir supprimer votre compte ?"),
    YES("Yes", "Oui"),
    NO("No", "Non"),
    START_GAME("Start Game", "Démarrer le jeu"),
    HERO_SHANA("Shana", "Shana"),
    HERO_DIAMOND("Diamond", "Diamant"),
    HERO_SCARLET("Scarlet", "Écarlate"),
    HERO_LILITH("Lilith", "Lilith"),
    HERO_DASHER("Dasher", "Sprinteur"),
    DURATION_2_MIN("2 min", "2 min"),
    DURATION_5_MIN("5 min", "5 min"),
    DURATION_10_MIN("10 min", "10 min"),
    DURATION_20_MIN("20 min", "20 min"),
    HINT_HERO_SHANA("Shana", "Shana"),
    HINT_HERO_DIAMOND("Diamond", "Diamant"),
    HINT_HERO_SCARLET("Scarlet", "Écarlate"),
    HINT_KEYS("Controller Keys", "Touches du contrôleur"),
    HINT_CHEAT("Cheat", "Triche"),
    HINT_ABILITIES("Ability", "Capacité"),
    HINT_BACK("Back", "Retour"),
    CHOOSING_AVATAR("Choose Avatar", "Choisir le avatar"),
    HINT_DIALOG_SHANA("Shana is a balanced character with average speed and average health.", "..."),
    HINT_DIALOG_DIAMOND("Diamond has higher health but lower speed.", "..."),
    HINT_DIALOG_SCARLET("Scarlet is even faster than Shana but its health is a little bit less.", "..."),
    HINT_DIALOG_KEYS("W ~> move up\nS ~> move down\nA ~> move left\nD ~> move right\nLeft click ~> shoot\nR ~> reload\nP ~> pause", "..."),
    HINT_DIALOG_CHEAT("T ~> increases the game time for one minute\n L ~> increasing level \n H ~> makes your health max\n Q ~> going to buss fight\n K ~> increases kills", "..."),
    HINT_DIALOG_ABILITIES("Vitality ~> increases maximum health by 1\nDamager ~> increases weapon damage 1.25x for 10s\nProcrease ~> ???", "..."),
    HINT_OK("OK", "D'accord"),
    PAUSE_TITLE("Pause", "Pause"),
    PAUSE_CONTINUE("Continue", "Continuer"),
    PAUSE_EXIT("Exit", "Quitter"),
    PAUSE_GIVE_UP("Give Up", "Abandonner"),
    SCOREBOARD_TITLE("Scoreboard", "Tableau des scores"),
    SORT_BY("Sort by:", "Trier par :"),
    SORT_SCORE("Score", "Score"),
    SORT_USERNAME("Username", "Nom d'utilisateur"),
    SORT_KILL("Kill", "Eliminations"),
    SORT_TIME("Survival Time", "Temps de survie"),
    KILL("Kill", "Éliminations"),
    TIME("Survival Time", "Temps de survie"),
    SURVIVAL_TIME("Survival Time", "Temps de survie"),
    CHOOSE_ABILITIES("Choose Ability", "choisir la capacité"),
    CHEAT_TITLE("Cheat", "tricher"),
    ABILITY("Abilities","capacité"),
    CHOOSE_FROM_COMPUTER("Choose From Computer", "Choisir depuis un ordinateur"),

    ;

    private String english;
    private String french;
    private static boolean isEnglish = true;

    Language(String english, String french) {
        this.english = english;
        this.french = french;
    }
    public static void setIsEnglish(boolean isEnglish) {
        Language.isEnglish = isEnglish;
    }

    public String getText(){
        if(isEnglish){
            return english;
        }
        return french;
    }

    public static boolean isIsEnglish() {
        return isEnglish;
    }
}

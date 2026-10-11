package com.untilDawn.Module;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.awt.*;
import java.util.ArrayList;

public class GameAssetManager {
    private static GameAssetManager instance;

    private GameAssetManager() {
    }

    public static GameAssetManager getInstance() {
        if (instance == null) {
            instance = new GameAssetManager();
        }
        return instance;
    }
    private Skin skin = new Skin(Gdx.files.internal("skin/pixthulhu-ui.json"));
    private ArrayList<String> avatars = new ArrayList<>();
    {
        avatars.add("T_Abby_Portrait.png");
        avatars.add("T_Dasher_Portrait.png");
        avatars.add("T_Diamond_Portrait.png");
        avatars.add("T_Hastur_Portrait.png");
        avatars.add("T_Hina_Portrait.png");
        avatars.add("T_Lilith_Portrait.png");
        avatars.add("T_Luna_Portrait.png");
        avatars.add("T_Raven_Portrait.png");
        avatars.add("T_Scarlett_Portrait.png");
        avatars.add("T_Shana_Portrait.png");
        avatars.add("T_Spark_Portrait.png");
        avatars.add("T_Yuki_Portrait.png");


    }
    private final String character1_idle0 = "Character/Idle_0.png";
    private final String character1_idle1 = "Character/Idle_1.png";
    private final String character1_idle2 = "Character/Idle_2.png";
    private final String character1_idle3 = "Character/Idle_3.png";
    private final String character1_idle4 = "Character/Idle_4.png";
    private final String character1_idle5 = "Character/Idle_5.png";
    private final Texture character1_idle0_tex = new Texture(character1_idle0);
    private final Texture character1_idle1_tex = new Texture(character1_idle1);
    private final Texture character1_idle2_tex = new Texture(character1_idle2);
    private final Texture character1_idle3_tex = new Texture(character1_idle3);
    private final Texture character1_idle4_tex = new Texture(character1_idle4);
    private final Texture character1_idle5_tex = new Texture(character1_idle5);
    private final Animation<Texture> character1_idle_frames = new Animation<>(0.1f, character1_idle0_tex, character1_idle1_tex, character1_idle2_tex, character1_idle3_tex, character1_idle4_tex, character1_idle5_tex);

    public Animation<Texture> getCharacter1_idle_frames() {
        return character1_idle_frames;
    }

    public String getCharacter1_idle0() {
        return character1_idle0;
    }



    private final String character2_idle0 = "Character2/Walk_0 #8277.png";
    private final String character2_idle1 = "Character2/Walk_1 #8631.png";
    private final String character2_idle2 = "Character2/Walk_2 #8710.png";
    private final String character2_idle3 = "Character2/Walk_3 #8768.png";
    private final String character2_idle4 = "Character2/Walk_4 #8464.png";
    private final String character2_idle5 = "Character2/Walk_5 #8687.png";
    private final String character2_idle6 = "Character2/Walk_6 #8441.png";
    private final String character2_idle7 = "Character2/Walk_7 #8213.png";
    private final Texture character2_idle0_tex = new Texture(character2_idle0);
    private final Texture character2_idle1_tex = new Texture(character2_idle1);
    private final Texture character2_idle2_tex = new Texture(character2_idle2);
    private final Texture character2_idle3_tex = new Texture(character2_idle3);
    private final Texture character2_idle4_tex = new Texture(character2_idle4);
    private final Texture character2_idle5_tex = new Texture(character2_idle5);
    private final Texture character2_idle6_tex = new Texture(character2_idle6);
    private final Texture character2_idle7_tex = new Texture(character2_idle7);

    private final Animation<Texture> character2_idle_frames = new Animation<>(0.1f, character2_idle0_tex, character2_idle1_tex, character2_idle2_tex, character2_idle3_tex, character2_idle4_tex, character2_idle5_tex, character2_idle6_tex, character2_idle7_tex);

    public Animation<Texture> getcharacter2_idle_frames() {
        return character2_idle_frames;
    }

    public String getcharacter2_idle0() {
        return character2_idle0;
    }


    private final String character3_idle0 = "Character3/Walk_0 #8278.png";
    private final String character3_idle1 = "Character3/Walk_1 #8632.png";
    private final String character3_idle2 = "Character3/Walk_2 #8711.png";
    private final String character3_idle3 = "Character3/Walk_3 #8769.png";
    private final String character3_idle4 = "Character3/Walk_5 #8688.png";
    private final String character3_idle5 = "Character3/Walk_6 #8442.png";
    private final String character3_idle6 = "Character3/Walk_7 #8214.png";
    private final Texture character3_idle0_tex = new Texture(character3_idle0);
    private final Texture character3_idle1_tex = new Texture(character3_idle1);
    private final Texture character3_idle2_tex = new Texture(character3_idle2);
    private final Texture character3_idle3_tex = new Texture(character3_idle3);
    private final Texture character3_idle4_tex = new Texture(character3_idle4);
    private final Texture character3_idle5_tex = new Texture(character3_idle5);
    private final Texture character3_idle6_tex = new Texture(character3_idle6);

    private final Animation<Texture> character3_idle_frames = new Animation<>(0.1f, character3_idle0_tex, character3_idle1_tex, character3_idle2_tex, character3_idle3_tex, character3_idle4_tex, character3_idle5_tex, character3_idle6_tex);

    public Animation<Texture> getcharacter3_idle_frames() {
        return character3_idle_frames;
    }

    public String getcharacter3_idle0() {
        return character3_idle0;
    }



    private final String character4_idle0 = "Character4/Walk_0 #8279.png";
    private final String character4_idle1 = "Character4/Walk_1 #8633.png";
    private final String character4_idle2 = "Character4/Walk_2 #8712.png";
    private final String character4_idle3 = "Character4/Walk_3 #8770.png";
    private final String character4_idle4 = "Character4/Walk_4 #8465.png";
    private final String character4_idle5 = "Character4/Walk_5 #8689.png";
    private final String character4_idle6 = "Character4/Walk_6 #8443.png";
    private final String character4_idle7 = "Character4/Walk_7 #8215.png";
    private final Texture character4_idle0_tex = new Texture(character4_idle0);
    private final Texture character4_idle1_tex = new Texture(character4_idle1);
    private final Texture character4_idle2_tex = new Texture(character4_idle2);
    private final Texture character4_idle3_tex = new Texture(character4_idle3);
    private final Texture character4_idle4_tex = new Texture(character4_idle4);
    private final Texture character4_idle5_tex = new Texture(character4_idle5);
    private final Texture character4_idle6_tex = new Texture(character4_idle6);
    private final Texture character4_idle7_tex = new Texture(character4_idle7);

    private final Animation<Texture> character4_idle_frames = new Animation<>(0.1f, character4_idle0_tex, character4_idle1_tex, character4_idle2_tex, character4_idle3_tex, character4_idle4_tex, character4_idle5_tex, character4_idle6_tex, character4_idle7_tex);

    public Animation<Texture> getcharacter4_idle_frames() {
        return character4_idle_frames;
    }

    public String getcharacter4_idle0() {
        return character4_idle0;
    }




    private final String character5_idle0 = "Character5/Walk_0 #8679.png";
    private final String character5_idle1 = "Character5/Walk_1.png";
    private final String character5_idle2 = "Character5/Walk_2.png";
    private final String character5_idle3 = "Character5/Walk_3.png";
    private final String character5_idle4 = "Character5/Walk_4 #8618.png";
    private final String character5_idle5 = "Character5/Walk_5.png";
    private final String character5_idle6 = "Character5/Walk_6.png";
    private final String character5_idle7 = "Character5/Walk_7 #8788.png";
    private final Texture character5_idle0_tex = new Texture(character5_idle0);
    private final Texture character5_idle1_tex = new Texture(character5_idle1);
    private final Texture character5_idle2_tex = new Texture(character5_idle2);
    private final Texture character5_idle3_tex = new Texture(character5_idle3);
    private final Texture character5_idle4_tex = new Texture(character5_idle4);
    private final Texture character5_idle5_tex = new Texture(character5_idle5);
    private final Texture character5_idle6_tex = new Texture(character5_idle6);
    private final Texture character5_idle7_tex = new Texture(character5_idle7);

    private final Animation<Texture> character5_idle_frames = new Animation<>(0.1f, character5_idle0_tex, character5_idle1_tex, character5_idle2_tex, character5_idle3_tex, character5_idle4_tex, character5_idle5_tex, character5_idle6_tex, character5_idle7_tex);

    public Animation<Texture> getcharacter5_idle_frames() {
        return character5_idle_frames;
    }

    public String getcharacter5_idle0() {
        return character5_idle0;
    }




    public Skin getSkin() {
        return skin;
    }

    public void setSkin(Skin skin) {
        this.skin = skin;
    }

    public ArrayList<String> getAvatars() {
        return avatars;
    }

    private final String weapon0 = "smg/SMGReload_0.png";
    private final String weapon1 = "smg/SMGReload_1.png";
    private final String weapon2 = "smg/SMGReload_2.png";
    private final String weapon3 = "smg/SMGStill.png";
    private final String weapon4 = "smg/T_DualSMGs_Icon.png";
    private final Texture weapon_text0 = new Texture(weapon0);
    private final Texture weapon_text1 = new Texture( weapon1);
    private final Texture weapon_text2 = new Texture(weapon2);
    private final Texture weapon_text3 = new Texture(weapon3);
    private final Texture weapon_text4 = new Texture(weapon4);
    private final Animation<Texture> weapon_frames = new Animation<>(0.1f, weapon_text0, weapon_text1, weapon_text2, weapon_text3, weapon_text4);

    public String getWeapon0() {
        return weapon0;
    }

    public Animation<Texture> getWeapon_frames() {
        return weapon_frames;
    }

    private final String monster0 = "Monster/BrainMonster_0.png";
    private final String monster1 = "Monster/BrainMonster_1.png";
    private final String monster2 = "Monster/BrainMonster_2.png";
    private final String monster3 = "Monster/BrainMonster_3.png";
    private final Texture monster_text0 = new Texture(monster0);
    private final Texture monster_text1 = new Texture(monster1);
    private final Texture monster_text2 = new Texture(monster2);
    private final Texture monster_text3 = new Texture(monster3);
    private final Animation<Texture> monster_frames = new Animation<>(0.1f, monster_text0, monster_text1, monster_text2, monster_text3);

    public String getMonster00() {
        return monster0;
    }



    public Animation<Texture> getMonster_frames() {
        return monster_frames;
    }

    private final String eyeBat0 = "eyeBat/T_EyeBat_0.png";
    private final String eyeBat1 = "eyeBat/T_EyeBat_1.png";
    private final String eyeBat2 = "eyeBat/T_EyeBat_2.png";
    private final String eyeBat3 = "eyeBat/T_EyeBat_3.png";
    private final Texture eyeBat_text0 = new Texture(eyeBat0);
    private final Texture eyeBat_text1 = new Texture(eyeBat1);
    private final Texture eyeBat_text2 = new Texture(eyeBat2);
    private final Texture eyeBat_text3 = new Texture(eyeBat3);
    private final Animation<Texture> eyeBat_frames = new Animation<>(0.1f, eyeBat_text0, eyeBat_text1, eyeBat_text2, eyeBat_text3);

    public String getEyeBat0() {
        return eyeBat0;
    }
    public Animation<Texture> getEyeBat_frames() {
        return eyeBat_frames;
    }

    private final String wingedMonster0 = "WingeMonster/WingedMonster_0.png";
    private final String wingedMonster1 = "WingeMonster/WingedMonster_1.png";
    private final String wingedMonster2 = "WingeMonster/WingedMonster_2.png";
    private final String wingedMonster3 = "WingeMonster/WingedMonster_3.png";
    private final String wingedMonster4 = "WingeMonster/WingedMonster_4.png";
    private final Texture wingedMonster_text0 = new Texture(wingedMonster0);
    private final Texture wingedMonster_text1 = new Texture(wingedMonster1);
    private final Texture wingedMonster_text2 = new Texture(wingedMonster2);
    private final Texture wingedMonster_text3 = new Texture(wingedMonster3);
    private final Texture wingedMonster_text4 = new Texture(wingedMonster4);
    private final Animation<Texture> wingedMonster_frames = new Animation<>(0.1f, wingedMonster_text0, wingedMonster_text1, wingedMonster_text2, wingedMonster_text3,wingedMonster_text4);

    public String getWingedMonster0() {
        return wingedMonster0;
    }
    public Animation<Texture> getwingedMonster_frames() {
        return wingedMonster_frames;
    }


    private final String heart0 = "Heart/HeartAnimation_0.png";
    private final String heart1 = "Heart/HeartAnimation_1.png";
    private final String heart2 = "Heart/HeartAnimation_2.png";
    private final String heart3 = "Heart/HeartAnimation_3.png";
    private final Texture heart_text0 = new Texture(heart0);
    private final Texture heart_text1 = new Texture(heart1);
    private final Texture heart_text2 = new Texture(heart2);
    private final Texture heart_text3 = new Texture(heart3);
    private final Animation<Texture> heart_frames = new Animation<>(0.1f, heart_text0, heart_text1, heart_text2);
    public String getHeart0() {
        return heart0;
    }
    public Animation<Texture> getHeart_frames() {
        return heart_frames;
    }
    public String getHeart3() {
        return heart3;
    }


    private final String enemyDeath0 = "EnemyDeath/DeathFX_0.png";
    private final String enemyDeath1 = "EnemyDeath/DeathFX_1.png";
    private final String enemyDeath2 = "EnemyDeath/DeathFX_2.png";
    private final String enemyDeath3 = "EnemyDeath/DeathFX_3.png";
    private final Texture enemyDeath_text0 = new Texture(enemyDeath0);
    private final Texture enemyDeath_text1 = new Texture(enemyDeath1);
    private final Texture enemyDeath_text2 = new Texture(enemyDeath2);
    private final Texture enemyDeath_text3 = new Texture(enemyDeath3);
    private final Animation<Texture> enemyDeath_frames = new Animation<>(0.1f, enemyDeath_text0, enemyDeath_text1, enemyDeath_text2, enemyDeath_text3);
    public String getenemyDeath0() {
        return enemyDeath0;
    }
    public Animation<Texture> getenemyDeath_frames() {
        return enemyDeath_frames;
    }

    private BitmapFont font = new BitmapFont(Gdx.files.internal("Font/chevyLantern.fnt"));
    public BitmapFont getFont() {
        return font;
    }

    private final String obstacle = "T_TreeMonster_2.png";
    private final Texture obstacle_tex = new Texture(obstacle);

    public String getObstacle() {
        return obstacle;
    }
}

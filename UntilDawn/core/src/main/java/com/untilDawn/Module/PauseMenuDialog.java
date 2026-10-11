package com.untilDawn.Module;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.untilDawn.Module.Enums.Language;

public class PauseMenuDialog extends Dialog {

    public PauseMenuDialog(Skin skin, Runnable onContinue, Runnable givUp, Runnable onExit, Stage stage) {
        super(Language.PAUSE_TITLE.getText(), skin); // "Pause"
        pad(20);

        // Continue Button
        TextButton continueButton = new TextButton(Language.PAUSE_CONTINUE.getText(), skin);
        continueButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
                onContinue.run();
            }
        });

        // Exit Button
        TextButton exitButton = new TextButton(Language.PAUSE_EXIT.getText(), skin);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
                onExit.run();
            }
        });

        // Give Up Button
        TextButton giveUpButton = new TextButton(Language.PAUSE_GIVE_UP.getText(), skin);
        giveUpButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
                givUp.run();
            }
        });
        TextButton cheatButton = new TextButton(Language.CHEAT_TITLE.getText(), skin);
        cheatButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Dialog cheatDialog = new Dialog("", skin) {
                    @Override
                    protected void result(Object object) {
                        if (Boolean.TRUE.equals(object)) {
                            onContinue.run();  // Run only when OK is clicked
                        }
                    }
                };
                cheatDialog.text(Language.HINT_DIALOG_CHEAT.getText());
                cheatDialog.button(Language.HINT_OK.getText(), true); // 'true' will be passed to result()
                cheatDialog.show(stage);
            }
        });

        TextButton abilities = new TextButton(Language.ABILITY.getText(), skin);
        abilities.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Dialog cheatDialog = new Dialog("", skin) {
                    @Override
                    protected void result(Object object) {
                        if (Boolean.TRUE.equals(object)) {
                            onContinue.run();  // Run only when OK is clicked
                        }
                    }
                };
                cheatDialog.text(App.getInstance().getCurrentUser().getPlayer().getGainedAbilities().toString());
                cheatDialog.button(Language.HINT_OK.getText(), true); // 'true' will be passed to result()
                cheatDialog.show(stage);
            }
        });
        TextButton save = new TextButton("Save&Quit", skin);
        CheckBox blackAndWhite = new CheckBox(Language.BLACK_AND_WHITE.getText(), skin);
        blackAndWhite.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                App.getInstance().getCurrentUser().getPlayer().setBlackAndWhite(blackAndWhite.isChecked());

            }
        });
        blackAndWhite.setChecked(App.getInstance().getCurrentUser().getPlayer().isBlackAndWhite());


        button(continueButton);
        button(exitButton);
        button(giveUpButton);
        button(cheatButton);
        button(abilities);
        button(blackAndWhite);
        button(save);

    }
}

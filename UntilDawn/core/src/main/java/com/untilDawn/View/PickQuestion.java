package com.untilDawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.untilDawn.Controller.SignUpMenuController;
import com.untilDawn.Main;
import com.untilDawn.Module.Enums.Question;

public class PickQuestion implements Screen {
    private SignUpMenuController controller;
    private Stage stage;
    private Table table;
    private TextButton firstQuestion;
    private TextButton secondQuestion;
    private TextButton thirdQuestion;
    private TextButton fourthQuestion;
    private TextButton fifthQuestion;
    private TextField Answer;
    private Label label;
    private TextButton backButton;
    private TextButton submitButton;
    private TextButton selectedQuestion = null;

    public PickQuestion(SignUpMenuController controller,Skin skin) {
        this.controller = controller;
        stage = new Stage();
        table = new Table();

        label = new Label("Please select a question", skin);
        firstQuestion = new TextButton(Question.first.getQuestion(), skin);
        secondQuestion = new TextButton(Question.second.getQuestion(), skin);
        thirdQuestion = new TextButton(Question.third.getQuestion(), skin);
        fourthQuestion = new TextButton(Question.fourth.getQuestion(), skin);
        fifthQuestion = new TextButton(Question.fifth.getQuestion(), skin);
        Answer = new TextField("Your answer", skin);
        backButton = new TextButton("Back", skin);
        submitButton = new TextButton("Submit", skin);
        backButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleBackFromQuestioning();
            }
        });

        submitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleQuestioning();
            }
        });

        addToggleListener(firstQuestion);
        addToggleListener(secondQuestion);
        addToggleListener(thirdQuestion);
        addToggleListener(fourthQuestion);
        addToggleListener(fifthQuestion);
        controller.setQuestion(this);

    }



    private void addToggleListener(final TextButton questionButton) {
        questionButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (selectedQuestion == questionButton) {
                    questionButton.setColor(Color.WHITE);
                    selectedQuestion = null;
                } else {
                    if (selectedQuestion != null) {
                        selectedQuestion.setColor(Color.WHITE);
                    }
                    questionButton.setColor(Color.GREEN);
                    selectedQuestion = questionButton;
                }
            }
        });
    }


    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table.setFillParent(true);
        table.center().pad(40); // More generous padding around all

        // Title label
        table.add(label).colspan(2).center().padBottom(30);
        table.row();

        // Question buttons (grouped with spacing)
        TextButton[] questions = { firstQuestion, secondQuestion, thirdQuestion, fourthQuestion, fifthQuestion };
        for (TextButton question : questions) {
            table.add(question).width(1300).height(100).pad(10);
            table.row();
        }

        // Answer label (optional)
        table.add(Answer).colspan(2).padTop(20).center().width(400);
        table.row();

        // Submit and Back buttons on the same row
        Table buttonRow = new Table();
        buttonRow.add(submitButton).width(200).height(80).padRight(20);
        buttonRow.add(backButton).width(200).height(80);
        table.add(buttonRow).colspan(2).padTop(30).center();

        stage.addActor(table);
    }


    @Override
    public void render(float v) {
        ScreenUtils.clear(0,0,0,1);
        Main.getBatch().begin();
        Main.getBatch().end();
        stage.act(Math.min( Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public SignUpMenuController getController() {
        return controller;
    }

    public Stage getStage() {
        return stage;
    }

    public Table getTable() {
        return table;
    }

    public TextButton getFirstQuestion() {
        return firstQuestion;
    }

    public TextButton getSecondQuestion() {
        return secondQuestion;
    }

    public TextButton getThirdQuestion() {
        return thirdQuestion;
    }

    public TextButton getFourthQuestion() {
        return fourthQuestion;
    }

    public TextButton getFifthQuestion() {
        return fifthQuestion;
    }

    public TextField getAnswer() {
        return Answer;
    }

    public Label getLabel() {
        return label;
    }

    public TextButton getBackButton() {
        return backButton;
    }

    public TextButton getSubmitButton() {
        return submitButton;
    }

    public TextButton getSelectedQuestion() {
        return selectedQuestion;
    }
}

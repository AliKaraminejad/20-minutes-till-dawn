package com.untilDawn;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.ScreenUtils;
import com.untilDawn.Controller.SignUpMenuController;
import com.untilDawn.Module.App;
import com.untilDawn.Module.FileChooserBridge;
import com.untilDawn.Module.GameAssetManager;
import com.untilDawn.View.GoogleGame.GoogleView;
import com.untilDawn.View.SignUpMenu;





/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class    Main extends Game {
    private static Main main;
    private static SpriteBatch batch;
    private Texture image;
    private Music backgroundMusic;

    private FileChooserBridge fileChooserBridge;

    public Main(FileChooserBridge fileChooserBridge) {
        this.fileChooserBridge = fileChooserBridge;
    }

    public FileChooserBridge getFileChooserBridge() {
        return fileChooserBridge;
    }



    @Override
    public void create() {
//        long windowHandle = ((Lwjgl3Graphics) Gdx.graphics).getWindow().getWindowHandle();
//
//        GLFW.glfwSetDropCallback(windowHandle, new GLFWDropCallback() {
//            @Override
//            public void invoke(long window, int count, long names) {
//                for (int i = 0; i < count; i++) {
//                    String droppedPath = GLFWDropCallback.getName(names, i);
//                    File file = new File(droppedPath);
//                    if (file.exists() && isImageFile(file)) {
//                        // Your file handling code here
//                    }
//                }
//            }
//        });
        main = this;
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");
        main.setScreen((new SignUpMenu(new SignUpMenuController(), GameAssetManager.getInstance().getSkin())));
        this.backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("4_5909019549184822982_1.mp3"));
        Pixmap pixmap = new Pixmap(Gdx.files.internal("T_Cursor.png"));
        Cursor customCursor = Gdx.graphics.newCursor(pixmap, 0, 0);
        Gdx.graphics.setCursor(customCursor);
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.5f); // Optional: control volume
        backgroundMusic.play();
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }

    public static SpriteBatch getBatch() {
        return batch;
    }

    public static Main getMain() {
        return main;
    }

    public void setBackgroundMusic(Music newMusic) {
        if (this.backgroundMusic != null) {
            this.backgroundMusic.stop(); // Stop the currently playing music
            this.backgroundMusic.dispose(); // Optional: clean up if no longer used
        }
        this.backgroundMusic = newMusic;
        this.backgroundMusic.setLooping(true); // Optional: make sure it loops
        this.backgroundMusic.setVolume(0.5f);  // Optional: set default volume
        this.backgroundMusic.play();          // Start the new music
    }


    public Music getBackgroundMusic() {
        return backgroundMusic;
    }
}

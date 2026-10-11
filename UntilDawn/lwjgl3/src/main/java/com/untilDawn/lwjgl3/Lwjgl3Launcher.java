package com.untilDawn.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.untilDawn.Main;
import com.untilDawn.Module.App;
import com.untilDawn.Module.FileChooserBridge;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWDropCallback;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static com.badlogic.gdx.Gdx.app;

/** Launches the desktop (LWJGL3) application. */
public class Lwjgl3Launcher {
    public static void main(String[] args) {
        if (StartupHelper.startNewJvmIfRequired()) return; // This handles macOS support and helps on Windows.

        FileChooserBridge fileChooserBridge = new FileChooserBridge() {
            @Override
            public void pickFileForAvatar() {
                File file = FilePicker.pickFile(); // Or JFileChooser
                if (file != null && file.exists()) {
                    try {
                        // Store avatar in a safe, accessible path (optional)
                        File destFolder = new File("avatars"); // Make sure this exists
                        if (!destFolder.exists()) destFolder.mkdirs();

                        File dest = new File(destFolder, file.getName());
                        Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);

                        // ✅ Set path in current user (MUST be absolute or local)
                        App.getInstance().getCurrentUser().setAvatarPath(dest.getAbsolutePath());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        };

        new Lwjgl3Application(new Main(fileChooserBridge), getDefaultConfiguration());


//        long windowHandle = app.getWindow().getWindowHandle();

//        GLFW.glfwSetDropCallback(windowHandle, new GLFWDropCallback() {
//            @Override
//            public void invoke(long window, int count, long names) {
//                for (int i = 0; i < count; i++) {
//                    String droppedPath = getName(names, i);
//                    File file = new File(droppedPath);
//                    if (file.exists() && isImageFile(file)) {
//                        try {
//                            File destFolder = new File("avatars");
//                            if (!destFolder.exists()) destFolder.mkdirs();
//
//                            File dest = new File(destFolder, file.getName());
//                            Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
//
//                            App.getInstance().getCurrentUser().setAvatarPath(dest.getAbsolutePath());
//                            System.out.println("Drag-and-drop avatar set: " + dest.getAbsolutePath());
//
//                            // Optional: trigger UI refresh if needed
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//        });



    }

    private static boolean isImageFile(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith(".jpeg");
    }




//    private static Lwjgl3Application createApplication() {
//        return new Lwjgl3Application(new Main(), getDefaultConfiguration());
//    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("TillDawn");
        //// Vsync limits the frames per second to what your hardware can display, and helps eliminate
        //// screen tearing. This setting doesn't always work on Linux, so the line after is a safeguard.
        configuration.useVsync(true);
        //// Limits FPS to the refresh rate of the currently active monitor, plus 1 to try to match fractional
        //// refresh rates. The Vsync setting above should limit the actual FPS to match the monitor.
        configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate + 1);
        //// If you remove the above line and set Vsync to false, you can get unlimited FPS, which can be
        //// useful for testing performance, but can also be very stressful to some hardware.
        //// You may also need to configure GPU drivers to fully disable Vsync; this can cause screen tearing.

        configuration.setWindowedMode(1920, 1080);
        //// You can change these files; they are in lwjgl3/src/main/resources/ .
        //// They can also be loaded from the root of assets/ .
        configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");
        return configuration;
    }
}

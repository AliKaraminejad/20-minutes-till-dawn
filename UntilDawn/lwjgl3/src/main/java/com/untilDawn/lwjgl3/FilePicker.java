package com.untilDawn.lwjgl3;

import javax.swing.*;
import java.io.File;

public class FilePicker {
    public static File pickFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Select an avatar image");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = chooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        }
        return null;
    }
}


package org.example;

import javafx.application.Platform;
import javafx.stage.Stage;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GlobalShortcutService implements NativeKeyListener {

    private final Stage stage;

    private boolean optionPressed = false;
    private boolean commandPressed = false;

    public GlobalShortcutService(Stage stage) {
        this.stage = stage;
    }

    public void start() {
        try {
            Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
            logger.setLevel(Level.OFF);
            logger.setUseParentHandlers(false);

            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(this);

            System.out.println("Global shortcut registered: Option + Command + V");

        } catch (NativeHookException e) {
            System.err.println("Failed to register global shortcut");
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            GlobalScreen.removeNativeKeyListener(this);
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_ALT) {
            optionPressed = true;
        }

        if (e.getKeyCode() == NativeKeyEvent.VC_META) {
            commandPressed = true;
        }

        if (optionPressed && commandPressed && e.getKeyCode() == NativeKeyEvent.VC_V) {
            Platform.runLater(() -> {
                stage.show();
                stage.toFront();
                stage.requestFocus();
            });
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_ALT) {
            optionPressed = false;
        }

        if (e.getKeyCode() == NativeKeyEvent.VC_META) {
            commandPressed = false;
        }
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
        // Not used
    }
}
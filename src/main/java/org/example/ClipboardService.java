package org.example;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.StringSelection;


public class ClipboardService {
    /*
    1. Get system clipboard
    2. Get clipboard content
    3. Check if content is text
    4. Return text
    5. If not text, return empty string
    */
    public String getCurrentText() {
        try {
            // Get the system clipboard
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

            // Get the clipboard content
            Transferable content = clipboard.getContents(null);

            // Check if the content is text
            if (content != null && content.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                // Return the text from the clipboard
                return (String) content.getTransferData(DataFlavor.stringFlavor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public void setCurrentText(String text) {

        // Wrap the text so it can be stored in the system clipboard
        StringSelection selection = new StringSelection(text);
        // Get the macOS clipboard
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        // Update clipboard with the provided text
        clipboard.setContents(selection, null);
    }
}
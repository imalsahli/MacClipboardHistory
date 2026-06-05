package org.example;

public class ClipboardItem {

    private String text;
    private boolean pinned;

    public ClipboardItem(String text) {
        this.text = text;
        this.pinned = false;

    }

    public ClipboardItem(String text, boolean pinned) {
        this.text = text;
        this.pinned = pinned;
    }

    public String getText() {
        return text;
    }

    public boolean isPinned() {
        return pinned;
    }

    public void togglePinned() {
        this.pinned = !this.pinned;
    }

    public String getDisplayText() {
        if (pinned) {
            return "📌 " + text;
        }
        return text;
    }
}

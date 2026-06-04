package org.example;

public class ClipboardItem {

    private String text;
    private boolean pinned;

    public ClipboardItem(String text){
        this.text = text;
        this.pinned = false;

    }

    public String getText() {
        return text;
    }

    public boolean isPinned() {
        return pinned;
    }
}

package org.example;

import java.util.ArrayList;
import java.util.List;


public class ClipboardHistory {

    private List<ClipboardItem> items = new ArrayList<>();


    // addText(text)
    public boolean addText(String text) {
        for (ClipboardItem item : items) {
            if (item.getText().equals(text)) {
                return false;
            }
        }

        ClipboardItem item = new ClipboardItem(text);
        items.add(item);
        return true;
    }

    // getAllItems()
    public List<ClipboardItem> getAllItems() {
        return items;

    }

    // clear all
    public void clearAll() {
        items.removeIf(item -> !item.isPinned());
    }

    // count it
    public int getCount() {
        return items.size();
    }

    // remove only one record
    public void removeText(String text) {
        items.removeIf(item -> item.getText().equals(text));
    }

    public void addItem(ClipboardItem item) {
        items.add(item);
    }


    public void togglePinned(String text) {
        for (ClipboardItem item : items) {
            if (item.getText().equals(text)) {
                item.togglePinned();
                items.sort((a, b) -> Boolean.compare(b.isPinned(), a.isPinned()));
                return;
            }
        }
    }

}

package org.example;
import java.util.ArrayList;

public class ClipboardHistory {

    private ArrayList<ClipboardItem> items = new ArrayList<>();

    public void addText(String text){
        ClipboardItem item = new ClipboardItem(text);
        items.add(item);
    }


    public ArrayList<ClipboardItem> getAllItems(){
        return items;

    }

}

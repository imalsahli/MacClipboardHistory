package org.example;
import java.util.ArrayList;

public class ClipboardHistory {

    private ArrayList<ClipboardItem> items = new ArrayList<>();

    // addText(text)
 
    public void addText(String text){
        ClipboardItem item = new ClipboardItem(text);
        items.add(item);
    }

    // getAllItems()   
    public ArrayList<ClipboardItem> getAllItems(){
        return items;

    }

    public void clearAll(){
        items.clear();
    }

}

package org.example;

public class ClipboardApp {

    public static void main(String[] args) {

        ClipboardService clipboardService = new ClipboardService();
        String text = clipboardService.getCurrentText();

        System.out.println(text);

        

    }
}

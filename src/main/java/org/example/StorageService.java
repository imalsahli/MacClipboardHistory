package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class StorageService {


    public void save(ClipboardHistory history) {

        try {

            FileWriter writer = new FileWriter("clipboard-history.txt");
            for (ClipboardItem item : history.getAllItems()) {
                writer.write(item.isPinned() + "|" + item.getText() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ClipboardHistory load() {
        ClipboardHistory history = new ClipboardHistory();

        File file = new File("clipboard-history.txt");

        if (!file.exists()) {
            return history;
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty()) {
                    String[] parts = line.split("\\|", 2);

                    if (parts.length == 2) {
                        boolean pinned = Boolean.parseBoolean(parts[0]);
                        String text = parts[1];

                        history.addItem(new ClipboardItem(text, pinned));
                    } else {
                        history.addText(line);
                    }
                }
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return history;
    }
}

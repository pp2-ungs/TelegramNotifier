package telegram;

import com.google.gson.Gson;
import core.Settings;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TelegramFinder {
    
    public static Map<String, Number> getTelegramIDMap() throws IOException {
        Gson gson = new Gson();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(Settings.RESOURCES + "Telegram.json"))) {
            return gson.fromJson(reader, HashMap.class);
        }
    }
    
}

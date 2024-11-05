package telegram;

import java.io.IOException;
import observer.Observer;
import java.util.Map;

public class TelegramNotifier implements Observer {

    private Map<String, Number> personIDs;

    public TelegramNotifier() {
        try {
            personIDs = TelegramFinder.getTelegramIDMap();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void update(Object event) {
        String taskDescription = (String) ((Map) event).get("Task");
        String personName = (String) ((Map) event).get("Name");

        Long telegramID = personIDs.get(personName).longValue();

        String msg = "Hola " + personName + "!\nTenés una nueva tarea!\nSe te asignó la tarea: " + taskDescription + "\nSaludos!";

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    new Telegram().sendMessageToUser(telegramID, msg);
                } catch (Exception ex) {
                    System.out.println("?telegram not working");
                }
            }
        }).start();
        System.out.println("[debugging] TelegramNotifier update: \n" + event);
    }
}

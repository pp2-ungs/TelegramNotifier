package main;

import annotation.Notificator;
import java.io.IOException;
import observer.Observer;
import java.util.Map;

@Notificator
public class TelegramNotificator implements Observer {

    private Map<String, Number> membersIDs;

    public TelegramNotificator() {
        try {
            membersIDs = TelegramFinder.getTelegramIDMap();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void update(Object event) {

        //\begin{FIXME}
        String taskDescription = (String) ((Map) event).get("Task");
        String memberName = (String) ((Map) event).get("Name");
        //\end{FIXME}

        Long memberTelegramId = membersIDs.get(memberName).longValue();

        String msg = "Hola " + memberName + "!\nTenés una nueva tarea!\nSe te asignó la tarea: " + taskDescription + "\nSaludos!";

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    new Telegram().sendMessageToUser(memberTelegramId, msg);
                } catch (Exception ex) {
                    System.out.println("?telegram not working");
                }
            }
        }).start();
        System.out.println("[debuggin] TelegramNotificator update: \n" + event);
    }

}

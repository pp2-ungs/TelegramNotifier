package main;

import core.Observer;
import java.util.Map;

public class TelegramNotificator implements Observer {

    @Override
    public void update(Object event) {

        //\begin{FIXME}
        var memberTelegramId = Integer.parseInt((String) ((Map) event).get("TelegramId"));
        var taskDescription = (String) ((Map) event).get("Task");
        var memberName = (String) ((Map) event).get("Name");
        //\end{FIXME}

        StringBuilder msg = new StringBuilder();
        msg.append("Hola ");
        msg.append(memberName);
        msg.append("!\nTenés una nueva tarea!\nSe te asignó la tarea: ");
        msg.append(taskDescription);
        msg.append("\nSaludos!");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    new Telegram().sendMessageToUser(memberTelegramId, msg.toString());
                } catch (Exception ex) {
                    System.out.println("?telegram not working");
                }
            }
        }).start();
        System.out.println("[debuggin] TelegramNotificator update: \n" + event);
    }

}

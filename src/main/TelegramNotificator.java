package main;

import core.NotificationDTO;
import core.Notificator;
import java.util.HashMap;
import java.util.Map;
import org.telegram.telegrambots.meta.api.objects.MemberStatus;

public class TelegramNotificator implements Notificator {

    @Override
    public void notify(NotificationDTO notificationDTO) {
        StringBuilder msg = new StringBuilder();
        msg.append("Hola ");
        msg.append(notificationDTO.getMember());
        msg.append("!\nTenés una nueva tarea!\nSe te asignó la tarea: ");
        msg.append(notificationDTO.getTask());
        msg.append("\nSaludos!");
        
        //FIXME: Esto no debería estar acá
        Map<String, Integer> members = new HashMap();
        members.put("Gonzalo Javier López", 698039957);
        members.put("Ximena Ebertz", 850210514);
        members.put("Hernán Rondelli", 70417546);
        
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    new Telegram().sendMessageToUser(members.get(notificationDTO.getMember().getName()), msg.toString());
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        }).start();
        System.out.println("Soy TelegramNotificator y me notificaron: \n" + notificationDTO.getMessage());
    }

    @Override
    public void update(NotificationDTO notificationDTO) {
        notify(notificationDTO);
    }
    
}

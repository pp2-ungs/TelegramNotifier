package main;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@SuppressWarnings("deprecation")
public class Telegram extends TelegramLongPollingBot {

    private final String botUsername;
    private final String botToken;

    public Telegram() {
        botUsername = "TASkOcupadoBot";
        botToken = "8164629182:AAEq3BStt8YRgxU3QdJiqs8SwGuiNk11OfI";
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    public void sendMessageToUser(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            System.out.println("?telegram bot not working");
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
    }

}

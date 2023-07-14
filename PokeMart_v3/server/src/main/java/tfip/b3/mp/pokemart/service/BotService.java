package tfip.b3.mp.pokemart.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.BotSession;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.util.Random;

@Service
public class BotService extends TelegramLongPollingBot {

    @Value("${telegram.bot.username}")
    private String botUsername;
    @Value("${telegram.bot.token}")
    private String botToken;

    private BotSession session = null;

    @PostConstruct
    public void registerBot() {
        try {
            System.out.println(">> [INFO] BotSession Instance: " + this.session);
            System.out.println(">> [INFO] SPRING LIFECYCLE POSTCONSTRUCT Pokeporybot");
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            this.session = botsApi.registerBot(this);
            System.out.println(">> [INFO] BotSession Running: " + this.session.isRunning());
            System.out.println(">> [INFO] BotSession Instance: " + this.session);
        } catch (TelegramApiException teleErr) {
            System.out.println(teleErr);
        }
    }

    @PreDestroy
    public void destroy() {
        System.out.println(">> [INFO] SPRING LIFECYCLE PREDESTROY Pokeporybot");
        System.out.println(">> [INFO] BotSession Running: " + this.session.isRunning());
        System.out.println(">> [INFO] Terminating BotSession");
        this.session.stop();
        System.out.println(">> [INFO] BotSession Running: " + this.session.isRunning());
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        var msg = update.getMessage();
        String txt = msg.getText();
        var user = msg.getFrom();
        Long id = user.getId();
        System.out.println(">> [BOT]: " + user.getUserName() + "|" + (id + ": " + txt));
        if (msg.hasLocation())
            sendMsg(id,msg.getLocation().toString());
        String resp = (updateHandler(txt));
        sendMsg(id, resp);
    }

    public String updateHandler(String txt) {
        if (txt.charAt(0) == ('/')) {
            switch (txt) {
                case "/start":
                    return("Welcome to the Pokemart! How may Porygon help you today?");
                case "/help":
                    return("I cannot help you la bro");
                case "/register":
                    return("You requested to register your telegram account with Pokemart");
                case "/orders":
                    return("You requested to check your order history");
                case "/delivered":
                    return("You requested to mark an order as delivered");
                case "/outlets":
                    return("You requested to know our outlet locations");
                default:
                    return("Sorry I do not understand that command");
            }
            // case "/settings":
        }
        return beepboop();
    }

    public void sendMsg(Long userID, String text) {
        SendMessage messageUser = SendMessage.builder().chatId(userID.toString()).text(text).build();
        try {
            execute(messageUser);
        } catch (TelegramApiException teleErr) {
            System.out.println(teleErr);
        }
    }

    public String beepboop(){
        Random rand = new Random();
        int num = rand.nextInt(10)+1;
        String resp = "";
        for(int i=0;i<num;i++){
            if((rand.nextInt(2)+1)%2==0){
                resp += "beep ";
            } else {
                resp += "boop ";
            }
        }
        return resp;
    }

}

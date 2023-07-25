package tfip.b3.mp.pokemart.service;

import org.springframework.beans.factory.annotation.Autowired;
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
import tfip.b3.mp.pokemart.model.OrderSummaryDAO;
import tfip.b3.mp.pokemart.repository.OrderRepository;
import tfip.b3.mp.pokemart.repository.TTLRepository;
import tfip.b3.mp.pokemart.repository.TeleRepository;
import tfip.b3.mp.pokemart.repository.UserRepository;
import tfip.b3.mp.pokemart.utils.GeneralUtils;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class BotService extends TelegramLongPollingBot {

    @Value("${telegram.bot.username}")
    private String botUsername;
    @Value("${telegram.bot.token}")
    private String botToken;

    private BotSession session = null;

    @Autowired
    private TeleRepository teleRepo;
    @Autowired
    private TTLRepository TTLRepo;
    @Autowired
    private EmailService emailSvc;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private OrderRepository orderRepo;

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
            sendMsg(id, msg.getLocation().toString());
        updateHandler(id, txt);
    }

    public void updateHandler(Long id, String txt) {
        if (txt.charAt(0) == ('/')) {
            switch (txt) {
                case "/start":
                    sendMsg(id, "Welcome to the Pokemart! How may Porygon help you today? Beepbeep!");
                    break;
                case "/help":
                    sendMsg(id, "Boooop! I can only help those who help themselves");
                    break;
                case "/register":
                    if (checkRegistered(id))
                        return;
                    sendMsg(id, "Beep! You requested to register your telegram account with Pokemart");
                    sendMsg(id, """
                            Please key in user ID and email in this format, boop!
                            AUT <yourUserID> <yourEmail>
                            Example: AUT u12345678 ashKetchum@pokemail.com
                            """);
                    break;
                case "/orders":
                    if (!authFilter(id)) {
                        return;
                    }
                    sendMsg(id, "Boop! You requested to check your order history");
                    getOrderSummaries(id);
                    break;
                case "/delivered":
                    if (!authFilter(id)) {
                        return;
                    }
                    sendMsg(id, "Boop! You requested to mark an order as delivered");
                    sendMsg(id, """
                            Please key in the order number you want to mark as delivered:
                            DEL <yourOrderNumber>
                            Example: DEL oA1b23c4d
                            """);
                    break;
                case "/outlets":
                    sendMsg(id, "Beep! You requested to know our outlet locations");
                    sendMsg(id, "I haven't learnt how to do this yet! Tell my developer to level me up! Beepbeepbeep!");
                    break;
                default:
                    sendMsg(id, "BeepBoop! Sorry I do not understand that command");
                    break;
            }
            // case "/settings":
            return;
        }
        if (txt.length() > 4) {
            System.out.println(">> [INFO] Telerequest CMD:" + txt);
            String inputs = txt.substring(0, 4);
            if (inputs.equals("AUT ")) {
                System.out.println(">> [INFO] Telerequest CMD: AUT");
                if (checkRegistered(id))
                    return;
                createAuth(id, txt);
                return;
            }
            if (inputs.equals("OTP ")) {
                System.out.println(">> [INFO] Telerequest CMD: OTP");
                if (checkRegistered(id))
                    return;
                checkAuth(id, txt);
                return;
            }
            if (inputs.equals("DEL ")) {
                System.out.println(">> [INFO] Telerequest CMD: DEL");
                teleMarkDelivered(id, txt);
                return;
            }
        }
        beepboop(id);

    }

    public void sendMsg(Long userID, String text) {
        SendMessage messageUser = SendMessage.builder().chatId(userID.toString()).text(text).build();
        try {
            execute(messageUser);
        } catch (TelegramApiException teleErr) {
            System.out.println(teleErr);
        }
    }

    public void beepboop(Long id) {
        Random rand = new Random();
        int num = rand.nextInt(10) + 1;
        String resp = "";
        for (int i = 0; i < num; i++) {
            if ((rand.nextInt(2) + 1) % 2 == 0) {
                resp += "beep ";
            } else {
                resp += "boop ";
            }
        }
        sendMsg(id, resp + "!");
    }

    public boolean checkRegistered(Long id) {
        System.out.println(">> [INFO] Telerequest Check Registuer:" + id.toString());
        if (teleRepo.checkTelegramID(id.toString())) {
            sendMsg(id, "Boooop! You already registered this telegram account!");
            return true;
        } else {
            return false;
        }
    }

    public boolean authFilter(Long id) {
        boolean valid = teleRepo.checkTelegramID(id.toString());
        if (!valid)
            sendMsg(id, "Boooop! You have not registered this telegram account!");
        return valid;
    }

    public boolean createOTP(Long id) {
        if (TTLRepo.existsTTL(id.toString())) {
            sendMsg(id, """
                    Boop! Please key in the OTP already sent to your email in this format:
                    OTP <your6CharacterOTP>
                    Example: OTP 123abc
                    """);
            return false;
        } else {
            return true;
        }
    }

    public boolean createAuth(Long id, String txt) {
        String[] teleReq = txt.split(" ", 0);
        System.out.println(">> [INFO] Telerequest:" + teleReq[1] + "," + teleReq[2]);
        if (!createOTP(id))
            return false;
        if (userRepo.checkEmail(teleReq[1], teleReq[2])) {
            String OTP = GeneralUtils.generateUUID(6);
            TTLRepo.newTTL(id.toString(), OTP);
            emailSvc.sendOTPEmail(teleReq[2], OTP);
            teleRepo.registerTelegramID(teleReq[1], id.toString());
            sendMsg(id, """
                    Boop! Please check your email and key in the provided OTP in this format:
                    OTP <your6CharacterOTP>
                    Example: OTP 123abc
                    """);
            return true;
        } else {
            sendMsg(id, "You have keyed in an invalid userID or email!");
            return false;
        }
    }

    public boolean checkAuth(Long id, String txt) {
        String[] teleReq = txt.split(" ", 0);
        String otp = teleReq[1];
        Optional<String> value = TTLRepo.getValue(id.toString());
        if (value.isEmpty()) {
            sendMsg(id, """
                    Please key in user ID and email in this format, boop!
                    AUT <yourUserID> <yourEmail>
                    Example: AUT u12345678 ashKetchum@pokemail.com
                    """);
            return false;
        } else if (value.get().equals(otp)) {
            teleRepo.authenticateByTelegramID(id.toString(), true);
            sendMsg(id, "Beepbeep! Your Telegram account is registered!");
            return true;
        } else {
            sendMsg(id, "Boooop! You have keyed in an invalid or expired OTP!");
            return false;
        }

    }

    public boolean teleMarkDelivered(Long id, String txt) {
        String[] teleReq = txt.split(" ", 0);
        String orderId = teleReq[1];
        String customerID = teleRepo.getUserIDfromTelegramID(id.toString());
        if (orderId.length() != 9 && orderId.charAt(0) == 'o') {
            sendMsg(id, "Boooop! You have keyed in an invalid order ID");
            return false;
        }
        if (!orderRepo.getCustomerIDbyOrderID(orderId).equals(customerID)) {
            sendMsg(id, "Boooop! Order " + orderId + " does not belong to " + customerID);
            return false;
        }
        if (orderRepo.markOrderDelivered(orderId)) {
            sendMsg(id, "Order " + orderId + " marked as delivered. Beep!");
            return true;
        } else {
            sendMsg(id, "Beeep! This order does not exist.");
            return false;
        }
    }

    public void getOrderSummaries(Long id) {
        String customerID = teleRepo.getUserIDfromTelegramID(id.toString());
        System.out.println(">> [BOT] Getting Orders of " + customerID);

        List<OrderSummaryDAO> orderSummaries = orderRepo.getOrderSummaryByCustomerID(customerID);
        if(orderSummaries.size()<1){
            sendMsg(id,"You have no orders");
            return;
        }

        String msg = "Your Orders: \n";
        int count = 1;
        for (OrderSummaryDAO summary : orderSummaries) {
            msg += count + ". [ ";
            msg += summary.getOrderID() + " | ";
            msg += summary.getOrderDate() + " | Total Cost:$";
            msg += summary.getTotal() + " | ";
            if (summary.isDelivered()) {
                msg += "Delivered";
            } else {
                msg += "Pending Del";
            }
            msg += " ] \n";
            count++;
        }
        sendMsg(id, msg);

    }

    public boolean helpMePls(Long id) {
        return true;
    }
}

package tfip.b3.mp.pokemart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import tfip.b3.mp.pokemart.bot.Pokeporybot;

@SpringBootApplication
public class PokemartApplication {

	public static void main(String[] args) throws TelegramApiException {
		SpringApplication.run(PokemartApplication.class, args);

		  TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
		  botsApi.registerBot(new Pokeporybot());
	}

}

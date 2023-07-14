package tfip.b3.mp.pokemart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@SpringBootApplication
public class PokemartApplication {

	public static void main(String[] args) throws TelegramApiException {
		SpringApplication.run(PokemartApplication.class, args);
	}

}

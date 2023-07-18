package tfip.b3.mp.pokemart.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import static tfip.b3.mp.pokemart.repository.TeleQueries.*;

@Repository
public class TeleRepository {

    @Autowired
    JdbcTemplate jTemplate;

    public boolean checkTelegramID(String telegramID) throws DataAccessException {
        return jTemplate.queryForObject(EXISTS_BY_TELEGRAM_ID, Boolean.class, telegramID);
    }

    public String getUserIDfromTelegramID(String telegramID) throws DataAccessException {
        return jTemplate.queryForObject(SELECT_USER_ID_BY_TELEGRAM_ID, String.class, telegramID);
    }

    public int registerTelegramID(String userID, String telegramID) throws DataAccessException {
        return jTemplate.update(INSERT_TELEGRAM_ID,userID,telegramID,false);
    }

    public int authenticateByTelegramID(String telegramID, boolean authstatus) throws DataAccessException {
        return jTemplate.update(UPDATE_AUTH_BY_TELEGRAM_ID,authstatus,telegramID);
    }

    public int deleteUnauthenticated() throws DataAccessException {
        return jTemplate.update(DELETE_UNAUTHENTICATED);
    }


    //LOGCHATS?

}

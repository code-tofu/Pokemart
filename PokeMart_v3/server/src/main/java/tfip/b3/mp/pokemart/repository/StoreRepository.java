package tfip.b3.mp.pokemart.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jakarta.json.JsonObject;
import tfip.b3.mp.pokemart.model.StoreDAO;

import static tfip.b3.mp.pokemart.repository.StoreQueries.*;

import java.util.List;

@Repository
public class StoreRepository {

    @Autowired
    JdbcTemplate jTemplate;

    public void insertStore(JsonObject jsonOb) throws DataAccessException{
        // store_address, store_name, store_phone, store_lat,store_long
        jTemplate.update(INSERT_NEW_STORE,
            jsonOb.getString("address"),
            jsonOb.getString("displayText"),
            jsonOb.getString("phone"),
            Double.parseDouble(jsonOb.getJsonObject("coordinate").getString("lat")),
            Double.parseDouble(jsonOb.getJsonObject("coordinate").getString("long"))
        );
    }

    public List<StoreDAO> getAllStores() throws DataAccessException{
        return jTemplate.query(SELECT_ALL_STORES,new StoreDAOMapper());
    }

}

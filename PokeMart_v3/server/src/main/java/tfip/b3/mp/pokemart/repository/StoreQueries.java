package tfip.b3.mp.pokemart.repository;

public class StoreQueries {

  public static final String MAX_STORES = "200";

  public static final String INSERT_NEW_STORE = """
      INSERT INTO stores (store_address, store_name, store_phone, store_lat,store_long) VALUES (?, ?, ?, ?, ?)
      """;

  public static final String SELECT_ALL_STORES = "SELECT * from stores limit " + MAX_STORES;
}

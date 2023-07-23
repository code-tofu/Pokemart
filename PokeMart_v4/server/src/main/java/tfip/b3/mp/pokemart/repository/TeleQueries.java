package tfip.b3.mp.pokemart.repository;

public class TeleQueries {

    public static final String EXISTS_BY_TELEGRAM_ID = """
    SELECT EXISTS(SELECT * FROM user_telegram WHERE telegram_id = ? AND authenticated = TRUE)
    """;

    public static final String SELECT_USER_ID_BY_TELEGRAM_ID = """
    SELECT user_id FROM user_telegram WHERE telegram_id = ? AND authenticated = TRUE
    """;

    public static final String EXISTS_BY_USERID = """
    SELECT EXISTS(SELECT * FROM user_telegram WHERE user_id= ? AND authenticated = TRUE)
    """;

    public static final String INSERT_TELEGRAM_ID="""
    INSERT INTO user_telegram VALUES (?,?,?)     
    """;

    public static final String UPDATE_AUTH_BY_TELEGRAM_ID="""
    UPDATE user_telegram SET authenticated = ? WHERE telegram_id = ?
    """;

    public static final String DELETE_UNAUTHENTICATED="""
    DELETE FROM user_telegram WHERE authenticated = false
    """;


    
}

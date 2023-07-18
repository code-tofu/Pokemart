package tfip.b3.mp.pokemart.repository;

public class UserQueries {

    public static final String SELECT_USERDETAILS_BY_USERNAME = """
        SELECT * FROM user_details WHERE username= ?
        """;
    
    public static final String SELECT_USERDETAILS_BY_USERID = """
        SELECT * FROM user_details WHERE user_id= ?
        """;

    public static final String SELECT_USERPROFILE_BY_EMAIL = """
        SELECT * FROM user_profiles WHERE customer_email= ?
        """;

    public static final String SELECT_USERPROFILE_BY_USERID = """
        SELECT * FROM user_profiles WHERE user_id= ?
        """;

    public static final String INSERT_NEW_USER_DETAILS = """
        INSERT INTO user_details (user_id, username, password, enabled, account_non_expired, credentials_non_expired, account_non_locked,role) VALUES (?,?,?,?,?,?,?,?)
        """;

    public static final String INSERT_NEW_USER_PROFILE = """
        INSERT INTO user_profiles (user_id,customer_name,customer_email,customer_phone,shipping_address,birthdate,gender,member_level,member_since) VALUES (?,?,?,?,?,?,?,?,?)
        """;
    
    public static final String EXISTS_USER_BY_USERID = """
        SELECT EXISTS(SELECT * from user_details WHERE user_id= ? )
        """;

    public static final String EXIST_BY_EMAIL_AND_ID = """
        SELECT EXISTS(SELECT * FROM user_profiles WHERE user_id = ? AND customer_email = ?)
    """;

}

    
package constants;

import io.github.cdimascio.dotenv.Dotenv;

public class Constants {

    private static final Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
    
    /**
     * Base URLs of projects:
     */
    public static String BASE_URL_EPICVIN = dotenv.get("BASE_URL_EPICVIN");
    public static String BASE_URL_EPICVIN_PRECHECK = dotenv.get("BASE_URL_EPICVIN_PRECHECK");
    public static String BASE_URL_DEV = dotenv.get("BASE_URL_DEV");
    public static String EPICVIN_TRUSTPILOT = dotenv.get("EPICVIN_TRUSTPILOT");

    /**
     * Internal test cleanup endpoints (loaded from .env).
     */
    public static String REPORTS_EPICVIN = dotenv.get("REPORTS_EPICVIN");
    public static String PAYMENT_METHOD_EPICVIN = dotenv.get("PAYMENT_METHOD_EPICVIN");
    public static String ADD_DISPUTE_EPICVIN = dotenv.get("ADD_DISPUTE_EPICVIN");
    public static String CLOSE_DISPUTE_EPICVIN = dotenv.get("CLOSE_DISPUTE_EPICVIN");
    public static String ADD_FUNDS = dotenv.get("ADD_FUNDS");
    public static String TRIAL_SUB_EPICVIN = dotenv.get("TRIAL_SUB_EPICVIN");
    public static String FULL_SUB_EPICVIN = dotenv.get("FULL_SUB_EPICVIN");
    public static String FINGERPRINTS_EPICVIN = dotenv.get("FINGERPRINTS_EPICVIN");

    /**
     * Test VINs and License plates:
     */
    public static String VALID_VIN = dotenv.get("VALID_VIN");
    public static String VALID_VIN_IN_LOWERCASE = dotenv.get("VALID_VIN_IN_LOWERCASE");
    public static String VIN_MORE_THAN_17SYMBOLS = dotenv.get("VIN_MORE_THAN_17SYMBOLS");
    public static String INVALID_VIN = dotenv.get("INVALID_VIN");
    public static String LESS_FIVE_SYMBOLS_VIN = dotenv.get("LESS_FIVE_SYMBOLS_VIN");
    public static String EMPTY_VIN = dotenv.get("EMPTY_VIN");
    public static String LICENSE_PLATE = dotenv.get("LICENSE_PLATE");
    public static String LICENSE_PLATE_WITH_MORE_THAN_10SYMBOLS = dotenv.get("LICENSE_PLATE_WITH_MORE_THAN_10SYMBOLS");

    /**
     * Test accounts (emails and passwords):
     */
    public static String TEST_EMAIL_BASE = dotenv.get("TEST_EMAIL_BASE");
    public static String VALID_EMAIL = dotenv.get("VALID_EMAIL");
    public static String VALID_EMAIL4 = dotenv.get("VALID_EMAIL4");
    public static String VALID_EMAIL6 = dotenv.get("VALID_EMAIL6");
    public static String VALID_EMAIL9 = dotenv.get("VALID_EMAIL9");
    public static String VALID_EMAIL13 = dotenv.get("VALID_EMAIL13");
    public static String VALID_EMAIL14 = dotenv.get("VALID_EMAIL14");
    public static String VALID_EMAIL15 = dotenv.get("VALID_EMAIL15");
    public static String VALID_EMAIL16 = dotenv.get("VALID_EMAIL16");
    public static String INVALID_EMAIL = dotenv.get("INVALID_EMAIL");
    public static String NULL_EMAIL = dotenv.get("NULL_EMAIL");
    public static String VALID_PASSWORD = dotenv.get("VALID_PASSWORD");
    public static String INVALID_PASSWORD = dotenv.get("INVALID_PASSWORD");
    public static String NULL_PASSWORD = dotenv.get("NULL_PASSWORD");

    /**
     * Test cards:
     */
    public static String CARD_NUMBER = dotenv.get("CARD_NUMBER");
    public static String CARD_NUMBER_1 = dotenv.get("CARD_NUMBER_1");
    public static String MONTH_YEAR = dotenv.get("MONTH_YEAR");
    public static String CVC = dotenv.get("CVC");
    public static String YUNO_3DS_CODE = dotenv.get("YUNO_3DS_CODE");

    /**
     * Test paypal:
     */
    public static String PAYPAL_EMAIL = dotenv.get("PAYPAL_EMAIL");
    public static String PAYPAL_PASSWORD = dotenv.get("PAYPAL_PASSWORD");

    /**
     * Database connection:
     */
    public static String DATABASE_URL_EPICVIN = dotenv.get("DATABASE_URL_EPICVIN");
    public static String DATABASE_USER = dotenv.get("DATABASE_USER");
    public static String DATABASE_PASSWORD = dotenv.get("DATABASE_PASSWORD");

    /**
     * Parsers database connection:
     */
    public static String PARSERS_DB_URL = dotenv.get("PARSERS_DB_URL");
    public static String PARSERS_DB_USER = dotenv.get("PARSERS_DB_USER");
    public static String PARSERS_DB_PASSWORD = dotenv.get("PARSERS_DB_PASSWORD");

    /**
     * Comma-separated list of test user IDs excluded from SQL queries.
     */
    public static String TEST_USER_IDS = dotenv.get("TEST_USER_IDS");

    /**
     * Comma-separated list of auctions excluded from the VIN validation query.
     */
    public static String EXCLUDED_AUCTIONS = dotenv.get("EXCLUDED_AUCTIONS");

    /**
     * Other test data
     */
    public static String ZIP = dotenv.get("ZIP");
    public static String TEST = dotenv.get("TEST");
    public static String MAKE = dotenv.get("MAKE");
    public static String MODEL = dotenv.get("MODEL");
    public static String COLOR = dotenv.get("COLOR");
    public static String YEAR = dotenv.get("YEAR");
    public static String ODOMETER = dotenv.get("ODOMETER");
    public static String MY_REPORT_URL_PAGE5 = dotenv.get("MY_REPORT_URL_PAGE5");

    /**
     * Basic auth credentials
     */
    public static String DEV_LOGIN_BASIC_AUTH_EPICVIN = dotenv.get("DEV_LOGIN_BASIC_AUTH_EPICVIN");
    public static String DEV_PASS_BASIC_AUTH_EPICVIN = dotenv.get("DEV_PASS_BASIC_AUTH_EPICVIN");
}
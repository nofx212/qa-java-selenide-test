package constants;

import io.github.cdimascio.dotenv.Dotenv;

public class Constants {

    private static final Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

    private static Integer toInt(String value) {
        try {
            return value == null || value.isBlank() ? null : Integer.valueOf(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Base URLs of projects:
     */
    public static String BASE_URL_EPICVIN = dotenv.get("BASE_URL_EPICVIN");
    public static String BASE_URL_EPICVIN_PRECHECK = dotenv.get("BASE_URL_EPICVIN_PRECHECK");
    public static String BASE_URL_VININSPECT_PRECHECK = dotenv.get("BASE_URL_VININSPECT_PRECHECK");
    public static String BASE_URL_VINGURUS = dotenv.get("BASE_URL_VINGURUS");
    public static String BASE_URL_VININSPECT = dotenv.get("BASE_URL_VININSPECT");
    public static String BASE_URL_SCA = dotenv.get("BASE_URL_SCA");
    public static String EPICVIN_EXT_URL = dotenv.get("EPICVIN_EXT_URL");
    public static String EPICVIN_TRUSTPILOT = dotenv.get("EPICVIN_TRUSTPILOT");

    /**
     * Delete specific data from an account by selecting checkboxes:
     * - Membership
     * - Phone
     * - Uploaded Documents
     * - Buying Power
     * - Address
     * - Lock/Unlock User
     */
    public static String RESET_USER_DATA_URL = dotenv.get("RESET_USER_DATA_URL");

    /**
     * Delete reports in logged in account only for test vin "2FMPK3J94LBA39390"
     * and  removes limits on all packages for the current day.
     */
    public static String REPORTS_EPICVIN = dotenv.get("REPORTS_EPICVIN");

    /**
     * Delete all test reviews from moderation with trigger first/last name = "AUTOTEST"
     */
    public static String REVIEWS_EPICVIN = dotenv.get("REVIEWS_EPICVIN");

    /**
     * Delete all payment methods if the account is logged in, leaving only one active method.
     */
    public static String PAYMENT_METHOD_EPICVIN = dotenv.get("PAYMENT_METHOD_EPICVIN");
    public static String PAYMENT_METHOD_VININSPECT = dotenv.get("PAYMENT_METHOD_VININSPECT");

    /**
     * This method simulates a dispute in the payment system.
     */
    public static String ADD_DISPUTE_EPICVIN = dotenv.get("ADD_DISPUTE_EPICVIN");
    public static String ADD_DISPUTE_VININSPECT = dotenv.get("ADD_DISPUTE_VININSPECT");
    public static String CLOSE_DISPUTE_EPICVIN = dotenv.get("CLOSE_DISPUTE_EPICVIN");
    public static String CLOSE_DISPUTE_VININSPECT = dotenv.get("CLOSE_DISPUTE_VININSPECT");

    /**
     * This method adds $100 in funds.
     */
    public static String ADD_FUNDS = dotenv.get("ADD_FUNDS");

    /**
     * Delete all trial and full subscriptions if the account is logged in.
     */
    public static String TRIAL_SUB_EPICVIN = dotenv.get("TRIAL_SUB_EPICVIN");
    public static String TRIAL_SUB_VININSPECT = dotenv.get("TRIAL_SUB_VININSPECT");
    public static String FULL_SUB_VININSPECT = dotenv.get("FULL_SUB_VININSPECT");
    public static String FULL_SUB_EPICVIN = dotenv.get("FULL_SUB_EPICVIN");

    /**
     * Delete all test affiliates if the account is logged in.
     */
    public static String AFFILIATES_EPICVIN = dotenv.get("AFFILIATES_EPICVIN");

    /**
     * Delete all fingerprints if the account is logged in on test cards:
     * - SiJTDE6Az2R8kQ0R (4111 1111 1111 1111)
     * - hTzGcV8jRxb1sSpu (4000 0566 5566 5556)
     * - xpzxSbwkUs1kGJsX (4242 4242 4242 4242)
     * - iYOkZWJku9xqXOu0 (4000 0000 0000 3220)
     */
    public static String FINGERPRINTS_EPICVIN = dotenv.get("FINGERPRINTS_EPICVIN");
    public static String FINGERPRINTS_VININSPECT = dotenv.get("FINGERPRINTS_VININSPECT");

    /**
     * Test VINs and License plates:
     */
    public static String VALID_VIN = dotenv.get("VALID_VIN");
    public static String VALID_VIN_IN_LOWERCASE = dotenv.get("VALID_VIN_IN_LOWERCASE");
    public static String VIN_MORE_THAN_17SYMBOLS = dotenv.get("VIN_MORE_THAN_17SYMBOLS");
    public static String CRASHES_VIN = dotenv.get("CRASHES_VIN");
    public static String EMISSIONS_VIN = dotenv.get("EMISSIONS_VIN");
    public static String SERVICES_VIN = dotenv.get("SERVICES_VIN");
    public static String INVALID_VIN = dotenv.get("INVALID_VIN");
    public static String REPAIR_SMITH_VIN = dotenv.get("REPAIR_SMITH_VIN");
    public static String LESS_FIVE_SYMBOLS_VIN = dotenv.get("LESS_FIVE_SYMBOLS_VIN");
    public static String EMPTY_VIN = dotenv.get("EMPTY_VIN");
    public static String LICENSE_PLATE = dotenv.get("LICENSE_PLATE");
    public static String LICENSE_PLATE_WITH_MORE_THAN_10SYMBOLS = dotenv.get("LICENSE_PLATE_WITH_MORE_THAN_10SYMBOLS");

    /**
     * Test accounts (emails and passwords):
     */
    public static String VALID_EMAIL = dotenv.get("VALID_EMAIL");
    public static String VALID_EMAIL1 = dotenv.get("VALID_EMAIL1");
    public static String VALID_EMAIL2 = dotenv.get("VALID_EMAIL2");
    public static String VALID_EMAIL3 = dotenv.get("VALID_EMAIL3");
    public static String VALID_EMAIL4 = dotenv.get("VALID_EMAIL4");
    public static String VALID_EMAIL5 = dotenv.get("VALID_EMAIL5");
    public static String VALID_EMAIL6 = dotenv.get("VALID_EMAIL6");
    public static String VALID_EMAIL7 = dotenv.get("VALID_EMAIL7");
    public static String VALID_EMAIL8 = dotenv.get("VALID_EMAIL8");
    public static String VALID_EMAIL9 = dotenv.get("VALID_EMAIL9");
    public static String VALID_EMAIL10 = dotenv.get("VALID_EMAIL10");
    public static String VALID_EMAIL11 = dotenv.get("VALID_EMAIL11");
    public static String VALID_EMAIL12 = dotenv.get("VALID_EMAIL12");
    public static String VALID_EMAIL13 = dotenv.get("VALID_EMAIL13");
    public static String VALID_EMAIL14 = dotenv.get("VALID_EMAIL14");
    public static String VALID_EMAIL15 = dotenv.get("VALID_EMAIL15");
    public static String VALID_EMAIL16 = dotenv.get("VALID_EMAIL16");
    public static String VALID_EMAIL17 = dotenv.get("VALID_EMAIL17");
    public static String VALID_DEALER_EMAIL = dotenv.get("VALID_DEALER_EMAIL");
    public static String VALID_LIVE_EMAIL = dotenv.get("VALID_LIVE_EMAIL");
    public static String LOCAL_EMAIL_EPICVIN = dotenv.get("LOCAL_EMAIL_EPICVIN");
    public static String LOCAL_EMAIL_SCA = dotenv.get("LOCAL_EMAIL_SCA");
    public static String INVALID_EMAIL = dotenv.get("INVALID_EMAIL");
    public static String INVALID_EMAIL_WITH_TYPO = dotenv.get("INVALID_EMAIL_WITH_TYPO");
    public static String NULL_EMAIL = dotenv.get("NULL_EMAIL");
    public static String VALID_PASSWORD = dotenv.get("VALID_PASSWORD");
    public static String INVALID_PASSWORD = dotenv.get("INVALID_PASSWORD");
    public static String LESS_SIX_SYMBOLS_PASSWORD = dotenv.get("LESS_SIX_SYMBOLS_PASSWORD");
    public static String NULL_PASSWORD = dotenv.get("NULL_PASSWORD");
    public static String GOOGLE_PASSWORD = dotenv.get("GOOGLE_PASSWORD");
    public static String VALID_LIVE_PASSWORD = dotenv.get("VALID_LIVE_PASSWORD");

    /**
     * Test cards:
     */
    public static String CARD_NUMBER = dotenv.get("CARD_NUMBER");
    public static String CARD_WITH_3Ds = dotenv.get("CARD_WITH_3DS");
    public static String CARD_INSUFFICIENT_FUNDS = dotenv.get("CARD_INSUFFICIENT_FUNDS");
    public static String CARD_PREPAID = dotenv.get("CARD_PREPAID");
    public static String CARD_PREPAID_IXOPAY = dotenv.get("CARD_PREPAID_IXOPAY");
    public static String CARD_NUMBER_1 = dotenv.get("CARD_NUMBER_1");
    public static String CARD_NUMBER_2 = dotenv.get("CARD_NUMBER_2");
    public static String CARD_MAESTRO = dotenv.get("CARD_MAESTRO");
    public static String MONTH_YEAR = dotenv.get("MONTH_YEAR");
    public static String INVALID_MONTH_YEAR = dotenv.get("INVALID_MONTH_YEAR");
    public static String CVC = dotenv.get("CVC");
    public static String INVALID_CVV = dotenv.get("INVALID_CVV");
    public static String YUNO_3DS_CODE = dotenv.get("YUNO_3DS_CODE");
    public static String CARD_DECLINE_YUNO = dotenv.get("CARD_DECLINE_YUNO");

    /**
     * Test paypal:
     */
    public static String PAYPAL_EMAIL = dotenv.get("PAYPAL_EMAIL");
    public static String PAYPAL_PASSWORD = dotenv.get("PAYPAL_PASSWORD");

    /**
     * Epicvin/EpicvinUK/Vininspect/SCA database connection:
     */
    public static String DATABASE_URL_EPICVIN = dotenv.get("DATABASE_URL_EPICVIN");
    public static String DATABASE_URL_EPICVIN_UK = dotenv.get("DATABASE_URL_EPICVIN_UK");
    public static String DATABASE_URL_VININSPECT = dotenv.get("DATABASE_URL_VININSPECT");
    public static String DATABASE_URL_SCA= dotenv.get("DATABASE_URL_SCA");
    public static String DATABASE_USER = dotenv.get("DATABASE_USER");
    public static String DATABASE_USER_SCA = dotenv.get("DATABASE_USER_SCA");
    public static String DATABASE_PASSWORD = dotenv.get("DATABASE_PASSWORD");
    public static String DATABASE_PASSWORD_SCA = dotenv.get("DATABASE_PASSWORD_SCA");

    /**
     * Parsers database connection:
     */
    public static String PARSERS_DB_URL = dotenv.get("PARSERS_DB_URL");
    public static String PARSERS_DB_USER = dotenv.get("PARSERS_DB_USER");
    public static String PARSERS_DB_PASSWORD = dotenv.get("PARSERS_DB_PASSWORD");

    /**
     * Affiliate login:
     */
    public static String AFFILIATE_USER = dotenv.get("AFFILIATE_USER");
    public static String AFFILIATE_PASSWORD = dotenv.get("AFFILIATE_PASSWORD");

    /**
     * Merchant login:
     */
    public static String MERCHANT_USER = dotenv.get("MERCHANT_USER");
    public static String MERCHANT_PASSWORD = dotenv.get("MERCHANT_PASSWORD");

    /**
     * Other test data
     */
    public static String ZIP = dotenv.get("ZIP");
    public static String POSTAL_CODE_LONG = dotenv.get("POSTAL_CODE_LONG");
    public static String TEST = dotenv.get("TEST");
    public static String PROMO = dotenv.get("PROMO");
    public static String PROMO_SCA = dotenv.get("PROMO_SCA");
    public static String MAKE = dotenv.get("MAKE");
    public static String MODEL = dotenv.get("MODEL");
    public static String COLOR = dotenv.get("COLOR");
    public static String YEAR = dotenv.get("YEAR");
    public static String ODOMETER = dotenv.get("ODOMETER");
    public static String STATE = dotenv.get("STATE");
    public static String PHONE_SCA = dotenv.get("PHONE_SCA");
    public static String AMOUNT_PRICE = dotenv.get("AMOUNT_PRICE");
    public static String BLOG_URL_PAGE2 = dotenv.get("BLOG_URL_PAGE2");
    public static String MY_REPORT_URL_PAGE5 = dotenv.get("MY_REPORT_URL_PAGE5");
    public static Integer PERFORMANCE_LIMIT_SCORE = toInt(dotenv.get("PERFORMANCE_LIMIT_SCORE"));
    public static Integer MAX_RESPONSE_TIME_MS = toInt(dotenv.get("MAX_RESPONSE_TIME_MS"));
    public static String EMAIL_WITH_ERROR = dotenv.get("EMAIL_WITH_ERROR");
    public static String LESS_TWO_SYMBOLS_NAME = dotenv.get("LESS_TWO_SYMBOLS_NAME");
    public static String MAX_LENGTH_1024_SYMBOLS = dotenv.get("MAX_LENGTH_1024_SYMBOLS");

    /**
     * Basic auth credentials
     */
    public static String DEV_LOGIN_BASIC_AUTH_EPICVIN = dotenv.get("DEV_LOGIN_BASIC_AUTH_EPICVIN");
    public static String DEV_PASS_BASIC_AUTH_EPICVIN = dotenv.get("DEV_PASS_BASIC_AUTH_EPICVIN");
    public static String DEV_LOGIN_BASIC_AUTH_SCA = dotenv.get("DEV_LOGIN_BASIC_AUTH_SCA");
    public static String DEV_PASS_BASIC_AUTH_SCA = dotenv.get("DEV_PASS_BASIC_AUTH_SCA");

}
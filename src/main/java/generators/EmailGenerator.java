package generators;

import enums.InvalidEmailType;

import java.util.Random;
import java.util.UUID;

public class EmailGenerator {

    private static final Random RANDOM = new Random();

    public static String generateInvalidEmail(InvalidEmailType type) {

        String local = "test." + RANDOM.nextInt(1000);

        switch (type) {
            case DOMAIN_TYPO:
                return local + "@gma.com";
            case INVALID_TLD:
                return local + "@gmail.con";
            case MISSING_TLD:
                return local + "@gmail";
            case DOUBLE_DOT_DOMAIN:
                return local + "@gmail..com";
            case NO_AT_SYMBOL:
                return local + "gmail.com";
            case DOUBLE_AT:
                return local + "@@gmail.com";
            case DOUBLE_DOT_LOCAL:
                return "test.." + RANDOM.nextInt(1000) + "@gmail.com";
            case DOT_AT_START:
                return "." + local + "@gmail.com";
            case DOT_AT_END:
                return local + ".@gmail.com";
            case INVALID_DOMAIN_ONLY_NUMBERS:
                return "Ronijuan@" + RANDOM.nextInt(100);
            case TOO_SHORT_DOMAIN:
                return "D@" + UUID.randomUUID().toString().substring(0, 5);
            case NON_ASCII_CHARACTERS:
                return "тест" + RANDOM.nextInt(100) + "@gmail.com";
            case EMPTY_EMAIL:
                return "";
            case ONLY_SYMBOLS:
                return "@@@@";
            case UNALLOWED_SPECIAL_CHARS:
                return "test!#$%&'*+/=?^_`{|}~" + RANDOM.nextInt(100) + "@gmail.com";
            default:
                throw new IllegalArgumentException("Unsupported type: " + type);
        }
    }
}

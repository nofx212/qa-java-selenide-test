package annotations.epicvin;

import com.codeborne.selenide.junit5.TextReportExtension;
import decorator.LogsExtension;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ParameterizedTest(name = "#{index}. Type: {0}")
@ValueSource(strings = {"DOMAIN_TYPO", "INVALID_TLD", "MISSING_TLD", "DOUBLE_DOT_DOMAIN", "NO_AT_SYMBOL", "DOUBLE_AT",
        "DOUBLE_DOT_LOCAL", "DOT_AT_START", "DOT_AT_END", "INVALID_DOMAIN_ONLY_NUMBERS", "TOO_SHORT_DOMAIN",
        "NON_ASCII_CHARACTERS", "EMPTY_EMAIL", "ONLY_SYMBOLS", "UNALLOWED_SPECIAL_CHARS"})
@Execution(ExecutionMode.SAME_THREAD)
@ExtendWith({TextReportExtension.class, LogsExtension.class})
@Tag("epicvin")
public @interface EpicvinRegistrationTest {
}

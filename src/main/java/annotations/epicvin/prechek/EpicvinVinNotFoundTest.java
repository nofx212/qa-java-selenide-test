package annotations.epicvin.prechek;

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
@ParameterizedTest(name = "#{index}. Payment method: {0}")
@ValueSource(strings = {"card","paypal","cardWithDecline"})
@Execution(ExecutionMode.SAME_THREAD)
@ExtendWith({TextReportExtension.class, LogsExtension.class})
@Tag("epicvin")
public @interface EpicvinVinNotFoundTest {
}

package annotations.epicvin;

import com.codeborne.selenide.junit5.TextReportExtension;
import decorator.LogsExtension;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Tag("epicvin")
@Test
@ExtendWith({LogsExtension.class, TextReportExtension.class})
public @interface EpicvinTest {
}

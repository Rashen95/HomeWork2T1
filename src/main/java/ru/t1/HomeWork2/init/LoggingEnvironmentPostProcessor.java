package ru.t1.HomeWork2.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import ru.t1.HomeWork2.exception.LoggingStartupException;

@Slf4j
public class LoggingEnvironmentPostProcessor implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String enablePropertyValue = environment.getProperty("logging.enabled");
        boolean isBoolValue = Boolean.TRUE.toString().equals(enablePropertyValue) || Boolean.FALSE.toString().equals(enablePropertyValue);

        if (!isBoolValue) {
            throw new LoggingStartupException("Ошибка при проверке свойства 'logging.enabled' ");
        }

        String loggingLevelValue = environment.getProperty("logging.level.ru.t1.HomeWork2");
        boolean levelIsCorrect = loggingLevelValue.equals("TRACE")
                || loggingLevelValue.equals("DEBUG")
                || loggingLevelValue.equals("INFO")
                || loggingLevelValue.equals("WARN")
                || loggingLevelValue.equals("ERROR");

        if (!levelIsCorrect) {
            throw new LoggingStartupException("Ошибка при проверке свойства 'logging.level' ");
        }
    }
}
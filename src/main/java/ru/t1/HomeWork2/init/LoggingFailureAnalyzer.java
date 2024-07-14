package ru.t1.HomeWork2.init;

import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;
import ru.t1.HomeWork2.exception.LoggingStartupException;

public class LoggingFailureAnalyzer extends AbstractFailureAnalyzer<LoggingStartupException> {
    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, LoggingStartupException cause) {
        return new FailureAnalysis(cause.getMessage(), "Укажите валидные значения для свойства", cause);
    }
}
package com.operation.system;

import org.springframework.context.support.GenericXmlApplicationContext;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.FileHandler;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class OperationSystemTest {
    private static Logger logger = Logger.getLogger(OperationSystem.class.getName());

    static {
        LogManager.getLogManager().reset();
        logger.setUseParentHandlers(false);
        Logger globalLogger = Logger.getLogger("global");
        Arrays.stream(globalLogger.getHandlers())
                .forEach(
                        globalLogger::removeHandler
                );

        Arrays.stream(logger.getHandlers())
                .forEach(logger::removeHandler);

        try {
            logger.addHandler(
                    new FileHandler("src/main/resources/log.xml")
            );
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
        ctx.load("app-context.xml");
        ctx.refresh();

        OperationSystem operationSystem = (OperationSystem) ctx.getBean("operationSystem");

        operationSystem.setLogger(logger);

        logger.info("Hello." +
                "Operation System test has started");

        operationSystem.start();
        operationSystem.stop();

        logger.info("Operation system test has ended." +
                "Bye");
    }
}
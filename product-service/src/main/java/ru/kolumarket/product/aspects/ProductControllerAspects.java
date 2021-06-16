package ru.kolumarket.product.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Aspect
@Component
public class ProductControllerAspects {
    Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @Before("execution(public * ru.kolumarket.product.controller.ProductController.*(..))")
    public void beforeAnyMethodInProductControllerClassWithDetails(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        StringBuilder signature = new StringBuilder();
        signature.append("В ProductController был вызван метод: " + methodSignature + "\n");
        Object[] args = joinPoint.getArgs();
        if (args.length > 0) {
            signature.append("Аргументы:\n");
            for (Object o : args) {
                signature.append(o+"\n");
            }
        }
        log.log(Level.INFO,signature.toString());
    }
}

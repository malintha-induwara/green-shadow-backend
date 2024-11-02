package lk.ijse.gdse68.greenshadow.annotation;

import lk.ijse.gdse68.greenshadow.util.IdGenerator;
import org.hibernate.annotations.IdGeneratorType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@IdGeneratorType(IdGenerator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface CustomGenerator {
    String prefix() ;
}

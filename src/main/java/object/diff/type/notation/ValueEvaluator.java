package object.diff.type.notation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import object.diff.type.config.ValueEqualEvaluator;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ValueEvaluator {
    Class<? extends ValueEqualEvaluator> valueEvaluator() default ValueEqualEvaluator.class;
}

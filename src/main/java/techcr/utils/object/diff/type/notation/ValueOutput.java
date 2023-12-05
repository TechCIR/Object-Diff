package techcr.utils.object.diff.type.notation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import techcr.utils.object.diff.type.config.ValueOutputHandler;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ValueOutput {
    Class<? extends ValueOutputHandler> outputHandler() default ValueOutputHandler.class;
}

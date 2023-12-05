package techcr.utils.object.diff.type.config;

import java.util.Objects;

public interface ValueEqualEvaluator<Type> {

    default boolean isEqual(Type source, Type target) {
        return Objects.equals(source, target);
    }
}

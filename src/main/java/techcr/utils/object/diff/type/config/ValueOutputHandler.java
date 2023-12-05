package techcr.utils.object.diff.type.config;

public interface ValueOutputHandler<Type> {
    default String print(Type type) {
        return type == null ? "" : type.toString();
    }
}

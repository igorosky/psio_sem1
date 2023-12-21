package ConsoleUI;

import java.util.Optional;

public interface InputValidator<T> {
    public Optional<String> validate(final T val);
}

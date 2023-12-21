package ConsoleUI.Validators;

import java.util.Optional;

import ConsoleUI.InputValidator;

public class StringNotEmpty implements InputValidator<String> {

    @Override
    public Optional<String> validate(final String val) {
        if(val.isEmpty()) {
            return Optional.of("Value cannot be empty");
        }
        return Optional.empty();
    }
    
}

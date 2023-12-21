package ConsoleUI.Validators;

import java.util.Optional;

import ConsoleUI.InputValidator;

public class NumberInRange implements InputValidator<Integer> {

    private int beg;
    private int end;
    
    /**
     * Check if number is in range [beg;end)
     * @param beg
     * @param end
     */
    public NumberInRange(int beg, int end) {
        this.beg = beg;
        this.end = end;
    }

    @Override
    public Optional<String> validate(final Integer val) {
        if(val < beg || val >= end) {
            return Optional.of(String.format("Number out of range [%d;%d)", beg, end));
        }
        return Optional.empty();
    }
    
}

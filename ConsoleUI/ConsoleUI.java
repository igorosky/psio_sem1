package ConsoleUI;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import ConsoleUI.Validators.NumberInRange;

public class ConsoleUI {
    static Scanner scanner = new Scanner(System.in);
    
    public static void print(Object obj) {
        System.out.println(obj);
    }

    public static void printList(List<?> list, boolean ordered) {
        for(int i = 0; i < list.size(); ++i) {
            if(ordered) {
                System.out.printf("%d. ", i + 1);
            }
            System.out.println(list.get(i));
        }
    }

    public static int choice(final List<?> list) {
        print("Options:");
        printList(list, true);
        return inputNumber("Choose option", List.of(new NumberInRange(1, list.size() + 1)));
    }

    public static int inputNumber(final String prompt, List<InputValidator<Integer>> validators) {
        int ans = -1;
        boolean valid = false;
        while(!valid) {
            System.out.printf("%s: ", prompt);
            try {
                ans = Integer.parseInt(scanner.nextLine());
            } catch (final NumberFormatException e) {
                print("It's not a number");
                continue;
            }
            valid = true;
            if(validators != null) {
                for (InputValidator<Integer> inputValidator : validators) {
                    Optional<String> check = inputValidator.validate(ans);
                    if(check.isPresent()) {
                        print(check.get());
                        valid = false;
                        break;
                    }
                }
            }
        }
        return ans;
    }

    public static String inputString(final String prompt, List<InputValidator<String>> validators) {
        String ans = "";
        boolean valid = false;
        while(!valid) {
            System.out.printf("%s: ", prompt);
            ans = scanner.nextLine();
            valid = true;
            if(validators != null) {
                for (InputValidator<String> inputValidator : validators) {
                    Optional<String> check = inputValidator.validate(ans);
                    if(check.isPresent()) {
                        print(check.get());
                        valid = false;
                        break;
                    }
                }
            }
        }
        return ans;
    }

    public static boolean inputBoolean(final String prompt) {
        return inputNumber(String.format("%s (0 or 1)", prompt), List.of(new NumberInRange(0, 2))) == 1;
    }
}

package ua.goit.console;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.regex.*;

public interface Command {

  Pattern pattern = Pattern.compile("^\\w+");

  void handle(String params, Consumer<Command> setActive)
      throws Exception;
  void printActiveMenu();
  default Optional<String> getCommandString(String params) {
    Matcher matcher = pattern.matcher(params);
    if (matcher.find()) {
      return Optional.of(matcher.group());
    }
    else {
      return Optional.empty();
    }
  }
}
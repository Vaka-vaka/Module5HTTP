package ua.goit.console.commands;

import java.io.IOException;
import java.util.function.Consumer;
import org.apache.http.HttpException;
import org.apache.logging.log4j.*;
import ua.goit.console.Command;
import ua.goit.service.UserService;

public class UserCommand implements Command {

  private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(UserCommand.class);
  private String[] paramsArray = new String[0];

  @Override
  public void handle(String params, Consumer<Command> setActive) throws Exception {
    paramsArray = params.split(" ");
    String subParams = String.join(" ", params.replace(paramsArray[0]+ " ", ""));
    switch (paramsArray[0]) {
      case "createUser": createUser(subParams); break;
      case "getUserByName" : getUserByName(subParams); break;
      case "updateUser": updateUser(subParams); break;
      case "deleteUser": deleteUser(subParams); break;
      case "userLogin": userLogin(subParams); break;
      case "userLogout": userLogout(); break;
    }
  }

  private void createUser(String params) throws HttpException, IOException {
    paramsArray = params.split(" ");
    if (paramsArray.length > 1) {
      System.out.println(UserService.createUser(
          Integer.parseInt(paramsArray[0]),
          paramsArray[1],
          paramsArray[2],
          paramsArray[3],
          paramsArray[4],
          paramsArray[5],
          paramsArray[6],
          Integer.parseInt(paramsArray[7])));
    }
    else {
      System.out.println(UserService.createUser());
    }
  }

  private void getUserByName(String params) throws HttpException, IOException {
    paramsArray = params.split(" ");
    System.out.println(UserService.getUserByName(paramsArray[0]));
  }

  private void updateUser(String params) throws HttpException, IOException {
    paramsArray = params.split(" ");
    if (paramsArray.length > 1) {
      System.out.println(UserService
          .updateUser(Integer.parseInt( paramsArray[0]),
                                        paramsArray[1],
                                        paramsArray[2],
                                        paramsArray[3],
                                        paramsArray[4],
                                        paramsArray[5],
                                        paramsArray[6],
                                        Integer.parseInt(paramsArray[7])));
    }
    else {
      System.out.println(UserService.updateUser());
    }
  }

  private void deleteUser(String params) throws HttpException, IOException {
    paramsArray = params.split(" ");
    System.out.println(UserService.deleteUser(paramsArray[0]));
  }

  private void userLogin(String params) throws HttpException, IOException {
    paramsArray = params.split(" ");
    System.out.println(UserService.userLogin(paramsArray[0],
                                             paramsArray[1]));
  }

  private void userLogout() throws HttpException, IOException {
    System.out.println(UserService.userLogout());
  }

  @Override
  public void printActiveMenu() {
    LOGGER.info("---------------------User menu---------------------");
    LOGGER.info("Users command list:");
    LOGGER.info("Without params: createUser" +
                  "With params: createUser [id] [userName] [firstName] [lastName] [email] [password] [phone] [status(0, 1)]");
    LOGGER.info("getUserByName [username]");
    LOGGER.info("Without params: updateUser\n" +
                  "With params: updateUser [id] [userName] [firstName] [lastName] [email] [password] [phone] [status]");
    LOGGER.info("deleteUser [username]");
    LOGGER.info("userLogin [userName] [password]");
    LOGGER.info("userLogout");
  }
}
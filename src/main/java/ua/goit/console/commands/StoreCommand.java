package ua.goit.console.commands;

import java.io.IOException;
import java.util.function.Consumer;
import org.apache.http.HttpException;
import org.apache.logging.log4j.*;
import ua.goit.console.Command;
import ua.goit.service.StoreService;

public class StoreCommand implements Command {

  private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(StoreCommand.class);
  private String[] paramsArray = new String[0];


  @Override
  public void handle(String params, Consumer<Command> setActive) throws Exception {
    paramsArray = params.split(" ");
    String subParams = String.join(" ", params.replace(paramsArray[0]+ " ", ""));
    switch (paramsArray[0]) {
      case "inventory": inventory(); break;
      case "orderPet" : orderPet(subParams); break;
      case "findOrderById": findOrderById(subParams); break;
      case "deleteOrderById": deleteOrderById(subParams); break;
    }
  }

  private void inventory() throws HttpException, IOException {
    System.out.println(StoreService.inventory());
  }

  private void orderPet(String params) throws HttpException, IOException {
    paramsArray = params.split(" ");
    if (paramsArray.length > 1) {
      System.out.println(StoreService.orderPet(Integer.parseInt(paramsArray[0]),
                                               Integer.parseInt(paramsArray[1]),
                                               Integer.parseInt(paramsArray[2])));
    } else {
      System.out.println(StoreService.orderPet());
    }
  }

  private void findOrderById(String params) throws HttpException, IOException {
    paramsArray = params.split(" ");
    System.out.println(StoreService.findOrderById(Integer.parseInt(paramsArray[0])));
  }

  private void deleteOrderById(String params) throws HttpException, IOException {
    paramsArray = params.split(" ");
    System.out.println(StoreService.deleteOrderById(Integer.parseInt(paramsArray[0])));
  }

  @Override
  public void printActiveMenu() {
    LOGGER.info("---------------------Store menu---------------------");
    LOGGER.info("Store command list:");
    LOGGER.info("inventory");
    LOGGER.info("Without params: orderPet\n" +
                   "With params: orderPet [id] [petId] [quantity]");
    LOGGER.info("findOrderById [id]");
    LOGGER.info("deleteOrderById [id]");
  }
}
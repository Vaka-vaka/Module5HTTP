package ua.goit.console.commands;

import java.io.IOException;
import java.util.function.Consumer;
import org.apache.http.HttpException;
import org.apache.logging.log4j.*;
import ua.goit.console.Command;
import ua.goit.service.PetService;

public class PetsCommand implements Command {

  private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(PetsCommand.class);
  private String[] paramsArray = new String[0];

  @Override
  public void handle(String params, Consumer<Command> setActive)
      throws Exception {
    paramsArray = params.split(" ");
    String subParams = String.join(" ", params.replace(paramsArray[0]+ " ", ""));
    switch (paramsArray[0]) {
      case "uploadPetImage": uploadPetImage(subParams); break;
      case "addPet" : addPet(subParams); break;
      case "updatePet": updatePet(subParams); break;
      case "findPetByStatus": findPetByStatus(subParams); break;
      case "findPetById": findPetById(subParams); break;
      case "updatePetFromDAta": updatePetFromDAta(subParams); break;
      case "deletePet": deletePet(subParams); break;
    }
  }

  private void uploadPetImage(String params) throws IOException {
    paramsArray = params.split(" ");
    Integer id = Integer.parseInt(paramsArray[0]);
    String pathFileToPhoto = paramsArray[1];
    String additionalMetadata = paramsArray[2];
    System.out.println(PetService.uploadPetImage(id, pathFileToPhoto, additionalMetadata));
  }

  private void addPet(String params) throws Exception {
    paramsArray = params.split(" ");
    if (paramsArray.length > 1) {
      System.out.println(PetService.addPet(Integer.parseInt(paramsArray[0]),
                                           Integer.parseInt(paramsArray[1]),
                                           paramsArray[2],
                                           paramsArray[3],
                                           Integer.parseInt(paramsArray[4]),
                                           paramsArray[5],
                                           paramsArray[6]));
    }
    else {
      System.out.println(PetService.addPet());
    }
  }

  private void updatePet(String params) throws HttpException, IOException {
    paramsArray = params.split(" ");
    if (paramsArray.length > 1) {
      System.out.println(PetService.updatePet(Integer.parseInt(paramsArray[0]),
          Integer.parseInt(paramsArray[1]),
          paramsArray[2],
          paramsArray[3],
          Integer.parseInt(paramsArray[4]),
          paramsArray[5],
          paramsArray[6]));
    }
    else {
      System.out.println(PetService.updatePet());
    }
  }

  private void findPetByStatus(String params) throws HttpException, IOException {
    paramsArray = params.split(" ");
    System.out.println(PetService.findPetByStatus(paramsArray[0]));
  }

  private void findPetById(String params) throws HttpException, IOException {
    paramsArray = params.split(" ");
    System.out.println(PetService.findPetById(Integer.parseInt(paramsArray[0])));
  }

  private void updatePetFromDAta(String params) throws HttpException, IOException {
    paramsArray = params.split(" ");
    System.out.println(PetService.updatePetFromDAta(Integer.parseInt(paramsArray[0])));
  }

  private void deletePet(String params) throws HttpException, IOException {
    paramsArray = params.split(" ");
    System.out.println(PetService.deletePet(Integer.parseInt(paramsArray[0])));
  }

  @Override
  public void printActiveMenu() {
    LOGGER.info("---------------------Pet menu---------------------");
    LOGGER.info("Pets command list:");
    LOGGER.info("uploadPetImage [id] [pathFileToPhoto] [additionalMetadata]");
    LOGGER.info("Without params: addPet\n" +
    "With params: addPet [id] [categoryId] [categoryName] [petName] [tagsId] [tagName] [status(available, pending, sold)]");
    LOGGER.info("Without params: updatePet\n" +
    "With params: updatePet [id] [categoryId] [categoryName] [petName] [tagsId] [tagName] [status(available, pending, sold)]");
    LOGGER.info("findPetByStatus [status(available, pending, sold)]");
    LOGGER.info("findPetById [id]");
    LOGGER.info("updatePetFromDAta [id]");
    LOGGER.info("deletePet [id]");
  }
}

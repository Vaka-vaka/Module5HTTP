package ua.goit.command;

import com.google.gson.reflect.TypeToken;
import ua.goit.model.ApiResponse;
import ua.goit.model.User;
import ua.goit.service.ConsoleManager;
import ua.goit.service.HttpHelper;
import ua.goit.service.JSONConverter;
import ua.goit.service.Util;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class UserCommand implements Command {
    private final JSONConverter converter = new JSONConverter();
    private final ConsoleManager consoleManager;
    private final HttpClient client = HttpClient.newHttpClient();
    private final TypeToken<User> userTypeToken = new TypeToken<>(){};
    private final TypeToken<ArrayList<User>> usersTypeToken = new TypeToken<>(){};
    private final TypeToken<ApiResponse> apiResponseTypeToken = new TypeToken<>(){};

    private static final String HOST = "https://petstore.swagger.io/v2/";
    private static final String USER = "user/";
    private static final String LOG = "login?username=%s&password=%s";
    private static final String USER_MENU = """
            Введите  номер команды, которую хотите:
            1 - создать списком
            2 - получить по имени
            3 - обновить по имени
            4 - удалить по имени
            5 - сохранить данные о пользователе
            6 - выполнить выход из текущего сеанса пользователя, вошедшего в систему
            7 - создать пользователя
            
            exit - закончить работу
            """;

    public UserCommand(ConsoleManager consoleManager) {
        this.consoleManager = consoleManager;
    }

    private String getByName(String name) throws IOException, InterruptedException {
        String url = String.format("%s%s%s", HOST, USER, name);
        HttpRequest request = HttpHelper.createRequest(url, "GET");
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return converter.convertJSONToObject(response.body(), userTypeToken).toString();
    }


    private String create(User entity) throws IOException, InterruptedException {
        String url = String.format("%s%s", HOST, USER);
        HttpRequest httpRequest = HttpHelper.createRequest(url, "POST", entity);
        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        ApiResponse response1 = converter.convertJSONToObject(response.body(), apiResponseTypeToken);
        return Util.getResponseMessage(response1);
    }

    private String delete(String name) throws IOException, InterruptedException {
        String url = String.format("%s%s%s", HOST, USER, name);
        HttpRequest httpRequest = HttpHelper.createRequest(url, "DELETE");
        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        ApiResponse response1 = converter.convertJSONToObject(response.body(), apiResponseTypeToken);
        return Util.getResponseMessage(response1);
    }

    private String update(String name, User entity) throws IOException, InterruptedException {
        String url = String.format("%s%s%s", HOST, USER, name);
        HttpRequest request = HttpHelper.createRequest(url, "PUT", entity);
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ApiResponse response1 = converter.convertJSONToObject(response.body(), apiResponseTypeToken);
        return Util.getResponseMessage(response1);
    }
    private User createUser() throws NumberFormatException{
        consoleManager.write("Введите идентификатор пользователя");
        long obtainedId =  Long.parseLong(consoleManager.read());
        consoleManager.write("Введите имя аккаунта");
        String obtainedUserName = consoleManager.read();
        consoleManager.write("Введите имя пользователя");
        String obtainedFirstName = consoleManager.read();
        consoleManager.write("Введите фамилия");
        String obtainedLastName = consoleManager.read();
        consoleManager.write("Введите email пользователя");
        String obtainedEmail = consoleManager.read();
        consoleManager.write("Введите пароль пользователя");
        String obtainedPassword = consoleManager.read();
        consoleManager.write("Введите телефонный номер пользователя");
        String obtainedPhone = consoleManager.read();
        consoleManager.write("Введите статус пользователя");
        int obtainedStatus = Integer.parseInt(consoleManager.read());
        return new User(obtainedId, obtainedUserName, obtainedFirstName, obtainedLastName, obtainedEmail,
                obtainedPassword, obtainedPhone, obtainedStatus);
    }

    private String createUsersWithList(ArrayList<User> users) throws IOException, InterruptedException {
        String url = String.format("%s%s%s", HOST, USER, "createWithList");
        HttpRequest request = HttpHelper.createRequest(url, "POST", users);
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ApiResponse response1 = converter.convertJSONToObject(response.body(), apiResponseTypeToken);
        return Util.getResponseMessage(response1);
    }

    private String logUot() throws IOException, InterruptedException {
        String url = String.format("%s%s%s", HOST, USER, "logout");
        HttpRequest request = HttpHelper.createRequest(url, "GET");
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ApiResponse response1 = converter.convertJSONToObject(response.body(), apiResponseTypeToken);
        return Util.getResponseMessage(response1);
    }

    private String logUser(String name, String pass) throws IOException, InterruptedException {
        String login = String.format(LOG, name, pass);
        String url = String.format("%s%s%s", HOST, USER, login);
        HttpRequest request = HttpHelper.createRequest(url, "GET");
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ApiResponse response1 = converter.convertJSONToObject(response.body(), apiResponseTypeToken);
        return Util.getResponseMessage(response1);
    }

    @Override
    public String commandName() {
        return "user";
    }

    @Override
    public void process() {
        boolean running = true;
        while (running) {
            try {
                consoleManager.write(USER_MENU);
                switch (consoleManager.read()) {
                    case "1" -> {
                        consoleManager.write("Введите сколько пользователей Вы хотите добавить");
                        int amount = Integer.parseInt(consoleManager.read());
                        ArrayList<User> users = new ArrayList<>();
                        for (int i = 0; i < amount; i++) {
                            users.add(createUser());
                            consoleManager.write("""
                                    Пользователь успешно добавлен в список
                                    """);
                        }
                        consoleManager.write(createUsersWithList(users));
                    }
                    case "2" -> {
                        consoleManager.write("Введите имя пользователя");
                        consoleManager.write(getByName(consoleManager.read()));
                    }
                    case "3" -> {
                        consoleManager.write("Введите имя пользователя которого необходимо обновить");
                        String oldName = consoleManager.read();
                        User user = createUser();
                        consoleManager.write(update(oldName, user));
                    }
                    case "4" -> {
                        consoleManager.write("Введите имя пользователя");
                        consoleManager.write(delete(consoleManager.read()));
                    }
                    case "5" -> {
                        consoleManager.write("Введите имя");
                        String name = consoleManager.read();
                        consoleManager.write("Введите пароль");
                        String pass = consoleManager.read();
                        consoleManager.write(logUser(name, pass));
                    }
                    case "6" -> consoleManager.write(logUot());
                    case "7" -> consoleManager.write(create(createUser()));
                    case "exit" -> running = false;
                }
            } catch (IOException | InterruptedException | NumberFormatException e){
                e.printStackTrace();
            }
        }
    }
}

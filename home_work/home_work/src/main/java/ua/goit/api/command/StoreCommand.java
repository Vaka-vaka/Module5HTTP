package ua.goit.api.command;

import com.google.gson.reflect.TypeToken;
import ua.goit.model.ApiResponse;
import ua.goit.model.Order;
import ua.goit.model.OrderStatus;
import ua.goit.api.service.ConsoleManager;
import ua.goit.api.service.HttpHelper;
import ua.goit.api.service.JSONConverter;
import ua.goit.api.service.Util;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.HashMap;

public class StoreCommand implements Command {
    private final JSONConverter converter = new JSONConverter();
    private final ConsoleManager consoleManager;
    private final HttpClient client = HttpClient.newHttpClient();
    private final TypeToken<HashMap<String, Integer>> inventoryTypeToken = new TypeToken<>(){};
    private final TypeToken<ApiResponse> apiResponseTypeToken = new TypeToken<>(){};
    private final TypeToken<Order> orderTypeToken = new TypeToken<>(){};

    private static final String HOST = "https://petstore.swagger.io/v2/store";
    private static final String INVENTORY = "/inventory";
    private static final String ORDER = "/order";
    private static final String STORE_MENU = """
            Введите номер комманды, которую хотите выполнить
            1 - получить хранилища питомцев
            2 - создать заказ питомцу
            3 - получить информацию по заказу
            4 - удалить запись
            
            exit - закончить работу
            """;

    public StoreCommand(ConsoleManager consoleManager) {
        this.consoleManager = consoleManager;
    }

    public HashMap<String, Integer> getInventory() throws IOException, InterruptedException {
        String url = String.format("%s%s", HOST, INVENTORY);
        HttpRequest request = HttpHelper.createRequest(url, "GET");
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        return converter.convertJSONToObject(httpResponse.body(), inventoryTypeToken);
    }

    public String create(Order entity) throws IOException, InterruptedException {
        String url = String.format("%s%s", HOST, ORDER);
        HttpRequest request = HttpHelper.createRequest(url, "POST", entity);
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Order order = converter.convertJSONToObject(response.body(), orderTypeToken);
        return Util.getResponseMessage(order);
    }

    public String getById(long id) throws IOException, InterruptedException {
        String url = String.format("%s%s/%d", HOST, ORDER, id);
        HttpRequest request = HttpHelper.createRequest(url, "GET");
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Order order = converter.convertJSONToObject(response.body(), orderTypeToken);
        return order.toString();
    }

    public String delete(long id) throws IOException, InterruptedException {
        String url = String.format("%s%s/%d", HOST, ORDER, id);
        HttpRequest request = HttpHelper.createRequest(url, "DELETE");
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ApiResponse response1 = converter.convertJSONToObject(response.body(), apiResponseTypeToken);
        return Util.getResponseMessage(response1);
    }

    @Override
    public String commandName() {
        return "store";
    }

    @Override
    public void process() {
        boolean running = true;
        while (running){
            try{
                consoleManager.write(STORE_MENU);
                String obtainedData = consoleManager.read();
                switch (obtainedData) {
                    case "1" -> {
                        HashMap<String, Integer> inventories = getInventory();
                        inventories.entrySet().forEach(status -> consoleManager.write(status.toString()));
                    }
                    case "2" -> {
                        consoleManager.write("Введите идентификатор заказа");
                        long orderId = convertStringToLong(consoleManager.read());
                        consoleManager.write("Введите идентификатор питомца");
                        long petId = convertStringToLong(consoleManager.read());
                        consoleManager.write("Введите количество");
                        int quantity = Integer.parseInt(consoleManager.read());
                        String date = LocalDate.now().toString();
                        consoleManager.write("""
                                Введите номер статуса заказа
                                1 - approved
                                2 - delivered
                                3 - placed
                                """);
                        OrderStatus orderStatus;
                        switch (consoleManager.read()) {
                            case "1" -> orderStatus = OrderStatus.APPROVED;
                            case "3" -> orderStatus = OrderStatus.PLACED;
                            default -> orderStatus = OrderStatus.DELIVERED;
                        }
                        consoleManager.write("""
                                Введите закончен ли заказ
                                1 - закончен
                                2 - не закончен
                                """);
                        boolean complete;
                        if (consoleManager.read().equals("1")){
                            complete = true;
                        } else {
                            complete = false;
                        }
                        Order order = new Order(orderId, petId, quantity, date, orderStatus, complete);
                        consoleManager.write(create(order));
                    }
                    case "3" -> {
                        consoleManager.write("Введите идентификатор заказа");
                        long id = Long.parseLong(consoleManager.read());
                        consoleManager.write(getById(id));
                    }
                    case "4" -> {
                        consoleManager.write("Введите идентификатор заказа");
                        long idDelete = Long.parseLong(consoleManager.read());
                        consoleManager.write(delete(idDelete));
                    }
                    case "exit" -> running = false;

                }
            } catch (IOException | InterruptedException | NumberFormatException e){
                consoleManager.write("Повторите попытку ввода данных");
            }
        }
    }

    private long convertStringToLong(String str) throws NumberFormatException{
        return Long.parseLong(str);
    }
}

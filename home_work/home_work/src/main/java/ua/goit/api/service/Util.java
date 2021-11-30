package ua.goit.api.service;

import ua.goit.model.ApiResponse;

import java.util.List;
import java.util.StringJoiner;

public class Util {
    public static String menuMessage() {
        return ("""
                Введите номер категории комманд
                pet - категория для работы с любимцами
                user - категория для работы с пользователями
                store - категория для работы с магазином
                exit - закончить работу""");
    }

    public static <T> String joinListElements(List<T> t){
        StringJoiner joiner = new StringJoiner("\n\n");
        for (T temp:t) {
            joiner.add(temp.toString());
        }
        return joiner.toString();
    }

    public static String getResponseMessage(ApiResponse response){
        if (response.getCode() == 200){
            return successful();
        } else {
            return error();
        }
    }

    public static <T> String getResponseMessage(T t){
        if (t!=null){
            return successful();
        } else {
            return error();
        }
    }

    public static String successful(){
        return "Ваш запрос обработан успешно";
    }

    public static String error(){
        return "Произошла ошибка, повторите ввод данных";
    }
}

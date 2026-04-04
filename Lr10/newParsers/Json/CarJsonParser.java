package Lr10.newParsers.Json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

public class CarJsonParser {
    private static final String FILE_PATH = "src/Lr10/newParsers/Json/cars.json";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nВыберите действие:");
            System.out.println("1 - Показать все автомобили");
            System.out.println("2 - Найти автомобили по автору");
            System.out.println("3 - Добавить новый автомобиль");
            System.out.println("4 - Удалить автомобиль по названию");
            System.out.println("5 - Выход");
            System.out.print("Ваш выбор: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1:
                        printAllCars();
                        break;
                    case 2:
                        System.out.print("Введите автора: ");
                        String author = scanner.nextLine();
                        searchByAuthor(author);
                        break;
                    case 3:
                        addNewCar(scanner);
                        break;
                    case 4:
                        System.out.print("Введите название автомобиля для удаления: ");
                        String titleToDelete = scanner.nextLine();
                        deleteCarByTitle(titleToDelete);
                        break;
                    case 5:
                        System.out.println("Выход из программы.");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Неверный пункт меню.");
                }
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }

    public static JSONObject readJsonFile() throws Exception {
        JSONParser parser = new JSONParser();
        FileReader reader = new FileReader(FILE_PATH);
        return (JSONObject) parser.parse(reader);
    }

    public static void writeJsonFile(JSONObject jsonObject) throws IOException {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            writer.write(jsonObject.toJSONString());
            writer.flush();
        }
    }

    public static void printAllCars() throws Exception {
        JSONObject jsonObject = readJsonFile();
        JSONArray cars = (JSONArray) jsonObject.get("cars");

        System.out.println("\nСписок автомобилей:");
        for (Object obj : cars) {
            JSONObject car = (JSONObject) obj;
            System.out.println("Название: " + car.get("title"));
            System.out.println("Производитель: " + car.get("author"));
            System.out.println("Год: " + car.get("year"));
            System.out.println("Цвет: " + car.get("color"));
            System.out.println("Цена: " + car.get("price"));
            System.out.println("-----------------------------");
        }
    }

    public static void searchByAuthor(String author) throws Exception {
        JSONObject jsonObject = readJsonFile();
        JSONArray cars = (JSONArray) jsonObject.get("cars");

        boolean found = false;
        System.out.println("\nРезультаты поиска по автору: " + author);

        for (Object obj : cars) {
            JSONObject car = (JSONObject) obj;
            String carAuthor = String.valueOf(car.get("author"));

            if (carAuthor.equalsIgnoreCase(author)) {
                found = true;
                System.out.println("Название: " + car.get("title"));
                System.out.println("Производитель: " + car.get("author"));
                System.out.println("Год: " + car.get("year"));
                System.out.println("Цвет: " + car.get("color"));
                System.out.println("Цена: " + car.get("price"));
                System.out.println("-----------------------------");
            }
        }

        if (!found) {
            System.out.println("Автомобили с таким автором не найдены.");
        }
    }

    public static void addNewCar(Scanner scanner) throws Exception {
        JSONObject jsonObject = readJsonFile();
        JSONArray cars = (JSONArray) jsonObject.get("cars");

        JSONObject newCar = new JSONObject();

        System.out.print("Введите название автомобиля: ");
        String title = scanner.nextLine();

        System.out.print("Введите производителя: ");
        String author = scanner.nextLine();

        System.out.print("Введите год выпуска: ");
        String year = scanner.nextLine();

        System.out.print("Введите цвет: ");
        String color = scanner.nextLine();

        System.out.print("Введите цену: ");
        String price = scanner.nextLine();

        newCar.put("title", title);
        newCar.put("author", author);
        newCar.put("year", year);
        newCar.put("color", color);
        newCar.put("price", price);

        cars.add(newCar);
        writeJsonFile(jsonObject);

        System.out.println("Автомобиль успешно добавлен.");
    }

    public static void deleteCarByTitle(String titleToDelete) throws Exception {
        JSONObject jsonObject = readJsonFile();
        JSONArray cars = (JSONArray) jsonObject.get("cars");

        Iterator<?> iterator = cars.iterator();
        boolean removed = false;

        while (iterator.hasNext()) {
            JSONObject car = (JSONObject) iterator.next();
            String title = String.valueOf(car.get("title"));

            if (title.equalsIgnoreCase(titleToDelete)) {
                iterator.remove();
                removed = true;
                break;
            }
        }

        if (removed) {
            writeJsonFile(jsonObject);
            System.out.println("Автомобиль успешно удален.");
        } else {
            System.out.println("Автомобиль с таким названием не найден.");
        }
    }
}
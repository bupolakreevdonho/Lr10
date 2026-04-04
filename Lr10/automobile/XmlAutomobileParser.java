package Lr10.automobile;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.Scanner;

public class XmlAutomobileParser {
    private static final String FILE_PATH = "src/lr10/automobile/example.xml";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n----- МЕНЮ -----");
            System.out.println("1. Показать все автомобили");
            System.out.println("2. Добавить автомобиль");
            System.out.println("3. Найти автомобиль по модели");
            System.out.println("4. Найти автомобиль по году");
            System.out.println("5. Удалить автомобиль по бренду");
            System.out.println("6. Выход");
            System.out.print("Выберите пункт: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> printAllCars();
                case 2 -> addCar(scanner);
                case 3 -> searchByModel(scanner);
                case 4 -> searchByYear(scanner);
                case 5 -> deleteCarByBrand(scanner);
                case 6 -> {
                    System.out.println("Завершение работы.");
                    return;
                }
                default -> System.out.println("Неверный пункт меню.");
            }
        }
    }

    private static Document getDocument() throws Exception {
        File file = new File(FILE_PATH);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(file);
    }

    private static void saveDocument(Document doc) throws Exception {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(FILE_PATH));
        transformer.transform(source, result);
    }

    private static void printAllCars() {
        try {
            Document doc = getDocument();
            NodeList cars = doc.getElementsByTagName("car");

            System.out.println("\nСписок всех автомобилей:");
            for (int i = 0; i < cars.getLength(); i++) {
                Element car = (Element) cars.item(i);
                printCar(car);
            }
        } catch (Exception e) {
            System.out.println("Ошибка при чтении XML: " + e.getMessage());
        }
    }

    private static void addCar(Scanner scanner) {
        try {
            Document doc = getDocument();
            Element root = doc.getDocumentElement();

            System.out.print("Введите бренд автомобиля: ");
            String brand = scanner.nextLine();

            System.out.print("Введите модель: ");
            String model = scanner.nextLine();

            System.out.print("Введите год издания: ");
            String year = scanner.nextLine();

            Element newCar = doc.createElement("car");

            Element brandElement = doc.createElement("brand");
            brandElement.appendChild(doc.createTextNode(brand));

            Element modelElement = doc.createElement("model");
            modelElement.appendChild(doc.createTextNode(model));

            Element yearElement = doc.createElement("year");
            yearElement.appendChild(doc.createTextNode(year));

            newCar.appendChild(brandElement);
            newCar.appendChild(modelElement);
            newCar.appendChild(yearElement);

            root.appendChild(newCar);

            saveDocument(doc);
            System.out.println("Автомобиль успешно добавлен.");
        } catch (Exception e) {
            System.out.println("Ошибка при добавлении автомобиля: " + e.getMessage());
        }
    }

    private static void searchByModel(Scanner scanner) {
        try {
            Document doc = getDocument();
            NodeList cars = doc.getElementsByTagName("car");

            System.out.print("Введите модель: ");
            String modelSearch = scanner.nextLine();

            boolean found = false;
            System.out.println("\nРезультаты поиска:");

            for (int i = 0; i < cars.getLength(); i++) {
                Element car = (Element) cars.item(i);
                String model = car.getElementsByTagName("model").item(0).getTextContent();

                if (model.equalsIgnoreCase(modelSearch)) {
                    printCar(car);
                    found = true;
                }
            }

            if (!found) {
                System.out.println("Автомобиль такой модели не найден.");
            }
        } catch (Exception e) {
            System.out.println("Ошибка при поиске: " + e.getMessage());
        }
    }

    private static void searchByYear(Scanner scanner) {
        try {
            Document doc = getDocument();
            NodeList cars = doc.getElementsByTagName("car");

            System.out.print("Введите год для поиска: ");
            String yearSearch = scanner.next();

            boolean found = false;
            for (int i = 0; i < cars.getLength(); i++) { // исправлено на cars.getLength()
                Element car = (Element) cars.item(i);
                if (car.getElementsByTagName("year").item(0).getTextContent().equals(yearSearch)) {
                    printCar(car);
                    found = true;
                }
            }
            if (!found) System.out.println("Автомобили этого года не найдены.");
        } catch (Exception e) {
            System.out.println("Ошибка при поиске: " + e.getMessage());
        }
    }

    private static void deleteCarByBrand(Scanner scanner) {
        try {
            Document doc = getDocument();
            NodeList cars = doc.getElementsByTagName("car");

            System.out.print("Введите бренд автомобиля для удаления: ");
            String brandSearch = scanner.nextLine();

            boolean found = false;

            for (int i = 0; i < cars.getLength(); i++) {
                Element car = (Element) cars.item(i);
                String brand = car.getElementsByTagName("brand").item(0).getTextContent();

                if (brand.equalsIgnoreCase(brandSearch)) {
                    Node parent = car.getParentNode();
                    parent.removeChild(car);
                    found = true;
                    break;
                }
            }

            if (found) {
                saveDocument(doc);
                System.out.println("Автомобиль успешно удален.");
            } else {
                System.out.println("Автомобиль такого бренда не найден.");
            }
        } catch (Exception e) {
            System.out.println("Ошибка при удалении: " + e.getMessage());
        }
    }

    private static void printCar(Element car) {
        String brand = car.getElementsByTagName("brand").item(0).getTextContent();
        String model = car.getElementsByTagName("model").item(0).getTextContent();
        String year = car.getElementsByTagName("year").item(0).getTextContent();

        System.out.println("Бренд: " + brand);
        System.out.println("Модель: " + model);
        System.out.println("Год: " + year);
        System.out.println("----------------------");
    }
}


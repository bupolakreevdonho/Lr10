package Lr10.newParsers.Html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;

public class HtmlNewsParser {

    private static final String URL = "https://example.com/news";
    private static final String OUTPUT_FILE = "news.txt";

    public static void main(String[] args) {
        Document doc = getDocumentWithRetry(URL, 3);

        if (doc == null) {
            System.out.println("Не удалось получить HTML-код страницы.");
            return;
        }

        StringBuilder result = new StringBuilder();
        Elements news = doc.select(".news-item");

        for (Element item : news) {
            String title = item.select(".title").text();
            String date = item.select(".date").text();

            String line = "Тема: " + title + ", Дата: " + date;
            System.out.println(line);
            result.append(line).append(System.lineSeparator());
        }

        saveToFile(result.toString(), OUTPUT_FILE);
    }

    private static Document getDocumentWithRetry(String url, int attempts) {
        for (int i = 1; i <= attempts; i++) {
            try {
                return Jsoup.connect(url).get();
            } catch (IOException e) {
                System.out.println("Ошибка загрузки страницы. Попытка " + i + " из " + attempts);
                if (i == attempts) {
                    System.out.println("Все попытки подключения исчерпаны.");
                } else {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
        return null;
    }

    private static void saveToFile(String data, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(data);
            System.out.println("Данные успешно сохранены в файл: " + filePath);
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }
}

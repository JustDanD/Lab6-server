package com.jd.lab6.server.CSV;

import com.jd.lab6.data.SpaceMarine;
import org.supercsv.cellprocessor.*;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.*;
import java.util.TreeSet;

/**
 * @author Пименов Данила P3130
 * Класс, отвечающий за загрузку/сохранение коллекции из/в файл(-а).
 */
public class CsvIO {
    /**
     * Метод, возвращающий процессоры для чтения csv
     */
    private static CellProcessor[] getReadingProcessors() {
        return new CellProcessor[]{
                new NotNull(new ParseLong()), //id
                new NotNull(), //name
                new NotNull(new ParseCoordinates()), //coordinates
                new NotNull(new ParseDate("yyyy-mm-dd")), //data
                new NotNull(new ParseDouble()), //health
                new NotNull(new ParseLong()), //heartCount
                new Optional(new ParseBool()), //loyal
                new NotNull(), //MeleeWeapon
                new NotNull(new ParseChapter()), //Chapter
        };
    }

    /**
     * Метод, возвращающий процессоры для записи csv
     */
    private static CellProcessor[] getWritingProcessors() {
        return new CellProcessor[]{
                new NotNull(new FmtNumber("")), //id
                new NotNull(), //name
                new NotNull(new FmtCoords()), //coordinates
                new NotNull(new FmtDate("yyyy-mm-dd")), //data
                new NotNull(new FmtNumber("")), //health
                new NotNull(new FmtNumber("")), //heartCount
                new Optional(new FmtBool("true", "false")), //loyal
                new NotNull(new FmtMeleeWeapon()), //MeleeWeapon
                new NotNull(new FmtChapter()), //Chapter
        };
    }

    /**
     * Метод, загружащий коллекцию из файла
     *
     * @param path - путь к файлу
     * @return TreeSet<SpaceMarine> - объект готовой коллекция
     */
    public static TreeSet<SpaceMarine> readFrom(String path) {
        TreeSet<SpaceMarine> inputTree = new TreeSet<>();
        String fileName = System.getenv(path);
        if (fileName == null) {
            System.out.println("Нет такой переменной окуржения");
            return inputTree;
        }
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(fileName))) {
            File file = new File(fileName);
            if (!file.exists()) {
                System.out.println("Такого файла нет!");
                return inputTree;
            }
            if (file.isDirectory()) {
                System.out.println("Не умею читать из директории(");
                return inputTree;
            }
            if (!file.isFile()) {
                System.out.println("Это плохой, вероятно, страшный и служебный файл)");
                return inputTree;
            }
            InputStreamReader inr = new InputStreamReader(in);
            CsvBeanReader beanReader = new CsvBeanReader(inr, CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE);
            String[] header = beanReader.getHeader(true);
            final CellProcessor[] processors = getReadingProcessors();
            SpaceMarine m;
            while ((m = beanReader.read(SpaceMarine.class, header, processors)) != null) {
                inputTree.add(m);
            }
        } catch (Exception e) {
            System.out.println("Ошибка загрузки коллекции:" + e.getMessage());
        }
        return inputTree;
    }

    /**
     * Метод, записывающий коллекцию в файл
     *
     * @param path - путь к файлу
     * @param col  - коллекция для сохранения
     */
    public static void writeTo(String path, TreeSet<SpaceMarine> col) {
        try {
            ICsvBeanWriter beanWriter = new CsvBeanWriter(new BufferedWriter(new FileWriter(path)), CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE);
            final String[] header = new String[]{"id", "name", "coordinates", "creationDate", "health", "heartCount", "loyal", "meleeWeapon", "chapter"};
            final CellProcessor[] processors = getWritingProcessors();
            beanWriter.writeHeader(header);
            for (final SpaceMarine marine : col) {
                beanWriter.write(marine, header, processors);
            }
            beanWriter.close();
            System.out.println("Коллекция успешно сохранена");
        } catch (Exception e) {
            System.out.println("Ошибка сохранения:" + e.getMessage());
        }
    }
}

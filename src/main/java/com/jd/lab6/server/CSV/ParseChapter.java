package com.jd.lab6.server.CSV;

import com.jd.lab6.data.Chapter;
import org.supercsv.cellprocessor.CellProcessorAdaptor;
import org.supercsv.cellprocessor.ift.StringCellProcessor;
import org.supercsv.exception.SuperCsvCellProcessorException;
import org.supercsv.util.CsvContext;

/**
 * @author Пименов Данила P3130
 * Входной парсер для части
 */
public class ParseChapter extends CellProcessorAdaptor implements StringCellProcessor {
    public ParseChapter() {

    }

    @Override
    public Chapter execute(Object value, CsvContext context) {
        this.validateInputNotNull(value, context);
        if (value instanceof String) {
            String[] parts = ((String) value).split(",");
            if (parts.length > 2 || parts.length < 1)
                throw new SuperCsvCellProcessorException("Broken chapter", context, this);
            if (parts.length == 1)
                return new Chapter(parts[0], "");
            return new Chapter(parts[0], parts[1]);
        }
        return null;
    }
}
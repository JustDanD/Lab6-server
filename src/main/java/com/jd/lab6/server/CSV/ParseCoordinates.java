package com.jd.lab6.server.CSV;

import com.jd.lab6.data.Coordinates;
import org.supercsv.cellprocessor.CellProcessorAdaptor;
import org.supercsv.cellprocessor.ift.StringCellProcessor;
import org.supercsv.exception.SuperCsvCellProcessorException;
import org.supercsv.util.CsvContext;

/**
 * @author Пименов Данила P3130
 * Входной парсер для координат
 */
public class ParseCoordinates extends CellProcessorAdaptor implements StringCellProcessor {
    public ParseCoordinates() {

    }

    @Override
    public Coordinates execute(Object value, CsvContext context) {
        this.validateInputNotNull(value, context);
        if (value instanceof String) {
            String[] parts = ((String) value).split(",");
            if (parts.length != 2)
                throw new SuperCsvCellProcessorException("Not enough coordinates", context, this);
            try {
                long x;
                float y;
                x = Long.parseLong(parts[0]);
                y = Float.parseFloat(parts[1]);
                return new Coordinates(x, y);
            } catch (Exception e) {
                throw new SuperCsvCellProcessorException("Wrong coordinates", context, this);
            }
        }
        return null;
    }
}

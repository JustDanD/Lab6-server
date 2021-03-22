package com.jd.lab6.server.CSV;


import com.jd.lab6.data.Chapter;
import org.supercsv.cellprocessor.CellProcessorAdaptor;
import org.supercsv.exception.SuperCsvCellProcessorException;
import org.supercsv.util.CsvContext;

/**
 * @author Пименов Данила P3130
 * Выходной парсер для части
 */
public class FmtChapter extends CellProcessorAdaptor {
    public FmtChapter() {
    }

    public <T> T execute(Object value, CsvContext context) {
        this.validateInputNotNull(value, context);
        if (!(value instanceof Chapter)) {
            throw new SuperCsvCellProcessorException("Broken chapter to write", context, this);
        } else {
            String res = ((Chapter) value).getName() + "," + ((Chapter) value).getParentLegion();
            return this.next.execute(res, context);
        }
    }
}

package com.jd.lab6.server.CSV;

import com.jd.lab6.data.MeleeWeapon;
import org.supercsv.cellprocessor.CellProcessorAdaptor;
import org.supercsv.exception.SuperCsvCellProcessorException;
import org.supercsv.util.CsvContext;

/**
 * @author Пименов Данила P3130
 * Выходной парсер для оружия
 */
public class FmtMeleeWeapon extends CellProcessorAdaptor {


    public FmtMeleeWeapon() {
    }

    public <T> T execute(Object value, CsvContext context) {
        this.validateInputNotNull(value, context);
        if (!(value instanceof MeleeWeapon)) {
            throw new SuperCsvCellProcessorException("Broken weapon to write", context, this);
        } else {
            String res = "" + value;
            return this.next.execute(res, context);
        }
    }
}
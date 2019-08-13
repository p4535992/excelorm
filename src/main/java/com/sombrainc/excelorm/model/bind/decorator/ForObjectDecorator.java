package com.sombrainc.excelorm.model.bind.decorator;

import com.sombrainc.excelorm.Main;
import com.sombrainc.excelorm.model.bind.ExcelReader;
import org.apache.poi.ss.usermodel.Cell;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ForObjectDecorator<T> extends BasicDecorator<T> {

    public ForObjectDecorator(Supplier<T> value, Cell cell) {
        super(value, cell);
    }

    public <T1> ForObjectDecorator<T> bind(String propertyName, ExcelReader<T1> excelReader) {
        return this;
    }

    public <T1 extends ForObjectDecorator> ForObjectDecorator<T> query(
            Consumer<Main.CellSelectOnly<ForObjectDecorator<T>, T>> queryConsumer
    ) {
        return this;
    }

}
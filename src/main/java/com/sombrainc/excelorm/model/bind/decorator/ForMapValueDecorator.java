package com.sombrainc.excelorm.model.bind.decorator;

import com.sombrainc.excelorm.Main;
import com.sombrainc.excelorm.model.bind.ExcelReader;
import org.apache.poi.ss.usermodel.Cell;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ForMapValueDecorator<T> extends BasicDecorator<T> {

    public ForMapValueDecorator(Supplier<T> value, Cell cell) {
        super(value, cell);
    }

    public <T1> ForMapValueDecorator<T> bind(String propertyName, ExcelReader<T1> excelReader) {
        return this;
    }

    public <T1> ForMapValueDecorator<T> bind(ExcelReader<T1> excelReader) {
        return this;
    }

    public ForMapValueDecorator<T> query(
            Consumer<Main.CellSelect<ForCollectionDecorator<T>, T>> queryConsumer
    ) {
        return this;
    }

}
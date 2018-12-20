package com.sombrainc.excelorm.implementor.tactic.implementation;

import com.sombrainc.excelorm.implementor.CellIndexTracker;
import com.sombrainc.excelorm.implementor.ExcelReader;
import com.sombrainc.excelorm.implementor.tactic.AbstractTactic;
import com.sombrainc.excelorm.implementor.tactic.CellTypeHandler;
import org.apache.poi.ss.usermodel.Sheet;

import java.lang.reflect.Field;

public class CellTypeMark<E> extends AbstractTactic<E> implements CellTypeHandler {

    public CellTypeMark(Field field, E instance, Sheet sheet, CellIndexTracker tracker) {
        super(field, instance, sheet, tracker);
    }

    @Override
    public Object process() {
        return ExcelReader.read(sheet, field.getType(), tracker);
    }
}
package com.sombrainc.excelorm.e2.impl.map.list;

import com.sombrainc.excelorm.e2.impl.map.CoreMapExecutor;
import com.sombrainc.excelorm.e2.impl.map.MapHolder;
import com.sombrainc.excelorm.exception.IncorrectRangeException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.*;

import static com.sombrainc.excelorm.utils.ExcelUtils.getOrCreateCell;
import static com.sombrainc.excelorm.utils.ExcelUtils.obtainRange;

public class MapOfListsExecutor<K, V> extends CoreMapExecutor<K, List<V>> {
    private final MapHolder<K, V> holder;

    protected MapOfListsExecutor(MapOfLists<K, V> target) {
        super(target);
        this.holder = target.holder;
    }

    @Override
    public Map<K, List<V>> go() {
        validateOnPureObjects(holder);

        final CellRangeAddress keyRange = obtainRange(holder.getKeyRange());
        final CellRangeAddress valueRange = obtainRange(holder.getValueRange());

        validate(keyRange, valueRange);

        final Map<K, List<V>> map = new HashMap<>();
        Iterator<CellAddress> valueIterator = valueRange.iterator();
        final FormulaEvaluator formulaEvaluator = createFormulaEvaluator();
        int counter = -1;

        for (CellAddress keyAddr : keyRange) {
            counter++;
            final Cell keyCell = toCell(keyAddr);
            if (isUntilByKeyReached(holder, keyCell)) {
                break;
            }
            if (filterByKey(holder, keyCell)) {
                valueIterator = incrementValuesIterator(keyRange, valueRange, valueIterator, counter);
                continue;
            }
            final K key = readRequestedType(formulaEvaluator, keyCell, holder.getKeyMapper(), holder.getKeyClass());

            if (!isVector(keyRange) || !isVector(valueRange) || isSameVector(keyRange, valueRange)) {
                final V value = readRequestedType(formulaEvaluator, toCell(valueIterator.next()), holder.getValueMapper(), holder.getValueClass());
                map.putIfAbsent(key, new ArrayList<V>() {{
                    add(value);
                }});
            } else {
                if (!map.isEmpty()) {
                    valueIterator = incrementValuesIterator(keyRange, valueRange, valueIterator, counter);
                }
                final List<V> values = readValue(valueIterator, formulaEvaluator, new ArrayList<>());
                map.putIfAbsent(key, values);
            }
        }
        return map;
    }

    private Iterator<CellAddress> incrementValuesIterator(CellRangeAddress keyRange, CellRangeAddress valueRange, Iterator<CellAddress> valueIterator, int counter) {
        if (isVector(keyRange) && !isHorizontal(keyRange, valueRange) && !isVertical(keyRange, valueRange)) {
            if (isHorizontal(valueRange)) {
                return new CellRangeAddress(valueRange.getFirstRow() + counter, valueRange.getLastRow() + counter,
                        valueRange.getFirstColumn(), valueRange.getLastColumn()).iterator();
            } else {
                return new CellRangeAddress(valueRange.getFirstRow(), valueRange.getLastRow(),
                        valueRange.getFirstColumn() + counter, valueRange.getLastColumn() + counter).iterator();
            }
        }
        valueIterator.next();
        return valueIterator;
    }

    private List<V> readValue(Iterator<CellAddress> valueIterator, FormulaEvaluator formulaEvaluator, List<V> list) {
        while (valueIterator.hasNext()) {
            final CellAddress next = valueIterator.next();
            final Cell cell = toCell(next);
            if (!Optional.ofNullable(holder.getValueFilter()).map(func -> func.apply(cell)).orElse(true)) {
                continue;
            }
            final V item = readRequestedType(formulaEvaluator, cell, holder.getValueMapper(), holder.getValueClass());
            list.add(item);
        }
        return list;
    }

    private static void validate(CellRangeAddress keyA, CellRangeAddress valueA) {
        if (isVector(keyA)) {
            final String message = "Cell range for value is not correct";
            if (isVector(valueA)) {
                if (isSameVector(keyA, valueA) && keyA.getNumberOfCells() != valueA.getNumberOfCells()) {
                    throw new IncorrectRangeException(message);
                }
            } else {
                throw new IncorrectRangeException(message);
            }
        } else if (keyA.getNumberOfCells() != valueA.getNumberOfCells()) {
            throw new IncorrectRangeException("Cell range for key and cell range for value should have the same number of cells");
        }
    }

    private static boolean isVector(CellRangeAddress addresses) {
        return addresses.getFirstRow() == addresses.getLastRow()
                || addresses.getFirstColumn() == addresses.getLastColumn();
    }

    private static boolean isHorizontal(CellRangeAddress addresses) {
        return addresses.getFirstRow() == addresses.getLastRow();
    }

    private static boolean isVertical(CellRangeAddress addresses) {
        return addresses.getFirstColumn() == addresses.getLastColumn();
    }

    private static boolean isHorizontal(CellRangeAddress key, CellRangeAddress value) {
        return isHorizontal(key) && isHorizontal(value);
    }

    private static boolean isVertical(CellRangeAddress key, CellRangeAddress value) {
        return isVertical(key) && isVertical(value);
    }

    private static boolean isSameVector(CellRangeAddress keyA, CellRangeAddress valueA) {
        return (keyA.getFirstRow() == keyA.getLastRow() && valueA.getFirstRow() == valueA.getLastRow())
                || (keyA.getFirstColumn() == keyA.getLastColumn() && valueA.getFirstColumn() == valueA.getLastColumn());
    }

}

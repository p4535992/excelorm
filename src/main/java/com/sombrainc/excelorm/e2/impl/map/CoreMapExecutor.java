package com.sombrainc.excelorm.e2.impl.map;

import com.sombrainc.excelorm.e2.impl.CoreActions;
import com.sombrainc.excelorm.e2.impl.MiddleExecutor;
import com.sombrainc.excelorm.exception.POIRuntimeException;

import java.util.Map;

public abstract class CoreMapExecutor<K, V> extends MiddleExecutor<Map<K, V>> {

    protected CoreMapExecutor(CoreActions core) {
        super(core.getEReaderContext());
    }

    protected void validateOnPureObjects(MapHolder holder) {
        validateOnPureObject(holder.getKeyClass(), "Key object is not supported.");
        if (holder.getValueClass() == null) {
            throw new POIRuntimeException("Value object could not be null");
        }
    }

}

package com.sombrainc.excelorm.e2.impl.map;

import com.sombrainc.excelorm.e2.impl.Bind;
import com.sombrainc.excelorm.e2.impl.BindField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Data
@Accessors(chain = true)
public class MapHolder<K1, V1> {
    protected String keyRange;
    private Class<K1> keyClass;
    private Class<V1> valueClass;
    private Function<BindField, K1> keyMapper;
    private Function<BindField, V1> valueMapper;
    private Function<BindField, Boolean> keyUntil;
    private Function<BindField, Boolean> valueUntil;
    private Function<BindField, Boolean> keyFilter;
    private Function<BindField, Boolean> valueFilter;
    private String valueRange;

    private List<Bind> binds;
    private boolean frozen;

    public MapHolder() {
        binds = new ArrayList<>();
    }
}

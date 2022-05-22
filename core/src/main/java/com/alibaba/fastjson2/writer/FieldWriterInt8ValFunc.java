package com.alibaba.fastjson2.writer;

import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.function.ToByteFunction;

import java.lang.reflect.Method;

final class FieldWriterInt8ValFunc
        extends FieldWriterInt8 {
    final Method method;
    final ToByteFunction function;

    protected FieldWriterInt8ValFunc(String fieldName, int ordinal, Method method, ToByteFunction function) {
        super(fieldName, ordinal, byte.class);
        this.method = method;
        this.function = function;
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public Object getFieldValue(Object object) {
        return function.applyAsByte(object);
    }

    @Override
    public void writeValue(JSONWriter jsonWriter, Object object) {
        byte value = function.applyAsByte(object);
        jsonWriter.writeInt32(value);
    }

    @Override
    public boolean write(JSONWriter jsonWriter, Object object) {
        byte value;
        try {
            value = function.applyAsByte(object);
        } catch (RuntimeException error) {
            if (jsonWriter.isIgnoreErrorGetter()) {
                return false;
            }
            throw error;
        }
        writeInt8(jsonWriter, value);
        return true;
    }
}

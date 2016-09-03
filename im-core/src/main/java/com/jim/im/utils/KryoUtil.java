/*
 * Copyright 2014 Jim. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jim.im.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

/**
 * @version 1.0.0
 * @date 2015年6月1日
 */
public class KryoUtil {

    static ThreadLocal<Kryo> kryoLocal = new ThreadLocal<Kryo>() {
        protected Kryo initialValue() {
            Kryo kryo = new Kryo();
            return kryo;
        }

        ;
    };

    public static <T> T fromBytes(byte[] bytes, Class<T> clazz, Kryo kryo) {
        ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
        return kryo.readObject(new Input(bin), clazz);
    }

    public static <T> T fromBytes(byte[] bytes, Class<T> clazz) {
        Kryo kryo = kryoLocal.get();
        return fromBytes(bytes, clazz, kryo);
    }

    public static byte[] toBytes(Object obj, Kryo kryo) {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        Output output = new Output(bout);
        kryo.writeObject(output, obj);
        output.close();
        return bout.toByteArray();
    }

    public static byte[] toBytes(Object obj) {
        Kryo kryo = kryoLocal.get();
        return toBytes(obj, kryo);
    }

}

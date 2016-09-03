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

import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.jim.im.exception.ImRuntimeException;


/**
 * @author chencao
 */
@SuppressWarnings("rawtypes")
public class Assert {

    
    /**
     * @param message
     */
    public static void throwException(final String message) {
        throw new ImRuntimeException(message);
    }
    
    /**
     * 断言表达式{@code expression}的值为真。如果表达式的值为假则抛出 <code>IllegalStateException</code>异常。
     * <p/>
     * 
     * <pre class="code">
     * Assert.isTrue(i &gt; 0, &quot;The value must be greater than zero&quot;);
     * </pre>
     *
     * @param expression 一个布尔表达式
     * @param message 断言失败时呈现的异常消息
     * @throws IllegalStateException 如果表达式的值是<code>false</code>
     */
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throwException(message);
        }
    }

    /**
     * 断言表达式{@code expression}的值为真。如果表达式的值为假则抛出 <code>IllegalStateException</code>异常。
     * <p/>
     * 
     * <pre class="code">
     * Assert.isTrue(i &gt; 0);
     * </pre>
     *
     * @param expression 一个布尔表达式
     * @throws IllegalStateException 如果表达式的值是<code>false</code>
     */
    public static void isTrue(boolean expression) {
        isTrue(expression, "[Assertion failed] - this expression must be true");
    }

    /**
     * 断言表达式{@code expression}的值为假。如果表达式的值为真则抛出 <code>IllegalStateException</code>异常。
     * <p/>
     * 
     * <pre class="code">
     * Assert.isFalse(i &gt; 0, &quot;The value must be smaller than zero&quot;);
     * </pre>
     *
     * @param expression 一个布尔表达式
     * @param message 断言失败时呈现的异常消息
     * @throws IllegalStateException 如果表达式的值是<code>true</code>
     */
    public static void isFalse(boolean expression, String message) {
        if (expression) {
            throwException(message);
        }
    }

    /**
     * 断言表达式{@code expression}的值为假。如果表达式的值为真则抛出 <code>IllegalStateException</code>异常。
     * <p/>
     * 
     * <pre class="code">
     * Assert.isFalse(i &gt; 0);
     * </pre>
     *
     * @param expression 一个布尔表达式
     * @throws IllegalStateException 如果表达式的值是<code>true</code>
     */
    public static void isFalse(boolean expression) {
        isFalse(expression, "[Assertion failed] - this expression must be false");
    }

    /**
     * Assert that an object is <code>null</code> .
     * <p/>
     * 
     * <pre class="code">
     * Assert.isNull(value, &quot;The value must be null&quot;);
     * </pre>
     *
     * @param object the object to check
     * @param message the exception message to use if the assertion fails
     * @throws IllegalStateException if the object is not <code>null</code>
     */
    public static void isNull(Object object, String message) {
        if (object != null) {
            throwException(message);
        }
    }
    
    public static void isNotNull(Object object, String message) {
        if (object == null) {
            throwException(message);
        }
    }

    public static void isNotNull(Object object) {
        isNotNull(object, "[Assertion failed] - the object argument must be null");
    }

    /**
     * Assert that an object is <code>null</code> .
     * <p/>
     * 
     * <pre class="code">
     * Assert.isNull(value);
     * </pre>
     *
     * @param object the object to check
     * @throws IllegalStateException if the object is not <code>null</code>
     */
    public static void isNull(Object object) {
        isNull(object, "[Assertion failed] - the object argument must be null");
    }

    /**
     * Assert that an object is not <code>null</code> .
     * <p/>
     * 
     * <pre class="code">
     * Assert.notNull(clazz, &quot;The class must not be null&quot;);
     * </pre>
     *
     * @param object the object to check
     * @param message the exception message to use if the assertion fails
     * @throws IllegalStateException if the object is <code>null</code>
     */
    public static void notNull(Object object, String message) {
        if (object == null) {
            throwException(message);
        }
    }

    /**
     * Assert that an object is not <code>null</code> .
     * <p/>
     * 
     * <pre class="code">
     * Assert.notNull(clazz);
     * </pre>
     *
     * @param object the object to check
     * @throws IllegalStateException if the object is <code>null</code>
     */
    public static void notNull(Object object) {
        notNull(object, "[Assertion failed] - this argument is required; it must not be null");
    }

    /**
     * Assert that a character sequence is null or contains no character
     *
     * @param text the character sequence to be checked
     * @param message the exception message to display when the assertion failed
     */
    public static void isEmpty(CharSequence text, String message) {
        if (StringUtils.isNotEmpty(text)) {
            throwException(message);
        }
    }

    /**
     * Assert that a character sequence is null or contains no character
     *
     * @param text the character sequence to be checked
     */
    public static void isEmpty(CharSequence text) {
        isEmpty(text,
                "[Assertion failed] - this CharSequence argument must contains one character at least");
    }

    /**
     * Assert that a character sequence contains one character at least.
     *
     * @param text the character sequence to be checked
     * @param message the exception message to display when the assertion failed
     */
    public static void notEmpty(CharSequence text, String message) {
        if (StringUtils.isEmpty(text)) {
            throwException(message);
        }
    }

    /**
     * Assert that a character sequence contains one character at least.
     *
     * @param text the character sequence to be checked
     */
    public static void notEmpty(CharSequence text) {
        notEmpty(text,
                "[Assertion failed] - this CharSequence argument must contains one character at least");
    }

    /**
     * Assert that an array is <code>null</code> or has not elements.
     * <p/>
     * 
     * <pre class="code">
     * Assert.isEmpty(array, &quot;The array must have not elements&quot;);
     * </pre>
     *
     * @param array the array to check
     * @param message the exception message to use if the assertion fails
     * @throws IllegalStateException if the object array has any elements
     */
    public static void isEmpty(Object[] array, String message) {
        if (array != null && array.length > 0) {
            throwException(message);
        }
    }

    /**
     * Assert that an array is <code>null</code> or has not elements.
     * <p/>
     * 
     * <pre class="code">
     * Assert.isEmpty(array, &quot;The array must have not elements&quot;);
     * </pre>
     *
     * @param array the array to check
     * @throws IllegalStateException if the object array has any elements
     */
    public static void isEmpty(Object[] array) {
        isEmpty(array,
                "[Assertion failed] - this array must be empty: it must not contain any element");
    }

    /**
     * Assert that an array has at least one element.
     * <p/>
     * 
     * <pre class="code">
     * Assert.notEmpty(array, &quot;The array must have elements&quot;);
     * </pre>
     *
     * @param array the array to check
     * @param message the exception message to use if the assertion fails
     * @throws IllegalStateException if the object array is <code>null</code> or has no elements
     */
    public static void notEmpty(Object[] array, String message) {
        if (array == null || array.length == 0) {
            throwException(message);
        }
    }

    /**
     * Assert that an array has at least one element.
     * <p/>
     * 
     * <pre class="code">
     * Assert.notEmpty(array);
     * </pre>
     *
     * @param array the array to check
     * @throws IllegalStateException if the object array is <code>null</code> or has no elements
     */
    public static void notEmpty(Object[] array) {
        notEmpty(array,
                "[Assertion failed] - this array must not be empty: it must contain at least 1 element");
    }

    /**
     * Assert that a collection has no elements.
     * <p/>
     * 
     * <pre class="code">
     * Assert.isEmpty(collection, &quot;Collection must have elements&quot;);
     * </pre>
     *
     * @param collection the collection to check
     * @param message the exception message to use if the assertion fails
     * @throws IllegalStateException if the collection has any elements
     */
    public static void isEmpty(Collection collection, String message) {
        if (collection != null && collection.size() > 0) {
            throwException(message);
        }
    }

    /**
     * Assert that a collection has no elements.
     * <p/>
     * 
     * <pre class="code">
     * Assert.notEmpty(collection, &quot;Collection must have elements&quot;);
     * </pre>
     *
     * @param collection the collection to check
     * @throws IllegalStateException if the collection has any elements
     */
    public static void isEmpty(Collection collection) {
        isEmpty(collection, "[Assertion failed] - this array must have no elements");
    }

    /**
     * Assert that a collection has elements.
     * <p/>
     * 
     * <pre class="code">
     * Assert.notEmpty(collection, &quot;Collection must have elements&quot;);
     * </pre>
     *
     * @param collection the collection to check
     * @param message the exception message to use if the assertion fails
     * @throws IllegalStateException if the collection is <code>null</code> or has no elements
     */
    public static void notEmpty(Collection collection, String message) {
        if (collection == null || collection.isEmpty()) {
            throwException(message);
        }
    }

    /**
     * Assert that a collection has elements.
     * <p/>
     * 
     * <pre class="code">
     * Assert.notEmpty(collection, &quot;Collection must have elements&quot;);
     * </pre>
     *
     * @param collection the collection to check
     * @throws IllegalStateException if the collection is <code>null</code> or has no elements
     */
    public static void notEmpty(Collection collection) {
        notEmpty(collection,
                "[Assertion failed] - this collection must contain at least 1 element");
    }

    /**
     * Assert that a MAP must have no entries.
     * <p/>
     * 
     * <pre class="code">
     * Assert.isEmpty(MAP, &quot;Map must have no elements&quot;);
     * </pre>
     *
     * @param map the MAP to check
     * @param message the exception message to use if the assertion fails
     * @throws IllegalStateException if the MAP has any entries
     */
    public static void isEmpty(Map map, String message) {
        if (map != null && map.size() > 0) {
            throwException(message);
        }
    }

    /**
     * Assert that a MAP must have no entries.
     * <p/>
     * 
     * <pre class="code">
     * Assert.notEmpty(MAP, &quot;Map must have no elements&quot;);
     * </pre>
     *
     * @param map the MAP to check
     * @throws IllegalStateException if the MAP has any entries
     */
    public static void isEmpty(Map map) {
        isEmpty(map, "[Assertion failed] - this array must be null or empty");
    }

    /**
     * Assert that a Map has entries.
     * <p/>
     * 
     * <pre class="code">
     * Assert.notEmpty(MAP, &quot;Map must have entries&quot;);
     * </pre>
     *
     * @param map the MAP to check
     * @param message the exception message to use if the assertion fails
     * @throws IllegalStateException if the MAP is <code>null</code> or has no entries
     */
    public static void notEmpty(Map map, String message) {
        if (map == null || map.isEmpty()) {
            throwException(message);
        }
    }

    /**
     * Assert that a Map has entries.
     * <p/>
     * 
     * <pre class="code">
     * Assert.notEmpty(MAP);
     * </pre>
     *
     * @param map the MAP to check
     * @throws IllegalStateException if the MAP is <code>null</code> or has no entries
     */
    public static void notEmpty(Map map) {
        notEmpty(map, "[Assertion failed] - this MAP must have at least one entry");
    }

    /**
     * 断言字符串为<code>null</code>、空字符串或者只包含空白字符串（空格、回车、换行、tab等字符） Assert that a character sequence is
     * null, empty or contains blank characters only.
     *
     * @param text the character sequence to be checked
     * @param message the exception message to display when the assertion failed
     */
    public static void isBlank(CharSequence text, String message) {
        if (StringUtils.isNotBlank(text)) {
            throwException(message);
        }
    }

    /**
     * 断言字符串为<code>null</code>、空字符串或者只包含空白字符串（空格、回车、换行、tab等字符） Assert that a character sequence is
     * null, empty or contains blank characters only.
     *
     * @param text the character sequence to be checked
     */
    public static void isBlank(CharSequence text) {
        isBlank(text, "[Assertion failed] - this CharSequence argument must be null or blank");
    }

    /**
     * 断言字符串text至少包含一个非空白字符串（空格、回车、换行、tab等空白字符之外的字符） Assert that a character sequence must have at
     * least one not blank character.
     *
     * @param text the character sequence to be checked
     * @param message the exception message to display when the assertion failed
     */
    public static void notBlank(CharSequence text, String message) {
        if (StringUtils.isBlank(text)) {
            throwException(message);
        }
    }

    /**
     * 断言字符串text至少包含一个非空白字符串（空格、回车、换行、tab等空白字符之外的字符） Assert that a character sequence must have at
     * least one not blank character.
     *
     * @param text the character sequence to be checked
     */
    public static void notBlank(CharSequence text) {
        notBlank(text, "[Assertion failed] - this CharSequence argument must not be null or blank");
    }

    /**
     * Assert that the given text contains the given substring.
     * <p/>
     * 
     * <pre class="code">
     * Assert.containsText(name, &quot;rod&quot;, &quot;Name must contains
     * 'rod'&quot;);
     * </pre>
     *
     * @param textToSearch the text to search
     * @param substring the substring to find within the text
     * @param message the exception message to use if the assertion fails
     */
    public static void containsText(String textToSearch, String substring, String message) {
        if (!StringUtils.isEmpty(textToSearch) && !StringUtils.isEmpty(substring)
                && textToSearch.indexOf(substring) == -1) {
            throwException(message);
        }
    }

    /**
     * Assert that the given text contains the given substring.
     * <p/>
     * 
     * <pre class="code">
     * Assert.containsText(name, &quot;rod&quot;);
     * </pre>
     *
     * @param textToSearch the text to search
     * @param substring the substring to find within the text
     */
    public static void containsText(String textToSearch, String substring) {
        containsText(textToSearch, substring,
                "[Assertion failed] - this String argument must contain the substring [" + substring
                        + "]");
    }

    /**
     * Assert that the given text does not contain the given substring.
     * <p/>
     * 
     * <pre class="code">
     * Assert.notContainsText(name, &quot;rod&quot;, &quot;Name must not contain
     * 'rod'&quot;);
     * </pre>
     *
     * @param textToSearch the text to search
     * @param substring the substring to find within the text
     * @param message the exception message to use if the assertion fails
     */
    public static void notContainsText(String textToSearch, String substring, String message) {
        if (!StringUtils.isEmpty(textToSearch) && !StringUtils.isEmpty(substring)
                && textToSearch.indexOf(substring) != -1) {
            throwException(message);
        }
    }

    /**
     * Assert that the given text does not contain the given substring.
     * <p/>
     * 
     * <pre class="code">
     * Assert.notContainsText(name, &quot;rod&quot;);
     * </pre>
     *
     * @param textToSearch the text to search
     * @param substring the substring to find within the text
     */
    public static void notContainsText(String textToSearch, String substring) {
        notContainsText(textToSearch, substring,
                "[Assertion failed] - this String argument must not contain the substring ["
                        + substring + "]");
    }

    /**
     * Assert that the given text starts with given substring.
     * <p/>
     * 
     * <pre class="code">
     * Assert.startsWithText(name, &quot;rod&quot;, &quot;Name must starts with
     * 'rod'&quot;);
     * </pre>
     *
     * @param textToSearch the text to search
     * @param substring the substring
     * @param message the exception message to use if the assertion fails
     */
    public static void startsWithText(String textToSearch, String substring, String message) {
        if (!StringUtils.isEmpty(textToSearch) && !StringUtils.isEmpty(substring)
                && !textToSearch.startsWith(substring)) {
            throwException(message);
        }
    }

    /**
     * Assert that the given text starts with given substring.
     * <p/>
     * 
     * <pre class="code">
     * Assert.startsWithText(name, &quot;rod&quot;);
     * </pre>
     *
     * @param textToSearch the text to search
     * @param substring the substring
     */
    public static void startsWithText(String textToSearch, String substring) {
        startsWithText(textToSearch, substring,
                "[Assertion failed] - this String argument must start with the substring ["
                        + substring + "]");
    }

    /**
     * Assert that the given text not starts with the given substring.
     * <p/>
     * 
     * <pre class="code">
     * Assert.notStartsWithText(name, &quot;rod&quot;, &quot;Name must not
     * starts with 'rod'&quot;);
     * </pre>
     *
     * @param textToSearch the text to search
     * @param substring the substring
     * @param message the exception message to use if the assertion fails
     */
    public static void notStartsWithText(String textToSearch, String substring, String message) {
        if (!StringUtils.isEmpty(textToSearch) && !StringUtils.isEmpty(substring)
                && textToSearch.startsWith(substring)) {
            throwException(message);
        }
    }

    /**
     * Assert that the given text not starts with the given substring.
     * <p/>
     * 
     * <pre class="code">
     * Assert.notStartsWithText(name, &quot;rod&quot;);
     * </pre>
     *
     * @param textToSearch the text to search
     * @param substring the substring to find
     */
    public static void notStartsWithText(String textToSearch, String substring) {
        notStartsWithText(textToSearch, substring,
                "[Assertion failed] - this String argument must not start with the substring ["
                        + substring + "]");
    }

    /**
     * Assert that the given text ends with given substring.
     * <p/>
     * 
     * <pre class="code">
     * Assert.endsWithText(name, &quot;rod&quot;, &quot;Name must end with
     * 'rod'&quot;);
     * </pre>
     *
     * @param textToSearch the text to search
     * @param substring the substring
     * @param message the exception message to use if the assertion fails
     */
    public static void endsWithText(String textToSearch, String substring, String message) {
        if (!StringUtils.isEmpty(textToSearch) && !StringUtils.isEmpty(substring)
                && !textToSearch.endsWith(substring)) {
            throwException(message);
        }
    }

    /**
     * Assert that the given text ends with given substring.
     * <p/>
     * 
     * <pre class="code">
     * Assert.endsWithText(name, &quot;rod&quot;);
     * </pre>
     *
     * @param textToSearch the text to search
     * @param substring the substring
     */
    public static void endsWithText(String textToSearch, String substring) {
        endsWithText(textToSearch, substring,
                "[Assertion failed] - this String argument must end with the substring ["
                        + substring + "]");
    }

    /**
     * Assert that the given text must not end with the given substring.
     * <p/>
     * 
     * <pre class="code">
     * Assert.notEndsWithText(name, &quot;rod&quot;, &quot;Name must not end
     * with 'rod'&quot;);
     * </pre>
     *
     * @param textToSearch the text to search
     * @param substring the substring
     * @param message the exception message to use if the assertion fails
     */
    public static void notEndsWithText(String textToSearch, String substring, String message) {
        if (!StringUtils.isEmpty(textToSearch) && !StringUtils.isEmpty(substring)
                && textToSearch.endsWith(substring)) {
            throwException(message);
        }
    }

    /**
     * Assert that the given text not end with the given substring.
     * <p/>
     * 
     * <pre class="code">
     * Assert.notEndsWithText(name, &quot;rod&quot;);
     * </pre>
     *
     * @param textToSearch the text to search
     * @param substring the substring to find
     */
    public static void notEndsWithText(String textToSearch, String substring) {
        notEndsWithText(textToSearch, substring,
                "[Assertion failed] - this String argument must not end with the substring ["
                        + substring + "]");
    }

    /**
     * Assert that an array has no null elements. Note: Does not complain if the array is empty!
     * <p/>
     * 
     * <pre class="code">
     * Assert.noNullElements(array, &quot;The array must have non-null
     * elements&quot;);
     * </pre>
     *
     * @param array the array to check
     * @param message the exception message to use if the assertion fails
     * @throws IllegalStateException if the object array contains a <code>null</code> element
     */
    public static void noNullElements(Object[] array, String message) {
        if (array == null) {
            return;
        }
        for (Object each : array) {
            notNull(each, message);
        }
    }

    /**
     * Assert that an array has no null elements. Note: Does not complain if the array is empty!
     * <p/>
     * 
     * <pre class="code">
     * Assert.noNullElements(array);
     * </pre>
     *
     * @param array the array to check
     * @throws IllegalStateException if the object array contains a <code>null</code> element
     */
    public static void noNullElements(Object[] array) {
        noNullElements(array, "[Assertion failed] - this array must not contain any null elements");
    }


    /**
     * Assert that an collection has no null elements. Note: Does not complain if the collection is
     * empty!
     * <p/>
     * 
     * <pre class="code">
     * Assert.noNullElements(collection, &quot;The collection must have non-null
     * elements&quot;);
     * </pre>
     *
     * @param collection the collection to check
     * @param message the exception message to use if the assertion fails
     * @throws IllegalStateException if the object array contains a <code>null</code> element
     */
    public static void noNullElements(Collection collection, String message) {
        if (collection == null) {
            return;
        }
        for (Object each : collection) {
            notNull(each, message);
        }
    }

    /**
     * Assert that an collection has no null elements. Note: Does not complain if the collection is
     * empty!
     * <p/>
     * 
     * <pre class="code">
     * Assert.noNullElements(collection);
     * </pre>
     *
     * @param collection the collection to check
     * @throws IllegalStateException if the object collection contains a <code>null</code> element
     */
    public static void noNullElements(Collection collection) {
        noNullElements(collection,
                "[Assertion failed] - this array must not contain any null elements");
    }

    /**
     * Assert that an MAP has no null elements. Note: Does not complain if the MAP is empty!
     * <p/>
     * 
     * <pre class="code">
     * Assert.noNullElements(MAP, &quot;The MAP must have non-null
     * elements&quot;);
     * </pre>
     *
     * @param map the MAP to check
     * @param message the exception message to use if the assertion fails
     * @throws IllegalStateException if the object array contains a <code>null</code> element
     */
    public static void noNullElements(Map map, String message) {
        if (map == null) {
            return;
        }
        for (Object each : map.values()) {
            notNull(each, message);
        }
    }

    /**
     * Assert that an MAP has no null elements. Note: Does not complain if the MAP is empty!
     * <p/>
     * 
     * <pre class="code">
     * Assert.noNullElements(MAP);
     * </pre>
     *
     * @param map the MAP to check
     * @throws IllegalStateException if the object MAP contains a <code>null</code> element
     */
    public static void noNullElements(Map map) {
        noNullElements(map, "[Assertion failed] - this MAP must not contain any null elements");
    }

    /**
     * Assert that the provided object is an instance of the provided class.
     * <p/>
     * 
     * <pre class="code">
     * Assert.isInstanceOf(Foo.class, foo, &quot;The object must be instance of
     * the type&quot;);
     * </pre>
     *
     * @param type the type to check against
     * @param obj the object to check
     * @param message a message which will be prepended to the message produced by the function
     *        itself, and which may be used to provide context. It should normally end in a ": " or
     *        ". " so that the function generate message looks ok when prepended to it.
     * @throws IllegalStateException if the object is not an instance of clazz
     * @see Class#isInstance
     */
    public static void isInstanceOf(Class type, Object obj, String message) {
        notNull(type, "Type to check against must not be null");
        if (!type.isInstance(obj)) {
            throwException(message);
        }
    }

    /**
     * Assert that the provided object is an instance of the provided class.
     * <p/>
     * 
     * <pre class="code">
     * Assert.isInstanceOf(Foo.class, foo);
     * </pre>
     *
     * @param type the required class
     * @param obj the object to check
     * @throws IllegalStateException if the object is not an instance of clazz
     * @see Class#isInstance
     */
    public static void isInstanceOf(Class type, Object obj) {
        isInstanceOf(type, obj,
                "Object of class [" + (obj != null ? obj.getClass().getName() : "null")
                        + "] must be an instance of " + type);
    }

    /**
     * Assert that <code>superType.isAssignableFrom(subType)</code> is <code>true</code>.
     * <p/>
     * 
     * <pre class="code">
     * Assert.isAssignableFrom(Number.class, myClass, &quot;The subType must be
     * instance of the superType&quot;);
     * </pre>
     *
     * @param superType the super type to check against
     * @param subType the sub type to check
     * @param message a message which will be prepended to the message produced by the function
     *        itself, and which may be used to provide context. It should normally end in a ": " or
     *        ". " so that the function generate message looks ok when prepended to it.
     * @throws IllegalStateException if the classes are not assignable
     */
    @SuppressWarnings("unchecked")
    public static void isAssignableFrom(Class superType, Class subType, String message) {
        notNull(superType, "Type to check against must not be null");
        if (subType == null || !superType.isAssignableFrom(subType)) {
            throwException(message);
        }
    }

    /**
     * Assert that <code>superType.isAssignableFrom(subType)</code> is <code>true</code>.
     * <p/>
     * 
     * <pre class="code">
     * Assert.isAssignableFrom(Number.class, myClass);
     * </pre>
     *
     * @param superType the super type to check
     * @param subType the sub type to check
     * @throws IllegalStateException if the classes are not assignable
     */
    public static void isAssignableFrom(Class superType, Class subType) {
        isAssignableFrom(superType, subType, subType + " must be assignable to " + superType);
    }

    public static void check(final boolean expression, final String message) {
        if (!expression) {
            throwException(message);
        }
    }

    public static void check(final boolean expression, final String message, final Object... args) {
        if (!expression) {
            String str = String.format(message, args);
            throwException(str);
        }
    }

    public static void check(final boolean expression, final String message, final Object arg) {
        if (!expression) {
            String str = String.format(message, arg);
            throwException(str);
        }
    }
}

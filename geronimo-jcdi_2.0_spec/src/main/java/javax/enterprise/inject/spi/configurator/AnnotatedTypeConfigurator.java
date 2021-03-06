/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package javax.enterprise.inject.spi.configurator;

import javax.enterprise.inject.spi.AnnotatedConstructor;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.AnnotatedType;
import java.lang.annotation.Annotation;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface AnnotatedTypeConfigurator<T> {

    /**
     * @return the original {@link AnnotatedType}
     */
    AnnotatedType<T> getAnnotated();

    /**
     * Add an annotation to the field.
     *
     * @param annotation to add
     * @return self
     */
    AnnotatedTypeConfigurator<T> add(Annotation annotation);

    /**
     * Remove all annotations which fit the given Predicate.
     * @return self
     */
    AnnotatedTypeConfigurator<T> remove(Predicate<Annotation> predicate);

    /**
     * Remove all annotations from the type.
     *
     * @return self
     */
    default AnnotatedTypeConfigurator<T> removeAll()
    {
        remove((e) -> true);
        return this;
    }

    /**
     *
     * @return an immutable set of {@link AnnotatedMethodConfigurator}s reflecting the {@link AnnotatedType#getMethods()}
     */
    Set<AnnotatedMethodConfigurator<? super T>> methods();

    /**
     * @param predicate Testing the original {@link AnnotatedMethod}
     * @return a sequence of {@link AnnotatedMethodConfigurator}s matching the given predicate
     * @see AnnotatedMethodConfigurator#getAnnotated()
     */
    default Stream<AnnotatedMethodConfigurator<? super T>> filterMethods(Predicate<AnnotatedMethod<? super T>> predicate) {
        return methods().stream().filter(c -> predicate.test(c.getAnnotated()));
    }

    /**
     *
     * @return an immutable set of {@link AnnotatedFieldConfigurator}s reflecting the {@link AnnotatedType#getFields()}
     */
    Set<AnnotatedFieldConfigurator<? super T>> fields();

    /**
     * @param predicate Testing the original {@link AnnotatedField}
     * @return a sequence of {@link AnnotatedFieldConfigurator}s matching the given predicate
     * @see AnnotatedFieldConfigurator#getAnnotated()
     */
    default Stream<AnnotatedFieldConfigurator<? super T>> filterFields(Predicate<AnnotatedField<? super T>> predicate) {
        return fields().stream().filter(f -> predicate.test(f.getAnnotated()));
    }

    /**
     *
     * @return an immutable set of {@link AnnotatedConstructorConfigurator}s reflecting the
     *         {@link AnnotatedType#getConstructors()}
     */
    Set<AnnotatedConstructorConfigurator<T>> constructors();

    /**
     *
     * @param predicate Testing the original {@link AnnotatedConstructor}
     * @return a sequence of {@link AnnotatedConstructorConfigurator}s matching the given predicate
     * @see AnnotatedConstructorConfigurator#getAnnotated()
     */
    default Stream<AnnotatedConstructorConfigurator<T>> filterConstructors(Predicate<AnnotatedConstructor<T>> predicate) {
        return constructors().stream().filter(c -> predicate.test(c.getAnnotated()));
    }

}

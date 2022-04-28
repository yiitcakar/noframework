package com.bayzat.poc.noframework.reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

class ClassManagerImpl implements ClassManager
{
	static ClassManagerImpl instance = new ClassManagerImpl();

	@Override
	public boolean classHasAnnotation(Class<?> cls, Class<? extends Annotation> ant) {
		return NullCheckUtils.isNotObjectEmpty(cls) 
				? NullCheckUtils.isNotObjectEmpty(ant)
						? NullCheckUtils.isNotObjectEmpty(cls.getAnnotation(ant))
						: false
				: false ;
	}

	@Override
	public <T extends Annotation> T getAnnotationFromClassWithNullCheck(Class<?> cls, Class<T> ant) {
		return NullCheckUtils.isNotObjectEmpty(cls) 
				? NullCheckUtils.isNotObjectEmpty(ant)
						? cls.getAnnotation(ant)
						: null
				: null ;
	}

	@Override
	public <T extends Annotation> T getAnnotationFromClassWithoutNullCheck(Class<?> cls, Class<T> ant) {
		return cls.getAnnotation(ant);
	}

	@Override
	public Collection<Method> getMethodsFromClassWithGivenAnnotation(Class<?> cls, Class<? extends Annotation> ant) {
		return NullCheckUtils.isNotObjectEmpty(cls)
				? NullCheckUtils.isNotObjectEmpty(ant)
					? Arrays.asList(cls.getDeclaredMethods())
						.stream()
						.filter(method -> NullCheckUtils.isNotObjectEmpty(method.getAnnotation(ant)))
						.collect(Collectors.toList())
					: Collections.emptyList()
				: Collections.emptyList();
	}

	@Override
	public List<Field> getFieldsFromClassWithAnnotation(Class<?> cls, Class<? extends Annotation> ant) {
		return NullCheckUtils.isNotObjectEmpty(cls)
				? NullCheckUtils.isNotObjectEmpty(ant)
					? Arrays.asList(cls.getDeclaredFields())
						.stream()
						.filter(field -> NullCheckUtils.isNotObjectEmpty(field.getAnnotation(ant)))
						.collect(Collectors.toList())
					: Collections.emptyList()
				: Collections.emptyList();
	}


}

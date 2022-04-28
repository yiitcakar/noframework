package com.bayzat.poc.noframework.injection.common;

import java.util.List;
import java.util.Set;

public interface ApplicationContext {

    <T> T getBean(Class<T> beanClass);
    Set<String> getBeanNames();
}

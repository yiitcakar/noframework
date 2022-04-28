package com.bayzat.poc.noframework.injection;

import com.bayzat.poc.noframework.injection.common.*;
import com.bayzat.poc.noframework.injection.exception.CircularDependencyException;
import com.bayzat.poc.noframework.injection.exception.NoDefaultConstructorException;
import com.bayzat.poc.noframework.injection.exception.NoSuchABeanException;
import com.bayzat.poc.noframework.reflections.ClassManager;
import com.bayzat.poc.noframework.reflections.PackageManager;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class SimpleApplicationContext implements ApplicationContext
{
    protected Map<String,Object> beanMap;

    public SimpleApplicationContext(String sourcePackage){
        Map<String,Class<?>> beanClassMap = createBeanClassMap(sourcePackage);
        validateBeans(beanClassMap);
        createBeans(beanClassMap);
        injectDependencies();
        injectPropertyValues();
        invokeInitMethods();
    }

    protected void invokeInitMethods(){
        this.beanMap.values().forEach(bean->{
            ClassManager.instance().getMethodsFromClassWithGivenAnnotation(bean.getClass(), Init.class)
                    .stream()
                    .forEach(init->{
                        try {
                            init.invoke(bean);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    });
        });
    }

    protected void injectDependencies(){
        this.beanMap.values().forEach(bean->{
            ClassManager.instance().getFieldsFromClassWithAnnotation(bean.getClass(),Inject.class)
                    .stream()
                    .forEach(field -> {
                        field.setAccessible(true);
                        try {
                            field.set(bean,beanMap.get(field.getType().getName()));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    });
        });
    }

    protected void injectPropertyValues(){
        this.beanMap.values().forEach(bean->{
            ClassManager.instance().getFieldsFromClassWithAnnotation(bean.getClass(), Prop.class)
                    .stream()
                    .forEach(field -> {
                        field.setAccessible(true);
                        try {
                            if(field.getType().equals(Integer.class)){
                                field.set(bean,PropReader.instance().getAsInt(field.getAnnotation(Prop.class).key()));
                            }else if(field.getType().equals(String.class)){
                                field.set(bean,PropReader.instance().getAsString(field.getAnnotation(Prop.class).key()));
                            }
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    });
        });
    }

    protected void createBeans(Map<String,Class<?>> beanClassMap){
        this.beanMap = beanClassMap.values()
                .stream()
                .collect(Collectors.toMap(
                        beanClass->beanClass.getName(),
                        beanClass-> {
                            try {
                                return beanClass.getDeclaredConstructor().newInstance();
                            } catch (Exception e) {
                                throw new NoDefaultConstructorException();
                            }
                        })
                );
    }

    protected Map<String,Class<?>> createBeanClassMap(String sourcePackage){
        return PackageManager.instance()
                .getAllClassesWithAnnotation(Bean.class, sourcePackage)
                .stream()
                .collect(Collectors.toMap(
                        beanClass -> beanClass.getName(),
                        beanClass->beanClass));

    }

    protected void validateBeans(Map<String,Class<?>> beanClassMap){
        beanClassMap.values().forEach(beanClass->{
            List<String> lookUp = new ArrayList<>();
            lookUp.add(beanClass.getName());
            validateBeansRec(beanClassMap,beanClass,lookUp);
        });
    }

    protected void validateBeansRec(Map<String,Class<?>> beanClassMap,Class<?> bean,List<String> lookUp){
        ClassManager.instance().getFieldsFromClassWithAnnotation(bean, Inject.class)
                .forEach(field->{
                    Class<?> dependencyClass = field.getType();
                    if(!beanClassMap.containsKey(dependencyClass.getName())){
                        throw new NoSuchABeanException();
                    }
                    if(lookUp.contains(dependencyClass.getName())){
                        throw new CircularDependencyException();
                    }
                    lookUp.add(dependencyClass.getName());
                    validateBeansRec(beanClassMap,dependencyClass,lookUp);
                });

    }


    @Override
    public <T> T getBean(Class<T> beanClass) {
        return (T) this.beanMap.get(beanClass.getName());
    }

    @Override
    public Set<String> getBeanNames() {
        return this.beanMap.keySet();
    }
}

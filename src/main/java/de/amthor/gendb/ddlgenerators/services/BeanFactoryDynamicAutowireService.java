package de.amthor.gendb.ddlgenerators.services;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.amthor.gendb.ddlgenerators.DdlGeneratorServiceInterface;

@Service
public class BeanFactoryDynamicAutowireService {

	private static final String SERVICE_NAME_SUFFIX = "_GeneratorService";
    private final BeanFactory beanFactory;

    @Autowired
    public BeanFactoryDynamicAutowireService(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
    
    DdlGeneratorServiceInterface getGenerator(String dbType) {
    	DdlGeneratorServiceInterface service = beanFactory.getBean(getDBGeneratorBeanName(dbType),  DdlGeneratorServiceInterface.class);
    	
    	return service;    	
    }
    
    private String getDBGeneratorBeanName(String dbType) {
        return dbType + SERVICE_NAME_SUFFIX;
    }
}

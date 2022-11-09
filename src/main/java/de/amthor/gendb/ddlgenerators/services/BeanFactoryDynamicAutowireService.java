package de.amthor.gendb.ddlgenerators.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.amthor.gendb.ddlgenerators.DdlGeneratorServiceInterface;

@Service
public class BeanFactoryDynamicAutowireService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BeanFactoryDynamicAutowireService.class);
	
	private static final String SERVICE_NAME_SUFFIX = "_GeneratorService";
    private final BeanFactory beanFactory;

    @Autowired
    public BeanFactoryDynamicAutowireService(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
    
    public DdlGeneratorServiceInterface getGenerator(String dbType) {
    	
    	try {
    		DdlGeneratorServiceInterface service = beanFactory.getBean(getDBGeneratorBeanName(dbType),  DdlGeneratorServiceInterface.class);
    		LOGGER.info("================> " + service.getDbType() );
        	return service;    	
    	}
    	catch ( BeansException ex ) {
    		LOGGER.error("No such Bean: " + getDBGeneratorBeanName(dbType));
    	}
    	return null;    	
    }
    
    private String getDBGeneratorBeanName(String dbType) {
        return dbType + SERVICE_NAME_SUFFIX;
    }
}

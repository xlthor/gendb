package de.amthor.gendb.service.impl;

import org.modelmapper.ModelMapper;


public class ServiceBase {

	
	protected ModelMapper mapper;

	public ServiceBase(ModelMapper mapper) {
        this.mapper = mapper;
	}
	
	
    /**
     * Generic simple object mapper
     * 
     * @param <To> Target class
     * @param <From> Source class 
     * @param source Source
     * @param type Target type
     * @return mapped object
     */
    public <To, From> To genericSimpleMapper(From source, Class<To>type) {
    	To target = source != null ? mapper.map(source, type) : null;
        return target;
    }
	
}

/**
 * Copyright (C) 2016 Infinite Automation Software. All rights reserved.
 * @author Terry Packer
 */
package com.serotonin.m2m2.reports;

import com.serotonin.m2m2.module.ModelDefinition;
import com.serotonin.m2m2.reports.vo.ReportModel;
import com.serotonin.m2m2.web.mvc.rest.v1.model.AbstractRestModel;
import com.serotonin.m2m2.web.mvc.rest.v1.model.AbstractVoModel;

/**
 * @author Terry Packer
 *
 */
public class ReportModelDefinition extends ModelDefinition{
	
	public static final String TYPE_NAME = "REPORT";

	/* (non-Javadoc)
	 * @see com.serotonin.m2m2.module.ModelDefinition#getModelKey()
	 */
	@Override
	public String getModelKey() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.serotonin.m2m2.module.ModelDefinition#getModelTypeName()
	 */
	@Override
	public String getModelTypeName() {
		return TYPE_NAME;
	}

	/* (non-Javadoc)
	 * @see com.serotonin.m2m2.module.ModelDefinition#createModel()
	 */
	@Override
	public AbstractVoModel<?> createModel() {
	    return new ReportModel();
	}

	/* (non-Javadoc)
	 * @see com.serotonin.m2m2.module.ModelDefinition#supportsClass(java.lang.Class)
	 */
	@Override
	public boolean supportsClass(Class<?> clazz) {
		return ReportModel.class.equals(clazz);
	}

	/* (non-Javadoc)
	 * @see com.serotonin.m2m2.module.ModelDefinition#getModelClass()
	 */
	@Override
	public Class<? extends AbstractRestModel<?>> getModelClass() {
		return ReportModel.class;
	}
}

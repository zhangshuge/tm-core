package com.ccbtrust.core.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
/**
 * 
 * @author ciyuan
 *
 */
public class CommonController extends AbstractController{

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("calll contrller: " + getClass().getSimpleName());
		return null;
	}

}

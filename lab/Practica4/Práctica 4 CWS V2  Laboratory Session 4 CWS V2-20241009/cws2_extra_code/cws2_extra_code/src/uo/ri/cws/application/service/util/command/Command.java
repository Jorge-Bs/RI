package uo.ri.cws.application.service.util.command;

import uo.ri.util.exception.BusinessException;

public interface Command<T> {

	T execute() throws BusinessException; 
}

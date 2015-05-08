package by.vsu.mf.ammc.pm.service.main;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import by.vsu.mf.ammc.pm.dao.abstraction.Transaction;
import by.vsu.mf.ammc.pm.exception.PersistentException;
import by.vsu.mf.ammc.pm.service.abstraction.Service;
import by.vsu.mf.ammc.pm.service.abstraction.ServiceFactory;

public class ServiceFactoryImpl implements ServiceFactory {
	private static Logger logger = Logger.getLogger(ServiceFactoryImpl.class);

	private static Map<Class<? extends Service>, Class<? extends ServiceImpl>> services = new ConcurrentHashMap<>();
	static {
	}

	private Transaction transaction;

	public ServiceFactoryImpl(Transaction transaction) {
		this.transaction = transaction;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <Type extends Service> Type getService(Class<Type> service) throws PersistentException {
		Class<? extends ServiceImpl> serviceImplClass = services.get(service);
		if(serviceImplClass != null) {
			try {
				ServiceImpl serviceImpl = serviceImplClass.newInstance();
				serviceImpl.setTransaction(transaction);
				return (Type)Proxy.newProxyInstance(serviceImplClass.getClassLoader(), new Class<?>[]{service}, new InvocationHandlerImpl(serviceImpl));
			} catch(InstantiationException | IllegalAccessException e) {
				logger.error("It is impossible to instantiate service class", e);
				throw new PersistentException(e);
			}
		}
		return null;
	}

	@Override
	public void close() {
		transaction.close();
	}

	private static class InvocationHandlerImpl implements InvocationHandler {
		private ServiceImpl service;

		public InvocationHandlerImpl(ServiceImpl service) {
			this.service = service;
		}

		@Override
		public Object invoke(Object object, Method method, Object[] argiments) throws Throwable {
			try {
				Object result = method.invoke(service, argiments);
				service.getTransaction().commit();
				return result;
			} catch(InvocationTargetException | IllegalAccessException | IllegalArgumentException | PersistentException e) {
				rollback();
				throw e instanceof PersistentException ? e : e.getCause();
			}
		}

		private void rollback() {
			try {
				service.getTransaction().rollback();
			} catch(PersistentException e) {
				logger.error("It is impossible to rollback transaction", e);
			}
		}
	}
}

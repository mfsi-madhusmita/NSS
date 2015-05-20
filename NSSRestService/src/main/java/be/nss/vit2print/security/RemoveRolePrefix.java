package be.nss.vit2print.security;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.PriorityOrdered;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

public class RemoveRolePrefix implements BeanPostProcessor, PriorityOrdered {

	@Override
	public int getOrder() {
		return PriorityOrdered.HIGHEST_PRECEDENCE;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		if (bean instanceof DefaultMethodSecurityExpressionHandler)
			((DefaultMethodSecurityExpressionHandler) bean)
					.setDefaultRolePrefix(null);

		if (bean instanceof DefaultWebSecurityExpressionHandler)
			((DefaultWebSecurityExpressionHandler) bean)
					.setDefaultRolePrefix(null);

		return bean;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		return bean;
	}

}

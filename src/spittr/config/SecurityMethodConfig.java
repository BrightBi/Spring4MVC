package spittr.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

import spittr.security.Security;

/**
 * 启用 Spring 的方法保护需要配置 @EnableGlobalMethodSecurity()。
 * securedEnabled=true 设置启用 @Secured。
 * jsr250Enabled=true 设置启用 @RolesAllowed。
 * prePostEnabled=true 设置启用 @PreAuthorize @PostAuthorize。
 * 
 * createExpressionHandler 配置自定义的安全检查。
 */
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityMethodConfig extends GlobalMethodSecurityConfiguration {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("bb").password("123").authorities("ROLE_USER")
		.and().withUser("mm").password("456").authorities("ROLE_USER", "ROLE_ADMIN");
		System.out.println("SecurityMethodConfig configure");
	}

	@Override
	protected MethodSecurityExpressionHandler createExpressionHandler() {
		DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
		expressionHandler.setPermissionEvaluator(new Security());
		return expressionHandler;
	}
	
}

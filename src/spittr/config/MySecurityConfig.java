package spittr.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Spring Security 必须配置在一个实现了 WebSecurityConfigurer 的 bean 中， 或者扩展
 * WebSecurityConfigurerAdapter。
 */
@Configuration
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// 使用内存用户存储
		auth.inMemoryAuthentication().withUser("bi").password("123").authorities("ROLE_USER")
		.and().withUser("mi").password("456").authorities("ROLE_USER", "ROLE_ADMIN");

		// 使用 JDBC 用户存储
		/*
		 * auth.jdbcAuthentication().dataSource(null)
		 * .usersByUsernameQuery(" select username, password, true from table where username = ? ")
		 * .authoritiesByUsernameQuery(" select username, 'ROLE_USER' from table where username = ? ")
		 * .passwordEncoder(new StandardPasswordEncoder("bi@1987"));
		 */

		// 使用 LDAP 用户存储
		/*
		 * 密码加密传送到远程进行比对
		 * auth.ldapAuthentication().userSearchBase("ou=people").userSearchFilter("(uid={0})")
		 * .groupSearchBase("ou=groups").groupSearchFilter("member={0}")
		 * .passwordCompare().passwordEncoder(new Md5PasswordEncoder()).passwordAttribute("passcode");
		 * 
		 * 使用远程 LDAP 需要指定 URL
		 * auth.ldapAuthentication().userSearchBase("ou=people").userSearchFilter("(uid={0})")
		 * .groupSearchBase("ou=groups").groupSearchFilter("member={0}")
		 * .contextSource().url("ldap://habuma.com:389/dc=habuma,dc=com");
		 *
		 *使用嵌入式 LDAP
		 * auth.ldapAuthentication().userSearchBase("ou=people").userSearchFilter("(uid={0})")
		 * .groupSearchBase("ou=groups").groupSearchFilter("member={0}")
		 * .contextSource().root("dc=habuma,dc=com").ldif("classpath:users.ldif");
		 */
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/*
		 * authenticated() 要求用户必须登陆
		 * hasAuthority("ROLE_ADMIN") 要求用户必须具有 ROLE_ADMIN 权限
		 * access("hasRole('ROLE_ADMIN') and hasIpAddress('127.0.0.1')") 要求用户必须具有 ROLE_ADMIN 权限，且来自指定 ip 的请求
		 * 
		 * 在为路径配置权限时候需要注意，antMatchers/regexMatchers/anyRequest 调用的顺序决定了安全检查顺序。
		 * 详细的路径配置应放在模糊的路径配置之前调用。如果先配置了 anyRequest 那么后续配置的路径无法被执行到。
		 */
		http.formLogin() // 使用默认登陆页面
		.and().authorizeRequests()
		.antMatchers(HttpMethod.GET, "/security/mvc/one").authenticated() // 对指定路径的指定 HttpMethod 配置安全检查
		.antMatchers("/security/mvc/two", "/security/mvc/three").hasAuthority("ROLE_ADMIN") // 对多个指定路径配置安全检查
		.regexMatchers("/security/mvc/regex/.*").access("hasRole('ROLE_ADMIN') and hasIpAddress('127.0.0.1')") // 对匹配正则表达式指定的路径配置安全检查
		.regexMatchers("/self/.*").access("hasRole('ROLE_ADMIN')") // 验证对自定义的 servlet 也会安全检查
		.anyRequest().permitAll() // 对其余路径不做安全检查
		.and().requiresChannel().antMatchers("/security/mvc/s").requiresSecure() // 对指定路径的请求必须是 https 请求
		.and().csrf().disable(); // 关闭了跨站防护
	}
}

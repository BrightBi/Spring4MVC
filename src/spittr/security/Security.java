package spittr.security;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

/**
 * 
 * 编写自己的安全检查需要实现 PermissionEvaluator 接口，并实现它的两个 hasPermission 方法。
 * 自己的安全检查要想生效，还需要将其配置到 GlobalMethodSecurityConfiguration 中。
 * 通过 spittr.config.SecurityMethodConfig 重写 createExpressionHandler 方法来实现配置。
 *
 */
public class Security implements PermissionEvaluator {

	@Override
	public boolean hasPermission(Authentication auth, Object target, Object permission) {
		System.out.println("auth=" + auth);
		System.out.println("target=" + target);
		System.out.println("permission=" + permission);
		if (target instanceof String) {
			String s = (String) target;
			return s.length() < 6;
		}
		return false;
	}

	@Override
	public boolean hasPermission(Authentication auth, Serializable targetId, String targetType, Object permission) {
		return false;
	}
}

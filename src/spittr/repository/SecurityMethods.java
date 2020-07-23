package spittr.repository;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
/**
 * 使用 @RolesAllowed 需要引入 javax.annotation-api-1.3.2.jar
 */
@Component
public class SecurityMethods {

	/*
	 *  @Secured 中的参数要求调用此方法的用户至少拥有参数中的一种角色权限，
	 *  @Secured 是基于 Spring 的。
	 */
	@Secured(value = {"ROLE_ADMIN"})
	public String secured() {
		return "@Secured";
	}
	
	/*
	 *  @RolesAllowed 中的参数要求调用此方法的用户至少拥有参数中的一种角色权限，
	 *  @RolesAllowed 是基于 JSR-250 的。
	 */
	@RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
	public String rolesAllowed() {
		return "@RolesAllowed";
	}
	
	/*
	 *  @PreAuthorize 在方法调用前检查权限(@PreAuthorize 支持SpEL)
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	public String preAuthorize() {
		System.out.println("In preAuthorize");
		return "@PreAuthorize";
	}
	/*
	 *  当用户是 ROLE_USER 角色时候入参长度不能超过 50，或者用户角色是 ROLE_ADMIN。
	 *  可以应用于会员可以无限上传，非会员上传有长度限制。
	 *  #s 是引用了同名入参。
	 */
	@PreAuthorize("hasRole('ROLE_USER') and #name.length() < 5 or hasRole('ROLE_ADMIN')")
	public String preAuthorizeSpEL(String name) {
		System.out.println("In preAuthorizeSpEL");
		return "@PreAuthorize " + name;
	}
	
	/*
	 *  @PostAuthorize 在方法调用完，拿到返回结果以后判断是否抛出安全性异常(@PostAuthorize 支持SpEL)。
	 *  returnObject 指代方法的返回值。
	 *  principal 是 Spring Security 内置对象，包含当前登陆用户主要信息（通常是用户名，所以要现登陆才能用 principal）
	 */
	@PostAuthorize("returnObject.length() < 20 and hasRole('ROLE_USER') and principal.username == 'bi'")
	public String postAuthorize(String s) {
		System.out.println("In postAuthorize");
		return "@PostAuthorize " + s;
	}
	
	/*
	 *  @PostFilter 在方法调用完，拿到返回结果以后对结果集进行过滤返回(@PostFilter 支持SpEL)。
	 *  filterObject 指代方法的返回值。
	 */
	@PostFilter("filterObject.length() < 5")
	public List<String> postFilter() {
		System.out.println("In postFilter");
		List<String> list = new ArrayList<>();
		list.add("123");
		list.add("456");
		list.add("123456");
		return list;
	}
	
	/*
	 *  @PreFilter 在方法调用前，对参数进行过滤返回(@PreFilter 支持SpEL)。
	 *  filterObject 指代方法的入参值。 如果有多个入参，需要配合 filterTarget 指定具体参数。
	 */
	@PreFilter(filterTarget = "list", value = "filterObject.length() < 5")
	public int preFilter(List<String> list, String s) {
		System.out.println("In preFilter");
		return list.size();
	}
	
	/*
	 *  调用 hasPermission 来做安全检查，这里需要 spittr.security.Security 提供具体实现。
	 */
	@PreFilter("hasPermission(filterObject, 'bi')")
	public int selfPermissionCheck(List<String> list) {
		System.out.println("In selfPermissionCheck");
		return list.size();
	}
}
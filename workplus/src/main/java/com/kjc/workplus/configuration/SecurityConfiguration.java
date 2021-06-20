package com.kjc.workplus.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.kjc.workplus.login.handler.LoginFailHandler;
import com.kjc.workplus.login.service.MemberService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private LoginFailHandler loginFailHandler;

	@Bean
	public PasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
		
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		
		web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**");
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
//		http
//			.authorizeRequests() // 해당 메소드 아래는 각 경로에 따른 권한을 지정할 수 있음.
//				.antMatchers("/", "/login", "/service", "/resources/**", "/create").permitAll() // 로그인 권한은 누구나, resources 파일도 모든 권한
//				.antMatchers("/admin").hasRole("ADMIN") // 괄호의 권한을 가진 유저만 접근가능, ROLE_가 붙어서 적용 됨. 즉, 테이블에 ROLE_권한명으로 저장해야 함.
//				.antMatchers("/user").hasRole("USER")
//				.antMatchers("/member").hasRole("MEMBER")
//				.anyRequest().authenticated() // 로그인된 사용자가 요청을 수행할 때 필요함. 만약 사용자가 인증되지 않았다면, 스프링 시큐리티 필터는 요청을 잡아내고 사용자를 로그인 페이지로 리다이렉션 해줌.
//				.and()
//			.formLogin() // 하위에 내가 직접 구현한 로그인 폼, 로그인 성공시 이동 경로 설정 가능. 로그인 폼의 아이디, 패스워드는 username, password로 맞춰야 함.
//						.loginPage("/login") // 로그인이 수행될 경로
//						.loginProcessingUrl("/loginProcess") // 로그인form의 action과 일치시켜주어야 함.
//						.defaultSuccessUrl("/loginSuccess") // 로그인 성공 시 이동할 경로
//						.failureUrl("/login?error=true") // 인증에 실패했을 때 보여주는 화면 url, 로그인 form으로 파라미터값 error=true로 보냄
//					.permitAll()
//					.and()
//				.logout()
//					.permitAll()
//					.logoutUrl("/logout")
//					.logoutSuccessUrl("/")
//					.and()
//				.exceptionHandling()
//					.accessDeniedPage("/accessDenied_page"); // 권한이 없는 대상이 접속을 시도했을 때
		
		http.authorizeRequests()
		        .antMatchers("/workplus/member/**").hasRole("MEMBER")
		        .antMatchers("/workplus/admin/**").hasRole("ADMIN")
		        .antMatchers("/workplus/**").permitAll();

		http.formLogin()
		        .loginPage("/workplus/login.do")
		        .defaultSuccessUrl("/workplus/main.do")
		        .failureHandler(loginFailHandler)
		        .permitAll();
		
		http.logout()
		        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		        .logoutSuccessUrl("/workplus/main.do")
		        .invalidateHttpSession(true);
		
		http.exceptionHandling()
		        .accessDeniedPage("/workplus/denied.do");
		
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
		
	}
}

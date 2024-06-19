package almond.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MessageUtil {

	@Before("execution(* almond.*.*.*(..))")
	public void beforeMessage(JoinPoint jp) {
		System.out.println("----- " + jp.getSignature() + " メソッド開始 -----");
	}
}
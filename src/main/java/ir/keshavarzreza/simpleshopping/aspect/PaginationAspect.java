//package ir.keshavarzreza.simpleshopping.aspect;
//
//
//import ir.keshavarzreza.simpleshopping.domain.dto.ApiPagination;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ResponseStatusException;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//
//@Slf4j
//@Aspect
//@Component
//public class PaginationAspect {
//	final String ACCEPTED_REGEX = "(^[a-zA-Z]+)(\\.(asc|desc))?" +
//			"((,[a-zA-Z]+)(\\.(asc|desc))?)*" +
//			"$";//for example, we want to accept name.desc,family.ASC
//
//	private final HttpServletRequest request;
//
//	public PaginationAspect(HttpServletRequest request) {
//		this.request = request;
//	}
//
//
//	@Around(value = "@annotation(ir.keshavarzreza.simpleshopping.annotation.Pagination)")
//	public ResponseEntity beforeAccountMethodExecution(ProceedingJoinPoint proceedingJoinPoint) throws Exception {
//		for (int i = 0; i < proceedingJoinPoint.getArgs().length; i++) {
//			ApiPagination apiPagination = new ApiPagination();
//			if (proceedingJoinPoint.getArgs()[i] instanceof ApiPagination) {
//
//				String pattern = "^\\d*$";
//
//				if (request.getParameter("page") != null && request.getParameter("page").matches(pattern) && Integer.valueOf(request.getParameter("page")) != 0) {
//					apiPagination.setPage(Integer.valueOf(Integer.parseInt(request.getParameter("page"))) - 1);
//				} else
//					apiPagination.setPage(0);
//
//				if (request.getParameter("size") != null && request.getParameter("size").matches(pattern) && Integer.valueOf(request.getParameter("size")) != 0) {
//					apiPagination.setSize(Integer.valueOf(Integer.parseInt(request.getParameter("size"))));
//				} else
//					apiPagination.setSize(10);
//
//
//				if (request.getParameter("sort") != null) {
//					String sort = request.getParameter("sort");
//					//
//					Matcher m = Pattern.compile(ACCEPTED_REGEX, Pattern.CASE_INSENSITIVE)
//							.matcher(sort);
//
//					if (m.find()) {
//						apiPagination.setInputSortString(sort);
//					} else
//						throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request for sort parameter");
//				}
//				proceedingJoinPoint.getArgs()[i] = apiPagination;
//			}
//		}
//
//		try {
//			return (ResponseEntity) proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
//		} catch (Throwable throwable) {
//			log.trace("error occurred while proceeding pagination aspect operations. detail:{}", throwable.getMessage());
//			if (throwable instanceof ResponseStatusException) {
//				throw (ResponseStatusException) throwable;
//			} else {
//				throw (Exception) throwable;
//			}
//		}
//	}
//}

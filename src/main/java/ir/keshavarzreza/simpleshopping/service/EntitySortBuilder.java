package ir.keshavarzreza.simpleshopping.service;

import ir.keshavarzreza.simpleshopping.exceptions.SortParameterHasProblomException;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.data.domain.Sort;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EntitySortBuilder {
	final static String ACCEPTED_REGEX = "(^[a-zA-Z]+)(\\.(asc|desc))?" +
			"((,[a-zA-Z]+)(\\.(asc|desc))?)*" +
			"$";//name.desc,id.ASC

	final static String ASCENDING_STR = "ASC";
	final static String DESCENDING_STR = "DESC";

	private static String[] extractSortExpressions(String regex) {
		return regex.split(",");
	}

	private static String extractRequestFieldName(String regexExpression) {
		return regexExpression.split("\\.")[0];
	}

	private static String extractDirection(String regexExpression) {
		return regexExpression.split("\\.").length == 1 ? "" : regexExpression.split("\\.")[1];
	}

	public static Sort buildSort(String regexSortRequest) throws SortParameterHasProblomException, InvalidPropertyException {

		Matcher m = Pattern.compile(ACCEPTED_REGEX, Pattern.CASE_INSENSITIVE)
				.matcher(regexSortRequest);
		Sort allSorts = null;
		if (!m.find()) throw new SortParameterHasProblomException();
		String[] expressions = extractSortExpressions(regexSortRequest);
		for (int i = 0; i < expressions.length; ++i) {
			String oneRegexExpression = expressions[i];
			String requestFieldName = extractRequestFieldName(oneRegexExpression);
			String sortDirection = extractDirection(oneRegexExpression).toUpperCase();
			Sort specialSort;
			if (sortDirection.equals(ASCENDING_STR))
				specialSort = Sort.by(Sort.Order.asc(requestFieldName).ignoreCase());
			else if (sortDirection.equals(DESCENDING_STR))
				specialSort = Sort.by(Sort.Order.desc(requestFieldName).ignoreCase());
			else
				specialSort = Sort.by(Sort.Order.by(requestFieldName).ignoreCase());
			allSorts = (allSorts != null) ? allSorts.and(specialSort) : specialSort;
		}

		return allSorts;
	}
}
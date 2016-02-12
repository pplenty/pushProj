package com.pushman.util;

public class ListPagingUtil {
	// 현재페이지 시작 인덱스 구하는 함수
	public static int getStartIndexOfPage(int pageNo, int pageSize) {
		return (pageNo - 1) * pageSize;
	}

	// 전체 페이지 수 구하는 함수
	public static int countTotalPage(int pageSize, int totalRecords) {
		int maxPage = totalRecords / pageSize;
		if (totalRecords % pageSize > 0) {
			maxPage++;
		}
		return maxPage;
	}
	
	
}

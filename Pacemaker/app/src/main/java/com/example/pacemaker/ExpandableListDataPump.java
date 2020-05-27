package com.example.pacemaker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> category1 = new ArrayList<String>();
        category1.add("함수");
        category1.add("연속함수");
        category1.add("미분계수");
        category1.add("미분법 기본 공식");
        category1.add("초월함수의 미분공식");
        category1.add("여러가지 함수의 미분법");
        category1.add("고계 도함수");
        category1.add("평균값 정리 및 극한");
        category1.add("테일러급수");
        category1.add("곡선의 개형 및 최대값, 최소값");
        category1.add("속도와 가속도");

        List<String> category2 = new ArrayList<String>();
        category2.add("부정적분");
        category2.add("치환적분법");
        category2.add("삼각치환법");
        category2.add("부분적분법");
        category2.add("유리함수의 부정적분");
        category2.add("무리함수의 부정적분");
        category2.add("삼각함수의 적분법");
        category2.add("정적분");
        category2.add("미적분학의 기본정리");
        category2.add("정적분의 계산");
        category2.add("이상적분");
        category2.add("정적분의 응용");
        category2.add("속도, 거리 적분");

        List<String> category3 = new ArrayList<String>();
        category3.add("행렬의 정의");
        category3.add("곱셈");
        category3.add("행렬의 분류");
        category3.add("행렬식");
        category3.add("역행렬");
        category3.add("행렬의 계수");
        category3.add("벡터의 내적");
        category3.add("외적 또는 벡터곱");
        category3.add("공간에서의 직선, 평면의 방정식");
        category3.add("벡터공간에서의 기저");
        category3.add("행, 열공간과 영공간");
        category3.add("rank와 퇴화차수");
        category3.add("고유치, 고유벡터");
        category3.add("선형사상");

        List<String> category4 = new ArrayList<String>();
        category4.add("편도함수");
        category4.add("2계 편도함수");
        category4.add("전미분");
        category4.add("합성함수의 미분법와 음함수의 미분법");
        category4.add("경도 및 방향미분계수");
        category4.add("접선, 법선 및 접평면, 법평면");
        category4.add("2변수함수의 테일러 급수 전개");
        category4.add("2변수 함수의 극대, 극소");

        List<String> category5 = new ArrayList<String>();
        category5.add("이중적분");
        category5.add("극좌표계에서의 이중적분");
        category5.add("공간상 입체의 체적");
        category5.add("곡면의 면적");
        category5.add("3중적분");

        List<String> category6 = new ArrayList<String>();
        category6.add("무한급수의 성질");
        category6.add("양항급수");
        category6.add("교대급수");
        category6.add("멱급수의 수렴반경과 구간");

        List<String> category7 = new ArrayList<String>();
        category7.add("1계미분방정식");
        category7.add("고계선형미분방정식");
        category7.add("비제차선형미분방정식");
        category7.add("미분방정식의 급수해법");
        category7.add("연립 1계선형미분방정식");

        List<String> category8 = new ArrayList<String>();
        category8.add("Laplace 변환과 역 Laplace 변환");
        category8.add("Laplace 변환과 역 Laplace 변환의 기본성질");
        category8.add("특수함수와 그 Laplace 변환");
        category8.add("Convolution 정리");
        category8.add("Laplace 변환의 무한적분에 이용");

        List<String> category9 = new ArrayList<String>();
        category9.add("발산률과 회전");
        category9.add("곡선과 곡면 위의 적분");

        List<String> category10 = new ArrayList<String>();
        category10.add("조화함수");
        category10.add("복소수의 극형식");
        category10.add("복소함수의 정적분");
        category10.add("Cauchy의 적분 공식");
        category10.add("극 및 특이점");
        category10.add("유수");

        List<String> category11 = new ArrayList<String>();
        category11.add("Fourier 급수전개식");
        category11.add("기함수, 우함수의 Fourier 급수전개식");

        expandableListDetail.put("미분법", category1);
        expandableListDetail.put("적분법", category2);
        expandableListDetail.put("선형대수", category3);
        expandableListDetail.put("편미분", category4);
        expandableListDetail.put("중적분", category5);
        expandableListDetail.put("무한급수", category6);
        expandableListDetail.put("미분방정식", category7);
        expandableListDetail.put("Laplace 변환", category8);
        expandableListDetail.put("선적분과 면적분", category9);
        expandableListDetail.put("복소함수", category10);
        expandableListDetail.put("Fourier 급수", category11);
        return expandableListDetail;
    }
}

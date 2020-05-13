package com.example.pacemaker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> category1 = new ArrayList<String>();
        category1.add("함수와 극한");
        category1.add("도함수");
        category1.add("미분법의 응용");
        category1.add("적분");
        category1.add("적분의 응용");
        category1.add("역함수: 지수함수, 로그함수, 역삼각함수");
        category1.add("적분방법");
        category1.add("적분법의 다양한 응용");
        category1.add("매개변수방정식과 극좌표");
        category1.add("무한수열과 무한급수");
        category1.add("벡터와 공간기하학");
        category1.add("벡터함수");
        category1.add("편도함수");
        category1.add("다중적분");
        category1.add("벡터해석");

        List<String> category2 = new ArrayList<String>();
        category2.add("행렬");
        category2.add("행렬의 연산");
        category2.add("특수 행렬");
        category2.add("행렬식");
        category2.add("수반행렬과 역행렬");
        category2.add("선형연립방정식과 계수");
        category2.add("벡터");
        category2.add("직선과 평면의 방정식");
        category2.add("벡터공간");
        category2.add("고윳값");
        category2.add("선형변환");
        category2.add("내적공간");
        category2.add("직교행렬, 대칭행렬, 멱등행렬, 멱영행렬");
        category2.add("이차형식");

        List<String> category3 = new ArrayList<String>();
        category3.add("벡터장의 발산과 회전");
        category3.add("선적분");
        category3.add("면적분");
        category3.add("1계 미분방정식");
        category3.add("제차 선형 미분방정식");
        category3.add("비제차 선형 미분방정식");
        category3.add("멱급수해");
        category3.add("연립 1계 선형방정식");
        category3.add("모델링");
        category3.add("라플라스 변환");
        category3.add("푸리에 급수");
        category3.add("복소미분");
        category3.add("복소적분");

        expandableListDetail.put("미분적분학", category1);
        expandableListDetail.put("선형대수학", category2);
        expandableListDetail.put("공업수학", category3);
        return expandableListDetail;
    }
}

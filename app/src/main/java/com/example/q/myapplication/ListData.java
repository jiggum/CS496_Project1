package com.example.q.myapplication;

/**
 * Created by q on 2016-06-29.
 */
import android.graphics.drawable.Drawable;

import java.text.Collator;
import java.util.Comparator;

public class ListData {
    /* 리스트 정보를 담고 있을 객체 생성 */
    public Drawable mPhoto;
    public String mName;
    public String mNumber;

    /* 알파벳 정렬 */

    public static final Comparator<ListData> ALPHA_COMPARATOR = new Comparator<ListData>() {
        private final Collator sCollactor = Collator.getInstance();

        @Override
        public int compare(ListData lhs, ListData rhs) {
            return sCollactor.compare(lhs.mName,rhs.mName);
        }
    };
}

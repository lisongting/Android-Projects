package com.test.administrator.fragmenttest;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/9/22.
 * [有错]
 */
public class BookDetailFragment extends Fragment {
    public static final String ITEM_ID="item_id";
    BookContent.Book book;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //这里总是报空指针异常(照着书上的代码敲的还报错,书上也不说清楚)
        if(getArguments().containsKey(ITEM_ID)){
            book = BookContent.ITEM_MAP.get(getArguments().getInt(ITEM_ID));//???
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_book_detail,container,false);
        if(book!=null){
            ((TextView)root.findViewById(R.id.bookTitle)).setText(book.title);
            ((TextView)root.findViewById(R.id.book_desc)).setText(book.desc);

        }
        return root;
    }

}

package com.prarui.common;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by Prarui on 2017/8/25.
 */

public class DisView extends LinearLayout {
    public DisView(Context context) {
        this(context,null);

    }

    public DisView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DisView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}

package com.bogdanorzea.regexquiz;

import java.io.Serializable;

public interface OnCheckClickListener extends Serializable {
    void onCheckClick(Question q);
}

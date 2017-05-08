package com.bogdanorzea.regexquiz;

import java.io.Serializable;

interface OnCheckClickListener extends Serializable {
    void onCheckClick(Question q);
}

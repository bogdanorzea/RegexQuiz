package com.bogdanorzea.regexquiz;

import java.io.Serializable;

public interface OnItemClickListener extends Serializable {
    void onItemClick(Question q);
}

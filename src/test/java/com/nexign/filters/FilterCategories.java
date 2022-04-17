package com.nexign.filters;

public enum FilterCategories {
    Brand ("Бренд"), Category ("Категория");
    public final String rusName;

    FilterCategories(String rusName) {
        this.rusName = rusName;
    }
}

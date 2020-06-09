package com.fintech.demo.util;

public class Constant {
    public final static String TOKEN = "?token=Tpk_ee567917a6b640bb8602834c9d30e571";
    public final static String ALL_COMPANIES_URL = "https://sandbox.iexapis.com/stable/ref-data/symbols" + TOKEN;
    public final static String COMPANY_INFO_URL = "https://sandbox.iexapis.com/stable/stock/%s/quote" + TOKEN;
}

package skalii.testjob.db2limited.data.type;

public enum CurrencyType {

    AED("AED"),
    AMD("AMD"),
    AZN("AZN"),
    AUD("AUD"),
    BDT("BDT"),
    BGN("BGN"),
    BRL("BRL"),
    BYN("BYN"),
    CAD("CAD"),
    CHF("CHF"),
    CNY("CNY"),
    CZK("CZK"),
    DKK("DKK"),
    DZD("DZD"),
    EGP("EGP"),
    EUR("EUR"),
    GBP("GBP"),
    GEL("GEL"),
    GHS("GHS"),
    HKD("HKD"),
    HRK("HRK"),
    HUF("HUF"),
    IDR("IDR"),
    ILS("ILS"),
    IQD("IQD"),
    INR("INR"),
    IRR("IRR"),
    JPY("JPY"),
    KRW("KRW"),
    KGS("KGS"),
    KZT("KZT"),
    LBP("LBP"),
    LYD("LYD"),
    MAD("MAD"),
    MDL("MDL"),
    MXN("MXN"),
    MYR("MYR"),
    NOK("NOK"),
    NZD("NZD"),
    PLN("PLN"), //todo Polish zloty NBU
    PLZ("PLZ"), //todo Polish zloty PB
    RON("RON"),
    RSD("RSD"),
    RUR("RUR"), //todo Russian ruble PB
    RUB("RUB"), //todo Russian ruble NBU
    SAR("SAR"),
    SEK("SEK"),
    SGD("SGD"),
    THB("THB"),
    TJS("TJS"),
    TMT("TMT"),
    TND("TND"),
    TRY("TRY"),
    TWD("TWD"),
    UAH("UAH"),
    USD("USD"),
    UZS("UZS"),
    VND("VND"),
    XAG("XAG"),
    XAU("XAU"),
    XDR("XDR"),
    XPD("XPD"),
    XPT("XPT"),
    ZAR("ZAR");

    private String title;

    CurrencyType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}
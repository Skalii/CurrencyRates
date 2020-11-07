package skalii.testjob.db2limited.data.model;


import skalii.testjob.db2limited.data.type.CurrencyType;


public interface CurrencyBase {

    CurrencyType getCurrencyType();

    void setCurrencyType(CurrencyType currencyType);

    double getFirstValue();

    void setFirstValue(double buy);

    double getSecondValue();

    void setSecondValue(double sale);

}
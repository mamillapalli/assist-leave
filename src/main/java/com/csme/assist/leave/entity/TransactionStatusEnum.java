package com.csme.assist.leave.entity;

import java.util.HashMap;
import java.util.Map;

public enum TransactionStatusEnum {

    PENDING,MASTER;

    private static Map<String, TransactionStatusEnum> valueMap;

    public static TransactionStatusEnum getValue(String possibleName)
    {
        if (valueMap == null)
        {
            valueMap = new HashMap<String, TransactionStatusEnum>();
            for(TransactionStatusEnum transactionStatus: values())
                valueMap.put(transactionStatus.toString(), transactionStatus);
        }
        return valueMap.get(possibleName);

    }
}

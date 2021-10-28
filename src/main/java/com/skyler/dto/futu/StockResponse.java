/**
  * Copyright 2021 bejson.com 
  */
package com.skyler.dto.futu;

/**
 * Auto-generated: 2021-10-24 0:2:28
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class StockResponse {

    private int retType;
    private String retMsg;
    private int errCode;
    private S2c s2c;
    public void setRetType(int retType) {
         this.retType = retType;
     }
     public int getRetType() {
         return retType;
     }

    public void setRetMsg(String retMsg) {
         this.retMsg = retMsg;
     }
     public String getRetMsg() {
         return retMsg;
     }

    public void setErrCode(int errCode) {
         this.errCode = errCode;
     }
     public int getErrCode() {
         return errCode;
     }

    public void setS2c(S2c s2c) {
         this.s2c = s2c;
     }
     public S2c getS2c() {
         return s2c;
     }

}
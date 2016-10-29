package com.jf.xyweather.model;

import java.io.Serializable;

/**
 * Created by JF on 2016/10/29.
 * Suggestion of life(index of life)
 */
public class LifeSuggestion implements Serializable{

    private Index comf;     //舒适度
    private Index cw;       //洗车指数
    private Index drsg;     //穿衣指数
    private Index flu;      //感冒指数
    private Index sport;    //运动指数
    private Index trav;     //旅游指数
    private Index uv;       //紫外线指数

    /**
     * Every suggestion of life include brief(brf) and details content(txt)
     */
    public class Index implements Serializable{
        String brf;
        String txt;

        public String getBrf() {
            return brf;
        }

        public void setBrf(String brf) {
            this.brf = brf;
        }

        public String getTxt() {
            return txt;
        }

        public void setTxt(String txt) {
            this.txt = txt;
        }
    }

    public LifeSuggestion(){
    }

    public LifeSuggestion(Index comf, Index cw, Index drsg, Index flu, Index sport, Index trav, Index uv) {
        this.comf = comf;
        this.cw = cw;
        this.drsg = drsg;
        this.flu = flu;
        this.sport = sport;
        this.trav = trav;
        this.uv = uv;
    }

    public Index getComf() {
        return comf;
    }

    public void setComf(Index comf) {
        this.comf = comf;
    }

    public Index getCw() {
        return cw;
    }

    public void setCw(Index cw) {
        this.cw = cw;
    }

    public Index getDrsg() {
        return drsg;
    }

    public void setDrsg(Index drsg) {
        this.drsg = drsg;
    }

    public Index getFlu() {
        return flu;
    }

    public void setFlu(Index flu) {
        this.flu = flu;
    }

    public Index getSport() {
        return sport;
    }

    public void setSport(Index sport) {
        this.sport = sport;
    }

    public Index getTrav() {
        return trav;
    }

    public void setTrav(Index trav) {
        this.trav = trav;
    }

    public Index getUv() {
        return uv;
    }

    public void setUv(Index uv) {
        this.uv = uv;
    }
}

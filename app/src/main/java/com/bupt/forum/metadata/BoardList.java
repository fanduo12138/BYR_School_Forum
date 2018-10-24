package com.bupt.forum.metadata;

/**
 * Created by dolphin on 02/01/2018.
 */

import java.util.HashMap;

public class BoardList {

    private HashMap<String , String> map = null;

    public BoardList(){
        map = new HashMap<String , String>();
        map.put("考研专版","AimGraduate");
        map.put("学为人师，行为世范","BNU");
        map.put("北邮互联网俱乐部","BUPT_Internet_Club");
        map.put("北邮人在上海","BYRatSH");
        map.put("深圳邮人家","BYRatSZ");
        map.put("认证考试","Certification");
        map.put("公务员","CivilServant");
        map.put("管理咨询","Consulting");
        map.put("创业交流","Entrepreneurship");
        map.put("家庭生活","FamilyLife");
        map.put("金融职场","Financecareer");
        map.put("金融投资","Financial");
        map.put("飞跃重洋","GoAbroad");
        map.put("安居乐业","Home");
        map.put("信息产业","IT");
        map.put("毕业生找工作","Job");
        map.put("招聘信息专版","JobInfo");
        map.put("跳槽就业","Jump");
        map.put("网络资源","NetResources");
        map.put("海外北邮人","Overseas");
        map.put("兼职实习信息","ParttimeJob");
        map.put("产品疯人院","PMatBUPT");
        map.put("学习交流区","StudyShare");
        map.put("天气预报","Weather");
        map.put("职场人生","WorkLife");
        map.put("天文","Astronomy");
        map.put("辩论","Debate");
        map.put("视频制作","DV");
        map.put("英语吧","EnglishBar");
        map.put("奇闻异事","Ghost");
        map.put("吉他","Guitar");
        map.put("日语学习","Japanese");
        map.put("韩流吧","KoreanWind");
        map.put("音乐交流区","Music");
        map.put("摄影","Photo");
        map.put("诗词歌赋","Poetry");
        map.put("心理健康在线","PsyHealthOnline");
        map.put("曲苑杂谈","Quyi");
        map.put("书屋","Reading");
        map.put("科幻奇幻","ScienceFiction");
        map.put("T恤文化","Tshirt");
        map.put("美容护肤","Beauty");
        map.put("北邮愿望树","Blessing");
        map.put("衣衣不舍","Clothing");
        map.put("星雨星愿","Constellations");
        map.put("数字生活","DigiLife");
        map.put("创意生活","DIYLife");
        map.put("环境保护","Environment");
        map.put("情感的天空","Feeling");
        map.put("秀色可餐","Food");
        map.put("缘来如此","Friends");
        map.put("健康保健","Health");
        map.put("悄悄话","IWhisper");
        map.put("失物招领与拾金不昧","LostandFound");
        map.put("谈天说地","Talking");


    }

    public String getValue(String key){
        return map.get(key);
    }

    public static void main(String args[]){
        BoardList a = new BoardList();
        System.out.println(a.getValue("语文"));


    }

}

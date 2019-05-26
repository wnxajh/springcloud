package com.wn.util.enumeration;

/**
 * 用户类型枚举
 * @author wnxaj
 *
 */
public enum UserLevelEnum {
	common(0,"普通"),
	vip(1,"VIP"),
	superVip(2,"SUPVIP");
	
	private Integer type;
	private String name;
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private UserLevelEnum(Integer type, String name) {
		this.type = type;
		this.name = name;
	}
	
	public static String getNameByType(Integer type){
		if (type == null)
            return UserLevelEnum.common.name;
        for (UserLevelEnum userLevelEnum : UserLevelEnum.values()) {
            if (userLevelEnum.getType().equals(type))
                return userLevelEnum.getName();
        }
        return UserLevelEnum.common.name;
	}
}

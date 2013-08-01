package cn.lichengwu.app.batch.constant;

/**
 * Proxy Status
 * 
 * @author lichengwu
 * @version 1.0
 * @created 2012-12-31 2:43 PM
 */
public enum ProxyType {

    HTTP(1, "HTTP"), FTP(2, "FTP"), SOCKS4(3, "SOCKS4"), SOCKS5(4, "SOCKS5"), TELNET(5, "TELNET"), HTTPS(
            6, "HTTPS"), UNKNOWN(99, "UNKNOWN");

    private Integer index;

    private String name;

    ProxyType(Integer index, String name) {
        this.index = index;
        this.name = name;
    }

    public static String getName(Integer index) {
        for (ProxyType type : ProxyType.values()) {
            if (index == type.getIndex()) {
                return type.getName();
            }
        }
        return null;
    }

    public static ProxyType getProxyType(Integer index) {
        for (ProxyType proxyType : ProxyType.values()) {
            if (index == proxyType.getIndex()) {
                return proxyType;
            }
        }
        return null;
    }

    public static ProxyType getProxyType(String name) {
        for (ProxyType proxyType : ProxyType.values()) {
            if (proxyType.getName().equals(name)) {
                return proxyType;
            }
        }
        return null;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

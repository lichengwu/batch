package cn.lichengwu.batch.constant;

/**
 * Proxy Status
 * 
 * @author lichengwu
 * @version 1.0
 * @created 2012-12-31 2:43 PM
 */
public enum ProxyStatus {

    OK(1, "ok"), EXPIRED(2, "expired"), RECOVERED(3, "recovered");

    private Integer index;

    private String name;

    ProxyStatus(Integer index, String name) {
        this.index = index;
        this.name = name;
    }

    public String getName(Integer index) {
        for (ProxyStatus status : ProxyStatus.values()) {
            if (index == status.getIndex()) {
                return status.getName();
            }
        }
        return null;
    }

    public ProxyStatus getProxyStatus(Integer index) {
        for (ProxyStatus status : ProxyStatus.values()) {
            if (index == status.getIndex()) {
                return status;
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

package oliver.app.batch.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * proxy
 * 
 * @author lichengwu
 * @version 1.0
 * @created 2012-12-26 PM11:20
 */
public class Proxy implements Serializable {

    private static final long serialVersionUID = -1592101378476981839L;

    /**
     * id
     */
    private Integer id;

    /**
     * {@link oliver.app.batch.constant.ProxyStatus}
     */
    private Integer status;

    private Integer type;

    /**
     * proxy id
     */
    private String ip;

    /**
     * proxy port
     */
    private Integer port;

    /**
     * proxy username
     */
    private String username;

    /**
     * proxy password
     */
    private String password;

    /**
     * proxy add time
     */
    private Date addTime;

    /**
     * proxy last scan date
     */
    private Date updateTime;

    /**
     * proxy responseTime
     */
    private Integer responseTime;

    /**
     * note
     */
    private String note;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Integer responseTime) {
        this.responseTime = responseTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Proxy{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                ", type=" + type +
                ", status=" + status +
                '}';
    }
}

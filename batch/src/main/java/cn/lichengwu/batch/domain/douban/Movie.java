package cn.lichengwu.batch.domain.douban;

import java.io.Serializable;
import java.util.Date;

/**
 * movie.douban.com
 * 
 * @author lichengwu
 * @version 1.0
 * @created 2013-01-13 PM1:57
 */
public class Movie implements Serializable {
    private static final long serialVersionUID = -3932109428681423014L;

    /**
     * id in movie.douban.com
     */
    private Integer movieId;

    /**
     * movie name
     */
    private String movieName;

    /**
     * movie director name
     */
    private String directorName;

    /**
     * writer name
     */
    private String writerName;

    /**
     * move language
     */
    private String movieLanguage;

    /**
     * online date
     */
    private String onlineDate;

    /**
     * producing country or region
     */
    private String producingArea;

    /**
     * movie alias
     */
    private String movieAlias;

    /**
     * IMBb
     */
    private String imbb;

    /**
     * movie site link
     */
    private String site;

    /**
     * add time
     */
    private Date addTime;

    /**
     * mod time
     */
    private Date modTime;

    /**
     * movie rank
     */
    private Integer rank;

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getImbb() {
        return imbb;
    }

    public void setImbb(String imbb) {
        this.imbb = imbb;
    }

    public String getMovieAlias() {
        return movieAlias;
    }

    public void setMovieAlias(String movieAlias) {
        this.movieAlias = movieAlias;
    }

    public String getProducingArea() {
        return producingArea;
    }

    public void setProducingArea(String producingArea) {
        this.producingArea = producingArea;
    }

    public String getOnlineDate() {
        return onlineDate;
    }

    public void setOnlineDate(String onlineDate) {
        this.onlineDate = onlineDate;
    }

    public String getMovieLanguage() {
        return movieLanguage;
    }

    public void setMovieLanguage(String movieLanguage) {
        this.movieLanguage = movieLanguage;
    }

    public String getWriterName() {
        return writerName;
    }

    public void setWriterName(String writerName) {
        this.writerName = writerName;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getModTime() {
        return modTime;
    }

    public void setModTime(Date modTime) {
        this.modTime = modTime;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }
}

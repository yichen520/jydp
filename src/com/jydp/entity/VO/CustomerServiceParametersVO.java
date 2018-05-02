package com.jydp.entity.VO;

/**
 * 客服记录接受vo对象
 * @author njx
 **/
public class CustomerServiceParametersVO {
    private String feedbackTitle; //标题
    private String feedbackContent; //内容

    /**
     * 标题
     * @return 标题
     */
    public String getFeedbackTitle() {
        return feedbackTitle;
    }

    /**
     * 设置标题
     * @param feedbackTitle
     */
    public void setFeedbackTitle(String feedbackTitle) {
        this.feedbackTitle = feedbackTitle;
    }

    /**
     * 内容
     * @return  内容
     */
    public String getFeedbackContent() {
        return feedbackContent;
    }

    /**
     * 内容
     * @param feedbackContent 内容
     */
    public void setFeedbackContent(String feedbackContent) {
        this.feedbackContent = feedbackContent;
    }
}
